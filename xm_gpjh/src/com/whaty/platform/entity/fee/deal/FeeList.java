/**
 * 
 */
package com.whaty.platform.entity.fee.deal;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
public interface FeeList {

	public List searchFeeRecord(Page page, List searchProperty,
			List orderProperty) throws PlatformException;

	public int searchFeeRecordNum(List searchProperty) throws PlatformException;

	public List searchUnConfirmFeeRecord(Page page, List searchProperty,
			List orderProperty) throws PlatformException;

	public int searchUnConfirmFeeRecordNum(List searchProperty)
			throws PlatformException;

	public List searchLeftFee(Page page, List searchProperty)
			throws PlatformException;

	public List getFeeStatBySemester(String semesterId);

	public List getSiteFeeStat(String startDate, String endDate, String siteId);
	
	public List getSiteFeeStat(String startDate, String endDate, String siteId,String gradeId);
	
	public List getGradeFeeStat(String startDate, String endDate, String siteId,String gradeId);
	
	public List getStudentFeeStat(String startDate, String endDate, String siteId,String gradeId);

	public double getUserFeeStandard(String userId);

	public double getUserFeeStandard(List searchProperty, List orderProperty);

	public int checkReturnApply(String id);

	public int backReturnApply(String id, String note);

	public int payReturnApply(String id);

	/**
	 * 获取某个站点的应交学费总额
	 * 
	 * @param siteId
	 * @return
	 */
	public double getSiteTotalFee(String siteId);

	/**
	 * 获取某个时间段内学生学费总额
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public double getFeeSum(String startDate, String endDate, String siteId);
	
	/**
	 * 获取某个时间段内学生费用总额
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public double getFeeSum(String feeType,String payoutType,String startDate, String endDate, String siteId);
	
	/**
	 * @param id
	 * @return
	 */
	public int setUserFeeRecordPrinted(String id);
}
