package com.tensquare.friend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tensquare.friend.pojo.Friend;


public interface FriendDao extends JpaRepository<Friend, String>{

	//@Query(value="",nativeQuery=true)
	Friend findByUseridAndFriendid(String userid, String friendid);

	/**
	* 根据用户ID与被关注用户ID查询记录个数
	* @param userid
	* @param friendid
	* @return
	*/
	@Query(value="select count(*) from tb_friend  where userid=? and friendid=?",nativeQuery=true)
	public int selectCount(String userid,String friendid);
	
	
	/**
	* 更新为互相喜欢
	* @param userid
	* @param friendid
	*/
	@Modifying
	@Query(value="update tb_friend set islike=? where userid=? and friendid=?",nativeQuery=true)
	public void updateLike(String userid,String friendid,String islike);
	
	/**
	* 删除喜欢
	* @param userid
	* @param friendid
	*/
	@Modifying
	@Query(value="delete from tb_friend  where userid=? and friendid=?",nativeQuery=true)
	public void deleteFriend(String userid,String friendid);
	
}
