package com.whaty.platform.entity.service.sms;

import java.util.List;

import org.quartz.JobExecutionException;

import com.whaty.platform.entity.bean.PeSmsInfo;
import com.whaty.platform.entity.bean.PrStuChangeSchool;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;

public interface PeSmsInfoService {
	public abstract PeSmsInfo save(PeSmsInfo peSmsInfo,String[] mobilePhones,String type_code,SsoUser ssouser) throws EntityException;

	public abstract void update_checkSmsMessage(String peSms_ids) throws Exception;
	
	public abstract void update_resend(String peSms_ids) throws Exception;

	public abstract void update_rejectSmsMessage(String peSms_ids, String returnReason);
	
	public int saveSendSmsHandle(String code) throws JobExecutionException ;
	/**
	 * 根据学生id和系统短信code发送短信
	 * @param ids   PeStudent 的id
	 * @param code   系统短信code ，使用系统短信中内容
	 * @return
	 */
	public String saveSendStuSms(List ids,String code) throws EntityException ;
}
