package com.tensquare.spit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;

import entity.Result;
import entity.StatusCode;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

	@Autowired
	private SpitService spitService;
	
	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Result findAll() {
		return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * 
	 * @param id
	 *            
	 * @return
	 */
	@RequestMapping(value = "/{spitId}", method = RequestMethod.GET)
	public Result findOne(@PathVariable String spitId) {
		return new Result(true, StatusCode.OK, "查询成功", spitService.findById(spitId));
	}
	
	/**
	* 增加
	* @param spit
	*/
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Spit spit) {
		
		
		
		spitService.add(spit);
		 return new Result(true, StatusCode.OK, "添加成功");
		
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/{spitId}",method=RequestMethod.PUT)
	public Result update(@RequestBody Spit spit, @PathVariable String spitId) {
		spit.set_id(spitId);
		spitService.update(spit);
		return new Result(true, StatusCode.OK, "修改成功");
	}
	
	/**
	* 删除
	* @param id
	*/
	@RequestMapping(value = "/{spitId}", method = RequestMethod.DELETE)
	public Result deleteById(@PathVariable String spitId) {
		spitService.deleteById(spitId);
		return new Result(true, StatusCode.OK, "删除成功");
	}
	
	@RequestMapping(value = "/comment/{parentid}/{page}/{size}", method = RequestMethod.GET)
	public Result findByParentId(@PathVariable String  parentid,@PathVariable int page, @PathVariable int size) {
		System.out.println(parentid);
		return new Result(true, StatusCode.OK, "查找成功",spitService.findByParentId(parentid,page,size));
		
	}
	
	/**
	* 点赞
	* @param id
	* @return
	*/
	@RequestMapping(value="/thumbup/{spitId}",method=RequestMethod.PUT)
	public Result updateThumbup(@PathVariable String spitId) {
		if (spitService.updateThumbup(spitId))
			return new Result(true, StatusCode.OK, "点赞成功");
		else
			return new Result(true, StatusCode.OK, "你已经点过赞了");
		
	}
}
