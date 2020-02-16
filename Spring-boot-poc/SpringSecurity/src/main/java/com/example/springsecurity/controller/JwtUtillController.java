package com.example.springsecurity.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class JwtUtillController {

	

	private final String TOKEN_SECRET = "h4of9eh48vmg02nfu30v27yen295hfj65";

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token,
			Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token)
				.getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		//System.out.println("in th eis token expired");
		return expiration.before(new Date());
	}

	

	public void validateToken(String token) {
		//final String username = getUsernameFromToken(token);
		//System.out.println("inside validate token method");
	boolean tokenExpired=	!isTokenExpired(token);
		if(tokenExpired== true)
		{
			System.out.println("Token is valid");
		}
		else
		{
			System.out.println("Token is not valid as the time is expired");
		}
		//System.out.println("!isTokenExpired(token)"+!isTokenExpired(token));
		
	}
}
