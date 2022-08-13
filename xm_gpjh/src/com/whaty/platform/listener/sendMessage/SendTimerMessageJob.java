package com.whaty.platform.listener.sendMessage;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeSmsInfo;
import com.whaty.platform.entity.bean.PrSmsSendStatus;
import com.whaty.platform.entity.bean.WhatyuserLog4j;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.sms.SmsSendThread;
/**
 * 发送定时短信
 * @author 
 *
 */
public class SendTimerMessageJob extends QuartzJobBean {

	private MyListDAO myListDao;

	public MyListDAO getMyListDao() {
		return myListDao;
	}

	public void setMyListDao(MyListDAO myListDao) {
		this.myListDao = myListDao;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {

		this.sendTimerMessage();
		
	}
		
	/**
	 * 发送定时短信
	 */
	private void sendTimerMessage(){
		
		//查出所有定时短信
//		Date nowDate = new Date();
//		nowDate.setSeconds(0);
//		myListDao.setEntityClass(PeSmsInfo.class);
//		DetachedCriteria dc = DetachedCriteria.forClass(PeSmsInfo.class);
//		dc.createAlias("enumConstByFlagSmsType", "enumConstByFlagSmsType");
//		dc.createAlias("enumConstByFlagSmsStatus", "enumConstByFlagSmsStatus");
//		dc.add(Restrictions.eq("enumConstByFlagSmsStatus.code","1"));
//		dc.add(Restrictions.eq("enumConstByFlagSmsType.code","1"));
//		dc.add(Restrictions.eq("bookingDate", nowDate));
		
		String sql = "select sms.id id from pe_sms_info sms, enum_const enum_const1, enum_const enum_const2 " +
			" where sms.flag_sms_type = enum_const1.id   and enum_const1.code = '1'" +
			" and sms.flag_sms_status = enum_const2.id   and enum_const2.code = '1' " +
			" and to_char(sms.booking_date,'yyyy-mm-dd hh24:mi') = to_char(sysdate ,'yyyy-mm-dd hh24:mi')" +
			" for update";
		//TODO: 怀疑是多个应用同时调用，会重复选择导致重复发短信，加锁(for update)以避免此问题。张利斌 2009-1-16
		
		List ids = myListDao.getBySQL(sql);
		
		for (int i=0;i<ids.size();i++) {
			PeSmsInfo peSmsInfo = (PeSmsInfo)myListDao.getById(PeSmsInfo.class, ids.get(i).toString());
			Set set = peSmsInfo.getPrSmsSendStatuses();
			StringBuffer mobiles = new StringBuffer();
		
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				PrSmsSendStatus prSmsSendStatus = (PrSmsSendStatus) iterator.next();
				mobiles.append(prSmsSendStatus.getMobilePhone()+",");
				prSmsSendStatus.setFlagSendStatus("1");
				myListDao.save(prSmsSendStatus);
			}
			peSmsInfo.setSendDate(new Date());
			myListDao.save(peSmsInfo);
			SmsSendThread sendThread = new SmsSendThread(mobiles.toString(),peSmsInfo.getNote());
			sendThread.start();
			
//			记录短信记录
			WhatyuserLog4j log = new WhatyuserLog4j();
			log.setLogtype("System");
			log.setBehavior("发送定时短信");
			log.setNotes("短信内容："+peSmsInfo.getNote()+"  ;发送手机号："+mobiles.toString());
			log.setStatus("true");
			log.setOperateTime(new Date());
			this.getMyListDao().save(log);
			//记录短信记录
			
		}
	}

}
