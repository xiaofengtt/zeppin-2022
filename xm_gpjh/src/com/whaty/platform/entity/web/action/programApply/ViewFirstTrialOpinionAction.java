package com.whaty.platform.entity.web.action.programApply;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeProApply;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.bean.PrProExpert;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.programJudge.ProgramJudgmentService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * 查看初审意见
 * 
 * @author 侯学龙
 *
 */
public class ViewFirstTrialOpinionAction extends MyBaseAction {
	private ProgramJudgmentService programJudgmentService;
	private List prProExpertList;
	private String note;
	private boolean flag;
	private PeProApply peProApply;
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setTitle(this.getText("查看初审结果"));
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
		this.getGridConfig().addColumn(this.getText("申报时间"), "declareDate");
		this.getGridConfig().addColumn(this.getText("平均分"), "avgScore",false,false,true,"");
		this.getGridConfig().addColumn(this.getText("初审状态"), "combobox_EnumConstByFkCheckFirst.checkStatus");
		this.getGridConfig().addRenderScript("平均分", 
				"{if((record.data['avgScore'])=='')" +
				"{return '未评分';}else if((record.data['combobox_EnumConstByFkCheckFirst.checkStatus'])=='未审核'){ return '未审核';}" +
				"else { return record.data['avgScore'];}}", ""); 
		this.getGridConfig().addColumn(this.getText("sdf"), "declaration",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("项目申报书"), "<a href='${value}' target='_blank'>下载</a>", "declaration");
		this.getGridConfig().addColumn(this.getText("ads"), "scheme",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("实施方案"), "<a href='${value}' target='_blank'>下载</a>", "scheme");
		
