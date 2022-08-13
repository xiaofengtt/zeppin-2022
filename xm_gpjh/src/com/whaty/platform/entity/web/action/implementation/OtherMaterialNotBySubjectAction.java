package com.whaty.platform.entity.web.action.implementation;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * 查看其他材料
 * 
 * @author 侯学龙
 *
 */
public class OtherMaterialNotBySubjectAction extends TrainingCourseSummaryAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("材料二（按项目提交的材料）"));
		this.getGridConfig().addColumn(this.getText("ID"), "id");
		this.getGridConfig().addColumn(this.getText("培训项目名称"), "peProApplyno.name");
		this.getGridConfig().addColumn(this.getText("培训项目编号"), "peProApplyno.code");
//		this.getGridConfig().addColumn(this.getText("承办单位"), "peUnit.name");
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(us.getRoleType().equals("4")){
			ColumnConfig cc=new ColumnConfig(this.getText("培训单位"), "peUnit.name", true, true, true, "TextField", true, 50,null);
			cc.setComboSQL("select u.id,u.name from pe_unit u join enum_const e on u.fk_unit_type=e.id and e.code ='1526' order by u.name");
			this.getGridConfig().addColumn(cc);
		}else if(us.getRoleType().equals("3")){
			ColumnConfig cc=new ColumnConfig(this.getText("培训单位"), "peUnit.name", true, true, true, "TextField", true, 50,null);
			cc.setComboSQL("select u.id,u.name from pe_unit u join enum_const e on u.fk_unit_type=e.id  where  e.code ='1526'  and u.fk_province=(select unit.fk_province from pe_manager manager join pe_unit unit on manager.fk_unit=unit.id where manager.fk_sso_user_id='"+us.getSsoUser().getId()+"') order by u.name");
			this.getGridConfig().addColumn(cc);
		}else{
			this.getGridConfig().addColumn(this.getText("培训单位"), "peUnit.name",false,false,true,"");
		}
		this.getGridConfig().addColumn(this.getText("项目类型"), "peProApplyno.enumConstByFkProgramType.name");
		this.getGridConfig().addColumn(this.getText("申报时限"), "peProApplyno.deadline",false,false,true,"Date",false,20);
		this.getGridConfig().addColumn(this.getText("总结报告上传时间"), "inputDate",false,false,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("其他材料最近上传时间"), "lastUploadDate",false,false,true,"Date");
		this.getGridConfig().addColumn(this.getText("ads"), "summaryFile",false,false,false,"");
		this.getGridConfig().addRenderScript(this.getText("查看总结报告"), "{if (${value}==null||${value}=='') return '未提交'; else return '<a href='+${value}+' target=\\'_blank\\'>查看</a>';}", "summaryFile");
		this.getGridConfig().addRenderScript(this.getText("查看其他材料"), "{ return '<a href=\\'/entity/implementation/showOtherMaterial.action?id_='+record.data['id']+'\\' target=\\'_blank\\'>查看</a>';}", "id");
	}

	@Override
	public void setEntityClass() {

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/implementation/otherMaterialNotBySubject";
	}
	

//	private PeUnit getPeUnit(){
//		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		try {
//			List peManagerList = this.getGeneralService().getByHQL("from PeManager p where p.ssoUser = '"+userSession.getSsoUser().getId()+"'");
//			PeManager manager = (PeManager)peManagerList.get(0);
//			return manager.getPeUnit();
//		} catch (EntityException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
}
