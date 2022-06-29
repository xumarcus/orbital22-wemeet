package com.orbital22.wemeet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Builder
@Getter
@Setter
@FieldNameConstants
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roster_plan_user_info")
public class RosterPlanUserInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private int id;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "roster_plan_id")
  @NotNull
  private RosterPlan rosterPlan;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @NotNull
  private User user;

  @Column(name = "has_responded") // TODO
  private boolean locked;

  @JsonProperty
  public String getEmail() {
    return user.getEmail();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RosterPlanUserInfo that = (RosterPlanUserInfo) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
