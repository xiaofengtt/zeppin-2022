package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.ServiceApply;
import cn.zeppin.entity.ServiceApplyReply;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IServiceApplyReplyService;
import cn.zeppin.service.IServiceApplyService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.dataTimeConvertUtility;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 需要处理的下级申请
 */
public class ServiceApplyInfoAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IServiceApplyService iServiceApplyService;
	private IProjectAdminService iProjectAdminService;
//	private IServiceApplyReplyService iServiceApplyReplyService;

	private LinkedHashMap<Integer, String[]> serviceApplyHash;
	private String applyType;// 申请类型
	private String applyContent;// 申请内容
	private static String applyId;
	private static String selectType;

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * 获取“下级申请列表” 只有顶级的项目管理员可以查看所有，其他的项目管理员，只能看到自己的，剩下的都没有这个权限
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String init() {
		String[] str = null;
		ProjectAdmin creator;
		String replyText;
		Timestamp checktime;
		ProjectAdmin checker;

		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		int organizationLevel = us.getOrganizationLevel();

		// 起始页
		String ist = (String) request.getParameter("page");

		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);
		// 根据审核状态筛选
		String status = request.getParameter("status");
		// 根据申请类型筛选
		String type = request.getParameter("type");
		selectType = type;
		Map<String, Object> params = new HashMap<>();
		params.put("status", status);
		params.put("type", type);
		int offset = (start - 1) * DictionyMap.maxPageSize;
		// 判断权限
		if (organizationLevel != 1) {
			params.put("creator", us.getId());
		}
		List<ServiceApply> list = iServiceApplyService.getListByParams(params,
				offset, DictionyMap.maxPageSize);

		this.serviceApplyHash = new LinkedHashMap<Integer, String[]>();

		if (list != null && list.size() > 0) {
			for (ServiceApply serviceApply : list) {
				int id = serviceApply.getId();
				if (!this.serviceApplyHash.containsKey(id)) {
					str = new String[10];
					creator = serviceApply.getCreator();
					// 申请人
					str[0] = creator.getOrganization().getName()
							+ creator.getName();
					// 申请类型
					switch (serviceApply.getType()) {
					case 0:
						str[1] = "添加承训单位申请";
						str[9] = "0";// 申请类型对应的select id;
						break;
					case 1:
						str[1] = "添加培训学科申请";
						str[9] = "1";// 申请类型对应的select id;
						break;
					case 2:
						str[1] = "其他申请";
						str[9] = "2";// 申请类型对应的select id;
						break;
					}
					// 申请内容
					str[2] = serviceApply.getContent();
					// 申请时间 格式： xx-xx-xx xx:xx:xx
					str[3] = Utlity.timeSpanToString(serviceApply
							.getCreatetime());
					// 审核状态
					switch (serviceApply.getStatus()) {
					case 0:
						str[4] = "未回复";
						break;
					case 1:
						str[4] = "已回复";
						break;
					}
					// 回复内容
					replyText = serviceApply.getReplyText();
					str[5] = replyText == null ? "无" : replyText;
					// 回复时间
					// 回复时间 格式： xx-xx-xx xx:xx:xx
					checktime = serviceApply.getChecktime();
					if (checktime == null) {
						str[6] = "无";
					} else {
						str[6] = Utlity.timeSpanToString(checktime);
					}
					// 回复人
					checker = serviceApply.getChecker();
					if (checker == null) {
						str[7] = "无";
					} else {
						str[7] = checker.getOrganization().getName()
								+ checker.getName();
					}
					str[8] = organizationLevel + "";// 顶级的项目管理员有回复、编辑功能，其他的人都没有这个权限
					this.serviceApplyHash.put(id, str);
				}
			}
		} else {
			sendResponse("NONE", "无数据");
		}
		return "init";
	}

	/**
	 * 删除下级申请
	 */
	public void deleteServiceApply() {
		initServlert();
		String applyId = this.request.getParameter("id");
		if (applyId != null && !applyId.equals("")) {
			ServiceApply sa = this.iServiceApplyService.get(Integer
					.valueOf(applyId));
			if (sa != null) {
				try {
					sa.setStatus((short) -1);
					this.iServiceApplyService.updateServiceApply(sa);
					sendResponse("OK", "删除成功");

				} catch (Exception ex) {
					sendResponse("ERROR", "删除失败");
				}
			} else {
				sendResponse("ERROR", "删除失败，不存该申请记录");
			}

		} else {
			sendResponse("ERROR", "删除失败，不存该申请记录");
		}
	}

	/**
	 * 初始化添加和编辑操作
	 */
	public String addServiceApplyInit() {
		initServlert();
		// 通过传递的url获取id
		applyId = request.getParameter("id");
		if (applyId != null) {
			// 编辑
			Map<String, Object> params = new HashMap<>();
			params.put("id", applyId);
			// 通过id获取数据
			ServiceApply data = iServiceApplyService.getListByParams(params, 0,
					-1).get(0);
			applyContent = data.getContent();
			applyType = data.getType() + "";
		} else {
			// 添加
			applyContent = "";
			applyType = "0";
		}
		return "addInit";
	}

	/**
	 * 往数据库添加数据
	 */
	public void addServiceApply() {
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		Map<String, String[]> parMap = request.getParameterMap();
		String type = parMap.get("applyType")[0];// 申请类型
		String content = parMap.get("applyContent")[0];// 提交内容
		// 内容为空
		if (content == null || content.equals("")) {
			sendResponse("ERROR", "请填写申请内容(或理由)");
			return;
		}
		ServiceApply serviceApply = new ServiceApply();
		serviceApply.setContent(content);// 申请内容
		serviceApply.setCreatetime(dataTimeConvertUtility.getCurrentTime(""));// 申请时间
		serviceApply.setCreator(iProjectAdminService.get(user.getId()));// 申请人id
		serviceApply.setCretorType(user.getRole());// 申请人类型1-管理员 2-承训单位
		serviceApply.setStatus((short) 0);// 处理状态:未处理
		serviceApply.setType(Short.parseShort(type));// 申请类型
		if (applyId == null || applyId.equals("0") || applyId.equals("")) {
			// 添加
			iServiceApplyService.addServiceApply(serviceApply);
			session.removeAttribute("uploadfile");
			sendResponse("OK", "添加成功");
		} else {
			// 更新
			serviceApply.setId(Integer.parseInt(applyId));
			iServiceApplyService.updateServiceApply(serviceApply);
			session.removeAttribute("uploadfile");
			sendResponse("OK", "编辑成功");
		}
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
	 * 分页
	 */
	@SuppressWarnings("unchecked")
	public void getPageJson() {
		initServlert();

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);

		// 搜索参数
		// 根据审核状态筛选
		String status = request.getParameter("status");
		// 根据申请类型筛选
		String type = request.getParameter("type");
		Map<String, Object> params = new HashMap<>();
		params.put("status", status);
		params.put("type", type);
		int records = this.iServiceApplyService.getCountByParams(params);
		if (records != 0) {
			int total = (int) Math.ceil((float) records
					/ DictionyMap.maxPageSize);

			StringBuilder sb = new StringBuilder();
			sb.append("{\"currentPage\":" + (start) + ",");
			sb.append("\"totalPage\":" + total);
			sb.append("}");

			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}

	/**
	 * 保存回复内容 9月13号修改“下级申请”回复：信息保存到“service_apply_reply”表中
	 * 
	 * @return
	 */
	public void addReplyServiceApply() {
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		Map<String, String[]> parMap = request.getParameterMap();
		String content = parMap.get("replyContent")[0];// 提交内容
		// 内容为空
		if (content == null || content.equals("")) {
			sendResponse("ERROR", "请填写回复内容");
			return;
		}
//		List<ServiceApplyReply> replyList = iServiceApplyReplyService
//				.getReplyListByServiceApplyID(applyId, 0, -1);

		Map<String, Object> params = new HashMap<>();
		params.put("id", applyId);
		List<ServiceApply> list = iServiceApplyService.getListByParams(params,
				0, -1);
		ServiceApply serviceApply = list.get(0);
		serviceApply.setReplyText(content);// 回复内容
		serviceApply.setChecktime(dataTimeConvertUtility.getCurrentTime(""));// 回复时间
		serviceApply.setChecker(iProjectAdminService.get(user.getId()));// 回复人
		serviceApply.setStatus((short) 1);// 已回复

		if (!(applyId == null || applyId.equals("0") || applyId.equals(""))) {
			// 更新
			serviceApply.setId(Integer.parseInt(applyId));
			iServiceApplyService.updateServiceApply(serviceApply);
			session.removeAttribute("uploadfile");
			sendResponse("OK", "回复成功");
		} else {
			sendResponse("ERROR", "操作失败");
		}
	}

	public IServiceApplyService getiServiceApplyService() {
		return iServiceApplyService;
	}

	public void setiServiceApplyService(
			IServiceApplyService iServiceApplyService) {
		this.iServiceApplyService = iServiceApplyService;
	}

	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	public void setiProjectAdminService(
			IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}

	public LinkedHashMap<Integer, String[]> getProjectApplyHash() {
		return serviceApplyHash;
	}

	public void setProjectApplyHash(
			LinkedHashMap<Integer, String[]> serviceApplyHash) {
		this.serviceApplyHash = serviceApplyHash;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getApplyContent() {
		return applyContent;
	}

	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

}
