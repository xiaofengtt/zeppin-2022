package com.whaty.platform.entity.web.action.workspaceTeacher;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class HomeworkAction extends MyBaseAction {

	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceTeacher/homework";
	}
	
	private String courseId;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	public String compose() {
		dbpool db = new dbpool();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String zuoye = "";
		String score = "0";
		double totalScore = 0;
		int count = 0;
		String sql = "select t.zuoye from entity_course_item t where t.id='"+this.getCourseId()+"'";
		
		List electiveList = new ArrayList();
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
		dc.createCriteria("peStudent", "peStudent").createCriteria("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus");
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		dc.createCriteria("prTchOpencourse", "prTchOpencourse").createAlias("peSemester", "peSemester").createAlias("peTchCourse", "peTchCourse");
		dc.add(Restrictions.eq("peTchCourse.id", this.getCourseId()));
		dc.add(Restrictions.or(Restrictions.and(Restrictions.eq("peSemester.flagActive", "0"), Restrictions.lt("scoreTotal", new Double(60))), Restrictions.eq("peSemester.flagActive", "1")));
		try {
			electiveList = this.getGeneralService().getList(dc);
		} catch (EntityException e1) {
			e1.printStackTrace();
			return "";
		}
		try {
			conn = db.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs!=null && rs.next()) {
				zuoye = rs.getString("zuoye");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				rs = null;
				stmt.close();
				stmt = null;
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (zuoye.equals("1")) { //在线作业
			
			for (Iterator iter = electiveList.iterator(); iter.hasNext();) {
				PrTchStuElective elective = (PrTchStuElective) iter.next();
				conn = db.getConn();
				String sql1 = 	"select nvl(h.score,0) as score from pe_tch_course       c," +
								"            pr_tch_opencourse          o,        " +
								"            test_homeworkpaper_info    i,        " +
								"            test_homeworkpaper_history h,        " +
								"            pe_semester                s         " +
								" where s.flag_active = '1'                       " +
								"   and o.fk_course_id = c.id                     " +
								"   and o.fk_semester_id = s.id                   " +
								"   and i.group_id = o.id                         " +
								"   and h.testpaper_id = i.id                     " +
								"   and c.id = '"+ this.getCourseId() +"'		  " +
								"	and h.user_id like '%"+elective.getPeStudent().getId()+"%'";
				try {
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql1);
					while (rs!=null && rs.next()) {
						
						totalScore += Double.parseDouble(rs.getString("score"));
						count ++;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						rs.close();
						rs = null;
						stmt.close();
						stmt = null;
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(count==0) {
					count = 1;
				}
				score = totalScore/count + "";
				elective.setScoreHomework(Double.parseDouble(score));
				try {
					this.getGeneralService().save(elective);
					count = 0;
					totalScore = 0;
				} catch (EntityException e) {
					e.printStackTrace();
				}
			}
			
		} else { //上传作业
			for (Iterator iter = electiveList.iterator(); iter.hasNext();) {
				PrTchStuElective elective = (PrTchStuElective) iter.next();
				String sql3 =   "select nvl(h.score,0)	as score		  " +
								"  from homework_info     i,      " +
								"       homework_history  h,      " +
								"       pr_tch_opencourse o,      " +
								"       pe_tch_course     c,      " +
								"       pe_semester       s       " +
								" where h.homework_id = i.id      " +
								"   and i.group_id = o.id         " +
								"   and o.fk_course_id = c.id     " +
								"   and o.fk_semester_id = s.id   " +
								"   and s.flag_active = '1'       " +
								"   and c.id = '"+this.getCourseId()+"'" +
								"	and h.user_id = '"+elective.getPeStudent().getId()+"'";
				conn = db.getConn();
				try {
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql3);
					while (rs!=null && rs.next()) {
						totalScore += Double.parseDouble(rs.getString("score"));
						count ++;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						rs.close();
						rs = null;
						stmt.close();
						stmt = null;
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(count==0) {
					count = 1;
				}
				score = totalScore/count + "";
				elective.setScoreHomework(Double.parseDouble(score));
				try {
					this.getGeneralService().save(elective);
					count = 0;
					totalScore = 0;
				} catch (EntityException e) {
					e.printStackTrace();
				}
			}
		}
		this.setInteractionMsg("作业成绩计算完成。");
		return "interactionMsg";
	}
	
	
}
