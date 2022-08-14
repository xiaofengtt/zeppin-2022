package cn.product.worldmall.dao;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.product.worldmall.controller.base.TransactionException;
import cn.product.worldmall.entity.PayNotifyInfo;

/**
 */
public interface PayNotifyInfoDao extends IDao<PayNotifyInfo>{
	
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
	public List<PayNotifyInfo> getListByParams(Map<String, Object> params);
	
	/**
	 * 
	 * @param map
	 * @param type
	 * @return
	 * @throws ParseException
	 * @throws TransactionException
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public HashMap<String, Object> insertPayNotifyInfo(Map<String, Object> map, String type);
	
	/**
	 * 批处理更新
	 * @param listUpdate
	 */
	public void updateAll(List<PayNotifyInfo> listUpdate);
}
