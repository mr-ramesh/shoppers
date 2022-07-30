package com.shoppers.gateway.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopperUser {


	private String firstName;

	private String lastName;

	private String username;

	private String password;

	private String address;

	private Date createdOn;

	private Date updatedOn;
}
