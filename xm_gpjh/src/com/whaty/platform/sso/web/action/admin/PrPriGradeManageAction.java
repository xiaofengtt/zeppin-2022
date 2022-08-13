package com.whaty.platform.sso.web.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.bean.PrPriManagerGrade;
import com.whaty.platform.sso.exception.SsoException;
import com.whaty.platform.sso.service.PrPriManagerGradeService;

public class PrPriGradeManageAction extends MyBaseAction {

	private PrPriManagerGrade bean;
	
	private PrPriManagerGradeService PrPriManagerGradeService;
	
	public PrPriManagerGrade getBean() {
		return bean;
	}

	public void setBean(PrPriManagerGrade bean) {
		this.bean = bean;
	}

	public PrPriManagerGradeService getPrPriManagerGradeService() {
		return PrPriManagerGradeService;
	}

	public void setPrPriManagerGradeService(
			PrPriManagerGradeService PrPriManagerGradeService) {
		this.PrPriManagerGradeService = PrPriManagerGradeService;
	}

	@Override
	public Map add() {
		this.setBean((PrPriManagerGrade)super.setSubIds(this.getBean()));
		
		PrPriManagerGrade instance = null;
		Map map = new HashMap();
		try {
			instance = this.getPrPriManagerGradeService().save(this.getBean());
			map.put("success", "true");
			map.put("info", this.getText("test.add")+this.getText("test.success"));
		} catch (Exception e) {
			map.put("success", "false");
			map.put("info", this.getText("test.add")+this.getText("test.failed"));
		}
		return map;
	}

	@Override
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
				
				try{
					int count =this.getPrPriManagerGradeService().deleteByIds(idList);
					if (count>0) {
						map.put("success", "true");
						map.put("info", this.getText("test.delete") + this.getText("test.success"));
					} else {
						map.put("success", "false");
						map.put("info", this.getText("test.delete") + this.getText("test.failed"));
					}
				}catch(SsoException e){
					map.put("success", "false");
					map.put("info", this.getText("test.delete") + this.getText("test.failed"));
				}
				
				
			}
		} else {
			map.put("success", "false");
			map.put("info", this.getText("test.delete") + this.getText("test.failed"));
		}

		return map;
	}

	@Override
	public void init() {
		this.setServletPath("");
		
		this.getGridConfig().setTitle(this.getText("test.gridtitle"));

		this.getGridConfig().addColumn(this.getText("test.id"), "id", false);
		this.getGridConfig().addColumn(this.getText("test.name"), "ssoUser.name");
		this.getGridConfig().addColumn(this.getText("CheckStatus"), "peEdutype.name");
	}

	@Override
	public Page list() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrPriManagerGrade.class);
		dc.createCriteria("ssoUser", "ssoUser");
		dc.createCriteria("peEdutype", "peEdutype");
		
		dc = super.setDetachedCriteria(dc, this.getBean());
		return getPrPriManagerGradeService().getByPage(dc, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
	}

	@Override
	public Map update() {
		PrPriManagerGrade dbInstance = this.getPrPriManagerGradeService().getById(this.getBean().getId());
		
		this.setBean((PrPriManagerGrade)super.setSubIds(dbInstance, this.getBean()));
		
		PrPriManagerGrade instance = null;
		Map map = new HashMap();
		try {
			instance = this.getPrPriManagerGradeService().save(this.getBean());
			map.put("success", "true");
			map.put("info", this.getText("test.save")+this.getText("test.success"));
		} catch (Exception e) {
			map.put("success", "false");
			map.put("info", this.getText("test.save")+this.getText("test.failed"));
		}
		return map;
	}

	@Override
	public Map updateColumn() {
		// TODO Auto-generated method stub
		return null;
	}

}
