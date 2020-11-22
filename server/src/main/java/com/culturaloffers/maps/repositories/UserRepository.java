package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}