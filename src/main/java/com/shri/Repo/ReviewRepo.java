package com.shri.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shri.models.Movies;
import com.shri.models.Reviews;
import java.util.List;
import com.shri.models.UserModel;



public interface ReviewRepo extends JpaRepository<Reviews, Integer> {

	List<Reviews> findByMovie(Movies movie);
	
	List<Reviews> findByUser(UserModel user);
	
}
