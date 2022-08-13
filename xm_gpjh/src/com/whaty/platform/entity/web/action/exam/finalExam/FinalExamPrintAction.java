package com.whaty.platform.entity.web.action.exam.finalExam;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeExamNo;
import com.whaty.platform.entity.bean.PeExamRoom;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class FinalExamPrintAction extends MyBaseAction {
	private String sitename; //所选择的学习中心
	private String examnoname;//考试场次
	private String examroomname;//考场
	private String printType; //打印类型
	
	List<List> bookinglist;


	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/exam/finalExamPrint";

	}
	
	public String printSignIn(){
		List<List> list = new ArrayList<List>();
		DetachedCriteria dcPeExamNo = DetachedCriteria.forClass(PeExamNo.class);
		dcPeExamNo.createCriteria("peSemester", "peSemester").add(Restrictions.eq("flagActive", "1"));
		if(!this.getExamnoname().equals("所有场次")){
			dcPeExamNo.add(Restrictions.eq("name", this.getExamnoname()));
		}
		dcPeExamNo.addOrder(Order.asc("sequence"));
		List<PeExamNo> peExamNoList = null;
		try {
			peExamNoList = this.getGeneralService().getList(dcPeExamNo);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if (peExamNoList==null||!(peExamNoList.size()>0)){
			this.setMsg("没有符合条件的数据");
		}
		for (PeExamNo peExamNo : peExamNoList) {
			//取出考场
			DetachedCriteria dcPeExamRoom = DetachedCriteria.forClass(PeExamRoom.class);
			dcPeExamRoom.add(Restrictions.eq("peExamNo", peExamNo));
			if(!this.getExamroomname().equals("所有考场")){
				dcPeExamRoom.add(Restrictions.eq("name", this.getExamroomname()));
			}
			dcPeExamRoom.addOrder(Order.asc("code"));
			List<PeExamRoom> peExamRoomList = null;
			try {
				peExamRoomList = this.getGeneralService().getList(dcPeExamRoom);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			if(!(peExamRoomList!=null&&peExamRoomList.size()>0)){
				continue;
			}
			
			//取出学习中心
			DetachedCriteria dcPeSite = DetachedCriteria.forClass(PeSite.class);
			dcPeSite.add(Restrictions.eq("name", this.getSitename()));
			List<PeSite> peSiteList = null;
			try {
				peSiteList=this.getGeneralService().getList(dcPeSite);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			
			if(!(peSiteList!=null&&peSiteList.size()>0)){
				this.setMsg("所填的学习中心不存在");
				return "msg";
			}
			
			PeSite peSite = peSiteList.get(0);
			for (PeExamRoom peExamRoom : peExamRoomList) {
				
				//取出这个学习中心这个考场的考试预约记录
				DetachedCriteria dcPrExamBooking = DetachedCriteria.forClass(PrExamBooking.class);
				dcPrExamBooking.add(Restrictions.eq("peExamRoom", peExamRoom));
				dcPrExamBooking.add(Restrictions.eq("peExamNo", peExamNo));
				DetachedCriteria dcSite = dcPrExamBooking.createCriteria("prTchStuElective", "prTchStuElective")
					.createCriteria("peStudent", "peStudent").createCriteria("peSite", "peSite");
				dcSite.add(Restrictions.or(Restrictions.eq("id", peSite.getId()), Restrictions.eq("peSite", peSite)));
				dcPrExamBooking.addOrder(Order.asc("seatNo"));
				List<PrExamBooking> prExamBookingList = null;
				try {
					prExamBookingList = this.getGeneralService().getList(dcPrExamBooking);
				} catch (EntityException e) {
					e.printStackTrace();
				}
				if(!(prExamBookingList!=null&&prExamBookingList.size()>0)){
					continue;
				}
				list.add(prExamBookingList);
			}
		}
		if(list==null||list.isEmpty()){
			this.setMsg("没有符合条件的数据");
			return "msg";
		}
		
		this.setBookinglist(list);
		if (this.getPrintType()!=null&&this.getPrintType().equals("seatNo")){
			return "seatNo";
		}
	
		return "signIn";
	}
	
	/**
	 * 获得当前学期名字
	 * @return
	 */
	public String getCurrentSemName(){
		String semNmae = "";
		try{
			DetachedCriteria semDc = DetachedCriteria.forClass(PeSemester.class);
			semDc.add(Restrictions.eq("flagActive", "1"));
			List<PeSemester> PeSemesterlist = this.getGeneralService().getList(semDc);
			semNmae = PeSemesterlist.get(0).getName();
		}catch(Throwable e){
			e.printStackTrace();
		}
		return semNmae;
	}
	
	/**
	 * 根据学期查询出考试场次信息
	 * @param semName
	 * @return
	 */
	public List getExamNo(String semName){
		List examNoList = null;
		try{
			DetachedCriteria examNoDc = DetachedCriteria.forClass(PeExamNo.class);
			if(this.getExamnoname()!=null&&this.getExamnoname().length()>0&&!this.getExamnoname().equals("所有场次")){
				examNoDc.add(Restrictions.eq("name", this.getExamnoname()));
			}else{
				examNoDc.createAlias("peSemester", "peSemester").add(Restrictions.eq("peSemester.name", semName));
			}
			examNoDc.addOrder(Order.asc("sequence"));
			examNoList = this.getGeneralService().getList(examNoDc);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return examNoList;
	}
	
	/**
	 * 根据考试场次信息查询出站点信息
	 * @param examNoId
	 * @return
	 */
	public List getExamSite(String examNoId){
		List eSiteList = null;
		try{
			DetachedCriteria examsiteDc = DetachedCriteria.forClass(PeSite.class);
			if(this.getSitename()!=null&&this.getSitename().length()>0&&!this.getSitename().equals("所有学习中心")){
				examsiteDc.add(Restrictions.eq("name", this.getSitename()));
			}else{
				examsiteDc.add(Restrictions.sqlRestriction("id in (select distinct decode(site.fk_exam_site_id,null,site.id,site.fk_exam_site_id) from pe_exam_room er,pr_exam_booking eb,pr_tch_stu_elective tse,pe_student stu,pe_site site where er.id=eb.fk_exam_room_id and eb.fk_tch_stu_elective_id=tse.id and tse.fk_stu_id=stu.id and stu.fk_site_id=site.id)"));
			}
			examsiteDc.addOrder(Order.asc("name"));
			eSiteList  = this.getGeneralService().getList(examsiteDc);			
		}catch(Throwable e){
			e.printStackTrace();
		}
		return eSiteList;
	}
	
	/**
	 * 根据考试场次ID和站点ID查询出考场信息
	 * @param examNoId
	 * @param siteId
	 * @return
	 */
	public List getExamRoom(String examNoId,String siteId){
		List eroomList = null;
		try{
			DetachedCriteria examRoomDc = DetachedCriteria.forClass(PeExamRoom.class);
			examRoomDc.add(Restrictions.sqlRestriction("id in (select distinct er.id from pe_exam_room er,pr_exam_booking eb,pr_tch_stu_elective tse,pe_student stu,pe_site site where er.id = eb.fk_exam_room_id and eb.fk_tch_stu_elective_id = tse.id and tse.fk_stu_id = stu.id and stu.fk_site_id = site.id and er.fk_exam_no ='"+examNoId+"' and decode(site.fk_exam_site_id, null, site.id, site.fk_exam_site_id) = '"+siteId+"')"));
			examRoomDc.addOrder(Order.asc("code"));
			eroomList  = this.getGeneralService().getList(examRoomDc);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return eroomList;
	}
	
	public List getExamCourse(String examRoomId,String siteId){
		List eCourseList = null;
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("select c.code, c.name, soc.stuNum                       ");
			sql.append("  from pr_tch_opencourse toc,                           ");
			sql.append("       pe_tch_course c,                                 ");
			sql.append("       (select count(*) stuNum, tse.fk_tch_opencourse_id");
			sql.append("          from pr_exam_booking     eb,                  ");
			sql.append("               pr_tch_stu_elective tse,                 ");
			sql.append("               pe_student          stu,                 ");
			sql.append("               pe_site             site                 ");
			sql.append("         where eb.fk_exam_room_id = '"+examRoomId+"'    ");
			sql.append("           and eb.fk_tch_stu_elective_id = tse.id       ");
			sql.append("           and tse.fk_stu_id = stu.id                   ");
			sql.append("           and stu.fk_site_id = site.id                 ");
			sql.append("           and decode(site.fk_exam_site_id,             ");
			sql.append("                      null,                             ");
			sql.append("                      site.id,                          ");
			sql.append("                      site.fk_exam_site_id) =           ");
			sql.append("               '"+siteId+"'                             ");
			sql.append("         group by tse.fk_tch_opencourse_id) soc         ");
			sql.append(" where toc.id = soc.fk_tch_opencourse_id                ");
			sql.append("   and toc.fk_course_id = c.id                          ");
			eCourseList = this.getGeneralService().getBySQL(sql.toString());
		}catch(Throwable e){
			e.printStackTrace();
		}
		return eCourseList;
	}
	
	public String printDrawing(){
		return "drawPrint";
	}
	
	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	public String getExamnoname() {
		return examnoname;
	}

	public void setExamnoname(String examnoname) {
		this.examnoname = examnoname;
	}

	public String getExamroomname() {
		return examroomname;
	}

	public void setExamroomname(String examroomname) {
		this.examroomname = examroomname;
	}

	public List<List> getBookinglist() {
		return bookinglist;
	}

	public void setBookinglist(List<List> bookinglist) {
		this.bookinglist = bookinglist;
	}

	public String getPrintType() {
		return printType;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}
	
}
