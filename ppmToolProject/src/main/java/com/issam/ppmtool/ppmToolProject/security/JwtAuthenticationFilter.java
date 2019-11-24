package com.issam.ppmtool.ppmToolProject.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.issam.ppmtool.ppmToolProject.security.SecurityConstants.HEADER_STRING;
import static com.issam.ppmtool.ppmToolProject.security.SecurityConstants.PREFIX_TOKEN;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.issam.ppmtool.ppmToolProject.Domain.User;
import com.issam.ppmtool.ppmToolProject.Service.CustomUserDetailsService;

public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 try {
			 
			 String jwt = getJwtFromHttpServletRequest(request);
			 
			 if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				 Long userId = tokenProvider.getUserIdFromToken(jwt);
				 User userDetails = (User)customUserDetailsService.loadUserById(userId);
				 
				 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						     userDetails,null,Collections.emptyList());
				 SecurityContextHolder.getContext().setAuthentication(authentication);
			 }
			
		} catch (Exception e) {
			logger.error("Could not set user authentication in our security context !",e);
		}
		 
		 filterChain.doFilter(request, response);
	}
	
	private String getJwtFromHttpServletRequest(HttpServletRequest request) {
		
		String berearRequest = request.getHeader(HEADER_STRING);
		
		if(StringUtils.hasText(berearRequest) && berearRequest.startsWith(PREFIX_TOKEN)) {
			return berearRequest.substring(7,berearRequest.length());
		}
		return null;
	}

}
