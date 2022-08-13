/**
 * 
 */
package com.whaty.platform.sms.imp;

import com.whaty.platform.sms.SmsFactory;
import com.whaty.platform.sms.SmsManage;

/**
 * @author wq
 * 
 */
public class OracleSmsFactory extends SmsFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.sms.SmsFactory#creatSmsManage(com.whaty.platform.sms.SmsManagerPriv)
	 */

	
	public SmsManage creatSmsManage() {
		return new OracleSmsManage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.sms.SmsFactory#getSmsManagerPriv(java.lang.String)
	 */

}
