package com.fqfx.UserCenter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "system_config")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 配置项key
     */
    @Column(name = "`key`")
    private String key;

    /**
     * 配置项值
     */
    private String data;

    /**
     * 说明
     */
    private String brief;

    @Column(name = "is_deleted")
    private Boolean deleted;
}
