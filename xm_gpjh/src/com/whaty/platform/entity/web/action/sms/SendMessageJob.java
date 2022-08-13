package com.whaty.platform.entity.web.action.sms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.SmsInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.sms.SmsSendThread;


public class SendMessageJob extends QuartzJobBean {

	private GeneralService generalService;
	
	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
			Date date1=new Date();
			Date date2=new Date();
			GregorianCalendar now=new GregorianCalendar();
			date1=now.getTime();
			now.add(now.MINUTE,5);
			date2=now.getTime();
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date1s=format.format(date1);
			String date2s=format.format(date2);
			String sql="select sms.id from sms_info sms ,enum_const enu1,enum_const enu2,enum_const enu3" +
						" where sms.type=enu1.id and enu1.namespace='Type' and enu1.code='1'" +
						" and sms.sendstatus=enu2.id and enu2.namespace='Sendstatus' and enu2.code='0'"+
					    "  and to_date('"+date2s+"','yyyy-MM-dd hh24:mi:ss') > sms.send_date"+
					    "  and to_date('"+date1s+"','yyyy-MM-dd hh24:mi:ss') < sms.send_date"+
					    "  and sms.sms_status=enu3.id and enu3.namespace='SmsStatus' and enu3.code='1'";
//System.out.println("sql_id is "+sql);
			List list_id=new LinkedList();
			try {
				list_id=this.getGeneralService().getBySQL(sql);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.getGeneralService().getGeneralDao().setEntityClass(SmsInfo.class);
			if(list_id!=null&&list_id.size()>0){
				for(int i=0;i<list_id.size();i++){
					Object objs= list_id.get(i);
					String smsId=(String)objs;
					SmsInfo sms=null;
					try {
						sms=(SmsInfo) this.getGeneralService().getById(smsId);
					} catch (EntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String mobile = sms.getTargets();
					String content = sms.getContent();
					SmsSendThread sendThread = new SmsSendThread(mobile,
							content);
					sendThread.start();
					//查找已发送状态
					EnumConst enu_send=null;
					this.getGeneralService().getGeneralDao().setEntityClass(EnumConst.class);
					DetachedCriteria dc1=DetachedCriteria.forClass(EnumConst.class);
					dc1.add(Restrictions.eq("namespace", "Sendstatus"));
					dc1.add(Restrictions.eq("code", "1"));
					List list_send=new LinkedList();
					try {
						list_send=this.getGeneralService().getList(dc1);
					} catch (EntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(list_send!=null&list_send.size()>0){
						enu_send=(EnumConst) list_send.get(0);
						sms.setEnumConstBySendstatus(enu_send);
					}
					try {
						sms=(SmsInfo) this.getGeneralService().save(sms);
					} catch (EntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
//***************************************************************************************************************************			
//			DetachedCriteria dc=DetachedCriteria.forClass(SmsInfo.class);
//			dc.createCriteria("peManagerBySender","peManagerBySender");
//			dc.createAlias("enumConstByType", "enumConstByType");
//			dc.add(Restrictions.eq("enumConstByType.code", "1"));//定时短信
//			
////			dc.add(Restrictions.between("sendDate", date1, date2));//时间在下一分钟
////			dc.add(Restrictions.ge("sendDate", date1));
////			dc.add(Restrictions.le("sendDate", date2));
////			dc.add(Expression.between("sendDate", date1, date2));
//			
//			dc.createCriteria("enumConstBy	","enumConstBySendstatus");
//			dc.add(Restrictions.eq("enumConstBySendstatus.code","0"));//未发送的短信
			
//			List list=new LinkedList();
//			try {
//				list=this.getGeneralService().getList(dc);
//			} catch (EntityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println("list size is  "+list.size());
//System.out.println("date1 is "+date1);
//System.out.println("date2 is "+date2);
//			if(list!=null&&list.size()>0){
//				for(int i=0;i<list.size();i++){
//					
//System.out.println("begin to send sms");
//					SmsInfo sms=(SmsInfo) list.get(i);
//					String mobile = sms.getTargets();
//					String content = sms.getContent();
//					SmsSendThread sendThread = new SmsSendThread(mobile,
//							content);
////					sendThread.start();
//System.out.println("mobile is "+mobile);
//System.out.println("content is "+content);
//System.out.println("sms.getSendDate() compareTo date1"+(sms.getSendDate().compareTo(date1)));
//System.out.println("sms.getSendDate() compareTo date2"+(sms.getSendDate().compareTo(date2)));
//					//查找已发送状态
//					EnumConst enu_send=null;
//					this.getGeneralService().getGeneralDao().setEntityClass(EnumConst.class);
//					DetachedCriteria dc1=DetachedCriteria.forClass(EnumConst.class);
//					dc1.add(Restrictions.eq("namespace", "Sendstatus"));
//					dc1.add(Restrictions.eq("code", "1"));
//					List list_send=new LinkedList();
//					try {
//						list_send=this.getGeneralService().getList(dc1);
//					} catch (EntityException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
////					enu_send=this.getMyListService().getEnumConstByNamespaceCode("Sendstatus", "1");
//					
//				}
//			}
			
	}
}
