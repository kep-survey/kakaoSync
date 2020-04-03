package com.kep.kakaosync.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kep.kakaosync.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
}
