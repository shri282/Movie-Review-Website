package com.shri.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { 	
         @UniqueConstraint(columnNames = "username", name = "unique_username_constraint"),
         @UniqueConstraint(columnNames = "email", name = "unique_email_constraint")
              }
      )
public class UserModel {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    
	    @Column(unique = true)
	    private String username;
	    private String userpassword;
	    @Column(unique = true)
	    private String email;
	    @Enumerated(EnumType.STRING)
	    private Roles role;
	    private boolean superadmin;
        private String firstname;
        private String lastname;
        
        
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
		public String getUserpassword() {
			return userpassword;
		}
		public void setUserpassword(String userpassword) {
			this.userpassword = userpassword;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public Roles getRole() {
			return role;
		}
		public void setRole(Roles role) {
			this.role = role;
		}
		public boolean isSuperadmin() {
			return superadmin;
		}
		public void setSuperadmin(boolean superadmin) {
			this.superadmin = superadmin;
		}
		public String getFirstname() {
			return firstname;
		}
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}
		public String getLastname() {
			return lastname;
		}
		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
		@Override
		public String toString() {
			return "UserModel [id=" + id + ", username=" + username + ", userpassword=" + userpassword + ", email="
					+ email + ", role=" + role + ", superadmin=" + superadmin + ", firstname=" + firstname
					+ ", lastname=" + lastname + "]";
		}
		public UserModel(String username, String userpassword, String email, Roles role, boolean superadmin,
				String firstname, String lastname) {
			super();
			this.username = username;
			this.userpassword = userpassword;
			this.email = email;
			this.role = role;
			this.superadmin = superadmin;
			this.firstname = firstname;
			this.lastname = lastname;
		}
		public UserModel(int id, String username, String userpassword, String email, Roles role, boolean superadmin,
				String firstname, String lastname) {
			super();
			this.id = id;
			this.username = username;
			this.userpassword = userpassword;
			this.email = email;
			this.role = role;
			this.superadmin = superadmin;
			this.firstname = firstname;
			this.lastname = lastname;
		}
		public UserModel() {
			super();
		}
        
		
	
}
