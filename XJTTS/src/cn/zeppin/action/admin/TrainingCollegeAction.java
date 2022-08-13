package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectLevel;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IProjectLevelService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.MD5Util;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Administrator
 * @category 承训单位
 * 
 */
public class TrainingCollegeAction extends ActionSupport {

	/**
	 * 承訓單位action，添加删除编辑承训单位
	 */

	private static final long serialVersionUID = 1L;
	static Logger logger = LogManager.getLogger(TrainingCollegeAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private ITrainingCollegeService iTrainingCollegeService;
	private IAreaService iArea; // 地区信息
	private IProjectLevelService iProjectLevel; // 项目级别
	private ITrainingAdminService iTrainingAdmin;
	private IProjectService iProject;

	private String areaString;

	public TrainingCollegeAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * @category 导航初始化
	 * @return
	 */
	public String initPage() {

		if (areaString == null || areaString.length() == 0) {
			this.areaString = getAreaCodeStringMethod();
		}

		return "init";
	}

	/**
	 * @category 获取承训单位列表
	 * @param jtStartIndex
	 * @param jtPageSize
	 * @param jtSorting
	 */
	public void getTrainingCollegeList() {
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

			String type = (String) request.getParameter("type");
			type = type == null ? "all" : type;

			int start = Integer.parseInt(ist);
			int limit = DictionyMap.maxPageSize;
			limit = Integer.parseInt(ien);

			// 排序
			String sort = request.getParameter("jtSorting");

			String hql = " from TrainingCollege t where 1=1 ";

			if (!type.equals("all")) {
				hql += " and t.status=" + type;
			}

			// 以后其他搜索参数
			String q = request.getParameter("q");
			q = q == null ? "" : q;
			String stype = request.getParameter("stype");
			stype = stype == null ? "" : stype;

			if (q.length() > 0) {
				hql += " and t." + stype + " like '%" + q + "%' ";
			}

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

			List<Object> licount = this.iTrainingCollegeService.findByHSQL("select count(*) " + hql);

			if (licount != null && licount.size() > 0) {
				int records = Integer.parseInt(licount.get(0).toString());
				sb.append("\"TotalRecordCount\":" + records);
				sb.append(",");

			} else {
				sb.append("\"TotalRecordCount\":0");
				sb.append(",");
			}

			List<TrainingCollege> li = this.iTrainingCollegeService.getListForPage(hql, start, limit);

			sb.append("\"Records\":[");

			for (int i = 0; i < li.size(); i++) {
				TrainingCollege pa = li.get(i);
				int id = pa.getId();
				int area = pa.getArea().getId();
				short projectLevel = pa.getProjectLevel().getId();
				String name = pa.getName();
				String shortName = pa.getShortName();
				String adress = pa.getAdress();
				String contacts = pa.getContacts();
				String phone = pa.getPhone();
				String fax = pa.getFax();

				short status = pa.getStatus();
				
				String trainURL = pa.getTrainURL();

				String sr = "{\"id\":" + id + ",\"name\":\"" + name + "\",";
				sr += "\"shortName\":\"" + shortName + "\",";
				sr += "\"area\":" + area + ",";
				sr += "\"projectLevel\":" + projectLevel + ",";
				sr += "\"phone\":\"" + phone + "\",\"contacts\":\"" + contacts + "\",\"fax\":\"" + fax + "\",";
				sr += "\"status\":" + status + ",\"adress\":\"" + adress + "\",\"trainURL\":\""+trainURL+"\"";
				sr += "},";
				sb.append(sr);
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

	public void getTrainingCollegeListForRange(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		List<TrainingCollege> list = this.iTrainingCollegeService.getTrainingCollegeListForRange();
		for (TrainingCollege tc : list) {
			int tcid = tc.getId();
			String sr = "{\"id\":" + tcid + ",\"name\":\"" + tc.getName() + "\"},";
			sb.append(sr);
		}
		if (list.size() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * @author Administrator
	 * @category 诚寻单位的操作,要做手机、邮箱、身份证进行验证
	 * @param method
	 *            add,edit,delete
	 * 
	 */
	public void opTrainingCollege() {

		initServlert();

		try {

			String method = request.getParameter("method");

			if (method != null) {
				if (method.equals("add") || method.equals("edit")) {

					// 名称
					String name = request.getParameter("name");
					// 简称
					String shortName = request.getParameter("shortName");

					// 地区id
					String areaId = request.getParameter("area");

					// 可以承训项目级别
					String projectLevelId = request.getParameter("projectLevel");

					String phone = request.getParameter("phone");
					String contacts = request.getParameter("contacts");
					String fax = request.getParameter("fax");

					String status = request.getParameter("status");
					String adress = request.getParameter("adress");
					
					String trainURL = "";
					if(request.getParameter("trainURL") != null && !"".equals(request.getParameter("trainURL"))){
						trainURL = request.getParameter("trainURL");
					}

					// TODO 检测用户输入的信息是否合法 例如验证手机号码，验证姓名是否为空等等

					if (Utlity.checkStringNull(name)) {
						sendResponse("ERROR", "承训单位名称为空");
						return;
					}
					if (Utlity.checkStringNull(shortName)) {
						sendResponse("ERROR", "单位简称为空");
						return;
					}

					List<TrainingCollege> litcs = this.iTrainingCollegeService.findByName(name);

					// 不存在用户信息
					if (method.equals("add")) {

						if (litcs.size() > 0) {
							sendResponse("ERROR", "已经存在诚寻单位:" + name);
							return;
						}

						Date date = new Date(); // 当前时间
						TrainingCollege tc = new TrainingCollege();
						tc.setName(name);
						tc.setShortName(shortName);

						tc.setAdress(adress);
						tc.setContacts(contacts);
						tc.setPhone(phone);
						tc.setFax(fax);
						tc.setStatus(Short.parseShort(status));
						
						//生成登录令牌
						String md5Str = tc.getId()+"_"+tc.getName();
						String token = MD5Util.string2MD5(md5Str);
						tc.setToken(token);
						
						//录入远程培训链接地址
						tc.setTrainURL(trainURL);

						UserSession us = (UserSession) session.getAttribute("usersession");
						
						//创建id
						tc.setCreator(us.getId());

						// 项目级别
						ProjectLevel pl = this.iProjectLevel.get(Short.parseShort(projectLevelId));
						tc.setProjectLevel(pl);

						// 地区
						Area are = this.iArea.get(Integer.parseInt(areaId));
						tc.setArea(are);

						tc.setCreattime(new Timestamp(date.getTime()));

						this.iTrainingCollegeService.add(tc);

						// 添加成功
						StringBuilder sb = new StringBuilder();
						sb.append("{");
						sb.append("\"Result\":\"OK\"");
						sb.append(",");
						sb.append("\"Record\":");

						String sr = "{\"id\":" + tc.getId() + ",\"name\":\"" + name + "\",";
						sr += "\"shortName\":\"" + shortName + "\",";
						sr += "\"area\":" + areaId + ",";
						sr += "\"projectLevel\":" + projectLevelId + ",";
						sr += "\"phone\":\"" + phone + "\",\"contacts\":\"" + contacts + "\",\"fax\":\"" + fax + "\",";
						sr += "\"status\":" + status + ",\"adress\":\"" + adress + "\"";
						sr += "}";

						sb.append(sr);

						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);

					} else {
						// 编辑

						String id = request.getParameter("id"); // id 用于编辑
						int eid = Integer.parseInt(id);
						TrainingCollege tc = this.iTrainingCollegeService.get(eid);

						if (!tc.getName().equals(name)) {
							if (litcs.size() > 0) {
								sendResponse("ERROR", "已经存在承训单位:" + name);
								return;
							}
						}

						tc.setName(name);
						tc.setShortName(shortName);

						tc.setAdress(adress);
						tc.setContacts(contacts);
						tc.setPhone(phone);
						tc.setFax(fax);
						tc.setStatus(Short.parseShort(status));

						// 项目级别
						ProjectLevel pl = this.iProjectLevel.get(Short.parseShort(projectLevelId));
						tc.setProjectLevel(pl);

						// 地区
						Area are = this.iArea.get(Integer.parseInt(areaId));
						tc.setArea(are);

//						//重新生成登录令牌
//						String md5Str = tc.getId()+"_"+tc.getName();
//						String token = MD5Util.string2MD5(md5Str);
//						tc.setToken(token);
						
						//录入远程培训链接地址
						tc.setTrainURL(trainURL);
						
						// 跟新成功
						this.iTrainingCollegeService.update(tc);

						sendResponse("OK", "更新成功");

					}

				} else if (method.equals("delete")) {

					// 删除 承训单位
					// 只要承训单位id
					// 如果在此承训单位下存在 管理员账户 则不能删除，要 先删除管理员用户，在删除承训单位信息
					String id = request.getParameter("id");
					if (id != null) {
						int did = Integer.parseInt(id);

						TrainingCollege tc = this.iTrainingCollegeService.get(did);

						if (tc.getTrainingAdmins() != null && tc.getTrainingAdmins().size() > 0) {
							// 不能删除

							sendResponse("ERROR", "此承训单位下存在管理员用户，不能删除此承训单位");

						}else if(tc.getTeacherTrainingRecordses()!=null && tc.getTeacherTrainingRecordses().size()>0){
							sendResponse("ERROR", "此承训单位下存在学员培训记录，不能删除此承训单位");
						}
						else if(tc.getAssignTeacherTasks()!=null && tc.getAssignTeacherTasks().size()>0){
							sendResponse("ERROR", "此承训单位下存在学员分配名额任务，不能删除此承训单位");
						}
						else if(tc.getProjectApplies()!=null && tc.getProjectApplies().size()>0){
							sendResponse("ERROR", "此承训单位下存项目申报任务，不能删除此承训单位");
						}
						else {
							// 可以删除
							this.iTrainingCollegeService.delete(tc);

							sendResponse("OK", "删除成功");
						}

					} else {
						sendResponse("ERROR", "删除失败!不存在删除的承训单位");
					}

				} else {
					sendResponse("ERROR", "操作失败!不存在操作类型");
				}
			} else {
				// 如果没有method

				sendResponse("ERROR", "操作失败!不存在操作类型");
			}

		} catch (Exception ex) {
			logger.error(ex);
			sendResponse("ERROR", "操作失败!");
		}

	}

	public void getTrainingCollegeListByProject() {
		initServlert();

		try {

			String projectId = (String) request.getParameter("projectId");

			String type = (String) request.getParameter("type");
			type = type == null ? "all" : type;

			String hql = " from TrainingCollege t where 1=1 ";

			if (!type.equals("all")) {
				hql += " and t.status=" + type;
			}

//			if (projectId != null && !projectId.equals("") && !projectId.equals("0")) {
//				Project p = this.iProject.get(Integer.valueOf(projectId));
//				if (p != null) {
//					hql += " and t.projectLevel=" + p.getProjectType().getProjectLevel().getId();
//				}
//			}

			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");

			List<TrainingCollege> li = this.iTrainingCollegeService.getListByHSQL(hql);

			sb.append("\"Records\":[");

			for (int i = 0; i < li.size(); i++) {
				TrainingCollege pa = li.get(i);
				int id = pa.getId();
				int area = pa.getArea().getId();
				short projectLevel = pa.getProjectLevel().getId();
				String name = pa.getName();
				String shortName = pa.getShortName();
				String adress = pa.getAdress();
				String contacts = pa.getContacts();
				String phone = pa.getPhone();
				String fax = pa.getFax();

				short status = pa.getStatus();

				String sr = "{\"id\":" + id + ",\"name\":\"" + name + "\",";
				sr += "\"shortName\":\"" + shortName + "\",";
				sr += "\"area\":" + area + ",";
				sr += "\"projectLevel\":" + projectLevel + ",";
				sr += "\"phone\":\"" + phone + "\",\"contacts\":\"" + contacts + "\",\"fax\":\"" + fax + "\",";
				sr += "\"status\":" + status + ",\"adress\":\"" + adress + "\"";
				sr += "},";
				sb.append(sr);
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

	public String getAreaCodeStringMethod() {
		List<Area> li = this.iArea.getParentCodeArea("0");
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

	public void sendResponse(String status, String value) {
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"" + status + "\"");
		checkSB.append(",");
		checkSB.append("\"Message\":\"" + value + "\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
	}

	public IProjectService getiProject() {
		return iProject;
	}

	public void setiProject(IProjectService iProject) {
		this.iProject = iProject;
	}

	public ITrainingCollegeService getiTrainingCollegeService() {
		return iTrainingCollegeService;
	}

	public void setiTrainingCollegeService(ITrainingCollegeService iTrainingCollegeService) {
		this.iTrainingCollegeService = iTrainingCollegeService;
	}

	public IAreaService getiArea() {
		return iArea;
	}

	public void setiArea(IAreaService iArea) {
		this.iArea = iArea;
	}

	public IProjectLevelService getiProjectLevel() {
		return iProjectLevel;
	}

	public void setiProjectLevel(IProjectLevelService iProjectLevel) {
		this.iProjectLevel = iProjectLevel;
	}

	public ITrainingAdminService getiTrainingAdmin() {
		return iTrainingAdmin;
	}

	public void setiTrainingAdmin(ITrainingAdminService iTrainingAdmin) {
		this.iTrainingAdmin = iTrainingAdmin;
	}

	public String getAreaString() {
		return areaString;
	}

	public void setAreaString(String areaString) {
		this.areaString = areaString;
	}

}
