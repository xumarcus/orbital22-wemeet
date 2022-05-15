package com.orbital22.wemeet.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@Data
@Entity
@Table(name="authorities")
public class Authority implements GrantedAuthority {
    @Id
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private Collection<User> users;
}
