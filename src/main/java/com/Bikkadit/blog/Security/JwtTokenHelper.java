package com.Bikkadit.blog.Security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class JwtTokenHelper {

	//public static final long jwt_token_validity = 5 * 60 * 60;

	//@Value("${jwt.secret}")
	//private String jwtsecret;

	/**
	 * @author Pallavi Yeola
	 * @apiNote retrive User name From JWT Token
	 * @param token
	 * @return
	 */
	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	/**
	 * @apiNote Retrive Expiration Date From JwT Token
	 * @param token
	 * @return
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);

	}

	/**
	 * @author PAllavi Yeola
	 * @apiNote
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return
	 */
	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * @author Pallavi Yeola
	 * @apiNote To REtrive Any Information From Token We Need Secrete Key
	 * @param token
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SecurityConstant.SECRET).parseClaimsJws(token).getBody();
		// TODO Auto-generated method stub

	}

	/**
	 * @author Pallavi Yeola
	 * @apiNote Check Is Token Expired Or Not
	 * @param token
	 * @return
	 */
	private Boolean IsTokenExpired(String token)

	{
		Date expirationDateFromToken = getExpirationDateFromToken(token);
		return expirationDateFromToken.before(new Date());

	}

	/**
	 * @author Pallavi Yeola
	 * @apiNote Generate Token For User
	 * @param userDetails
	 * @return
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());

	}

	/**
	 * @apiNote While Cerating Token 1.Define Claims Of Token Like
	 *          Issuers,Expiration Subject And IDs 2.Sign The JWT Token with HSS12
	 *          Algoritham And Secret Key 3.According to JWS Comapct Serialization
	 *          compaction of JWT to URL_safe String
	 * @param claims
	 * @param username
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() +SecurityConstant.JWT_TOKEN_VALIDITY * 100))
				.signWith(SignatureAlgorithm.HS512, SecurityConstant.SECRET).compact();
	}
	
	/**
	 *Z@apiNote  Api use To Validate Our Token
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userName = getUserNameFromToken(token);
		return userName.equals(userDetails.getUsername()) && !IsTokenExpired(token);
	}
}
