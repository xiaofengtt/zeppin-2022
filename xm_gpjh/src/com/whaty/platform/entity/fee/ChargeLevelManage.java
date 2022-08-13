/**
 * 
 */
package com.whaty.platform.entity.fee;

import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
public interface ChargeLevelManage {

	/**
	 * 得到某个用户的收费标准
	 * 
	 * @param userId
	 * @return
	 */
	public Map getChargeLevel(String userId);

	/**
	 * 为某个用户设置收费标准
	 * 
	 * @param userId
	 * @param chargeLevel
	 * @return
	 */
	public void setChargeLevel(String userId, Map chargeLevel)
			throws PlatformException;

	/**
	 * 为某个站点,专业,层次,年级的用户批量设置收费标准
	 * 
	 * @param siteId
	 * @param majorId
	 * @param edutypeId
	 * @param gradeId
	 * @param chargeLevel
	 */
	public void setChargeLevel(String siteId, String majorId, String edutypeId,
			String gradeId, Map chargeLevel) throws PlatformException;

	public void setChargeLevel(String[] siteId, String[] majorId,
			String[] edutypeId, String[] gradeId, Map chargeLevel)
			throws PlatformException;

	public void setOtherChargeLevel(String[] siteId, String[] majorId,
			String[] edutypeId, String[] gradeId, String otherFeeTypeId,
			Map chargeLevel) throws PlatformException;

	public List getChargeLevelByType(Page page, String regNo, String name,
			String siteId, String majorId, String edutypeId, String gradeId,
			String type);

	/**
	 * 获取符合条件的ChargeLevel列表
	 * 
	 * @param page
	 * @param regNo
	 * @param name
	 * @param siteId
	 * @param majorId
	 * @param edutypeId
	 * @param gradeId
	 * @return
	 */
	public List getChargeLevels(Page page, String regNo, String name,
			String siteId, String majorId, String edutypeId, String gradeId);

	/**
	 * 获取符合条件的ChargeLevel数
	 * 
	 * @param regNo
	 * @param name
	 * @param siteId
	 * @param majorId
	 * @param edutypeId
	 * @param gradeId
	 * @return
	 */
	public int getChargeLevelsNum(String regNo, String name, String siteId,
			String majorId, String edutypeId, String gradeId);

	public int getChargeLevelByTypeNum(String regNo, String name,
			String siteId, String majorId, String edutypeId, String gradeId,
			String type);

	public String getUserChargeLevel(String userId, String type);
	
	/**
	 * 获取符合条件的报名学生ChargeLevel列表
	 * 
	 * @param page
	 * @param regNo
	 * @param name
	 * @param siteId
	 * @param majorId
	 * @param edutypeId
	 * @param gradeId
	 * @return
	 */
	public List getRecruitChargeLevels(Page page, String regNo, String name,
			String siteId, String majorId, String edutypeId, String gradeId);

	/**
	 * 获取符合条件的报名学生ChargeLevel数
	 * 
	 * @param regNo
	 * @param name
	 * @param siteId
	 * @param majorId
	 * @param edutypeId
	 * @param gradeId
	 * @return
	 */
	public int getRecruitChargeLevelsNum(String regNo, String name, String siteId,
			String majorId, String edutypeId, String gradeId);
	
	public String getRecruitUserChargeLevel(String userId, String type);
	
	/**
	 * 为某个报名学生设置收费标准
	 * 
	 * @param userId
	 * @param chargeLevel
	 * @return
	 */
	public void setRecruitChargeLevel(String userId, Map chargeLevel)
			throws PlatformException;

}
