package com.Bikkadit.blog.Security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticatinFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	/**
	 * @author PAllavi Yeola
	 * @apiNote Call this Method When Api Request Hits
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 1.Get token
		String requestToken = request.getHeader(SecurityConstant.HEADER_STRING);
	

		// We wil get Token in Bearer terfduanmdkjfj123jdbkmdn this format

		System.out.println(requestToken);

		String userName = null;

		String actualToken = null;


			if (requestToken != null && requestToken.startsWith(SecurityConstant.BEARER))
		{
			actualToken = requestToken.substring(SecurityConstant.TOKEN_INDEX);   //token = requestToken.substring(7);
			try {
				userName = this.jwtTokenHelper.getUserNameFromToken(actualToken);
			} catch (IllegalArgumentException e) {
				logger.warn("Unable to get Jwt Token");
			} catch (ExpiredJwtException e) {
				logger.warn("Jwt token has Expired");
			} catch (MalformedJwtException e) {
				logger.warn("Invalid Jwt");
			}

		} else {
			logger.info("Jwt token does not begin with Bearer");
		}
			


		// Once We Get The Token Now We Validate Our Token

		// check username is not null and Securitycontext has no hold of another
		// Authentication

		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

			Boolean validateToken = this.jwtTokenHelper.validateToken(actualToken, userDetails);
			
		
           if (validateToken) {
				// If True Then we Have to Don Athenticacation With Security Context holder
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				logger.info("Invalid Jwt token");
			}
		} else {
			logger.info("Username is null or Context is not null");
		}


		filterChain.doFilter(request, response);
	}

}
