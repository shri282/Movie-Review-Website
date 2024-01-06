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
	        @UniqueConstraint(name = "unique_adminusername_constraint", columnNames = "username"),
	        @UniqueConstraint(name = "unique_adminemail_constraint", columnNames = "email")
	    }
	)
public class AuthorizedAdmins {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true)
	private String username;
	@Column(unique = true)
	private String email;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "AuthorizedAdmins [id=" + id + ", username=" + username + ", email=" + email + "]";
	}
	public AuthorizedAdmins(int id, String username, String email) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
	}
	public AuthorizedAdmins() {
		super();
	}
	public AuthorizedAdmins(String username, String email) {
		super();
		this.username = username;
		this.email = email;
	}
	
	
	
}
