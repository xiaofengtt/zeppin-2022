package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

/**
 * @param
 * @version 创建时间：2009-6-22 下午02:15:44
 * @return
 * @throws PlatformException
 *             类说明
 */
public class PeBzzBatchAction extends MyBaseAction<PeBzzBatch> {

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.getGridConfig().setTitle("学期列表");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学期名称"), "name");
		this.getGridConfig().addColumn(this.getText("开始时间"), "startDate");
		this.getGridConfig().addColumn(this.getText("结束时间"), "endDate");
		this.getGridConfig().addColumn(this.getText("学时标准"), "standards",
				false, true, true, Const.twoNum_for_extjs);
		this.getGridConfig().addColumn(this.getText("考试时间"), "courseDate",
				true, true, true, "");
		this.getGridConfig().addColumn(this.getText("课程评估开始时间"),
				"evalStartDate", false, true, true, "");
		this.getGridConfig().addColumn(this.getText("课程评估结束时间"), "evalEndDate",
				false, true, true, "");
		this.getGridConfig().addColumn(this.getText("提问开始时间"), "askStartDate",
				false, true, true, "");
		this.getGridConfig().addColumn(this.getText("提问结束时间"), "askEndDate",
				false, true, true, "");

		this.getGridConfig().addColumn(this.getText("学员总数"), "stunum", false,
				false, false, "");
		this.getGridConfig().addColumn(this.getText("参加考试人数"), "cknum", false,
				false, false, "");
		this.getGridConfig().addColumn(this.getText("缓考人数"), "hknum", false,
				false, false, "");
		this.getGridConfig().addColumn(this.getText("通过人数"), "tgnum", false,
				false, false, "");
		this.getGridConfig().addColumn(this.getText("补考人数"), "bknum", false,
				false, false, "");

