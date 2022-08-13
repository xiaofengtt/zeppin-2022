package cn.zeppin.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import cn.zeppin.dao.ITeacherDao;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Teacher;
import cn.zeppin.utility.Utlity;

public class TeacherDaoImpl extends BaseDaoImpl<Teacher, Integer> implements ITeacherDao {

	/**
	 * @author Clark 2014.05.27
	 * 
	 *         通过搜索条件取得教师信息
	 * @param paramsMap
	 * @param offset
	 * @param length
	 * @return List<Teacher> 符合条件的教师信息列表
	 */
	public List getTeachers(Map<String, Object> paramsMap, int offset, int length) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append(" from Teacher t, Organization o where 1=1 ");
		sb.append(" and t.organization=o.id");

		// 身份证号
		String teacherIdCard = (String) paramsMap.get("teacherIdCard");
		if (teacherIdCard != null && teacherIdCard.length() > 0) {
			sb.append(" and t.idcard like '%");
			sb.append(teacherIdCard);
			sb.append("%'");
		}

		// 姓名
		String teacherName = (String) paramsMap.get("teacherName");
		if (teacherName != null && teacherName.length() > 0) {
			sb.append(" and t.name like '%");
			sb.append(teacherName);
			sb.append("%'");
		}

		// 年龄
		String age1 = (String) paramsMap.get("age1");
		String age2 = (String) paramsMap.get("age2");
		if (age1 != null && age1.length() > 0 && age2 != null && age2.length() > 0) {
			String strAges = Utlity.getAgeToDate(Integer.valueOf(age2));
			String strAgee = Utlity.getAgeToDate(Integer.valueOf(age1));
			sb.append(" and t.birthday BETWEEN '");
			sb.append(strAges);
			sb.append("' AND '");
			sb.append(strAgee);
			sb.append("'");
		}

		// 教龄
		String tage1 = (String) paramsMap.get("tage1");
		String tage2 = (String) paramsMap.get("tage2");
		if (tage1 != null && tage1.length() > 0 && tage2 != null && tage2.length() > 0) {
			String strAges = Utlity.getAgeToDate(Integer.valueOf(tage2));
			String strAgee = Utlity.getAgeToDate(Integer.valueOf(tage1));
			sb.append(" and t.teachingAge BETWEEN '");
			sb.append(strAges);
			sb.append("' AND '");
			sb.append(strAgee);
			sb.append("'");
		}

		// 学校
		String school = (String) paramsMap.get("school");
		if (school != null && school.length() > 0) {
			sb.append(" and t.organization=");
			sb.append(school);
		}

		// 权限——能看到哪些学校的教师
		Organization org = (Organization) paramsMap.get("organization");
		if (org != null) {
			sb.append(" and o.scode like '").append(org.getScode()).append("%' ");
		}

		// sex
		String sex = (String) paramsMap.get("sex");
		if (sex != null && sex.length() > 0) {
			sb.append(" and t.sex=");
			sb.append(sex);
		}

		// 双语
		String isMu = (String) paramsMap.get("isMu");
		if (isMu != null && isMu.length() > 0) {
			sb.append(" and t.multiLanguage=");
			sb.append(isMu);
		}

		// 民族
		String ethnic = (String) paramsMap.get("ethnic");
		if (ethnic != null && ethnic.length() > 0) {
			sb.append(" and t.ethnic=");
			sb.append(ethnic);
		}

		// 职称
		String jobTitle = (String) paramsMap.get("jobTitle");
		if (jobTitle != null && jobTitle.length() > 0) {
			sb.append(" and t.jobTitle=");
			sb.append(jobTitle);
		}

		// 汉语言水平
		String chineseLanguageLevel = (String) paramsMap.get("chineseLanguageLevel");
		if (chineseLanguageLevel != null && chineseLanguageLevel.length() > 0) {
			sb.append(" and t.chineseLanguageLevel=");
			sb.append(chineseLanguageLevel);
		}

		// 政治面貌
		String politics = (String) paramsMap.get("politics");
		if (politics != null && politics.length() > 0) {
			sb.append(" and t.politics=");
			sb.append(politics);
		}

		// 职务
		String jobDuty = (String) paramsMap.get("jobDuty");
		if (jobDuty != null && jobDuty.length() > 0) {
			sb.append(" and t.jobDuty=");
			sb.append(jobDuty);
		}

		// 学历背景
		String eductionBackground = (String) paramsMap.get("eductionBackground");
		if (eductionBackground != null && eductionBackground.length() > 0) {
			sb.append(" and t.eductionBackground=");
			sb.append(eductionBackground);
		}

