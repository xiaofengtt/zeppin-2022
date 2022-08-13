package com.whaty.platform.entity.service.imp.teaching.elective;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeTchBook;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PeTchCourseware;
import com.whaty.platform.entity.bean.PrTchCourseBook;
import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.bean.PrTchOpencourseBook;
import com.whaty.platform.entity.bean.PrTchOpencourseCourseware;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.elective.SemesterOpenCourseService;
import com.whaty.platform.util.Const;
import com.whaty.util.string.AttributeManage;
import com.whaty.util.string.WhatyAttributeManage;

public class SemesterOpenCourseServiceImp implements SemesterOpenCourseService {

	private GeneralDao generalDao;
	private MyListDAO myListDAO;
	
	public MyListDAO getMyListDAO() {
		return myListDAO;
	}

	public void setMyListDAO(MyListDAO myListDAO) {
		this.myListDAO = myListDAO;
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}
	
	public PrTchOpencourse savePrTchOpencourse(PrTchOpencourse instance) throws EntityException{
		
		try {
			this.getGeneralDao().save(instance);
			
			PeTchCourse  peTchCourse = instance.getPeTchCourse();
			
			DetachedCriteria dcCourseBook = DetachedCriteria.forClass(PrTchCourseBook.class);
			DetachedCriteria dcPeTchBook = dcCourseBook.createCriteria("peTchBook", "peTchBook");
			dcPeTchBook.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
			dcCourseBook.add(Restrictions.eq("peTchCourse", peTchCourse));
			dcPeTchBook.add(Restrictions.eq("enumConstByFlagIsvalid.code", "1"));
			List peTchBookList = this.getGeneralDao().getList(dcCourseBook);
			
			for (Iterator iter = peTchBookList.iterator(); iter.hasNext();) {
				PeTchBook prTchBook = ((PrTchCourseBook) iter.next()).getPeTchBook();
				PrTchOpencourseBook prTchOpencourseBook = new PrTchOpencourseBook();
				prTchOpencourseBook.setPrTchOpencourse(instance);
				prTchOpencourseBook.setPeTchBook(prTchBook);
				try {
					this.getGeneralDao().save(prTchOpencourseBook);
				} catch (Exception e1) {
					throw new EntityException("添加开课教材失败。");
				}
			}
			
			DetachedCriteria dcPeTchCourseWare = DetachedCriteria.forClass(PeTchCourseware.class);
			dcPeTchCourseWare.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
			dcPeTchCourseWare.add(Restrictions.eq("peTchCourse", peTchCourse));
			dcPeTchCourseWare.add(Restrictions.eq("enumConstByFlagIsvalid.code", "1"));
			List peTchCoursewareList = this.getGeneralDao().getList(dcPeTchCourseWare);
			
			for (Iterator iter = peTchCoursewareList.iterator(); iter.hasNext();) {
				PeTchCourseware peTchCourseware = (PeTchCourseware) iter.next();
				PrTchOpencourseCourseware prTchOpencourseCourseware = new PrTchOpencourseCourseware();
				prTchOpencourseCourseware.setPrTchOpencourse(instance);
				prTchOpencourseCourseware.setPeTchCourseware(peTchCourseware);
				try {
					this.getGeneralDao().save(prTchOpencourseCourseware);
				} catch (Exception e) {
					throw new EntityException("添加开课课件失败。");
				}
			}
			
//			DetachedCriteria dcPrTchCourseTeacher = DetachedCriteria.forClass(PrTchCourseTeacher.class);
//			dcPrTchCourseTeacher.add(Restrictions.eq("peTchCourse",peTchCourse));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EntityException("添加学期开课失败。");
		}
		return instance;
	}

	
	public int saveSemesterOpenCourse() throws EntityException {
		
		this.deleteUnOpenElectiveRecord();
		//得到课程表中所有有效课程
		DetachedCriteria dcPeTchCourse = DetachedCriteria.forClass(PeTchCourse.class);
		dcPeTchCourse.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		dcPeTchCourse.add(Restrictions.eq("enumConstByFlagIsvalid.code", "1"));
		List peTchCourseList = this.getGeneralDao().getList(dcPeTchCourse);
		
		//得到当前活动学期
		DetachedCriteria dcPeSemester = DetachedCriteria.forClass(PeSemester.class);
		dcPeSemester.add(Restrictions.eq("flagNextActive", "1"));
		List peSemesterList = this.getGeneralDao().getList(dcPeSemester);
		PeSemester activeSemester = (PeSemester) peSemesterList.get(0);
		
		for (int i = 0; i < peTchCourseList.size(); i++) {
			PrTchOpencourse prTchOpenCourse = new PrTchOpencourse();
			prTchOpenCourse.setPeTchCourse((PeTchCourse)peTchCourseList.get(i));
			prTchOpenCourse.setPeSemester(activeSemester);
			try {
//				this.getGeneralDao().save(prTchOpenCourse);
				this.savePrTchOpencourse(prTchOpenCourse);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EntityException("开课失败。");
			}
			
		}
		return peTchCourseList.size();
	}
	
