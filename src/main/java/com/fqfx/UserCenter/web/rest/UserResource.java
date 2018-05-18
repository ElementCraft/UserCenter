package com.fqfx.UserCenter.web.rest;

import com.fqfx.UserCenter.repository.ArticleRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
