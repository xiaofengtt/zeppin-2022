/**
 * 
 */
package com.whaty.platform.entity.fee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.fee.deal.OtherFeeType;
import com.whaty.platform.entity.fee.deal.UserFeeRecord;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
public interface FeeManage {

	public boolean dealWith(String userId, double feeValue, String feeType,
			String payoutType, String note, boolean needCheck)
			throws PlatformException;

	public double getCurrentDeposit(String userId) throws PlatformException;

	public double getUserTotalFee(String userId) throws PlatformException;
	
	public double getUserDepositTotalFee(String userId) throws PlatformException;

	public List getUserFeeRecord(Page page, String userId, String feeType,
			String payoutType, String startDate, String endDate)
			throws PlatformException;

	public int getUserFeeRecordNum(String userId, String feeType,
			String payoutType, String startDate, String endDate)
			throws PlatformException;

	public List searchUserFeeRecord(Page page, String loginId, String name,
			String feeType, String payoutType, String startDate,
			String endDate, String site_id) throws PlatformException;

	public int searchUserFeeRecordNum(String loginId, String name,
			String feeType, String payoutType, String startDate,
			String endDate, String site_id) throws PlatformException;

	public List searchUserFeeRecord(Page page, String loginId, String name,
			String feeType, String payoutType, String startDate,
			String endDate, String site_id, String grade_id, String edutype_id,
			String major_id, String invoice_status) throws PlatformException;

	public int searchUserFeeRecordNum(String loginId, String name,
			String feeType, String payoutType, String startDate,
			String endDate, String site_id, String grade_id, String edutype_id,
			String major_id, String invoice_status) throws PlatformException;

	public List searchUnConfirmUserFeeRecord(Page page, String loginId,
			String name, String feeType, String payoutType, String startDate,
			String endDate, String site_id, String order_no)
			throws PlatformException;

	public int searchUnConfirmUserFeeRecordNum(String loginId, String name,
			String feeType, String payoutType, String startDate,
			String endDate, String site_id, String order_no)
			throws PlatformException;

	public List searchTotalUserFeeRecord(Page page, String loginId,
			String name, String feeType, String payoutType, String startDate,
			String endDate, String site_id, String order_no)
			throws PlatformException;

	public int searchTotalUserFeeRecordNum(String loginId, String name,
			String feeType, String payoutType, String startDate,
			String endDate, String site_id, String order_no)
			throws PlatformException;

	public List getFeeStatBySemester(String semesterId)
			throws PlatformException;

	public UserFeeRecord getUserFeeRecord(String orderno, String userId)
			throws PlatformException;

	public UserFeeRecord getUserFeeRecord(String id) throws PlatformException;

	public int setUserFeeRecordPrinted(String id) throws PlatformException;

	/**
	 * ͳ��ĳ��ʱ��ϵĽ�ѧվѧ��
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws PlatformException
	 */
	public List getSiteFeeStat(String startDate, String endDate, String siteId)
			throws PlatformException;

	public List getSiteFeeStat(String startDate, String endDate, String siteId,
			String gradeId) throws PlatformException;

	public List getGradeFeeStat(String startDate, String endDate,
			String siteId, String gradeId) throws PlatformException;

	public List getStudentFeeStat(String startDate, String endDate,
			String siteId, String gradeId) throws PlatformException;

	/**
	 * ��ȡ�ƶ�ѧ���ѧ�ֱ�׼
	 * 
	 * @param userId
	 * @return
	 * @throws PlatformException
	 */
	public double getUserFeeStandard(String userId) throws PlatformException;

	/**
	 * @param siteId
	 * @param majorId
	 * @param gradeId
	 * @param eduTypeId
	 * @return
	 * @throws PlatformException
	 */
	public double getUserFeeStandard(String siteId, String majorId,
			String gradeId, String eduTypeId) throws PlatformException;

	public List getUserFeeStandard(Page page, String siteId, String majorId,
			String gradeId, String eduTypeId) throws PlatformException;

	public int getUserFeeStandardNum(String siteId, String majorId,
			String gradeId, String eduTypeId) throws PlatformException;

	/**
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public int checkReturnApply(String id) throws PlatformException;

	/**
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public int payReturnApply(String id) throws PlatformException;

	/**
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public int backReturnApply(String id, String note) throws PlatformException;

	/**
	 * ��ȡ���������˷�������
	 * 
	 * @param userId
	 * @param isChecked
	 * @param isReturned
	 * @param note
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getUserFeeReturnApplyNum(String userId,
			String isChecked, String isReturned, String note)
			throws PlatformException;

	/**
	 * ��ȡ���������˷������б�
	 * 
	 * @param page
	 * @param userId
	 * @param isChecked
	 * @param isReturned
	 * @param note
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUserFeeReturnApplyList(Page page, String userId,
			String isChecked, String isReturned, String note)
			throws PlatformException;

	/**
	 * ���ѧ�����Ϣ�б�
	 * 
	 * @param page
	 * @param site_id
	 * @param edutype_id
	 * @param major_id
	 * @param grade_id
	 * @param name
	 * @param reg_no
	 * @param id_card
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudentFeeList(Page page, String site_id,
			String edutype_id, String major_id, String grade_id, String name,
			String reg_no, String id_card) throws PlatformException;

	/**
	 * ��ȡָ��վ���Ӧ��ѧ���ܶ�
	 * 
	 * @param siteId
	 * @return
	 * @throws PlatformException
	 */
	public abstract double getSiteTotalStandardFee(String siteId)
			throws PlatformException;

	/**
	 * ��ȡָ��ʱ����ڵ�ѧ���ܶ�
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws PlatformException
	 */
	public abstract double getFeeSum(String startDate, String endDate,
			String siteId) throws PlatformException;

	public abstract double getFeeSum(String startDate, String endDate,
			String site_id, String grade_id, String edutype_id,
			String major_id, String invoice_status) throws PlatformException;

	/**
	 * ��ȡָ��ʱ����ڵĸ��ַ����ܶ�
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws PlatformException
	 */
	public abstract double getFeeSum(String feeType, String payoutType,
			String startDate, String endDate, String siteId)
			throws PlatformException;

	// OtherFeeType

	public abstract int addOtherFeeType(HttpServletRequest request)
			throws PlatformException;

	public abstract int deleteOtherFeeType(HttpServletRequest request)
			throws PlatformException;

	public abstract OtherFeeType getOtherFeeType(HttpServletRequest request)
			throws PlatformException;

	public abstract int updateOtherFeeType(HttpServletRequest request)
			throws PlatformException;

	public abstract List getOtherFeeTypes(Page page, HttpServletRequest request)
			throws PlatformException;

	public abstract int getOtherFeeTypesNum(HttpServletRequest request)
			throws PlatformException;

	/**
	 * ��ȡѧ��Ӧ��ѧ���ܶ�
	 * 
	 * @param userId
	 * @return
	 * @throws PlatformException
	 */
	public double getUserTotalXueFee(String userId) throws PlatformException;

	/**
	 * ��ȡѧ��ÿ�νɷѽ��
	 * 
	 * @param userId
	 * @return
	 * @throws PlatformException
	 */
	public double getUserXueFeeByTime(String userId) throws PlatformException;

	/**
	 * ��ȡѧ��ѡ���������
	 * 
	 * @param userId
	 * @return
	 * @throws PlatformException
	 */
	public double getUserElectiveFee(String userId, HttpServletRequest request)
			throws PlatformException;
}
