package com.tensquare.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IdWorker idWorker;
	
	@Autowired
	private RedisTemplate  redisTemplate;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private BCryptPasswordEncoder encoder;
	

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param user
	 */
	public void add(User user) {
		userDao.save(user);
	}
	


	/**
	 * 修改
	 * @param user
	 */
	public void update(User user) {
		userDao.save(user);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		userDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 手机号码
                if (searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))) {
                	predicateList.add(cb.like(root.get("mobile").as(String.class), "%"+(String)searchMap.get("mobile")+"%"));
                }
                // 密码
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // 性别
                if (searchMap.get("sex")!=null && !"".equals(searchMap.get("sex"))) {
                	predicateList.add(cb.like(root.get("sex").as(String.class), "%"+(String)searchMap.get("sex")+"%"));
                }
                // 头像
                if (searchMap.get("avatar")!=null && !"".equals(searchMap.get("avatar"))) {
                	predicateList.add(cb.like(root.get("avatar").as(String.class), "%"+(String)searchMap.get("avatar")+"%"));
                }
                // E-Mail
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                // 兴趣
                if (searchMap.get("interest")!=null && !"".equals(searchMap.get("interest"))) {
                	predicateList.add(cb.like(root.get("interest").as(String.class), "%"+(String)searchMap.get("interest")+"%"));
                }
                // 个性
                if (searchMap.get("personality")!=null && !"".equals(searchMap.get("personality"))) {
                	predicateList.add(cb.like(root.get("personality").as(String.class), "%"+(String)searchMap.get("personality")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	/**
	 * 注册
	 * @param user
	 */
	public void register(User user,String code) {
	
		String redisCode=(String) redisTemplate.opsForValue().get("smscode_"+user.getMobile());
		if(redisCode ==null || "".equals(redisCode)) {
			System.out.println("缓存中的密码是:"+redisCode);
			throw new RuntimeException("短信验证码已过期，请重新申请");
		}
		
		
		if( ! code.equals(redisCode)) {
			System.out.println("缓存中的密码是:"+redisCode);
			System.out.println("缓存中的密码是:"+redisCode);
			throw new RuntimeException("短信验证码有错，请重新输入");
		}
		
		user.setId( idWorker.nextId()+"" );
		user.setFollowcount(0);//关注数
		user.setFanscount(0);//粉丝数
		user.setOnline(0L);//在线时长
		user.setRegdate(new Date());//注册日期
		user.setUpdatedate(new Date());//更新日期
		user.setLastdate(new Date());//最后登陆日期
		String newpassword=encoder.encode(user.getPassword());
		user.setPassword(newpassword);
		
		userDao.save(user);
		
	}
	
	 /**
	  * 发送短信
	  * @param mobile
	  */
	public void sendSms(String mobile) {
		
		//1.产生6位数的短信验证码
		int max=999999;
		int min=100000;		
		Random random=new Random();
		int code=random.nextInt(max);
		if(code<min) {
			code=code+min;
		}
		System.out.println("产生的短信验证码是:"+code);
		
		//2.往缓存中保存验证码，以备后续使用'
		redisTemplate.opsForValue().set("smscode_"+mobile,code+"",5,TimeUnit.MINUTES);
		
		//3.将验证码和手机号发动到rabbitmq中
		Map map=new HashMap();
		map.put("mobile", mobile);
		map.put("code", code+"");
		rabbitTemplate.convertAndSend("sms", map);
		
	}
	
	/**
	* 根据手机号码和密码查询
	* @param loginname
	* @param password
	* @return
	*/
	public User findByMobileAndPassword(String mobile, String password) {
		User user = userDao.findByMobile(mobile);
		
		if (user != null && encoder.matches(password, user.getPassword())) {
			return user;
		} else {
			return null;
		}
	}
	
	/**
	* 更新粉丝数
	* @param x
	*/
	
	public void incFanscount(String userid,int x){
		userDao.incFanscount(userid,x);
	}
	
	
	/**
	* 更新关注数
	* @param x
	*/
	
	public void incFollowcount(String userid,int x){
	userDao.incFollowcount(userid,x);
	}
	
}
