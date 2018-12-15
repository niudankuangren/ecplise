package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tensquare.user.pojo.User;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{
	
	public User findByMobile(String mobile);
	
	
	/**
	* 更新粉丝数
	* @param userid 用户ID
	* @param x 粉丝数
	*/
	@Modifying
	@Query(value="update tb_user u set u.fanscount=u.fanscount+? where u.id=?",nativeQuery=true)
	public void incFanscount(String id,int x);
	
	@Modifying
	@Query(value="update tb_user u set u.followcount=u.followcount+? where u.id=?",nativeQuery=true)
	public void incFollowcount(String id,int x);
}
