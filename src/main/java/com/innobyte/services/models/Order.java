package com.innobyte.services.models;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.innobyte.services.dto.OrderDto;
import com.innobyte.services.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String orderDescription;
	private Long totalAmount;
	private String address;
	private String payment;
	private OrderStatus orderStatus;
	@Column(name = "created_at", updatable = false)
	private String createdAt;
	@Column(name = "updated_at")
	private String updatedAt;
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	private List<OrderItems> orderItems;
	
	public OrderDto getOrderDto() {
		OrderDto orderDto = new OrderDto();
		   orderDto.setId(id);
		   orderDto.setOrderDescription(orderDescription);
		   orderDto.setAddress(address);
		   orderDto.setOrderStatus(orderStatus);
		   orderDto.setUserName(user.getName());
		   orderDto.setCreatedAt(createdAt);
		   orderDto.setUpdatedAt(updatedAt);
		 return orderDto;  
	}
}
