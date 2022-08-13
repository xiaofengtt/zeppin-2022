package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;
import com.whaty.platform.entity.bean.PeEnterpriseManager;

public class PeDetailAction extends MyBaseAction {

	private String id;
	private String num;
	private PeEnterprise peEnterprise;
	private PeBzzStudent peBzzStudent;
	private String type;

	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	public PeEnterprise getPeEnterprise() {
		return peEnterprise;
	}

	public void setPeEnterprise(PeEnterprise peEnterprise) {
		this.peEnterprise = peEnterprise;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("学员列表"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "regNo", false,
				false, true, "textField");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("性别"), "gender");
		this.getGridConfig().addColumn(this.getText("学历"), "education", false);
		this.getGridConfig().addColumn(this.getText("民族"), "folk");
		this.getGridConfig().addColumn(this.getText("出生日期"), "birthdayDate", false);
		if(type.equals("enterprise"))
		{
			this.getGridConfig().addColumn(this.getText("所在学期"), "ComboBox_PeBzzBatch.peBzzBatchname",true,true,true,"");
			this.getGridConfig().addColumn(this.getText("所在企业"), "pname",false,false,true,"");
		}
		else if (type.equals("allnum")) 
		{
			this.getGridConfig().addColumn(this.getText("所在学期"), "peBzzBatchname",false,true,true,"");
			this.getGridConfig().addColumn(this.getText("所在企业"), "ComboBox_PeEnterprise.pname",true,false,true,""); //需要添加范围限制
		}
		
