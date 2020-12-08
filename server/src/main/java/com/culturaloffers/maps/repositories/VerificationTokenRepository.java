package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.User;
import com.culturaloffers.maps.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository
        extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
