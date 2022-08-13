package cn.zeppin.service.api;

import java.util.Map;
import java.util.List;

import cn.zeppin.entity.UserTextbookcapterDegree;

public interface IUserTextbookcapterDegreeService {

	/**
	 * 添加
	 * @author Administrator
	 * @date: 2014年10月23日 下午3:01:21 <br/> 
	 * @param utbDegree
	 */
	public void addUserTextbookcapterDegree(UserTextbookcapterDegree utbDegree);
	
	/**
	 * 计算数量
	 * @author Administrator
	 * @date: 2014年10月23日 下午3:01:32 <br/> 
	 * @param map
	 * @return
	 */
	public int getUserTextbookcapterDegreeCount(Map<String, Object> map);

	/**
	 * 获取列表
	 * @author Administrator
	 * @date: 2014年10月23日 下午3:01:46 <br/> 
	 * @param map
	 * @param sort
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<UserTextbookcapterDegree> getUserTextbookcapterDegreeByMap(Map<String, Object> map, String sort, int offset, int length);
	
	/**
	 * by id 
	 * @author Administrator
	 * @date: 2014年10月23日 下午3:01:57 <br/> 
	 * @param id
	 * @return
	 */
	public UserTextbookcapterDegree getUserTextbookcapterDegreeById(Long id);

}
