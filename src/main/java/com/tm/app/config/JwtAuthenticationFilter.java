package com.tm.app.config;

import java.io.IOException;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tm.app.entity.User;
import com.tm.app.repo.UserRepository;
import com.tm.app.service.impl.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserRepository userRepository;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Headers", "*");

		jwt = authHeader.substring(7);
		try {
			String jwtToken = jwtService.extractJwtObject(jwt).getUserName();
			if (jwtToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				User userDetails = userRepository.findByUserNameIgnoreCase(jwtToken).orElseThrow();
				if (jwtService.isTokenValid(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, List.of(new SimpleGrantedAuthority(userDetails.getUserRoles().name())));
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
					filterChain.doFilter(request, response);
				} else {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}

	}
}
