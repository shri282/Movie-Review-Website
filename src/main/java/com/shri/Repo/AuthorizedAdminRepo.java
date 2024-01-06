package com.shri.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shri.models.AuthorizedAdmins;
import java.util.List;

@Repository
public interface AuthorizedAdminRepo extends JpaRepository<AuthorizedAdmins, Integer> {

	List<AuthorizedAdmins> findByEmail(String email);
	
}
