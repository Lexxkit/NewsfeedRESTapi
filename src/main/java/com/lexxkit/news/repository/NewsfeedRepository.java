package com.lexxkit.news.repository;

import com.lexxkit.news.entity.NewsArticle;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsfeedRepository extends JpaRepository<NewsArticle, Long>,
    JpaSpecificationExecutor<NewsArticle> {

  interface Specs {
    static Specification<NewsArticle> byNameLike(String name) {
      return (root, query, criteriaBuilder) -> {
        if (name == null || name.isBlank()) {
          return criteriaBuilder.conjunction();
        }
        return criteriaBuilder.like(root.get("name"), "%"+name+"%");
      };
    }

    static Specification<NewsArticle> byContentLike(String content) {
      return (root, query, criteriaBuilder) -> {
        if (content == null || content.isBlank()) {
          return criteriaBuilder.conjunction();
        }
        return criteriaBuilder.like(root.get("content"), "%"+content+"%");
      };
    }

    static Specification<NewsArticle> byCategoryEquals(String category) {
      return (root, query, criteriaBuilder) -> {
        if (category == null || category.isBlank()) {
          return criteriaBuilder.conjunction();
        }
        return criteriaBuilder.like(root.get("category").get("name"), category);
      };
    }
  }
}
