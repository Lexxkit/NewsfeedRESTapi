package com.lexxkit.news.repository;

import com.lexxkit.news.entity.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsfeedRepository extends JpaRepository<NewsArticle, Long> {

}
