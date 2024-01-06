package com.shri.models;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class WatchLater {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private UserModel user;
	@ManyToOne
	private Movies movie;
	@Column(name = "wl_timestamp")
	private Timestamp timestamp;
	@Column(columnDefinition = "text")
	private String link;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public WatchLater(int id, UserModel user, Movies movie, Timestamp timestamp, String link) {
		super();
		this.id = id;
		this.user = user;
		this.movie = movie;
		this.timestamp = timestamp;
		this.link = link;
	}
	
	public WatchLater() {
		super();
	}
	
	@Override
	public String toString() {
		return "WatchLater [id=" + id + ", user=" + user + ", movie=" + movie + ", timestamp=" + timestamp + ", link="
				+ link + "]";
	}
	
	
	
}
