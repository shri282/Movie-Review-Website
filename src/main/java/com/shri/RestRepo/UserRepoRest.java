package com.shri.RestRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.shri.models.UserModel;

@RepositoryRestResource(collectionResourceRel = "restusers",path = "restusers")
public interface UserRepoRest extends JpaRepository<UserModel, Integer> {

}
