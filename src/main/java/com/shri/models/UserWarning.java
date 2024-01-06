package com.shri.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class UserWarning {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToOne
	private UserModel userid;
	private int warning;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UserModel getUserid() {
		return userid;
	}
	public void setUserid(UserModel userid) {
		this.userid = userid;
	}
	public int getWarning() {
		return warning;
	}
	public void setWarning(int warning) {
		this.warning = warning;
	}
	public UserWarning(int id, UserModel userid, int warning) {
		super();
		this.id = id;
		this.userid = userid;
		this.warning = warning;
	}
	public UserWarning(UserModel userid, int warning) {
		super();
		this.userid = userid;
		this.warning = warning;
	}
	@Override
	public String toString() {
		return "UserWarning [id=" + id + ", userid=" + userid + ", warning=" + warning + "]";
	}
	public UserWarning() {
		super();
	}
	
	
}
