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
			enumConst = this.getMyListDao().getEnumConstByNamespaceCode("FlagScoreStatus", "0");	//??????????????????
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
			throw new EntityException("??????????????????");
		}
		
		return ids.size();
	}

	public int save_Score(PrExamBooking bean) throws EntityException {
		
		EnumConst enumConst = new EnumConst();
		String code = "";			//?????????????????????????????????????????????????????????
		
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
					student.setDisobeyNote((student.getDisobeyNote() == null ? "" : student.getDisobeyNote()) + "????????????:???????????????"+bean.getPrTchStuElective().getPrTchProgramCourse().getPeTchCourse().getName()+"???" + enumConst.getName() + "|");
					this.getGeneralDao().setEntityClass(PeStudent.class);
					this.getGeneralDao().save(student);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}else{
			try {
				enumConst = this.getMyListDao().getEnumConstByNamespaceCode("FlagScoreStatus", "1");	//????????????1?????????
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
	 * ???????????????????????????????????????
	 * 
	 * @param file
	 *            ????????????????????????????????????????????????????????????????????????????????????
	 * @return ?????????????????????
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
			msg.append("Excel??????????????????????????????????????????<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("???????????????<br/>");
		}
		String temp = "";
		Set<PrExamBooking> scoreSet = new HashSet();

		for (int i = 1; i < rows; i++) {
			try {
				temp = sheet.getCell(0, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("???" + (i + 1) + "???????????????????????????<br/>");
					continue;
				}
				
				DetachedCriteria dcPeStudent = DetachedCriteria.forClass(PeStudent.class);
				dcPeStudent.add(Restrictions.eq("regNo", temp));
				
				List<PeStudent> peStudentList = this.getGeneralDao().getList(dcPeStudent);
				
				if (peStudentList==null || peStudentList.isEmpty()) {
					msg.append("???" + (i + 1) + "??????????????????????????????<br/>");
					continue;
				}
				//?????????????????????????????????				
				
				PeStudent peStudent =peStudentList.get(0);
				
				temp = sheet.getCell(1, i).getContents().trim();
				if (temp != null && !"".equals(temp)) {
					if (!peStudent.getTrueName().equals(temp)) {
					msg.append("???" + (i + 1) + "??????????????????????????????????????????<br/>");
					continue;
					}
				}
				
				temp = sheet.getCell(2, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("???" + (i + 1) + "???????????????????????????????????????<br/>");
					continue;
				}
				
				DetachedCriteria dcPeTchCourse = DetachedCriteria.forClass(PeTchCourse.class);
				dcPeTchCourse.add(Restrictions.eq("name", temp));
				List<PeTchCourse> peTchCourseList = this.getGeneralDao().getList(dcPeTchCourse);
				if (peTchCourseList==null || peTchCourseList.isEmpty()) {
					msg.append("???" + (i + 1) + "????????????????????????????????????<br/>");
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
					msg.append("???" + (i + 1) + "??????????????????????????????????????????????????????<br/>");
					continue;
				}
				
				PrExamBooking prExamBooking = prExamBookingList.get(0);
				
//				if (prExamBooking.getScoreExam() != null) {
//					msg.append("???" + (i + 1) + "????????????????????????????????????????????????????????????<br/>");
//					continue;
//				}
//				
//				if (prExamBooking.getEnumConstByFlagScoreStatus()!=null&&
//						!prExamBooking.getEnumConstByFlagScoreStatus().getCode().equals("0")) {
//					msg.append("???" + (i + 1) + "?????????????????????????????????????????????????????????????????????????????????<br/>");
//					continue;
//				}
					
				
				temp = sheet.getCell(3, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("???" + (i + 1) + "?????????????????????????????????<br/>");
					continue;
				}
				Double score;
				try {
					score = Double.parseDouble(temp);
				} catch (RuntimeException e) {
					msg.append("???" + (i + 1) + "?????????????????????????????????<br/>");
					continue;
				}
				if (score < 0) {
					msg.append("???" + (i + 1) + "??????????????????????????????0???<br/>");
					continue;
				}
				
				EnumConst scoreStatus = null;
				temp = sheet.getCell(4, i).getContents().trim();
				String code="";
				if (temp != null && !"".equals(temp)) {
					if (temp.equals("??????")) {
						code="1";
					} else if (temp.equals("??????")) {
						code="2";
					} else if (temp.equals("??????")) {
						code="3";
					} else if (temp.equals("??????")) {
						code="4";
					} else {
						msg.append("???" + (i + 1) + "???????????????????????????????????????<br/>");
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
					note += "????????????:???????????????"+peTchCourse.getName()+"???" + temp + "|";
					peStudent.setDisobeyNote(note);
					this.getGeneralDao().save(peStudent);
				}
				
				prExamBooking.setScoreExam(score);
				prExamBooking.setEnumConstByFlagScoreStatus(scoreStatus);
				prExamBooking.getPrTchStuElective().setScoreExam(score);
				prExamBooking.getPrTchStuElective().setEnumConstByFlagScoreStatus(scoreStatus);
				if (!scoreSet.add(prExamBooking)) {
					msg.append("???" + (i + 1) + "?????????????????????????????????????????????<br/>");
					continue;
				}
				count++;
			} catch (Exception e) {
				e.printStackTrace();
				msg.append("???" + (i + 1) + "????????????????????????<br/>");
				continue;
			}
		}

		if (msg.length() > 0) {
			msg.append("???????????????????????????????????????????????????????????????????????????????????????<br/>");
			throw new EntityException(msg.toString());
		}

		for (PrExamBooking course : scoreSet) {
			try {
				this.getGeneralDao().save(course.getPrTchStuElective());
				this.getGeneralDao().save(course);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EntityException("??????????????????????????????????????????");
			}
		}
		return count;

	}
	
	/**
	 * ??????????????????
	 * @return
	 * @throws EntityException
	 */
	public String saveAutoExamRoom() throws Exception{
		String message="";//????????????
		String examNoMSG=""; //??????????????????????????????
		PeSemester peSemester = this.getSemester(); //????????????
		Set<PeTchCourse> conflictCourse = new HashSet();//????????????????????????
		boolean ifConflict = false;//?????????????????????
		List<PeSite> sites = this.getExamSite();
		
		/**
		 * ?????????????????????????????????????????????????????????????????????????????????
		 */
		DetachedCriteria dcExamBooking = DetachedCriteria.forClass(PrExamBooking.class);
		dcExamBooking.add(Restrictions.eq("peSemester", peSemester));

		List<PrExamBooking> prExamBookings = this.getGeneralDao().getList(dcExamBooking);
		if(prExamBookings==null||prExamBookings.isEmpty()){
			throw new EntityException("?????????????????????????????????");
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
		 * ????????????????????????
		 */
		DetachedCriteria dcExamRoom = DetachedCriteria.forClass(PeExamRoom.class);
		dcExamRoom.createCriteria("peExamNo", "peExamNo").add(Restrictions.eq("peSemester", peSemester));
		List<PeExamRoom> peExamRooms = this.getGeneralDao().getList(dcExamRoom);
		if(peExamRooms!=null&&peExamRooms.size()>0){
			for (PeExamRoom peExamRoom : peExamRooms) {
				this.generalDao.delete(peExamRoom);
			}
		}
		
		// ?????????????????????????????????
		List list = this.theConflicts();
		
		PeExamNo peExamNo0 = null;//????????????????????????0??????
		if(list!=null&&list.size()>0){
			ifConflict = true;
			message +="??????"+list.size()+"???????????????????????????</br>";
			
			//??????????????????0????????????
			DetachedCriteria dcPeExamNo0 = DetachedCriteria.forClass(PeExamNo.class);
			dcPeExamNo0.add(Restrictions.eq("peSemester", peSemester));
			dcPeExamNo0.add(Restrictions.eq("sequence", 0l));
			List<PeExamNo> peExamNoList = this.getGeneralDao().getList(dcPeExamNo0);
			if(peExamNoList==null||peExamNoList.isEmpty()){
				throw new EntityException("?????????????????????????????????????????????????????????????????????????????????????????????????????????0???");
			}
			peExamNo0 = peExamNoList.get(0);
			
			//???????????????????????????????????????????????????????????????????????????????????????
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
					//?????????????????????????????????????????????????????????????????????????????????
					
					PrExamBooking prExamBooking1 = (PrExamBooking)this.getMyListDao().getById(PrExamBooking.class, stuBooking.get(0).toString());
					PrExamBooking prExamBooking2 = (PrExamBooking)this.getMyListDao().getById(PrExamBooking.class, stuBooking.get(1).toString());
					PeTchCourse course1=prExamBooking1.getPrTchStuElective().getPrTchOpencourse().getPeTchCourse();
					PeTchCourse course2=prExamBooking2.getPrTchStuElective().getPrTchOpencourse().getPeTchCourse();
					
					//????????????1??????????????????????????????????????????????????????????????????
					if(conflictCourse!=null&&conflictCourse.contains(course1)){
						prExamBooking1.setPeExamNo(peExamNo0);
						this.getGeneralDao().save(prExamBooking1);
						continue;
					}
					
					//????????????2??????????????????????????????????????????????????????????????????
					if(conflictCourse!=null&&conflictCourse.contains(course2)){
						prExamBooking2.setPeExamNo(peExamNo0);
						this.getGeneralDao().save(prExamBooking2);
						continue;
					}
					
					/**
					 * ????????????????????????????????????,?????????????????????,?????????????????????????????????
					 */
					List list1 = this.courseConflict(course1.getId());
					List list2 = this.courseConflict(course2.getId());
					if(list1.size()<=list2.size()){
						prExamBooking1.setPeExamNo(peExamNo0);
						this.getGeneralDao().save(prExamBooking1);
						
						//???????????????
						conflictCourse.add(course1);
					} else {
						prExamBooking2.setPeExamNo(peExamNo0);
						this.getGeneralDao().save(prExamBooking2);
						//???????????????
						conflictCourse.add(course2);
					}
//					System.out.println(prExamBooking.getPrTchStuElective().getPrTchOpencourse().getPeTchCourse().getName());
					
				}
//				System.out.println(o[0].toString());
			}
			message +="??????"+ conflictCourse.size()+"?????????????????????"+peExamNo0.getName()+"</br>";
		}
		
		//????????????????????????????????????
		DetachedCriteria dcPeExamNo = DetachedCriteria.forClass(PeExamNo.class);
		dcPeExamNo.add(Restrictions.eq("peSemester", peSemester));
		dcPeExamNo.addOrder(Order.asc("sequence"));
		List<PeExamNo> peExamNoList = this.getGeneralDao().getList(dcPeExamNo);
		if(peExamNoList==null||peExamNoList.isEmpty()){
			throw new EntityException("????????????????????????????????????????????????????????????????????????????????????");
		}
		
		//?????????????????????
		for (PeExamNo peExamNo : peExamNoList) {
			int count = 0; //???????????????????????????????????????
			//???????????????????????????
			if(peExamNo.getSequence()==0){
				//???????????????????????????
				if(!ifConflict){
					continue;
				}
//				System.out.println("00000");
				

					//????????????????????????
					for(PeSite peSite : sites){
						
						HashMap<PeExamRoom,Integer> map = new HashMap<PeExamRoom,Integer>(); //????????????????????????????????????
						int room = 0 ; //?????????
						//?????????????????????
						for (PeTchCourse peTchCourse : conflictCourse) {						

						
						/**
						 * ????????????????????????
						 * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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
						int seat = 0;//?????????
//						message +="??????????????????"+this.toThree(room)+" </br>";
						PeExamRoom peExamRoom = new PeExamRoom();
						peExamRoom.setName(peExamNo.getName()+peSite.getName()+this.toThree(room));
						peExamRoom.setPeExamNo(peExamNo);
						peExamRoom.setCode(this.toThree(room));
						peExamRoom.setPeSite(peSite);
						peExamRoom = (PeExamRoom)this.getGeneralDao().save(peExamRoom);
						
						//???????????????????????????
						for (PrExamBooking prExamBooking : prExamBookingList) {
							count++;//?????????????????????1
							if(seat==30){
								room ++;
								seat=1;
//								message +="??????????????????"+this.toThree(room)+" </br> ";
								PeExamRoom examRoom = new PeExamRoom();
								examRoom.setName(peExamNo.getName()+peSite.getName()+this.toThree(room));
								examRoom.setPeExamNo(peExamNo);
								examRoom.setCode(this.toThree(room));
								examRoom.setPeSite(peSite);
								peExamRoom = (PeExamRoom)this.getGeneralDao().save(examRoom);
								
//								message +="?????????????????????"+prExamBooking.getPrTchStuElective().getPeStudent().getTrueName()+" ?????????"+seat+""+ "??????"+this.toThree(room)+" </br> ";
								prExamBooking.setPeExamNo(peExamNo);
								prExamBooking.setPeExamRoom(peExamRoom);
								prExamBooking.setSeatNo(this.toTwo(seat));
								this.getGeneralDao().save(prExamBooking);
								continue;
							}
							seat++;
//							message +="?????????????????????"+prExamBooking.getPrTchStuElective().getPeStudent().getTrueName()+" ?????????"+this.toTwo(seat)+ "??????"+this.toThree(room)+" </br> ";
							prExamBooking.setPeExamNo(peExamNo);
							prExamBooking.setPeExamRoom(peExamRoom);
							prExamBooking.setSeatNo(this.toTwo(seat));
							this.getGeneralDao().save(prExamBooking);
						}
						//???????????????????????????,?????????????????????map???
						if(seat<30){
							map.put(peExamRoom, seat);
						}
					}
						if(map!=null&&map.size()>1){
							//?????????????????????1?????????????????????????????????
							this.saveSmallExamRoom(map, room);
						}
				}
			examNoMSG +=peExamNo.getName()+"??????????????????"+count+"???</br>";
			} else { //??????????????????????????????
				
				//???????????????????????????????????????????????????
				DetachedCriteria dcPrTchOpencourse = DetachedCriteria.forClass(PrTchOpencourse.class);
				dcPrTchOpencourse.add(Restrictions.eq("peSemester",peSemester));
				dcPrTchOpencourse.add(Restrictions.eq("adviceExamNo", peExamNo.getSequence().toString()));
				List<PrTchOpencourse>  prTchOpencourseList = this.getGeneralDao().getList(dcPrTchOpencourse);
				if(prTchOpencourseList==null||prTchOpencourseList.isEmpty()){
					continue;
				}
				

					//????????????????????????
					for(PeSite peSite : sites){
						
						HashMap<PeExamRoom,Integer> map = new HashMap<PeExamRoom,Integer>(); //????????????????????????????????????
						int room = 0 ; //?????????
						//???????????????????????????
						for (PrTchOpencourse prTchOpencourse : prTchOpencourseList) {						

//						System.out.println(prTchOpencourse.getPeTchCourse().getName()+peSite.getName());
						
						/**
						 * ????????????????????????
						 * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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
						int seat = 0;//?????????
//						message +="??????????????????"+this.toThree(room)+" </br>";
						PeExamRoom peExamRoom = new PeExamRoom();
						peExamRoom.setName(peExamNo.getName()+peSite.getName()+this.toThree(room));
						peExamRoom.setPeExamNo(peExamNo);
						peExamRoom.setCode(this.toThree(room));
						peExamRoom.setPeSite(peSite);
						peExamRoom = (PeExamRoom)this.getGeneralDao().save(peExamRoom);
						
						//???????????????????????????
						for (PrExamBooking prExamBooking : prExamBookingList) {
							count++;//?????????????????????1
							if(seat==30){
								room ++;
								seat=1;
//								message +="??????????????????"+this.toThree(room)+" </br> ";
								PeExamRoom examRoom = new PeExamRoom();
								examRoom.setName(peExamNo.getName()+peSite.getName()+this.toThree(room));
								examRoom.setPeExamNo(peExamNo);
								examRoom.setCode(this.toThree(room));
								examRoom.setPeSite(peSite);
								peExamRoom = (PeExamRoom)this.getGeneralDao().save(examRoom);
								
//								message +="?????????????????????"+prExamBooking.getPrTchStuElective().getPeStudent().getTrueName()+" ?????????"+seat+ "??????"+this.toThree(room)+" </br> ";
								prExamBooking.setPeExamNo(peExamNo);
								prExamBooking.setPeExamRoom(peExamRoom);
								prExamBooking.setSeatNo(this.toTwo(seat));
								this.getGeneralDao().save(prExamBooking);
								continue;
							}
							seat++;
//							message +="?????????????????????"+prExamBooking.getPrTchStuElective().getPeStudent().getTrueName()+" ?????????"+this.toTwo(seat)+ "??????"+this.toThree(room)+" </br> ";
							prExamBooking.setPeExamNo(peExamNo);
							prExamBooking.setPeExamRoom(peExamRoom);
							prExamBooking.setSeatNo(this.toTwo(seat));
							this.getGeneralDao().save(prExamBooking);
						}
						//???????????????????????????,?????????????????????map???
						if(seat<30){
							map.put(peExamRoom, seat);
						}
					}
						if(map!=null&&map.size()>1){
							//?????????????????????1?????????????????????????????????
							this.saveSmallExamRoom(map, room);
						}
				}
				examNoMSG +=peExamNo.getName()+"??????????????????"+count+"???</br>";
			}
		}

		return message+examNoMSG + "????????????????????????";
	}
	
	/**
	 * ????????????????????????????????????????????????????????????
	 */
	private String saveSmallExamRoom(HashMap<PeExamRoom,Integer> map,int roomNo ){
			

			List<Integer> list = new ArrayList<Integer>();//????????????????????????????????????
			List<PeExamRoom> roomList = new ArrayList<PeExamRoom>();//??????????????????????????????
			
			//???HashMap??????value??????????????????
		    List<Map.Entry<PeExamRoom, Integer>> entryList = new ArrayList<Map.Entry<PeExamRoom, Integer>>(map.entrySet());
		    Collections.sort(entryList, new EntryComparator());
		    
		    Iterator<Map.Entry<PeExamRoom, Integer>> i = entryList.iterator();
		    while (i.hasNext()) {
		      Map.Entry<PeExamRoom, Integer> entry = i.next();
//		      System.out.println("key=" + entry.getKey() + ", value=" + entry.getValue());
		      PeExamRoom room = entry.getKey(); //??????
		      int n = entry.getValue();//??????
		      
		      int m=0;//?????????????????????????????????
				for(m=0;m<list.size();m++) { 
					if(list.get(m)+n<=30) break;
				}
				if(m==list.size()){
					list.add(n);//????????????????????????
					PeExamRoom newRoom = new PeExamRoom(); //???????????????
					int number = roomNo+m+1;
					newRoom.setCode(this.toThree(number));
					newRoom.setPeExamNo(room.getPeExamNo());
					newRoom.setName(room.getName().substring(0, room.getName().length()-3)+this.toThree(number));
					newRoom.setPeSite(room.getPeSite());
					newRoom = (PeExamRoom)this.getGeneralDao().save(newRoom);
					
					roomList.add(newRoom);//???????????????
					
					DetachedCriteria dcPrExamBooking = DetachedCriteria.forClass(PrExamBooking.class);
					dcPrExamBooking.add(Restrictions.eq("peExamRoom", room));
					dcPrExamBooking.addOrder(Order.asc("seatNo"));
					List<PrExamBooking> prExamBookingList = this.getGeneralDao().getList(dcPrExamBooking);
					//???????????????newRoom
					for (PrExamBooking prExamBooking : prExamBookingList) {
						prExamBooking.setPeExamRoom(newRoom);
						this.getGeneralDao().save(prExamBooking);
					}

					//????????????
					this.getGeneralDao().delete(room);
				} else {
					int num = list.get(m);
					list.set(m,list.get(m)+n);//??????????????????
					DetachedCriteria dcPrExamBooking = DetachedCriteria.forClass(PrExamBooking.class);
					dcPrExamBooking.add(Restrictions.eq("peExamRoom", room));
					dcPrExamBooking.addOrder(Order.asc("seatNo"));
					List<PrExamBooking> prExamBookingList = this.getGeneralDao().getList(dcPrExamBooking);
					//???????????????roomList.get(m),?????????????????????
					for (PrExamBooking prExamBooking : prExamBookingList) {
						num++;
						prExamBooking.setPeExamRoom(roomList.get(m));
						prExamBooking.setSeatNo(this.toTwo(num));
						this.getGeneralDao().save(prExamBooking);
					}
					//????????????
					this.getGeneralDao().delete(room);
				}
				
		    }

		return "";
	}

	/**
	 * ?????????????????????????????????
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
	 * ?????????????????????ddd?????????
	 * @param num ?????????
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
	 * ?????????????????????01?????????
	 * @param num ?????????
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
	 * ???????????????????????????????????????????????????(peStudent.id , adviceExamNo)
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
	 *  ???????????????????????????
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
	 * ????????????????????????
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
	 * ????????????????????????
	 * 
	 */
	public void saveModifyRoomNo(){
		PeSemester peSemester = this.getSemester();
		//????????????????????????????????????
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
				//?????????????????????????????????????????????
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
								info.append("????????????"+prExamBooking.getSeatNo()+"??????????????????????????????B??????????????????<br/>"); 
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
								info.append("????????????"+prExamBooking.getSeatNo()+"??????????????????????????????A??????????????????<br/>"); 
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
			throw new EntityException("?????????????????????");
		}
		return info.toString();
	}	
}
