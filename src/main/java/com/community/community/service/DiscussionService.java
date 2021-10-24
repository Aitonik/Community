package com.community.community.service;

import com.community.community.dao.DiscussionRepository;
import com.community.community.dto.DiscussionDTO;
import com.community.community.dto.Period;
import com.community.community.entities.Discussion;
import com.community.community.entities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class DiscussionService {

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private CommentService commentService;

    public List<DiscussionDTO> getNews(Long profileId) {
        List<Discussion> discussions = discussionRepository.findByProfileId(profileId);
        List<Discussion> discussionsFr = discussionRepository.getNewsFriends(profileId);
        List<Discussion> discussionsFrFr = discussionRepository.getNewsFriendsFriends(profileId);

        Set<Discussion> discussionSet = new HashSet<>(discussions);
        discussionSet.addAll(discussionsFr);
        discussionSet.addAll(discussionsFrFr);
        return discussionSet.stream().sorted(Comparator.comparing(Discussion::getCreationTime)).map(DiscussionDTO::new).collect(Collectors.toList());
    }

    public DiscussionDTO findById(Long id) {
        Discussion discussion = discussionRepository.findById(id).get();

        return new DiscussionDTO(discussion);
    }

    public DiscussionDTO save(String text, Period period, Long userId) {
        LocalDateTime creationTime = LocalDateTime.now();
        LocalDateTime deleteTime = null;
        if (period == Period.HOUR) {
            deleteTime = creationTime.plusHours(1L);
        }
        if (period == Period.WEEK) {
            deleteTime = creationTime.plusDays(7L);
        }
        if (period == Period.DAY) {
            deleteTime = creationTime.plusDays(1L);
        }
        if (period == Period.MONTH) {
            deleteTime = creationTime.plusMonths(1L);
        }
        Profile profile = profileService.findById(userId);
        Discussion discussion = Discussion.builder()
                .active(true)
                .period(period.name())
                .creationTime(creationTime)
                .deleteTime(deleteTime)
                .text(text)
                .profile(profile).build();
        Discussion discussionSaved = discussionRepository.save(discussion);

        return new DiscussionDTO(discussionSaved);
    }

    public DiscussionDTO update(Long id, String text, Period period) {
        Discussion discussion = discussionRepository.findById(id).get();
        LocalDateTime creationTime = discussion.getCreationTime();
        LocalDateTime deleteTime = null;
        if (period == Period.HOUR) {
            deleteTime = creationTime.plusHours(1L);
        }
        if (period == Period.WEEK) {
            deleteTime = creationTime.plusDays(7L);
        }
        if (period == Period.DAY) {
            deleteTime = creationTime.plusDays(1L);
        }
        if (period == Period.MONTH) {
            deleteTime = creationTime.plusMonths(1L);
        }
        discussion.setText(text);
        discussion.setPeriod(period.name());
        discussion.setDeleteTime(deleteTime);
        if (deleteTime.isBefore(LocalDateTime.now())) {
            discussion.setActive(false);
        }

        return new DiscussionDTO(discussion);
    }

    public void delete() {
        List<Discussion> discussions = discussionRepository.findForDelete(LocalDateTime.now());
        if (!discussions.isEmpty()) {
            discussions.forEach(d -> d.setActive(false));
        }
    }

}
