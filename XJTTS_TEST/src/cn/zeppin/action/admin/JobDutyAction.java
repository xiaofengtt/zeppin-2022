package cn.zeppin.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.baseAction;
import cn.zeppin.entity.JobDuty;
import cn.zeppin.service.IJobDutyService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

public class JobDutyAction extends baseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = LogManager.getLogger(OrganizationAction.class);
	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	private IJobDutyService iJobDutyService;
	
	/**
	 * @return the iJobDutyService
	 */
	public IJobDutyService getiJobDutyService() {
		return iJobDutyService;
	}

	/**
	 * @param iJobDutyService
	 *            the iJobDutyService to set
	 */
	public void setiJobDutyService(IJobDutyService iJobDutyService) {
		this.iJobDutyService = iJobDutyService;
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

		String hql = " from JobDuty j where 1=1 ";

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
		
		List<Object> licount = this.iJobDutyService.findByHSQL("select count(*) " + hql);
		if (licount != null && licount.size() > 0) {
			int records = Integer.parseInt(licount.get(0).toString());
			sb.append("\"TotalRecordCount\":" + records);
			sb.append(",");

		} else {
			sb.append("\"TotalRecordCount\":0");
			sb.append(",");
		}
		
		List<JobDuty> li = this.iJobDutyService.getListForPage(hql, start, limit);
		sb.append("\"Records\":[");

		for (int i = 0; i < li.size(); i++) {
			JobDuty g = li.get(i);
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
		
		List<JobDuty> litcs = this.iJobDutyService.findByName(name);
		
		if (litcs.size() > 0) {
			sendResponse("ERROR", "已经存在职务:" + name);
			return;
		}
		
		JobDuty jobDuty = new JobDuty();
		jobDuty.setName(name);
		this.iJobDutyService.add(jobDuty);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Record\":");

		String sr = "{\"id\":" + jobDuty.getId() + ",\"name\":\"" + name +"\"";
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
		
		List<JobDuty> litcs = this.iJobDutyService.findByName(name);
		
		String id = request.getParameter("id"); // id 用于编辑
		JobDuty jobDuty = this.iJobDutyService.getJobDutyById(id);
		
		if (!jobDuty.getName().equals(name)) {
			if (litcs.size() > 0) {
				sendResponse("ERROR", "已经存在职务:" + name);
				return;
			}
		}
		
		jobDuty.setName(name);
		this.iJobDutyService.update(jobDuty);
		sendResponse("OK", "更新成功");
	}
	
	public void Delete(){
		initServlert();
		
		String id = request.getParameter("id");
		if (id != null) {
			JobDuty jobDuty = this.iJobDutyService.getJobDutyById(id);
			if(jobDuty.getTeachers()!=null && jobDuty.getTeachers().size()>0){
				sendResponse("ERROR", "有教师选择此职务，不能删除此职务");
			}else{
				this.iJobDutyService.delete(jobDuty);
				sendResponse("OK", "删除成功");
			}
		}else{
			sendResponse("ERROR", "删除失败!不存在删除的职务");
		}
	}
}
