package com.lexxkit.news.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class NewsArticleDto {
  private long id;
  private String name;
  private String content;
  private LocalDate publishedAt;
  private String category;
}
