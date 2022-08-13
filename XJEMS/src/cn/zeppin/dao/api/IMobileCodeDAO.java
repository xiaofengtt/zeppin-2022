package cn.zeppin.dao.api;

//import java.util.List;
import java.util.List;
import java.util.Map;

import cn.zeppin.entity.MobileCode;

public interface IMobileCodeDAO extends IBaseDAO<MobileCode, Integer> {

	/**
	 * 根据UUID查询手机验证码
	 * @param uuid
	 * @return
	 */
	public MobileCode getRecordByUuid(String uuid);
	
	/**
	 * 根据条件获取手机验证码记录数
	 * @param params
	 * @return
	 */
	public int getMobileCodeCountByParams(Map<String, Object> params);
	
	/**
	 * 根据条件获取手机验证码
	 * @return
	 */
	public List<MobileCode> getMobileCodeByParams(Map<String, Object> params);
}
