package com.innobyte.services.dto;

import lombok.Data;

@Data
public class PlaceOrderDto {

	private Long userId;
	private String address;
	private String orderDescription;
	private String createdAt;
	private String updatedAt;
}
