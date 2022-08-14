package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserPaymentOrder;
import cn.product.worldmall.entity.LuckygameGoods;
import cn.product.worldmall.entity.LuckygameGoodsIssue;
import cn.product.worldmall.entity.WinningInfo;
import cn.product.worldmall.entity.WinningInfoReceive;

public interface LuckygameGoodsIssueDao extends IDao<LuckygameGoodsIssue> {
	
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
    public List<LuckygameGoodsIssue> getListByParams(Map<String, Object> params);
    
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
     * @param luckygameGoods
     */
    public void updateGoodsIssue(LuckygameGoods luckygameGoods);
    
    /**
     * 批量更新状态
     * @param params
     */
    public void batchUpdateStatus(Map<String,Object> params);
    
    /**
     * 开奖
     * @param listLuck
     * @param listwin
     * @param listlgi
     * @param listlginew
     * @param listlg
     */
    public void taskLottery(Map<String, FrontUserAccount> mapfua, List<FrontUserPaymentOrder> listLuck, List<WinningInfo> listwin
    		, List<LuckygameGoodsIssue> listlgi);
    
   /**
    * 开奖-战队玩法
    * @param mapfua
    * @param listLuck
    * @param listwin
    * @param insertReceive
    * @param insertHistory
    */
    public void taskLottery(Map<String, FrontUserAccount> mapfua, List<FrontUserPaymentOrder> listLuck, List<WinningInfo> listwin
    		, List<LuckygameGoodsIssue> listlgi, List<WinningInfoReceive> insertReceive, List<FrontUserHistory> insertHistory);
    
    
    /**
     * 获取当前商品正在进行的一期
     * @param luckygameGoods
     * @return
     */
    public LuckygameGoodsIssue getCurrentIssue(String luckygameGoods);
    
    /**
     * 获取非缓存详情
     * @param uuid
     * @return
     */
    public LuckygameGoodsIssue getById(String uuid);
}
