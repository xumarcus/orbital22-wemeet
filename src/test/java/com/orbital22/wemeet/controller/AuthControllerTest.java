package com.orbital22.wemeet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbital22.wemeet.dto.AuthRegisterRequest;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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

  // Remove this unused mock break tests
  @MockBean private AuthenticationManager authenticationManager;

  @Test
  public void givenEmptyRequest_whenRegister_thenBadRequest() throws Exception {
    when(userService.register(any())).thenReturn(Optional.empty());
    RequestBuilder request =
        post("/api/auth/register")
            .accept(MediaTypes.HAL_JSON_VALUE)
            .content(objectMapper.writeValueAsString(new AuthRegisterRequest()))
            .contentType(MediaType.APPLICATION_JSON);
    this.mockMvc.perform(request).andExpect(status().isBadRequest());
  }

  @Test
  public void givenValidRequest_whenRegister_thenOk() throws Exception {
    User user = User.ofRegistered("user@wemeet.com", "encodedPassword");
    user.setId(1);
    when(userService.register(any())).thenReturn(Optional.of(user));
    AuthRegisterRequest authRegisterRequest =
        AuthRegisterRequest.builder().email("user@wemeet.com").password("password").build();

    RequestBuilder request =
        post("/api/auth/register")
            .accept(MediaTypes.HAL_JSON_VALUE)
            .content(objectMapper.writeValueAsString(authRegisterRequest))
            .contentType(MediaType.APPLICATION_JSON);
    String expectedBody =
        "{ \"email\" : \"user@wemeet.com\", \"enabled\" : true, \"registered\" : true,"
            + "\"authorities\" : [ ], \"timeSlotUserInfos\" : [ ], \"_links\" : { \"self\" :"
            + "{ \"href\" : \"http://localhost/api/auth/register\" }, \"user\" : { \"href\" : "
            + "\"http://localhost/api/users/1\" } } }";
    this.mockMvc
        .perform(request)
        .andExpect(status().isOk())
        .andExpect(content().json(expectedBody));
  }
}
