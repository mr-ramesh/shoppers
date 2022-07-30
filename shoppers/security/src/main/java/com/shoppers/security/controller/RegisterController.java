package com.shoppers.security.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppers.security.model.RegisterRequest;
import com.shoppers.security.model.ShopperUser;
import com.shoppers.security.service.RegisterService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin()
@RequestMapping("/security/register")
public class RegisterController {

	@Autowired
	UserDetailsService userService;

	@Autowired
	RegisterService registerService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping
	public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) throws Exception {
		UserDetails userDetails = null;
		try {
			userDetails = userService.loadUserByUsername(registerRequest.getUsername());
		} catch (UsernameNotFoundException ex) {
			log.info("User not found in DB, creating new User!");
		}
		if (null == userDetails) {
			ShopperUser sUser = new ShopperUser();
			sUser.setAddress(registerRequest.getAddress());
			sUser.setCreatedOn(new Date());
			sUser.setFirstName(registerRequest.getFirstName());
			sUser.setLastName(registerRequest.getLastName());
			sUser.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
			sUser.setUpdatedOn(new Date());
			sUser.setUsername(registerRequest.getUsername());
			registerService.createUser(sUser);
			return ResponseEntity.ok("User created successfully!");
		} else {
			return new ResponseEntity<String>("Username " + registerRequest.getUsername() + " already exist!",
					HttpStatus.BAD_REQUEST);
		}
	}
}
