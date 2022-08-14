package cn.zeppin.product.score.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.NewsPublish;
import cn.zeppin.product.score.vo.back.StatusCountVO;

public interface NewsPublishService extends IService<NewsPublish>{
	
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
	public List<NewsPublish> getListByParams(Map<String, Object> params);
	
	/**
	 * 新增发布新闻
	 * @param newsPublish
	 * @return
	 */
	public void insertNewsPublish(NewsPublish newsPublish);
	
	/**
	 * 删除发布新闻
	 * @param newsPublish
	 * @return
	 */
	public void deleteNewsPublish(String[] uuids, String[] news);
	
	/**
	 * 更新发布新闻状态
	 * @param params
	 * @return
	 */
	public void updateStatus(Map<String,Object> params);
	
	/**
	 * 获取分状态列表
	 * @param params
	 * @return
	 */
	public List<StatusCountVO> getStatusList(Map<String, Object> params);
}
