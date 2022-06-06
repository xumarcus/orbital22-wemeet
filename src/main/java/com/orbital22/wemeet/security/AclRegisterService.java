package com.orbital22.wemeet.security;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AclRegisterService {
  private JdbcMutableAclService aclService;

  public void register(Object model, String email, Permission... permissions) {
    ObjectIdentity oi = new ObjectIdentityImpl(model);
    MutableAcl acl =
        Try.of(() -> (MutableAcl) aclService.readAclById(oi))
            .getOrElse(() -> aclService.createAcl(oi));
    Sid sid = new PrincipalSid(email);

    // Do not use CumulativePermission!
    for (Permission permission : permissions)
      acl.insertAce(acl.getEntries().size(), permission, sid, true);
  }
}
