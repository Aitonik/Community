package com.community.community.config;

import com.community.community.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class Scheduler {

    @Autowired
    private DiscussionService discussionService;

    @Scheduled(fixedRate = 600000)
    public void archiveDiscussion() {
        discussionService.delete();
    }
}
