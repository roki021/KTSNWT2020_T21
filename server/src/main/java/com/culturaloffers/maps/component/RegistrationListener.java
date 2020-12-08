package com.culturaloffers.maps.component;

import com.culturaloffers.maps.helper.OnRegistrationCompleteEvent;
import com.culturaloffers.maps.model.User;
import com.culturaloffers.maps.services.UserDetailsServiceImpl;
import com.culturaloffers.maps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userDetailsService.createVerificationToken(user, token);

        String recipientAddress = user.getEmailAddress();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = event.getAppUrl() + "/auth/registrationConfirm?token=" + token;
        String message = "Za završetak registracije otvorite prosleđeni link: ";
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
        mailSender.send(email);
    }
}
