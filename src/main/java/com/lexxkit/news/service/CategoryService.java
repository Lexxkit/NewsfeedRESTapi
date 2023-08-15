package com.lexxkit.news.service;

import com.lexxkit.news.dto.CategoryDto;
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

  public List<CategoryDto> getCategories() {
    return categoryMapper.toCategoryDtoList(categoryRepository.findAll());
  }

  public CategoryDto createCategory(CategoryDto categoryDto) {
    return categoryMapper.toCategoryDto(
        categoryRepository.save(
            categoryMapper.toCategory(categoryDto)
        )
    );
  }

  public void deleteCategory(long id) {
    categoryRepository.delete(categoryRepository.findById(id).orElseThrow(
        () -> new CategoryNotFoundException("There is no category with id: " + id)));
  }
}
