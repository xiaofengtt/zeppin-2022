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
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.OrganizationLevel;
import cn.zeppin.entity.Teacher;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IGradeService;
import cn.zeppin.service.IOrganizationLevelService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.ITeacherAdjustService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.pinyingUtil;
import cn.zeppin.utility.DictionyMap.ROLEENUM;

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
	private ITeacherService iTeacherService;// 教师操作
	private ITeacherAdjustService teacherAdjustService;

	private String parentId;
	private String navi;
	private String organizationLevelStr;
	private String orgId;
	private Organization orgn;

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
	
	/**
	 * 初始化管理员操作页面
	 * @return
	 */
	public String initPageForAdmin() {

		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		String pid = request.getParameter("pid");
		
		if (pid == null || pid.equals("0") || pid.equals("")) {
			pid = us.getOrganization()+"";
			this.orgId = us.getOrganization()+"";
			
		}else{
			this.orgId = "0";
		}
		session.setAttribute("org", this.orgId);
		this.parentId = pid;
		Organization oz = this.iOrganization.get(Integer.parseInt(this.parentId));
		if (oz != null) {
			String nv = " &rsaquo; ";
			if(!"0".equals(this.orgId)){
				nv += oz.getName();
			}else{
				nv += getNaviStringForAdmin(oz,us) + " &rsaquo; " + oz.getName();
			}
			this.navi = nv;
			if(oz.getOrganizationLevel().getLevel() == 4){
				this.organizationLevelStr = getOrganizationLevelStringMethod(oz.getOrganizationLevel().getLevel(), oz.getOrganizationLevel().getLevel());
			}else{
				if(!"0".equals(this.orgId)){
					this.organizationLevelStr = getOrganizationLevelStringMethod(oz.getOrganizationLevel().getLevel(), oz.getOrganizationLevel().getLevel());
				}else{
					this.organizationLevelStr = getOrganizationLevelStringMethod(oz.getOrganizationLevel().getLevel() + 1, oz.getOrganizationLevel().getLevel() + 1);
				}
			}
			
		} else {
			this.navi = "";
			this.organizationLevelStr = getOrganizationLevelStringMethod(1, 100);
		}

		return "initForAdmin";
	}

	public String initSearchPage() {
		return "search";
	}
	
	/**
	 * 定位搜查结果
	 */
	public void getSearchList(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		String oid = request.getParameter("oid") == null ? "" : request.getParameter("oid");
		Organization o = null;
		if(!"".equals(oid)){
			o = this.iOrganization.get(Integer.parseInt(oid));
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			String pid = "";
			if(o.getOrganization() != null){
				pid = o.getOrganization().getId()+"";
			}
				
			sb.append("\"pid\":"+pid);
			sb.append("}");
		}else{
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"查询出错\"");
			sb.append("}");
		}
		
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
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
				int areaid3 = 0;
				int areaid4 = 0;
				String areacode = a.getParentcode();
				//获取学校地区改造（5级地区）
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
								areacode = pa.getParentcode();
								// 父父地区
								if (areacode != null && !areacode.equals("0") && !areacode.equals("")) {
									pa = this.iArea.getAreaByCode(areacode);
									if (pa != null) {
										areaid3 = pa.getId();
										areacode = pa.getParentcode();
										// 父父地区
										if (areacode != null && !areacode.equals("0") && !areacode.equals("")) {
											pa = this.iArea.getAreaByCode(areacode);
											if (pa != null) {
												areaid4 = pa.getId();
												
											}
										}
									}
								}
							}
						}
					}
				}
				int a1 = 0;
				int a2 = 0;
				int a3 = 0;
				int a4 = 0;
				int a5 = 0;
				if (areaid1 > 0) {
					if (areaid2 > 0) {
						if(areaid3 > 0){
							if(areaid4 > 0){
								a1 = areaid4;
								a2 = areaid3;
								a3 = areaid2;
								a4 = areaid1;
								a5 = areaid;
							}else{
								a1 = areaid3;
								a2 = areaid2;
								a3 = areaid1;
								a4 = areaid;
							}
						}else{
							a1 = areaid2;
							a2 = areaid1;
							a3 = areaid;
						}
					} else {
						a1 = areaid1;
						a2 = areaid;
					}
				} else {
					a1 = areaid;
				}

				String contacts = ts.getContacts() == null ? "" : ts.getContacts();
				String phone = ts.getPhone() == null ? "" : ts.getPhone();
				String fax = ts.getFax() == null ? "" : ts.getFax();
				int status = ts.getStatus();
				int isschool = ts.getIsschool() ? 1 : 0;
//				int grade = -1;
//				if (ts.getGrade() != null) {
//					grade = ts.getGrade().getId();
//				}
//				String grade = "";
				int grade = -1;
				if(ts.getType() != null && !"".equals(ts.getType())){
					grade = Integer.parseInt(getTypeIdString(ts.getType()));
				}

				String otherscode = ts.getOtherscode() == null ? "" : ts.getOtherscode();//标识码
				
				String organizerName = ts.getOrganizerName() == null ? "" : ts.getOrganizerName();//举办者类型
				int organizer = 0;
				if(!"".equals(organizerName)){
					organizer = getorganizerNameId(organizerName);
				}
				
				String dictype = ts.getDictype() == null ? "" : ts.getDictype();//办别
				int dic = 0;
				if(!"".equals(dictype)){
					if("公办".equals(dictype)){
						dic = 1;
					}else if ("民办".equals(dictype)){
						dic = 2;
					}
				}
				
				String ftype = ts.getFtype() == null ? "" : ts.getFtype();//城乡分类
				int ftp = 0;
				if(!"".equals(ftype)){
//					if("城市".equals(ftype)){
//						ftp = 1;
//					}else if ("农村".equals(ftype)){
//						ftp = 2;
//					}
					if("主城区".equals(ftype)){
						ftp = 1;
					} else if ("城乡结合区".equals(ftype)) {
						ftp = 2;
					} else if ("镇中心区".equals(ftype)) {
						ftp = 3;
					} else if ("镇乡结合区".equals(ftype)) {
						ftp = 4;
					} else if ("特殊区域".equals(ftype)) {
						ftp = 5;
					} else if ("乡中心区".equals(ftype)) {
						ftp = 6;
					} else if ("村庄".equals(ftype)) {
						ftp = 7;
					} else if ("乡村".equals(ftype)) {
						ftp = 8;
					}
				}
				
				Short isPoor = ts.getIsPoor() == null ? 0 : ts.getIsPoor();//是否为集中连片特困地区县
				Short isCountryPoor = ts.getIsCountryPoor() == null ? 0 : ts.getIsCountryPoor();//是否为国家级贫困县
