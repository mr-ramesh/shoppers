package com.shoppers.security.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenApiResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String key;

	private Object value;

	private String errorMessage;
}
