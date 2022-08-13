package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeTchCoursegroup;
import com.whaty.platform.entity.bean.PeTchProgram;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.basicInfo.PeTchProgramService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class PeTeaPlanManagerAction extends MyBaseAction<PeTchProgram> {

	private static final long serialVersionUID = 3858309720010130693L;
	private PeTchProgramService peTchProgramService;
	private File upload;
	
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public PeTchProgramService getPeTchProgramService() {
		return peTchProgramService;
	}
	public void setPeTchProgramService(PeTchProgramService peTchProgramService) {
		this.peTchProgramService = peTchProgramService;
	}
	private String grade;
	
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String totransplant(){
		String id = this.getIds().substring(0,this.getIds().indexOf(","));
		try {
			this.setBean( this.getGeneralService().getById(id));
			
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "totransplant";
	}
	public String batch(){
		return "batch";
	}
	public String batchexe(){
		this.setTogo("back");
		int count = 0;
		try {
			count = this.getPeTchProgramService().saveTchProgram(this.getUpload());
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "msg";
		}
		this.setMsg("导入教学计划成功！共" + count + "门课程导入教学计划中！");
		return "msg";
	}
	
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("教学计划列表"));
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "name", true, true, true, "TextField", false, 25);
		this.getGridConfig().addColumn(this.getText("年级"), "peGrade.name", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("专业备注"), "enumConstByFlagMajorType.name");
		this.getGridConfig().addColumn(this.getText("毕业最低学分标准"), "graduateMinCredit", false, true, true,Const.credit_for_extjs);
		this.getGridConfig().addColumn(this.getText("获得学位最低平均分"), "degreeAvgScore", false, true, true,Const.credit_for_extjs);
		this.getGridConfig().addColumn(this.getText("获得学位最低论文分数"), "degreePaperScore", false, true, true,Const.credit_for_extjs);
		this.getGridConfig().addColumn(this.getText("选择论文课程的最小学分"), "paperMinCreditHour", false, true, true,Const.credit_for_extjs);
		this.getGridConfig().addColumn(this.getText("选择论文课程的最小学期"), "paperMinSemeser", false, true, true,"regex:new RegExp(/^\\d{1}?$/),regexText:'输入格式：1位整数',");
		this.getGridConfig().addColumn(this.getText("最大选课数"), "maxElective", false, true, true, "regex:new RegExp(/^\\d{1,2}?$/),regexText:'输入格式：1到2位整数',");
//		this.getGridConfig().addColumn(this.getText("最小年限"),"minSemester", false, true, true,"regex:new RegExp(/^\\d{1}?$/),regexText:'学分输入格式：1位整数',");
//		this.getGridConfig().addColumn(this.getText("最大年限"),"maxSemester", false, true, true,"regex:new RegExp(/^\\d{1}?$/),regexText:'学分输入格式：1位整数',");
		this.getGridConfig().addColumn(this.getText("最小年限"),"minSemester", false, true, true,Const.credit_for_extjs);
		this.getGridConfig().addColumn(this.getText("最大年限"),"maxSemester", false, true, true,Const.credit_for_extjs);
		this.getGridConfig().addColumn(this.getText("统考科目A"), "enumConstByFlagUniteA.name", true, true, true, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("统考科目B"), "enumConstByFlagUniteB.name", true, true, true, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("备注"), "note", false, true, true, "TextArea", true, 500);
		this.getGridConfig().addRenderFunction(this.getText("课程类型信息"), "<a href=\"teachPlanInfo.action?bean.peTchProgram.id=${value}\" target='_self'>查看类型</a>","id");
		this.getGridConfig().addRenderFunction(this.getText("所有课程信息"), "<a href=\"prTchProgramCourse.action?bean.peTchProgramGroup.peTchProgram.id=${value}\"  target='_self'>查看所有课程</a>","id");
		this.getGridConfig().addMenuFunction(this.getText("复制教学计划"), "/entity/teaching/peTeaPlan_totransplant.action", true, false);
	
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeTchProgram.class;
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/peTeaPlan";
		
	}
	
	public void setBean(PeTchProgram instance) {
		super.superSetBean(instance);
		
	}
	
	public PeTchProgram getBean(){
		return (PeTchProgram) super.superGetBean();
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchProgram.class);
		dc.createCriteria("peGrade","peGrade");
		dc.createCriteria("peEdutype","peEdutype");
		dc.createCriteria("peMajor","peMajor");
		dc.createAlias("enumConstByFlagMajorType", "enumConstByFlagMajorType", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByFlagUniteA", "enumConstByFlagUniteA", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByFlagUniteB", "enumConstByFlagUniteB", DetachedCriteria.LEFT_JOIN);
		return dc;
	}
	@Override
	public Map add() {
		
		this.superSetBean((PeTchProgram)this.setSubIds(this.getBean()));
		Map<String, String> map = new HashMap<String, String>();
		try {
			this.checkBeforeAdd();
			map =  peTchProgramService.save(this.getBean());
		} catch (EntityException e) {
			map.put("success", "false");
			map.put("info", e.getMessage());
			return map;
		} catch (RuntimeException re) {
//			Map<String, String> map = new HashMap<String, String>();
			map.put("success", "false");
			map.put("info", "教学计划已存在。");
			return map;
		}
		return map;
	}
