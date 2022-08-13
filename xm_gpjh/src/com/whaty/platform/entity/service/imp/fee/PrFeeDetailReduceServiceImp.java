package com.whaty.platform.entity.service.imp.fee;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.bean.PrPriManagerEdutype;
import com.whaty.platform.entity.bean.PrPriManagerGrade;
import com.whaty.platform.entity.bean.PrPriManagerMajor;
import com.whaty.platform.entity.bean.PrPriManagerSite;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.fee.PrFeeDetailReduceService;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PrFeeDetailReduceServiceImp implements PrFeeDetailReduceService{
	
	private GeneralDao generalDao;
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.fee.PrFeeDetailReduceService#save(com.whaty.platform.entity.bean.PrFeeDetail)
	 */
	public PrFeeDetail save(PrFeeDetail prFeeDetail) throws EntityException{
		try{
			//查询出prFeeDetail的学生
			String regNo = prFeeDetail.getPeStudent().getRegNo();
			if(regNo==null||regNo.trim().length()<=0){
				throw new EntityException("学生的学号不能为空...");
			}
			DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
			dc.add(Restrictions.eq("regNo", regNo.trim()));
			java.util.List stuList = this.getGeneralDao().getList(dc);
			if(stuList==null||stuList.size()<=0){
				throw new EntityException("没有学号为"+regNo.trim()+"的学生...");
			}
			PeStudent student = (PeStudent)stuList.remove(0);
			//判断该学生是否已经申请通过一次这样的减免
//			DetachedCriteria dc0 = DetachedCriteria.forClass(PrFeeDetail.class);
//			dc0.add(Restrictions.eq("peStudent.id",student.getId()))
//				.add(Restrictions.eq("enumConstByFlagFeeType.id", prFeeDetail.getEnumConstByFlagFeeType().getId()))
//				.add(Restrictions.eq("enumConstByFlagFeeCheck.id", this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "2").getId()))
//				;
//			java.util.List list = this.getGeneralDao().getList(dc0);
//			if(list!=null&&list.size()>0){
//				throw new EntityException("学生已经有过这种类型的减免了！");
//			}
			
//			if(prFeeDetail.getFeeAmount()>0){
//				if(student.getFeeInactive()==null)
//					student.setFeeInactive(prFeeDetail.getFeeAmount());
//				else
//					student.setFeeInactive(student.getFeeInactive()+prFeeDetail.getFeeAmount());
//			}else
//				throw new EntityException("为学生减免的费用应该大于零...");
//			student = (PeStudent)this.getGeneralDao().save(student);
			prFeeDetail.setPeStudent(student);
			prFeeDetail.setEnumConstByFlagFeeCheck(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "3"));
			prFeeDetail = (PrFeeDetail)this.getGeneralDao().save(prFeeDetail);
		}catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().indexOf("学生")>=0)
				throw new EntityException(e.getMessage()); 
			else
				throw new EntityException("为学生添加减免费用失败！");
		}
		return prFeeDetail;
	}
	/**
	 * 批量添加学生减免费用信息
	 * @param 
	 * @return
	 * @throws EntityException
	 */
	public int saveBatch(File file) throws EntityException{
		StringBuffer msg = new StringBuffer();
		int count = 0;

		Workbook work = null;

		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量添加失败！<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("表格为空！<br/>");
		}
		String temp = "";
		Set<PrFeeDetail> prFeeDetailSet = new HashSet();