//				Short attribute = ts.getAttribute() == null ? 0 : ts.getAttribute();//学校地域属性1-城市 2-县城 3-镇区 4-乡 5-村 6-教学点 0-请选择

				String sr = "{\"id\":" + id + ",\"name\":\"" + name + "\",\"shortName\":\"" + shortName;
				sr += "\",\"organizationLevel\":" + organizlevel + ",\"area\":" + a1;
				sr += ",\"area1\":" + a2 + ",\"area2\":" + a3 + ",\"area3\":" + a4 + ",\"area4\":" + a5;
				sr += ",\"adress\":\"" + adress + "\",\"isschool\":" + isschool + ",\"grade\":" + grade;
				sr += ",\"otherscode\":\"" + otherscode + "\",\"organizerName\":" + organizer + ",\"dictype\":" + dic;
				sr += ",\"ftype\":" + ftp + ",\"isPoor\":" + isPoor + ",\"isCountryPoor\":" + isCountryPoor;
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
	 * @category 获取 组织架构列表
	 * @param jtStartIndex
	 * @param jtPageSize
	 * @param pid
	 * @param jtSorting
	 * @param
	 */
	@SuppressWarnings("unchecked")
	public void getOrganizationListForAdmin() {

		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		Organization or = this.iOrganization.get(us.getOrganization());
		Short isMerge = 0;
		if(!or.getIsschool()){//是否有权限合并学校：学校管理员无权操作
			isMerge = 1;
		}
		
		Short isTransfer = 0;
		if(!or.getIsschool()){//是否有权限迁移学校：县级、学校管理员无权操作
			isTransfer = 1;
		}
		if(us.getOrganizationLevel() > 2){
			isTransfer = 0;
		}
		try {
			String pid = request.getParameter("pid") == null ? "0" : request.getParameter("pid");
			if("".equals(pid)){
				pid = "0";
			}
			String oid = session.getAttribute("org").toString();
			if("".equals(oid)){
				oid = "0";
			}
			if(us.getOrganization() == Integer.parseInt(oid)){
				int records = 1;

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

//				List<Organization> li = (List<Organization>) ht.get("data");

				sb.append("\"Records\":[");
				Organization ts = this.iOrganization.get(Integer.parseInt(pid));
				int id = ts.getId();
				String name = ts.getName();
				String shortName = ts.getShortName();
				String adress = ts.getAdress();
				Short organizlevel = ts.getOrganizationLevel().getId();
				Area a = ts.getArea();
				int areaid = a.getId();
				int areaid1 = 0;
				int areaid2 = 0;
				int areaid3 = 0;
				int areaid4 = 0;
				String areacode = a.getParentcode();
				//获取学校地区改造（5级地区）
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
								areacode = pa.getParentcode();
								// 父父地区
								if (areacode != null && !areacode.equals("0") && !areacode.equals("")) {
									pa = this.iArea.getAreaByCode(areacode);
									if (pa != null) {
										areaid3 = pa.getId();
										areacode = pa.getParentcode();
										// 父父地区
										if (areacode != null && !areacode.equals("0") && !areacode.equals("")) {
											pa = this.iArea.getAreaByCode(areacode);
											if (pa != null) {
												areaid4 = pa.getId();
												
											}
										}
									}
								}
							}
						}
					}
				}
				int a1 = 0;
				int a2 = 0;
				int a3 = 0;
				int a4 = 0;
				int a5 = 0;
				if (areaid1 > 0) {
					if (areaid2 > 0) {
						if(areaid3 > 0){
							if(areaid4 > 0){
								a1 = areaid4;
								a2 = areaid3;
								a3 = areaid2;
								a4 = areaid1;
								a5 = areaid;
							}else{
								a1 = areaid3;
								a2 = areaid2;
								a3 = areaid1;
								a4 = areaid;
							}
						}else{
							a1 = areaid2;
							a2 = areaid1;
							a3 = areaid;
						}
					} else {
						a1 = areaid1;
						a2 = areaid;
					}
				} else {
					a1 = areaid;
				}

				String contacts = ts.getContacts() == null ? "" : ts.getContacts();
				String phone = ts.getPhone() == null ? "" : ts.getPhone();
				String fax = ts.getFax() == null ? "" : ts.getFax();
				int status = ts.getStatus();
				int isschool = ts.getIsschool() ? 1 : 0;
