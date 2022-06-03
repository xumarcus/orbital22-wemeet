package com.orbital22.wemeet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
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
@Table(name = "users") // user is reserved keyword in Postgres
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @NaturalId
    @NonNull
    @Column(unique = true)
    private String email;

    @JsonIgnore
    @RestResource(exported = false)
    @NonNull
    @Column
    private String password;

    @Column
    private boolean enabled;

    @Column
    private boolean registered;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    @ToString.Include
    @Builder.Default
    @NonNull
    private Set<Authority> authorities = Collections.emptySet();

    @OneToMany(mappedBy = "owner")
    @ToString.Exclude
    @Builder.Default
    @NonNull
    private Set<RosterPlan> ownedRosterPlans = Collections.emptySet();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Builder.Default
    @NonNull
    private Set<RosterPlanUserInfo> rosterPlanUserInfos = Collections.emptySet();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Builder.Default
    @NonNull
    private Set<TimeSlotUserInfo> timeSlotUserInfos = Collections.emptySet();

    public static User ofRegistered(String email, String encodedPassword) {
        return User.builder()
                   .email(email)
                   .password(encodedPassword)
                   .enabled(true)
                   .registered(true)
                   .build();
    }

    public static User ofUnregistered(String email) {
        return User.builder()
                   .email(email)
                   .password("")   // Fails authentication
                   .enabled(true)
                   .registered(false)
                   .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