		//flagPEO用来判断是否是项目执行办 通过admin用户设置权限
		if(this.getGridConfig().checkBeforeAddMenu("/entity/programApply/flagPEO_flagPEO.action")){
			this.setFlag(true);
			this.getGridConfig().addRenderFunction(this.getText("管理员审核"), "<a href='/entity/programApply/viewFirstTrialOpinion_managerCheck.action?ids=${value}' target='_blank'>进入</a>", "id");
		}
		this.getGridConfig().addRenderFunction(this.getText("查看审核意见"), "<a href='/entity/programApply/viewFirstTrialOpinion_viewDetail.action?ids=${value}' target='_blank'>查看</a>", "id");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeProApply.class;
	}
	protected PeUnit getPeUnit(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			List peManagerList = this.getGeneralService().getByHQL("from PeManager p where p.ssoUser = '"+userSession.getSsoUser().getId()+"'");
			PeManager manager = (PeManager)peManagerList.get(0);
			return manager.getPeUnit();
		} catch (EntityException e) {
			return null;
		}
	}
	@Override
	public Page list() {
		StringBuffer sql = new StringBuffer();
		sql.append("select program.id as id,                                              ");
		sql.append("       unit.name as unitName,                                         ");
		sql.append("       applyno.name as applynoName,                                   ");
		sql.append("       subject.name as subjectName,                                   ");
		sql.append("       program.DECLARE_DATE as declareDate,                           ");
		sql.append("       avg(t.result_first) as avgScore,                              ");
		sql.append("       ec.name as checkStatus  ,                                      ");
		sql.append("       program.declaration as declaration,                            ");
		sql.append("       program.scheme as scheme                                      ");
//		sql.append("       ,to_char(program.note_first) as checkNote                       ");
		sql.append("  from pr_pro_expert t                                                ");
		sql.append(" inner join pe_pro_apply program on t.fk_program = program.id         ");
		sql.append(" inner join pe_subject subject on program.fk_subject = subject.id     ");
		sql.append(" inner join pe_unit unit on program.fk_unit = unit.id                 ");
		sql.append(" inner join pe_pro_applyno applyno on program.fk_applyno = applyno.id ");
		sql.append("  left outer join enum_const ec on program.fk_check_first = ec.id     ");
		sql.append("  where /*applyno.year='"+Const.getYear()+"'*/ 1=1  ");
		if( this.getPeUnit().getEnumConstByFkUnitType().getCode().equals("1526")){
			sql.append(" and unit.id ='"+this.getPeUnit().getId()+"' ");
		}
		sql.append(" group by program.id,                                                 ");
		sql.append("          subject.name,                                               ");
		sql.append("          unit.name,                                                  ");
		sql.append("          applyno.name,                                               ");
		sql.append("          program.declaration,                                        ");
		sql.append("          program.scheme,                                             ");
		sql.append("          ec.name,                                                    ");
//		sql.append("          to_char(program.note_first)," );
		sql.append("		  program.DECLARE_DATE              ");
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
	
	public void setSqlCondition(StringBuffer sql,String groupBy){
		ActionContext context = ActionContext.getContext(); 
		Map params = context.getParameters();
		//为支持2009-01-13之前所写的sql，特加上如下处理
		this.arrangeSQL(sql, params);
		setCondition(sql,params);
		if(groupBy != null && !"".equals(groupBy)){
			sql.append(groupBy);
		}
		/**
		 * 对于表中含有下划线"_"的字段暂不支持排序
		 * (如果需要对含下划线的字段也支持排序，则命名时要求命名为与数据库字段名相同)
		 */
		String temp = this.getSort();
		//截掉前缀 Combobox_PeXxxxx.
		if(temp.indexOf(".") > 1){
			if(temp.toLowerCase().startsWith("combobox_")){
				temp = temp.substring(temp.indexOf(".") + 1);
			}
		}
		if (this.getSort() != null && temp != null) {
			if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")){
				sql.append(" order by " +temp+ " desc ");
			}else{
				sql.append(" order by " +temp+ " asc ");
			}
			if(!temp.equals("id")){
				sql.append(" , id desc");
			}
		}
	}
	
	/**
	 * 为SQL查询方法添加搜索条件，遍历servlet的参数中的search__开头的变量，设置到sql中
	 * 参考setDC(DetachedCriteria, Map)方法
	 * @param sql 
	 * @param params
	 */
	private void setCondition(StringBuffer sql,Map params){
		Iterator iterator = params.entrySet().iterator();
		do {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            String name = entry.getKey().toString();
            String value = ((String[])entry.getValue())[0].toString();
            if(!name.startsWith("search__"))
            	continue;
            if("".equals(value))
            	continue;
            if(name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")){
            	name=name.substring(name.lastIndexOf(".", name.lastIndexOf(".")-1)+1);
			}else{
				name=name.substring(8);
			}
            //截掉前缀 Combobox_PeXxxxx.
            if(name.toLowerCase().startsWith("combobox_")){
            	name = name.substring(name.indexOf(".") + 1);
            	if(value.indexOf(" or ") > -1){
            		sql.append(" and "+ name +" in (");
            		String[] list = value.split(" or ");
            		for(String str : list){
            			sql.append("'" + str.trim() + "',");
            		}
            		sql.delete(sql.length() - 1, sql.length());
            		sql.append(")");
            	} else {
            		sql.append(" and " + name + " = '"+value+"'");
            	}
            	
            }else{
            	if(name.endsWith("Date")){//紧限于 yyyy-MM-dd格式的日期
            		sql.append(" and " + name + "- to_date('"+ value +"','yyyy-MM-dd')<1");
            		sql.append(" and " + name + "- to_date('"+ value +"','yyyy-MM-dd')>0");
            	}else{
            		sql.append(" and " + name + " like '%"+value+"%'");
            	}
            }
	       
	   }while(true);
	}
	
	/**
	 * 为支持2009-01-13之前所写的sql，特加上如下处理
	 */
	private void arrangeSQL(StringBuffer sql,Map params){
		Iterator iterator = params.entrySet().iterator();
		do {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            String name = entry.getKey().toString();
            String value = ((String[])entry.getValue())[0].toString();
            if(!name.startsWith("search__"))
            	continue;
            if(name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")){
            	name=name.substring(name.lastIndexOf(".", name.lastIndexOf(".")-1)+1);
			}else{
				name=name.substring(8);
			}
            if(name.toLowerCase().startsWith("combobox_")){
            	sql.insert(0, "select * from (");
        		sql.append(") t where 1 = 1 ");
        		return;
            }
        }while(true);
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/programApply/viewFirstTrialOpinion";
	}
	private void initPeProApply(){
		DetachedCriteria dc = DetachedCriteria.forClass(PrProExpert.class);
		dc.createAlias("peProApply", "peProApply");
		dc.createAlias("peValuaExpert", "peValuaExpert");
		dc.add(Restrictions.eq("peProApply.id", this.getIds()));
		List list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setPrProExpertList(list);
	}
	public String managerCheck(){
		this.initPeProApply();
		return "manager_check";
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
	 */
	public String mangerCheckSave(){
		try {
			this.getProgramJudgmentService().saveMangerFirstCheck(this.getBean(),false);
			this.setMsg("保存成功");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
		}
		this.setIds(this.getBean().getId());
		return managerCheck();
	}
	public List getPrProExpertList() {
		return prProExpertList;
	}

	public void setPrProExpertList(List prProExpertList) {
		this.prProExpertList = prProExpertList;
	}
	
	public void setBean(PeProApply instance) {
		super.superSetBean(instance);
	}

	public PeProApply getBean() {
		return (PeProApply) super.superGetBean();
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public PeProApply getPeProApply() {
		return peProApply;
	}

	public void setPeProApply(PeProApply peProApply) {
		this.peProApply = peProApply;
	}

	public ProgramJudgmentService getProgramJudgmentService() {
		return programJudgmentService;
	}

	public void setProgramJudgmentService(
			ProgramJudgmentService programJudgmentService) {
		this.programJudgmentService = programJudgmentService;
	}
}
