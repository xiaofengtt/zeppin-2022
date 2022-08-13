package com.whaty.platform.entity.service.imp.fee;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeFeeBatch;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.fee.PeFeeBatchService;
import com.whaty.platform.entity.service.imp.studentStatus.PeStudentSeriveImp;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeFeeBatchServiceImp implements PeFeeBatchService {
	private GeneralDao generalDao;
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.imp.fee.PeFeeBatchService#report(java.util.List)
	 */
	public int updateForReport(List ids) throws EntityException{
		try{
			EnumConst enumConstFlagFeeCheck = this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "1");
			EnumConst oldEnumConstFlagFeeCheck = this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "0");
			for(Object id:ids){
				String peFeeBatch_id = (String)id;
				PeFeeBatch peFeeBatch = (PeFeeBatch)this.getGeneralDao().getById(peFeeBatch_id);
				if(!peFeeBatch.getEnumConstByFlagFeeCheck().equals(oldEnumConstFlagFeeCheck)){
					throw new EntityException("所要上报的交费信息已经上报过，不能重复上报！");
				}
				//上报人
				UserSession us = (UserSession)ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
				String userLoginType = "";//保存管理员类型
				if (us!=null) {
					userLoginType = us.getUserLoginType();
				}
				if(userLoginType.equals("2")){
					DetachedCriteria dc = DetachedCriteria.forClass(PeSitemanager.class);
					dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
					try {
						peFeeBatch.setPeSitemanager((PeSitemanager)this.getGeneralDao().getList(dc).get(0));
					} catch (Exception e) {
						e.printStackTrace();
						peFeeBatch.setPeSitemanager(null);
					}
				} else {
					peFeeBatch.setPeSitemanager(null);
				}
				
				
				peFeeBatch.setEnumConstByFlagFeeCheck(enumConstFlagFeeCheck);
				peFeeBatch.setInputDate(new java.util.Date());
				peFeeBatch = (PeFeeBatch)this.getGeneralDao().save(peFeeBatch);
				java.util.Iterator prFeeDetails = peFeeBatch.getPrFeeDetails().iterator();
				while(prFeeDetails.hasNext()){
					PrFeeDetail prFeeDetail = (PrFeeDetail)prFeeDetails.next();
					prFeeDetail.setEnumConstByFlagFeeCheck(enumConstFlagFeeCheck);
					prFeeDetail = (PrFeeDetail)this.getGeneralDao().save(prFeeDetail);
					if(prFeeDetail.getEnumConstByFlagFeeType().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeType", "0"))){
						PeStudent peStudent = prFeeDetail.getPeStudent();
						if(peStudent.getEnumConstByFlagStudentStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "1"))){
							peStudent.setEnumConstByFlagStudentStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "2"));
							peStudent = (PeStudent)this.getGeneralDao().save(peStudent);
						}else{
							throw new EntityException("学生"+peStudent.getTrueName()+"不是已交费状态，不能上报！");
						}
					}
				}
			}
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new EntityException("上报交费批次失败...");
		}
		return ids.size();
	}
	

	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.fee.PeFeeBatchService#updateCancelReport(java.util.List)
	 */
	public int updateCancelReport(List ids) throws EntityException{
		try{
			EnumConst oldEnumConstFlagFeeCheck = this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "1");
			EnumConst enumConstFlagFeeCheck = this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "0");
			for(Object id:ids){
				PeFeeBatch peFeeBatch = (PeFeeBatch)this.getGeneralDao().getById(id.toString());
				if(!peFeeBatch.getEnumConstByFlagFeeCheck().equals(oldEnumConstFlagFeeCheck)){
					throw new EntityException("所要取消上报的交费信息不是已经上报的交费信息，不能取消上报！");
				}
				//上报人
				peFeeBatch.setPeSitemanager(null);
				peFeeBatch.setEnumConstByFlagFeeCheck(enumConstFlagFeeCheck);
				peFeeBatch.setInputDate(null);
				peFeeBatch = (PeFeeBatch)this.getGeneralDao().save(peFeeBatch);
				java.util.Iterator prFeeDetails = peFeeBatch.getPrFeeDetails().iterator();
				while(prFeeDetails.hasNext()){
					PrFeeDetail prFeeDetail = (PrFeeDetail)prFeeDetails.next();
					prFeeDetail.setEnumConstByFlagFeeCheck(enumConstFlagFeeCheck);
					prFeeDetail = (PrFeeDetail)this.getGeneralDao().save(prFeeDetail);
					if(prFeeDetail.getEnumConstByFlagFeeType().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeType", "0"))){
						PeStudent peStudent = prFeeDetail.getPeStudent();
						if(peStudent.getEnumConstByFlagStudentStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "2"))){
							peStudent.setEnumConstByFlagStudentStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "1"));
							peStudent = (PeStudent)this.getGeneralDao().save(peStudent);
						}else{
							throw new EntityException("学生"+peStudent.getTrueName()+"不是已上报状态，不能取消上报！");
						}
					}
				}
			}
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new EntityException("取消上报失败...");
		}
		return ids.size();
	}
	
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}


	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.fee.PeFeeBatchService#updateCancelCheck(java.util.List)
	 */
	public int updateCancelCheck(List ids) throws EntityException {
		try{
			EnumConst enumConstFlagFeeCheck = this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "1");
			EnumConst oldEnumConstFlagFeeCheck = this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "2");
			for(Object id:ids){
				PeFeeBatch peFeeBatch = (PeFeeBatch)this.getGeneralDao().getById(id.toString());
				if(!peFeeBatch.getEnumConstByFlagFeeCheck().equals(oldEnumConstFlagFeeCheck)){
					throw new EntityException("所要取消审核的交费批次还没有被审核，不能取消审核！");
				}
				peFeeBatch.setEnumConstByFlagFeeCheck(enumConstFlagFeeCheck);
				peFeeBatch = (PeFeeBatch)this.getGeneralDao().save(peFeeBatch);
				java.util.Iterator prFeeDetails = peFeeBatch.getPrFeeDetails().iterator();
				while(prFeeDetails.hasNext()){
					PrFeeDetail prFeeDetail = (PrFeeDetail)prFeeDetails.next();
					prFeeDetail.setEnumConstByFlagFeeCheck(enumConstFlagFeeCheck);
					prFeeDetail = (PrFeeDetail)this.getGeneralDao().save(prFeeDetail);
					PeStudent peStudent = prFeeDetail.getPeStudent();
					if(prFeeDetail.getEnumConstByFlagFeeType().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeType", "0"))){
						if(peStudent.getEnumConstByFlagStudentStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "4"))){
							peStudent.setEnumConstByFlagStudentStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "2"));
							peStudent.setRecruitDate(null);
							/**
							 * 以下是取消注册
							 */
							SsoUser ssoUser = peStudent.getSsoUser();
							peStudent.setSsoUser(null);
							if(ssoUser!=null){
							this.getGeneralDao().delete(ssoUser);
							}
							
							peStudent.setRegNo("");
							if(peStudent.getPrStudentInfo()!=null){
							peStudent.setName(peStudent.getPrStudentInfo().getCardNo()+"/"+peStudent.getTrueName());
							}else {
								peStudent.setName("/"+peStudent.getTrueName());
							}
							peStudent.setEnumConstByFlagStudentStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "2"));
							peStudent = (PeStudent)this.getGeneralDao().save(peStudent);	
							
							/**
							 * 以上是取消注册
							 */							
						}else{
							throw new EntityException("学生"+peStudent.getTrueName()+"状态异常，不能取消审核！");
						}
					}
					peStudent.setFeeBalance(peStudent.getFeeBalance()-prFeeDetail.getFeeAmount());
					peStudent = (PeStudent)this.getGeneralDao().save(peStudent);
					//清除生效时的账户余额
					prFeeDetail.setCreditAmount(0d);
					prFeeDetail = (PrFeeDetail)this.getGeneralDao().save(prFeeDetail);
				}
			}
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new EntityException("取消审核失败...");
		}
		return ids.size();
	}


	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.fee.PeFeeBatchService#updateForCheck(java.util.List)
	 */
	public int updateForCheck(List ids) throws EntityException {
		try{
			java.util.Map<String,String> regNoSerialMap = new java.util.HashMap<String,String>();
			
			EnumConst oldEnumConstFlagFeeCheck = this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "1");
			EnumConst enumConstFlagFeeCheck = this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "2");
			for(Object id:ids){
				PeFeeBatch peFeeBatch = (PeFeeBatch)this.getGeneralDao().getById(id.toString());
				if(!peFeeBatch.getEnumConstByFlagFeeCheck().equals(oldEnumConstFlagFeeCheck)){
					throw new EntityException("所要审核通过的交费批次不是已经上报的交费批次，不能审核通过！");
				}
				peFeeBatch.setEnumConstByFlagFeeCheck(enumConstFlagFeeCheck);
				peFeeBatch = (PeFeeBatch)this.getGeneralDao().save(peFeeBatch);
				java.util.Iterator prFeeDetails = peFeeBatch.getPrFeeDetails().iterator();
				while(prFeeDetails.hasNext()){
					PrFeeDetail prFeeDetail = (PrFeeDetail)prFeeDetails.next();
//					if(prFeeDetail.getInvoiceNo()==null||prFeeDetail.getInvoiceNo().length()<=0){
//						throw new EntityException("所要审核通过的交费批次中还有学生没有收据发票号，不能审核通过！");
//					}
					prFeeDetail.setEnumConstByFlagFeeCheck(enumConstFlagFeeCheck);
					prFeeDetail = (PrFeeDetail)this.getGeneralDao().save(prFeeDetail);
					PeStudent peStudent = prFeeDetail.getPeStudent();
					if(prFeeDetail.getEnumConstByFlagFeeType().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeType", "0"))){
						if(peStudent.getEnumConstByFlagStudentStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "2"))){
							peStudent.setEnumConstByFlagStudentStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "3"));
							peStudent.setRecruitDate(new java.util.Date());
							
							/**
							 * 以下学生注册
							 */
							String halfRegNo = "W"+peStudent.getPeSite().getCode()+peStudent.getPeGrade().getCode()
							+peStudent.getPeEdutype().getCode()+peStudent.getPeMajor().getCode();
					if(regNoSerialMap==null||regNoSerialMap.size()<=0){
						regNoSerialMap.put(halfRegNo, this.createRegNoSerial(halfRegNo));						
					}else{
						String regNoSerial = regNoSerialMap.get(halfRegNo);
						if(regNoSerial==null||regNoSerial.length()<4){
							regNoSerialMap.put(halfRegNo,  this.createRegNoSerial(halfRegNo));
						}else{
							int serial = Integer.parseInt(regNoSerial);
							serial ++ ;
							regNoSerial = serial+"";
							while(regNoSerial.length()<4){
								regNoSerial = "0"+regNoSerial;
							}
							regNoSerialMap.put(halfRegNo,regNoSerial);
						}
					}
					String regNo = halfRegNo + regNoSerialMap.get(halfRegNo);
					SsoUser ssoUser = new SsoUser();
					ssoUser.setLoginId(regNo);
					ssoUser.setPassword(regNo.substring(regNo.length()-6));
					DetachedCriteria dc = DetachedCriteria.forClass(PePriRole.class);
					dc.createAlias("enumConstByFlagRoleType", "enumConstByFlagRoleType")
						.add(Restrictions.eq("enumConstByFlagRoleType.code", "0"));
					ssoUser.setPePriRole((PePriRole)this.getGeneralDao().getList(dc).remove(0));
					ssoUser.setLoginNum((long)0);
					ssoUser.setEnumConstByFlagIsvalid(this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsvalid", "1"));
					ssoUser = (SsoUser) this.getGeneralDao().save(ssoUser);
					
					peStudent.setRegNo(regNo);
					peStudent.setName(regNo+"/"+peStudent.getTrueName());
					peStudent.setEnumConstByFlagStudentStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "4"));
					peStudent.setSsoUser(ssoUser);
					peStudent = (PeStudent)this.getGeneralDao().save(peStudent);					
					/**
					 * 以上学生注册
					 */
						}else{
							throw new EntityException("学生"+peStudent.getTrueName()+"不是已上报状态，无法审核通过！");
						}
					}
					peStudent.setFeeBalance(prFeeDetail.getFeeAmount()+peStudent.getFeeBalance());
					if(peStudent.getFeeBalance()>=100000000l){
						throw new EntityException("学生"+peStudent.getTrueName()+"账户余额超出最大限制，无法审核通过！");
					}
					peStudent = (PeStudent)this.getGeneralDao().save(peStudent);	
					//保存生效时的账户余额
					prFeeDetail.setCreditAmount(peStudent.getFeeBalance());
					prFeeDetail = (PrFeeDetail)this.getGeneralDao().save(prFeeDetail);
				}
			}
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new EntityException("审核通过失败!");
		}
		return ids.size();
	}
	
	public String createRegNoSerial(String halfRegNo)throws EntityException{
		String regNoSerial = "";		
		String sql = "select substr(max(reg_No),length('"+halfRegNo+"')+1,4) from pe_student where reg_No like '"+halfRegNo+"%'";
		try{
			java.util.List list= this.getGeneralDao().getBySQL(sql);
			if(list==null||list.size()<=0){
				regNoSerial = "0001";
			}else{
				Object o = list.remove(0);
				if(o==null||o.equals(null)){
					regNoSerial = "0001";
				}else{
					int i = Integer.parseInt(o.toString());
					i++;
					regNoSerial = i+"";
					while(regNoSerial.length()<4){
						regNoSerial = "0"+regNoSerial;
					}
				}
			}
		}catch(Throwable e){
			e.printStackTrace();
			throw new EntityException("学生的学号生成失败...");
		}
		return regNoSerial;		
	}
}
