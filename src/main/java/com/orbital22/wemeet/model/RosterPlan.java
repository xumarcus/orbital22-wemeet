package com.orbital22.wemeet.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
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
public class RosterPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @NonNull
    private User owner;

    private Map<User, RosterPlanUserInfo> userMap;

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
