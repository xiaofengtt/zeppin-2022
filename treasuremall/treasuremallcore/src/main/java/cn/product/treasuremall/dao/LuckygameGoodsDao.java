package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.LuckygameGoods;
import cn.product.treasuremall.entity.LuckygameGoodsIssue;

public interface LuckygameGoodsDao extends IDao<LuckygameGoods> {
	
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
    public List<LuckygameGoods> getListByParams(Map<String, Object> params);
    
    /**
     * 上架操作
     * @param lg
     */
    public void updateStatus(LuckygameGoods lg, LuckygameGoodsIssue lgi);
    
    /**
     * 排序操作
     * @param lg
     * @param params
     */
    public void updateSort(LuckygameGoods lg, Map<String, Object> params);
    
    /**
     * 删除操作
     * @param lg
     * @param params
     */
    public void delete(LuckygameGoods lg, Map<String, Object> params);
    
    /**
     *  删除操作
     * @param lg
     * @param list
     */
    public void updateStatus(LuckygameGoods lg, List<LuckygameGoodsIssue> list);
    
    /**
     *  更新期数操作-避免缓存不清空的问题
     * @param lg
     * @param list
     */
    public void updateIssue(LuckygameGoodsIssue lg);
}
