package com.lexxkit.news.dto;

import lombok.Data;

@Data
public class CreateNewsArticleDto {
  private String name;
  private String content;
  private String category;
}
