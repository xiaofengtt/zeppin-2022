/**
 * 
 */
package com.whaty.platform.entity.fee.deal;

import java.util.List;

import com.whaty.platform.User;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
public abstract class UserFee {

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 得到目前帐户余额
	 * 
	 * @return
	 */
	public abstract double getUserTotalFee();

	/**
	 * 得到学生某类型的存款总额
	 * 
	 * @param feeType
	 * @return
	 */
	public abstract double getUserTotalDeposit(String feeType);

	/**
	 * 得到用户的消费记录列表
	 * 
	 * @param page
	 * @param feeType
	 * @param payoutType
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUserFeeRecord(Page page, String feeType,
			String payoutType) throws PlatformException;

	/**
	 * 得到用户消费的总钱数
	 * 
	 * @param feeType
	 * @param payoutType
	 * @return
	 */
	public abstract double getTotalFee(String feeType, String payoutType);

}
