package com.community.community.dto;

import com.community.community.entities.Discussion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionDTO implements Serializable {

    private Long id;

    private String text;

    private String period;

    private LocalDateTime creationTime;

    private LocalDateTime deleteTime;

    private ProfileShortDTO profileShort;

    private List<CommentDto> comments;

    private int commentCount;

    public DiscussionDTO(Discussion discussion) {
        this.id = discussion.getId();
        this.text = discussion.getText();
        this.creationTime = discussion.getCreationTime();
        this.deleteTime = discussion.getDeleteTime();
        this.profileShort = new ProfileShortDTO(discussion.getProfile());
        if (discussion.getComments() != null) {
            this.comments = discussion.getComments().stream().map(CommentDto::new).collect(Collectors.toList());
        }
    }

}
