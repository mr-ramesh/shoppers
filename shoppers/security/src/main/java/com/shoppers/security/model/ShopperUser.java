package com.shoppers.security.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopperUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;

	private String firstName;

	private String lastName;

	private String username;

	private String password;

	private String address;

	private Date createdOn;

	private Date updatedOn;
}
