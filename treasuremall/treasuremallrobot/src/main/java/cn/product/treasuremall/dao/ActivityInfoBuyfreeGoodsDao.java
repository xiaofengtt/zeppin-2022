package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.ActivityInfoBuyfree;
import cn.product.treasuremall.entity.ActivityInfoBuyfreeGoods;

public interface ActivityInfoBuyfreeGoodsDao extends IDao<ActivityInfoBuyfreeGoods> {
	
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
    public List<ActivityInfoBuyfreeGoods> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
    public List<ActivityInfoBuyfreeGoods> getListByActivityInfo(String activity);
    
    /**
     * 上架操作
     * @param aibfg
     */
    public void updateStatus(ActivityInfoBuyfreeGoods aibfg, ActivityInfoBuyfree aibf);
    
    /**
     * 排序操作
     * @param aibfg
     * @param params
     */
    public void updateSort(ActivityInfoBuyfreeGoods aibfg, Map<String, Object> params);
    
    /**
     * 删除操作
     * @param aibfg
     * @param params
     */
    public void delete(ActivityInfoBuyfreeGoods aibfg, Map<String, Object> params);
    
    /**
     *  删除操作
     * @param aibfg
     * @param list
     */
    public void updateStatus(ActivityInfoBuyfreeGoods aibfg, List<ActivityInfoBuyfree> list);
    
    /**
     *  更新期数操作-避免缓存不清空的问题
     * @param aibfg
     * @param list
     */
    public void updateIssue(ActivityInfoBuyfree aibfg);
}
