package com.tensquare.qa.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import util.JwtUtil;

@Component
public class JwtFilter extends HandlerInterceptorAdapter {
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("经过了拦截器");
		
		String authHeader=request.getHeader("Authorization");
		if(authHeader !=null || authHeader.startsWith("Bearer ")) {
			
			String token=authHeader.substring(7);//提取token
			Claims claims = jwtUtil.parseJWT(token);
			
			if(claims !=null  ){
			
				if("admin".equals(claims.get("roles"))){//如果是管理员
					request.setAttribute("admin_claims", claims);
					}
				if("user".equals(claims.get("roles"))){//如果是用户
					request.setAttribute("user_claims", claims);
					}
				
			}
			
		}
		

		return true;
	}
}
