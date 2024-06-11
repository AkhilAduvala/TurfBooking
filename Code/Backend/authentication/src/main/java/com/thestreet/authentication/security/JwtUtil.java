/**
 * 
 */
package com.thestreet.authentication.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author akhil
 * 
 * JwtUtil provides the utility methods for generating and validating the JWT tokens
 *
 */
@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKey;
	
	@Value("${jwt.expiration}")
	private long jwtExpirationInMs;
	
	private static final Logger logger = LogManager.getLogger(JwtUtil.class);
	
	/**
	 * Retrieve the username from the JWT token
	 * 
	 * @param JWT token
	 * @return username
	 * 
	 */
	public String extractUsername(String token) {
		logger.info("inside extractUsername");
		return extractClaim(token, Claims::getSubject);
	}
	
	/**
	 * Retrieve the expiration date frpm JWT token
	 * 
	 * @param JWT Token
	 * @return expiration date
	 * 
	 */
	public Date extractExpiration(String token) {
		logger.info("inside extractExpiration");
		return extractClaim(token, Claims::getExpiration);
	}
	
	/**
     * Retrieve a specific claim from the JWT token.
     * 
     * @param <T> the type of the claim
     * @param token JWT token
     * @param claimsResolver a function to extract the claim
     * @return the claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        logger.info("inside extractClaim");
        return claimsResolver.apply(claims);
    }
    
    /**
     * Retrieve all claims from the JWT token.
     * 
     * @param token JWT token
     * @return the claims
     */
    private Claims extractAllClaims(String token) {
    	logger.info("inside extractAllClaims");
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    /**
     * Check if the JWT token has expired.
     * 
     * @param token JWT token
     * @return true if the token has expired, false otherwise
     */
    private Boolean isTokenExpired(String token) {
    	logger.info("inside isTokenExpired");
        return extractExpiration(token).before(new Date());
    }
    
    /**
     * Generate a JWT token for the specified user.
     * 
     * @param userDetails the user details
     * @return the JWT token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        logger.info("inside generateToken");
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Create a JWT token with the specified claims and subject.
     * 
     * @param claims the claims
     * @param subject the subject
     * @return the JWT token
     */
    private String createToken(Map<String, Object> claims, String subject) {
    	logger.info("inside createToken");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Validate the JWT token.
     * 
     * @param token JWT token
     * @param userDetails the user details
     * @return true if the token is valid, false otherwise
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        logger.info("inside validateToken");
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
