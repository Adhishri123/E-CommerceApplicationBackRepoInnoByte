package com.innobyte.services.dto;

import java.util.List;

import com.innobyte.services.enums.OrderStatus;
import com.innobyte.services.models.OrderItems;
import com.innobyte.services.models.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
@Data
public class OrderDto {

	private Long id;
	private String orderDescription;
	private Long totalAmount;
	private String address;
	private String payment;
	private OrderStatus orderStatus;
	private String createdAt;
	private String updatedAt;
    private String userName;
	private List<OrderItemsDto> orderItems;
}
