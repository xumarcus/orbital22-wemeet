package com.orbital22.wemeet.service;

import com.orbital22.wemeet.dto.RosterPlanCreateRequest;
import com.orbital22.wemeet.mapper.TimeSlotMapper;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.RosterPlanUserInfo;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.RosterPlanRepository;
import com.orbital22.wemeet.repository.RosterPlanUserInfoRepository;
import com.orbital22.wemeet.repository.TimeSlotRepository;
import com.orbital22.wemeet.security.AclRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.security.acls.domain.BasePermission.*;

@Service
@Transactional
@AllArgsConstructor
public class RosterPlanService {
  private RosterPlanRepository rosterPlanRepository;
  private TimeSlotRepository timeSlotRepository;
  private RosterPlanUserInfoRepository rosterPlanUserInfoRepository;
  private UserService userService;
  private AclRegisterService aclRegisterService;

  public RosterPlan create(RosterPlanCreateRequest request, User owner) {
    RosterPlan rosterPlan =
        rosterPlanRepository.save(
            RosterPlan.builder().owner(owner).title(request.getTitle()).build());
    aclRegisterService.register(rosterPlan, owner.getEmail(), READ, WRITE, DELETE);

    rosterPlan.setRosterPlanUserInfos(
        rosterPlanUserInfoRepository
            .saveAll(
                () ->
                    userService.fromEmails(request.getEmails()).stream()
                        .peek(
                            user -> aclRegisterService.register(rosterPlan, user.getEmail(), READ))
                        .map(
                            user ->
                                RosterPlanUserInfo.builder()
                                    .rosterPlan(rosterPlan)
                                    .user(user)
                                    .hasResponded(false)
                                    .build())
                        .peek(
                            info ->
                                aclRegisterService.register(
                                    info, info.getUser().getEmail(), READ, WRITE))
                        .iterator())
            .stream()
            .collect(Collectors.toMap(RosterPlanUserInfo::getUser, Function.identity())));

    // No ACL
    rosterPlan.setTimeSlots(
        new HashSet<>(
            timeSlotRepository.saveAll(
                () ->
                    request.getTimeSlotDtos().stream()
                        .map(TimeSlotMapper.INSTANCE::timeSlotDtoToTimeSlot)
                        .peek(timeSlot -> timeSlot.setRosterPlan(rosterPlan))
                        .iterator())));

    return rosterPlanRepository.saveAndFlush(rosterPlan);
  }
}
