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
	 * �õ�Ŀǰ�ʻ����
	 * 
	 * @return
	 */
	public abstract double getUserTotalFee();

	/**
	 * �õ�ѧ��ĳ���͵Ĵ���ܶ�
	 * 
	 * @param feeType
	 * @return
	 */
	public abstract double getUserTotalDeposit(String feeType);

	/**
	 * �õ��û������Ѽ�¼�б�
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
	 * �õ��û����ѵ���Ǯ��
	 * 
	 * @param feeType
	 * @param payoutType
	 * @return
	 */
	public abstract double getTotalFee(String feeType, String payoutType);

}
