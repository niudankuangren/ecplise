package com.tensquare.spit.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;

import entity.PageResult;
import util.IdWorker;

@Service
@Transactional
public class SpitService {

	@Autowired
	private SpitDao spitDao;
	
	@Autowired
	private IdWorker idWorker;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * 查询全部记录
	 * 
	 * @return
	 */
	public List<Spit> findAll() {
		return spitDao.findAll();
	}

	/**
	 * 根据主键查询实体
	 * 
	 * @param id
	 * @return
	 */
	public Spit findById(String id) {
		Spit spit = spitDao.findById(id).get();
		return spit;
	}
	
	/**
	 * 增加
	 * 
	 * @param spit
	 */
	public void add(Spit spit) {
		spit.set_id(idWorker.nextId() + ""); // 主键值
		spit.setPublishtime(new Date());//发布日期
		spit.setVisits(0);//浏览量
		spit.setShare(0);//分享数
		spit.setThumbup(0);//点赞数
		spit.setComment(0);//回复数
		spit.setState("1");//状态
		
		if(spit.getParentid()!=null && !"".equals(spit.getParentid())) {					
			//回复数+1
			inc(spit.getParentid(),"comment",1);
		}
		System.out.println(spit);
		
		spitDao.save(spit);
	}

	/**
	 * 修改
	 * 
	 * @param spit
	 */
	public void update(Spit spit) {
		spitDao.save(spit);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void deleteById(String id) {
		spitDao.deleteById(id);
	}

	public PageResult<Spit> findByParentId(String parentid, int page, int size) {
	  Pageable pageable=(Pageable) PageRequest.of(page-1, size);
	  Page page1=spitDao.findByParentid(parentid,pageable);
		System.out.println("service");
		System.out.println("个数:"+page1.getTotalElements());
		System.out.println("内容:"+page1.getContent());
		
		return new PageResult(page1.getTotalElements(),page1.getContent());
	}

	
	public boolean updateThumbup(String spitId) {
	
		//低效率
//		Spit spit=spitDao.findById(spitId).get();
//		spit.setThumbup((spit.getThumbup() ==null? 0 :spit.getThumbup())+1);
//		spitDao.save(spit);
		
		//高效率，使用原生态  db.spit.update({_id:"2"},{$inc:{visits:NumberInt(1)}})
		
		if(redisTemplate.opsForValue().get("spit_"+spitId)!=null) {			
			return false;
		}
		
		//点赞
		inc(spitId,"thumbup",1);		
		return true;
	}

	private void inc(String spitId,String column,int inc) {
		Query query =new Query();
		query.addCriteria(Criteria.where("_id").is(spitId));
		Update update =new Update();
		update.inc(column, inc);	
		mongoTemplate.updateFirst(query, update, "spit");
	}
	
	
	
}
