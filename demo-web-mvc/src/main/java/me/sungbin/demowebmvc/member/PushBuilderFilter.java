package me.sungbin.demowebmvc.member;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.PushBuilder;
import java.io.IOException;

/**
 * packageName : me.sungbin.demowebmvc.member
 * fileName : PushBuilderFilter
 * author : rovert
 * date : 2022/02/02
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/02       rovert         최초 생성
 */

public class PushBuilderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        PushBuilder pushBuilder = request.newPushBuilder();
        // wrapping
        filterChain.doFilter(servletRequest, servletResponse);

        // TODO: HTML 응답 본문 가져오기
        // TODO: 추가로 가져올 리소스 분석
        // TODO: PushBuilder로 보내기
    }
}
