package com.tensquare.friend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;


public interface NoFriendDao extends JpaRepository<NoFriend, String>{


	NoFriend findByUseridAndFriendid(String userid, String friendid);
	
	
}
