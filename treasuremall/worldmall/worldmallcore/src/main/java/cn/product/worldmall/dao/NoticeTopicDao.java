package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.NoticeTopic;

public interface NoticeTopicDao extends IDao<NoticeTopic>{
	
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
	public List<NoticeTopic> getListByParams(Map<String, Object> params);
	
	/**
	 * 批量操作
	 * @param update
	 */
	public void updateTask(List<NoticeTopic> update);
	
	/**
	 * 获取信息
	 * @param topicArn
	 * @return
	 */
	public NoticeTopic getByArn(String topicArn);
}
