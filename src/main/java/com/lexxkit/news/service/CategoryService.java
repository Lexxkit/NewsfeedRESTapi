package com.lexxkit.news.service;

import com.lexxkit.news.dto.CategoryDto;
import com.lexxkit.news.entity.Category;
import com.lexxkit.news.exception.CategoryNotFoundException;
import com.lexxkit.news.mapper.CategoryMapper;
import com.lexxkit.news.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  /**
   * Get a list off all categories.
   * @return a list of categories
   */
  public List<CategoryDto> getCategories() {
    return categoryMapper.toCategoryDtoList(categoryRepository.findAll());
  }

  /**
   * Save new {@link com.lexxkit.news.entity.Category} to DB.
   * @param categoryDto {@link CategoryDto} instance
   * @return {@link CategoryDto} with saved info
   */
  public CategoryDto createCategory(CategoryDto categoryDto) {
    return categoryMapper.toCategoryDto(
        categoryRepository.save(
            categoryMapper.toCategory(categoryDto)
        )
    );
  }

  /**
   * Delete category from DB.
   * @param id id number of a category to delete
   * @throws CategoryNotFoundException if category was not found by its id
   */
  public void deleteCategory(long id) {
    categoryRepository.deleteById(id);
  }

  /**
   * Check if DB contains category with specified name or throw a RuntimeException.
   * @param categoryName name of category
   * @return {@link Category} instance
   * @throws CategoryNotFoundException if category was not found by its name
   */
  public Category getCategoryByName(String categoryName) {
    return categoryRepository
        .findByName(categoryName).orElseThrow(
            () -> new CategoryNotFoundException(
                "There is no category with name: " + categoryName
            ));
  }
}
