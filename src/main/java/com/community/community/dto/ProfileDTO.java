package com.community.community.dto;

import com.community.community.entities.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDTO implements Serializable {

    private Long id;

    private String login;

    private String description;

    private String socialLink;

    private String photoPath;

    private List<ProfileShortDTO> friends = new ArrayList<>();

    private List<ProfileShortDTO> friendRequests = new ArrayList<>();

    private String profession;

    private String zodiac;

    private String food;

    private String movie;

    private String power;

    private String book;

    private String telegramChannel;

    private String hobby;

    private String future;

    public ProfileDTO(Profile profile) {
        this.id = profile.getId();
        this.login = profile.getLogin();
        this.description = profile.getDescription();
        this.socialLink = profile.getSocialLink();
        this.photoPath = profile.getPhotoPath();
        this.friends = profile.getFriends().stream().map(ProfileShortDTO::new).collect(Collectors.toList());
        this.friendRequests = profile.getFriendRequests().stream().map(ProfileShortDTO::new).collect(Collectors.toList());
        this.profession = profile.getProfession();
        this.zodiac = profile.getZodiac();
        this.food = profile.getFood();
        this.movie = profile.getMovie();
        this.power = profile.getPower();
        this.book = profile.getBook();
        this.telegramChannel = profile.getBook();
        this.hobby = profile.getHobby();
        this.future = profile.getFuture();
    }
}