//	@Override
//	自己写的service；
//	public Map delete() {
//		Map map = new HashMap();
//		if (this.getIds() != null && this.getIds().length() > 0) {
//			String str = this.getIds();
//			if (str != null && str.length() > 0) {
//				String[] ids = str.split(",");
//				List idList = new ArrayList();
//				for (int i = 0; i < ids.length; i++) {
//					idList.add(ids[i]);
//				}
//				map.put("success", "true");
//				map.put("info", "删除成功");
//				try{
//					this.peTchProgramService.del(idList);
//				}catch(Exception e){
//					map.clear();
//					map.put("success", "false");
//					map.put("info", "删除失败");
//				}
//
//			} else {
//				map.put("success", "false");
//				map.put("info", "请选择操作项");
//			}
//		}
//		return map;
//	}
//	不使用service，自己重写delete同时借助hibernate的级联删除。
//	@Override
//	public Map delete() {
//		Map map = new HashMap();
//		if (this.getIds() != null && this.getIds().length() > 0) {
//			String str = this.getIds();
//			if (str != null && str.length() > 0) {
//				String[] ids = str.split(",");
//				List idList = new ArrayList();
//				map.put("success", "true");
//				map.put("info", "删除成功");
//				for (int i = 0; i < ids.length; i++) {
//					try {
//						this.getGeneralService().delete(this.getGeneralService().getById(ids[i]));
//					} catch (Exception e) {
//						e.printStackTrace();
//						map.clear();
//						map.put("success", "false");
//						map.put("info", "删除失败");
//					}
//				}
//			} else {
//				map.put("success", "false");
//				map.put("info", "请选择操作项");
//			}
//		}
//		return map;
//	}
	
	public String plantExe() {
		String result = this.peTchProgramService.save_plant(this.getIds(), this.getMyListService().getIdByName("PeGrade", this.getGrade()));
		this.setMsg(result);
		if (result.equals("复制教学计划成功！")) {
			this.setTogo("/entity/teaching/peTeaPlan.action");
		} else {
			this.setTogo("back");
		}
		return "msg";
	}
	@Override
	public void checkBeforeAdd() throws EntityException {
		if (this.getBean().getMaxSemester() < this.getBean().getMinSemester()) {
			throw new EntityException("最小年限不能大于最大年限！");
		}
		if(this.getBean().getEnumConstByFlagUniteA()!=null&&this.getBean().getEnumConstByFlagUniteB()!=null){
			if(this.getBean().getEnumConstByFlagUniteA().getName().equals(this.getBean().getEnumConstByFlagUniteB().getName())){
				throw new EntityException("不能设置相同的两个统考科目！");
			}
		}
	}
	@Override
	public void checkBeforeUpdate() throws EntityException {
		this.checkBeforeAdd();
	}
	
}
