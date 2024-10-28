package com.innobyte.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobyte.services.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
