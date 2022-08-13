package excel;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.ExcelUtil;
import util.Identity;
import util.Letter;
import util.TeacherService;
import util.TeacherTrainingRecordsService;
import entity.Grade;
import entity.Language;
import entity.Subject;
import entity.Teacher;
import entity.TeacherTrainingRecords;
import entity.TeachingGrade;
import entity.TeachingLanguage;
import entity.TeachingSubject;

public class HibernateTest {

	private Configuration configuration;
	private SessionFactory sessionFactory;
	private ServiceRegistry serviceRegistry;
	private Session session;
	private Transaction transaction;

	
	//private String filePath = "D:\\（修改后）双语名单反馈.xls";
	private String filePath = "D:\\双语学员导入信息反馈表（0225）.xlsx";
	// private String filePath = "D:\\2014、2013年双语培训学员导入信息0305.xlsx"; 
	 //private String filePath = "D:\\2013秋季双语学员补报人员.xlsx"; 

	private int rowStart = 1; // based 0

	@Before
	public void before() {
		configuration = new Configuration().configure();
		serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		session = sessionFactory.getCurrentSession();
		transaction = session.beginTransaction();
	}

	@After
	public void after() {
		transaction.commit();
		sessionFactory.close();
	}

	// 只检测数据正确性，不对数据库内容做修改
	@Test
	public void checkTeacherData() throws Exception {
		Identity identity = new Identity();
		Workbook wb = WorkbookFactory.create(new File(filePath));
		Sheet sheet = wb.getSheetAt(0);
		int rowEnd = sheet.getLastRowNum();
		int i = 0;
		for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
			i++;
			Row row = sheet.getRow(rowNum);
			TeacherService.getIdentity(identity, row);
			Teacher teacher = TeacherService.findTeacher(identity, session);
			System.out.print(rowNum + 1 + " ");
			if (teacher == null) {
				teacher = new Teacher();
				TeacherService.checkTeacherDate(teacher, row, session, i);
			}
			TeacherTrainingRecords teacherTrainingRecords = new TeacherTrainingRecords();
			TeacherTrainingRecordsService.checkTeacherTrainingRecordsInfo(teacherTrainingRecords, row, session);
			if (i % 20 == 0) {
				session.flush();
				session.clear();
			}
			System.out.println();
		}
		System.out.println(i);
	}

	// 把数据插入数据库，如果遇到错误信息则跳过
	@Test
	public void test() throws Exception {
		Identity identity = new Identity();
		Workbook wb = WorkbookFactory.create(new File(filePath));
		Sheet sheet = wb.getSheetAt(0);
		int rowEnd = sheet.getLastRowNum();
		int i = 0;
		for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
			i++;
			Row row = sheet.getRow(rowNum);
			TeacherService.getIdentity(identity, row);
			Teacher teacher = TeacherService.findTeacher(identity, session);
			Boolean bool = true;
			// System.out.println(rowNum + 1);
			// 如果数据库中无相应教师信息,则将教师信息入库
			if (teacher == null) {
				teacher = new Teacher();
				bool = TeacherService.setTeacherInfo(teacher, row, session, i);
				// 如果教师信息正确,则进行入库操作
				if (bool) {
					TeacherTrainingRecords teacherTrainingRecords = new TeacherTrainingRecords();
					boolean rcoBool = TeacherTrainingRecordsService.setTeacherTrainingRecordsInfo(teacherTrainingRecords, row, session);
					if (rcoBool) {
						teacherTrainingRecords.setTeacher(teacher);
						session.save(teacher);
						session.save(teacherTrainingRecords);
					}
				} else {
					System.out.println("错误" + identity);
				}
			} else {
				// 教师信息已存在,只在教师培训记录表中添加数据
				System.out.println(rowNum + 1 + "  " + identity + " 已存在  ");
				TeacherTrainingRecords teacherTrainingRecords = new TeacherTrainingRecords();
				boolean sc = TeacherTrainingRecordsService.setTeacherTrainingRecordsInfo(teacherTrainingRecords, row, session);
				if (sc) {
					teacherTrainingRecords.setTeacher(teacher);
					try {
						session.save(teacherTrainingRecords);
					} catch (Exception e) {
					}

				}
			}

			if (i % 20 == 0) { // 20, same as the JDBC batch size
				// flush a batch of inserts and release memory:
				session.flush();
				session.clear();
			}
		}
		System.out.println(i);
	}

	@Test
	public void TeacherGradle() throws InvalidFormatException, IOException {
		Identity identity = new Identity();
		Workbook wb = WorkbookFactory.create(new File(filePath));
		Sheet sheet = wb.getSheetAt(0);
		int rowEnd = sheet.getLastRowNum();
		int i = 0;
		for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
			i++;

			Row row = sheet.getRow(rowNum);
			TeacherService.getIdentity(identity, row);
			Teacher teacher = TeacherService.findTeacher(identity, session);
			System.out.print(rowNum + 1 + " ");
			if (teacher == null) {
				System.out.println("无相应老师记录");
				continue;
			}

		
			Grade grade = TeacherService.getGrade(ExcelUtil.GetCellValue(Letter.Q, row), session);
			Language language = TeacherService.getLanguage(ExcelUtil.GetCellValue(Letter.R, row), session);
			Subject subject = TeacherService.getSubject(ExcelUtil.GetCellValue(Letter.P, row), session);

			if (grade != null) {
				TeachingGrade teachingGrade = new TeachingGrade(grade, teacher, true);
				if (!TeacherService.teachingGradeIsExist(teachingGrade, session)) {
					session.save(teachingGrade);
				}
			}

			if (language != null) {
				TeachingLanguage teachingLanguage = new TeachingLanguage(language, teacher, true);
				if (!TeacherService.teachingLanguageIsExist(teachingLanguage, session)) {
					session.save(teachingLanguage);
				}
			}

			if (subject != null) {
				TeachingSubject teachingSubject = new TeachingSubject(subject, teacher, true);

				if (!TeacherService.teachingSubjectIsExist(teachingSubject, session)) {
					session.save(teachingSubject);
				}
			}

			if (i % 20 == 0) {
				session.flush();
				session.clear();
			}
			System.out.println();

		}

		System.out.println(i);
	}

}
