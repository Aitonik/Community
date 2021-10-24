package com.community.community.dto;

import com.community.community.entities.Comment;
import com.community.community.entities.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto implements Serializable {

    private Long id;

    private String text;

    private LocalDateTime creationTime;

    private ProfileShortDTO profileShort;

    public CommentDto(Comment comment, Profile profile) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.creationTime = comment.getCreationTime();
        this.profileShort = new ProfileShortDTO(profile);
    }

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.creationTime = comment.getCreationTime();
        this.profileShort = new ProfileShortDTO(comment.getProfile());
    }

}
