package com.community.community.service;

import com.community.community.dao.ProfileRepository;
import com.community.community.dto.ProfileShortDTO;
import com.community.community.entities.Profile;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private DiscussionService discussionService;

    public List<Pair<Profile, Profile>> getFr(Long id) {
        return profileRepository.findFr(id);
    }

    public List<Pair<Profile, Profile>> getFrFr(Long id) {

        return profileRepository.findFrFr(id);
    }

    public Profile findById(Long id) {
        return profileRepository.findById(id).get();
    }

    public Profile findByEmail(String email) {
        return profileRepository.findByEmail(email);
    }

    public Profile update(Profile profile) {

        return profileRepository.save(profile);
    }

    public List<ProfileShortDTO> findFriendRequestsFromMe(Long profileId) {
        return profileRepository.findRequestsFromMe(profileId).stream().map(ProfileShortDTO::new).collect(Collectors.toList());
    }

    public List<ProfileShortDTO> friendRequest(Long profileId, Long friendId) {

        Profile profile = findById(profileId);
        Profile friend = findById(friendId);

        friend.getFriendRequests().add(profile);
        friend = profileRepository.saveAndFlush(friend);

        return profileRepository.findRequestsFromMe(profileId).stream().map(ProfileShortDTO::new).collect(Collectors.toList());

    }

    public List<ProfileShortDTO> agree(Long profileId, Long friendId) {

        Profile profile = findById(profileId);
        Profile friend = findById(friendId);

        profile.getFriendRequests().remove(friend);
        profile.getFriends().add(friend);
        friend.getFriends().add(profile);
        profile = profileRepository.saveAndFlush(profile);

        return profile.getFriendRequests().stream().map(ProfileShortDTO::new).collect(Collectors.toList());
    }

    public List<ProfileShortDTO> disAgree(Long profileId, Long friendId) {

        Profile profile = findById(profileId);
        Profile friend = findById(friendId);

        profile.getFriendRequests().remove(friend);
        profile = profileRepository.saveAndFlush(profile);

        return profile.getFriendRequests().stream().map(ProfileShortDTO::new).collect(Collectors.toList());
    }

    public List<ProfileShortDTO> search(String name) {

        return profileRepository.findByEmailLike(name).stream().map(ProfileShortDTO::new).collect(Collectors.toList());
    }

    public Profile create(String email, String code, Profile friend) {

        Profile profile = new Profile();
        profile.setLogin(email);
        profile.setEmail(email);
        profile.setFriends(new HashSet<>(Collections.singletonList(friend)));
        profile.setPassword(code);

        profile = profileRepository.saveAndFlush(profile);
        friend.getFriends().add(profile);

        return profile;
    }
}
