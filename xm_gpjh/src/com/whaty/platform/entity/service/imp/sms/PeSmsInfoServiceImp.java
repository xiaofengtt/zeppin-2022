package com.whaty.platform.entity.service.imp.sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobExecutionException;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PeSmsInfo;
import com.whaty.platform.entity.bean.PeSmsSystempoint;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrSmsSendStatus;
import com.whaty.platform.entity.bean.PrStuChangeSchool;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.WhatyuserLog4j;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.sms.PeSmsInfoService;
import com.whaty.platform.sms.SmsSendThread;
import com.whaty.platform.util.Const;


public class PeSmsInfoServiceImp implements PeSmsInfoService {

	private MyListDAO myListDao;
	
	public PeSmsInfo save(PeSmsInfo peSmsInfo, String[] mobilePhones,String type_code,SsoUser ssouser)throws EntityException {
		
		peSmsInfo.setEnumConstByFlagSmsType(myListDao.getEnumConstByNamespaceCode("FlagSmsType", type_code));
		peSmsInfo.setEnumConstByFlagSmsStatus(myListDao.getEnumConstByNamespaceCode("FlagSmsStatus", "0"));
		//如果是分站管理员，插入分站点
		if(ssouser.getPePriRole().getName().equals("分站管理员")){
			PeSitemanager peSitemanager = (PeSitemanager)myListDao.getById(PeSitemanager.class, ssouser.getId());
			PeSite peSite = peSitemanager.getPeSite(); 
			peSmsInfo.setPeSite(peSite);
		}
		peSmsInfo.setCreatDate(new Date());
		
		peSmsInfo = (PeSmsInfo)myListDao.save(peSmsInfo);
		for(int i=0;i<mobilePhones.length;i++){
			PrSmsSendStatus prSmsSendStatus = new PrSmsSendStatus();
			prSmsSendStatus.setPeSmsInfo(peSmsInfo);
			prSmsSendStatus.setFlagSendStatus("0");
			prSmsSendStatus.setMobilePhone(mobilePhones[i]);
			myListDao.save(prSmsSendStatus);
		}
		return peSmsInfo;
	}

	public MyListDAO getMyListDao() {
		return myListDao;
	}

	public void setMyListDao(MyListDAO myListDao) {
		this.myListDao = myListDao;
	}
	/**
	 * @throws Exception 
	 * 审核短信
	 */
	public void update_checkSmsMessage(String peSms_ids) throws Exception {
		// TODO Auto-generated method stub
		String[] ids = peSms_ids.split(",");
		for(int i=0;i<ids.length;i++){
			//修改短信审核状态
			PeSmsInfo peSmsInfo = (PeSmsInfo)myListDao.getById(PeSmsInfo.class,ids[i]);
			peSmsInfo.setEnumConstByFlagSmsStatus(myListDao.getEnumConstByNamespaceCode("FlagSmsStatus", "1"));
			myListDao.save(peSmsInfo);
			Set set =peSmsInfo.getPrSmsSendStatuses();
			if(set.size()>0){
				String mobilephones = "";
				for (Iterator iter = set.iterator(); iter.hasNext();) {
					//修改PrSmsSendStatus短信条发送状态
					PrSmsSendStatus prSmsSendStatus  = (PrSmsSendStatus) iter.next();
					prSmsSendStatus.setFlagSendStatus("1"); 
					mobilephones+=prSmsSendStatus.getMobilePhone();
					myListDao.save(prSmsSendStatus);
				}
				//判断是否是普通短信，调用短信发送方法
				if(peSmsInfo.getEnumConstByFlagSmsType().getCode().equals("0")){
					try {
						peSmsInfo.setSendDate(new Date());
						sendSms(mobilephones, peSmsInfo.getNote());
					} catch (Exception e) {
						throw new Exception("发送短信失败！");
					}
				}
				
			}
			myListDao.save(peSmsInfo);
		}
	}
	/**
	 * 发送短信
	 * @param mobilephones
	 * @param content
	 * @return
	 * @throws PlatformException
	 */
	
	public void update_resend(String peSms_ids) throws Exception {
		String[] ids = peSms_ids.split(",");
		for(int i=0;i<ids.length;i++){
			//修改短信审核状态
			PeSmsInfo peSmsInfo = (PeSmsInfo)myListDao.getById(PeSmsInfo.class,ids[i]);
			peSmsInfo.setReturnReason("");
			peSmsInfo.setEnumConstByFlagSmsStatus(myListDao.getEnumConstByNamespaceCode("FlagSmsStatus", "0"));
			myListDao.save(peSmsInfo);
			Set set =peSmsInfo.getPrSmsSendStatuses();
			if(set.size()>0){
				for (Iterator iter = set.iterator(); iter.hasNext();) {
					//修改PrSmsSendStatus短信条发送状态
					PrSmsSendStatus prSmsSendStatus  = (PrSmsSendStatus) iter.next();
					prSmsSendStatus.setFlagSendStatus("0"); 
					myListDao.save(prSmsSendStatus);
				}
				
			}
		}
	}

