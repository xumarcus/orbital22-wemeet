package com.orbital22.wemeet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roster_plan")
public class RosterPlan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private int id;

  @Column private String title;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "parent_id")
  @Nullable
  private RosterPlan parent;

  @JsonProperty(access = READ_ONLY)
  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;

  @JsonProperty(access = READ_ONLY)
  @OneToMany(mappedBy = "rosterPlan")
  @Builder.Default
  @NotNull
  private Set<TimeSlot> timeSlots = Collections.emptySet();

  @JsonProperty(access = READ_ONLY)
  @OneToMany(mappedBy = "rosterPlan")
  @Builder.Default
  @NotNull
  private Set<RosterPlanUserInfo> rosterPlanUserInfos = Collections.emptySet();

  @JsonProperty(access = READ_ONLY)
  @Nullable // not applicable to parent
  @Column
  private Boolean solved;

  @Column
  private int minAllocationCount;

  @Column
  private int maxAllocationCount;

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
}
