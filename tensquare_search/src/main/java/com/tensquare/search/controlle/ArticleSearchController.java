package com.tensquare.search.controlle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleSearchService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleSearchController {
	@Autowired
	private ArticleSearchService articleSearchService;

	@RequestMapping(method = RequestMethod.POST)
	public Result save(@RequestBody Article article) {
		articleSearchService.add(article);
		return new Result(true, StatusCode.OK, "操作成功");
	}
	
	@RequestMapping(value="/search/{keywords}/{page}/{size}",method=RequestMethod.GET)
	public Result findByTitleLike(@PathVariable String keywords,@PathVariable int page, @PathVariable int size){
			Page<Article> articlePage =articleSearchService.findByTitleLike(keywords,page,size);
			System.out.println("keywords:"+ keywords);
			return new Result(true, StatusCode.OK, "查询成功",new PageResult<Article>(articlePage.getTotalElements(),articlePage.getContent()));
	
	
      }
	
	
}