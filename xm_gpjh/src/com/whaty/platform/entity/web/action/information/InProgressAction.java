package com.whaty.platform.entity.web.action.information;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

/**
 * @param
 * @version 创建时间：2009-6-26 上午01:34:14
 * @return
 * @throws PlatformException
 * 类说明
 */
public class InProgressAction extends MyBaseAction {
	
	private List<String[]> infoList;
	
	public String test() {
		return "construct";
	}
	
	public String print() throws EntityException {
		
		ActionContext axt = ActionContext.getContext();
		String str = (String)axt.getSession().get("studentIds");
		
		String[] ids = null;
		if(str != null) {
			ids = str.split(",");
			infoList = new ArrayList<String[]>();
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(String id : ids) {
				PeTrainee pt = (PeTrainee)this.getGeneralService().getById(id);
				if(pt != null) {
					String[] info = new String[4];
					info[0] = pt.getTrueName();
					info[1] = pt.getPeTrainingClass().getName();
					info[2] = pt.getEnumConstByTrainingType().getName();
					info[3] = sdf.format(new Date());
					infoList.add(info);
				}
			}
		} catch (Exception e) {
			throw new EntityException("证书打印失败！");
		}
		System.out.println(infoList);
		return "print";
	}
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().setTitle(this.getText("证书打印"));

		this.getGridConfig().addColumn(this.getText("ID"), "peTrainee.id", false);
		this.getGridConfig().addColumn(this.getText("报名号"), "peTrainee.userName");
		this.getGridConfig().addColumn(this.getText("用户名"), "peTrainee.loginId");
		this.getGridConfig().addColumn(this.getText("姓名"), "peTrainee.trueName");
		this.getGridConfig().addColumn(this.getText("身份证号"), "peTrainee.cardNo");
		this.getGridConfig().addColumn(this.getText("证书类型"), "enumConstByApplyType.name");
		this.getGridConfig().addColumn(this.getText("学员状态"), "peTrainee.enumConstByStatus.name");
		this.getGridConfig().addColumn(this.getText("所属培训班"), "peTrainee.peTrainingClass.name");
		this.getGridConfig().addColumn(this.getText("培训级别"), "peTrainee.enumConstByTrainingType.name");
		
		this.getGridConfig().addMenuScript("打印",
				"{var m = grid.getSelections();                                                                                                                " + 
				" if(m.length > 0){                                                                                                                            " +
				" 	Ext.MessageBox.confirm('" + this.getText("test.confirm") + "',                                                                         " +
				" 	'" + this.getText("test.sureTodo") + "',                                                                                               " +
				" 	function(btn) {                                                                                                                        " +
				" 		if(btn == 'yes'){                                                                                                              " +
				" 			var jsonData = '';                                                                                                     " +
				" 			for(var i = 0, len = m.length; i < len; i++){                                                                          " +
				" 				var ss =  m[i].get('peTrainee.id');                                                                                      " +
				" 				if(i==0)                                                                                                       " +
				" 				jsonData = jsonData + ss ;                                                                                     " +
				" 				else jsonData = jsonData + ',' + ss;                                                                           " +
				" 				}                                                                                                              " +
				" 				jsonData=jsonData+',';                                                                                         " +
				" 				Ext.Ajax.request({url:'/entity/information/inProgress_saveIdsToSession.action',                        " +
				" 				params:{ids:jsonData,column:'id',value:'1'},                                                                   " +
				" 				method:'post',                                                                                                 " +
				" 				waitMsg:'" + this.getText("test.inProcessing") + "',                                                           " +
				" 				success: function(response, options){                                                                          " +
				" 					var responseArray = Ext.util.JSON.decode(response.responseText);                                       " +
				" 					if(responseArray.success=='true'){                                                                     " +
				" 						window.open('/entity/information/inProgress_print.action');                  " +
				" 						}                                                                                              " +
				" 					else {                                                                                                 " +
				" 						Ext.MessageBox.alert('错误', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');     " +
				" 						}                                                                                              " +
				" 					}                                                                                                      " +
				" 					});                                                                                                    " +
				" 			}                                                                                                                      " +
				" 		} );                                                                                                                           " +
				" }                                                                                                                                            " +
				" else {                                                                                                                                       " +
				" 	Ext.MessageBox.alert('" + this.getText("test.error") + "',                                                                             " +
				" 	'" + this.getText("test.pleaseSelectAtLeastOneItem") + "');                                                                            " +
				" 	} }                                                                                                                                     " );
		
	
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeTrainee.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/inProgress";
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc1 = DetachedCriteria.forClass(SystemApply.class);
		dc1.createCriteria("enumConstByFlagApplyStatus","enumConstByFlagApplyStatus");
		dc1.add(Restrictions.eq("enumConstByFlagApplyStatus.code", "1"));
		dc1.createCriteria("enumConstByApplyType","enumConstByApplyType");
		dc1.add(Restrictions.or(Restrictions.or(Restrictions.eq("enumConstByApplyType.code", "22"), Restrictions.eq("enumConstByApplyType.code", "23")), Restrictions.eq("enumConstByApplyType.code", "24")));
		DetachedCriteria dc = dc1.createCriteria("peTrainee","peTrainee");
	
		dc.createCriteria("enumConstByStatus", "enumConstByStatus",
				DetachedCriteria.LEFT_JOIN);	

		dc.createCriteria("peTrainingClass", "peTrainingClass",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByTrainingType", "enumConstByTrainingType",DetachedCriteria.LEFT_JOIN);

		return dc;
	}

	public List<String[]> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<String[]> infoList) {
		this.infoList = infoList;
	}
	
	public String saveIdsToSession(){
		
		if (this.getIds() != null && this.getIds().length() > 0) {
			ActionContext axt = ActionContext.getContext();
			axt.getSession().put("studentIds", this.getIds());
		}
		Map map = new HashMap();
		
		map.put("success", "true");
		map.put("info", "success");

		this.setJsonString(JsonUtil.toJSONString(map));
		
		return json();
	}

}
