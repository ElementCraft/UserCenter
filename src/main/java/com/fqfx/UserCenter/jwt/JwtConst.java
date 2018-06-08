package com.fqfx.UserCenter.jwt;

public interface JwtConst {

    /**
     * 数据库无配置时默认使用的秘钥
     */
    String SECRET = "nooblog!Daedalus@130#noobug.com";

    /**
     * 鉴权请求头部前缀
     */
    String BEARER = "Bearer ";

    /**
     * 秘钥数据库配置KEY
     */
    String SECRET_KEY = "jwt.secret";

    /**
     * 过期时间数据库配置KEY
     */
    String EXPIRED_KEY = "jwt.token.expired";

    /**
     * 数据库无配置时，默认使用的过期时间
     */
    Long EXPIRED = 24L * 60L * 60L * 1000L;

    /**
     * JWT包含权限集合信息的KEY
     */
    String AUTHORITIES_CLAIM_KEY = "auths";

    /**
     * JWT包含密码信息的KEY
     */
    String PASSWORD_CLAIM_KEY = "pwd";

    /**
     * 分割权限字符串的逗号
     */
    String AUTHORITIES_CLAIM_DELIMITER = ",";

    String ISSUER_KEY = "jwt.token.issuer";

    String ISSUER = "noobug.org";

    String AUTHENTICATION_HEADER = "Authorization";
}
