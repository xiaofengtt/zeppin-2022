package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.ActivityInfo;

public interface ActivityInfoDao extends IDao<ActivityInfo>{
	
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
	public List<ActivityInfo> getListByParams(Map<String, Object> params);
    
    /**
     * 编辑活动
     * @param ai
     * @param editMap
     */
    public void editBuyfree(ActivityInfo ai, Map<String, Object> editMap);
    
    
    /**
     * 编辑活动
     * @param ai
     * @param editMap
     */
    public void editCheckin(ActivityInfo ai, Map<String, Object> editMap);
    
    
    /**
     * 编辑活动
     * @param ai
     * @param editMap
     */
    public void editScorelottery(ActivityInfo ai, Map<String, Object> editMap);
    
    
    /**
     * 编辑活动
     * @param ai
     * @param editMap
     */
    public void editFirstcharge(ActivityInfo ai, Map<String, Object> editMap);
    
    
    /**
     * 编辑活动
     * @param ai
     * @param editMap
     */
    public void editRecharge(ActivityInfo ai, Map<String, Object> editMap);
}
