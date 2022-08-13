package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.HashMap;
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
import cn.zeppin.entity.Document;
import cn.zeppin.entity.EductionBackground;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherEduAdvance;
import cn.zeppin.entity.TeacherEduAdvanceAdu;
import cn.zeppin.entity.TeacherEduEvidence;
import cn.zeppin.service.IDocumentService;
import cn.zeppin.service.IEductionBackgroundService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.ITeacherEduAdvanceAduService;
import cn.zeppin.service.ITeacherEduAdvanceService;
import cn.zeppin.service.ITeacherEduEvidenceService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

public class TeacherEduAdvanceAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(TeacherEduAdvanceAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	
	private ITeacherService iTeacherService;
	private IEductionBackgroundService iEductionBackgroundService;// 学历
	private IProjectAdminService iProjectAdminService;// 管理员
	private IDocumentService iDocumentService;//文件
	private IOrganizationService iOrganization; // organization
	
	private ITeacherEduAdvanceService iTeacherEduAdvanceService;
	private ITeacherEduAdvanceAduService iTeacherEduAdvanceAduService;
	private ITeacherEduEvidenceService ITeacherEduEvidenceService;
	
	
	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}
	
	/**
	 * 根据角色权限获取各自机构及下属辖区内的教师学历提升认定记录
	 * 以供审核使用
	 * 
	 */
	public void getList(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("usersession");
		//分页参数
		String ist = (String) request.getParameter("jtStartIndex");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		// 显示的条数
		String ien = (String) request.getParameter("jtPageSize");
		if (ien == null || ien.equals("")) {
			ien = DictionyMap.maxPageSize + "";
		}

		int start = Integer.parseInt(ist);
		int limit = DictionyMap.maxPageSize;
		limit = Integer.parseInt(ien);
		int offset = (start -1)*limit;
		// 排序
		String sort = request.getParameter("jtSorting");
		String sorts = "";
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split(" ");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			sorts += sortname + " " + sorttype;
		}else{
			sorts = "id desc";
		}
		
		//其他搜索参数
		String search = request.getParameter("search") == null ? "" : request.getParameter("search");
		String status = request.getParameter("status") == null ? "-2" : request.getParameter("status");
		if("".equals(status)){
			status = "-2";
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		Organization oz = this.iOrganization.get(us.getOrganization());
		if(oz != null){
			params.put("organization", oz.getScode());
		}
		
		if(!"".equals(search)){
			params.put("search", search);
		}
		
		/*
		 * 状态筛选按权限等级区分
		 * 学校管理员查看本单位教师记录 按status
		 * -2 - 全部
		 * -1 - 未审核
		 * 0 - 未通过
		 * 1 - 初审通过
		 * 2 - 终审通过
		 */
		if(!"-2".equals(status)){
			if("-1".equals(status)){//未审核
				params.put("status", status);
				params.put("finalStatus", status);
			}else if("0".equals(status)){//未通过
				params.put("otherstatus", status);
			}else if("1".equals(status)){//初审通过
				params.put("otherstatus", status);
			}else if("2".equals(status)){//终审通过
				params.put("finalStatus", 1);
			}
		}
//		else{
//			if(oz != null){
//				if(!oz.getIsschool()){
//					params.put("status", 1);
//				}
//			}
//		}
		
		int count = this.iTeacherEduAdvanceService.getListCountByParams(params);
		List<TeacherEduAdvance> list = this.iTeacherEduAdvanceService.getListByParams(params, offset, limit, sorts);
		if(list != null && list.size() > 0){
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Totalcount\":"+count);
			sb.append(",");
			sb.append("\"Records\":[");
			for(TeacherEduAdvance tea : list){
				Teacher teacher = tea.getTeacher();
				EductionBackground eb = tea.getEducationBackground();//提升到学历
//				Set<TeacherEduEvidence> teacherEduEvidences = tea.getTeacherEduEvidences();//认证凭证
				String oldeb = tea.getOldEducationBack() == null ? teacher.getEductionBackground().getName() : tea.getOldEducationBack();//旧学历
				if("".equals(oldeb)){
					oldeb = teacher.getEductionBackground().getName();
				}
				Organization ora = teacher.getOrganization();//教师所属学校（机构）
				int isAdmin = 0;//是否为学校上级管理员 2为其他管理员
				if(oz.getId() == ora.getId()){//学校管理员
					isAdmin = 0;
				}else if(oz.getId() == ora.getOrganization().getId()){
					isAdmin = 1;
				}else{
					isAdmin = 2;
				}
				String statusStr = "";
				if("-2".equals(status)){
//					if(oz.getIsschool()){//学校管理员
//						if("-1".equals(tea.getStatus().toString())){//未审核
//							statusStr = "-1";
//						}else if("0".equals(tea.getStatus().toString()) || "0".equals(tea.getFinalStatus().toString())){//未通过
//							statusStr = "0";
//						}else if((tea.getStatus() ==1 && tea.getFinalStatus() == -1) || (tea.getStatus() ==1 && tea.getFinalStatus() == 1)){//已通过：status=1 fstatus=1 or status=1 fstatus=-1//初审通过
//							statusStr = "1";
//						}
//					}else{
//						if(tea.getFinalStatus() == -1){//未审核
//							statusStr = "-1";
//						}else if(tea.getFinalStatus() == 0){//未通过
//							statusStr = "0";
//						}else if(tea.getFinalStatus() == 1){
//							statusStr = "1";
//						}
//					}
					if(tea.getStatus() == -1 && tea.getFinalStatus() == -1){//未审核
						statusStr = "-1";
					}else if(tea.getFinalStatus() == 0){//未通过
						statusStr = "0";
					}else if(tea.getStatus() == 1 && tea.getFinalStatus() == -1){//初审通过
						statusStr = "1";
					}else if(tea.getFinalStatus() == 1){//终审通过
						statusStr = "2";
					}else{//未通过
						statusStr = "0";
					}
				}else{
					statusStr = status;
				}
				
				sb.append("{");
				sb.append("\"id\":"+tea.getId());
				sb.append(",");
				sb.append("\"name\":\""+teacher.getName()+"\"");
				sb.append(",");
				sb.append("\"idcard\":\""+teacher.getIdcard()+"\"");
				sb.append(",");
				sb.append("\"sex\":\""+teacher.getSex()+"\"");
				sb.append(",");
				sb.append("\"status\":\""+statusStr+"\"");
				sb.append(",");
				sb.append("\"isAdmin\":"+isAdmin);
				sb.append(",");
				sb.append("\"organization\":\""+ora.getName()+"\"");
				sb.append(",");
				sb.append("\"graduation\":\""+tea.getGraduation()+"\"");
				sb.append(",");
				sb.append("\"major\":\""+tea.getMajor()+"\"");
				sb.append(",");
				sb.append("\"starttime\":\""+Utlity.timeSpanToDateString(tea.getStarttime())+"\"");
				sb.append(",");
				sb.append("\"endtime\":\""+Utlity.timeSpanToDateString(tea.getEndtime())+"\"");
				sb.append(",");
				sb.append("\"certificate\":\""+tea.getCertificate()+"\"");
				sb.append(",");
				sb.append("\"educationBackground\":\""+eb.getName()+"\"");
				sb.append(",");
				sb.append("\"odlEducationBackground\":\""+oldeb+"\"");
				sb.append("},");
			}
			sb.delete(sb.length() -1, sb.length());
			sb.append("]}");
		}else{
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Records\":[");
			sb.append("]}");
		}
		
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * 获取审核记录
	 */
	public void getAduRecords(){
		initServlert();
		StringBuilder sb = new StringBuilder();
//		UserSession us = (UserSession)session.getAttribute("usersession");
		String id = request.getParameter("id") == null ? "0" : request.getParameter("id");
		if("".equals(id)){
			id = "0";
		}
		
		if(!"0".equals(id)){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("teacherEduAdvance", id);
			List<TeacherEduAdvanceAdu> list = this.iTeacherEduAdvanceAduService.getListByParams(params, -1, -1, "createtime desc");
			if(list != null && list.size() > 0){
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Records\":[");
				int index = 1;
				for(TeacherEduAdvanceAdu teaa : list){
					short type = teaa.getType();
					String typestr = "";
					switch (type) {
					case 1:
						typestr = "初审通过";
						break;
					case 2:
						typestr = "终审通过";
						break;
					case 3:
						typestr = "审核不通过";
						break;
					}
					
					ProjectAdmin pa = this.iProjectAdminService.get(teaa.getChecker());
					String name = pa.getOrganization().getName()+pa.getName()+typestr;
					String reason = teaa.getReason() == null ? "":teaa.getReason();
					
					if(type == 3 && !"".equals(reason)){
						name += ("<br>原因："+reason);
					}
					sb.append("{");
					sb.append("\"id\":"+index);
					sb.append(",");
					sb.append("\"time\":\""+Utlity.timeSpanToString(teaa.getCreatetime())+"\"");
					sb.append(",");
					sb.append("\"info\":\""+name+"\"");
					sb.append("},");
					
					index++;
				}
				sb.delete(sb.length() - 1, sb.length());
				sb.append("]");
				sb.append("}");
			}else{
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Records\":[");
				sb.append("]}");
			}
		}else{
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"无效的操作记录\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	
	/**
	 * 初始化审核认定记录页面
	 * @return
	 */
	public String initAduRecord(){
		
		return "initAdu";
	}
	
	/**
	 * 获取单条认定记录详细信息
	 */
	public void loadRecordInfo(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		String id = request.getParameter("id") == null ? "0" : request.getParameter("id");
		if("".equals(id)){
			id = "0";
		}
		TeacherEduAdvance tea = this.iTeacherEduAdvanceService.get(Integer.parseInt(id));
		if(tea == null){
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"无效的操作记录\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		
		
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Records\":{");
		
		Teacher teacher = tea.getTeacher();
		EductionBackground eb = tea.getEducationBackground();//提升到学历
		Set<TeacherEduEvidence> teacherEduEvidences = tea.getTeacherEduEvidences();//认证凭证
		String oldeb = tea.getOldEducationBack() == null ? teacher.getEductionBackground().getName() : tea.getOldEducationBack();//旧学历
		if("".equals(oldeb)){
			oldeb = teacher.getEductionBackground().getName();
		}
		Organization ora = teacher.getOrganization();//教师所属学校（机构）
//		int isAdmin = 0;//是否为学校上级管理员 2为其他管理员
//		if(oz.getId() == ora.getId()){//学校管理员
//			isAdmin = 0;
//		}else if(oz.getId() == ora.getOrganization().getId()){
//			isAdmin = 1;
//		}else{
//			isAdmin = 2;
//		}
		
//		sb.append("{");
		sb.append("\"id\":"+tea.getId());
		sb.append(",");
		sb.append("\"name\":\""+teacher.getName()+"\"");
		sb.append(",");
		sb.append("\"idcard\":\""+teacher.getIdcard()+"\"");
//		sb.append(",");
//		sb.append("\"isAdmin\":"+isAdmin);
		sb.append(",");
		sb.append("\"organization\":\""+ora.getName()+"\"");
		sb.append(",");
		sb.append("\"graduation\":\""+tea.getGraduation()+"\"");
		sb.append(",");
		sb.append("\"major\":\""+tea.getMajor()+"\"");
		sb.append(",");
		sb.append("\"starttime\":\""+Utlity.timeSpanToDateString(tea.getStarttime())+"\"");
		sb.append(",");
		sb.append("\"endtime\":\""+Utlity.timeSpanToDateString(tea.getEndtime())+"\"");
		sb.append(",");
		sb.append("\"certificate\":\""+tea.getCertificate()+"\"");
		sb.append(",");
		sb.append("\"educationBackground\":\""+eb.getName()+"\"");
		sb.append(",");
		sb.append("\"odlEducationBackground\":\""+oldeb+"\"");
		sb.append(",");
		sb.append("\"eivdence\":[");
		if(teacherEduEvidences != null && teacherEduEvidences.size() > 0){
			for(TeacherEduEvidence tee : teacherEduEvidences){
				Document doc = tee.getDocument();
				sb.append("{");
				sb.append("\"id\":"+tee.getId());
				sb.append(",");
				sb.append("\"path\":\""+doc.getResourcePath()+"\"");
				sb.append("},");
			}
			sb.delete(sb.length()-1, sb.length());
		}
		sb.append("]");
//		sb.append("},");
		
		
		sb.append("}");
		sb.append("}");
		
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * 审核教师学历提升认定记录是否符合标准
	 * 先由所属机构管理员初审
	 * 再由所属机构的上级机构管理员终审
	 * 终审通过后变更对应教师学历
	 * 
	 * 审核权限
	 * 所属机构管理员--可以审核教师提交的认定记录，即终审状态为未审核的
	 * 上级管理员 -- 可以审核下属机构审核通过的认定记录，即审核状态为已通过的
	 */
	public void aduRecords(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("usersession");
		String id = request.getParameter("id") == null ? "0" : request.getParameter("id");
		if("".equals(id)){
			id = "0";
		}
		String isAdmin = request.getParameter("isAdmin") == null ? "-1":request.getParameter("isAdmin");
		if("".equals(isAdmin)){
			isAdmin = "-1";
		}
		if("-1".equals(isAdmin)){
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"参数异常\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		TeacherEduAdvance tea = this.iTeacherEduAdvanceService.get(Integer.parseInt(id));
		if(tea == null){
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"无效的操作记录\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		String method = request.getParameter("method") == null ? "" : request.getParameter("method");
		if("".equals(method)){
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"参数异常\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		if("pass".equals(method)){//审核通过
			if("0".equals(isAdmin)){//学校管理员
				if(tea.getFinalStatus() > -1){
					sb.append("{");
					sb.append("\"Result\":\"ERROR\"");
					sb.append(",");
					sb.append("\"Message\":\"已终审，无操作权限\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				if(tea.getStatus() != 1){
					tea.setStatus((short)1);
					
					TeacherEduAdvanceAdu teaa = new TeacherEduAdvanceAdu();
					teaa.setChecker(us.getId());
					teaa.setTeacherEduAdvance(tea);
					teaa.setType((short)1);
					teaa.setCreatetime(new Timestamp(System.currentTimeMillis()));
					try {
						this.iTeacherEduAdvanceService.update(tea);
						this.iTeacherEduAdvanceAduService.add(teaa);
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						sb.append("{");
						sb.append("\"Result\":\"ERROR\"");
						sb.append(",");
						sb.append("\"Message\":\"操作异常\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;
					}
				}
				
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"操作成功\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
				
			}else if ("1".equals(isAdmin)) {
//				tea.setStatus((short)1);
				if(tea.getFinalStatus() != 1){
					tea.setFinalStatus((short)1);
					TeacherEduAdvanceAdu teaa = new TeacherEduAdvanceAdu();
					teaa.setChecker(us.getId());
					teaa.setTeacherEduAdvance(tea);
					teaa.setType((short)2);
					teaa.setCreatetime(new Timestamp(System.currentTimeMillis()));
					try {
						this.iTeacherEduAdvanceService.update(tea);
						this.iTeacherEduAdvanceAduService.add(teaa);
						Teacher teacher = tea.getTeacher();
						teacher.setEductionBackground(tea.getEducationBackground());
						this.iTeacherService.update(teacher);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						sb.append("{");
						sb.append("\"Result\":\"ERROR\"");
						sb.append(",");
						sb.append("\"Message\":\"操作异常\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;
					}
				}
				
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"操作成功\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
				
			}else{
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"暂无操作权限\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
		}else if("nopass".equals(method)){
			
			String reason = request.getParameter("reason") == null ? "" : request.getParameter("reason");
			
			if("0".equals(isAdmin)){//学校管理员
				
				if(tea.getFinalStatus() > -1){
					sb.append("{");
					sb.append("\"Result\":\"ERROR\"");
					sb.append(",");
					sb.append("\"Message\":\"已终审，无操作权限\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				if(tea.getStatus() != 0){
					tea.setStatus((short)0);
//					tea.setFinalStatus((short)0);
					TeacherEduAdvanceAdu teaa = new TeacherEduAdvanceAdu();
					teaa.setChecker(us.getId());
					teaa.setTeacherEduAdvance(tea);
					teaa.setType((short)3);
					teaa.setCreatetime(new Timestamp(System.currentTimeMillis()));
					teaa.setReason(reason);
					try {
						this.iTeacherEduAdvanceService.update(tea);
						this.iTeacherEduAdvanceAduService.add(teaa);
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						sb.append("{");
						sb.append("\"Result\":\"ERROR\"");
						sb.append(",");
						sb.append("\"Message\":\"操作异常\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;
					}
				}
				
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"操作成功\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
				
			}else if ("1".equals(isAdmin)) {//学校上级管理员
				
				if(tea.getFinalStatus() != 0){
					tea.setFinalStatus((short)0);
					TeacherEduAdvanceAdu teaa = new TeacherEduAdvanceAdu();
					teaa.setChecker(us.getId());
					teaa.setTeacherEduAdvance(tea);
					teaa.setType((short)3);
					teaa.setCreatetime(new Timestamp(System.currentTimeMillis()));
					teaa.setReason(reason);
					try {
						this.iTeacherEduAdvanceService.update(tea);
						this.iTeacherEduAdvanceAduService.add(teaa);
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						sb.append("{");
						sb.append("\"Result\":\"ERROR\"");
						sb.append(",");
						sb.append("\"Message\":\"操作异常\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;
					}
				}
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"操作成功\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
				
			}else{
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"暂无操作权限\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
		}
	}

	
	public void getStatusCount() {
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("usersession");
		//其他搜索参数
		String search = request.getParameter("search") == null ? "" : request.getParameter("search");
//		String status = request.getParameter("status") == null ? "-2" : request.getParameter("status");
//		if("".equals(status)){
//			status = "-2";
//		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		Organization oz = this.iOrganization.get(us.getOrganization());
		if(oz != null){
			params.put("organization", oz.getScode());
		}
		
		if(!"".equals(search)){
			params.put("search", search);
		}
//		if(!"-2".equals(status)){
//			if("-1".equals(status)){//未审核
//				params.put("status", status);
//				params.put("finalStatus", status);
//			}else if("0".equals(status)){//未通过
//				params.put("otherstatus", status);
//			}else if("1".equals(status)){//初审通过
//				params.put("otherstatus", status);
//			}else if("2".equals(status)){//终审通过
//				params.put("finalStatus", 1);
//			}
//		}
		
		int count = 0;
		int noCheck = 0;
		int checkNoPass = 0;
		int checkPass = 0;
		int checkFPass = 0;
		if(oz != null){
			count = this.iTeacherEduAdvanceService.getListCountByParams(params);//全部
			params.put("status", -1);
			params.put("finalStatus", -1);
			noCheck = this.iTeacherEduAdvanceService.getListCountByParams(params);//未审核
			params.remove("status");
			params.remove("finalStatus");
			params.put("otherstatus", 0);
			checkNoPass = this.iTeacherEduAdvanceService.getListCountByParams(params);//未通过
			params.put("otherstatus", 1);
			checkPass = this.iTeacherEduAdvanceService.getListCountByParams(params);//初审通过
			params.remove("otherstatus");
			params.put("finalStatus", 1);
			checkFPass = this.iTeacherEduAdvanceService.getListCountByParams(params);//终审通过
//			if(oz.getIsschool()){//学校管理员
//				count = this.iTeacherEduAdvanceService.getListCountByParams(params);//全部
//				params.put("status", -1);
//				params.put("finalStatus", -1);
//				noCheck = this.iTeacherEduAdvanceService.getListCountByParams(params);
//				params.remove("status");
//				params.remove("finalStatus");
//				params.put("otherstatus", 0);
//				checkNoPass = this.iTeacherEduAdvanceService.getListCountByParams(params);
//				params.put("otherstatus", 1);
//				checkPass = this.iTeacherEduAdvanceService.getListCountByParams(params);
//			}else{
//				params.put("status", 1);
//				count = this.iTeacherEduAdvanceService.getListCountByParams(params);//全部
//				params.put("status", 1);
//				params.put("finalStatus", -1);
//				noCheck = this.iTeacherEduAdvanceService.getListCountByParams(params);
//				params.put("finalStatus", 0);
//				checkNoPass = this.iTeacherEduAdvanceService.getListCountByParams(params);
//				params.put("finalStatus", 1);
//				checkPass = this.iTeacherEduAdvanceService.getListCountByParams(params);
//			}
		}

		sb.append("{\"totalCount\":" + (count) + ",");// 全部
		sb.append("\"noCheck\":" + (noCheck) + ",");// 未审核
		sb.append("\"checkPass\":" + (checkPass) + ",");// 初审通过
		sb.append("\"checkFPass\":" + (checkFPass) + ",");// 终审通过
		sb.append("\"checkNoPass\":" + (checkNoPass));// 未通过
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public ITeacherService getiTeacherService() {
		return iTeacherService;
	}


	public void setiTeacherService(ITeacherService iTeacherService) {
		this.iTeacherService = iTeacherService;
	}


	public IEductionBackgroundService getiEductionBackgroundService() {
		return iEductionBackgroundService;
	}


	public void setiEductionBackgroundService(
			IEductionBackgroundService iEductionBackgroundService) {
		this.iEductionBackgroundService = iEductionBackgroundService;
	}


	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}


	public void setiProjectAdminService(IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}


	public IDocumentService getiDocumentService() {
		return iDocumentService;
	}


	public void setiDocumentService(IDocumentService iDocumentService) {
		this.iDocumentService = iDocumentService;
	}


	public ITeacherEduAdvanceService getiTeacherEduAdvanceService() {
		return iTeacherEduAdvanceService;
	}


	public void setiTeacherEduAdvanceService(
			ITeacherEduAdvanceService iTeacherEduAdvanceService) {
		this.iTeacherEduAdvanceService = iTeacherEduAdvanceService;
	}


	public ITeacherEduAdvanceAduService getiTeacherEduAdvanceAduService() {
		return iTeacherEduAdvanceAduService;
	}


	public void setiTeacherEduAdvanceAduService(
			ITeacherEduAdvanceAduService iTeacherEduAdvanceAduService) {
		this.iTeacherEduAdvanceAduService = iTeacherEduAdvanceAduService;
	}


	public ITeacherEduEvidenceService getITeacherEduEvidenceService() {
		return ITeacherEduEvidenceService;
	}


	public void setITeacherEduEvidenceService(
			ITeacherEduEvidenceService iTeacherEduEvidenceService) {
		ITeacherEduEvidenceService = iTeacherEduEvidenceService;
	}

	public IOrganizationService getiOrganization() {
		return iOrganization;
	}

	public void setiOrganization(IOrganizationService iOrganization) {
		this.iOrganization = iOrganization;
	}
	
	

}
