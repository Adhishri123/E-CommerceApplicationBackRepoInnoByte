package com.innobyte.services.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobyte.services.enums.UserRole;
import com.innobyte.services.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//	User findByUserId(Long uid);
//	
//	User findByUserEmailIdAndPassword(String useremailid , String password);
	
	Optional<User> findFirstByEmail(String email);
	
	User findByRole(UserRole userRole);
}
