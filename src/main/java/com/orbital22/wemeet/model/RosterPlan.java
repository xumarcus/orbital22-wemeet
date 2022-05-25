package com.orbital22.wemeet.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

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

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @NonNull
    private User owner;

    @Column
    @NonNull
    private String title;

    @OneToMany(fetch = FetchType.EAGER)
    private Collection<RosterPlanUserInfo> rosterPlanUserInfos;

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
