package com.issam.ppmtool.ppmToolProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.issam.ppmtool.ppmToolProject.Domain.User;
import com.issam.ppmtool.ppmToolProject.Exceptions.UniqueLoginException;
import com.issam.ppmtool.ppmToolProject.Repository.UserRepository;
import com.issam.ppmtool.ppmToolProject.Validator.UserValidation;

@Service
public class UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncrypter;
	
	
	
	public User saveUser(User user) {
		user.setPassword(passwordEncrypter.encode(user.getPassword()));
		user.setConfirmPassword("");
		//the username has to be unique
		User newUser = user;
		try {
			userRepository.save(newUser);
		} catch (Exception e) {
			throw new UniqueLoginException("the username '"+newUser.getUsername()+"'already exists!");
		}
		//make sure that the password and confirmPassword match
		//to not persist or show the confirmPassword attribute
		return newUser;
	}
}
