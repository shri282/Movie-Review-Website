package com.shri.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shri.models.Movies;
import com.shri.models.ReviewReplys;
import com.shri.models.Reviews;
import com.shri.models.UserModel;

import java.util.List;


@Repository
public interface reviewreplyRepo extends JpaRepository<ReviewReplys, Integer> {

	List<ReviewReplys> findByMovie(Movies movie);
	
	List<ReviewReplys> findByReview(Reviews review);
	
	List<ReviewReplys> findByUser(UserModel user);
	
}
