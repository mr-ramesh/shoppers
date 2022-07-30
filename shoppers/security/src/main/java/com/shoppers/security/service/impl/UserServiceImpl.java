package com.shoppers.security.service.impl;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shoppers.security.model.ShopperUser;
import com.shoppers.security.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepo;

	public ShopperUser getUser(String username) {
		return userRepo.findByUsername(username);
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ShopperUser sUser = userRepo.findByUsername(username);
		if (sUser == null)
			throw new UsernameNotFoundException(username + " is not found!");
		log.info("Createing userdetails with : {}", sUser.getUsername());
		return new User(sUser.getUsername(), sUser.getPassword(), new HashSet<GrantedAuthority>());
	}
}
