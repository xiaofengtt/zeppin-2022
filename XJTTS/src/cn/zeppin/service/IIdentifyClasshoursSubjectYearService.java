package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.IdentifyClasshoursSubjectYear;

public interface IIdentifyClasshoursSubjectYearService extends
		IBaseService<IdentifyClasshoursSubjectYear, Integer> {
	/**
	 * 根据条件获取收件箱列表
	 * @param params
	 * @param start
	 * @param limit
	 * @param sort
	 * @return
	 */
	List<IdentifyClasshoursSubjectYear> getListByParams(Map<String, Object> params, int start, int limit, String sort);
	
	/**
	 * 根据条件获取收件箱邮件数
	 * @param params
	 * @return
	 */
	int getListCountByParams(Map<String, Object> params);
}
