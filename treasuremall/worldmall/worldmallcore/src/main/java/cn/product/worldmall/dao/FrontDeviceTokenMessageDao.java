package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontDeviceToken;
import cn.product.worldmall.entity.FrontDeviceTokenMessage;

public interface FrontDeviceTokenMessageDao extends IDao<FrontDeviceTokenMessage> {
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
	public List<FrontDeviceTokenMessage> getListByParams(Map<String, Object> params);
	
	
	/**
	 * 批量发送SNS推送
	 * @param list
	 */
	public void sendSNSMessage(List<FrontDeviceTokenMessage> list);
	
	/**
	 * 批量发送SNS推送
	 * @param list
	 */
	public void insertMessage(FrontDeviceToken fdt, FrontDeviceTokenMessage fdtm);
}
