package com.whaty.platform.entity.service.imp.teaching.examScore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.examScore.ExamScoreComposeService;

public class ExamScoreComposeServiceImp implements ExamScoreComposeService {

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

//	public String saveCompose() throws EntityException {
//
//		double scoreSystemRate = 0;
//		double scoreHomeworkRate = 0;
//		double scoreExamRate = 0;
//		double scoreUsualRate = 0;
//		long count = 0l;
//		StringBuffer sb = new StringBuffer();
//		// 先将考试课程分组
//		DetachedCriteria openCoursedc = DetachedCriteria
//				.forClass(PrTchOpencourse.class);
//		openCoursedc.createCriteria("peSemester", "peSemester");
//		openCoursedc.add(Restrictions.eq("peSemester.flagActive", "1"));
//		List openCourseList = this.getGeneralDao().getList(openCoursedc);
//
//		for (Iterator iter = openCourseList.iterator(); iter.hasNext();) {
//
//			PrTchOpencourse opencourse = (PrTchOpencourse) iter.next();
//			PeTchCourse course = opencourse.getPeTchCourse();
//			scoreHomeworkRate = course.getScoreHomeworkRate();
//			scoreExamRate = course.getScoreExamRate();
//			scoreUsualRate = course.getScoreUsualRate();
//
//			List stuElectiveList = new ArrayList();
//			if (scoreExamRate ==0) {
//				//合成的记录全来自选课表。
//				DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
//				dc.add(Restrictions.eq("prTchOpencourse", opencourse));
//				stuElectiveList = this.getGeneralDao().getList(dc);
//			} else {
//				//合成的记录全来自预约考试表的相关选课记录
//				DetachedCriteria dc = DetachedCriteria.forClass(PrExamBooking.class);
//				dc.createCriteria("peSemester", "peSemester");
//				dc.add(Restrictions.eq("peSemester.flagActive", "1"));
//				List stuExamBookingList = this.getGeneralDao().getList(dc);
//				for (Iterator iterator = stuExamBookingList.iterator(); iterator
//						.hasNext();) {
//					PrExamBooking prExamBooking = (PrExamBooking) iterator.next();
//					stuElectiveList.add(prExamBooking.getPrTchStuElective());
//				}
//			}
//			
//			for (Iterator iterator = stuElectiveList.iterator(); iterator
//					.hasNext();) {
//				PrTchStuElective instance = (PrTchStuElective) iterator.next();
//				double scoreExam = 0;
//				double scoreUsual = 0;
//				double scoreHomework = 0;
//				double scoreTotal = 0;
//				if (scoreExamRate != 0) {
//					try {
//						scoreExam = instance.getScoreExam() * scoreExamRate;
//					} catch (Exception e) {
//						sb.append(instance.getPeStudent().getTrueName() + course.getName() + "的考试成绩未录入。");
//						continue;
//					}
//				}
//				if (scoreHomeworkRate != 0) {
//					try {
//						scoreHomework = instance.getScoreHomework() * scoreHomeworkRate;
//					} catch (Exception e) {
//						sb.append(instance.getPeStudent().getTrueName() + course.getName() + "的作业成绩未录入。");
//						continue;
//					}
//				}
//				if (scoreUsualRate != 0) {
//					try {
//						scoreUsual = instance.getScoreUsual() * scoreUsualRate;
//					} catch (Exception e) {
//						sb.append(instance.getPeStudent().getTrueName() + course.getName() + "的平时成绩未录入。");
//						continue;
//					}
//				}
//				scoreTotal = scoreHomework+scoreUsual+scoreExam;
//				instance.setScoreTotal(scoreTotal);
//				count += 1;
//				
//			}
//		}
//
//		return count + "条记录合成成绩成功。<br>" + sb.toString();
//	}
	
