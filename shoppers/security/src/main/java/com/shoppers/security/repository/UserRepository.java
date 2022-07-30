package com.shoppers.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppers.security.model.ShopperUser;

@Repository
public interface UserRepository extends JpaRepository<ShopperUser, Long> {

	ShopperUser findByUsername(String userName);

}
