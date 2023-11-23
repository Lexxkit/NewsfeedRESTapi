package com.lexxkit.news.controller;

import com.lexxkit.news.dto.CategoryDto;
import com.lexxkit.news.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Categories API", description = "Manage news categories")
public class CategoryController {

  private final CategoryService categoryService;

  @Operation(
      summary = "Get a list of all categories",
      description = "Returns all categories",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
      }
  )
  @GetMapping
  public List<CategoryDto> getCategories() {
    return categoryService.getCategories();
  }

  @Operation(
      summary = "Create new category",
      description = "Returns created category id",
      responses = {
          @ApiResponse(responseCode = "201", description = "Successfully created"),
          @ApiResponse(responseCode = "400", description = "Bad request - check your request body"),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
      }
  )
  @PostMapping
  public ResponseEntity<Long> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDto).getId());
  }

  @Operation(
      summary = "Delete category",
      description = "Delete category by id",
      responses = {
          @ApiResponse(responseCode = "204", description = "Successfully deleted"),
          @ApiResponse(responseCode = "404", description = "Not found - category not found"),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
      }
  )
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable long id) {
    categoryService.deleteCategory(id);
    return ResponseEntity.noContent().build();
  }
}
