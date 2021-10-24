package com.community.community.controller;

import com.community.community.dao.InviteRepository;
import com.community.community.dao.ProfileRepository;
import com.community.community.entities.Invite;
import com.community.community.entities.Profile;
import com.community.community.payload.ApiCallResult;
import com.community.community.payload.LoginRequest;
import com.community.community.security.TokenProvider;
import com.community.community.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private InviteRepository inviteRepository;

    @Autowired
    private TokenProvider tokenProvider;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ApiCallResult<String> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("login - " + loginRequest.getEmail());

        Profile profile = profileRepository.findByEmail(loginRequest.getEmail());

        if (profile == null) {
            List<Invite> invites = inviteRepository.findAllByEmailAndCode(loginRequest.getEmail().trim(), loginRequest.getPassword().trim());

            if (!invites.isEmpty()) {
                profileService.create(loginRequest.getEmail(), loginRequest.getPassword(), invites.get(0).getProfile());
            } else {
                logger.warn("Вас нет в системе");
                return ApiCallResult.<String>builder()
                        .info("Вас нет в системе")
                        .status(HttpStatus.BAD_REQUEST).build();
            }
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail().toLowerCase(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.createToken(authentication);
            return ApiCallResult.<String>builder()
                    .payload(token)
                    .status(HttpStatus.OK).build();
        } catch (BadCredentialsException e) {
            logger.info(e.getMessage());
        }
        return ApiCallResult.<String>builder()
                .info("Неверный пароль")
                .status(HttpStatus.BAD_REQUEST).build();
    }



}
