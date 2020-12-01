package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer> {

    Guest findByUsername(String username);

    Guest findByEmailAddress(String emailAddress);

    Page<Guest> findAll(Pageable pageable);
}