package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontDeviceToken;
import cn.product.worldmall.entity.NoticeTopic;

public interface FrontDeviceTokenDao extends IDao<FrontDeviceToken>{
	
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<FrontDeviceToken> getListByParams(Map<String, Object> params);
	
	public Integer getLeftCountByParams(Map<String, Object> params);
	
	public List<FrontDeviceToken> getLeftListByParams(Map<String, Object> params);
	
	public FrontDeviceToken getByUniqueToken(String uniqueToken);
	
	public FrontDeviceToken getByDeviceToken(String deviceToken);
	
	public FrontDeviceToken getByFrontUser(String frontUser);
	
	public List<FrontDeviceToken> getFrontUserGroupDfList();
	
	public void updateTopicTask(List<NoticeTopic> insert, List<NoticeTopic> update);
	
	public void bindTopic(List<FrontDeviceToken> list);
	
}
