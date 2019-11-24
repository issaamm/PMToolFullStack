package com.issam.ppmtool.ppmToolProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issam.ppmtool.ppmToolProject.Domain.User;
import com.issam.ppmtool.ppmToolProject.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user==null) new UsernameNotFoundException("User can not be found");
		return user;
	}
	
	@Transactional
	public UserDetails loadUserById(Long id) {
		User user = userRepository.getById(id);
		if(user==null) new UsernameNotFoundException("User can not be found");
		return user;
	}

}
