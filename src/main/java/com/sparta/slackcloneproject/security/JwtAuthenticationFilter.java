package com.sparta.slackcloneproject.security;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String apiPath = ((HttpServletRequest) request).getServletPath();

        if (apiPath.equals("/api/login")||apiPath.equals("/api/signup")) {
            chain.doFilter(request, response);
        } else {
            // 헤더에서 jwt 토큰 받아옴
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
            if( token == null) {
                throw new JwtException("로그인이 필요합니다.");
            }
            // 유효한 토큰인지 확인
            if (jwtTokenProvider.validateToken(token)) {
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아와서 저장
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // 스레드 경합을 피하려면 아래처럼 비어있는 SecurityContext를 하나 생성해야된다고 한다.
                // SecurityContext context = SecurityContextHolder.createEmptyContext();
                // context.setAuthentication(authentication);
                // SecurityContextHolder.setContext(context);
                chain.doFilter(request, response);
            }
        }
    }
}
