package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.IdentifyClasshoursSubject;

public interface IIdentifyClasshoursSubjectDao extends IBaseDao<IdentifyClasshoursSubject, Integer> {

	/**
	 * 根据条件获取收件箱列表
	 * @param params
	 * @param start
	 * @param limit
	 * @param sort
	 * @return
	 */
	List<IdentifyClasshoursSubject> getListByParams(Map<String, Object> params, int start, int limit, String sort);
	
	/**
	 * 根据条件获取收件箱邮件数
	 * @param params
	 * @return
	 */
	int getListCountByParams(Map<String, Object> params);
}
