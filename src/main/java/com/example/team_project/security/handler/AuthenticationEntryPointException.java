package com.example.team_project.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  인증 처리 예외 (신원 검증)
 *  RequestCache : 인증 성공 시 이전 요청 정보로 이동하기 위한 캐시 저장
 *  SavedRequest : 사용자 요청 파라미터 및 헤더 정보
 */
@Component
public class AuthenticationEntryPointException implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendRedirect("/login");
    }
}