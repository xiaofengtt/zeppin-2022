package cn.zeppin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.dao.IMailAttachmentDao;
import cn.zeppin.dao.IMailConnectionDao;
import cn.zeppin.dao.IMailInformationDao;
import cn.zeppin.dao.IServiceApplyReplyDao;
import cn.zeppin.entity.Document;
import cn.zeppin.entity.MailAttachment;
import cn.zeppin.entity.MailConnection;
import cn.zeppin.entity.MailInformation;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectExpert;
import cn.zeppin.entity.ServiceApplyReply;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.service.IMailInformationService;

@SuppressWarnings("rawtypes")
public class MailInformationServiceImpl extends
		BaseServiceImpl<MailInformation, Integer> implements
		IMailInformationService {

	private IMailInformationDao mailInformationDao;
	private IMailConnectionDao mailConnectionDao;
	private IMailAttachmentDao mailAttachmentDao;
	private IServiceApplyReplyDao serviceApplyReplyDao;
	
	public IMailConnectionDao getMailConnectionDao() {
		return mailConnectionDao;
	}

	public void setMailConnectionDao(IMailConnectionDao mailConnectionDao) {
		this.mailConnectionDao = mailConnectionDao;
	}

	public IMailInformationDao getMailInformationDao() {
		return mailInformationDao;
	}

	public void setMailInformationDao(IMailInformationDao mailInformationDao) {
		this.mailInformationDao = mailInformationDao;
	}
	
	public IMailAttachmentDao getMailAttachmentDao() {
		return mailAttachmentDao;
	}

	public void setMailAttachmentDao(IMailAttachmentDao mailAttachmentDao) {
		this.mailAttachmentDao = mailAttachmentDao;
	}

	public IServiceApplyReplyDao getServiceApplyReplyDao() {
		return serviceApplyReplyDao;
	}

	public void setServiceApplyReplyDao(IServiceApplyReplyDao serviceApplyReplyDao) {
		this.serviceApplyReplyDao = serviceApplyReplyDao;
	}

	public MailInformation add(MailInformation t) {
		return mailInformationDao.add(t);
	}

	public MailInformation update(MailInformation t) {
		return mailInformationDao.update(t);
	}

	public void delete(MailInformation t) {
		mailInformationDao.delete(t);
	}

	public HibernateTemplate getHibeTemplate() {
		return mailInformationDao.getHibeTemplate();
	}

	public MailInformation load(Integer id) {
		return mailInformationDao.load(id);
	}

	public MailInformation get(Integer id) {
		return mailInformationDao.get(id);
	}

	public List<MailInformation> loadAll() {
		return mailInformationDao.loadAll();
	}

	public List<MailInformation> findAll() {
		return mailInformationDao.findAll();
	}

	public List<Object> findByHSQL(String querySql) {
		return mailInformationDao.findByHSQL(querySql);
	}

	public List<MailInformation> getListForPage(String hql, int offset,
			int length) {
		return mailInformationDao.getListForPage(hql, offset, length);
	}

	public List<MailInformation> getListForPageByParams(String hql, int offset,
			int length, Object[] paras) {
		return mailInformationDao.getListForPageByParams(hql, offset, length,
				paras);
	}

	public List<MailInformation> getListForPage(String hql, int offset,
			int length, Object[] paras) {
		return mailInformationDao.getListForPage(hql, offset, length, paras);
	}

	public void executeHSQL(String hql) {
		mailInformationDao.executeHSQL(hql);
	}

	public List<MailInformation> getListByHSQL(String hql) {
		return mailInformationDao.getListByHSQL(hql);
	}

	public List getListBySQL(String sql, Object[] objParas) {
		return mailInformationDao.getListBySQL(sql, objParas);
	}

	public void executeSQLUpdate(String sql, Object[] objParas) {
		mailInformationDao.executeSQLUpdate(sql, objParas);
	}

	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		return mailInformationDao.getListPage(sql, offset, length, objParas);
	}

	public Object getObjectByHql(String hql, Object[] paras) {
		return mailInformationDao.getObjectByHql(hql, paras);
	}

	public Object getObjectBySql(String sql, Object[] paras) {
		return mailInformationDao.getObjectBySql(sql, paras);
	}

	@Override
	public List<MailInformation> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		return this.mailInformationDao.getListByParams(params, start, limit, sort);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.mailInformationDao.getListCountByParams(params);
	}

	@Override
	public String deleteInfo(UserSession us, String id, String type) {
		// TODO Auto-generated method stub
		String result = "ok";
		if(id != null && type != null){
			if("1".equals(type)){//收件 删除对应角色的回复
				MailConnection mc = this.mailConnectionDao.get(Integer.parseInt(id));
				if(mc != null){
					MailInformation mi = mc.getMailInformation();
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("role", us.getRole());
					params.put("creator", us.getId());
					params.put("mid", mi.getId());
					List<ServiceApplyReply> sarlist = this.serviceApplyReplyDao.getReplyListByServiceApplyID(params, -1, -1);
					if(sarlist != null && sarlist.size() > 0){
						for(ServiceApplyReply sar : sarlist){
							sar.setStatus((short)0);
							this.serviceApplyReplyDao.update(sar);
						}
					}
					mc.setStatus((short)-1);
					this.mailConnectionDao.update(mc);
				}else{
					result = "要删除的信息不存在";
					return result;
				}
			}else if("2".equals(type)) {//发件 删除收件、附件和回复
				MailInformation mi = this.mailInformationDao.get(Integer.parseInt(id));
				if(mi != null){
					Set<MailConnection> mcSet = mi.getMailConnections();
					Set<MailAttachment> maSet = mi.getMailAttachment();
					Set<ServiceApplyReply>sarSet = mi.getServiceApplyReply();
//					Set<MailAttachment> mailAttachment = new HashSet<MailAttachment>();
					mi.setSendStatus((short)0);
					
					if(mcSet != null && mcSet.size() > 0){
						for(MailConnection mc : mcSet){
							mc.setStatus((short)-1);
							this.mailConnectionDao.update(mc);
						}
					}
					if(maSet != null && maSet.size() > 0){//删除
						mi.setMailAttachment(null);
						for(MailAttachment ma : maSet){
//							ma.setStatus((short)-1);
							this.mailAttachmentDao.delete(ma);
						}
					}
					if(sarSet != null){
						for(ServiceApplyReply sar : sarSet){
							sar.setStatus((short)0);
							this.serviceApplyReplyDao.update(sar);
						}
					}
					this.mailInformationDao.update(mi);
				}else{
					result = "要删除的信息不存在";
					return result;
				}
				
			}else if("3".equals(type)){//删回复
				ServiceApplyReply sar = this.serviceApplyReplyDao.get(Integer.parseInt(id));
				if(sar != null){
					sar.setStatus((short)0);
					this.serviceApplyReplyDao.update(sar);
				}else{
					result = "要删除的信息不存在";
					return result;
				}
			}else{
				result = "参数错误";
				return result;
			}
		}else{
			result = "参数错误";
			return result;
		}
		
		return result;
	}

	/**
	 * 发送信息批处理
	 * params.put("mailInformation", mi);
	 * params.put("attachment", lstDoc);
	 * params.put("address", addressMap);
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String addMail(Map<String, Object> params) {
		// TODO Auto-generated method stub
		String result = "OK";
		if(params != null){
			try {
				MailInformation mi = new MailInformation();
				if(params.containsKey("mailInformation")){
					mi = (MailInformation) params.get("mailInformation");
				}else{
					result = "信息错误，发送失败";
					return result;
				}
				
				//信息内容入库
//				mi = this.mailInformationDao.add(mi);
				
				List<Document> lstDoc = new ArrayList<Document>();
				if(params.containsKey("mailInformation")){
					lstDoc = (List<Document>) params.get("attachment");
				}
				Map<String, Object> addressMap = new HashMap<String, Object>();
				if(params.containsKey("address")){
					addressMap = (Map<String, Object>) params.get("address");
				}else{
					result = "联系人信息错误，发送失败";
					return result;
				}
				
				if(lstDoc != null && lstDoc.size() > 0){
					for(Document doc : lstDoc){
						MailAttachment ma = new MailAttachment();
						ma.setDocument(doc);
						ma.setMailInformation(mi);
						mi.getMailAttachment().add(ma);//级联入库
					}
				}
				
				//处理联系人
				if(addressMap.containsKey("1")){
					Map<Integer, ProjectAdmin> paMap = (Map<Integer, ProjectAdmin>) addressMap.get("1");
					for (Integer key : paMap.keySet()) {  
						  
						MailConnection mc = new MailConnection();
						mc.setAddressee(key);
						mc.setAddresseeRole((short)1);
						mc.setMailInformation(mi);
						mc.setStatus((short)1);
						mi.getMailConnections().add(mc);//级联入库
					} 
				}
				if(addressMap.containsKey("2")){
					Map<Integer, TrainingAdmin> paMap = (Map<Integer, TrainingAdmin>) addressMap.get("2");
					for (Integer key : paMap.keySet()) {  
						  
						MailConnection mc = new MailConnection();
						mc.setAddressee(key);
						mc.setAddresseeRole((short)2);
						mc.setMailInformation(mi);
						mc.setStatus((short)1);
						mi.getMailConnections().add(mc);//级联入库
					} 
				}
				if(addressMap.containsKey("3")){
					Map<Integer, Teacher> paMap = (Map<Integer, Teacher>) addressMap.get("3");
					for (Integer key : paMap.keySet()) {  
						  
						MailConnection mc = new MailConnection();
						mc.setAddressee(key);
						mc.setAddresseeRole((short)3);
						mc.setMailInformation(mi);
						mc.setStatus((short)1);
						mi.getMailConnections().add(mc);//级联入库
					} 
				}
				if(addressMap.containsKey("4")){
					Map<Integer, ProjectExpert> paMap = (Map<Integer, ProjectExpert>) addressMap.get("4");
					for (Integer key : paMap.keySet()) {  
						  
						MailConnection mc = new MailConnection();
						mc.setAddressee(key);
						mc.setAddresseeRole((short)4);
						mc.setMailInformation(mi);
						mc.setStatus((short)1);
						mi.getMailConnections().add(mc);//级联入库
					} 
				}
				
				this.mailInformationDao.add(mi);
				
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
				result = "发送过程异常，发送失败";
				return result;
			}
		}else{
			result = "发送失败";
			return result;
		}
		
		return result;
	}
	
	
	
}
