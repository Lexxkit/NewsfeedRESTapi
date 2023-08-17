package com.lexxkit.news.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lexxkit.news.dto.CategoryDto;
import com.lexxkit.news.entity.Category;
import com.lexxkit.news.exception.CategoryNotFoundException;
import com.lexxkit.news.mapper.CategoryMapper;
import com.lexxkit.news.mapper.CategoryMapperImpl;
import com.lexxkit.news.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

  @Mock
  private CategoryRepository categoryRepository;
  @Spy
  private CategoryMapper categoryMapper = new CategoryMapperImpl();
  @InjectMocks
  private CategoryService out;

  @Test
  void shouldReturnListOfCategoryDto_whenGetCategories() {
    //given
    Category testCategory = new Category();
    testCategory.setId(1L);
    testCategory.setName("TEST");
    List<Category> expected = List.of(testCategory);
    when(categoryRepository.findAll()).thenReturn(expected);

    //when
    List<CategoryDto> result = out.getCategories();

    //then
    assertThat(result).isNotNull();
    assertThat(result.size()).isEqualTo(expected.size());
    assertThat(result.get(0)).isInstanceOf(CategoryDto.class);
    assertThat(result.get(0).getId()).isEqualTo(testCategory.getId());
    assertThat(result.get(0).getName()).isEqualTo(testCategory.getName());
  }

  @Test
  void shouldUseRepoSaveOnce_whenCreateCategory() {
    //given
    Category testCategory = new Category();
    testCategory.setId(1L);
    CategoryDto testCategoryDto = new CategoryDto();
    testCategoryDto.setId(1L);
    when(categoryRepository.save(testCategory)).thenReturn(testCategory);

    //when
    out.createCategory(testCategoryDto);

    //then
    verify(categoryRepository, times(1)).save(testCategory);
  }

  @Test
  void shouldUseRepoDeleteOnce_whenDeleteCategory() {
    //given
    Category testCategory = new Category();
    testCategory.setId(1L);
    CategoryDto testCategoryDto = new CategoryDto();
    testCategoryDto.setId(1L);
    when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(testCategory));
    doNothing().when(categoryRepository).delete(testCategory);

    //when
    out.deleteCategory(testCategory.getId());

    //then
    verify(categoryRepository, times(1)).delete(testCategory);
  }

  @Test
  void shouldThrowCategoryNotFoundException_whenDeleteCategoryWithWrongId() {
    //given
    Category testCategory = new Category();
    testCategory.setId(1L);
    CategoryDto testCategoryDto = new CategoryDto();
    testCategoryDto.setId(1L);
    when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

    //then
    assertThatExceptionOfType(CategoryNotFoundException.class)
        .isThrownBy(() -> out.deleteCategory(testCategory.getId()));
    verify(categoryRepository, Mockito.never()).delete(testCategory);
  }
}
