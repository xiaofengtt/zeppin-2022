package com.whaty.platform.entity.service.imp.teaching.elective;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.elective.ElectiveCancelService;

public class ElectiveCancelServiceImp implements ElectiveCancelService {

	private GeneralDao generalDao;
	
	private MyListDAO myListDAO;
	
	public MyListDAO getMyListDAO() {
		return myListDAO;
	}

	public void setMyListDAO(MyListDAO myListDAO) {
		this.myListDAO = myListDAO;
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public String delElective(List ids) throws EntityException{
		
		StringBuffer sb = new StringBuffer();
		
		for (Iterator iter = ids.iterator(); iter.hasNext();) {
			
			String id = (String) iter.next();
			PrTchStuElective stuElective = (PrTchStuElective) this.getGeneralDao().getById(id);
			PeTchCourse course = stuElective.getPrTchProgramCourse().getPeTchCourse();
			
			//判断选课是否开课
			if (stuElective.getEnumConstByFlagElectiveAdmission().getCode().equals("1")) {
				PeStudent student = stuElective.getPeStudent();
				Double feePercredit = student.getPeFeeLevel().getFeePercredit();
				Double courseCredit = stuElective.getPrTchProgramCourse().getCredit();
				Double feeBalance = student.getFeeBalance();
				feeBalance  += feePercredit * courseCredit;
				student.setFeeBalance(feeBalance);
				try {
					this.getGeneralDao().save(student);
					this.getGeneralDao().delete(stuElective);
					sb.append("已将已开课的 " + course.getName() + "退课。" + "<br>");
					PrFeeDetail prFeeDetail = new PrFeeDetail();
					prFeeDetail.setPeStudent(student);
					EnumConst enumConstByFlagFeeType = this.getMyListDAO().getEnumConstByNamespaceCode("FlagFeeType", "4");
					prFeeDetail.setEnumConstByFlagFeeType(enumConstByFlagFeeType);
					EnumConst enumConstByFlagFeeCheck = this.getMyListDAO().getEnumConstByNamespaceCode("FlagFeeCheck", "2");
					prFeeDetail.setEnumConstByFlagFeeCheck(enumConstByFlagFeeCheck);
					prFeeDetail.setFeeAmount(+feePercredit*courseCredit);
					prFeeDetail.setCreditAmount(feeBalance);
					prFeeDetail.setInputDate(new Date());
					prFeeDetail.setNote(stuElective.getPrTchOpencourse().getPeTchCourse().getName() +"开课后退课退费");
					this.getGeneralDao().save(prFeeDetail);
				} catch (Exception e) {
					throw new EntityException("删除选课失败!");
				}
			} else {
				try {
					this.getGeneralDao().delete(stuElective);
					sb.append("已将课程" + course.getName() + "退课。" + "<br>");
				} catch (Exception e) {
					throw new EntityException("删除选课失败!");
				}
			}
		}
		return sb.toString();
	}

}
