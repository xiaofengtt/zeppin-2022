package com.whaty.platform.entity.web.action.programApply;

import java.io.File;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

/**
 * 项目培训项目管理
 * @author houxuelong
 *
 */
public class PeProApplynoAction extends MyBaseAction {

	private static final long serialVersionUID = 1L;

	@Override
	public void initGrid() {

		this.getGridConfig().setTitle(this.getText("培训项目管理"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("培训项目名称"), "name", true, true, true, "TextField", false,	90, null);
		this.getGridConfig().addColumn(this.getText("培训项目编号"), "code",true,true,true,Const.twoNum_for_extjs);
		this.getGridConfig().addColumn(this.getText("所属年度"), "year",true,true,true,Const.year_for_extjs);
		this.getGridConfig().addColumn(this.getText("项目类型"), "enumConstByFkProgramType.name");
		this.getGridConfig().addColumn(this.getText("项目申报书格式"), "replyBook",false,true,false,"File",true,50);
		this.getGridConfig().addColumn(this.getText("申报时限"), "deadline",true,true,true,"Date",false,20);
		this.getGridConfig().addColumn(this.getText("活动状态"), "enumConstByFkProgramStatus.name",false,false,true,"",false,50);
//		this.getGridConfig().addRenderScript(this.getText("申报书状态"),
//				"{if (${value}=='1') return '活动'; else return'不活动';}",
//				"enumConstByFkProgramStatus.code");
		this.getGridConfig().addColumn(this.getText("申报学科上限"), "limit",false,true,true,Const.twoNum_for_extjs2);
		this.getGridConfig().addColumn(this.getText("人均经费标准"), "feeStandard",false,true,true,Const.fee_for_extjs);
		this.getGridConfig().addColumn(this.getText("是否需要省级审核"), "enumConstByFkProvinceCheck.name",false,true,true,"",false,50);
		this.getGridConfig().addColumn(this.getText("学时"), "classHour",false,true,true,"",false,3,Const.scoreLine_for_extjs);
		this.getGridConfig().addColumn(this.getText("备注"), "note",false,true,true,"",true,200);
		this.getGridConfig().addRenderFunction(this.getText("设置申报单位"), "<a href=\"/entity/programApply/prProgramUnit.action?peProApplynoId=${value}&method=set\" target=\"_self\"><u><font color=blue>设置</font></u></a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("查看申报单位"), "<a href=\"/entity/programApply/prProgramUnit.action?peProApplynoId=${value}&method=get\" target=\"_self\"><u><font color=blue>查看</font></u></a>", "id");
		this.getGridConfig().addRenderScript("项目申报书格式","{if(${value}==null||${value}=='') return '未上传'; else return '<a href='+${value}+' target=\\'_blank\\'>下载查看</a>';}", "replyBook");
		this.getGridConfig().addMenuFunction(this.getText("设置为自动"),
				"enumConstByFkProgramStatus.id",
				this.getMyListService().getEnumConstByNamespaceCode("FkProgramStatus", "2").getId());
		this.getGridConfig().addMenuFunction(this.getText("设置为活动状态"),
				"enumConstByFkProgramStatus.id",
				this.getMyListService().getEnumConstByNamespaceCode("FkProgramStatus", "1").getId());
		this.getGridConfig().addMenuFunction(this.getText("设置为非活动状态"),
				"enumConstByFkProgramStatus.id",
				this.getMyListService().getEnumConstByNamespaceCode("FkProgramStatus", "0").getId());
		
		this.getGridConfig().addRenderFunction(this.getText("实施方案"), "<a href='${value}' target='_blank'>下载</a>", "scheme");
		this.getGridConfig().addRenderFunction(this.getText("查看已分配管理员"), "<a href='/entity/basic/prSsoProAction.action?applyIds=${value}&method=get' target='_self'>查看</a>", "id");
		this.getGridConfig().addMenuScript(this.getText("指定项目管理员"), 
					"{var m = grid.getSelections();  "+
						"if(m.length > 0){	         "+
						"Ext.MessageBox.confirm(\"请确认\",\"是否真的要给指定的项目分配项目管理员?\",function(button,text){ " +
						" if(button=='yes'){  "+
						"	var jsonData = '';       "+
						"	for(var i = 0, len = m.length; i < len; i++){"+
						"		var ss =  m[i].get('id');"+
						"		if(i==0)	jsonData = jsonData + ss ;"+
						"		else	jsonData = jsonData + ',' + ss;"+
						"	}                        "+
						"	document.getElementById('user-defined-content').style.display='none'; "+
						"	document.getElementById('user-defined-content').innerHTML=\"<form target='_self' action='/entity/basic/prSsoProAction.action' method='post' name='formx1' style='display:none'>" +
						"   <input name=applyIds type=hidden value='\"+jsonData+\"' />"+
						"   <input name=method type=hidden value='set' /></form>\";"+
						"	document.formx1.submit();"+
						"	document.getElementById('user-defined-content').innerHTML=\"\";  "+
						" }});"+
						"} else {                    "+
						"Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("test.pleaseSelectAtLeastOneItem") + "');  "  +                    
		"}}                         ");
		
		this.getGridConfig().addRenderFunction(this.getText("查看评分指标"), "<a href=\"/gpjh/list.action?proid=${value}&method=get\" target=\"_self\"><u><font color=blue>查看</font></u></a>", "id");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeProApplyno.class;
	}
	public void setBean(PeProApplyno instance) {
		super.superSetBean(instance);

	}

//	@Override
//	public void checkBeforeUpdate() throws EntityException {
//		String sql = "select t.id from pe_pro_applyno t where t.code=:code and t.year='"+Const.getYear()+"' and t.id !=:id";
//		Map map = new HashMap();
//		map.put("code", this.getBean().getCode());
//		map.put("id", this.getBean().getId());
//		List list = this.getGeneralService().getBySQL(sql,map);
//		if(list!=null&&!list.isEmpty()){
//			throw new EntityException("编号已经存在！");
//		}
//	}
	@Override
	public void setFilePath(String filePath) {
		File file = new File(ServletActionContext.getRequest().getRealPath(filePath+Const.getYear()+"/"));
		if(!file.exists()){
			file.mkdirs();
		}
		super.setFilePath(filePath+Const.getYear()+"/");
	}

	public PeProApplyno getBean() {
		return (PeProApplyno) super.superGetBean();
	}
	@Override
	public void checkBeforeAdd() throws EntityException {
		if (this.getBean().getId() == null || "".equals(this.getBean().getId())) {
			this.getBean().setEnumConstByFkProgramStatus(this.getMyListService().getEnumConstByNamespaceCode("FkProgramStatus", "2"));
		}
//		String sql = "select t.id from pe_pro_applyno t where t.code=:code and t.year='"+Const.getYear()+"'";
//		Map map = new HashMap();
//		map.put("code", this.getBean().getCode());
//		List list = this.getGeneralService().getBySQL(sql,map);
//		if(list!=null&&!list.isEmpty()){
//			throw new EntityException("编号已经存在！");
//		}
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/programApply/peProApplyno";
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeProApplyno.class);
		dc.createAlias("enumConstByFkProvinceCheck", "enumConstByFkProvinceCheck");
		dc.createAlias("enumConstByFkProgramStatus", "enumConstByFkProgramStatus");
		dc.createAlias("enumConstByFkProgramType", "enumConstByFkProgramType");
//		dc.add(Restrictions.eq("year", Const.getYear()));
		return dc;
	}

}
