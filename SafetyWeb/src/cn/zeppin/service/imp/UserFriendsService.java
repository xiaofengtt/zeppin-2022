package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IUserFriendsDAO;
import cn.zeppin.entity.UserFriends;
import cn.zeppin.service.api.IUserFriendsService;

public class UserFriendsService implements IUserFriendsService {

	private IUserFriendsDAO userFriendsDAO;

	public IUserFriendsDAO getUserFriendsDAO() {
		return userFriendsDAO;
	}

	public void setUserFriendsDAO(IUserFriendsDAO userFriendsDAO) {
		this.userFriendsDAO = userFriendsDAO;
	}

	@Override
	public void addFriends(UserFriends friend) {
		this.userFriendsDAO.save(friend);
	}

	
	@Override
	public boolean checkFriend(int userid, int friend) {
		return this.userFriendsDAO.checkFriend(userid, friend);
	}

	@Override
	public List<UserFriends> getFriends(Map<String, String> map, int offset, int length) {
		
		return this.userFriendsDAO.getFriends(map, offset, length);
	}

}
