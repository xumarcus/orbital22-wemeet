package com.orbital22.wemeet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbital22.wemeet.dto.AuthRegisterRequest;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@OverrideAutoConfiguration(enabled = true)
class AuthControllerTest {
  @Autowired private ObjectMapper objectMapper;
  @Autowired private MockMvc mockMvc;
  @MockBean private UserService userService;
  @MockBean private AuthenticationManager authenticationManager;

  @Test
  public void givenEmptyRequest_whenRegister_thenBadRequest() throws Exception {
    when(userService.register(anyString(), anyString())).thenReturn(null);
    RequestBuilder request =
        post("/api/auth/register")
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new AuthRegisterRequest()))
            .contentType(MediaType.APPLICATION_JSON);
    this.mockMvc.perform(request).andExpect(status().isBadRequest());
    Mockito.verifyNoInteractions(authenticationManager);
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
    Mockito.verify(authenticationManager).authenticate(any(Authentication.class));
  }
}
