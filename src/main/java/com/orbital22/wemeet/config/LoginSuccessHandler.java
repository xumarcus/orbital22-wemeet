package com.orbital22.wemeet.config;

import com.orbital22.wemeet.repository.UserRepository;
import com.orbital22.wemeet.security.CustomUser;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  @Override
  protected String determineTargetUrl(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    UriTemplate uriTemplate = new UriTemplate("/api/{rel}/{id}");
    Map<String, Object> vars = new HashMap<>();
    vars.put(
        "rel",
        UserRepository.class.getAnnotation(RepositoryRestResource.class).collectionResourceRel());
    vars.put("id", ((CustomUser) authentication.getPrincipal()).getId());
    return uriTemplate.expand(vars).getPath();
  }
}
