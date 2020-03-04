package br.com.projects.usersauthentication.login.exception;

public class InvalidCredentialException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public InvalidCredentialException(String msg) {
		super(msg);
	}

}
