package util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.Query;
import org.hibernate.Session;

import entity.Area;
import entity.ChineseLanguageLevel;
import entity.EductionBackground;
import entity.Ethnic;
import entity.Grade;
import entity.JobDuty;
import entity.JobTitle;
import entity.Language;
import entity.Organization;
import entity.Politics;
import entity.Subject;
import entity.Teacher;
import entity.TeachingGrade;
import entity.TeachingLanguage;
import entity.TeachingSubject;

public class TeacherService {

	/**
	 * 把Excel中特定行的信息放入teacher对象中
	 * 
	 * @param teacher
	 * @param row
	 * @return 如果信息无效则返回false 设置成功返回true
	 */
	public static Boolean setTeacherInfo(Teacher teacher, Row row, Session session, int line) {
		boolean bool = false;
		String idcard = ExcelUtil.GetCellValue(Letter.B, row);
		String errorInfo = IdCardUtil.IDCardValidate(idcard);
		if (!errorInfo.equals("")) {
			System.out.println(line + "行身份证号为:" + idcard + "   " + errorInfo);
			return bool;
		}
		Date date = ExcelUtil.stringToDate(ExcelUtil.GetCellValue(Letter.N, row));
		if (date == null) {
			System.out.println(line + "行身份证号为:" + idcard + " 的参加工作时间格式错误");
			return bool;
		}
		teacher.setTeachingAge(date);
		teacher.setIdcard(idcard);
		teacher.setPassword(idcard.substring(idcard.length() - 6));
		teacher.setStatus((short) 1);
		teacher.setName(ExcelUtil.GetCellValue(Letter.A, row));
		teacher.setMobile(ExcelUtil.GetCellValue(Letter.K, row));
		teacher.setEmail(ExcelUtil.GetCellValue(Letter.O, row));
		Date date2 = new Date();
		Timestamp nousedate = new Timestamp(date2.getTime());

		teacher.setCreattime(nousedate);
		teacher.setSex(IdCardUtil.getSex(idcard));
		teacher.setBirthday(IdCardUtil.getBirthday(idcard));
		teacher.setMultiLanguage(ExcelUtil.GetCellValue(Letter.S, row).equals("是") ? true : false);
		teacher.setCreator(350000004);

		// 外键
		teacher.setArea(TeacherService.getArea(ExcelUtil.GetCellValue(Letter.E, row), session));
		if (teacher.getArea() == null) {
			return bool;
		}

		teacher.setJobDuty(TeacherService.getDuty(ExcelUtil.GetCellValue(Letter.F, row), session));
		if (teacher.getJobDuty() == null) {
			return bool;
		}

		teacher.setJobTitle(TeacherService.getJobTitle(ExcelUtil.GetCellValue(Letter.G, row), session));
		if (teacher.getJobTitle() == null) {
			return bool;
		}

		teacher.setEductionBackground(TeacherService.getEductionBackground(ExcelUtil.GetCellValue(Letter.H, row), session));
		if (teacher.getEductionBackground() == null) {
			return bool;
		}

		teacher.setPolitics(TeacherService.getPolitics(ExcelUtil.GetCellValue(Letter.I, row), session));
		if (teacher.getPolitics() == null) {
			return bool;
		}

		teacher.setEthnic(TeacherService.getEthnic(ExcelUtil.GetCellValue(Letter.J, row), session));
		if (teacher.getEthnic() == null) {
			return bool;
		}

		teacher.setOrganization(TeacherService.getOrganization(ExcelUtil.GetCellValue(Letter.L, row), session));
		if (teacher.getOrganization() == null) {
			return bool;
		}

		teacher.setChineseLanguageLevel(TeacherService.getChineseLanguageLevel(ExcelUtil.GetCellValue(Letter.M, row), session));
		if (teacher.getChineseLanguageLevel() == null) {
			return bool;
		}
		bool = true;
		return true;
	}

