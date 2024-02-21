package com.shri.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
	    uniqueConstraints = {
	        @UniqueConstraint(name = "unique_Deletedmoviename_constraint", columnNames = "moviename")
	    }
	)
public class DeletedMovies {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String moviename;
    @Column(columnDefinition = "text")
    private String description;
    private String director;
    private String year;
    private float rating;
    @Column(columnDefinition = "text")
    private String path;
    private String genre;
    @Column(columnDefinition = "text")
    private String trailerlink;
    @Column(columnDefinition = "text")
    private String slidepath;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMoviename() {
		return moviename;
	}
	public void setMoviename(String moviename) {
		this.moviename = moviename;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getTrailerlink() {
		return trailerlink;
	}
	public void setTrailerlink(String trailerlink) {
		this.trailerlink = trailerlink;
	}
	public String getSlidepath() {
		return slidepath;
	}
	public void setSlidepath(String slidepath) {
		this.slidepath = slidepath;
	}
	
	@Override
	public String toString() {
		return "DeletedMovies [id=" + id + ", moviename=" + moviename + ", description=" + description + ", director="
				+ director + ", year=" + year + ", rating=" + rating + ", path=" + path + ", genre=" + genre
				+ ", trailerlink=" + trailerlink + ", slidepath=" + slidepath + "]";
	}
	
	public DeletedMovies(int id, String moviename, String description, String director, String year, float rating,
			String path, String genre, String trailerlink, String slidepath) {
		super();
		this.id = id;
		this.moviename = moviename;
		this.description = description;
		this.director = director;
		this.year = year;
		this.rating = rating;
		this.path = path;
		this.genre = genre;
		this.trailerlink = trailerlink;
		this.slidepath = slidepath;
	}
	
	public DeletedMovies(String moviename, String description, String director, String year, float rating, String path,
			String genre, String trailerlink, String slidepath) {
		super();
		this.moviename = moviename;
		this.description = description;
		this.director = director;
		this.year = year;
		this.rating = rating;
		this.path = path;
		this.genre = genre;
		this.trailerlink = trailerlink;
		this.slidepath = slidepath;
	}
	
	public DeletedMovies() {
		super();
	}
	
	
	
	
    
    
	
}
