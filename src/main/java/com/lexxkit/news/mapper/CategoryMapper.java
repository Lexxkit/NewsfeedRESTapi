package com.lexxkit.news.mapper;

import com.lexxkit.news.dto.CategoryDto;
import com.lexxkit.news.entity.Category;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CategoryMapper {
  CategoryDto toCategoryDto(Category category);

  List<CategoryDto> toCategoryDtoList(List<Category> categoryList);

  Category toCategory(CategoryDto categoryDto);

}