		this.getGridConfig().addRenderFunction(this.getText("详细信息"), "<a href=\"peDetail_stuviewDetail.action?id=${value}&type=1\">查看详细信息</a>", "id");
		this.getGridConfig().addMenuScript(this.getText("返回"),
				"{window.history.back();}");
	}

	public void setEntityClass() {
	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/peDetail";
	}
	
	public String abstractList() {
		ActionContext context = ActionContext.getContext();
		Map map1 = context.getParameters();
		Iterator it  = map1.entrySet().iterator();
		int k=0;
		int n=0;
		StringBuffer buffer = new StringBuffer();
		while(it.hasNext()){
			java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
			String name = entry.getKey().toString();
			if(name.startsWith("search__")){
				n++;
				StringTokenizer stringTokenizer = new StringTokenizer(name,".");
				while(stringTokenizer.hasMoreTokens()){
				String temp =	stringTokenizer.nextToken();
					if(!temp.startsWith("search__")){
						name="search__"+temp;
					}
				}
				String value = ((String[])entry.getValue())[0];
				if(value==""||"".equals(value)){
					k++;
				}else{
					name = name.substring(8);
					buffer.append(" and " + name + " like '%"+value+"%'");
				}
			}
		}
		String js ="";
		if(k-n==0?true:false){
			js = super.abstractList();
		}else{
			initGrid();
			Page page = null;
			String tempsql = buffer.toString();
			String qxsql =this.sql_con();
			String sql=""; 
			if(type.equals("enterprise")){
			sql = "select one.*  from (select ps.id as id,ps.REG_NO as regNo ,ps.true_name as trueName"
				+ " ,ps.GENDER as gender,ps.EDUCATION as education ,ps.folk as folk ,ps.BIRTHDAY as birthdayDate,pb.name as peBzzBatchname,p.name as pname from pe_enterprise p\n"
				+ " inner join pe_bzz_student ps on ps.fk_enterprise_id = p.id\n"
				+ " inner join pe_bzz_batch pb on ps.fk_batch_id = pb.id where p.id = '"+ id+ "' "+qxsql
				+ "union\n"
				+ "select ps.id as id,ps.REG_NO as regNo ,ps.true_name as trueName ,ps.GENDER as gender ,ps.EDUCATION as education ,ps.folk as folk ,ps.BIRTHDAY as birthdayDate,pb.name as peBzzBatchname,p.name as pname "
				+ "from pe_enterprise p inner join pe_bzz_student ps on ps.fk_enterprise_id = p.id\n"
				+ " inner join pe_bzz_batch pb on ps.fk_batch_id = pb.id  where p.fk_parent_id ='"
				+ id + "' " +qxsql+
						") one where 1=1 " +tempsql;
			}
			if (type.equals("allnum")) {
				sql = "select one.*  from ( select ps.id as id , ps.reg_no as regNo, ps.true_name as trueName, ps.gender as gender ,ps.EDUCATION as education ,ps.folk as folk , ps.BIRTHDAY as birthdayDate, pb.name as peBzzBatchname, pe.name as pname "
						+ "  from pe_bzz_student ps, pe_bzz_batch pb, pe_enterprise pe "
						+ "  where ps.fk_batch_id = '"+id+"' "
						+ "  and ps.fk_batch_id = pb.id and ps.fk_enterprise_id = pe.id "+qxsql+") one where 1=1 "+tempsql;
			}
			//System.out.println("sql:"+sql);
			try {
				page = this.getGeneralService().getByPageSQL(sql, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
				List jsonObjects = JsonUtil.ArrayToJsonObjects(page.getItems(), this.getGridConfig().getListColumnConfig());
				Map map = new HashMap();
				if (page != null) {
					map.put("totalCount", page.getTotalCount());
					map.put("models", jsonObjects);
				}
				this.setJsonString(JsonUtil.toJSONString(map));
				JsonUtil.setDateformat("yyyy-MM-dd");
				js = this.json();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		return js;
	}
	
	private String sql_con(){
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		String sql_con = "";
		if (us.getRoleId().equals("2")
				|| us.getRoleId().equals("402880a92137be1c012137db62100006")) {
			sql_con = "     and ps.fk_enterprise_id in (select p1.id\n" +
						"   from pe_enterprise p, pe_enterprise p1\n" + 
						"   where p.fk_parent_id is null\n" + 
						"   and p1.fk_parent_id = p.id\n" + 
						"   and p.id = '"+us.getPriEnterprises().get(0).getId()+"'\n" + 
						"   union\n" + 
						"   select p.id from pe_enterprise p where p.id = '"+us.getPriEnterprises().get(0).getId()+"')";
		} else if (us.getRoleId().equals("402880f322736b760122737a60c40008")
				|| us.getRoleId().equals("402880f322736b760122737a968a0010")) {
			sql_con = " and ps.fk_enterprise_id in (select id from pe_enterprise where id='"
					+ us.getPriEnterprises().get(0).getId() + "')";
		}
		return sql_con;
	}

	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		String sql_con = "";
		if (us.getRoleId().equals("2")
				|| us.getRoleId().equals("402880a92137be1c012137db62100006")) {
			sql_con = "     and ps.fk_enterprise_id in (select p1.id\n" +
						"   from pe_enterprise p, pe_enterprise p1\n" + 
						"   where p.fk_parent_id is null\n" + 
						"   and p1.fk_parent_id = p.id\n" + 
						"   and p.id = '"+us.getPriEnterprises().get(0).getId()+"'\n" + 
						"   union\n" + 
						"   select p.id from pe_enterprise p where p.id = '"+us.getPriEnterprises().get(0).getId()+"')";
		} else if (us.getRoleId().equals("402880f322736b760122737a60c40008")
				|| us.getRoleId().equals("402880f322736b760122737a968a0010")) {
			sql_con = " and ps.fk_enterprise_id in (select id from pe_enterprise where id='"
					+ us.getPriEnterprises().get(0).getId() + "')";
		}
		
		Page page = null;
		String sql = null;
		try {
			if (type.equals("enterprise")) {
				sql = "select ps.id as id,ps.REG_NO as regNo ,ps.true_name as trueName"
						+ " ,ps.GENDER as gender , ps.EDUCATION as education ,ps.folk as folk ,ps.BIRTHDAY as birthdayDate,pb.name as peBzzBatchname,p.name as pname from pe_enterprise p\n"
						+ " inner join pe_bzz_student ps on ps.fk_enterprise_id = p.id\n"
						+ " inner join pe_bzz_batch pb on ps.fk_batch_id = pb.id where p.id = '"+ id+ "' "+sql_con
						+ "union\n"
						+ "select ps.id as id,ps.REG_NO as regNo ,ps.true_name as trueName ,ps.GENDER as gender, ps.EDUCATION as education ,ps.folk as folk ,ps.BIRTHDAY as birthdayDate,pb.name as peBzzBatchname,p.name as pname "
						+ "from pe_enterprise p inner join pe_bzz_student ps on ps.fk_enterprise_id = p.id\n"
						+ " inner join pe_bzz_batch pb on ps.fk_batch_id = pb.id  where p.fk_parent_id ='"
						+ id + "'"+sql_con;
			}
			if (type.equals("allnum")) {
				sql = " select ps.id as id , ps.reg_no as regNo, ps.true_name as trueName, ps.gender as gender ,ps.EDUCATION as education ,ps.folk as folk , ps.BIRTHDAY as birthdayDate, pb.name as peBzzBatchname, pe.name as pname "
						+ "  from pe_bzz_student ps, pe_bzz_batch pb, pe_enterprise pe "
						+ "  where ps.fk_batch_id = '"+id+"' "
						+ "  and ps.fk_batch_id = pb.id and ps.fk_enterprise_id = pe.id "+sql_con;
			}
			if(type.equals("cknum")||type.equals("hknum")||type.equals("tgnum")||type.equals("bknum")){
				sql=" select ps.id as id , ps.reg_no as regNo, ps.true_name as trueName, ps.gender as gender ,ps.EDUCATION as education ,ps.folk as folk , ps.BIRTHDAY as BIRTHDAY, pb.name as peBzzBatchname, pe.name as pname "
						+ "  from pe_bzz_student ps, pe_bzz_batch pb, pe_enterprise pe "
						+ "  where ps.fk_batch_id = '"+id+"' "
						+ "  and ps.fk_batch_id = pb.id and ps.fk_enterprise_id = pe.id and 1!= 1 "+sql_con;
			}
			StringBuffer sql_temp = new StringBuffer(sql);
			this.setSqlCondition(sql_temp);
			page = this.getGeneralService().getByPageSQL(sql_temp.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}

	public String viewDetail() {
		DetachedCriteria viwedc = DetachedCriteria.forClass(PeEnterprise.class);
		viwedc.add(Restrictions.eq("id", id));
		PeEnterprise enterprise =null;
		try {
			enterprise = this.getGeneralService().getSubEnterprise(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setPeEnterprise(enterprise);
		return "viewDetail";
	}

	public String viewPeDetail() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		DetachedCriteria dc = DetachedCriteria
				.forClass(PeEnterpriseManager.class);
		dc.add(Restrictions.eq("loginId", us.getLoginId()));
		List peList = new ArrayList();
		try {
			peList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}

		PeEnterpriseManager pe = (PeEnterpriseManager) peList.get(0);

		PeEnterprise enterprise = null;
		try {
			enterprise = this.getGeneralService().getSubEnterprise(
					pe.getPeEnterprise().getId());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.setPeEnterprise(enterprise);

		String sql = "";
		if (us.getRoleId().equals("2")
				|| us.getRoleId().equals("402880a92137be1c012137db62100006")) // 一级企业
		{
			sql = "select s.id as id from pe_bzz_student s ,pe_enterprise p,pe_enterprise p1 where p.fk_parent_id is null and p1.fk_parent_id=p.id and s.fk_enterprise_id=p1.id and p.id='"
					+ enterprise.getId() + "' union "
					+"select s.id as id from pe_bzz_student s ,pe_enterprise p "
					+" where s.fk_enterprise_id=p.id and p.id='"+enterprise.getId()+"'";
		} else // 二级企业
		{
			sql = "select a.id as id from pe_bzz_student a where a.fk_enterprise_id='"
					+ enterprise.getId() + "'";
		}
		List stuList = null;
		try {
			stuList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setNum(stuList.size() + "");

		return "viewPeDetail";
	}

	public String stuviewDetail() {
		this.setType(type);
		DetachedCriteria criteria = DetachedCriteria
				.forClass(PeBzzStudent.class);
		criteria.createCriteria("peBzzBatch", "peBzzBatch");
		criteria.createCriteria("peEnterprise", "peEnterprise");
		criteria.add(Restrictions.eq("id", id));
		try {
			this.setPeBzzStudent(this.getGeneralService().getStudentInfo(criteria));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "stuinfo";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
