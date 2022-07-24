package com.orbital22.wemeet.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import com.orbital22.wemeet.security.CustomUser;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@AutoConfigureRestDocs
@Import(H2Util.class)
class UserReadIntegrationTest {
  @Autowired ObjectMapper objectMapper;
  @Autowired MockMvc mockMvc;

  User talk;
  User cock;

  @BeforeEach
  public void setUp(@Autowired UserRepository userRepository) {
    List<User> users =
        userRepository.saveAll(
            () ->
                Stream.of("talk@wemeet.com", "cock@wemeet.com")
                    .map(User::ofUnregistered)
                    .iterator());
    talk = users.get(0);
    cock = users.get(1);
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
  public void givenUnauthenticatedUser_whenGetMe_thenRedirectToLogin() throws Exception {
    this.mockMvc
        .perform(get("/api/users/me"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("http://localhost:8080/login"));
  }

  @Test
  public void givenAuthenticatedUser_whenGetMe_thenOk() throws Exception {
    this.mockMvc
        .perform(get("/api/users/me").with(user(new CustomUser(talk))))
        .andExpect(status().isOk())
        .andExpect(forwardedUrl("/api/users/1"));
  }
}
