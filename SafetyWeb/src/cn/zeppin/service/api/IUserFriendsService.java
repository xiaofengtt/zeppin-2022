package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.UserFriends;

public interface IUserFriendsService {

	public void addFriends(UserFriends friend);

	public boolean checkFriend(int userid, int friend);
	
	public List<UserFriends> getFriends(Map<String,String> map ,int offset,int length);

}
