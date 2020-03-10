package br.com.projects.usersauthentication.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.util.WebUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTBasicAuthenticationFilter extends BasicAuthenticationFilter {

	
	public JWTBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {


		try {

			Cookie tokenCookie = WebUtils.getCookie(request, "token");
			if (tokenCookie == null) {
				chain.doFilter(request, response);
				return;
			}

			String tokenJWT = tokenCookie.getValue();

			DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("ServerSecretKey")).build().verify(tokenJWT);

			String userLogged = decodedJWT.getClaim("userLogged").asString();
			
			List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
			UsernamePasswordAuthenticationToken authentication = 
					new UsernamePasswordAuthenticationToken(userLogged, null, authorities);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Request authenticated
			chain.doFilter(request, response);

		} catch (JWTVerificationException e) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "Error JWT verification!!!");
		}
	}
	
	
}
