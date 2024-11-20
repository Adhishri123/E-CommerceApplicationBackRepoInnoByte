package com.innobyte.services.services.customer.cart;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.innobyte.services.dto.AddProductInCartDto;
import com.innobyte.services.dto.OrderDto;
import com.innobyte.services.dto.PlaceOrderDto;
import com.innobyte.services.models.Order;

public interface CartService {

	public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);

	public OrderDto getCartByUserId(Long userId);

	public OrderDto increaseCartItemQuantity(AddProductInCartDto addProductInCartDto);
	
	public OrderDto decreaseCartItemQuantity(AddProductInCartDto addProductInCartDto);

	public OrderDto placeOrder(PlaceOrderDto placeOrderDto);

	public List<OrderDto> getAllPlacedOrdersForAdmin();

	public OrderDto changeOrderStatus(Long orderId, String status);

	public List<OrderDto> getMyPlacedOrder(Long userId);

}
