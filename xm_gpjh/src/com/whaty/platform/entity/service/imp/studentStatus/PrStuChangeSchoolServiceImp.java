package com.whaty.platform.entity.service.imp.studentStatus;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrStuChangeSchool;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.studentStatas.PrStuChangeSchoolService;

public class PrStuChangeSchoolServiceImp implements PrStuChangeSchoolService {
	
	private GeneralDao generalDao;
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.studentStatas.PrStuChangeSchoolService#save(com.whaty.platform.entity.bean.PrStuChangeSchool)
	 */
	public PrStuChangeSchool save(PrStuChangeSchool prStuChangeSchool) throws EntityException{
		try{			
			if(prStuChangeSchool!=null){
				DetachedCriteria dc0 = DetachedCriteria.forClass(PeStudent.class);
				dc0.add(Restrictions.eq("id", prStuChangeSchool.getPeStudent().getId()));					
				PeStudent student = (PeStudent)this.getGeneralDao().getList(dc0).remove(0);//prStuChangeEdutype.getPeStudent();
				if(student.getEnumConstByFlagStudentStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "4"))){
					if(prStuChangeSchool.getEnumConstByFlagAbortschoolType().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagAbortschoolType", "0"))){
						student.setEnumConstByFlagStudentStatus(this.generalDao.getEnumConstByNamespaceCode("FlagStudentStatus", "6"));					
					}else if(prStuChangeSchool.getEnumConstByFlagAbortschoolType().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagAbortschoolType", "1"))){
						student.setEnumConstByFlagStudentStatus(this.generalDao.getEnumConstByNamespaceCode("FlagStudentStatus", "7"));					
					}else{					
					}
				}else{
					throw new EntityException("学生"+prStuChangeSchool.getPeStudent().getTrueName()+"("+prStuChangeSchool.getPeStudent().getRegNo()+")"+"不是在籍的学生，不能对其操作！");					
				}
				SsoUser ssouser = student.getSsoUser();
				ssouser.setEnumConstByFlagIsvalid(this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsvalid", "0"));
				ssouser = (SsoUser)this.getGeneralDao().save(ssouser);
				student.setSsoUser(ssouser);
				student = (PeStudent)this.getGeneralDao().save(student);
				prStuChangeSchool.setPeStudent(student);
				prStuChangeSchool.setId(null);
				prStuChangeSchool = (PrStuChangeSchool)this.getGeneralDao().save(prStuChangeSchool);
			}else{
				throw new EntityException("对学生"+prStuChangeSchool.getPeStudent().getTrueName()+"("+prStuChangeSchool.getPeStudent().getRegNo()+")"+"的操作失败！");					
			}
		}catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().indexOf("学生")>=0)
				throw new EntityException(e.getMessage());
			else
				throw new EntityException("操作失败！");
		}
		return prStuChangeSchool;
	}

	public boolean delForCancel(PrStuChangeSchool prStuChangeSchool)
			throws EntityException {
		boolean b = false;
		try{
			prStuChangeSchool = (PrStuChangeSchool)this.getGeneralDao().getById(prStuChangeSchool.getId());
			if(prStuChangeSchool!=null){
				DetachedCriteria dc0 = DetachedCriteria.forClass(PeStudent.class);
				dc0.add(Restrictions.eq("id", prStuChangeSchool.getPeStudent().getId()));					
				PeStudent student = (PeStudent)this.getGeneralDao().getList(dc0).remove(0);//prStuChangeEdutype.getPeStudent();
				if(prStuChangeSchool.getEnumConstByFlagAbortschoolType().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagAbortschoolType", "0"))){
					if(student.getEnumConstByFlagStudentStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "6"))){
						student.setEnumConstByFlagStudentStatus(this.generalDao.getEnumConstByNamespaceCode("FlagStudentStatus", "4"));
					}else{
						throw new EntityException("学生"+prStuChangeSchool.getPeStudent().getTrueName()+"("+prStuChangeSchool.getPeStudent().getRegNo()+")"+"不是已经开除学籍的学生，不能对其操作！");					
					}
				}else if(prStuChangeSchool.getEnumConstByFlagAbortschoolType().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagAbortschoolType", "1"))){
					if(student.getEnumConstByFlagStudentStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "7"))){
						student.setEnumConstByFlagStudentStatus(this.generalDao.getEnumConstByNamespaceCode("FlagStudentStatus", "4"));
					}else{
						throw new EntityException("学生"+prStuChangeSchool.getPeStudent().getTrueName()+"("+prStuChangeSchool.getPeStudent().getRegNo()+")"+"不是已经退学的学生，不能对其操作！");					
					}
				}else{					
				}
				SsoUser ssouser = student.getSsoUser();
				ssouser.setEnumConstByFlagIsvalid(this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsvalid", "1"));
				ssouser = (SsoUser)this.getGeneralDao().save(ssouser);
				student.setSsoUser(ssouser);
				student = (PeStudent)this.getGeneralDao().save(student);
				this.getGeneralDao().delete(prStuChangeSchool);
				b = true;
			}else{
				throw new EntityException("对学生"+prStuChangeSchool.getPeStudent().getTrueName()+"("+prStuChangeSchool.getPeStudent().getRegNo()+")"+"的取消操作失败！");					
			}
		}catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().indexOf("学生")>=0)
				throw new EntityException(e.getMessage());
			else
				throw new EntityException("操作失败！");
		}
		return b;
	}
	

	public int delForCancel(List ids) throws EntityException {
		try{
			for(Object id:ids){
				PrStuChangeSchool prStuChangeSchool = (PrStuChangeSchool)this.getGeneralDao().getById((String)id);
				if(prStuChangeSchool!=null){
					DetachedCriteria dc0 = DetachedCriteria.forClass(PeStudent.class);
					dc0.add(Restrictions.eq("id", prStuChangeSchool.getPeStudent().getId()));					
					PeStudent student = (PeStudent)this.getGeneralDao().getList(dc0).remove(0);//prStuChangeEdutype.getPeStudent();
					if(prStuChangeSchool.getEnumConstByFlagAbortschoolType().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagAbortschoolType", "0"))){
						if(student.getEnumConstByFlagStudentStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "6"))){
							student.setEnumConstByFlagStudentStatus(this.generalDao.getEnumConstByNamespaceCode("FlagStudentStatus", "4"));
						}else{
							throw new EntityException("有些学生比如"+prStuChangeSchool.getPeStudent().getTrueName()+"("+prStuChangeSchool.getPeStudent().getRegNo()+")"+"不是已经开除学籍的学生，操作失败！");					
						}
					}else if(prStuChangeSchool.getEnumConstByFlagAbortschoolType().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagAbortschoolType", "1"))){
						if(student.getEnumConstByFlagStudentStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "7"))){
							student.setEnumConstByFlagStudentStatus(this.generalDao.getEnumConstByNamespaceCode("FlagStudentStatus", "4"));
						}else{
							throw new EntityException("有些学生比如"+prStuChangeSchool.getPeStudent().getTrueName()+"("+prStuChangeSchool.getPeStudent().getRegNo()+")"+"不是已经退学的学生，操作失败！");					
						}
					}else{					
					}
					student = (PeStudent)this.getGeneralDao().save(student);
					this.getGeneralDao().delete(prStuChangeSchool);
				}else{
					throw new EntityException("对学生取消异动操作失败！");					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().indexOf("学生")>=0)
				throw new EntityException(e.getMessage());
			else
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


}
