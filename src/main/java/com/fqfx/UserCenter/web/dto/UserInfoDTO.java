package com.fqfx.UserCenter.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * 用户个人资料DTO
 *
 * @author noobug.com
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoDTO {

    private Long id;

    private String account;

    private String nickName;

    private ZonedDateTime gmtCreate;
}
