package cn.zeppin.action.base;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.entity.ProjectLevel;
import cn.zeppin.service.IProjectLevelService;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

public class ProjectLevelAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(ProjectLevelAction.class);

	private IProjectLevelService projectLevelService;

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	public ProjectLevelAction() throws NullPointerException {

	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public void getProjectLevel() {

		StringBuilder sb = new StringBuilder();
		try {
			initServlert();

			List<ProjectLevel> li = projectLevelService.findAll();

			sb.append("{");
			sb.append("\"status\":\"ok\"");
			sb.append(",");
			sb.append("\"count\":" + li.size());
			sb.append(",");
			sb.append("\"rows\":[");

			for (int i = 0; i < li.size(); i++) {
				ProjectLevel area = li.get(i);
				short code = area.getLevel();
				String name = area.getName();
				sb.append("{\"level\":" + code + ",\"name\":\"" + name + "\"},");
			}
			if (li.size() > 0) {
				sb.delete(sb.length() - 1, sb.length());
			}
			sb.append("]}");

			Utlity.ResponseWrite(sb.toString(), "application/json", response);

		} catch (Exception ex) {
			logger.error(ex);
			sb.append("{");
			sb.append("\"status\":\"error\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}

	}

	public IProjectLevelService getProjectLevelService() {
		return projectLevelService;
	}

	public void setProjectLevelService(IProjectLevelService projectLevelService) {
		this.projectLevelService = projectLevelService;
	}

}
