package com.community.community.controller;

import com.community.community.dto.DiscussionDTO;
import com.community.community.dto.Period;
import com.community.community.payload.ApiCallResult;
import com.community.community.security.CurrentUser;
import com.community.community.security.UserPrincipal;
import com.community.community.service.DiscussionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

    private Logger logger = LoggerFactory.getLogger(DiscussionController.class);

    @GetMapping(value = "/person/news")
    public ApiCallResult<List<DiscussionDTO>> getPersonNews(@CurrentUser UserPrincipal userPrincipal) {
        logger.info("getPersonNews");

        try {
            List<DiscussionDTO> discussionDTOS = discussionService.getNews(userPrincipal.getId());

            return ApiCallResult.<List<DiscussionDTO>>builder().payload(discussionDTOS).status(HttpStatus.OK).build();
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return ApiCallResult.<List<DiscussionDTO>>builder().info("Что-то пошло не так").status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/{id}")
    public ApiCallResult<DiscussionDTO> getDiscussion(@PathVariable(value = "id") String id,
                                                      @CurrentUser UserPrincipal userPrincipal) {
        logger.info("getItem");

        try {
            DiscussionDTO discussionDTO = discussionService.findById(Long.parseLong(id));

            return ApiCallResult.<DiscussionDTO>builder().payload(discussionDTO).status(HttpStatus.OK).build();
        } catch (Exception e) {

            logger.warn(e.getMessage());
            return ApiCallResult.<DiscussionDTO>builder().info("Что-то пошло не так").status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/")
    public ApiCallResult<DiscussionDTO> update(
            @RequestParam(required = false) String itemId,
            @RequestParam String text,
            @RequestParam String period,
            @CurrentUser UserPrincipal userPrincipal
    )  {
        logger.info("update");
        try {
            DiscussionDTO discussionDTO;
            if (StringUtils.hasText(itemId)) {
                discussionDTO = discussionService.update(Long.parseLong(itemId), text, Period.valueOf(period));
            } else {
                discussionDTO = discussionService.save(text, Period.valueOf(period), userPrincipal.getId());
            }

            return ApiCallResult.<DiscussionDTO>builder().payload(discussionDTO).status(HttpStatus.OK).build();
        } catch (Exception e) {

            logger.warn(e.getMessage());
            return ApiCallResult.<DiscussionDTO>builder().info("Что-то пошло не так").status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
