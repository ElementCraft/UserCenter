package com.fqfx.UserCenter.jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fqfx.UserCenter.domain.User;
import com.fqfx.UserCenter.repository.UserRepository;
import com.fqfx.UserCenter.web.dto.UserInfoDTO;
import com.fqfx.UserCenter.web.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author 小王子
 */
@Component
public class JwtAuthFilter extends GenericFilterBean {

    private final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        try {

            boolean needAuth = needAuth(httpServletRequest);

            String jwt = resolveToken(httpServletRequest);
            if (needAuth) {
                if (StringUtils.hasText(jwt)) {
                    if (this.tokenProvider.validateToken(jwt)) {
                        Authentication authentication = this.tokenProvider.resolveToken(jwt);

                        // 验证账号密码
                        Optional<User> user = userRepository.findByAccountAndPasswordAndDeleted(
                                authentication.getPrincipal().toString(), authentication.getCredentials().toString(), Boolean.FALSE);

                        if (!user.isPresent()) {
                            log.warn("[JWTToken] Token无效：{}", "Token携带的用户信息无效");
                            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            return;
                        }

                        UserInfoDTO userInfoDTO = userMapper.toDto(user.get());

                        ((UsernamePasswordAuthenticationToken) authentication).setDetails(userInfoDTO);

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } else {
                    log.warn("[JWTToken] {}", "需要鉴权");
                    ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (TokenExpiredException e) {
            log.warn("[JWTToken] Token过期：{}", e.getMessage());
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private boolean needAuth(HttpServletRequest request) {
        String url = request.getRequestURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        String[] patterns = {
                "/api/user/login",
                "/api/user/articles",
                "/api/nav/all",
        };

        for (String pattern : patterns) {
            if (pathMatcher.match(pattern, url)) {
                return false;
            }
        }

        return true;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtConst.AUTHENTICATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtConst.BEARER)) {
            String jwt = bearerToken.substring(JwtConst.BEARER.length(), bearerToken.length());
            return jwt;
        }
        return null;
    }
}

