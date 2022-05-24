package com.orbital22.wemeet.model;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @NaturalId
    @NonNull
    @Column(unique=true)
    private String authority;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities")   // See User.java
    private Collection<User> users = Collections.emptyList();
}
