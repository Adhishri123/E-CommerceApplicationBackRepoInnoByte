package com.innobyte.services.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
@Data
public class ProductDto {
	private Long id;
	private String name;
	private Long price;
	private String description;
	private Long stock;
	private String createdAt;
	private String updatedAt;
	private byte[] byteImage;
	private Long categoryId;
	private String categoryName;
	private MultipartFile image;
}
