package com.whaty.platform.entity.web.action.recruit.setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeFeeLevel;
import com.whaty.platform.entity.bean.PrEduMajorSiteFeeLevel;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.fee.feeStandard.FeeStandardManagerAction;
import com.whaty.platform.util.JsonUtil;

/**
 * 设置学分收费标准
 * 
 * @author 李冰
 * 
 */
public class SetCreditBAction extends FeeStandardManagerAction {

	private String siteIds;

	@Override
	public void initGrid() {

		this.getGridConfig().setTitle(this.getText("收费标准"));
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "name");
		this.getGridConfig().addColumn(this.getText("每学分费用"), "feePercredit");
		this.getGridConfig().addColumn(this.getText("允许欠费额"), "oweFeeLimit");
		this.getGridConfig().addColumn(this.getText("是否为默认收费标准"),"enumConstByFlagDefault.name");
		this.getGridConfig().addColumn(this.getText("备注"), "note");
		this.getGridConfig().addMenuFunction(this.getText("为所选的学习中心设置学分标准"),
				"/entity/recruit/setCreditB_setCredit.action", true, true);
		this.getGridConfig().addMenuScript(this.getText("返回"),
				"{window.history.go(-2)}");
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/setCreditB";
	}

	@Override
	public String abstractList() {
		if (siteIds == null || siteIds.equals("")) {
			this.siteIds = this.getIds();
		}
		return super.abstractList();
	}

	/**
	 * 为所选择的项目设置收费标准
	 * 
	 * @return
	 */
	public String setCredit() {
		Map map = new HashMap();
		// 取得所选择的收费标准的id
		String id = this.getIds().substring(0, this.getIds().indexOf(","));
		// 取出收费标准对象
		PeFeeLevel peFeeLevel = new PeFeeLevel();
		try {

			peFeeLevel = (PeFeeLevel) this.getGeneralService().getById(id);
		} catch (EntityException e) {
			e.printStackTrace();
			map.put("success", "false");
			map.put("info", "所选收费标准错误");
			this.setJsonString(JsonUtil.toJSONString(map));
			return json();
		}
		// 取得传入的id
		String[] ids = ActionContext.getContext().getSession().get(
				"SetCreditBActionIds").toString().split(",");
		List<String> idList = new ArrayList<String>();
		for (String string : ids) {
			if (string != null && string.length() > 0) {
				idList.add(string);
			}
		}
		this.getGeneralService().getGeneralDao().setEntityClass(
				PrEduMajorSiteFeeLevel.class);
		this.getGeneralService().getGeneralDao().updateColumnByIds(idList,
				"peFeeLevel.id", peFeeLevel.getId());
		map.put("success", "true");
		map.put("info", "收费标准设置成功");
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}

	public String getSiteIds() {
		return siteIds;
	}

	public void setSiteIds(String siteIds) {
		this.siteIds = siteIds;
	}

	@Override
	public String execute() {
		// 将前一个页面选择的id保存入session中
		ActionContext.getContext().getSession().put("SetCreditBActionIds",
				this.getIds());
		return super.execute();
	}
}
