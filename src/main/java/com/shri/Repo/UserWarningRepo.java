package com.shri.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shri.models.UserModel;
import com.shri.models.UserWarning;


@Repository
public interface UserWarningRepo extends JpaRepository<UserWarning, Integer>{

	Optional<UserWarning> findByUserid(UserModel userid);
	
	@Modifying
	@Query("UPDATE UserWarning m SET m.warning = :warning WHERE m.id = :id")
	void updateUserWarning(@Param("warning") int warning,@Param("id") int id);
	
}
