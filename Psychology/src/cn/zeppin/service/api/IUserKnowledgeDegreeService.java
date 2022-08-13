package cn.zeppin.service.api;

import java.util.Map;
import java.util.List;

import cn.zeppin.entity.UserKnowledgeDegree;

public interface IUserKnowledgeDegreeService {
	
	/**
	 * 添加
	 * @author Administrator
	 * @date: 2014年10月26日 下午5:18:20 <br/> 
	 * @param ukld
	 */
	public void addUserKnowledgeDegree(UserKnowledgeDegree ukld);
	
	/**
	 * 计算count
	 * @author Administrator
	 * @date: 2014年10月23日 下午6:08:06 <br/> 
	 * @param map
	 * @return
	 */
	public int getUserKnowledgeDegreeCount(Map<String,Object> map);
	
	
	/**
	 * 获取
	 * @author Administrator
	 * @date: 2014年10月23日 下午6:08:44 <br/> 
	 * @param map
	 * @param sort
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<UserKnowledgeDegree> getUserKnowledgeDegree(Map<String,Object> map,String sort,int offset,int length);
	
}
