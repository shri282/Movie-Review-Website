package com.shri.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shri.models.Movies;
import com.shri.models.UserModel;
import com.shri.models.WatchLater;
import java.util.List;


@Repository
public interface WatchLaterRepo extends JpaRepository<WatchLater, Integer> {
     List<WatchLater> findByUser(UserModel user);
     
     List<WatchLater> findByMovie(Movies movie);
}