		List result = this.getListForPage(sb.toString(), offset, length);
		return result;
	}

	/**
	 * @author Clark 2014.05.27
	 * 
	 *         通过搜索条件取得教师信息数量
	 * @param paramsMap
	 * @return int 符合条件的教师信息数量
	 */
	public int getTeacherCount(Map<String, Object> paramsMap) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(*) from Teacher t, Organization o, TeachingSubject ts, TeachingLanguage tl, TeachingGrade tg where 1=1 ");
		sb.append(" and t.organization=o.id");
		sb.append(" and t.id=ts.teacher");
		sb.append(" and ts.isprime=1");
		sb.append(" and t.id=tl.teacher");
		sb.append(" and tl.isprime=1");
		sb.append(" and t.id=tg.teacher");
		sb.append(" and tg.isprime=1");

		// 身份证号
		String teacherIdCard = (String) paramsMap.get("teacherIdCard");
		if (teacherIdCard != null && teacherIdCard.length() > 0) {
			sb.append(" and t.idcard like '%");
			sb.append(teacherIdCard);
			sb.append("%'");
		}

		// 姓名
		String teacherName = (String) paramsMap.get("teacherName");
		if (teacherName != null && teacherName.length() > 0) {
			sb.append(" and t.name like '%");
			sb.append(teacherName);
			sb.append("%'");
		}

		// 年龄
		String age1 = (String) paramsMap.get("age1");
		String age2 = (String) paramsMap.get("age2");
		if (age1 != null && age1.length() > 0 && age2 != null && age2.length() > 0) {
			String strAges = Utlity.getAgeToDate(Integer.valueOf(age1));
			String strAgee = Utlity.getAgeToDate(Integer.valueOf(age2));
			sb.append(" and t.birthday BETWEEN '");
			sb.append(strAges);
			sb.append("' AND '");
			sb.append(strAgee);
			sb.append("'");
		}

		// 教龄
		String tage1 = (String) paramsMap.get("tage1");
		String tage2 = (String) paramsMap.get("tage2");
		if (tage1 != null && tage1.length() > 0 && tage2 != null && tage2.length() > 0) {
			String strAges = Utlity.getAgeToDate(Integer.valueOf(tage1));
			String strAgee = Utlity.getAgeToDate(Integer.valueOf(tage2));
			sb.append(" and t.teachingAge BETWEEN '");
			sb.append(strAges);
			sb.append("' AND '");
			sb.append(strAgee);
			sb.append("'");
		}

		// 学校
		String school = (String) paramsMap.get("school");
		if (school != null && school.length() > 0) {
			sb.append(" and t.organization=");
			sb.append(school);
		}

		// 权限——能看到哪些学校的教师
		Organization org = (Organization) paramsMap.get("organization");
		if (org != null) {
			sb.append(" and o.scode like '").append(org.getScode()).append("%' ");
		}

		// sex
		String sex = (String) paramsMap.get("sex");
		if (sex != null && sex.length() > 0) {
			sb.append(" and t.sex=");
			sb.append(sex);
		}

		// 双语
		String isMu = (String) paramsMap.get("isMu");
		if (isMu != null && isMu.length() > 0) {
			sb.append(" and t.multiLanguage=");
			sb.append(isMu);
		}

		// 民族
		String ethnic = (String) paramsMap.get("ethnic");
		if (ethnic != null && ethnic.length() > 0) {
			sb.append(" and t.ethnic=");
			sb.append(ethnic);
		}

		// 职称
		String jobTitle = (String) paramsMap.get("jobTitle");
		if (jobTitle != null && jobTitle.length() > 0) {
			sb.append(" and t.jobTitle=");
			sb.append(jobTitle);
		}

		// 汉语言水平
		String chineseLanguageLevel = (String) paramsMap.get("chineseLanguageLevel");
		if (chineseLanguageLevel != null && chineseLanguageLevel.length() > 0) {
			sb.append(" and t.chineseLanguageLevel=");
			sb.append(chineseLanguageLevel);
		}

		// 政治面貌
		String politics = (String) paramsMap.get("politics");
		if (politics != null && politics.length() > 0) {
			sb.append(" and t.politics=");
			sb.append(politics);
		}

		// 职务
		String jobDuty = (String) paramsMap.get("jobDuty");
		if (jobDuty != null && jobDuty.length() > 0) {
			sb.append(" and t.jobDuty=");
			sb.append(jobDuty);
		}

		// 学历背景
		String eductionBackground = (String) paramsMap.get("eductionBackground");
		if (eductionBackground != null && eductionBackground.length() > 0) {
			sb.append(" and t.eductionBackground=");
			sb.append(eductionBackground);
		}

		// 主要教学学科
		String mainTeachingSubject = (String) paramsMap.get("mainTeachingSubject");
		if (mainTeachingSubject != null && mainTeachingSubject.length() > 0) {
			sb.append(" and ts.subject=");
			sb.append(mainTeachingSubject);
		}

		// 主要教学语言
		String mainTeachingLanguage = (String) paramsMap.get("mainTeachingLanguage");
		if (mainTeachingLanguage != null && mainTeachingLanguage.length() > 0) {
			sb.append(" and tl.language=");
			sb.append(mainTeachingLanguage);
		}

		// 主要教学学段
		String mainTeachingGrades = (String) paramsMap.get("mainTeachingGrades");
		if (mainTeachingGrades != null && mainTeachingGrades.length() > 0) {
			sb.append(" and tg.grade=");
			sb.append(mainTeachingGrades);
		}

		int result = ((Long) this.getObjectByHql(sb.toString(), null)).intValue();
		return result;
	}

	/**
	 * @param string
	 * @return
	 */
	public double getCount(String string) {
		// TODO Auto-generated method stub
		double count = Double.parseDouble(getObjectByHql(string, null).toString());
		return count;
	}

	@Override
	public List<Teacher> getTeacherByEmail(String email) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from Teacher t where 1=1 and t.email='");
		sb.append(email+"'");
		
		return this.getListByHSQL(sb.toString());
	}

	@Override
	public List getTeacherForLogin(Object[] pars) {
		// TODO Auto-generated method stub
		String sql = "select * from teacher t where (t.idcard=? or t.mobile=? or t.email=?) ";
		Query query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter(0, pars[0]);
		query.setParameter(1, pars[0]);
		query.setParameter(2, pars[0]);
		
		List li = query.list();
		return li;
	}

}
