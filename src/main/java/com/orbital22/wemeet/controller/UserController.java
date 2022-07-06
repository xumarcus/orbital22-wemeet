package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.UserPostRequest;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.*;
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

  // Workaround
  // See SDR source for the dirty slash
  @PostMapping("/users")
  ResponseEntity<?> post(@RequestBody @Valid UserPostRequest request) {
    User entity = userService.post(request);

    EntityModel<User> resource = EntityModel.of(entity);

    Link self = entityLinks.linkToItemResource(entity, User::getId);
    resource.add(self.withSelfRel());
    resource.add(self.withRel("user"));
    resource.add(
        Link.of(
            UriTemplate.of(self.getHref() + "/" + User.Fields.ownedRosterPlans),
            LinkRelation.of(User.Fields.ownedRosterPlans)));
    resource.add(
        Link.of(
            UriTemplate.of(self.getHref() + "/" + User.Fields.rosterPlanUserInfos)
                .with("projection", TemplateVariable.VariableType.REQUEST_PARAM),
            LinkRelation.of(User.Fields.rosterPlanUserInfos)));
    resource.add(
        Link.of(
            UriTemplate.of(self.getHref() + "/" + User.Fields.timeSlotUserInfos)
                .with("projection", TemplateVariable.VariableType.REQUEST_PARAM),
            LinkRelation.of(User.Fields.timeSlotUserInfos)));

    return ResponseEntity.created(self.toUri()).body(resource);
  }
}
