package com.fqfx.UserCenter.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "article")
public class Article {

    @Id
    private Long id;

    private String title;

    private Integer flag;

    private ZonedDateTime releaseTime;

    private String content;
}
