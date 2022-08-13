package com.whaty.platform.entity.web.action.programApply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PeProApply;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.programJudge.ProgramJudgmentService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * 管理员查看终审结果
 * 
 * @author 侯学龙
 *
 */
public class ViewFinalJudgmentOpinionAction extends ViewFirstTrialOpinionAction {
	private ProgramJudgmentService programJudgmentService;
	private String note;
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setTitle(this.getText("查看终审结果"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		if(us.getRoleType().equals("4")){
			ColumnConfig cc=new ColumnConfig(this.getText("培训单位"), "combobox_PeUnit.unitName", true, true, true, "TextField", true, 50,null);
			cc.setComboSQL("select u.id,u.name from pe_unit u join enum_const e on u.fk_unit_type=e.id and e.code ='1526' order by u.name");
			this.getGridConfig().addColumn(cc);
		}else if(us.getRoleType().equals("3")){
			ColumnConfig cc=new ColumnConfig(this.getText("培训单位"), "combobox_PeUnit.unitName", true, true, true, "TextField", true, 50,null);
			cc.setComboSQL("select u.id,u.name from pe_unit u join enum_const e on u.fk_unit_type=e.id  where  e.code ='1526'  and u.fk_province=(select unit.fk_province from pe_manager manager join pe_unit unit on manager.fk_unit=unit.id where manager.fk_sso_user_id='"+us.getSsoUser().getId()+"') order by u.name");
			this.getGridConfig().addColumn(cc);
		}else{
			this.getGridConfig().addColumn(this.getText("培训单位"), "unitName",false,false,true,"");
		}
//		this.getGridConfig().addColumn(this.getText("承办单位"), "combobox_PeUnit.unitName");
		this.getGridConfig().addColumn(this.getText("培训项目"), "combobox_PeProApplyno.applynoName");
		this.getGridConfig().addColumn(this.getText("申报学科"), "combobox_PeSubject.subjectName");
		
		this.getGridConfig().addColumn(this.getText("班级标识码"), "classIdentifier",false,false,us.getRoleType().equals("4"),"");
		
		this.getGridConfig().addColumn(this.getText("申报时间"), "declareDate");
		this.getGridConfig().addColumn(this.getText("终审状态"), "combobox_EnumConstByFkCheckFinal.checkStatus");
		this.getGridConfig().addColumn(this.getText("平均分"), "avgScore",false,false,false,"");
		this.getGridConfig().addRenderScript("平均分", 
				"{if((record.data['avgScore'])=='')" +
				"{return '未评分';}else if((record.data['combobox_EnumConstByFkCheckFinal.checkStatus'])=='未审核'){ return '未审核';}" +
				"else { return record.data['avgScore'];}}", ""); 
		this.getGridConfig().addColumn(this.getText("sdf"), "declaration",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("项目申报书"), "<a href='${value}' target='_blank'>下载</a>", "declaration");
		this.getGridConfig().addColumn(this.getText("ads"), "scheme",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("实施方案"), "<a href='${value}' target='_blank'>下载</a>", "scheme");
		
	
//		this.getGridConfig().addColumn(this.getText("初审意见"), "checkNote");
		if(this.getGridConfig().checkBeforeAddMenu("/entity/programApply/flagPEO_flagPEO.action")){
			this.getGridConfig().addRenderFunction(this.getText("管理员审核"), "<a href='/entity/programApply/viewFinalJudgmentOpinion_managerCheck.action?ids=${value}' target='_blank'>进入</a>", "id");
		}
		this.getGridConfig().addRenderFunction(this.getText("查看审核意见"), "<a href='/entity/programApply/viewFinalJudgmentOpinion_viewDetail.action?ids=${value}' target='_blank'>查看</a>", "id");
	}
	@Override
	public Page list() {
		StringBuffer sql = new StringBuffer();
		sql.append("select program.id as id,                                              ");
		sql.append("       unit.name as unitName,                                         ");
		sql.append("       applyno.name as applynoName,                                   ");
		sql.append("       subject.name as subjectName,                                   ");
		sql.append("       program.CLASS_IDENTIFIER as classIdentifier,                   ");
		sql.append("       program.DECLARE_DATE as declareDate,                           ");
		sql.append("       ec.name as checkStatus   ,                                     ");
		sql.append("       avg(t.result_final) as avgScore,                              ");
		sql.append("       program.declaration as declaration,                            ");
		sql.append("       program.scheme as scheme                                      ");
//		sql.append("       ,to_char(program.note_first) as checkNote                       ");
		sql.append("  FROM PR_PRO_EXPERT T                                                ");
		sql.append(" INNER JOIN PE_PRO_APPLY PROGRAM ON T.FK_PROGRAM = PROGRAM.ID         ");
		sql.append(" INNER JOIN PE_SUBJECT SUBJECT ON PROGRAM.FK_SUBJECT = SUBJECT.ID     ");
		sql.append(" INNER JOIN PE_UNIT UNIT ON PROGRAM.FK_UNIT = UNIT.ID                 ");
		sql.append(" INNER JOIN PE_PRO_APPLYNO APPLYNO ON PROGRAM.FK_APPLYNO = APPLYNO.ID ");
		sql.append("  LEFT OUTER JOIN ENUM_CONST EC ON PROGRAM.FK_CHECK_FINAL = EC.ID     ");
		sql.append("  INNER JOIN ENUM_CONST EC2 ON PROGRAM.FK_CHECK_FIRST = EC2.ID   	  ");
		sql.append("  where /*applyno.year='"+Const.getYear()+"' AND */ EC2.CODE='1011' 		  ");
		if( this.getPeUnit().getEnumConstByFkUnitType().getCode().equals("1526")){
			sql.append(" AND UNIT.ID ='"+this.getPeUnit().getId()+"' ");
		}
		sql.append(" GROUP BY PROGRAM.ID,                                                 ");
		sql.append("          SUBJECT.NAME,                                               ");
		sql.append("          UNIT.NAME,                                                  ");
		sql.append("          APPLYNO.NAME,                                               ");
		sql.append("          PROGRAM.DECLARATION,                                        ");
		sql.append("          PROGRAM.SCHEME,                                             ");
		sql.append("          EC.NAME,                                                    ");
		sql.append("          PROGRAM.CLASS_IDENTIFIER,        " );
		sql.append("		  PROGRAM.DECLARE_DATE              ");
//		return this.iniSqllist(sql);
		
		setSqlCondition(sql,"");
		Page page = null;
		try {
			page = getGeneralService().getByPageSQL(addPri(sql.toString()), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return page;
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PeProApply.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/programApply/viewFinalJudgmentOpinion";
	}

	public String viewDetail(){
		PeProApply apply = null;
		try {
			apply = (PeProApply)this.getGeneralService().getById(PeProApply.class, this.getIds());
//			 list = this.getGeneralService().getBySQL("select to_char(t.note_first) from pe_pro_apply t where t.id = '"+this.getIds()+"'");
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(apply!=null){
			this.setPeProApply(apply);
		}
//		return "msg";
		return "note_detail";
	}
	/**
	 * 保存管理员审核信息
	 * @return
	 * @throws EntityException 
	 */
	public String mangerCheckSave(){
		try {
			this.getProgramJudgmentService().saveMangerFinalCheck(this.getBean());
			this.setMsg("保存成功");
		} catch (EntityException e) {
			this.setMsg(e.getMessage());
		}
		this.setIds(this.getBean().getId());
		return managerCheck();
	}
	/**
	 * @description 保存与项目评审结果相关的SsoUser，方便学员登录
	 * @param classIdentifier 项目终审通过后生成的班级标示码，即SsoUser中的login_id
	 */
	private void associateSsoUser(String classIdentifier) {
		SsoUser ssoUser = new SsoUser();
		ssoUser.setLoginId(classIdentifier);
		ssoUser.setPassword(Const.FIRST_PASSWORD);
		ssoUser.setLoginNum(new Long(1));
		//学员不能修改密码，也不必去修改个人信息
		ssoUser.setCheckedInfo("1");
		ssoUser.setCheckedPw("1");
		
		EnumConst enumConst = getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsvalid", "1");
		ssoUser.setEnumConstByFlagIsvalid(enumConst);
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PePriRole.class);
		detachedCriteria.add(Restrictions.eq("name", "学员"));
		List<PePriRole> roleList = null;
		try {
			roleList = getGeneralService().getList(detachedCriteria);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if(roleList != null && !roleList.isEmpty()) {
			ssoUser.setPePriRole(roleList.get(0));
		}
		getGeneralService().getGeneralDao().setEntityClass(SsoUser.class);
		try {
			getGeneralService().save(ssoUser);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @throws EntityException 单位、学科及项目标示码不正确时会抛出，评审无法通过
	 * @description 生成登录名（班级标示码）
	 * @return 生成的班级标识符，由年份、单位码、学科友、项目码及一位随机数字组成
	 */
	private String generateClassIdentifier() throws EntityException {
//		Calendar calendar = Calendar.getInstance();
		String yearCode = "";//(calendar.get(Calendar.YEAR) + "").substring(2);

		String getFKsInPeProApplySQL = "select FK_SUBJECT,FK_UNIT,FK_APPLYNO from pe_pro_apply where id=:theId";
		Map<String, String> params = new HashMap<String, String>();
		params.put("theId", getBean().getId());
		List FKsList = null;
		try {
			 FKsList = getGeneralService().getBySQL(getFKsInPeProApplySQL, params);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String FKToUnit = "";
		String FKToSuject = "";
		String FKToProject = "";
		if(FKsList != null && !FKsList.isEmpty()) {
			Object[] tempObjects = (Object[]) FKsList.get(0);
			FKToUnit = (String) tempObjects[1];
			FKToProject = (String) tempObjects[2];
			FKToSuject = (String) tempObjects[0];
		}
		params.clear();
		String getUnitCodeSQL = "select code from pe_unit where id=:theId";
		String getSujectCodeSQL = "select code from pe_subject where id=:theId";
		String getProjectCodeSQL = "select code,year from pe_pro_applyno where id=:theId";
		String unitCode = "";
		String subjectCode = "";
		String projectCode = "";
		try {
			params.put("theId", FKToUnit);
			unitCode = (String) getGeneralService().getBySQL(getUnitCodeSQL,params).get(0);
			params.put("theId", FKToSuject);
			subjectCode = (String) getGeneralService().getBySQL(getSujectCodeSQL,params).get(0);
			params.put("theId", FKToProject);
			Object object =  getGeneralService().getBySQL(getProjectCodeSQL,params).get(0);
			Object [] obj = (Object[])object;
			projectCode = obj[0].toString();
			yearCode = obj[1].toString().substring(2);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(unitCode.length() != 5) {
			throw new EntityException("单位代码有误,无法生成班级标示码,评审不通过！");
		}
		if(subjectCode.length() != 2) {
			throw new EntityException("学科代码有误,无法生成班级标示码,评审不通过！");
		}
		if(projectCode.length() != 1) {
			throw new EntityException("培训项目代码有误,无法生成班级标示码,评审不通过！");
		}
		String randomCode = Math.round(Math.random()*9)+"";
		String classIdentifier = yearCode + unitCode + subjectCode + projectCode + randomCode;
		return classIdentifier;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public ProgramJudgmentService getProgramJudgmentService() {
		return programJudgmentService;
	}
	public void setProgramJudgmentService(
			ProgramJudgmentService programJudgmentService) {
		this.programJudgmentService = programJudgmentService;
	}
}