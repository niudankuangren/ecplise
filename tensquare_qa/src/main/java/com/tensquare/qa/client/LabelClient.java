package com.tensquare.qa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import entity.Result;

@FeignClient(value="tensquare-base",fallback=LabelClientImpl.class)
public interface LabelClient {
	
	@RequestMapping(value = "/label/{id}", method = RequestMethod.GET)
	public Result findById(@PathVariable("id") String id);
	
	
	
}
