package br.com.projects.usersauthentication.login.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(InvalidCredentialException.class)
	public ResponseEntity<StandardError> invalidCredential(InvalidCredentialException e, HttpServletRequest request) {

		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.UNAUTHORIZED.value(),
				"Invalid Credential!", e.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

}
