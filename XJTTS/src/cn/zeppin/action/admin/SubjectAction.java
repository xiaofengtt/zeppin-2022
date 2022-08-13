package cn.zeppin.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.baseAction;
import cn.zeppin.entity.Subject;
import cn.zeppin.service.ISubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

public class SubjectAction extends baseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = LogManager.getLogger(OrganizationAction.class);
	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	private ISubjectService iSubjectService;
	
	/**
	 * @return the iSubjectService
	 */
	public ISubjectService getiSubjectService() {
		return iSubjectService;
	}

	/**
	 * @param iSubjectService
	 *            the iSubjectService to set
	 */
	public void setiSubjectService(ISubjectService iSubjectService) {
		this.iSubjectService = iSubjectService;
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

		String hql = " from Subject s where 1=1 ";
		
		// 以后其他搜索参数
		String q = request.getParameter("q");
		q = q == null ? "" : q;
		String stype = request.getParameter("stype");
		stype = stype == null ? "" : stype;

		if (q.length() > 0) {
			hql += " and s." + stype + " like '%" + q + "%' ";
		}
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split(" ");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			hql += " order by s." + sortname + " " + sorttype;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		
		List<Object> licount = this.iSubjectService.findByHSQL("select count(*) " + hql);
		if (licount != null && licount.size() > 0) {
			int records = Integer.parseInt(licount.get(0).toString());
			sb.append("\"TotalRecordCount\":" + records);
			sb.append(",");

		} else {
			sb.append("\"TotalRecordCount\":0");
			sb.append(",");
		}
		
		List<Subject> li = this.iSubjectService.getListForPage(hql, start, limit);
		sb.append("\"Records\":[");

		for (int i = 0; i < li.size(); i++) {
			Subject g = li.get(i);
			int id = g.getId();
			String name = g.getName();
			
			String sr = "{\"id\":" + id + ",\"name\":\"" + name  + "\"";
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
		
		if (Utlity.checkStringNull(name)) {
			sendResponse("ERROR", "名称为空");
			return;
		}
		
		List<Subject> litcs = this.iSubjectService.findByName(name);
		
		if (litcs.size() > 0) {
			sendResponse("ERROR", "已经存在学科:" + name);
			return;
		}
		
		Subject subject = new Subject();
		subject.setName(name);
		this.iSubjectService.add(subject);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Record\":");

		String sr = "{\"id\":" + subject.getId() + ",\"name\":\"" + name + "\"";
		sr += "}";

		sb.append(sr);

		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public void Edit(){
		initServlert();
		
		String name = request.getParameter("name");
		
		if (Utlity.checkStringNull(name)) {
			sendResponse("ERROR", "名称为空");
			return;
		}
		
		List<Subject> litcs = this.iSubjectService.findByName(name);
		
		
		String id = request.getParameter("id"); // id 用于编辑
		short eid = Short.parseShort(id);
		Subject subject = this.iSubjectService.get(eid);
		
		if (!subject.getName().equals(name)) {
			if (litcs.size() > 0) {
				sendResponse("ERROR", "已经存在学科:" + name);
				return;
			}
		}
		
		subject.setName(name);
		
		this.iSubjectService.update(subject);
		sendResponse("OK", "更新成功");
	}
	
	public void Delete(){
		initServlert();
		
		String id = request.getParameter("id");
		if (id != null) {
			short did = Short.parseShort(id);
			Subject subject = this.iSubjectService.get(did);
			if(subject.getTeachingSubjects()!=null && subject.getTeachingSubjects().size()>0){
				sendResponse("ERROR", "有教师选择此学科，不能删除此学科");
			}else{
				this.iSubjectService.delete(subject);
				sendResponse("OK", "删除成功");
			}
		}else{
			sendResponse("ERROR", "删除失败!不存在删除的学科");
		}
	}
}
