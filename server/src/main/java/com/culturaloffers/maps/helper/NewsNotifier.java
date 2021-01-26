package com.culturaloffers.maps.helper;

import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.model.Guest;
import com.culturaloffers.maps.model.OfferNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class NewsNotifier {

    @Autowired
    private JavaMailSender mailSender;

    public void notifySubscribers(CulturalOffer culturalOffer, OfferNews offerNews)
    {
        Set<Guest> subscribedGuests = culturalOffer.getSubscribers();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(culturalOffer.getTitle() + ": " + offerNews.getTitle());
        email.setText(offerNews.getDescription());

        for(Guest g : subscribedGuests)
        {
            email.setTo(g.getEmailAddress());
            mailSender.send(email);
        }

    }
}
