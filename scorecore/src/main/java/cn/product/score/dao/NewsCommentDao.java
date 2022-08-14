package cn.product.score.dao;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.NewsComment;

public interface NewsCommentDao extends IDao<NewsComment>{
	
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
	public List<NewsComment> getListByParams(Map<String, Object> params);
}
