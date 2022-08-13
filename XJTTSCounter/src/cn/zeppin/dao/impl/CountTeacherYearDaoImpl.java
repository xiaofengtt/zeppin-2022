package cn.zeppin.dao.impl;

import org.springframework.transaction.annotation.Transactional;

import cn.zeppin.dao.ICountTeacherYearDao;

public class CountTeacherYearDaoImpl extends BaseDaoImpl<Object[], Integer> implements ICountTeacherYearDao {

	@Override
	@Transactional
	public void updateDate(Integer year) {
		try{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE count_teacher_year set status=0 where year=").append(year);
		this.executeSQLUpdate(sb.toString(), null);
		
		sb = new StringBuilder();
		sb.append(" INSERT into count_teacher_year(year,organization,subject,grade,teacher_count)");
		sb.append(" SELECT ").append(year).append(" as year,t.ORGANIZATION, ts.subject, tg.GRADE, count(*) as teacher_count ");
		sb.append(" from teacher t ");
		sb.append(" LEFT OUTER join teaching_grade tg on tg.teacher=t.id and tg.ISPRIME=1 ");
		sb.append(" LEFT OUTER JOIN teaching_subject ts on ts.teacher=t.id and ts.ISPRIME=1");
		sb.append(" where t.STATUS=1");
		sb.append(" GROUP BY t.ORGANIZATION, ts.SUBJECT, tg.GRADE;");
		this.executeSQLUpdate(sb.toString(), null);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
