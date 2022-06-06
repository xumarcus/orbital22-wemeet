package com.orbital22.wemeet.model;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authority")
public class Authority implements GrantedAuthority {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private int id;

  @NaturalId
  @NonNull
  @Column(unique = true)
  private String authority;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities")
  @ToString.Exclude
  @Builder.Default
  @NonNull
  private Set<User> users = Collections.emptySet();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Authority authority = (Authority) o;
    return id == authority.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
