package com.shri.models;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Reviews {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "text") 
    private String review;

    private int star;

    @ManyToOne
    private UserModel user;

    @ManyToOne
    private Movies movie;

    @Column(name = "review_timestamp") 
    private Timestamp timestamp;
    
    
    
    

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public Movies getMovie() {
		return movie;
	}

	public void setMovie(Movies movie) {
		this.movie = movie;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Reviews [id=" + id + ", review=" + review + ", star=" + star + ", user=" + user + ", movie=" + movie
				+ ", timestamp=" + timestamp + "]";
	}

	public Reviews(int id, String review, int star, UserModel user, Movies movie, Timestamp timestamp) {
		super();
		this.id = id;
		this.review = review;
		this.star = star;
		this.user = user;
		this.movie = movie;
		this.timestamp = timestamp;
	}
	
	

	public Reviews(String review, int star, UserModel user, Movies movie, Timestamp timestamp) {
		super();
		this.review = review;
		this.star = star;
		this.user = user;
		this.movie = movie;
		this.timestamp = timestamp;
	}

	public Reviews() {
		super();
	}
    
    


	
}
