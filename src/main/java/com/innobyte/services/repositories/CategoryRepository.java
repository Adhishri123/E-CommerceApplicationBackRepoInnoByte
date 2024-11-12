package com.innobyte.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobyte.services.models.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
