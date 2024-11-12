package com.innobyte.services.services.admin.productadmin;

import java.util.List;
import java.io.IOException;
import com.innobyte.services.dto.ProductDto;
import com.innobyte.services.models.Product;

public interface ProductService {

	public ProductDto addProduct(ProductDto productDto) throws Exception;

//	public List<ProductDto> getAllProductByName(String name);

	public void deleteProductById(Long productId);

	public List<Product> getAllProduct();

	public ProductDto getProductById(Long productId);

	public ProductDto updateProduct(Long id, ProductDto productDto) throws IOException;

	public List<Product> getAllProductForUser();

	public List<Product> searchProductByTitle(String name);

	public List<Product> getAllProductByName(String name);

}
