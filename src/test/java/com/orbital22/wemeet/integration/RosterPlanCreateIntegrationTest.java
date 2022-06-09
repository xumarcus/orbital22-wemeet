package com.orbital22.wemeet.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RosterPlanCreateIntegrationTest {
  @Autowired private ObjectMapper objectMapper;
  @Autowired private MockMvc mockMvc;
  @Autowired private UserRepository userRepository;

  @Test
  public void givenValidRequest_whenCreate_thenCreateEmptyRosterPlan() throws Exception {
    userRepository.saveAll(
        () -> Stream.of("mary@wemeet.com", "sue@wemeet.com").map(User::ofUnregistered).iterator());

    Map<String, Object> req0 = new HashMap<>();
    req0.put("title", "Mary Sue");

    this.mockMvc
        .perform(
            post("/api/rosterPlan")
                .with(user("mary@wemeet.com"))
                .content(objectMapper.writeValueAsString(req0))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(redirectedUrl("http://localhost/api/rosterPlan/1"));

    this.mockMvc
        .perform(
            get("/api/rosterPlan/1")
                .with(user("mary@wemeet.com"))
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(req0), false));

    this.mockMvc
        .perform(
            get("/api/rosterPlan/1")
                .with(user("sue@wemeet.com"))
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void givenNewUser_whenOwnerAddUserInfo_thenNewUserCanRead() throws Exception {
    userRepository.saveAll(
        () -> Stream.of("mary@wemeet.com", "sue@wemeet.com").map(User::ofUnregistered).iterator());

    Map<String, Object> req0 = new HashMap<>();
    req0.put("title", "Mary Sue");

    this.mockMvc.perform(
        post("/api/rosterPlan")
            .with(user("mary@wemeet.com"))
            .content(objectMapper.writeValueAsString(req0))
            .contentType(MediaType.APPLICATION_JSON));

    Map<String, Object> req1 = new HashMap<>();
    req1.put("hasResponded", false);
    req1.put("user", "/api/users/2");
    req1.put("rosterPlan", "/api/rosterPlan/1");

    this.mockMvc
        .perform(
            post("/api/rosterPlanUserInfo")
                .with(user("mary@wemeet.com"))
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(req1))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    this.mockMvc
        .perform(
            get("/api/rosterPlan/1")
                .with(user("sue@wemeet.com"))
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(req0), false));
  }
}
