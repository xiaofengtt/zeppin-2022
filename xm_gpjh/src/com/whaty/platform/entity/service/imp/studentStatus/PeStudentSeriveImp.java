package com.whaty.platform.entity.service.imp.studentStatus;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.studentStatas.PeStudentSerive;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrPriManagerEdutype;
import com.whaty.platform.entity.bean.PrPriManagerGrade;
import com.whaty.platform.entity.bean.PrPriManagerMajor;
import com.whaty.platform.entity.bean.PrPriManagerSite;
import com.whaty.platform.entity.bean.PrStudentInfo;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeStudentSeriveImp implements PeStudentSerive {
	
	private GeneralDao generalDao;	
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.studentStatas.PeStudentSerive#save(com.whaty.platform.entity.bean.PeStudent)
	 */
	public PeStudent save(PeStudent bean) throws EntityException{
		this.getGeneralDao().setEntityClass(PeStudent.class);
		PeStudent stu = (PeStudent)this.getGeneralDao().getById(bean.getId());
		try{
			PrStudentInfo prstu = stu.getPrStudentInfo();
			prstu.setAddress(bean.getPrStudentInfo().getAddress());
			prstu.setBirthday(bean.getPrStudentInfo().getBirthday());
			prstu.setCardNo(bean.getPrStudentInfo().getCardNo());
			prstu.setCardType(bean.getPrStudentInfo().getCardType());
			prstu.setCity(bean.getPrStudentInfo().getCity());
			prstu.setEmail(bean.getPrStudentInfo().getEmail());
			prstu.setFork(bean.getPrStudentInfo().getFork());
			prstu.setGender(bean.getPrStudentInfo().getGender());
			prstu.setGraduateCode(bean.getPrStudentInfo().getGraduateCode());
			prstu.setGraduateSchool(bean.getPrStudentInfo().getGraduateSchool());
			prstu.setGraduateSchoolCode(bean.getPrStudentInfo().getGraduateSchoolCode());
			prstu.setGraduateYear(bean.getPrStudentInfo().getGraduateYear());
			prstu.setMarriage(bean.getPrStudentInfo().getMarriage());
			prstu.setMobilephone(bean.getPrStudentInfo().getMobilephone());
			prstu.setOccupation(bean.getPrStudentInfo().getOccupation());
			prstu.setPhone(bean.getPrStudentInfo().getPhone());
			//prstu.setPhotoDate(bean.getPrStudentInfo().getPhotoDate());
			//prstu.setPhotoLink(bean.getPrStudentInfo().getPhotoLink());
			prstu.setProvince(bean.getPrStudentInfo().getProvince());
			prstu.setWorkplace(bean.getPrStudentInfo().getWorkplace());
			prstu.setXueli(bean.getPrStudentInfo().getXueli());
			//prstu.setXuezhi(bean.getPrStudentInfo().getXuezhi());
			prstu.setZip(bean.getPrStudentInfo().getZip());
			prstu.setZzmm(bean.getPrStudentInfo().getZzmm());
			prstu = (PrStudentInfo)this.getGeneralDao().save(prstu);
			stu.setPrStudentInfo(prstu);
			//SsoUser user = stu.getSsoUser();
			//user.setLoginId(bean.getRegNo());
			//user = (SsoUser)this.getGeneralDao().save(user);

			stu.setRegNo(bean.getRegNo());
			stu.setTrueName(bean.getTrueName());
			stu.setName(bean.getRegNo()+"/"+bean.getTrueName());
			stu = (PeStudent)this.getGeneralDao().save(stu);		
		}catch(Throwable e){
			throw  new EntityException(e.getMessage());
		}
		return stu;
	}

	public String createRegister(List<String> idsList)throws EntityException{
		String msg = "";
		int i = 0;
		java.util.Map<String,String> regNoSerialMap = new java.util.HashMap<String,String>();
		try{
			for(String id:idsList){
				PeStudent peStudent = (PeStudent)this.getGeneralDao().getById(id);			
				if(peStudent.getEnumConstByFlagStudentStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "3"))){

					String halfRegNo = "W"+peStudent.getPeSite().getCode()+peStudent.getPeGrade().getCode()
							+peStudent.getPeEdutype().getCode()+peStudent.getPeMajor().getCode();
					if(regNoSerialMap==null||regNoSerialMap.size()<=0){
						regNoSerialMap.put(halfRegNo, createRegNoSerial(halfRegNo));						
					}else{
						String regNoSerial = regNoSerialMap.get(halfRegNo);
						if(regNoSerial==null||regNoSerial.length()<4){
							regNoSerialMap.put(halfRegNo, createRegNoSerial(halfRegNo));
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
					i++;
				}else
					throw new EntityException("学生不是已经交费的学生，不能注册！");			
			}
		}catch(RuntimeException e){
			throw new EntityException(e.getMessage());
		}
		return msg+="共"+i+"学生注册成功！<br>";
	}

	public String delRegister(List<String> idsList)throws EntityException {
		String msg = "";
		int i = 0;
		try{
			for(String id:idsList){
				PeStudent peStudent = (PeStudent)this.getGeneralDao().getById(id);				
				if(peStudent.getEnumConstByFlagStudentStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "4"))){
					SsoUser ssoUser = peStudent.getSsoUser();
					peStudent.setSsoUser(null);
					this.getGeneralDao().delete(ssoUser);
					
					peStudent.setRegNo("");
					peStudent.setName("/"+peStudent.getTrueName());
					peStudent.setEnumConstByFlagStudentStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "3"));
					peStudent = (PeStudent)this.getGeneralDao().save(peStudent);
					i++;
				}else
					throw new EntityException("学生不是已经注册的学生，不能取消注册！");
			}
		}catch(RuntimeException e){
			throw new EntityException(e.getMessage());
		}
		return msg+="共"+i+"学生取消注册成功！<br>";
	}
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
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
	/**
	 * 保存毕业证编号学位证编号
	 * @param file
	 * @return
	 * @throws EntityException
	 */
	public int saveCertificateNo(File file) throws EntityException{
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
		Set<PeStudent> studentSet = new HashSet();
		
		//添加横向权限判断。取得用户可以操作的范围				
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(userSession==null){
			throw new EntityException("批量上传毕业证学位证编号失败,无法取得您的身份信息！");
		}
		
		this.getGeneralDao().setEntityClass(SsoUser.class);
		SsoUser ssoUser = (SsoUser)this.getGeneralDao().getById(userSession.getSsoUser().getId());
		Set edutypes = ssoUser.getPrPriManagerEdutypes();
		Set sites = ssoUser.getPrPriManagerSites();
		Set grades = ssoUser.getPrPriManagerGrades();
		Set majors = ssoUser.getPrPriManagerMajors();
		
		try {
			if( !sheet.getCell(2, 0).getContents().trim().equals("毕业证编号")|| 
					!sheet.getCell(3, 0).getContents().trim().equals("学位证编号")){
				throw new EntityException("批量上传失败,模板错误！");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new EntityException("批量上传失败,模板错误！");
		}
		
		Set<String> certificateNo1 =  new HashSet();
		Set<String> certificateNo2 =  new HashSet();		
		for (int i = 1; i < rows; i++) {
			try {
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
				
				if (!peStudent.getEnumConstByFlagStudentStatus().getCode().equals("5")){
					msg.append("第" + (i + 1) + "行数据，学生不是已毕业状态！<br/>");
					continue;
				}
				
//				if (peStudent.getPeEdutype().getName().indexOf("本")==-1){
//					msg.append("第" + (i + 1) + "行数据，学生所在层次没有统考！<br/>");
//					continue;
//				}
				
				//横向权限检查。
				if(edutypes!=null&&!edutypes.isEmpty()) {
					boolean checked = false;
					for (Object object : edutypes) {
						if(((PrPriManagerEdutype)object).getPeEdutype().getId().equals(peStudent.getPeEdutype().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个层次没有操作权限<br/>");
						continue;
					}
				}
				if(sites!=null&&!sites.isEmpty()) {
					boolean checked = false;
					for (Object object : edutypes) {
						if(((PrPriManagerSite)object).getPeSite().getId().equals(peStudent.getPeSite().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个学习中心没有操作权限<br/>");
						continue;	
					}
				}
				if(grades!=null&&!grades.isEmpty()) {
					boolean checked = false;
					for (Object object : edutypes) {
						if(((PrPriManagerGrade)object).getPeGrade().getId().equals(peStudent.getPeGrade().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个年级没有操作权限<br/>");
						continue;	
					}
				}
				if(majors!=null&&!majors.isEmpty()) {
					boolean checked = false;
					for (Object object : edutypes) {
						if(((PrPriManagerMajor)object).getPeMajor().getId().equals(peStudent.getPeMajor().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个专业没有操作权限<br/>");
						continue;		
					}
				}
				//以上横向权限检查。
				
				String no1 = null;
				temp = sheet.getCell(2, i).getContents().trim();
				if (temp!= null&& !"".equals(temp)) {
					no1=temp;

					if(!certificateNo1.add(no1)){
						msg.append("第" + (i + 1) + "行数据，毕业证编号与文件中前面的数据重复<br/>");
						continue;		
					}
					DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
					dc.add(Restrictions.eq("graduationCertificateNo", no1));
					List list = this.getGeneralDao().getList(dc);
					if (list!=null&&list.size()>0){
						msg.append("第" + (i + 1) + "行数据，毕业证编号已经存在<br/>");
						continue;		
					}
					peStudent.setGraduationCertificateNo(temp);

				}
				
				String no2=null;
				temp = sheet.getCell(3, i).getContents().trim();
				if (temp!= null&& !"".equals(temp)) {
					no2 = temp;
					if (peStudent.getDegreeDate()!=null){
						if(!certificateNo2.add(no2)){
							msg.append("第" + (i + 1) + "行数据，学位证编号与文件中前面的数据重复<br/>");
							continue;		
						}
						DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
						dc.add(Restrictions.eq("degreeCertificateNo", no2));
						List list = this.getGeneralDao().getList(dc);
						if (list!=null&&list.size()>0){
							msg.append("第" + (i + 1) + "行数据，学位证编号已经存在<br/>");
							continue;		
						}
						peStudent.setDegreeCertificateNo(temp);

					} else {
						msg.append("第" + (i + 1) + "行数据，学生没有取得学位，无法添加学位证书编号<br/>");
						continue;			
					}
				}
				
				if(no1==null&&no2==null){
					msg.append("第" + (i + 1) + "行数据，毕业证编号和学位证编号至少要填写一项<br/>");
					continue;			
				}
				
				
				if (!studentSet.add(peStudent)){
					msg.append("第" + (i + 1) + "行数据与文件中前面的数据重复<br/>");
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
			msg.append("毕业证编号学位证编号批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}

		for (PeStudent peStudent : studentSet) {
			try {
				this.getGeneralDao().save(peStudent);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EntityException("批量上传学生统考成绩失败");
			}
		}
		return count;
	}
}
