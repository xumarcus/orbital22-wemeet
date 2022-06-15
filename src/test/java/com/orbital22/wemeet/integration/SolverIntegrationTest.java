package com.orbital22.wemeet.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbital22.wemeet.model.*;
import com.orbital22.wemeet.repository.RosterPlanUserInfoRepository;
import com.orbital22.wemeet.repository.TimeSlotRepository;
import com.orbital22.wemeet.repository.TimeSlotUserInfoRepository;
import com.orbital22.wemeet.repository.UserRepository;
import com.orbital22.wemeet.security.AclRegisterService;
import com.orbital22.wemeet.service.RosterPlanService;
import com.orbital22.wemeet.service.UserService;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
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
public class SolverIntegrationTest {
  @Autowired ObjectMapper objectMapper;
  @Autowired MockMvc mockMvc;
  @Autowired CacheManager cacheManager;
  @Autowired AclRegisterService aclRegisterService;

  User talk;
  User cock;
  User suck;
  RosterPlan rosterPlan;

  @WithMockUser(username = "talk@wemeet.com")
  @BeforeEach
  public void setUp(
      @Autowired UserRepository userRepository,
      @Autowired RosterPlanUserInfoRepository rosterPlanUserInfoRepository,
      @Autowired UserService userService,
      @Autowired RosterPlanService rosterPlanService) {
    List<User> users =
        userRepository.saveAll(
            () ->
                Stream.of("talk@wemeet.com", "cock@wemeet.com", "suck@wemeet.com")
                    .map(User::ofUnregistered)
                    .iterator());
    talk = users.get(0);
    cock = users.get(1);
    suck = users.get(2);
    rosterPlan =
        rosterPlanService.justSave(RosterPlan.builder().title("Talk Cock").owner(talk).build());

    rosterPlanUserInfoRepository.saveAll(
        () ->
            users.stream()
                .map(user -> RosterPlanUserInfo.builder().user(user).rosterPlan(rosterPlan).build())
                .iterator());

    userService.setSessionUser(talk);
    aclRegisterService.register(rosterPlan, talk.getEmail(), READ, WRITE, DELETE);
    aclRegisterService.register(rosterPlan, cock.getEmail(), READ);
    aclRegisterService.register(rosterPlan, suck.getEmail(), READ);
  }

  @AfterEach
  public void tearDown(@Autowired H2Util h2Util) {
    h2Util.resetDatabase();
    Cache cache = cacheManager.getCache("aclCache");
    if (cache != null) {
      cache.clear();
    }
  }

  @Test
  public void contextLoads() {}

  private void addFirstTimeSlot() throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("startDateTime", "2019-06-09T18:00:00");
    map.put("endDateTime", "2019-06-09T19:00:00");
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

  private void addSecondTimeSlot() throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("startDateTime", "2019-06-12T18:00:00");
    map.put("endDateTime", "2019-06-12T19:00:00");
    map.put("capacity", 1);
    map.put("rosterPlan", "/api/rosterPlan/1");

    this.mockMvc
        .perform(
            post("/api/timeSlot")
                .with(user(talk.getEmail()))
                .content(objectMapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(redirectedUrl("http://localhost/api/timeSlot/2"));
  }

  private void addTimeSlotUserInfo(User user, int timeSlotId, int rank) throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("timeSlot", "/api/timeSlot/" + timeSlotId);
    map.put("rank", rank);

    this.mockMvc
        .perform(
            post("/api/timeSlotUserInfo")
                .with(user(user.getEmail()))
                .content(objectMapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  public void givenTimeSlotUserInfos_whenAddChild_thenSolverRuns(
      @Autowired TimeSlotRepository timeSlotRepository,
      @Autowired TimeSlotUserInfoRepository timeSlotUserInfoRepository)
      throws Exception {
    TimeSlot t1 =
        timeSlotRepository.save(
            TimeSlot.builder()
                .rosterPlan(rosterPlan)
                .startDateTime(LocalDateTime.of(2019, 6, 9, 18, 0, 0))
                .endDateTime(LocalDateTime.of(2019, 6, 9, 19, 0, 0))
                .capacity(1)
                .build());

    TimeSlot t2 =
        timeSlotRepository.save(
            TimeSlot.builder()
                .rosterPlan(rosterPlan)
                .startDateTime(LocalDateTime.of(2019, 6, 12, 18, 0, 0))
                .endDateTime(LocalDateTime.of(2019, 6, 12, 19, 0, 0))
                .capacity(2)
                .build());

    timeSlotUserInfoRepository.saveAll(
        List.of(
            TimeSlotUserInfo.builder().timeSlot(t1).user(talk).rank(1).build(),
            TimeSlotUserInfo.builder().timeSlot(t2).user(cock).rank(2).build(),
            TimeSlotUserInfo.builder().timeSlot(t2).user(suck).rank(3).build()));

    Map<String, Object> map = new HashMap<>();
    map.put("title", "Talk Cock Suck");
    map.put("parent", "/api/rosterPlan/1");

    this.mockMvc
        .perform(
            post("/api/rosterPlan")
                .with(user(talk.getEmail()))
                .content(objectMapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(redirectedUrl("http://localhost/api/rosterPlan/2"));

    Map<String, Object> resp = new HashMap<>();
    resp.put("title", "Talk Cock Suck");
    resp.put("solved", false);

    this.mockMvc
        .perform(get("/api/rosterPlan/2").with(user(talk.getEmail())))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(resp)));

    // Solver terminates in 1s
    Thread.sleep(2000);

    resp.replace("solved", true);
    this.mockMvc
        .perform(get("/api/rosterPlan/2").with(user(talk.getEmail())))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(resp)));
  }
}
