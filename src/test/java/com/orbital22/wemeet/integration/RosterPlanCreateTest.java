package com.orbital22.wemeet.integration;

import com.orbital22.wemeet.dto.RosterPlanCreateRequest;
import com.orbital22.wemeet.dto.TimeSlotDto;
import com.orbital22.wemeet.dto.UsersRegisterRequest;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.service.RosterPlanService;
import com.orbital22.wemeet.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RosterPlanCreateTest {
  @Autowired private RosterPlanService rosterPlanService;

  @Autowired private UserService userService;

  @Test
  public void givenValidRequests_whenCreate_thenCascade() {
    UsersRegisterRequest usersRegisterRequest =
        UsersRegisterRequest.builder().email("user@wemeet.com").password("password").build();
    User owner = userService.register(usersRegisterRequest).orElseThrow();

    RosterPlanCreateRequest rosterPlanCreateRequest =
        RosterPlanCreateRequest.builder()
            .emails(Arrays.asList("1@1.com", "2@2.org", "user@wemeet.com"))
            .timeSlotDtos(
                Arrays.asList(
                    TimeSlotDto.builder()
                        .startDateTime(LocalDateTime.of(1989, 6, 4, 1, 0))
                        .endDateTime(LocalDateTime.of(1989, 6, 4, 2, 0))
                        .capacity(2)
                        .build(),
                    TimeSlotDto.builder()
                        .startDateTime(LocalDateTime.of(2022, 2, 24, 1, 0))
                        .endDateTime(LocalDateTime.of(2022, 2, 24, 2, 0))
                        .capacity(2)
                        .build()))
            .title("Demilitarization")
            .build();
    RosterPlan rosterPlan = rosterPlanService.create(rosterPlanCreateRequest, owner);

    assertAll(
        () -> assertEquals(rosterPlan.getOwner().getEmail(), "user@wemeet.com"),
            () -> assertEquals(rosterPlan.getTitle(), "Demilitarization"),
        () -> assertEquals(rosterPlan.getTimeSlots().size(), 2),
            () -> assertEquals(rosterPlan.getRosterPlanUserInfos().size(), 3));
  }
}