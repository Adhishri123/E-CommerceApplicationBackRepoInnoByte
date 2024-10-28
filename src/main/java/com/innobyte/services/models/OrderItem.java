package com.innobyte.services.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "order_items")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderItemId;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	private int quantity;
	private double price;
}