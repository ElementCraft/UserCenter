package com.fqfx.UserCenter.repository;

import com.fqfx.UserCenter.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRespository extends JpaRepository<Article, Long> {

    List<Article> findAllByFlag(Integer flag);
}
