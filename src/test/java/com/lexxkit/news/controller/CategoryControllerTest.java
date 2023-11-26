package com.lexxkit.news.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lexxkit.news.entity.Category;
import com.lexxkit.news.mapper.CategoryMapperImpl;
import com.lexxkit.news.repository.CategoryRepository;
import com.lexxkit.news.service.CategoryService;
import java.util.Collections;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private CategoryRepository categoryRepository;
  @SpyBean
  private CategoryService categoryService;
  @SpyBean
  private CategoryMapperImpl categoryMapper;
  @InjectMocks
  private CategoryController categoryController;


  @Test
  void shouldReturnListOfCategoryDtos_whenGetCategories() throws Exception {
    when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/categories"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("[]"));
  }

  @Test
  void shouldFail_whenCreateCategoryNotValid() throws Exception {
    JSONObject testCategoryObject = new JSONObject();
    testCategoryObject.put("name", " ");


    mockMvc.perform(post("/categories")
            .content(testCategoryObject.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldPass_whenCreateCategory() throws Exception {
    JSONObject testCategoryDto = new JSONObject();
    testCategoryDto.put("name", "TEST");

    Category testCategory = new Category();
    testCategory.setId(1L);
    testCategory.setName("TEST");

    when(categoryRepository.save(any())).thenReturn(testCategory);

    mockMvc.perform(post("/categories")
            .content(testCategoryDto.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().json(testCategory.getId().toString()));
  }

  @Test
  void shouldPass_whenDeleteCategory() throws Exception {
    doNothing().when(categoryRepository).deleteById(anyLong());

    mockMvc.perform(delete("/categories/1"))
        .andExpect(status().is2xxSuccessful());
  }
}