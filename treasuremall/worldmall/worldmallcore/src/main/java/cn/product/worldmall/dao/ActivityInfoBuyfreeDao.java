package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.ActivityInfoBuyfree;
import cn.product.worldmall.entity.ActivityInfoBuyfreeGoods;
import cn.product.worldmall.entity.ActivityInfoBuyfreeSharesnum;
import cn.product.worldmall.entity.FrontUserBuyfreeOrder;

public interface ActivityInfoBuyfreeDao extends IDao<ActivityInfoBuyfree> {
	
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
    public List<ActivityInfoBuyfree> getListByParams(Map<String, Object> params);
    
    /**
     * 更新排序
     * @param params
     */
    public void updateSorts(Map<String,Object> params);
    
    /**
     * 更新排序（其他数据）
     * @param params
     */
    public void updateOtherSorts(Map<String,Object> params);
    
    /**
     * 更新goods
     * @param activityInfoBuyfreeGoods
     */
    public void updateGoodsIssue(ActivityInfoBuyfreeGoods activityInfoBuyfreeGoods);
    
    /**
     * 批量更新状态
     * @param params
     */
    public void batchUpdateStatus(Map<String,Object> params);
    
    /**
     *  开奖
     * @param listLuck
     * @param listaibf
     * @param listaibfnew
     * @param listaibfg
     * @param listaibfs
     */
    public void taskLottery(List<FrontUserBuyfreeOrder> listLuck, List<ActivityInfoBuyfree> listaibf, List<ActivityInfoBuyfree> listaibfnew, List<ActivityInfoBuyfreeGoods> listaibfg, List<ActivityInfoBuyfreeSharesnum> listaibfs);
}
