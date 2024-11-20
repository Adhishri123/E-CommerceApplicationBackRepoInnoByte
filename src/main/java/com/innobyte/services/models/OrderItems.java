package com.innobyte.services.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.innobyte.services.dto.OrderItemsDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "order_items")
public class OrderItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long price;
	private Long quantity;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Product product;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;
	
	public OrderItemsDto getOrderDto() {
		OrderItemsDto orderItemsDto = new OrderItemsDto();
		   orderItemsDto.setId(id);
		   orderItemsDto.setPrice(price);
		   orderItemsDto.setProductId(product.getId());
		   orderItemsDto.setQuantity(quantity);
		   orderItemsDto.setUserId(user.getId());
		   orderItemsDto.setProductName(product.getName());
		   orderItemsDto.setReturnedImg(product.getImage());
		return orderItemsDto;   
	}
}
