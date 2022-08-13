package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.basicInfo.PeTeacherService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
import com.whaty.util.Exception.IdcardErrorException;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.AttributeManage;
import com.whaty.util.string.WhatyAttributeManage;

public class PeTeacherManagerAction extends MyBaseAction<PeTeacher> {
	private File upload;
	private PeTeacherService peTeacherService;
	
	public PeTeacherService getPeTeacherService() {
		return peTeacherService;
	}

	public void setPeTeacherService(PeTeacherService peTeacherService) {
		this.peTeacherService = peTeacherService;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("教师管理"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("教师姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("用户名"), "loginId");
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("身份证号"), "idCard", true, true, true,  "TextField", false, 25);
		this.getGridConfig().addColumn(this.getText("所属专业"),"peMajor.name",true,true,true,"TextField",true,50);
		this.getGridConfig().addColumn(this.getText("最高学历"), "enumConstByFlagMaxXueli.name");
		this.getGridConfig().addColumn(this.getText("最高学位"), "enumConstByFlagMaxXuewei.name");
		this.getGridConfig().addColumn(this.getText("职称"), "enumConstByFlagZhicheng.name");
		this.getGridConfig().addColumn(this.getText("毕业院校"), "graduateSchool", true, true, false,  "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("毕业专业"), "graduateMajor", true, true, false,  "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("单位电话"), "phoneOffice",  false, true, true, Const.telephone_for_extjs);
		this.getGridConfig().addColumn(this.getText("移动电话"), "mobilephone", false, true, true, Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", false, true, true, Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("工作单位"), "workplace", true, true, true, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("是否带论文"), "enumConstByFlagPaper.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("最大论文学生数"), "stuCountLimit", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("是否有效"), "enumConstByFlagActive.name");
		this.getGridConfig().addColumn(this.getText("简介"), "note", true, true, false, "TextArea", true, 500);
		this.getGridConfig().addRenderFunction(this.getText("辅导课程"), "<a href=\"/entity/teaching/teacherCourse.action?bean.peTeacher.id=${value}\" target=\"_self\">辅导课程管理</a>", "id");
	
	}

	/**
	 * 批量上传学生平时成绩
	 * @return
	 */
	public String teacherBatch(){
		this.setTogo("back");
		int count = 0;
		try {
			count = this.getPeTeacherService().saveBatch(this.getUpload());
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "msg";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		return "msg";
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeTeacher.class;
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/teacher";
		
	}
	
	public void setBean(PeTeacher instance) {
		super.superSetBean(instance);
		
	}
	
	public PeTeacher getBean(){
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTeacher.class);
		dc.createCriteria("enumConstByFlagMaxXueli","enumConstByFlagMaxXueli");
		dc.createCriteria("enumConstByFlagMaxXuewei","enumConstByFlagMaxXuewei");
		dc.createCriteria("enumConstByFlagZhicheng","enumConstByFlagZhicheng");
		dc.createCriteria("enumConstByFlagActive","enumConstByFlagActive");
		dc.createCriteria("enumConstByFlagPaper","enumConstByFlagPaper");
		dc.createCriteria("enumConstByGender","enumConstByGender");
		dc.createCriteria("peMajor", "peMajor",DetachedCriteria.LEFT_JOIN);
		return dc;
	}

	@Override
	public void checkBeforeAdd() throws EntityException {
		if(this.getBean().getEnumConstByFlagPaper()==null){
			this.getBean().setEnumConstByFlagPaper(this.getMyListService().getEnumConstByNamespaceCode("FlagPaper", "0"));
		}
		AttributeManage manage=new WhatyAttributeManage();
		if(this.getBean().getIdCard() != null && !"".equals(this.getBean().getIdCard())){
			boolean isvalid=false;
			try {
				isvalid=manage.isValidIdcard(this.getBean().getIdCard());
			} catch (IdcardErrorException e) {
				e.printStackTrace();
			} catch (WhatyUtilException e) {
				e.printStackTrace();
			}
			if(!isvalid){
				throw new EntityException("身份证号无效");
			}
		}
	}

	@Override
	public void checkBeforeUpdate() throws EntityException {
		this.checkBeforeAdd();
	}

	@Override
	public Map add() {
		
		Map map = new HashMap();
		this.setBean((PeTeacher)super.setSubIds(this.getBean()));
		
		try {
			checkBeforeAdd();
		} catch (EntityException e1) {
			map.put("success", "false");
			map.put("info", e1.getMessage());
			return map;
		}
		
		PeTeacher instance = null;

		try {
			instance = this.peTeacherService.save(this.getBean());
			map.put("success", "true");
			map.put("info", "添加成功");
			logger.info("添加成功! id= " + instance.getId());
		} catch (Exception e) {
			map.clear();
			map.put("success", "false");
			map.put("info", "用户名已存在");
			logger.error("添加失败!");
		}

		return map;
	}
	
	public Map delete() {
		
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				map.put("success", "true");
				map.put("info", "删除成功");
				try{
					this.getPeTeacherService().deleteByIds(idList);
				}catch(Exception e){
					return super.checkForeignKey(e);
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}

	@Override
	public Map update() {
		Map map = new HashMap();
		
		PeTeacher instance = null;
		try {
			instance = this.getGeneralService().getById(this.getBean().getId());
		} catch (EntityException e1) {
			map.put("success", "false");
			map.put("info", "更新失败");
			return map;
		}
		this.superSetBean((PeTeacher) setSubIds(instance,this.getBean()));
		
		try {
			this.checkBeforeUpdate();
		} catch (EntityException e1) {
			try {
				this.getGeneralService().saveError();
			} catch (EntityException e2) {
			}
			map.put("success", "false");
			map.put("info", e1.getMessage());
			return map;
		}
		
		try {
			this.getPeTeacherService().update(instance);
			map.put("success", "true");
			map.put("info", "更新成功");
		} catch (EntityException e) {
			map.put("success", "false");
			map.put("info", e.getMessage());
			return map;
		}	
		 catch (Exception e) {
			return this.checkAlternateKey(e, "更新");
			
		}
		return map;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
	

}
