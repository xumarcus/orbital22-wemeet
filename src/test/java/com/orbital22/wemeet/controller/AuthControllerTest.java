package com.orbital22.wemeet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbital22.wemeet.dto.AuthRegisterRequest;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
  private final ObjectMapper objectMapper = new ObjectMapper();
  @Autowired private MockMvc mockMvc;
  @Autowired private AuthController authController;
  @MockBean private UserService userService;
  @MockBean private AuthenticationManager authenticationManager;

  @Test
  void contextLoads() {
    assertThat(authController).isNotNull();
  }

  @Test
  public void givenEmptyRequest_whenRegister_thenBadRequest() throws Exception {
    when(userService.register(anyString(), anyString())).thenReturn(null);
    RequestBuilder request =
        post("/api/auth/register")
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new AuthRegisterRequest()))
            .contentType(MediaType.APPLICATION_JSON);
    this.mockMvc.perform(request).andExpect(status().isBadRequest());
  }

  @Test
  public void givenValidRequest_whenRegister_thenOk() throws Exception {
    User user = User.ofRegistered("user@wemeet.com", "encodedPassword");
    user.setId(1);
    when(userService.register(anyString(), anyString())).thenReturn(user);
    when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(null);
    RequestBuilder request =
        post("/api/auth/register")
            .accept(MediaType.APPLICATION_JSON)
            .content(
                objectMapper.writeValueAsString(
                    new AuthRegisterRequest("user@wemeet.com", "password")))
            .contentType(MediaType.APPLICATION_JSON);
    this.mockMvc
        .perform(request)
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(user)));
  }
}
