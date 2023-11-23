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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@Tag(name = "Newsfeed API", description = "Manage Newsfeed")
public class NewsfeedController {

  private final NewsfeedService newsfeedService;

  @Operation(
      summary = "Get a list of all articles with desired size and page number",
      description = "Returns desired page of newsfeed",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
      }
  )
  @GetMapping
  public Page<NewsArticleDto> getNewsArticles(
      @RequestParam(defaultValue = "3", required = false) Integer pageSize,
      @RequestParam(defaultValue = "0", required = false) Integer page
  ) {
    return newsfeedService.getNewsfeed(PageRequest.of(page, pageSize));
  }

  @Operation(
      summary = "Get a list of articles filtered by desired criteria",
      description = "Returns newsfeed with filtered articles",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
      }
  )
  @GetMapping("/search")
  public List<NewsArticleDto> getNewsArticlesByCriteria(
      @RequestParam(required = false) String category,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String content
  ) {
    return newsfeedService.getFilteredNewsfeed(category, name, content);
  }

  @Operation(
      summary = "Create new article",
      description = "Returns created article id",
      responses = {
          @ApiResponse(responseCode = "201", description = "Successfully created"),
          @ApiResponse(responseCode = "400", description = "Bad request - check your request body"),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
      }
  )
  @PostMapping
  public ResponseEntity<Long> createNewsArticle(
      @RequestBody CreateNewsArticleDto createNewsArticleDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(newsfeedService.createNewsArticle(createNewsArticleDto).getId());
  }

  @Operation(
      summary = "Update article",
      description = "Patch article with new info",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully updated"),
          @ApiResponse(responseCode = "400", description = "Bad request - check your request body"),
          @ApiResponse(responseCode = "404", description = "Not found - check article id or category name"),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
      }
  )
  @PatchMapping("/{id}")
  public ResponseEntity<Void> updateNewsArticle(@PathVariable long id,
      @RequestBody CreateNewsArticleDto createNewsArticleDto) {
    newsfeedService.updateNewsArticle(id, createNewsArticleDto);
    return ResponseEntity.ok().build();
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
