package com.lexxkit.news.service;

import com.lexxkit.news.dto.CreateNewsArticleDto;
import com.lexxkit.news.dto.NewsArticleDto;
import com.lexxkit.news.entity.Category;
import com.lexxkit.news.entity.NewsArticle;
import com.lexxkit.news.exception.CategoryNotFoundException;
import com.lexxkit.news.mapper.NewsArticleMapper;
import com.lexxkit.news.repository.CategoryRepository;
import com.lexxkit.news.repository.NewsfeedRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsfeedService {

  private final NewsfeedRepository newsfeedRepository;
  private final CategoryRepository categoryRepository;
  private final NewsArticleMapper newsArticleMapper;

  public List<NewsArticleDto> getNewsfeed() {
    return newsArticleMapper.toNewsArticleDtoList(newsfeedRepository.findAll());
  }

  public NewsArticleDto createNewsArticle(CreateNewsArticleDto createNewsArticleDto) {
    NewsArticle newsArticle = newsArticleMapper.toNewsArticle(createNewsArticleDto);
    Category category = categoryRepository
        .findByName(createNewsArticleDto.getCategory()).orElseThrow(
            () -> new CategoryNotFoundException(
            "There is no category with name: " + createNewsArticleDto.getCategory()
        ));
    newsArticle.setCategory(category);
    newsArticle.setPublishedAt(LocalDate.now());
    return newsArticleMapper.toNewsArticleDto(newsfeedRepository.save(newsArticle));
  }

  public void deleteNewsArticle(long id) {
    newsfeedRepository.deleteById(id);
  }
}
