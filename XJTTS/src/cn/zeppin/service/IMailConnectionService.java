package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.MailConnection;
import cn.zeppin.entity.MailInformation;

@SuppressWarnings({"rawtypes"})
public interface IMailConnectionService extends
		IBaseService<MailConnection, Integer> {

	/**
	 * 根据条件获取收件箱列表
	 * @param params
	 * @param start
	 * @param limit
	 * @param sort
	 * @return
	 */
	List getListByParams(Map<String, Object> params, int start, int limit, String sort);
	
	/**
	 * 根据条件获取收件箱邮件数
	 * @param params
	 * @return
	 */
	int getListCountByParams(Map<String, Object> params);
	
	/**
	 * 发送站内信
	 * @param mcList 发送的所有数据
	 */
	void send(List<MailConnection> mcList);
	
	/**
	 * 发送站内信
	 * @param params
	 * @return
	 */
	public String addMail(Map<String, Object> params);

}
