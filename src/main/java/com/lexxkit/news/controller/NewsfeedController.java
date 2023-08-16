package com.lexxkit.news.controller;

import com.lexxkit.news.dto.CreateNewsArticleDto;
import com.lexxkit.news.dto.NewsArticleDto;
import com.lexxkit.news.service.NewsfeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@Tag(name = "Newsfeed API", description = "Manage Newsfeed")
public class NewsfeedController {

  private final NewsfeedService newsfeedService;

  @Operation(
      summary = "Get a list of all articles",
      description = "Returns newsfeed",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
      }
  )
  @GetMapping
  public List<NewsArticleDto> getNewsArticles() {
    return newsfeedService.getNewsfeed();
  }

  @Operation(
      summary = "Create new article",
      description = "Returns created article",
      responses = {
          @ApiResponse(responseCode = "201", description = "Successfully created"),
          @ApiResponse(responseCode = "400", description = "Bad request - check your request body"),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
      }
  )
  @PostMapping
  public ResponseEntity<NewsArticleDto> createNewsArticle(
      @RequestBody CreateNewsArticleDto createNewsArticleDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(newsfeedService.createNewsArticle(createNewsArticleDto));
  }

  @Operation(
      summary = "Update article",
      description = "Returns article with updated info",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully updated"),
          @ApiResponse(responseCode = "400", description = "Bad request - check your request body"),
          @ApiResponse(responseCode = "404", description = "Not found - check article id or category name"),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
      }
  )
  @PatchMapping("/{id}")
  public NewsArticleDto updateNewsArticle(@PathVariable long id,
      @RequestBody CreateNewsArticleDto createNewsArticleDto) {
    return newsfeedService.updateNewsArticle(id, createNewsArticleDto);
  }

  @Operation(
      summary = "Delete article",
      description = "Delete article by id",
      responses = {
          @ApiResponse(responseCode = "204", description = "Successfully deleted"),
          @ApiResponse(responseCode = "404", description = "Not found - article not found"),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
      }
  )
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteNewsArticle(@PathVariable long id) {
    newsfeedService.deleteNewsArticle(id);
    return ResponseEntity.noContent().build();
  }

}
