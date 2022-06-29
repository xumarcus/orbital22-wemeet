package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.RosterPlanUserInfoPostRequest;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.RosterPlanUserInfo;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.service.RosterPlanUserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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
  private final RepositoryEntityLinks entityLinks;
  private final RosterPlanUserInfoService rosterPlanUserInfoService;

  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.addValidators(validator);
  }

  @PostMapping("/rosterPlanUserInfo") // PUT?
  ResponseEntity<?> post(@RequestBody @Valid RosterPlanUserInfoPostRequest request) {
    RosterPlanUserInfo entity = rosterPlanUserInfoService.post(request);

    EntityModel<RosterPlanUserInfo> resource = EntityModel.of(entity);

    Link self = entityLinks.linkToItemResource(entity, RosterPlanUserInfo::getId);
    resource.add(self.withSelfRel());
    resource.add(
        entityLinks
            .linkToItemResource(entity.getRosterPlan(), RosterPlan::getId)
            .withRel(RosterPlanUserInfo.Fields.rosterPlan));
    resource.add(
        entityLinks
            .linkToItemResource(entity.getUser(), User::getId)
            .withRel(RosterPlanUserInfo.Fields.user));

    return ResponseEntity.created(self.toUri()).body(resource);
  }
}
