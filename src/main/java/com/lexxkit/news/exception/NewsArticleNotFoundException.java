package com.lexxkit.news.exception;

public class NewsArticleNotFoundException extends RuntimeException {

  public NewsArticleNotFoundException() {
    super();
  }

  public NewsArticleNotFoundException(String message) {
    super(message);
  }
}
