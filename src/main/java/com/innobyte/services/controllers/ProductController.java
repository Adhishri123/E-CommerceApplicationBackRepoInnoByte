package com.innobyte.services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.innobyte.services.services.ProductService;

@RestController
public class ProductController {

	@Autowired
	ProductService productservice;
}
