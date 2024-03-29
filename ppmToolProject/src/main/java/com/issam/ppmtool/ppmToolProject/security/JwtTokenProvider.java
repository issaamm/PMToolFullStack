package com.issam.ppmtool.ppmToolProject.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.issam.ppmtool.ppmToolProject.Domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import static com.issam.ppmtool.ppmToolProject.security.SecurityConstants.SECRET;
import static com.issam.ppmtool.ppmToolProject.security.SecurityConstants.EXPIRATION_TIME;;

@Component
public class JwtTokenProvider {
	
	
	

	//generate the Token
	
	
	public String genrateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		
		Date now = new Date(System.currentTimeMillis());
		
		Date expiryDate = new Date(now.getTime()+EXPIRATION_TIME);
		
		String userId = Long.toString(user.getId());
		
		Map<String, Object> mapClaims = new HashMap<>();
		 mapClaims.put("id", userId);
		 mapClaims.put("username", user.getUsername());
		 mapClaims.put("fullName", user.getFullName());
		
		return Jwts.builder()
				.setSubject(userId)
				.setClaims(mapClaims)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
	}
	
	//validate the token generated
	
	public boolean validateToken(String token ) {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
			return true;
			
		} catch (SignatureException ex) {
			System.out.println("Invalid JWT Signature");
		}catch (MalformedJwtException ex) {
			System.out.println("Invalid JWT token");
		}catch (ExpiredJwtException ex) {
			System.out.println("Expired JWT token");
		}catch (UnsupportedJwtException ex) {
			System.out.println("Unsupported JWT token");
		}catch (IllegalArgumentException ex) {
			System.out.println("JWT claims string is empty");
		}
		return false;
	}
	
	//get the user id from token 
	
	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		String id = (String)claims.get("id");
		
		return Long.parseLong(id);
	}

}
