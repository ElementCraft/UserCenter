package com.fqfx.UserCenter.web.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小王子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleQueryParam {

    private String title;

    private Integer typeFlag;

    public static ArticleQueryParam of(String title, Integer typeFlag){
        return new ArticleQueryParam(title, typeFlag);
    }
}
