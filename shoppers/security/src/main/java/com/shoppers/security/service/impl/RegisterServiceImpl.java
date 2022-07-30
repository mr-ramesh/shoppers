package com.shoppers.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppers.security.model.ShopperUser;
import com.shoppers.security.repository.UserRepository;
import com.shoppers.security.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	UserRepository userRepo;

	public void createUser(ShopperUser user) {
		userRepo.save(user);
	}

}
