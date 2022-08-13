package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.UserFriends;

public interface IUserFriendsDAO extends IBaseDAO<UserFriends,Integer> {

	public boolean checkFriend(int userid, int friend);
	
	public List<UserFriends> getFriends(Map<String,String> map ,int offset,int length);
	
}
