package com.innobyte.services.services.admin.category;

import java.util.List;

import com.innobyte.services.dto.CategoryDto;
import com.innobyte.services.models.Category;

public interface CategoryService {

	public Category createcategory(CategoryDto categoryDto);

	public List<Category> getallCategory();
}