	public void update_rejectSmsMessage(String peSms_ids, String returnReason) {
		String[] ids = peSms_ids.split(",");
		List list  = new ArrayList();
		for(int i=0;i<ids.length;i++ ){
			list.add(ids[i]);
		}
		String column ="returnReason,enumConstByFlagSmsStatus.id";
		String value = returnReason+","+myListDao.getEnumConstByNamespaceCode("FlagSmsStatus", "2").getId();
		myListDao.setEntityClass(PeSmsInfo.class);	
		myListDao.updateColumnByIds(list, column, value);
	}
	
	/**
	 * 发送手动触发短信
	 * @param code pe_sms_systempoint 的code字段
	 * @return 返回发送短息的条数
	 * @throws JobExecutionException
	 */
	public int saveSendSmsHandle(String code) throws JobExecutionException {
		this.getMyListDao().setEntityClass(PeSmsSystempoint.class);
		PeSmsSystempoint instance = new PeSmsSystempoint();
		instance.setCode(code);
		List list = myListDao.getByExample(instance);
		if(list.size()>=0){
			PeSmsSystempoint smspoint = (PeSmsSystempoint)list.get(0); 
			List infos = myListDao.getBySQL(smspoint.getSqlNote());
			//组合手机号
			if(infos.size() > 0){
				sendSms(infos,smspoint.getNote());
			}
			return infos.size();
		}
		return 0;
	}
	
	/**
	 * 根据学生id和系统短信code发送短信
	 * @param ids   PeStudent 的id
	 * @param code   系统短信code ，使用系统短信中内容
	 * @return
	 */
	public String saveSendStuSms(List ids,String code) throws EntityException {

		List<PeStudent> students = this.getMyListDao().getByIds(PeStudent.class, ids);
		String nullStu = "";
		String errorStu = "";
		String message = "";
		String str = "相同的号码只发送一条短信。重复出现的号码为："; //记录重复出现的号码
		int strLen = str.length();
		Set num = new HashSet<String>();
		Set repeat = new HashSet<String>();//记录重复出现的号码
		if(code==null||code.equals("")){
			throw new EntityException("短信编号为空，发送失败。");
		}
		List list = new ArrayList();
		for (PeStudent peStudent : students) {
			String mobile = peStudent.getPrStudentInfo().getMobilephone();
			if(mobile==null||mobile.length()<1){
				nullStu += peStudent.getName() + ",";
				continue;
			}
			if(!mobile.matches(Const.mobile)){
				errorStu += peStudent.getName() + ",";
				continue;
			}
			if(num.add(mobile)){
				list.add(new Object[]{mobile});
			}else if(repeat.add(mobile)){
				str += mobile + ",";
			}
			
		}
		if(nullStu.length()>0){
			message +="</br>手机号码为空的学生为："+nullStu;
		}
		if(errorStu.length()>0){
			message +="</br>手机号码格式错误的学生为："+errorStu;
		}
		if(list==null||list.isEmpty()){
			message +="</br>发送短信数量为0。";
			return message;
		}

		this.getMyListDao().setEntityClass(PeSmsSystempoint.class);
		PeSmsSystempoint instance = new PeSmsSystempoint();
		instance.setCode(code);
		List listSms = myListDao.getByExample(instance);
		if(listSms!=null&&listSms.size()>0){
			if(str.length()>strLen){
				message += str;
			}
			
			PeSmsSystempoint smspoint = (PeSmsSystempoint)listSms.get(0); 
 			this.sendSms(list, smspoint.getNote());
			message +="</br>成功发送"+list.size()+"条短信。";
		}else{
			message +="</br>没有查到编号为"+code+"的系统短信点，发送失败。";
			throw new EntityException(message);
			
		}
		return message;
	}
	
	private void sendSms(String mobilephones , String content)throws PlatformException {
		SmsSendThread sendThread = new SmsSendThread(mobilephones,content);
		sendThread.start();
		//记录短信记录
		WhatyuserLog4j log = new WhatyuserLog4j();
		log.setLogtype("System");
		log.setBehavior("发送普通短信");
		log.setNotes("短信内容："+content+"  ;发送手机号："+mobilephones);
		log.setStatus("true");
		log.setOperateTime(new Date());
		this.getMyListDao().save(log);
		//记录短信记录
	}
	private void sendSms(List infos,String note){
		StringBuffer mobiles = new StringBuffer();
		for (int i = 0; i < infos.size(); i++) {
			Object[] obj = (Object[])infos.get(i);  
			mobiles.append((String)obj[0]+",");
		}
		SmsSendThread sendThread = new SmsSendThread(mobiles.toString(),note);
		sendThread.start();
		//记录短信记录
		WhatyuserLog4j log = new WhatyuserLog4j();
		log.setLogtype("System");
		log.setBehavior("发送手动触发短信点短信");
		log.setNotes("短信内容："+note+"  ;发送手机号："+mobiles);
		log.setStatus("true");
		log.setOperateTime(new Date());
		this.getMyListDao().save(log);
		//记录短信记录
	}
}
