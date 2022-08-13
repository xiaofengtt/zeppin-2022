package com.whaty.platform.entity.web.action.basic;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeExpertSearchHistory;
import com.whaty.platform.entity.bean.PeTrainExpert;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.AnalyseClassType;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.ExpressionParse;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

public class PeTrainingExpertsSearchAction extends MyBaseAction {
	private List unitList;
	private List subjectList;
	private String expname;//专家姓名
	private String expunit;//专家单位
	private String expsubject;//专家学科
	
	private PeTrainExpert expert;//专家
	private List expertList;

	private String search__workplace;//搜索工作单位
	
	public void initGrid() {
		this.getGridConfig().setCapability(false,false,false,true,false);
		this.getGridConfig().setTitle(this.getText("培训教学专家信息查询"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学科"), "peSubject.name",true,true,true,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("姓名"), "name",true,true,true,"",false,20);
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name",true,true,true,"TextField",true,50);
		this.getGridConfig().addColumn(this.getText("省份"), "peProvince.name",true,true,true,"TextField",true,50);
		this.getGridConfig().addColumn(this.getText("工作单位"), "workplace");
		this.getGridConfig().addColumn(this.getText("职称"), "zhicheng",true,true,true,"",true,200);
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(us.getRoleType().equals("4")){
			ColumnConfig cc=new ColumnConfig(this.getText("推荐类别"), "enumConstByFkStatus.name");
			cc.setComboSQL("select * from enum_const where namespace='FkStatus' and code in ('1258','1259')");
			this.getGridConfig().addColumn(cc);
		}
		
		
//		this.getGridConfig().addColumn(this.getText("民族"), "folk",false, true, true,"",true,200);
//		this.getGridConfig().addColumn(this.getText("出生年月"), "birthdate",false, true, true,"Date",true,200);
//		this.getGridConfig().addColumn(this.getText("学历"), "education",true,true,true,"",true,200);
//		this.getGridConfig().addColumn(this.getText("所学专业"), "major",false, true, true,"",true,200);
//		this.getGridConfig().addColumn(this.getText("政治面貌"), "politics",true,true,true,"",true,200);
//		this.getGridConfig().addColumn(this.getText("职务"), "zhiwu",true,true,true,"",true,200);
//		this.getGridConfig().addColumn(this.getText("研究专长"), "researchArea",false,true,true,"",true,2000);
//		this.getGridConfig().addColumn(this.getText("教学专长"), "trainingArea",false,true,true,"",true,2000);
//		this.getGridConfig().addColumn(this.getText("通讯地址"), "address",false,true,true,"",true,200);
//		this.getGridConfig().addColumn(this.getText("邮政编码"), "zip",false,true,true,"",true,200,Const.zip_for_extjs);
//		this.getGridConfig().addColumn(this.getText("单位电话"), "officePhone",false, true, true,"",true,200,Const.phone_number_for_extjs);
//		this.getGridConfig().addColumn(this.getText("传真"), "fax",false,true,true,"",true,20,Const.phone_number_for_extjs);
//		this.getGridConfig().addColumn(this.getText("电子邮箱"), "email",false, true, true,"",true,200,Const.email_for_extjs);
//		this.getGridConfig().addColumn(this.getText("入库时间"), "inputDate");
//		this.getGridConfig().addRenderFunction(this.getText("操作"),
//				"<a href=\"/entity/basic/peTrainingExpertsSearchAction_viewDetail.action?bean.id=${value}\" target=\"_blank\">详细信息</a>",
//				"id");
		this.getGridConfig().addRenderFunction(this.getText("操作"),"<a href=\"#\" onclick=\"window.open('/entity/basic/peTrainingExpertsSearchAction_viewDetail.action?bean.id=${value}','','toolbar=no,menubar=no,resizable=yes,scrollbars=yes,height=300, width=900')\">详细信息</a>", "id");
//		this.getGridConfig().addColumn(this.getText("个人简历"), "personalResume",false,true,true,"TextArea",true,2000);
//		this.getGridConfig().addColumn(this.getText("教育教学成果"), "trainingResult",false,true,true,"TextArea",true,2000);
//		this.getGridConfig().addColumn(this.getText("教师培训经验"), "trainingExperience",false,true,true,"TextArea",true,2000);
//		this.getGridConfig().addColumn(this.getText("省份"), "peProvince.name",true,true,true,"TextField",true,50);
//		this.getGridConfig().addColumn(this.getText("状态"), "enumConstByFkStatus.name");
//		this.getGridConfig().addColumn(this.getText("手机"), "telephone",false, true, true,"",true,200,Const.mobile_for_extjs);
//		this.getGridConfig().addColumn(this.getText("年龄"),"age",false,true,false,"",true,10,Const.number_for_extjs);
//		this.getGridConfig().addColumn(this.getText("身份证号"), "idcard",false,true,false,"",true,20);
//		this.getGridConfig().addColumn(this.getText("住宅电话"), "homePhone",false,true,true,"",true,200,Const.phone_number_for_extjs);
//		this.getGridConfig().addColumn(this.getText("入库时间"), "birthdate",false, true, true,"Date",true,200);
//		this.getGridConfig().addColumn(this.getText("其他需要说明事项"), "otherItems",false,true,false,"TextArea",true,2000);
//		this.getGridConfig().addColumn(this.getText("所在单位意见"), "unitComment",false,true,false,"TextArea",true,2000);
//		this.getGridConfig().addColumn(this.getText("推荐部门意见"), "recommendComment",false,true,false,"TextArea",true,2000);
//		this.getGridConfig().addColumn(this.getText("教育部师范司组织专家审核意见"), "finalComment",false,true,false,"TextArea",true,2000);
//		this.getGridConfig().addColumn(this.getText("备注"), "note", false,true,true, "TextArea", true, 2000);
	}
	public String toSearchPage(){
//		if(this.firstLogin()){
//			return "firstLogin";
//		}
		this.initUnitList();
		this.initSubjectList();
		return "search_trainexp";
	}

	public void initUnitList(){
		String sql="select id,name from pe_workplace order by name";
		List list=new LinkedList();
		try {
			list=this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(list!=null&&list.size()>0){
			this.setUnitList(list);
		}
	}
	public void initSubjectList(){
		String sql="select id,name from pe_subject order by name";
		List list=new LinkedList();
		try {
			list=this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(list!=null&&list.size()>0){
			this.setSubjectList(list);
		}
	}
	private boolean firstLogin(){
		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser user=us.getSsoUser();
		if(user.getLoginNum()<1l||user.getLoginNum()==null){
			return true;
		}
		return false;
	}
	@Override
	public void checkBeforeAdd() throws EntityException{
	}
	@Override
	public void checkBeforeUpdate() throws EntityException{
	}
	

	@Override
	public void setEntityClass() {
		this.entityClass = PeTrainExpert.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peTrainingExpertsSearchAction";
	}

	public void setBean(PeTrainExpert instance) {
		super.superSetBean(instance);
	}

	public PeTrainExpert getBean() {
		return (PeTrainExpert) super.superGetBean();
	}

	public String viewDetail() {
		try {
			this.setBean((PeTrainExpert)this.getGeneralService().getById(PeTrainExpert.class,this.getBean().getId()));
			String sqlString = "update pe_train_expert set SEARCH_COUNT=to_char(to_number(nvl(SEARCH_COUNT,'0'))+1) where id=:ids";
			Map map=new HashMap();
			map.put("ids", this.getBean().getId());
			this.getGeneralService().executeBySQL(sqlString,map);
			
			//将培训专家的查看记录存入数据库
			PeExpertSearchHistory history = new PeExpertSearchHistory();
			history.setPeTrainExpert(getBean());
			history.setSearchTime(new Date());
			UserSession session = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
			history.setSsoUser(session.getSsoUser());
			getGeneralService().getGeneralDao().setEntityClass(PeExpertSearchHistory.class);
			getGeneralService().save(history);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "detail";
	}

	public DetachedCriteria initDetachedCriteria() {
		boolean err=false;
//		if((this.getExpname()==null||this.getExpname().length()<2)&&this.getExpsubject().equals("")){//用户输入错误
//			err=true;
//		}
		if(!err||true){//用户输入正常时
			
			DetachedCriteria dc=DetachedCriteria.forClass(PeTrainExpert.class);
			dc.createCriteria("peSubject","peSubject",DetachedCriteria.LEFT_JOIN);
			dc.createCriteria("enumConstByFkGender","enumConstByFkGender",DetachedCriteria.LEFT_JOIN);
			dc.createCriteria("peProvince","peProvince",DetachedCriteria.LEFT_JOIN);
			dc.createCriteria("peUnit","peUnit",DetachedCriteria.LEFT_JOIN);
			UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
			if(us.getRoleType().equals("4")){
				dc.createCriteria("enumConstByFkStatus","enumConstByFkStatus").add(Restrictions.or(Restrictions.eq("code", "1258"), Restrictions.eq("code", "1259")));
			}else{
				dc.createCriteria("enumConstByFkStatus","enumConstByFkStatus").add(Restrictions.eq("code", "1259"));
			}
//				if(this.getExpsubject()!=null&&!this.getExpsubject().equals("")){
//				dc.add(Restrictions.eq("peSubject.id", this.getExpsubject()));
//			}
//			if(this.getExpname()!=null&&!this.getExpname().equals("")){
//				dc.add(Restrictions.like("name", this.getExpname(), MatchMode.ANYWHERE));
//			}
			JsonUtil.setDateformat("yyyy-MM");
			return dc;
		}
		return null;
	}

	/**
	 * 判断sort，dir输入，置入DetachedCriteria 递归遍历bean，取得用户输入的参数，置入DetachedCriteria
	 */
	public DetachedCriteria setDetachedCriteria(
			DetachedCriteria detachedCriteria, Object bean) {

		if (this.getSort() != null) {
			if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc"))
				detachedCriteria.addOrder(Order.desc(this.getSort()));
			else
				detachedCriteria.addOrder(Order.asc(this.getSort()));
			if(!this.getSort().equals("id")){
				detachedCriteria.addOrder(Order.desc("id"));
			}
		}
		
		ActionContext context = ActionContext.getContext(); 
		String method=ServletActionContext.getRequest().getMethod();
		Map params = context.getParameters();

		Iterator iterator = params.entrySet().iterator();
//		boolean flag=false;//是否是首次搜索
		String str_="search1_";
		while(iterator.hasNext()){
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            String name = entry.getKey().toString();
            if(name.startsWith("search__")){
	            if("".equals(((String[])entry.getValue())[0]))
	            	continue;
            	str_="search__";
            }
		}
		detachedCriteria = setDC1(detachedCriteria, params,str_);
		return detachedCriteria;
//		super.setDetachedCriteria(detachedCriteria, bean);
//		return detachedCriteria;
	}

	private DetachedCriteria setDC1(DetachedCriteria detachedCriteria, Map params,String str_) {
		Iterator iterator = params.entrySet().iterator();
		do {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            String name = entry.getKey().toString();
            if(!name.startsWith(str_))
            	continue;
            if("".equals(((String[])entry.getValue())[0]))
            	continue;
            if(name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")){
            	name=name.substring(name.lastIndexOf(".", name.lastIndexOf(".")-1)+1);
			}else{
				name=name.substring(8);
			}
            CriteriaImpl imp = (CriteriaImpl)detachedCriteria.getExecutableCriteria(null);
            Class beanClass = null;
			try {
				beanClass = Class.forName(imp.getEntityOrClassName());
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			Class clazz = AnalyseClassType.getClassType(beanClass, entry.getKey().toString().substring(8));
			
            detachedCriteria = ExpressionParse.parseExpression(detachedCriteria, name, ((String[])entry.getValue())[0], clazz);
        }while(true);
		return detachedCriteria;
	}
	public String getExpname() {
		return expname;
	}
	public void setExpname(String expname) {
		this.expname = expname;
	}
	public String getExpunit() {
		return expunit;
	}
	public void setExpunit(String expunit) {
		this.expunit = expunit;
	}
	public String getExpsubject() {
		return expsubject;
	}
	public void setExpsubject(String expsubject) {
		this.expsubject = expsubject;
	}
	public PeTrainExpert getExpert() {
		return expert;
	}
	public void setExpert(PeTrainExpert expert) {
		this.expert = expert;
	}
	public List getUnitList() {
		return unitList;
	}
	public void setUnitList(List unitList) {
		this.unitList = unitList;
	}
	public List getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List subjectList) {
		this.subjectList = subjectList;
	}
	public List getExpertList() {
		return expertList;
	}
	public void setExpertList(List expertList) {
		this.expertList = expertList;
	}
}
