package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserPaymentOrder;
import cn.product.treasuremall.entity.GoodsIssueSharesnum;
import cn.product.treasuremall.entity.LuckygameGoods;
import cn.product.treasuremall.entity.LuckygameGoodsIssue;
import cn.product.treasuremall.entity.WinningInfo;
import cn.product.treasuremall.entity.WinningInfoReceive;

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
    		, List<LuckygameGoodsIssue> listlgi, List<LuckygameGoodsIssue> listlginew, List<LuckygameGoods> listlg, List<GoodsIssueSharesnum> listgis);
    
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
}
