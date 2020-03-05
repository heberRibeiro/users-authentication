package br.com.projects.usersauthentication.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.util.WebUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

/*
 * We do not need this class anymore, because the spring security provides us the necessary filters
 */

//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		/*
		 * Do not require authentication if the call is from a request from login page.
		 */
		if (httpRequest.getServletPath().startsWith("/api/login")) {
			chain.doFilter(request, response);
			return;

		}

		try {

			Cookie tokenCookie = WebUtils.getCookie(httpRequest, "token");
			if (tokenCookie == null) {
				httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "User without authenticated credential!");
				return;
			}

			String tokenJWT = tokenCookie.getValue();

			DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("ServerSecretKey")).build().verify(tokenJWT);

			String userLogged = decodedJWT.getClaim("userLogged").asString();
			httpRequest.setAttribute("userLogged", userLogged);

			// Request authenticated
			chain.doFilter(request, response);

		} catch (JWTVerificationException e) {
			httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Error JWT verification!!!");
		}
	}

}
