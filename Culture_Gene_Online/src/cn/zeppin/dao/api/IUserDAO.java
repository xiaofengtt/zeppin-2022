package cn.zeppin.dao.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.User;

public interface IUserDAO extends IBaseDAO<User, Integer> {
	
	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap);
	
	/**
	 * 通过参数取列表
	 */
	public List<User> getListByParams(HashMap<String,String> searchMap, String sorts, Integer offset, Integer length);
}
