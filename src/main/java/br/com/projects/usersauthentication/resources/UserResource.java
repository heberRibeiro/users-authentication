package br.com.projects.usersauthentication.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projects.usersauthentication.domain.User;
import br.com.projects.usersauthentication.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		List<User> list = userService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{userLogin}")
	public ResponseEntity<User> findUser(@PathVariable("userLogin") String userLogin/*,
			@RequestAttribute("userLogged") String userLogged*/) {

//		if (!userLogin.equals(userLogged)) {
//			throw new InvalidCredentialException("Logged user does not have permission for this resource!");
//		}
		User user = userService.findByLogin(userLogin);

		return ResponseEntity.ok().body(user);
	}

}
