package com.whaty.platform.entity.web.action.fee.pay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeFeeBatch;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.fee.statistic.PeFeeBathQueryAction;
import com.whaty.platform.util.JsonUtil;

public class PeFeeBathCheckAction extends PeFeeBathQueryAction {

	private static final long serialVersionUID = 1816279298978242070L;

	@Override
	public void initGrid() {
		
		this.getGridConfig().addMenuFunction(this.getText("交费审核通过"), "/entity/fee/peFeeBathCheck_check.action", false, true);
		this.getGridConfig().addMenuFunction(this.getText("取消审核"), "/entity/fee/peFeeBathCheck_cancelCheck.action", false, true);
		
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("交费批次审核"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("批次号"),"name");
		this.getGridConfig().addColumn(this.getText("所属学习中心"),"peSite.name");
		this.getGridConfig().addColumn(this.getText("上报人"), "peSitemanager.trueName");
//		this.getGridConfig().addColumn(this.getText("上报状态"), "enumConstByFlagFeeCheck.name");
		ColumnConfig column2 = new ColumnConfig(this.getText("上报状态"),"enumConstByFlagFeeCheck.name");
		column2.setComboSQL("select id,name from enum_const where namespace='FlagFeeCheck' and code in('0','1','2')");
		this.getGridConfig().addColumn(column2);
		this.getGridConfig().addColumn(this.getText(""), "enumConstByFlagFeeCheck.code",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("上报时间"), "inputDate");
		this.getGridConfig().addColumn(this.getText("总金额"), "feeAmountTotal");
		this.getGridConfig().addColumn(this.getText("费用条目数量"), "feeRecordNum");		
		//this.getGridConfig().addRenderFunction(this.getText("学生交费明细"), "<a href=\"peFeeDetailForReciept.action?bean.peFeeBatch.id=${value}\" >查看详细信息</a>", "id");
		this.getGridConfig().addRenderScript(
				this.getText("学生交费明细"),
				"{if(record.data['enumConstByFlagFeeCheck.code']=='1') return '<a href=peFeeDetailForReciept.action?msg=msg&bean.peFeeBatch.id='+record.data['id']+'>查看详细信息</a>';else return '<a href=peFeeDetailForReciept.action?msg=msg&bean.peFeeBatch.id='+record.data['id']+'>查看详细信息</a>';}", "");
	}	

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/fee/peFeeBathCheck";
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeFeeBatch.class);
		dc.createAlias("peSitemanager", "peSitemanager", DetachedCriteria.LEFT_JOIN)
			.createAlias("enumConstByFlagFeeCheck", "enumConstByFlagFeeCheck");
		dc.add(Restrictions.or(Restrictions.eq("enumConstByFlagFeeCheck.code", "1"),
				Restrictions.eq("enumConstByFlagFeeCheck.code", "2")));
		dc.createAlias("peSite", "peSite", DetachedCriteria.LEFT_JOIN);
		return dc;
	}
	
	public String check(){
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
					if(this.getPeFeeBatchService().updateForCheck(idList)>0){
						map.put("success", "true");
						map.put("info", "审核交费成功!");
					}else{
						map.put("success", "false");
						map.put("info", "审核交费失败...");
					}
				}catch(Exception e){
					map.put("success", "false");
					map.put("info", e.getMessage());
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择所要上报的批次！");
			}
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}
	
	public String cancelCheck(){
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
					if(this.getPeFeeBatchService().updateCancelCheck(idList)>0){
						map.put("success", "true");
						map.put("info", "取消审核成功!");
					}else{
						map.put("success", "false");
						map.put("info", "取消审核失败...");
					}
				}catch(Exception e){
					map.put("success", "false");
					map.put("info", e.getMessage());
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择所要取消审核的批次！");
			}
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}
}
