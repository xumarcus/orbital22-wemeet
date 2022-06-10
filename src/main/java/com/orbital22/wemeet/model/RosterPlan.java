package com.orbital22.wemeet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

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
