package com.lexxkit.news.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.lexxkit.news.dto.NewsArticleDto;
import com.lexxkit.news.entity.NewsArticle;
import com.lexxkit.news.mapper.NewsArticleMapper;
import com.lexxkit.news.mapper.NewsArticleMapperImpl;
import com.lexxkit.news.repository.CategoryRepository;
import com.lexxkit.news.repository.NewsfeedRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class NewsfeedServiceTest {

  @Mock
  private CategoryRepository categoryRepository;
  @Mock
  private NewsfeedRepository newsfeedRepository;
  @Spy
  private NewsArticleMapper newsArticleMapper = new NewsArticleMapperImpl();
  @InjectMocks
  private NewsfeedService out;

  @Test
  void shouldReturnPageOfNewsArticleDto_whenGetNewsfeed() {
    //given
    List<NewsArticleDto> testListDto = List.of(new NewsArticleDto());
    List<NewsArticle> testList = List.of(new NewsArticle());
    Pageable testPageable = PageRequest.of(1, 1);
    Page<NewsArticleDto> testPageDto = new PageImpl<>(testListDto, testPageable, testList.size());
    Page<NewsArticle> testPage = new PageImpl<>(testList, testPageable, testList.size());
    when(newsfeedRepository.findAll(testPageable)).thenReturn(testPage);

    //when
    Page<NewsArticleDto> result = out.getNewsfeed(testPageable);

    //then
    assertThat(result).isNotNull();
    assertThat(result).isInstanceOf(Page.class);
    assertThat(result).isEqualTo(testPageDto);
    assertThat(result.stream().count()).isEqualTo(testListDto.size());
    assertThat(result.stream().toList()).isEqualTo(testListDto);
  }

  @Test
  void getFilteredNewsfeed() {
  }

  @Test
  void createNewsArticle() {
  }

  @Test
  void updateNewsArticle() {
  }

  @Test
  void deleteNewsArticle() {
  }
}