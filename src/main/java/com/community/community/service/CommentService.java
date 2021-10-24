package com.community.community.service;

import com.community.community.dao.CommentRepository;
import com.community.community.entities.Comment;
import com.community.community.entities.Discussion;
import com.community.community.entities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(String text, Profile profile, Discussion discussion) {
        Comment comment = Comment.builder()
                .text(text)
                .creationTime(LocalDateTime.now())
                .profile(profile)
                .discussion(discussion)
                .build();
        return commentRepository.save(comment);
    }

}
