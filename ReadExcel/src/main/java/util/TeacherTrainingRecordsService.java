package util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.hibernate.Query;
import org.hibernate.Session;

import entity.Organization;
import entity.Project;
import entity.TeacherTrainingRecords;
import entity.TrainingCollege;
import entity.TrainingSubject;

public class TeacherTrainingRecordsService {

	public static void checkTeacherTrainingRecordsInfo(TeacherTrainingRecords teacherTrainingRecords, Row row, Session session) {
		Short checkStatus = 1;
		Short finalStatus = 2;
		teacherTrainingRecords.setCheckStatus(checkStatus);
		teacherTrainingRecords.setIsPrepare(false);
		teacherTrainingRecords.setCreator(350000004);
		teacherTrainingRecords.setFinalStatus(finalStatus);

		Date date = new Date();
		Timestamp nousedate = new Timestamp(date.getTime());
		teacherTrainingRecords.setCreattime(nousedate);
		teacherTrainingRecords.setRegisttime(nousedate);
		teacherTrainingRecords.setTrainingStatus((short) 1);

		try {
			teacherTrainingRecords.setUuid(util.Utlity.getUuidPwd());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 外键
		teacherTrainingRecords.setOrganization(TeacherTrainingRecordsService.getOrganization(ExcelUtil.GetCellValue(Letter.L, row), session));

		teacherTrainingRecords.setTrainingSubject(TeacherTrainingRecordsService.getTrainingSubject(ExcelUtil.GetCellValue(Letter.X, row), session));

		teacherTrainingRecords.setProject(TeacherTrainingRecordsService.getProject(ExcelUtil.GetCellValue(Letter.W, row), session));

		teacherTrainingRecords.setTrainingCollege(TeacherTrainingRecordsService.getTrainingCollege((ExcelUtil.GetCellValue(Letter.Y, row)), session));

	}

	/**
	 * 把Excel中特定行的信息放入TeacherTrainingRecords对象中
	 * 
	 * @param TeacherTrainingRecords
	 * @param row
	 * @return 如果信息无效则返回false 设置成功返回true
	 */
	public static Boolean setTeacherTrainingRecordsInfo(TeacherTrainingRecords teacherTrainingRecords, Row row, Session session) {
		Short checkStatus = 1;
		Short finalStatus = 2;
		teacherTrainingRecords.setCheckStatus(checkStatus);
		teacherTrainingRecords.setIsPrepare(false);
		teacherTrainingRecords.setCreator(350000004);
		teacherTrainingRecords.setFinalStatus(finalStatus);

		Date date = new Date();
		Timestamp nousedate = new Timestamp(date.getTime());
		teacherTrainingRecords.setCreattime(nousedate);
		teacherTrainingRecords.setRegisttime(nousedate);
		teacherTrainingRecords.setTrainingStatus((short) 1);

		try {
			teacherTrainingRecords.setUuid(util.Utlity.getUuidPwd());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 外键
		teacherTrainingRecords.setOrganization(TeacherTrainingRecordsService.getOrganization(ExcelUtil.GetCellValue(Letter.L, row), session));
		if (teacherTrainingRecords.getOrganization() == null) {
			return false;
		}
		teacherTrainingRecords.setTrainingSubject(TeacherTrainingRecordsService.getTrainingSubject(ExcelUtil.GetCellValue(Letter.X, row), session));
		if (teacherTrainingRecords.getTrainingSubject() == null) {
			return false;
		}
		teacherTrainingRecords.setProject(TeacherTrainingRecordsService.getProject(ExcelUtil.GetCellValue(Letter.W, row), session));
		if (teacherTrainingRecords.getProject() == null) {
			return false;
		}
		teacherTrainingRecords.setTrainingCollege(TeacherTrainingRecordsService.getTrainingCollege((ExcelUtil.GetCellValue(Letter.Y, row)), session));
		if (teacherTrainingRecords.getTrainingCollege() == null) {
			return false;
		}
		return true;
	}

	/**
	 * 根据承接单位名称获取承接单位对象
	 * 
	 * @param name
	 * @param session
	 * @return
	 */
	public static TrainingCollege getTrainingCollege(String name, Session session) {
		String hql = "select t from TrainingCollege t where name='" + name + "'";
		return query(hql, session, "承接单位");
	}

	/**
	 * 根据培训项目名称获取培训项目对象
	 * 
	 * @param name
	 * @param session
	 * @return
	 */
	public static Project getProject(String name, Session session) {
		int id = 0;
		if ("自治区双语教师培训项目2013秋季".equals(name)) {
			id = 350000023;
		} else if ("自治区双语教师培训项目2014秋季".equals(name)) {
			id = 350000021;
		} else if ("自治区双语教师培训项目2014春季".equals(name)) {
			id = 350000019;
		}
		Project project = (Project) session.get(Project.class, id);
		return project;
	}

	/**
	 * 根据培训科目名称获取培训科目对象
	 * 
	 * @param name
	 * @param session
	 * @return
	 */
	public static TrainingSubject getTrainingSubject(String name, Session session) {
		String hql = "select t from TrainingSubject t where name ='" + name + "'";
		return query(hql, session, "培训科目");
	}

	/**
	 * 根据报送机构名称获取机构对象
	 * 
	 * @param name
	 * @param session
	 * @return
	 */
	public static Organization getOrganization(String name, Session session) {
		String hql = "select o from Organization o where o.name='" + name + "'";
		return query(hql, session, "保送机构");
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

}
