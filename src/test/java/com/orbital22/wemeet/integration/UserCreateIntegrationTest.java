package com.orbital22.wemeet.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import com.orbital22.wemeet.security.CustomUser;
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
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
  public void givenValidRequest_whenRegister_thenCreatedAndReturned() throws Exception {
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

    this.mockMvc
        .perform(
            get("/api/users/1")
                .with(user("authenticated-user@wemeet.com"))
                .accept(MediaTypes.HAL_JSON))
        .andExpect(status().isOk())
        .andDo(document("get-users-success"));

    this.mockMvc
        .perform(
            get("/api/users/me")
                .with(user(new CustomUser(User.builder().email("user@wemeet.com").id(1).build())))
                .accept(MediaTypes.HAL_JSON))
        .andExpect(status().isOk())
        .andExpect(forwardedUrl("/api/users/1"))
        .andDo(document("get-users-me-success"));
  }

  @Test
  public void givenUnregisteredUser_whenRegister_thenCreatedAndReturned(
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
        .andDo(document("post-users-from-unregistered-registered-success"));
  }

  @Test
  public void givenRegisteredUser_whenRegister_thenBadRequest(
      @Autowired UserRepository userRepository) throws Exception {
    userRepository.save(
        User.builder()
            .id(1)
            .email("user@wemeet.com")
            .password("nil")
            .enabled(true)
            .registered(true)
            .build());

    Map<String, Object> map = new HashMap<>();
    map.put("email", "user@wemeet.com");
    map.put("rawPassword", "password");

    this.mockMvc
        .perform(
            post("/api/users")
                .content(objectMapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andDo(document("post-users-registered-already-registered-error"));
  }
}
