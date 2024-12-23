package com.innobyte.services.dto;

import com.innobyte.services.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {

	private Long id;
	private String email;
	private String name;
	private String address;
	private UserRole userRole;
}
