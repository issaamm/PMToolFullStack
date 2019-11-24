package com.issam.ppmtool.ppmToolProject.Validator;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.issam.ppmtool.ppmToolProject.Domain.User;

@Component
public class UserValidation implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		User user = (User) target;

		if(user.getPassword().length()<6) {
			errors.rejectValue("password", "Length","the password length must be more than 6 digits");
		}
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword","Match" ,"passwords must match");
		}
	}

}
