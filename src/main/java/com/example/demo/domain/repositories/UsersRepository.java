package com.example.demo.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.entities.User;


public interface UsersRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
}
