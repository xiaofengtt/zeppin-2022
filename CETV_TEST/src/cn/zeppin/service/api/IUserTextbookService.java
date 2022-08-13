package cn.zeppin.service.api;

import java.util.Map;

import cn.zeppin.entity.UserTextbook;

public interface IUserTextbookService {
	
	/**
	 * 搜索UserTextbook
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:44:41 <br/> 
	 * @param map
	 * @return
	 */
	public UserTextbook getUserTextbookByMap(Map<String,Object> map);
	
	/**
	 * 添加UserTextbook
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:44:56 <br/> 
	 * @param book
	 */
	public void addUserTextbook(UserTextbook book);
	
	
	/**
	 * 删除
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:45:10 <br/> 
	 * @param book
	 */
	public void deleteUserTextbook(UserTextbook book);
	
}
