package br.com.projects.usersauthentication.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MongoUserDetail implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String id;
	private String login;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public MongoUserDetail() {

	}

	public MongoUserDetail(String id, String login, String password,
			Collection<? extends GrantedAuthority> authorities) {

		this.id = id;
		this.password = password;
		this.login = login;
		this.authorities = authorities;
	}

	public String getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return login;
	}
	public String getLogin() {
		return login;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
