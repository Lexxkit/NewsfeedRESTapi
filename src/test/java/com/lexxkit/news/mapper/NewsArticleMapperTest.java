package com.lexxkit.news.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.lexxkit.news.dto.CreateNewsArticleDto;
import com.lexxkit.news.dto.NewsArticleDto;
import com.lexxkit.news.entity.Category;
import com.lexxkit.news.entity.NewsArticle;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class NewsArticleMapperTest {

  private final NewsArticleMapper mapper = Mappers.getMapper(NewsArticleMapper.class);

  @Test
  void shouldMapNewsArticleToDto() {
    //given
    Category category = new Category();
    category.setId(1L);
    category.setName("TEST");

    NewsArticle newsArticle = new NewsArticle();
    newsArticle.setId(1L);
    newsArticle.setName("Test article");
    newsArticle.setContent("Test content");
    newsArticle.setPublishedAt(LocalDate.now());
    newsArticle.setCategory(category);

    //when
    NewsArticleDto out = mapper.toNewsArticleDto(newsArticle);

    //then
    assertThat(out).isNotNull();
    assertThat(out.getId()).isEqualTo(newsArticle.getId());
    assertThat(out.getName()).isEqualTo(newsArticle.getName());
    assertThat(out.getContent()).isEqualTo(newsArticle.getContent());
    assertThat(out.getPublishedAt()).isEqualTo(newsArticle.getPublishedAt());
    assertThat(out.getCategory()).isEqualTo(category.getName());
  }

  @Test
  void shouldMapCreateNewsArticleDtoToEntity() {
    //given
    CreateNewsArticleDto dto = new CreateNewsArticleDto();
    dto.setName("Test name");
    dto.setContent("Test content");
    dto.setCategory("TEST");

    //when
    NewsArticle out = mapper.toNewsArticle(dto);

    //then
    assertThat(out).isNotNull();
    assertThat(out.getId()).isNull();
    assertThat(out.getName()).isEqualTo(dto.getName());
    assertThat(out.getContent()).isEqualTo(dto.getContent());
    assertThat(out.getCategory()).isNull();
  }
}
