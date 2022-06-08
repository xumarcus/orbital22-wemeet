package com.orbital22.wemeet.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "time_slot")
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @ManyToOne
    @JoinColumn(name = "roster_plan_id")
    private RosterPlan rosterPlan;

    @NonNull
    @Column
    private LocalDateTime startDateTime;

    @NonNull
    @Column
    private LocalDateTime endDateTime;

    @Column
    private int capacity;

    @OneToMany(mappedBy = "timeSlot")
    @MapKeyJoinColumn(name = "user_id")
    @Builder.Default
    @NonNull
    private Map<User, TimeSlotUserInfo> timeSlotUserInfos = Collections.emptyMap();

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