		this
				.getGridConfig()
				.addRenderScript(
						this.getText("学员人数"),
						"{return '<a href=peDetail.action?id='+record.data['id']+'&type=allnum><font color=#0000ff ><u>'+record.data['stunum']+'</u></font></a>';}",
						"");
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("参加考试人数"),
						"{return '<a href=peDetail.action?id='+record.data['id']+'&type=cknum><font color=#0000ff ><u>'+record.data['cknum']+'</u></font></a>';}",
						"");
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("缓考人数"),
						"{return '<a href=peDetail.action?id='+record.data['id']+'&type=hknum><font color=#0000ff ><u>'+record.data['hknum']+'</u></font></a>';}",
						"");
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("通过人数"),
						"{return '<a href=peDetail.action?id='+record.data['id']+'&type=tgnum><font color=#0000ff ><u>'+record.data['tgnum']+'</u></font></a>';}",
						"");
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("补考人数"),
						"{return '<a href=peDetail.action?id='+record.data['id']+'&type=bknum><font color=#0000ff ><u>'+record.data['bknum']+'</u></font></a>';}",
						"");
	}

	public void setEntityClass() {
		this.entityClass = PeBzzBatch.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/peBzzBatch";
	}

	public void setBean(PeBzzBatch instance) {
		super.superSetBean(instance);
	}

	public PeBzzBatch getBean() {
		return super.superGetBean();
	}

	public Map delete() {
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				DetachedCriteria criteria = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
				criteria.createCriteria("peBzzBatch", "peBzzBatch");
				criteria.add(Restrictions.in("peBzzBatch.id", ids));
				
				DetachedCriteria studc = DetachedCriteria.forClass(PeBzzStudent.class);
				studc.createCriteria("peBzzBatch", "peBzzBatch");
				studc.add(Restrictions.in("peBzzBatch.id", ids));
				try {
					List<PeEnterprise> plist = this.getGeneralService().getList(criteria);
					List<PeBzzStudent> stulist = this.getGeneralService().getList(studc);
					if(plist.size()>0||stulist.size()>0){
						map.clear();
						map.put("success", "false");
						map.put("info", "该学期下已经开课或者存有相关联的学员,无法删除!");
						return map;
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}
				try{
					this.getGeneralService().deleteByIds(idList);
					map.put("success", "true");
					map.put("info", "删除成功");
				}catch(RuntimeException e){
					return this.checkForeignKey(e);
				}catch(Exception e1){
					map.clear();
					map.put("success", "false");
					map.put("info", "删除失败");
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}
	
	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria enterdc = DetachedCriteria.forClass(PeBzzBatch.class);
		enterdc.add(Restrictions.eq("id", this.getBean().getId()));
		try {
			page = this.getGeneralService().getByPage(enterdc,
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
			if (page != null) {
				page.setItems(JsonUtil.ArrayToJsonObjects(page.getItems(), this
						.getGridConfig().getListColumnConfig()));
				Container o = new Container();
				o.setBean(page.getItems().get(0));
				List list = new ArrayList();
				list.add(o);
				map.put("totalCount", 1);
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
		Map map1 = context.getParameters();
		Iterator it = map1.entrySet().iterator();
		int k = 0;
		int n = 0;
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sql_con = "";
		if (us.getRoleId().equals("2")
				|| us.getRoleId().equals("402880a92137be1c012137db62100006")) {
			sql_con = "     where ps.fk_enterprise_id in (select p1.id\n"
					+ "   from pe_enterprise p, pe_enterprise p1\n"
					+ "   where p.fk_parent_id is null\n"
					+ "   and p1.fk_parent_id = p.id\n" + "   and p.id = '"
					+ us.getPriEnterprises().get(0).getId() + "'\n"
					+ "   union\n"
					+ "   select p.id from pe_enterprise p where p.id = '"
					+ us.getPriEnterprises().get(0).getId() + "')";
		} else if (us.getRoleId().equals("402880f322736b760122737a60c40008")
				|| us.getRoleId().equals("402880f322736b760122737a968a0010")) {
			sql_con = " where ps.fk_enterprise_id in (select id from pe_enterprise where id='"
					+ us.getPriEnterprises().get(0).getId() + "')";
		}

		String tempsql= "					select pp.id as id,\n"
				+ "                       pp.name as name,\n"
				+ "                       pp.startDate as startDate,\n"
				+ "                       pp.endDate as endDate,\n"
				+ "                       pp.standards as standards,\n"
				+ "                       pp.courseDate as courseDate,\n"
				+ "                       pp.evalStartDate as evalStartDate,\n"
				+ "                       pp.evalEndDate as evalEndDate,\n"
				+ "                       pp.askStartDate as askStartDate,\n"
				+ "                       pp.askEndDate as askEndDate,\n"
				+ "                       pp.stunum,\n"
				+ "                       pp.cknum,\n"
				+ "                       pp. hknum,\n"
				+ "                       pp. tgnum,\n"
				+ "                       pp. bknum\n"
				+ "                   from (select pb.id as id,\n"
				+ "                       pb.name as name,\n"
				+ "                       pb.start_time as startDate,\n"
				+ "                       pb.end_time as endDate,\n"
				+ "                       pb.standards as standards,\n"
				+ "                       pb.eval_start_date as evalStartDate,\n"
				+ "                       pb.eval_end_date as evalEndDate,\n"
				+ "                       pb.ask_start_date as askStartDate,\n"
				+ "                       pb.ask_end_date as askEndDate,\n"
				+ "                       pb.course_date as courseDate,\n"
				+ "                       count(ps.id) as stunum,\n"
				+ "                       0 as cknum,\n"
				+ "                       0 as hknum,\n"
				+ "                       0 as tgnum,\n"
				+ "                       0 as bknum\n"
				+ "                  from pe_bzz_batch pb\n"
				+ "                  left outer join (select fk_enterprise_id, fk_batch_id, id from pe_bzz_student ps "
				+ sql_con
				+"					) ps on ps.fk_batch_id = pb.id \n"
				+ "                 group by pb.id,\n"
				+ "                          pb.name,\n"
				+ "                          pb.start_time,\n"
				+ "                          pb.end_time,\n"
				+ "                          pb.standards,\n"
				+ "                           pb.course_date ,"
				+ "                          pb.eval_start_date,\n"
				+ "                          pb.eval_end_date,\n"
				+ "                          pb.ask_start_date,\n"
				+ "                          pb.ask_end_date  ) pp  where 1=1 ";
		StringBuffer buffer = new StringBuffer(tempsql);
		while (it.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
			String name = entry.getKey().toString();
			String sq ="";
			if (name.startsWith("search__")) {
				n++;
				String value = ((String[]) entry.getValue())[0];
				if (value == "" || "".equals(value)) {
					k++;
				} else {
					name = name.substring(8);
					if(!name.contains("Date")){
						sq = "like '%" + value + "%'";
					}else if(name.contains("startDate")){
						sq =">= to_date('"+value+"','yyyy-MM-dd')";
					}
					else if(name.contains("endDate")){
						sq ="<= to_date('"+value+"','yyyy-MM-dd')";
					}
					else if(name.contains("courseDate")){
						sq ="<= to_date('"+value+"','yyyy-MM-dd')";
					}
					buffer.append("and " + name + " "+sq);
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

		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sql_con = "";
		if (us.getRoleId().equals("2")
				|| us.getRoleId().equals("402880a92137be1c012137db62100006")) {
			sql_con = "     where ps.fk_enterprise_id in (select p1.id\n"
					+ "   from pe_enterprise p, pe_enterprise p1\n"
					+ "   where p.fk_parent_id is null\n"
					+ "   and p1.fk_parent_id = p.id\n" + "   and p.id = '"
					+ us.getPriEnterprises().get(0).getId() + "'\n"
					+ "   union\n"
					+ "   select p.id from pe_enterprise p where p.id = '"
					+ us.getPriEnterprises().get(0).getId() + "')";
		} else if (us.getRoleId().equals("402880f322736b760122737a60c40008")
				|| us.getRoleId().equals("402880f322736b760122737a968a0010")) {
			sql_con = " where ps.fk_enterprise_id in (select id from pe_enterprise where id='"
					+ us.getPriEnterprises().get(0).getId() + "')";
		}

		String sql = "select pp.id as id,\n"
				+ "                       pp.name as name,\n"
				+ "                       pp.startDate as startDate,\n"
				+ "                       pp.endDate as endDate,\n"
				+ "                       pp.standards as standards,\n"
				+ "                       pp.courseDate as courseDate,\n"
				+ "                       pp.evalStartDate as evalStartDate,\n"
				+ "                       pp.evalEndDate as evalEndDate,\n"
				+ "                       pp.askStartDate as askStartDate,\n"
				+ "                       pp.askEndDate as askEndDate,\n"
				+ "                       pp.stunum,\n"
				+ "                       pp.cknum,\n"
				+ "                       pp. hknum,\n"
				+ "                       pp. tgnum,\n"
				+ "                       pp. bknum\n"
				+ "                   from (select pb.id as id,\n"
				+ "                       pb.name as name,\n"
				+ "                       pb.start_time as startDate,\n"
				+ "                       pb.end_time as endDate,\n"
				+ "                       pb.standards as standards,\n"
				+ "                       pb.eval_start_date as evalStartDate,\n"
				+ "                       pb.eval_end_date as evalEndDate,\n"
				+ "                       pb.ask_start_date as askStartDate,\n"
				+ "                       pb.ask_end_date as askEndDate,\n"
				+ "                       pb.course_date as courseDate,\n"
				+ "                       count(ps.id) as stunum,\n"
				+ "                       0 as cknum,\n"
				+ "                       0 as hknum,\n"
				+ "                       0 as tgnum,\n"
				+ "                       0 as bknum\n"
				+ "                  from pe_bzz_batch pb\n"
				+ "                  left outer join (select fk_enterprise_id, fk_batch_id, id from pe_bzz_student ps"
				+sql_con
				+"					) ps on ps.fk_batch_id = pb.id \n"
				+ "                 group by pb.id,\n"
				+ "                          pb.name,\n"
				+ "                          pb.start_time,\n"
				+ "                          pb.end_time,\n"
				+ "                          pb.standards,\n"
				+ "                           pb.course_date ,"
				+ "                          pb.eval_start_date,\n"
				+ "                          pb.eval_end_date,\n"
				+ "                          pb.ask_start_date,\n"
				+ "                          pb.ask_end_date  ) pp";
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
	
	public void checkBeforeAdd() throws EntityException {
		if (this.getBean().getStartDate().after(this.getBean().getEndDate())) {
			throw new EntityException("学期的开始时间不能晚于结束时间");
		}
		if (this.getBean().getEvalStartDate().after(this.getBean().getEvalEndDate())) {
			throw new EntityException("学期的课程评估开始时间不能晚于结束时间");
		}
		if (this.getBean().getAskStartDate().after(this.getBean().getAskEndDate())) {
			throw new EntityException("学期的提问开始时间不能晚于结束时间");
		}
	}
	
	public void checkBeforeUpdate() throws EntityException {
		if (this.getBean().getStartDate().after(this.getBean().getEndDate())) {
			throw new EntityException("学期的开始时间不能晚于结束时间");
		}
		if (this.getBean().getEvalStartDate().after(this.getBean().getEvalEndDate())) {
			throw new EntityException("学期的课程评估开始时间不能晚于结束时间");
		}
		if (this.getBean().getAskStartDate().after(this.getBean().getAskEndDate())) {
			throw new EntityException("学期的提问开始时间不能晚于结束时间");
		}
	}

}
