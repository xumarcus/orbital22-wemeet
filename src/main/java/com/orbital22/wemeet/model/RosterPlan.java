package com.orbital22.wemeet.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;

  @Column @NonNull private String title;

  @OneToMany(mappedBy = "rosterPlan", fetch = FetchType.EAGER)
  @Builder.Default
  @NonNull
  private Set<TimeSlot> timeSlots = Collections.emptySet();

  @OneToMany(mappedBy = "rosterPlan", fetch = FetchType.EAGER)
  @MapKeyJoinColumn(name = "user_id")
  @Builder.Default
  @NonNull
  private Map<User, RosterPlanUserInfo> rosterPlanUserInfos = Collections.emptyMap();

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
