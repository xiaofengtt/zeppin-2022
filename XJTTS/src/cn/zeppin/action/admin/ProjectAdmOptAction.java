package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.Ethnic;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectAdminRight;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IEthnicService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectAdminRightService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.DictionyMap.ROLEENUM;

import com.opensymphony.xwork2.ActionSupport;

public class ProjectAdmOptAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(ProjectAdmOptAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IAreaService iAreaService;
	private IEthnicService iEthnicService;
	private IOrganizationService iOragnizationService;
	private IProjectAdminService iProjectAdminService;
	private IProjectTypeService iProjectTypeService;
	private IProjectAdminRightService iProjectAdminRightService;

	// 初始化 地區列表
	private List<Object[]> provinceIds;
	private List<Object[]> cityIds;
	private List<Object[]> countyIds;
	private List<Ethnic> listEthnic;

	// from 表单

	private String id;
	private String name;
	private String sex;
	private String idcard;
	private String mobile;
	private String[] area;
	private String ethnic;
	private String organization;
	private String organizationName = "请选择工作单位";
	private String department;
	private String jobDuty;
	private String email;
	private String address;
	private String phone;
	private String fax;
	private String status;
	private String remark;
	private String restrictRight; // 是否开启 项目类型权限
	private String createuser; // 是否可创建用户
	private String level;
	private String[] restrictRightId;
	private List<String[]> restrictRightList;
	private List<ProjectType> projectTypeList;
	private String pageStatus;
	private String message;
	
	//学校管理范围（只对县级管理校级管理员启用）
	private String projectAdminOrg;
	private String[] projectAdminOrgId;
	private List<String[]> projectAdminOrgList;
	
	private String projectAdminLevel;

	public ProjectAdmOptAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	// 业务逻辑方法

	// 导航跳转初始化
	public String initPage() {
		initServlert();
		String id = request.getParameter("id");
		this.id = id;
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.projectAdminLevel = "0";
		// 设置地区 与 民族
		// 地区第一级
		provinceIds = new ArrayList<Object[]>();
		List<Area> sli = iAreaService.getParentCodeArea("0");
		for (Area ae : sli) {
			if (ae.getCode().equals(DictionyMap.areaCode)) {
				Object[] obj = { ae.getId(), ae.getName() };
				provinceIds.add(obj);
			}
		}

		// 获取民族信息
		if (this.listEthnic == null) {
			this.listEthnic = this.iEthnicService.getEthnicByWight();
		}
		this.projectTypeList = this.iProjectTypeService
				.getProjectTypeList(null);
		if (id != null && !id.equals("0") && !id.equals("")) {
			// 编辑
			ProjectAdmin pa = this.iProjectAdminService.get(Integer
					.parseInt(id));
			if(us != null){
				if(us.getOrganizationLevel() == 3 && pa.getOrganization().getOrganizationLevel().getId() == 4){
					this.projectAdminLevel = "1";
				}
			}
			this.pageStatus = "";
			this.message = "";

			if (pa != null) {
				this.name = pa.getName();
				this.sex = pa.getSex() == null ? "1" : pa.getSex().toString();

				this.idcard = pa.getIdcard();
				this.mobile = pa.getMobile();
				this.ethnic = pa.getEthnic() == null ? "1" : pa.getEthnic()
						.getId().toString();
				this.organization = pa.getOrganization().getId().toString();
				this.organizationName = pa.getOrganization().getName();
				this.department = pa.getDepartment();
				this.jobDuty = pa.getJobDuty();
				this.email = pa.getEmail();
				this.address = pa.getAddress();
				this.phone = pa.getPhone();
				this.fax = pa.getFax();
				this.status = pa.getStatus().toString();
				this.remark = pa.getRemark();
				this.level = pa.getLevel().toString();
				this.createuser = pa.getCreateuser() ? "1" : "0";
				this.restrictRight = pa.getRestrictRight() ? "1" : "0";

				Area a = pa.getArea();
				String code1 = "0";
				String code2 = "0";
				int areaid = a.getId();
				int areaid1 = 0;
				int areaid2 = 0;
				String areacode = a.getParentcode();
				if (areacode != null && !areacode.equals("0")
						&& !areacode.equals("")) {
					// 当前的地区的父地区存在
					Area pae = this.iAreaService.getAreaByCode(areacode);
					if (pae != null) {
						areaid1 = pae.getId();
						code1 = pae.getCode();
						areacode = pae.getParentcode();
						// 父父地区
						if (areacode != null && !areacode.equals("0")
								&& !areacode.equals("")) {
							pae = this.iAreaService.getAreaByCode(areacode);
							if (pae != null) {
								areaid2 = pae.getId();
								code2 = pae.getCode();
							}
						}
					}
				}
				int a1 = 0, a2 = 0, a3 = 0;
				String c1 = "0", c2 = "0";
				if (areaid1 > 0) {
					if (areaid2 > 0) {
						a1 = areaid2;
						a2 = areaid1;
						a3 = areaid;
						c1 = code2;
						c2 = code1;
					} else {
						a1 = areaid1;
						a2 = areaid;
						a3 = 0;
						c1 = code1;
					}
				} else {
					a1 = areaid;
					a2 = 0;
					a3 = 0;
				}
				this.setArea(new String[] { a1 + "", a2 + "", a3 + "" });

				if (!c1.equals("0")) {
					List<Area> li = iAreaService.getParentCodeArea(c1);
					cityIds = new ArrayList<Object[]>();
					for (Area ae : li) {
						Object[] obj = { ae.getId(), ae.getName() };
						cityIds.add(obj);
					}
				} else {
					this.cityIds = new ArrayList<Object[]>();
					this.cityIds.add(new Object[] { 0, "请选择..." });

					List<Area> tli = iAreaService
							.getParentCodeArea(DictionyMap.areaCode);
					for (Area ae : tli) {
						Object[] obj = { ae.getId(), ae.getName() };
						cityIds.add(obj);
					}
				}
				if (!c2.equals("0")) {
					List<Area> li = iAreaService.getParentCodeArea(c2);
					this.countyIds = new ArrayList<Object[]>();
					for (Area ae : li) {
						Object[] obj = { ae.getId(), ae.getName() };
						countyIds.add(obj);
					}
				} else {
					this.countyIds = new ArrayList<Object[]>();
					this.countyIds.add(new Object[] { 0, "请选择..." });
				}

				// 加载权限
				Set<ProjectAdminRight> setProjectRights = pa
						.getProjectAdminRights();
				this.restrictRightId = new String[setProjectRights.size()];
				this.restrictRightList = new ArrayList<String[]>();
				int index = 0;
				for (ProjectAdminRight tpar : setProjectRights) {
					ProjectType tpt = tpar.getProjectType();
					this.restrictRightId[index] = tpt.getId().toString();
					String ts = getNaviString(tpt);
					if (ts.length() > 0) {
						String[] spts = ts.split("&");
						String[] rerights = new String[spts.length + 1];
						for (int i = 0; i < spts.length; i++) {
							rerights[i] = spts[i];
						}
						rerights[spts.length] = tpt.getId().toString();
						this.restrictRightList.add(rerights);
					}
					index++;
				}

			}

		} else {
			// 添加

			this.pageStatus = "";
			this.message = "";

			this.name = "";
			this.sex = "";
			this.idcard = "";
			this.mobile = "";
			this.ethnic = "";
			this.organization = "";
			this.organizationName = "请选择工作单位";
			this.department = "";
			this.jobDuty = "";
			this.email = "";
			this.address = "";
			this.phone = "";
			this.fax = "";
			this.status = "";
			this.remark = "";
			this.level = "";
			this.createuser = "0";
			this.restrictRight = "0";

			this.area = new String[3];
			this.restrictRightId = null;
			this.restrictRightList = null;
			this.projectAdminOrgId = null;
			this.projectAdminOrgList = null;

			this.cityIds = new ArrayList<Object[]>();
			this.cityIds.add(new Object[] { 0, "请选择..." });

			List<Area> tli = iAreaService
					.getParentCodeArea(DictionyMap.areaCode);
			for (Area ae : tli) {
				Object[] obj = { ae.getId(), ae.getName() };
				cityIds.add(obj);
			}
			this.countyIds = new ArrayList<Object[]>();
			this.countyIds.add(new Object[] { 0, "请选择..." });
		}

		return "init";
	}

	public void opProjectAdmin() {

		initServlert();
		try {

			Map<String, String[]> parMap = request.getParameterMap();
			String[] area = parMap.get("area");
			String id = parMap.get("id")[0];
			String name = parMap.get("name")[0];
			String sex = parMap.get("sex")[0];
			String idcard = parMap.get("idcard")[0];
			String mobile = parMap.get("mobile")[0];
			String ethnic = parMap.get("ethnic")[0];
			String organization = parMap.get("organization")[0];
			String department = parMap.get("department")[0];
			String jobDuty = parMap.get("jobDuty")[0];
			String email = parMap.get("email")[0];
			String address = parMap.get("address")[0];
			String phone = parMap.get("phone")[0];
			String fax = parMap.get("fax")[0];
			String status = parMap.get("status")[0];
			String remark = parMap.get("remark")[0];
			String restrictRight = parMap.containsKey("restrictRight") ? parMap
					.get("restrictRight")[0] : ""; // 是否开启项目类型权限
			String createuser = parMap.containsKey("createuser") ? parMap
					.get("createuser")[0] : ""; // 是否可创建用户
			String[] restrictRightId = parMap.get("restrictRightId");

			// 获取地区id
			String area0 = area[0];
			String area1 = area[1];
			String area2 = area[2];
			String tmpArea = area0;
			if (area2 != null && !area2.equals("0") && !area2.equals("")) {
				tmpArea = area2;
			} else {
				if (area1 != null && !area1.equals("0") && !area1.equals("")) {
					tmpArea = area1;
				}
			}
			if (name == null || name.equals("")) {
				sendResponse("ERROR", "用户名为空");
				return;
			}
			if (idcard == null || idcard.equals("")) {
				sendResponse("ERROR", "身份证为空");
				return;
			}
			if (mobile == null || mobile.equals("")) {
				sendResponse("ERROR", "手机为空");
				return;
			}
			if (email == null || email.equals("")) {
				sendResponse("ERROR", "邮箱为空");
				return;
			}
			if (organization == null || organization.equals("")) {
				sendResponse("ERROR", "工作单位为空");
				return;
			}
			if (!Utlity.checkIdCard(idcard)) {
				sendResponse("ERROR", "身份证不合法");
				return;
			}
			if (!Utlity.isMobileNO(mobile)) {
				sendResponse("ERROR", "手机不合法");
				return;
			}
			if (!Utlity.isEmail(email)) {
				sendResponse("ERROR", "邮箱不合法");
				return;
			}
			if (id == null || id.equals("0") || id.equals("")) {
				Object[] pars = { idcard, mobile, email };
				int flag = iProjectAdminService.checkUserInfo(pars);
				if (flag > 0) {
					sendResponse("ERROR", "添加失败，已经存在用户信息");
					return;
				}

				ProjectAdmin pa = new ProjectAdmin();
				pa.setName(name);
				pa.setIdcard(idcard);
				pa.setSex(Short.parseShort(sex));
				pa.setMobile(mobile);
				pa.setPassword(mobile.substring(mobile.length() - 6,
						mobile.length()));
				pa.setDepartment(department);
				pa.setJobDuty(jobDuty);
				pa.setEmail(email);
				pa.setAddress(address);
				pa.setPhone(phone);
				pa.setFax(fax);

				if (createuser != null && createuser.equals("on")) {
					pa.setCreateuser(true);
				} else {
					pa.setCreateuser(false);
				}
				if (restrictRight != null && restrictRight.equals("on")) {
					pa.setRestrictRight(true);
				} else {
					pa.setRestrictRight(false);
				}
				pa.setLevel((short) 1);
				pa.setStatus(Short.parseShort(status));
				pa.setRemark(remark);

				Area a = this.iAreaService.get(Integer.parseInt(tmpArea));
				pa.setArea(a);

				Ethnic e = this.iEthnicService.get(Short.parseShort(ethnic));
				pa.setEthnic(e);

				Organization o = this.iOragnizationService.get(Integer
						.parseInt(organization));
				pa.setOrganization(o);

				UserSession us = (UserSession) session
						.getAttribute("usersession");
				pa.setCreator(us.getCreator());

				this.iProjectAdminService.add(pa);

				try {
					if (restrictRightId != null) {
						for (String s : restrictRightId) {
							if (s != null && !s.equals("0") && !s.equals("")) {

								ProjectType pt = this.iProjectTypeService
										.get(Integer.parseInt(s));
								ProjectAdminRight par = new ProjectAdminRight();
								par.setProjectType(pt);
								par.setProjectAdmin(pa);
								this.iProjectAdminRightService.add(par);

							}
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				sendResponse("OK", "添加成功");
				return;

			} else {
				// 修改

				// TODO 判断 是否存在相同属性数据
				int eid = Integer.parseInt(id);
				ProjectAdmin pa = this.iProjectAdminService.get(eid);

				// 省份证
				if (pa.getIdcard() == null || !pa.getIdcard().equals(idcard)) {
					Object[] pars = { idcard, null, null };
					int flag = iProjectAdminService.checkUserInfo(pars);
					if (flag > 0) {
						sendResponse("ERROR", "添加失败，已经存在用户 身份证 信息");
						return;
					}
				}

				// 手机
				if (pa.getMobile() == null || !pa.getMobile().equals(mobile)) {
					Object[] pars = { null, mobile, null };
					int flag = iProjectAdminService.checkUserInfo(pars);
					if (flag > 0) {
						sendResponse("ERROR", "添加失败，已经存在用户 手机 信息");
						return;
					}
				}

				if (pa.getEmail() == null || !pa.getEmail().equals(email)) {
					Object[] pars = { null, null, email };
					int flag = iProjectAdminService.checkUserInfo(pars);
					if (flag > 0) {
						sendResponse("ERROR", "添加失败，已经存在用户 邮箱 信息");
						return;
					}
				}

				pa.setName(name);
				pa.setIdcard(idcard);
				pa.setSex(Short.parseShort(sex));
				pa.setMobile(mobile);
				pa.setDepartment(department);
				pa.setJobDuty(jobDuty);
				pa.setEmail(email);
				pa.setAddress(address);
				pa.setPhone(phone);
				pa.setFax(fax);
				pa.setRemark(remark);

				if (createuser != null && createuser.equals("on")) {
					pa.setCreateuser(true);
				} else {
					pa.setCreateuser(false);
				}
				if (restrictRight != null && restrictRight.equals("on")) {
					pa.setRestrictRight(true);
				} else {
					pa.setRestrictRight(false);
				}
				pa.setStatus(Short.parseShort(status));

				Area a = this.iAreaService.get(Integer.parseInt(tmpArea));
				pa.setArea(a);

				Ethnic e = this.iEthnicService.get(Short.parseShort(ethnic));
				pa.setEthnic(e);

				Organization o = this.iOragnizationService.get(Integer
						.parseInt(organization));
				pa.setOrganization(o);

				this.iProjectAdminService.update(pa);

				// 编辑权限
				try {

					// 先删除以前所有
					this.iProjectAdminRightService.deleteByProjectAdmin(pa
							.getId());
					if (restrictRightId != null) {
						for (String s : restrictRightId) {
							if (s != null && !s.equals("0") && !s.equals("")) {
								ProjectType pt = this.iProjectTypeService
										.get(Integer.parseInt(s));
								ProjectAdminRight par = new ProjectAdminRight();
								par.setProjectType(pt);
								par.setProjectAdmin(pa);
								this.iProjectAdminRightService.add(par);
							}
						}
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

				sendResponse("OK", "编辑成功");
				return;

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			sendResponse("ERROR", "操作出现异常");
			return;
		}
	}

	// 获取组织架构树
	@SuppressWarnings("rawtypes")
	public void getOrganizationLevel() {
		initServlert();

		String id = request.getParameter("id");
		if (id.equals("0")) {
			UserSession user = (UserSession) session
					.getAttribute("usersession");
			ROLEENUM ROLE = ROLEENUM.valueof(user.getRole());
			int orgainId = 0;
			if (ROLE != ROLEENUM.SUPERADMIN) {
				orgainId = user.getOrganization();
				Organization ora = this.iOragnizationService.get(orgainId);
				int flag = this.iOragnizationService.getOrgHasChild(orgainId);
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				String sr = "{\"id\":" + ora.getId() + ",\"scode\":\"" + ora.getScode() + "\",\"name\":\"" + ora.getName() + "\",\"haschild\":" + (flag > 0 ? 1 : 0) + "}";
				sb.append(sr);
				sb.append("]");
				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
				return;
			}
		}

		StringBuilder sb = new StringBuilder();
		sb.append("[");

		if (id != null && !id.equals("")) {

			int sid = Integer.parseInt(id);
			List li = this.iOragnizationService.getLevelOrganization(sid);
			for (int i = 0; i < li.size(); i++) {
				Object[] obj = (Object[]) li.get(i);
				int hid = Integer.parseInt(obj[0].toString());
				int flag = this.iOragnizationService.getOrgHasChild(hid);
				String sr = "{\"id\":" + hid + ",\"scode\":\"" + obj[2] + "\",\"name\":\""
						+ obj[1].toString() + "\",\"haschild\":"
						+ (flag > 0 ? 1 : 0) + "},";
				sb.append(sr);
			}
			if (li.size() > 0) {
				sb.delete(sb.length() - 1, sb.length());
			}
		}

		sb.append("]");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);

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

	public String getNaviString(ProjectType pt) {
		if (pt.getProjectType() == null) {
			return pt.getName();
		} else {
			return getNaviString(pt.getProjectType()) + "&" + pt.getName();
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

	public void setiOragnizationService(
			IOrganizationService iOragnizationService) {
		this.iOragnizationService = iOragnizationService;
	}

	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	public void setiProjectAdminService(
			IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}

	public IProjectTypeService getiProjectTypeService() {
		return iProjectTypeService;
	}

	public void setiProjectTypeService(IProjectTypeService iProjectTypeService) {
		this.iProjectTypeService = iProjectTypeService;
	}

	public IProjectAdminRightService getiProjectAdminRightService() {
		return iProjectAdminRightService;
	}

	public void setiProjectAdminRightService(
			IProjectAdminRightService iProjectAdminRightService) {
		this.iProjectAdminRightService = iProjectAdminRightService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String[] getArea() {
		return area;
	}

	public void setArea(String[] area) {
		this.area = area;
	}

	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJobDuty() {
		return jobDuty;
	}

	public void setJobDuty(String jobDuty) {
		this.jobDuty = jobDuty;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRestrictRight() {
		return restrictRight;
	}

	public void setRestrictRight(String restrictRight) {
		this.restrictRight = restrictRight;
	}

	public List<Object[]> getProvinceIds() {
		return provinceIds;
	}

	public void setProvinceIds(List<Object[]> provinceIds) {
		this.provinceIds = provinceIds;
	}

	public List<Object[]> getCityIds() {
		return cityIds;
	}

	public void setCityIds(List<Object[]> cityIds) {
		this.cityIds = cityIds;
	}

	public List<Object[]> getCountyIds() {
		return countyIds;
	}

	public void setCountyIds(List<Object[]> countyIds) {
		this.countyIds = countyIds;
	}

	public List<Ethnic> getListEthnic() {
		return listEthnic;
	}

	public void setListEthnic(List<Ethnic> listEthnic) {
		this.listEthnic = listEthnic;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPageStatus() {
		return pageStatus;
	}

	public void setPageStatus(String pageStatus) {
		this.pageStatus = pageStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String[] getRestrictRightId() {
		return restrictRightId;
	}

	public void setRestrictRightId(String[] restrictRightId) {
		this.restrictRightId = restrictRightId;
	}

	public List<String[]> getRestrictRightList() {
		return restrictRightList;
	}

	public void setRestrictRightList(List<String[]> restrictRightList) {
		this.restrictRightList = restrictRightList;
	}

	public List<ProjectType> getProjectTypeList() {
		return projectTypeList;
	}

	public void setProjectTypeList(List<ProjectType> projectTypeList) {
		this.projectTypeList = projectTypeList;
	}


	public String[] getProjectAdminOrgId() {
		return projectAdminOrgId;
	}
	

	public void setProjectAdminOrgId(String[] projectAdminOrgId) {
		this.projectAdminOrgId = projectAdminOrgId;
	}
	

	public List<String[]> getProjectAdminOrgList() {
		return projectAdminOrgList;
	}

	public void setProjectAdminOrgList(List<String[]> projectAdminOrgList) {
		this.projectAdminOrgList = projectAdminOrgList;
	}

	public String getProjectAdminOrg() {
		return projectAdminOrg;
	}

	public void setProjectAdminOrg(String projectAdminOrg) {
		this.projectAdminOrg = projectAdminOrg;
	}

	public String getProjectAdminLevel() {
		return projectAdminLevel;
	}

	public void setProjectAdminLevel(String projectAdminLevel) {
		this.projectAdminLevel = projectAdminLevel;
	}
}
