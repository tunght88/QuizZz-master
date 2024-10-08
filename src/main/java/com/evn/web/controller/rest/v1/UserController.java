package com.evn.web.controller.rest.v1;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evn.web.controller.utils.RestVerifier;
import com.evn.web.model.AuthenticatedUser;
import com.evn.web.model.User;
import com.evn.web.service.UserService;
import com.evn.web.service.usermanagement.RegistrationService;
import com.evn.web.service.usermanagement.UserManagementService;

@RestController
@RequestMapping(UserController.ROOT_MAPPING)
public class UserController {

	public static final String ROOT_MAPPING = "/api/users";
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private UserManagementService userManagementService;

	@Autowired
	private UserService userService;


	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	public ResponseEntity<User> signUp(@Valid User user, BindingResult result) {

		RestVerifier.verifyModelResult(result);
		User newUser = registrationService.startRegistration(user);

		if (registrationService.isRegistrationCompleted(newUser)) {
			return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<User>(newUser, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/{user_id}/continueRegistration", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public ResponseEntity<User> nextRegistrationStep(@PathVariable Long user_id, String token) {
		User user = userService.find(user_id);
		registrationService.continueRegistration(user, token);

		if (registrationService.isRegistrationCompleted(user)) {
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/{user_id}", method = RequestMethod.DELETE)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable Long user_id) {

		userService.delete(user_id);
	}

	
	@RequestMapping(value = "/login")
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.OK)
	public User login(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
		logger.debug("Logged in as " + authenticatedUser.getUsername());
		return authenticatedUser.getUser();
	}
	
	@RequestMapping(value = "/logoutDummy")
	@PreAuthorize("permitAll()")
	@ResponseStatus(HttpStatus.OK)
	public void logout() {
		// Dummy endpoint to point Spring Security to
		logger.debug("Logged out");
	}
	
	@RequestMapping(value = "/forgotPassword")
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public User forgotPassword(String email) {
		User user = userService.findByEmail(email);
		userManagementService.resendPassword(user);
		
		return user;
	}
	

}
