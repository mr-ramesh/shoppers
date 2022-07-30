package com.shoppers.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppers.security.model.TokenApiResponse;
import com.shoppers.security.util.JwtTokenUtil;

@RestController
@CrossOrigin()
@RequestMapping("/security/token")
public class TokenController {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userService;

	@GetMapping("/getUsername")
	public TokenApiResponse getUsername(@RequestParam String token) {
		TokenApiResponse response = new TokenApiResponse();
		response.setKey("userName");
		response.setValue(jwtTokenUtil.getUsernameFromToken(token));
		return response;
	}

	@GetMapping("/validateToken")
	public TokenApiResponse validateToken(@RequestParam String token) {
		String userName = jwtTokenUtil.getUsernameFromToken(token);
		UserDetails userDetails = userService.loadUserByUsername(userName);

		TokenApiResponse response = new TokenApiResponse();
		response.setKey("tokenStatus");
		try {
			response.setValue(jwtTokenUtil.validateToken(token, userDetails));
		} catch (RuntimeException ex) {
			response.setValue(false);
			response.setErrorMessage(ex.getMessage());
		}
		return response;
	}
}
