/**
 * 
 */
package com.whaty.platform.entity.fee.deal;

import com.whaty.platform.database.oracle.standard.entity.fee.exception.FeeException;

/**
 * @author chenjian
 * 
 */
public interface DealWithFee {

	public String getNewRecordId() throws FeeException;

	public String getOrderNo(String regno) throws FeeException;

	public boolean dealWith(String recordId, String userId, double feeValue,
			String feeType, String payoutType, String note, boolean needCheck)
			throws FeeException;

	public boolean dealWith(String recordId, String userId, double feeValue,
			String feeType, String payoutType, String note, boolean needCheck,
			String orderNo) throws FeeException;

	public boolean checkFee(String id) throws FeeException;

	public boolean checkFeeByOrderNo(String orderNo) throws FeeException;
	
	public boolean repealByOrderNo(String orderNo) throws FeeException;

	public boolean unCheckFeeByOrderNo(String orderNo) throws FeeException;

	public void deleteByOrderNo(String orderNo) throws FeeException;

	public boolean getCheckStatusByOrderNo(String orderNo) throws FeeException;
}
