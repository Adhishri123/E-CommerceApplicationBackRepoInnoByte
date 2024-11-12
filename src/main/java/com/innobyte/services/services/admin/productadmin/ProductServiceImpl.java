package com.innobyte.services.services.admin.productadmin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.innobyte.services.dto.ProductDto;
import com.innobyte.services.models.Category;
import com.innobyte.services.models.Product;
import com.innobyte.services.repositories.CategoryRepository;
import com.innobyte.services.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public ProductDto addProduct(ProductDto productDto) throws Exception {
		Product product = new Product();
		product.setName(productDto.getName());
		product.setPrice(productDto.getPrice());
		product.setDescription(productDto.getDescription());
		product.setStock(productDto.getStock());
		product.setCreatedAt(productDto.getCreatedAt());
		product.setUpdatedAt(productDto.getUpdatedAt());
		product.setImage(productDto.getImage().getBytes());

		Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();

		product.setCategory(category);
		return productRepository.save(product).getDto();
	}

	@Override
	public List<Product> getAllProductByName(String name) {
		List<Product> products = productRepository.findAllByNameContaining(name);
		return products;
	}

	@Override
	public void deleteProductById(Long productId) {

		productRepository.deleteById(productId);

	}

	@Override
	public List<Product> getAllProduct() {
		List<Product> allproduct = productRepository.findAll();
		return allproduct;
	}

	@Override
	public ProductDto getProductById(Long productId) {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if (optionalProduct.isPresent()) {
			return optionalProduct.get().getDto();
		} else {
			return null;
		}
	}

	@Override
	public ProductDto updateProduct(Long id, ProductDto productDto) throws IOException {
		Optional<Product> optionalProduct = productRepository.findById(id);
		Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
		if (optionalProduct.isPresent() && optionalCategory.isPresent()) {
			Product product = optionalProduct.get();
			product.setName(productDto.getName());
			product.setPrice(productDto.getPrice());
			product.setDescription(product.getDescription());
			product.setStock(productDto.getStock());
			product.setCreatedAt(productDto.getCreatedAt());
			product.setUpdatedAt(productDto.getUpdatedAt());
			product.setCategory(optionalCategory.get());
			if (productDto.getImage() != null) {
				product.setImage(productDto.getImage().getBytes());
			}
			return productRepository.save(product).getDto();
		} else {
			return null;
		}
	}

	@Override
	public List<Product> getAllProductForUser() {
		List<Product> allproduct = productRepository.findAll();
		return allproduct;
	}

	@Override
	public List<Product> searchProductByTitle(String name) {
		List<Product> products = productRepository.findAllByNameContaining(name);
		return products;
	}

}