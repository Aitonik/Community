package com.community.community.service;

import com.community.community.dao.InviteRepository;
import com.community.community.entities.Invite;
import com.community.community.entities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class InviteService {

    @Autowired
    private InviteRepository inviteRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    public Invite create(Profile profile, String email) {
        Invite invite = new Invite();
        invite.setCode(UUID.randomUUID().toString());
        invite.setEmail(email);
        invite.setProfile(profile);

        inviteRepository.save(invite);

        SimpleMailMessage simpleMail = new SimpleMailMessage();
        simpleMail.setFrom(username);
        simpleMail.setTo(email.toLowerCase());
        simpleMail.setSubject("Vdele");
        simpleMail.setText("code: " + invite.getCode());

        mailSender.send(simpleMail);

        return invite;
    }
}
