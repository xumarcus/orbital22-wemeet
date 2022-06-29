package com.orbital22.wemeet.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.RosterPlanRepository;
import com.orbital22.wemeet.repository.UserRepository;
import com.orbital22.wemeet.security.AclRegisterService;
import de.cronn.testutils.h2.H2Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.security.acls.domain.BasePermission.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Import(H2Util.class)
public class RosterPlanUpdateIntegrationTest {
  @Autowired ObjectMapper objectMapper;
  @Autowired MockMvc mockMvc;

  User talk;
  User cock;
  RosterPlan rosterPlan;

  @WithMockUser(username = "talk@wemeet.com")
  @BeforeEach
  public void setUp(
      @Autowired UserRepository userRepository,
      @Autowired RosterPlanRepository rosterPlanRepository,
      @Autowired AclRegisterService aclRegisterService) {
    List<User> users =
        userRepository.saveAll(
            () ->
                Stream.of("talk@wemeet.com", "cock@wemeet.com")
                    .map(User::ofUnregistered)
                    .iterator());
    talk = users.get(0);
    cock = users.get(1);
    rosterPlan =
        rosterPlanRepository
            .saveAll(
                Collections.singleton(RosterPlan.builder().title("Talk Cock").owner(talk).build()))
            .get(0);

    SecurityContextHolder.getContext()
        .setAuthentication(
            new UsernamePasswordAuthenticationToken(talk.getEmail(), null, Collections.emptySet()));
    aclRegisterService.register(rosterPlan, talk.getEmail(), READ, WRITE, DELETE);
  }

  @AfterEach
  public void tearDown(@Autowired H2Util h2Util, @Autowired CacheManager cacheManager) {
    h2Util.resetDatabase();
    Cache cache = cacheManager.getCache("aclCache");
    if (cache != null) {
      cache.clear();
    }
  }

  @Test
  public void contextLoads() {}

  private void addCock() throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("locked", false);
    map.put("email", "cock@wemeet.com");
    map.put("rosterPlan", "/api/rosterPlan/1");

    this.mockMvc
        .perform(
            post("/api/rosterPlanUserInfo")
                .with(user(talk.getEmail()))
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  private void addTimeSlot() throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("startDateTime", "2019-06-09T18:00:00.000Z");
    map.put("endDateTime", "2019-06-09T19:00:00.000Z");
    map.put("capacity", 2);
    map.put("rosterPlan", "/api/rosterPlan/1");

    this.mockMvc
        .perform(
            post("/api/timeSlot")
                .with(user(talk.getEmail()))
                .content(objectMapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(redirectedUrl("http://localhost/api/timeSlot/1"));
  }

  @Test
  public void givenNewUser_whenOwnerAddUserInfo_thenNewUserCanRead() throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("title", "Talk Cock");

    addCock();
    this.mockMvc
        .perform(get("/api/rosterPlan/1").with(user(cock.getEmail())).accept(MediaTypes.HAL_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(map), false));
  }

  @Test
  public void givenValidRequest_whenOwnerAddTimeSlot_thenNewUserCannotRead() throws Exception {
    addTimeSlot();

    this.mockMvc
        .perform(get("/api/rosterPlan/1").with(user(cock.getEmail())).accept(MediaTypes.HAL_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void givenValidRequestAndAssociatedUser_whenOwnerAddTimeSlot_thenAssociatedUserCanRead()
      throws Exception {
    addCock();
    addTimeSlot();

    this.mockMvc
        .perform(get("/api/rosterPlan/1").with(user(cock.getEmail())).accept(MediaTypes.HAL_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void givenValidRequestAndTimeSlot_whenAssociatedUserRankPreference_thenOk()
      throws Exception {
    addCock();
    addTimeSlot();

    Map<String, Object> map = new HashMap<>();
    map.put("rank", 1);
    map.put("timeSlot", "/api/timeSlot/1");

    // TODO ACL planning
    this.mockMvc
        .perform(
            post("/api/timeSlotUserInfo")
                .with(user(cock.getEmail()))
                .content(objectMapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(redirectedUrl("http://localhost/api/timeSlotUserInfo/1"));
  }

  @Test
  public void givenValidEmail_whenOwnerAddUserInfo_thenCreateNewUnregisteredUser()
      throws Exception {
    this.mockMvc
        .perform(get("/api/users/3").with(user("suck@wemeet.com")).accept(MediaTypes.HAL_JSON))
        .andExpect(status().isNotFound());

    Map<String, Object> map = new HashMap<>();
    map.put("locked", false);
    map.put("email", "suck@wemeet.com");

    Map<String, Object> expected = new HashMap<>(map);

    map.put("rosterPlan", "/api/rosterPlan/1");
    this.mockMvc
        .perform(
            post("/api/rosterPlanUserInfo")
                .with(user(talk.getEmail()))
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().json(objectMapper.writeValueAsString(expected), false));

    this.mockMvc
        .perform(get("/api/users/3").with(user("suck@wemeet.com")).accept(MediaTypes.HAL_JSON))
        .andExpect(status().isOk());
  }
}
