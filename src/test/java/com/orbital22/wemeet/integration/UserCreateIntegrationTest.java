package com.orbital22.wemeet.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserCreateIntegrationTest {
  @Autowired private ObjectMapper objectMapper;
  @Autowired private MockMvc mockMvc;

  @Test
  public void givenInvalidRequest_whenRegister_thenBadRequest() throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("email", "not-email");
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
  public void givenValidRequest_whenRegister_thenOk() throws Exception {
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
