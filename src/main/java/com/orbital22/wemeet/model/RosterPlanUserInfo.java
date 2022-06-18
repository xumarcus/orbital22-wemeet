package com.orbital22.wemeet.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roster_plan_user_info")
public class RosterPlanUserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @ManyToOne
    @JoinColumn(name = "roster_plan_id")
    private RosterPlan rosterPlan;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private boolean hasResponded;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RosterPlanUserInfo that = (RosterPlanUserInfo) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
