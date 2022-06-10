package com.orbital22.wemeet.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.cronn.testutils.h2.H2Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Import(H2Util.class)
class UserCreateIntegrationTest {
  @Autowired ObjectMapper objectMapper;
  @Autowired MockMvc mockMvc;
  @Autowired CacheManager cacheManager;

  @AfterEach
  public void tearDown(@Autowired H2Util h2Util) {
    h2Util.resetDatabase();
    Cache cache = cacheManager.getCache("aclCache");
    if (cache != null) {
      cache.clear();
    }
  }

  @Test
  public void givenRequestWithInvalidEmail_whenRegister_thenBadRequest() throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("email", "not-email");
    map.put("password", "password");
    map.put("registered", true);

    this.mockMvc
        .perform(
            post("/api/users")
                .content(objectMapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void givenRequestWithInvalidPassword_whenRegister_thenBadRequest() throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("email", "user@wemeet.com");
    map.put("password", "invalid");
    map.put("registered", true);

    this.mockMvc
        .perform(
            post("/api/users")
                .content(objectMapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void givenValidRequest_whenCreateUnregistered_thenCreated() throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("email", "user@wemeet.com");
    map.put("password", null);
    map.put("registered", false);

    this.mockMvc
        .perform(
            post("/api/users")
                .content(objectMapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(redirectedUrl("http://localhost/api/users/1"));
  }

  @Test
  public void givenValidRequest_whenRegister_thenCreated() throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("email", "user@wemeet.com");
    map.put("password", "password");
    map.put("registered", true);

    this.mockMvc
        .perform(
            post("/api/users")
                .content(objectMapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(redirectedUrl("http://localhost/api/users/1"));
  }
}
