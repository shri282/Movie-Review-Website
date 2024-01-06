package com.shri.models;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ReviewReplys {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(columnDefinition = "text")
	private String reply;
	@ManyToOne
	private UserModel user;
	@ManyToOne
	private Movies movie;
	@ManyToOne
	private Reviews review;
	@Column(name = "reply_timestamp") 
    private Timestamp timestamp;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
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
	public Reviews getReview() {
		return review;
	}
	public void setReview(Reviews review) {
		this.review = review;
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
	public ReviewReplys(int id, String reply, UserModel user, Movies movie, Reviews review, Timestamp timestamp) {
		super();
		this.id = id;
		this.reply = reply;
		this.user = user;
		this.movie = movie;
		this.review = review;
		this.timestamp = timestamp;
	}
	
	public ReviewReplys() {
		super();
	}
	@Override
	public String toString() {
		return "ReviewReplys [id=" + id + ", reply=" + reply + ", user=" + user + ", movie=" + movie + ", review="
				+ review + ", timestamp=" + timestamp + "]";
	}
	
	
	
	
	

}
