package com.lexxkit.news.service;

import com.lexxkit.news.dto.CategoryDto;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  public Set<CategoryDto> getCategories() {
    return Set.of(new CategoryDto());
  }
}
