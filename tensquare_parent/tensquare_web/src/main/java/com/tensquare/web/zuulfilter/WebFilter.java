package com.tensquare.web.zuulfilter;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;


@Component
public class WebFilter extends ZuulFilter{

	@Override
	public Object run() throws ZuulException {
		System.out.println("zuul过滤器...");
		
		return null;
	}

	
	// 是否执行该过滤器，此处为true，说明需要过滤
	@Override
	public boolean shouldFilter() {
	
		return true;
	}

	
	// 优先级为0，数字越大，优先级越低
	@Override
	public int filterOrder() {
	
		return 0;
	}
	

	// 前置过滤器
	@Override
	public String filterType() {
	
		return "pre";
	}
	
}
