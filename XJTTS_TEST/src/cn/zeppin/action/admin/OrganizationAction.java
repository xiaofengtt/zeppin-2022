package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.OrganizationLevel;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IGradeService;
import cn.zeppin.service.IOrganizationLevelService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.pinyingUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Administrator
 * @category 组织架构业务
 */
public class OrganizationAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(OrganizationAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IOrganizationService iOrganization; // organization
	private IOrganizationLevelService iOrganizationLevel; // organizationlevel
	private IAreaService iArea; // 地区信息
	private IProjectAdminService iProjectAdmin; // 项目管理员
	private IGradeService iGrade;

	private String parentId;
	private String navi;
	private String organizationLevelStr;

	public OrganizationAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public String initPage() {

		initServlert();

		String pid = request.getParameter("pid");
		this.parentId = pid;
		if (this.parentId == null || this.parentId.equals("0") || this.parentId.equals("")) {
			this.navi = "";
			this.organizationLevelStr = getOrganizationLevelStringMethod(1, 100);
		} else {
			Organization oz = this.iOrganization.get(Integer.parseInt(this.parentId));
			if (oz != null) {
				String nv = " &rsaquo; ";
				if (oz.getOrganization() == null) {
					nv += oz.getName();
				} else {
					nv += getNaviString(oz.getOrganization()) + " &rsaquo; " + oz.getName();
				}
				this.navi = nv;
				this.organizationLevelStr = getOrganizationLevelStringMethod(oz.getOrganizationLevel().getLevel() + 1, oz.getOrganizationLevel().getLevel() + 1);
			} else {
				this.navi = "";
				this.organizationLevelStr = getOrganizationLevelStringMethod(1, 100);
			}
		}

		return "init";
	}

	public String initSearchPage() {
		return "search";
	}
	
	/**
	 * @category 获取 组织架构列表
	 * @param jtStartIndex
	 * @param jtPageSize
	 * @param pid
	 * @param jtSorting
	 * @param
	 */
	@SuppressWarnings("unchecked")
	public void getOrganizationList() {

		initServlert();

		try {

			Hashtable<String, Object> ht = this.iOrganization.getOrganizationsListPage(request);
			int records = (int) ht.get("records");

			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			if (records > 0) {
				sb.append("\"TotalRecordCount\":" + records);
				sb.append(",");

			} else {
				sb.append("\"TotalRecordCount\":0");
				sb.append(",");
			}

			List<Organization> li = (List<Organization>) ht.get("data");

			sb.append("\"Records\":[");

			for (int i = 0; i < li.size(); i++) {

				Organization ts = li.get(i);
				int id = ts.getId();
				String name = ts.getName();
				String shortName = ts.getShortName();
				String adress = ts.getAdress();
				Short organizlevel = ts.getOrganizationLevel().getId();
				Area a = ts.getArea();
				int areaid = a.getId();
				int areaid1 = 0;
				int areaid2 = 0;
				String areacode = a.getParentcode();
				if (areacode != null && !areacode.equals("0") && !areacode.equals("")) {
					// 当前的地区的父地区存在
					Area pa = this.iArea.getAreaByCode(areacode);
					if (pa != null) {
						areaid1 = pa.getId();
						areacode = pa.getParentcode();
						// 父父地区
						if (areacode != null && !areacode.equals("0") && !areacode.equals("")) {
							pa = this.iArea.getAreaByCode(areacode);
							if (pa != null) {
								areaid2 = pa.getId();
							}
						}
					}
				}
				int a1 = 0;
				int a2 = 0;
				int a3 = 0;
				if (areaid1 > 0) {
					if (areaid2 > 0) {
						a1 = areaid2;
						a2 = areaid1;
						a3 = areaid;
					} else {
						a1 = areaid1;
						a2 = areaid;
						a3 = 0;
					}
				} else {
					a1 = areaid;
					a2 = 0;
					a3 = 0;
				}

				String contacts = ts.getContacts() == null ? "" : ts.getContacts();
				String phone = ts.getPhone() == null ? "" : ts.getPhone();
				String fax = ts.getFax() == null ? "" : ts.getFax();
				int status = ts.getStatus();
				int isschool = ts.getIsschool() ? 1 : 0;
				int grade = -1;
				if (ts.getGrade() != null) {
					grade = ts.getGrade().getId();
				}

				String sr = "{\"id\":" + id + ",\"name\":\"" + name + "\",\"shortName\":\"" + shortName;
				sr += "\",\"organizationLevel\":" + organizlevel + ",\"area\":" + a1;
				sr += ",\"area1\":" + a2 + ",\"area2\":" + a3;
				sr += ",\"adress\":\"" + adress + "\",\"isschool\":" + isschool + ",\"grade\":" + grade;
				sr += ",\"contacts\":\"" + contacts + "\",\"phone\":\"" + phone + "\",\"fax\":\"" + fax + "\",\"status\":" + status + "}";
				sb.append(sr);
				sb.append(",");
			}

			if (li.size() > 0) {
				sb.delete(sb.length() - 1, sb.length());
			}
			sb.append("]}");

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
	 * @author Administrator
	 * @category 组织架构的操作,要做手机、邮箱、身份证进行验证
	 * @param method
	 *            add,edit,delete
	 * 
	 */
	public void opOrganization() {

		initServlert();

		try {

			String method = request.getParameter("method");
			if (method != null) {

				if (method.equals("add") || method.equals("edit")) {

					String name = request.getParameter("name");
					String shortName = request.getParameter("shortName");
					String adress = request.getParameter("adress");
					String areaid = request.getParameter("area");
					String areaid1 = request.getParameter("area1");
					String areaid2 = request.getParameter("area2");
					String isschool = request.getParameter("isschool");
					String gradeid = request.getParameter("grade");
					isschool = isschool == null ? "0" : isschool;
					String organizationLevel = request.getParameter("organizationLevel");
					String phone = request.getParameter("phone");
					String contacts = request.getParameter("contacts");
					String fax = request.getParameter("fax");
					String status = request.getParameter("status");

					String id = request.getParameter("id");
					String pid = request.getParameter("pid");

					// TODO 在次要认证必填字段信息
					if (Utlity.checkStringNull(name)) {
						sendResponse("ERROR", "单位名称为空");
						return;
					}

					if ((areaid == null || areaid.equals("") || areaid.equals("0")) && (areaid == null || areaid.equals("") || areaid.equals("0")) && (areaid == null || areaid.equals("") || areaid.equals("0"))) {
						sendResponse("ERROR", "省/市/县 地区为空");
						return;
					}

					if (Utlity.checkStringNull(shortName)) {
						sendResponse("ERROR", "简称为空");
						return;
					}

					String tmpArea = areaid;
					if (areaid2 != null && !areaid2.equals("0") && !areaid2.equals("")) {
						tmpArea = areaid2;
					} else {
						if (areaid1 != null && !areaid1.equals("0") && !areaid1.equals("")) {
							tmpArea = areaid1;
						}
					}

					if (method.equals("add")) {

						/**
						 * 
						 * 添加组织架构，在添加完组织架构之后再添加相应的管理员初始信息 首先要验证 用户是否已经存在用户信息
						 */
						Object[] pars = { null, phone, null };
						int flag = iOrganization.checkUserInfo(pars);
						if (flag > 0) {
							// 已经存在用户信息

							sendResponse("ERROR", "添加失败，已经存在用户信息");
							return;
						}

						Date date = new Date(); // 当前时间
						Organization ora = new Organization();
						ora.setName(name); // 名称
						ora.setPinying(pinyingUtil.getFirstSpell(name));
						ora.setShortName(shortName); // 简称
						if (isschool.equals("1")) {
							ora.setIsschool(true);
						} else if (isschool.equals("0")) {
							ora.setIsschool(false);
						}
						ora.setAdress(adress);
						ora.setContacts(contacts);
						ora.setPhone(phone);
						ora.setFax(fax);
						ora.setStatus(Short.parseShort(status));

						// // 角色
						UserSession us = (UserSession) session.getAttribute("usersession");
						ora.setCreator(us.getCreator());

						// 组织架级别
						OrganizationLevel oralevel = this.iOrganizationLevel.get(Short.parseShort(organizationLevel));
						ora.setOrganizationLevel(oralevel);

						// 地区
						Area are = this.iArea.get(Integer.parseInt(tmpArea));
						ora.setArea(are);

						// 学段
						if (ora.getIsschool()) {
							Grade grade = this.iGrade.get(Short.parseShort(gradeid));
							ora.setGrade(grade);
						}
						// 上级组织架构
						if (pid != null && !pid.equals("0") && !pid.equals("")) {
							Organization parentOrag = this.iOrganization.get(Integer.parseInt(pid));
							if (parentOrag != null) {
								ora.setOrganization(parentOrag);
							}
						}
						ora.setCreattime(new Timestamp(date.getTime()));

						// 添加 组织架构
						this.iOrganization.addOrganization(ora);

						StringBuilder sb = new StringBuilder();
						sb.append("{");
						sb.append("\"Result\":\"OK\"");
						sb.append(",");
						sb.append("\"Record\":");

						String sr = "{\"id\":" + ora.getId() + ",\"name\":\"" + name + "\",\"organizationLevel\":" + organizationLevel + ",\"area\":" + areaid;
						sr += ",\"contacts\":\"" + contacts + "\",\"phone\":\"" + phone + "\",\"fax\":\"" + fax + "\",\"status\":" + status + "}";

						sb.append(sr);

						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);

					} else {
						// 编辑 组织架构

						/**
						 * 首先要验证 用户是否已经存在用户信息
						 */

						int uid = Integer.parseInt(id);
						Organization ora = this.iOrganization.get(uid);
						if (ora != null && ora.getPhone() != null && !ora.getPhone().equals(phone)) {

							Object[] pars = { null, phone, null };
							int flag = iOrganization.checkUserInfo(pars);
							if (flag > 0) {
								// 已经存在用户信息
								sendResponse("ERROR", "编辑失败，已经存在用户信息");
								return;
							}
						}

						if (ora != null) {

							ora.setName(name); // 名称
							ora.setPinying(pinyingUtil.getFirstSpell(name));
							ora.setShortName(shortName); // 简称
							if (isschool.equals("1")) {
								ora.setIsschool(true);
							} else if (isschool.equals("0")) {
								ora.setIsschool(false);
							}
							ora.setAdress(adress);
							ora.setContacts(contacts);
							ora.setPhone(phone);
							ora.setFax(fax);
							ora.setStatus(Short.parseShort(status));

							// 组织架级别
							OrganizationLevel oralevel = this.iOrganizationLevel.get(Short.parseShort(organizationLevel));
							ora.setOrganizationLevel(oralevel);

							// 地区
							Area are = this.iArea.get(Integer.parseInt(tmpArea));
							ora.setArea(are);

							// 学段
							if (ora.getIsschool()) {
								Grade grade = this.iGrade.get(Short.parseShort(gradeid));
								ora.setGrade(grade);
							}

							this.iOrganization.update(ora);

							sendResponse("OK", "更新成功");

						} else {
							sendResponse("ERROR", "编辑失败，不存在此条组织架构");
						}

					}

				} else if (method.equals("delete")) {

					/**
					 * 删除操作 首先查看 此条记录是否存在 如果存在，则先删除所有子级
					 */

					String id = request.getParameter("id");
					if (id != null) {

						int did = Integer.parseInt(id);
						Organization oa = this.iOrganization.get(did);
						if (oa != null) {

							// TODO 判断外键
							Set<Organization> hset = oa.getOrganizations();
							if (hset.size() > 0) {
								sendResponse("ERROR", "删除失败，存在子级组织架构");
							} else if (oa.getProjectAdmins() != null && oa.getProjectAdmins().size() > 0) {
								sendResponse("ERROR", "删除失败，存在组织架构对应的项目管理员");
							} else if (oa.getAssignTeacherTasksForGOrganization() != null && oa.getAssignTeacherTasksForGOrganization().size() > 0) {
								sendResponse("ERROR", "删除失败，存在组织架构对应的学员名额分配任务");
							} else if (oa.getAssignTeacherTasksForPOrganization() != null && oa.getAssignTeacherTasksForPOrganization().size() > 0) {
								sendResponse("ERROR", "删除失败，存在组织架构对应的学员名额分配任务");
							} else if (oa.getTeachers() != null && oa.getTeachers().size() > 0) {
								sendResponse("ERROR", "删除失败，组织架构下存在学员信息");
							} else {
								try {

									this.iOrganization.delete(oa);
									sendResponse("OK", "删除成功");

								} catch (Exception ex) {
									ex.printStackTrace();
									sendResponse("ERROR", "删除失败,出现异常");
								}
							}

						} else {
							sendResponse("ERROR", "删除失败，不存在此条组织架构");
						}

					} else {
						sendResponse("ERROR", "删除失败，不存在此条组织架构");
					}

				} else {
					sendResponse("ERROR", "操作失败，不存在操作类型");
				}

			} else {
				sendResponse("ERROR", "操作失败，不存在操作类型");
			}

		} catch (Exception ex) {
			logger.error(ex);
			sendResponse("ERROR", "操作失败");
		}

	}

	// 递归删除
	public void deleteOrganization(Organization oa) {
		try {
			Set<Organization> hset = oa.getOrganizations();
			if (hset.size() == 0) {
				if (oa.getOrganization() != null) {
					oa.getOrganization().getOrganizations().remove(oa);
				}
				this.iOrganization.delete(oa);
			} else {

				for (Organization ora : hset) {
					deleteOrganization(ora);
				}
				deleteOrganization(oa);
			}
		} catch (Exception ex) {
			throw ex;
		}
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

	// 获取 组织架构级别
	public String getOrganizationLevelStringMethod(int level, int end) {
		List<OrganizationLevel> li = this.iOrganizationLevel.getList();
		String str = "[";
		String st = "";
		for (OrganizationLevel pl : li) {
			if (pl.getLevel() >= level && pl.getLevel() <= end) {
				st += "{\"Value\":" + pl.getId() + ",\"DisplayText\":\"" + pl.getName() + "\"}";
				st += ",";
			}
		}
		if (st.length() > 0) {
			st = st.substring(0, st.length() - 1);
		}
		str += st + "]";
		return str;
	}

	// 获取道航
	public String getNaviString(Organization oz) {
		if (oz.getOrganization() == null) {
			return "<a href=\"../admin/organization_initPage.action?pid=" + oz.getId() + "\">" + oz.getName() + "</a>";
		} else {
			return getNaviString(oz.getOrganization()) + " &rsaquo; " + "<a href=\"../admin/organization_initPage.action?pid=" + oz.getId() + "\">" + oz.getName() + "</a>";
		}
	}

	// 属性区

	public IOrganizationService getiOrganization() {
		return iOrganization;
	}

	public void setiOrganization(IOrganizationService iOrganization) {
		this.iOrganization = iOrganization;
	}

	public IOrganizationLevelService getiOrganizationLevel() {
		return iOrganizationLevel;
	}

	public void setiOrganizationLevel(IOrganizationLevelService iOrganizationLevel) {
		this.iOrganizationLevel = iOrganizationLevel;
	}

	public IAreaService getiArea() {
		return iArea;
	}

	public void setiArea(IAreaService iArea) {
		this.iArea = iArea;
	}

	public IProjectAdminService getiProjectAdmin() {
		return iProjectAdmin;
	}

	public void setiProjectAdmin(IProjectAdminService iProjectAdmin) {
		this.iProjectAdmin = iProjectAdmin;
	}

	public IGradeService getiGrade() {
		return iGrade;
	}

	public void setiGrade(IGradeService iGrade) {
		this.iGrade = iGrade;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getNavi() {
		return navi;
	}

	public void setNavi(String navi) {
		this.navi = navi;
	}

	public String getOrganizationLevelStr() {
		return organizationLevelStr;
	}

	public void setOrganizationLevelStr(String organizationLevelStr) {
		this.organizationLevelStr = organizationLevelStr;
	}

}
