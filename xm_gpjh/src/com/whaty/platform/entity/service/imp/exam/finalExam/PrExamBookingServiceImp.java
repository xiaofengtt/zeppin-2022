package com.whaty.platform.entity.service.imp.exam.finalExam;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeExamNo;
import com.whaty.platform.entity.bean.PeExamRoom;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.exam.finalExam.PrExamBookingService;
import com.whaty.platform.entity.util.EntryComparator;

public class PrExamBookingServiceImp implements PrExamBookingService {
	
	private GeneralDao generalDao;
	private MyListDAO myListDao;
	
	public MyListDAO getMyListDao() {
		return myListDao;
	}

	public void setMyListDao(MyListDAO myListDao) {
		this.myListDao = myListDao;
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public int save_BookingData(List ids) throws EntityException {
		Date currentDate = new Date();
		PeSemester activeSemester = null;
		DetachedCriteria dcPeSemester = DetachedCriteria.forClass(PeSemester.class);
		dcPeSemester.add(Restrictions.eq("flagActive", "1"));
		
		List<PeSemester> semesterList = new ArrayList();
		try{
			semesterList = this.getGeneralDao().getList(dcPeSemester);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(semesterList.size() > 0){
			activeSemester = semesterList.get(0);
		}
		EnumConst enumConst = new EnumConst();
		try {
			enumConst = this.getMyListDao().getEnumConstByNamespaceCode("FlagScoreStatus", "0");	//默认为未录入
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try{

			for(int i = 0; i < ids.size(); i++){
				PrExamBooking instance = new PrExamBooking();
				PrTchStuElective elective = new PrTchStuElective();
				elective.setId(ids.get(i).toString());
				instance.setBookingDate(currentDate);
				instance.setEnumConstByFlagScoreStatus(enumConst);
				instance.setPeSemester(activeSemester);
				instance.setPrTchStuElective(elective);
				this.getGeneralDao().setEntityClass(PrExamBooking.class);
				this.generalDao.save(instance);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new EntityException("预约考试失败");
		}
		
		return ids.size();
	}

	public int save_Score(PrExamBooking bean) throws EntityException {
		
		EnumConst enumConst = new EnumConst();
		String code = "";			//用来判断录入成绩时录入的是否为违纪信息
		
		if(bean.getEnumConstByFlagScoreStatus() != null && bean.getEnumConstByFlagScoreStatus().getId() != null){
			this.getGeneralDao().setEntityClass(EnumConst.class);
			try {
				enumConst = (EnumConst)this.getGeneralDao().getById(bean.getEnumConstByFlagScoreStatus().getId());
				code = enumConst.getCode();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(code != null && !"".equals(code) && !("0".equals(code) || "1".equals(code))){
			
			if(!"2".equals(code)){
				PeStudent student = bean.getPrTchStuElective().getPeStudent();
				try {
					student.setDisobeyNote((student.getDisobeyNote() == null ? "" : student.getDisobeyNote()) + "系统记录:考试课程《"+bean.getPrTchStuElective().getPrTchProgramCourse().getPeTchCourse().getName()+"》" + enumConst.getName() + "|");
					this.getGeneralDao().setEntityClass(PeStudent.class);
					this.getGeneralDao().save(student);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}else{
			try {
				enumConst = this.getMyListDao().getEnumConstByNamespaceCode("FlagScoreStatus", "1");	//成绩状态1：正常
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		bean.setEnumConstByFlagScoreStatus(enumConst);
		this.generalDao.setEntityClass(PrExamBooking.class);
		bean = (PrExamBooking)this.generalDao.save(bean);
		
		

		PrTchStuElective instance = bean.getPrTchStuElective();
		instance.setScoreExam(bean.getScoreExam());
		instance.setEnumConstByFlagScoreStatus(enumConst);
		this.generalDao.setEntityClass(PrTchStuElective.class);
		this.generalDao.save(instance);

		
		return 0;
	}
	
	/**
	 * 保存批量上传的学生考试成绩
	 * 
	 * @param file
	 *            所包含的列：学号，姓名，考试课程，考试成绩，考试成绩状态
	 * @return 操作的结果信息
	 * @throws EntityException
	 */

	public int saveUploadScore(File file) throws EntityException{
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
		Set<PrExamBooking> scoreSet = new HashSet();

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
				//有待添加横向权限判断。				
				
				PeStudent peStudent =peStudentList.get(0);
				
				temp = sheet.getCell(1, i).getContents().trim();
				if (temp != null && !"".equals(temp)) {
					if (!peStudent.getTrueName().equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，姓名或者学号不正确！<br/>");
					continue;
					}
				}
				
				temp = sheet.getCell(2, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，考试课程不能为空！<br/>");
					continue;
				}
				
				DetachedCriteria dcPeTchCourse = DetachedCriteria.forClass(PeTchCourse.class);
				dcPeTchCourse.add(Restrictions.eq("name", temp));
				List<PeTchCourse> peTchCourseList = this.getGeneralDao().getList(dcPeTchCourse);
				if (peTchCourseList==null || peTchCourseList.isEmpty()) {
					msg.append("第" + (i + 1) + "行数据，考试课程不存在！<br/>");
					continue;
				}
				
				PeTchCourse peTchCourse = peTchCourseList.get(0);
				
				DetachedCriteria dcPrExamBooking = DetachedCriteria.forClass(PrExamBooking.class);
				dcPrExamBooking.createAlias("peSemester", "peSemester");
				DetachedCriteria dcPrTchStuElective = dcPrExamBooking.createCriteria("prTchStuElective", "prTchStuElective");
				dcPrTchStuElective.add(Restrictions.eq("peStudent", peStudent));
				dcPrTchStuElective.createCriteria("prTchOpencourse", "prTchOpencourse")
						.add(Restrictions.eq("peTchCourse", peTchCourse));
				dcPrExamBooking.add(Restrictions.eq("peSemester.flagActive", "1"));
				List<PrExamBooking> prExamBookingList = this.getGeneralDao().getList(dcPrExamBooking);
				
				if (prExamBookingList==null || prExamBookingList.isEmpty()) {
					msg.append("第" + (i + 1) + "行数据，学生对这门课程没有预约考试！<br/>");
					continue;
				}
				
				PrExamBooking prExamBooking = prExamBookingList.get(0);
				
//				if (prExamBooking.getScoreExam() != null) {
//					msg.append("第" + (i + 1) + "行数据，学生相应考试课程的成绩已经存在！<br/>");
//					continue;
//				}
//				
//				if (prExamBooking.getEnumConstByFlagScoreStatus()!=null&&
//						!prExamBooking.getEnumConstByFlagScoreStatus().getCode().equals("0")) {
//					msg.append("第" + (i + 1) + "行数据，学生相应考试课程的成绩状态不为空且不是未录入！<br/>");
//					continue;
//				}
					
				
				temp = sheet.getCell(3, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，成绩不能为空！<br/>");
					continue;
				}
				Double score;
				try {
					score = Double.parseDouble(temp);
				} catch (RuntimeException e) {
					msg.append("第" + (i + 1) + "行数据，成绩格式有误！<br/>");
					continue;
				}
				if (score < 0) {
					msg.append("第" + (i + 1) + "行数据，成绩不能小于0！<br/>");
					continue;
				}
				
				EnumConst scoreStatus = null;
				temp = sheet.getCell(4, i).getContents().trim();
				String code="";
				if (temp != null && !"".equals(temp)) {
					if (temp.equals("正常")) {
						code="1";
					} else if (temp.equals("缺考")) {
						code="2";
					} else if (temp.equals("违纪")) {
						code="3";
					} else if (temp.equals("作弊")) {
						code="4";
					} else {
						msg.append("第" + (i + 1) + "行数据，考试成绩状态错误！<br/>");
						continue;
					}
					scoreStatus = this.getGeneralDao().getEnumConstByNamespaceCode("FlagScoreStatus", code);
				} else {
					scoreStatus = this.getGeneralDao().getEnumConstByNamespaceCode("FlagScoreStatus", "1");
				}
				
				if (code.equals("3")||code.equals("4")) {
					String note ="";
					if (peStudent.getDisobeyNote()!=null) {
						note = peStudent.getDisobeyNote();
					}
					note += "系统记录:考试课程《"+peTchCourse.getName()+"》" + temp + "|";
					peStudent.setDisobeyNote(note);
					this.getGeneralDao().save(peStudent);
				}
				
				prExamBooking.setScoreExam(score);
				prExamBooking.setEnumConstByFlagScoreStatus(scoreStatus);
				prExamBooking.getPrTchStuElective().setScoreExam(score);
				prExamBooking.getPrTchStuElective().setEnumConstByFlagScoreStatus(scoreStatus);
				if (!scoreSet.add(prExamBooking)) {
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
			msg.append("学生期末考试成绩批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}

		for (PrExamBooking course : scoreSet) {
			try {
				this.getGeneralDao().save(course.getPrTchStuElective());
				this.getGeneralDao().save(course);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EntityException("批量上传学生期末考试成绩失败");
			}
		}
		return count;

	}
	
	/**
	 * 自动分配考场
	 * @return
	 * @throws EntityException
	 */
	public String saveAutoExamRoom() throws Exception{
		String message="";//保存信息
		String examNoMSG=""; //保存考试场次人数信息
		PeSemester peSemester = this.getSemester(); //当前学期
		Set<PeTchCourse> conflictCourse = new HashSet();//保存有冲突的课程
		boolean ifConflict = false;//是否有冲突课程
		List<PeSite> sites = this.getExamSite();
		
		/**
		 * 将本学期的考试预约表中的考试批次，考场，座位号设置为空
		 */
		DetachedCriteria dcExamBooking = DetachedCriteria.forClass(PrExamBooking.class);
		dcExamBooking.add(Restrictions.eq("peSemester", peSemester));

		List<PrExamBooking> prExamBookings = this.getGeneralDao().getList(dcExamBooking);
		if(prExamBookings==null||prExamBookings.isEmpty()){
			throw new EntityException("本学期没有学生预约考试");
		}
		
		dcExamBooking.add(Restrictions.or(Restrictions.isNotNull("seatNo"), Restrictions.isNotNull("peExamRoom")));
		prExamBookings = this.getGeneralDao().getList(dcExamBooking);
		if(prExamBookings!=null&&!prExamBookings.isEmpty()){
			for (PrExamBooking prExamBooking : prExamBookings) {
				prExamBooking.setPeExamNo(null);
				prExamBooking.setPeExamRoom(null);
				prExamBooking.setSeatNo(null);
				this.getGeneralDao().save(prExamBooking);
			}
		}
		/**
		 * 删除本学期的考场
		 */
		DetachedCriteria dcExamRoom = DetachedCriteria.forClass(PeExamRoom.class);
		dcExamRoom.createCriteria("peExamNo", "peExamNo").add(Restrictions.eq("peSemester", peSemester));
		List<PeExamRoom> peExamRooms = this.getGeneralDao().getList(dcExamRoom);
		if(peExamRooms!=null&&peExamRooms.size()>0){
			for (PeExamRoom peExamRoom : peExamRooms) {
				this.generalDao.delete(peExamRoom);
			}
		}
		
		// 首先判断是否有冲突场次
		List list = this.theConflicts();
		
		PeExamNo peExamNo0 = null;//冲突考试批次（第0场）
		if(list!=null&&list.size()>0){
			ifConflict = true;
			message +="共有"+list.size()+"名学生有冲突的课程</br>";
			
			//取得顺序号为0的场次。
			DetachedCriteria dcPeExamNo0 = DetachedCriteria.forClass(PeExamNo.class);
			dcPeExamNo0.add(Restrictions.eq("peSemester", peSemester));
			dcPeExamNo0.add(Restrictions.eq("sequence", 0l));
			List<PeExamNo> peExamNoList = this.getGeneralDao().getList(dcPeExamNo0);
			if(peExamNoList==null||peExamNoList.isEmpty()){
				throw new EntityException("没有设置冲突课程的考试场次，请先设置冲突场次，将这个场次的顺序号设为“0”");
			}
			peExamNo0 = peExamNoList.get(0);
			
			//查询所有的冲突记录，将两门冲突课程中的一个设置到冲突场次中
			for (Object object : list) {

				Object[] o=(Object[])object;
				StringBuffer sql_temp = new StringBuffer();
				sql_temp.append("   select booking.id  																				  ");			
				sql_temp.append("     from pr_exam_booking       booking,                       ");
				sql_temp.append("          pr_tch_stu_elective   elective,                      ");
				sql_temp.append("          pe_student            student,                       ");
				sql_temp.append("          pe_tch_course         course,                        ");
				sql_temp.append("          pr_tch_opencourse     opencourse,                    ");
				sql_temp.append("          pr_tch_program_course programcourse,                 ");
				sql_temp.append("          pe_semester           semester_for_course,           ");
				sql_temp.append("          pe_semester           semester_for_booking           ");
				sql_temp.append("    where booking.fk_semester_id = semester_for_booking.id     ");
				sql_temp.append("      and semester_for_booking.flag_active = '1'               ");
				sql_temp.append("      and booking.fk_tch_stu_elective_id = elective.id         ");
				sql_temp.append("      and elective.fk_tch_program_course = programcourse.id    ");
				sql_temp.append("      and programcourse.fk_course_id = course.id               ");
				sql_temp.append("      and opencourse.fk_course_id = course.id                  ");
				sql_temp.append("      and opencourse.fk_semester_id = semester_for_course.id   ");
				sql_temp.append("      and semester_for_course.flag_active = '1'                ");
				sql_temp.append("      and elective.fk_stu_id = student.id                      ");
				sql_temp.append("      and student.id = '"+o[0]+"'      ");
				sql_temp.append("      and opencourse.advice_exam_no ="+o[1]);	
				List stuBooking = this.getMyListDao().getBySQL(sql_temp.toString());
				if(stuBooking!=null&&stuBooking.size()>0){
					//这个学生其中的一个冲突的预约记录，设置考试场次为冲突场
					
					PrExamBooking prExamBooking1 = (PrExamBooking)this.getMyListDao().getById(PrExamBooking.class, stuBooking.get(0).toString());
					PrExamBooking prExamBooking2 = (PrExamBooking)this.getMyListDao().getById(PrExamBooking.class, stuBooking.get(1).toString());
					PeTchCourse course1=prExamBooking1.getPrTchStuElective().getPrTchOpencourse().getPeTchCourse();
					PeTchCourse course2=prExamBooking2.getPrTchStuElective().getPrTchOpencourse().getPeTchCourse();
					
					//如果课程1为冲突课程，将这个记录的考试场次设置为冲突场
					if(conflictCourse!=null&&conflictCourse.contains(course1)){
						prExamBooking1.setPeExamNo(peExamNo0);
						this.getGeneralDao().save(prExamBooking1);
						continue;
					}
					
					//如果课程2为冲突课程，将这个记录的考试场次设置为冲突场
					if(conflictCourse!=null&&conflictCourse.contains(course2)){
						prExamBooking2.setPeExamNo(peExamNo0);
						this.getGeneralDao().save(prExamBooking2);
						continue;
					}
					
					/**
					 * 取出这两门课程的预约记录,比较预约的数量,数量少的课程为冲突课程
					 */
					List list1 = this.courseConflict(course1.getId());
					List list2 = this.courseConflict(course2.getId());
					if(list1.size()<=list2.size()){
						prExamBooking1.setPeExamNo(peExamNo0);
						this.getGeneralDao().save(prExamBooking1);
						
						//冲突的课程
						conflictCourse.add(course1);
					} else {
						prExamBooking2.setPeExamNo(peExamNo0);
						this.getGeneralDao().save(prExamBooking2);
						//冲突的课程
						conflictCourse.add(course2);
					}
//					System.out.println(prExamBooking.getPrTchStuElective().getPrTchOpencourse().getPeTchCourse().getName());
					
				}
//				System.out.println(o[0].toString());
			}
			message +="共有"+ conflictCourse.size()+"门课程分配到了"+peExamNo0.getName()+"</br>";
		}
		
		//取得本学期的所有考试场次
		DetachedCriteria dcPeExamNo = DetachedCriteria.forClass(PeExamNo.class);
		dcPeExamNo.add(Restrictions.eq("peSemester", peSemester));
		dcPeExamNo.addOrder(Order.asc("sequence"));
		List<PeExamNo> peExamNoList = this.getGeneralDao().getList(dcPeExamNo);
		if(peExamNoList==null||peExamNoList.isEmpty()){
			throw new EntityException("没有设置任何考试场次，请先设置考试场次然后再进行分配考场");
		}
		
		//按考试场次循环
		for (PeExamNo peExamNo : peExamNoList) {
			int count = 0; //记录考试场次分配的学生人数
			//对于冲突场次排考场
			if(peExamNo.getSequence()==0){
				//判断是否有冲突记录
				if(!ifConflict){
					continue;
				}
//				System.out.println("00000");
				

					//遍历考试学习中心
					for(PeSite peSite : sites){
						
						HashMap<PeExamRoom,Integer> map = new HashMap<PeExamRoom,Integer>(); //用于保存尾考场，考场人数
						int room = 0 ; //考场号
						//遍历冲突的课程
						for (PeTchCourse peTchCourse : conflictCourse) {						

						
						/**
						 * 取得考试预约记录
						 * 条件：学习中心考试学习中心，课程是开课表课程，当前学期，考试场次为空或者不是冲突场次。
						 */
						DetachedCriteria dcPrExamBooking = DetachedCriteria.forClass(PrExamBooking.class);
						DetachedCriteria dcPrTchStuElective = dcPrExamBooking.createCriteria("prTchStuElective", "prTchStuElective");
						DetachedCriteria dcPeStudent = dcPrTchStuElective.createCriteria("peStudent", "peStudent");
						dcPeStudent.createCriteria("peSite", "peSite")
							.add(Restrictions.or(Restrictions.eq("peSite.id", peSite.getId()),Restrictions.eq("peSite.peSite.id", peSite.getId())));
						
						dcPrTchStuElective.createCriteria("prTchOpencourse", "prTchOpencourse")
							.add(Restrictions.eq("peTchCourse", peTchCourse));
						dcPrExamBooking.add(Restrictions.eq("peSemester", peSemester));
						
						dcPrExamBooking.add(Restrictions.eq("peExamNo", peExamNo0));
					
						dcPeStudent.addOrder(Order.asc("peEdutype"));
						dcPeStudent.addOrder(Order.asc("peMajor"));

						List<PrExamBooking> prExamBookingList = this.getGeneralDao().getList(dcPrExamBooking);
						if(prExamBookingList==null||prExamBookingList.isEmpty()){
							continue;
						}
						room++;
						int seat = 0;//座位号
//						message +="保存考场信息"+this.toThree(room)+" </br>";
						PeExamRoom peExamRoom = new PeExamRoom();
						peExamRoom.setName(peExamNo.getName()+peSite.getName()+this.toThree(room));
						peExamRoom.setPeExamNo(peExamNo);
						peExamRoom.setCode(this.toThree(room));
						peExamRoom.setPeSite(peSite);
						peExamRoom = (PeExamRoom)this.getGeneralDao().save(peExamRoom);
						
						//遍历取得的选课记录
						for (PrExamBooking prExamBooking : prExamBookingList) {
							count++;//考试场次人数加1
							if(seat==30){
								room ++;
								seat=1;
//								message +="保存考场信息"+this.toThree(room)+" </br> ";
								PeExamRoom examRoom = new PeExamRoom();
								examRoom.setName(peExamNo.getName()+peSite.getName()+this.toThree(room));
								examRoom.setPeExamNo(peExamNo);
								examRoom.setCode(this.toThree(room));
								examRoom.setPeSite(peSite);
								peExamRoom = (PeExamRoom)this.getGeneralDao().save(examRoom);
								
//								message +="保存学生信息："+prExamBooking.getPrTchStuElective().getPeStudent().getTrueName()+" 座位号"+seat+""+ "考场"+this.toThree(room)+" </br> ";
								prExamBooking.setPeExamNo(peExamNo);
								prExamBooking.setPeExamRoom(peExamRoom);
								prExamBooking.setSeatNo(this.toTwo(seat));
								this.getGeneralDao().save(prExamBooking);
								continue;
							}
							seat++;
//							message +="保存学生信息："+prExamBooking.getPrTchStuElective().getPeStudent().getTrueName()+" 座位号"+this.toTwo(seat)+ "考场"+this.toThree(room)+" </br> ";
							prExamBooking.setPeExamNo(peExamNo);
							prExamBooking.setPeExamRoom(peExamRoom);
							prExamBooking.setSeatNo(this.toTwo(seat));
							this.getGeneralDao().save(prExamBooking);
						}
						//判断是否属于尾考场,尾考场则添加到map中
						if(seat<30){
							map.put(peExamRoom, seat);
						}
					}
						if(map!=null&&map.size()>1){
							//如果尾考场大于1个则调用尾考场合并方法
							this.saveSmallExamRoom(map, room);
						}
				}
			examNoMSG +=peExamNo.getName()+"共分配了学生"+count+"人</br>";
			} else { //对于正常的场次排考场
				
				//从开课表中取得本需求这个场次的课程
				DetachedCriteria dcPrTchOpencourse = DetachedCriteria.forClass(PrTchOpencourse.class);
				dcPrTchOpencourse.add(Restrictions.eq("peSemester",peSemester));
				dcPrTchOpencourse.add(Restrictions.eq("adviceExamNo", peExamNo.getSequence().toString()));
				List<PrTchOpencourse>  prTchOpencourseList = this.getGeneralDao().getList(dcPrTchOpencourse);
				if(prTchOpencourseList==null||prTchOpencourseList.isEmpty()){
					continue;
				}
				

					//遍历考试学习中心
					for(PeSite peSite : sites){
						
						HashMap<PeExamRoom,Integer> map = new HashMap<PeExamRoom,Integer>(); //用于保存尾考场，考场人数
						int room = 0 ; //考场号
						//遍历取得的开课记录
						for (PrTchOpencourse prTchOpencourse : prTchOpencourseList) {						

//						System.out.println(prTchOpencourse.getPeTchCourse().getName()+peSite.getName());
						
						/**
						 * 取得考试预约记录
						 * 条件：学习中心考试学习中心，课程是开课表课程，当前学期，考试场次为空或者不是冲突场次。
						 */
						DetachedCriteria dcPrExamBooking = DetachedCriteria.forClass(PrExamBooking.class);
						DetachedCriteria dcPrTchStuElective = dcPrExamBooking.createCriteria("prTchStuElective", "prTchStuElective");
						DetachedCriteria dcPeStudent = dcPrTchStuElective.createCriteria("peStudent", "peStudent");
						dcPeStudent.createCriteria("peSite", "peSite")
							.add(Restrictions.or(Restrictions.eq("peSite.id", peSite.getId()),Restrictions.eq("peSite.peSite.id", peSite.getId())));
						
						dcPrTchStuElective.createCriteria("prTchOpencourse", "prTchOpencourse")
							.add(Restrictions.eq("peTchCourse", prTchOpencourse.getPeTchCourse()));
						dcPrExamBooking.add(Restrictions.eq("peSemester", peSemester));
						if(ifConflict){
							dcPrExamBooking.add(Restrictions.or(Restrictions.isNull("peExamNo"), Restrictions.not(Restrictions.eq("peExamNo.id", peExamNo0.getId()))));
						}
						dcPeStudent.addOrder(Order.asc("peEdutype"));
						dcPeStudent.addOrder(Order.asc("peMajor"));

						List<PrExamBooking> prExamBookingList = this.getGeneralDao().getList(dcPrExamBooking);
						if(prExamBookingList==null||prExamBookingList.isEmpty()){
							continue;
						}
						room ++;
						int seat = 0;//座位号
//						message +="保存考场信息"+this.toThree(room)+" </br>";
						PeExamRoom peExamRoom = new PeExamRoom();
						peExamRoom.setName(peExamNo.getName()+peSite.getName()+this.toThree(room));
						peExamRoom.setPeExamNo(peExamNo);
						peExamRoom.setCode(this.toThree(room));
						peExamRoom.setPeSite(peSite);
						peExamRoom = (PeExamRoom)this.getGeneralDao().save(peExamRoom);
						
						//遍历取得的选课记录
						for (PrExamBooking prExamBooking : prExamBookingList) {
							count++;//考试场次人数加1
							if(seat==30){
								room ++;
								seat=1;
//								message +="保存考场信息"+this.toThree(room)+" </br> ";
								PeExamRoom examRoom = new PeExamRoom();
								examRoom.setName(peExamNo.getName()+peSite.getName()+this.toThree(room));
								examRoom.setPeExamNo(peExamNo);
								examRoom.setCode(this.toThree(room));
								examRoom.setPeSite(peSite);
								peExamRoom = (PeExamRoom)this.getGeneralDao().save(examRoom);
								
//								message +="保存学生信息："+prExamBooking.getPrTchStuElective().getPeStudent().getTrueName()+" 座位号"+seat+ "考场"+this.toThree(room)+" </br> ";
								prExamBooking.setPeExamNo(peExamNo);
								prExamBooking.setPeExamRoom(peExamRoom);
								prExamBooking.setSeatNo(this.toTwo(seat));
								this.getGeneralDao().save(prExamBooking);
								continue;
							}
							seat++;
//							message +="保存学生信息："+prExamBooking.getPrTchStuElective().getPeStudent().getTrueName()+" 座位号"+this.toTwo(seat)+ "考场"+this.toThree(room)+" </br> ";
							prExamBooking.setPeExamNo(peExamNo);
							prExamBooking.setPeExamRoom(peExamRoom);
							prExamBooking.setSeatNo(this.toTwo(seat));
							this.getGeneralDao().save(prExamBooking);
						}
						//判断是否属于尾考场,尾考场则添加到map中
						if(seat<30){
							map.put(peExamRoom, seat);
						}
					}
						if(map!=null&&map.size()>1){
							//如果尾考场大于1个则调用尾考场合并方法
							this.saveSmallExamRoom(map, room);
						}
				}
				examNoMSG +=peExamNo.getName()+"共分配了学生"+count+"人</br>";
			}
		}

		return message+examNoMSG + "自动分配考场完毕";
	}
	
	/**
	 * 以学习中心为单位合并尾考场。采用贪婪法。
	 */
	private String saveSmallExamRoom(HashMap<PeExamRoom,Integer> map,int roomNo ){
			

			List<Integer> list = new ArrayList<Integer>();//用来保存新占用的考场人数
			List<PeExamRoom> roomList = new ArrayList<PeExamRoom>();//用来保存新占用的考场
			
			//将HashMap按照value的值降序排列
		    List<Map.Entry<PeExamRoom, Integer>> entryList = new ArrayList<Map.Entry<PeExamRoom, Integer>>(map.entrySet());
		    Collections.sort(entryList, new EntryComparator());
		    
		    Iterator<Map.Entry<PeExamRoom, Integer>> i = entryList.iterator();
		    while (i.hasNext()) {
		      Map.Entry<PeExamRoom, Integer> entry = i.next();
//		      System.out.println("key=" + entry.getKey() + ", value=" + entry.getValue());
		      PeExamRoom room = entry.getKey(); //考场
		      int n = entry.getValue();//人数
		      
		      int m=0;//记录新合并出来的考场数
				for(m=0;m<list.size();m++) { 
					if(list.get(m)+n<=30) break;
				}
				if(m==list.size()){
					list.add(n);//添加新的考场人数
					PeExamRoom newRoom = new PeExamRoom(); //创建新考场
					int number = roomNo+m+1;
					newRoom.setCode(this.toThree(number));
					newRoom.setPeExamNo(room.getPeExamNo());
					newRoom.setName(room.getName().substring(0, room.getName().length()-3)+this.toThree(number));
					newRoom.setPeSite(room.getPeSite());
					newRoom = (PeExamRoom)this.getGeneralDao().save(newRoom);
					
					roomList.add(newRoom);//添加新考场
					
					DetachedCriteria dcPrExamBooking = DetachedCriteria.forClass(PrExamBooking.class);
					dcPrExamBooking.add(Restrictions.eq("peExamRoom", room));
					dcPrExamBooking.addOrder(Order.asc("seatNo"));
					List<PrExamBooking> prExamBookingList = this.getGeneralDao().getList(dcPrExamBooking);
					//设置考场为newRoom
					for (PrExamBooking prExamBooking : prExamBookingList) {
						prExamBooking.setPeExamRoom(newRoom);
						this.getGeneralDao().save(prExamBooking);
					}

					//删除考场
					this.getGeneralDao().delete(room);
				} else {
					int num = list.get(m);
					list.set(m,list.get(m)+n);//更新考场人数
					DetachedCriteria dcPrExamBooking = DetachedCriteria.forClass(PrExamBooking.class);
					dcPrExamBooking.add(Restrictions.eq("peExamRoom", room));
					dcPrExamBooking.addOrder(Order.asc("seatNo"));
					List<PrExamBooking> prExamBookingList = this.getGeneralDao().getList(dcPrExamBooking);
					//设置考场为roomList.get(m),设置新的座位号
					for (PrExamBooking prExamBooking : prExamBookingList) {
						num++;
						prExamBooking.setPeExamRoom(roomList.get(m));
						prExamBooking.setSeatNo(this.toTwo(num));
						this.getGeneralDao().save(prExamBooking);
					}
					//删除考场
					this.getGeneralDao().delete(room);
				}
				
		    }

		return "";
	}

	/**
	 * 某门课程的预约考试记录
	 * @param courseId
	 * @return
	 */
	private List courseConflict(String courseId){
		StringBuffer sql_temp = new StringBuffer();
		sql_temp.append("    select student.name																								");				
		sql_temp.append("      from pr_exam_booking       booking,                              ");
		sql_temp.append("           pr_tch_stu_elective   elective,                             ");
		sql_temp.append("           pe_student            student,                              ");
		sql_temp.append("           enum_const            enum_stu_status,                      ");
		sql_temp.append("           pr_tch_program_course programcourse,                        ");
		sql_temp.append("           pe_tch_course         course,                               ");
		sql_temp.append("           pe_semester           semester_for_booking                  ");
		sql_temp.append("     where booking.fk_semester_id = semester_for_booking.id            ");
		sql_temp.append("       and semester_for_booking.flag_active = '1'                      ");
		sql_temp.append("       and booking.fk_tch_stu_elective_id = elective.id                ");
		sql_temp.append("       and elective.fk_tch_program_course = programcourse.id           ");
		sql_temp.append("       and programcourse.fk_course_id = course.id                      ");
		sql_temp.append("       and elective.fk_stu_id = student.id                             ");
		sql_temp.append("       and student.flag_student_status = enum_stu_status.id            ");
		sql_temp.append("       and enum_stu_status.code = '4'                                  ");
		sql_temp.append("     and course.id='"+courseId+"'									     ");
		List list = this.getGeneralDao().getBySQL(sql_temp.toString());
		return list;
	}
	
	/**
	 * 将考场号设置为ddd的格式
	 * @param num 考场号
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
	/**
	 * 取得预约记录中考试场次有冲突的记录(peStudent.id , adviceExamNo)
	 * @return
	 */
	private List theConflicts(){
		StringBuffer sql_temp = new StringBuffer();
		sql_temp.append("   select two.student_name as student_name, two.examno as examno														");	
		sql_temp.append("     from (select one.student_name as student_name,                                        ");
		sql_temp.append("                  one.examno as examno,                                                    ");
		sql_temp.append("                  count(one.id) as nums                                                    ");
		sql_temp.append("             from (select elective.id               as id,                                 ");
		sql_temp.append("                          student.id                as student_name,                       ");
		sql_temp.append("                          opencourse.advice_exam_no as examno                              ");
		sql_temp.append("                     from pr_exam_booking booking,                                         ");
		sql_temp.append("                          pr_tch_stu_elective elective,                                    ");
		sql_temp.append("                          pe_student student,                                              ");
		sql_temp.append("                          enum_const enum_stu_status,                                      ");
		sql_temp.append("                          pr_tch_program_course programcourse,                             ");
		sql_temp.append("                          pe_tch_course course,                                            ");
		sql_temp.append("                          pr_tch_opencourse opencourse,                                    ");
		sql_temp.append("                          pe_semester semester_for_course,                                 ");
		sql_temp.append("                          pe_semester semester_for_booking,                                ");
		sql_temp.append("                          (select elective.fk_stu_id as student_id                         ");
		sql_temp.append("                             from pr_tch_stu_elective elective                             ");
		sql_temp.append("                                                                                           ");
		sql_temp.append("                           ) stuid                                                         ");
		sql_temp.append("                    where booking.fk_semester_id = semester_for_booking.id                 ");
		sql_temp.append("                      and semester_for_booking.flag_active = '1'                           ");
		sql_temp.append("                      and booking.fk_tch_stu_elective_id = elective.id                     ");
		sql_temp.append("                      and elective.fk_tch_program_course = programcourse.id                ");
		sql_temp.append("                      and programcourse.fk_course_id = course.id                           ");
		sql_temp.append("                      and opencourse.fk_course_id = course.id                              ");
		sql_temp.append("                      and opencourse.fk_semester_id = semester_for_course.id               ");
		sql_temp.append("                      and semester_for_course.flag_active = '1'                            ");
		sql_temp.append("                      and elective.fk_stu_id = student.id                                  ");
		sql_temp.append("                      and student.id = stuid.student_id                                    ");
		sql_temp.append("                      and student.flag_student_status = enum_stu_status.id                 ");
		sql_temp.append("                      and enum_stu_status.code = '4'                                       ");
		sql_temp.append("                   union                                                                   ");
		sql_temp.append("                   select elective.id               as id,                                 ");
		sql_temp.append("                          student.id                as student_name,                       ");
		sql_temp.append("                          opencourse.advice_exam_no as examno                              ");
		sql_temp.append("                     from pr_tch_stu_elective   elective,                                  ");
		sql_temp.append("                          pr_exam_booking       booking,                                   ");
		sql_temp.append("                          pe_student            student,                                   ");
		sql_temp.append("                          pr_tch_program_course programcourse,                             ");
		sql_temp.append("                          pe_tch_course         course,                                    ");
		sql_temp.append("                          pr_tch_opencourse     opencourse,                                ");
		sql_temp.append("                          enum_const            enum_stu_status,                           ");
		sql_temp.append("                          pe_semester           semester_for_booking,                      ");
		sql_temp.append("                          pe_semester           semester                                   ");
		sql_temp.append("                    where elective.fk_tch_program_course = programcourse.id                ");
		sql_temp.append("                      and elective.fk_stu_id = student.id                                  ");
		sql_temp.append("                      and booking.fk_tch_stu_elective_id = elective.id                     ");
		sql_temp.append("                      and booking.fk_semester_id = semester_for_booking.id                 ");
		sql_temp.append("                      and semester_for_booking.flag_active = '1'                           ");
		sql_temp.append("                      and student.flag_student_status = enum_stu_status.id                 ");
		sql_temp.append("                      and enum_stu_status.code = '4'                                       ");
		sql_temp.append("                      and programcourse.fk_course_id = course.id                           ");
		sql_temp.append("                      and opencourse.fk_course_id = course.id                              ");
		sql_temp.append("                      and opencourse.fk_semester_id = semester.id                          ");
		sql_temp.append("                      and semester.flag_active = '1'                                       ");
		sql_temp.append("                                                                                           ");
		sql_temp.append("                   ) one                                                                   ");
		sql_temp.append("            group by one.student_name, one.examno                                          ");
		sql_temp.append("           having count(one.id) > 1) two                                                   ");
		sql_temp.append("    where two.nums > 1								 										");	
		List list = this.getMyListDao().getBySQL(sql_temp.toString());
		return list;
	}
	
	/**
	 *  取出当前活动的学期
	 * @return
	 */
	private PeSemester getSemester(){
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeSemester.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		List<PeSemester> peSemesterList = this.getGeneralDao().getList(dc);
		if(peSemesterList!=null&&peSemesterList.size()>0){
			PeSemester	peSemester = peSemesterList.get(0);
			return peSemester;
			}else {
				return null;
			}
			
	}
	
	/**
	 * 查出所有考试中心
	 * @return
	 */
	private List<PeSite> getExamSite(){
		DetachedCriteria dcSite = DetachedCriteria.forClass(PeSite.class);

		List<PeSite> siteList = this.getGeneralDao().getList(dcSite);
		Set<PeSite> peSiteSet = new HashSet();
		List<PeSite> site = new ArrayList<PeSite>();
		if (siteList.size()>0) {
			for (PeSite peSite : siteList) {
				if (peSite.getPeSite()!=null) {
					if (peSiteSet.add(peSite.getPeSite())) {
						site.add(peSite.getPeSite());
					}
				} else {
					if (peSiteSet.add(peSite)) {
						site.add(peSite);
					}
				}
			}
		}
		return site;
	}
	
	/**
	 * 重新生成考场编号
	 * 
	 */
	public void saveModifyRoomNo(){
		PeSemester peSemester = this.getSemester();
		//取得本学期的所有考试场次
		DetachedCriteria dcPeExamNo = DetachedCriteria.forClass(PeExamNo.class);
		dcPeExamNo.add(Restrictions.eq("peSemester", peSemester));
		dcPeExamNo.addOrder(Order.asc("sequence"));
		List<PeExamNo> peExamNoList = this.getGeneralDao().getList(dcPeExamNo);
		if(peExamNoList==null||peExamNoList.isEmpty()){
			return;
		}
		List<PeSite> siteList = this.getExamSite();
		for (PeExamNo peExamNo : peExamNoList) {
			for (PeSite peSite : siteList) {
				//根据考试场次和学习中心取出考场
				DetachedCriteria dc = DetachedCriteria.forClass(PeExamRoom.class);
				dc.add(Restrictions.eq("peExamNo", peExamNo));
				dc.add(Restrictions.eq("peSite", peSite));
				dc.addOrder(Order.asc("code"));
				List<PeExamRoom> roomList = null;
				roomList = this.getGeneralDao().getList(dc);
				if(roomList!=null&&roomList.size()>0){
					int size = roomList.size();
					String lastNo = roomList.get(size-1).getCode();
					if(Integer.parseInt(lastNo)<=size){
						continue;
					}
					for (int i = 0; i < roomList.size(); i++) {
						roomList.get(i).setName(roomList.get(i).getName().substring(0, roomList.get(i).getName().length()-3)+this.toThree(i+1));
						roomList.get(i).setCode(this.toThree(i+1));
						this.getGeneralDao().save(roomList.get(i));
					}
				}
			}
		}		
		
	}

	public String saveScore(boolean isa, String[] ids, String[] scores,
			String[] scorestatus) throws EntityException {
		StringBuffer info = new StringBuffer();
		try{
			for(int i= 0;i<scores.length;i++){
				if(scores[i]!=null&&scores[i].trim().length()>0){
					PrExamBooking prExamBooking = (PrExamBooking)this.getMyListDao().getById(PrExamBooking.class, ids[i].trim());
					if(isa){
						Double scoreb = prExamBooking.getScoreExamB();
						EnumConst scorestatusb = prExamBooking.getEnumConstByFlagScoreStatusb();
						Double scorea = Double.parseDouble(scores[i].trim());
						EnumConst scorestatusa = (EnumConst)this.getMyListDao().getById(EnumConst.class, scorestatus[i].trim());
						if(scoreb!=null){
							if(!scoreb.equals(scorea)||!scorestatusb.equals(scorestatusa)){
								info.append("座位号为"+prExamBooking.getSeatNo()+"的学生成绩和登分人员B录入的不一致<br/>"); 
							}
							prExamBooking.setScoreExam(scorea);
							prExamBooking.setEnumConstByFlagScoreStatus(scorestatusa);
						}
						prExamBooking.setScoreExamA(scorea);
						prExamBooking.setEnumConstByFlagScoreStatusa(scorestatusa);
					}else{
						Double scorea =  prExamBooking.getScoreExamA();
						EnumConst scorestatusa = prExamBooking.getEnumConstByFlagScoreStatusa();
						Double scoreb = Double.parseDouble(scores[i].trim());
						EnumConst scorestatusb =  (EnumConst)this.getMyListDao().getById(EnumConst.class, scorestatus[i].trim());
						if(scorea!=null){
							if(!scoreb.equals(scorea)||!scorestatusb.equals(scorestatusa)){
								info.append("座位号为"+prExamBooking.getSeatNo()+"的学生成绩和登分人员A录入的不一致<br/>"); 
							}
							prExamBooking.setScoreExam(scoreb);
							prExamBooking.setEnumConstByFlagScoreStatus(scorestatusb);
						}
						prExamBooking.setScoreExamB(scoreb);
						prExamBooking.setEnumConstByFlagScoreStatusb(scorestatusb);
					}
					this.getGeneralDao().save(prExamBooking);
				}
			}
		}catch(Throwable e){
			throw new EntityException("登分保存失败！");
		}
		return info.toString();
	}	
}
