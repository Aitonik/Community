package com.community.community.dto;

import com.community.community.entities.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileShortDTO implements Serializable {

    private Long id;
    private String login;
    private String photoPath;
    private String profession;
    private List<Short> frs = new ArrayList<>();

    public ProfileShortDTO(Profile profile) {
        this.id = profile.getId();
        this.login = profile.getLogin();
        this.photoPath = profile.getPhotoPath();
        this.profession = profile.getProfession();
    }
}