//				int grade = -1;
//				if (ts.getGrade() != null) {
//					grade = ts.getGrade().getId();
//				}
//				String grade = "";
				int grade = -1;
				if(ts.getType() != null && !"".equals(ts.getType())){
					grade = Integer.parseInt(getTypeIdString(ts.getType()));
				}

				String otherscode = ts.getOtherscode() == null ? "" : ts.getOtherscode();//标识码
				
				String organizerName = ts.getOrganizerName() == null ? "" : ts.getOrganizerName();//举办者类型
				int organizer = 0;
				if(!"".equals(organizerName)){
					organizer = getorganizerNameId(organizerName);
				}
				
				String dictype = ts.getDictype() == null ? "" : ts.getDictype();//办别
				int dic = 0;
				if(!"".equals(dictype)){
					if("公办".equals(dictype)){
						dic = 1;
					}else if ("民办".equals(dictype)){
						dic = 2;
					}
				}
				
				/*
				 * 城乡类型：
					111主城区
					112城乡结合区
					121镇中心区
					122镇乡结合区
					123特殊区域
					210乡中心区
					220村庄
				 */
				String ftype = ts.getFtype() == null ? "" : ts.getFtype();//城乡分类
				int ftp = 0;
				if(!"".equals(ftype)){
					if("主城区".equals(ftype)){
						ftp = 1;
					} else if ("城乡结合区".equals(ftype)) {
						ftp = 2;
					} else if ("镇中心区".equals(ftype)) {
						ftp = 3;
					} else if ("镇乡结合区".equals(ftype)) {
						ftp = 4;
					} else if ("特殊区域".equals(ftype)) {
						ftp = 5;
					} else if ("乡中心区".equals(ftype)) {
						ftp = 6;
					} else if ("村庄".equals(ftype)) {
						ftp = 7;
					} else if ("乡村".equals(ftype)) {
						ftp = 8;
					}
//					if("城市".equals(ftype)){
//						ftp = 1;
//					}else if ("农村".equals(ftype)){
//						ftp = 2;
//					}
				}
				
				Short isPoor = ts.getIsPoor() == null ? 0 : ts.getIsPoor();//是否为集中连片特困地区县
				Short isCountryPoor = ts.getIsCountryPoor() == null ? 0 : ts.getIsCountryPoor();//是否为国家级贫困县
//				Short attribute = ts.getAttribute() == null ? 0 : ts.getAttribute();//学校地域属性1-城市 2-县城 3-镇区 4-乡 5-村 6-教学点 0-请选择
				

				String sr = "{\"id\":" + id + ",\"name\":\"" + name + "\",\"shortName\":\"" + shortName;
				sr += "\",\"organizationLevel\":" + organizlevel + ",\"area\":" + a1;
				sr += ",\"area1\":" + a2 + ",\"area2\":" + a3 + ",\"area3\":" + a4 + ",\"area4\":" + a5;
				sr += ",\"adress\":\"" + adress + "\",\"isschool\":" + isschool + ",\"grade\":" + grade;
				sr += ",\"otherscode\":\"" + otherscode + "\",\"organizerName\":" + organizer + ",\"dictype\":" + dic;
				sr += ",\"ftype\":" + ftp + ",\"isPoor\":" + isPoor + ",\"isCountryPoor\":" + isCountryPoor + ",\"isMerge\":" + isMerge + ",\"isTransfer\":" + isTransfer;
				sr += ",\"contacts\":\"" + contacts + "\",\"phone\":\"" + phone + "\",\"fax\":\"" + fax + "\",\"status\":" + status + "}";
				sb.append(sr);
				sb.append("]}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}else{
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
					int areaid3 = 0;
					int areaid4 = 0;
					String areacode = a.getParentcode();
					//获取学校地区改造（5级地区）
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
									areacode = pa.getParentcode();
									// 父父地区
									if (areacode != null && !areacode.equals("0") && !areacode.equals("")) {
										pa = this.iArea.getAreaByCode(areacode);
										if (pa != null) {
											areaid3 = pa.getId();
											areacode = pa.getParentcode();
											// 父父地区
											if (areacode != null && !areacode.equals("0") && !areacode.equals("")) {
												pa = this.iArea.getAreaByCode(areacode);
												if (pa != null) {
													areaid4 = pa.getId();
													
												}
											}
										}
									}
								}
							}
						}
					}
					int a1 = 0;
					int a2 = 0;
					int a3 = 0;
					int a4 = 0;
					int a5 = 0;
					if (areaid1 > 0) {
						if (areaid2 > 0) {
							if(areaid3 > 0){
								if(areaid4 > 0){
									a1 = areaid4;
									a2 = areaid3;
									a3 = areaid2;
									a4 = areaid1;
									a5 = areaid;
								}else{
									a1 = areaid3;
									a2 = areaid2;
									a3 = areaid1;
									a4 = areaid;
								}
							}else{
								a1 = areaid2;
								a2 = areaid1;
								a3 = areaid;
							}
						} else {
							a1 = areaid1;
							a2 = areaid;
						}
					} else {
						a1 = areaid;
					}

					String contacts = ts.getContacts() == null ? "" : ts.getContacts();
					String phone = ts.getPhone() == null ? "" : ts.getPhone();
					String fax = ts.getFax() == null ? "" : ts.getFax();
					int status = ts.getStatus();
					int isschool = ts.getIsschool() ? 1 : 0;
