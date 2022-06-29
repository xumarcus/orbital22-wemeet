package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.UserPostRequest;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.RosterPlanUserInfo;
import com.orbital22.wemeet.model.TimeSlotUserInfo;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.service.UserService;
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
public class UserController {
  private final LocalValidatorFactoryBean validator;
  private final RepositoryEntityLinks entityLinks;
  private final UserService userService;

  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.addValidators(validator);
  }

  @PostMapping("/users") // PUT?
  ResponseEntity<?> post(@RequestBody @Valid UserPostRequest request) {
    User entity = userService.post(request);

    EntityModel<User> resource = EntityModel.of(entity);

    Link self = entityLinks.linkToItemResource(entity, User::getId);
    resource.add(self.withSelfRel());
    resource.add(
        entityLinks
            .linkToCollectionResource(RosterPlan.class)
            .withRel(User.Fields.ownedRosterPlans));
    resource.add(
        entityLinks
            .linkToCollectionResource(RosterPlanUserInfo.class)
            .withRel(User.Fields.rosterPlanUserInfos));
    resource.add(
        entityLinks
            .linkToCollectionResource(TimeSlotUserInfo.class)
            .withRel(User.Fields.timeSlotUserInfos));

    return ResponseEntity.created(self.toUri()).body(resource);
  }
}
