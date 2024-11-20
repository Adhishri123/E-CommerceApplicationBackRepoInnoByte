package com.innobyte.services.controller.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innobyte.services.dto.AddProductInCartDto;
import com.innobyte.services.dto.OrderDto;
import com.innobyte.services.dto.PlaceOrderDto;
import com.innobyte.services.models.Order;
import com.innobyte.services.models.Product;
import com.innobyte.services.services.customer.cart.CartService;

@RestController
@RequestMapping("/orders")
public class CartController {

	@Autowired
	CartService cartService;
	
	@PostMapping("/add_to_cart")
	public ResponseEntity<?> addProductsToCart(@RequestBody AddProductInCartDto addProductInCartDto) {
		return cartService.addProductToCart(addProductInCartDto);
	}
	
	@GetMapping("/get_in_cart/{userId}")
	public ResponseEntity<?> getProductsInCart(@PathVariable Long userId) {
		OrderDto orderDto = cartService.getCartByUserId(userId);
		return ResponseEntity.status(HttpStatus.OK).body(orderDto);
	} 
	
	@PostMapping("/increase_cartitem")
	public ResponseEntity<OrderDto> increaseProductQuantity(@RequestBody AddProductInCartDto addProductInCartDto) {
		OrderDto orderDto = cartService.increaseCartItemQuantity(addProductInCartDto);
		return new ResponseEntity<OrderDto>(orderDto,HttpStatus.CREATED);
	}
	
	@PostMapping("/decrease_cartitem")
	public ResponseEntity<OrderDto> decreaseProductQuantity(@RequestBody AddProductInCartDto addProductInCartDto) {
		OrderDto orderDto = cartService.decreaseCartItemQuantity(addProductInCartDto);
		return new ResponseEntity<OrderDto>(orderDto,HttpStatus.CREATED);
	}
	
	@PostMapping("/place_order")
	public ResponseEntity<OrderDto> placeOrders(@RequestBody PlaceOrderDto placeOrderDto) {
		OrderDto orderDto = cartService.placeOrder(placeOrderDto);
		return new ResponseEntity<OrderDto>(orderDto,HttpStatus.CREATED);
	}
	
	@GetMapping("/place_order_foradmin")
	public ResponseEntity<List<OrderDto>> getAllPlaceOrders() {
		List<OrderDto> orderDtoList = cartService.getAllPlacedOrdersForAdmin();
		return new ResponseEntity<List<OrderDto>>(orderDtoList,HttpStatus.OK);
	}
	
	@GetMapping("/change_order_status/{orderId}/{status}")
	public ResponseEntity<?> changeOrderStatus(@PathVariable Long orderId,@PathVariable String status) {
		OrderDto orderDto = cartService.changeOrderStatus(orderId,status);
		if(orderDto == null) {
			return new ResponseEntity<>("Something went wrong!",HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.OK).body(orderDto);
	}
	
	@GetMapping("/get_myOrders/{userId}")
	public ResponseEntity<List<OrderDto>> getMyPlacedOrders(@PathVariable Long userId) {
		return ResponseEntity.ok(cartService.getMyPlacedOrder(userId));
	}
}
