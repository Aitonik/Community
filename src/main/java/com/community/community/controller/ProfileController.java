package com.community.community.controller;

import com.community.community.dto.ProfileDTO;
import com.community.community.dto.ProfileShortDTO;
import com.community.community.entities.Invite;
import com.community.community.entities.Profile;
import com.community.community.payload.ApiCallResult;
import com.community.community.security.CurrentUser;
import com.community.community.security.UserPrincipal;
import com.community.community.service.InviteService;
import com.community.community.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private InviteService inviteService;

    private Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @GetMapping("/{id}")
    public ApiCallResult<ProfileDTO> getProfileById(
            @CurrentUser UserPrincipal userPrincipal,
            @PathVariable("id") Long id) {
        logger.info("getProfileById");
        try {
            if (id == 0) {
                id = userPrincipal.getId();
            }
            Profile profile = profileService.findById(id);
            ProfileDTO profileDTO = new ProfileDTO(profile);
            profileDTO.setFriendRequestsFromMe(profileService.findFriendRequestsFromMe(userPrincipal.getId()));

            return ApiCallResult.<ProfileDTO>builder().payload(profileDTO).status(HttpStatus.OK).build();
        } catch (Exception e) {

            logger.warn(e.getMessage());
            return ApiCallResult.<ProfileDTO>builder().info("Что-то пошло не так").status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/")
    public ApiCallResult<ProfileDTO> update(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "login") String login,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "socialLink", required = false) String socialLink,
            @RequestParam(name = "photoPath", required = false) String photoPath,
            @RequestParam(name = "profession", required = false) String profession,
            @RequestParam(name = "hobby", required = false) String hobby,
            @RequestParam(name = "zodiac", required = false) String zodiac,
            @RequestParam(name = "food", required = false) String food,
            @RequestParam(name = "movie", required = false) String movie,
            @RequestParam(name = "book", required = false) String book,
            @RequestParam(name = "power", required = false) String power,
            @RequestParam(name = "link", required = false) String link,
            @RequestParam(name = "future", required = false) String future,
            @RequestParam(name = "city", required = false) String city,
            @CurrentUser UserPrincipal userPrincipal
            ) {
        logger.info("update");
        try {
            Profile profile = profileService.findById(Long.parseLong(id));

            if (!StringUtils.isEmpty(login)) {
                profile.setLogin(login);
            }
            if (!StringUtils.isEmpty(description)) {
                profile.setDescription(description);
            }
            if (!StringUtils.isEmpty(socialLink)) {
                profile.setSocialLink(socialLink);
            }
            if (!StringUtils.isEmpty(photoPath)) {
                profile.setPhotoPath(photoPath);
            }
            if (!StringUtils.isEmpty(profession)) {
                profile.setProfession(profession);
            }
            if (!StringUtils.isEmpty(hobby)) {
                profile.setHobby(hobby);
            }
            if (!StringUtils.isEmpty(zodiac)) {
                profile.setZodiac(zodiac);
            }
            if (!StringUtils.isEmpty(food)) {
                profile.setFood(food);
            }
            if (!StringUtils.isEmpty(movie)) {
                profile.setMovie(movie);
            }
            if (!StringUtils.isEmpty(book)) {
                profile.setBook(book);
            }
            if (!StringUtils.isEmpty(power)) {
                profile.setPower(power);
            }
            if (!StringUtils.isEmpty(link)) {
                profile.setLink(link);
            }
            if (!StringUtils.isEmpty(future)) {
                profile.setFuture(future);
            }
            if (!StringUtils.isEmpty(city)) {
                profile.setCity(city);
            }

            profile = profileService.update(profile);

            return ApiCallResult.<ProfileDTO>builder().payload(new ProfileDTO(profile)).status(HttpStatus.OK).build();
        } catch (Exception e) {

            logger.warn(e.getMessage());
            return ApiCallResult.<ProfileDTO>builder().info("Что-то пошло не так").status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/friend_request/{id}")
    public ApiCallResult friendRequest(
            @PathVariable("id") Long id,
            @CurrentUser UserPrincipal userPrincipal) {
        logger.info("friend_request");
        try {
            List<ProfileShortDTO> profileShortDTOS = profileService.friendRequest(userPrincipal.getId(), id);

            ProfileDTO profileDTO = new ProfileDTO(profileService.findById(userPrincipal.getId()));
            profileDTO.setFriendRequestsFromMe(profileService.findFriendRequestsFromMe(userPrincipal.getId()));

            return ApiCallResult.builder().payload(profileDTO).status(HttpStatus.OK).build();
        } catch (Exception e) {

            logger.warn(e.getMessage());
            return ApiCallResult.<ProfileDTO>builder().info("Что-то пошло не так").status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/agree_friend/{id}")
    public ApiCallResult agreeFriend(
            @PathVariable("id") Long id,
            @CurrentUser UserPrincipal userPrincipal) {
        logger.info("agreeFriend");
        try {
            List<ProfileShortDTO> profileShortDTOS = profileService.agree(userPrincipal.getId(), id);

            ProfileDTO profileDTO = new ProfileDTO(profileService.findById(userPrincipal.getId()));
            profileDTO.setFriendRequestsFromMe(profileService.findFriendRequestsFromMe(userPrincipal.getId()));

            return ApiCallResult.builder().payload(profileDTO).status(HttpStatus.OK).build();
        } catch (Exception e) {

            logger.warn(e.getMessage());
            return ApiCallResult.<ProfileDTO>builder().info("Что-то пошло не так").status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/disagree_friend/{id}")
    public ApiCallResult disAgreeFriend(
            @PathVariable("id") Long id,
            @CurrentUser UserPrincipal userPrincipal) {
        logger.info("disAgreeFriend");
        try {

            List<ProfileShortDTO> profileShortDTOS = profileService.disAgree(userPrincipal.getId(), id);

            ProfileDTO profileDTO = new ProfileDTO(profileService.findById(userPrincipal.getId()));
            profileDTO.setFriendRequestsFromMe(profileService.findFriendRequestsFromMe(userPrincipal.getId()));

            return ApiCallResult.builder().payload(profileDTO).status(HttpStatus.OK).build();
        } catch (Exception e) {

            logger.warn(e.getMessage());
            return ApiCallResult.<ProfileDTO>builder().info("Что-то пошло не так").status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "/search")
    public ApiCallResult search(@RequestParam(name = "email") String email,
                                @CurrentUser UserPrincipal userPrincipal) {
        logger.info("search");
        try {

            List<ProfileShortDTO> profileShortDTOS = profileService.search(email);

            return ApiCallResult.builder().payload(profileShortDTOS).status(HttpStatus.OK).build();
        } catch (Exception e) {

            logger.warn(e.getMessage());
            return ApiCallResult.<ProfileDTO>builder().info("Что-то пошло не так").status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "/invite")
    public ApiCallResult invite(@RequestParam(name = "email") String email,
                                @CurrentUser UserPrincipal userPrincipal) {
        logger.info("invite");
        try {
            Profile profile = profileService.findByEmail(email);
            if (profile != null) {
                return ApiCallResult.<ProfileDTO>builder().info("Аккаунт с таким email уже есть в системе").status(HttpStatus.BAD_REQUEST).build();
            }

            Profile p = profileService.findById(userPrincipal.getId());
            Invite invite = inviteService.create(p, email);

            return ApiCallResult.builder().payload(Boolean.TRUE).status(HttpStatus.OK).build();
        } catch (MailException e) {

            return ApiCallResult.<ProfileDTO>builder().info("Приглашение не отправилось").status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {

            logger.warn(e.getMessage());
            return ApiCallResult.<ProfileDTO>builder().info("Что-то пошло не так").status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
