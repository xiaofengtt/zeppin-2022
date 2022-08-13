package com.whaty.platform.entity.service.imp.recruit.recExam;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeExamPatrol;
import com.whaty.platform.entity.bean.PeRecRoom;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PrRecExamCourseTime;
import com.whaty.platform.entity.bean.PrRecExamStuCourse;
import com.whaty.platform.entity.bean.PrRecPatrolSetting;
import com.whaty.platform.entity.bean.PrRecPlanMajorEdutype;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.recruit.recExam.PrRecExamStuCourseService;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * 入学考试学生-课程 关系 自动分配功能, 批量上传入学考试成绩
 * 
 * @author 李冰
 * 
 */
public class PrRecExamStuCourseServiceImp implements PrRecExamStuCourseService {

	private GeneralDao generalDao;

	/**
	 * 添加学生课程关系记录
	 */

	public String save(List list) throws EntityException {
		String result = "";
		// 首先删除原有数据
		int n = this.getGeneralDao().executeBySQL(this.deleteSQL());
		// 插入新的学生课程关系
		for (int j = 0; j < list.size(); j++) {
			Object[] str = (Object[]) list.get(j);
			PrRecExamStuCourse prRecExamStuCourse = new PrRecExamStuCourse();
			PeRecStudent peRecStudent = new PeRecStudent();
			PrRecExamCourseTime prRecExamCourseTime = new PrRecExamCourseTime();
			peRecStudent.setId((String) str[0]);
			prRecExamCourseTime.setId((String) str[1]);
			prRecExamStuCourse.setPeRecStudent(peRecStudent);
			prRecExamStuCourse.setPrRecExamCourseTime(prRecExamCourseTime);
			this.getGeneralDao().save(prRecExamStuCourse);
		}
		result += "分配考试课程成功，共分配成功" + list.size() + "条数据";
		return result;
	}

	/**
	 * 插入操作之前的删除语句，先把当前活动招生批次学生的信息全部删除
	 * 
	 * @return
	 */
	private String deleteSQL() {
		String sql = "			delete from pr_rec_exam_stu_course		"
				+ "			 where fk_rec_student_id in	"
				+ "		       (select student.id	"
				+ "			          from pe_rec_student            student,	"
				+ "			               pr_rec_plan_major_site    plan_site,	"
				+ "			               pr_rec_plan_major_edutype plan_edutype,	"
				+ "			               pe_recruitplan            recruitplan	"
				+ "			         where student.fk_rec_major_site_id = plan_site.id	"
				+ "			           and plan_site.fk_rec_plan_major_edutype_id = plan_edutype.id	"
				+ "			           and plan_edutype.fk_recruitplan_id = recruitplan.id	"
				+ "			           and recruitplan.flag_active = '1')	";
		return sql;
	}


