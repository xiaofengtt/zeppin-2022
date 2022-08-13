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
	 * �õ�ĳ���û����շѱ�׼
	 * 
	 * @param userId
	 * @return
	 */
	public Map getChargeLevel(String userId);

	/**
	 * Ϊĳ���û������շѱ�׼
	 * 
	 * @param userId
	 * @param chargeLevel
	 * @return
	 */
	public void setChargeLevel(String userId, Map chargeLevel)
			throws PlatformException;

	/**
	 * Ϊĳ��վ��,רҵ,���,�꼶���û����������շѱ�׼
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
	 * ��ȡ����������ChargeLevel�б�
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
	 * ��ȡ����������ChargeLevel��
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
	 * ��ȡ���������ı���ѧ��ChargeLevel�б�
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
	 * ��ȡ���������ı���ѧ��ChargeLevel��
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
	 * Ϊĳ������ѧ�������շѱ�׼
	 * 
	 * @param userId
	 * @param chargeLevel
	 * @return
	 */
	public void setRecruitChargeLevel(String userId, Map chargeLevel)
			throws PlatformException;

}