//					int grade = -1;
//					if (ts.getGrade() != null) {
//						grade = ts.getGrade().getId();
//					}
//					String grade = "";
					int grade = -1;
					if(ts.getType() != null && !"".equals(ts.getType())){
						grade = Integer.parseInt(getTypeIdString(ts.getType()));
					}

					String otherscode = ts.getOtherscode() == null ? "" : ts.getOtherscode();//标识码
					
					String organizerName = ts.getOrganizerName() == null ? "" : ts.getOrganizerName();//举办者类型
					int organizer = 0;
					if(!"".equals(organizerName)){
						organizer = getorganizerNameId(organizerName);
					}
					
					String dictype = ts.getDictype() == null ? "" : ts.getDictype();//办别
					int dic = 0;
					if(!"".equals(dictype)){
						if("公办".equals(dictype)){
							dic = 1;
						}else if ("民办".equals(dictype)){
							dic = 2;
						}
					}
					
					String ftype = ts.getFtype() == null ? "" : ts.getFtype();//城乡分类
					int ftp = 0;
					if(!"".equals(ftype)){
//						if("城市".equals(ftype)){
//							ftp = 1;
//						}else if ("农村".equals(ftype)){
//							ftp = 2;
//						}
						if("主城区".equals(ftype)){
							ftp = 1;
						} else if ("城乡结合区".equals(ftype)) {
							ftp = 2;
						} else if ("镇中心区".equals(ftype)) {
							ftp = 3;
						} else if ("镇乡结合区".equals(ftype)) {
							ftp = 4;
						} else if ("特殊区域".equals(ftype)) {
							ftp = 5;
						} else if ("乡中心区".equals(ftype)) {
							ftp = 6;
						} else if ("村庄".equals(ftype)) {
							ftp = 7;
						} else if ("乡村".equals(ftype)) {
							ftp = 8;
						}
					}
					
					Short isPoor = ts.getIsPoor() == null ? 0 : ts.getIsPoor();//是否为集中连片特困地区县
					Short isCountryPoor = ts.getIsCountryPoor() == null ? 0 : ts.getIsCountryPoor();//是否为国家级贫困县
