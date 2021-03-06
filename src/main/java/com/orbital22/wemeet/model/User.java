package com.orbital22.wemeet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Builder
@Getter
@Setter
@FieldNameConstants
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users") // user is reserved keyword in Postgres
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private int id;

  @NaturalId
  @NotNull
  @Column(unique = true)
  private String email;

  @JsonIgnore
  @RestResource(exported = false) // internal
  @Builder.Default
  @NotNull
  @Column
  private String password = StringUtils.EMPTY;

  @JsonIgnore
  @RestResource(exported = false) // internal
  @Builder.Default
  @Column
  private boolean enabled = true;

  @Builder.Default @Column private boolean registered = true;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_authority",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "authority_id"))
  @Builder.Default
  @NotNull
  private Set<Authority> authorities = Collections.emptySet();

  @ToString.Exclude
  @OneToMany(mappedBy = "owner")
  @Builder.Default
  @NotNull
  private Set<RosterPlan> ownedRosterPlans = Collections.emptySet();

  @ToString.Exclude
  @OneToMany(mappedBy = "user")
  @Builder.Default
  @NotNull
  private Set<RosterPlanUserInfo> rosterPlanUserInfos = Collections.emptySet();

  @ToString.Exclude
  @OneToMany(mappedBy = "user")
  @Builder.Default
  @NotNull
  private Set<TimeSlotUserInfo> timeSlotUserInfos = Collections.emptySet();

  public static User ofUnregistered(String email) {
    return User.builder()
        .email(email)
        .password(StringUtils.EMPTY) // Fails authentication
        .enabled(true)
        .registered(false)
        .build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return id == user.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
