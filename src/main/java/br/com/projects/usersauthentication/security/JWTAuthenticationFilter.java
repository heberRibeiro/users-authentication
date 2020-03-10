package br.com.projects.usersauthentication.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Filter that intercepts the login request.
 * The login end point is reserved for spring security.
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter() {

	}

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			MongoUserDetail credential = new ObjectMapper().readValue(request.getInputStream(), MongoUserDetail.class);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					credential.getLogin(), credential.getPassword(), credential.getAuthorities());

			Authentication authResult = authenticationManager.authenticate(authToken);
			return authResult;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		MongoUserDetail user = (MongoUserDetail) authResult.getPrincipal();
		String login = user.getUsername();

		String jwt = JWT.create().withClaim("userLogged", login) // JWT payload
				.sign(Algorithm.HMAC256("ServerSecretKey"));

		Cookie cookie = new Cookie("token", jwt);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(60 * 45); // 45 minutes

		response.addCookie(cookie);
	
		super.successfulAuthentication(request, response, chain, authResult);
	}

}
