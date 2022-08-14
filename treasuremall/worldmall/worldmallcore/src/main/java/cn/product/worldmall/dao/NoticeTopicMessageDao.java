package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.NoticeTopicMessage;

public interface NoticeTopicMessageDao extends IDao<NoticeTopicMessage>{
	
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
	public List<NoticeTopicMessage> getListByParams(Map<String, Object> params);
	
}
