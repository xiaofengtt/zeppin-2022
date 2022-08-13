package cn.zeppin.action.admin;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

	public String higherAdmin() {
		return "higherAdmin";
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
			
			//20180313新增搜索参数status
			String statusn = request.getParameter("status") == null ? "0" : request.getParameter("status");
			if("".equals(statusn)){
				statusn = "0";
			}

			UserSession user = (UserSession) session.getAttribute("usersession");
			ROLEENUM ROLE = ROLEENUM.valueof(user.getRole());
			int records = 0;
			List li = new ArrayList<>();
			if (ROLE == ROLEENUM.SUPERADMIN) {
				// 超级管理员
				records = this.iProjectAdminService.getProjectAdminCount(q, stype, sortname, sorttype, false, 0, 0, null, Short.parseShort(statusn));
				li = this.iProjectAdminService.getProjectAdmin(q, stype, sortname, sorttype, false, 0, 0, null, start, limit, Short.parseShort(statusn));

			} else if (ROLE == ROLEENUM.ADMIN) {
				boolean createUser = user.getCreateuser() == 1 ? true : false;

				StringBuilder sbOrganizations = new StringBuilder();

				if (user.getOrganizationLevel() == DictionyMap.ORGANIZATION_LEVEL_PROVINCE) {
					sbOrganizations.append("all");
				} else {
					sbOrganizations.append(user.getOrganizationScode());
				}

				records = this.iProjectAdminService.getProjectAdminCount(q, stype, sortname, sorttype, createUser, user.getOrganization(), user.getId(), sbOrganizations.toString(),Short.parseShort(statusn));

				li = this.iProjectAdminService.getProjectAdmin(q, stype, sortname, sorttype, createUser, user.getOrganization(), user.getId(), sbOrganizations.toString(), start, limit,Short.parseShort(statusn));
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
				String idcard = pa.getIdcard();
				String mobile = pa.getMobile();
				String email = pa.getEmail();
				short sex = pa.getSex() == null ? 0 : pa.getSex();

				// short ethnicid = 0;
				String ethnicName = "无";
				if (pa.getEthnic() != null) {
					ethnicName = pa.getEthnic().getName();
				}
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
	 * @category 获取上级项目管理员列表
	 * @param jtStartIndex
	 * @param jtPageSize
	 * @param jtSorting
	 */
	@SuppressWarnings("rawtypes")
	public void getHigherAdminList() {

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
			Organization organization = this.iOragnizationService.get(user.getOrganization());
			int higherOrganization = organization.getOrganization().getId();
			
			int records = 0;
			List li = new ArrayList<>();
			
			records = this.iProjectAdminService.getHigherAdminCount(q, stype, sortname, sorttype, higherOrganization);
			li = this.iProjectAdminService.getHigherAdmin(q, stype, sortname, sorttype, higherOrganization , start, limit);

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
				String idcard = pa.getIdcard();
				String mobile = pa.getMobile();
				String email = pa.getEmail();
				short sex = pa.getSex() == null ? 0 : pa.getSex();

				String ethnicName = "无";
				if (pa.getEthnic() != null) {
					ethnicName = pa.getEthnic().getName();
				}
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
			int records = 0;
			List li = new ArrayList<>();
			boolean createUser = user.getCreateuser() == 1 ? true : false;

			StringBuilder sbOrganizations = new StringBuilder();

			if (user.getOrganizationLevel() == DictionyMap.ORGANIZATION_LEVEL_PROVINCE) {
				sbOrganizations.append("all");
			} else {
				sbOrganizations.append(user.getOrganizationScode());
			}

			records = this.iProjectAdminService.getProjectAdminCount(q, stype, sortname, sorttype, createUser, user.getOrganization(), user.getId(), sbOrganizations.toString(),(short)0);

			li = this.iProjectAdminService.getProjectAdmin(q, stype, sortname, sorttype, createUser, user.getOrganization(), user.getId(), sbOrganizations.toString(), start, limit,(short)0);

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

	
	/**
	 * @category 导出项目管理员列表
	 * @param jtStartIndex
	 * @param jtPageSize
	 * @param jtSorting
	 */
	@SuppressWarnings("rawtypes")
	public void outputAdminList() {
		initServlert();

		try {
			UserSession user = (UserSession) session.getAttribute("usersession");
			ROLEENUM ROLE = ROLEENUM.valueof(user.getRole());
			List li = new ArrayList<>();
			if (ROLE == ROLEENUM.SUPERADMIN) {
				// 超级管理员
				li = this.iProjectAdminService.getProjectAdmin(null, null, null, null, false, 0, 0, null, 0, -1,(short)0);
			} else if (ROLE == ROLEENUM.ADMIN) {
				boolean createUser = user.getCreateuser() == 1 ? true : false;
				StringBuilder sbOrganizations = new StringBuilder();
				if (user.getOrganizationLevel() == DictionyMap.ORGANIZATION_LEVEL_PROVINCE) {
					sbOrganizations.append("all");
				} else {
					sbOrganizations.append(user.getOrganizationScode());
				}
				li = this.iProjectAdminService.getProjectAdmin(null, null, null, null, createUser, user.getOrganization(), user.getId(), sbOrganizations.toString(), 0, -1,(short)0);
			}
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "管理员列表");
			HSSFRow row = s.createRow(0);
			String title[] = {"姓名", "性别", "民族", "政治面貌", "工作单位", "工作部门", "地址", "职务", "办公电话", "手机", "邮箱", "权限"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			int t = 0;
			for (int i = 0; i < li.size(); i++) {
				Object[] ot = (Object[]) li.get(i);
				ProjectAdmin pa = (ProjectAdmin) ot[0];
				
				t++;
				row = s.createRow(t);
				
				String name = pa.getName();
				String mobile = pa.getMobile();
				String email = pa.getEmail();
				short sexInt = pa.getSex() == null ? 0 : pa.getSex();
				String sex = sexInt==1? "男": "女";
				String ethnicName = "无";
				if (pa.getEthnic() != null) {
					ethnicName = pa.getEthnic().getName();
				}
				// 工作单位
				String organizationName = pa.getOrganization().getName();
				String department = pa.getDepartment(); // 所在部门

				String politics = "群众"; // 政治面貌
				if (pa.getPolitics() != null) {
					politics = pa.getPolitics().getName();
				}
				String phone = pa.getPhone();
				String jobDuty = pa.getJobDuty(); // 职务
				String address = pa.getAddress();
				
				String authority = "";
				if(pa.getRestrictRight()){
					for (ProjectAdminRight par : pa.getProjectAdminRights()) {
						authority += getNaviString(par.getProjectType());
						authority += ",";
					}
					if(authority.length() > 1){
						authority = authority.substring(0, authority.length()-1);
					}
				}
				if(authority.equals("")){
					authority = "全部项目类型权限";
				}
				
				String tInfo[] = { name, sex, ethnicName, politics, organizationName, department, address, jobDuty, phone, mobile, email, authority};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=projectAdmin.xls");
			OutputStream ouputStream = response.getOutputStream();
			wb.write(ouputStream);

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
	 * 获取分状态标签数
	 */
	public void getProjectAdminStatusList() {
		initServlert();
		try {
			// 搜索参数
			String q = request.getParameter("q");
			q = q == null ? "" : q;
			String stype = request.getParameter("stype");
			stype = stype == null ? "" : stype;


			UserSession user = (UserSession) session.getAttribute("usersession");
			ROLEENUM ROLE = ROLEENUM.valueof(user.getRole());
			
			List list = new ArrayList<>();
			if (ROLE == ROLEENUM.SUPERADMIN) {
				list = this.iProjectAdminService.getProjectAdminStatusList(q, stype, false, 0, 0, null);
			} else if (ROLE == ROLEENUM.ADMIN) {
				boolean createUser = user.getCreateuser() == 1 ? true : false;

				StringBuilder sbOrganizations = new StringBuilder();

				if (user.getOrganizationLevel() == DictionyMap.ORGANIZATION_LEVEL_PROVINCE) {
					sbOrganizations.append("all");
				} else {
					sbOrganizations.append(user.getOrganizationScode());
				}
				list = this.iProjectAdminService.getProjectAdminStatusList(q, stype, createUser, user.getOrganization(), user.getId(), sbOrganizations.toString());
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			Integer countNormal = 0;
			Integer countDisable = 0;
			if(!list.isEmpty()){
				for(int i = 0; i < list.size(); i++){
					Object[] obj = (Object[])list.get(i);
					Short status = Short.parseShort(obj[0].toString());
					Integer count = Integer.parseInt(obj[1].toString());
					if(1 == status){
						countNormal = count;
					} else if (2 == status) {
						countDisable = count;
					}
				}
			}
			Integer totalcount = countDisable+countNormal;
			sb.append("\"CountNormal\":"+countNormal);
			sb.append(",");
			sb.append("\"CountDisable\":"+countDisable);
			sb.append(",");
			sb.append("\"TotalCount\":"+totalcount);
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append("\"Message\":\"操作异常！\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
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
