package com.orbital22.wemeet.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Table(name="authorities")
@NoArgsConstructor
@RequiredArgsConstructor
public class Authority implements GrantedAuthority {
    @Id
    @NonNull
    private String authority;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities")   // See User.java
    private Collection<User> users = Collections.emptyList();
}
