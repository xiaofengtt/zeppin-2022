package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class PeManagerAction extends MyBaseAction {
	
	private String pwd;
	private String rePwd;
	private String managerId;
	private String managerType;
	private String resetMsg;
	
	public String getResetMsg() {
		return resetMsg;
	}

	public void setResetMsg(String resetMsg) {
		this.resetMsg = resetMsg;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(true, true, true, true);
		this.getGridConfig().setTitle(this.getText("管理员管理"));
		
		this.getGridConfig().addMenuFunction(this.getText("设为有效"), "truevalid");
		this.getGridConfig().addMenuFunction(this.getText("设为无效"), "falsevalid");

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("用户名"), "loginId", true, true, true, Const.userName_for_extjs);
		this.getGridConfig().addColumn(this.getText("密码"), "ssoUser.password", false);
		ColumnConfig c_name = new ColumnConfig(this.getText("权限组"), "pePriRole.name");
		c_name.setAdd(true); c_name.setSearch(false); c_name.setList(true);
		c_name.setComboSQL("select t.id,t.name  from pe_pri_role t, enum_const e where t.flag_role_type=e.id and e.code='3' and e.namespace='FlagRoleType' order by t.name");
		this.getGridConfig().addColumn(c_name);
		this.getGridConfig().addColumn(this.getText("单位"), "peUnit.name");
		this.getGridConfig().addColumn(this.getText("部门"), "department");
		this.getGridConfig().addColumn(this.getText("是否有效"), "enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name", false);
		this.getGridConfig().addColumn(this.getText("民族"), "folk", true, true, true, "");
		this.getGridConfig().addColumn(this.getText("办公电话"), "officePhone",false,true,true,Const.telephone_for_extjs);
		this.getGridConfig().addColumn(this.getText("手机"), "telephone",false,true,true,Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("传真"), "fax",false,true,true,Const.telephone_for_extjs);
		this.getGridConfig().addColumn(this.getText("电子邮箱"), "email",false,true,true,Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("通信地址"), "address", false, true, true, "", false, 150);
		this.getGridConfig().addColumn(this.getText("邮编"), "zip", false, true, true, Const.zip_for_extjs, false, 150);
//		this.getGridConfig().addColumn(this.getText("毕业院校、专业"), "graduationInfo", false);
//		this.getGridConfig().addColumn(this.getText("毕业时间"), "graduationDate", false);
		this.getGridConfig().addColumn(this.getText("职务职称"), "zhiwuzhicheng", false);
		this.getGridConfig().addColumn(this.getText("政治面貌"), "politics", false);
		this.getGridConfig().addColumn(this.getText("学历"), "education", false);
		this.getGridConfig().addColumn(this.getText("备注"), "note", false);
		this.getGridConfig().addRenderFunction(this.getText("重置密码"), "<a href='peManager_reset.action?managerId=${value}&managerType=3' target='_self'>重置</a>", "id");
//		this.getGridConfig().addRenderFunction(this.getText("设置权限范围"), "<a href='managerRangeRight_showRangeRight.action?managerId=${value}&managerType=3' target='_self'>设置</a>", "id");
	}
	
	public Map<String, String> updateColumn() {
		Map<String, String> map = new HashMap<String, String>();
		int count = 0;
		String action = this.getColumn();
		if (this.getIds() != null && this.getIds().length() > 0) {

		    String[] ids = getIds().split(",");
		    List idList = new ArrayList();
		    for (int i = 0; i < ids.length; i++) {
		    	idList.add(ids[i]);
		    }
		    List<PeManager> plist = new ArrayList<PeManager>();
		    try {
			DetachedCriteria pubdc = DetachedCriteria.forClass(PeManager.class);
			pubdc.add(Restrictions.in("id", ids));
			plist = this.getGeneralService().getList(pubdc);
			EnumConst enumConst = null;
			if (action.equals("truevalid")) {
			    enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
			}
			if (action.equals("falsevalid")) {
			    enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0");
			}
			for (int k = 0; k < plist.size(); k++) {
			    if (action.contains("valid")) {
			    	plist.get(k).setEnumConstByFlagIsvalid(enumConst);
			    	plist.get(k).getSsoUser().setEnumConstByFlagIsvalid(enumConst);
			    }
			    PeManager bulletin = (PeManager) this.getGeneralService().save(plist.get(k));
			    count++;
			}
		    } catch (EntityException e) {
			e.printStackTrace();
			map.clear();
			map.put("success", "false");
			map.put("info", "操作失败");
			return map;
		    }
		    map.clear();
		    map.put("success", "true");
		    map.put("info", count + "条记录操作成功");

		} else {
		    map.put("success", "false");
		    map.put("info", "parameter value error");
		}
		return map;
	}
	
	@Override
	public void checkBeforeAdd() throws EntityException {
		DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
		dc.add(Restrictions.eq("loginId", this.getBean().getLoginId()));
		
		List list = this.getGeneralService().getList(dc);
		if(list !=null && list.size() > 0) {
			throw new EntityException("'" + this.getBean().getLoginId() + "' 用户已经存在！");
		} else {
			SsoUser user = new SsoUser();
			user.setLoginId(this.getBean().getLoginId());
			user.setPassword(Const.FIRST_PASSWORD);
			user.setLoginNum(Long.valueOf("0"));
			user.setPePriRole(this.getBean().getPePriRole());
			
			EnumConst ec = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
			user.setEnumConstByFlagIsvalid(ec);
			
			this.entityClass = SsoUser.class;
			this.getGeneralService().save(user);
			
			this.entityClass = PeManager.class;
			this.getBean().setSsoUser(user);
//			this.getBean().setName(this.getBean().getLoginId() + "/" + this.getBean().getTrueName());
		}
	}
	
	@Override
	public void checkBeforeUpdate() throws EntityException {
		PeManager oldBean = (PeManager)this.getGeneralService().getById(this.getBean().getId());
		if(!oldBean.getLoginId().equals(this.getBean().getLoginId())) {
			DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
			dc.add(Restrictions.eq("loginId", this.getBean().getLoginId()));
			
			List list = this.getGeneralService().getList(dc);
			if(list !=null && list.size() > 0) {
				throw new EntityException("'" + this.getBean().getLoginId() + "' 用户已经存在！");
			} else {
				oldBean.getSsoUser().setLoginId(this.getBean().getLoginId());
				this.getBean().setSsoUser(oldBean.getSsoUser());
//				this.getBean().setName(this.getBean().getLoginId() + "/" + this.getBean().getTrueName());
			}
		}
	}
	
	public String reset() {
		return "reset";
	}
	
	public String dispatch() {
		try {
			PeManager pm = (PeManager) this.getGeneralService().getById(this.getManagerId());
			if(this.getPwd() != null && !this.getPwd().equals(this.getRePwd())) {
				this.setResetMsg("重置密码不一致！");
				return "reset";
			}
			pm.getSsoUser().setPassword(this.getPwd());
		} catch (EntityException e) {
			this.setResetMsg("重置密码失败！");
			return "reset";
		}
		return "dispatch";
	}
	
