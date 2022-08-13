package com.whaty.platform.entity.web.action.programApply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeValuaExpert;
import com.whaty.platform.entity.bean.PrProExpert;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.programApply.ProgramApplyService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

/**
 * 指定评审专家
 * 
 * @author 侯学龙
 *
 */
public class SearchExpertForDistributeAction extends MyBaseAction {

	private static final long serialVersionUID = 1L;
	
	private String applyIds;
	
	private String method;
	
	private ProgramApplyService programApplyService;
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false, true, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name", true, true, true, "", false, 20);
		this.getGridConfig().addColumn(this.getText("状态"), "enumConstByFkStatusValuate.name", true, true, true, "TextField", false, 20);
		this.getGridConfig().addColumn(this.getText("工作单位"), "workplace", true, true, true, "", false, 40);
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("民族"), "folk", true, true, true, "", true, 200);
		this.getGridConfig().addColumn(this.getText("手机"), "telephone",false, true, true,"",true,200,Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("出生年月"), "birthyearmonth", true, true, true, "", true, 200);
		this.getGridConfig().addColumn(this.getText("邮箱"), "email", true, true, true, "", true, 200,Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("所学专业"), "major",false, true, true,"",true,200);
		this.getGridConfig().addColumn(this.getText("办公电话"), "officePhone",false, true, true,"",true,200,Const.phone_number_for_extjs);
		this.getGridConfig().addColumn(this.getText("年龄"),"age",false,true,false,"",true,10,Const.number_for_extjs);
		
		this.getGridConfig().addColumn(this.getText("学历"), "education",true,true,false,"",true,200);
		this.getGridConfig().addColumn(this.getText("政治面貌"), "politics",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("职务"), "zhiwu",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("职称"), "zhicheng",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("研究专长"), "researchArea",false,true,false,"",true,2000);
		this.getGridConfig().addColumn(this.getText("教学专长"), "trainingArea",false,true,false,"",true,2000);
		this.getGridConfig().addColumn(this.getText("身份证号"), "idcard",false,true,false,"",true,20);
		this.getGridConfig().addColumn(this.getText("通讯地址"), "address",false,true,false,"",true,200);
		this.getGridConfig().addColumn(this.getText("邮编"), "zip",false,true,false,"",true,200,Const.zip_for_extjs);
		this.getGridConfig().addColumn(this.getText("住宅电话"), "homePhone",false,true,true,"",true,200,Const.phone_number_for_extjs);
		this.getGridConfig().addColumn(this.getText("传真"), "fax",false,true,true,"",true,20,Const.phone_number_for_extjs);