//		//添加横向权限判断。取得用户可以操作的范围				
//		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		if(userSession==null){
//			throw new EntityException("批量添加学生减免费用信息失败,无法取得您的身份信息！");
//		}
//		this.getGeneralDao().setEntityClass(SsoUser.class);
//		SsoUser ssoUser = (SsoUser)this.getGeneralDao().getById(userSession.getSsoUser().getId());
//		Set edutypes = ssoUser.getPrPriManagerEdutypes();
//		Set sites = ssoUser.getPrPriManagerSites();
//		Set grades = ssoUser.getPrPriManagerGrades();
//		Set majors = ssoUser.getPrPriManagerMajors();
		
		for (int i = 1; i < rows; i++) {
			try {sheet.getCell(4, i).getContents().trim();
			if(sheet.getCell(0, i).getContents().trim().equals("")&&
					sheet.getCell(1, i).getContents().trim().equals("")&&
					sheet.getCell(2, i).getContents().trim().equals("")&&
					sheet.getCell(3, i).getContents().trim().equals("")&&
					sheet.getCell(4, i).getContents().trim().equals("")){
				msg.append("第"+(i+1)+"行数据添加失败，没有输入任何数据！<br/>");
				continue;
			}
		} catch (Exception e1) {
			msg.append("第"+(i+1)+"行数据添加失败，模板错误！<br/>");
			continue;
		}
			try {
				PrFeeDetail prFeeDetail =new PrFeeDetail();
				temp = sheet.getCell(0, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，学号为空！<br/>");
					continue;
				}
				
				DetachedCriteria dcPeStudent = DetachedCriteria.forClass(PeStudent.class);
				dcPeStudent.add(Restrictions.eq("regNo", temp));
				
				List<PeStudent> peStudentList = this.getGeneralDao().getList(dcPeStudent);
				
				if (peStudentList==null || peStudentList.isEmpty()) {
					msg.append("第" + (i + 1) + "行数据，学号不存在！<br/>");
					continue;
				}	
				
				PeStudent peStudent =peStudentList.get(0);
				
				temp = sheet.getCell(1, i).getContents().trim();
				if (temp != null && !"".equals(temp)) {
					if (!peStudent.getTrueName().equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，姓名或者学号不正确！<br/>");
					continue;
					}
				}
				
				if (!peStudent.getEnumConstByFlagStudentStatus().getCode().equals("4")){
					msg.append("第" + (i + 1) + "行数据，学生不是在籍状态！<br/>");
					continue;
				}
				
//				//横向权限检查。
//				if(edutypes!=null&&!edutypes.isEmpty()) {
//					boolean checked = false;
//					for (Object object : edutypes) {
//						if(((PrPriManagerEdutype)object).getPeEdutype().getId().equals(peStudent.getPeEdutype().getId())){
//							checked = true;
//							break;
//						}
//					}
//					if(!checked){
//						msg.append("第" + (i + 1) + "行数据，您对这个层次没有操作权限<br/>");
//						continue;
//					}
//				}
//				if(sites!=null&&!sites.isEmpty()) {
//					boolean checked = false;
//					for (Object object : edutypes) {
//						if(((PrPriManagerSite)object).getPeSite().getId().equals(peStudent.getPeSite().getId())){
//							checked = true;
//							break;
//						}
//					}
//					if(!checked){
//						msg.append("第" + (i + 1) + "行数据，您对这个学习中心没有操作权限<br/>");
//						continue;	
//					}
//				}
//				if(grades!=null&&!grades.isEmpty()) {
//					boolean checked = false;
//					for (Object object : edutypes) {
//						if(((PrPriManagerGrade)object).getPeGrade().getId().equals(peStudent.getPeGrade().getId())){
//							checked = true;
//							break;
//						}
//					}
//					if(!checked){
//						msg.append("第" + (i + 1) + "行数据，您对这个年级没有操作权限<br/>");
//						continue;	
//					}
//				}
//				if(majors!=null&&!majors.isEmpty()) {
//					boolean checked = false;
//					for (Object object : edutypes) {
//						if(((PrPriManagerMajor)object).getPeMajor().getId().equals(peStudent.getPeMajor().getId())){
//							checked = true;
//							break;
//						}
//					}
//					if(!checked){
//						msg.append("第" + (i + 1) + "行数据，您对这个专业没有操作权限<br/>");
//						continue;		
//					}
//				}
//				//以上横向权限检查。
				
				prFeeDetail.setPeStudent(peStudent);
				
				temp = sheet.getCell(2, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，减免类型不能为空！<br/>");
					continue;
				}
				
				DetachedCriteria dcFlagFeeType = DetachedCriteria.forClass(EnumConst.class);
				dcFlagFeeType.add(Restrictions.eq("namespace", "FlagFeeType"));
				dcFlagFeeType.add(Restrictions.eq("name", temp));
				List<EnumConst> flagFeeTypeList = this.getGeneralDao().getList(dcFlagFeeType);
				if (flagFeeTypeList==null || flagFeeTypeList.isEmpty()) {
					msg.append("第" + (i + 1) + "行数据，减免类型不存在！<br/>");
					continue;
				}
				
				prFeeDetail.setEnumConstByFlagFeeType(flagFeeTypeList.get(0));
					
				temp = sheet.getCell(3, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，减免金额不能为空！<br/>");
					continue;
				}
				if (!temp.matches(Const.fee)){
					msg.append("第" + (i + 1) + "行数据，减免金额格式有误!"+Const.feeMessage+"<br/>");
					continue;
				}
				
				Double money;
				try {
					money = Double.parseDouble(temp);
				} catch (RuntimeException e) {
					msg.append("第" + (i + 1) + "行数据，减免金额格式有误！<br/>");
					continue;
				}
				
				prFeeDetail.setFeeAmount(money);
				
				temp = sheet.getCell(4, i).getContents().trim();
				prFeeDetail.setNote(temp);
				
				prFeeDetail.setEnumConstByFlagFeeCheck(
						this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "3"));
				
				prFeeDetail.setInputDate(new Date());
				
				if (!prFeeDetailSet.add(prFeeDetail)) {
					msg.append("第" + (i + 1) + "行数据与文件中前面的数据重复！<br/>");
					continue;
				}
				count++;
			} catch (Exception e) {
				e.printStackTrace();
				msg.append("第" + (i + 1) + "行数据添加失败！<br/>");
				continue;
			}
		}

		if (msg.length() > 0) {
			msg.append("学生减免费用批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}

		for (PrFeeDetail prFeeDetail : prFeeDetailSet) {
			try {
				this.getGeneralDao().save(prFeeDetail);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EntityException("批量上传学生减免费用失败");
			}
		}
		return count;
	}
	
	/**
	 * 教师减免费用
	 * @param 
	 * @return
	 * @throws EntityException
	 */
	public int saveTeacherReduce(List<String> idList) throws EntityException{
		int count = 0;
		this.getGeneralDao().setEntityClass(PeStudent.class);
		EnumConst flagFeeType = this.generalDao.getEnumConstByNamespaceCode("FlagFeeType", "7");
		EnumConst flagFeeCheck = this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "2");
		for (String id : idList) {
			PeStudent student= (PeStudent)this.getGeneralDao().getById(id);
			double feeLeve = student.getPeFeeLevel().getFeePercredit(); //收费标准
			double feeBalance = 0d; //账户余额
			double feeInactive = 0d;//不可退余额
			if(student.getFeeBalance()!=null){
				feeBalance =Const.add(feeBalance,student.getFeeBalance());
			}
			if(student.getFeeInactive()!=null){
				feeInactive = Const.add(feeInactive, student.getFeeInactive());
			}
			//向账户中添加15个学分的费用
			feeBalance = Const.add(feeBalance, feeLeve*15);
			feeInactive = Const.add(feeInactive, feeLeve*15);
			student.setFeeBalance(feeBalance);
			student.setFeeInactive(feeInactive);
			student = (PeStudent)this.generalDao.save(student);
			
			/**
			 * 添加费用记录
			 */
			PrFeeDetail feeDetail = new PrFeeDetail();
			feeDetail.setCreditAmount(feeBalance);
			feeDetail.setFeeAmount(feeLeve*15);
			feeDetail.setPeStudent(student);
			feeDetail.setEnumConstByFlagFeeCheck(flagFeeCheck);
			feeDetail.setEnumConstByFlagFeeType(flagFeeType);
			feeDetail.setInputDate(new Date());
			feeDetail = (PrFeeDetail) this.getGeneralDao().save(feeDetail);
			
			count++;
		}
		
		
		return count;
	}
	
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.fee.PrFeeDetailReduceService#updateForCheck(java.util.List)
	 */
	public int updateForCheck(List<String> ids,boolean pass) throws EntityException {
		try{
			if(pass)
				for(String id:ids){
					PrFeeDetail prFeeDetail = (PrFeeDetail)this.getGeneralDao().getById(id);
					PeStudent student = prFeeDetail.getPeStudent();
					if(!student.getEnumConstByFlagStudentStatus().getCode().equals("4")){
						throw new EntityException("学生"+student.getTrueName()+"不是在籍的学生");
					}
					if(student.getFeeBalance()==null){
						student.setFeeBalance(0.0);
					}
					if(student.getFeeInactive()==null){
						student.setFeeInactive(0.0);
					}
					student.setFeeBalance(student.getFeeBalance()+prFeeDetail.getFeeAmount());
					student.setFeeInactive(student.getFeeInactive()+prFeeDetail.getFeeAmount());
					student = (PeStudent)this.getGeneralDao().save(student);
					prFeeDetail.setPeStudent(student);
					prFeeDetail.setCreditAmount(student.getFeeBalance());
					if(prFeeDetail.getEnumConstByFlagFeeCheck().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "3"))){
						prFeeDetail.setEnumConstByFlagFeeCheck(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "2"));
						this.getGeneralDao().save(prFeeDetail);
					}else{
						throw new EntityException("学生"+student.getTrueName()+"减免不是未审核状态！");
					}
				}
			else
				for(String id:ids){
					PrFeeDetail prFeeDetail = (PrFeeDetail)this.getGeneralDao().getById(id);					
					if(prFeeDetail.getEnumConstByFlagFeeCheck().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "3"))){
						prFeeDetail.setEnumConstByFlagFeeCheck(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "4"));
						this.getGeneralDao().save(prFeeDetail);
					}else{
						throw new EntityException("学生"+prFeeDetail.getPeStudent().getTrueName()+"减免不是未审核状态！");
					}
				}
		}catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().indexOf("学生")>=0)
				throw new EntityException(e.getMessage()); 
			else
				throw new EntityException("审核学生减免费用失败！");
		}
			
		return ids.size();
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.fee.PrFeeDetailReduceService#updateForCancel(java.util.List, boolean)
	 */
	public int updateForCancel(List<String> ids, boolean pass)
			throws EntityException {
		try{
			if(pass)
				for(String id:ids){
					PrFeeDetail prFeeDetail = (PrFeeDetail)this.getGeneralDao().getById(id);
					PeStudent student = prFeeDetail.getPeStudent();
					if(!student.getEnumConstByFlagStudentStatus().getCode().equals("4")){
						throw new EntityException("学生"+student.getTrueName()+"不是在籍的学生!取消失败!");
					}
					if(student.getFeeBalance()<prFeeDetail.getFeeAmount()){
						throw new EntityException("学生"+student.getTrueName()+"("+student.getRegNo()+")"+"已经使用了这些费用,取消失败!");
					}
					student.setFeeBalance(student.getFeeBalance()-prFeeDetail.getFeeAmount());
					student.setFeeInactive(student.getFeeInactive()-prFeeDetail.getFeeAmount());
					student = (PeStudent)this.getGeneralDao().save(student);
					prFeeDetail.setPeStudent(student);
					prFeeDetail.setCreditAmount((double)0);
					if(prFeeDetail.getEnumConstByFlagFeeCheck().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "2"))){
						prFeeDetail.setEnumConstByFlagFeeCheck(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "3"));
						this.getGeneralDao().save(prFeeDetail);
					}else{
						throw new EntityException("学生"+student.getTrueName()+"的减免记录不是已审核状态,取消失败!");
					}
				}
			else
				for(String id:ids){
					PrFeeDetail prFeeDetail = (PrFeeDetail)this.getGeneralDao().getById(id);					
					if(prFeeDetail.getEnumConstByFlagFeeCheck().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "4"))){
						prFeeDetail.setEnumConstByFlagFeeCheck(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "3"));
						this.getGeneralDao().save(prFeeDetail);
					}else{
						throw new EntityException("学生"+prFeeDetail.getPeStudent().getTrueName()+"的减免不是审核不通过的，取消失败!");
					}
				}
		}catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().indexOf("学生")>=0)
				throw new EntityException(e.getMessage()); 
			else
				throw new EntityException("取消失败!");
		}
		return ids.size();
	}
		
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	
}
