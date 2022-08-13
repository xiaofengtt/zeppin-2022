package com.whaty.platform.entity.service.imp;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrEduMajorSiteFeeLevel;
import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.bean.PrStuChangeEdutype;
import com.whaty.platform.entity.bean.PrStuChangeMajor;
import com.whaty.platform.entity.bean.PrStuChangeSite;
import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.bean.PrTchProgramCourse;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.bean.SystemVariables;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.SystemApplyService;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class SystemApplyServiceImp implements SystemApplyService {
	private MyListDAO myListDao;
	private GeneralDao generalDao;
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.SystemApplyService#updateForCheck(java.util.List)
	 */
	public int updateForCheck(List<String> ids,boolean check)throws EntityException{
		try{
			for(String id:ids){
				SystemApply systemApply = (SystemApply)this.getGeneralDao().getById(id);
				if(systemApply.getEnumConstByFlagApplyStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "0"))){
					if(check)
						systemApply.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "1"));
					else
						systemApply.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "2"));
					systemApply.setCheckDate(new java.util.Date());
					this.getGeneralDao().save(systemApply);
				}else
					throw new EntityException("申请状态不是待审核状态，不能对其做这个操作！");
			}
		}catch(RuntimeException e){
			throw new EntityException("操作失败！");
		}
		return ids.size();
	}
	
	public int updateForCancel(List<String> ids,boolean check)throws EntityException{
		try{
			for(String id:ids){
				SystemApply systemApply = (SystemApply)this.getGeneralDao().getById(id);
				if(check){
					if(!systemApply.getEnumConstByFlagApplyStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "1")))
						throw new EntityException("申请状态不是申请通过状态，不能再被取消审核！");
				}else{
					if(!systemApply.getEnumConstByFlagApplyStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "2")))
						throw new EntityException("申请状态不是申请不通过状态，不能再被取消！");	
				}
				systemApply.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
				systemApply.setCheckDate(null);
				systemApply.setCheckNote("");
				this.getGeneralDao().save(systemApply);
			}
		}catch(RuntimeException e){
			throw new EntityException("操作失败！");
		}
		return ids.size();
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.SystemApplyService#updateForGraduation(java.util.List)
	 */
	public int updateForGraduation(List<String> ids,boolean degree) throws EntityException {
		try{
			for(String id:ids){
				SystemApply systemApply = (SystemApply)this.getGeneralDao().getById(id);
				if(systemApply.getEnumConstByFlagApplyStatus().getCode().equals("0")){
					systemApply.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "1"));
					PeStudent peStudent = systemApply.getPeStudent();
					if(degree){
						if(!peStudent.getEnumConstByFlagStudentStatus().getCode().equals("5")){
							throw new EntityException("请先通过学生的毕业审核...");
						}
						peStudent.setDegreeDate(new java.util.Date());
					}else{
						if(!peStudent.getEnumConstByFlagStudentStatus().getCode().equals("4")){
							throw new EntityException("有学生不是在籍状态不能准予毕业！");							
						}
						peStudent.setEnumConstByFlagStudentStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "5"));
						peStudent.setGraduationDate(new java.util.Date());
						SsoUser ssouser = peStudent.getSsoUser();
						ssouser.setEnumConstByFlagIsvalid(this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsvalid", "0"));
						ssouser = (SsoUser)this.getGeneralDao().save(ssouser);
						peStudent.setSsoUser(ssouser);
					}
					peStudent = (PeStudent)this.getGeneralDao().save(peStudent);
					systemApply.setPeStudent(peStudent);
					systemApply.setCheckDate(new java.util.Date());
					this.getGeneralDao().save(systemApply);
				}else
					throw new EntityException("申请状态不是未审核状态，不能对其做这个操作！");
			}
		}catch(RuntimeException e){
			throw new EntityException("操作失败！");
		}
		return ids.size();
	}
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.SystemApplyService#updateForCancelGraduation(java.util.List)
	 */
	public int updateForCancelGraduation(List<String> ids,boolean degree) throws EntityException {
		try{
			for(String id:ids){
				SystemApply systemApply = (SystemApply)this.getGeneralDao().getById(id);
				if(systemApply.getEnumConstByFlagApplyStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "1"))){
					systemApply.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
					PeStudent peStudent = systemApply.getPeStudent();
					if(degree){
						peStudent.setDegreeDate(null);
						peStudent.setDegreeCertificateNo(null);
					}else{
						if(!peStudent.getEnumConstByFlagStudentStatus().getCode().equals("5")){
							throw new EntityException("有学生不是毕业状态不能取消毕业！");							
						}
						DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
						dc.createAlias("enumConstByApplyType", "enumConstByApplyType").add(Restrictions.eq("enumConstByApplyType.code", "9"))
							.createAlias("enumConstByFlagApplyStatus", "enumConstByFlagApplyStatus").add(Restrictions.eq("enumConstByFlagApplyStatus.code", "1"))
							.createAlias("peStudent", "peStudent").add(Restrictions.eq("peStudent.id", peStudent.getId()));
						List list = this.getGeneralDao().getList(dc);
						if(list!=null&&list.size()>0){
							throw new EntityException("有学生已经通过学位申请了，请先取消他的学位！");			
						}
						peStudent.setEnumConstByFlagStudentStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "4"));
						peStudent.setGraduationCertificateNo(null);
						peStudent.setGraduationDate(null);
						SsoUser ssouser = peStudent.getSsoUser();
						ssouser.setEnumConstByFlagIsvalid(this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsvalid", "1"));
						ssouser = (SsoUser)this.getGeneralDao().save(ssouser);
						peStudent.setSsoUser(ssouser);
					}
					peStudent = (PeStudent)this.getGeneralDao().save(peStudent);
					systemApply.setPeStudent(peStudent);
					systemApply.setCheckDate(null);
					this.getGeneralDao().save(systemApply);
				}else
					throw new EntityException("申请状态不是已经审核状态，不能对其做这个操作！");
			}
		}catch(RuntimeException e){
			throw new EntityException("取消审核通过失败！");
		}
		return ids.size();
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.SystemApplyService#saveForApplyGraduationAndDegree(com.whaty.platform.entity.bean.PeStudent)
	 */
	public boolean saveForApplyGraduationAndDegree(PeStudent peStudent)
			throws EntityException {
		boolean b;
		try {
			SystemApply systemApply1 = new SystemApply();
			systemApply1.setPeStudent(peStudent);
			systemApply1.setApplyDate(new Date());
			systemApply1.setEnumConstByFlagApplyStatus(getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));		
			systemApply1.setEnumConstByApplyType(getGeneralDao().getEnumConstByNamespaceCode("ApplyType", "8"));
			this.getGeneralDao().save(systemApply1);
		} catch (Throwable e) {
			b = false;
			e.printStackTrace();
			throw new EntityException("毕业申请失败！");
		}
		try{
			SystemApply systemApply2 = new SystemApply();
			systemApply2.setPeStudent(peStudent);
			systemApply2.setApplyDate(new Date());
			systemApply2.setEnumConstByFlagApplyStatus(getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));		
			systemApply2.setEnumConstByApplyType(getGeneralDao().getEnumConstByNamespaceCode("ApplyType", "9"));
			this.getGeneralDao().save(systemApply2);
			b = true;
		} catch (Throwable e) {
			b = false;
			e.printStackTrace();
			throw new EntityException("学位申请失败！");
		}
		return b;
	}
	/**
	 * 评优申请审核通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateExcellentPass(List<String> ids) throws EntityException {
		try{
			for(String id:ids){
				SystemApply systemApply = (SystemApply)this.getGeneralDao().getById(id);
				if(systemApply.getEnumConstByFlagApplyStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "0"))){
						systemApply.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "1"));
					systemApply.setCheckDate(new java.util.Date());
					this.getGeneralDao().save(systemApply);
					
					systemApply.getPeStudent().setEnumConstByFlagAdvanced(
							this.getGeneralDao().getEnumConstByNamespaceCode("FlagAdvanced", "1"));
					this.getGeneralDao().save(systemApply.getPeStudent());
					
				}else
					throw new EntityException("只能操作未审核状态的学生！");
			}
		}catch(RuntimeException e){
			throw new EntityException("操作失败！");
		}
		return ids.size();
	}
	
	/**
	 * 评优申请审核 取消通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateExcellentCancelPass(List<String> ids) throws EntityException {
		try{
			for(String id:ids){
				SystemApply systemApply = (SystemApply)this.getGeneralDao().getById(id);
				if(systemApply.getEnumConstByFlagApplyStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "1"))){
						systemApply.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
					systemApply.setCheckDate(new java.util.Date());
					this.getGeneralDao().save(systemApply);
					
					systemApply.getPeStudent().setEnumConstByFlagAdvanced(
							this.getGeneralDao().getEnumConstByNamespaceCode("FlagAdvanced", "0"));
					this.getGeneralDao().save(systemApply.getPeStudent());
					
				}else
					throw new EntityException("只能操作申请通过状态的学生！");
			}
		}catch(RuntimeException e){
			throw new EntityException("操作失败！");
		}
		return ids.size();
	}
	
	/**
	 * 评优申请审核不通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateExcellentNoPass(List<String> ids) throws EntityException {
		try{
			for(String id:ids){
				SystemApply systemApply = (SystemApply)this.getGeneralDao().getById(id);
				if(systemApply.getEnumConstByFlagApplyStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "0"))){
						systemApply.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "2"));
					systemApply.setCheckDate(new java.util.Date());
					this.getGeneralDao().save(systemApply);
					
					systemApply.getPeStudent().setEnumConstByFlagAdvanced(
							this.getGeneralDao().getEnumConstByNamespaceCode("FlagAdvanced", "0"));
					this.getGeneralDao().save(systemApply.getPeStudent());
					
				}else
					throw new EntityException("只能操作未审核状态的学生！");
			}
		}catch(RuntimeException e){
			throw new EntityException("操作失败！");
		}
		return ids.size();
	}
	
	/**
	 * 评优申请审核 取消不通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateExcellentCancelNoPass(List<String> ids) throws EntityException {
		try{
			for(String id:ids){
				SystemApply systemApply = (SystemApply)this.getGeneralDao().getById(id);
				if(systemApply.getEnumConstByFlagApplyStatus().getCode().equals("2")){
						systemApply.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
					systemApply.setCheckDate(new java.util.Date());
					this.getGeneralDao().save(systemApply);
					
				}else
					throw new EntityException("只能操作申请不通过状态的学生！");
			}
		}catch(RuntimeException e){
			throw new EntityException("操作失败！");
		}
		return ids.size();
	}
	
	/**
	 * 统考免试申请审核通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateUniteExamPass(List<String> ids,String type)throws EntityException {
		try{
			
			for(String id:ids){
				SystemApply systemApply = (SystemApply)this.getGeneralDao().getById(id);
				if(systemApply.getEnumConstByFlagApplyStatus().getCode().equals("0")){
						systemApply.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "1"));
					systemApply.setCheckDate(new java.util.Date());
					this.getGeneralDao().save(systemApply);
					if (type.equals("A")) {
						systemApply.getPeStudent().setEnumConstByScoreUniteEnglishA(
								this.getGeneralDao().getEnumConstByNamespaceCode("ScoreUniteEnglishA", "0"));
					} else if (type.equals("B")) {
						systemApply.getPeStudent().setEnumConstByScoreUniteEnglishB(
								this.getGeneralDao().getEnumConstByNamespaceCode("ScoreUniteEnglishB", "0"));
					} else if (type.equals("C")) {
						systemApply.getPeStudent().setEnumConstByScoreUniteComputer(
								this.getGeneralDao().getEnumConstByNamespaceCode("ScoreUniteComputer", "0"));
					} else if (type.equals("D")) {
						if (systemApply.getApplyNote().equals("大学英语（A）")) {
							systemApply.getPeStudent().setEnumConstByScoreUniteEnglishA(
									this.getGeneralDao().getEnumConstByNamespaceCode("ScoreUniteEnglishA", "0"));
						} else if (systemApply.getApplyNote().equals("大学英语（B）")) {
							systemApply.getPeStudent().setEnumConstByScoreUniteEnglishB(
									this.getGeneralDao().getEnumConstByNamespaceCode("ScoreUniteEnglishB", "0"));
						} else if (systemApply.getApplyNote().equals("计算机应用基础")) {
							systemApply.getPeStudent().setEnumConstByScoreUniteComputer(
									this.getGeneralDao().getEnumConstByNamespaceCode("ScoreUniteComputer", "0"));
						} else if (systemApply.getApplyNote().equals("大学英语（C）")) {
							systemApply.getPeStudent().setEnumConstByScoreUniteEnglishC(
									this.getGeneralDao().getEnumConstByNamespaceCode("ScoreUniteEnglishC", "0"));
						} else if (systemApply.getApplyNote().equals("大学语文（B）")) {
							systemApply.getPeStudent().setEnumConstByScoreUniteYuwen(
									this.getGeneralDao().getEnumConstByNamespaceCode("ScoreUniteYuwen", "0"));
						} else if (systemApply.getApplyNote().equals("高等数学（B）")) {
							systemApply.getPeStudent().setEnumConstByScoreUniteShuxue(
									this.getGeneralDao().getEnumConstByNamespaceCode("ScoreUniteShuxue", "0"));
						} 
					}
					this.getGeneralDao().save(systemApply.getPeStudent());
					
				}else
					throw new EntityException("只能操作未审核状态的学生！");
			}
		}catch(RuntimeException e){
			throw new EntityException("操作失败！");
		}
		return ids.size();
	}
	
	/**
	 * 统考免试申请审核取消通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateUniteExamCancelPass(List<String> ids,String type)throws EntityException{
		try{
			
			for(String id:ids){
				SystemApply systemApply = (SystemApply)this.getGeneralDao().getById(id);
				if(systemApply.getEnumConstByFlagApplyStatus().getCode().equals("1")){
						systemApply.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
					systemApply.setCheckDate(new java.util.Date());
					this.getGeneralDao().save(systemApply);
					if (type.equals("A")) {
						systemApply.getPeStudent().setEnumConstByScoreUniteEnglishA(null);
					} else if (type.equals("B")) {
						systemApply.getPeStudent().setEnumConstByScoreUniteEnglishB(null);
					} else if (type.equals("C")) {
						systemApply.getPeStudent().setEnumConstByScoreUniteComputer(null);
					} else if (type.equals("D")) {
						if (systemApply.getApplyNote().equals("大学英语（A）")) {
							systemApply.getPeStudent().setEnumConstByScoreUniteEnglishA(null);
						} else if (systemApply.getApplyNote().equals("大学英语（B）")) {
							systemApply.getPeStudent().setEnumConstByScoreUniteEnglishB(null);
						} else if (systemApply.getApplyNote().equals("计算机应用基础")) {
							systemApply.getPeStudent().setEnumConstByScoreUniteComputer(null);
						} else if (systemApply.getApplyNote().equals("大学英语（C）")) {
							systemApply.getPeStudent().setEnumConstByScoreUniteEnglishC(null);
						} else if (systemApply.getApplyNote().equals("大学语文（B）")) {
							systemApply.getPeStudent().setEnumConstByScoreUniteYuwen(null);
						} else if (systemApply.getApplyNote().equals("高等数学（B）")) {
							systemApply.getPeStudent().setEnumConstByScoreUniteShuxue(null);
						} 
					}
					systemApply.setCheckDate(null);
					this.getGeneralDao().save(systemApply.getPeStudent());
					
				}else
					throw new EntityException("只能操作审核状态的学生！");
			}
		}catch(RuntimeException e){
			throw new EntityException("操作失败！");
		}
		return ids.size();
	}
	
	/**
	 * 统考免试申请审核不通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateUniteExamNoPass(List<String> ids)throws EntityException{
		try{
			for(String id:ids){
				SystemApply systemApply = (SystemApply)this.getGeneralDao().getById(id);
				if(systemApply.getEnumConstByFlagApplyStatus().getCode().equals("0")){
						systemApply.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "2"));
					systemApply.setCheckDate(new java.util.Date());
					this.getGeneralDao().save(systemApply);
					
				}else
					throw new EntityException("只能操作未审核状态的学生！");
			}
		}catch(RuntimeException e){
			throw new EntityException("操作失败！");
		}
		return ids.size();
	}
	
	/**
	 * 统考免试申请审核取消不通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateUniteExamCancelNoPass(List<String> ids)throws EntityException{
		try{
			for(String id:ids){
				SystemApply systemApply = (SystemApply)this.getGeneralDao().getById(id);
				if(systemApply.getEnumConstByFlagApplyStatus().getCode().equals("2")){
						systemApply.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
					systemApply.setCheckDate(null);
					this.getGeneralDao().save(systemApply);
					
				}else
					throw new EntityException("只能操作申请不通过状态的学生！");
			}
		}catch(RuntimeException e){
			throw new EntityException("操作失败！");
		}
		return ids.size();
	}
	
	/**
	 * 课程免考申请审核通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateAvoidPass(List<String> ids)throws EntityException {
		try{
			UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
			for(String id:ids){
				this.getGeneralDao().setEntityClass(SystemApply.class);
				SystemApply systemApply = (SystemApply)this.getGeneralDao().getById(id);
				if(systemApply.getEnumConstByFlagApplyStatus().getCode().equals("0")){
						systemApply.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "1"));
					systemApply.setCheckDate(new java.util.Date());
					this.getGeneralDao().save(systemApply);
					
					String note = systemApply.getApplyNote();
					if (note!=null&& note.length()>0){
						String[] strs = note.split(",");
						if (strs.length>0) {
							
							PrTchProgramCourse program = null;
							this.getGeneralDao().setEntityClass(PrTchProgramCourse.class);
							program = (PrTchProgramCourse)this.getGeneralDao()
								.getById(strs[0].trim());
							
							//检查学生是否选过这门课
							DetachedCriteria dcElective = DetachedCriteria.forClass(PrTchStuElective.class);
							dcElective.add(Restrictions.eq("peStudent", systemApply.getPeStudent()));
							dcElective.createCriteria("prTchOpencourse", "prTchOpencourse")
								.add(Restrictions.eq("peTchCourse", program.getPeTchCourse()));
							List electiveList = this.getGeneralDao().getList(dcElective);
							if(electiveList!=null&&electiveList.size()>0){
								throw new EntityException("学生 "+systemApply.getPeStudent().getTrueName()+"已经选过"
											+program.getPeTchCourse().getName()+"，无法审核通过！");
							}
							
							if (program!=null) {
								DetachedCriteria dc = DetachedCriteria.forClass(PrTchOpencourse.class);
								dc.createAlias("peSemester", "peSemester");
								dc.add(Restrictions.eq("peSemester.flagNextActive", "1"));
								dc.add(Restrictions.eq("peTchCourse", program.getPeTchCourse()));
								List<PrTchOpencourse> list = this.getGeneralDao().getList(dc);
								if (list==null ||list.size()==0) {
									throw new EntityException("未取得学生 "+systemApply.getPeStudent().getTrueName()+"所申请课程的信息！");
								}
								
								PrTchStuElective elective = new PrTchStuElective();
								elective.setElectiveDate(new Date());
								elective.setEnumConstByFlagElectiveAdmission(
										this.getGeneralDao().getEnumConstByNamespaceCode("FlagElectiveAdmission", "1"));
								elective.setEnumConstByFlagScoreStatus(
										this.getGeneralDao().getEnumConstByNamespaceCode("FlagScoreStatus", "5"));
								elective.setPeStudent(systemApply.getPeStudent());
								elective.setPrTchOpencourse(list.get(0));
								elective.setPrTchProgramCourse(program);
								
								if (userSession!=null) {
									elective.setSsoUser(userSession.getSsoUser());
								}
								DetachedCriteria dcScore = DetachedCriteria.forClass(SystemVariables.class);
								dcScore.add(Restrictions.eq("name", "examAvoidScore"));
								List<SystemVariables> listScore = this.getGeneralDao().getList(dcScore);
								double score = 75.0;
								if (listScore!=null&&listScore.size()>0) {
									score = Double.parseDouble(listScore.get(0).getValue());
								}
								elective.setScoreExam(score);
								elective.setScoreHomework(score);
								elective.setScoreSystem(score);
								elective.setScoreTotal(score);
								elective.setScoreUsual(score);
								this.getGeneralDao().save(elective);
								
								/**
								 * 以下是扣费操作
								 */
								PeStudent student = systemApply.getPeStudent();
								Double feePercredit = student.getPeFeeLevel().getFeePercredit();
								Double courseCredit = program.getCredit();
								Double feeBalance = student.getFeeBalance();
								Double oweFeeLimit = student.getPeFeeLevel().getOweFeeLimit();
								double feeInactive = student.getFeeInactive();
								String s = this.getMyListDao().getSysValueByName("examAvoidFee");
								double fee = feePercredit*courseCredit*(Double.parseDouble(s));
								feeBalance = Const.sub(feeBalance, fee);
								feeInactive = Const.sub(feeInactive, fee);
								student.setFeeBalance(feeBalance);
								student.setFeeInactive(feeInactive);
								student = (PeStudent)this.generalDao.save(student);
								
								//向费用明细表插入数据
								PrFeeDetail prFeeDetail = new PrFeeDetail();
								prFeeDetail.setPeStudent(student);
								EnumConst enumConstByFlagFeeType = this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeType", "10");
								prFeeDetail.setEnumConstByFlagFeeType(enumConstByFlagFeeType);
								EnumConst enumConstByFlagFeeCheck = this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "2");
								prFeeDetail.setEnumConstByFlagFeeCheck(enumConstByFlagFeeCheck);
								prFeeDetail.setFeeAmount(-fee);
								prFeeDetail.setCreditAmount(feeBalance);
								prFeeDetail.setInputDate(new Date());
								prFeeDetail.setNote("免修" + program.getPeTchCourse().getName() + "扣费");
								this.getGeneralDao().save(prFeeDetail);
								
								if ((feeBalance+oweFeeLimit) < 0) {
									throw new EntityException(" 学生 " + student.getName() + " 余额不足 " );
									
								}
							
							} else {
								throw new EntityException("未取得学生 "+systemApply.getPeStudent().getTrueName()+"所申请课程的信息！");
							}
						}
					}
					
				}else
					throw new EntityException("只能操作未审核状态的学生！");
			}
		}catch(EntityException e){
			this.getGeneralDao().setEntityClass(SystemApply.class);
			throw new EntityException(e.getMessage());
		}
		this.getGeneralDao().setEntityClass(SystemApply.class);
		return ids.size();
	}
	
	
	/**
	 * 课程免考申请审核 取消通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateAvoidCancelPass(List<String> ids)throws EntityException {
		try{
			
			for(String id:ids){
				SystemApply systemApply = (SystemApply)this.getGeneralDao().getById(id);
				if(systemApply.getEnumConstByFlagApplyStatus().getCode().equals("1")){
						systemApply.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
					systemApply.setCheckDate(null);
					this.getGeneralDao().save(systemApply);
					
					String note = systemApply.getApplyNote();
					if (note!=null&& note.length()>0){
						String[] strs = note.split(",");
						if (strs.length>0) {
					DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
					dc.createAlias("enumConstByFlagScoreStatus", "enumConstByFlagScoreStatus");
					dc.add(Restrictions.eq("enumConstByFlagScoreStatus.code", "5"));
					dc.add(Restrictions.eq("prTchProgramCourse.id", strs[0]));
					List<PrTchStuElective> list = this.getGeneralDao().getList(dc);
					if (list!=null&&list.size()>0) {
						this.getGeneralDao().delete(list.get(0));
						/**
						 * 为学生退费
						 */
						try {
							PeStudent student = systemApply.getPeStudent();
							Double feePercredit = student.getPeFeeLevel().getFeePercredit();
							Double courseCredit = list.get(0).getPrTchProgramCourse().getCredit();
							Double feeBalance = student.getFeeBalance();
							String s = this.getMyListDao().getSysValueByName("examAvoidFee");
							double fee = feePercredit*courseCredit*(Double.parseDouble(s));
							feeBalance  = Const.add(feeBalance, fee);
							student.setFeeBalance(feeBalance);
							this.generalDao.save(student);
							
							//向费用明细表插入数据
							PrFeeDetail prFeeDetail = new PrFeeDetail();
							prFeeDetail.setPeStudent(student);
							EnumConst enumConstByFlagFeeType = this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeType", "11");
							prFeeDetail.setEnumConstByFlagFeeType(enumConstByFlagFeeType);
							EnumConst enumConstByFlagFeeCheck = this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "2");
							prFeeDetail.setEnumConstByFlagFeeCheck(enumConstByFlagFeeCheck);
							prFeeDetail.setFeeAmount(+fee);
							prFeeDetail.setCreditAmount(feeBalance);
							prFeeDetail.setInputDate(new Date());
							prFeeDetail.setNote("取消免修" + list.get(0).getPrTchProgramCourse().getPeTchCourse().getName() + "退费");
							this.getGeneralDao().save(prFeeDetail);
						} catch (Exception e) {
							e.printStackTrace();
							throw new EntityException("取消审核失败");
						}
						
					}
						}
					}
					
				}else
					throw new EntityException("只能操作审核状态的学生！");
			}
		}catch(RuntimeException e){
			throw new EntityException("操作失败！");
		}
		return ids.size();
	}
	
	/**
	 * 课程免考申请审核不通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateAvoidNoPass(List<String> ids)throws EntityException {
		try{
			for(String id:ids){
				SystemApply systemApply = (SystemApply)this.getGeneralDao().getById(id);
				if(systemApply.getEnumConstByFlagApplyStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "0"))){
						systemApply.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "2"));
					systemApply.setCheckDate(new java.util.Date());
					this.getGeneralDao().save(systemApply);
					
				}else
					throw new EntityException("只能操作未审核状态的学生！");
			}
		}catch(RuntimeException e){
			throw new EntityException("操作失败！");
		}
		return ids.size();
	}
	
	/**
	 * 课程免考申请审核 取消不通过
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public int updateAvoidCancelNoPass(List<String> ids)throws EntityException {
		try{
			for(String id:ids){
				SystemApply systemApply = (SystemApply)this.getGeneralDao().getById(id);
				if(systemApply.getEnumConstByFlagApplyStatus().getCode().equals("2")){
						systemApply.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
					systemApply.setCheckDate(null);
					this.getGeneralDao().save(systemApply);
					
				}else
					throw new EntityException("只能操作申请不通过状态的学生！");
			}
		}catch(RuntimeException e){
			throw new EntityException("操作失败！");
		}
		return ids.size();
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public int updateForPaperReapply(List<String> ids, boolean apply) throws EntityException {
		try{
			if (apply == true) {
				for(String id : ids){
					SystemApply sa = (SystemApply) this.getGeneralDao().getById(id);
					sa.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "1"));
					sa.setCheckDate(new Date());
				}
			} else {
				for (String id : ids) {
					SystemApply sa = (SystemApply) this.getGeneralDao().getById(id);
					sa.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "2"));
					sa.setCheckDate(new Date());
				}
			}
		}catch(RuntimeException e){
			throw new EntityException("操作失败！");
		}
		return ids.size();
	}


	public int updateForDegreeEnglishApply(List<String> ids, boolean apply) throws EntityException {
		try{
			if (apply == true) {
				for(String id : ids){
					SystemApply sa = (SystemApply) this.getGeneralDao().getById(id);
					sa.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "1"));
					sa.setCheckDate(new Date());
				}
			} else {
				for (String id : ids) {
					SystemApply sa = (SystemApply) this.getGeneralDao().getById(id);
					sa.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "2"));
					sa.setCheckDate(new Date());
				}
			}
		}catch(RuntimeException e){
			throw new EntityException("操作失败！");
		}
		return ids.size();
	}

	public int updateForChangeStudentApply(List<String> ids, boolean apply) throws EntityException {
		try{
			if (apply == true) {
				for(String id : ids){
					SystemApply sa = (SystemApply) this.getGeneralDao().getById(id);
					if(sa.getEnumConstByFlagApplyStatus().getCode().equals("1")){
						throw new EntityException(sa.getPeStudent().getTrueName()+"的学籍异动申请已经审核通过，无法再次操作。");
					}
					sa.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "1"));
					sa.setCheckDate(new Date());
					this.getGeneralDao().save(sa);
					String type = sa.getEnumConstByApplyType().getCode();
					PeStudent student = sa.getPeStudent();
					String note = sa.getApplyNote();
					if(note==null||note.length()==0||note.indexOf(":")<0){
						throw new EntityException(student.getTrueName()+"的异动新学籍为空，无法通过审核。");
					}
					String newStatusName = note.substring(0,note.indexOf(":"));
					if(type.equals("12")){
						PeSite oldSite = student.getPeSite();
						if(oldSite.getName().equals(newStatusName)){
							throw new EntityException(student.getTrueName()+"的异动新学籍与所在学籍相同，无法通过审核。");
						}
						DetachedCriteria dc = DetachedCriteria.forClass(PeSite.class);
						dc.add(Restrictions.eq("name", newStatusName));
						List<PeSite> list = this.getGeneralDao().getList(dc);
						if(list==null||list.isEmpty()){
							throw new EntityException(student.getTrueName()+"的异动新学习中心不存在，无法通过审核。");
						}
						PrStuChangeSite changeSite = new PrStuChangeSite();
						changeSite.setCDate(new Date());
						changeSite.setNote(sa.getApplyInfo());
						changeSite.setPeStudent(student);
						changeSite.setPeSiteByFkOldSiteId(oldSite);
						changeSite.setPeSiteByFkNewSiteId(list.get(0));
						this.getGeneralDao().save(changeSite);
						student.setPeSite(list.get(0));
						
					}else if(type.equals("13")){
						PeEdutype oldEdutype = student.getPeEdutype();
						if(oldEdutype.getName().equals(newStatusName)){
							throw new EntityException(student.getTrueName()+"的异动新学籍与所在学籍相同，无法通过审核。");
						}
						DetachedCriteria dc = DetachedCriteria.forClass(PeEdutype.class);
						dc.add(Restrictions.eq("name", newStatusName));
						List<PeEdutype> list = this.getGeneralDao().getList(dc);
						if(list==null||list.isEmpty()){
							throw new EntityException(student.getTrueName()+"的异动新层次不存在，无法通过审核。");
						}
						PrStuChangeEdutype changeEdutype = new PrStuChangeEdutype();
						changeEdutype.setCDate(new Date());
						changeEdutype.setNote(sa.getApplyInfo());
						changeEdutype.setPeStudent(student);
						changeEdutype.setPeEdutypeByFkOldEdutypeId(oldEdutype);
						changeEdutype.setPeEdutypeByFkNewEdutypeId(list.get(0));
						this.getGeneralDao().save(changeEdutype);
						student.setPeEdutype(list.get(0));
					}else if(type.equals("14")){
						PeMajor oldMajor = student.getPeMajor();
						if(oldMajor.getName().equals(newStatusName)){
							throw new EntityException(student.getTrueName()+"的异动新学籍与所在学籍相同，无法通过审核。");
						}
						DetachedCriteria dc = DetachedCriteria.forClass(PeMajor.class);
						dc.add(Restrictions.eq("name", newStatusName));
						List<PeMajor> list = this.getGeneralDao().getList(dc);
						if(list==null||list.isEmpty()){
							throw new EntityException(student.getTrueName()+"的异动新专业不存在，无法通过审核。");
						}
						PrStuChangeMajor changeMajor = new PrStuChangeMajor();
						changeMajor.setCDate(new Date());
						changeMajor.setNote(sa.getApplyInfo());
						changeMajor.setPeStudent(student);
						changeMajor.setPeMajorByFkOldMajorId(oldMajor);
						changeMajor.setPeMajorByFkNewMajorId(list.get(0));
						this.getGeneralDao().save(changeMajor);
						student.setPeMajor(list.get(0));

					}
					DetachedCriteria dcFee = DetachedCriteria.forClass(PrEduMajorSiteFeeLevel.class);
					dcFee.createAlias("peMajor", "peMajor").add(Restrictions.eq("peMajor.id", student.getPeMajor().getId()))
						.createAlias("peSite", "peSite").add(Restrictions.eq("peSite.id", student.getPeSite().getId()))
						.createAlias("peEdutype", "peEdutype").add(Restrictions.eq("peEdutype.id", student.getPeEdutype().getId()));
					student.setPeFeeLevel(((PrEduMajorSiteFeeLevel)this.getGeneralDao().getList(dcFee).remove(0)).getPeFeeLevel());	
					student = (PeStudent)this.getGeneralDao().save(student);
				}
			} else {
				for (String id : ids) {
					SystemApply sa = (SystemApply) this.getGeneralDao().getById(id);
					if(sa.getEnumConstByFlagApplyStatus().getCode().equals("1")){
						throw new EntityException(sa.getPeStudent().getTrueName()+"的学籍异动申请已经审核通过，无法设置为审核不通过。");
					}
					sa.setEnumConstByFlagApplyStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagApplyStatus", "2"));
					sa.setCheckDate(new Date());
				}
			}
		}catch(RuntimeException e){
			throw new EntityException("操作失败！");
		}
		return ids.size();
	}

	public MyListDAO getMyListDao() {
		return myListDao;
	}

	public void setMyListDao(MyListDAO myListDao) {
		this.myListDao = myListDao;
	}






	
}