	public int saveUploadScore(File file) throws EntityException {
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
		Set<PrRecExamStuCourse> scoreSet = new HashSet();

		for (int i = 1; i < rows; i++) {
			try {
				String peRecStudentId = "";// 报名信息表id
				String prRecExamCourseTimeId = "";// 课程时间表id
				PrRecExamStuCourse prRecExamStuCourse = new PrRecExamStuCourse();

				DetachedCriteria dcPeRecStudent = DetachedCriteria.forClass(PeRecStudent.class);
				DetachedCriteria dcPeRecruitplan = dcPeRecStudent
					.createCriteria("prRecPlanMajorSite", "prRecPlanMajorSite")
					.createCriteria("prRecPlanMajorEdutype", "prRecPlanMajorEdutype")
					.createCriteria("peRecruitplan", "peRecruitplan");
				dcPeRecruitplan.add(Restrictions.eq("flagActive", "1"));

				temp = sheet.getCell(0, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，考生姓名为空！<br/>");
					continue;
				}
				dcPeRecStudent.add(Restrictions.eq("name", temp));
				
				temp = sheet.getCell(1, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，准考证号不能为空！<br/>");
					continue;
				}
				dcPeRecStudent.add(Restrictions.eq("examCardNum", temp));

				List<PeRecStudent> studentList = new ArrayList();
				try {
					studentList = this.getGeneralDao().getList(dcPeRecStudent);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (studentList.size() == 0) {
					msg.append("第" + (i + 1) + "行数据，学生姓名或者准考证号错误！<br/>");
					continue;
				}
				peRecStudentId = studentList.get(0).getId();
				
				temp = sheet.getCell(2, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，考试课程名称不能为空！<br/>");
					continue;
				}
				
				PrRecExamCourseTime prRecExamCourseTime = new PrRecExamCourseTime();
				DetachedCriteria dcPrRecExamCourseTime = DetachedCriteria
						.forClass(PrRecExamCourseTime.class);
				DetachedCriteria dcPeRecExamcourse = dcPrRecExamCourseTime
						.createCriteria("peRecExamcourse", "peRecExamcourse");
				DetachedCriteria dcRecruitplan = dcPrRecExamCourseTime
						.createCriteria("peRecruitplan", "peRecruitplan");
				dcPeRecExamcourse.add(Restrictions.eq("name", temp));
				dcRecruitplan.add(Restrictions.eq("flagActive", "1"));
				List<PrRecExamCourseTime> courseTimeList = new ArrayList();
				try {
					courseTimeList = this.getGeneralDao().getList(
							dcPrRecExamCourseTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (courseTimeList.size() == 0) {
					msg.append("第" + (i + 1) + "行数据，考试课程名称在当前考试批次中不存在！<br/>");
					continue;
				}
				prRecExamCourseTimeId = courseTimeList.get(0).getId();
				
				DetachedCriteria dcPrRecExamStuCourse = DetachedCriteria
						.forClass(PrRecExamStuCourse.class);
				DetachedCriteria dcPeRecStudent0 = dcPrRecExamStuCourse
						.createCriteria("peRecStudent", "peRecStudent");
				dcPeRecStudent0.add(Restrictions.eq("id", peRecStudentId));
				DetachedCriteria dcPrRecExamCourseTime0 = dcPrRecExamStuCourse
						.createCriteria("prRecExamCourseTime",
								"prRecExamCourseTime");
				dcPrRecExamCourseTime0.add(Restrictions.eq("id",
						prRecExamCourseTimeId));
				List<PrRecExamStuCourse> examStuCourseList = new ArrayList();
				try {
					examStuCourseList = this.getGeneralDao().getList(
							dcPrRecExamStuCourse);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (examStuCourseList.size() == 0) {
					msg.append("第" + (i + 1) + "行数据，学生与考试课程的关系不存在！<br/>");
					continue;
				}
				
				prRecExamStuCourse = examStuCourseList.get(0);
//				if (!(prRecExamStuCourse.getScore() == null || prRecExamStuCourse
//						.getScore().equals(""))) {
//					msg.append("第" + (i + 1) + "行数据，学生相应考试课程的成绩已经存在！<br/>");
//					continue;
//				}

				temp = sheet.getCell(3, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，成绩不能为空！<br/>");
					continue;
				}
				if(!temp.matches(Const.score)){
					msg.append("第" + (i + 1) + "行数据，成绩"+Const.scoreMessage+"！<br/>");
					continue;
				}
				Double score;
				try {
					score = Double.parseDouble(temp);
				} catch (RuntimeException e) {
					msg.append("第" + (i + 1) + "行数据，成绩格式有误！<br/>");
					continue;
				}
				prRecExamStuCourse.setScore(score);

				if (!scoreSet.add(prRecExamStuCourse)) {
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
			msg.append("学生入学考试成绩批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}

		for (PrRecExamStuCourse course : scoreSet) {
			try {
				this.getGeneralDao().save(course);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EntityException("批量上传学生入学考试成绩失败");
			}
		}
		return count;

	}
	public String saveExamRoom(){
		StringBuffer msg = new StringBuffer();
		int number = 0; // 分配考场的学生总数
		// 取出当前活动的招生批次
		PeRecruitplan	peRecruitplan = this.getRecruit();
		
		// 先删除考场表，报名信息表中的考场字段自动设置为空
		DetachedCriteria dcPeRecRoom = DetachedCriteria.forClass(PeRecRoom.class);
		dcPeRecRoom.createCriteria("peRecruitplan", "peRecruitplan")
					.add(Restrictions.eq("flagActive", "1"));
		List<PeRecRoom> peRecRoomList = this.getGeneralDao().getList(dcPeRecRoom);
		for (PeRecRoom peRecRoom : peRecRoomList) {
			this.getGeneralDao().delete(peRecRoom);
		}
		
		/*废弃准考证号生成方式
		//先清空当前批次的学生原准考证号
		DetachedCriteria dcPeRecStudent = DetachedCriteria.forClass(PeRecStudent.class);
		dcPeRecStudent.createCriteria("prRecPlanMajorSite", "prRecPlanMajorSite")
				.createCriteria("prRecPlanMajorEdutype", "prRecPlanMajorEdutype")
				.createCriteria("peRecruitplan", "peRecruitplan")
				.add(Restrictions.eq("flagActive", "1"));
		dcPeRecStudent.setProjection(Projections.projectionList()
				.add(Projections.property("id")));
		List<String> peRecStudentList = this.getGeneralDao().getList(dcPeRecStudent);
		if (peRecStudentList.size()>0) {
			this.getGeneralDao().setEntityClass(PeRecStudent.class);
			int n = this.getGeneralDao().updateColumnByIds(peRecStudentList, "examCardNum", "");
		}
		*/
		
		// 取得当前的考试学习中心
		List<PeSite> siteList = this.getExamSite();
		if (siteList.size()==0) {
			return "当前招生批次下未设置考试学习中心，无法分配考场。";
		}
		
		// 查询出当前批次下的所有层次
		DetachedCriteria dcPrRecPlanMajorEdutype = DetachedCriteria.forClass(PrRecPlanMajorEdutype.class);
		DetachedCriteria dcPeEdutype = dcPrRecPlanMajorEdutype.createCriteria("peEdutype", "peEdutype");
		dcPrRecPlanMajorEdutype.createCriteria("peRecruitplan", "peRecruitplan")
								.add(Restrictions.eq("flagActive", "1"));
		dcPeEdutype.setProjection(Projections.projectionList()
				.add(Projections.distinct(Projections.property("peEdutype.id"))));
		List<String> edutypeList = this.getGeneralDao().getList(dcPrRecPlanMajorEdutype);
		if (edutypeList.size()==0) {
			return "当前招生批次下未设置报考层次，无法分配考场。";
		}
		
		int room = 0; // 保存考场号
		int seat = 0; // 保存座位号
		
		for (int i = 0; i < siteList.size(); i++) {
			room = 0; // 将考场号设为0
			for (int j = 0; j < edutypeList.size(); j++) {
				PeRecRoom peRecRoom = new PeRecRoom(); //考场表
				//取出课程标记为1的所有考生，按课程和专业排序。
				DetachedCriteria dcPrRecExamStuCourse = DetachedCriteria.forClass(PrRecExamStuCourse.class);
				DetachedCriteria dcStudent = dcPrRecExamStuCourse.createCriteria("peRecStudent", "peRecStudent");
				DetachedCriteria dcEnumConstByFlagNoexam = 
					dcStudent.createCriteria("enumConstByFlagNoexam", "enumConstByFlagNoexam");
				dcEnumConstByFlagNoexam.add(Restrictions.eq("code", "0"));
				DetachedCriteria dcRecPlanMajorSite = 
						dcStudent.createCriteria("prRecPlanMajorSite", "prRecPlanMajorSite");
//				dcRecPlanMajorSite.createCriteria("peSite", "peSite").createCriteria("peSite", "peSite1")
//						.add(Restrictions.eq("id", siteList.get(i).getId()));
				dcRecPlanMajorSite.createCriteria("peSite", "peSite").add(Restrictions
						.or(Restrictions.eq("peSite.id", siteList.get(i).getId()),
								Restrictions.eq("peSite.peSite.id", siteList.get(i).getId())));
				DetachedCriteria dcRecPlanMajorEdutype = 
					dcRecPlanMajorSite.createCriteria("prRecPlanMajorEdutype", "prRecPlanMajorEdutype");
				DetachedCriteria dcMajor = dcRecPlanMajorEdutype.createCriteria("peMajor","peMajor");
				DetachedCriteria dcEdutype = dcRecPlanMajorEdutype.createCriteria("peEdutype", "peEdutype");
				dcEdutype.add(Restrictions.eq("id", edutypeList.get(j)));
				DetachedCriteria dcPrRecExamCourseTime = 
					dcPrRecExamStuCourse.createCriteria("prRecExamCourseTime", "prRecExamCourseTime");
				DetachedCriteria dcRecruit = 
					dcPrRecExamCourseTime.createCriteria("peRecruitplan", "peRecruitplan");
				dcRecruit.add(Restrictions.eq("flagActive", "1"));
				DetachedCriteria dcPeRecExamcourse = 
					dcPrRecExamCourseTime.createCriteria("peRecExamcourse", "peRecExamcourse");
				DetachedCriteria dcEnumConstByFlagArrangeRoom =
					dcPeRecExamcourse.createCriteria("enumConstByFlagArrangeRoom", "enumConstByFlagArrangeRoom");
				dcEnumConstByFlagArrangeRoom.add(Restrictions.eq("code", "1"));
				dcPeRecExamcourse.addOrder(Order.asc("name"));
				dcMajor.addOrder(Order.asc("name"));
				
				List<PrRecExamStuCourse> stuCourseList = this.getGeneralDao().getList(dcPrRecExamStuCourse);
				
				if (stuCourseList.size()>0) {
				
					room ++; // 考场号+1
					seat = 0; // 座位号设0
					// 设置考场信息
					PeRecRoom examRoom = new PeRecRoom();
					examRoom.setCode(this.toThree(room));
					examRoom.setPeRecruitplan(peRecruitplan);
					// 考场全名 招生批次+站点+场号
					examRoom.setName(peRecruitplan.getName() 
							+ siteList.get(i).getName() + examRoom.getCode());
					examRoom.setPeSite(siteList.get(i));
					// 保存考场信息
					peRecRoom = (PeRecRoom)this.getGeneralDao().save(examRoom);
				
				}
				for (int k = 0; k < stuCourseList.size(); k++) {
					if (k > 0) {
						// 判断和前一个学生的课程是否相同
						boolean course = stuCourseList.get(k).getPrRecExamCourseTime().getId().
											equals(stuCourseList.get(k-1).getPrRecExamCourseTime().getId());
						// 课程与前一个不同 || 座位==30
						if (!course || seat == 30) {
							room ++; // 考场号+1
							seat = 1;
							// 设置考场信息
							PeRecRoom examRoom = new PeRecRoom();
							examRoom.setCode(this.toThree(room));
							examRoom.setPeRecruitplan(peRecruitplan);
							// 考场全名 招生批次+站点+场号
							examRoom.setName(peRecruitplan.getName() 
									+ siteList.get(i).getName() + examRoom.getCode());
							examRoom.setPeSite(siteList.get(i));
							// 保存考场信息
							peRecRoom = (PeRecRoom)this.getGeneralDao().save(examRoom);
							
							// 设置学生信息
							stuCourseList.get(k).getPeRecStudent().setPeRecRoom(peRecRoom);
							stuCourseList.get(k).getPeRecStudent().setSeatNum(this.toTwo(seat));
							
							/*废弃准考证号生成方式
							stuCourseList.get(k).getPeRecStudent()
									.setExamCardNum(peRecruitplan.getCode() + siteList.get(i).getCode()
									+ peRecRoom.getCode() + this.toThree(seat));//准考证号：批次+学习中心+考场号+座位号
									*/
							// 保存学生信息
							this.getGeneralDao().save(stuCourseList.get(k).getPeRecStudent());
							number++;
							continue;
						}
					}
					seat ++; // 座位号+1
					// 设置学生信息
					stuCourseList.get(k).getPeRecStudent().setPeRecRoom(peRecRoom);
					stuCourseList.get(k).getPeRecStudent().setSeatNum(this.toTwo(seat));
					/*废弃准考证号生成方式
					stuCourseList.get(k).getPeRecStudent()
							.setExamCardNum(peRecruitplan.getCode() + siteList.get(i).getCode()
							+ peRecRoom.getCode() + this.toThree(seat));//准考证号：考场全名+座位号
							*/
					// 保存学生信息
					this.getGeneralDao().save(stuCourseList.get(k).getPeRecStudent());
					number++;
					
				}
				
			}
			
		}
		
		/*废弃准考证号生成方式
		//生成准考证号

		//免试生准考证号：招生批次+学习中心+000+序列号
			int num = 0;
			DetachedCriteria dcNoexamRecStudent = DetachedCriteria.forClass(PeRecStudent.class);
			DetachedCriteria dcNoexamRecPlanMajorSite = dcNoexamRecStudent
						.createCriteria("prRecPlanMajorSite", "prRecPlanMajorSite");
			DetachedCriteria dcSite = dcNoexamRecPlanMajorSite.createCriteria("peSite", "peSite");
			dcNoexamRecPlanMajorSite.createCriteria("prRecPlanMajorEdutype", "prRecPlanMajorEdutype")
							.createCriteria("peRecruitplan", "peRecruitplan")
							.add(Restrictions.eq("flagActive", "1"));
			DetachedCriteria dcNoexamEnumConstByFlagNoexam = dcNoexamRecStudent
						.createCriteria("enumConstByFlagNoexam", "enumConstByFlagNoexam")
						.add(Restrictions.eq("code", "1"));
			DetachedCriteria dcEnumConstByFlagNoexamStatus = dcNoexamRecStudent
						.createCriteria("enumConstByFlagNoexamStatus", "enumConstByFlagNoexamStatus")
						.add(Restrictions.eq("code", "1"));
			dcSite.addOrder(Order.asc("name"));
			List<PeRecStudent> noexamStudentList = this.getGeneralDao().getList(dcNoexamRecStudent);
			int n = 0;
			for (int j = 0; j < noexamStudentList.size(); j++) {
				num++;
				if (j>0) {
					if (!noexamStudentList.get(j).getPrRecPlanMajorSite().getPeSite().getCode()
							.equals(noexamStudentList.get(j-1).getPrRecPlanMajorSite().getPeSite().getCode())) {
						num = 1;
					}
						
				}
				String examcardNum = "";
				if (num<1000) {
				examcardNum = noexamStudentList.get(j).getPrRecPlanMajorSite().getPrRecPlanMajorEdutype()
				.getPeRecruitplan().getCode() + noexamStudentList.get(j).getPrRecPlanMajorSite().getPeSite().getCode()
				+ "000" + this.toThree(num);
				} else {
					examcardNum = noexamStudentList.get(j).getPrRecPlanMajorSite().getPrRecPlanMajorEdutype()
					.getPeRecruitplan().getCode() + noexamStudentList.get(j).getPrRecPlanMajorSite().getPeSite().getCode()
					+ "000" + num;
				}
				noexamStudentList.get(j).setExamCardNum(examcardNum);
				this.getGeneralDao().save(noexamStudentList.get(j));
				n ++;
			}
			*/
		String infor = "";
		if (number > 0) {
			infor = "成功为" + number + "名考试生分配考场";
		} else {
			infor = "没有为任何学生分配考场";
		}
//		if (n > 0) {
//			infor +="</br>为" + n + "名免试生分配准考证号";
//		} else {
//			infor +="</br>没有为免试生分配准考证号";
//		}
		return infor;

	}
	/**
	 * 将考场号设置为ddd的格式
	 * @param num 考场
	 * @return
	 */
	private String toThree(int num){
		String no = num + "";
		if (no.length()==1) {
			no = "00" + no;
		} else if (no.length()==2) {
			no = "0" + no;
		}
		return no;
	}
	/**
	 * 将座位号设置成01的格式
	 * @param num 座位号
	 * @return
	 */
	private String toTwo(int num){
		String no = num + "";
		if (no.length()==1) {
			no = "0" + no;
		}
		return no;
	}
//	/**
//	 *  查询出当前招生批次下的所有考试中心 id 和 code 
//	 * @return
//	 */
//	private List<Object[]> examSite(){
//		
//		DetachedCriteria dcPrRecPlanMajorSite = DetachedCriteria.forClass(PrRecPlanMajorSite.class);
//		DetachedCriteria dcPeSite = dcPrRecPlanMajorSite.createCriteria("peSite", "peSite")
//							.createCriteria("peSite", "site");
//		DetachedCriteria dcRecruitplan = dcPrRecPlanMajorSite
//							.createCriteria("prRecPlanMajorEdutype", "prRecPlanMajorEdutype")
//							.createCriteria("peRecruitplan", "peRecruitplan");
//		dcRecruitplan.add(Restrictions.eq("flagActive", "1"));
//		dcPeSite.setProjection(Projections.projectionList()
//				.add(Projections.distinct(Projections.property("site.id")))
//				.add(Projections.property("site.code")));
//		List<Object[]> siteList = this.getGeneralDao().getList(dcPrRecPlanMajorSite);
//		return siteList;
//	}
	/**
	 * 取得已经分配了考场的学习中心
	 * @return
	 */
	private List<PeSite> getExamSite(){
		DetachedCriteria dcPrRecPlanMajorSite = DetachedCriteria.forClass(PrRecPlanMajorSite.class);
		DetachedCriteria dcPeSite = dcPrRecPlanMajorSite.createCriteria("peSite", "peSite");
		DetachedCriteria dcRecruitplan = dcPrRecPlanMajorSite
							.createCriteria("prRecPlanMajorEdutype", "prRecPlanMajorEdutype")
							.createCriteria("peRecruitplan", "peRecruitplan");
		dcRecruitplan.add(Restrictions.eq("flagActive", "1"));
		List<PrRecPlanMajorSite> siteList = this.getGeneralDao().getList(dcPrRecPlanMajorSite);
		Set<PeSite> peSiteSet = new HashSet();
		List<PeSite> site = new ArrayList<PeSite>();
		if (siteList.size()>0) {
			for (PrRecPlanMajorSite prRecPlanMajorSite : siteList) {
				if (prRecPlanMajorSite.getPeSite().getPeSite()!=null) {
					if (peSiteSet.add(prRecPlanMajorSite.getPeSite().getPeSite())) {
						site.add(prRecPlanMajorSite.getPeSite().getPeSite());
					}
				} else {
					if (peSiteSet.add(prRecPlanMajorSite.getPeSite())) {
						site.add(prRecPlanMajorSite.getPeSite());
					}
				}
			}
		}
		return site;
	}
	/**
	 * 自动分配巡考
	 * @return
	 */
	public String saveInspector(){
		// 取出报名巡考的人员
		DetachedCriteria dcPeExamPatrol = DetachedCriteria.forClass(PeExamPatrol.class);
		dcPeExamPatrol.createCriteria("enumConstByFlagIsXunkao", "enumConstByFlagIsXunkao")
		.add(Restrictions.eq("code", "1"));
		dcPeExamPatrol.addOrder(Order.asc("enumConstByGender"));
		List<PeExamPatrol> peExamPatrolList = this.getGeneralDao().getList(dcPeExamPatrol);
		PeRecruitplan	peRecruitplan = this.getRecruit();
		List<PeSite> sites = this.getExamSite();
		
		if (sites.size()*2>peExamPatrolList.size()){
			return "巡考报名人数不足以分配完所有考试学习中心";
		}
		
		// 分配前删除原分配结果
		DetachedCriteria dcPrRecPatrolSetting = DetachedCriteria.forClass(PrRecPatrolSetting.class);
		dcPrRecPatrolSetting.createCriteria("peRecruitplan", "peRecruitplan")
							.add(Restrictions.eq("flagActive", "1"));
		List<PrRecPatrolSetting> list = this.getGeneralDao().getList(dcPrRecPatrolSetting);
		for (PrRecPatrolSetting prRecPatrolSetting : list) {
			this.getGeneralDao().delete(prRecPatrolSetting);
		}
		
		int n = 0;
		boolean b = true;//用来判断，分配男或者女的标志
		int m = peExamPatrolList.size()-1;
		int count = 0;
		for (PeSite site : sites) {

			if (b) {
			// 保存入学考试巡考安排
			PrRecPatrolSetting prRecPatrolSetting = new PrRecPatrolSetting();
			prRecPatrolSetting.setPeExamPatrol(peExamPatrolList.get(n));
			prRecPatrolSetting.setPeSite(site);
			prRecPatrolSetting.setPeRecruitplan(peRecruitplan);
			this.getGeneralDao().save(prRecPatrolSetting);
			// 将巡考人员的监考状态设置为0
			peExamPatrolList.get(n).setEnumConstByFlagIsJiankao(
					this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsJiankao", "0"));
			this.getGeneralDao().save(peExamPatrolList.get(n));
			n ++;
			
			PrRecPatrolSetting prRecPatrolSetting2 = new PrRecPatrolSetting();
			prRecPatrolSetting2.setPeExamPatrol(peExamPatrolList.get(n));
			prRecPatrolSetting2.setPeSite(site);
			prRecPatrolSetting2.setPeRecruitplan(peRecruitplan);
			this.getGeneralDao().save(prRecPatrolSetting2);
			// 将巡考人员的监考状态设置为0
			peExamPatrolList.get(n).setEnumConstByFlagIsJiankao(
					this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsJiankao", "0"));
			this.getGeneralDao().save(peExamPatrolList.get(n));
			n ++;
			} else {
				// 保存入学考试巡考安排
				PrRecPatrolSetting prRecPatrolSetting = new PrRecPatrolSetting();
				prRecPatrolSetting.setPeExamPatrol(peExamPatrolList.get(m));
				prRecPatrolSetting.setPeSite(site);
				prRecPatrolSetting.setPeRecruitplan(peRecruitplan);
				this.getGeneralDao().save(prRecPatrolSetting);
				// 将巡考人员的监考状态设置为0
				peExamPatrolList.get(m).setEnumConstByFlagIsJiankao(
						this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsJiankao", "0"));
				this.getGeneralDao().save(peExamPatrolList.get(m));
				m --;
				
				PrRecPatrolSetting prRecPatrolSetting2 = new PrRecPatrolSetting();
				prRecPatrolSetting2.setPeExamPatrol(peExamPatrolList.get(m));
				prRecPatrolSetting2.setPeSite(site);
				prRecPatrolSetting2.setPeRecruitplan(peRecruitplan);
				this.getGeneralDao().save(prRecPatrolSetting2);
				// 将巡考人员的监考状态设置为0
				peExamPatrolList.get(m).setEnumConstByFlagIsJiankao(
						this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsJiankao", "0"));
				this.getGeneralDao().save(peExamPatrolList.get(m));
				m --;
				
			}
			count += 2;
			b = !b;
		}
		return "成功分配" + count + "名巡考人员";
	}
	
	/**
	 * 自动分配监考
	 * @return
	 */
	public String saveInvigilator() {
		// 根据session取得管理员所在的学习中心
		PeSite peSite = new PeSite();
		UserSession us = (UserSession)ServletActionContext
				.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us == null) {
			return "无分配监考的权限";
		}
		String userLoginType = us.getUserLoginType();
		SsoUser ssoUser = us.getSsoUser();
		// 根据管理员类型判断有无分配权限 并且取得所操作的学习中心
		if (userLoginType.equals("2")) {
			DetachedCriteria dcPeSitemanager = DetachedCriteria.forClass(PeSitemanager.class);
			dcPeSitemanager.createCriteria("ssoUser", "ssoUser")
							.add(Restrictions.eq("id", ssoUser.getId()));
			List<PeSitemanager> peSitemanagerList = this.getGeneralDao().getList(dcPeSitemanager);
			if (peSitemanagerList.size()==0) {
				return "无可操作站点";
			}
			if (peSitemanagerList.get(0).getPeSite()!=null) {
				peSite = peSitemanagerList.get(0).getPeSite();
			} else {
				return "无可操作站点";
			}
		} else {
			return "无自动分配监考的权限";
		}
//		peSite = this.getExamSite().get(0);
		PeRecruitplan	peRecruitplan = this.getRecruit();
		DetachedCriteria dcPeRecRoom = DetachedCriteria.forClass(PeRecRoom.class);
		DetachedCriteria dcPeRecruitplan =dcPeRecRoom.createCriteria("peRecruitplan", "peRecruitplan")
				.add(Restrictions.eq("flagActive", "1"));
		dcPeRecRoom.add(Restrictions.like("name", peRecruitplan.getName() + peSite.getName(), MatchMode.START));
		List<PeRecRoom> roomList = this.getGeneralDao().getList(dcPeRecRoom);
		
		// 取得这个学习中心报名监考的人员
		DetachedCriteria dcPeExamPatrol = DetachedCriteria.forClass(PeExamPatrol.class);
		DetachedCriteria dcFlagIsJiankao = dcPeExamPatrol
				.createCriteria("enumConstByFlagIsJiankao", "enumConstByFlagIsJiankao");
		dcFlagIsJiankao.add(Restrictions.eq("code", "1"));
		DetachedCriteria dcPeSite = dcPeExamPatrol.createCriteria("peSite", "peSite");
		dcPeSite.add(Restrictions.eq("id", peSite.getId()));
		List<PeExamPatrol> peExamPatrolList = this.getGeneralDao().getList(dcPeExamPatrol);
		if (roomList.size()*2 > peExamPatrolList.size()) {
			return "监考报名人数不足以分配完所有考场";
		}
		
		int n = 0;
		for (int i = 0; i < roomList.size(); i++) {
			roomList.get(i).setInvigilatorA(peExamPatrolList.get(n).getName());
			n ++;
			roomList.get(i).setInvigilatorB(peExamPatrolList.get(n).getName());
			n ++;
			this.getGeneralDao().save(roomList.get(i));
		}
		return "成功分配" + n + "名监考人员";
	}
	
	
	/**
	 *  取出当前活动的招生批次
	 * @return
	 */
	private PeRecruitplan getRecruit(){
		
		DetachedCriteria dcPeRecruitplan = DetachedCriteria.forClass(PeRecruitplan.class);
		dcPeRecruitplan.add(Restrictions.eq("flagActive", "1"));
		List<PeRecruitplan> peRecruitplanList = this.getGeneralDao().getList(dcPeRecruitplan);
		PeRecruitplan	peRecruitplan = peRecruitplanList.get(0);
		return peRecruitplan;
	}
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

}
