package br.com.projects.usersauthentication.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.projects.usersauthentication.domain.User;
import br.com.projects.usersauthentication.repositorys.UserRepository;

@Configuration
public class InstantiationDB implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;

	@Override
	public void run(String... args) throws Exception {

		userRepository.deleteAll();

		User joao = new User(null, "João Silva", 30, "joaosilva", bCrypt.encode("123"));
		User maria = new User(null, "Maria José", 40, "mariajose", bCrypt.encode("123"));
		User ana = new User(null, "Ana Maria", 50, "anamaria", bCrypt.encode("123"));

		userRepository.saveAll(Arrays.asList(joao, maria, ana));

	}

}
