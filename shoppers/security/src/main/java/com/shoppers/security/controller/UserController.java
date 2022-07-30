package com.shoppers.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppers.security.model.ShopperUser;
import com.shoppers.security.service.impl.UserServiceImpl;
import com.shoppers.security.util.JwtTokenUtil;

@RestController
@CrossOrigin()
@RequestMapping("/security/user")
public class UserController {

	@Autowired
	UserServiceImpl userService;

	@Autowired
	JwtTokenUtil tokenUtil;

	@GetMapping("/getUser")
	public ShopperUser getUser(@RequestParam String userName) throws Exception {
		return userService.getUser(userName);
	}
}
