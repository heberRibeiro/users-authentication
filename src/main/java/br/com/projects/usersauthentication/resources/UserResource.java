package br.com.projects.usersauthentication.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projects.usersauthentication.domain.User;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		User joao = new User("1", "joao@joao.com", "123");
		User maria = new User("2", "maria@maria.com", "123");
		User ana = new User("3", "ana@ana.com", "123");
		
		List<User> list = new ArrayList<>();
		list.addAll(Arrays.asList(joao, maria, ana));
		
		return ResponseEntity.ok().body(list);		
	}

}
