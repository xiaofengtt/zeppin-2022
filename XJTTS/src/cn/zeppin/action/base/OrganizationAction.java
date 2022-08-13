/**
 * 
 */
package cn.zeppin.action.base;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Organization;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author sj
 * 
 */
public class OrganizationAction extends ActionSupport {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(AreaAction.class);
	List<Organization> lstOrganizations = new ArrayList<>();

	private IOrganizationService iOrganizationService;

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public void getSchool() {
		initServlert();
		String result = "";
		int pid;
		String areaId;
		List<Organization> lstOrganizations = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		String hqlString = "";
		if (request.getParameterMap().containsKey("pid")) {
			pid = Integer.parseInt(request.getParameter("pid"));
		}
		if (request.getParameterMap().containsKey("areaId")) {
			areaId = request.getParameter("areaId");
			hqlString = "from Organization where area=" + areaId + " and isschool=1 and organizationLevel=1 ";

		}

		if (request.getParameterMap().containsKey("countyId")) {
			areaId = request.getParameter("countyId");
			hqlString = "from Organization where area=" + areaId + " and isschool=1 and organizationLevel=2 ";
		}

		if (request.getParameterMap().containsKey("schoolId")) {
			areaId = request.getParameter("schoolId");
			hqlString = "from Organization  where organization=" + Integer.parseInt(areaId) + " and isschool=1 and organizationLevel=4 ";
			// hqlString = "from Organization";
		}
		lstOrganizations = iOrganizationService.getListByHSQL(hqlString);

		if (lstOrganizations.size() > 0) {
			sb.append("{");
			sb.append("\"Result\":\"OK\",");
			sb.append("\"Options\":");
			String ret = "[";
			String st = "";
			st += "{\"Value\":" + 0 + ",\"DisplayText\":\"" + "请选择..." + "\"},";
			for (Organization org : lstOrganizations) {
				st += "{\"Value\":" + org.getId() + ",\"DisplayText\":\"" + org.getName() + "\"},";
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

	@SuppressWarnings("unchecked")
	public void searchSchool() {
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		if(user == null){
			user = new UserSession();
			user.setOrganization(26124);
			user.setOrganizationLevel(1);
			user.setOrganizationScode("1000026124");
			user.setRole((short)3);
		}
		Organization userOrganization = iOrganizationService.get(user.getOrganization());
		String string;
		if (request.getParameterMap().containsKey("search")) {
			string = request.getParameter("search");
			StringBuffer sb = new StringBuffer();

			String orgIds = "";
			String hqlString = "";

			if (user.getOrganizationLevel() != DictionyMap.ORGANIZATION_LEVEL_SCHOOL) {
				if(user.getRole()==5){
					hqlString = "from Organization where isschool=1 and (name like '%" + string + "%' or pinying like '%" + string + "%' )";
				}else{
					hqlString = "from Organization where isschool=1 and (name like '%" + string + "%' or pinying like '%" + string + "%' )  and scode like '" + user.getOrganizationScode() + "%' ";
				}
				try {
					lstOrganizations = iOrganizationService.getListForPage(hqlString, 0, 10, null);
				} catch (Exception e) {
					lstOrganizations.clear();
					// TODO: handle exception
				}
			} else {
				lstOrganizations.clear();
				lstOrganizations.add(userOrganization);
			}
			sb.append("{");
			sb.append("\"Result\":\"OK\",");
			sb.append("\"Options\":");
			String ret = "[";
			String st = "";
			if (lstOrganizations.size() > 0) {

				// st += "{\"Value\":" + 0 + ",\"DisplayText\":\"" + "请选择..." +
				// "\"},";
				for (Organization org : lstOrganizations) {
					st += "{\"id\":" + org.getId() + ",\"DisplayText\":\"" + org.getName() + "\"},";
				}

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

	/**
	 * @return the iOrganizationService
	 */
	public IOrganizationService getiOrganizationService() {
		return iOrganizationService;
	}

	/**
	 * @param iOrganizationService
	 *            the iOrganizationService to set
	 */
	public void setiOrganizationService(IOrganizationService iOrganizationService) {
		this.iOrganizationService = iOrganizationService;
	}

}
