package com.whaty.platform.entity.service.imp.teaching.elective;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.bean.PrTchProgramCourse;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.elective.StudentElectiveService;

public class StudentElectiveServiceImp implements StudentElectiveService {

	private GeneralDao generalDao;
	private MyListDAO myListDAO;
	
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public MyListDAO getMyListDAO() {
		return myListDAO;
	}

	public void setMyListDAO(MyListDAO myListDAO) {
		this.myListDAO = myListDAO;
	}

	public String saveElective(PeStudent student, PeSemester semester, String[] courseid) throws EntityException {
		
		//删除之前选课记录
		
		DetachedCriteria electiveDC = DetachedCriteria.forClass(PrTchStuElective.class);
		electiveDC.createCriteria("prTchOpencourse", "prTchOpencourse");
		electiveDC.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
		electiveDC.add(Restrictions.eq("peStudent", student));
		electiveDC.add(Restrictions.eq("enumConstByFlagElectiveAdmission.code", "0"));
		List alreadyChosenList = new ArrayList();
		alreadyChosenList = this.getGeneralDao().getList(electiveDC);
		
		for (Iterator iter = alreadyChosenList.iterator(); iter.hasNext();) {
			PrTchStuElective instance = (PrTchStuElective) iter.next();
			this.getGeneralDao().delete(instance);
			//spring 事务控制顺序 insert->update->delete
			this.getGeneralDao().getMyHibernateTemplate().flush();
		}

		if (courseid == null) {
			return "全部退课。操作成功";
		}
		
		//冲突次数
		int conflict = 0;
		StringBuffer sb = new StringBuffer();
		
		//拿到所选课程当前学期相对应的opencourse
		List openCourseList = new ArrayList();
		DetachedCriteria opencourseDC = DetachedCriteria.forClass(PrTchOpencourse.class);;
		for (int i = 0; i < courseid.length; i++) {
			opencourseDC = DetachedCriteria.forClass(PrTchOpencourse.class);
			PrTchProgramCourse pc = (PrTchProgramCourse)this.getMyListDAO().getById(PrTchProgramCourse.class, courseid[i]);
			if (student.getPeGrade().getSerialNumber().equals(semester.getSerialNumber())) {
				//新生
				if (pc.getUnit() != 1) {
					sb.append(pc.getPeTchCourse().getName() + "不是新生课程，现在还不能选。<br>");
				}
			} 
				if (pc.getPeTchCourse().getName().equals("毕业论文") || pc.getPeTchCourse().getName().equals("毕业实习")) {
					if (pc.getPeTchProgramGroup().getPeTchProgram().getPaperMinSemeser() >= semester.getSerialNumber()+1- student.getPeGrade().getSerialNumber()) {
						throw new EntityException("现在还没到毕业课程的最小学期，不能选毕业论文或毕业实习。");
					}
					Double credit = 0.0;
					//已修且拿到学分的课程
					DetachedCriteria alreadyDC = DetachedCriteria.forClass(PrTchStuElective.class);
					alreadyDC.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
					alreadyDC.add(Restrictions.eq("enumConstByFlagElectiveAdmission.code", "1"));
					alreadyDC.add(Restrictions.ge("scoreTotal", new Double(this.getMyListDAO().getSysValueByName("creditMustScore"))));
					alreadyDC.add(Restrictions.eq("peStudent", student));

					List list = this.getGeneralDao().getList(alreadyDC);
					//在修的课程
					DetachedCriteria learningDC = DetachedCriteria.forClass(PrTchStuElective.class);
					learningDC.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
					learningDC.createCriteria("prTchOpencourse", "prTchOpencourse").createAlias("peSemester", "peSemester");
					learningDC.add(Restrictions.eq("prTchOpencourse.peSemester", semester));
					learningDC.add(Restrictions.eq("peStudent", student));
					List learningList = this.getGeneralDao().getList(learningDC);
					
					list.addAll(learningList);
					
					for (Iterator iter = list.iterator(); iter.hasNext();) {
						PrTchStuElective instance = (PrTchStuElective) iter.next();
						credit += instance.getPrTchProgramCourse().getCredit();
					}
					
					if (credit < pc.getPeTchProgramGroup().getPeTchProgram().getPaperMinCreditHour()) {
						throw new EntityException("您的选修过的总学分与在修学分之和低于毕业论文最低学分。不能选毕业论文。");
					}
				}
			
			
			
			opencourseDC.createCriteria("peSemester", "peSemester");
			opencourseDC.add(Restrictions.eq("peTchCourse", pc.getPeTchCourse()));
			opencourseDC.add(Restrictions.eq("peSemester.flagActive", "1"));
			openCourseList.addAll(this.getGeneralDao().getList(opencourseDC));
			opencourseDC = null;
		}
		if (sb.length() > 0) {
			throw new EntityException(sb.toString());
		}
		
		for (int i = 0; i < openCourseList.size(); i++) {
			PrTchOpencourse pc1 = (PrTchOpencourse) openCourseList.get(i);
			if (i == openCourseList.size()-1) {
				break;
			}
			
//			try {
				for(int j = i+1; j < openCourseList.size(); j++) {
					PrTchOpencourse pc2 = (PrTchOpencourse) openCourseList.get(j);
					if(pc1.getAdviceExamNo()==null||pc2.getAdviceExamNo()==null){
						throw new EntityException("所选课程包含未设置建议考试场次的课程，请与管理员联系。");
					}
					if (pc1.getAdviceExamNo().equals(pc2.getAdviceExamNo())) {
						conflict += 1;
						sb.append(pc1.getPeTchCourse().getName() + "与" + pc2.getPeTchCourse().getName() + "考试场次冲突。<br>");
					}
					if (conflict > 1) {
						throw new EntityException("选课考试场次最多冲突 1 科。<br>" + sb.toString());
					}
				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new EntityException("所选课程包含未设置建议考试场次的课程，请与管理员联系。");
//			}
		}
		
		//保存选课记录
		PrTchProgramCourse pc;
		PrTchOpencourse opencourse;
		PrTchStuElective prTchStuElective = new PrTchStuElective();
		for (int i = 0; i < courseid.length; i++) {
			opencourseDC = DetachedCriteria.forClass(PrTchOpencourse.class);
			pc = (PrTchProgramCourse)this.getMyListDAO().getById(PrTchProgramCourse.class, courseid[i]);
			opencourseDC.createCriteria("peSemester", "peSemester");
			opencourseDC.add(Restrictions.eq("peTchCourse", pc.getPeTchCourse()));
			opencourseDC.add(Restrictions.eq("peSemester.flagNextActive", "1"));
			List opencourseList = new ArrayList();
			opencourseList = this.getGeneralDao().getList(opencourseDC);
			if(opencourseList==null||opencourseList.isEmpty()){
				throw new EntityException("你所选的 "+ pc.getPeTchCourse().getName()+"没有开课。<br>" );
			}
			opencourse = (PrTchOpencourse) opencourseList.get(0);
			prTchStuElective.setPeStudent(student);
			prTchStuElective.setPrTchProgramCourse(pc);
			prTchStuElective.setPrTchOpencourse(opencourse);
			prTchStuElective.setSsoUser(student.getSsoUser());
			prTchStuElective.setEnumConstByFlagElectiveAdmission(this.getMyListDAO().getEnumConstByNamespaceCode("FlagElectiveAdmission", "0"));
			prTchStuElective.setEnumConstByFlagScoreStatus(this.getMyListDAO().getEnumConstByNamespaceCode("FlagScoreStatus", "0"));
			prTchStuElective.setEnumConstByFlagScorePub(this.getMyListDAO().getEnumConstByNamespaceCode("FlagScorePub", "0"));
			prTchStuElective.setElectiveDate(new Date());
			
			//在选毕业论文课程的时候同时初始化3种成绩。因为成绩合成的时候会报错。
			if (prTchStuElective.getPrTchProgramCourse().getPeTchCourse().getName().equals("毕业论文")) {
				prTchStuElective.setScoreExam(0.0);
				prTchStuElective.setScoreHomework(0.0);
				prTchStuElective.setScoreUsual(0.0);
			}
			
			this.getGeneralDao().save(prTchStuElective);
			prTchStuElective = new PrTchStuElective();
			opencourseDC = null;
		}
		
		
		return "选课成功。";
	}

}
