package cn.zeppin.train.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Map.Entry;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.admin.AssignTeacherTaskAction;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TeachingGrade;
import cn.zeppin.entity.TeachingLanguage;
import cn.zeppin.entity.TeachingSubject;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITeachingGradeService;
import cn.zeppin.service.ITeachingLanguageService;
import cn.zeppin.service.ITeachingSubjectService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.service.ITrainingSubjectService;
//import cn.zeppin.train.entity.TrainingCollegeToken;
//import cn.zeppin.train.service.ITrainingCollegeTokenService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.IdCardUtil;
import cn.zeppin.utility.MD5Util;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;
@SuppressWarnings("rawtypes")
public class TrainUnitAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	static Logger logger = LogManager.getLogger(AssignTeacherTaskAction.class);

	private HttpServletRequest request;
	@SuppressWarnings("unused")
	private HttpSession session;
	private HttpServletResponse response;

	private IProjectService iProjectService;
	private IProjectTypeService iProjectTypeService;
	private ITrainingCollegeService iTrainingCollegeService;
	private ITrainingSubjectService iTrainingSubjectService;
	private IProjectApplyService iProjectApplyService;
	private IAreaService iAreaService;//地区操作
	
//	private ITrainingAdminService iTrainingAdminService;
	
	
	private ITeacherService iTeacherService;
	
	private ITeacherTrainingRecordsService iTeacherTrainingRecordsService;
	
