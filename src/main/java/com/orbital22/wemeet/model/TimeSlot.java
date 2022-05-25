package com.orbital22.wemeet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @NonNull
    @Column
    private LocalDateTime startDateTime;

    @NonNull
    @Column
    private LocalDateTime endDateTime;

    @Column
    private int capacity;

    private Map<User, TimeSlotUserInfo> userMap;

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
