package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.MobileCode;

public interface IMobileCodeService {
	
	/**
	 * 增加记录
	 * @param mobileCode
	 */
	public void addMobileCode(MobileCode mobileCode);
	
	/**
	 * 修改记录
	 * @param mobileCode
	 */
	public void updateMobileCode(MobileCode mobileCode);
	
	/**
	 * 删除记录
	 * @param mobileCode
	 */
	public void deleteMobileCode(MobileCode mobileCode);
	
	/**
	 * 根据ID获取记录
	 * @param id
	 * @return
	 */
	public MobileCode getMobileCodeById(Integer id);
	
	/**
	 * 根据条件获取记录数
	 * @param params
	 * @return
	 */
	public int getMobileCodeCount(Map<String, Object> params);

	/**
	 * 根据UUID获取记录
	 * @param uuid
	 * @return
	 */
	public MobileCode getRecordByUuid(String uuid);
	
	/**
	 * 根据条件获取记录
	 * @param params
	 * @return
	 */
	public List<MobileCode> getMobileCodeByParams(Map<String, Object> params);
}
