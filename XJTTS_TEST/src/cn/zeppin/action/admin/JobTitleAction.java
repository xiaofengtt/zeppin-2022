package cn.zeppin.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.baseAction;
import cn.zeppin.entity.JobTitle;
import cn.zeppin.service.IJobTitleService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

public class JobTitleAction extends baseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = LogManager.getLogger(OrganizationAction.class);
	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	private IJobTitleService iJobTitleService;
	
	/**
	 * @return the iJobTitleService
	 */
	public IJobTitleService getiJobTitleService() {
		return iJobTitleService;
	}

	/**
	 * @param iJobTitleService
	 *            the iJobTitleService to set
	 */
	public void setiJobTitleService(IJobTitleService iJobTitleService) {
		this.iJobTitleService = iJobTitleService;
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

		String hql = " from JobTitle j where 1=1 ";

		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split(" ");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			hql += " order by j." + sortname + " " + sorttype;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		
		List<Object> licount = this.iJobTitleService.findByHSQL("select count(*) " + hql);
		if (licount != null && licount.size() > 0) {
			int records = Integer.parseInt(licount.get(0).toString());
			sb.append("\"TotalRecordCount\":" + records);
			sb.append(",");

		} else {
			sb.append("\"TotalRecordCount\":0");
			sb.append(",");
		}
		
		List<JobTitle> li = this.iJobTitleService.getListForPage(hql, start, limit);
		sb.append("\"Records\":[");

		for (int i = 0; i < li.size(); i++) {
			JobTitle g = li.get(i);
			int id = g.getId();
			String name = g.getName();
			String level = g.getLevel();
			String sr = "{\"id\":" + id + ",\"name\":\"" + name  + "\",\"level\":\"" + level+ "\"";
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
		String level = request.getParameter("level")==null?"":request.getParameter("level");
		
		if (Utlity.checkStringNull(name)) {
			sendResponse("ERROR", "名称为空");
			return;
		}
		
		List<JobTitle> litcs = this.iJobTitleService.findByName(name);
		
		if (litcs.size() > 0) {
			sendResponse("ERROR", "已经存在职称:" + name);
			return;
		}
		
		JobTitle jobTitle = new JobTitle();
		jobTitle.setName(name);
		jobTitle.setLevel(level);
		this.iJobTitleService.add(jobTitle);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Record\":");

		String sr = "{\"id\":" + jobTitle.getId() + ",\"name\":\"" + name + "\",\"level\":\"" + jobTitle.getLevel() +"\"";
		sr += "}";

		sb.append(sr);

		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public void Edit(){
		initServlert();
		
		String name = request.getParameter("name");
		String level = request.getParameter("level")==null?"":request.getParameter("level");
		
		if (Utlity.checkStringNull(name)) {
			sendResponse("ERROR", "名称为空");
			return;
		}
		
		List<JobTitle> litcs = this.iJobTitleService.findByName(name);
		
		String id = request.getParameter("id"); // id 用于编辑
		JobTitle jobTitle = this.iJobTitleService.getJobTitleById(id);
		
		if (!jobTitle.getName().equals(name)) {
			if (litcs.size() > 0) {
				sendResponse("ERROR", "已经存在职称:" + name);
				return;
			}
		}
		
		jobTitle.setName(name);
		jobTitle.setLevel(level);
		this.iJobTitleService.update(jobTitle);
		sendResponse("OK", "更新成功");
	}
	
	public void Delete(){
		initServlert();
		
		String id = request.getParameter("id");
		if (id != null) {
			JobTitle jobTitle = this.iJobTitleService.getJobTitleById(id);
			if(jobTitle.getTeachers()!=null && jobTitle.getTeachers().size()>0){
				sendResponse("ERROR", "有教师选择此职称，不能删除此职称");
			}else{
				this.iJobTitleService.delete(jobTitle);
				sendResponse("OK", "删除成功");
			}
		}else{
			sendResponse("ERROR", "删除失败!不存在删除的职称");
		}
	}
}
