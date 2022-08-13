package cn.zeppin.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.baseAction;
import cn.zeppin.entity.Grade;
import cn.zeppin.service.IGradeService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

public class GradeAction extends baseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = LogManager.getLogger(OrganizationAction.class);
	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	private IGradeService iGradeService;
	
	/**
	 * @return the iGradeService
	 */
	public IGradeService getiGradeService() {
		return iGradeService;
	}

	/**
	 * @param iGradeService
	 *            the iGradeService to set
	 */
	public void setiGradeService(IGradeService iGradeService) {
		this.iGradeService = iGradeService;
	}
	
	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}
	
	public String initPage() {
		return "init";
	}
	
	public void sendResponse(String status, String value) {
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"" + status + "\"");
		checkSB.append(",");
		checkSB.append("\"Message\":\"" + value + "\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
	}
	
	public void List(){
		initServlert();
		
		String ist = (String) request.getParameter("jtStartIndex");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "0";
		}

		// 显示的条数
		String ien = (String) request.getParameter("jtPageSize");
		if (ien == null || ien.equals("")) {
			ien = DictionyMap.maxPageSize + "";
		}

		int start = Integer.parseInt(ist);
		int limit = DictionyMap.maxPageSize;
		limit = Integer.parseInt(ien);

		// 排序
		String sort = request.getParameter("jtSorting");

		String hql = " from Grade g where 1=1 ";

		// 以后其他搜索参数
		String q = request.getParameter("q");
		q = q == null ? "" : q;
		String stype = request.getParameter("stype");
		stype = stype == null ? "" : stype;

		if (q.length() > 0) {
			hql += " and g." + stype + " like '%" + q + "%' ";
		}
		
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split(" ");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			hql += " order by g." + sortname + " " + sorttype;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		
		List<Object> licount = this.iGradeService.findByHSQL("select count(*) " + hql);
		if (licount != null && licount.size() > 0) {
			int records = Integer.parseInt(licount.get(0).toString());
			sb.append("\"TotalRecordCount\":" + records);
			sb.append(",");

		} else {
			sb.append("\"TotalRecordCount\":0");
			sb.append(",");
		}
		
		List<Grade> li = this.iGradeService.getListForPage(hql, start, limit);
		sb.append("\"Records\":[");

		for (int i = 0; i < li.size(); i++) {
			Grade g = li.get(i);
			int id = g.getId();
			String name = g.getName();
			String isSchool = g.getIsSchool()?"1":"0";
			
			String sr = "{\"id\":" + id + ",\"name\":\"" + name + "\",\"isSchool\":\"" + isSchool + "\"";
			sr += "},";
			sb.append(sr);
		}

		if (li.size() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	
	public void Add(){
		initServlert();
		
		String name = request.getParameter("name");
		String isSchool = request.getParameter("isSchool");
		
		if (Utlity.checkStringNull(name)) {
			sendResponse("ERROR", "名称为空");
			return;
		}
		
		List<Grade> litcs = this.iGradeService.findByName(name);
		
		if (litcs.size() > 0) {
			sendResponse("ERROR", "已经存在学段:" + name);
			return;
		}
		
		Grade grade = new Grade();
		grade.setName(name);
		grade.setIsSchool(Boolean.parseBoolean(isSchool));
		this.iGradeService.add(grade);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Record\":");

		String sr = "{\"id\":" + grade.getId() + ",\"name\":\"" + name + "\",";
		sr += "\"isSchool\":\"" + isSchool + "\"";
		sr += "}";

		sb.append(sr);

		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public void Edit(){
		initServlert();
		
		String name = request.getParameter("name");
		String isSchool = request.getParameter("isSchool");
		
		if (Utlity.checkStringNull(name)) {
			sendResponse("ERROR", "名称为空");
			return;
		}
		
		List<Grade> litcs = this.iGradeService.findByName(name);
		
		String id = request.getParameter("id"); // id 用于编辑
		short eid = Short.parseShort(id);
		Grade grade = this.iGradeService.get(eid);
		
		if (!grade.getName().equals(name)) {
			if (litcs.size() > 0) {
				sendResponse("ERROR", "已经存在学段:" + name);
				return;
			}
		}
		
		grade.setName(name);
		grade.setIsSchool(Boolean.parseBoolean(isSchool));
		
		this.iGradeService.update(grade);
		sendResponse("OK", "更新成功");
	}
	
	public void Delete(){
		initServlert();
		
		String id = request.getParameter("id");
		if (id != null) {
			short did = Short.parseShort(id);
			Grade grade = this.iGradeService.get(did);
//			if(grade.getOrganizations()!=null && grade.getOrganizations().size()>0){
//				sendResponse("ERROR", "有机构选择此学段，不能删除此学段");
//			}else 
			if(grade.getTeachingGrades()!=null && grade.getTeachingGrades().size()>0){
				sendResponse("ERROR", "有教师选择此学段，不能删除此学段");
			}else{
				this.iGradeService.delete(grade);
				sendResponse("OK", "删除成功");
			}
		}else{
			sendResponse("ERROR", "删除失败!不存在删除的学段");
		}
	}
	
	public void getGrade() {
		initServlert();
		
		String getStuGradesHQL = " from Grade";
		List<Grade> lstGrades = iGradeService.getListByHSQL(getStuGradesHQL);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\",");
		sb.append("\"Options\":");
		String ret = "[";
		String st = "";
		for (Grade g :lstGrades){
			st += "{\"Value\":" + g.getId() + ",\"DisplayText\":\"" + g.getName() + "\"},";
		}
		if (st.length() > 0) {
			st = st.substring(0, st.length() - 1);
		}
		ret += st + "]";

		sb.append(ret);
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
}
