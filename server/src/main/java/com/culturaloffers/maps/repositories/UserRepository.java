package com.culturaloffers.maps.repositories;

import com.culturaloffers.maps.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmailAddress(String emailAddress);

    Page<User> findAll(Pageable pageable);

}