package com.whaty.platform.entity.service.imp.teaching.basicInfo;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeGrade;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PeTchCoursegroup;
import com.whaty.platform.entity.bean.PeTchProgram;
import com.whaty.platform.entity.bean.PeTchProgramGroup;
import com.whaty.platform.entity.bean.PrTchProgramCourse;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.basicInfo.PeTchProgramService;
import com.whaty.platform.util.Const;

public class PeTchProgramServiceImp implements PeTchProgramService {
	private GeneralDao generalDao;
	
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public Map<String, String> save(PeTchProgram instance) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			instance = (PeTchProgram) this.getGeneralDao().save(instance);
			map.put("success", "true");
			map.put("info", "添加成功");
			
			DetachedCriteria dcPeTchCoursegroup = DetachedCriteria.forClass(PeTchCoursegroup.class);
			List<PeTchCoursegroup> coursegroupList = null;
			try{
				coursegroupList = this.getGeneralDao().getList(dcPeTchCoursegroup);
			}catch(Exception e){
				e.printStackTrace();
			}
			for(PeTchCoursegroup temp : coursegroupList){
				PeTchProgramGroup a = new PeTchProgramGroup();
				a.setPeTchCoursegroup(temp);
				a.setPeTchProgram(instance);
				try {
					this.getGeneralDao().save(a);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			map.clear();
			map.put("success", "false");
			map.put("info", "添加失败");
			
		}

		return map;
	}

	public Map<String, String> del(List<String> idList) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			map.put("success", "true");
			map.put("info", "添加成功");
			
			for (Iterator iter = idList.iterator(); iter.hasNext();) {
				String id = (String) iter.next();
				
				DetachedCriteria dcPeTchProgramGroup = DetachedCriteria.forClass(PeTchProgramGroup.class);
				dcPeTchProgramGroup.add(Restrictions.eq("peTchProgram.id", id));
				List peTchProgramGroupList =  this.getGeneralDao().getList(dcPeTchProgramGroup);
				for (Iterator iterator = peTchProgramGroupList.iterator(); iterator.hasNext();) {
					PeTchProgramGroup peTchProgramGroup = (PeTchProgramGroup) iterator.next();
					
					DetachedCriteria dcPrTchProgramCourse = DetachedCriteria.forClass(PrTchProgramCourse.class);
					dcPrTchProgramCourse.add(Restrictions.eq("peTchProgramGroup.id", peTchProgramGroup.getId()));
					List prTchProgramCourseList = this.getGeneralDao().getList(dcPrTchProgramCourse);
					for (Iterator iterator1 = prTchProgramCourseList.iterator(); iterator1.hasNext();) {
						PrTchProgramCourse prTchProgramCourse = (PrTchProgramCourse) iterator1.next();
						this.getGeneralDao().delete(prTchProgramCourse);
					}
					this.getGeneralDao().delete(peTchProgramGroup);
				}
				this.getGeneralDao().delete(this.getGeneralDao().getById(id));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			map.clear();
			map.put("success", "false");
			map.put("info", "删除失败");
		}
		
