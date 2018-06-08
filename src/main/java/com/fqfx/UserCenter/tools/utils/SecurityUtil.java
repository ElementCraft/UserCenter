package com.fqfx.UserCenter.tools.utils;

import com.fqfx.UserCenter.web.dto.UserInfoDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

/**
 * 安全相关工具类（包括加密和鉴权相关）
 *
 * @author noobug.com
 */
@Component
public class SecurityUtil {

    /**
     * 获取当前登录人信息
     *
     * @return DTO
     */
    public UserInfoDTO getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication != null && authentication.getDetails() instanceof UserInfoDTO) {
            return (UserInfoDTO) authentication.getDetails();
        }

        return null;
    }

    /**
     * 获取当前登录人账号
     *
     * @return 账号
     */
    public String getCurrentUserAccount() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        String account = null;
        if (authentication != null && authentication.getDetails() instanceof UserInfoDTO) {
            UserInfoDTO userInfoDTO = (UserInfoDTO) authentication.getDetails();
            account = userInfoDTO.getAccount();
        }
        return account;
    }

    /**
     * 获取当前登录人ID
     *
     * @return id
     */
    public Long getCurrentUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        Long id = null;
        if (authentication != null && authentication.getDetails() instanceof UserInfoDTO) {
            UserInfoDTO userInfoDTO = (UserInfoDTO) authentication.getDetails();
            id = userInfoDTO.getId();
        }
        return id;
    }

    /**
     * MD5加密(大写）
     *
     * @param s 原字符串
     * @return 加密后字符串
     */
    public String md5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return toHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            ret.append(HEX_DIGITS[(aByte >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[aByte & 0x0f]);
        }
        return ret.toString();
    }
}
