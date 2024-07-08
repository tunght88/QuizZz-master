package com.evn.web.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.evn.web.exceptions.ResourceUnavailableException;
import com.evn.web.exceptions.UnauthorizedActionException;
import com.evn.web.exceptions.UserAlreadyExistsException;
import com.evn.web.model.User;

public interface UserService extends UserDetailsService {
	User saveUser(User user) throws UserAlreadyExistsException;

	User find(Long id) throws ResourceUnavailableException;;

	User findByEmail(String email) throws ResourceUnavailableException;

	User findByUsername(String username) throws ResourceUnavailableException;

	User updatePassword(User user, String password) throws ResourceUnavailableException;

	void delete(Long user_id) throws UnauthorizedActionException, ResourceUnavailableException;

	User setRegistrationCompleted(User user) throws ResourceUnavailableException;

	boolean isRegistrationCompleted(User user) throws ResourceUnavailableException;

}