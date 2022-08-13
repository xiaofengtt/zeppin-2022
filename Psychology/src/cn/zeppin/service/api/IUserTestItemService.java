package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.UserTestItem;


public interface IUserTestItemService {

	/**
	 * 添加用户考试记录
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午7:16:01 <br/>
	 * @param ut
	 */
	public void addUserTestItem(UserTestItem uti);
	
	/**
	 * 获取个数
	 * @author Administrator
	 * @date: 2014年10月28日 上午11:49:25 <br/> 
	 * @param map
	 * @return
	 */
	public int getUserTestItemCount(Map<String,Object> map);
	
	/**
	 * 获取列表
	 * @author Administrator
	 * @date: 2014年10月28日 上午11:49:36 <br/> 
	 * @param map
	 * @param sorts
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<UserTestItem> getUserTestItem(Map<String,Object> map,String sorts,int offset,int length);
	
	
	
	
}
