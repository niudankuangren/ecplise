package com.tensquare.qa.client;

import org.springframework.stereotype.Component;

import entity.Result;
import entity.StatusCode;

@Component
public class LabelClientImpl implements LabelClient{

	@Override
	public Result findById(String id) {
		
		 return new Result(false, StatusCode.ERROR,"熔断器启动了");
	}

}
