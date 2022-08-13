package com.whaty.platform.entity.web.action.fee.pay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeFeeBatch;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.fee.statistic.PeFeeBathQueryAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

public class PrFeeDetailForTakeInAction extends PeFeeBathQueryAction {

	private static final long serialVersionUID = 275139115619252409L;
	
	@Override
	public void initGrid() {
		if(this.getGridConfig().checkBeforeAddMenu("/entity/fee/prFeeDetailForTakeIn_addPici.action")){
		this.getGridConfig().addMenuScript("新建批次",
				"{Ext.Ajax.request({url:'/entity/fee/prFeeDetailForTakeIn_addPici.action',                           " +
				" 				method:'post',                                                                       " +
				" 				waitMsg:'" + this.getText("test.inProcessing") + "',                                 " +
				" 				success: function(response, options){                                                " +
				" 					var responseArray = Ext.util.JSON.decode(response.responseText);                 " +
				" 					if(responseArray.success=='true'){                                               " +
				" 						Ext.MessageBox.alert('信息','新建批次成功&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');" +
				" 						}                                                                            " +
				" 					else {                                                                           " +
				" 						Ext.MessageBox.alert('错误', '新建批次失败&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');" +
				" 						}                                                                             " +
				" 					store.load({params:{start:g_start,limit:g_limit}});                               " +
				" 					}                                                                                 " +
				" 				});}");
		}
		this.getGridConfig().addMenuFunction(this.getText("上报"), "/entity/fee/prFeeDetailForTakeIn_report.action", false, true);
		this.getGridConfig().addMenuFunction(this.getText("取消上报"), "/entity/fee/prFeeDetailForTakeIn_cancelReport.action", false, true);
		
		this.getGridConfig().setCapability(false, true, false,true);
		this.getGridConfig().setTitle(this.getText("交费批次管理"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("批次号"),"name");
		this.getGridConfig().addColumn(this.getText("所属学习中心"),"peSite.name");
		this.getGridConfig().addColumn(this.getText("上报人"), "peSitemanager.trueName");
//		this.getGridConfig().addColumn(this.getText("上报状态"), "enumConstByFlagFeeCheck.name");
		ColumnConfig column2 = new ColumnConfig(this.getText("上报状态"),"enumConstByFlagFeeCheck.name");
		column2.setComboSQL("select id,name from enum_const where namespace='FlagFeeCheck' and code in('0','1','2')");
		column2.setAdd(false);
		this.getGridConfig().addColumn(column2);
		this.getGridConfig().addColumn(this.getText(""), "enumConstByFlagFeeCheck.code",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("上报时间"), "inputDate");
		this.getGridConfig().addColumn(this.getText("总金额"), "feeAmountTotal");
		this.getGridConfig().addColumn(this.getText("费用条目数量"), "feeRecordNum");
		
		this.getGridConfig().addRenderScript(
				this.getText("学生交费明细"), 
				"{if (record.data['enumConstByFlagFeeCheck.code']=='0') return '<a href=/entity/fee/prFeeDetailPici.action?onlyRead=false&peFeeBatch_id=' + record.data['id'] + ' ><font color=blue>修改</font></a>'; else return '<a href=/entity/fee/prFeeDetailPici.action?onlyRead=true&peFeeBatch_id=' + record.data['id'] + ' ><font color=blue>查看</font></a>';}","");
		//this.getGridConfig().addRenderFunction(
		//		this.getText("学生交费信息"),
		//		"<a href='/entity/fee/prFeeDetailPici.action?onlyRead=false&peFeeBatch_id=\" + record.data['id'] + \"' target='_blank'><font color='blue'>修改</font></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='/entity/fee/prFeeDetailPici.action?onlyRead=true&peFeeBatch_id=\" + record.data['id'] + \"' target='_blank'><font color='blue'>查看</font></a>");
	}



	@Override
	public void setServletPath() {
		this.servletPath = "/entity/fee/prFeeDetailForTakeIn";
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeFeeBatch.class);
		dc.createAlias("peSitemanager", "peSitemanager", DetachedCriteria.LEFT_JOIN)
			.createAlias("enumConstByFlagFeeCheck", "enumConstByFlagFeeCheck");
		dc.add(Restrictions.or(Restrictions.eq("enumConstByFlagFeeCheck.code", "0"), Restrictions.eq("enumConstByFlagFeeCheck.code", "1")));
		dc.createAlias("peSite", "peSite", DetachedCriteria.LEFT_JOIN);
//		return super.initDetachedCriteria().add(Restrictions.or(Restrictions.eq("enumConstByFlagFeeCheck.code", "0"), Restrictions.eq("enumConstByFlagFeeCheck.code", "1")));
		return dc;
	}
	
	@SuppressWarnings("unchecked")
	public String addPici(){
		this.setBean(new PeFeeBatch());
		this.getBean().setName(createPici());
		//TODO 添加session中站点管理员的信息,分站新建 设置所属学习中心
		UserSession us = (UserSession)ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if(us!=null){
			if(us.getUserLoginType().equals("2")){
				DetachedCriteria dc = DetachedCriteria.forClass(PeSitemanager.class);
				dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
				List<PeSitemanager> list;
				try {
					list = this.getGeneralService().getList(dc);
					if(list!=null&&list.size()>0){
						this.getBean().setPeSite(list.get(0).getPeSite());
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}

			}
		}
		this.getBean().setPeSitemanager(null);
		this.getBean().setEnumConstByFlagFeeCheck(this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "0"));
		this.getBean().setFeeAmountTotal((double)0);
		this.getBean().setFeeRecordNum(0l);
		java.util.Map map = super.add();
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}
	
	public String report(){
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
					if(this.getPeFeeBatchService().updateForReport(idList)>0){
						map.put("success", "true");
						map.put("info", "上报交费成功!");
					}else{
						map.put("success", "false");
						map.put("info", "上报交费失败...");
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
	
	public String cancelReport(){
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
					if(this.getPeFeeBatchService().updateCancelReport(idList)>0){
						map.put("success", "true");
						map.put("info", "取消上报成功!");
					}else{
						map.put("success", "false");
						map.put("info", "取消上报失败...");
					}
				}catch(Exception e){
					map.put("success", "false");
					map.put("info", e.getMessage());
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择所要取消上报的批次！");
			}
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}
	
	public String createPici(){
		return new java.text.SimpleDateFormat("yyyyMMddHHmmssS").format(new java.util.Date());
	}


}
