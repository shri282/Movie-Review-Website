package com.shri.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shri.models.DeletedMovies;

@Repository
public interface DeletedMovieRepo extends JpaRepository<DeletedMovies, Integer> {

}
