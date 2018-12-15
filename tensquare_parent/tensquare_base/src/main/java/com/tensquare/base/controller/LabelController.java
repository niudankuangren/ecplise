package com.tensquare.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;

import entity.Result;
import entity.StatusCode;

@RestController
@CrossOrigin
@RequestMapping("/label")
@RefreshScope
public class LabelController {
	
	@Autowired
	private LabelService labelService;
	@Value("${sms.ip}")
	private String ip;
	/**
	 * 查
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public Result findAll() {
		System.out.println(ip);
		return new Result(true,StatusCode.OK,"查找成功",labelService.findAll());				
	}
	
	/**
	 * 查
	 * @param labelId
	 * @return
	 */
	@RequestMapping(value="/{labelId}",method=RequestMethod.GET)
	public Result findById(@PathVariable String labelId) {
				
		return new Result(true,StatusCode.OK,"查找成功",labelService.findById(labelId));				
	}
	
	/**
	 * 增
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Label label) {
		labelService.add(label);
		return new Result(true,StatusCode.OK,"添加成功");				
	}
	
	/**
	 * 改
	 * @param labelId
	 * @return
	 */
	
	@RequestMapping(value="/{labelId}",method=RequestMethod.PUT)
	public Result update(@PathVariable String labelId ,@RequestBody Label label) {
		label.setId(labelId);
		labelService.update(label);
		return new Result(true,StatusCode.OK,"修改成功");				
	}
	
	
	/**
	 * 删
	 * @param labelId
	 * @return
	 */
	@RequestMapping(value="/{labelId}",method=RequestMethod.DELETE)
	public Result delete(@PathVariable String labelId) {
		labelService.deleteById(labelId);
		return new Result(true,StatusCode.OK,"删除成功");				
	}
	
	
	
	
	
	/**
	 * 查找推荐列表
	 * @return
	 */
	@RequestMapping(value="/toplist",method=RequestMethod.GET)
	public Result toplist() {
				
		return new Result(true,StatusCode.OK,"查找推荐列表成功");				
	}
	
	/**
	 * 查找有效标签
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Result list() {
				
		return new Result(true,StatusCode.OK,"查找有效标签成功");				
	}
	
	
	/**
	 * 根据page和size查找标签分页成功
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result search(@PathVariable int page, @PathVariable int size,@RequestBody Label label) {
		
		return new Result(true,StatusCode.OK,"查找标签分页成功",labelService.search(label,page,size));				
	}
	
	
	/**
	 * 查找标签分页成功
	 * @return
	 */
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public Result search(@RequestBody Label label) {
		
		return new Result(true,StatusCode.OK,"查找标签分页成功",labelService.search(label));				
	}
	
	
}
