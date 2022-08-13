package cn.zeppin.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.Submit;
import cn.zeppin.service.IProjectApplyExpertService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

public class ProjectReviewAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(ProjectExpertManaOptAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IProjectApplyService iProjectApplyService;
	private IProjectApplyExpertService iProjectApplyExpertService;

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public String initPage() {
		return "init";
	}

	public void getProjectList() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		String ist = (String) request.getParameter("jtStartIndex");
//		System.out.println("jtStartIndex:"+ist);
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);
		int pageSize = Integer.parseInt(request.getParameter("jtPageSize").toString());
//		System.out.println("jtPageSize:"+pageSize);
		
		int expertid = us.getId();

		int records = this.iProjectApplyExpertService.getCountForExpert(expertid);
		List li = this.iProjectApplyService.getListForExpert(expertid, start, pageSize);
//		System.out.println(li.size());
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		boolean flag = false;
		sb.append("\"Records\":[");
		for (int i = 0; i < li.size(); i++) {
			flag = true;
			Object[] ob = (Object[]) li.get(i);
			ProjectApply pa = (ProjectApply) ob[0];
			String paid = pa.getId().toString();
			String year = pa.getProject().getYear();
			String project = pa.getProject().getName();
			String subject = pa.getTrainingSubject().getName();
			String training = pa.getTrainingCollege().getName();
			String startTime = pa.getTrainingStarttime() == null ? "" : pa.getTrainingStarttime().toString().substring(0, 10);
			String endTime = pa.getTrainingEndtime() == null ? "" : pa.getTrainingEndtime().toString().substring(0, 10);
			String classHour = pa.getTrainingClasshour().toString();
			String report = "";
			String path = "";
			if (pa.getDocumentByProjectApplyDocument() != null) {
				report = pa.getDocumentByProjectApplyDocument().getTitle();
				path = pa.getDocumentByProjectApplyDocument().getResourcePath();
			}
			int id = 0;
			int pid = 0;
			int sid = 0;
			int tc = 0;
			int type = 0;
			int valuator = 0;
			String score="";
			if (pa.getProject().getPsqByProjectJudgePsq() != null) {
				id = pa.getProject().getPsqByProjectJudgePsq().getId();
				pid = pa.getProject().getId();
				sid = pa.getTrainingSubject().getId();
				tc = pa.getTrainingCollege().getId();
				type = 1;
				valuator = us.getId();
				int psq=pa.getProject().getPsqByProjectJudgePsq().getId();
				List lis=this.iProjectApplyService.getSubmitForExpert(pid,sid,tc,psq,valuator);
				if(lis.size()>0){
					Submit submit=(Submit) lis.get(0);
					int sub=submit.getId();
					int sc=this.iProjectApplyService.getScoreBySubmit(sub);
					score=sc+"";
				}
			}
			StringBuilder sbstr = new StringBuilder();
			sbstr.append("{");
			sbstr.append("\"paid\":" + paid);
			sbstr.append(",");
			sbstr.append("\"year\":\"" + year + "\"");
			sbstr.append(",");
			sbstr.append("\"project\":\"" + project + "\"");
			sbstr.append(",");
			sbstr.append("\"subject\":\"" + subject + "\"");
			sbstr.append(",");
			sbstr.append("\"training\":\"" + training + "\"");
			sbstr.append(",");
			sbstr.append("\"startTime\":\"" + startTime + "\"");
			sbstr.append(",");
			sbstr.append("\"endTime\":\"" + endTime + "\"");
			sbstr.append(",");
			sbstr.append("\"classHour\":\"" + classHour + "\"");
			sbstr.append(",");
			sbstr.append("\"score\":\"" + score + "\"");
			sbstr.append(",");
			sbstr.append("\"report\":\"" + report + "\"");
			sbstr.append(",");
			sbstr.append("\"path\":\"" + path + "\"");
			sbstr.append(",");
			sbstr.append("\"id\":\"" + id + "\"");
			sbstr.append(",");
			sbstr.append("\"pid\":\"" + pid + "\"");
			sbstr.append(",");
			sbstr.append("\"sid\":\"" + sid + "\"");
			sbstr.append(",");
			sbstr.append("\"tc\":\"" + tc + "\"");
			sbstr.append(",");
			sbstr.append("\"type\":\"" + type + "\"");
			sbstr.append(",");
			sbstr.append("\"valuator\":\"" + valuator + "\"");
			sbstr.append("},");
			sb.append(sbstr.toString());
		}
		if (flag) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		sb.append(",");
		sb.append("\"TotalRecordCount\":" + records);
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);

	}

	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}

	public void setiProjectApplyService(IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
	}

	public IProjectApplyExpertService getiProjectApplyExpertService() {
		return iProjectApplyExpertService;
	}

	public void setiProjectApplyExpertService(IProjectApplyExpertService iProjectApplyExpertService) {
		this.iProjectApplyExpertService = iProjectApplyExpertService;
	}
}
