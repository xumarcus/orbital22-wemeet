package com.orbital22.wemeet.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import de.cronn.testutils.h2.H2Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@AutoConfigureRestDocs
@Import(H2Util.class)
class UserCreateIntegrationTest {
  @Autowired ObjectMapper objectMapper;
  @Autowired MockMvc mockMvc;

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
  public void givenRequestWithInvalidEmail_whenRegister_thenBadRequest() throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("email", "not-email");
    map.put("rawPassword", "password");

    this.mockMvc
        .perform(
            post("/api/users")
                .content(objectMapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andDo(document("post-users-registered-email-error"));
  }

  @Test
  public void givenRequestWithInvalidPassword_whenRegister_thenBadRequest() throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("email", "user@wemeet.com");
    map.put("rawPassword", "invalid");

    this.mockMvc
        .perform(
            post("/api/users")
                .content(objectMapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andDo(document("post-users-registered-password-error"));
  }

  @Test
  public void givenValidRequest_whenRegister_thenCreated() throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("email", "user@wemeet.com");
    map.put("rawPassword", "password");

    this.mockMvc
        .perform(
            post("/api/users")
                .content(objectMapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(redirectedUrl("http://localhost:8080/api/users/1"))
        .andDo(document("post-users-registered-success"));
  }

  @Test
  public void givenUnregisteredUser_whenRegister_thenCreated(
      @Autowired UserRepository userRepository) throws Exception {
    userRepository.save(User.ofUnregistered("user@wemeet.com"));

    Map<String, Object> map = new HashMap<>();
    map.put("email", "user@wemeet.com");
    map.put("rawPassword", "password");

    this.mockMvc
        .perform(
            post("/api/users")
                .content(objectMapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(redirectedUrl("http://localhost:8080/api/users/1"))
        .andDo(document("post-users-registered-success"));
  }
}
