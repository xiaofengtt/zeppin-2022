package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.ProjectLevel;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IProjectLevelService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Administrator
 * @category 项目类型action
 */
public class ProjectTypeAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = LogManager.getLogger(ProjectTypeAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	/*
	 * 项目类型
	 */
	private IProjectTypeService projectTypeService;

	/*
	 * 地区
	 */
	private IAreaService areaService;

	/*
	 * 项目级别
	 */
	private IProjectLevelService projectLevelService;

	// 父id
	private String parentId;
	private String navi;
	private int level;
	private String projectLevelString;
	private String areaString;

	public ProjectTypeAction() {
	}

	public String initPage() {

		initServlert();
		String pid = request.getParameter("pid");
		this.parentId = pid;

		if (this.parentId == null || this.parentId.equals("0") || this.parentId.equals("")) {
			// 不存在父id
			this.navi = "";
			level = 1;
			this.projectLevelString = getProjectLevelStringMethod();
			Area aArea = this.areaService.get(DictionyMap.areaId);
			this.areaString = "[{\"Value\":" + aArea.getId() + ",\"DisplayText\":\"" + aArea.getName() + "\"}]"; // getAreaCodeStringMethod();
		} else {
			// 存在父id的话那就得获取当前父id信息已经父id 的父id等信息形成导航条
			ProjectType pt = this.projectTypeService.get(Integer.parseInt(this.parentId));
			if (pt != null) {
				String nv = " &rsaquo; ";
				if (pt.getProjectType() == null) {
					nv += pt.getName();
				} else {
					nv += getNaviString(pt.getProjectType()) + " &rsaquo; " + pt.getName();
				}

				this.navi = nv;
				this.level = pt.getLevel() + 1;
				this.projectLevelString = getProjectLevelStringMethod();
				this.areaString = "[{\"Value\":" + pt.getArea().getId() + ",\"DisplayText\":\"" + pt.getArea().getName() + "\"}]";
			} else {
				this.navi = "";
				this.level = 1;
				this.projectLevelString = getProjectLevelStringMethod();
				this.areaString = getAreaCodeStringMethod();
			}
		}

		return "init";
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * @author Administrator
	 * @category 获取项目类型列表 根据项目的pid来获取项目类型信息
	 * 
	 * @param jtStartIndex
	 *            起始
	 * @param pid
	 *            父id 默认为0
	 * @param jtPageSize
	 *            每页数据条数 默认为10
	 * 
	 * @param jtSorting
	 *            排序
	 * 
	 */
	public void getProjectTypeList() {
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

			// 排序
			String sort = request.getParameter("jtSorting");

			String pid = request.getParameter("pid");

			String hql = "from ProjectType t where 1=1 ";
			if (pid == null || pid.equals("0") || pid.equals("")) {
				hql += " and t.projectType is null ";
			} else {
				hql += " and t.projectType=" + pid;
			}

			// 以后其他搜索参数

			// 排序 参数
			if (sort != null && !sort.equals("")) {
				String[] sortarray = sort.split(" ");
				String sortname = sortarray[0];
				String sorttype = sortarray[1];
				hql += " order by t." + sortname + " " + sorttype;
			}

			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");

			List<Object> licount = projectTypeService.findByHSQL("select count(*) " + hql);

			if (licount != null && licount.size() > 0) {
				int records = Integer.parseInt(licount.get(0).toString());
				sb.append("\"TotalRecordCount\":" + records);
				sb.append(",");

			} else {
				sb.append("\"TotalRecordCount\":0");
				sb.append(",");
			}

			List<ProjectType> li = projectTypeService.getListForPage(hql, start, limit);

			sb.append("\"Records\":[");

			for (int i = 0; i < li.size(); i++) {

				ProjectType ts = li.get(i);
				int id = ts.getId();
				String name = ts.getName();
				String shortName = ts.getShortname();
				int projectLevel = ts.getProjectLevel().getId();
				short level = ts.getLevel();
				int areid = ts.getArea().getId();

				int status = ts.getStatus();

				String sr = "{\"id\":" + id + ",\"name\":\"" + name.trim() + "\",\"shortname\":\"" + shortName.trim() + "\",\"projectLevel\":" + projectLevel + ",\"area\":" + areid + ",\"level\":" + level + ",\"status\":" + status + "}";
				sb.append(sr);
				sb.append(",");
			}

			if (li.size() > 0) {
				sb.delete(sb.length() - 1, sb.length());
			}
			sb.append("]}");

			Utlity.ResponseWrite(sb.toString(), "application/json", response);

		} catch (Exception ex) {
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
	 * @category 项目类型的操作包括 添加，修改，删除
	 * @param method
	 *            add,edit,del
	 * @param id
	 * @param name
	 * @param shortname
	 * @param projectlevel
	 * @param level
	 * @param pid
	 * @param area
	 * @param status
	 * 
	 * 
	 */
	public void opProjectType() {

		initServlert();

		try {

			String method = request.getParameter("method");
			if (method == null) {
				sendResponse("ERROR", "不存在操作类型");

			} else {
				if (method.equals("add") || method.equals("edit")) {

					// 名称
					String name = request.getParameter("name");
					// 简称
					String shortname = request.getParameter("shortname");

					if (Utlity.checkStringNull(name)) {
						sendResponse("ERROR", "名称为空");
						return;
					}
					if (Utlity.checkStringNull(shortname)) {
						sendResponse("ERROR", "简称为空");
						return;
					}

					// 项目级别
					String projectLevel = request.getParameter("projectLevel");

					// 层级
					String level = request.getParameter("level");
					if (level == null || level.equals("")) {
						level = "1";
					}

					// 父id
					String pid = request.getParameter("pid");

					// 地区
					String areaid = request.getParameter("area");

					String status = request.getParameter("status");

					if (method.equals("add")) {

						List<ProjectType> liExist = projectTypeService.findByName(name);

						if (liExist != null && liExist.size() > 0 && level.equals("1")) {
							// 已经存在重复名称科目
							sendResponse("ERROR", "已经存在相同的项目类型");
							return;
						}

						Date date = new Date(); // 当前时间
						ProjectType pt = new ProjectType();
						pt.setName(name); // 名称
						pt.setShortname(shortname); // 简称
						pt.setLevel(Short.parseShort(level)); // 层级
						pt.setStatus(Short.parseShort(status)); // 状态
						pt.setCreattime(new Timestamp(date.getTime())); // 创建时间

						// 角色
						UserSession us = (UserSession) session.getAttribute("usersession");
						us.setCreateuser(us.getOrganization());

						ProjectLevel pl = this.projectLevelService.get(Short.parseShort(projectLevel));
						// 项目级别
						pt.setProjectLevel(pl);

						// 地区
						Area are = this.areaService.get(Integer.parseInt(areaid));
						pt.setArea(are);

						// 上级项目类型
						if (pid != null && !pid.equals("") && !pid.equals("0")) {
							ProjectType fpt = this.projectTypeService.get(Integer.parseInt(pid));
							if (fpt != null) {
								pt.setProjectType(fpt);
							}
						}

						// 增加
						this.projectTypeService.add(pt);

						StringBuilder sb = new StringBuilder();
						sb.append("{");
						sb.append("\"Result\":\"OK\"");
						sb.append(",");
						sb.append("\"Record\":");

						String sr = "{\"id\":" + pt.getId() + ",\"name\":\"" + name + "\",\"shortname\":\"" + shortname + "\",\"projectLevel\":" + projectLevel + ",\"level\":" + level + ",\"status\":" + status + "}";

						sb.append(sr);

						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);

					} else if (method.equals("edit")) {
						String id = request.getParameter("id");
						if (id != null) {

							ProjectType pt = this.projectTypeService.get(Integer.parseInt(id));
							if (pt != null && !pt.getName().equals(name)) {
								List<ProjectType> liExist = projectTypeService.findByName(name);

								if (liExist != null && liExist.size() > 0 && level.equals("1")) {
									// 已经存在重复名称科目
									sendResponse("ERROR", "已经存在相同的项目类型");
									return;
								}
							}
							short shostatus = Short.parseShort(status);
							if (pt != null && pt.getStatus() != shostatus) {
								// 如果状态修改
								// 循环子级所有状态修改
								this.projectTypeService.updateProjectTypeStatus(pt, shostatus);
							}
							pt.setName(name);
							pt.setShortname(shortname);
							ProjectLevel pl = this.projectLevelService.get(Short.parseShort(projectLevel));
							// 项目级别
							pt.setProjectLevel(pl);

							this.projectTypeService.update(pt);

							sendResponse("OK", "更新成功");

						} else {
							sendResponse("ERROR", "修改失败，不存在项目类型");
						}
					}

				} else if (method.equals("delete")) {
					String id = request.getParameter("id");
					if (id != null) {
						int did = Integer.parseInt(id);
						ProjectType pt = this.projectTypeService.get(did);

						if (pt != null) {

							if (pt.getProjectTypes().size() > 0) {
								sendResponse("ERROR", "删除失败，存在子类型");
							} else {
								this.projectTypeService.delete(pt);
								sendResponse("OK", "删除成功");
							}

						} else {

							sendResponse("ERROR", "删除失败，不存在项目类型");
						}

					} else {
						sendResponse("ERROR", "删除失败，不存在项目类型");
					}
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			sendResponse("ERROR", "操作失败");
		}

	}

	public void getListByPid() {
		initServlert();
		try {
			String id = request.getParameter("id");
			if (id == null || id.equals("")) {
				id = "0";
			}
			int sid = Integer.parseInt(id);
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			List<ProjectType> list = this.projectTypeService.getListByPid(sid);
			ProjectType ppt = this.projectTypeService.get(sid);
			if (ppt!=null){
				sb.append("{\"id\":" + sid + ",\"name\":\"全部\",\"haschild\":0},");
			}
			for (ProjectType pt : list) {
				int ptid = pt.getId();
				int flag = this.projectTypeService.getProjectHasChild(ptid);
				String sr = "{\"id\":" + ptid + ",\"name\":\"" + pt.getName() + "\",\"haschild\":" + flag + "},";
				sb.append(sr);
			}
			if (list.size() > 0) {
				sb.delete(sb.length() - 1, sb.length());
			}
			sb.append("]");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);

		} catch (Exception ex) {

		}
	}
	
	/**
	 * 项目分级后 添加项目筛选项目类型
	 */
	public void getTypeListByPid() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		try {
			String id = request.getParameter("id");
			if (id == null || id.equals("")) {
				id = "0";
			}
			int sid = Integer.parseInt(id);
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			Map<String, Object> paramss = new HashMap<>();
			paramss.put("level", us.getOrganizationLevel());
			paramss.put("pid", sid);
			List<ProjectType> list = this.projectTypeService.getProjectTypeList(null,paramss);
			ProjectType ppt = this.projectTypeService.get(sid);
			if (ppt!=null){
				sb.append("{\"id\":" + sid + ",\"name\":\"全部\",\"haschild\":0},");
			}
			for (ProjectType pt : list) {
				int ptid = pt.getId();
				int flag = this.projectTypeService.getProjectHasChild(ptid);
				String sr = "{\"id\":" + ptid + ",\"name\":\"" + pt.getName() + "\",\"haschild\":" + flag + "},";
				sb.append(sr);
			}
			if (list.size() > 0) {
				sb.delete(sb.length() - 1, sb.length());
			}
			sb.append("]");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);

		} catch (Exception ex) {

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

	// 获取道航字符串
	public String getNaviString(ProjectType pt) {
		if (pt.getProjectType() == null) {
			return "<a href=\"../admin/projectType_initPage.action?pid=" + pt.getId() + "\">" + pt.getName() + "</a>";
		} else {
			return getNaviString(pt.getProjectType()) + " &rsaquo; " + "<a href=\"../admin/projectType_initPage.action?pid=" + pt.getId() + "\">" + pt.getName() + "</a>";
		}
	}

	public String getProjectLevelStringMethod() {
		List<ProjectLevel> li = this.projectLevelService.getList();
		String str = "[";
		String st = "";
		for (ProjectLevel pl : li) {
			st += "{\"Value\":" + pl.getId() + ",\"DisplayText\":\"" + pl.getName() + "\"}";
			st += ",";
		}
		if (st.length() > 0) {
			st = st.substring(0, st.length() - 1);
		}
		str += st + "]";
		return str;
	}

	public String getAreaCodeStringMethod() {
		List<Area> li = this.areaService.getParentCodeArea(DictionyMap.areaCode);
		String ret = "[";
		String st = "";
		for (Area ar : li) {
			st += "{\"Value\":" + ar.getId() + ",\"DisplayText\":\"" + ar.getName() + "\"},";
		}
		if (st.length() > 0) {
			st = st.substring(0, st.length() - 1);
		}
		ret += st + "]";
		return ret;
	}

	// 属性区

	public IProjectTypeService getProjectTypeService() {
		return projectTypeService;
	}

	public void setProjectTypeService(IProjectTypeService projectTypeService) {
		this.projectTypeService = projectTypeService;
	}

	public IAreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}

	public IProjectLevelService getProjectLevelService() {
		return projectLevelService;
	}

	public void setProjectLevelService(IProjectLevelService projectLevelService) {
		this.projectLevelService = projectLevelService;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getProjectLevelString() {
		return projectLevelString;
	}

	public void setProjectLevelString(String projectLevelString) {
		this.projectLevelString = projectLevelString;
	}

	public String getAreaString() {
		return areaString;
	}

	public void setAreaString(String areaString) {
		this.areaString = areaString;
	}

}
