package com.lexxkit.news.mapper;

import com.lexxkit.news.dto.CreateNewsArticleDto;
import com.lexxkit.news.dto.NewsArticleDto;
import com.lexxkit.news.entity.NewsArticle;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface NewsArticleMapper {

  @Mapping(target = "category", ignore = true)
  NewsArticle toNewsArticle(CreateNewsArticleDto createNewsArticleDto);
  @Mapping(target = "category", source = "category.name")
  NewsArticleDto toNewsArticleDto(NewsArticle newsArticle);

  List<NewsArticleDto> toDtoList(List<NewsArticle> newsArticleList);
}
