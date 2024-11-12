package com.innobyte.services.controller.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.innobyte.services.dto.ProductDto;
import com.innobyte.services.models.Product;
import com.innobyte.services.services.admin.productadmin.ProductService;
import java.io.IOException;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@PostMapping("/add_products")
	public ResponseEntity<ProductDto> addProducts(@ModelAttribute ProductDto productDto) throws Exception {
		ProductDto productdto = productService.addProduct(productDto);
		return new ResponseEntity<ProductDto>(productdto, HttpStatus.CREATED);
	}

	@GetMapping("/get_all_product")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAllProduct();
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@GetMapping("/search/{name}")
	public ResponseEntity<List<Product>> getAllProductsByNames(@PathVariable("name") String name) {
		List<Product> productdtos = productService.getAllProductByName(name);
		return new ResponseEntity<List<Product>>(productdtos, HttpStatus.OK);
	}

	@GetMapping("/get_product_byid/{productId}")
	public ResponseEntity<ProductDto> getProductsById(@PathVariable("productId") Long productId) {
		ProductDto productdto = productService.getProductById(productId);
		return new ResponseEntity<ProductDto>(productdto, HttpStatus.OK);
	}

	@PutMapping("/update_product/{id}")
	public ResponseEntity<ProductDto> updateProducts(@PathVariable("id") Long id, @ModelAttribute ProductDto productDto)
			throws IOException {
		ProductDto productdtos = productService.updateProduct(id, productDto);
		return new ResponseEntity<ProductDto>(productdtos, HttpStatus.OK);
	}

	@DeleteMapping("/delete_product/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) {
		productService.deleteProductById(productId);

		return new ResponseEntity<String>("Delete Product successfully..!", HttpStatus.OK);
	}
	
	@GetMapping("/get_all_product_foruser")
	public ResponseEntity<List<Product>> getAllProductsForUsers() {
		List<Product> products = productService.getAllProductForUser();
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@GetMapping("/search_foruser/{name}")
	public ResponseEntity<List<Product>> getAllProductsByNameForUsers(@PathVariable("name") String name) {
		List<Product> productdtos = productService.searchProductByTitle(name);
		return new ResponseEntity<List<Product>>(productdtos, HttpStatus.OK);
	}
}
