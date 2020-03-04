package br.com.projects.usersauthentication.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.projects.usersauthentication.domain.User;
import br.com.projects.usersauthentication.services.UserService;

@RestController
@RequestMapping("/api/login")
public class LoginController {

	@Autowired
	private UserService userService;

	@PostMapping
	public void login(@RequestBody CredentialDTO credential, HttpServletResponse response) {

		User user = userService.findByLogin(credential.getLogin());
		if (user == null) {
			throw new RuntimeException("Invalid credentials!!!");
		}
		if (!credential.getPassword().equals(user.getPassword())) {
			throw new RuntimeException("Invalid credentials!!!");
		}

		/*
		 * Create a token JWT with a payload {"userLogged": user login} with user's login data
		 * Server secret key used to sign a token must be in properties or in path variables,
		 * never in application's code.
		 * After create token JWT, is created a cookie with this token and send in response 
		 */
		String jwt = JWT.create()
				.withClaim("userLogged", user.getLogin()) // JWT payload
				.sign(Algorithm.HMAC256("ServerSecretKey"));

		Cookie cookie = new Cookie("token", jwt);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(60 * 240); // 45 minutes

		response.addCookie(cookie);
	}

}
