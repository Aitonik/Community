package com.community.community.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROFILE")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String email;

    private String password;

    private String link;

    private String description;

    private String socialLink;

    @Column(name = "photoPath")
    private String photoPath;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "profile_friends",
            joinColumns = { @JoinColumn(name = "profile_id") },
            inverseJoinColumns = { @JoinColumn(name = "friends_id") }
    )
    private Set<Profile> friends = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "profile_friendrequests",
            joinColumns = { @JoinColumn(name = "profile_id") },
            inverseJoinColumns = { @JoinColumn(name = "friends_id") }
    )
    private Set<Profile> friendRequests = new HashSet<>();

    private String profession;

    private String zodiac;

    private String food;

    private String movie;

    private String power;

    private String book;

    private String hobby;

    private String future;

    private String city;

    @Column(name = "creation_time")
    private LocalDate creationTime;
}
