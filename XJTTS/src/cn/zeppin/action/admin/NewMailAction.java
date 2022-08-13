package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Document;
import cn.zeppin.entity.MailAttachment;
import cn.zeppin.entity.MailConnection;
import cn.zeppin.entity.MailInformation;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectExpert;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.ServiceApplyReply;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.fileInfo;
import cn.zeppin.service.IDocumentService;
import cn.zeppin.service.IMailAttachmentService;
import cn.zeppin.service.IMailConnectionService;
import cn.zeppin.service.IMailInformationService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectExpertService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IServiceApplyReplyService;
import cn.zeppin.service.ISysUserService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.dataTimeConvertUtility;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 站内信 1. 只有项目管理员和承训单位管理员有发送的权限，有“收件箱”和“发件箱”功能； 老师和评审专家只有查看“收件箱”的功能； 2.
 * 目前不做短信功能，所以type只有“站内信”值为1 3. 目前不做定时发送功能，所以sendstatus只有“非定时”，值为2
 */
public class NewMailAction extends ActionSupport {
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
	
	private IMailInformationService mailInformationService;
	private IServiceApplyReplyService serviceApplyReplyService;
	private IMailAttachmentService mailAttachmentService;
	private IDocumentService iDocumentService;
	private ITeacherService iTeacherService;// 教师操作
	private ITeacherTrainingRecordsService iTeacherTrainingRecords;
	
	private ISysUserService iSysUserService;//联系人操作
	
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
	 * 标题、内容、落款、时间、发送人
	 */
	@SuppressWarnings("rawtypes")
	public void getInboxList() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		Map<String, Object> params = new HashMap<String, Object>();
//		inboxMap = new HashMap<Integer, String[]>();
//		String[] str = null;

		// 排序(按发送时间)
		String sort = "sendtime desc";
		// 状态 ： 全部、已读、未读
		String status = request.getParameter("status") == null ? "0" : request.getParameter("status");;
		if("".equals(status)){
			status = "0";
		}
		if (!"0".equals(status)) {
			params.put("status", status);
		}
		params.put("paId", us.getId());// 收件人的id
		params.put("role", us.getRole());// 收件人的权限
		getQueryParameter(us, params);

		try {
			List list = this.mailConnectionService.getListByParams(params,
					offset, DictionyMap.maxPageSize, sort);
			int count = this.mailConnectionService.getListCountByParams(params);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"TotalCount\":"+count);
			sb.append(",");
			sb.append("\"Role\":"+us.getRole());
			sb.append(",");
			sb.append("\"Records\":[");
			
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
					MailConnection mc = (MailConnection) obj[0];
					MailInformation mi = (MailInformation) obj[1];
					//标题、内容、落款、时间、发送人
					int id = mc.getId();//信息ID
					int mid = mi.getId();
					short type = mi.getType();//信息类型
					String title = mi.getTitle();
					String content = mi.getText();
					String inscription = mi.getInscription() == null ? "":mi.getInscription();
					String creator = "";
					String organization = "";
					if(mi.getCreatorRole() == 1){
						ProjectAdmin pa = this.iProjectAdminService.get(mi.getCreator());
						creator = pa.getName();
						organization = pa.getOrganization().getName();
					} else {
						TrainingAdmin ta = this.iTrainingAdmin.get(mi.getCreator());
						creator = ta.getName();
						organization = ta.getTrainingCollege().getName();
					}
					String createtime = Utlity.timeSpanToString(mi.getSendtime());
					short statuss = mc.getStatus();
					sb.append("{");
					sb.append("\"id\":"+id);
					sb.append(",");
					sb.append("\"mid\":"+mid);
					sb.append(",");
					sb.append("\"type\":"+type);
					sb.append(",");
					sb.append("\"status\":"+statuss);
					sb.append(",");
					sb.append("\"title\":\""+title+"\"");
					sb.append(",");
					sb.append("\"content\":\""+content+"\"");
					sb.append(",");
					sb.append("\"inscription\":\""+inscription+"\"");
					sb.append(",");
					sb.append("\"creator\":\""+creator+"\"");
					sb.append(",");
					sb.append("\"organization\":\""+organization+"\"");
					sb.append(",");
					sb.append("\"createtime\":\""+createtime+"\"");
					sb.append(",");
					Set<MailAttachment> maSet = mi.getMailAttachment();
					sb.append("\"attachment\":[");
					if(maSet != null && maSet.size() > 0){
						for(MailAttachment ma : maSet){
							Document doc = ma.getDocument();
							sb.append("{");
							sb.append("\"id\":"+ma.getId());
							sb.append(",");
							sb.append("\"name\":\""+doc.getTitle()+"\"");
							sb.append(",");
							sb.append("\"path\":\""+doc.getResourcePath()+"\"");
							sb.append("},");
						}
						sb.delete(sb.length() - 1, sb.length());
					}
					sb.append("]");
					sb.append("},");
				}
				sb.delete(sb.length() - 1 , sb.length());
			}
			sb.append("]");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} catch (Exception e) {
			e.printStackTrace();
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"获取信息失败\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
		}
