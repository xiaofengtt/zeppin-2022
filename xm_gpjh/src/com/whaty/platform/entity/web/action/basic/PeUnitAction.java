package com.whaty.platform.entity.web.action.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

/**
 * 单位管理
 * 
 * @author 赵玉晓
 * 
 */
public class PeUnitAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(true, true, true, true, true);
		this.getGridConfig().setTitle(this.getText("单位管理"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "name");
		this.getGridConfig().addColumn(this.getText("编号"), "code",true, true, true, Const.fiveNum_for_extjs);
		this.getGridConfig().addColumn(this.getText("是否有效"),"enumConstByFlagIsvalid.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("单位级别"),"enumConstByFkUnitType.name");
		this.getGridConfig().addColumn(this.getText("省份"), "peProvince.name",true,true,true,"TextField",false,20);
		this.getGridConfig().addColumn(this.getText("备注"), "note", true,true,true, "TextArea", true, 2000);
		this.getGridConfig().addMenuFunction(this.getText("设为有效"), "checkupPass");
		this.getGridConfig().addMenuFunction(this.getText("设为无效"), "checkupNotPass");

	}
	
	@Override
	public void checkBeforeAdd() throws EntityException {
		if(this.getBean().getName().length()>25){
			throw new EntityException("名称不能超过25个字符！");
		}
		String sql = "select t.id from pe_unit t where t.code=:code";
		Map map = new HashMap();
		map.put("code", this.getBean().getCode());
		List list = this.getGeneralService().getBySQL(sql,map);
		if(list!=null&&!list.isEmpty()){
			throw new EntityException("编号已经存在！");
		}
		this.getBean().setEnumConstByFlagIsvalid(this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1"));
	}

	@Override
	public void checkBeforeUpdate() throws EntityException {
		String sql = "select t.id from pe_unit t where t.code=:code and t.id !=:id";
		Map map = new HashMap();
		map.put("code", this.getBean().getCode());
		map.put("id", this.getBean().getId());
		List list = this.getGeneralService().getBySQL(sql,map);
		if(list!=null&&!list.isEmpty()){
			throw new EntityException("编号已经存在！");
		}
	}

	@Override
	public Map<String,String> updateColumn() {
		Map<String, String> msgMap = new HashMap<String, String>();
		msgMap.put("success", "false");
		msgMap.put("info", "操作失败");
		
		String action = this.getColumn();
		if(getIds() != null && getIds().length() != 0) {
			String[] ids = this.getIds().split(",");
			List<PeUnit> unitList = null;
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PeUnit.class);
			detachedCriteria.add(Restrictions.in("id", ids));
			try {
				unitList = this.getGeneralService().getList(detachedCriteria);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			EnumConst enumConst = null;
			for (PeUnit unit : unitList) {
				if("checkupPass".equals(action)) {
					enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
					updateSsoFlagIsValid(unit,"2");
				}else if("checkupNotPass".equals(action)) {
					enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0");
					updateSsoFlagIsValid(unit,"3");
				}
				unit.setEnumConstByFlagIsvalid(enumConst);
			}
			try {
				this.getGeneralService().saveList(unitList);
			} catch (EntityException e) {
				e.printStackTrace();
				return msgMap;
			}
			if("checkupPass".equals(action)) {
				msgMap.put("info", unitList.size()+"个单位被设置为有效。");
			}else {
				msgMap.put("info", unitList.size()+"个单位被设置为无效。");
			}
		}
		msgMap.put("success", "true");
		
		return msgMap;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeUnit.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peUnitAction";
	}

	public void setBean(PeUnit instance) {
		super.superSetBean(instance);
	}

	public PeUnit getBean() {
		return (PeUnit) super.superGetBean();
	}


	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeUnit.class);
		dc.createCriteria("peProvince", "peProvince", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFkUnitType", "enumConstByFkUnitType",DetachedCriteria.LEFT_JOIN);
		return dc;
	}
	/**
	 * 将用户账号设置为 有效或无效
	 * @param unit
	 * @param value
	 */
	public void updateSsoFlagIsValid(PeUnit unit,String value){
		String sql = "update sso_user u  set u.flag_isvalid = '"+value+"' where u.id in  (select m.fk_sso_user_id as id" +
				" from pe_manager m  where m.fk_unit = '"+unit.getId()+"')        ";
		try {
			this.getGeneralService().executeBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
}
