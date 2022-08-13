/**
 * 
 */
package com.whaty.platform.listener.sms;

import java.util.TimerTask;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.sms.OracleSmsManagerPriv;
import com.whaty.platform.sms.SmsFactory;
import com.whaty.platform.sms.SmsManage;
import com.whaty.platform.util.log.EntityLog;

public class SendReSMSTask extends TimerTask {

	private static boolean isRunning = false;

	public String processPhone(String phone) {
		String[] phones = null;
		if (phone != null)
			phones = phone.split(",");
		else
			return "";
		String tmpPhone = "";
		for (int i = 0; i < phones.length; i++) {
			if (phones[i] != null
					&& !phones[i].equals("")
					&& !phones[i].equals("null")
					&& (tmpPhone.length() == 0 || (tmpPhone.length() > 0 && tmpPhone
							.indexOf("," + phones[i]) < 0)))
				tmpPhone += "," + phones[i];
		}
		if (tmpPhone.length() > 0)
			tmpPhone = tmpPhone.substring(1);
		return tmpPhone;
	}

	public void run() {
		
		if (!isRunning) {
			if (true) {
				isRunning = true;
				 
				String sql = "select id,targets,content from sms_info where status='1' and sendstatus='1'";
				dbpool db = new dbpool();
				MyResultSet rs = null;
				try {
					rs = db.executeQuery(sql);
				} catch (Exception e) {
					db.close(rs);
				}
				SmsFactory sfactory = SmsFactory.getInstance();
				OracleSmsManagerPriv smsManagerPriv = new OracleSmsManagerPriv(
						"system");
				SmsManage smsManage = sfactory.creatSmsManage(smsManagerPriv);
				try {
					while (rs != null && rs.next()) {
						try {
							smsManage.sendMessage(rs.getString("id"),processPhone(rs
									.getString("targets")), rs
									.getString("content"));
							
						} catch (Exception e) {
						}
					}
				} catch (Exception e) {
					db.close(rs);
				}
				EntityLog.setDebug("SendReSMSTask�������·�������");
				db.close(rs);

				isRunning = false;
			}

			 
		}
	}
}
