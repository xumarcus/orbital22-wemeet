package com.orbital22.wemeet.service;

import com.orbital22.wemeet.dto.RosterPlanUserInfoPostRequest;
import com.orbital22.wemeet.model.RosterPlanUserInfo;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.RosterPlanUserInfoRepository;
import com.orbital22.wemeet.repository.UserRepository;
import com.orbital22.wemeet.security.AclRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import static org.springframework.security.acls.domain.BasePermission.*;

@Service
@Transactional
@AllArgsConstructor
public class RosterPlanUserInfoService {
  private final UserRepository userRepository;
  private final RosterPlanUserInfoRepository rosterPlanUserInfoRepository;
  private final AclRegisterService aclRegisterService;

  @NotNull
  public RosterPlanUserInfo post(RosterPlanUserInfoPostRequest request) {
    RosterPlanUserInfo rosterPlanUserInfo =
        rosterPlanUserInfoRepository.save(
            RosterPlanUserInfo.builder()
                .rosterPlan(request.getRosterPlan())
                .user(
                    userRepository
                        .findByEmail(request.getEmail())
                        .orElseGet(
                            () -> userRepository.save(User.ofUnregistered(request.getEmail()))))
                .locked(request.isLocked())
                .build());

    aclRegisterService.register(rosterPlanUserInfo.getRosterPlan(), request.getEmail(), READ);

    // TODO
    aclRegisterService.register(rosterPlanUserInfo, request.getEmail(), READ, WRITE, DELETE);

    return rosterPlanUserInfo;
  }
}
