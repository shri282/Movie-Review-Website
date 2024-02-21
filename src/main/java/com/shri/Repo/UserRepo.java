package com.shri.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shri.models.UserModel;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Integer> {

	UserModel findByusername(String username);
	
}
