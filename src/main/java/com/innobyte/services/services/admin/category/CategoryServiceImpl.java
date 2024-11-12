package com.innobyte.services.services.admin.category;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innobyte.services.dto.CategoryDto;
import com.innobyte.services.models.Category;
import com.innobyte.services.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;

    @Override
    public List<Category> getallCategory() {
    	
    	return categoryRepository.findAll();
    }
	
    @Override
	public Category createcategory(CategoryDto categoryDto) {
		Category category = new Category();
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		return categoryRepository.save(category);
	}

	
}
