package cn.zeppin.train.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.zeppin.action.admin.AssignTeacherTaskAction;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.Hsdtest;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectCycle;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TeachingGrade;
import cn.zeppin.entity.TeachingLanguage;
import cn.zeppin.entity.TeachingSubject;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IHsdtestService;
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
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;

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
	private IAreaService iAreaService;// ????????????

	// private ITrainingAdminService iTrainingAdminService;

	private ITeacherService iTeacherService;

	private ITeacherTrainingRecordsService iTeacherTrainingRecordsService;

	// private ITrainingCollegeTokenService iTrainingCollegeTokenService;
	private ITeachingLanguageService iTeachingLanguageService;// ??????????????????
	private ITeachingGradeService iTeachingGradeService;// ??????????????????
	private ITeachingSubjectService iTeachingSubjectService;// ??????????????????

	private IHsdtestService iHsdTestService;

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * ???????????????????????????????????????????????????????????? ????????????????????????????????????????????????????????????????????? ????????????????????????????????????????????????????????????
	 */
	public void ProjectInfo() {

		initServlert();

		StringBuilder sb = new StringBuilder();
		String token = "";
		if (request.getParameter("token") != null
				&& !"".equals(request.getParameter("token"))) {
			token = request.getParameter("token");
		} else {
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"??????????????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		// TrainingCollegeToken tct =
		// this.iTrainingCollegeTokenService.checkTrainingCollegeToken(token);
		List<TrainingCollege> lstTrainCollege = this.iTrainingCollegeService
				.checkTrainingCollegeToken(token);
		if (lstTrainCollege != null && lstTrainCollege.size() > 0) {
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");

			TrainingCollege tc = lstTrainCollege.get(0);

			List lstProjectApplies = this.iProjectApplyService
					.getProjectInfoByCollege(tc.getId());
			if (lstProjectApplies != null && !lstProjectApplies.isEmpty()) {
				int totalCount = lstProjectApplies.size();
				sb.append("\"Message\":\"????????????\"");
				sb.append(",");
				sb.append("\"TotalCount\":" + totalCount);
				sb.append(",");
				sb.append("\"Records\":[");

				for (int i = 0; i < lstProjectApplies.size(); i++) {
					Object[] obj = (Object[]) lstProjectApplies.get(i);
					ProjectApply pa = (ProjectApply) obj[0];
					Project p = (Project) obj[1];
					TrainingSubject ts = pa.getTrainingSubject();
					ProjectCycle pc = p.getProjectCycle();

					sb.append("{");
					sb.append("\"projectId\":" + p.getId());
					sb.append(",");
					sb.append("\"projectName\":" + "\"" + p.getName() + "\"");
					sb.append(",");
					sb.append("\"projectCycleId\":" + pc.getId());
					sb.append(",");
					sb.append("\"projectCycleName\":" + "\"" + pc.getName() + "\"");
					sb.append(",");
					sb.append("\"year\":" + p.getYear());
					sb.append(",");
					sb.append("\"trainingSubjectId\":" + ts.getId());
					sb.append(",");
					sb.append("\"trainingSubjectName\":" + "\"" + ts.getName()
							+ "\"");
					sb.append(",");
					sb.append("\"approveNumber\":" + pa.getApproveNumber());
					//20170327????????????????????????
					sb.append(",");
					sb.append("\"classHoursLevel\":[");
					StringBuilder sbclass = new StringBuilder();
					
					List<Map> studyhourList = JSONUtils.json2list(pa.getStudyhour(), Map.class);
					if(studyhourList != null){
						for(Map studyhour : studyhourList){
							Integer value = Integer.valueOf(studyhour.get("value").toString());
							if(value > 0){
								sbclass.append("{");
								sbclass.append("\"classHoursLevelName\":\"" + studyhour.get("name").toString() + "\"");
								sbclass.append(",");
								if(pa.getProjectCycle().getId() > 1) {
									value = 0;
								}
								sbclass.append("\"classHoursLevelNum\":"+value);
								sbclass.append("},");
							}
						}
						if(sbclass.length() > 0){
							sbclass.delete(sbclass.length() - 1, sbclass.length());
						}
						sb.append(sbclass.toString());
					}
					sb.append("]");
					sb.append("},");
				}

				sb.delete(sb.length() - 1, sb.length());
				sb.append("]}");

			} else {
				sb.append("\"Message\":\"?????????????????????????????????\"");
				sb.append("}");
			}

			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		} else {
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"?????????????????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}

	}

	/**
	 * ??????????????????????????????????????????????????? ????????????????????????????????????????????? ???????????????
	 */
	public void TeacherInfo() {

		initServlert();
		StringBuilder sb = new StringBuilder();
		String token = "";
		if (request.getParameter("token") != null
				&& !"".equals(request.getParameter("token"))) {
			token = request.getParameter("token");
		} else {
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"??????????????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		// TrainingCollegeToken tct =
		// this.iTrainingCollegeTokenService.checkTrainingCollegeToken(token);
		List<TrainingCollege> lstTrainCollege = this.iTrainingCollegeService
				.checkTrainingCollegeToken(token);

		if (lstTrainCollege != null && !lstTrainCollege.isEmpty()) {

			TrainingCollege tc = lstTrainCollege.get(0);

			int projectId = 0;
			if (request.getParameter("projectId") != null
					&& !"".equals(request.getParameter("projectId"))) {
				projectId = Integer.parseInt(request.getParameter("projectId"));

				Project pro = iProjectService.get(projectId);
				if (pro != null) {
					if (pro.getStatus() > 2) {// ??????????????????????????????????????????
						sb.append("{");
						sb.append("\"Result\":\"FAIL\"");
						sb.append(",");
						sb.append("\"Message\":\"???????????????\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json",
								response);
						return;
					}
				}
			}

			int trainingSubjectId = 0;
			if (request.getParameter("trainingSubjectId") != null
					&& !"".equals(request.getParameter("trainingSubjectId"))) {
				trainingSubjectId = Integer.parseInt(request
						.getParameter("trainingSubjectId"));
			}

			int trainingUnit = tc.getId();

			List<ProjectType> lityps = this.iProjectTypeService.findAll();

			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");

			List<Area> listArea = this.iAreaService.findAll();
			Map<String, Area> mapArea = new HashMap<String, Area>();
			if(listArea != null && !listArea.isEmpty()) {
				for(Area a : listArea) {
					mapArea.put(a.getCode(), a);
				}
			}
			int totalCount = this.iTeacherTrainingRecordsService
					.getTrainAduTeacherCount(null, projectId, trainingSubjectId,
							trainingUnit, 0, 2, null, lityps, null,0,0);
			List lstTeacherTrainRecords = this.iTeacherTrainingRecordsService
					.getTrainAduTeacher(null, projectId, trainingSubjectId,
							trainingUnit, 0, 2, null, lityps, 0, totalCount,
							null,0,0);
			if (lstTeacherTrainRecords != null
					&& !lstTeacherTrainRecords.isEmpty()) {

				sb.append("\"Message\":\"????????????\"");
				sb.append(",");
				sb.append("\"Records\":[");

				int otherCount = 0;// ????????????????????????
				for (int i = 0; i < lstTeacherTrainRecords.size(); i++) {
//					Object[] obj = (Object[]) lidata.get(i);

					TeacherTrainingRecords ttr = (TeacherTrainingRecords) lstTeacherTrainRecords.get(i);
//					Teacher th = (Teacher) obj[1];
					Teacher t = ttr.getTeacher();
					TrainingSubject ts = ttr.getTrainingSubject();

//					Teacher t = (Teacher) obj[1];
					Project p = ttr.getProject();

					/*
					 * ???????????????????????????????????????
					 */
					if (ttr.getTrainingStatus() == 0) {
						otherCount++;
						continue;
					}

					sb.append("{"); 
					sb.append("\"teacherId\":" + t.getId());
					sb.append(",");
					sb.append("\"teacherName\":" + "\"" + t.getName() + "\"");
					sb.append(",");
					sb.append("\"teacherIdcard\":" + "\"" + t.getIdcard()
							+ "\"");
					sb.append(",");
					sb.append("\"teacherAge\":"
							+ IdCardUtil.getAgeByBirthday(t.getBirthday()));
					sb.append(",");
					sb.append("\"teacherSex\":" + "\"" + t.getSex() + "\"");
					sb.append(",");
					String mobile = "";
					if(t.getMobile() != null){
						mobile = t.getMobile();
					}
					sb.append("\"teacherPhone\":" + "\"" + mobile + "\"");
					sb.append(",");
					String areasString = "";
					List<String> lstA = getParentNodes(t.getOrganization().getArea()
							.getCode(), mapArea);
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
						sb.append("\"teacherArea\":\""
								+ areasString.substring(0,
										areasString.length() - 2) + "\"");
					} else {
						sb.append("\"teacherArea\":\"" + areasString + "\"");
					}
					sb.append(",");
					sb.append("\"teacherOrganization\":\""
							+ t.getOrganization().getName() + "\"");
					sb.append(",");

					// ????????????????????????
					Set<TeachingLanguage> setLanguages = t.getTeachingLanguages();
					Set<TeachingGrade> setGrades = t.getTeachingGrades();
					Set<TeachingSubject> setSubjects = t.getTeachingSubjects();
					if(setLanguages != null && setLanguages.size()>0){
						TeachingLanguage teachingLanguage = null;
						for(TeachingLanguage tl:setLanguages){
							if(tl.getIsprime()){
								teachingLanguage = tl;
							}
						}
						if(teachingLanguage != null){
							sb.append("\"teachingLanguageId\":"+ teachingLanguage.getLanguage().getId());
							sb.append(",");
							sb.append("\"teachingLanguage\":\""+ teachingLanguage.getLanguage().getName()
									+ "\"");
							sb.append(",");
						}else{
							sb.append("\"teachingLanguageId\":0");
							sb.append(",");
							sb.append("\"teachingLanguage\":\"???\"");
							sb.append(",");
						}
					}else{
						sb.append("\"teachingLanguageId\":0");
						sb.append(",");
						sb.append("\"teachingLanguage\":\"???\"");
						sb.append(",");
					}
					
					if(setGrades != null && setGrades.size()>0){
						TeachingGrade teachingGrade = null;
						for(TeachingGrade tl:setGrades){
							if(tl.getIsprime()){
								teachingGrade = tl;
							}
						}
						if(teachingGrade != null){
							sb.append("\"teachingGradeId\":"+ teachingGrade.getGrade().getId());
							sb.append(",");
							sb.append("\"teachingGrade\":\""+ teachingGrade.getGrade().getName()
									+ "\"");
							sb.append(",");
						}else{
							sb.append("\"teachingGradeId\":0");
							sb.append(",");
							sb.append("\"teachingGrade\":\"???\"");
							sb.append(",");
						}
					}else{
						sb.append("\"teachingGradeId\":0");
						sb.append(",");
						sb.append("\"teachingGrade\":\"???\"");
						sb.append(",");
					}
					
					if(setSubjects != null && setSubjects.size()>0){
						TeachingSubject teachingSubject = null;
						for(TeachingSubject tl:setSubjects){
							if(tl.getIsprime()){
								teachingSubject = tl;
							}
						}
						if(teachingSubject != null){
							sb.append("\"teachingSubjectId\":"+ teachingSubject.getSubject().getId());
							sb.append(",");
							sb.append("\"teachingSubject\":\""+ teachingSubject.getSubject().getName()
									+ "\"");
							sb.append(",");
						}else{
							sb.append("\"teachingSubjectId\":0");
							sb.append(",");
							sb.append("\"teachingSubject\":\"???\"");
							sb.append(",");
						}
					}else{
						sb.append("\"teachingSubjectId\":0");
						sb.append(",");
						sb.append("\"teachingSubject\":\"???\"");
						sb.append(",");
					}
					
//					String hqlString = "from TeachingLanguage where teacher="
//							+ t.getId() + " and isprime=1";
//
//					List<TeachingLanguage> lsTeachingLanguages = iTeachingLanguageService
//							.getListByHSQL(hqlString);
//
//					if (lsTeachingLanguages != null && lsTeachingLanguages.size() > 0) {
//						TeachingLanguage teachingLanguage = lsTeachingLanguages
//								.get(0);
//						sb.append("\"teachingLanguageId\":"
//								+ teachingLanguage.getLanguage().getId());
//						sb.append(",");
//						sb.append("\"teachingLanguage\":\""
//								+ teachingLanguage.getLanguage().getName()
//								+ "\"");
//						sb.append(",");
//					}else{
//						sb.append("\"teachingLanguageId\":0");
//						sb.append(",");
//						sb.append("\"teachingLanguage\":\"???\"");
//						sb.append(",");
//					}
//
//					// ????????????????????????
//					hqlString = "from TeachingGrade where teacher=" + t.getId()
//							+ " and isprime=1";
//					List<TeachingGrade> lstTeachingGrades = iTeachingGradeService
//							.getListByHSQL(hqlString);
//					if (lstTeachingGrades != null && lstTeachingGrades.size() > 0) {
//						TeachingGrade teachingGrade = lstTeachingGrades.get(0);
//						sb.append("\"teachingGradeId\":"
//								+ teachingGrade.getGrade().getId());
//						sb.append(",");
//						sb.append("\"teachingGrade\":\""
//								+ teachingGrade.getGrade().getName() + "\"");
//						sb.append(",");
//					}else{
//						sb.append("\"teachingGradeId\":0");
//						sb.append(",");
//						sb.append("\"teachingGrade\":\"???\"");
//						sb.append(",");
//					}
//
//
//					// ????????????????????????
//					hqlString = "from TeachingSubject where teacher="
//							+ t.getId() + " and isprime=1";
//					List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService
//							.getListByHSQL(hqlString);
//					if (lsttTeachingSubjects != null && lsttTeachingSubjects.size() > 0) {
//						TeachingSubject teachingSubject = lsttTeachingSubjects
//								.get(0);
//						sb.append("\"teachingSubjectId\":"
//								+ teachingSubject.getSubject().getId());
//						sb.append(",");
//						sb.append("\"teachingSubject\":\""
//								+ teachingSubject.getSubject().getName() + "\"");
//						sb.append(",");
//					}else{
//						sb.append("\"teachingSubjectId\":0");
//						sb.append(",");
//						sb.append("\"teachingSubject\":\"???\"");
//						sb.append(",");
//					}

					sb.append("\"projectId\":" + p.getId());
					sb.append(",");
					sb.append("\"projectName\":" + "\"" + p.getName() + "\"");
					sb.append(",");
					sb.append("\"trainingSubjectId\":" + ts.getId());
					sb.append(",");
					sb.append("\"trainingSubjectName\":" + "\"" + ts.getName()
							+ "\"");

					sb.append("},");

				}
				sb.delete(sb.length() - 1, sb.length());

				totalCount = totalCount - otherCount;

				sb.append("]");
				sb.append(",");
				sb.append("\"TotalCount\":" + totalCount);
				sb.append("}");
			} else {
				sb.append("\"Message\":\"??????????????????????????????\"");
				sb.append("}");
			}

			Utlity.ResponseWrite(sb.toString(), "application/json", response);

		} else {
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"?????????????????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}

	}
	
	public List<String> getParentNodes(String code, Map<String, Area> mapArea) {
		List<String> lstAreaStrings = new ArrayList<String>();
		return getAreaName(code,lstAreaStrings,mapArea);
    }

	public List<String> getAreaName(String code, List<String> lstAreaStrings, Map<String, Area> mapArea) {
		Area area = mapArea.get(code);
		if (area != null) {
			lstAreaStrings.add(area.getName());
			if (area.getParentcode() != "0") {
				getAreaName(area.getParentcode(), lstAreaStrings, mapArea);
			}
		}
		return lstAreaStrings;
	}

	/**
	 * ??????????????????????????????????????????????????? ???????????????????????????????????? ??????????????????????????????teacherTrainRecords???
	 */
	public void inputTrainingInfo() {

		initServlert();
		StringBuilder sb = new StringBuilder();
		
//		sb.append("{");
//		sb.append("\"Result\":\"FAIL\"");
//		sb.append(",");
//		sb.append("\"Message\":\"???????????????????????????????????????????????????????????????????????????\"");
//		sb.append("}");
//		Utlity.ResponseWrite(sb.toString(), "application/json",
//				response);
//		return;
		
		String token = "";

		if (request.getParameter("token") != null
				&& !"".equals(request.getParameter("token"))) {
			token = request.getParameter("token");
		} else {
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"??????????????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		// TrainingCollegeToken tct =
		// this.iTrainingCollegeTokenService.checkTrainingCollegeToken(token);
		List<TrainingCollege> lstTrainCollege = this.iTrainingCollegeService
				.checkTrainingCollegeToken(token);

		if (lstTrainCollege != null && !lstTrainCollege.isEmpty()) {

			TrainingCollege tc = lstTrainCollege.get(0);

			int projectId = 0;
			int subjectId = 0;
			int teacherId = 0;

			if (request.getParameter("projectId") != null
					&& !"".equals(request.getParameter("projectId"))) {
				projectId = Integer.parseInt(request.getParameter("projectId"));

				Project pro = iProjectService.get(projectId);
				if (pro != null) {
					if (pro.getStatus() > 2) {// ????????????????????? ????????????????????????
						sb.append("{");
						sb.append("\"Result\":\"FAIL\"");
						sb.append(",");
						sb.append("\"Message\":\"????????????????????????????????????\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json",
								response);
						return;
					}
				}

			} else {
				sb.append("{");
				sb.append("\"Result\":\"FAIL\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
				return;
			}

			if (request.getParameter("trainingSubjectId") != null
					&& !"".equals(request.getParameter("trainingSubjectId"))) {
				subjectId = Integer.parseInt(request
						.getParameter("trainingSubjectId"));
			} else {
				sb.append("{");
				sb.append("\"Result\":\"FAIL\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
				return;
			}

			if (request.getParameter("teacherId") != null
					&& !"".equals(request.getParameter("teacherId"))) {
				teacherId = Integer.parseInt(request.getParameter("teacherId"));
			} else {
				sb.append("{");
				sb.append("\"Result\":\"FAIL\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????ID????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
				return;
			}

			Integer trainCollegeId = tc.getId();

			Map<String, Object> pagram = new HashMap<String, Object>();
			pagram.put("projectId", projectId);
			pagram.put("subjectId", subjectId);
			pagram.put("trainCollegeId", trainCollegeId);
			pagram.put("finalStatus", 2);

			int trainingCount = this.iTeacherTrainingRecordsService
					.getTeacherRecordsCountByTid(teacherId, pagram);
			List trainRecord = this.iTeacherTrainingRecordsService
					.getTeacherRecordsByTid(teacherId, 0, trainingCount, null,
							pagram);

			if (trainRecord != null && !trainRecord.isEmpty()) {

				Object[] obj = (Object[]) trainRecord.get(0);
				TeacherTrainingRecords ttr = (TeacherTrainingRecords) obj[0];
				
				/*
				 * ???????????????????????????????????????????????????????????????????????????
				 */
				if (ttr.getTrainingStatus() != 0) {
//					int trainingHour = 0;
					short trainingStatus = 0;
					String trainingReason = "";
					float trainingScore = 0;
					String certificate = "";

//					if (request.getParameter("trainingHour") != null
//							&& !"".equals(request.getParameter("trainingHour"))) {
//						trainingHour = Integer.parseInt(request
//								.getParameter("trainingHour"));
//					} else {
//						sb.append("{");
//						sb.append("\"Result\":\"FAIL\"");
//						sb.append(",");
//						sb.append("\"Message\":\"????????????????????????\"");
//						sb.append("}");
//						Utlity.ResponseWrite(sb.toString(), "application/json",
//								response);
//						return;
//					}

					if (request.getParameter("trainingStatus") != null
							&& !"".equals(request
									.getParameter("trainingStatus"))) {
						trainingStatus = Short.parseShort(request
								.getParameter("trainingStatus"));
					} else {
						sb.append("{");
						sb.append("\"Result\":\"FAIL\"");
						sb.append(",");
						sb.append("\"Message\":\"????????????????????????\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json",
								response);
						return;
					}

					if (request.getParameter("trainingReason") != null
							&& !"".equals(request
									.getParameter("trainingReason"))) {
						trainingReason = request.getParameter("trainingReason");
					}

					if (request.getParameter("trainingScore") != null
							&& !"".equals(request.getParameter("trainingScore"))) {
						trainingScore = Float.parseFloat(request
								.getParameter("trainingScore"));
					} else {
						sb.append("{");
						sb.append("\"Result\":\"FAIL\"");
						sb.append(",");
						sb.append("\"Message\":\"??????????????????????????????\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json",
								response);
						return;
					}

					if (request.getParameter("certificate") != null
							&& !"".equals(request.getParameter("certificate"))) {
						certificate = request.getParameter("certificate");
					}

//					ttr.setTrainingOnlineHour(trainingHour);
					ttr.setTrainingStatus(trainingStatus);
					ttr.setTrainingReason(trainingReason);
					ttr.setTrainingScore(trainingScore);
					ttr.setCertificate(certificate);
					ttr.setTrainingRegistertime(new Timestamp((new Date())
							.getTime()));
//					if(ttr.getTrainingClasshour() != null && ttr.getTrainingClasshour()>0){
//						//?????????????????????
//					}else{
//						ttr.setTrainingClasshour(0);
//					}
//					if(ttr.getTrainingOnlineHour() != null && ttr.getTrainingOnlineHour()>0){
//						//?????????????????????
//					}else{
//						ttr.setTrainingOnlineHour(trainingHour);
//					}
					
					// 9???12??? ????????????????????????
//					if(trainingStatus == 3){//?????????????????????????????????????????????
//						ProjectApply projectApply = iProjectApplyService.getByTrainingRecord(ttr);
//						ttr.setCentralize(projectApply.getCentralize());
//						ttr.setInformation(projectApply.getInformation());
//						ttr.setRegional(projectApply.getRegional());
//						ttr.setSchool(projectApply.getSchool());
//						ttr.setTotalhours(projectApply.getTotalhours());
//						ttr.setCredit(projectApply.getCredit());
//					}
					ProjectApply projectApply = iProjectApplyService.getByTrainingRecord(ttr);
					List<Map> paStudyhourList = JSONUtils.json2list(projectApply.getStudyhour(), Map.class);
					List<Map> resultList = new ArrayList<Map>();
					if((trainingStatus == 3 || trainingStatus == 5 || trainingStatus == 6) && trainingScore >= 60){//???????????????????????????
						for(Map paStudyhour : paStudyhourList){
							String shName = paStudyhour.get("name").toString();
							int paValue = Integer.valueOf(paStudyhour.get("value").toString());
							String value = request.getParameter(shName) == null ? "0" : request.getParameter(shName);
							if("".equals(value)){
								value = "0";
							}
							if(!Utlity.isNumeric(value)){
								sb.append("{");
								sb.append("\"Result\":\"FAIL\"");
								sb.append(",");
								sb.append("\"Message\":\""+paStudyhour.get("nameCN").toString()+"?????????????????????\"");
								sb.append("}");
								Utlity.ResponseWrite(sb.toString(), "application/json",
										response);
								return;
							}
							if(Integer.parseInt(value) > paValue){
								sb.append("{");
								sb.append("\"Result\":\"FAIL\"");
								sb.append(",");
								sb.append("\"Message\":\""+paStudyhour.get("nameCN").toString()+"????????????????????????????????????????????????\"");
								sb.append("}");
								Utlity.ResponseWrite(sb.toString(), "application/json",
										response);
								return;
							}
							
							Map<String, Object> resultMap = new HashMap<String, Object>();
							resultMap.put("name", paStudyhour.get("name").toString());
							resultMap.put("nameCN", paStudyhour.get("nameCN").toString());
							resultMap.put("value", Integer.parseInt(value));
							resultList.add(resultMap);
						}
						ttr.setStudyhour(JSONUtils.obj2json(resultList));
						ttr.setCredit(projectApply.getCredit());
					} else {
						for(Map paStudyhour : paStudyhourList){
							Map<String, Object> resultMap = new HashMap<String, Object>();
							resultMap.put("name", paStudyhour.get("name").toString());
							resultMap.put("nameCN", paStudyhour.get("nameCN").toString());
							resultMap.put("value", 0);
							resultList.add(resultMap);
						}
						ttr.setCredit(0);
					}
				}

				try {
					// ????????????
					this.iTeacherTrainingRecordsService.update(ttr);
					sb.append("{");
					sb.append("\"Result\":\"OK\"");
					sb.append(",");
					sb.append("\"Message\":\"??????????????????\"");
					sb.append("}");

				} catch (Exception e) {
					e.printStackTrace();
					sb.append("{");
					sb.append("\"Result\":\"FAIL\"");
					sb.append(",");
					sb.append("\"Message\":\"??????????????????\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json",
							response);
					return;
				}

			} else {
				sb.append("{");
				sb.append("\"Result\":\"FAIL\"");
				sb.append(",");
				sb.append("\"Message\":\"??????????????????????????????\"");
				sb.append("}");
			}

			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} else {
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"?????????????????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}

//	/**
//	 * ?????????????????????????????????
//	 */
//	private void newAddTeacherInfo(TeacherTrainingRecords teacherTrainingRecords) {
//		// ??????id????????????????????????
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("id", teacherTrainingRecords.getTeacher().getId() + "");
//		List<Teacher> teacherList = iTeacherService.getTeacherListByParams(map);
//		Teacher teacher = teacherList.get(0);
//		teacherTrainingRecords.setTeacherOrganization(teacher.getOrganization()
//				.getId());// ??????
//		teacherTrainingRecords.setJobTitle(teacher.getJobTitle());// ??????
//		teacherTrainingRecords.setEductionBackground(teacher
//				.getEductionBackground());// ??????
//		teacherTrainingRecords.setPolitics(teacher.getPolitics());// ????????????
//		// ????????????id??????TeachingSubject????????????
//		Map<String, Object> tsMap = new HashMap<String, Object>();
//		tsMap.put("id", teacher.getId() + "");
//		List<TeachingSubject> teachingSubjectList = iTeachingSubjectService
//				.getListByParams(tsMap);
//		for (TeachingSubject teachingSubject : teachingSubjectList) {
//			if (teachingSubject.getIsprime().equals("1")) {
//				teacherTrainingRecords.setSubject(teachingSubject.getSubject());// ????????????
//			}
//		}
//		// ????????????id??????TeachingGrade????????????
//		List<TeachingGrade> teachingGradeList = iTeachingGradeService
//				.getListByParams(tsMap);
//		for (TeachingGrade teachingGrade : teachingGradeList) {
//			if (teachingGrade.getIsprime().equals("1")) {
//				teacherTrainingRecords.setGrade(teachingGrade.getGrade());// ??????????????????
//			}
//		}
//		List<TeachingLanguage> teachingLanguageList = iTeachingLanguageService
//				.getListByParams(tsMap);
//		for (TeachingLanguage teachingLanguage : teachingLanguageList) {
//			if (teachingLanguage.getIsprime().equals("1")) {
//				teacherTrainingRecords.setLanguage(teachingLanguage
//						.getLanguage());// ??????????????????
//			}
//		}
//	}

	// /**
	// * ?????????????????????????????????????????????
	// * ???????????????????????????????????????ID+NAME???MD5?????????????????????
	// */
	// public void createTrainCollegeToken(){
	// initServlert();
	//
	// List<TrainingCollege> lstCollege =
	// this.iTrainingCollegeService.findAll();
	// if(lstCollege != null && !lstCollege.isEmpty()){
	// for(TrainingCollege tc: lstCollege){
	// String md5Str = tc.getId()+"_"+tc.getName();
	// String token = MD5Util.string2MD5(md5Str);
	// tc.setToken(token);
	// this.iTrainingCollegeService.update(tc);
	// }
	// }
	// String str = "1_??????????????????";
	// String md5Str = MD5Util.string2MD5(str);
	// System.out.println(md5Str);
	// }

	/**
	 * ?????????????????????????????? ???????????????????????????????????????????????????????????????????????????????????????.
	 * ????????????????????????????????????????????????????????????????????????????????????ID????????????????????????ID?????????????????????ID??????
	 * 
	 */
	public void checkTeacherInfo() {
		// initServlert();
		initServlert();
		StringBuilder sb = new StringBuilder();
		String token = "";
		if (request.getParameter("token") != null
				&& !"".equals(request.getParameter("token"))) {
			token = request.getParameter("token");
		} else {
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"??????????????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		// TrainingCollegeToken tct =
		// this.iTrainingCollegeTokenService.checkTrainingCollegeToken(token);
		List<TrainingCollege> lstTrainCollege = this.iTrainingCollegeService
				.checkTrainingCollegeToken(token);

		if (lstTrainCollege != null && !lstTrainCollege.isEmpty()) {

			TrainingCollege tc = lstTrainCollege.get(0);

			int projectId = 0;
			if (request.getParameter("projectId") != null
					&& !"".equals(request.getParameter("projectId"))) {
				projectId = Integer.parseInt(request.getParameter("projectId"));

//				Project pro = iProjectService.get(projectId);
//				if (pro != null) {
//					if (pro.getStatus() > 2) {// ??????????????????????????????????????????
//						sb.append("{");
//						sb.append("\"Result\":\"OK\"");
//						sb.append(",");
//						sb.append("\"Message\":\"???????????????\"");
//						sb.append("}");
//						Utlity.ResponseWrite(sb.toString(), "application/json",
//								response);
//						return;
//					}
//				}
			}

			int subjectId = 0;
			if (request.getParameter("trainingSubjectId") != null
					&& !"".equals(request.getParameter("trainingSubjectId"))) {
				subjectId = Integer.parseInt(request
						.getParameter("trainingSubjectId"));
			}

			int teacherId = 0;
			if (request.getParameter("teacherId") != null
					&& !"".equals(request.getParameter("teacherId"))) {
				teacherId = Integer.parseInt(request.getParameter("teacherId"));
			}

			Map<String, Object> pagram = new HashMap<String, Object>();
			pagram.put("projectId", projectId);
			pagram.put("subjectId", subjectId);
			pagram.put("trainCollegeId", tc.getId());
			pagram.put("finalStatus", 2);

			List records = this.iTeacherTrainingRecordsService
					.getTeacherRecordsByTid(teacherId, 0,
							DictionyMap.maxPageSize, null, pagram);

			if (records != null && !records.isEmpty()) {
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????\"");
				sb.append(",");

				Object[] obj = (Object[]) records.get(0);
				// System.out.println(records.size());
				TeacherTrainingRecords ttr = (TeacherTrainingRecords) obj[0];
				TrainingSubject ts = ttr.getTrainingSubject();
				Teacher teacher = ttr.getTeacher();
				// Teacher teacher = (Teacher)obj[1];
				Project p = (Project) obj[1];

				sb.append("\"Records\":{");
				sb.append("\"teacherId\":" + teacher.getId());
				sb.append(",");
				sb.append("\"teacherName\":" + "\"" + teacher.getName() + "\"");
				sb.append(",");
				sb.append("\"teacherIdcard\":" + "\"" + teacher.getIdcard()
						+ "\"");
				sb.append(",");
				sb.append("\"teacherAge\":"
						+ IdCardUtil.getAgeByBirthday(teacher.getBirthday()));
				sb.append(",");
				sb.append("\"teacherSex\":" + "\"" + teacher.getSex() + "\"");
				sb.append(",");
				sb.append("\"teacherPhone\":" + "\"" + teacher.getMobile()
						+ "\"");
				sb.append(",");
				String areasString = "";
				List<String> lstA = iAreaService.getParentNodes(teacher
						.getArea().getCode());
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
					sb.append("\"teacherArea\":\""
							+ areasString.substring(0, areasString.length() - 2)
							+ "\"");
				} else {
					sb.append("\"teacherArea\":\""
							+ areasString.substring(0, areasString.length() - 2)
							+ "\"");
				}
				sb.append(",");
				sb.append("\"teacherOrganization\":\""
						+ teacher.getOrganization().getName() + "\"");
				sb.append(",");

				// ????????????????????????
				String hqlString = "from TeachingLanguage where teacher="
						+ teacher.getId() + " and isprime=1";

				List<TeachingLanguage> lsTeachingLanguages = iTeachingLanguageService
						.getListByHSQL(hqlString);

				if (lsTeachingLanguages.size() > 0) {
					TeachingLanguage teachingLanguage = lsTeachingLanguages
							.get(0);
					sb.append("\"teachingLanguageId\":"
							+ teachingLanguage.getLanguage().getId());
					sb.append(",");
					sb.append("\"teachingLanguage\":\""
							+ teachingLanguage.getLanguage().getName() + "\"");
					sb.append(",");
				}

				// ????????????????????????
				hqlString = "from TeachingGrade where teacher="
						+ teacher.getId() + " and isprime=1";
				List<TeachingGrade> lstTeachingGrades = iTeachingGradeService
						.getListByHSQL(hqlString);
				if (lstTeachingGrades.size() > 0) {
					TeachingGrade teachingGrade = lstTeachingGrades.get(0);
					sb.append("\"teachingGradeId\":"
							+ teachingGrade.getGrade().getId());
					sb.append(",");
					sb.append("\"teachingGrade\":\""
							+ teachingGrade.getGrade().getName() + "\"");
					sb.append(",");
				}

				// ????????????????????????
				hqlString = "from TeachingSubject where teacher="
						+ teacher.getId() + " and isprime=1";
				List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService
						.getListByHSQL(hqlString);
				if (lsttTeachingSubjects.size() > 0) {
					TeachingSubject teachingSubject = lsttTeachingSubjects
							.get(0);
					sb.append("\"teachingSubjectId\":"
							+ teachingSubject.getSubject().getId());
					sb.append(",");
					sb.append("\"teachingSubject\":\""
							+ teachingSubject.getSubject().getName() + "\"");
					sb.append(",");
				}

				sb.append("\"projectId\":" + p.getId());
				sb.append(",");
				sb.append("\"projectName\":" + "\"" + p.getName() + "\"");
				sb.append(",");
				sb.append("\"trainingSubjectId\":" + ts.getId());
				sb.append(",");
				sb.append("\"trainingSubjectName\":" + "\"" + ts.getName()
						+ "\"");

				sb.append("}}");

				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);

			} else {
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"?????????????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
			}

		} else {
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"?????????????????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}

	}

	/**
	 * ?????????????????????????????? ????????????????????????????????????????????????????????????????????? ?????????????????????????????? ????????????token???teacherId
	 * projectId ?????????????????????teacherId teacherName teacherIdCard recommendStr
	 * creatTime;
	 */
	@SuppressWarnings("unchecked")
	public void EvaluationInfo() {
		initServlert();
		StringBuilder sb = new StringBuilder();
		String token = "";
		if (request.getParameter("token") != null
				&& !"".equals(request.getParameter("token"))) {
			token = request.getParameter("token");
		} else {
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"??????????????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		// TrainingCollegeToken tct =
		// this.iTrainingCollegeTokenService.checkTrainingCollegeToken(token);
		List<TrainingCollege> lstTrainCollege = this.iTrainingCollegeService
				.checkTrainingCollegeToken(token);

		if (lstTrainCollege != null && !lstTrainCollege.isEmpty()) {
			TrainingCollege tc = lstTrainCollege.get(0);
			int teacherId = 0;
			if (request.getParameter("teacherId") != null
					&& !"".equals(request.getParameter("teacherId"))) {
				teacherId = Integer.parseInt(request.getParameter("teacherId"));
			}
			if (teacherId == 0) {
				sb.append("{");
				sb.append("\"Result\":\"FAIL\"");
				sb.append(",");
				sb.append("\"Message\":\"??????ID?????????????????????????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
				return;
			}

			int projectId = 0;
			if (request.getParameter("projectId") != null
					&& !"".equals(request.getParameter("projectId"))) {
				projectId = Integer.parseInt(request.getParameter("projectId"));

				Project pro = iProjectService.get(projectId);
				if (pro != null) {
					if (pro.getStatus() > 2) {// ??????????????????????????????????????????
						sb.append("{");
						sb.append("\"Result\":\"FAIL\"");
						sb.append(",");
						sb.append("\"Message\":\"???????????????\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json",
								response);
						return;
					}
				}
			}

			/*
			 * ???????????????????????????????????????
			 */
			String sqlString = "select ttr.teacher from teacher_training_records ttr where 1=1 and ttr.final_status=2 and ttr.project=? and ttr.training_college=? and ttr.teacher=?";
			Object[] objParas = { projectId, tc.getId(), teacherId };

			List<Integer> lst = this.iTeacherTrainingRecordsService.executeSQL(
					sqlString, objParas);

			if (!lst.contains(teacherId)) {
				sb.append("{");
				sb.append("\"Result\":\"FAIL\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????????????????????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
				return;
			}

			Calendar a = Calendar.getInstance();
			int year = a.get(Calendar.YEAR);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("teacher", teacherId);
			map.put("year", year);

			int count = this.getiHsdTestService().getHsdTestCount(map);
			if (count == 0) {
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"???????????????????????????????????????????????????????????????????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
				return;
			}
			List<Hsdtest> lidata = this.getiHsdTestService().getHsdTest(map, 0,
					count);
			Hsdtest ht = lidata.get(0);// ????????????????????????????????????
			Teacher teacher = this.iTeacherService.get(teacherId);
			String recommendStr = ht.getRecommend() == null ? "" : ht
					.getRecommend();
			String creatTime = ht.getCreatetime().toString();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"?????????\"");
			sb.append(",");
			sb.append("\"Record\":");
			sb.append("{");
			sb.append("\"teacherId\":" + teacherId);
			sb.append(",");
			sb.append("\"teacherName\":" + "\"" + teacher.getName() + "\"");
			sb.append(",");
			sb.append("\"teacherIdcard\":" + "\"" + teacher.getIdcard() + "\"");
			sb.append(",");
			sb.append("\"recommendStr\":" + "\"" + recommendStr + "\"");
			sb.append(",");
			sb.append("\"creatTime\":" + "\"" + creatTime + "\"");
			sb.append("}");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);

		} else {
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"?????????????????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}

	}

	// public void createTeacherUUID(){
	// initServlert();
	//
	// List<Teacher> lstTeacher = this.iTeacherService.findAll();
	// for(Teacher t: lstTeacher){
	// String md5Str = t.getId()+"_"+t.getName();
	// String teacherUuid = MD5Util.string2MD5(md5Str);
	// t.setUuid(teacherUuid);
	// }
	//
	//
	// String teacherUuid = "48138_?????????";
	// String md5str = MD5Util.convertMD5(teacherUuid);
	//
	// System.out.println(md5str);
	// }
	
	public void TeacherMoreInfo() {

		initServlert();
		StringBuilder sb = new StringBuilder();
		String token = "";
		if (request.getParameter("token") != null
				&& !"".equals(request.getParameter("token"))) {
			token = request.getParameter("token");
		} else {
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"??????????????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		// TrainingCollegeToken tct =
		// this.iTrainingCollegeTokenService.checkTrainingCollegeToken(token);
		List<TrainingCollege> lstTrainCollege = this.iTrainingCollegeService
				.checkTrainingCollegeToken(token);

		if (lstTrainCollege != null && !lstTrainCollege.isEmpty()) {

			TrainingCollege tc = lstTrainCollege.get(0);

			int projectId = 0;
			if (request.getParameter("projectId") != null
					&& !"".equals(request.getParameter("projectId"))) {
				projectId = Integer.parseInt(request.getParameter("projectId"));

				Project pro = iProjectService.get(projectId);
				if (pro != null) {
					if (pro.getStatus() > 2) {// ??????????????????????????????????????????
						sb.append("{");
						sb.append("\"Result\":\"FAIL\"");
						sb.append(",");
						sb.append("\"Message\":\"???????????????\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json",
								response);
						return;
					}
				}
			}

			int trainingSubjectId = 0;
			if (request.getParameter("trainingSubjectId") != null
					&& !"".equals(request.getParameter("trainingSubjectId"))) {
				trainingSubjectId = Integer.parseInt(request
						.getParameter("trainingSubjectId"));
			}

			int trainingUnit = tc.getId();

			List<ProjectType> lityps = this.iProjectTypeService.findAll();

			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");

			int totalCount = this.iTeacherTrainingRecordsService
					.getAduTeacherCount(null, projectId, trainingSubjectId,
							trainingUnit, 0, 2, null, lityps, null,0,0);
			List lstTeacherTrainRecords = this.iTeacherTrainingRecordsService
					.getAduTeacher(null, projectId, trainingSubjectId,
							trainingUnit, 0, 2, null, lityps, 0, totalCount,
							null,0,0);
			if (lstTeacherTrainRecords != null
					&& !lstTeacherTrainRecords.isEmpty()) {

				sb.append("\"Message\":\"????????????\"");
				sb.append(",");
				sb.append("\"Records\":[");

				int otherCount = 0;// ????????????????????????
				for (int i = 0; i < lstTeacherTrainRecords.size(); i++) {
//					Object[] obj = (Object[]) lidata.get(i);

					TeacherTrainingRecords ttr = (TeacherTrainingRecords) lstTeacherTrainRecords.get(i);
//					Teacher th = (Teacher) obj[1];
					Teacher t = ttr.getTeacher();
					TrainingSubject ts = ttr.getTrainingSubject();

//					Teacher t = (Teacher) obj[1];
					Project p = ttr.getProject();

					/*
					 * ???????????????????????????????????????
					 */
					if (ttr.getTrainingStatus() == 0) {
						otherCount++;
						continue;
					}

					sb.append("{");
					sb.append("\"teacherId\":" + t.getId());
					sb.append(",");
					sb.append("\"teacherName\":" + "\"" + t.getName() + "\"");
					sb.append(",");
					sb.append("\"teacherIdcard\":" + "\"" + t.getIdcard()
							+ "\"");
					sb.append(",");
					sb.append("\"teacherAge\":"
							+ IdCardUtil.getAgeByBirthday(t.getBirthday()));
					sb.append(",");
					sb.append("\"teacherSex\":" + "\"" + t.getSex() + "\"");
					sb.append(",");
					String mobile = "";
					if(t.getMobile() != null){
						mobile = t.getMobile();
					}
					sb.append("\"teacherPhone\":" + "\"" + mobile + "\"");
					sb.append(",");
					String email = "";
					if(t.getEmail() != null){
						email = t.getEmail();
					}
					sb.append("\"teacherEmail\":" + "\"" + email + "\"");//email
					sb.append(",");
					sb.append("\"teacherFolk\":" + "\"" + t.getEthnic().getName() + "\"");//??????
					sb.append(",");
					sb.append("\"teacherJobTitle\":" + "\"" + t.getJobTitle().getName() + "\"");//??????
					sb.append(",");
					sb.append("\"teacherJobDuty\":" + "\"" + t.getJobDuty().getName() + "\"");//??????
					sb.append(",");
					String graduation = "";
					String major = "";
					if(t.getGraduation() != null){
						graduation = t.getGraduation();
					}
					if(t.getMajor() != null){
						major = t.getMajor();
					}
					sb.append("\"teacherGraduation\":" + "\"" + graduation + "\"");//????????????
					sb.append(",");
					sb.append("\"teacherMajor\":" + "\"" + major + "\"");//????????????
					sb.append(",");
					String education = "";
					if(t.getEductionBackground() != null){
						education = t.getEductionBackground().getName();
					}
					sb.append("\"teacherEducation\":" + "\"" + education + "\"");//????????????
					sb.append(",");
					String areasString = "";
					List<String> lstA = iAreaService.getParentNodes(t.getArea()
							.getCode());
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
						sb.append("\"teacherArea\":\""
								+ areasString.substring(0,
										areasString.length() - 2) + "\"");
					} else {
						sb.append("\"teacherArea\":\"" + areasString + "\"");
					}
					sb.append(",");
					sb.append("\"teacherOrganization\":\""
							+ t.getOrganization().getName() + "\"");
					sb.append(",");
					String ftype = "";
					if(t.getOrganization().getFtype() != null){
						ftype = t.getOrganization().getFtype();
					}
					String type = "";
					if(t.getOrganization().getType() != null){
						type = t.getOrganization().getType();
					}

					sb.append("\"organizationFtype\":\""//??????????????????
							+ ftype + "\"");
					sb.append(",");
					sb.append("\"organizationType\":\""//????????????
							+ type + "\"");
					sb.append(",");
					// ????????????????????????
					Set<TeachingLanguage> setLanguages = t.getTeachingLanguages();
					Set<TeachingGrade> setGrades = t.getTeachingGrades();
					Set<TeachingSubject> setSubjects = t.getTeachingSubjects();
					if(setLanguages != null && setLanguages.size()>0){
						TeachingLanguage teachingLanguage = null;
						for(TeachingLanguage tl:setLanguages){
							if(tl.getIsprime()){
								teachingLanguage = tl;
							}
						}
						if(teachingLanguage != null){
							sb.append("\"teachingLanguageId\":"+ teachingLanguage.getLanguage().getId());
							sb.append(",");
							sb.append("\"teachingLanguage\":\""+ teachingLanguage.getLanguage().getName()
									+ "\"");
							sb.append(",");
						}else{
							sb.append("\"teachingLanguageId\":0");
							sb.append(",");
							sb.append("\"teachingLanguage\":\"???\"");
							sb.append(",");
						}
					}else{
						sb.append("\"teachingLanguageId\":0");
						sb.append(",");
						sb.append("\"teachingLanguage\":\"???\"");
						sb.append(",");
					}
					
					if(setGrades != null && setGrades.size()>0){
						TeachingGrade teachingGrade = null;
						for(TeachingGrade tl:setGrades){
							if(tl.getIsprime()){
								teachingGrade = tl;
							}
						}
						if(teachingGrade != null){
							sb.append("\"teachingGradeId\":"+ teachingGrade.getGrade().getId());
							sb.append(",");
							sb.append("\"teachingGrade\":\""+ teachingGrade.getGrade().getName()
									+ "\"");
							sb.append(",");
						}else{
							sb.append("\"teachingGradeId\":0");
							sb.append(",");
							sb.append("\"teachingGrade\":\"???\"");
							sb.append(",");
						}
					}else{
						sb.append("\"teachingGradeId\":0");
						sb.append(",");
						sb.append("\"teachingGrade\":\"???\"");
						sb.append(",");
					}
					
					if(setSubjects != null && setSubjects.size()>0){
						TeachingSubject teachingSubject = null;
						for(TeachingSubject tl:setSubjects){
							if(tl.getIsprime()){
								teachingSubject = tl;
							}
						}
						if(teachingSubject != null){
							sb.append("\"teachingSubjectId\":"+ teachingSubject.getSubject().getId());
							sb.append(",");
							sb.append("\"teachingSubject\":\""+ teachingSubject.getSubject().getName()
									+ "\"");
							sb.append(",");
						}else{
							sb.append("\"teachingSubjectId\":0");
							sb.append(",");
							sb.append("\"teachingSubject\":\"???\"");
							sb.append(",");
						}
					}else{
						sb.append("\"teachingSubjectId\":0");
						sb.append(",");
						sb.append("\"teachingSubject\":\"???\"");
						sb.append(",");
					}
					

					sb.append("\"projectId\":" + p.getId());
					sb.append(",");
					sb.append("\"projectName\":" + "\"" + p.getName() + "\"");
					sb.append(",");
					sb.append("\"trainingSubjectId\":" + ts.getId());
					sb.append(",");
					sb.append("\"trainingSubjectName\":" + "\"" + ts.getName()
							+ "\"");

					sb.append("},");

				}
				sb.delete(sb.length() - 1, sb.length());

				totalCount = totalCount - otherCount;

				sb.append("]");
				sb.append(",");
				sb.append("\"TotalCount\":" + totalCount);
				sb.append("}");
			} else {
				sb.append("\"Message\":\"??????????????????????????????\"");
				sb.append("}");
			}

			Utlity.ResponseWrite(sb.toString(), "application/json", response);

		} else {
			sb.append("{");
			sb.append("\"Result\":\"FAIL\"");
			sb.append(",");
			sb.append("\"Message\":\"?????????????????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}

	}

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

	public void setiProjectApplyService(
			IProjectApplyService iProjectApplyService) {
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

	public void setiTeachingGradeService(
			ITeachingGradeService iTeachingGradeService) {
		this.iTeachingGradeService = iTeachingGradeService;
	}

	public ITeachingSubjectService getiTeachingSubjectService() {
		return iTeachingSubjectService;
	}

	public void setiTeachingSubjectService(
			ITeachingSubjectService iTeachingSubjectService) {
		this.iTeachingSubjectService = iTeachingSubjectService;
	}

	// public ITrainingCollegeTokenService getiTrainingCollegeTokenService() {
	// return iTrainingCollegeTokenService;
	// }
	//
	// public void setiTrainingCollegeTokenService(
	// ITrainingCollegeTokenService iTrainingCollegeTokenService) {
	// this.iTrainingCollegeTokenService = iTrainingCollegeTokenService;
	// }
	public IHsdtestService getiHsdTestService() {
		return iHsdTestService;
	}

	public void setiHsdTestService(IHsdtestService iHsdTestService) {
		this.iHsdTestService = iHsdTestService;
	}
}
