package com.whaty.platform.listener.sendMessage;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.whaty.platform.entity.bean.PeSmsSystempoint;
import com.whaty.platform.entity.bean.WhatyuserLog4j;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.sms.SmsSendThread;



public class SendMessageJob extends QuartzJobBean {
	 public SendMessageJob() { 
		  
	  } 
	 
	 private MyListDAO myListDao;

		public MyListDAO getMyListDao() {
			return myListDao;
		}

		public void setMyListDao(MyListDAO myListDao) {
			this.myListDao = myListDao;
		}

		@Override
		protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
			DetachedCriteria dc = DetachedCriteria.forClass(PeSmsSystempoint.class);
			dc.createAlias("enumConstByFlagIsauto", "enumConstByFlagIsauto");
			dc.createAlias("enumConstByFlagSmsStatus", "enumConstByFlagSmsStatus");
			dc.add(Restrictions.eq("enumConstByFlagIsauto.code", "1"));
			dc.add(Restrictions.eq("enumConstByFlagSmsStatus.code", "1"));
			List list = myListDao.getList(dc);
			//遍历短信点
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				PeSmsSystempoint element = (PeSmsSystempoint) iter.next();
				List infos = myListDao.getBySQL(element.getSqlNote());
				//组合手机号
				if(infos.size() > 0){
//					StringBuffer mobiles = new StringBuffer();
//					for (int i = 0; i < infos.size(); i++) {
//						Object[] obj = (Object[])infos.get(i);  
//						mobiles.append((String)obj[i]);
//					}
//					SmsSendThread sendThread = new SmsSendThread(mobiles.toString(),element.getNote());
//					sendThread.start();
					sendSms(infos,element.getNote());
				}
			}
		}
		private void sendSms(List infos,String note){
			StringBuffer mobiles = new StringBuffer();
			for (int i = 0; i < infos.size(); i++) {
				Object[] obj = (Object[])infos.get(i);  
				mobiles.append((String)obj[i]+",");
			}
			//记录短信记录
			WhatyuserLog4j log = new WhatyuserLog4j();
			log.setLogtype("System");
			log.setBehavior("发送短信点短信");
			log.setNotes("短信内容："+note+"  ;发送手机号："+mobiles);
			log.setStatus("true");
			log.setOperateTime(new Date());
			this.getMyListDao().save(log);
			//记录短信记录
			
			SmsSendThread sendThread = new SmsSendThread(mobiles.toString(),note);
			sendThread.start();
		}



	  

}
