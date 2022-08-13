package com.whaty.platform.entity.service.imp.teaching.basicInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import jxl.Sheet;
import jxl.Workbook;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PeTchBook;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.basicInfo.PeTchBookService;
import com.whaty.platform.util.Const;

public class PeTchBookServiceImp implements PeTchBookService {

	private GeneralDao<PeTchBook> generalDao;
	
	private MyListDAO myListDao;
	
	public MyListDAO getMyListDao() {
		return myListDao;
	}

	public void setMyListDao(MyListDAO myListDao) {
		this.myListDao = myListDao;
	}

	public GeneralDao<PeTchBook> getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao<PeTchBook> generalDao) {
		this.generalDao = generalDao;
	}

	public int save_uploadBook(File file) throws EntityException {
		//用来存储操作过程中的信息
		StringBuffer msg = new StringBuffer();
		int count = 0;
		
		Workbook work = null;
		
		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e){
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量添加失败！<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();			//获取Excel表格的行数
		if(rows<2){
			msg.append("表格为空！<br/>");
			throw new EntityException(msg.toString());
		}
		
		//教材添加之后默认状态为有效（为避免多次查找数据库，先从enumconst表中查出有效状态）
		
//		DetachedCriteria dcEnumConst = DetachedCriteria.forClass(EnumConst.class);
//		dcEnumConst.add(Restrictions.eq("namespace", "flagIsValid"));
//		dcEnumConst.add(Restrictions.eq("code", "1"));
//		List<EnumConst> enumConstList = null;
//		EnumConst flag_isvalid = null;
//		try{
//			enumConstList = this.getGeneralDao().getList(dcEnumConst);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		if(enumConstList.size() > 0){
//			flag_isvalid = enumConstList.get(0);
//		}else{
//			throw new EntityException("在EnumConst表中不存在教材是否有效的的记录，请先设置之后，在上传教材信息<br/>");
//		}
		
		String temp = "";
		Set<PeTchBook> bookSet = new HashSet();
		Set<String> name = new HashSet<String>();
		//Excel表格是从第0行开始的，第0行为表格头部信息，所以从第一行开始读数据
		for(int i=1;i<rows;i++){
			try{
				
				PeTchBook instance = new PeTchBook();
				
				
				temp = sheet.getCell(0, i).getContents().trim();
				if(temp == null || "".equals(temp)){
					msg.append("第"+(i+1)+"行数据教材名称为空！<br/>");
					continue;
				}
				
				DetachedCriteria dcPeTchBook = DetachedCriteria.forClass(PeTchBook.class);
				dcPeTchBook.add(Restrictions.eq("name", temp));
				List<PeTchBook> bookList = new ArrayList();
				try{
					bookList = this.getGeneralDao().getList(dcPeTchBook);
				}catch(Exception e){
					e.printStackTrace();
				}
				if(bookList.size() > 0){
					msg.append("第"+(i+1)+"行数据，教材名称已存在！<br/>");
					continue;
				}
				if(!name.add(temp)) {
					msg.append("第"+(i+1)+"行数据，教材名称与文件中前面的数据重复！<br/>");
					continue;
				}
				instance.setName(temp);
				
				temp = sheet.getCell(1, i).getContents().trim();
				if(temp == null || "".equals(temp)){
					msg.append("第"+(i+1)+"行数据对应课程为空！<br/>");
					continue;
				}
				DetachedCriteria dcPeTchCourse = DetachedCriteria.forClass(PeTchCourse.class);
				dcPeTchCourse.add(Restrictions.eq("name", temp));
				List<PeTchCourse> courseList = null;
				try{
					courseList = this.getGeneralDao().getList(dcPeTchCourse);
				}catch(Exception e){
					e.printStackTrace();
				}
				if(courseList.size() < 1){
					msg.append("第"+(i+1)+"行对应课程不存在，请确认输入是否正确！<br/>");
					continue;
				}
				PeTchCourse course = courseList.get(0);
				
				instance.setPeTchCourse(course);
				
				temp = sheet.getCell(2, i).getContents().trim();
				if(temp == null || "".equals(temp)){
					msg.append("第"+(i+1)+"行数据ISBN号为空！<br/>");
					continue;
				}
				
				instance.setIsbn(temp);
				
				temp = sheet.getCell(3, i).getContents().trim();
				if(temp == null || "".equals(temp)){
					msg.append("第"+(i+1)+"行数据价格为空！<br/>");
					continue;
				}
				if (!temp.matches(Const.fee)) {
					msg.append("第"+(i+1)+"行数据添加失败！价格只能是1到8位整数 0到2位小数<br/>");
					continue;
				}
				double price = 0.0;
				try{
						price = Double.parseDouble(temp);
				}catch(NumberFormatException e){
						e.printStackTrace();
						msg.append("第"+(i+1)+"行数据添加失败！价格只能是1到8位整数 0到2位小数<br/>");
						continue;
				}
					
				instance.setPrice(price);	
				
				temp = sheet.getCell(4, i).getContents().trim();
				if(temp == null || "".equals(temp)){
					msg.append("第"+(i+1)+"行数据作者为空！<br/>");
					continue;
				}
					
				instance.setAuthor(temp);
				
				temp = sheet.getCell(5, i).getContents().trim();
				if(temp == null || "".equals(temp)){
					msg.append("第"+(i+1)+"行数据出版社为空！<br/>");
					continue;
				}
					
				instance.setPublisher(temp);
				
				temp = sheet.getCell(6, i).getContents().trim();
				if(temp == null || "".equals(temp)){
					msg.append("第"+(i+1)+"行数据是否在使用为空！<br/>");
					continue;
				}
				
				if (temp.equals("是")) {
					instance.setEnumConstByFlagIsvalid(this.myListDao.getEnumConstByNamespaceCode("FlagIsvalid", "1"));
				} else if (temp.equals("否")) {
					instance.setEnumConstByFlagIsvalid(this.myListDao.getEnumConstByNamespaceCode("FlagIsvalid", "0"));
				} else {
					msg.append("第"+(i+1)+"行数据添加失败！是否在使用只能写“是”或者“否”<br/>");
					continue;
				}
				
				instance.setNote(sheet.getCell(7, i).getContents().trim());
				
				
				if(!bookSet.add(instance)){
					msg.append("第"+(i+1)+"行数据与文件中前面的数据重复！<br/>");
					continue;
				}
				count++;
				
			}catch(Exception e){
				e.printStackTrace();
				msg.append("第"+(i+1)+"行数据添加失败！<br/>");
				continue;
			}
		}
		
		if(msg.length() > 0){
			msg.append("教材批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}
		
		for(PeTchBook book : bookSet){
			try{
				this.getGeneralDao().save(book);
			}catch(Exception e){
				e.printStackTrace();
				throw new EntityException("批量上传教材失败");
			}
		}
		
		return count;
	}
}
