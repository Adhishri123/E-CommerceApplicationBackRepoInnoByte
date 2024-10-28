package com.innobyte.services.serviceimpls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innobyte.services.repositories.OrderRepository;
import com.innobyte.services.services.OrderService;

@Service
public class OrderServiceimpl implements OrderService {

	@Autowired
	OrderRepository orderrepository;
}
