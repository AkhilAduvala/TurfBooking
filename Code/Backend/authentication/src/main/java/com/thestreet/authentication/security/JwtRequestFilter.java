/**
 * 
 */
package com.thestreet.authentication.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.thestreet.authentication.service.CustomUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

/**
 * @author akhil
 * 
 * JwtRequestFiler filters incoming requests and validates the JWt tokens
 *
 */

@Component
public class JwtRequestFilter extends OncePerRequestFilter{

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private static final Logger logger = LogManager.getLogger(JwtRequestFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.info("inside the doFilterInternal of JwtRequestFilter");
		final String requestTokenHeader = request.getHeader("Authorization");
		
		String username = null;
		String jwtToken = null;
		
		if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtUtil.extractUsername(jwtToken);
			} catch (IllegalArgumentException e) {
				// TODO: handle exception
				logger.error("Unable to get JWT token {}", e);
			}catch (ExpiredJwtException e) {
				// TODO: handle exception
				logger.error("JWT token has expired {}", e);
			}catch (SignatureException e) {
				// TODO: handle exception
				logger.error("JWT token has been tampared with {}", e);
			}
		}else {
			logger.warn("JWT token does not begin with the bearer string");
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			//get all the user details for the provided user name
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			logger.info("User Details recieved from loadUserByUsername {}", userDetails);
			
			if(jwtUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request,response);
	}

}