	//批量合成
	public String saveCompose() throws EntityException {
		
		double scoreHomeworkRate = 0;
		double scoreExamRate = 0;
		double scoreUsualRate = 0;
		long count = 0l;
		StringBuffer sb = new StringBuffer();
		
		DetachedCriteria peTchCourseDC = DetachedCriteria.forClass(PeTchCourse.class);
		peTchCourseDC.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid").add(Restrictions.eq("code", "1"));
		peTchCourseDC.add(Restrictions.ne("name", "毕业论文"));
		peTchCourseDC.add(Restrictions.ne("name", "毕业实习"));
		List peTchCourseList = this.getGeneralDao().getList(peTchCourseDC);
		for (Iterator iter = peTchCourseList.iterator(); iter.hasNext();) {
			PeTchCourse course = (PeTchCourse) iter.next();
			
			scoreHomeworkRate =course.getScoreHomeworkRate();
			scoreExamRate = course.getScoreExamRate();
			scoreUsualRate = course.getScoreUsualRate();
			List stuElectiveList = new ArrayList();
			
			if (course.getScoreExamRate() == 0) {
				//该课程不考试。数据来源自选课表中，当前学期的选课记录。
				DetachedCriteria electiveDC = DetachedCriteria.forClass(PrTchStuElective.class);
				electiveDC.createCriteria("peStudent","peStudent");
				electiveDC.createCriteria("prTchOpencourse", "prTchOpencourse").createCriteria("peSemester", "peSemester");
				electiveDC.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
				electiveDC.add(Restrictions.eq("prTchOpencourse.peTchCourse", course));
				electiveDC.add(Restrictions.eq("peSemester.flagActive", "1"));
				electiveDC.add(Restrictions.eq("enumConstByFlagElectiveAdmission.code", "1"));
//				electiveDC.addOrder(Order.desc("peStudent"));
				stuElectiveList = this.getGeneralDao().getList(electiveDC);
			} else {
				//该课程考试。数据来自预约考试表。
				DetachedCriteria electiveDC = DetachedCriteria.forClass(PrExamBooking.class);
				electiveDC.createCriteria("prTchStuElective", "prTchStuElective").createAlias("peStudent","peStudent").createAlias("prTchOpencourse","prTchOpencourse");
				electiveDC.createCriteria("peSemester", "peSemester");
				electiveDC.add(Restrictions.eq("peSemester.flagActive", "1"));
				electiveDC.add(Restrictions.eq("prTchOpencourse.peTchCourse", course));
//				electiveDC.addOrder(Order.desc("prTchStuElective.peStudent"));
				List stuExamBookingList = this.getGeneralDao().getList(electiveDC);
				for (Iterator iterator = stuExamBookingList.iterator(); iterator
						.hasNext();) {
					PrExamBooking prExamBooking = (PrExamBooking) iterator.next();
					stuElectiveList.add(prExamBooking.getPrTchStuElective());
				}
			}
			String stuName = "";
			boolean same = true;
			for (Iterator iterator = stuElectiveList.iterator(); iterator
					.hasNext();) {
				PrTchStuElective instance = (PrTchStuElective) iterator.next();
				double scoreExam = 0;
				double scoreUsual = 0;
				double scoreHomework = 0;
				double scoreTotal = 0;
				if (scoreExamRate != 0) {
					try {
						scoreExam = instance.getScoreExam() * scoreExamRate;
					} catch (Exception e) {
						if(same){
							sb.append("【" + course.getName() + "】");
							same = false;
						}
						sb.append(instance.getPeStudent().getTrueName() + "的考试成绩未录入。");
						continue;
					}
				}
				if (scoreHomeworkRate != 0) {
					try {
						scoreHomework = instance.getScoreHomework() * scoreHomeworkRate;
					} catch (Exception e) {
						if(same){
							sb.append("【" + course.getName() + "】");
							same = false;
						}
						sb.append(instance.getPeStudent().getTrueName() + "的作业成绩未录入。");
						continue;
					}
				}
				if (scoreUsualRate != 0) {
					try {
						scoreUsual = instance.getScoreUsual() * scoreUsualRate;
					} catch (Exception e) {
						if(same){
							sb.append("【" + course.getName() + "】");
							same = false;
						}
						sb.append(instance.getPeStudent().getTrueName() + "的平时成绩未录入。");
						continue;
					}
				}
				scoreTotal = scoreHomework+scoreUsual+scoreExam;
				instance.setScoreTotal(scoreTotal);
				this.getGeneralDao().save(instance);
				count += 1;
			}
		}
		return count + "条记录合成成绩成功。<br>" + sb.toString();
	}
	
