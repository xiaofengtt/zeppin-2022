package cn.zeppin.task;

import java.sql.Timestamp;
import java.util.Calendar;

import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.utility.Utlity;

public class TaskJobDAO extends HibernateTemplateDAO<Object, Integer> {

	/**
	 * 计算当年内，教师监考次数
	 */
	public void calculateUserInvigilationData() {
		// TODO Auto-generated method stub
		Calendar thisYear = Calendar.getInstance();
		Integer year = thisYear.get(Calendar.YEAR);
		StringBuilder sql = new StringBuilder();

		sql.append("update invigilation_teacher t set t.INVIGILATE_COUNT=(");
		sql.append("select count(*) from exam_teacher_room e where 1=1 ");
		sql.append(" and e.teacher=t.id and e.`STATUS`=1 and e.IS_CONFIRM=1 ");
		sql.append(" and DATE_FORMAT(e.CREATETIME,'%Y')=").append(year);
		sql.append(" group by e.TEACHER,DATE_FORMAT(e.CREATETIME,'%Y') ORDER BY e.CREATETIME");
		sql.append(")");
		this.executeSQL(sql.toString());
	}

	public void calculateUserInvigilationCount() {
		StringBuilder sql = new StringBuilder();
		sql.append("update invigilation_teacher t set t.INVIGILATE_COUNT=0 where t.INVIGILATE_COUNT is null ");
		this.executeSQL(sql.toString());
	}

	/**
	 * 查看考试截止时间，超过后，自动结束考试
	 */
	public void calculateExamEndtimeData() {
		// TODO Auto-generated method stub
		String nowTime = Utlity.timeSpanToString(new Timestamp(System.currentTimeMillis()));
		StringBuilder sql = new StringBuilder();
		sql.append("update exam_information ei set ei.`STATUS`=0 where 1=1");
		sql.append(" and ei.status<>0");
		sql.append(" and ei.EXAM_ENDTIME < '" + nowTime + "'");
		this.executeSQL(sql.toString());

	}

	/**
	 * 停用老师定时解禁
	 */
	public void calculateTeacherReleaseTime() {
		String nowTime = Utlity.timeSpanToString(new Timestamp(System.currentTimeMillis()));
		StringBuilder sql = new StringBuilder();
		sql.append("update invigilation_teacher t set t.DISABLETYPE=1,t.STATUS=1 where 1=1");
		sql.append(" and t.DISABLETYPE=2");//半年后自动解禁
		sql.append(" and t.RELEASETIME < '" + nowTime + "'");
	    System.out.println("解禁："+ sql.toString());
		this.executeSQL(sql.toString());
	}

}
