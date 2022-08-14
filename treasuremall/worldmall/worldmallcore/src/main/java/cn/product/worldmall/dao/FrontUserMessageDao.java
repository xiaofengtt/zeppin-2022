/**
 * 
 */
package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserMessage;
import cn.product.worldmall.entity.FrontUserRechargeOrder;
import cn.product.worldmall.entity.FrontUserWithdrawOrder;

/**
 *
 */
public interface FrontUserMessageDao extends IDao<FrontUserMessage> {
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @return
	 */
	public List<FrontUserMessage> getListByParams(Map<String, Object> params);

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 全部已读
	 * @param updateList
	 */
	public void readAll(List<FrontUserMessage> updateList);
	
	/**
	 * 发送消息
	 * @param fum
	 */
	public void sendMessage(FrontUserMessage fum);
	
	/**
	 * 发送消息
	 * @param fum
	 */
	public void sendMessage(List<FrontUserMessage> fumList);
	
	/**
	 * 发送消息(短信)
	 * @param fum
	 * @param temp_id 短信模板信息
	 */
	public void sendMessage(FrontUserMessage fum, String temp_id);
	
	/**
	 * 发送消息(短信)
	 * @param fum
	 * @param temp_id 短信模板信息
	 */
	public void sendMessage(List<FrontUserMessage> fumList, String temp_id);
	

	
	/**
	 * 发送消息(充值)
	 * @param furo
	 */
	public void sendMessage(FrontUserRechargeOrder furo, String sourceId);
	
	/**
	 * 发送消息(提现)
	 * @param fuwo
	 */
	public void sendMessage(FrontUserWithdrawOrder fuwo, String sourceId);
}
