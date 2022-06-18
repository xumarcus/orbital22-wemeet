package com.orbital22.wemeet.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "time_slot_user_info")
public class TimeSlotUserInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private int id;

  @Builder.Default @Column private int rank = 0;

  @Builder.Default @Column private boolean picked = false;

  @ManyToOne
  @JoinColumn(name = "user_id")
  // Nullable before aspect injects
  private User user;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "time_slot_id")
  @NotNull
  private TimeSlot timeSlot;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TimeSlotUserInfo that = (TimeSlotUserInfo) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
