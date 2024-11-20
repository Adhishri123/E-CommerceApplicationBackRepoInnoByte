package com.innobyte.services.models;

import com.innobyte.services.dto.ProductDto;
import com.innobyte.services.dto.UserDto;
import com.innobyte.services.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String password;
	private String name;
	private String address;
	private UserRole role;
	
	public UserDto getDto() {
		UserDto userDto = new UserDto();
		userDto.setId(id);
		userDto.setName(name);
		userDto.setEmail(email);
		userDto.setAddress(address);
		
		return userDto;
	}
}