//	private void checkIdCard() throws EntityException {
//		AttributeManage manage=new WhatyAttributeManage();
//		try {
//			if(!manage.isValidIdcard(this.getBean().getIdCard())){
//				throw new EntityException("身份证号码输入错误！");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new EntityException("身份证号码输入错误！");
//		}
//	}
	
//	public Map delete() {
//		
//		Map map = null;
//		
//		if (this.getIds() != null && this.getIds().length() > 0) {
//			String str = this.getIds();
//			if (str != null && str.length() > 0) {
//				String[] ids = str.split(",");
//				List idList = new ArrayList();
//				try {
//					for (int i = 0; i < ids.length; i++) {
//						PeManager pm = (PeManager)this.getGeneralService().getById(ids[i]);
//						idList.add(pm.getSsoUser().getId());
//					}
//					
//					map = super.delete();
//					this.getGeneralService().getGeneralDao().setEntityClass(SsoUser.class);
//					this.getGeneralService().deleteByIds(idList);
//					
//					this.getGeneralService().getGeneralDao().setEntityClass(PeManager.class);
//				} catch (EntityException e) {
//					e.printStackTrace();
//				}
//				
//			}
//		}
//		
//		return map;
//		
//	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeManager.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/sso/admin/peManager";
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
//		UserSession userSession = (UserSession) ActionContext.getContext()
//			.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		SsoUser ssoUser = userSession.getSsoUser();
		DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
		dc.createCriteria("enumConstByFkGender", "enumConstByFkGender", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByFkStatus", "enumConstByFkStatus", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("peUnit", "peUnit", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("ssoUser", "ssoUser").createCriteria("pePriRole", "pePriRole");
//		dc.add(Restrictions.not(Restrictions.eq("loginId", ssoUser.getLoginId())));
		return dc;
	}

	public PeManager getBean() {
		return (PeManager)super.superGetBean();
	}

	public void setBean(PeManager bean) {
		super.superSetBean(bean);
	}
	
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getRePwd() {
		return rePwd;
	}

	public void setRePwd(String rePwd) {
		this.rePwd = rePwd;
	}
	
	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getManagerType() {
		return managerType;
	}

	public void setManagerType(String managerType) {
		this.managerType = managerType;
	}
}