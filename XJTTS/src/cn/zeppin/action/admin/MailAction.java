package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.MailConnection;
import cn.zeppin.entity.MailInformation;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectExpert;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.service.IMailConnectionService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectExpertService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.dataTimeConvertUtility;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 站内信功能
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
public class MailAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IMailConnectionService mailConnectionService;
	private IProjectAdminService iProjectAdminService;
	private ITrainingAdminService iTrainingAdmin;
	private IProjectExpertService iProjectExpertService;
	private IOrganizationService iOragnizationService;
	private ITrainingCollegeService iTrainingCollegeService;
	private IProjectService iProjectService;
	private List<Project> searchReportTask;// 教师-培训项目

	private Map<Integer, String[]> inboxMap;
	private Map<Integer, String[]> outboxMap;

	private int offset;// 起始页

	private void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * 收件箱列表
	 */
	public String getInboxList() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		Map<String, Object> params = new HashMap<String, Object>();
		inboxMap = new HashMap<Integer, String[]>();
		String[] str = null;

		// 排序(按发送时间)
		String sort = "mi.sendtime desc";
		// 状态 ： 全部、已读、未读
		String status = request.getParameter("status");
		if (status != null && status != "") {
			params.put("status", status);
		}
		params.put("paId", us.getId());// 收件人的id
		params.put("role", us.getRole());// 收件人的权限
		getQueryParameter(us, params);

		try {
			List list = this.mailConnectionService.getListByParams(params,
					offset, DictionyMap.maxPageSize, sort);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
					MailConnection mc = (MailConnection) obj[0];
					MailInformation mi = (MailInformation) obj[1];
					int id = mc.getId();
					if (!this.inboxMap.containsKey(id)) {
						str = new String[10];
						String creator = "";
						if (mi.getCreatorRole() == 1) {
							ProjectAdmin pa = this.iProjectAdminService.get(mi
									.getCreator());
							String creatorStr = pa.getOrganization().getName()
									+ "管理员--" + pa.getName();
							creator += creatorStr;
						} else {
							TrainingAdmin ta = this.iTrainingAdmin.get(mi
									.getCreator());
							String creatorStr = ta.getTrainingCollege()
									.getName() + "管理员--" + ta.getName();
							creator += creatorStr;
						}
						str[0] = creator;// 发件人
						str[1] = dataTimeConvertUtility.DateToString(
								mi.getSendtime(), "yyyy-MM-dd");// 发送时间
						str[2] = mi.getTitle();// 发送主题
						str[3] = mi.getText();// 发送内容
						str[4] = mc.getStatus() + "";// 1-未读 2-已读
						System.out.println("站内信： " + str.toString());
						this.inboxMap.put(id, str);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "inboxList";
	}

	/**
	 * 发件箱列表
	 */
	public String getOutBoxList() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		Map<String, Object> params = new HashMap<String, Object>();
		outboxMap = new HashMap<Integer, String[]>();
		String[] str = null;

		// 排序(按发送时间)
		String sort = "sendtime desc";
		params.put("creator", us.getId());// 发件人的id
		params.put("creatorRole", us.getRole());// 发件人的权限
		getQueryParameter(us, params);
		try {
			List list = this.mailConnectionService.getListByParams(params,
					offset, DictionyMap.maxPageSize, sort);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
					MailConnection mc = (MailConnection) obj[0];
					MailInformation mi = (MailInformation) obj[1];
					int id = mc.getId();
					if (!this.outboxMap.containsKey(id)) {
						str = new String[10];
						String creator = "";
						if (mc.getAddresseeRole() == 1) {
							ProjectAdmin pa = this.iProjectAdminService.get(mc
									.getAddressee());
							String creatorStr = pa.getOrganization().getName()
									+ "管理员--" + pa.getName();
							creator += creatorStr;
						} else {
							TrainingAdmin ta = this.iTrainingAdmin.get(mc
									.getAddressee());
							String creatorStr = ta.getTrainingCollege()
									.getName() + "管理员--" + ta.getName();
							creator += creatorStr;
						}
						str[0] = creator;// 收件人
						str[1] = dataTimeConvertUtility.DateToString(
								mi.getSendtime(), "yyyy-MM-dd");// 发送时间
						str[2] = mi.getTitle();// 发送主题
						str[3] = mi.getText();// 发送内容
						System.out.println("站内信发件箱： " + str.toString());
						this.outboxMap.put(id, str);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "outBoxList";
	}

	/**
	 * 获取下级管理员地区列表
	 */
	public void getNextOrganization() {
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession) session.getAttribute("usersession");
		String hql = " from Organization o where 1=1 and o.status=1 ";
		if (us.getOrganizationLevel() < 3) {
			hql += " and o.isschool=0 ";
		}
		hql += " and o.organization=" + us.getOrganization();
		List<Organization> lstOrg = this.iOragnizationService
				.getListByHSQL(hql);
		if (lstOrg != null && lstOrg.size() > 0) {
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Records\":[");
			for (Organization organization : lstOrg) {
				sb.append("{");
				sb.append("\"id\"" + organization.getId());
				sb.append(",");
				sb.append("\"name\":\"" + organization.getName() + "\"");
				sb.append("},");
				System.out.println("站内信： 管理员 ===== " + organization.getId()
						+ "-----------" + organization.getName());
			}

			sb.delete(sb.length() - 1, sb.length());
			sb.append("]}");
		} else {
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"暂无记录\"");
			sb.append("}");
		}
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	/**
	 * 获取搜索的承训单位信息
	 */
	public void searchTrainCollege() {
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();

		if (user != null) {
			String str = "";
			List<TrainingCollege> lstTc = new ArrayList<TrainingCollege>();
			// if (request.getParameterMap().containsKey("search")) {
			// str = request.getParameter("search");
			// String hqlString =
			// " from TrainingCollege tc where 1=1 and ( tc.name like '%"
			// + str + "%' or tc.shortName like '%" + str + "%' )";
			// lstTc = this.iTrainingCollegeService.getListForPage(hqlString,
			// 0, DictionyMap.maxPageSize);
			// }
			lstTc = this.iTrainingCollegeService
					.getTrainingCollegeListForRange();
			if (lstTc.size() > 0) {
				sb.append("{");
				sb.append("\"Result\":\"OK\",");
				sb.append("\"Options\":[");
				sb.append("{\"Value\":" + 0 + ",\"DisplayText\":\""
						+ "请选择...},");
				for (TrainingCollege tc : lstTc) {
					sb.append("{");
					sb.append("\"id\":" + tc.getId());
					sb.append(",");
					sb.append("\"DisplayText\":\"" + tc.getName() + "\"");
					sb.append("},");
					System.out.println("站内信： 承训单位管理员 ===== " + tc.getId()
							+ "-----------" + tc.getName());
				}
				sb.delete(sb.length() - 1, sb.length());
				sb.append("]}");
				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
			}
		}
	}

	/**
	 * 获取老师列表（根据项目名称、培训科目、承训单位）
	 */
	public void getTeacherInfo() {
		initServlert();
		List<ProjectType> lityps = (List<ProjectType>) session
				.getAttribute("lstProjectType");
		// 项目名称，培训科目和承训单位根据ttRecordAdu.jsp的 changeProject方法调用的接口
//		this.searchReportTask = this.iProjectService.getProjectByStatusAndType(
//				DictionyMap.releaseProject, lityps,
//				DictionyMap.PROJECT_ENROLL_TYPE_PLAN);

	}

	/**
	 * 获取评审专家的列表(未完成)
	 */
	public void getExpertManage() {
		List<ProjectExpert> list = this.iProjectExpertService
				.getProjectExpertList();
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Records\":[");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				ProjectExpert pa = (ProjectExpert) list.get(i);
				sb.append("{");
				sb.append("\"id\"" + pa.getId());
				sb.append(",");
				sb.append("\"name\":\"" + pa.getName() + "\"");
				sb.append("},");
			}

			sb.delete(sb.length() - 1, sb.length());
			sb.append("]}");
		} else {
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"暂无记录\"");
			sb.append("}");
		}
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	/**
	 * 发送站内信
	 */
	public void send() {
		initServlert();
		if(checkInput()){
			
		}
	}

	/**
	 * 检查输入内容:非空验证
	 * 
	 * @return false验证失败，true验证通过
	 */
	private boolean checkInput() {
		// 收件人
		String recipient = request.getParameter("recipient");
		if (recipient != null && recipient.equals("")) {
			sendResponse("ERROR", "请选择收件人");
			return false;
		}
		// 标题
		String title = request.getParameter("title");
		if (title != null && title.equals("")) {
			sendResponse("ERROR", "请填写站内信标题");
			return false;
		}
		// 内容
		String content = request.getParameter("content");
		if (content != null && content.equals("")) {
			sendResponse("ERROR", "请填写站内信内容");
			return false;
		}
		// 落款
		String inscription = request.getParameter("inscription");
		if (inscription != null && inscription.equals("")) {
			sendResponse("ERROR", "请填写站内信落款");
			return false;
		}
		return true;
	}

	/**
	 * 响应结果
	 * 
	 * @param status
	 * @param value
	 */
	public void sendResponse(String status, String value) {
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"" + status + "\"");
		checkSB.append(",");
		checkSB.append("\"Message\":\"" + value + "\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
	}

	/**
	 * 获取查询的参数
	 * 
	 * @param us
	 * @param params
	 */
	private void getQueryParameter(UserSession us, Map<String, Object> params) {
		// 搜索内容
		String content = request.getParameter("content");

		if (content != null && content != "") {
			params.put("content", content);
		}

		// 分页
		// 起始页
		String ist = (String) request.getParameter("page");

		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);
		offset = (start - 1) * DictionyMap.maxPageSize;
	}

	public IMailConnectionService getMailConnectionService() {
		return mailConnectionService;
	}

	public void setMailConnectionService(
			IMailConnectionService mailConnectionService) {
		this.mailConnectionService = mailConnectionService;
	}

	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	public void setiProjectAdminService(
			IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}

	public ITrainingAdminService getiTrainingAdmin() {
		return iTrainingAdmin;
	}

	public void setiTrainingAdmin(ITrainingAdminService iTrainingAdmin) {
		this.iTrainingAdmin = iTrainingAdmin;
	}

	public IProjectExpertService getiProjectExpertService() {
		return iProjectExpertService;
	}

	public void setiProjectExpertService(
			IProjectExpertService iProjectExpertService) {
		this.iProjectExpertService = iProjectExpertService;
	}

	public IOrganizationService getiOragnizationService() {
		return iOragnizationService;
	}

	public void setiOragnizationService(
			IOrganizationService iOragnizationService) {
		this.iOragnizationService = iOragnizationService;
	}

	public ITrainingCollegeService getiTrainingCollegeService() {
		return iTrainingCollegeService;
	}

	public void setiTrainingCollegeService(
			ITrainingCollegeService iTrainingCollegeService) {
		this.iTrainingCollegeService = iTrainingCollegeService;
	}

	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}

	public List<Project> getSearchReportTask() {
		return searchReportTask;
	}

	public void setSearchReportTask(List<Project> searchReportTask) {
		this.searchReportTask = searchReportTask;
	}

	public Map<Integer, String[]> getInboxMap() {
		return inboxMap;
	}

	public void setInboxMap(Map<Integer, String[]> inboxMap) {
		this.inboxMap = inboxMap;
	}

	public Map<Integer, String[]> getOutboxMap() {
		return outboxMap;
	}

	public void setOutboxMap(Map<Integer, String[]> outboxMap) {
		this.outboxMap = outboxMap;
	}

}
