package com.whaty.platform.entity.service.imp.teaching.basicInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PeTchCourseware;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.basicInfo.PeTchCourseService;
import com.whaty.platform.util.Const;
import com.whaty.util.Exception.IdcardErrorException;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.AttributeManage;
import com.whaty.util.string.WhatyAttributeManage;

public class PeTchCourseServiceImp implements PeTchCourseService {

	private GeneralDao generalDao;
	
	private MyListDAO myListDao;
	
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public MyListDAO getMyListDao() {
		return myListDao;
	}



	public void setMyListDao(MyListDAO myListDao) {
		this.myListDao = myListDao;
	}

	public int save_uploadCourse(File file) throws EntityException {
		StringBuffer msg = new StringBuffer();
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
		
		String temp = "";
		Set<PeBzzTchCourse> courseSet = new HashSet();
		Set<String> name = new HashSet();
		Set<String> code = new HashSet();
		for(int i=1; i<rows; i++) {
			try {
				PeBzzTchCourse instance = new PeBzzTchCourse();
				
				temp = sheet.getCell(0, i).getContents().trim();
				if(!(temp == null || "".equals(temp))){
					instance.setSuqNum(Integer.parseInt(temp));
				}
				
				temp = sheet.getCell(1, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i+1) +"行数据，课程名为空！<br/>");
					continue;
				}
				else if(temp.length()>50)
				{
					msg.append("第" + (i+1) +"行数据，课程名长度不能超过50！<br/>");
					continue;
				}
				
				DetachedCriteria dcPeTchCourse = DetachedCriteria.forClass(PeBzzTchCourse.class);
				dcPeTchCourse.add(Restrictions.eq("name", temp));
				List<PeBzzTchCourse> courseList = new ArrayList();
				try {
					courseList = this.getGeneralDao().getList(dcPeTchCourse);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (courseList.size() > 0) {
					msg.append("第" + (i+1)+ "行数据，课程名已存在<br/>");
					continue;
				}
				instance.setName(temp);
				
				if (!name.add(temp)) {
					msg.append("第" + (i+1)+ "行数据，课程名与文件中前面的数据重复<br/>");
					continue;
				}
				
				temp = sheet.getCell(2, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第"+ (i+1)+ "行数据，课程代码不能为空！<br/>");
					continue;
				}
				if(temp.length()!=6){
				    msg.append("第"+ (i+1)+ "行数据，课程代码长度为6！<br/>");
					continue;
				}

				DetachedCriteria dcPeTchCriteria2 = DetachedCriteria.forClass(PeBzzTchCourse.class);
				dcPeTchCriteria2.add(Restrictions.eq("code", temp));
				List<PeBzzTchCourse> courseList1 = new ArrayList();
				try {
					courseList1 = this.getGeneralDao().getList(dcPeTchCriteria2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (courseList1.size() > 0) {
					msg.append("第" + (i+1)+ "行数据，课程代码已存在<br/>");
					continue;
				}	
				if (!code.add(temp)) {
					msg.append("第" + (i+1)+ "行数据，课程代码与文件中前面的数据重复<br/>");
					continue;
				}
				instance.setCode(temp);
				
				
				temp = sheet.getCell(3, i).getContents().trim();
				if(temp == null || "".equals(temp)) {
					msg.append("第" + (i+1) + "行数据，课程性质不能为空！<br/>");
					continue;
				}
				DetachedCriteria crit = DetachedCriteria.forClass(EnumConst.class);
				crit.add(Restrictions.eq("name", temp));
				List<EnumConst> elist = this.getGeneralDao().getList(crit);
				if(elist.size()<1){
					msg.append("第" + (i+1) + "行数据，课程性质信息不存在！<br/>");
					continue;
				}
				instance.setEnumConstByFlagCourseType(elist.get(0));

				temp = sheet.getCell(4, i).getContents().trim();
				if(!(temp == null || "".equals(temp))) {
					DetachedCriteria edc = DetachedCriteria.forClass(EnumConst.class);
					edc.add(Restrictions.eq("name", temp));
					List<EnumConst> enumConstlist = this.getGeneralDao().getList(edc);
					if(enumConstlist.size()<1){
						msg.append("第" + (i+1) + "行数据，课程类型信息不存在！<br/>");
						continue;
					}else{
						instance.setEnumConstByFlagCourseCategory(enumConstlist.get(0));
					}
				}
				
				temp = sheet.getCell(5, i).getContents().trim();
				if(temp.length()>2&&temp.length()<1){
					msg.append("第" + (i+1) + "行数据，学时长度最大不能超过2位！<br/>");
					continue;
				}
				instance.setTime( Long.parseLong(temp));
				
				
				temp = sheet.getCell(6, i).getContents().trim();
				if(temp == null || "".equals(temp)) {
					msg.append("第" + (i+1) + "行数据，是否有效不能为空！<br/>");
					continue;
				}
				if("是".equals(temp) || "否".equals(temp)) {
					if (temp.equals("是")) {
						instance.setEnumConstByFlagIsvalid(this.getMyListDao().getEnumConstByNamespaceCode("FlagIsvalid", "1"));
					} else {
						instance.setEnumConstByFlagIsvalid(this.getMyListDao().getEnumConstByNamespaceCode("FlagIsvalid", "0"));
					}
				} else {
					msg.append("第" + (i+1) + "行数据，是否有效只能填 ”是“ 或 ”否“！<br/>");
					continue;
				}
				
				temp = sheet.getCell(7, i).getContents().trim();
				/*if(temp == null || "".equals(temp)) {
					msg.append("第" + (i+1) + "行数据，教师不能为空！<br/>");
					continue;
				}*/
				if(temp!=null && !"".equals(temp)&& temp.length()>25)
				{
					msg.append("第" + (i+1) + "行数据，教师长度不能超过25！<br/>");
					continue;
				}
				instance.setTeacher(temp);
				
				temp = sheet.getCell(8, i).getContents().trim();
				/*if(temp == null || "".equals(temp)) {
					msg.append("第" + (i+1) + "行数据，教师简介不能为空！<br/>");
					continue;
				}*/
				if(temp!=null && !"".equals(temp)&& temp.length()>200)
				{
					msg.append("第" + (i+1) + "行数据，教师简介长度不能超过200！<br/>");
					continue;
				}
				instance.setTeacherNote(temp);
				
				temp = sheet.getCell(9, i).getContents().trim();
				/*if(temp == null || "".equals(temp)) {
					msg.append("第" + (i+1) + "行数据，课程简介不能为空！<br/>");
					continue;
				}*/
				if(temp!=null && !"".equals(temp)&& temp.length()>500)
				{
					msg.append("第" + (i+1) + "行数据，课程简介长度不能超过500！<br/>");
					continue;
				}
				instance.setNote(temp);
				
				if (!courseSet.add(instance)) {
					msg.append("第"+(i+1)+"行数据与文件中前面的数据重复！<br/>");
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
			msg.append("课程批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}
		
		for (PeBzzTchCourse course : courseSet) {
			try {
				this.getGeneralDao().save(course);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EntityException("批量上传课程失败");
			}
		}
		return count;
	}
	/**
	 * 批量添加课件
	 * @param file
	 * @return
	 * @throws EntityException
	 */
	public int save_uploadCourseware(File file) throws EntityException{
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
		
		String temp = "";
		Set<PeTchCourseware> peTchCoursewareSet = new HashSet();
		Set<String> name = new HashSet();
		Set<String> code = new HashSet();
		for(int i=1; i<rows; i++) {
			try {sheet.getCell(7, i).getContents().trim();
				if(sheet.getCell(0, i).getContents().trim().equals("")&&
						sheet.getCell(1, i).getContents().trim().equals("")&&
						sheet.getCell(2, i).getContents().trim().equals("")&&
						sheet.getCell(3, i).getContents().trim().equals("")&&
						sheet.getCell(4, i).getContents().trim().equals("")&&
						sheet.getCell(5, i).getContents().trim().equals("")&&
						sheet.getCell(6, i).getContents().trim().equals("")&&
						sheet.getCell(7, i).getContents().trim().equals("")){
					msg.append("第"+(i+1)+"行数据添加失败，没有输入任何数据！<br/>");
					continue;
				}
			} catch (Exception e1) {
				msg.append("第"+(i+1)+"行数据添加失败，模板错误！<br/>");
				continue;
			}
			try {
				StringBuffer lineMsg = new StringBuffer(); //保存每一行的错误信息
				PeTchCourseware peTchCourseware = new PeTchCourseware();
				//课件名
				temp = sheet.getCell(0, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					lineMsg.append("课件名不能为空！");
				} else {

					DetachedCriteria dcPeTchCourseware = DetachedCriteria
						.forClass(PeTchCourseware.class);
					dcPeTchCourseware.add(Restrictions.eq("name", temp));
					List bookList = this.getGeneralDao().getList(dcPeTchCourseware);
					if(bookList!=null&&bookList.size()>0){
						lineMsg.append("课件名已经存在！");
					}else{
						if(!name.add(temp)){
							lineMsg.append("课件名与文件中前面的数据重复！");
						}else {
							peTchCourseware.setName(temp);
						}
					}
				}
				//课件编号
				temp = sheet.getCell(1, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					lineMsg.append("课件编号不能为空！");
				} else {
					if(!temp.matches("^\\d{3}?$")){
						lineMsg.append("课件编号格式错误，应为3位整数！");
					} else{
						DetachedCriteria dcPeTchCourseware = DetachedCriteria
							.forClass(PeTchCourseware.class);
						dcPeTchCourseware.add(Restrictions.eq("code", temp));
						List bookList = this.getGeneralDao().getList(dcPeTchCourseware);
						if(bookList!=null&&bookList.size()>0){
							lineMsg.append("课件编号已经存在！");
						}else{
							if(!code.add(temp)){
								lineMsg.append("课件编号与文件中前面的数据重复！");
							}else {
								peTchCourseware.setCode(temp);
							}
						}
					}
				}
				
				
				temp = sheet.getCell(2, i).getContents().trim();
				peTchCourseware.setAuthor(temp);
				
				temp = sheet.getCell(3, i).getContents().trim();
				peTchCourseware.setAuthor(temp);
				
				//所属课程
				temp = sheet.getCell(4, i).getContents().trim();
				if(temp == null || "".equals(temp)) {
					lineMsg.append("所属课程不能为空！ ");
				}else{
					DetachedCriteria dcPeTchCourse = DetachedCriteria
						.forClass(PeTchCourse.class);
					dcPeTchCourse.add(Restrictions.eq("name", temp));
					List<PeTchCourse> list = this.getGeneralDao().getList(dcPeTchCourse);
					if(list!=null&&list.size()>0){
						peTchCourseware.setPeTchCourse(list.get(0));
						
					}else{
						lineMsg.append("所属课程不存在！ ");
					}
				}
				//作者
				temp = sheet.getCell(5, i).getContents().trim();
				peTchCourseware.setAuthor(temp);
				//出版商
				temp = sheet.getCell(6, i).getContents().trim();
				peTchCourseware.setPublisher(temp);
				//是否在使用（是，否）
				temp = sheet.getCell(7, i).getContents().trim();
				if(temp == null || "".equals(temp)) {
					lineMsg.append("是否在使用不能为空！ ");
				}else{
					if("是".equals(temp) || "否".equals(temp)) {
						if (temp.equals("是")) {
							peTchCourseware.setEnumConstByFlagIsvalid(this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsvalid", "1"));
						} else {
							peTchCourseware.setEnumConstByFlagIsvalid(this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsvalid", "0"));
						}
					} else {
						lineMsg.append("是否在使用只能填 “是” 或 “否”！ ");
					}
				}
				//备注
				temp = sheet.getCell(8, i).getContents().trim();
				peTchCourseware.setNote(temp);
				//课件地址
				temp = sheet.getCell(9, i).getContents().trim();
				if(temp == null || "".equals(temp)) {
					lineMsg.append("课件地址不能为空！ ");
				}else{
					peTchCourseware.setLink(temp);
				}
				
				
				if (!peTchCoursewareSet.add(peTchCourseware)) {
					lineMsg.append("本行数据与文件中前面的数据重复！ ");
				}
				if (lineMsg.length()>0){
					msg.append("第"+(i+1)+"行数据添加失败！"+lineMsg.toString()+"<br/>");
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
			msg.append("批量上传课件失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}
		
		for (PeTchCourseware peTchCourseware :peTchCoursewareSet) {
			try {
				this.getGeneralDao().save(peTchCourseware);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EntityException("批量上传课件失败");
			}
		}
		return count;
	}
}
