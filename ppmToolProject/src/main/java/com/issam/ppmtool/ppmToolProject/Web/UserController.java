package com.issam.ppmtool.ppmToolProject.Web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issam.ppmtool.ppmToolProject.Domain.User;
import com.issam.ppmtool.ppmToolProject.Payload.JwtLoginSuccessResponse;
import com.issam.ppmtool.ppmToolProject.Payload.LoginRequest;
import com.issam.ppmtool.ppmToolProject.Service.UserService;
import com.issam.ppmtool.ppmToolProject.Service.ValidationService;
import com.issam.ppmtool.ppmToolProject.Validator.UserValidation;
import com.issam.ppmtool.ppmToolProject.security.JwtTokenProvider;

import static com.issam.ppmtool.ppmToolProject.security.SecurityConstants.PREFIX_TOKEN;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ValidationService validationService; 
	
	@Autowired
	private UserValidation userValidation;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User newUser, BindingResult result){
		
		userValidation.validate(newUser, result);
		
		ResponseEntity<?> errorMap = validationService.getErrorsForProject(result);
		if(errorMap != null) return validationService.getErrorsForProject(result);
		
		User user = userService.saveUser(newUser);
		return new ResponseEntity<User>(user,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
		ResponseEntity<?> errorMap = validationService.getErrorsForProject(result);
		if(errorMap!=null) return errorMap;
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
                             loginRequest.getUsername(), 
                             loginRequest.getPassword()
                             )
				);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt  = PREFIX_TOKEN + tokenProvider.genrateToken(authentication);
		
		return ResponseEntity.ok(new JwtLoginSuccessResponse(true, jwt));
	}

}
