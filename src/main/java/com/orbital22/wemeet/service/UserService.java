package com.orbital22.wemeet.service;

import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
  private PasswordEncoder passwordEncoder;
  private UserRepository userRepository;
  private JdbcMutableAclService aclService;

  public void register(String email, String password) {
    Assert.isTrue(!userRepository.existsByEmail(email), "Email exists");
    User user = User.ofRegistered(email, passwordEncoder.encode(password));
    userRepository.save(user);

    // Allow user to access oneself
    ObjectIdentity oi = new ObjectIdentityImpl(user);
    MutableAcl acl =
        Try.of(() -> (MutableAcl) aclService.readAclById(oi))
            .getOrElse(() -> aclService.createAcl(oi));
    Sid sid = new PrincipalSid(email);

    // Do not use CumulativePermission!
    Permission[] permissions =
        new Permission[] {BasePermission.READ, BasePermission.WRITE, BasePermission.DELETE};
    for (Permission permission : permissions) {
      acl.insertAce(acl.getEntries().size(), permission, sid, true);
    }
    aclService.updateAcl(acl);
  }
}
