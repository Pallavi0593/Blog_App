package com.Bikkadit.blog.Security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

 private static final Logger logger=LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
 
 /**
  * @author Pallavi Yeola
  * @apiNote This method Executed When An Unauthorized User Want to access Application
  */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		logger.error("Unauthorized Error:{}",authException.getMessage());
		
		//response.sendError(HttpServletResponse.SC_UNAUTHORIZED, SecurityConstant.UNAUTHORIZED_MESSAGE);
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		Map<String,Object> body=new HashMap<>();
		body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
		body.put("error",SecurityConstant.UNAUTHORIZED_MESSAGE);
		body.put("mesaage",authException.getMessage());
		body.put("path", request.getServletPath());
		
		ObjectMapper mapper=new ObjectMapper();
		
		mapper.writeValue(response.getOutputStream(),body);
		
	}

}
