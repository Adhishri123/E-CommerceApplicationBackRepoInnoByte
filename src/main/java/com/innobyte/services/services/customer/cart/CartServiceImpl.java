package com.innobyte.services.services.customer.cart;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.innobyte.services.dto.AddProductInCartDto;
import com.innobyte.services.dto.OrderDto;
import com.innobyte.services.dto.OrderItemsDto;
import com.innobyte.services.dto.PlaceOrderDto;
import com.innobyte.services.enums.OrderStatus;
import com.innobyte.services.models.Order;
import com.innobyte.services.models.OrderItems;
import com.innobyte.services.models.Product;
import com.innobyte.services.models.User;
import com.innobyte.services.repositories.OrderItemsRepository;
import com.innobyte.services.repositories.OrderRepository;
import com.innobyte.services.repositories.ProductRepository;
import com.innobyte.services.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrderItemsRepository orderItemsRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Override
	public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
		if (activeOrder == null) {
	        // If no active order is found, return an error response
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No active pending order found for user");
	    }
		Optional<OrderItems> optionalOrderItems = orderItemsRepository.findByProductIdAndOrderIdAndUserId(addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());
		if(optionalOrderItems.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already in the cart");
		}
		else {
			Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
			Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());
			if(optionalProduct.isPresent() && optionalUser.isPresent()) {
				OrderItems cart = new OrderItems();
				  cart.setProduct(optionalProduct.get());
				  cart.setPrice(optionalProduct.get().getPrice());
				  cart.setQuantity(1L);
				  cart.setUser(optionalUser.get());
				  cart.setOrder(activeOrder);
				  
				OrderItems updatedCart = orderItemsRepository.save(cart);
				
				activeOrder.setTotalAmount(activeOrder.getTotalAmount()+cart.getPrice());
				activeOrder.getOrderItems().add(cart);
				
				   orderRepository.save(activeOrder);
				   
				return ResponseEntity.status(HttpStatus.CREATED).body(cart.getId());   
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Product not found");
			}
		}
	}

	@Override
	public OrderDto getCartByUserId(Long userId) {
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
		// Check if no active order was found
	    if (activeOrder == null) {
	        // Handle the case where no active order is found, 
	        // for example, return an empty OrderDto or throw a custom exception
	        return new OrderDto();  // or handle appropriately
	    }
		List<OrderItemsDto> orderItemsDtoList = activeOrder.getOrderItems().stream().map(OrderItems::getOrderDto).collect(Collectors.toList());
		OrderDto orderDto = new OrderDto();
		   orderDto.setId(activeOrder.getId());
		   orderDto.setOrderStatus(activeOrder.getOrderStatus());
		   orderDto.setTotalAmount(activeOrder.getTotalAmount());
		   orderDto.setOrderItems(orderItemsDtoList);
		   
		return orderDto;
	}

	@Override
	public OrderDto increaseCartItemQuantity(AddProductInCartDto addProductInCartDto) {
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
		Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
		Optional<OrderItems> optionalOrderItems = orderItemsRepository.findByProductIdAndOrderIdAndUserId(addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());
		if(optionalProduct.isPresent() && optionalOrderItems.isPresent()) {
			OrderItems orderItem = optionalOrderItems.get();
			Product product = optionalProduct.get();
			activeOrder.setTotalAmount(activeOrder.getTotalAmount()+product.getPrice());
			orderItem.setQuantity(orderItem.getQuantity()+1);
			orderItemsRepository.save(orderItem);
			orderRepository.save(activeOrder);
			return activeOrder.getOrderDto();
		}
		return null;
	}
	
	@Override
	public OrderDto decreaseCartItemQuantity(AddProductInCartDto addProductInCartDto) {
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
		Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
		Optional<OrderItems> optionalOrderItems = orderItemsRepository.findByProductIdAndOrderIdAndUserId(addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());
		if(optionalProduct.isPresent() && optionalOrderItems.isPresent()) {
			OrderItems orderItem = optionalOrderItems.get();
			Product product = optionalProduct.get();
			activeOrder.setTotalAmount(activeOrder.getTotalAmount()-product.getPrice());
			orderItem.setQuantity(orderItem.getQuantity()-1);
			orderItemsRepository.save(orderItem);
			orderRepository.save(activeOrder);
			return activeOrder.getOrderDto();
		}
		return null;
	}

	@Override
	public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
		
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
		
		Optional<User> optionalUser = userRepository.findById(placeOrderDto.getUserId());
		if(optionalUser.isPresent()) {
			activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
			activeOrder.setAddress(placeOrderDto.getAddress());
			activeOrder.setCreatedAt(placeOrderDto.getCreatedAt());
			activeOrder.setUpdatedAt(placeOrderDto.getUpdatedAt());
			activeOrder.setOrderStatus(OrderStatus.Placed);
			orderRepository.save(activeOrder);
			
			Order order = new Order();
			   order.setTotalAmount(0L);
			   order.setUser(optionalUser.get());
			   order.setOrderStatus(OrderStatus.Pending);
			 orderRepository.save(order);
			 
			 return activeOrder.getOrderDto();
		}
		return null;
	}


	@Override
	public List<OrderDto> getAllPlacedOrdersForAdmin() {
		List<Order> orderList = orderRepository.findAllByOrderStatusIn(List.of(OrderStatus.Placed,OrderStatus.Shipped,OrderStatus.Delivered));
//		return orderList;
		return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());
	}

	@Override
	public OrderDto changeOrderStatus(Long orderId, String status) {
		Optional<Order> optionalOrder = orderRepository.findById(orderId);
		if(optionalOrder.isPresent()) {
			Order order = optionalOrder.get();
			if(Objects.equals(status, "Shipped")) {
				order.setOrderStatus(OrderStatus.Shipped);
			}else if(Objects.equals(status, "Delivered")) {
				order.setOrderStatus(OrderStatus.Delivered);
			}
			return orderRepository.save(order).getOrderDto();
		}
		return null;
	}

	@Override
	public List<OrderDto> getMyPlacedOrder(Long userId) {
		
		return orderRepository.findByUserIdAndOrderStatusIn(userId, List.of(OrderStatus.Placed,OrderStatus.Shipped,OrderStatus.Delivered))
				.stream().map(Order::getOrderDto).collect(Collectors.toList());
	}

}