		return map;
	}

	/**
	 * 复制教学计划
	 */
	public String save_plant(String programId, String gradeId) {
		try {
			PeTchProgram fromProgram = (PeTchProgram) this.getGeneralDao().getById(programId);
			PeTchProgram toProgram = new PeTchProgram();
			DetachedCriteria dcPeGrade = DetachedCriteria.forClass(PeGrade.class);
			dcPeGrade.add(Restrictions.eq("id", gradeId));
			PeGrade peGrade = (PeGrade) this.getGeneralDao().getList(dcPeGrade).get(0);
			DetachedCriteria dcProgram = DetachedCriteria.forClass(PeTchProgram.class);
			dcProgram.add(Restrictions.eq("peEdutype", fromProgram.getPeEdutype()));
			dcProgram.add(Restrictions.eq("peMajor", fromProgram.getPeMajor()));
			List programList = this.getGeneralDao().getList(dcProgram);
			for (Iterator iter = programList.iterator(); iter.hasNext();) {
				PeTchProgram program = (PeTchProgram) iter.next();
				if (program.getPeGrade().getId().equals(gradeId)) {
					return "您选择的年级存在教学计划！";
				}
			}
			toProgram.setDegreeAvgScore(fromProgram.getDegreeAvgScore());
			toProgram.setDegreePaperScore(fromProgram.getDegreePaperScore());
			toProgram.setEnumConstByFlagMajorType(fromProgram.getEnumConstByFlagMajorType());
			toProgram.setEnumConstByFlagDegreeCandisobey(fromProgram.getEnumConstByFlagDegreeCandisobey());
			toProgram.setGraduateMinCredit(fromProgram.getGraduateMinCredit());
			toProgram.setMaxElective(fromProgram.getMaxElective());
			toProgram.setName(peGrade.getName() + fromProgram.getPeEdutype().getName() + fromProgram.getPeMajor().getName());
			toProgram.setPaperMinCreditHour(fromProgram.getPaperMinCreditHour());
			toProgram.setPaperMinSemeser(fromProgram.getPaperMinSemeser());
			toProgram.setMaxSemester(fromProgram.getMaxSemester());
			toProgram.setMinSemester(fromProgram.getMinSemester());
			toProgram.setPeEdutype(fromProgram.getPeEdutype());
			toProgram.setPeGrade(peGrade);
			toProgram.setPeMajor(fromProgram.getPeMajor());
			this.getGeneralDao().save(toProgram);
			
			DetachedCriteria dcPeTchProgramGroup = DetachedCriteria.forClass(PeTchProgramGroup.class);
			dcPeTchProgramGroup.add(Restrictions.eq("peTchProgram.id", fromProgram.getId()));
			List<PeTchProgramGroup> coursegroupList = null;
			try{
				coursegroupList = this.getGeneralDao().getList(dcPeTchProgramGroup);
			}catch(Exception e){
				e.printStackTrace();
			}
			for(PeTchProgramGroup fromPeTchProgramGroup : coursegroupList){
				PeTchProgramGroup toPeTchProgramGroup = new PeTchProgramGroup();
				toPeTchProgramGroup.setPeTchCoursegroup(fromPeTchProgramGroup.getPeTchCoursegroup());
				toPeTchProgramGroup.setMaxCredit(fromPeTchProgramGroup.getMaxCredit());
				toPeTchProgramGroup.setMinCredit(fromPeTchProgramGroup.getMinCredit());
				toPeTchProgramGroup.setPeTchProgram(toProgram);
				try {
					this.getGeneralDao().save(toPeTchProgramGroup);
					DetachedCriteria dcFromPrTchProgramCourse = DetachedCriteria.forClass(PrTchProgramCourse.class);
					dcFromPrTchProgramCourse.add(Restrictions.eq("peTchProgramGroup.id", fromPeTchProgramGroup.getId()));
					List fromPrTchProgramCourseList = this.getGeneralDao().getList(dcFromPrTchProgramCourse);
					for (Iterator iter = fromPrTchProgramCourseList.iterator(); iter
							.hasNext();) {
						PrTchProgramCourse fromPrTchProgramCourse = (PrTchProgramCourse) iter.next();
						PrTchProgramCourse toPrTchProgramCourse = new PrTchProgramCourse();
						toPrTchProgramCourse.setCredit(fromPrTchProgramCourse.getCredit());
						toPrTchProgramCourse.setEnumConstByFlagIsMainCourse(fromPrTchProgramCourse.getEnumConstByFlagIsMainCourse());
						toPrTchProgramCourse.setPeTchCourse(fromPrTchProgramCourse.getPeTchCourse());
						toPrTchProgramCourse.setPeTchProgramGroup(toPeTchProgramGroup);
						toPrTchProgramCourse.setUnit(fromPrTchProgramCourse.getUnit());
						this.getGeneralDao().save(toPrTchProgramCourse);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					return "复制教学计划失败！";
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return "复制教学计划失败！";
		}
		return "复制教学计划成功！";
	}
	/**
	 * 导入教学计划
	 * @param file
	 * @return
	 * @throws EntityException
	 */
	public int saveTchProgram(File file) throws EntityException{
		StringBuffer msg = new StringBuffer();
		int count = 0;

		Workbook work = null;

		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量添加失败！<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		
		//校验模板是否正确
		if(sheet.getCell(0, 0).getContents().equals("教学计划导入模板（请不要修改单元格位置）")
				&&sheet.getCell(0, 1).getContents().equals("*教学计划名称")
				&&sheet.getCell(0, 2).getContents().equals("*专业")
				&&sheet.getCell(0, 3).getContents().equals("*层次")
				&&sheet.getCell(0, 4).getContents().equals("*年级")
				&&sheet.getCell(0, 6).getContents().equals("*毕业最低学分标准")
				&&sheet.getCell(0, 7).getContents().equals("*获得学位最低平均分")
				&&sheet.getCell(0, 8).getContents().equals("*获得学位最低论文分数")
				&&sheet.getCell(0, 21).getContents().equals("*课程分组")
				&&sheet.getCell(4, 21).getContents().equals("是否为主干课（是/[否]）")){
			
		} else {
			throw new EntityException("Excel模板错误！批量添加失败！<br/>");
		}
		int rows = sheet.getRows();
		if (rows < 23) {
			msg.append("表格填写不完整！<br/>");
			throw new EntityException(msg.toString());
		}
		PeTchProgram peTchProgram = new PeTchProgram();
		int i = 0; //行数
		String temp = "";
		
		//*教学计划名称
		i++;
		temp = sheet.getCell(1, i).getContents().trim();
		if (temp == null || "".equals(temp)) {
			msg.append("第" + (i + 1) + "行数据，教学计划名称不能为空！<br/>");
		} else {
			DetachedCriteria dcProgram = DetachedCriteria.forClass(PeTchProgram.class);
			dcProgram.add(Restrictions.eq("name", temp));
			List<PeTchProgram> list = this.getGeneralDao().getList(dcProgram);
			if (list==null ||list.isEmpty()){
				peTchProgram.setName(temp);
			} else {
				msg.append("第" + (i + 1) + "行数据，教学计划名称已经存在！<br/>");
			}
		}
		
		//*专业
		PeMajor peMajor = null;
		i++;
		temp = sheet.getCell(1, i).getContents().trim();
		if (temp == null || "".equals(temp)) {
			msg.append("第" + (i + 1) + "行数据，专业不能为空！<br/>");
		} else {
			DetachedCriteria dcMajor = DetachedCriteria.forClass(PeMajor.class);
			dcMajor.add(Restrictions.eq("name", temp));
			List<PeMajor> list = this.getGeneralDao().getList(dcMajor);
			if (list==null ||list.isEmpty()){
				msg.append("第" + (i + 1) + "行数据，专业不存在！<br/>");
			} else {
				peMajor = list.get(0);
				peTchProgram.setPeMajor(peMajor);
			}
		}
		
		//*专业备注
		EnumConst majorType = null;
		temp = sheet.getCell(4, i).getContents().trim();
		if (temp == null || "".equals(temp)) {
			msg.append("第" + (i + 1) + "行数据，专业备注不能为空！<br/>");
		} else {
			DetachedCriteria dcType = DetachedCriteria.forClass(EnumConst.class);
			dcType.add(Restrictions.eq("name", temp));
			dcType.add(Restrictions.eq("namespace", "FlagMajorType"));
			List<EnumConst> list = this.getGeneralDao().getList(dcType);
			if (list==null ||list.isEmpty()){
				msg.append("第" + (i + 1) + "行数据，专业备注不存在！<br/>");
			} else {
				majorType = list.get(0);
				peTchProgram.setEnumConstByFlagMajorType(majorType);
			}
		}
		
		//*层次
		PeEdutype peEdutype = null;
		i++;
		temp = sheet.getCell(1, i).getContents().trim();
		if (temp == null || "".equals(temp)) {
			msg.append("第" + (i + 1) + "行数据，层次不能为空！<br/>");
		} else {
			DetachedCriteria dcEdutype = DetachedCriteria.forClass(PeEdutype.class);
			dcEdutype.add(Restrictions.eq("name", temp));
			List<PeEdutype> list = this.getGeneralDao().getList(dcEdutype);
			if (list==null ||list.isEmpty()){
				msg.append("第" + (i + 1) + "行数据，层次不存在！<br/>");
			} else {
				peEdutype = list.get(0);
				peTchProgram.setPeEdutype(peEdutype);
			}
		}
		
		//*统考科目A
		EnumConst tongkaoA = null;
		temp = sheet.getCell(4, i).getContents().trim();
		if (temp == null || "".equals(temp)) {
		} else {
			DetachedCriteria dcType = DetachedCriteria.forClass(EnumConst.class);
			dcType.add(Restrictions.eq("name", temp));
			dcType.add(Restrictions.eq("namespace", "FlagUniteA"));
			List<EnumConst> list = this.getGeneralDao().getList(dcType);
			if (list==null ||list.isEmpty()){
				msg.append("第" + (i + 1) + "行数据，统考科目A不存在！<br/>");
			} else {
				tongkaoA = list.get(0);
				peTchProgram.setEnumConstByFlagUniteA(tongkaoA);
			}
		}
		
		//*年级
		PeGrade peGrade = null;
		i++;
		temp = sheet.getCell(1, i).getContents().trim();
		if (temp == null || "".equals(temp)) {
			msg.append("第" + (i + 1) + "行数据，年级不能为空！<br/>");
		} else {
			DetachedCriteria dcGrade = DetachedCriteria.forClass(PeGrade.class);
			dcGrade.add(Restrictions.eq("name", temp));
			List<PeGrade> list = this.getGeneralDao().getList(dcGrade);
			if (list==null ||list.isEmpty()){
				msg.append("第" + (i + 1) + "行数据，年级不存在！<br/>");
			} else {
				peGrade = list.get(0);
				peTchProgram.setPeGrade(peGrade);
			}
		}
		
		//*统考科目B
		EnumConst tongkaoB = null;
		temp = sheet.getCell(4, i).getContents().trim();
		if (temp == null || "".equals(temp)) {
		} else {
			DetachedCriteria dcType = DetachedCriteria.forClass(EnumConst.class);
			dcType.add(Restrictions.eq("name", temp));
			dcType.add(Restrictions.eq("namespace", "FlagUniteB"));
			List<EnumConst> list = this.getGeneralDao().getList(dcType);
			if (list==null ||list.isEmpty()){
				msg.append("第" + (i + 1) + "行数据，统考科目B不存在！<br/>");
			} else {
				tongkaoB = list.get(0);
				peTchProgram.setEnumConstByFlagUniteB(tongkaoB);
			}
		}
		
		//*毕业最低学分标准
		i = i+2;
		temp = sheet.getCell(1, i).getContents().trim();
		if (temp == null || "".equals(temp)) {
			msg.append("第" + (i + 1) + "行数据，毕业最低学分标准不能为空！<br/>");
		} else {
			if (temp.matches(Const.scoreLine)){
				try {
					peTchProgram.setGraduateMinCredit(Long.parseLong(temp));
				} catch (Exception e) {
					e.printStackTrace();
					msg.append("第" + (i + 1) + "行数据，毕业最低学分标准格式错误<br/>");
				}
			} else {
			
				msg.append("第" + (i + 1) + "行数据，毕业最低学分标准"+Const.scoreLineMessage+"<br/>");
			}
		}
		
		//*获得学位最低平均分
		i++;
		temp = sheet.getCell(1, i).getContents().trim();
		if (temp == null || "".equals(temp)) {
			msg.append("第" + (i + 1) + "行数据，获得学位最低平均分不能为空！<br/>");
		} else {
			if (temp.matches(Const.score)){
				try {
					peTchProgram.setDegreeAvgScore(Double.parseDouble(temp));
				} catch (Exception e) {
					e.printStackTrace();
					msg.append("第" + (i + 1) + "行数据，获得学位最低平均分格式错误！<br/>");
				}
			} else {
				msg.append("第" + (i + 1) + "行数据，获得学位最低平均分"+Const.scoreMessage+"<br/>");
			}
		}
		
		//*获得学位最低论文分数
		i++;
		temp = sheet.getCell(1, i).getContents().trim();
		if (temp == null || "".equals(temp)) {
			msg.append("第" + (i + 1) + "行数据，获得学位最低论文分数不能为空！<br/>");
		} else {
			if (temp.matches(Const.score)){
				try {
					peTchProgram.setDegreePaperScore(Double.parseDouble(temp));
				} catch (Exception e) {
					e.printStackTrace();
					msg.append("第" + (i + 1) + "行数据，获得学位最低论文分数格式错误！<br/>");
				}
			} else {
				msg.append("第" + (i + 1) + "行数据，获得学位最低论文分数"+Const.scoreMessage+"<br/>");
			}
		}
		
		//*选择论文课程的最小学分
		i++;
		temp = sheet.getCell(1, i).getContents().trim();
		if (temp == null || "".equals(temp)) {
			msg.append("第" + (i + 1) + "行数据，选择论文课程的最小学分不能为空！<br/>");
		} else {
			if (temp.matches(Const.scoreLine)){
				try {
					peTchProgram.setPaperMinCreditHour(Long.parseLong(temp));
				} catch (Exception e) {
					e.printStackTrace();
					msg.append("第" + (i + 1) + "行数据，选择论文课程的最小学分格式错误！<br/>");
				}
			} else {
				msg.append("第" + (i + 1) + "行数据，选择论文课程的最小学分"+Const.scoreLineMessage+"<br/>");
			}
		}
		
		//*选择论文课程的最小学期
		i++;
		temp = sheet.getCell(1, i).getContents().trim();
		if (temp == null || "".equals(temp)) {
			msg.append("第" + (i + 1) + "行数据，选择论文课程的最小学期不能为空！<br/>");
		} else {
			if (temp.matches(Const.scoreLine)){
				try {
					peTchProgram.setPaperMinSemeser(Long.parseLong(temp));
				} catch (Exception e) {
					e.printStackTrace();
					msg.append("第" + (i + 1) + "行数据，选择论文课程的最小学期格式错误！<br/>");
				}
			} else {
				msg.append("第" + (i + 1) + "行数据，选择论文课程的最小学期"+Const.scoreLineMessage+"<br/>");
			}
		}
		
		//*每学期最大选课数
		i++;
		temp = sheet.getCell(1, i).getContents().trim();
		if (temp == null || "".equals(temp)) {
			msg.append("第" + (i + 1) + "行数据，每学期最大选课数不能为空！<br/>");
		} else {
			if (temp.matches(Const.scoreLine)){
				try {
					peTchProgram.setMaxElective(Long.parseLong(temp));
				} catch (Exception e) {
					e.printStackTrace();
					msg.append("第" + (i + 1) + "行数据，每学期最大选课数格式错误！<br/>");
				}
			} else {
				msg.append("第" + (i + 1) + "行数据，每学期最大选课数"+Const.scoreLineMessage+"<br/>");
			}
		}
		
		//*最小学习年限
		i++;
		temp = sheet.getCell(1, i).getContents().trim();
		if (temp == null || "".equals(temp)) {
			msg.append("第" + (i + 1) + "行数据，最小学习年限不能为空！<br/>");
		} else {
			if (temp.matches(Const.score)){
				try {
					peTchProgram.setMinSemester(Double.parseDouble(temp));
				} catch (Exception e) {
					e.printStackTrace();
					msg.append("第" + (i + 1) + "行数据，最小学习年限格式错误！<br/>");
				}
			} else {
				msg.append("第" + (i + 1) + "行数据，最小学习年限"+Const.scoreMessage+"<br/>");
			}
		}
		
		//*最大学习年限
		i++;
		temp = sheet.getCell(1, i).getContents().trim();
		if (temp == null || "".equals(temp)) {
			msg.append("第" + (i + 1) + "行数据，最大学习年限不能为空！<br/>");
		} else {
			if (temp.matches(Const.score)){
				try {
					peTchProgram.setMaxSemester(Double.parseDouble(temp));
				} catch (Exception e) {
					e.printStackTrace();
					msg.append("第" + (i + 1) + "行数据，最大学习年限格式错误！<br/>");
				}
			} else {
				msg.append("第" + (i + 1) + "行数据，最大学习年限"+Const.scoreMessage+"<br/>");
			}
		}
		
		if(msg.length()>0){
			msg.append("教学计划上传失败，请修改完以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}
		DetachedCriteria dcPeTchProgram = DetachedCriteria.forClass(PeTchProgram.class);
		dcPeTchProgram.add(Restrictions.eq("peGrade", peGrade));
		dcPeTchProgram.add(Restrictions.eq("peMajor", peMajor));		
		dcPeTchProgram.add(Restrictions.eq("peEdutype", peEdutype));	
		dcPeTchProgram.add(Restrictions.eq("enumConstByFlagMajorType", majorType));
		List list = this.getGeneralDao().getList(dcPeTchProgram);
		if (list!=null&&!list.isEmpty()){
			throw new EntityException(peGrade.getName()+peEdutype.getName()+peMajor.getName()+"教学计划已经存在！");
		}
		peTchProgram = (PeTchProgram)this.getGeneralDao().save(peTchProgram);
		
		double zybx = 0.0;//专业必修学分
		double zyxx = 0.0;//专业选修最高学分
		double ggbx = 0.0;//公共必修学分
		double ggxx = 0.0;//公共必修最高学分
		
		PeTchProgramGroup zybxGroup = new PeTchProgramGroup();//专业必修
		PeTchProgramGroup zyxxGroup = new PeTchProgramGroup();//专业选修
		PeTchProgramGroup ggbxGroup = new PeTchProgramGroup();//共必修
		PeTchProgramGroup ggxxGroup = new PeTchProgramGroup();//公共必修
		
		this.getGeneralDao().setEntityClass(PeTchProgramGroup.class);
		zybxGroup.setPeTchProgram(peTchProgram);
		DetachedCriteria dczybx = DetachedCriteria.forClass(PeTchCoursegroup.class);
		dczybx.add(Restrictions.eq("name", "专业必修课"));
		List<PeTchCoursegroup> zybxList = this.getGeneralDao().getList(dczybx);
		if(zybxList!=null&&zybxList.size()>0){
		zybxGroup.setPeTchCoursegroup(zybxList.get(0));
		}
		zybxGroup = (PeTchProgramGroup)this.getGeneralDao().save(zybxGroup);
		
		zyxxGroup.setPeTchProgram(peTchProgram);
		DetachedCriteria dczyxx = DetachedCriteria.forClass(PeTchCoursegroup.class);
		dczyxx.add(Restrictions.eq("name", "专业选修课"));
		List<PeTchCoursegroup> zyxxList = this.getGeneralDao().getList(dczyxx);
		if(zyxxList!=null&&zyxxList.size()>0){
		zyxxGroup.setPeTchCoursegroup(zyxxList.get(0));
		}
		zyxxGroup = (PeTchProgramGroup)this.getGeneralDao().save(zyxxGroup);
		
		ggbxGroup.setPeTchProgram(peTchProgram);
		DetachedCriteria dcggbx = DetachedCriteria.forClass(PeTchCoursegroup.class);
		dcggbx.add(Restrictions.eq("name", "公共必修课"));
		List<PeTchCoursegroup> ggbxList = this.getGeneralDao().getList(dcggbx);
		if(ggbxList!=null&&ggbxList.size()>0){
		ggbxGroup.setPeTchCoursegroup(ggbxList.get(0));
		}
		ggbxGroup = (PeTchProgramGroup)this.getGeneralDao().save(ggbxGroup);
		
		ggxxGroup.setPeTchProgram(peTchProgram);
		DetachedCriteria dcggxx = DetachedCriteria.forClass(PeTchCoursegroup.class);
		dcggxx.add(Restrictions.eq("name", "公共选修课"));
		List<PeTchCoursegroup> ggxxList = this.getGeneralDao().getList(dcggxx);
		if(ggxxList!=null&&ggxxList.size()>0){
		ggxxGroup.setPeTchCoursegroup(ggxxList.get(0));
		}
		ggxxGroup = (PeTchProgramGroup)this.getGeneralDao().save(ggxxGroup);

		Set<PrTchProgramCourse> programCourseSet = new HashSet();
		Set<String> courseName = new HashSet();
		for (i = 22; i < rows; i++) {
			try {
				temp = sheet.getCell(0, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，课程分组不能为空！<br/>");
					continue;
				}
				
				PrTchProgramCourse prTchProgramCourse = new PrTchProgramCourse();
				
				if (temp.equals("专业必修课")){
					//*课程名称
					temp = sheet.getCell(1, i).getContents().trim();
					if (temp == null || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，课程名称不能为空！<br/>");
						continue;
					}
					if(!courseName.add(temp)){
						msg.append("第" + (i + 1) + "行数据，课程名称与文件中前面的数据重复！<br/>");
						continue;
					}
					DetachedCriteria dcCourse = DetachedCriteria.forClass(PeTchCourse.class);
					dcCourse.add(Restrictions.eq("name", temp));
					List<PeTchCourse> courseList = this.getGeneralDao().getList(dcCourse);
					if (courseList==null||courseList.isEmpty()){
						msg.append("第" + (i + 1) + "行数据，课程不存在！<br/>");
						continue;	
					}
					prTchProgramCourse.setPeTchCourse(courseList.get(0));
					
					//*学分(数字)
					temp = sheet.getCell(2, i).getContents().trim();
					if (temp == null || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，课程学分不能为空！<br/>");
						continue;
					}
					
					if (temp.matches(Const.score)){
						double credit = 0;
						try {
							credit=Double.parseDouble(temp);
						} catch (Exception e) {
							e.printStackTrace();
							msg.append("第" + (i + 1) + "行数据，课程学分格式错误！<br/>");
						}
						zybx += credit;
						prTchProgramCourse.setCredit(credit);
					} else {
						msg.append("第" + (i + 1) + "行数据，课程学分"+Const.scoreMessage+"<br/>");
					}
					
					//*建议学期(1/2/3/4/5)
					temp = sheet.getCell(3, i).getContents().trim();
					if (temp == null || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，建议学期不能为空！<br/>");
						continue;
					}

					if(temp.matches("^\\d{1}$")){
						prTchProgramCourse.setUnit(Long.parseLong(temp));
					} else {
						msg.append("第" + (i + 1) + "行数据，建议学期错误，只能填写1位数字！<br/>");
						continue;
					}
					
					//是否为主干课（是/[否]）
					temp = sheet.getCell(4, i).getContents().trim();
					if (temp != null && !"".equals(temp)) {
						if(!temp.equals("是")){
							msg.append("第" + (i + 1) + "行数据，是否为主干课填写错误。如果是主干课请填写“是”，否则不用填写！<br/>");
							continue;
						}
						prTchProgramCourse.setEnumConstByFlagIsMainCourse(
								this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsMainCourse", "1"));
					} else {
						prTchProgramCourse.setEnumConstByFlagIsMainCourse(
								this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsMainCourse", "0"));
					}
					
					prTchProgramCourse.setPeTchProgramGroup(zybxGroup);
					if(!programCourseSet.add(prTchProgramCourse)){
						msg.append("第" + (i + 1) + "行数据与文件中前面的数据重复！<br/>");
						continue;
					}
					count++;
				}else if (temp.equals("专业选修课")) {
					//*课程名称
					temp = sheet.getCell(1, i).getContents().trim();
					if (temp == null || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，课程名称不能为空！<br/>");
						continue;
					}
					if(!courseName.add(temp)){
						msg.append("第" + (i + 1) + "行数据，课程名称与文件中前面的数据重复！<br/>");
						continue;
					}
					DetachedCriteria dcCourse = DetachedCriteria.forClass(PeTchCourse.class);
					dcCourse.add(Restrictions.eq("name", temp));
					List<PeTchCourse> courseList = this.getGeneralDao().getList(dcCourse);
					if (courseList==null||courseList.isEmpty()){
						msg.append("第" + (i + 1) + "行数据，课程不存在！<br/>");
						continue;	
					}
					prTchProgramCourse.setPeTchCourse(courseList.get(0));
					
					//*学分(数字)
					temp = sheet.getCell(2, i).getContents().trim();
					if (temp == null || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，课程学分不能为空！<br/>");
						continue;
					}
					
					if (temp.matches(Const.score)){
						double credit = 0;
						try {
							credit=Double.parseDouble(temp);
						} catch (Exception e) {
							e.printStackTrace();
							msg.append("第" + (i + 1) + "行数据，课程学分格式错误！<br/>");
						}
						zyxx += credit;
						prTchProgramCourse.setCredit(credit);
					} else {
						msg.append("第" + (i + 1) + "行数据，课程学分"+Const.scoreMessage+"<br/>");
					}
					
					//*建议学期(1/2/3/4/5)
					temp = sheet.getCell(3, i).getContents().trim();
					if (temp == null || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，建议学期不能为空！<br/>");
						continue;
					}
					if(temp.matches("^\\d{1}$")){
						prTchProgramCourse.setUnit(Long.parseLong(temp));
					} else {
						msg.append("第" + (i + 1) + "行数据，建议学期错误，只能填写1位数字！<br/>");
						continue;
					}
					
					//是否为主干课（是/[否]）
					temp = sheet.getCell(4, i).getContents().trim();
					if (temp != null && !"".equals(temp)) {
						if(!temp.equals("是")){
							msg.append("第" + (i + 1) + "行数据，是否为主干课填写错误。如果是主干课请填写“是”，否则不用填写！<br/>");
							continue;
						}
						prTchProgramCourse.setEnumConstByFlagIsMainCourse(
								this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsMainCourse", "1"));
					} else {
						prTchProgramCourse.setEnumConstByFlagIsMainCourse(
								this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsMainCourse", "0"));
					}
					
					prTchProgramCourse.setPeTchProgramGroup(zyxxGroup);
					if(!programCourseSet.add(prTchProgramCourse)){
						msg.append("第" + (i + 1) + "行数据与文件中前面的数据重复！<br/>");
						continue;
					}
					count++;
				}else if (temp.equals("公共必修课")) {
					//*课程名称
					temp = sheet.getCell(1, i).getContents().trim();
					if (temp == null || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，课程名称不能为空！<br/>");
						continue;
					}
					if(!courseName.add(temp)){
						msg.append("第" + (i + 1) + "行数据，课程名称与文件中前面的数据重复！<br/>");
						continue;
					}
					DetachedCriteria dcCourse = DetachedCriteria.forClass(PeTchCourse.class);
					dcCourse.add(Restrictions.eq("name", temp));
					List<PeTchCourse> courseList = this.getGeneralDao().getList(dcCourse);
					if (courseList==null||courseList.isEmpty()){
						msg.append("第" + (i + 1) + "行数据，课程不存在！<br/>");
						continue;	
					}
					prTchProgramCourse.setPeTchCourse(courseList.get(0));
					
					//*学分(数字)
					temp = sheet.getCell(2, i).getContents().trim();
					if (temp == null || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，课程学分不能为空！<br/>");
						continue;
					}
					
					if (temp.matches(Const.score)){
						double credit = 0;
						try {
							credit=Double.parseDouble(temp);
						} catch (Exception e) {
							e.printStackTrace();
							msg.append("第" + (i + 1) + "行数据，课程学分格式错误！<br/>");
						}
						ggbx += credit;
						prTchProgramCourse.setCredit(credit);
					} else {
						msg.append("第" + (i + 1) + "行数据，课程学分"+Const.scoreMessage+"<br/>");
					}
					
					//*建议学期(1/2/3/4/5)
					temp = sheet.getCell(3, i).getContents().trim();
					if (temp == null || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，建议学期不能为空！<br/>");
						continue;
					}
					if(temp.matches("^\\d{1}$")){
						prTchProgramCourse.setUnit(Long.parseLong(temp));
					} else {
						msg.append("第" + (i + 1) + "行数据，建议学期错误，只能填写1位数字！<br/>");
						continue;
					}
					
					//是否为主干课（是/[否]）
					temp = sheet.getCell(4, i).getContents().trim();
					if (temp != null && !"".equals(temp)) {
						if(!temp.equals("是")){
							msg.append("第" + (i + 1) + "行数据，是否为主干课填写错误。如果是主干课请填写“是”，否则不用填写！<br/>");
							continue;
						}
						prTchProgramCourse.setEnumConstByFlagIsMainCourse(
								this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsMainCourse", "1"));
					} else {
						prTchProgramCourse.setEnumConstByFlagIsMainCourse(
								this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsMainCourse", "0"));
					}
					
					prTchProgramCourse.setPeTchProgramGroup(ggbxGroup);
					if(!programCourseSet.add(prTchProgramCourse)){
						msg.append("第" + (i + 1) + "行数据与文件中前面的数据重复！<br/>");
						continue;
					}
					count++;
				}else if (temp.equals("公共选修课")) {
					//*课程名称
					temp = sheet.getCell(1, i).getContents().trim();
					if (temp == null || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，课程名称不能为空！<br/>");
						continue;
					}
					if(!courseName.add(temp)){
						msg.append("第" + (i + 1) + "行数据，课程名称与文件中前面的数据重复！<br/>");
						continue;
					}
					DetachedCriteria dcCourse = DetachedCriteria.forClass(PeTchCourse.class);
					dcCourse.add(Restrictions.eq("name", temp));
					List<PeTchCourse> courseList = this.getGeneralDao().getList(dcCourse);
					if (courseList==null||courseList.isEmpty()){
						msg.append("第" + (i + 1) + "行数据，课程不存在！<br/>");
						continue;	
					}
					prTchProgramCourse.setPeTchCourse(courseList.get(0));
					
					//*学分(数字)
					temp = sheet.getCell(2, i).getContents().trim();
					if (temp == null || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，课程学分不能为空！<br/>");
						continue;
					}
					
					if (temp.matches(Const.score)){
						double credit = 0;
						try {
							credit=Double.parseDouble(temp);
						} catch (Exception e) {
							e.printStackTrace();
							msg.append("第" + (i + 1) + "行数据，课程学分格式错误！<br/>");
						}
						ggxx += credit;
						prTchProgramCourse.setCredit(credit);
					} else {
						msg.append("第" + (i + 1) + "行数据，课程学分"+Const.scoreMessage+"<br/>");
					}
					
					//*建议学期(1/2/3/4/5)
					temp = sheet.getCell(3, i).getContents().trim();
					if (temp == null || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，建议学期不能为空！<br/>");
						continue;
					}
					if(temp.matches("^\\d{1}$")){
						prTchProgramCourse.setUnit(Long.parseLong(temp));
					} else {
						msg.append("第" + (i + 1) + "行数据，建议学期错误，只能填写1位数字！<br/>");
						continue;
					}
					
					//是否为主干课（是/[否]）
					temp = sheet.getCell(4, i).getContents().trim();
					if (temp != null && !"".equals(temp)) {
						if(!temp.equals("是")){
							msg.append("第" + (i + 1) + "行数据，是否为主干课填写错误。如果是主干课请填写“是”，否则不用填写！<br/>");
							continue;
						}
						prTchProgramCourse.setEnumConstByFlagIsMainCourse(
								this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsMainCourse", "1"));
					} else {
						prTchProgramCourse.setEnumConstByFlagIsMainCourse(
								this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsMainCourse", "0"));
					}
					
					prTchProgramCourse.setPeTchProgramGroup(ggxxGroup);
					if(!programCourseSet.add(prTchProgramCourse)){
						msg.append("第" + (i + 1) + "行数据与文件中前面的数据重复！<br/>");
						continue;
					}
					count++;
				} else {
					msg.append("第" + (i + 1) + "行数据，课程分组错误！<br/>");
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
				msg.append("第" + (i + 1) + "行数据添加失败！<br/>");
				continue;
			}
		}
		
		//专业选修课分组最低学分
		temp =  sheet.getCell(1, 18).getContents().trim();
		if (temp == null || "".equals(temp)) {
			msg.append("第19行数据，专业选修课分组最低学分不能为空！<br/>");
		}
		if (temp.matches(Const.scoreLine)){
			try {
				zyxxGroup.setMinCredit(Long.parseLong(temp));
			} catch (Exception e) {
				e.printStackTrace();
				msg.append("第19行数据，专业选修课分组最低学分格式错误！<br/>");
			}
		} else {
			msg.append("第19行数据，专业选修课分组最低学分"+Const.scoreLineMessage+"<br/>");
		}
		
		//公共选修课分组最高学分
		temp =  sheet.getCell(2, 19).getContents().trim();
		if (temp == null || "".equals(temp)) {
			msg.append("第20行数据，公共选修课分组最高学分不能为空！<br/>");
		}
		if (temp.matches(Const.scoreLine)){
			try {
				ggxxGroup.setMaxCredit(Long.parseLong(temp));
			} catch (Exception e) {
				e.printStackTrace();
				msg.append("第20行数据，公共选修课分组最高学分格式错误！<br/>");
			}
		} else {
			msg.append("第20行数据，公共选修课分组最高学分"+Const.scoreLineMessage+"<br/>");
		}
		
		if (msg.length() > 0) {
			msg.append("导入教学计划失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}

		
		long zybxL = (long)zybx;
		if (zybxL<zybx){
			throw new EntityException("课程分组内的课程总学分必须为整数，专业必修课 课程总学分为"+zybx+"，请修改课程学分使得总学分为整数。");
		}
		long zyxxL = (long)zyxx;
		if (zyxxL<zyxx){
			throw new EntityException("课程分组内的课程总学分必须为整数，专业选修课 课程总学分为"+zyxx+"，请修改课程学分使得总学分为整数。");
		}
		long ggbxL = (long)ggbx;
		if (ggbxL<ggbx){
			throw new EntityException("课程分组内的课程总学分必须为整数，专业必修课 课程总学分为"+ggbx+"，请修改课程学分使得总学分为整数。");
		}
		zybxGroup.setMaxCredit(zybxL);
		zybxGroup.setMinCredit(zybxL);
		this.getGeneralDao().save(zybxGroup);
		
		zyxxGroup.setMaxCredit(zyxxL);
		this.getGeneralDao().save(zyxxGroup);
		
		ggbxGroup.setMaxCredit(ggbxL);
		ggbxGroup.setMinCredit(ggbxL);
		this.getGeneralDao().save(ggbxGroup);
		
		ggxxGroup.setMinCredit(0L);
		this.getGeneralDao().save(ggxxGroup);
		
		for (PrTchProgramCourse prTchProgramCourse : programCourseSet) {
			try {
				this.getGeneralDao().save(prTchProgramCourse);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EntityException("导入教学计划失败");
			}
		}
		return count;

	}

}
