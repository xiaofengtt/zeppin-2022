package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeEnterpriseBacthService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public class PeSubEnterpriseAction extends MyBaseAction {

	private PeEnterpriseBacthService enterpriseBacthService;

	public PeEnterpriseBacthService getEnterpriseBacthService() {
		return enterpriseBacthService;
	}

	public void setEnterpriseBacthService(
			PeEnterpriseBacthService enterpriseBacthService) {
		this.enterpriseBacthService = enterpriseBacthService;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle("二级企业列表");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("企业名称"), "name");
		this.getGridConfig().addColumn(this.getText("企业编号"), "code", true,
				false, true, "");
		
		this.getGridConfig().addColumn(this.getText("上级企业"), "peEnterprise.name", false,
				false, true, "");
//		ColumnConfig c_name = new ColumnConfig(this.getText("上级企业"), "peEnterprise.name"
//				,false,true,true,"",false,1000,"");
//		System.out.println("id======================================>"+this.getBean().getPeEnterprise().getId());
//		c_name
//				.setComboSQL("select t.id,t.name as p_ame from pe_enterprise t where t.id='"
//						+ this.getBean().getPeEnterprise().getId() + "'");
//		this.getGridConfig().addColumn(c_name);
		/*this.getGridConfig().addColumn(this.getText("所属行业"), "industry");
		this.getGridConfig().addColumn(this.getText("通讯地址"), "address", false);
		this.getGridConfig().addColumn(this.getText("邮编"), "zipcode", false,true,true, Const.zip_for_extjs);
		this.getGridConfig().addColumn(this.getText("传真"), "fax", false);*/
		this.getGridConfig().addColumn(this.getText("所属行业"), "industry", false,
				true, false, "TextField",true,100);
		this.getGridConfig().addColumn(this.getText("通讯地址"), "address", false,
				true, false, "TextField",true,150);
		//this.getGridConfig().addColumn(this.getText("邮编"), "zipcode", false,true,true,Const.zip_for_extjs);
		this.getGridConfig().addColumn(this.getText("邮编"), "zipcode",  false,true, false, "TextField",true,10,Const.zip_for_extjs);
		//this.getGridConfig().addColumn(this.getText("传真"), "fax", false);
		this.getGridConfig().addColumn(this.getText("传真"), "fax", false,
				true, false, "TextField",true,20);
		
		this.getGridConfig().addColumn(this.getText("省"), "infoSheng", false,
				true, true, "TextField",true,100);
		this.getGridConfig().addColumn(this.getText("市"), "infoShi", false,
				true, true, "TextField",true,100);
		this.getGridConfig().addColumn(this.getText("街道"), "infoJiedao", false,
				true, true, "TextField",true,100);
	

		this.getGridConfig().addMenuScript(this.getText("返回"),
				"{window.location='/entity/basic/peEnterprise.action'}");

		this.getGridConfig().addColumn(this.getText("负责人姓名"), "fzrName", false,
				true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("负责人性别"), "fzrXb", false,
				true, false, "TextField",true,5);
		this.getGridConfig().addColumn(this.getText("负责人部门"), "fzrDepart",
				false, true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("负责人职务"), "fzrPosition",
				false, true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("负责人办公电话"), "fzrPhone",
				false, true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("负责人移动电话"), "fzrMobile",
				false, true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("负责人电子邮件"), "fzrEmail",
				false, true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("负责人通讯地址"), "fzrAddress",
				false, true, false, "TextField",true,150);
		this.getGridConfig().addColumn(this.getText("联系人姓名"), "lxrName", false,
				true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("联系人性别"), "lxrXb", false,
				true, false, "TextField",true,5);
		this.getGridConfig().addColumn(this.getText("联系人部门"), "lxrDepart",
				false, true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("联系人职务"), "lxrPosition",
				false, true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("联系人办公电话"), "lxrPhone",
				false, true, false,"TextField",true,25);
		this.getGridConfig().addColumn(this.getText("联系人移动电话"), "lxrMobile",
				false, true, false,"TextField",true,25);
		this.getGridConfig().addColumn(this.getText("联系人电子邮件"), "lxrEmail",
				false, true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("联系人通讯地址"), "lxrAddress",
				false, true, false, "TextField",true,150);
		
		this.getGridConfig().addColumn(this.getText("学员人数"), "num", false,
				false, false, "");
		this
		.getGridConfig()
		.addRenderScript(
				this.getText("学员人数"),
				"{return '<a href=peDetail.action?id='+record.data['id']+'&type=enterprise><font color=#0000ff ><u>'+record.data['num']+'</u></font></a>';}",
				"");
		this
		.getGridConfig()
		.addRenderScript(
				this.getText("企业信息"),
				"{return '<a href=peDetail_viewDetail.action?id='+record.data['id']+'&num='+record.data['num']+'>查看</a>';}",
				"");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeEnterprise.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/peSubEnterprise";
	}

	public PeEnterprise getBean() {
		// TODO Auto-generated method stub
		return (PeEnterprise) super.superGetBean();
	}

	public void setBean(PeEnterprise enterprise) {
		// TODO Auto-generated method stub
		super.superSetBean(enterprise);
	}
	
	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria enterdc = DetachedCriteria
				.forClass(PeEnterprise.class);
		enterdc.createCriteria("peEnterprise", "peEnterprise");
		enterdc.add(Restrictions.eq("id", this.getBean().getId()));
		try {
			page = this.getGeneralService().getByPage(enterdc,
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
			String id = this.getBean().getId();
			
			if (page != null) {
				page.setItems(JsonUtil.ArrayToJsonObjects(page.getItems(), this
						.getGridConfig().getListColumnConfig()));
				Container o = new Container();
				o.setBean(page.getItems().get(0));
				List list = new ArrayList();
				list.add(o);
				map.put("totalCount",1);
				map.put("models", list);
			}
			this.setJsonString(JsonUtil.toJSONString(map));
			JsonUtil.setDateformat("yyyy-MM-dd");
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return json();
	}

	public String abstractList() {

		ActionContext context = ActionContext.getContext();
		String pid = this.getBean().getPeEnterprise().getId();
		Map map1 = context.getParameters();
		Iterator it = map1.entrySet().iterator();
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sql_con = "";
		if (us.getRoleId().equals("2"))
			sql_con = " and pe.id='" + us.getPriEnterprises().get(0).getId()
					+ "'";
		int k = 0;
		int n = 0;
		String tempsql = " select * from (  select p.id as id, p.name  as name, p.code as code,pp.name  as p_name, p.industry as industry,p.address as address,\n"
			+ "  p.zipcode as zipcode,p.fax as fax,p.info_sheng as infoSheng,p.info_shi as infoShi,p.info_jiedao as infoJiedao,p.fzr_name as fzrName,p.fzr_xb as fzrXb, p.fzr_depart as fzrDepart, p.fzr_position  as fzrPosition,\n"
			+ "  p.fzr_phone as fzrPhone, p.fzr_mobile as fzrMobile,  p.fzr_email as fzrEmail, p.fzr_address  as fzrAddress,\n"
			+ "  p.lxr_name as lxrName,  p.lxr_xb as lxrXb,p.lxr_depart as lxrDepart, p.lxr_position as lxrPosition,  p.lxr_phone as lxrPhone,\n"
			+ "  p.lxr_mobile as lxrMobile, p.lxr_email as lxrEmail, p.lxr_address  as lxrAddress, count(ps.id)\n"
			+ "  from pe_enterprise p  inner join pe_enterprise pp on p.fk_parent_id = pp.id\n"
			+ "  left outer join pe_bzz_student ps on p.id = ps.fk_enterprise_id\n"
			+ "  where p.fk_parent_id = '"+pid+"'  and p.fk_parent_id is not null\n "
			+"   group by  p.id, p.name, p.code, pp.name , p.industry  ,p.address , p.zipcode , p.fax , p.info_sheng,p.info_shi,p.info_jiedao,p.fzr_name, p.fzr_xb ,p.fzr_depart ,\n"
			+" p.fzr_position  , p.fzr_phone , p.fzr_mobile , p.fzr_email , p.fzr_address , p.lxr_name , p.lxr_xb ,p.lxr_depart, p.lxr_position ,\n"	
			+" p.lxr_phone , p.lxr_mobile , p.lxr_email , p.lxr_address ) p where 1=1 ";
		StringBuffer buffer = new StringBuffer(tempsql);
		while (it.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
			String name = entry.getKey().toString();
			if (name.startsWith("search__")) {
				n++;
				String value = ((String[]) entry.getValue())[0];
				if (value == "" || "".equals(value)) {
					k++;
				} else {
					name = name.substring(8);
					buffer
							.append(" and p."
									+ name
									+ " like '%"
									+ value
									+ "%'");
				}
			}
		}
		String js = null;
		if (k - n == 0 ? true : false) {
			js = super.abstractList();
		} else {
			initGrid();
			Page page = null;
			String sql = buffer.toString();
			try {
				page = this.getGeneralService().getByPageSQL(sql,
						Integer.parseInt(this.getLimit()),
						Integer.parseInt(this.getStart()));
				List jsonObjects = JsonUtil.ArrayToJsonObjects(page.getItems(),
						this.getGridConfig().getListColumnConfig());
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

	public Page list() {
		Page page = null;

		String pid = this.getBean().getPeEnterprise().getId();
		DetachedCriteria tempdc = DetachedCriteria.forClass(PeEnterprise.class);
		tempdc.add(Restrictions.eq("id", pid));
		String pcode = null;
		try {
			pcode = ((PeEnterprise) this.getGeneralService().getList(tempdc)
					.get(0)).getCode();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		Map session = ActionContext.getContext().getSession();
		session.put("pcode", pcode);
		session.put("pid", pid);

		String sql = "  select p.id as id, p.name  as name, p.code as code,pp.name  as p_name, p.industry as industry,p.address as address,\n"
				+ "  p.zipcode as zipcode,p.fax as fax,p.info_sheng as infoSheng,p.info_shi as infoShi,p.info_jiedao as infoJiedao,p.fzr_name as fzrName,p.fzr_xb as fzrXb, p.fzr_depart as fzrDepart, p.fzr_position  as fzrPosition,\n"
				+ "  p.fzr_phone as fzrPhone, p.fzr_mobile as fzrMobile,  p.fzr_email as fzrEmail, p.fzr_address  as fzrAddress,\n"
				+ "  p.lxr_name as lxrName,  p.lxr_xb as lxrXb, p.lxr_depart as lxrDepart, p.lxr_position as lxrPosition,  p.lxr_phone as lxrPhone,\n"
				+ "  p.lxr_mobile as lxrMobile, p.lxr_email as lxrEmail, p.lxr_address  as lxrAddress, count(ps.id)\n"
				+ "  from pe_enterprise p  inner join pe_enterprise pp on p.fk_parent_id = pp.id\n"
				+ "  left outer join pe_bzz_student ps on p.id = ps.fk_enterprise_id\n"
				+ "  where p.fk_parent_id = '"+pid+"'  and p.fk_parent_id is not null\n"
				+ "  group by  p.id, p.name, p.code, pp.name , p.industry  ,p.address , p.zipcode , p.fax , p.info_sheng,p.info_shi,p.info_jiedao,p.fzr_name, p.fzr_xb , p.fzr_depart, \n"
				+ "  p.fzr_position  , p.fzr_phone , p.fzr_mobile , p.fzr_email , p.fzr_address , p.lxr_name , p.lxr_xb , p.lxr_depart , p.lxr_position ,\n"
				+ "  p.lxr_phone , p.lxr_mobile , p.lxr_email , p.lxr_address  ";

		try {
			StringBuffer sql_temp = new StringBuffer(sql);
			this.setSqlCondition(sql_temp);
			page = this.getGeneralService().getByPageSQL(sql_temp.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return page;
	}

	public Map add() {
		Map map = new HashMap();
		String pcode = (String) ServletActionContext.getContext().getSession()
				.get("pcode");
		this.setBean((PeEnterprise) super.setSubIds(this.getBean()));
		PeEnterprise instance = null;
		boolean flag = false;
		try {
			instance = this.enterpriseBacthService.save(this.getBean(), pcode);
			map.put("success", "true");
			map.put("info", "添加成功");
			logger.info("添加成功! id= " + instance.getId());
		} catch (Exception e) {
			return super.checkAlternateKey(e, "添加");
		}
		return map;
	}

}
