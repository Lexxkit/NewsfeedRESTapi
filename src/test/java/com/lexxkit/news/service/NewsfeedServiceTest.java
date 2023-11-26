package com.lexxkit.news.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lexxkit.news.dto.CreateNewsArticleDto;
import com.lexxkit.news.dto.NewsArticleDto;
import com.lexxkit.news.entity.Category;
import com.lexxkit.news.entity.NewsArticle;
import com.lexxkit.news.exception.NewsArticleNotFoundException;
import com.lexxkit.news.mapper.NewsArticleMapper;
import com.lexxkit.news.mapper.NewsArticleMapperImpl;
import com.lexxkit.news.repository.NewsfeedRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class NewsfeedServiceTest {

  @Mock
  private CategoryService categoryService;
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
    Pageable testPageable = PageRequest.of(0, 1);
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
  void shouldUseRepoSaveOnce_whenCreateNewsArticle() {
    //given
    CreateNewsArticleDto testInitDto = new CreateNewsArticleDto();
    testInitDto.setName("Test");
    testInitDto.setContent("Lorem ipsum");
    testInitDto.setCategory("TEST");
    Category testCategory = new Category();
    testCategory.setName("TEST");
    when(categoryService.getCategoryByName(testCategory.getName())).thenReturn(testCategory);
    when(newsfeedRepository.save(any())).thenReturn(new NewsArticle());

    //when
    NewsArticleDto result = out.createNewsArticle(testInitDto);

    //then
    verify(newsfeedRepository, times(1)).save(any());
    assertThat(result).isNotNull();
    assertThat(result).isInstanceOf(NewsArticleDto.class);
  }

  @Test
  void shouldThrowException_whenUpdateNewsArticleWithIdNotFound() {
    //given
    when(newsfeedRepository.findById(anyLong())).thenReturn(Optional.empty());

    //then
    assertThatExceptionOfType(NewsArticleNotFoundException.class)
        .isThrownBy(() -> out.updateNewsArticle(anyLong(), new CreateNewsArticleDto()));
    verify(newsfeedRepository, times(0)).save(any());
  }

  @Test
  void shouldUseRepoSaveOnce_whenUpdateNewsArticle() {
    //given
    CreateNewsArticleDto testInitDto = new CreateNewsArticleDto();
    testInitDto.setName("Test");
    testInitDto.setContent("Lorem ipsum");
    testInitDto.setCategory("TEST");
    Category testCategory = new Category();
    testCategory.setName("TEST");
    NewsArticle existingArticle = new NewsArticle();
    existingArticle.setId(1L);
    existingArticle.setName("Test old");
    existingArticle.setContent("Old content");
    existingArticle.setCategory(testCategory);

    when(newsfeedRepository.findById(anyLong())).thenReturn(Optional.of(existingArticle));
    NewsArticle mockOutput = newsArticleMapper.toNewsArticle(testInitDto);
    when(newsfeedRepository.save(any())).thenReturn(mockOutput);

    //when
    NewsArticleDto result = out.updateNewsArticle(anyLong(), testInitDto);

    //then
    verify(newsfeedRepository, times(1)).save(any());
    assertThat(result).isNotNull();
    assertThat(result).isInstanceOf(NewsArticleDto.class);
  }

  @Test
  void shouldUseRepoDeleteOnce_whenDeleteNewsArticle() {
    //given
    doNothing().when(newsfeedRepository).deleteById(anyLong());

    //when
    out.deleteNewsArticle(anyLong());

    //then
    verify(newsfeedRepository, times(1)).deleteById(anyLong());
  }

  @Test
  void shouldReturnEmptyList_whenFilterNewsWithNullParams() {
    //given
    when(newsfeedRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

    //when
    List<NewsArticleDto> result = out.getFilteredNewsfeed(null, null, null);

    //then
    assertThat(result).isNotNull();
    assertThat(result).isInstanceOf(List.class);
    assertThat(result.size()).isEqualTo(0);
  }
}