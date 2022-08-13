package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.ProjectExpert;
import cn.zeppin.service.IProjectExpertService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Administrator
 * @category 评审专家业务逻辑
 */
public class ProjectExpertManageAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(ProjectExpertManageAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IProjectExpertService iProjectExpertService;

	public ProjectExpertManageAction() {
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
		UserSession user = (UserSession) session.getAttribute("usersession");
		if(user.getRole()==4){
				return "expertinit";
			}else{
				return "init";
			}			
		}

	/**
	 * @category 获取评审专家列表
	 * @param jtStartIndex
	 * @param jtPageSize
	 * @param jtSorting
	 */
	@SuppressWarnings("rawtypes")
	public void getExpertList() {

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
			String searchName = request.getParameter("q");
			searchName = searchName == null ? "" : searchName;
			String searchType = request.getParameter("stype");
			searchType = searchType == null ? "" : searchType;

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
			if(user.getRole()==4){
				records=1;
				ProjectExpert ex=this.iProjectExpertService.get(user.getId());
				li.add(ex);
			}else{
				records=this.iProjectExpertService.getProjectExpertCount(searchName, searchType, sortname, sorttype);
				li=this.iProjectExpertService.getProjectExpert(searchName, searchType, sortname, sorttype, start, limit);
			}
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");

			boolean flag = false;
			sb.append("\"Records\":[");
			for (int i = 0; i < li.size(); i++) {
				ProjectExpert pa =(ProjectExpert) li.get(i);

				int id = pa.getId();
				flag = true;
				String name = pa.getName();
				String idcard = pa.getIdcard(); // 身份证
				String mobile = pa.getMobile();
				String email = pa.getEmail();
				short sex = pa.getSex() == null ? 0 : pa.getSex();

				String ethnicName = "无";
				if (pa.getEthnic() != null) {
					ethnicName = pa.getEthnic().getName();
				}
				// 工作单位
				String organization = pa.getOrganization();
				String department = pa.getDepartment(); // 所在部门


				short politicsId = 0; // 政治面貌
				if (pa.getPolitics() != null) {
					politicsId = pa.getPolitics().getId();
				}


				String phone = pa.getPhone();
				String fax = pa.getFax();
				String jobDuty = pa.getJobDuty(); // 职务
				
				String jobTitle=pa.getJobTitle();
				String research=pa.getResearch();
				String resume=pa.getResume();
				String teach=pa.getTeach();
				String achievement=pa.getAchievement();
				String experience=pa.getExperience();
				String eduction=pa.getEductionBackground().getName();
				int areaid=0;
				if(pa.getArea()!=null){
					areaid = pa.getArea().getId(); // 地区
				}
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
				sbstr.append("\"organization\":\"" + organization + "\"");
				sbstr.append(",");
				sbstr.append("\"department\":\"" + department + "\"");
				sbstr.append(",");
				sbstr.append("\"politics\":\"" + politicsId+ "\"");
				sbstr.append(",");
				sbstr.append("\"jobTitle\":\"" + jobTitle+ "\"");
				sbstr.append(",");
				sbstr.append("\"research\":\"" + research+ "\"");
				sbstr.append(",");
				sbstr.append("\"resume\":\"" + resume+ "\"");
				sbstr.append(",");
				sbstr.append("\"teach\":\"" + teach+ "\"");
				sbstr.append(",");
				sbstr.append("\"achievement\":\"" + achievement+ "\"");
				sbstr.append(",");
				sbstr.append("\"experience\":\"" + experience+ "\"");
				sbstr.append(",");
				sbstr.append("\"eduction\":\"" + eduction+ "\"");
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
	 * @category 评审专家操作
	 * @param method
	 *            add,edit,delete 只有删除功能 添加，编辑 是另外流程编辑
	 */
	public void opProjectExpert() {

		try {
			initServlert();

			String method = request.getParameter("method");
			if (method.equals("delete")) {
				// 删除
				String id = request.getParameter("id");
				if (id != null && !id.equals("")) {
					ProjectExpert pa = this.iProjectExpertService.get(Integer.parseInt(id));
					if (pa != null) {

						try {

							pa.setStatus((short) 2);
							this.iProjectExpertService.update(pa);
							sendResponse("OK", "停用成功");

						} catch (Exception ex) {
							sendResponse("ERROR", "删除失败,存在项目权限");
						}

					} else {
						sendResponse("ERROR", "删除失败，不存在该评审专家");
					}
				} else {
					sendResponse("ERROR", "删除失败，不存在评审专家");
				}

			} else {
				sendResponse("ERROR", "操作失败，不存在操作类型");
			}
		} catch (Exception ex) {
			logger.error(ex);
			sendResponse("ERROR", "操作失败");
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


	// 属性

	public IProjectExpertService getiProjectExpertService() {
		return iProjectExpertService;
	}

	public void setiProjectExpertService(IProjectExpertService iProjectExpertService) {
		this.iProjectExpertService = iProjectExpertService;
	}


}
