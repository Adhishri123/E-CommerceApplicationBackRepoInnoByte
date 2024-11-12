package com.innobyte.services.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innobyte.services.dto.CategoryDto;
import com.innobyte.services.models.Category;
import com.innobyte.services.services.admin.category.CategoryService;

@RestController
@RequestMapping("/admin")
public class AdminCategoryController {

	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/category")
	public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
		Category category = categoryService.createcategory(categoryDto);
		return new ResponseEntity<Category>(category,HttpStatus.CREATED);
	}
	
	@GetMapping("/get_all_category")
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> allcategory = categoryService.getallCategory();
		return new ResponseEntity<List<Category>>(allcategory,HttpStatus.OK);
	}
}
