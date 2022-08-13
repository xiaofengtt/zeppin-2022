package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.elective.SemesterOpenCourseService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;

import com.whaty.platform.util.Const;

public class PrBzzTchOpenCourseReadyAction extends
		MyBaseAction{

	private String type;
	private String pid;

	public String getType() {
		return type;
	}

	public String getPid() {
		return pid;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPid(String pid) {

		this.pid = pid;
	}

	public void initGrid() {
		this.getGridConfig().setTitle("批量课程列表");
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名称"), "name");
		this.getGridConfig().addColumn(this.getText("课程代码"), "code");
		this.getGridConfig().addColumn(this.getText("学时"), "time", false, true,
				true, Const.number_for_extjs);
		this.getGridConfig().addColumn(this.getText("是否有效"),
				"enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("授课教师"), "teacher", false,
				true, true, "");
		//this.getGridConfig().addColumn(this.getText("教师简介"), "teacherNote",
		//		false, true, true, "");
		//this.getGridConfig().addColumn(this.getText("课程简介"), "note", false,
		//		true, true, "TextArea", true, 1000);
		this.getGridConfig().addMenuFunction(this.getText("确认开课"));
		this
				.getGridConfig()
				.addMenuScript(this.getText("返回"),
						"{window.location='/entity/teaching/prBzzTchOpenCourse.action'}");

	}

	public Map updateColumn() {
		pid = (String) ServletActionContext.getRequest().getSession()
				.getAttribute("pid");

		type = (String) ServletActionContext.getRequest().getSession()
				.getAttribute("type");
		Map map = new HashMap();
		List idList = null;
		if (this.getIds() != null && this.getIds().length() > 0) {

			String[] ids = getIds().split(",");
			idList = new ArrayList();

			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			try {

				DetachedCriteria cdc = DetachedCriteria
						.forClass(PrBzzTchOpencourse.class);
				cdc.createCriteria("peBzzTchCourse", "peBzzTchCourse");
				cdc.createCriteria("peBzzBatch", "peBzzBatch");
				cdc.add(Restrictions.eq("peBzzBatch.id", pid));
				cdc.add(Restrictions.in("peBzzTchCourse.id", ids));
				// 检查是否有重复开课信息
				List<PrBzzTchOpencourse> plist = this.getGeneralService()
						.getList(cdc);
				if (plist.size() > 0) {
					map.clear();
					map.put("success", "false");
					map.put("info", "重复数据");
					return map;
				} else {
					DetachedCriteria detachedCriteria = DetachedCriteria
							.forClass(PeBzzTchCourse.class);
					detachedCriteria.add(Restrictions.in("id", ids));
					List<PeBzzTchCourse> list = this.getGeneralService()
							.getList(detachedCriteria);
					List<PrBzzTchOpencourse> oplist = new ArrayList<PrBzzTchOpencourse>();
					for (int n = 0; n < list.size(); n++) {
						PrBzzTchOpencourse opencourse = new PrBzzTchOpencourse();
						PeBzzBatch batch = new PeBzzBatch();
						EnumConst const1 = new EnumConst();
						const1.setId(type);
						batch.setId(pid);
						opencourse.setPeBzzTchCourse(list.get(n));
						opencourse.setPeBzzBatch(batch);
						opencourse.setEnumConstByFlagCourseType(const1);
						oplist.add(opencourse);
					}
					this.getGeneralService().saveList(oplist);
//					// 取得已开课的对象
//					DetachedCriteria newdc = DetachedCriteria
//							.forClass(PrBzzTchOpencourse.class);
//					newdc.createCriteria("peBzzTchCourse", "peBzzTchCourse");
//					newdc.add(Restrictions.in("peBzzTchCourse.id", ids));
//					List<PrBzzTchOpencourse> bzzlist = this.getGeneralService()
//							.getList(newdc);
//					// 取得当前批次下的学生对象
//					DetachedCriteria studentdc = DetachedCriteria
//							.forClass(PeBzzStudent.class);
//					studentdc.createCriteria("peBzzBatch", "peBzzBatch");
//					studentdc.add(Restrictions.eq("peBzzBatch.id", pid));
//					List<PeBzzStudent> stulist = this.getGeneralService()
//							.getList(studentdc);
//					// 插入 PrBzzTchStuElective
//					for (int m = 0; m < bzzlist.size(); m++) {
//						for (int n = 0; n < stulist.size(); n++) {
//							Date now = new Date();
//							TrainingCourseStudent tr = new TrainingCourseStudent();
//							tr.setPercent(Long.parseLong("0"));
//							tr.setSsoUser(stulist.get(n).getSsoUser());
//							tr.setPrBzzTchOpencourse(bzzlist.get(m));
//							tr.setLearnStatus("INCOMPLETE");
//							PrBzzTchStuElective elective = new PrBzzTchStuElective();
//							elective.setPrBzzTchOpencourse(bzzlist.get(m));
//							elective.setPeBzzStudent(stulist.get(n));
//							elective.setElectiveDate(now);
//							tr.setPrBzzTchStuElective(elective);
//							elective.setTrainingCourseStudent(tr);
//							this.getGeneralService().save(elective);
//						}
//					}
				}
			} catch (EntityException e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
			map.clear();
			map.put("success", "true");
			map.put("info", this.getText(String.valueOf(idList.size())
					+ "条记录操作成功"));

		} else {
			map.put("success", "false");
			map.put("info", "parameter value error");
		}
		return map;
	}

	public void setEntityClass() {
		this.entityClass=PrBzzTchOpencourse.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/teaching/prBzzTchOpenCourseReady";

	}

	public PrBzzTchOpencourse getBean() {
		// TODO Auto-generated method stub
		return (PrBzzTchOpencourse) super.superGetBean();
	}

	public void setBean(PrBzzTchOpencourse opencourse) {
		// TODO Auto-generated method stub
		super.superSetBean(opencourse);
	}

	public DetachedCriteria initDetachedCriteria() {
		ActionContext ctx = ActionContext.getContext();
		Map session = ctx.getSession();
		DetachedCriteria ddc = DetachedCriteria
				.forClass(PrBzzTchOpencourse.class);
		ddc.createCriteria("peBzzBatch", "peBzzBatch");
		ddc.add(Restrictions.eq("peBzzBatch.id", pid));
		DetachedCriteria dcd = null;
		try {
			List<PrBzzTchOpencourse> templist = this.getGeneralService()
					.getList(ddc);
			int len = templist.size();
			String[] tt = null;
			if (len > 0) {
				tt = new String[len];
				for (int i = 0; i < len; i++) {
					tt[i] = templist.get(i).getPeBzzTchCourse().getId();
				}
			}
			dcd = DetachedCriteria.forClass(PeBzzTchCourse.class);
			if (len > 0)
				dcd.add(Restrictions.not(Restrictions.in("id", tt)));
			DetachedCriteria dcFlagIsvalid = dcd.createCriteria(
					"enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
			dcFlagIsvalid.add(Restrictions.eq("id", "2"));
			//dcd.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
			session.put("pid", pid);
			session.put("type", type);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return dcd;
	}

}
