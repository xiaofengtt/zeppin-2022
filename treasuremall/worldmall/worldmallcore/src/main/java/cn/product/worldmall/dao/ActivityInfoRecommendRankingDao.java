package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.ActivityInfoRecommendRanking;

public interface ActivityInfoRecommendRankingDao extends IDao<ActivityInfoRecommendRanking> {
	
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
    public List<ActivityInfoRecommendRanking> getListByParams(Map<String, Object> params);
    
    /**
	 * 更新排行榜数据
	 * @param addList
	 * @param editList
	 * @param deleteList
	 * @return
	 */
    public void updateRanking(List<ActivityInfoRecommendRanking> addList, List<ActivityInfoRecommendRanking> editList, 
    		List<ActivityInfoRecommendRanking> deleteList);
    
    /**
     * 通过用户ID获取排行榜数据
     * @param frontUser
     * @return
     */
    public ActivityInfoRecommendRanking getByFrontUser(String frontUser);
}
