package com.innobyte.services.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
	private String productName;
	private String productDiscription;
	private double productPrice;
	private int stock;
	@Column(name = "created_at", updatable = false)
	private String createdAt;
	@Column(name = "updated_at")
	private String updatedAt;
}
