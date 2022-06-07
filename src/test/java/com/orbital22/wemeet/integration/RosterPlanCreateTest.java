package com.orbital22.wemeet.integration;

import com.orbital22.wemeet.dto.AuthRegisterRequest;
import com.orbital22.wemeet.dto.RosterPlanCreateRequest;
import com.orbital22.wemeet.dto.TimeSlotDto;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.RosterPlanRepository;
import com.orbital22.wemeet.service.RosterPlanService;
import com.orbital22.wemeet.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RosterPlanCreateTest {
  @Autowired private RosterPlanService rosterPlanService;

  @Autowired private UserService userService;
  @Autowired private RosterPlanRepository rosterPlanRepository;

  @BeforeEach
  public void init() {
    AuthRegisterRequest authRegisterRequest =
        AuthRegisterRequest.builder().email("user@wemeet.com").password("password").build();
    User owner = userService.register(authRegisterRequest).orElseThrow();

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

    rosterPlanService.create(rosterPlanCreateRequest, owner);
  }

  @Transactional
  @Test
  public void givenValidRequests_whenCreate_thenCascade() {
    RosterPlan rosterPlan = rosterPlanRepository.findById(1).orElseThrow();
    assertAll(
        () -> assertEquals("user@wemeet.com", rosterPlan.getOwner().getEmail()),
        () -> assertEquals("Demilitarization", rosterPlan.getTitle()),
        () -> assertEquals(2, rosterPlan.getTimeSlots().size()),
        () -> assertEquals(3, rosterPlan.getRosterPlanUserInfos().size()));
  }
}
