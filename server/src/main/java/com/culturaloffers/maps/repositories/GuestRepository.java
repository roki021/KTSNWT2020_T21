package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Integer> {

}