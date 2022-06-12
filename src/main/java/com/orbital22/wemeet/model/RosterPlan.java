package com.orbital22.wemeet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orbital22.wemeet.enums.RosterPlanStatus;
import lombok.*;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
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
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roster_plan")
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

  @NonNull @Column private String title;

  @OneToMany(mappedBy = "rosterPlan")
  @Builder.Default
  @NonNull
  private Set<TimeSlot> timeSlots = Collections.emptySet();

  @OneToMany(mappedBy = "rosterPlan")
  @Builder.Default
  @NonNull
  private Set<RosterPlanUserInfo> rosterPlanUserInfos = Collections.emptySet();

  @Enumerated(EnumType.ORDINAL)
  @Builder.Default
  @NotNull
  @Column // TODO
  private RosterPlanStatus rosterPlanStatus = MODIFIED;

  @ManyToOne
  @JoinColumn(name = "snapshot_of_id")
  @Nullable
  @Column
  private RosterPlan snapshotOf;

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
    return snapshotOf != null;
  }
}
