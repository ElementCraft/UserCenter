package com.fqfx.UserCenter.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fqfx.UserCenter.tools.utils.ConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 小王子
 */
@Slf4j
@Component
public class TokenProvider {

    @Autowired
    private ConfigUtil configUtil;

    /**
     * 生成Jwt Token
     *
     * @param subject     用户账号
     * @param credentials 无用
     * @param authorities 权限集合
     * @return 生成的Token
     */
    public String createToken(String subject, String credentials, Collection<? extends GrantedAuthority> authorities) {
        Long expired = configUtil.getLong(JwtConst.EXPIRED_KEY, JwtConst.EXPIRED);
        String secret = configUtil.get(JwtConst.SECRET_KEY, JwtConst.SECRET);
        String issuer = configUtil.get(JwtConst.ISSUER_KEY, JwtConst.ISSUER);

        String auths = Optional.ofNullable(authorities)
                .map(o -> authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(JwtConst.AUTHORITIES_CLAIM_DELIMITER))
                ).orElse("");


        Long tmpTime = System.currentTimeMillis();
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            token = JWT.create()
                    .withSubject(subject)
                    .withIssuer(issuer)
                    .withExpiresAt(new Date(tmpTime + expired))
                    .withClaim(JwtConst.AUTHORITIES_CLAIM_KEY, auths)
                    .withClaim(JwtConst.PASSWORD_CLAIM_KEY, credentials)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            log.error("[JWTToken] 生成失败 !", e);
        }

        return token;
    }

    public Authentication resolveToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            String auths = jwt.getClaim(JwtConst.AUTHORITIES_CLAIM_KEY).asString();
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(auths.split(JwtConst.AUTHORITIES_CLAIM_DELIMITER))
                            .filter(str -> str != null && str.length() > 0)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            String account = jwt.getSubject();
            String md5 = jwt.getClaim(JwtConst.PASSWORD_CLAIM_KEY).asString();

            return new UsernamePasswordAuthenticationToken(account, md5, authorities);
        } catch (JWTDecodeException e) {
            log.error("[JWTToken] 解析失败！", e);
            return null;
        }
    }

    /**
     * 验证Jwt Token
     *
     * @param authToken 要验证的Token
     * @return
     */
    public boolean validateToken(String authToken) {
        String secret = configUtil.get(JwtConst.SECRET_KEY, JwtConst.SECRET);
        String issuer = configUtil.get(JwtConst.ISSUER_KEY, JwtConst.ISSUER);

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();

            DecodedJWT jwt = verifier.verify(authToken);

            return true;
        } catch (UnsupportedEncodingException exception) {
            return false;
        }
    }
}
