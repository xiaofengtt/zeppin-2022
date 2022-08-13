package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.MailInformation;

public interface IMailInformationService extends
		IBaseService<MailInformation, Integer> {
	/**
	 * 根据条件获取收件箱列表
	 * @param params
	 * @param start
	 * @param limit
	 * @param sort
	 * @return
	 */
	List<MailInformation> getListByParams(Map<String, Object> params, int start, int limit, String sort);
	
	/**
	 * 根据条件获取收件箱邮件数
	 * @param params
	 * @return
	 */
	int getListCountByParams(Map<String, Object> params);
	
	public String deleteInfo(UserSession us ,String id,String type);
	
	public String addMail(Map<String, Object> params);
}
