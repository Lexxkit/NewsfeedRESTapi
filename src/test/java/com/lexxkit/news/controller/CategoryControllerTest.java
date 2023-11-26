package com.lexxkit.news.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lexxkit.news.mapper.CategoryMapperImpl;
import com.lexxkit.news.repository.CategoryRepository;
import com.lexxkit.news.service.CategoryService;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
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

  @BeforeEach
  void init() {

  }

  @Test
  void shouldReturnListOfCategoryDtos_whenGetCategories() throws Exception {
    when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/categories"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("[]"));
  }
}