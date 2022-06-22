package com.orbital22.wemeet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Builder
@Getter
@Setter
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
  @NonNull // For deserialization
  private User user;

  @Column(name = "has_responded") // TODO
  private boolean locked;

  // For deserialization
  @Transient
  @Nullable
  @Email
  private String email;

  @JsonIgnore
  public String getTransientEmail() {
    return email;
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
