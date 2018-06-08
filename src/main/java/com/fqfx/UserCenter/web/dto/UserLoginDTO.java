package com.fqfx.UserCenter.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 用户注册DTO
 *
 * @author noobug.com
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginDTO {
    private String account;

    private String password;
}