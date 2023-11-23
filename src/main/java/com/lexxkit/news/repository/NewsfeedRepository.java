package com.lexxkit.news.repository;

import com.lexxkit.news.entity.NewsArticle;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsfeedRepository extends JpaRepository<NewsArticle, Long>,
    JpaSpecificationExecutor<NewsArticle> {
}
