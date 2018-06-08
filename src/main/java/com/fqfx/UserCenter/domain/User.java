package com.fqfx.UserCenter.domain;


import com.fqfx.UserCenter.tools.entity.BasePojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User extends BasePojo {

    @Id
    private Long id;

    private String account;

    private String password;

    private String nickName;

    @Column(name = "is_deleted")
    private Boolean deleted;
}
