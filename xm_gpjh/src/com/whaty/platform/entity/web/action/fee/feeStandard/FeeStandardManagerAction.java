package com.whaty.platform.entity.web.action.fee.feeStandard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeFeeLevel;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.fee.FeeStandardService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

/**
 * 收费标准设置
 * 
 * @author 靳希坤
 * 
 */
public class FeeStandardManagerAction extends MyBaseAction<PeFeeLevel> {

	private static final long serialVersionUID = 5984822245464447024L;
	private FeeStandardService feeStandardService;
	@Override
	public void initGrid() {

		this.getGridConfig().setTitle(this.getText("收费标准"));
		this.getGridConfig().addMenuFunction(this.getText("设置为默认收费标准"), "/entity/fee/feeStandardManager_setToDefault.action", true, true);
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "name");
		this.getGridConfig().addColumn(this.getText("每学分费用"), "feePercredit",true, true,true, Const.fee_for_extjs);
		this.getGridConfig().addColumn(this.getText("允许欠费额"), "oweFeeLimit",true, true,true, Const.fee_for_extjs);
		this.getGridConfig().addColumn(this.getText("是否为默认收费标准"),
				"enumConstByFlagDefault.name", true, false,
				true, "");
		this.getGridConfig().addColumn(this.getText("备注"), "note",true, true,true, "TextArea",true,500);
	}

	public void setBean(PeFeeLevel instance) {
		super.superSetBean(instance);

	}

	public PeFeeLevel getBean() {
		return (PeFeeLevel) super.superGetBean();
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeFeeLevel.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/fee/feeStandardManager";
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeFeeLevel.class);
		dc.createCriteria("enumConstByFlagDefault", "enumConstByFlagDefault",
				DetachedCriteria.LEFT_JOIN);
		return dc;
	}
	
	public String setToDefault(){
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				try{
					this.getFeeStandardService().updateSetDefault(idList);
					map.put("success", "true");
					map.put("info", "设置默认学分标准成功！");
				}catch(Exception e){
					map.put("success", "false");
					map.put("info", "设置默认学分标准失败..");
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}

		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map add() {
		this.getBean().setEnumConstByFlagDefault(this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagDefault", "0"));
		return super.add();
	}

	public FeeStandardService getFeeStandardService() {
		return feeStandardService;
	}

	public void setFeeStandardService(FeeStandardService feeStandardService) {
		this.feeStandardService = feeStandardService;
	}

	@Override
	public void checkBeforeDelete(List idList) throws EntityException {
		if (this.getIds() != null && this.getIds().length() > 0) {
			String idss = "'"+this.getIds().replaceAll(",","','");
			idss = idss.substring(0,idss.length()-2);
			if(this.getGeneralService().getBySQL("select f.id from pe_fee_level f, enum_const e where f.flag_default=e.id and e.code='1' and f.id in ("+idss+")").size()>0){
				throw new  EntityException("不能删除默认收费标准！");
			}
			
		}
		super.checkBeforeDelete(idList);
	}

}
