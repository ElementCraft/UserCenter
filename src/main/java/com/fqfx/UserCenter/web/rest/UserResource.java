package com.fqfx.UserCenter.web.rest;

import com.fqfx.UserCenter.domain.Article;
import com.fqfx.UserCenter.repository.ArticleRespository;
import com.fqfx.UserCenter.web.param.ArticleQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fqfx.UserCenter.constants.ArticleConst.Flag.*;

/**
 * @author 小王子
 */
@RestController
@RequestMapping("/api/user")
public class UserResource {

    @Autowired
    private ArticleRespository articleRespository;

    @GetMapping("/articles")
    public Map test() {
        return Stream.of(OWN, REPRINT, TRANSLATE)
                .map(flag -> Pair.of(flag, articleRespository.findAllByFlag(flag)))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }

    @PostMapping("/articles2")
    public List<List<Article>> test2(@RequestBody List<ArticleQueryParam> params) {

        return params.stream()
                .map(flag -> articleRespository.findAllByFlagAndTitleIsLike(flag.getTypeFlag(), flag.getTitle()))
                .collect(Collectors.toList());
    }
}
