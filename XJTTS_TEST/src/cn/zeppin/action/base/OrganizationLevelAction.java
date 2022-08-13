package cn.zeppin.action.base;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.entity.OrganizationLevel;
import cn.zeppin.service.IOrganizationLevelService;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/*
 * 部门级别
 */
public class OrganizationLevelAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(OrganizationLevelAction.class);

	private IOrganizationLevelService orgainzationLevelService;

	public IOrganizationLevelService getOrgainzationLevelService() {
		return orgainzationLevelService;
	}

	public void setOrgainzationLevelService(
			IOrganizationLevelService orgainzationLevelService) {
		this.orgainzationLevelService = orgainzationLevelService;
	}

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	public OrganizationLevelAction() throws NullPointerException {

	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public void getOrganizationLevel() {

		initServlert();

		StringBuilder sb = new StringBuilder();

		try {
			List<OrganizationLevel> li = orgainzationLevelService.findAll();

			sb.append("{");
			sb.append("\"status\":\"ok\"");
			sb.append(",");
			sb.append("\"count\":" + li.size());
			sb.append(",");
			sb.append("\"rows\":[");

			for (int i = 0; i < li.size(); i++) {
				OrganizationLevel orgaLevel = li.get(i);
				short level = orgaLevel.getLevel();
				String name = orgaLevel.getName();
				sb.append("{\"level\":" + level + ",\"name\":\"" + name	+ "\"},");
			}

			sb.delete(sb.length() - 1, sb.length());
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

}
