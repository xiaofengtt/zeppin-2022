/**
 * 
 */
package cn.zeppin.action.teacher;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.Submit;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.service.ISubmitService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Administrator
 *
 */ 
public class TeacherSpaceAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8266142466333833634L;

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private ITeacherTrainingRecordsService iTeacherTrainingRecordsService;
	private IProjectTypeService iProjectTypeService;
	private IProjectApplyService iProjectApplyService;
	private ISubmitService iSubmitService;
	private IProjectService iProjectService;

	public TeacherSpaceAction() {
	}
	
	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	
	/**
	 * 个人主页初始化数据
	 * 1、已参加培训：totalCount -已结业培训endCount -未结业培训onTrainCount
	 * 2、可报名培训：trainCount(未报名的)
	 * 3、已获学时：classHourCount -集中培训学时 centralizeCount -远程培训学时 remoteCount
	 * 4、培训质量反馈：-已评价evaluateCount -未评价unEvaluateCount
	 */
	@SuppressWarnings("rawtypes")
	public void initMainPage(){
		initServlert();
		
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		Integer teacherId = 0;
		if(us != null){
			
			teacherId = us.getId();

		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"用户未登录\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		try {
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"查询完毕\"");
			sb.append(",");
			sb.append("\"Records\":{");
			
			
			Map<String, Object> pagram = new HashMap<String, Object>();
			pagram.put("finalStatus", 2);
			
			int totalCount = this.iTeacherTrainingRecordsService.getTeacherRecordsCountByTid(teacherId, pagram);
			sb.append("\"totalCount\":\""+totalCount+"\"");
			sb.append(",");
			
			//加入参数 是否已结束 1代表已结束 0代表未结束
			pagram.put("isStop", 1);
			int endCount = this.iTeacherTrainingRecordsService.getTeacherRecordsCountByTid(teacherId, pagram);
			sb.append("\"endCount\":\""+endCount+"\"");
			sb.append(",");
			
			int onTrainCount = totalCount-endCount;
			sb.append("\"onTrainCount\":\""+onTrainCount+"\"");
			sb.append(",");
			
			List<ProjectType> lityps = this.iProjectTypeService.findAll();
//			int count = this.iProjectService.getProjectCountByParams(DictionyMap.releaseProject, lityps, DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("enrollType", DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM);
			params.put("status", 2);
			params.put("group", "t.project");
			
			List lstProject = this.iProjectApplyService.getProjectByParams(params, lityps);
			
			int count = lstProject.size();
//			System.out.println("可报名项目数"+count);
			sb.append("\"trainCount\":\""+count+"\"");
			
			sb.append(",");
			
			List li = this.iTeacherTrainingRecordsService.getTeacherRecordsByTid(teacherId, 0, totalCount, null, null);
			int classHourCount = 0;
			int centralizeCount = 0;
			int remoteCount = 0;
			
			int evaluateCount = 0;
			int unEvaluateCount = 0;
			
			if(li!=null && !li.isEmpty()){
				for(int i = 0; i < li.size(); i++){
					Object[] obj = (Object[])li.get(i);
					TeacherTrainingRecords ttr = (TeacherTrainingRecords)obj[0];
					Project p = (Project)obj[1];
//					ProjectApply pa = (ProjectApply)obj[2];
					if(p.getTraintype() == 1 && ttr.getTrainingStatus()>2){
						classHourCount+=ttr.getTrainingClasshour();
						centralizeCount+=ttr.getTrainingClasshour();
						
					}else if(p.getTraintype() == 2 && ttr.getTrainingStatus()>2){
						remoteCount+=ttr.getTrainingOnlineHour();
						classHourCount+=ttr.getTrainingOnlineHour();
					}
					
					int tid = ttr.getTeacher().getId();
					int pid = ttr.getProject().getId();
					short sid = ttr.getTrainingSubject().getId();
					int tcid = ttr.getTrainingCollege().getId();
					int paperid = 0;

					if (ttr.getProject().getPsqByEvaluationTrainingPsq() != null) {
						paperid = ttr.getProject().getPsqByEvaluationTrainingPsq().getId();
						Submit sub = this.getiSubmitService().getSubmitByAll(tid, paperid, pid, sid, tcid, tid);
						if(sub == null){//未评价
							unEvaluateCount++;
						}else{
							evaluateCount++;
						}
					}

				}
			}
			sb.append("\"classHourCount\":\""+classHourCount+"\"");//总
			sb.append(",");
			sb.append("\"centralizeCount\":\""+centralizeCount+"\"");//集中
			sb.append(",");
			sb.append("\"remoteCount\":\""+remoteCount+"\"");//远程
			sb.append(",");
			sb.append("\"evaluateCount\":\""+evaluateCount+"\"");//已评价
			sb.append(",");
			sb.append("\"unEvaluateCount\":\""+unEvaluateCount+"\"");//未评价
			sb.append("}}");
			
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"查询过程异常\"");
			sb.append("}");
			
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
		
		
	}
	
	public ITeacherTrainingRecordsService getiTeacherTrainingRecordsService() {
		return iTeacherTrainingRecordsService;
	}

	public void setiTeacherTrainingRecordsService(
			ITeacherTrainingRecordsService iTeacherTrainingRecordsService) {
		this.iTeacherTrainingRecordsService = iTeacherTrainingRecordsService;
	}

	public IProjectTypeService getiProjectTypeService() {
		return iProjectTypeService;
	}

	public void setiProjectTypeService(IProjectTypeService iProjectTypeService) {
		this.iProjectTypeService = iProjectTypeService;
	}

	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}

	public void setiProjectApplyService(IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
	}

	public ISubmitService getiSubmitService() {
		return iSubmitService;
	}

	public void setiSubmitService(ISubmitService iSubmitService) {
		this.iSubmitService = iSubmitService;
	}

	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}
	
	

}