	public static void checkTeacherDate(Teacher teacher, Row row, Session session, int line) {
		String idcard = ExcelUtil.GetCellValue(Letter.B, row);
		String errorInfo = IdCardUtil.IDCardValidate(idcard);
		if (!errorInfo.equals("")) {
			System.out.print(" 身份证号为:" + idcard + "   " + errorInfo);
		}
		Date date = ExcelUtil.stringToDate(ExcelUtil.GetCellValue(Letter.N, row));
		if (date == null) {
			System.out.print(" 身份证号为:" + idcard + " 的参加工作时间格式错误");
		}
		teacher.setIdcard(idcard);
		teacher.setPassword(idcard.substring(idcard.length() - 6));
		teacher.setStatus((short) 1);
		teacher.setName(ExcelUtil.GetCellValue(Letter.A, row));
		teacher.setMobile(ExcelUtil.GetCellValue(Letter.K, row));
		teacher.setEmail(ExcelUtil.GetCellValue(Letter.O, row));
		Date date2 = new Date();
		Timestamp nousedate = new Timestamp(date2.getTime());

		teacher.setCreattime(nousedate);
		teacher.setSex(IdCardUtil.getSex(idcard));
		teacher.setBirthday(IdCardUtil.getBirthday(idcard));
		teacher.setMultiLanguage(ExcelUtil.GetCellValue(Letter.S, row).equals("是") ? true : false);
		teacher.setCreator(350000004);

		// 外键
		teacher.setArea(TeacherService.getArea(ExcelUtil.GetCellValue(Letter.E, row), session));
		teacher.setJobDuty(TeacherService.getDuty(ExcelUtil.GetCellValue(Letter.F, row), session));
		teacher.setJobTitle(TeacherService.getJobTitle(ExcelUtil.GetCellValue(Letter.G, row), session));
		teacher.setEductionBackground(TeacherService.getEductionBackground(ExcelUtil.GetCellValue(Letter.H, row), session));
		teacher.setPolitics(TeacherService.getPolitics(ExcelUtil.GetCellValue(Letter.I, row), session));
		teacher.setEthnic(TeacherService.getEthnic(ExcelUtil.GetCellValue(Letter.J, row), session));
		teacher.setOrganization(TeacherService.getOrganization(ExcelUtil.GetCellValue(Letter.L, row), session));
		teacher.setChineseLanguageLevel(TeacherService.getChineseLanguageLevel(ExcelUtil.GetCellValue(Letter.M, row), session));
	}

	public static Identity getIdentity(Identity identity, Row r) {
		// 身份证号
		Cell idCard = r.getCell(1, Row.RETURN_BLANK_AS_NULL);
		identity.setIdCard(ExcelUtil.GetCellValue(idCard));
		// 手机号
		Cell mobil = r.getCell(10, Row.RETURN_BLANK_AS_NULL);
		identity.setMobile(ExcelUtil.GetCellValue(mobil));
		// 邮箱
		Cell email = r.getCell(14, Row.RETURN_BLANK_AS_NULL);
		identity.setEmail(ExcelUtil.GetCellValue(email));
		return identity;
	}