	/**
	 * 删除选课表中选课未开课的记录。
	 *
	 */
	private void deleteUnOpenElectiveRecord() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
		dc.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
		dc.createCriteria("prTchOpencourse", "prTchOpencourse").createAlias("peSemester", "peSemester");
		dc.add(Restrictions.not(Restrictions.eq("peSemester.flagNextActive", "1")));
		dc.add(Restrictions.eq("enumConstByFlagElectiveAdmission.code", "0"));
		
		List unOpenElectiveList = new ArrayList();
		unOpenElectiveList = this.getGeneralDao().getList(dc);
		for (Iterator iter = unOpenElectiveList.iterator(); iter.hasNext();) {
			PrTchStuElective unOpenElective = (PrTchStuElective) iter.next();
			this.getGeneralDao().delete(unOpenElective);
		}
	}
	
	/**
	 * 批量导入学期开课（建议考试场次或上课次数）
	 * @param file
	 * @param type 操作类型 examNo 建议考试场次  ，  courseTime  上课次数
	 * @return
	 * @throws EntityException
	 */
	public int saveOpenCourseBatch(File file,String type) throws EntityException{
		StringBuffer msg = new StringBuffer();
		AttributeManage manage=new WhatyAttributeManage();
		int count = 0;
		
		Workbook work = null;
		
		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Ecel表格读取异常！批量添加失败！<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows<2) {
			msg.append("表格为空！<br/>");
		}
		
		DetachedCriteria dcPeSemester = DetachedCriteria.forClass(PeSemester.class);
		dcPeSemester.add(Restrictions.eq("flagNextActive", "1"));
		List<PeSemester> peSemesterList = this.getGeneralDao().getList(dcPeSemester);
		PeSemester peSemester = peSemesterList.get(0);		//选课学期

		String temp = "";
		Set<PrTchOpencourse> prTchOpencourseSet = new HashSet();
		Set<String> name = new HashSet();
		for(int i=1; i<rows; i++) {
			try {
				if(sheet.getCell(0, i).getContents().trim().equals("")&&
						sheet.getCell(1, i).getContents().trim().equals("")){
					msg.append("第"+(i+1)+"行数据添加失败，没有输入任何数据！<br/>");
					continue;
				}
			} catch (Exception e1) {
				msg.append("第"+(i+1)+"行数据添加失败，模板错误！<br/>");
				continue;
			}
			try {
				PrTchOpencourse prTchOpencourse = null;
				PeTchCourse peTchCourse = null; //课程
				String examNo = ""; //建议考试场次
				int num = 10; //上课次数
				StringBuffer lineMsg = new StringBuffer(); //保存每一行的错误信息
				PeTchCourseware peTchCourseware = new PeTchCourseware();
				//课件名
				temp = sheet.getCell(0, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					lineMsg.append("课程名不能为空！");
				} else {

					DetachedCriteria dcPeTchCourse = DetachedCriteria
						.forClass(PeTchCourse.class);
					dcPeTchCourse.add(Restrictions.eq("name", temp));
					List<PeTchCourse> courseList = this.getGeneralDao().getList(dcPeTchCourse);
					if(courseList==null||courseList.size()==0){
						lineMsg.append("课程不存在！");
					}else{
						if(!name.add(temp)){
							lineMsg.append("课程名与文件中前面的数据重复！");
						}else {
							peTchCourse = courseList.get(0);
						}
					}
				}
				if(type.equals("examNo")){
				//建议考试场次
				temp = sheet.getCell(1, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					lineMsg.append("建议考试场次不能为空！");
				} else {
					if(!temp.matches(Const.oneNum)){
						lineMsg.append("建议考试场次格式错误，"+Const.oneNumMessage+"！");
					} else{
							examNo = temp;
					}
				}
				}
				
				if(type.equals("courseTime")){
				//上课次数
				temp = sheet.getCell(1, i).getContents().trim();
				if(temp == null || "".equals(temp)) {
					lineMsg.append("上课次数不能为空！ ");
				}else{
					if(!temp.matches(Const.scoreLine)){
						lineMsg.append("上课次数格式错误，"+Const.scoreLineMessage+"。");
					} else{
						try {
							num = Integer.parseInt(temp);
						} catch (Exception e) {
							e.printStackTrace();
							lineMsg.append("上课次数格式错误！");
						}
					}
				}
				}
				
				if (lineMsg.length()>0){
					msg.append("第"+(i+1)+"行数据添加失败！"+lineMsg.toString()+"<br/>");
					continue;
				}
				
				DetachedCriteria dcPrTchOpencourse = DetachedCriteria.forClass(PrTchOpencourse.class);
				dcPrTchOpencourse.add(Restrictions.eq("peSemester", peSemester));
				dcPrTchOpencourse.add(Restrictions.eq("peTchCourse", peTchCourse));
				List<PrTchOpencourse> opencourseList= this.getGeneralDao().getList(dcPrTchOpencourse);
				if(opencourseList==null||opencourseList.isEmpty()){
					msg.append("第"+(i+1)+"行数据添加失败，请在“学期开课查看”中添加课程"+peTchCourse.getName()+"相关数据<br/>");
					continue;
					
				} else {
					prTchOpencourse = opencourseList.get(0);
					
					if(type.equals("examNo")){
						prTchOpencourse.setAdviceExamNo(examNo);
					}
					if(type.equals("courseTime")){
						prTchOpencourse.setCourseTime((long)num);
					}
				}
				
				if(!prTchOpencourseSet.add(prTchOpencourse)){
					msg.append("第"+(i+1)+"行数据与文件中前面的数据重复<br/>");
					continue;
				}
				
				count ++ ;
			} catch(Exception e) {
				e.printStackTrace();
				msg.append("第"+(i+1)+"行数据添加失败！<br/>");
				continue;
			}
		}
		
		if (msg.length() > 0) {
			if(type.equals("examNo")){
				msg.append("批量导入课程建议考试场次失败，请修改以上错误之后重新上传！<br/>");
			}
			if(type.equals("courseTime")){
				msg.append("批量导入课程上课次数失败，请修改以上错误之后重新上传！<br/>");
			}
			throw new EntityException(msg.toString());
		}
		
		for (PrTchOpencourse prTchOpencourse :prTchOpencourseSet) {
			try {
				this.getGeneralDao().save(prTchOpencourse);
			} catch (Exception e) {
				e.printStackTrace();
				if(type.equals("examNo")){
					throw new EntityException("批量导入课程建议考试场次失败");
				}
				if(type.equals("courseTime")){
					throw new EntityException("批量导入课程上课次数失败");
				}
				
			}
		}
		return count;
	}
}
