package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

/**
 * 单位管理
 * 
 * @author 赵玉晓
 * 
 */
public class PeManagersAction extends MyBaseAction {
	
	private GeneralService peManagerService;
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(true, true, true, true, true);
		this.getGridConfig().setTitle(this.getText("联系人信息管理"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name",true,true,true,"",true,20);
		this.getGridConfig().addColumn(this.getText("登录名"), "loginId");
		this.getGridConfig().addColumn(this.getText("单位"), "peUnit.name");
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name",true,true,true,"TextField",true,200,"");
//		this.getGridConfig().addColumn(this.getText("状态"), "enumConstByFkStatus.name");
		this.getGridConfig().addColumn(this.getText("部门"), "department",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("手机"), "telephone",true, true, true,"",true,200,Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("email"), "email",true, true, true,"",true,200,Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("办公电话"), "officePhone",true, true, true,"",true,200,Const.phone_number_for_extjs);
		this.getGridConfig().addColumn(this.getText("传真"), "fax",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("生日"), "birthday",true,true,true,"Date",true,200);
		this.getGridConfig().addColumn(this.getText("通讯地址"), "address",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("邮编"), "zip",true,true,true,"",true,200,Const.zip_for_extjs);
		this.getGridConfig().addColumn(this.getText("民族"), "folk",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("政治面貌"), "politics",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("学历"), "education",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("账号属性"), "enumConstByFlagProperty.name",true,true,true,"TextField",true,200);
		this.getGridConfig().addColumn(this.getText("是否有效"), "ssoUser.enumConstByFlagIsvalid.name",true,true,true,"TextField",true,200);
		this.getGridConfig().addColumn(this.getText("备注"), "note", true,true,true, "TextArea", true, 2000);
		this.getGridConfig().addMenuFunction(this.getText("密码重置"), "restpassword");
		this.getGridConfig().addMenuFunction(this.getText("设为有效"), "isvalid");
		this.getGridConfig().addMenuFunction(this.getText("设为无效"), "isNovalid");
	}
	@Override
	public void checkBeforeAdd() throws EntityException{
		boolean checked=this.checkId(this.getBean().getLoginId());
		if(!checked){
			throw new EntityException("此登录名已存在！");
		}
		SsoUser sso=new SsoUser();
		sso.setEnumConstByFlagIsvalid(this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1"));
		sso.setLoginId(this.getBean().getLoginId());
		sso.setPassword(Const.FIRST_PASSWORD);
		sso.setCheckedInfo("0");
		sso.setCheckedPw("0");
		EnumConst fkType=this.getBean().getPeUnit().getEnumConstByFkUnitType();
		String sql="select enu.name from enum_const enu join pe_unit on pe_unit.fk_unit_type=enu.id where pe_unit.id=:ids";
		Map map=new HashMap();
		map.put("ids", this.getBean().getPeUnit().getId());
		List list_unitType=this.getGeneralService().getBySQL(sql, map);
		String unitType="";
		if(list_unitType!=null&&list_unitType.size()>0){
			unitType=(String) list_unitType.get(0);
		}	
		DetachedCriteria dc=DetachedCriteria.forClass(PePriRole.class);
		dc.add(Restrictions.eq("name", unitType));
		List list=new LinkedList();
		list=this.getGeneralService().getList(dc);
		if(list!=null&&list.size()>0){
			PePriRole role=(PePriRole) list.get(0);
			sso.setPePriRole(role);
		}
//		this.getGeneralService().getGeneralDao().setEntityClass(SsoUser.class);
//		sso=(SsoUser) this.getGeneralService().save(sso);
		this.getBean().setSsoUser(sso);
		this.getBean().setEnumConstByFlagIsvalid(this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1"));
	}
	//重置联系人密码
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
		    if (action.contains("restpassword")) {
			    List<PeManager> plist = new ArrayList<PeManager>();
			    try {
					DetachedCriteria pubdc = DetachedCriteria.forClass(PeManager.class);
					pubdc.add(Restrictions.in("id", ids));
					plist = this.getGeneralService().getList(pubdc);
					for (int k = 0; k < plist.size(); k++) {
						PeManager manager = plist.get(k);
						manager.getSsoUser().setPassword(Const.FIRST_PASSWORD);
					    this.getGeneralService().save(manager);
					    count++;
					}
			    } catch (EntityException e) {
					e.printStackTrace();
					map.clear();
					map.put("success", "false");
					map.put("info", "操作失败");
					return map;
			    }
		    }else if (action.contains("isvalid")) {
			    List<PeManager> plist = new ArrayList<PeManager>();
			    try {
					DetachedCriteria pubdc = DetachedCriteria.forClass(PeManager.class);
					pubdc.add(Restrictions.in("id", ids));
					plist = this.getGeneralService().getList(pubdc);
					for (int k = 0; k < plist.size(); k++) {
						PeManager manager = plist.get(k);
						manager.getSsoUser().setEnumConstByFlagIsvalid(this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1"));
					    this.getGeneralService().save(manager);
					    count++;
					}
			    } catch (EntityException e) {
					e.printStackTrace();
					map.clear();
					map.put("success", "false");
					map.put("info", "操作失败");
					return map;
			    }
		    }else  if (action.contains("isNovalid")) {
			    List<PeManager> plist = new ArrayList<PeManager>();
			    try {
					DetachedCriteria pubdc = DetachedCriteria.forClass(PeManager.class);
					pubdc.add(Restrictions.in("id", ids));
					plist = this.getGeneralService().getList(pubdc);
					for (int k = 0; k < plist.size(); k++) {
						PeManager manager = plist.get(k);
						manager.getSsoUser().setEnumConstByFlagIsvalid(this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0"));
					    this.getGeneralService().save(manager);
					    count++;
					}
			    } catch (EntityException e) {
					e.printStackTrace();
					map.clear();
					map.put("success", "false");
					map.put("info", "操作失败");
					return map;
			    }
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
	public void checkBeforeUpdate() throws EntityException{
		//检查是否修改了loginid，如果修改，则需要在数据库中保持唯一性，不能与其它联系人登录名一样
		String loginString = this.getBean().getLoginId();
		SsoUser sso = ((PeManager)this.getMyListService().getById(PeManager.class,this.getBean().getId())).getSsoUser();
		boolean isLoginIDLegality = isLoginIDLegality(loginString,this.getBean().getId());
		if(isLoginIDLegality) {
			sso.setLoginId(loginString);
//			changeLoginIdInSso(loginString,this.getBean().getId());
		}
		//检查是否修改了所属单位，如果修改了，需要修改此用户的角色
		String unitName=this.getBean().getPeUnit().getName();
		String oldUnitName=this.getunitIdByMgrId(this.getBean().getId());
		if(!unitName.equals(oldUnitName)){
			PePriRole role=this.getRoleByUnitName(unitName);
//			String beanId=this.getBean().getId();
//			this.getGeneralService().getGeneralDao().setEntityClass(PeManager.class);
//			PeManager mgr=(PeManager) this.getGeneralService().getById(beanId);
//			SsoUser user=mgr.getSsoUser();
//			user.setPePriRole(role);
//			this.getGeneralService().save(user);
			sso.setPePriRole(role);
		}
		this.getBean().setSsoUser(sso);
	}
	
	/**
	 * @description 验证数据库中是否已存在用户改变后的登录名
	 * @param loginString 改变后的loginid
	 * @param id 与Sso_User关联的专家id号
	 * @return loginid是否合法，true为合法，false为不合法
	 * @throws EntityException 
	 */
	private boolean isLoginIDLegality(String loginString, String id) throws EntityException {
		boolean flag = false;
		String getLoginIdSQL = "select login_id from pe_manager where id='" + id + "'";
		List<String> resultList = null;
		try {
			resultList = this.getGeneralService().getBySQL(getLoginIdSQL);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String preLoginId = "";
		if(resultList != null && resultList.size() != 0) {
			preLoginId = resultList.get(0).trim();
		}
		if(!loginString.trim().equals(preLoginId)){
			if(!isLoginIDExist(loginString)) {
				flag = true;
			}else {
				throw new EntityException("系统中已存在该登录名，不允许重复！");
			}
		}
		return flag;
	}
	/**
	 * @description 改变联系人所关联的Sso_user中的登录名loginid
	 * @param loginString 改变后的loginid
	 * @param id 与Sso_User关联的联系人id号
	 */
//	private void changeLoginIdInSso(String loginString, String id) {
//		String expertSQL = "select FK_SSO_USER_ID from pe_manager where id='" + id + "'";
//		List<String> result = null;
//		try {
//			result = this.getGeneralService().getBySQL(expertSQL);
//		} catch (EntityException e) {
//			e.printStackTrace();
//		}
//		String managerFKToSsoId = result.get(0);
//		if(result != null&&result.size()!=0) {
//			String updateLoginIdSQL = "update sso_user set LOGIN_ID='" + loginString + "' where id='" + managerFKToSsoId + "'";
//			try {
//				this.getGeneralService().executeBySQL(updateLoginIdSQL);
//			} catch (EntityException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	/**
	 * @descripton 判断数据库中是否已存在给定的loginid对应的记录
	 * @param loginString 从修改页面传进来的登录名
	 * @return 是否存在，true为存在；false为不存在
	 */
	private boolean isLoginIDExist(String loginString) {
		boolean flag = false;
		String loginIdExistSQL = "select id from pe_manager where login_id=:loginId";
		Map<String, String> params = new HashMap<String, String>();
		params.put("loginId", loginString);
		List<String> resultList = null;
		try {
			resultList = this.getGeneralService().getBySQL(loginIdExistSQL, params);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(resultList != null && resultList.size()!=0) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * @description 在删除联系人之前需要将与之关联的Sso_user表中的对应记录删除掉
	 * @param idList 联系人id组成的集合List
	 */
	@Override
	public void checkBeforeDelete(List idList) throws EntityException {
		List<String> managerFKToSsoList = null;
		String getFKSQL = "select fk_sso_user_id from pe_manager where id=:managerId";
		String setFKNullSQL = "update pe_manager set fk_sso_user_id=null where id=:managerId";
		String delSsoSQL = "delete from sso_user where id=:ssoUserId";
		Map<String, String> managerIds = new HashMap<String, String>();
		Map<String, String> ssoUserIds = new HashMap<String, String>();
		for(int i=0;i<idList.size();i++) {
			managerIds.put("managerId", idList.get(i).toString());
			managerFKToSsoList = this.getGeneralService().getBySQL(getFKSQL, managerIds);
			this.getGeneralService().executeBySQL(setFKNullSQL, managerIds);
			if(managerFKToSsoList != null && managerFKToSsoList.size() != 0) {
				ssoUserIds.put("ssoUserId", managerFKToSsoList.get(0));
				this.getGeneralService().executeBySQL(delSsoSQL, ssoUserIds);
			}
		}
	}
	/**
	 * 根据联系人的ID查询所在单位Id
	 * @param beanId
	 * @return
	 */
	public String getunitIdByMgrId(String beanId){
		String unidId="";
		String sql="select u.name from pe_unit u join pe_manager m on m.fk_unit=u.id where m.id=:ids";
		Map map=new HashMap();
		map.put("ids",beanId);
		List list=new LinkedList();
		try {
			list=this.getGeneralService().getBySQL(sql, map);
			if(list!=null&&list.size()>0){
				unidId=(String) list.get(0);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return unidId;
	}
	
	/**
	 * 根据单位ID返回角色
	 * @return
	 */
	public PePriRole getRoleByUnitName(String unitName){
		PePriRole role=null;
		String unitType="";
		String sql="select e.name from enum_const e join pe_unit u on u.fk_unit_type=e.id where u.name=:ids";
		Map map=new HashMap();
		map.put("ids", unitName);
		unitType=(String) this.getBySqlMap(sql, map);
		
		DetachedCriteria dc=DetachedCriteria.forClass(PePriRole.class);
		dc.add(Restrictions.eq("name", unitType));
		List list=new LinkedList();
		try {
			list=this.getGeneralService().getList(dc);
			if(list!=null&&list.size()>0){
				role=(PePriRole) list.get(0);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return role;
	}
	
	public Object getBySqlMap(String sql,Map map){
		Object obj=null;
		List list=new LinkedList();	
		try {
			list=this.getGeneralService().getBySQL(sql, map);
			if(list!=null&&list.size()>0){
				obj=list.get(0);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public boolean checkId(String loginId){
		String sql="select id from sso_user where login_id=:ids";
		Map map=new HashMap();
		map.put("ids", loginId);
		List list=new LinkedList();
		try {
			list=this.getGeneralService().getBySQL(sql, map);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(list!=null&&list.size()>0){
			return false;
		}
		return true;
	}
	public boolean checkLoginIdModify(String ssoId,String loginId){
//		String sql="select id from sso_user where id=:ids and login_id=:loginIds";
//		Map map=new HashMap();
//		map.put("ids", )
		return true;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeManager.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peManagersAction";
	}

	public void setBean(PeManager instance) {
		super.superSetBean(instance);
	}

	public PeManager getBean() {
		return (PeManager) super.superGetBean();
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
//		dc.createCriteria("ssoUser", "ssoUser", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peUnit", "peUnit",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFkGender", "enumConstByFkGender",DetachedCriteria.LEFT_JOIN);
//		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid",DetachedCriteria.LEFT_JOIN);
		DetachedCriteria dcsso = dc.createCriteria("ssoUser", "ssoUser",DetachedCriteria.LEFT_JOIN);
		dcsso.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagProperty", "enumConstByFlagProperty",DetachedCriteria.LEFT_JOIN);
		return dc;
	}
	public GeneralService getGeneralService() {
		return peManagerService;
	}
	public void setPeManagerService(GeneralService peManagerService) {
		this.peManagerService = peManagerService;
	}
}