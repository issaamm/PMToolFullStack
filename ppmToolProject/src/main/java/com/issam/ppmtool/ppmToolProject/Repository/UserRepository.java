package com.issam.ppmtool.ppmToolProject.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.issam.ppmtool.ppmToolProject.Domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	User findByUsername(String username);
	User getById(Long id);
}
