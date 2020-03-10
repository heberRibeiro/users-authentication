package br.com.projects.usersauthentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.projects.usersauthentication.security.JWTAuthenticationFilter;
import br.com.projects.usersauthentication.security.JWTBasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService mongoUserDetailsService;

	/*
	 * Tell Spring Security how to handle user authentication.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
		.csrf().disable()
		.authorizeRequests().antMatchers("/users/anamaria").permitAll() // resource don't need authentication
		.anyRequest().authenticated();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
		http.addFilter(new JWTBasicAuthenticationFilter(authenticationManager()));
	}

	/*
	 * Tell Spring where the user data is located  and where to find the information it needs for authentication,
	 * indicating the class capable of performing the search.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(mongoUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
