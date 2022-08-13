package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
import com.whaty.util.string.AttributeManage;
import com.whaty.util.string.WhatyAttributeManage;

public class PeTeacherAction extends MyBaseAction {

	@Override
	public void initGrid() {

		this.getGridConfig().setCapability(true, true, true, true, true);
		this.getGridConfig().setTitle(this.getText("教师管理"));
		
		this.getGridConfig().addMenuFunction(this.getText("设为有效"), "truevalid");
		this.getGridConfig().addMenuFunction(this.getText("设为无效"), "falsevalid");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("教师姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("用户名"), "loginId", true, true, true, Const.userName_for_extjs);
		this.getGridConfig().addColumn(this.getText("性别"),
				"enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("是否有效"),
				"enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("身份证号"), "cardNo", true, true, true, Const.cardId_for_extjs);
		this.getGridConfig().addColumn(this.getText("出生日期"), "birthDate", false, true, true, "TextField", true, 10);
		this.getGridConfig().addColumn(this.getText("办公电话"), "phoneOffice", false,true,true,"TextField", true,20,Const.telephone_for_extjs);
		this.getGridConfig().addColumn(this.getText("家庭电话"), "phoneHome", false,true,true,"TextField", true,20,Const.telephone_for_extjs);
		this.getGridConfig().addColumn(this.getText("手机"), "mobilephone",true,true,true,"TextField", true,20,Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", false,true,true,"TextField", true,50,Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("工作单位"), "workplace", false, true, true, "TextField", true,100);

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

		    List<PeTeacher> plist = new ArrayList<PeTeacher>();
		    try {
			DetachedCriteria pubdc = DetachedCriteria.forClass(PeTeacher.class);
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
			    PeTeacher bulletin = (PeTeacher) this.getGeneralService().save(plist.get(k));
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
	public void setEntityClass() {
		this.entityClass = PeTeacher.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peTeacher";

	}

	public void setBean(PeTeacher instance) {
		super.superSetBean(instance);

	}

	public PeTeacher getBean() {
		return (PeTeacher)super.superGetBean();
	}

	@Override
	public void checkBeforeAdd() throws EntityException {
		
		checkIdCard();
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
			
			user.setPePriRole(this.getPePriRole());
			
			EnumConst ec = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
			user.setEnumConstByFlagIsvalid(ec);
			
			this.entityClass = SsoUser.class;
			this.getGeneralService().save(user);
			
			this.entityClass = PeTeacher.class;
			this.getBean().setSsoUser(user);
			this.getBean().setName(this.getBean().getLoginId() + "/" + this.getBean().getTrueName());
			
		}
		
	}

	@Override
	public void checkBeforeUpdate() throws EntityException {

		checkIdCard();
		PeTeacher oldBean = (PeTeacher)this.getGeneralService().getById(this.getBean().getId());
		if(!oldBean.getLoginId().equals(this.getBean().getLoginId())) {
			DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
			dc.add(Restrictions.eq("loginId", this.getBean().getLoginId()));
			
			List list = this.getGeneralService().getList(dc);
			if(list !=null && list.size() > 0) {
				throw new EntityException("'" + this.getBean().getLoginId() + "' 用户已经存在！");
			} else {
				oldBean.getSsoUser().setLoginId(this.getBean().getLoginId());
				this.getBean().setSsoUser(oldBean.getSsoUser());
				this.getBean().setName(this.getBean().getLoginId() + "/" + this.getBean().getTrueName());
			}
		}
		
	}
	
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
//						PeTeacher pt = (PeTeacher)this.getGeneralService().getById(ids[i]);
//						idList.add(pt.getSsoUser().getId());
//					}
//					
//					map = super.delete();
//					this.getGeneralService().getGeneralDao().setEntityClass(SsoUser.class);
//					this.getGeneralService().deleteByIds(idList);
//					
//					this.getGeneralService().getGeneralDao().setEntityClass(PeTeacher.class);
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
	public DetachedCriteria initDetachedCriteria() {
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeTeacher.class);
		dc.createCriteria("ssoUser", "ssoUser");
		dc.createCriteria("enumConstByGender", "enumConstByGender");
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		return dc;
		
	}
	
	private void checkIdCard() throws EntityException {
		AttributeManage manage=new WhatyAttributeManage();
		try {
			if(!manage.isValidIdcard(this.getBean().getCardNo())){
				throw new EntityException("身份证号码输入错误！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new EntityException("身份证号码输入错误！");
		}
	}
	
	private PePriRole getPePriRole() throws EntityException {
		
		DetachedCriteria dc = DetachedCriteria.forClass(PePriRole.class);
		dc.add(Restrictions.eq("name", "教师"));
		
		List list = null;
		PePriRole role = null;
		list = this.getGeneralService().getList(dc);
		if(list != null && list.size() > 0) {
			role = (PePriRole)list.get(0);
		}
			
		return role;
	}

}
