package com.whaty.platform.entity.service.imp.exam.mainCourse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeExamMaincourseNo;
import com.whaty.platform.entity.bean.PeExamMaincourseRoom;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrExamOpenMaincourse;
import com.whaty.platform.entity.bean.PrExamStuMaincourse;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.exam.mainCourse.PrExamOpenMaincourseService;

public class PrExamOpenMaincourseServiceImp implements
		PrExamOpenMaincourseService {

	private GeneralDao generalDao;
	private MyListDAO myListDao;
	
	public MyListDAO getMyListDao() {
		return myListDao;
	}

	public void setMyListDao(MyListDAO myListDao) {
		this.myListDao = myListDao;
	}

	public int saveBooking(String stuIds, String courseIds)
			throws EntityException {
		
		StringBuffer msg = new StringBuffer();
		
		List list = new ArrayList();
		
		String[] ids = stuIds.split(",");
		
		String temp = stuIds.replaceAll(",", "','");
		String condition_in = "('";
		condition_in += temp.substring(0,temp.length() - 2);
		condition_in += ")";
		
		String sql = " select s.name as student_name                            " + 
		"   from pe_student s                                      " +
		"  where s.id in                                           " + condition_in +
		" minus                                                    " +
		" select stu.student_name                                  " +
		"   from pr_tch_program_course pc,                         " +
		"        pe_tch_course course,                             " +
		"        pe_tch_program_group pg,                          " +
		"        pe_tch_program p,                                 " +
		"        Enum_Const e,                                     " +
		"        (select student.id            as id,              " +
		"                student.name          as student_name,    " +
		"                student.fk_major_id   as major_id,        " +
		"                student.fk_edutype_id as edutype_id,      " +
		"                student.fk_grade_id   as grade_id         " +
		"                student.flag_major_type   as major_type   " +
		"           from pe_student student                        " +
		"          where student.id in                             " + condition_in +
		"          ) stu                                           " +
		"  where pc.fk_course_id = course.id                       " +
		"    and pc.fk_programgroup_id = pg.id                     " +
		"    and pg.fk_program_id = p.id                           " +
		"    and p.fk_major_id = stu.major_id                      " +
		"    and p.fk_edutype_id = stu.edutype_id                  " +
		"    and p.fk_grade_id = stu.grade_id                      " +
		"    and pc.flag_is_main_course = e.id                     " +
		"    and e.code = '1'                                      " +
		"    and p.flag_major_type = stu.major_type                " +
		"    and course.id = '"+courseIds+"'                       " ;
		
		try {
			list = this.getGeneralDao().getBySQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.size() > 0){
			msg.append("?????????" + list.get(0).toString());
			for(int i = 1; i < list.size(); i++){
				msg.append("???" + list.get(i));
			}
			msg.append("????????????????????????????????????????????????????????????????????????????????????");
			throw new EntityException(msg.toString());
		}
		
		String sql_check = " select student.name                                          " + 
		"   from pr_exam_stu_maincourse  m,                            " +
		"        pe_student              student,                      " +
		"        pr_exam_open_maincourse openmaincourse,               " +
		"        pe_exam_maincourse_no   no,                           " +
		"        pe_semester             semester                      " +
		"  where m.fk_exam_open_maincourse_id = openmaincourse.id      " +
		"    and m.fk_student_id = student.id                          " +
		"    and openmaincourse.fk_exam_maincourse_no_id = no.id       " +
		"    and no.fk_semester_id = semester.id                       " +
		"    and semester.flag_active = '1'                            " +
		"    and student.id in                                         " + condition_in +
		"    and openmaincourse.fk_course_id = '"+courseIds+"'                   " ;
		
		try {
			list = this.getGeneralDao().getBySQL(sql_check);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.size() > 0){
			msg.append("?????????" + list.get(0).toString());
			for(int i = 1; i < list.size(); i++){
				msg.append("???" + list.get(i));
			}
			msg.append("????????????????????????????????????????????????????????????????????????");
			throw new EntityException(msg.toString());
		}
			
		EnumConst flag_score_status = new EnumConst();
		EnumConst flag_score_pub = new EnumConst();
		PrExamOpenMaincourse openMaincourse = new PrExamOpenMaincourse();
		try{
			flag_score_status = this.getMyListDao().getEnumConstByNamespaceCode("FlagScoreStatus", "0");	//0:?????????
			flag_score_pub = this.getMyListDao().getEnumConstByNamespaceCode("FlagScorePub", "0");	//0:?????????
			
			DetachedCriteria dcPrExamOpenMaincourse = DetachedCriteria.forClass(PrExamOpenMaincourse.class);
			dcPrExamOpenMaincourse.createAlias("peTchCourse", "peTchCourse");
			dcPrExamOpenMaincourse.createCriteria("peExamMaincourseNo", "peExamMaincourseNo")
									.createAlias("peSemester", "peSemester");
			dcPrExamOpenMaincourse.add(Restrictions.eq("peTchCourse.id", courseIds));
			dcPrExamOpenMaincourse.add(Restrictions.eq("peSemester.flagActive", "1"));
			openMaincourse = (PrExamOpenMaincourse)this.getGeneralDao().getList(dcPrExamOpenMaincourse).get(0);
		}catch(Exception e){
			e.printStackTrace();
		}

		this.getGeneralDao().setEntityClass(PrExamStuMaincourse.class);
		for(int i = 0; i < ids.length; i++){
			PrExamStuMaincourse stuMaincourse = new PrExamStuMaincourse();
			PeStudent student = new PeStudent();
			student.setId(ids[i]);
			stuMaincourse.setPeStudent(student);
			stuMaincourse.setEnumConstByFlagScorePub(flag_score_pub);
			stuMaincourse.setEnumConstByFlagScoreStatus(flag_score_status);
			stuMaincourse.setPrExamOpenMaincourse(openMaincourse);
			this.getGeneralDao().save(stuMaincourse);
		}
		
		return ids.length;
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	
	/**
	 * ??????????????? ?????????????????????
	 */
	public int saveReserver(PeStudent peStudent,
			List<PrExamOpenMaincourse> prExamOpenMaincourseList)
			throws EntityException {
		EnumConst enumConst = this.getGeneralDao().getEnumConstByNamespaceCode("FlagScoreStatus", "0");
		for (PrExamOpenMaincourse prExamOpenMaincourse : prExamOpenMaincourseList) {
			PrExamStuMaincourse prExamStuMaincourse = new PrExamStuMaincourse();
			prExamStuMaincourse.setPeStudent(peStudent);
			prExamStuMaincourse.setPrExamOpenMaincourse(prExamOpenMaincourse);
			prExamStuMaincourse.setEnumConstByFlagScoreStatus(enumConst);
			this.getGeneralDao().save(prExamStuMaincourse);
		}
		return prExamOpenMaincourseList.size();
	}
	/**
	 * ?????????????????????????????????
	 * @return
	 * @throws EntityException
	 */
	public String saveMainExamRoom() throws EntityException{
		String message = "";
		PeSemester peSemester = this.getSemester(); //????????????
		List<PeSite> sites = this.getExamSite();
		
		/**
		 * ????????????????????????????????????????????????????????????????????????????????????
		 */
		DetachedCriteria dcPeExamMaincourseRoom = DetachedCriteria.forClass(PeExamMaincourseRoom.class);
		dcPeExamMaincourseRoom.createCriteria("prExamOpenMaincourse", "prExamOpenMaincourse")
			.createCriteria("peExamMaincourseNo", "peExamMaincourseNo").createCriteria("peSemester", "peSemester")
			.add(Restrictions.eq("flagActive", "1"));
		List<PeExamMaincourseRoom> peExamMaincourseRoomList = this.getGeneralDao().getList(dcPeExamMaincourseRoom);
		if(peExamMaincourseRoomList!=null&&peExamMaincourseRoomList.size()>0){
			for (PeExamMaincourseRoom peExamMaincourseRoom : peExamMaincourseRoomList) {
				this.getGeneralDao().delete(peExamMaincourseRoom);
			}
		}
		
		//??????????????????????????????????????????
		DetachedCriteria dcPeExamMaincourseNo = DetachedCriteria.forClass(PeExamMaincourseNo.class);
		dcPeExamMaincourseNo.add(Restrictions.eq("peSemester", peSemester));
		dcPeExamMaincourseNo.addOrder(Order.asc("name"));
		List<PeExamMaincourseNo> peExamMaincourseNoList = this.getGeneralDao().getList(dcPeExamMaincourseNo);
		if(peExamMaincourseNoList==null||peExamMaincourseNoList.isEmpty()){
			throw new EntityException("??????????????????????????????????????????");
		}
		
		//??????????????????
		for (PeExamMaincourseNo peExamMaincourseNo : peExamMaincourseNoList) {
			int count=0;
			//???????????????????????????
			DetachedCriteria dcPrExamOpenMaincourse = DetachedCriteria.forClass(PrExamOpenMaincourse.class);
			dcPrExamOpenMaincourse.add(Restrictions.eq("peExamMaincourseNo", peExamMaincourseNo));
			List<PrExamOpenMaincourse> prExamOpenMaincourseList = this.getGeneralDao().getList(dcPrExamOpenMaincourse);
			if(prExamOpenMaincourseList!=null&&prExamOpenMaincourseList.size()>0){
				//??????????????????
				for(PeSite peSite : sites){
					int room = 0;//?????????

						for (PrExamOpenMaincourse prExamOpenMaincourse : prExamOpenMaincourseList) {					
						//????????????????????????
						DetachedCriteria dcPrExamStuMaincourse = DetachedCriteria.forClass(PrExamStuMaincourse.class);
						DetachedCriteria dcPeStudent = dcPrExamStuMaincourse.createCriteria("peStudent", "peStudent");
						dcPrExamStuMaincourse.add(Restrictions.eq("prExamOpenMaincourse", prExamOpenMaincourse));
						dcPeStudent.createCriteria("peSite", "peSite")
						.add(Restrictions.or(Restrictions.eq("peSite.id", peSite.getId()),Restrictions.eq("peSite.peSite.id", peSite.getId())));
						dcPeStudent.addOrder(Order.asc("peEdutype"));
						dcPeStudent.addOrder(Order.asc("peMajor"));
						List<PrExamStuMaincourse> prExamStuMaincourseList = this.getGeneralDao().getList(dcPrExamStuMaincourse);
						if(prExamStuMaincourseList==null||prExamStuMaincourseList.isEmpty()){
							continue;
						}
						room++;
						int seat = 0;//?????????
						//??????????????????
						PeExamMaincourseRoom peExamMaincourseRoom = new PeExamMaincourseRoom();
						peExamMaincourseRoom.setName(peExamMaincourseNo.getName()+peSite.getName()+this.toThree(room));
						peExamMaincourseRoom.setPrExamOpenMaincourse(prExamOpenMaincourse);
						peExamMaincourseRoom.setCode(this.toThree(room));
						peExamMaincourseRoom.setPeSite(peSite);
						peExamMaincourseRoom = (PeExamMaincourseRoom)this.getGeneralDao().save(peExamMaincourseRoom);
//						System.out.println("?????????????????????"+peExamMaincourseRoom.getName());
						
						for (PrExamStuMaincourse prExamStuMaincourse : prExamStuMaincourseList) {
							
							if(seat==30){
								room++;
								//??????????????????
								peExamMaincourseRoom = new PeExamMaincourseRoom();
								peExamMaincourseRoom.setName(peExamMaincourseNo.getName()+peSite.getName()+this.toThree(room));
								peExamMaincourseRoom.setPrExamOpenMaincourse(prExamOpenMaincourse);
								peExamMaincourseRoom.setCode(this.toThree(room));
								peExamMaincourseRoom.setPeSite(peSite);
								peExamMaincourseRoom = (PeExamMaincourseRoom)this.getGeneralDao().save(peExamMaincourseRoom);
//								System.out.println("?????????????????????"+peExamMaincourseRoom.getName());
								
								seat=1;
								//??????????????????
								prExamStuMaincourse.setSeatNo(this.toThree(seat));
								prExamStuMaincourse.setPeExamMaincourseRoom(peExamMaincourseRoom);
								this.getGeneralDao().save(prExamStuMaincourse);
//								System.out.println("?????????????????????"+prExamStuMaincourse.getPeStudent().getName()+"?????????"+prExamStuMaincourse.getSeatNo());
								count++;
								continue;
								
							}
							seat++;
							//??????????????????
							prExamStuMaincourse.setSeatNo(this.toThree(seat));
							prExamStuMaincourse.setPeExamMaincourseRoom(peExamMaincourseRoom);
							this.getGeneralDao().save(prExamStuMaincourse);
//							System.out.println("?????????????????????"+prExamStuMaincourse.getPeStudent().getName()+"?????????"+prExamStuMaincourse.getSeatNo());
							count++;
							
						}
					}
					
				}
			}
			message += peExamMaincourseNo.getName()+"?????????"+ count +"???</br>";
		}

		return message;
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
	 * ??????????????????????????????ddd?????????
	 * @param num ????????????????????????
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
}
