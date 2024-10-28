package com.innobyte.services.serviceimpls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innobyte.services.repositories.ProductRepository;
import com.innobyte.services.services.ProductService;

@Service
public class ProductServiceimpl implements ProductService {

	@Autowired
	ProductRepository productrepository;
}
