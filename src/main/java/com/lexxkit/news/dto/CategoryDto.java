package com.lexxkit.news.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDto {
  private long id;
  @NotBlank
  private String name;
}