//	private ITrainingCollegeTokenService iTrainingCollegeTokenService;
	private ITeachingLanguageService iTeachingLanguageService;// 教学语言操作
	private ITeachingGradeService iTeachingGradeService;// 教学学段操作
	private ITeachingSubjectService iTeachingSubjectService;// 教学科目操作

	
	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}
	
	/**
	 * 第三方培训平台获取中标项目、学科信息接口
	 * 首先验证第三方培训平台唯一登录令牌是否正确存在
	 * 然后查询该平台下的相关中标项目、学科信息
	 */
	public void ProjectInfo(){
		
		initServlert();
		
		StringBuilder sb = new StringBuilder();
		String token = "";
		if(request.getParameter("token") != null && !"".equals(request.getParameter("token"))){
			token = request.getParameter("token");
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"授权码为空，请求失败\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
//		TrainingCollegeToken tct = this.iTrainingCollegeTokenService.checkTrainingCollegeToken(token);
		List<TrainingCollege> lstTrainCollege = this.iTrainingCollegeService.checkTrainingCollegeToken(token);
		if(lstTrainCollege != null && lstTrainCollege.size() > 0) {
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			
			TrainingCollege tc = lstTrainCollege.get(0);
			
			List lstProjectApplies = this.iProjectApplyService.getProjectInfoByCollege(tc.getId());
			if(lstProjectApplies != null && !lstProjectApplies.isEmpty()){
				int totalCount = lstProjectApplies.size();
				sb.append("\"Message\":\"请求成功\"");
				sb.append(",");
				sb.append("\"TotalCount\":"+totalCount);
				sb.append(",");
				sb.append("\"Records\":[");
				
				for(int i = 0; i < lstProjectApplies.size(); i++){
					Object[] obj = (Object[])lstProjectApplies.get(i);
					ProjectApply pa = (ProjectApply)obj[0];
					Project p = (Project)obj[1];
					TrainingSubject ts = pa.getTrainingSubject();
					
					sb.append("{");
					sb.append("\"projectId\":"+p.getId());
					sb.append(",");
					sb.append("\"projectName\":"+"\""+p.getName()+"\"");
					sb.append(",");
					sb.append("\"year\":"+p.getYear());
					sb.append(",");
					sb.append("\"trainingSubjectId\":"+ts.getId());
					sb.append(",");
					sb.append("\"trainingSubjectName\":"+"\""+ts.getName()+"\"");
					sb.append(",");
					sb.append("\"approveNumber\":"+pa.getApproveNumber());
					sb.append("},");
				}
				
				sb.delete(sb.length()-1, sb.length());
				sb.append("]}");
				
			}else{
				sb.append("\"Message\":\"暂无中标项目、学科数据\"");
				sb.append("}");
			}
			
			
			
			
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"无效的授权码，请求失败\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
	}
	
	/**
	 * 第三方培训平台获取报名学员信息接口
	 * 先验证第三方培训平台的登录令牌
	 * 再回传数据
	 */
	public void TeacherInfo(){
		
		initServlert();
		StringBuilder sb = new StringBuilder();
		String token = "";
		if(request.getParameter("token") != null && !"".equals(request.getParameter("token"))){
			token = request.getParameter("token");
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"授权码为空，请求失败\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
//		TrainingCollegeToken tct = this.iTrainingCollegeTokenService.checkTrainingCollegeToken(token);
		List<TrainingCollege> lstTrainCollege = this.iTrainingCollegeService.checkTrainingCollegeToken(token);
		
		if(lstTrainCollege != null && !lstTrainCollege.isEmpty()){
			
			TrainingCollege tc = lstTrainCollege.get(0);
			
			int projectId = 0;
			if(request.getParameter("projectId") != null && !"".equals(request.getParameter("projectId"))){
				projectId = Integer.parseInt(request.getParameter("projectId"));
				
				Project pro = iProjectService.get(projectId);
				if(pro != null){
					if(pro.getStatus() > 2){//已关闭的项目不能获取教师信息
						sb.append("{");
						sb.append("\"Result\":\"OK\"");
						sb.append(",");
						sb.append("\"Message\":\"项目已关闭\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;
					}
				}
			}
			
			int trainingSubjectId = 0;
			if(request.getParameter("trainingSubjectId") != null && !"".equals(request.getParameter("trainingSubjectId"))){
				trainingSubjectId = Integer.parseInt(request.getParameter("trainingSubjectId"));
			}
			
			int trainingUnit = tc.getId();
			
			List<ProjectType> lityps = this.iProjectTypeService.findAll();
			
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			
			int totalCount = this.iTeacherTrainingRecordsService.getAduTeacherCount(null, projectId, trainingSubjectId, trainingUnit, 0, 2, null, lityps, null);
			List lstTeacherTrainRecords = this.iTeacherTrainingRecordsService.getAduTeacher(null, projectId, trainingSubjectId, trainingUnit, 0, 2, null, lityps, 0, totalCount, null);
			if(lstTeacherTrainRecords != null && !lstTeacherTrainRecords.isEmpty()){
				
				sb.append("\"Message\":\"请求成功\"");
				sb.append(",");
				sb.append("\"Records\":[");
				
				int otherCount = 0;//记录异动学员数目
				for(int i = 0; i < lstTeacherTrainRecords.size(); i++){
					Object[] obj = (Object[])lstTeacherTrainRecords.get(i);
					
					TeacherTrainingRecords ttr = (TeacherTrainingRecords)obj[0];
					TrainingSubject ts = ttr.getTrainingSubject();
					
					Teacher t = (Teacher)obj[1];
					Project p = (Project)obj[3];
					
					/*
					 * 过滤掉培训状态为异动的学员
					 */
					if(ttr.getTrainingStatus() == 0){
						otherCount++;
						continue;
					}
					
					sb.append("{");
					sb.append("\"teacherId\":"+t.getId());
					sb.append(",");
					sb.append("\"teacherName\":"+"\""+t.getName()+"\"");
					sb.append(",");
					sb.append("\"teacherIdcard\":"+"\""+t.getIdcard()+"\"");
					sb.append(",");
					sb.append("\"teacherAge\":"+IdCardUtil.getAgeByBirthday(t.getBirthday()));
					sb.append(",");
					sb.append("\"teacherSex\":"+"\""+t.getSex()+"\"");
					sb.append(",");
					sb.append("\"teacherPhone\":"+"\""+t.getMobile()+"\"");
					sb.append(",");
					String areasString = "";
					List<String> lstA = iAreaService.getParentNodes(t.getArea().getCode());
					if (lstA.size() > 0) {
						List<String> tt = new ArrayList<>();
						for (int o = lstA.size() - 1; o >= 0; o--) {
							tt.add(lstA.get(o));
						}
		
						for (String string : tt) {
							areasString += string + ">>";
						}
					}
					if (areasString.length() > 2) {
						sb.append("\"teacherArea\":\""+areasString.substring(0, areasString.length() - 2)+"\"");
					}else{
						sb.append("\"teacherArea\":\""+areasString+"\"");
					}
					sb.append(",");
					sb.append("\"teacherOrganization\":\""+t.getOrganization().getName()+"\"");
					sb.append(",");
					
					// 获取主要教学语言
					String hqlString = "from TeachingLanguage where teacher=" + t.getId() + " and isprime=1";
		
					List<TeachingLanguage> lsTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
										   
					if (lsTeachingLanguages.size() > 0) {
						TeachingLanguage teachingLanguage = lsTeachingLanguages.get(0);
						sb.append("\"teachingLanguageId\":"+teachingLanguage.getLanguage().getId());
						sb.append(",");
						sb.append("\"teachingLanguage\":\""+teachingLanguage.getLanguage().getName()+"\"");
						sb.append(",");
					}
		
					// 获取主要教学学段
					hqlString = "from TeachingGrade where teacher=" + t.getId() + " and isprime=1";
					List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
					if (lstTeachingGrades.size() > 0) {
						TeachingGrade teachingGrade = lstTeachingGrades.get(0);
						sb.append("\"teachingGradeId\":"+teachingGrade.getGrade().getId());
						sb.append(",");
						sb.append("\"teachingGrade\":\""+teachingGrade.getGrade().getName()+"\"");
						sb.append(",");
					}
		
					// 获取主要教学学科
					hqlString = "from TeachingSubject where teacher=" + t.getId() + " and isprime=1";
					List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
					if (lsttTeachingSubjects.size() > 0) {
						TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
						sb.append("\"teachingSubjectId\":"+teachingSubject.getSubject().getId());
						sb.append(",");
						sb.append("\"teachingSubject\":\""+teachingSubject.getSubject().getName()+"\"");
						sb.append(",");
					}
					
					sb.append("\"projectId\":"+p.getId());
					sb.append(",");
					sb.append("\"projectName\":"+"\""+p.getName()+"\"");
					sb.append(",");
					sb.append("\"trainingSubjectId\":"+ts.getId());
					sb.append(",");
					sb.append("\"trainingSubjectName\":"+"\""+ts.getName()+"\"");
					
					sb.append("},");
					
				}
				sb.delete(sb.length()-1, sb.length());
				
				totalCount = totalCount-otherCount;
				
				sb.append("]");
				sb.append(",");
				sb.append("\"TotalCount\":"+totalCount);
				sb.append("}");
			}else{
				sb.append("\"Message\":\"暂无报名学员信息数据\"");
				sb.append("}");
			}
			
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"无效的授权码，请求失败\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
	}
	
	/**
	 * 第三方培训平台学员培训成绩写回接口
	 * 先验证第三封培训平台令牌
	 * 然后将回传的数据写入teacherTrainRecords中
	 */
	public void inputTrainingInfo(){
		
		
		initServlert();
		StringBuilder sb = new StringBuilder();
		String token = "";
//		Map<String, String[]> param = request.getParameterMap();
//		System.out.println("isEmpty:"+param.isEmpty());
//		if(param != null){
//			for (Entry<String, String[]> entry : param.entrySet()) {  
//				  
//			    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().toString());  
//			  
//			} 
//		}
		
		if(request.getParameter("token") != null && !"".equals(request.getParameter("token"))){
			token = request.getParameter("token");
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"授权码为空，请求失败\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
//		TrainingCollegeToken tct = this.iTrainingCollegeTokenService.checkTrainingCollegeToken(token);
		List<TrainingCollege> lstTrainCollege = this.iTrainingCollegeService.checkTrainingCollegeToken(token);
		
		if(lstTrainCollege != null && !lstTrainCollege.isEmpty()){
			
			TrainingCollege tc = lstTrainCollege.get(0);
			
			
			int projectId = 0;
			int subjectId = 0;
			int teacherId = 0;
			
			if(request.getParameter("projectId") != null && !"".equals(request.getParameter("projectId"))){
				projectId = Integer.parseInt(request.getParameter("projectId"));
				
				Project pro = iProjectService.get(projectId);
				if(pro != null){
					if(pro.getStatus() > 2){//已经关闭的项目 不能写入学员成绩
						sb.append("{");
						sb.append("\"Result\":\"OK\"");
						sb.append(",");
						sb.append("\"Message\":\"项目已关闭，不能写入成绩\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;
					}
				}
				
			}else{
				sb.append("{");
				sb.append("\"Result\":\"FAIL\"");
				sb.append(",");
				sb.append("\"Message\":\"培训项目不能为空\"");
				sb.append("}");
				return;
			}
			
			if(request.getParameter("trainingSubjectId") != null && !"".equals(request.getParameter("trainingSubjectId"))){
				subjectId = Integer.parseInt(request.getParameter("trainingSubjectId"));
			}else{
				sb.append("{");
				sb.append("\"Result\":\"FAIL\"");
				sb.append(",");
				sb.append("\"Message\":\"培训学科不能为空\"");
				sb.append("}");
				return;
			}
			
			if(request.getParameter("teacherId") != null && !"".equals(request.getParameter("teacherId"))){
				teacherId = Integer.parseInt(request.getParameter("teacherId"));
			}else{
				sb.append("{");
				sb.append("\"Result\":\"FAIL\"");
				sb.append(",");
				sb.append("\"Message\":\"培训学员ID不能为空\"");
				sb.append("}");
				return;
			}
			
			Integer trainCollegeId = tc.getId();
			
			Map<String, Object> pagram = new HashMap<String, Object>();
			pagram.put("projectId", projectId);
			pagram.put("subjectId", subjectId);
			pagram.put("trainCollegeId", trainCollegeId);
			
			
			int trainingCount = this.iTeacherTrainingRecordsService.getTeacherRecordsCountByTid(teacherId, pagram);
			List trainRecord = this.iTeacherTrainingRecordsService.getTeacherRecordsByTid(teacherId, 0, trainingCount, null, pagram);
			
			if(trainRecord != null && !trainRecord.isEmpty()){
				
				Object[] obj = (Object[])trainRecord.get(0);
				TeacherTrainingRecords ttr = (TeacherTrainingRecords)obj[0];
				
				/*
				 * 培训状态为异动的学员成绩无法录入系统。不做任何操作
				 */
				if(ttr.getTrainingStatus() != 0){
					int trainingHour = 0;
					short trainingStatus = 0;
					String trainingReason = "";
					float trainingScore = 0;
					String certificate = "";
					
					if(request.getParameter("trainingHour") != null && !"".equals(request.getParameter("trainingHour"))){
						trainingHour = Integer.parseInt(request.getParameter("trainingHour"));
					}else{
						sb.append("{");
						sb.append("\"Result\":\"FAIL\"");
						sb.append(",");
						sb.append("\"Message\":\"培训学时不能为空\"");
						sb.append("}");
						return;
					}
					
					if(request.getParameter("trainingStatus") != null && !"".equals(request.getParameter("trainingStatus"))){
						trainingStatus = Short.parseShort(request.getParameter("trainingStatus"));
					}else{
						sb.append("{");
						sb.append("\"Result\":\"FAIL\"");
						sb.append(",");
						sb.append("\"Message\":\"培训状态不能为空\"");
						sb.append("}");
						return;
					}
					
					if(request.getParameter("trainingReason") != null && !"".equals(request.getParameter("trainingReason"))){
						trainingReason = request.getParameter("trainingReason");
					}
					
					if(request.getParameter("trainingScore") != null && !"".equals(request.getParameter("trainingScore"))){
						trainingScore = Float.parseFloat(request.getParameter("trainingScore"));
					}else{
						sb.append("{");
						sb.append("\"Result\":\"FAIL\"");
						sb.append(",");
						sb.append("\"Message\":\"学员培训成绩不能为空\"");
						sb.append("}");
						return;
					}
					
					if(request.getParameter("certificate") != null && !"".equals(request.getParameter("certificate"))){
						certificate = request.getParameter("certificate");
					}
					
					
					
					ttr.setTrainingOnlineHour(trainingHour);
					ttr.setTrainingStatus(trainingStatus);
					ttr.setTrainingReason(trainingReason);
					ttr.setTrainingScore(trainingScore);
					ttr.setCertificate(certificate);
					ttr.setTrainingRegistertime(new Timestamp((new Date()).getTime()));
					ttr.setTrainingClasshour(0);
				}
				
				try {
					//更新操作
					this.iTeacherTrainingRecordsService.update(ttr);
					sb.append("{");
					sb.append("\"Result\":\"OK\"");
					sb.append(",");
					sb.append("\"Message\":\"成功写入成绩\"");
					sb.append("}");
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					sb.append("{");
					sb.append("\"Result\":\"FAIL\"");
					sb.append(",");
					sb.append("\"Message\":\"成绩写入异常\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				
			}else{
				sb.append("{");
				sb.append("\"Result\":\"FAIL\"");
				sb.append(",");
				sb.append("\"Message\":\"无此学员相关报名信息\"");
				sb.append("}");
			}
			
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"无效的授权码，请求失败\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}
	
	/**
	 * 自动生成第三封培训平台登录令牌
	 * 令牌由第三方培训平台报备的ID+NAME由MD5编码加密后获得
	 */
	public void createTrainCollegeToken(){
		initServlert();
		
		List<TrainingCollege> lstCollege = this.iTrainingCollegeService.findAll();
		if(lstCollege != null && !lstCollege.isEmpty()){
			for(TrainingCollege tc: lstCollege){
				String md5Str = tc.getId()+"_"+tc.getName();
				String token = MD5Util.string2MD5(md5Str);
				tc.setToken(token);
				this.iTrainingCollegeService.update(tc);
			}
		}
		String str = "1_新疆师范大学";
		String md5Str = MD5Util.string2MD5(str);
		System.out.println(md5Str);
	}
	
	/**
	 * 单个教师信息校对接口
	 * 用于在教师一键登入第三方培训平台时，进行教师信息获取并比对.
	 * 传入的参数有四个，一个是培训方的登录令牌，一个是培训项目ID，一个是培训学科ID，一个是教师的ID信息
	 * 
	 */
	public void checkTeacherInfo(){
//		initServlert();
		initServlert();
		StringBuilder sb = new StringBuilder();
		String token = "";
		if(request.getParameter("token") != null && !"".equals(request.getParameter("token"))){
			token = request.getParameter("token");
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"授权码为空，请求失败\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
//		TrainingCollegeToken tct = this.iTrainingCollegeTokenService.checkTrainingCollegeToken(token);
		List<TrainingCollege> lstTrainCollege = this.iTrainingCollegeService.checkTrainingCollegeToken(token);
		
		if(lstTrainCollege != null && !lstTrainCollege.isEmpty()){
			
			TrainingCollege tc = lstTrainCollege.get(0);
			
			int projectId = 0;
			if(request.getParameter("projectId") != null && !"".equals(request.getParameter("projectId"))){
				projectId = Integer.parseInt(request.getParameter("projectId"));
				
				Project pro = iProjectService.get(projectId);
				if(pro != null){
					if(pro.getStatus() > 2){//已关闭的项目不能获取教师信息
						sb.append("{");
						sb.append("\"Result\":\"OK\"");
						sb.append(",");
						sb.append("\"Message\":\"项目已关闭\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;
					}
				}
			}
			
			int subjectId = 0;
			if(request.getParameter("trainingSubjectId") != null && !"".equals(request.getParameter("trainingSubjectId"))){
				subjectId = Integer.parseInt(request.getParameter("trainingSubjectId"));
			}
			
			int teacherId = 0;
			if(request.getParameter("teacherId") != null && !"".equals(request.getParameter("teacherId"))){
				teacherId = Integer.parseInt(request.getParameter("teacherId"));
			}
			
			Map<String, Object> pagram = new HashMap<String, Object>();
			pagram.put("projectId", projectId);
			pagram.put("subjectId", subjectId);
			pagram.put("trainCollegeId", tc.getId());
			pagram.put("finalStatus", 2);
			
			List records = this.iTeacherTrainingRecordsService.getTeacherRecordsByTid(teacherId, 0, DictionyMap.maxPageSize, null, pagram);
			
			if(records != null && !records.isEmpty()){
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"请求成功\"");
				sb.append(",");
				
				Object[] obj = (Object[])records.get(0);
//				System.out.println(records.size());
				TeacherTrainingRecords ttr = (TeacherTrainingRecords)obj[0];
				TrainingSubject ts = ttr.getTrainingSubject();
				Teacher teacher = ttr.getTeacher();
//				Teacher teacher = (Teacher)obj[1];
				Project p = (Project)obj[1];
				
				sb.append("\"Records\":{");
				sb.append("\"teacherId\":"+teacher.getId());
				sb.append(",");
				sb.append("\"teacherName\":"+"\""+teacher.getName()+"\"");
				sb.append(",");
				sb.append("\"teacherIdcard\":"+"\""+teacher.getIdcard()+"\"");
				sb.append(",");
				sb.append("\"teacherAge\":"+IdCardUtil.getAgeByBirthday(teacher.getBirthday()));
				sb.append(",");
				sb.append("\"teacherSex\":"+"\""+teacher.getSex()+"\"");
				sb.append(",");
				sb.append("\"teacherPhone\":"+"\""+teacher.getMobile()+"\"");
				sb.append(",");
				String areasString = "";
				List<String> lstA = iAreaService.getParentNodes(teacher.getArea().getCode());
				if (lstA.size() > 0) {
					List<String> tt = new ArrayList<>();
					for (int o = lstA.size() - 1; o >= 0; o--) {
						tt.add(lstA.get(o));
					}
	
					for (String string : tt) {
						areasString += string + ">>";
					}
				}
				if (areasString.length() > 2) {
					sb.append("\"teacherArea\":\""+areasString.substring(0, areasString.length() - 2)+"\"");
				}else{
					sb.append("\"teacherArea\":\""+areasString.substring(0, areasString.length() - 2)+"\"");
				}
				sb.append(",");
				sb.append("\"teacherOrganization\":\""+teacher.getOrganization().getName()+"\"");
				sb.append(",");
				
				// 获取主要教学语言
				String hqlString = "from TeachingLanguage where teacher=" + teacher.getId() + " and isprime=1";
	
				List<TeachingLanguage> lsTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
									   
				if (lsTeachingLanguages.size() > 0) {
					TeachingLanguage teachingLanguage = lsTeachingLanguages.get(0);
					sb.append("\"teachingLanguageId\":"+teachingLanguage.getLanguage().getId());
					sb.append(",");
					sb.append("\"teachingLanguage\":\""+teachingLanguage.getLanguage().getName()+"\"");
					sb.append(",");
				}
	
				// 获取主要教学学段
				hqlString = "from TeachingGrade where teacher=" + teacher.getId() + " and isprime=1";
				List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
				if (lstTeachingGrades.size() > 0) {
					TeachingGrade teachingGrade = lstTeachingGrades.get(0);
					sb.append("\"teachingGradeId\":"+teachingGrade.getGrade().getId());
					sb.append(",");
					sb.append("\"teachingGrade\":\""+teachingGrade.getGrade().getName()+"\"");
					sb.append(",");
				}
	
				// 获取主要教学学科
				hqlString = "from TeachingSubject where teacher=" + teacher.getId() + " and isprime=1";
				List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
				if (lsttTeachingSubjects.size() > 0) {
					TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
					sb.append("\"teachingSubjectId\":"+teachingSubject.getSubject().getId());
					sb.append(",");
					sb.append("\"teachingSubject\":\""+teachingSubject.getSubject().getName()+"\"");
					sb.append(",");
				}
				
				sb.append("\"projectId\":"+p.getId());
				sb.append(",");
				sb.append("\"projectName\":"+"\""+p.getName()+"\"");
				sb.append(",");
				sb.append("\"trainingSubjectId\":"+ts.getId());
				sb.append(",");
				sb.append("\"trainingSubjectName\":"+"\""+ts.getName()+"\"");
				
				sb.append("}}");
				
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				
			}else{
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"暂无该教师信息\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}
//			Map<String, Object> pagram = new HashMap<String, Object>();
//			//其他筛选参数
//			//项目ID
//			int projectId = 0;
//			if(request.getParameter("projectId")!=null){
//				projectId = Integer.parseInt(request.getParameter("projectId"));
//				pagram.put("projectId", projectId);
//			}
//			//学科ID
//			int subjectId = 0;
//			if(request.getParameter("subjectId")!=null){
//				projectId = Integer.parseInt(request.getParameter("subjectId"));
//				pagram.put("subjectId", subjectId);
//			}
//			//承训单位ID
//			int trainCollegeId = 0;
//			if(request.getParameter("trainCollegeId")!=null){
//				projectId = Integer.parseInt(request.getParameter("trainCollegeId"));
//				pagram.put("trainCollegeId", trainCollegeId);
//			}
//			Teacher teacher = iTeacherService.get(teacherId);
//			
//			if(teacher != null){
//				
//				sb.append("{");
//				sb.append("\"Result\":\"OK\"");
//				sb.append(",");
//				sb.append("\"Message\":\"请求成功\"");
//				sb.append(",");
//				sb.append("\"Records\":{");
////				sb.append("{");
//				sb.append("\"teacherId\":"+teacher.getId());
//				sb.append(",");
//				sb.append("\"teacherName\":"+"\""+teacher.getName()+"\"");
//				sb.append(",");
//				sb.append("\"teacherIdcard\":"+"\""+teacher.getIdcard()+"\"");
//				sb.append(",");
//				sb.append("\"teacherAge\":"+IdCardUtil.getAgeByBirthday(teacher.getBirthday()));
//				sb.append(",");
//				sb.append("\"teacherSex\":"+"\""+teacher.getSex()+"\"");
//				sb.append(",");
//				sb.append("\"teacherPhone\":"+"\""+teacher.getMobile()+"\"");
//				sb.append(",");
//				String areasString = "";
//				List<String> lstA = iAreaService.getParentNodes(teacher.getArea().getCode());
//				if (lstA.size() > 0) {
//					List<String> tt = new ArrayList<>();
//					for (int o = lstA.size() - 1; o >= 0; o--) {
//						tt.add(lstA.get(o));
//					}
//	
//					for (String string : tt) {
//						areasString += string + ">>";
//					}
//				}
//				if (areasString.length() > 2) {
//					sb.append("\"teacherArea\":\""+areasString.substring(0, areasString.length() - 2)+"\"");
//				}else{
//					sb.append("\"teacherArea\":\""+areasString.substring(0, areasString.length() - 2)+"\"");
//				}
//				sb.append(",");
//				sb.append("\"teacherOrganization\":\""+teacher.getOrganization().getName()+"\"");
//				
//				// 获取主要教学语言
//				String hqlString = "from TeachingLanguage where teacher=" + teacher.getId() + " and isprime=1";
//	
//				List<TeachingLanguage> lsTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
//									   
//				if (lsTeachingLanguages.size() > 0) {
//					TeachingLanguage teachingLanguage = lsTeachingLanguages.get(0);
//					sb.append("\"teachingLanguageId\":"+teachingLanguage.getLanguage().getId());
//					sb.append(",");
//					sb.append("\"teachingLanguage\":\""+teachingLanguage.getLanguage().getName()+"\"");
//					sb.append(",");
//				}
//	
//				// 获取主要教学学段
//				hqlString = "from TeachingGrade where teacher=" + teacher.getId() + " and isprime=1";
//				List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
//				if (lstTeachingGrades.size() > 0) {
//					TeachingGrade teachingGrade = lstTeachingGrades.get(0);
//					sb.append("\"teachingGradeId\":"+teachingGrade.getGrade().getId());
//					sb.append(",");
//					sb.append("\"teachingGrade\":\""+teachingGrade.getGrade().getName()+"\"");
//					sb.append(",");
//				}
//	
//				// 获取主要教学学科
//				hqlString = "from TeachingSubject where teacher=" + teacher.getId() + " and isprime=1";
//				List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
//				if (lsttTeachingSubjects.size() > 0) {
//					TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
//					sb.append("\"teachingSubjectId\":"+teachingSubject.getSubject().getId());
//					sb.append(",");
//					sb.append("\"teachingSubject\":\""+teachingSubject.getSubject().getName()+"\"");
//					sb.append(",");
//				}
//				
//				sb.append("}}");
//				Utlity.ResponseWrite(sb.toString(), "application/json", response);
////				return;
//
//			}else{
//				sb.append("{");
//				sb.append("\"Result\":\"OK\"");
//				sb.append(",");
//				sb.append("\"Message\":\"暂无该教师信息\"");
//				sb.append("}");
//				Utlity.ResponseWrite(sb.toString(), "application/json", response);
////				return;
//			}
			
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"无效的授权码，请求失败\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
	}
	
	/**
	 * 教师测评信息获取接口
	 * 用于第三方培训平台获取已测评教师的测评结果信息
	 * 必须是当年的测评信息
	 * 三个参数token、projectId、teacherId
	 */
	public void EvaluationInfo(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		String token = "";
		if(request.getParameter("token") != null && !"".equals(request.getParameter("token"))){
			token = request.getParameter("token");
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"授权码为空，请求失败\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
//		TrainingCollegeToken tct = this.iTrainingCollegeTokenService.checkTrainingCollegeToken(token);
		List<TrainingCollege> lstTrainCollege = this.iTrainingCollegeService.checkTrainingCollegeToken(token);
		
		if(lstTrainCollege != null && !lstTrainCollege.isEmpty()){
			
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"无效的授权码，请求失败\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
	}
//	public void createTeacherUUID(){
//		initServlert();
//		
//		List<Teacher> lstTeacher = this.iTeacherService.findAll();
//		for(Teacher t: lstTeacher){
//			String md5Str = t.getId()+"_"+t.getName();
//			String teacherUuid = MD5Util.string2MD5(md5Str);
//			t.setUuid(teacherUuid);
//		}
//		
//		
//		String teacherUuid = "48138_刘运涛";
//		String md5str = MD5Util.convertMD5(teacherUuid);
//		
//		System.out.println(md5str);
//	}
	
	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}

	public IProjectTypeService getiProjectTypeService() {
		return iProjectTypeService;
	}

	public void setiProjectTypeService(IProjectTypeService iProjectTypeService) {
		this.iProjectTypeService = iProjectTypeService;
	}


	public ITrainingCollegeService getiTrainingCollegeService() {
		return iTrainingCollegeService;
	}

	public void setiTrainingCollegeService(
			ITrainingCollegeService iTrainingCollegeService) {
		this.iTrainingCollegeService = iTrainingCollegeService;
	}

	public ITrainingSubjectService getiTrainingSubjectService() {
		return iTrainingSubjectService;
	}

	public void setiTrainingSubjectService(
			ITrainingSubjectService iTrainingSubjectService) {
		this.iTrainingSubjectService = iTrainingSubjectService;
	}

	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}

	public void setiProjectApplyService(IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
	}

	public ITeacherService getiTeacherService() {
		return iTeacherService;
	}

	public void setiTeacherService(ITeacherService iTeacherService) {
		this.iTeacherService = iTeacherService;
	}

	public ITeacherTrainingRecordsService getiTeacherTrainingRecordsService() {
		return iTeacherTrainingRecordsService;
	}

	public void setiTeacherTrainingRecordsService(
			ITeacherTrainingRecordsService iTeacherTrainingRecordsService) {
		this.iTeacherTrainingRecordsService = iTeacherTrainingRecordsService;
	}

	public IAreaService getiAreaService() {
		return iAreaService;
	}

	public void setiAreaService(IAreaService iAreaService) {
		this.iAreaService = iAreaService;
	}

	public ITeachingLanguageService getiTeachingLanguageService() {
		return iTeachingLanguageService;
	}

	public void setiTeachingLanguageService(
			ITeachingLanguageService iTeachingLanguageService) {
		this.iTeachingLanguageService = iTeachingLanguageService;
	}

	public ITeachingGradeService getiTeachingGradeService() {
		return iTeachingGradeService;
	}

	public void setiTeachingGradeService(ITeachingGradeService iTeachingGradeService) {
		this.iTeachingGradeService = iTeachingGradeService;
	}

	public ITeachingSubjectService getiTeachingSubjectService() {
		return iTeachingSubjectService;
	}

	public void setiTeachingSubjectService(
			ITeachingSubjectService iTeachingSubjectService) {
		this.iTeachingSubjectService = iTeachingSubjectService;
	}

//	public ITrainingCollegeTokenService getiTrainingCollegeTokenService() {
//		return iTrainingCollegeTokenService;
//	}
//
//	public void setiTrainingCollegeTokenService(
//			ITrainingCollegeTokenService iTrainingCollegeTokenService) {
//		this.iTrainingCollegeTokenService = iTrainingCollegeTokenService;
//	}
	
	
	
	

}
