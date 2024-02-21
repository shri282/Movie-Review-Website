package com.shri.Repo;


import java.util.List; 
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shri.models.Movies;


@Repository
public interface MoviesRepo extends JpaRepository<Movies, Integer> {

	Optional<Movies> findByMoviename(String moviename);
	
	List<Movies> findTop5ByOrderByRatingDesc();
	
	List<Movies> findByGenre(String genre);
	
	List<Movies> findByDirector(String director);
	
	@Modifying
	@Query("UPDATE Movies m SET m.moviename = :newValue WHERE m.id = :id")
	void updateMovienameById(@Param("id") int id, @Param("newValue") String newValue);
	
	@Modifying
	@Query("UPDATE Movies m SET m.description = :newValue WHERE m.id = :id")
	void updateDescriptionById(@Param("id") int id, @Param("newValue") String newValue);
	
	@Modifying
	@Query("UPDATE Movies m SET m.director = :newValue WHERE m.id = :id")
	void updateDirectorById(@Param("id") int id, @Param("newValue") String newValue);
	
	@Modifying
	@Query("UPDATE Movies m SET m.year = :newValue WHERE m.id = :id")
	void updateyearById(@Param("id") int id, @Param("newValue") String newValue);
	
	
	@Modifying
	@Query("UPDATE Movies m SET m.rating = :newValue WHERE m.id = :id")
	void updateRatingById(@Param("id") int id, @Param("newValue") float newValue);
	
	
	@Modifying
	@Query("UPDATE Movies m SET m.path = :newValue WHERE m.id = :id")
	void updatePathById(@Param("id") int id, @Param("newValue") String newValue);
	
	
	@Modifying
	@Query("UPDATE Movies m SET m.genre = :newValue WHERE m.id = :id")
	void updateGenreById(@Param("id") int id, @Param("newValue") String newValue);
	
	
	@Modifying
	@Query("UPDATE Movies m SET m.trailerlink = :newValue WHERE m.id = :id")
	void updateTrailerlinkById(@Param("id") int id, @Param("newValue") String newValue);
	
	
	@Modifying
	@Query("UPDATE Movies m SET m.slidepath = :newValue WHERE m.id = :id")
	void updateSlidepathById(@Param("id") int id, @Param("newValue") String newValue);
	
}
