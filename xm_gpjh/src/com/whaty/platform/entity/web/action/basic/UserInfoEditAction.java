package com.whaty.platform.entity.web.action.basic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeValuaExpert;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * 用户信息修改
 * 
 * @author 赵玉晓
 * 
 */
public class UserInfoEditAction extends MyBaseAction {
	private PeManager manager;//当前管理员
	private List unitList;
	private List provinceList;
	private List subjectList;
	private List genderList;
	private EnumConst enumConstByFkStatus;
	private EnumConst enumConstByFlagIsvalid;
	private String sex;
	private boolean succ;//是否修改成功
	private PeValuaExpert valuaExpert;
	
	private String password_old;
	private String password_new;
	
	public String toEdit(){
		this.getCurrentUser();
		this.initBasicData();
		this.succ=false;
		return "toEdit";
	}
	
	// 评审专家只能查看自己的资料，不能修改
	public String toView() {
		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String valuaExpertIdSQL = "select id from pe_valua_expert where login_id=:theLoginId";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("theLoginId", us.getLoginId());
		List<String> idList = null;
		try {
			idList = getGeneralService().getBySQL(valuaExpertIdSQL, paramsMap);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String valuaExpertId = "";
		if(idList != null && !idList.isEmpty()) {
			valuaExpertId = idList.get(0);
		}
//		getGeneralService().getGeneralDao().setEntityClass();
		
		setValuaExpert((PeValuaExpert) getMyListService().getById(PeValuaExpert.class,valuaExpertId));
		
		return "toView";
	}
	
	public String toEditPw(){
		return "toEditPw";
	}
	
	public String savePw(){
		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		this.getCurrentUser();
		
		PeManager instance = this.getManager();
		SsoUser user = us.getSsoUser();
		if (!user.getPassword().equals(this.getPassword_old())) {
			this.setMsg("原始密码不正确");
			return "toEditPw";
		} else {
			try {
//				this.getGeneralService().getGeneralDao().setEntityClass();
				user=(SsoUser) this.getMyListService().getById(SsoUser.class,user.getId());
				user.setPassword(this.getPassword_new());
				
//				String sql="update sso_user set checked_pw='1' where id=:ids";
//				Map map=new HashMap();
//				map.put("ids", user.getId());
//				this.getGeneralService().executeBySQL(sql, map);
				user.setCheckedPw("1");
				this.getGeneralService().save(user);
				us.setSsoUser(user);
				ActionContext.getContext().getSession().put(SsoConstant.SSO_USER_SESSION_KEY, us);
				this.setMsg("密码修改成功");
				this.succ=true;
			} catch (EntityException e) {
				e.printStackTrace();
				this.setMsg("密码修改失败");
				this.succ=false;
			}
			this.setManager(instance);
		}
		return "toEditPw";
	}
	private void getCurrentUser(){
		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser user=us.getSsoUser();
		DetachedCriteria dc=DetachedCriteria.forClass(PeManager.class);
		dc.createCriteria("ssoUser","ssoUser");
		dc.createCriteria("peUnit","peUnit");
		dc.createCriteria("enumConstByFkGender","enumConstByFkGender",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFkStatus","enumConstByFkStatus",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagIsvalid","enumConstByFlagIsvalid",DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("ssoUser.id", user.getId()));
		List list=new LinkedList();
		try {
			list=this.getGeneralService().getList(dc);
			if(list!=null&&list.size()>0){
				PeManager mgr=(PeManager) list.get(0);
				this.setManager(mgr);
				this.setBean(mgr);
			}else{
				System.out.println("查找当前用户，未找到");
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	public String saveUserInfo(){
		boolean flag=true;
		
		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser user=us.getSsoUser();
		DetachedCriteria dc=DetachedCriteria.forClass(PeManager.class);
		dc.createCriteria("ssoUser","ssoUser");
		dc.createCriteria("peUnit","peUnit");
		dc.createCriteria("enumConstByFkGender","enumConstByFkGender",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFkStatus","enumConstByFkStatus",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagIsvalid","enumConstByFlagIsvalid",DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("ssoUser.id", user.getId()));
		List list=new LinkedList();
		try {
			list=this.getGeneralService().getList(dc);
			if(list!=null&&list.size()>0){
				PeManager mgr=(PeManager) list.get(0);
				this.setManager(mgr);
				this.getManager().setName(this.getBean().getName());
				this.getManager().setDepartment(this.getBean().getDepartment());
				this.getManager().setTelephone(this.getBean().getTelephone());
				this.getManager().setEmail(this.getBean().getEmail());
				this.getManager().setOfficePhone(this.getBean().getOfficePhone());
				this.getManager().setFax(this.getBean().getFax());
				this.getManager().setZhiwuzhicheng(this.getBean().getZhiwuzhicheng());
				this.getManager().setEnumConstByFkGender(this.getBean().getEnumConstByFkGender());
				this.getManager().setEducation(this.getBean().getEducation());
				this.getManager().setPolitics(this.getBean().getPolitics());
				this.getManager().setAddress(this.getBean().getAddress());
				this.getManager().setZip(this.getBean().getZip());
				this.getManager().setFolk(this.getBean().getFolk());
				this.getGeneralService().getGeneralDao().setEntityClass(PeManager.class);
				this.getGeneralService().save(this.getManager());
				
				String sql="update sso_user set checked_info='1' where id=:ids";
				Map map=new HashMap();
				map.put("ids", user.getId());
				this.getGeneralService().executeBySQL(sql, map);
				user.setCheckedInfo("1");
				ActionContext.getContext().getSession().put(SsoConstant.SSO_USER_SESSION_KEY, us);
			}else{
				flag=false;
				System.out.println("查找当前用户，未找到");
			}
		} catch (EntityException e) {
			flag=false;
			e.printStackTrace();
		}
		this.toEdit();
		if(flag){
			this.setMsg("修改成功");
			this.succ=true;
		}else{
			this.setMsg("修改失败");
			this.succ=false;
		}
		return "toEdit";
	}
	private void initBasicData(){
		List subjectList=new LinkedList();
		List proviceList=new LinkedList();
		List genderList=new LinkedList();
		String sql="select id,name from pe_subject";
		try {
			subjectList=this.getGeneralService().getBySQL(sql);
			if(subjectList!=null&&subjectList.size()>0){
//				this.setUnitList(unitList);
				this.setSubjectList(subjectList);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String sql1="select id,name from pe_province";
		try {
			provinceList=this.getGeneralService().getBySQL(sql1);
			if(provinceList!=null&&provinceList.size()>0){
				this.setProvinceList(provinceList);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String sql2="select id,name from enum_const where namespace='FkGender'";
		try {
			genderList=this.getGeneralService().getBySQL(sql2);
			if(genderList!=null&&genderList.size()>0){
				this.setGenderList(genderList);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void initGrid() {
	}
	@Override
	public void checkBeforeAdd() throws EntityException{
	}
	@Override
	public void checkBeforeUpdate() throws EntityException{
	}

	@Override
	public void setEntityClass() {
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/userInfoEditAction";
	}

	public void setBean(PeManager instance) {
		super.superSetBean(instance);
	}

	public PeManager getBean() {
		return (PeManager) super.superGetBean();
	}

	public DetachedCriteria initDetachedCriteria() {
		return null;
	}
	public PeManager getManager() {
		return manager;
	}
	public void setManager(PeManager manager) {
		this.manager = manager;
	}
	public List getUnitList() {
		return unitList;
	}
	public void setUnitList(List unitList) {
		this.unitList = unitList;
	}
	
	public List getGenderList() {
		return genderList;
	}
	public void setGenderList(List genderList) {
		this.genderList = genderList;
	}
	public EnumConst getEnumConstByFkStatus() {
		return enumConstByFkStatus;
	}
	public void setEnumConstByFkStatus(EnumConst enumConstByFkStatus) {
		this.enumConstByFkStatus = enumConstByFkStatus;
	}
	public EnumConst getEnumConstByFlagIsvalid() {
		return enumConstByFlagIsvalid;
	}
	public void setEnumConstByFlagIsvalid(EnumConst enumConstByFlagIsvalid) {
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
	}
	public List getProvinceList() {
		return provinceList;
	}
	public void setProvinceList(List provinceList) {
		this.provinceList = provinceList;
	}
	public List getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List subjectList) {
		this.subjectList = subjectList;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public boolean isSucc() {
		return succ;
	}
	public void setSucc(boolean succ) {
		this.succ = succ;
	}
	public String getPassword_old() {
		return password_old;
	}
	public void setPassword_old(String passwordOld) {
		password_old = passwordOld;
	}
	public String getPassword_new() {
		return password_new;
	}
	public void setPassword_new(String passwordNew) {
		password_new = passwordNew;
	}

	public PeValuaExpert getValuaExpert() {
		return valuaExpert;
	}

	public void setValuaExpert(PeValuaExpert valuaExpert) {
		this.valuaExpert = valuaExpert;
	}
}
