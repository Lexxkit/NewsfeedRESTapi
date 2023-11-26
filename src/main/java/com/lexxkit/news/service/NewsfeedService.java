package com.lexxkit.news.service;

import static com.lexxkit.news.repository.FilterSpecs.byCategoryEquals;
import static com.lexxkit.news.repository.FilterSpecs.byContentLike;
import static com.lexxkit.news.repository.FilterSpecs.byNameLike;

import com.lexxkit.news.dto.CreateNewsArticleDto;
import com.lexxkit.news.dto.NewsArticleDto;
import com.lexxkit.news.entity.Category;
import com.lexxkit.news.entity.NewsArticle;
import com.lexxkit.news.exception.CategoryNotFoundException;
import com.lexxkit.news.exception.NewsArticleNotFoundException;
import com.lexxkit.news.mapper.NewsArticleMapper;
import com.lexxkit.news.repository.NewsfeedRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsfeedService {

  private final NewsfeedRepository newsfeedRepository;
  private final CategoryService categoryService;
  private final NewsArticleMapper newsArticleMapper;

  /**
   *Get all news articles from DB split by pages with desired size.
   * @param pageable contains page size and number of page
   * @return {@link Page} of {@link NewsArticleDto} objects
   */
  public Page<NewsArticleDto> getNewsfeed(Pageable pageable) {
    return newsfeedRepository.findAll(pageable).map(newsArticleMapper::toNewsArticleDto);
  }

  /**
   * Get all articles filtered by category, name, and content.
   * @param category name of category
   * @param name string to search by {@link NewsArticle} name
   * @param content string to search by {@link NewsArticle content
   * @return
   */
  public List<NewsArticleDto> getFilteredNewsfeed(String category, String name, String content) {
    List<NewsArticle> articles = newsfeedRepository.findAll(
        byNameLike(name)
            .and(byContentLike(content))
            .and(byCategoryEquals(category))
    );
    return newsArticleMapper.toDtoList(articles);
  }

  /**
   * Save new {@link NewsArticle} to DB.
   * @param createNewsArticleDto {@link CreateNewsArticleDto} instance
   * @return save {@link NewsArticle} mapped to {@link NewsArticleDto}
   */
  public NewsArticleDto createNewsArticle(CreateNewsArticleDto createNewsArticleDto) {
    NewsArticle newsArticle = newsArticleMapper.toNewsArticle(createNewsArticleDto);
    Category category = categoryService.getCategoryByName(createNewsArticleDto.getCategory());
    newsArticle.setCategory(category);
    newsArticle.setPublishedAt(LocalDate.now());
    return newsArticleMapper.toNewsArticleDto(newsfeedRepository.save(newsArticle));
  }

  /**
   * Update info in an existent {@link NewsArticle}.
   * @param id id number of an article to update
   * @param createNewsArticleDto {@link CreateNewsArticleDto} instance with new info
   * @return {@link NewsArticleDto} with updated info
   */
  public NewsArticleDto updateNewsArticle(long id, CreateNewsArticleDto createNewsArticleDto) {
    NewsArticle oldArticle = newsfeedRepository.findById(id).orElseThrow(
        () -> new NewsArticleNotFoundException("There is no article with id: " + id)
    );
    if (!oldArticle.getCategory().getName().equals(createNewsArticleDto.getCategory())) {
      Category category = categoryService.getCategoryByName(createNewsArticleDto.getCategory());
      oldArticle.setCategory(category);
    }
    oldArticle.setName(createNewsArticleDto.getName());
    oldArticle.setContent(createNewsArticleDto.getContent());
    return newsArticleMapper.toNewsArticleDto(newsfeedRepository.save(oldArticle));
  }

  /**
   * Delete news article from DB
   * @param id id number of an article to delete
   */
  public void deleteNewsArticle(long id) {
    newsfeedRepository.deleteById(id);
  }

  /**
   * Check if DB contains category from {@link CreateNewsArticleDto} or throw a RuntimeException.
   * @param categoryName name of category
   * @return {@link Category} instance
   * @throws CategoryNotFoundException if category was not found by its name
   */
}
