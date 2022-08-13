package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

public class CourseHomeworkAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("课程作业形式设置"));
		this.getGridConfig().addColumn("id", "id",false);
		this.getGridConfig().addColumn(this.getText("课程编号"), "code");
		this.getGridConfig().addColumn(this.getText("课程名"), "name");
		if(this.getGridConfig().checkBeforeAddMenu("/entity/teaching/courseHomework_saveIdsToSession.action")){
			this.getGridConfig().addMenuScript(this.getText("设置作业形式"), "{var m = grid.getSelections();if(m.length > 0){Ext.MessageBox.confirm('" + this.getText("test.confirm") + "', '" + this.getText("test.sureTodo") + "',    function(btn) {if(btn == 'yes'){var jsonData = '';for(var i = 0, len = m.length; i < len; i++){        var ss =  m[i].get('id');if(i==0)        jsonData = jsonData + ss ;   else jsonData = jsonData + ',' + ss;        }        jsonData=jsonData+',';Ext.Ajax.request({url:'/entity/teaching/courseHomework_saveIdsToSession.action',params:{ids:jsonData,column:'id',value:'1'},        method:'post',        waitMsg:'" + this.getText("test.inProcessing") + "',success: function(response, options) {    var responseArray = Ext.util.JSON.decode(response.responseText);      if(responseArray.success=='true'){   window.location = '/entity/teacher/courseItem_manage.jsp';    } else {    Ext.MessageBox.alert('错误', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');    }    store.load({params:{start:g_start,limit:g_limit}});}        }); }    } );    } else {Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("test.pleaseSelectAtLeastOneItem") + "');} }");
		}
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeTchCourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/courseHomework";
	}
	
	public PeTchCourse getBean() {
		return (PeTchCourse)super.superGetBean();
	}

	public void setBean(PeTchCourse instance) {
		super.superSetBean(instance);
	}

	public String saveIdsToSession() {
		if (this.getIds() != null && this.getIds().length() > 0) {
			ActionContext axt = ActionContext.getContext();
			axt.getSession().put("courseIds", this.getIds());
		}

		Map map = new HashMap();
		
		map.put("success", "true");
		map.put("info", "setIds OK");

		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchCourse.class);
		return dc;
	}
	
	
}