//		return "inboxList";
	}

	/**
	 * 发件箱列表
	 */
	public void getOutBoxList() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		Map<String, Object> params = new HashMap<String, Object>();
//		outboxMap = new HashMap<Integer, String[]>();
//		String[] str = null;

		// 排序(按发送时间)
		String sort = "sendtime desc";
		
		String status = request.getParameter("status") == null ? "0" : request.getParameter("status");;
		if("".equals(status)){
			status = "0";
		}
		if (!"0".equals(status)) {
			params.put("status", status);
		}
		
		params.put("paId", us.getId());// 发件人的id
		params.put("role", us.getRole());// 发件人的权限
		getQueryParameter(us, params);
		try {
			List<MailInformation> list = this.mailInformationService.getListByParams(params,
					offset, DictionyMap.maxPageSize, sort);
			int count = this.mailInformationService.getListCountByParams(params);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"TotalCount\":"+count);
			sb.append(",");
			sb.append("\"Role\":"+us.getRole());
			sb.append(",");
			sb.append("\"Records\":[");
			if (list != null && list.size() > 0) {
				for (MailInformation mi : list) {
//					Object[] obj = (Object[]) list.get(i);
//					MailConnection mc = (MailConnection) obj[0];
//					MailInformation mi = (MailInformation) obj[1];
					int id = mi.getId();
					short type = mi.getType();//信息类型
					String title = mi.getTitle();
					String content = mi.getText();
					String inscription = mi.getInscription() == null ? "":mi.getInscription();
					String creator = "";
					String organization = "";
					if(mi.getCreatorRole() == 1){
						ProjectAdmin pa = this.iProjectAdminService.get(mi.getCreator());
						creator = pa.getName();
						organization = pa.getOrganization().getName();
					} else {
						TrainingAdmin ta = this.iTrainingAdmin.get(mi.getCreator());
						creator = ta.getName();
						organization = ta.getTrainingCollege().getName();
					}
					String createtime = Utlity.timeSpanToString(mi.getSendtime());
					sb.append("{");
					sb.append("\"id\":"+id);
					sb.append(",");
					sb.append("\"type\":"+type);
					sb.append(",");
					sb.append("\"title\":\""+title+"\"");
					sb.append(",");
					sb.append("\"content\":\""+content+"\"");
					sb.append(",");
					sb.append("\"inscription\":\""+inscription+"\"");
					sb.append(",");
					sb.append("\"creator\":\""+creator+"\"");
					sb.append(",");
					sb.append("\"organization\":\""+organization+"\"");
					sb.append(",");
					sb.append("\"createtime\":\""+createtime+"\"");
					Set<MailAttachment> maSet = mi.getMailAttachment();
					sb.append(",");
					sb.append("\"attachment\":[");
					if(maSet != null && maSet.size() > 0){
						for(MailAttachment ma : maSet){
							Document doc = ma.getDocument();
							sb.append("{");
							sb.append("\"id\":"+ma.getId());
							sb.append(",");
							sb.append("\"name\":\""+doc.getTitle()+"\"");
							sb.append(",");
							sb.append("\"path\":\""+doc.getResourcePath()+"\"");
							sb.append("},");
						}
						sb.delete(sb.length() - 1, sb.length());
					}
					sb.append("]");
					sb.append("},");
				}
				sb.delete(sb.length() - 1 , sb.length());
			}
			sb.append("]");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} catch (Exception e) {
			e.printStackTrace();
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"获取信息失败\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
		}
	}
	
	/**
	 * 获取信息详情
	 */
	public void getInfo(){
		initServlert();
//		UserSession us = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();
		String id = request.getParameter("id") == null ?"":request.getParameter("id");
		if("".equals(id)){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"参数异常\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		
		MailInformation mi = this.mailInformationService.get(Integer.parseInt(id));
		if(mi == null){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"查询信息不存在\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Records\":{");
		sb.append("\"id\":"+mi.getId());
		
		String content = mi.getText();
		String title = mi.getTitle();
		short type = mi.getType();
		String inscription = mi.getInscription();
		String createtime = Utlity.timeSpanToString(mi.getCreattime());
		
		sb.append(",");
		sb.append("\"title\":\""+title+"\"");
		sb.append(",");
		sb.append("\"content\":\""+content+"\"");
		sb.append(",");
		sb.append("\"inscription\":\""+inscription+"\"");
		sb.append(",");
		sb.append("\"createtime\":\""+createtime+"\"");
		sb.append(",");
		sb.append("\"type\":"+type);
		sb.append("}");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * 获取回复列表
	 * status 1正常 0删除
	 */
	public void getReplaList(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();
		String id = request.getParameter("id") == null ?"":request.getParameter("id");
		if("".equals(id)){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"参数异常\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		String status = request.getParameter("status") == null ? "0" : request.getParameter("status");;
		if("".equals(status)){
			status = "0";
		}
		// 分页
		// 起始页
		String ist = (String) request.getParameter("page");

		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);
		offset = (start - 1) * 5;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mid", id);
		if (!"0".equals(status)) {
			params.put("status", status);
		}
		List<ServiceApplyReply> sarlist = this.serviceApplyReplyService.getReplyListByServiceApplyID(params, offset, 5);
		int count = this.serviceApplyReplyService.getReplyCountByServiceApplyID(params);
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"TotalCount\":"+count);
		sb.append(",");
		sb.append("\"Records\":[");
		if(sarlist != null && sarlist.size() > 0){
			for(ServiceApplyReply sar : sarlist){
				int ids = sar.getId();
				String content = sar.getCountent();
				String createtime = Utlity.timeSpanToString(sar.getCreatetime());
				short role = sar.getCreatorRole();
				int creator = sar.getCreator();
				boolean flag = false;
				if(us.getRole() == role && us.getId() == creator){
					flag = true;
				}
				String creatorr = "";
				String organization = "";
				if(sar.getCreatorRole() == 1){
					ProjectAdmin pa = this.iProjectAdminService.get(sar.getCreator());
					creatorr = pa.getName();
					organization = pa.getOrganization().getName();
				} else {
					TrainingAdmin ta = this.iTrainingAdmin.get(sar.getCreator());
					creatorr = ta.getName();
					organization = ta.getTrainingCollege().getName();
				}
				sb.append("{");
				sb.append("\"id\":"+ids);
				sb.append(",");
				sb.append("\"content\":\""+content+"\"");
				sb.append(",");
				sb.append("\"creator\":\""+creatorr+"\"");
				sb.append(",");
				sb.append("\"organization\":\""+organization+"\"");
				sb.append(",");
				sb.append("\"createtime\":\""+createtime+"\"");
				sb.append(",");
				sb.append("\"isadd\":"+flag);
				sb.append("},");
			}
			sb.delete(sb.length() - 1 , sb.length());
		}
		
		sb.append("]");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	
	/**
	 * 添加回复
	 */
	public void addReplay(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();
		String id = request.getParameter("id") == null ?"":request.getParameter("id");
		if("".equals(id)){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"参数异常\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		
		String content = request.getParameter("content") == null ? "" : request.getParameter("content");
		MailInformation mi = this.mailInformationService.get(Integer.parseInt(id));
		if(mi == null){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"无法回复不存在的信息\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		
		ServiceApplyReply sar = new ServiceApplyReply();
		sar.setCountent(content);
		sar.setServiceApply(mi);
		sar.setCreatetime(new Timestamp(System.currentTimeMillis()));
		sar.setCreator(us.getId());
		sar.setCreatorRole(us.getRole());
		sar.setStatus((short)1);
		
		try {
			this.serviceApplyReplyService.addServiceApply(sar);
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"操作成功\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"操作异常\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
	}
	
	/**
	 * 删除
	 * type=1收件 2发件 3回复
	 */
	public void delete(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();
		String type = request.getParameter("type") == null ?"":request.getParameter("type");
		if("".equals(type)){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"参数异常\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		
		String id = request.getParameter("id") == null ?"":request.getParameter("id");
		if("".equals(id)){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"参数异常\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		
		String result = this.mailInformationService.deleteInfo(us, id, type);
		if(!"ok".equals(result)){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\""+result+"\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}else{
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"成功\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
	}

	/**
	 * 获取附件列表
	 */
	public void getAttachmentList(){
		initServlert();
//		UserSession us = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();
		String id = request.getParameter("id") == null ?"":request.getParameter("id");
		if("".equals(id)){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"参数异常\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		
		MailInformation mi = this.mailInformationService.get(Integer.parseInt(id));
		if(mi == null){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"查询信息不错在\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		
		Set<MailAttachment> maSet = mi.getMailAttachment();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Records\":[");
		if(maSet != null && maSet.size() > 0){
			for(MailAttachment ma : maSet){
				Document doc = ma.getDocument();
				sb.append("{");
				sb.append("\"id\":"+ma.getId());
				sb.append(",");
				sb.append("\"name\":\""+doc.getTitle()+"\"");
				sb.append(",");
				sb.append("\"path\":\""+doc.getResourcePath()+"\"");
				sb.append("},");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * 转发、添加信息初始化
	 * 包括发送人角色信息role 1-管理员 2-承训单位
	 * 信息ID
	 */
	public void getInfoInit(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession) session.getAttribute("usersession");
		
		short role = us.getRole();
		int level = us.getOrganizationLevel();
		String method = request.getParameter("method") == null ? "" : request.getParameter("method");
		if(!"".equals(method)){//正常
			
			if("add".equals(method)){//添加
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Records\":{");
				sb.append("\"role\":"+role);
				sb.append(",");
				sb.append("\"level\":"+level);
				sb.append("}");
				sb.append("}");
				
			}else if("edit".equals(method)){//转发
				String id = request.getParameter("id") == null ?"":request.getParameter("id");
				if("".equals(id)){
					StringBuilder csb = new StringBuilder();
					csb.append("{");
					csb.append("\"Result\":\"ERROR\"");
					csb.append(",");
					csb.append("\"Message\":\"参数异常\"");
					csb.append("}");
					Utlity.ResponseWrite(csb.toString(), "application/json", response);
					return;
				}
				MailInformation mi = this.mailInformationService.get(Integer.parseInt(id));
				if(mi != null){
//					int id = mi.getId();
					short type = mi.getType();//信息类型
					String title = mi.getTitle();
					String content = mi.getText();
					String inscription = mi.getInscription() == null ? "":mi.getInscription();
					String createtime = Utlity.timeSpanToString(mi.getSendtime());
					
					sb.append("{");
					sb.append("\"Result\":\"OK\"");
					sb.append(",");
					sb.append("\"Records\":{");
//					sb.append("{");
					sb.append("\"id\":"+id);
					sb.append(",");
					sb.append("\"type\":"+type);
					sb.append(",");
					sb.append("\"title\":\""+title+"\"");
					sb.append(",");
					sb.append("\"content\":\""+content+"\"");
					sb.append(",");
					sb.append("\"inscription\":\""+inscription+"\"");
					sb.append(",");
					sb.append("\"role\":"+role);
					sb.append(",");
					sb.append("\"level\":"+level);
					sb.append(",");
					sb.append("\"createtime\":\""+createtime+"\"");
					Set<MailAttachment> maSet = mi.getMailAttachment();
					sb.append(",");
					sb.append("\"attachment\":[");
					if(maSet != null && maSet.size() > 0){
						for(MailAttachment ma : maSet){
							Document doc = ma.getDocument();
							sb.append("{");
							sb.append("\"id\":"+ma.getId());
							sb.append(",");
							sb.append("\"name\":\""+doc.getTitle()+"\"");
							sb.append(",");
							sb.append("\"path\":\""+doc.getResourcePath()+"\"");
							sb.append("},");
						}
						sb.delete(sb.length() - 1, sb.length());
					}
					sb.append("]");
//					sb.append("},");
					
					
					sb.append("}");
					sb.append("}");
				}else{
					StringBuilder csb = new StringBuilder();
					csb.append("{");
					csb.append("\"Result\":\"WARNING\"");
					csb.append(",");
					csb.append("\"Message\":\"转发的消息不存在，是否继续？（继续讲发送新消息）\"");
					csb.append("}");
					Utlity.ResponseWrite(csb.toString(), "application/json", response);
					return;
				}
			}else{
				StringBuilder csb = new StringBuilder();
				csb.append("{");
				csb.append("\"Result\":\"ERROR\"");
				csb.append(",");
				csb.append("\"Message\":\"参数异常\"");
				csb.append("}");
				Utlity.ResponseWrite(csb.toString(), "application/json", response);
				return;
			}
			
		}else{
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"参数异常\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * 获取下级管理员地区列表
	 */
	public void getNextOrganization() {
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession) session.getAttribute("usersession");

		// Organization org =
		// this.iOragnizationService.get(us.getOrganization());
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
			if(us.getRole() == 1){
				Organization o = this.iOragnizationService.get(us.getOrganization());
				sb.append("{");
				sb.append("\"id\":" + o.getId());
				sb.append(",");
				sb.append("\"name\":\"" + o.getName() + "\"");
				sb.append("},");
			}
			for (Organization organization : lstOrg) {
				sb.append("{");
				sb.append("\"id\":" + organization.getId());
				sb.append(",");
				sb.append("\"name\":\"" + organization.getName() + "\"");
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
	 * 获取搜索的承训单位信息
	 */
	public void searchTrainCollege() {
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();

		if (user != null) {
//			String str = "";
			List<TrainingCollege> lstTc = new ArrayList<TrainingCollege>();
			lstTc = this.iTrainingCollegeService.getTrainingCollegeListForRange();
			if (lstTc != null && lstTc.size() > 0) {
				sb.append("{");
				sb.append("\"Result\":\"OK\",");
				sb.append("\"Records\":[");
				for (TrainingCollege tc : lstTc) {
					sb.append("{");
					sb.append("\"id\":" + tc.getId());
					sb.append(",");
					sb.append("\"name\":\"" + tc.getName() + "\"");
					sb.append("},");
				}
				sb.delete(sb.length() - 1, sb.length());
				sb.append("]}");
				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
			}else{
				StringBuilder csb = new StringBuilder();
				csb.append("{");
				csb.append("\"Result\":\"ERROR\"");
				csb.append(",");
				csb.append("\"Message\":\"暂无记录\"");
				csb.append("}");
				Utlity.ResponseWrite(csb.toString(), "application/json", response);
				return;
			}
		}else{
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"暂无记录\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
	}

	/**
	 * 获取老师列表（根据项目名称、培训科目、承训单位）
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getTeacherInfo() {
		initServlert();
		List<ProjectType> lityps = (List<ProjectType>) session
				.getAttribute("lstProjectType");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("organization", 26124);
		// map.put("level", us.getOrganizationLevel());enrollType
//		map.put("enrollType", DictionyMap.PROJECT_ENROLL_TYPE_PLAN);
//		List<Project> palist = new ArrayList<Project>();
		List list = this.iProjectService.getProjectListByParams(map,
				null, lityps, -1, -1);
		StringBuilder sb = new StringBuilder();
		
		if (list != null && list.size() > 0) {
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Records\":[");
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Project p = (Project) obj[0];
//				this.searchReportTask.add(p);
				sb.append("{");
				sb.append("\"id\":" + p.getId());
				sb.append(",");
				sb.append("\"name\":\"" + p.getName() + "\"");
				sb.append("},");
			}
			
			sb.delete(sb.length() - 1, sb.length());
			sb.append("]}");
		}else {
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"暂无记录\"");
			sb.append("}");
		}
		
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
		
//		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
//		this.searchReportTask = this.iProjectService.getProjectByStatusAndType(
//				DictionyMap.releaseProject, lityps,
//				DictionyMap.PROJECT_ENROLL_TYPE_PLAN);

	}

	/**
	 * 获取评审专家的列表
	 */
	public void getExpertManage() {
		initServlert();
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
				sb.append("\"id\":" + pa.getId());
				sb.append(",");
				sb.append("\"name\":\"" + pa.getName() + "\"");
				sb.append("},");
			}

			sb.delete(sb.length() - 1, sb.length());
			sb.append("]}");
		} else {
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"暂无记录\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	/**
	 * 根据条件获取收件人信息
	 * type--信息类型 
	 * role--收件人角色
	 */
	public void getConnectionInfo(){
		initServlert();
		String type = request.getParameter("type") == null ? "" : request.getParameter("type");
		if(!"1".equals(type)){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"参数错误\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		
		String role = request.getParameter("role") == null ? "" : request.getParameter("role");
		if("".equals(role)){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"参数错误\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		String search = request.getParameter("search") == null ? "" : request.getParameter("search").trim();
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Records\":[");
		StringBuilder sql = new StringBuilder();
		if("1".equals(role)){//管理员
			sql.append("select pa from ProjectAdmin pa,Organization o where pa.organization=o.id and 1=1 and pa.status=1");
			if(!"".equals(search)){
				sql.append(" and (pa.name like '%"+search+"%'");
				sql.append(" or pa.mobile like '%"+search+"%'");
				sql.append(" or o.name like '%"+search+"%')");
			}
			List<ProjectAdmin> paList = this.iProjectAdminService.getListForPage(sql.toString(), 0, 10);
			if(paList != null && paList.size() > 0){
				for(ProjectAdmin pa : paList){
					Organization o = pa.getOrganization();
					sb.append("{");
					sb.append("\"id\":"+pa.getId());
					sb.append(",");
					sb.append("\"DisplayText\":\""+o.getName()+"管理员-"+pa.getName()+"\"");
					sb.append("},");
				}
				sb.delete(sb.length() - 1, sb.length());
			}
		}else if ("2".equals(role)) {//承训单位管理员
			sql.append("select pa from TrainingAdmin pa,TrainingCollege o where pa.trainingCollege=o.id and 1=1 and pa.status=1");
			if(!"".equals(search)){
				sql.append(" and (pa.name like '%"+search+"%'");
				sql.append(" or pa.mobile like '%"+search+"%'");
				sql.append(" or o.name like '%"+search+"%')");
			}
			List<TrainingAdmin> taList = this.iTrainingAdmin.getListForPage(sql.toString(), 0, 10);
			if(taList != null && taList.size() > 0){
				for(TrainingAdmin pa : taList){
					TrainingCollege o = pa.getTrainingCollege();
					sb.append("{");
					sb.append("\"id\":"+pa.getId());
					sb.append(",");
					sb.append("\"DisplayText\":\""+o.getName()+"承训单位管理员-"+pa.getName()+"\"");
					sb.append("},");
				}
				sb.delete(sb.length() - 1, sb.length());
			}
		}else if ("3".equals(role)) {//状态为正常的教师
			sql.append("select pa from Teacher pa,Organization o where pa.organization=o.id and 1=1 and pa.status=1");
			if(!"".equals(search)){
				sql.append(" and (pa.name like '%"+search+"%'");
				sql.append(" or pa.idcard like '%"+search+"%'");
				sql.append(" or pa.mobile like '%"+search+"%'");
				sql.append(" or o.name like '%"+search+"%')");
			}
			List<Teacher> tList = this.iTeacherService.getListForPage(sql.toString(), 0, 10);
			if(tList != null && tList.size() > 0){
				for(Teacher pa : tList){
					Organization o = pa.getOrganization();
					sb.append("{");
					sb.append("\"id\":"+pa.getId());
					sb.append(",");
					sb.append("\"DisplayText\":\""+o.getName()+"教师-"+pa.getName()+"\"");
					sb.append("},");
				}
				sb.delete(sb.length() - 1, sb.length());
			}
		}else if ("4".equals(role)) {//评审专家
			sql.append("from ProjectExpert pa where 1=1 and pa.status=1");
			if(!"".equals(search)){
				sql.append(" and (pa.name like '%"+search+"%'");
				sql.append(" or pa.mobile like '%"+search+"%'");
				sql.append(" or pa.organization like '%"+search+"%')");
			}
			List<ProjectExpert> peList = this.iProjectExpertService.getListForPage(sql.toString(), 0, 10);
			if(peList != null && peList.size() > 0){
				for(ProjectExpert pa : peList){
					String o = pa.getOrganization();
					sb.append("{");
					sb.append("\"id\":"+pa.getId());
					sb.append(",");
					sb.append("\"DisplayText\":\""+o+"评审专家-"+pa.getName()+"\"");
					sb.append("},");
				}
				sb.delete(sb.length() - 1, sb.length());
			}
		} else {
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"参数错误\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		sb.append("]");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * 获取下级申请收件人信息
	 */
	public void getApplyConnectionListInfo(){
		initServlert();
		String type = request.getParameter("type") == null ? "" : request.getParameter("type");
		if(!"3".equals(type)){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"参数错误\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Records\":[");
		List<ProjectAdmin> paList = this.iProjectAdminService.getAdminByOrganization(26124, -1, -1);
		if(paList != null && paList.size() > 0){
			for(ProjectAdmin pa : paList){
				Organization o = pa.getOrganization();
				sb.append("{");
				sb.append("\"id\":"+pa.getId());
				sb.append(",");
				sb.append("\"name\":\""+pa.getName()+"\"");
				sb.append(",");
				sb.append("\"organization\":\""+o.getName()+"\"");
				sb.append(",");
				sb.append("\"role\":1");
				sb.append("},");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * 发送信息
	 * address--收件人：1-organizationId,2-trainingCollegeId,3-project-subject-trainingCollegeId,4-projectExpertId
	 * title -- 标题
	 * type -- 类型
	 * content -- 内容
	 * inscription -- 落款
	 * attachment -- attId,attId(逗号隔开的ID)
	 */
	public void addMail(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();
		Map<String,Object> params = new HashMap<String, Object>();
		String type = request.getParameter("type") == null ? "0" : request.getParameter("type");
		if("".equals(type)){
			type = "0";
		}
		if("0".equals(type)){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"请选择信息类型\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		String title = request.getParameter("title") == null ? "":request.getParameter("title").trim();
		if("".equals(title)){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"请填写信息标题\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		String address = request.getParameter("address") == null ? "":request.getParameter("address").trim();
//		if("3".equals(type)){//下级申请收件人为最上级管理员
//			address = "1-26124";
//		}
		if("".equals(address)){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"请选择收件人信息\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		
		String content = request.getParameter("content") == null ? "":request.getParameter("content").trim();
//		if(content.indexOf("\"") > 0){
//			
//		}
		//转义英文双引号
		if(!"".equals(content)){
			content = content.replaceAll("\"", "\\\\\"");
		}
		
		String inscription = request.getParameter("inscription") == null ? "":request.getParameter("inscription").trim();
		
		//准备信息字段入库
		MailInformation mi = new MailInformation();
		mi.setCreator(us.getId());
		mi.setCreatorRole(us.getRole());
		mi.setCreattime(new Timestamp(System.currentTimeMillis()));
		mi.setSendStatus((short)1);
		mi.setSendtime(new Timestamp(System.currentTimeMillis()));
		
		mi.setType(Short.parseShort(type));
		mi.setTitle(title);
		mi.setText(content);
		mi.setInscription(inscription);
		
		params.put("mailInformation", mi);
		//处理附件信息
		@SuppressWarnings("unchecked")
		List<fileInfo> lsFiles = (List<fileInfo>) session.getAttribute("attachment");
		session.removeAttribute("attachment");
		List<Document> lstDoc = new ArrayList<Document>();
		if (lsFiles != null && lsFiles.size() > 0) {
			for (fileInfo fileInfo : lsFiles) {
				Document document = new Document();
				document.setCreater(us.getId());
				document.setCreatetime(dataTimeConvertUtility
						.getCurrentTime(""));
				document.setName(fileInfo.getFileGuid());
				document.setSize(fileInfo.getFileSize());
				document.setResourcePath(fileInfo.getFilePath());
				if (fileInfo.getFileName().contains(".")) {
					document.setTitle(fileInfo.getFileName());
				} else {
					document.setTitle(fileInfo.getFileName()
							+ fileInfo.getFileExt());
				}
				document.setType((short) 9);//
				document.setResourceType((short) 1);
				document = iDocumentService.add(document);
				lstDoc.add(document);
			}
		}
		String attachment = request.getParameter("attachment") == null ? "" : request.getParameter("attachment").trim();
		if(!"".equals(attachment)){
			String[] attachments = attachment.split(",");
			if(attachments != null && attachments.length > 0){
				for(String id : attachments){
					MailAttachment ma = this.mailAttachmentService.get(Integer.parseInt(id));
					if(ma != null){
						Document doc = ma.getDocument();
						lstDoc.add(doc);
					}
					
				}
				
			}
		}
		params.put("attachment", lstDoc);
		//处理收件人信息
		//address--收件人：1-organizationId,2-trainingCollegeId,3-project-subject-trainingCollegeId,4-projectExpertId
		String[] addresses = address.split(",");
		
		Map<String, Object> addressMap = new HashMap<String, Object>();
		Map<Integer, ProjectAdmin> paMap = new HashMap<Integer, ProjectAdmin>();
		Map<Integer, TrainingAdmin> taMap = new HashMap<Integer, TrainingAdmin>();
		Map<Integer, Teacher> teaMap = new HashMap<Integer, Teacher>();
		Map<Integer, ProjectExpert> peMap = new HashMap<Integer, ProjectExpert>();
		
		if(addresses != null && addresses.length > 0){
			for(String str : addresses){
				String[] addresses2 = str.split("-");
				if(addresses2 != null && addresses2.length > 0){
					if(addresses2[0].equals("1")){//管理员
						if("2".equals(type)){//公告
							String organizationId = addresses2[1];
							if(organizationId != null && !"".equals(organizationId)){//全部
								if("0".equals(organizationId)){
									List<ProjectAdmin> lst = this.iProjectAdminService.getAdminByOrganization(0, -1, -1);
									if(lst != null && lst.size() > 0){
										for(ProjectAdmin pa : lst){
											if(us.getRole() == 1 && us.getId() == pa.getId()){
												continue;
											}
											paMap.put(pa.getId(), pa);
										}
									}
								}else{
									List<ProjectAdmin> lst = this.iProjectAdminService.getAdminByOrganization(Integer.parseInt(organizationId), -1, -1);
									if(lst != null && lst.size() > 0){
										for(ProjectAdmin pa : lst){
											if(us.getRole() == 1 && us.getId() == pa.getId()){
												continue;
											}
											paMap.put(pa.getId(), pa);
										}
									}
								}
							}
						}else{//公告以外的信息类型
							String id = addresses2[1];
							if(id != null && !"".equals(id)){
								ProjectAdmin pa = this.iProjectAdminService.get(Integer.parseInt(id));
								paMap.put(pa.getId(), pa);
							}
						}
						
					}else if(addresses2[0].equals("2")){//承训单位
						if("2".equals(type)){//公告
							String trainingCollegeId = addresses2[1];
							if(trainingCollegeId != null && !"".equals(trainingCollegeId)){
								if("0".equals(trainingCollegeId)){
									//全部
									List<TrainingAdmin> lst = this.iTrainingAdmin.getTrainingAdminByTrainingCollege(0);
									if(lst != null && lst.size() > 0){
										for(TrainingAdmin ta : lst){
											if(us.getRole() == 2 && us.getId() == ta.getId()){
												continue;
											}
											taMap.put(ta.getId(), ta);
										}
									}
								}else{
									List<TrainingAdmin> lst = this.iTrainingAdmin.getTrainingAdminByTrainingCollege(Integer.parseInt(trainingCollegeId));
									if(lst != null && lst.size() > 0){
										for(TrainingAdmin ta : lst){
											if(us.getRole() == 2 && us.getId() == ta.getId()){
												continue;
											}
											taMap.put(ta.getId(), ta);
										}
									}
								}
							}
						}else{//公告以外的信息类型
							String id = addresses2[1];
							if(id != null && !"".equals(id)){
								TrainingAdmin pa = this.iTrainingAdmin.get(Integer.parseInt(id));
								taMap.put(pa.getId(), pa);
							}
						}
						
					}else if(addresses2[0].equals("3")){//老师//address--收件人：1-organizationId,2-trainingCollegeId,3-project-subject-trainingCollegeId,4-projectExpertId
						if("2".equals(type)){//公告
							String projectId = addresses2[1] == null ? "0":addresses2[1];
							if("".equals(projectId)){
								projectId = "0";
							}
							String subjectId = addresses2[2] == null ? "0":addresses2[2];
							if("".equals(subjectId)){
								subjectId = "0";
							}
							String trainingCollegeId = addresses2[3] == null ? "0":addresses2[3];
							if("".equals(trainingCollegeId)){
								trainingCollegeId = "0";
							}
							
							List<TeacherTrainingRecords> lst = this.iTeacherTrainingRecords.
										getListForPage(Integer.parseInt(projectId), Short.parseShort(subjectId), Integer.parseInt(trainingCollegeId), 0, -1, -1, "r.id");
							if(lst != null && lst.size() > 0){
								for(TeacherTrainingRecords ttr : lst){
									Teacher teacher = ttr.getTeacher();
									teaMap.put(teacher.getId(), teacher);
								}
							}
							
						}else{//公告以外的信息类型
							String id = addresses2[1];
							if(id != null && !"".equals(id)){
								Teacher pa = this.iTeacherService.get(Integer.parseInt(id));
								teaMap.put(pa.getId(), pa);
							}
						}
						
						
					}else if(addresses2[0].equals("4")){//专家
						if("2".equals(type)){//公告
							String projectExpertId = addresses2[1];
							if(projectExpertId != null && !"".equals(projectExpertId)){
								if("0".equals(projectExpertId)){//全部
									List<ProjectExpert> lst = this.iProjectExpertService.getProjectExpertList();
									if(lst != null && lst.size() > 0){
										for(ProjectExpert pe : lst){
											if(us.getRole() == 4 && us.getId() == pe.getId()){
												continue;
											}
											peMap.put(pe.getId(), pe);
										}
									}
								}else{
									ProjectExpert pe = this.iProjectExpertService.get(Integer.parseInt(projectExpertId));
									if(pe != null){
										if(us.getRole() == 4 && us.getId() == pe.getId()){
											
										}else{
											peMap.put(pe.getId(), pe);
										}
									}
								}
								
							}
						}else{//公告以外的信息类型
							String id = addresses2[1];
							if(id != null && !"".equals(id)){
								ProjectExpert pa = this.iProjectExpertService.get(Integer.parseInt(id));
								peMap.put(pa.getId(), pa);
							}
						}
					}
					
				}else{
					continue;
				}
			}
			
		}else{
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"请选择收件人信息\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		
		addressMap.put("1", paMap);
		addressMap.put("2", taMap);
		addressMap.put("3", teaMap);
		addressMap.put("4", peMap);
		params.put("address", addressMap);
		
		String result = this.mailInformationService.addMail(params);//发送
		if(!"OK".equals(result)){
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\""+result+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Message\":\"信息发送成功\"");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
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

	
	public IMailInformationService getMailInformationService() {
		return mailInformationService;
	}
	

	public void setMailInformationService(
			IMailInformationService mailInformationService) {
		this.mailInformationService = mailInformationService;
	}

	public IServiceApplyReplyService getServiceApplyReplyService() {
		return serviceApplyReplyService;
	}

	public void setServiceApplyReplyService(
			IServiceApplyReplyService serviceApplyReplyService) {
		this.serviceApplyReplyService = serviceApplyReplyService;
	}

	public IMailAttachmentService getMailAttachmentService() {
		return mailAttachmentService;
	}

	public void setMailAttachmentService(
			IMailAttachmentService mailAttachmentService) {
		this.mailAttachmentService = mailAttachmentService;
	}

	public IDocumentService getiDocumentService() {
		return iDocumentService;
	}

	public void setiDocumentService(IDocumentService iDocumentService) {
		this.iDocumentService = iDocumentService;
	}

	public ITeacherService getiTeacherService() {
		return iTeacherService;
	}

	public void setiTeacherService(ITeacherService iTeacherService) {
		this.iTeacherService = iTeacherService;
	}

	public ITeacherTrainingRecordsService getiTeacherTrainingRecords() {
		return iTeacherTrainingRecords;
	}

	public void setiTeacherTrainingRecords(
			ITeacherTrainingRecordsService iTeacherTrainingRecords) {
		this.iTeacherTrainingRecords = iTeacherTrainingRecords;
	}

	public ISysUserService getiSysUserService() {
		return iSysUserService;
	}

	public void setiSysUserService(ISysUserService iSysUserService) {
		this.iSysUserService = iSysUserService;
	}

	
}