//					Short attribute = ts.getAttribute() == null ? 0 : ts.getAttribute();//学校地域属性1-城市 2-县城 3-镇区 4-乡 5-村 6-教学点 0-请选择

					String sr = "{\"id\":" + id + ",\"name\":\"" + name + "\",\"shortName\":\"" + shortName;
					sr += "\",\"organizationLevel\":" + organizlevel + ",\"area\":" + a1;
					sr += ",\"area1\":" + a2 + ",\"area2\":" + a3 + ",\"area3\":" + a4 + ",\"area4\":" + a5;
					sr += ",\"adress\":\"" + adress + "\",\"isschool\":" + isschool + ",\"grade\":" + grade;
					sr += ",\"otherscode\":\"" + otherscode + "\",\"organizerName\":" + organizer + ",\"dictype\":" + dic;
					sr += ",\"ftype\":" + ftp + ",\"isPoor\":" + isPoor + ",\"isCountryPoor\":" + isCountryPoor + ",\"isMerge\":" + isMerge + ",\"isTransfer\":" + isTransfer;
					sr += ",\"contacts\":\"" + contacts + "\",\"phone\":\"" + phone + "\",\"fax\":\"" + fax + "\",\"status\":" + status + "}";
					sb.append(sr);
					sb.append(",");
				}

				if (li.size() > 0) {
					sb.delete(sb.length() - 1, sb.length());
				}
				sb.append("]}");

				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}

			
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
	 * 
	 */
	public void opOrganization() {

		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
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
					
					String areaid3 = request.getParameter("area3");
					String areaid4 = request.getParameter("area4");
					
					//新增字段获取
					String otherscode = request.getParameter("otherscode");
					
					String organizerName = request.getParameter("organizerName");
					String dictype = request.getParameter("dictype");
					String ftype = request.getParameter("ftype");
					String isPoor = request.getParameter("isPoor") == null ? "0" : request.getParameter("isPoor");
					if("".equals(isPoor)){
						isPoor = "0";
					}
					String isCountryPoor = request.getParameter("isCountryPoor") == null ? "0" : request.getParameter("isCountryPoor");
					if("".equals(isCountryPoor)){
						isCountryPoor = "0";
					}
					
//					String attribute = request.getParameter("attribute");
					if(isschool.equals("1")){
						
						if(organizerName == null || "".equals(organizerName) || "0".equals(organizerName)){
							sendResponse("ERROR", "请选择举办者类型");
							return;
						}
						
						
						if(dictype == null || "".equals(dictype) || "0".equals(dictype)){
							sendResponse("ERROR", "请选择办别");
							return;
						}
						
						
						if(ftype == null || "".equals(ftype) || "0".equals(ftype)){
							sendResponse("ERROR", "请选择城乡分类");
							return;
						}
						
//						if(attribute == null || "".equals(attribute) || "0".equals(attribute)){
//							sendResponse("ERROR", "请选择学校地域属性");
//							return;
//						}
					}
					
					if(otherscode != null && !"".equals(otherscode)){//如果不为空，判断唯一性
						String hql = "from Organization o where 1=1 and o.otherscode='"+otherscode+"'";
						List<Organization>lstt = this.iOrganization.getListByHSQL(hql);
						if(lstt != null && lstt.size() > 0){
							if (method.equals("add")) {//add
								sendResponse("ERROR", "单位标识码已存在");
								return;
							}else {//edit
								if(!id.equals(lstt.get(0).getId().toString())){
									sendResponse("ERROR", "单位标识码已存在");
									return;
								}
							}
						}
					}
					
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
					if(areaid4 != null && !areaid4.equals("0") && !areaid4.equals("")){
						tmpArea = areaid4;
					}else{
						if(areaid3 != null && !areaid3.equals("0") && !areaid3.equals("")){
							tmpArea = areaid3;
						}else{
							if (areaid2 != null && !areaid2.equals("0") && !areaid2.equals("")) {
								tmpArea = areaid2;
							} else {
								if (areaid1 != null && !areaid1.equals("0") && !areaid1.equals("")) {
									tmpArea = areaid1;
								}
							}
						}
					}
					

					if (method.equals("add")) {
						if(us.getRole() != 5){
							if(otherscode == null || "".equals(otherscode)){
								sendResponse("ERROR", "标识码为空");
								return;
							}
						}

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
//						UserSession us = (UserSession) session.getAttribute("usersession");
						ora.setCreator(us.getCreator());

						// 组织架级别
						OrganizationLevel oralevel = this.iOrganizationLevel.get(Short.parseShort(organizationLevel));
						ora.setOrganizationLevel(oralevel);

						// 地区
						Area are = this.iArea.get(Integer.parseInt(tmpArea));
						ora.setArea(are);

						// 学段
						if (ora.getIsschool()) {
//							Grade grade = this.iGrade.get(Short.parseShort(gradeid));
//							ora.setGrade(grade);
							ora.setType(getTypeString(gradeid));
						}
						// 上级组织架构
						if (pid != null && !pid.equals("0") && !pid.equals("")) {
							Organization parentOrag = this.iOrganization.get(Integer.parseInt(pid));
							if (parentOrag != null) {
								ora.setOrganization(parentOrag);
							}
						}
						ora.setCreattime(new Timestamp(date.getTime()));
						
						/*
						 * organizerName:2
							dictype:1 --办别
							ftype:1 --城乡分类
							isPoor:0 --贫困县
							isCountryPoor:0 --国贫
							attribute:0 --地域属性
						 */
						ora.setOtherscode(otherscode);
						ora.setOrganizerName(getorganizerName(Integer.parseInt(organizerName)));
						if("1".equals(dictype)){//公办
							ora.setDictype("公办");
						}else if("2".equals(dictype)){//民办
							ora.setDictype("民办");
						}
						
						/*
						 * 城乡分类
						 * 111主城区
							112城乡结合区
							121镇中心区
							122镇乡结合区
							123特殊区域
							210乡中心区
							220村庄
						 */
						if("1".equals(ftype)){//公办
							ora.setFtype("主城区");
						}else if("2".equals(ftype)){//民办
							ora.setFtype("城乡结合区");
						}else if("3".equals(ftype)){//民办
							ora.setFtype("镇中心区");
						}else if("4".equals(ftype)){//民办
							ora.setFtype("镇乡结合区");
						}else if("5".equals(ftype)){//民办
							ora.setFtype("特殊区域");
						}else if("6".equals(ftype)){//民办
							ora.setFtype("乡中心区");
						}else if("7".equals(ftype)){//民办
							ora.setFtype("村庄");
						} else if ("8".equals(ftype)) {
							ora.setFtype("乡村");
						}
						
						ora.setIsPoor(Short.parseShort(isPoor));
						ora.setIsCountryPoor(Short.parseShort(isCountryPoor));
						
//						ora.setAttribute(Short.parseShort(attribute));

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

							// 组织架级别***编辑不改动部门级别
//							OrganizationLevel oralevel = this.iOrganizationLevel.get(Short.parseShort(organizationLevel));
//							ora.setOrganizationLevel(oralevel);

							// 地区
							Area are = this.iArea.get(Integer.parseInt(tmpArea));
							ora.setArea(are);

							// 学段
							if (ora.getIsschool()) {
//								Grade grade = this.iGrade.get(Short.parseShort(gradeid));
//								ora.setGrade(grade);
								ora.setType(getTypeString(gradeid));
							}
							
//							if(us.getRole() == 5){
//								ora.setOtherscode(otherscode);
//							}
							ora.setOtherscode(otherscode);
							ora.setOrganizerName(getorganizerName(Integer.parseInt(organizerName)));
							if("1".equals(dictype)){//公办
								ora.setDictype("公办");
							}else if("2".equals(dictype)){//民办
								ora.setDictype("民办");
							}
							
							if("1".equals(ftype)){//公办
								ora.setFtype("主城区");
							}else if("2".equals(ftype)){//民办
								ora.setFtype("城乡结合区");
							}else if("3".equals(ftype)){//民办
								ora.setFtype("镇中心区");
							}else if("4".equals(ftype)){//民办
								ora.setFtype("镇乡结合区");
							}else if("5".equals(ftype)){//民办
								ora.setFtype("特殊区域");
							}else if("6".equals(ftype)){//民办
								ora.setFtype("乡中心区");
							}else if("7".equals(ftype)){//民办
								ora.setFtype("村庄");
							} else if ("8".equals(ftype)) {
								ora.setFtype("乡村");
							}
							ora.setIsPoor(Short.parseShort(isPoor));
							ora.setIsCountryPoor(Short.parseShort(isCountryPoor));
							
//							ora.setAttribute(Short.parseShort(attribute));

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
									oa.setStatus(DictionyMap.ORGANIZATION_STATUS_STOP);
									this.iOrganization.update(oa);
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
			ex.printStackTrace();
			sendResponse("ERROR", "操作失败");
		}

	}

	/**
	 * 获取type
	 * @param typeId
	 * @return
	 */
	public String getTypeString(String typeId){
		String type = "";
		
		switch (typeId) {
		case "0":
			type = "幼儿园";
			break;
		case "1":
			type = "小学";
			break;
		case "2":
			type = "小学教学点";
			break;
		case "3":
			type = "初级中学";
			break;
		case "4":
			type = "高级中学";
			break;
		case "5":
			type = "完全中学";
			break;
		case "6":
			type = "九年一贯制学校";
			break;
		case "7":
			type = "十二年一贯制学校";
			break;
		case "8":
			type = "中等师范学校";
			break;
		case "9":
			type = "职业高中学校";
			break;
		case "10":
			type = "工读学校";
			break;
		case "11":
			type = "盲人学校";
			break;
		case "12":
			type = "聋人学校";
			break;
		case "13":
			type = "弱智学校";
			break;
		case "14":
			type = "其他特教学校";
			break;
		case "15":
			type = "其他非教学机构";
			break;
		default:
			break;
		}
		
		return type;
	}
	
	/**
	 * 获取typeId
	 * @param typeId
	 * @return
	 */
	public String getTypeIdString(String type){
		String typeId = "";
		
		switch (type) {
		case "幼儿园":
			typeId = "0";
			break;
		case "小学":
			typeId = "1";
			break;
		case "小学教学点":
			typeId = "2";
			break;
		case "初级中学":
			typeId = "3";
			break;
		case "高级中学":
			typeId = "4";
			break;
		case "完全中学":
			typeId = "5";
			break;
		case "九年一贯制学校":
			typeId = "6";
			break;
		case "十二年一贯制学校":
			typeId = "7";
			break;
		case "中等师范学校":
			typeId = "8";
			break;
		case "职业高中学校":
			typeId = "9";
			break;
		case "工读学校":
			typeId = "10";
			break;
		case "盲人学校":
			typeId = "11";
			break;
		case "聋人学校":
			typeId = "12";
			break;
		case "弱智学校":
			typeId = "13";
			break;
		case "其他特教学校":
			typeId = "14";
			break;
		case "其他非教学机构":
			typeId = "15";
			break;
		default:
			break;
		}
		
		return typeId;
	}
	
	/**
	 * 获取举办者类型ID
	 * @param organizerName
	 * @return
	 */
	public int getorganizerNameId(String organizerName){
		int id = 0;
		switch (organizerName) {
		case "省级教育部门":
			id=1;
			break;
		case "地级教育部门":
			id=2;
			break;
		case "县级教育部门":
			id=3;
			break;
		case "省级其他部门":
			id=4;
			break;
		case "地级其他部门":
			id=5;
			break;
		case "县级其他部门":
			id=6;
			break;
		case "事业单位":
			id=7;
			break;
		case "部队":
			id=8;
			break;
		case "集体":
			id=9;
			break;
		case "民办":
			id=10;
			break;
		case "地方企业":
			id=11;
			break;

		default:
			break;
		}
		return id;
	}
	
	/**
	 * 获取举办者类型名称
	 * @param organizerName
	 * @return
	 */
	public String getorganizerName(int organizerNameId){
		String  organizerName = "";
		switch (organizerNameId) {
		case 1:
			organizerName="省级教育部门";
			break;
		case 2:
			organizerName="地级教育部门";
			break;
		case 3:
			organizerName="县级教育部门";
			break;
		case 4:
			organizerName="省级其他部门";
			break;
		case 5:
			organizerName="地级其他部门";
			break;
		case 6:
			organizerName="县级其他部门";
			break;
		case 7:
			organizerName="事业单位";
			break;
		case 8:
			organizerName="部队";
			break;
		case 9:
			organizerName="集体";
			break;
		case 10:
			organizerName="民办";
			break;
		case 11:
			organizerName="地方企业";
			break;

		default:
			break;
		}
		return organizerName;
	}
	
	/**
	 * 重置学校学段为type
	 */
	public void getGrade() {
		initServlert();
		
//		String getStuGradesHQL = " from Grade";
//		List<Grade> lstGrades = iGradeService.getListByHSQL(getStuGradesHQL);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\",");
		sb.append("\"Options\":");
		String ret = "[";
		String st = "";
		st += "{\"Value\":0,\"DisplayText\":\"幼儿园\"},"
				+ "{\"Value\":1,\"DisplayText\":\"小学\"},"
				+ "{\"Value\":2,\"DisplayText\":\"小学教学点\"},"
				+ "{\"Value\":3,\"DisplayText\":\"初级中学\"},"
				+ "{\"Value\":4,\"DisplayText\":\"高级中学\"},"
				+ "{\"Value\":5,\"DisplayText\":\"完全中学\"},"
				+ "{\"Value\":6,\"DisplayText\":\"九年一贯制学校\"},"
				+ "{\"Value\":7,\"DisplayText\":\"十二年一贯制学校\"},"
				+ "{\"Value\":8,\"DisplayText\":\"中等师范学校\"},"
				+ "{\"Value\":9,\"DisplayText\":\"职业高中学校\"},"
				+ "{\"Value\":10,\"DisplayText\":\"工读学校\"},"
				+ "{\"Value\":11,\"DisplayText\":\"盲人学校\"},"
				+ "{\"Value\":12,\"DisplayText\":\"聋人学校\"},"
				+ "{\"Value\":13,\"DisplayText\":\"弱智学校\"},"
				+ "{\"Value\":14,\"DisplayText\":\"其他特教学校\"},"
				+ "{\"Value\":15,\"DisplayText\":\"其他非教学机构\"}";
//		for (Grade g :lstGrades){
//			st += "{\"Value\":" + g.getId() + ",\"DisplayText\":\"" + g.getName() + "\"},";
//		}
//		if (st.length() > 0) {
//			st = st.substring(0, st.length() - 1);
//		}
		ret += st + "]";

		sb.append(ret);
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * 举办者类型
	 * { '0': '无', '1': '省级教育部门', '2' : '地级教育部门', '3': '县级教育部门', '4': '省级其他部门', '5': '地级其他部门', '6': '县级其他部门', '7': '事业单位', '8': '部队', '9': '集体', '10': '民办', '11': '地方企业' },
	 */
	public void getOrganizerName() {
		initServlert();
		
//		String getStuGradesHQL = " from Grade";
//		List<Grade> lstGrades = iGradeService.getListByHSQL(getStuGradesHQL);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\",");
		sb.append("\"Options\":");
		String ret = "[";
		String st = "";
		st += "{\"Value\":0,\"DisplayText\":\"无\"},"
				+ "{\"Value\":1,\"DisplayText\":\"省级教育部门\"},"
				+ "{\"Value\":2,\"DisplayText\":\"地级教育部门\"},"
				+ "{\"Value\":3,\"DisplayText\":\"县级教育部门\"},"
				+ "{\"Value\":4,\"DisplayText\":\"省级其他部门\"},"
				+ "{\"Value\":5,\"DisplayText\":\"地级其他部门\"},"
				+ "{\"Value\":6,\"DisplayText\":\"县级其他部门\"},"
				+ "{\"Value\":7,\"DisplayText\":\"事业单位\"},"
				+ "{\"Value\":8,\"DisplayText\":\"部队\"},"
				+ "{\"Value\":9,\"DisplayText\":\"集体\"},"
				+ "{\"Value\":10,\"DisplayText\":\"民办\"},"
				+ "{\"Value\":11,\"DisplayText\":\"地方企业\"}";
//		for (Grade g :lstGrades){
//			st += "{\"Value\":" + g.getId() + ",\"DisplayText\":\"" + g.getName() + "\"},";
//		}
//		if (st.length() > 0) {
//			st = st.substring(0, st.length() - 1);
//		}
		ret += st + "]";

		sb.append(ret);
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * 学校地域属性
	 * { '0': '无', '1': '城市', '2': '县城', '3': '镇区', '4': '乡', '5': '村', '6': '教学点' }
	 */
	public void getAttribute() {
		initServlert();
		
//		String getStuGradesHQL = " from Grade";
//		List<Grade> lstGrades = iGradeService.getListByHSQL(getStuGradesHQL);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\",");
		sb.append("\"Options\":");
		String ret = "[";
		String st = "";
		st += "{\"Value\":0,\"DisplayText\":\"无\"},"
				+ "{\"Value\":1,\"DisplayText\":\"城市\"},"
				+ "{\"Value\":2,\"DisplayText\":\"县城\"},"
				+ "{\"Value\":3,\"DisplayText\":\"镇区\"},"
				+ "{\"Value\":4,\"DisplayText\":\"乡\"},"
				+ "{\"Value\":5,\"DisplayText\":\"村\"},"
				+ "{\"Value\":6,\"DisplayText\":\"教学点\"}";
//		for (Grade g :lstGrades){
//			st += "{\"Value\":" + g.getId() + ",\"DisplayText\":\"" + g.getName() + "\"},";
//		}
//		if (st.length() > 0) {
//			st = st.substring(0, st.length() - 1);
//		}
		ret += st + "]";

		sb.append(ret);
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
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
	
	public String getNaviStringForAdmin(Organization oz,UserSession us) {
		if (oz.getOrganization() == null) {
			return "<a href=\"../admin/organization_initPageForAdmin.action?pid=" + oz.getId() + "\">" + oz.getName() + "</a>";
		} else {
			if(oz.getId() == us.getOrganization()){
				return "<a href=\"../admin/organization_initPageForAdmin.action?pid=" + oz.getId() + "\">" + oz.getName() + "</a>";
			}
			return getNaviStringForAdmin(oz.getOrganization(),us) + " &rsaquo; " + "<a href=\"../admin/organization_initPageForAdmin.action?pid=" + oz.getId() + "\">" + oz.getName() + "</a>";
		}
	}

	
	/**
	 * 初始化合并学校页面
	 * @return
	 */
	public String mergeInit(){
		initServlert();
		String id = request.getParameter("id") == null ? "0" : request.getParameter("id");
//		String pid = request.getParameter("pid") == null ? "0" : request.getParameter("pid");
		if("".equals(id)){
			id = "0";
		}
		if(!"0".equals(id)){
			this.orgn = this.iOrganization.get(Integer.parseInt(id));
		}
		
		return "mergeInit";
	}
	
	/**
	 * 合并学校调出教师
	 */
	public void merge(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		String id = request.getParameter("id") == null ? "0" : request.getParameter("id");
		if("".equals(id)){
			id = "0";
		}
		String organization = request.getParameter("organization") == null ? "0" : request.getParameter("organization");
		if("".equals(organization)){
			organization = "0";
		}
		if("0".equals(organization)){
			sendResponse("ERROR", "请选择并入学校");
			return;
		}
		Organization oa = this.iOrganization.get(Integer.parseInt(id));
		Organization org = this.iOrganization.get(Integer.parseInt(organization));
		if(oa != null && org != null){
			Set<Teacher> teacherSet = oa.getTeachers();
			if(teacherSet != null && teacherSet.size() > 0){//调出教师
				this.teacherAdjustService.saveMergeSchool(oa, org, us);
			}
			//停用机构
			oa.setStatus(DictionyMap.ORGANIZATION_STATUS_PAUSE);
			this.iOrganization.update(oa);
			sendResponse("OK", "合并成功，教师已调出");
			
		}else{
			sendResponse("ERROR", "机构不存在");
		}
		
	}
	
	public void createpinyin(){
		initServlert();
		List<Organization> list = this.iOrganization.findAll();
		int i = 0;
		int j = 0;
		for(Organization o : list ){
			String name = o.getName();
			if(name.indexOf("吐鲁番市") >=0){
				name=name.replace("吐鲁番市", "高昌区");
			}
			if(name.indexOf("哈密市") >=0){
				name=name.replace("哈密市", "伊州区");
			}
			String sname = o.getShortName();
			if(sname.indexOf("吐鲁番市") >=0){
				sname=sname.replace("吐鲁番市", "高昌区");
			}
			if(sname.indexOf("哈密市") >=0){
				sname=sname.replace("哈密市", "伊州区");
			}
			o.setName(name);
			o.setShortName(sname);
//			String name = o.getName();
//			if(name.indexOf("吐鲁番市") >=0){
//				name.replace("吐鲁番市", "高昌区");
//			}
			String pinyin = pinyingUtil.getFirstSpell(name);
			String pinying = o.getPinying() == null ? "" : o.getPinying();
			if(!pinyin.equals(pinying)){
				o.setPinying(pinyin);
				this.iOrganization.update(o);
				j++;
				System.out.println(j);
			}
			System.out.println(i);
			i++;
		}
	}
	public void createpinyin2(){
		initServlert();
		List<Organization> list = this.iOrganization.findAll();
		int i = 0;
		int j = 0;
		for(Organization o : list ){
			String name = o.getName();
			if(name.indexOf("吐鲁番地区") >=0){
				name=name.replace("吐鲁番地区", "吐鲁番市");
			}
			if(name.indexOf("哈密地区") >=0){
				name=name.replace("哈密地区", "哈密市");
			}
			String sname = o.getShortName();
			if(sname.indexOf("哈密地区") >=0){
				sname=sname.replace("哈密地区", "哈密市");
			}
			if(sname.indexOf("吐鲁番地区") >=0){
				sname=sname.replace("吐鲁番地区", "吐鲁番市");
			}
			o.setName(name);
			o.setShortName(sname);
//			String name = o.getName();
//			if(name.indexOf("吐鲁番市") >=0){
//				name.replace("吐鲁番市", "高昌区");
//			}
			String pinyin = pinyingUtil.getFirstSpell(name);
			String pinying = o.getPinying() == null ? "" : o.getPinying();
			if(!pinyin.equals(pinying)){
				o.setPinying(pinyin);
				this.iOrganization.update(o);
				j++;
				System.out.println(j);
			}
			System.out.println(i);
			i++;
		}
	}
	
	public void createscode(){
		initServlert();
		List<Organization> list = this.iOrganization.findAll();
		int i = 0;
		for(Organization o : list ){
//			String name = o.getName();
//			String pinyin = pinyingUtil.getFirstSpell(name);
//			String pinying = o.getPinying() == null ? "" : o.getPinying();
//			if(!pinyin.equals(pinying)){
//				o.setPinying(pinyin);
//				this.iOrganization.update(o);
//				j++;
//				System.out.println(j);
//			}
			String str = String.format("%010d", o.getId());
			if (o.getOrganization() != null) {
				str = o.getOrganization().getScode() + str;
			}
			o.setScode(str);
			this.iOrganization.update(o);
			System.out.println(i);
			i++;
		}
	}
	
	
	public String transferInit(){
		initServlert();
		String id = request.getParameter("id") == null ? "0" : request.getParameter("id");
//		String pid = request.getParameter("pid") == null ? "0" : request.getParameter("pid");
		if("".equals(id)){
			id = "0";
		}
		if(!"0".equals(id)){
			this.orgn = this.iOrganization.get(Integer.parseInt(id));
		}
		return "transferInit";
	}
	
	public void transfer(){
		initServlert();
//		UserSession us = (UserSession) session.getAttribute("usersession");
		String id = request.getParameter("id") == null ? "0" : request.getParameter("id");
		if("".equals(id)){
			id = "0";
		}
		
		String organization = request.getParameter("organization") == null ? "0" : request.getParameter("organization");
		if("".equals(organization)){
			organization = "0";
		}
		if("0".equals(organization)){
			sendResponse("ERROR", "请选择预迁入地区");
			return;
		}
		Organization oa = this.iOrganization.get(Integer.parseInt(id));
		Organization oldParent = oa.getOrganization();
		Organization org = this.iOrganization.get(Integer.parseInt(organization));
		if(oa != null && org != null){
			oa.setOrganization(org);
			//重新生成scode
			String str = String.format("%010d", oa.getId());
			if (oa.getOrganization() != null) {
				str = oa.getOrganization().getScode() + str;
			}
			oa.setScode(str);
			this.iOrganization.update(oa);
//			sendResponse("OK", "成功，学校已成功由"+oldParent.getName()+"迁入"+org.getName());
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"成功，学校已成功由"+oldParent.getName()+"迁入"+org.getName()+"\"");
			sb.append(",");
			sb.append("\"pid\":"+org.getId());
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			
		}else{
			sendResponse("ERROR", "机构不存在");
		}
	}
	
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
				Organization ora = this.iOrganization.get(orgainId);
				int flag = this.iOrganization.getOrgHasChild(orgainId);
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
			List li = this.iOrganization.getLevelOrganization(sid);
			for (int i = 0; i < li.size(); i++) {
				Object[] obj = (Object[]) li.get(i);
				int hid = Integer.parseInt(obj[0].toString());
				int flag = this.iOrganization.getOrgHasChild(hid);
				if(obj[3]!=null && !"".equals(obj[3].toString()) && Integer.parseInt(obj[3].toString())>=3){
					flag=0;
				}
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	
	public Organization getOrgn() {
		return orgn;
	}
	

	public void setOrgn(Organization orgn) {
		this.orgn = orgn;
	}

	public ITeacherService getiTeacherService() {
		return iTeacherService;
	}

	public void setiTeacherService(ITeacherService iTeacherService) {
		this.iTeacherService = iTeacherService;
	}

	public ITeacherAdjustService getTeacherAdjustService() {
		return teacherAdjustService;
	}

	public void setTeacherAdjustService(ITeacherAdjustService teacherAdjustService) {
		this.teacherAdjustService = teacherAdjustService;
	}

	
}
