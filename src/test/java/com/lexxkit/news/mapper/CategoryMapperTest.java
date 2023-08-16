package com.lexxkit.news.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.lexxkit.news.dto.CategoryDto;
import com.lexxkit.news.entity.Category;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class CategoryMapperTest {

  private final CategoryMapper mapper = Mappers.getMapper(CategoryMapper.class);

  @Test
  void shouldMapCategoryToDto() {
    //given
    Category category = new Category();
    category.setId(1L);
    category.setName("TEST");

    //when
    CategoryDto out = mapper.toCategoryDto(category);

    //then
    assertThat(out).isNotNull();
    assertThat(out.getId()).isEqualTo(category.getId());
    assertThat(out.getName()).isEqualTo(category.getName());
  }

  @Test
  void shouldMapListOfCategoriesToListOfDtos() {
    //given
    Category category = new Category();
    category.setId(1L);
    category.setName("TEST");

    List<Category> categories = List.of(category);

    //when
    List<CategoryDto> out = mapper.toCategoryDtoList(categories);

    //then
    assertThat(out).isNotNull();
    assertThat(out.size()).isEqualTo(categories.size());
    assertThat(out.get(0)).isInstanceOf(CategoryDto.class);
    assertThat(out.get(0).getId()).isEqualTo(category.getId());
    assertThat(out.get(0).getName()).isEqualTo(category.getName());
  }

  @Test
  void shouldMapCategoryDtoToEntity() {
    //given
    CategoryDto categoryDto = new CategoryDto();
    categoryDto.setId(1L);
    categoryDto.setName("TEST");

    //when
    Category out = mapper.toCategory(categoryDto);

    //then
    assertThat(out).isNotNull();
    assertThat(out.getId()).isEqualTo(categoryDto.getId());
    assertThat(out.getName()).isEqualTo(categoryDto.getName());
  }
}
