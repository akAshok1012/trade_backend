package com.tm.app.service.impl;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tm.app.dto.JwtToken;
import com.tm.app.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	@Value("${server.env}")
	private String env;

	private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
	private static final String ROLE_CLAIM = "roleName";

	public JwtToken extractJwtObject(String token) throws JsonProcessingException {
		String subject = extractClaimSubject(token);
		return extractedJwtToken(subject);
	}

	private JwtToken extractedJwtToken(String subject) throws JsonProcessingException, JsonMappingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(subject, JwtToken.class);
	}

	public String extractTenantClaim(String token) {
		Claims claims = extractAllClaims(token);
		return claims.get(ROLE_CLAIM).toString();
	}

	public String extractEnv(String token) throws JsonProcessingException {
		String subject = extractClaimSubject(token);
		return extractedJwtToken(subject).getEnv();
	}

	public <T> String extractClaimSubject(String token) {
		final Claims claims = extractAllClaims(token);
		return claims.getSubject();
	}

	public <T> Date extractClaimExpiration(String token) {
		final Claims claims = extractAllClaims(token);
		return claims.getExpiration();
	}

	public String generateToken(JwtToken jwtToken) throws JsonProcessingException {
		return generateToken(new HashMap<>(), jwtToken);
	}

	public String generateToken(Map<String, Object> extraClaims, JwtToken jwtToken) throws JsonProcessingException {
		String payload = new ObjectMapper().writeValueAsString(jwtToken);
		return Jwts.builder().setClaims(extraClaims).setSubject(payload).setIssuer("auth")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	public boolean isTokenValid(String token, User user) throws JsonMappingException, JsonProcessingException {
		final JwtToken jwtTokenObject = extractJwtObject(token);
		return (jwtTokenObject.getUserName().equals(user.getUsername()))
				&& (user.getUpdatedAt().equals(jwtTokenObject.getUpdatedAt())) && !isTokenExpired(token)
				&& isCurrentEnv(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private boolean isCurrentEnv(String token) throws JsonProcessingException {
		return extractEnv(token).equalsIgnoreCase(env);
	}

	private Date extractExpiration(String token) {
		return extractClaimExpiration(token);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String createToken(String payload, long expiryInMinutes, JWSSigner signer) throws JOSEException {
		ZonedDateTime zdt = LocalDateTime.now().atZone(ZoneOffset.UTC);

		// Prepare JWT with claims set.
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().subject(payload).issuer("auth")
				.expirationTime(Date.from(zdt.plusMinutes(expiryInMinutes).toInstant())).audience("")
				.issueTime(Date.from(zdt.toInstant())).build();

		// JWT prepared and ready to be signed.
		SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

		// Apply the HMAC protection. JWT Signing.

		signedJWT.sign((JWSSigner) getSignInKey());
		return signedJWT.serialize();
	}
}
