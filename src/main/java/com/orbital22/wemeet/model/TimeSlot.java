package com.orbital22.wemeet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orbital22.wemeet.annotation.DateTimeRangeConstraint;
import com.orbital22.wemeet.util.HasDateTimeRange;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "time_slot")
@DateTimeRangeConstraint
public class TimeSlot implements HasDateTimeRange {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private int id;

  @ToString.Exclude
  @NotNull
  @ManyToOne
  @JoinColumn(name = "roster_plan_id")
  private RosterPlan rosterPlan;

  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
      timezone = "UTC")
  @NotNull
  @Column
  private LocalDateTime startDateTime;

  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
      timezone = "UTC")
  @NotNull
  @Column
  private LocalDateTime endDateTime;

  @Positive @Column private int capacity;

  @NotNull
  @OneToMany(mappedBy = "timeSlot")
  @Builder.Default
  private Set<TimeSlotUserInfo> timeSlotUserInfos = Collections.emptySet();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TimeSlot timeSlot = (TimeSlot) o;
    return id == timeSlot.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
