package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeProvince;
import com.whaty.platform.entity.bean.PeTrainee;
//import com.whaty.platform.entity.bean.PeProvince;
//import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeTraineeAdu;
import com.whaty.platform.entity.bean.PeUnit;
//import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PeTraineeReplaceAduAction extends MyBaseAction {
	private File file;
	private String fileFileName;
	private String fileContentType;
	
	@Override
	public void initGrid() {
		getGridConfig().setCapability(false, false, false, true, false);
		this.getGridConfig().setTitle(this.getText("替换学员管理"));
		this.initPublicGrid();
		if (!"2".equals(getUserSession().getRoleType())) {
			this.getGridConfig().addMenuFunction(this.getText("审核通过"), "checkupPass");
			this.getGridConfig().addMenuFunction(this.getText("审核不通过"), "checkupNotPass");
		}
	    getGridConfig().setPrepared(false);
	}
	//公共字段
	protected void initPublicGrid(){
//		UserSession us = getUserSession();
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("培训项目"), "traineeByFkOldTrainee.peProApplyno.name", true,true, true, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("培训学科"), "traineeByFkOldTrainee.peSubject.name", true,true, true, "TextField", true, 50);
		if(!"2".equals(getUserSession().getRoleType())){
			this.getGridConfig().addColumn(this.getText("培训单位"), "traineeByFkOldTrainee.peUnitByFkTrainingUnit.name", true,true, true, "TextField", true, 50);
		}
		this.getGridConfig().addColumn(this.getText("原学员名称"), "traineeByFkOldTrainee.name", true, false, true, "TextField", false, 20);
		this.getGridConfig().addColumn(this.getText("原学员单位"), "traineeByFkOldTrainee.workPlace",true, true, true, "TextField", false, 50, null);
		this.getGridConfig().addColumn(this.getText("新学员名称"), "traineeByFkNewTrainee.name", true, false, true, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("身份证号"), "traineeByFkNewTrainee.idcard", false,true, true, "TextField", false, 50, Const.cardId_for_extjs);
		this.getGridConfig().addColumn(this.getText("性别"), "traineeByFkNewTrainee.enumConstByFkGender.name",true,true,true,"TextField",false,10,"");
		this.getGridConfig().addColumn(this.getText("年龄"), "traineeByFkNewTrainee.age", true, true, true, "", false, 4,Const.twoNumandSpace_for_extjs);
		this.getGridConfig().addColumn(this.getText("民族"), "traineeByFkNewTrainee.folk.name", true, true, true, "", false, 20);
		this.getGridConfig().addColumn(this.getText("单位"), "traineeByFkNewTrainee.workPlace",true, true, true, "TextField", false, 50, null);
		this.getGridConfig().addColumn(this.getText("所在地区（省）"), "traineeByFkOldTrainee.peProvince.name", true, true, true, "TextField", false, 20);
		this.getGridConfig().addColumn(this.getText("所在地区（市）"), "traineeByFkNewTrainee.city.name", true, true, true, "TextField", false, 20);
		this.getGridConfig().addColumn(this.getText("所在地区（县）"), "traineeByFkNewTrainee.county.name", true, true, true, "TextField", false, 20);
		this.getGridConfig().addColumn(this.getText("学校所在区域"), "traineeByFkNewTrainee.unitAttribute.name",true, true, true, "TextField", false, 50, null);
		this.getGridConfig().addColumn(this.getText("学校类别"), "traineeByFkNewTrainee.unitType.name",true, true, true, "TextField", false, 50, null);
		this.getGridConfig().addColumn(this.getText("职务"), "traineeByFkNewTrainee.zhiwu", true, true, true, "", true, 25);
		this.getGridConfig().addColumn(this.getText("职称"), "traineeByFkNewTrainee.jobTitle.name", true, true, true, "", false, 20);
		this.getGridConfig().addColumn(this.getText("最高学历"), "traineeByFkNewTrainee.education.name", true, true, true, "", false, 25);
		this.getGridConfig().addColumn(this.getText("教龄"), "traineeByFkNewTrainee.workyear", true, true, true, "", false, 50);
		this.getGridConfig().addColumn(this.getText("手机"), "traineeByFkNewTrainee.telephone", true, true, true, "TextField", false, 20);
		this.getGridConfig().addColumn(this.getText("主要任教学段"), "traineeByFkNewTrainee.mainTeachingGrade.name", true, true, true, "", false, 50);
		this.getGridConfig().addColumn(this.getText("主要任教学科"), "traineeByFkNewTrainee.mainTeachingSubject.name", true, true, true, "", false, 50);
		this.getGridConfig().addColumn(this.getText("毕业院校"), "traineeByFkNewTrainee.graduation", true,true, true, "TextField", false, 50, null);
		this.getGridConfig().addColumn(this.getText("所学专业"), "traineeByFkNewTrainee.major", true,true, true, "TextField", false, 50, null);
		this.getGridConfig().addColumn(this.getText("参训状态"), "traineeByFkNewTrainee.enumConstByFkStatusTraining.name", true, true, true, "TextField", true, 20);
		this.getGridConfig().addColumn(this.getText("是否通过审核"), "traineeByFkNewTrainee.enumConstByFkCheckedTrainee.name", true, false, true, "TextField", true, 20);
		this.getGridConfig().addColumn(this.getText("创建时间"), "createTime", false, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("创建人"), "ssoUser.loginId", true, false, true, "TextField", false, 20);
		

	}
	
	
	public UserSession getUserSession(){
		return (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
	}

	/**
	 * 审核
	 */
	@Override
	public Map<String,String> updateColumn() {
		Map<String, String> msgMap = new HashMap<String, String>();
		msgMap.put("success", "false");
		msgMap.put("info", "操作失败");
		
		String action = this.getColumn();
		if(getIds() != null && getIds().length() != 0) {
			String[] ids = this.getIds().split(",");
			
			//增加ids转换，将petraineeadu 的id转换为 petrainee的id --20170609
			String[] idss = null;
			try {
				
				String idStr = "";
				for(String id : ids){
					PeTraineeAdu peTraineeadu = (PeTraineeAdu)getGeneralService().getById(PeTraineeAdu.class, id);
					idStr+= peTraineeadu.getTraineeByFkNewTrainee().getId()+",";
				}
				idss = idStr.split(",");
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msgMap.put("info", "操作失败,查询数据失败");
			}
			
			List<PeTrainee> traineeList = null;
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PeTrainee.class);
//			detachedCriteria.add(Restrictions.in("id", ids));
			detachedCriteria.add(Restrictions.in("id", idss));
			try {
				traineeList = this.getGeneralService().getList(detachedCriteria);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			
			/*
			 * 20170609
			 * 增加分级管理员判断 --
			 * 约定审核规则为上级审核通过后，下级无修改权限。
			 * 约定项目办管理员可以直接终审通过替换申请。
			 */
			if("2".equals(getUserSession().getRoleType())){//承办单位
				EnumConst enumConst = null;
				for (int i = 0; i < traineeList.size(); i++) {
					String code = traineeList.get(i).getEnumConstByFkCheckedTrainee() == null ? "" : traineeList.get(i).getEnumConstByFkCheckedTrainee().getCode();
					if(!"".equals(code) && "65235".equals(code)){//省厅审核
						msgMap.put("info", "学员"+traineeList.get(i).getName()+"已被省厅审核通过，您无权再次审核！");
						return msgMap;
					}
					if(!"".equals(code) && "65230".equals(code)){//项目办审核
						msgMap.put("info", "学员"+traineeList.get(i).getName()+"已被项目办审核通过，您无权再次审核！");
						return msgMap;
					}
					if(!"".equals(code) && "65233".equals(code)){//项目办审核
						msgMap.put("info", "学员"+traineeList.get(i).getName()+"已被项目办审核未通过，您无权再次审核！");
						return msgMap;
					}
					if("checkupPass".equals(action)) {
						enumConst = this.getMyListService().getEnumConstByNamespaceCode("FkCheckedTrainee", "65234");
						traineeList.get(i).setEnumConstByFkCheckedTrainee(enumConst);
					}else if("checkupNotPass".equals(action)) {
						enumConst = this.getMyListService().getEnumConstByNamespaceCode("FkCheckedTrainee", "65234");
						traineeList.get(i).setEnumConstByFkCheckedTrainee(enumConst);
					} else {
						msgMap.put("info", "状态错误！");
						return msgMap;
					}
				}
			} else if ("3".equals(getUserSession().getRoleType())) {//省厅
				EnumConst enumConst = null;
				for (int i = 0; i < traineeList.size(); i++) {
					String code = traineeList.get(i).getEnumConstByFkCheckedTrainee() == null ? "" : traineeList.get(i).getEnumConstByFkCheckedTrainee().getCode();
					if(!"".equals(code) && "65230".equals(code)){//项目办审核
						msgMap.put("info", "学员"+traineeList.get(i).getName()+"已被项目办审核通过，您无权再次审核！");
						return msgMap;
					}
					if(!"".equals(code) && "65233".equals(code)){//项目办审核
						msgMap.put("info", "学员"+traineeList.get(i).getName()+"已被项目办审核未通过，您无权再次审核！");
						return msgMap;
					}
					if("checkupPass".equals(action)) {
						enumConst = this.getMyListService().getEnumConstByNamespaceCode("FkCheckedTrainee", "65235");
						traineeList.get(i).setEnumConstByFkCheckedTrainee(enumConst);
					}else if("checkupNotPass".equals(action)) {
						enumConst = this.getMyListService().getEnumConstByNamespaceCode("FkCheckedTrainee", "65234");
						traineeList.get(i).setEnumConstByFkCheckedTrainee(enumConst);
					} else {
						msgMap.put("info", "状态错误！");
						return msgMap;
					}
				}
			} else if("4".equals(getUserSession().getRoleType()) || "5".equals(getUserSession().getRoleType())){
				EnumConst enumConst = null;
				for (int i = 0; i < traineeList.size(); i++) {
					if("checkupPass".equals(action)) {
						enumConst = this.getMyListService().getEnumConstByNamespaceCode("FkCheckedTrainee", "65230");
						traineeList.get(i).setEnumConstByFkCheckedTrainee(enumConst);
					}else if("checkupNotPass".equals(action)) {
						enumConst = this.getMyListService().getEnumConstByNamespaceCode("FkCheckedTrainee", "65235");
						traineeList.get(i).setEnumConstByFkCheckedTrainee(enumConst);
					} else {
						msgMap.put("info", "状态错误！");
						return msgMap;
					}
				}
			} else {
				msgMap.put("info", "无操作权限！");
				return msgMap;
			}
				
			try {
				this.getGeneralService().saveList(traineeList);
			} catch (EntityException e) {
				e.printStackTrace();
				return msgMap;
			}
			if("checkupPass".equals(action)) {
				msgMap.put("info", traineeList.size()+"个学员审核通过。");
			}else if("checkupNotPass".equals(action)){
				msgMap.put("info", traineeList.size()+"个学员审核不通过。");
			}else {
				msgMap.put("info", traineeList.size()+"个学员操作成功");
			}
		}
		msgMap.put("success", "true");
		
		return msgMap;
	}
	
	
	public String abstractUpdateColumn() {
		Map map = updateColumn();
		if (map == null) {
			map = new HashMap();
			map.put("success", "false");
			map.put("info",this.getText(("UpdateColumn method is not implemented in Action")));
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}
	

	/**
	 * 在修改学员信息之后需要将学员“修改是否通过”状态改成默认值(未通过)
	 */
	@Override
	public Map update() {
		Map resultMap = super.update();
		setTraineeModifyStatusToDefault();
		return resultMap;
	}
	// 将学员“修改是否通过”状态改成默认值(未通过)
	public void setTraineeModifyStatusToDefault() {
		EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FkModifyChecked", "1985");
		String updateTraineeFKModify = "update pe_trainee set FK_MODIFY_CHECKED=:theDefaultValue where id=:theTraineeId";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("theDefaultValue", enumConst.getId());
		paramsMap.put("theTraineeId", getBean().getId());
		try {
			getGeneralService().executeBySQL(updateTraineeFKModify, paramsMap);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	// 获取当前登录用户对应的Sso_User
	public SsoUser getCurrentSsoUser() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser currentSsoUser = userSession.getSsoUser();
		
		return currentSsoUser;
	}
	
	/**
	 * 获取当前用户所在单位的省份
	 * @return 当前用户所在单位的省份
	 */
	public PeProvince getCurrentUnitProvince() {
		SsoUser sso=this.getCurrentSsoUser();
		DetachedCriteria dc=DetachedCriteria.forClass(PeManager.class);
		dc.createAlias("ssoUser","ssoUser");
		DetachedCriteria dcpeUnit = dc.createCriteria("peUnit","peUnit");
		dcpeUnit.createAlias("peProvince","peProvince");
		dc.add(Restrictions.eq("ssoUser", sso));
		List list=new LinkedList();
		PeManager mgr=null;
		try {
			list=this.getGeneralService().getList(dc);
			if(list!=null&&list.size()>0){
				mgr=(PeManager) list.get(0);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(mgr!=null&&mgr.getPeUnit()!=null){
			return mgr.getPeUnit().getPeProvince();
		}
		return null;
	}
	
	/**
	 * 获取当前登录用户所在单位
	 * @return
	 */
	public PeUnit getCurrentUserBelongUnit() {
		SsoUser sso=this.getCurrentSsoUser();
		DetachedCriteria dc=DetachedCriteria.forClass(PeManager.class);
		dc.createCriteria("ssoUser","ssoUser");
		dc.createCriteria("peUnit","peUnit");
		dc.add(Restrictions.eq("ssoUser", sso));
		List<PeManager> managerList = null;
		try {
			managerList = getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(managerList != null && managerList.size() != 0) {
			return managerList.get(0).getPeUnit();
		}
		return null;
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTraineeAdu.class);
		dc.createAlias("ssoUser", "ssoUser", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("traineeByFkOldTrainee", "traineeByFkOldTrainee",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("traineeByFkNewTrainee", "traineeByFkNewTrainee",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("traineeByFkOldTrainee.peProApplyno", "peProApplyno",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("traineeByFkOldTrainee.peSubject", "peSubject", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("traineeByFkNewTrainee.enumConstByFkGender", "enumConstByFkGender",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("traineeByFkNewTrainee.folk", "folk", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("traineeByFkNewTrainee.unitAttribute", "unitAttribute", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("traineeByFkNewTrainee.unitType", "unitType", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("traineeByFkNewTrainee.jobTitle", "jobTitle", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("traineeByFkNewTrainee.education", "education", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("traineeByFkNewTrainee.mainTeachingGrade", "mainTeachingGrade", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("traineeByFkNewTrainee.mainTeachingSubject", "mainTeachingSubject", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("traineeByFkNewTrainee.enumConstByFkStatusTraining", "enumConstByFkStatusTraining", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("traineeByFkNewTrainee.enumConstByFkCheckedTrainee", "enumConstByFkCheckedTrainee",DetachedCriteria.LEFT_JOIN);
		String currentUserRoleTypeCodeString = getUserSession().getRoleType();
		if ("3".equals(currentUserRoleTypeCodeString)) {
	    	dc.createAlias("traineeByFkNewTrainee.peProvince", "peProvince");
			dc.add(Restrictions.eq("traineeByFkOldTrainee.peProvince",this.getCurrentUnitProvince()));
			dc.createAlias("traineeByFkNewTrainee.county", "county",DetachedCriteria.LEFT_JOIN);
			dc.createAlias("traineeByFkNewTrainee.city", "city",DetachedCriteria.LEFT_JOIN);
	    } else {
	    	dc.createAlias("traineeByFkNewTrainee.peProvince", "peProvince",DetachedCriteria.LEFT_JOIN);
	    	dc.createAlias("traineeByFkNewTrainee.county", "county",DetachedCriteria.LEFT_JOIN);
			dc.createAlias("traineeByFkNewTrainee.city", "city",DetachedCriteria.LEFT_JOIN);
	    }
		if ("2".equals(currentUserRoleTypeCodeString)) {
	      dc.createCriteria("traineeByFkOldTrainee.peUnitByFkTrainingUnit", "peUnitByFkTrainingUnit");
	      dc.add(Restrictions.eq("traineeByFkOldTrainee.peUnitByFkTrainingUnit", getCurrentUserBelongUnit()));
	    }else{
	    	dc.createCriteria("traineeByFkOldTrainee.peUnitByFkTrainingUnit", "peUnitByFkTrainingUnit",DetachedCriteria.LEFT_JOIN);
	    }
		dc.add(Restrictions.isNotNull("traineeByFkOldTrainee.peUnitByFkTrainingUnit"));
		dc.addOrder(Order.desc("createTime"));
		return dc;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeTraineeAdu.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peTraineeAduAction";
	}

	public PeTraineeAdu getBean() {
		return (PeTraineeAdu) super.superGetBean();
	}

	public void setBean(PeTraineeAdu bean) {
		super.superSetBean(bean);
	}

	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}


	
}