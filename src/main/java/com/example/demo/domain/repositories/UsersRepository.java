package com.example.demo.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.entities.User;
import java.util.List;
import java.util.Optional;


public interface UsersRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
}
