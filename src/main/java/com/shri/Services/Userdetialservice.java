package com.shri.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shri.UserPrincipal;
import com.shri.Repo.UserRepo;
import com.shri.models.UserModel;


public class Userdetialservice implements UserDetailsService {
	
	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserModel user = repo.findByusername(username);
		System.out.println(username);
		System.out.println(repo.findAll());
		System.out.println(user);
		if(user == null) {
			throw new UsernameNotFoundException("user 404");
		}
		return new UserPrincipal(user);
		
	}

}
