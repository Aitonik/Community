package com.community.community.controller;

import com.community.community.dao.DiscussionRepository;
import com.community.community.dao.ProfileRepository;
import com.community.community.dto.CommentDto;
import com.community.community.entities.Comment;
import com.community.community.entities.Discussion;
import com.community.community.entities.Profile;
import com.community.community.payload.ApiCallResult;
import com.community.community.security.CurrentUser;
import com.community.community.security.UserPrincipal;
import com.community.community.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    private final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @PostMapping("/")
    public ApiCallResult<CommentDto> save(
            @RequestParam String text,
            @RequestParam String discussionId,
            @CurrentUser UserPrincipal userPrincipal) {
        logger.info("saveByItem");
        try {
            Profile profile = profileRepository.findById(userPrincipal.getId()).get();
            Discussion discussion = discussionRepository.findById(Long.parseLong(discussionId)).get();
            Comment comment = commentService.saveComment(text, profile, discussion);

            return ApiCallResult.<CommentDto>builder().payload(new CommentDto(comment, profile)).status(HttpStatus.OK).build();
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return ApiCallResult.<CommentDto>builder().info("Вас нет в системе").status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
