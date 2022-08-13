package com.whaty.platform.entity.service.imp.teaching.elective;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeGrade;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTchCoursegroup;
import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.elective.ManualOpenCourseService;
import com.whaty.platform.util.Const;

public class ManualOpenCourseServiceImp implements ManualOpenCourseService {

	private GeneralDao generalDao;
	private MyListDAO myListDao;
	
	public MyListDAO getMyListDao() {
		return myListDao;
	}

	public void setMyListDao(MyListDAO myListDao) {
		this.myListDao = myListDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}
	
	public int updateOpenCourse(List ids) throws EntityException {
		
		for (Iterator iter = ids.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			
			PrTchStuElective stuElective = (PrTchStuElective) this.getMyListDao().getById(PrTchStuElective.class, id);
			EnumConst enumConstByFlagElectiveAdmission = this.getMyListDao().getEnumConstByNamespaceCode("FlagElectiveAdmission", "1");
			stuElective.setEnumConstByFlagElectiveAdmission(enumConstByFlagElectiveAdmission);
			
			PeStudent student = stuElective.getPeStudent();
			Double feePercredit = student.getPeFeeLevel().getFeePercredit();
			Double courseCredit = stuElective.getPrTchProgramCourse().getCredit();
			Double feeBalance = student.getFeeBalance();
			Double oweFeeLimit = student.getPeFeeLevel().getOweFeeLimit();
			double feeInactive = student.getFeeInactive();
			feeBalance = Const.sub(feeBalance, feePercredit*courseCredit);
			feeInactive = Const.sub(feeInactive, feePercredit*courseCredit);
			student.setFeeBalance(feeBalance);
			student.setFeeInactive(feeInactive);
			
			//向费用明细表插入数据
			PrFeeDetail prFeeDetail = new PrFeeDetail();
			prFeeDetail.setPeStudent(student);
			EnumConst enumConstByFlagFeeType = this.getMyListDao().getEnumConstByNamespaceCode("FlagFeeType", "3");
			prFeeDetail.setEnumConstByFlagFeeType(enumConstByFlagFeeType);
			EnumConst enumConstByFlagFeeCheck = this.getMyListDao().getEnumConstByNamespaceCode("FlagFeeCheck", "2");
			prFeeDetail.setEnumConstByFlagFeeCheck(enumConstByFlagFeeCheck);
			prFeeDetail.setFeeAmount(-feePercredit*courseCredit);
			prFeeDetail.setCreditAmount(feeBalance);
			prFeeDetail.setInputDate(new Date());
			prFeeDetail.setNote("开课" + stuElective.getPrTchOpencourse().getPeTchCourse().getName() + "扣费");
			this.getGeneralDao().save(prFeeDetail);
			
			if ((feeBalance+oweFeeLimit) < 0) {
				throw new EntityException(", 学生 " + student.getName() + " 余额不足" );
				
			}
			
		}
		return 0;
	}
	/**
	 * 批量开课
	 * @param peSiteList
	 * @param peEdutypeList
	 * @param peMajorList
	 * @param peGradeList
	 * @return
	 * @throws EntityException
	 */
	public String saveOpenCourseBatch(List<String> peSiteList,List<String> peEdutypeList,List<String> peMajorList,List<String> peGradeList )
			throws EntityException{
		String message = "";
		int count = 0; //记录开课的数量
		DetachedCriteria dcPeStudent = DetachedCriteria.forClass(PeStudent.class);
		dcPeStudent.createCriteria("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus").add(Restrictions.eq("code", "4"));
		dcPeStudent.createCriteria("peGrade", "peGrade").add(Restrictions.in("id", peGradeList));
		dcPeStudent.createCriteria("peMajor", "peMajor").add(Restrictions.in("id", peMajorList));
		dcPeStudent.createCriteria("peSite", "peSite").add(Restrictions.in("id", peSiteList));
		dcPeStudent.createCriteria("peEdutype", "peEdutype").add(Restrictions.in("id", peEdutypeList));
		//根据传入条件查询在籍学生
		List<PeStudent> peStudentlist = this.generalDao.getList(dcPeStudent);
		if(peStudentlist==null||peStudentlist.size()==0){
			throw new EntityException("根据所选条件没有查询到在籍学生" );
		}
		EnumConst enumConstByFlagElectiveAdmission = this.getMyListDao().getEnumConstByNamespaceCode("FlagElectiveAdmission", "1");
		
//		PeTchCoursegroup peTchCoursegroup1 = (PeTchCoursegroup)this.getMyListDao().getById(PeTchCoursegroup.class, "_1");
//		PeTchCoursegroup peTchCoursegroup2 = (PeTchCoursegroup)this.getMyListDao().getById(PeTchCoursegroup.class, "_2");
//		PeTchCoursegroup peTchCoursegroup3 = (PeTchCoursegroup)this.getMyListDao().getById(PeTchCoursegroup.class, "_3");
//		PeTchCoursegroup peTchCoursegroup4 = (PeTchCoursegroup)this.getMyListDao().getById(PeTchCoursegroup.class, "_4");
		
		for (PeStudent peStudent : peStudentlist) {
			
			boolean fee = true;//账户余额是否可以开课
			
			//查询出每个学生当前学期的未开课选课记录
			DetachedCriteria dcPrTchStuElective = DetachedCriteria.forClass(PrTchStuElective.class);
			DetachedCriteria dcPrTchOpencourse = dcPrTchStuElective.createCriteria("prTchOpencourse", "prTchOpencourse");
			DetachedCriteria dcPrTchProgramCourse = dcPrTchStuElective.createCriteria("prTchProgramCourse", "prTchProgramCourse");
			dcPrTchProgramCourse.createCriteria("peTchProgramGroup", "peTchProgramGroup").createCriteria("peTchCoursegroup", "peTchCoursegroup");
			dcPrTchOpencourse.createCriteria("peSemester", "peSemester").add(Restrictions.eq("flagActive", "1"));
			dcPrTchStuElective.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission").add(Restrictions.eq("code", "0"));
			dcPrTchStuElective.add(Restrictions.eq("peStudent", peStudent));
			dcPrTchProgramCourse.addOrder(Order.desc("credit"));
			List<PrTchStuElective> prTchStuElectiveList = this.getGeneralDao().getList(dcPrTchStuElective);
			if(prTchStuElectiveList==null||prTchStuElectiveList.size()==0){
				continue;
			}
			List<PrTchStuElective> electiveList1 = new ArrayList<PrTchStuElective>();
			List<PrTchStuElective> electiveList2 = new ArrayList<PrTchStuElective>();
			List<PrTchStuElective> electiveList3 = new ArrayList<PrTchStuElective>();
			List<PrTchStuElective> electiveList4 = new ArrayList<PrTchStuElective>();
			
			for(PrTchStuElective prTchStuElective : prTchStuElectiveList) {
				if(prTchStuElective.getPrTchProgramCourse().getPeTchProgramGroup().getPeTchCoursegroup().getId().equals("_1")){
					electiveList1.add(prTchStuElective);
				} else if (prTchStuElective.getPrTchProgramCourse().getPeTchProgramGroup().getPeTchCoursegroup().getId().equals("_2")) {
					electiveList2.add(prTchStuElective);
				} else if (prTchStuElective.getPrTchProgramCourse().getPeTchProgramGroup().getPeTchCoursegroup().getId().equals("_3")) {
					electiveList3.add(prTchStuElective);
				} else if (prTchStuElective.getPrTchProgramCourse().getPeTchProgramGroup().getPeTchCoursegroup().getId().equals("_4")) {
					electiveList4.add(prTchStuElective);
				}
				
			}
			
			//公共必修课
			if(electiveList1!=null&&electiveList1.size()>0){
				for (PrTchStuElective prTchStuElective1 : electiveList1) {
					
//					if(!fee){
//						message +=" , "+prTchStuElective1.getPrTchOpencourse().getPeTchCourse().getName();
//						continue;
//					}
					
					//对费用进行判断
					Double feePercredit = peStudent.getPeFeeLevel().getFeePercredit();
					Double courseCredit = prTchStuElective1.getPrTchProgramCourse().getCredit();
					Double feeBalance = peStudent.getFeeBalance();
					Double oweFeeLimit = peStudent.getPeFeeLevel().getOweFeeLimit();
					double feeInactive = peStudent.getFeeInactive();
					feeBalance = Const.sub(feeBalance, feePercredit*courseCredit);
					feeInactive = Const.sub(feeInactive, feePercredit*courseCredit);
					if ((feeBalance+oweFeeLimit) < 0){
						fee = false;
						message +="</br>学生"+peStudent.getName()+"账户余额不足。" 
								+ prTchStuElective1.getPrTchOpencourse().getPeTchCourse().getName()
								+ "无法开课";
						continue;
					}
					
					//如果费用足够的话
					//开课
					prTchStuElective1.setEnumConstByFlagElectiveAdmission(enumConstByFlagElectiveAdmission);
					this.generalDao.save(prTchStuElective1);
					//设置学生账户余额
					peStudent.setFeeBalance(feeBalance);
					peStudent.setFeeInactive(feeInactive);
					peStudent = (PeStudent)this.generalDao.save(peStudent);
					
					//向费用明细表插入数据
					PrFeeDetail prFeeDetail = new PrFeeDetail();
					prFeeDetail.setPeStudent(peStudent);
					EnumConst enumConstByFlagFeeType = this.getMyListDao().getEnumConstByNamespaceCode("FlagFeeType", "3");
					prFeeDetail.setEnumConstByFlagFeeType(enumConstByFlagFeeType);
					EnumConst enumConstByFlagFeeCheck = this.getMyListDao().getEnumConstByNamespaceCode("FlagFeeCheck", "2");
					prFeeDetail.setEnumConstByFlagFeeCheck(enumConstByFlagFeeCheck);
					prFeeDetail.setFeeAmount(-feePercredit*courseCredit);
					prFeeDetail.setCreditAmount(feeBalance);
					prFeeDetail.setInputDate(new Date());
					prFeeDetail.setNote("开课" + prTchStuElective1.getPrTchOpencourse().getPeTchCourse().getName() + "扣费");
					this.getGeneralDao().save(prFeeDetail);
					count++;
				}
			}
			
			//专业必修课
			if(electiveList2!=null&&electiveList2.size()>0){
				for (PrTchStuElective prTchStuElective2 : electiveList2) {
					
//					if(!fee){
//						message +=" , "+prTchStuElective2.getPrTchOpencourse().getPeTchCourse().getName();
//						continue;
//					}
					
					//对费用进行判断
					Double feePercredit = peStudent.getPeFeeLevel().getFeePercredit();
					Double courseCredit = prTchStuElective2.getPrTchProgramCourse().getCredit();
					Double feeBalance = peStudent.getFeeBalance();
					Double oweFeeLimit = peStudent.getPeFeeLevel().getOweFeeLimit();
					double feeInactive = peStudent.getFeeInactive();
					feeBalance = Const.sub(feeBalance, feePercredit*courseCredit);
					feeInactive = Const.sub(feeInactive, feePercredit*courseCredit);
					if ((feeBalance+oweFeeLimit) < 0){
						fee = false;
						message +="</br>学生"+peStudent.getName()+"账户余额不足。" 
								+ prTchStuElective2.getPrTchOpencourse().getPeTchCourse().getName()
								+ "无法开课";
						continue;
					}
					
					//如果费用足够的话
					//开课
					prTchStuElective2.setEnumConstByFlagElectiveAdmission(enumConstByFlagElectiveAdmission);
					this.generalDao.save(prTchStuElective2);
					//设置学生账户余额
					peStudent.setFeeBalance(feeBalance);
					peStudent.setFeeInactive(feeInactive);
					peStudent = (PeStudent)this.generalDao.save(peStudent);
					
					//向费用明细表插入数据
					PrFeeDetail prFeeDetail = new PrFeeDetail();
					prFeeDetail.setPeStudent(peStudent);
					EnumConst enumConstByFlagFeeType = this.getMyListDao().getEnumConstByNamespaceCode("FlagFeeType", "3");
					prFeeDetail.setEnumConstByFlagFeeType(enumConstByFlagFeeType);
					EnumConst enumConstByFlagFeeCheck = this.getMyListDao().getEnumConstByNamespaceCode("FlagFeeCheck", "2");
					prFeeDetail.setEnumConstByFlagFeeCheck(enumConstByFlagFeeCheck);
					prFeeDetail.setFeeAmount(-feePercredit*courseCredit);
					prFeeDetail.setCreditAmount(feeBalance);
					prFeeDetail.setInputDate(new Date());
					prFeeDetail.setNote("开课" + prTchStuElective2.getPrTchOpencourse().getPeTchCourse().getName() + "扣费");
					this.getGeneralDao().save(prFeeDetail);
					count++;
				}
			}
			//专业选修课
			if(electiveList3!=null&&electiveList3.size()>0){
				for (PrTchStuElective prTchStuElective3 : electiveList3) {
					
//					if(!fee){
//						message +=" , "+prTchStuElective3.getPrTchOpencourse().getPeTchCourse().getName();
//						continue;
//					}
					
					//对费用进行判断
					Double feePercredit = peStudent.getPeFeeLevel().getFeePercredit();
					Double courseCredit = prTchStuElective3.getPrTchProgramCourse().getCredit();
					Double feeBalance = peStudent.getFeeBalance();
					Double oweFeeLimit = peStudent.getPeFeeLevel().getOweFeeLimit();
					double feeInactive = peStudent.getFeeInactive();
					feeBalance = Const.sub(feeBalance, feePercredit*courseCredit);
					feeInactive = Const.sub(feeInactive, feePercredit*courseCredit);
					if ((feeBalance+oweFeeLimit) < 0){
						fee = false;
						message +="</br>学生"+peStudent.getName()+"账户余额不足。" 
								+ prTchStuElective3.getPrTchOpencourse().getPeTchCourse().getName()
								+ "无法开课";
						continue;
					}
					
					//如果费用足够的话
					//开课
					prTchStuElective3.setEnumConstByFlagElectiveAdmission(enumConstByFlagElectiveAdmission);
					this.generalDao.save(prTchStuElective3);
					//设置学生账户余额
					peStudent.setFeeBalance(feeBalance);
					peStudent.setFeeInactive(feeInactive);
					peStudent = (PeStudent)this.generalDao.save(peStudent);
					
					//向费用明细表插入数据
					PrFeeDetail prFeeDetail = new PrFeeDetail();
					prFeeDetail.setPeStudent(peStudent);
					EnumConst enumConstByFlagFeeType = this.getMyListDao().getEnumConstByNamespaceCode("FlagFeeType", "3");
					prFeeDetail.setEnumConstByFlagFeeType(enumConstByFlagFeeType);
					EnumConst enumConstByFlagFeeCheck = this.getMyListDao().getEnumConstByNamespaceCode("FlagFeeCheck", "2");
					prFeeDetail.setEnumConstByFlagFeeCheck(enumConstByFlagFeeCheck);
					prFeeDetail.setFeeAmount(-feePercredit*courseCredit);
					prFeeDetail.setCreditAmount(feeBalance);
					prFeeDetail.setInputDate(new Date());
					prFeeDetail.setNote("开课" + prTchStuElective3.getPrTchOpencourse().getPeTchCourse().getName() + "扣费");
					this.getGeneralDao().save(prFeeDetail);
					count++;
				}
			}
			
			//公共选修课
			if(electiveList4!=null&&electiveList4.size()>0){
				for (PrTchStuElective prTchStuElective4 : electiveList4) {
					
//					if(!fee){
//						message +=" , "+prTchStuElective4.getPrTchOpencourse().getPeTchCourse().getName();
//						continue;
//					}
					
					//对费用进行判断
					Double feePercredit = peStudent.getPeFeeLevel().getFeePercredit();
					Double courseCredit = prTchStuElective4.getPrTchProgramCourse().getCredit();
					Double feeBalance = peStudent.getFeeBalance();
					Double oweFeeLimit = peStudent.getPeFeeLevel().getOweFeeLimit();
					double feeInactive = peStudent.getFeeInactive();
					feeBalance = Const.sub(feeBalance, feePercredit*courseCredit);
					feeInactive = Const.sub(feeInactive, feePercredit*courseCredit);
					if ((feeBalance+oweFeeLimit) < 0){
						fee = false;
						message +="</br>学生"+peStudent.getName()+"账户余额不足。" 
								+ prTchStuElective4.getPrTchOpencourse().getPeTchCourse().getName()
								+ "无法开课";
						continue;
					}
					
					//如果费用足够的话
					//开课
					prTchStuElective4.setEnumConstByFlagElectiveAdmission(enumConstByFlagElectiveAdmission);
					this.generalDao.save(prTchStuElective4);
					//设置学生账户余额
					peStudent.setFeeBalance(feeBalance);
					peStudent.setFeeInactive(feeInactive);
					peStudent = (PeStudent)this.generalDao.save(peStudent);
					
					//向费用明细表插入数据
					PrFeeDetail prFeeDetail = new PrFeeDetail();
					prFeeDetail.setPeStudent(peStudent);
					EnumConst enumConstByFlagFeeType = this.getMyListDao().getEnumConstByNamespaceCode("FlagFeeType", "3");
					prFeeDetail.setEnumConstByFlagFeeType(enumConstByFlagFeeType);
					EnumConst enumConstByFlagFeeCheck = this.getMyListDao().getEnumConstByNamespaceCode("FlagFeeCheck", "2");
					prFeeDetail.setEnumConstByFlagFeeCheck(enumConstByFlagFeeCheck);
					prFeeDetail.setFeeAmount(-feePercredit*courseCredit);
					prFeeDetail.setCreditAmount(feeBalance);
					prFeeDetail.setInputDate(new Date());
					prFeeDetail.setNote("开课" + prTchStuElective4.getPrTchOpencourse().getPeTchCourse().getName() + "扣费");
					this.getGeneralDao().save(prFeeDetail);
					count++;
				}
			}
		}
			message += "</br>开课总数量为"+count;
		return message;
	}

}
