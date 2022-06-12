package com.orbital22.wemeet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orbital22.wemeet.annotation.RosterPlanStatusConstraint;
import com.orbital22.wemeet.enums.RosterPlanStatus;
import lombok.*;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.orbital22.wemeet.enums.RosterPlanStatus.MODIFIED;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roster_plan")
@RosterPlanStatusConstraint
public class RosterPlan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private int id;

  // needed?
  @JsonProperty(access = READ_ONLY)
  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;

  @NotBlank @Column private String title;

  @OneToMany(mappedBy = "rosterPlan")
  @Builder.Default
  @NotNull
  private Set<TimeSlot> timeSlots = Collections.emptySet();

  @OneToMany(mappedBy = "rosterPlan")
  @Builder.Default
  @NotNull
  private Set<RosterPlanUserInfo> rosterPlanUserInfos = Collections.emptySet();

  @Enumerated(EnumType.ORDINAL)
  @Builder.Default
  @NotNull
  @Column // TODO
  private RosterPlanStatus rosterPlanStatus = MODIFIED;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  @Nullable
  private RosterPlan parent;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RosterPlan that = (RosterPlan) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public boolean isSnapshot() {
    return parent != null;
  }
}
