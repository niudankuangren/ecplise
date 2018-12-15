package com.tensquare.friend.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;

@Service
@Transactional
public class FriendService {

	@Autowired
	private FriendDao friendDao;
	@Autowired
	private NoFriendDao noFriendDao;
	@Autowired
	private UserClient userClient;
	
	
	

	public int addFriend(String userid, String friendid) {
		
		
		//判断是否添加好友,先判断userid以前有没有添加friendid为好友的记录,如果没有执行以下
		//1.如果是,初始化friend的值,待确定islike属性后再往tb_friend中添加一条记录
		//2.接着查询好友friendid是否也添加了userid好友
		//3.如果是,修改双方的islike属性为1,表示互粉;然后增加userid用户的关注数和粉丝数,增加friendid用户的粉丝数
		//4.如果不是，只修改userid的islike属性为0即可;然后增加userid用户的关注数,增加friendid用户的粉丝数

		//判断如果用户已经添加了这个好友，则不进行任何操作,返回0
		if(friendDao.selectCount(userid, friendid)>0){
			return 0;
		}
		
		//向喜欢表中添加记录
		Friend friend=new Friend();
		friend.setUserid(userid);
		friend.setFriendid(friendid);
		friend.setIslike("0");
		friendDao.save(friend);
		
		//判断对方是否喜欢你，如果喜欢，将islike设置为1
		if(friendDao.selectCount( friendid,userid)>0){
			
			friendDao.updateLike(userid,friendid,"1");
			friendDao.updateLike(friendid,userid,"1");	
			userClient.incFollowcount(userid,1);//增加自己的关注数
			userClient.incFanscount(friendid,1);//增加对方的粉丝数
		}
		
		return 1;
		
}
	
	
	/**
	* 向不喜欢列表中添加记录
	* @param userid
	* @param friendid
	*/
	public void addNoFriend(String userid,String friendid){
		NoFriend noFriend=new NoFriend();
		noFriend.setUserid(userid);
		noFriend.setFriendid(friendid);
		noFriendDao.save(noFriend);
	}

	/**
	* 删除好友
	* @param userid
	* @param friendid
	*/

	public void deleteFriend(String userid,String friendid){
		friendDao.deleteFriend(userid, friendid);
		friendDao.updateLike(friendid, userid, "0");
		addNoFriend(userid, friendid);// 向不喜欢表中添加记录
		userClient.incFollowcount(userid,-1);//减少自己的关注数
		userClient.incFanscount(friendid,-1);//减少对方的粉丝数
		
		
	}
	
	
	
}