	public static Teacher findTeacher(Identity identity, Session session) {
		String hql = "select t from Teacher t where t.idcard=:idcard or t.email=:email or t.mobile=:mobile";
		Query query = session.createQuery(hql);
		query.setParameter("idcard", identity.getIdCard());
		query.setParameter("email", identity.getEmail());
		query.setParameter("mobile", identity.getMobile());
		@SuppressWarnings("unchecked")
		List<Teacher> list = query.list();
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据政治面貌名称获取对应政治面貌对象
	 * 
	 * @param name
	 * @param session
	 * @return
	 */
	public static Politics getPolitics(String name, Session session) {
		String hql = "select p from Politics p where p.name='" + name + "'";
		return query(hql, session, "政治面貌");
	}

	/**
	 * 根据教育背景名称获取对应教育背景对象
	 * 
	 * @param name
	 * @param session
	 * @return
	 */
	public static EductionBackground getEductionBackground(String name, Session session) {
		String hql = "select e from EductionBackground e where e.name='" + name + "'";
		return query(hql, session, "教育背景");
	}

	/**
	 * 根据所在地区名称获取地区对象
	 * 
	 * @param name
	 * @param session
	 * @return
	 */
	public static Area getArea(String name, Session session) {
		String hql = "select a from Area a where a.name='" + name + "'";
		return query(hql, session, "所在地区(县区市)");
	}

	/**
	 * 根据职称名称获取职称对象
	 * 
	 * @param name
	 * @param session
	 * @return
	 */
	public static JobTitle getJobTitle(String name, Session session) {
		if ("".equals(name) || "无".equals(name)) {
			name = "无职称";
		}

		String hql = "select j from JobTitle j where j.name='" + name + "'";
		return query(hql, session, "职称");
	}

	/**
	 * 根据职务名称获取对应职务对象
	 * 
	 * @param name
	 * @param session
	 * @return
	 */
	public static JobDuty getDuty(String name, Session session) {
		if ("".equals(name)) {
			name = "无";
		}
		String hql = "select j from JobDuty j where j.name='" + name + "'";
		return query(hql, session, "职务");
	}

	/**
	 * 根据民族名称获取对应民族对象
	 * 
	 * @param name
	 * @param session
	 * @return
	 */
	public static Ethnic getEthnic(String name, Session session) {
		String hql = "select e from Ethnic e where e.name='" + name + "'";
		return query(hql, session, "民族");
	}

	/**
	 * 根据所属学校名称获取对应学校对象
	 * 
	 * @param name
	 * @param session
	 * @return
	 */
	public static Organization getOrganization(String name, Session session) {
		String hql = "select o from Organization o where o.name='" + name + "'";
		return query(hql, session, "所属学校");
	}

	/**
	 * 根据汉语水平名称获取对应汉语水平对象
	 * 
	 * @param name
	 * @param session
	 * @return
	 */
	public static ChineseLanguageLevel getChineseLanguageLevel(String name, Session session) {
		if ("".equals(name) || "无".equals(name)) {
			name = "未测试";
		}
		String hql = "select c from ChineseLanguageLevel c where c.name='" + name + "'";
		return query(hql, session, "汉语水平");
	}

	/**
	 * 根据主要教学学段名称获取学段对象
	 * 
	 * @param name
	 * @param session
	 * @return
	 */
	public static Grade getGrade(String name, Session session) {
		String hql = "select g from Grade g where g.name='" + name + "'";
		return query(hql, session, "主要教学学段");
	}

	/**
	 * 根据主要教学学科名称获取学科对象
	 * 
	 * @param name
	 * @param session
	 * @return
	 */
	public static Subject getSubject(String name, Session session) {
		String hql = "select s from Subject s where s.name='" + name + "'";
		return query(hql, session, "主要教学学科");
	}

	/**
	 * 根据主要教学语言获取教学语言对象
	 * 
	 * @param name
	 * @param session
	 * @return
	 */
	public static Language getLanguage(String name, Session session) {
		String hql = "select l from Language l where l.name='" + name + "'";
		return query(hql, session, "主要教学语言");
	}

	protected static <T> T query(String hql, Session session, String typeName) {
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<T> list = query.list();
		if (list.size() == 1) {
			return list.get(0);
		} else {
			System.out.print(" 数据库无相应记录 --" + typeName + ":" + hql.substring(hql.indexOf("=")));
		}
		return null;
	}
	
	public static Boolean teachingSubjectIsExist(TeachingSubject teachingSubject, Session session){
		
		String hql = "select t from TeachingSubject t where t.teacher='"+teachingSubject.getTeacher().getId()+"' and t.subject='"+teachingSubject.getSubject().getId()+"'";
		return isExist(hql , session, "teachingSubject");
	}
	
	public static Boolean teachingLanguageIsExist(TeachingLanguage teachingLanguage, Session session){
		String hql = "select t from TeachingLanguage t where t.teacher='"+teachingLanguage.getTeacher().getId()+"' and t.language='"+teachingLanguage.getLanguage().getId()+"'";
		return isExist(hql , session, "teachingLanguage");
	}
	
	public static Boolean teachingGradeIsExist(TeachingGrade teachingGrade, Session session){
		String hql = "select t from TeachingGrade t where t.teacher='"+teachingGrade.getTeacher().getId()+"' and t.grade='"+teachingGrade.getGrade().getId()+"'";
		return isExist(hql , session, "teachingGrade");
	}

	protected static Boolean isExist(String hql, Session session, String typeName) {
		Query query = session.createQuery(hql);
		List<?> list = query.list();
		if (list.size() > 0) {
			System.out.print(" 记录已存在 --" + typeName + ":" + hql.substring(hql.indexOf("=")));
			return true;
		} else {
			return false;
		}
	}

}
