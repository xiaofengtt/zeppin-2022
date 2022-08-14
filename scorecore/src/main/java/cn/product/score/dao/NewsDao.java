package cn.product.score.dao;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.News;
import cn.product.score.vo.back.StatusCountVO;

public interface NewsDao extends IDao<News>{
	
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
	public List<News> getListByParams(Map<String, Object> params);
	
	/**
	 * 更新新闻状态
	 * @param params
	 * @return
	 */
	public void updateStatus(Map<String,Object> params);
	
	/**
	 * 获取分状态列表
	 * @return
	 */
	public List<StatusCountVO> getStatusList();
}
