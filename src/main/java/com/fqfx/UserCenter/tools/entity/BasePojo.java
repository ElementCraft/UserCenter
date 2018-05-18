package com.fqfx.UserCenter.tools.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 带版本控制和时间戳信息的实体类基类
 *
 * @author noobug.com
 */
@MappedSuperclass
public class BasePojo implements Serializable {

    /**
     * 创建时间
     */
    @Column(name = "gmt_create", nullable = false, updatable = false)
    private ZonedDateTime gmtCreate;

    /**
     * 更新时间
     */
    @Column(name = "gmt_modified", nullable = false)
    private ZonedDateTime gmtModified;

    @Version
    private Integer version;

    @PrePersist
    private void beforeSave() {
        this.gmtCreate = ZonedDateTime.now();
        this.gmtModified = ZonedDateTime.now();
    }

    @PreUpdate
    private void beforeUpdate() {
        this.gmtModified = ZonedDateTime.now();
    }

    public ZonedDateTime getGmtCreate() {
        return gmtCreate;
    }

    public ZonedDateTime getGmtModified() {
        return gmtModified;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
