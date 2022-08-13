package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IUserFriendsDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.UserFriends;

public class UserFriendsDAO extends HibernateTemplateDAO<UserFriends, Integer> implements IUserFriendsDAO {

	@Override
	public boolean checkFriend(int userid, int friend) {

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from UserFriends uf where 1=1 ");
		sb.append(" and uf.userByUser=").append(userid);
		sb.append(" and uf.userByFriend=").append(friend);

		int flag = ((Long) this.getResultByHQL(sb.toString())).intValue();

		if (flag > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public List<UserFriends> getFriends(Map<String, String> map, int offset, int length) {

		StringBuilder sb = new StringBuilder();
		sb.append("from UserFriends uf where 1=1 ");

		if (map.containsKey("userId")) {
			sb.append(" and uf.userByUser=").append(map.get("userId"));
		}

		return this.getByHQL(sb.toString(), offset, length);
	}

}
