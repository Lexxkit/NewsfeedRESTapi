package com.lexxkit.news.service;

import com.lexxkit.news.dto.CreateNewsArticleDto;
import com.lexxkit.news.dto.NewsArticleDto;
import com.lexxkit.news.entity.Category;
import com.lexxkit.news.entity.NewsArticle;
import com.lexxkit.news.exception.CategoryNotFoundException;
import com.lexxkit.news.exception.NewsArticleNotFoundException;
import com.lexxkit.news.mapper.NewsArticleMapper;
import com.lexxkit.news.repository.CategoryRepository;
import com.lexxkit.news.repository.NewsfeedRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsfeedService {

  private final NewsfeedRepository newsfeedRepository;
  private final CategoryRepository categoryRepository;
  private final NewsArticleMapper newsArticleMapper;

  public Page<NewsArticleDto> getNewsfeed(Pageable pageable) {
    return newsfeedRepository.findAll(pageable).map(newsArticleMapper::toNewsArticleDto);
  }

  public NewsArticleDto createNewsArticle(CreateNewsArticleDto createNewsArticleDto) {
    NewsArticle newsArticle = newsArticleMapper.toNewsArticle(createNewsArticleDto);
    Category category = getCategory(createNewsArticleDto);
    newsArticle.setCategory(category);
    newsArticle.setPublishedAt(LocalDate.now());
    return newsArticleMapper.toNewsArticleDto(newsfeedRepository.save(newsArticle));
  }

  public NewsArticleDto updateNewsArticle(long id, CreateNewsArticleDto createNewsArticleDto) {
    NewsArticle oldArticle = newsfeedRepository.findById(id).orElseThrow(
        () -> new NewsArticleNotFoundException("There is no article with id: " + id)
    );
    if (!oldArticle.getCategory().getName().equals(createNewsArticleDto.getName())) {
      Category category = getCategory(createNewsArticleDto);
      oldArticle.setCategory(category);
    }
    oldArticle.setName(createNewsArticleDto.getName());
    oldArticle.setContent(createNewsArticleDto.getContent());
    return newsArticleMapper.toNewsArticleDto(newsfeedRepository.save(oldArticle));
  }

  public void deleteNewsArticle(long id) {
    newsfeedRepository.deleteById(id);
  }

  private Category getCategory(CreateNewsArticleDto createNewsArticleDto) {
    return categoryRepository
        .findByName(createNewsArticleDto.getCategory()).orElseThrow(
            () -> new CategoryNotFoundException(
                "There is no category with name: " + createNewsArticleDto.getCategory()
            ));
  }
}
