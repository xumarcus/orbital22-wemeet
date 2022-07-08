package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.RosterPlanUserInfoPostRequest;
import com.orbital22.wemeet.model.RosterPlanUserInfo;
import com.orbital22.wemeet.service.RosterPlanUserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RepositoryRestController
@AllArgsConstructor
public class RosterPlanUserInfoController {
  private final LocalValidatorFactoryBean validator;
  private final RepositoryEntityLinks repositoryEntityLinks;
  private final RosterPlanUserInfoService rosterPlanUserInfoService;

  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.addValidators(validator);
  }

  @PostMapping("/rosterPlanUserInfo") // PUT?
  ResponseEntity<?> post(@RequestBody @Valid RosterPlanUserInfoPostRequest request) {
    RosterPlanUserInfo rosterPlanUserInfo = rosterPlanUserInfoService.post(request);
    return ResponseEntity.created(
            repositoryEntityLinks
                .linkToItemResource(rosterPlanUserInfo, RosterPlanUserInfo::getId)
                .toUri())
        .build();
  }
}
