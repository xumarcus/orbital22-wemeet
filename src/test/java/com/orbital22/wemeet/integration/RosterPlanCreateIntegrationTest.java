package com.orbital22.wemeet.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import de.cronn.testutils.h2.H2Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@AutoConfigureRestDocs
@Import(H2Util.class)
public class RosterPlanCreateIntegrationTest {
  @Autowired ObjectMapper objectMapper;
  @Autowired MockMvc mockMvc;

  @BeforeEach
  public void setUp(@Autowired UserRepository userRepository) {
    userRepository.saveAll(
        () -> Stream.of("talk@wemeet.com", "cock@wemeet.com").map(User::ofUnregistered).iterator());
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

  @Test
  public void givenValidRequest_whenCreate_thenCreateEmptyRosterPlan() throws Exception {
    Map<String, Object> req = new HashMap<>();
    req.put("title", "Talk Cock");

    this.mockMvc
        .perform(
            post("/api/rosterPlan")
                .with(user("talk@wemeet.com"))
                .content(objectMapper.writeValueAsString(req))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(redirectedUrl("http://localhost:8080/api/rosterPlan/1"))
        .andDo(document("givenValidRequest_whenCreate_thenCreateEmptyRosterPlan"));

    Map<String, Object> resp = new HashMap<>(req);
    resp.put("id", 1);
    resp.put("solved", null);
    resp.put("minAllocationCount", 1);
    resp.put("maxAllocationCount", 1);

    this.mockMvc
        .perform(get("/api/rosterPlan/1").with(user("talk@wemeet.com")).accept(MediaTypes.HAL_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(resp), false))
        .andDo(document("get-rosterPlan-success"));
  }

  @Test
  public void givenInvalidRequest_whenCreate_thenBadRequest() throws Exception {
    Map<String, Object> req0 = new HashMap<>();
    req0.put("title", "\t \n");

    this.mockMvc
        .perform(
            post("/api/rosterPlan")
                .with(user("talk@wemeet.com"))
                .content(objectMapper.writeValueAsString(req0))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andDo(document("givenInvalidRequest_whenCreate_thenBadRequest"));
  }

  @Test
  public void givenAnonymousUser_whenCreate_thenRedirectToLogin() throws Exception {
    Map<String, Object> req0 = new HashMap<>();
    req0.put("title", "Talk Cock");

    this.mockMvc
        .perform(
            post("/api/rosterPlan")
                .with(anonymous())
                .content(objectMapper.writeValueAsString(req0))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isFound())
        .andDo(document("givenAnonymousUser_whenCreate_thenRedirectToLogin"));
  }
}