//		this.getGridConfig().addColumn(this.getText("个人简历"), "personalResume",false,false,false,"TextArea",true,10000);
//		this.getGridConfig().addColumn(this.getText("教育教学成果"), "trainingResult",false,false,false,"TextArea",true,10000);
//		this.getGridConfig().addColumn(this.getText("教师培训经验"), "trainingExperience",false,false,false,"TextArea",true,10000);
//		this.getGridConfig().addColumn(this.getText("其他需要说明事项"), "otherItems",false,false,false,"TextArea",true,10000);
//		this.getGridConfig().addColumn(this.getText("所在单位意见"), "unitComment",false,false,false,"TextArea",true,10000);
//		this.getGridConfig().addColumn(this.getText("推荐部门意见"), "recommendComment",false,false,false,"TextArea",true,10000);
//		this.getGridConfig().addColumn(this.getText("教育部师范司组织专家审核意见"), "finalComment",false,false,false,"TextArea",true,10000);
		this.getGridConfig().addColumn(this.getText("备注"), "note", false,false,true, "TextArea", true, 10000);
		this.getGridConfig().addRenderFunction(this.getText("浏览"),
				"<a href=\"#\" onclick=\"window.open('/entity/basic/peValuaExpertAction_browseDetail.action?bean.id=${value}','','toolbar=no,menubar=no,resizable=yes,scrollbars=yes')\">浏览</a>",
				"id");
		if(this.getMethod().equals("set")){
			this.getGridConfig().setTitle("指定评审专家");
			this.getGridConfig().addMenuFunction(this.getText("指定为评审专家"),"distribute","");
		}else if(this.getMethod().equals("get")){
			this.getGridConfig().setTitle("取消评审专家");
			this.getGridConfig().addMenuFunction(this.getText("取消评审专家"),"cancel","");
		}
		this.getGridConfig().addMenuScript(this.getText("test.back"),"{window.history.back();}");
	}

	@Override
	public void setEntityClass() {

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/programApply/searchExpertForDistribute";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(PeValuaExpert.class);
		dCriteria.createCriteria("ssoUser", "ssoUser", DetachedCriteria.LEFT_JOIN);
		dCriteria.createCriteria("enumConstByFkGender", "enumConstByFkGender",DetachedCriteria.LEFT_JOIN);
		dCriteria.createCriteria("enumConstByFkStatusValuate", "enumConstByFkStatusValuate",DetachedCriteria.LEFT_JOIN);
		dCriteria.add(Restrictions.in("enumConstByFkStatusValuate.code", new String[]{"56215","56216"}));
		DetachedCriteria expcetdc = DetachedCriteria.forClass(PrProExpert.class);
		expcetdc.setProjection(Projections.distinct(Property.forName("peValuaExpert.id")));
		expcetdc.createCriteria("peProApply","peProApply");
		String[] applyIds = getApplyIds().split(",");
		expcetdc.add(Restrictions.in("peProApply.id", applyIds));
//		if (method.equals("set")) {
//			dCriteria.add(Subqueries.propertyNotIn("id", expcetdc));
//		}
		if (this.getMethod().equals("get")) {
			dCriteria.add(Subqueries.propertyIn("id", expcetdc));
		}
		return dCriteria;
	}
	@Override
	public Map updateColumn() {
		Map map = new HashMap();
		int count = 0;
		if (this.getIds() != null && this.getIds().length() > 0){
			String[] ids = getIds().split(",");
			List idList = new ArrayList();			
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			String[] applyIds = getApplyIds().split(",");
			List applyIdList = new ArrayList();			
			for (int i = 0; i < applyIds.length; i++) {
				applyIdList.add(applyIds[i]);
			}
			try {
				checkBeforeUpdateColumn(idList);
			} catch (EntityException e) {
				map.clear();
				map.put("success", "false");
				map.put("info", e.getMessage());
				return map;
			}
			try {
				if(this.getColumn().equals("distribute")){
					count = this.getProgramApplyService().distributeValueExpert(idList,applyIdList);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"个项目成功指定专家!"));
				}else if(this.getColumn().equals("cancel")){
					count = this.getProgramApplyService().cancelValueExpert(idList,applyIdList);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"个项目成功取消被指定的专家!"));
				}
			} catch (EntityException e) {
				map.put("success", "false");
				map.put("info", e.getMessage());
				return map;
			}

		} else {
			map.put("success", "false");
			map.put("info", "parameter value error");
		}
		return map;
	}
	public String getApplyIds() {
		return applyIds != null ? applyIds : (String)ActionContext.getContext().getSession().get("applyIds");
	}

	public void setApplyIds(String applyIds) {
		ActionContext.getContext().getSession().put("applyIds", applyIds);
		this.applyIds = applyIds;
	}

	public ProgramApplyService getProgramApplyService() {
		return programApplyService;
	}

	public void setProgramApplyService(ProgramApplyService programApplyService) {
		this.programApplyService = programApplyService;
	}

	public String getMethod() {
		return  method != null ? method : (String)ActionContext.getContext().getSession().get("method");
	}

	public void setMethod(String method) {
		ActionContext.getContext().getSession().put("method", method);
		this.method = method;
	}

}
