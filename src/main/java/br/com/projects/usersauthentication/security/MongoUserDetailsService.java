package br.com.projects.usersauthentication.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.projects.usersauthentication.domain.User;
import br.com.projects.usersauthentication.services.UserService;

@Service
public class MongoUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

		User user = userService.findByLogin(login);

		if (user == null) {
			throw new UsernameNotFoundException("User not found!");
		}

		List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		return new MongoUserDetail(user.getId(), user.getLogin(), user.getPassword(), authorities);
	}

}
