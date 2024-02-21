package com.shri.RestRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.shri.models.Movies;

@RepositoryRestResource(collectionResourceRel = "restmovies",path = "restmovies")
public interface MoviesRest extends JpaRepository<Movies, Integer> {

}
