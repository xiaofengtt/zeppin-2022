package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.NoticePublic;

public interface NoticePublicDao extends IDao<NoticePublic>{
	
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
	public List<NoticePublic> getListByParams(Map<String, Object> params);
	
	public void updateTask(List<NoticePublic> update);
}
