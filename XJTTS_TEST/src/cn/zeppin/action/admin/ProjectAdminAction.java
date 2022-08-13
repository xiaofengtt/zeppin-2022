package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectAdminRight;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IEthnicService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.DictionyMap.ROLEENUM;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Administrator
 * @category 项目管理员业务逻辑
 */
public class ProjectAdminAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(ProjectAdminAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IAreaService iAreaService;
	private IEthnicService iEthnicService;
	private IOrganizationService iOragnizationService;
	private IProjectAdminService iProjectAdminService;

	public ProjectAdminAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	// 业务逻辑方法

	// 导航跳转初始化
	public String initPage() {
		return "init";
	}

	public String authority() {
		return "authority";
	}
	
	/**
	 * @category 获取项目管理员列表
	 * @param jtStartIndex
	 * @param jtPageSize
	 * @param jtSorting
	 */
	@SuppressWarnings("rawtypes")
	public void getProjectAdminList() {

		initServlert();

		try {
			// 起始页
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

			// 搜索参数
			String q = request.getParameter("q");
			q = q == null ? "" : q;
			String stype = request.getParameter("stype");
			stype = stype == null ? "" : stype;

			// 排序 参数
			String sortname = "";
			String sorttype = "";
			String sort = request.getParameter("jtSorting");
			if (sort != null && !sort.equals("")) {
				String[] sortarray = sort.split(" ");
				sortname = sortarray[0];
				sorttype = sortarray[1];
			}

			UserSession user = (UserSession) session.getAttribute("usersession");
			ROLEENUM ROLE = ROLEENUM.valueof(user.getRole());
			int records = 0;
			List li = new ArrayList<>();
			if (ROLE == ROLEENUM.SUPERADMIN) {
				// 超级管理员
				records = this.iProjectAdminService.getProjectAdminCount(q, stype, sortname, sorttype, false, 0, 0, null);
				li = this.iProjectAdminService.getProjectAdmin(q, stype, sortname, sorttype, false, 0, 0, null, start, limit);

			} else if (ROLE == ROLEENUM.ADMIN) {
				boolean createUser = user.getCreateuser() == 1 ? true : false;

				StringBuilder sbOrganizations = new StringBuilder();

				if (user.getOrganizationLevel() == DictionyMap.ORGANIZATION_LEVEL_PROVINCE) {
					sbOrganizations.append("all");
				} else {
					sbOrganizations.append(user.getOrganizationScode());
				}

				records = this.iProjectAdminService.getProjectAdminCount(q, stype, sortname, sorttype, createUser, user.getOrganization(), user.getId(), sbOrganizations.toString());

				li = this.iProjectAdminService.getProjectAdmin(q, stype, sortname, sorttype, createUser, user.getOrganization(), user.getId(), sbOrganizations.toString(), start, limit);
			}

			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");

			boolean flag = false;
			sb.append("\"Records\":[");
			for (int i = 0; i < li.size(); i++) {
				Object[] ot = (Object[]) li.get(i);
				ProjectAdmin pa = (ProjectAdmin) ot[0];

				int id = pa.getId();
				if (user.getId().intValue() == id) {
					continue;
				}
				flag = true;
				String name = pa.getName();
				String idcard = pa.getIdcard(); // 生分证
				String mobile = pa.getMobile();
				String email = pa.getEmail();
				short sex = pa.getSex() == null ? 0 : pa.getSex();

				// short ethnicid = 0;
				String ethnicName = "无";
				if (pa.getEthnic() != null) {
					// ethnicid = pa.getEthnic().getId();
					ethnicName = pa.getEthnic().getName();
				}
				// int organizationid = pa.getOrganization().getId(); // 组织架构
				// 工作单位
				String organizationName = pa.getOrganization().getName();
				String department = pa.getDepartment(); // 所在部门

				short level = pa.getLevel(); // 级别 1.派出单位，2，承训单位

				short politicsId = 0; // 政治面貌
				if (pa.getPolitics() != null) {
					politicsId = pa.getPolitics().getId();
				}

				boolean createuser = pa.getCreateuser(); // 能否开设账号
				boolean restrictRight = pa.getRestrictRight(); // 是否开设权限

				String phone = pa.getPhone();
				String fax = pa.getFax();
				String jobDuty = pa.getJobDuty(); // 职务

				int areaid = pa.getArea() == null ? null : pa.getArea().getId(); // 地区

				String address = pa.getAddress();
				String postCode = pa.getPostcode();

				short status = pa.getStatus();
				String remark = pa.getRemark();
				
				if (remark != null) {
					remark = remark.replace("\t", "  ");
					remark = remark.replace("\n", " ");
					remark = remark.replace("\r", " ");
				}
				StringBuilder sbstr = new StringBuilder();
				sbstr.append("{");
				sbstr.append("\"id\":" + id);
				sbstr.append(",");
				sbstr.append("\"name\":\"" + name + "\"");
				sbstr.append(",");
				sbstr.append("\"idcard\":\"" + idcard + "\"");
				sbstr.append(",");
				sbstr.append("\"mobile\":\"" + mobile + "\"");
				sbstr.append(",");
				sbstr.append("\"email\":\"" + email + "\"");
				sbstr.append(",");
				sbstr.append("\"sex\":" + sex);
				sbstr.append(",");
				sbstr.append("\"ethnic\":\"" + ethnicName + "\"");
				sbstr.append(",");
				sbstr.append("\"organization\":\"" + organizationName + "\"");
				sbstr.append(",");
				sbstr.append("\"department\":\"" + department + "\"");
				sbstr.append(",");
				sbstr.append("\"level\":" + level);
				sbstr.append(",");
				sbstr.append("\"politics\":" + politicsId);
				sbstr.append(",");
				sbstr.append("\"createuser\":" + createuser);
				sbstr.append(",");
				sbstr.append("\"restrictRight\":" + restrictRight);
				sbstr.append(",");
				sbstr.append("\"phone\":\"" + phone + "\"");
				sbstr.append(",");
				sbstr.append("\"fax\":\"" + fax + "\"");
				sbstr.append(",");
				sbstr.append("\"jobDuty\":\"" + jobDuty + "\"");
				sbstr.append(",");
				sbstr.append("\"area\":" + areaid);
				sbstr.append(",");
				sbstr.append("\"address\":\"" + address + "\"");
				sbstr.append(",");
				sbstr.append("\"postcode\":\"" + postCode + "\"");
				sbstr.append(",");
				sbstr.append("\"status\":" + status);
				sbstr.append(",");
				sbstr.append("\"remark\":\"" + remark + "\"");
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

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}

	/**
	 * @category 获取项目管理员权限列表
	 * @param jtStartIndex
	 * @param jtPageSize
	 * @param jtSorting
	 */
	@SuppressWarnings("rawtypes")
	public void getProjectAdminAuthorityList() {

		initServlert();

		try {
			// 起始页
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

			// 搜索参数
			String q = request.getParameter("q");
			q = q == null ? "" : q;
			String stype = request.getParameter("stype");
			stype = stype == null ? "" : stype;
			
			// 排序 参数
			String sortname = "";
			String sorttype = "";
			String sort = request.getParameter("jtSorting");
			if (sort != null && !sort.equals("")) {
				String[] sortarray = sort.split(" ");
				sortname = sortarray[0];
				sorttype = sortarray[1];
			}

			UserSession user = (UserSession) session.getAttribute("usersession");
			ROLEENUM ROLE = ROLEENUM.valueof(user.getRole());
			int records = 0;
			List li = new ArrayList<>();
			boolean createUser = user.getCreateuser() == 1 ? true : false;

			StringBuilder sbOrganizations = new StringBuilder();

			if (user.getOrganizationLevel() == DictionyMap.ORGANIZATION_LEVEL_PROVINCE) {
				sbOrganizations.append("all");
			} else {
				sbOrganizations.append(user.getOrganizationScode());
			}

			records = this.iProjectAdminService.getProjectAdminCount(q, stype, sortname, sorttype, createUser, user.getOrganization(), user.getId(), sbOrganizations.toString());

			li = this.iProjectAdminService.getProjectAdmin(q, stype, sortname, sorttype, createUser, user.getOrganization(), user.getId(), sbOrganizations.toString(), start, limit);

			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");

			boolean flag = false;
			sb.append("\"Records\":[");
			for (int i = 0; i < li.size(); i++) {
				Object[] ot = (Object[]) li.get(i);
				ProjectAdmin pa = (ProjectAdmin) ot[0];
				
				int id = pa.getId();
				flag = true;
				String name = pa.getName();
				String mobile = pa.getMobile();

				// 工作单位
				String organizationName = pa.getOrganization().getName();
				String department = pa.getDepartment(); // 所在部门

				String phone = pa.getPhone();
				String jobDuty = pa.getJobDuty(); // 职务
				String authority = "";
				if(pa.getRestrictRight()){
					for (ProjectAdminRight par : pa.getProjectAdminRights()) {
						authority += getNaviString(par.getProjectType());
						authority += "</br>";
					}
				}
				if(authority.equals("")){
					authority = "全部项目类型权限";
				}
				StringBuilder sbstr = new StringBuilder();
				sbstr.append("{");
				sbstr.append("\"id\":" + id);
				sbstr.append(",");
				sbstr.append("\"name\":\"" + name + "\"");
				sbstr.append(",");
				sbstr.append("\"mobile\":\"" + mobile + "\"");
				sbstr.append(",");
				sbstr.append("\"organization\":\"" + organizationName + "\"");
				sbstr.append(",");
				sbstr.append("\"department\":\"" + department + "\"");
				sbstr.append(",");
				sbstr.append("\"phone\":\"" + phone + "\"");
				sbstr.append(",");
				sbstr.append("\"jobDuty\":\"" + jobDuty + "\"");
				sbstr.append(",");
				sbstr.append("\"authority\":\"" + authority + "\"");
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

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}
	
	public String getNaviString(ProjectType pt) {
		if (pt.getProjectType() == null) {
			return pt.getName();
		} else {
			return getNaviString(pt.getProjectType()) + ">" + pt.getName();
		}
	}
	/**
	 * @category 项目管理员操作
	 * @param method
	 *            add,edit,delete 只有删除功能 添加，编辑 是另外流程编辑
	 */
	public void opProjectAdmin() {

		try {
			initServlert();

			String method = request.getParameter("method");
			if (method.equals("delete")) {
				// 删除
				String id = request.getParameter("id");
				if (id != null && !id.equals("")) {
					ProjectAdmin pa = this.iProjectAdminService.get(Integer.parseInt(id));
					if (pa != null) {

						try {

							pa.setStatus((short) 2);
							this.iProjectAdminService.update(pa);
							sendResponse("OK", "停用成功");

						} catch (Exception ex) {
							sendResponse("ERROR", "删除失败,存在项目类型权限");
						}

					} else {
						sendResponse("ERROR", "删除失败，不存在项目管理员");
					}
				} else {
					sendResponse("ERROR", "删除失败，不存在项目管理员");
				}

			} else {
				sendResponse("ERROR", "操作失败，不存在操作类型");
			}
		} catch (Exception ex) {
			logger.error(ex);
			sendResponse("ERROR", "操作失败");
		}

	}
	public String opPassword() {
		initServlert();
		String id = request.getParameter("id");
		if (id != null && !id.equals("")) {
			ProjectAdmin pa = this.iProjectAdminService.get(Integer.parseInt(id));
			String password=pa.getMobile();
			password=password.substring(password.length()-6);
			pa.setPassword(password);
			this.iProjectAdminService.update(pa);
		}
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

	public boolean checkOrganizationUnion(Organization ora, int orgid) {
		if (ora.getOrganization() == null) {
			if (ora.getId() == orgid) {
				return true;
			} else {
				return false;
			}
		} else {
			if (ora.getId() == orgid) {
				return true;
			} else {
				return checkOrganizationUnion(ora.getOrganization(), orgid);
			}

		}
	}

	// 属性

	public IAreaService getiAreaService() {
		return iAreaService;
	}

	public void setiAreaService(IAreaService iAreaService) {
		this.iAreaService = iAreaService;
	}

	public IEthnicService getiEthnicService() {
		return iEthnicService;
	}

	public void setiEthnicService(IEthnicService iEthnicService) {
		this.iEthnicService = iEthnicService;
	}

	public IOrganizationService getiOragnizationService() {
		return iOragnizationService;
	}

	public void setiOragnizationService(IOrganizationService iOragnizationService) {
		this.iOragnizationService = iOragnizationService;
	}

	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	public void setiProjectAdminService(IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}

}