	//单个合成
	public String saveComposeSingle(String peCourseName,String scoreExamRate1,String scoreHomeworkRate1,
			String scoreUsualRate1,String peEdutypeName,String peGradeName,String peMajorName) 
			throws EntityException {
		
		double scoreExamRate = Double.parseDouble(scoreExamRate1)/100;
		double scoreHomeworkRate = Double.parseDouble(scoreHomeworkRate1)/100;
		double scoreUsualRate = Double.parseDouble(scoreUsualRate1)/100;
		double scoreExam = 0;
		double scoreUsual = 0;
		double scoreHomework = 0;
		double scoreTotal = 0;
		long count = 0l;
		StringBuffer sb = new StringBuffer();
		
		//删除选中专业的选课记录的成绩。
		DetachedCriteria deleteStuElectiveDC = DetachedCriteria.forClass(PrTchStuElective.class);
		deleteStuElectiveDC.createCriteria("peStudent", "peStudent").createAlias("peEdutype", "peEdutype")
							.createAlias("peMajor", "peMajor").createAlias("peGrade", "peGrade");
		deleteStuElectiveDC.createCriteria("prTchOpencourse", "prTchOpencourse").createAlias("peTchCourse", "peTchCourse").createAlias("peSemester", "peSemester");
		
		if(peEdutypeName != null&& peEdutypeName.length()>0) {
			deleteStuElectiveDC.add(Restrictions.eq("peEdutype.name", peEdutypeName));
		}
		if(peGradeName != null&&peGradeName.length()>0) {
			deleteStuElectiveDC.add(Restrictions.eq("peGrade.name", peGradeName));
		}
		if(peMajorName != null&&peMajorName.length()>0) {
			deleteStuElectiveDC.add(Restrictions.eq("peMajor.name", peMajorName));
		}
		deleteStuElectiveDC.add(Restrictions.eq("peSemester.flagActive", "1"));
		deleteStuElectiveDC.add(Restrictions.eq("peTchCourse.name", peCourseName));
		List deleteStuList = this.getGeneralDao().getList(deleteStuElectiveDC);
		for (Iterator iter = deleteStuList.iterator(); iter.hasNext();) {
			PrTchStuElective instance = (PrTchStuElective) iter.next();
			instance.setScoreTotal(null);
		}
		
		//再覆盖成绩。
		String id = this.getMyListDAO().getIdByName("PeTchCourse", peCourseName);
		PeTchCourse course = (PeTchCourse) this.getMyListDAO().getById(PeTchCourse.class, id);
		List<PrTchStuElective> stuElectiveList = new ArrayList();
		if (scoreExamRate == 0) {
			//数据来自选课表
			DetachedCriteria electiveDC = DetachedCriteria.forClass(PrTchStuElective.class);
			electiveDC.createCriteria("prTchOpencourse", "prTchOpencourse").createAlias("peSemester", "peSemester");;
			electiveDC.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
			electiveDC.add(Restrictions.eq("prTchOpencourse.peTchCourse", course));
			electiveDC.add(Restrictions.eq("peSemester.flagActive", "1"));
			electiveDC.add(Restrictions.eq("enumConstByFlagElectiveAdmission.code", "1"));
			DetachedCriteria dcStudent = electiveDC.createCriteria("peStudent", "peStudent");
			
			if(peEdutypeName != null&& peEdutypeName.length()>0) {
				dcStudent.createCriteria("peEdutype", "peEdutype").add(Restrictions.eq("name", peEdutypeName));
			}
			if(peGradeName != null&&peGradeName.length()>0) {
				dcStudent.createCriteria("peGrade", "peGrade").add(Restrictions.eq("name", peGradeName));
			}
			if(peMajorName != null&&peMajorName.length()>0) {
				dcStudent.createCriteria("peMajor", "peMajor").add(Restrictions.eq("name", peMajorName));
			}
			stuElectiveList = this.getGeneralDao().getList(electiveDC);
			
		} else {
			//数据来自预约考试表
			DetachedCriteria electiveDC = DetachedCriteria.forClass(PrExamBooking.class);
			electiveDC.createCriteria("peSemester", "peSemester");
			electiveDC.add(Restrictions.eq("peSemester.flagActive", "1"));
			DetachedCriteria dcElective = electiveDC.createCriteria("prTchStuElective", "prTchStuElective");
			DetachedCriteria dcStudent = dcElective.createCriteria("peStudent", "peStudent");
			dcElective.createCriteria("prTchOpencourse", "prTchOpencourse").add(Restrictions.eq("peTchCourse", course));
			if(peEdutypeName != null&& peEdutypeName.length()>0) {
				dcStudent.createCriteria("peEdutype", "peEdutype").add(Restrictions.eq("name", peEdutypeName));
			}
			if(peGradeName != null&&peGradeName.length()>0) {
				dcStudent.createCriteria("peGrade", "peGrade").add(Restrictions.eq("name", peGradeName));
			}
			if(peMajorName != null&&peMajorName.length()>0) {
				dcStudent.createCriteria("peMajor", "peMajor").add(Restrictions.eq("name", peMajorName));
			}
			List stuExamBookingList = this.getGeneralDao().getList(electiveDC);
			for (Iterator iterator = stuExamBookingList.iterator(); iterator
					.hasNext();) {
				PrExamBooking prExamBooking = (PrExamBooking) iterator.next();
				stuElectiveList.add(prExamBooking.getPrTchStuElective());
			}
		}
//		for (Iterator iterator = stuElectiveList.iterator(); iterator.hasNext();) {
//			PrTchStuElective instance = (PrTchStuElective) iterator.next();
		for (PrTchStuElective instance:stuElectiveList){
			if (scoreExamRate != 0) {
				try {
					scoreExam = instance.getScoreExam() * scoreExamRate;
				} catch (Exception e) {
					sb.append(instance.getPeStudent().getTrueName() + "的考试成绩未录入。");
					continue;
				}
			}
			if (scoreHomeworkRate != 0) {
				try {
					scoreHomework = instance.getScoreHomework() * scoreHomeworkRate;
				} catch (Exception e) {
					sb.append(instance.getPeStudent().getTrueName() + "的作业成绩未录入。");
					continue;
				}
			}
			if (scoreUsualRate != 0) {
				try {
					scoreUsual = instance.getScoreUsual() * scoreUsualRate;
				} catch (Exception e) {
					sb.append(instance.getPeStudent().getTrueName() + "的平时成绩未录入。");
					continue;
				}
			}
			scoreTotal = scoreHomework+scoreUsual+scoreExam;
			instance.setScoreTotal(scoreTotal);
			this.getGeneralDao().save(instance);
			count += 1;
		}
		
		return count + "条记录合成成绩成功。<br>" + sb.toString();
	}

}
