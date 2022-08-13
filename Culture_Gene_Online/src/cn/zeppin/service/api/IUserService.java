package cn.zeppin.service.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.User;

public interface IUserService {
	
	/**
	 * 获取
	 */
	public User getUser(Integer id);
	
	/**
	 * 添加
	 */
	public User addUser(User user);

	/**
	 * 删除
	 */
	public User deleteUser(User user);

	/**
	 * 修改
	 */
	public User updateUser(User user);

	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap);
	
	/**
	 * 获取页面信息
	 */
	public List<User> getListForPage(HashMap<String,String> searchMap, String sort,  Integer offset, Integer pagesize );
	
	/**
	 * 通过参数取列表
	 */
	public List<User> getListByParams(HashMap<String,String> searchMap);
}
