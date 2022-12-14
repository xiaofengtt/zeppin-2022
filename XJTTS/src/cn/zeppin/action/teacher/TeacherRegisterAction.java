package cn.zeppin.action.teacher;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;





//import cn.zeppin.utility.sms.sendSmsServ;
import cn.zeppin.action.baseAction;
import cn.zeppin.action.admin.OrganizationAction;
import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.ChineseLanguageLevel;
import cn.zeppin.entity.EductionBackground;
import cn.zeppin.entity.Ethnic;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.JobDuty;
import cn.zeppin.entity.JobTitle;
import cn.zeppin.entity.Language;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Politics;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherMobileCode;
import cn.zeppin.entity.TeachingGrade;
import cn.zeppin.entity.TeachingLanguage;
import cn.zeppin.entity.TeachingSubject;
import cn.zeppin.entity.teacherEx;
import cn.zeppin.entity.teachingSubjectEx;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IChineseLanguageLevelService;
import cn.zeppin.service.IEductionBackgroundService;
import cn.zeppin.service.IEthnicService;
import cn.zeppin.service.IGradeService;
import cn.zeppin.service.IJobDutyService;
import cn.zeppin.service.IJobTitleService;
import cn.zeppin.service.ILanguageService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IPoliticsService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.ISubjectService;
import cn.zeppin.service.ITeacherMobileCodeService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeachingGradeService;
import cn.zeppin.service.ITeachingLanguageService;
import cn.zeppin.service.ITeachingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.IdCardUtil;
import cn.zeppin.utility.SendSms;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.pinyingUtil;
 
public class TeacherRegisterAction extends baseAction {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1607876277011840055L;
	static Logger logger = LogManager.getLogger(OrganizationAction.class);
	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	
//	private Teacher teacher = new Teacher();
//	private teacherEx teacherEx = new teacherEx();// ??????ex
	private Area province;// ???
	private Area city;// ???
	private Area county;// ???
	private int id;
	private Organization organizationp;
//	private String pageStatus;// ????????????
//	private String message;// ????????????
	
//	String[] unMainTeachingSubject;
//	String[] unMainTeachingGrades;
//	String[] unMainTeachingLanguage;
	
//	private boolean isUpdate = false;
//	private boolean isReturn = false;

	ITeacherService iTeacherService;// ????????????
	IAreaService iAreaService;// ????????????
	IOrganizationService iOrganizationService;// ????????????
	IJobDutyService iJobDutyService;// ??????
	IJobTitleService iJobTitleService;// ??????
	IEductionBackgroundService iEductionBackgroundService;// ??????
	IPoliticsService iPoliticsService;// ????????????
	IEthnicService iEthnicService;// ??????
	ISubjectService iSubjectService;// ????????????
	IGradeService iGradeService;// ????????????
	IChineseLanguageLevelService iChineseLanguageLevelService;// ???????????????
	ILanguageService iLanguageService;// ????????????
	ITeachingLanguageService iTeachingLanguageService;// ??????????????????
	ITeachingGradeService iTeachingGradeService;// ??????????????????
	ITeachingSubjectService iTeachingSubjectService;// ??????????????????
	
	IProjectAdminService iProjectAdminService;//?????????
	ITeacherMobileCodeService iTeacherMobileCodeService;//???????????????
	
	
	// ??????
	/**
	 * @category ????????????
	 */
	public TeacherRegisterAction() {

	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public void registerInit() {
		initServlert();
		try {
			// ??????
			List<Ethnic> lstEthnics = iEthnicService.getEthnicByWight();
			// ??????
			List<JobDuty> lstJobDuties = iJobDutyService.findAll();
			// ??????
			List<JobTitle> lstJobTitles = iJobTitleService.findAll();
			// ??????
			List<EductionBackground> lstBackgrounds = iEductionBackgroundService.findAll();
			// ????????????
			List<Politics> lstPolitics = iPoliticsService.findAll();
			// ???????????????
			List<ChineseLanguageLevel> lstChineseLanguageLevels = iChineseLanguageLevelService.findAll();
			// ??????
//			List<Grade> lstGrades = iGradeService.findAll();
			String getStuGradesHQL = " from Grade g where g.isSchool=0 ";
			List<Grade> lstGrades = iGradeService.getListByHSQL(getStuGradesHQL);
			// ??????
			List<Subject> lstSubjects = iSubjectService.findAll();
			// ????????????
			List<teachingSubjectEx> lstteacTeachingSubjectExs = new ArrayList<teachingSubjectEx>();
			if(!lstSubjects.isEmpty()){
				for (Subject ts : lstSubjects) {
					teachingSubjectEx tt = new teachingSubjectEx();
					tt.setId(ts.getId().toString());
					tt.setName(ts.getName());
					tt.setSearchString(ts.getName() + pinyingUtil.getFirstSpell(ts.getName()));
					lstteacTeachingSubjectExs.add(tt);
				}
			}
			
			// ??????
//			lstLanguages = iLanguageService.findAll();
			List<Language> lstLanguages = iLanguageService.findAll();
			
			//????????????
			List<Area> lstCity = new ArrayList<Area>();
			List<Area> lstProvince = new ArrayList<Area>();
			
			Organization organization = iOrganizationService.get(26124);
			Area province = organization.getArea();
			lstCity = iAreaService.getParentCodeArea(province.getCode());
//			lstProvince = iAreaService.getLevelArea(province.getLevel());
			lstProvince.add(province);
			
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"????????????\"");
			sb.append(",");
			sb.append("\"Records\":[");
			
			//????????????
			sb.append("{\"lstEthnics\":[");
			if(!lstEthnics.isEmpty()){
				for(Ethnic ethnic: lstEthnics){
					sb.append("{");
					sb.append("\"ethnicId\":\""+ethnic.getId()+"\"");
					sb.append(",");
					sb.append("\"ethnicName\":\""+ethnic.getName()+"\"");
					sb.append("},");
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]}");
				
			}else{
				sb.append("\"message\":\"????????????\"");
				sb.append("]}");
			}
			sb.append(",");
			
			//??????
			sb.append("{\"lstJobDuties\":[");
			if(!lstJobDuties.isEmpty()){
				for(JobDuty jobDuty: lstJobDuties){
					sb.append("{");
					sb.append("\"jobDutyId\":\""+jobDuty.getId()+"\"");
					sb.append(",");
					sb.append("\"jobDutyName\":\""+jobDuty.getName()+"\"");
					sb.append("},");
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]}");
				
			}else{
				sb.append("\"message\":\"????????????\"");
				sb.append("]}");
			}
			sb.append(",");
			
			//??????
			sb.append("{\"lstJobTitles\":[");
			if(!lstJobTitles.isEmpty()){
				for(JobTitle jobTitle: lstJobTitles){
					
					sb.append("{");
					sb.append("\"jobTitleId\":\""+jobTitle.getId()+"\"");
					sb.append(",");
					sb.append("\"jobTitleName\":\""+jobTitle.getName()+"\"");
					sb.append("},");
					
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]}");
			}else{
				sb.append("\"message\":\"????????????\"");
				sb.append("]}");
			}
			sb.append(",");
			
			//??????
			sb.append("{\"lstBackgrounds\":[");
			if(!lstBackgrounds.isEmpty()){
				for(EductionBackground background: lstBackgrounds){
					
					sb.append("{");
					sb.append("\"eBackgroudId\":\""+background.getId()+"\"");
					sb.append(",");
					sb.append("\"eBackgroudName\":\""+background.getName()+"\"");
					sb.append("},");
					
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]}");
				
			}else{
				sb.append("\"message\":\"????????????\"");
				sb.append("]}");
			}
			sb.append(",");
			
			//????????????
			sb.append("{\"lstPolitics\":[");
			if(!lstPolitics.isEmpty()){
				for(Politics politics:lstPolitics){
					
					sb.append("{");
					sb.append("\"politicsId\":\""+politics.getId()+"\"");
					sb.append(",");
					sb.append("\"politicsName\":\""+politics.getName()+"\"");
					sb.append("},");
					
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]}");
				
			}else{
				sb.append("\"message\":\"????????????\"");
				sb.append("]}");
			}
			sb.append(",");
			
			//???????????????
			sb.append("{\"lstChineseLanguageLevels\":[");
			if(!lstChineseLanguageLevels.isEmpty()){
				for(ChineseLanguageLevel cLanguageLevel:lstChineseLanguageLevels){
					
					sb.append("{");
					sb.append("\"cLanguageLevelId\":\""+cLanguageLevel.getId()+"\"");
					sb.append(",");
					sb.append("\"cLanguageLevelName\":\""+cLanguageLevel.getName()+"\"");
					sb.append("},");
					
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]}");
				
			}else{
				sb.append("\"message\":\"????????????\"");
				sb.append("]}");
			}
			sb.append(",");
			
			//??????
			sb.append("{\"lstGrades\":[");
			if(!lstGrades.isEmpty()){
				for(Grade grade: lstGrades){
					
					sb.append("{");
					sb.append("\"gradeId\":\""+grade.getId()+"\"");
					sb.append(",");
					sb.append("\"gradeName\":\""+grade.getName()+"\"");
					sb.append("},");
					
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]}");
				
			}else{
				sb.append("\"message\":\"????????????\"");
				sb.append("]}");
			}
			sb.append(",");
			
			//??????
			sb.append("{\"lstteacTeachingSubjectExs\":[");
			if(!lstteacTeachingSubjectExs.isEmpty()){
				for(teachingSubjectEx teacherSubject : lstteacTeachingSubjectExs){
					
					sb.append("{");
					sb.append("\"subjectId\":\""+teacherSubject.getId()+"\"");
					sb.append(",");
					sb.append("\"subjectName\":\""+teacherSubject.getName()+"\"");
					sb.append(",");
					sb.append("\"searchString\":\""+teacherSubject.getSearchString()+"\"");
					sb.append("},");
					
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]}");
				
			}else{
				sb.append("\"message\":\"????????????\"");
				sb.append("]}");
			}
			sb.append(",");
			
			//??????
			sb.append("{\"lstLanguages\":[");
			if(!lstLanguages.isEmpty()){
				for(Language language: lstLanguages){
					
					
					sb.append("{");
					sb.append("\"languageId\":\""+language.getId()+"\"");
					sb.append(",");
					sb.append("\"languageName\":\""+language.getName()+"\"");
					sb.append("},");
					
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]}");
				
			}else{
				sb.append("\"message\":\"????????????\"");
				sb.append("]}");
			}
			sb.append(",");
			
			//??????????????????????????????????????????
			sb.append("{\"area\":[");
			sb.append("{\"lstProvince\":[");//??????????????????
			if(!lstProvince.isEmpty()){
				for(Area area:lstProvince){
					sb.append("{");
					sb.append("\"provinceId\":\""+area.getId()+"\"");
					sb.append(",");
					sb.append("\"provinceName\":\""+area.getName()+"\"");
					sb.append("},");
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]}");
			}else{
				sb.append("\"message\":\"????????????\"");
				sb.append("]}");
			}
			sb.append(",");
			
			sb.append("{\"lstCity\":[");//???????????????????????????
			if(!lstCity.isEmpty()){
				for(Area area : lstCity){
					sb.append("{");
					sb.append("\"cityId\":\""+area.getId()+"\"");
					sb.append(",");
					sb.append("\"cityName\":\""+area.getName()+"\"");
					sb.append("},");
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]}");
				
			}else{
				sb.append("\"message\":\"????????????\"");
				sb.append("]}");
			}
			sb.append("]}");
			
			sb.append("]");
			sb.append("}");
			
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"????????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			
		}
	}

	/**
	 * ????????????
	 * @throws ParseException
	 * @throws UnsupportedEncodingException
	 */
	public void register() throws ParseException, UnsupportedEncodingException {
		initServlert();

		//??????????????????
		String pageStatus = "";
		String message = "";
		String step = request.getParameter("step");	
		
		if(step != null && !"".equals(step)){
			
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			if(step.equals("first")){//?????????????????????
				sb.append("\"Step1\":[");
				//????????????
				if(request.getParameter("name")==null){
					pageStatus = "ERROR";
					message = "?????????????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("},");
				}
				
				//????????????
				String idCardString ="";
				if(request.getParameter("idCard")!=null){
					idCardString =  request.getParameter("idCard").trim()
							.toLowerCase();
				}
				if (idCardString != null) {
					if (IdCardUtil.IDCardValidate(idCardString).equals("")) {
						if (iTeacherService.existIdCard(idCardString)) {
							pageStatus = "ERRORIDCARD";
							message = "???????????????????????????????????????????????????????????????????????????????????????6??????";
							sb.append("{");
							sb.append("\"Result\":\""+pageStatus+"\"");
							sb.append(",");
							sb.append("\"Message\":\""+message+"\"");
							sb.append("},");
						} 

					} else {
						pageStatus = "ERRORIDCARD";
						message = "????????????????????????";
						sb.append("{");
						sb.append("\"Result\":\""+pageStatus+"\"");
						sb.append(",");
						sb.append("\"Message\":\""+message+"\"");
						sb.append("},");
					}
				} 
				
				// ??????
//				String email = URLDecoder.decode(request.getParameter("email").trim(),
//						"UTF-8");
				String email = "";
				if(request.getParameter("email")!=null){
//					email = request.getParameter("email").trim();
					email = URLDecoder.decode(request.getParameter("email").trim(),"UTF-8");
				}
				if (!Utlity.isEmail(email)) {
					pageStatus = "ERROREMAIL";
					message = "?????????????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("},");
				} else {
					if (iTeacherService.existEmail(email)) {
						pageStatus = "ERROREMAIL";
						message = "??????????????????";
						sb.append("{");
						sb.append("\"Result\":\""+pageStatus+"\"");
						sb.append(",");
						sb.append("\"Message\":\""+message+"\"");
						sb.append("},");
					}

				}
				// ??????
				String mobile = "";
				if(request.getParameter("mobile")!=null){
					mobile = request.getParameter("mobile");
				}
				if (!Utlity.isMobileNO(mobile.trim())) {
					pageStatus = "ERRORMOBILE";
					message = "?????????????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("},");
				} else {
					if (iTeacherService.existMobile(mobile.trim())) {
						pageStatus = "ERRORMOBILE";
						message = "???????????????????????????";
						sb.append("{");
						sb.append("\"Result\":\""+pageStatus+"\"");
						sb.append(",");
						sb.append("\"Message\":\""+message+"\"");
						sb.append("},");
					} 
				}
				String code = request.getParameter("code");
				if (code == null || code.equals("")) {
					// ?????????????????????
					pageStatus = "ERRORCODE";
					message = "?????????????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("},");
				}else{
					String authCode = session.getAttribute("authCode") == null ? "" : session.getAttribute("authCode").toString();
					if (!authCode.equals(code)) {
						pageStatus = "ERRORCODE";
						message = "?????????????????????";
						sb.append("{");
						sb.append("\"Result\":\""+pageStatus+"\"");
						sb.append(",");
						sb.append("\"Message\":\""+message+"\"");
						sb.append("},");
					}
				}
//				String code = "";
//				if(request.getParameter("code")!=null && !"".equals(request.getParameter("code"))){
//					code = request.getParameter("code");
//				}
//				String uuid = (String)session.getAttribute("code");
//				Timestamp time = new Timestamp(System.currentTimeMillis());
//				if(uuid != null){
//					TeacherMobileCode tmc = this.iTeacherMobileCodeService.getRecordByUuid(uuid);
//					if(tmc != null){
//						int seconds = (int)Math.ceil((time.getTime()-tmc.getCreattime().getTime())/(60*1000));
//						if(!tmc.getCode().equals(code)){
//							pageStatus = "ERRORCODE";
//							message = "???????????????????????????????????????";
//							sb.append("{");
//							sb.append("\"Result\":\""+pageStatus+"\"");
//							sb.append(",");
//							sb.append("\"Message\":\""+message+"\"");
//							sb.append("},");
//						}else if(seconds>20){
//							pageStatus = "ERRORCODE";
//							message = "?????????????????????????????????????????????";
//							sb.append("{");
//							sb.append("\"Result\":\""+pageStatus+"\"");
//							sb.append(",");
//							sb.append("\"Message\":\""+message+"\"");
//							sb.append("},");
//						}
//					}
//				}else{
//					pageStatus = "ERRORCODE";
//					message = "??????????????????????????????";
//					sb.append("{");
//					sb.append("\"Result\":\""+pageStatus+"\"");
//					sb.append(",");
//					sb.append("\"Message\":\""+message+"\"");
//					sb.append("},");
//				}
				
				if(sb.length()>10){
					sb.delete(sb.length()-1, sb.length());
					sb.append("]");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"??????\"");
				sb.append("}");
				sb.append("]");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
				
			}else if(step.equals("second")){
				
				sb.append("\"Step2\":");
				// ????????????
				if(request.getParameter("organization")==null){
					pageStatus = "ERROR";
					message = "?????????????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				

				// ??????
				if(request.getParameter("jobDuty")==null){
					pageStatus = "ERROR";
					message = "???????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				// ??????
				if(request.getParameter("jobTitle")==null){
					pageStatus = "ERROR";
					message = "???????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				
				//????????????
				if(request.getParameter("provinceId")==null){
					pageStatus = "ERROR";
					message = "?????????????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				if(request.getParameter("cityId")==null){
					pageStatus = "ERROR";
					message = "?????????????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				if(request.getParameter("countyId")==null){
					pageStatus = "ERROR";
					message = "?????????????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				// ??????
				if(request.getParameter("eductionBackground")==null){
					pageStatus = "ERROR";
					message = "???????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				// ????????????
				if(request.getParameter("politics")==null){
					pageStatus = "ERROR";
					message = "?????????????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				// ??????
				if(request.getParameter("ethnic")==null){
					pageStatus = "ERROR";
					message = "???????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}

				// ???????????????
				if(request.getParameter("chineseLanguageLevel")==null){
					pageStatus = "ERROR";
					message = "????????????????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"??????\"");
				sb.append("}");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}else if(step.equals("third")){
				sb.append("\"Step3\":");
				// ??????
				if(request.getParameter("teachingAge")==null){
					pageStatus = "ERROR";
					message = "???????????????????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				//??????????????????
				if (request.getParameter("multiLanguage") == null) {
					pageStatus = "ERROR";
					message = "???????????????????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				//??????????????????
				if(request.getParameter("mainTeachingSubject")==null){
					pageStatus = "ERROR";
					message = "???????????????????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}

				//??????????????????
				if(request.getParameter("mainTeachingGrades")==null){
					pageStatus = "ERROR";
					message = "???????????????????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				//??????????????????
				if(request.getParameter("mainTeachingLanguage")==null){
					pageStatus = "ERROR";
					message = "???????????????????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}

				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"??????\"");
				sb.append("}");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
				
			}
		}else{
			pageStatus = "ERROR";
			message = "????????????";
			String rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\""
					+ message + "\"}";
			Utlity.ResponseWrite(rString, "json", response);
		}
		
	}
	
	/**
	 * ??????-?????????????????????????????????????????????
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException 
	 */
	public void regist() throws UnsupportedEncodingException, ParseException{
		initServlert();
		Teacher teacher = new Teacher();
		teacherEx teacherEx = new teacherEx();
		//????????????
		if(request.getParameter("name")!=null){
//			teacher.setName(request.getParameter("name").trim());
			teacher.setName(URLDecoder.decode(request.getParameter("name").trim(),"UTF-8"));
		}
		//????????????
		String idCardString ="";
		if(request.getParameter("idCard")!=null){
			idCardString =  request.getParameter("idCard").trim()
					.toLowerCase();
		}
		
		if (idCardString != null) {
			if (IdCardUtil.IDCardValidate(idCardString).equals("")) {
				if (iTeacherService.existIdCard(idCardString)) {
					String pageStatus = "ERRORIDCARD";
					String message = "???????????????????????????????????????????????????????????????????????????????????????6??????";
					StringBuffer sb = new StringBuffer();
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				} 

			} else {
				String pageStatus = "ERRORIDCARD";
				String message = "????????????????????????";
				StringBuffer sb = new StringBuffer();
				sb.append("{");
				sb.append("\"Result\":\""+pageStatus+"\"");
				sb.append(",");
				sb.append("\"Message\":\""+message+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
		}
		
		teacher.setIdcard(idCardString);
		teacher.setBirthday(IdCardUtil.getBirthday(idCardString));
		teacher.setSex(IdCardUtil.getSex(idCardString));
		
		//??????
		String email = "";
		if(request.getParameter("email")!=null){
//			email = request.getParameter("email").trim();
			email = URLDecoder.decode(request.getParameter("email").trim(),"UTF-8");
		}
		if (!Utlity.isEmail(email)) {
			String pageStatus = "ERROREMAIL";
			String message = "?????????????????????";
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("\"Result\":\""+pageStatus+"\"");
			sb.append(",");
			sb.append("\"Message\":\""+message+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		} else {
			if (iTeacherService.existEmail(email)) {
				String pageStatus = "ERROREMAIL";
				String message = "??????????????????";
				StringBuffer sb = new StringBuffer();
				sb.append("{");
				sb.append("\"Result\":\""+pageStatus+"\"");
				sb.append(",");
				sb.append("\"Message\":\""+message+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}

		}
		teacher.setEmail(email);
		
		//??????
		String mobile = "";
		if(request.getParameter("mobile")!=null){
			mobile = request.getParameter("mobile");
		}
		if (!Utlity.isMobileNO(mobile.trim())) {
			String pageStatus = "ERRORMOBILE";
			String message = "?????????????????????";
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("\"Result\":\""+pageStatus+"\"");
			sb.append(",");
			sb.append("\"Message\":\""+message+"\"");
			sb.append("},");
		} else {
			if (iTeacherService.existMobile(mobile.trim())) {
				String pageStatus = "ERRORMOBILE";
				String message = "???????????????????????????";
				StringBuffer sb = new StringBuffer();
				sb.append("{");
				sb.append("\"Result\":\""+pageStatus+"\"");
				sb.append(",");
				sb.append("\"Message\":\""+message+"\"");
				sb.append("},");
			} 
		}
		teacher.setMobile(mobile.trim());
		
		// ????????????
		String organazationString = "";
		if(request.getParameter("organization")!=null){
			organazationString = request.getParameter("organization");
		}
		
		
		organizationp = iOrganizationService.get(Integer
				.parseInt(organazationString));
		teacher.setOrganization(organizationp);
		

		// ??????
		String jobDuty = "";
		if(request.getParameter("jobDuty")!=null){
			jobDuty = request.getParameter("jobDuty");
		}
		teacher.setJobDuty(iJobDutyService.getJobDutyById(jobDuty));
		
		// ??????
		String jobTitle = "";
		if(request.getParameter("jobTitle")!=null){
			jobTitle = request.getParameter("jobTitle");
		}
		teacher.setJobTitle(iJobTitleService.getJobTitleById(jobTitle));
		
		
		//????????????
		String provinceId = "";
		if(request.getParameter("provinceId")!=null){
			provinceId = request.getParameter("provinceId");
		}
		Area province = iAreaService.get(Integer.parseInt(provinceId));

		String cityId = "";
		if(request.getParameter("cityId")!=null){
			cityId = request.getParameter("cityId");
		}
		Area city = iAreaService.get(Integer.parseInt(cityId));

		String countyId = "";
		if(request.getParameter("countyId")!=null){
			countyId = request.getParameter("countyId");
		}
		Area county = iAreaService.get(Integer.parseInt(countyId));

		// ????????????
		if (province != null) {
			if (province.getName() != null) {
				teacher.setArea(province);
			}

		}
		if (city != null) {
			if (city.getName() != null) {
				teacher.setArea(city);
			}

		}
		if (county != null) {
			if (county.getName() != null) {
				teacher.setArea(county);
			}
		}
		
		// ??????
		String backgroundString = "";
		if(request.getParameter("eductionBackground")!=null){
			backgroundString = request.getParameter("eductionBackground");
		}
		teacher.setEductionBackground(iEductionBackgroundService.geteEductionBackgroundById(backgroundString));
		
		// ????????????
		String politcsString = "";
		if(request.getParameter("politics")!=null){
			politcsString = request.getParameter("politics");
		}
		teacher.setPolitics(iPoliticsService.getPoliticsById(politcsString));

		// ??????
		String ethnicString = "";
		if(request.getParameter("ethnic")!=null){
			ethnicString = request.getParameter("ethnic");
		}
		teacher.setEthnic(iEthnicService.get(Short.parseShort(ethnicString)));

		// ???????????????
		String chineseLanguageLevelString = "";
		if(request.getParameter("chineseLanguageLevel")!=null){
			chineseLanguageLevelString = request.getParameter("chineseLanguageLevel");
		}
		teacher.setChineseLanguageLevel(iChineseLanguageLevelService.getChineseLanguageLevelById(chineseLanguageLevelString));
		
		// ??????
		String teachingAageString = "";
		if(request.getParameter("teachingAge")!=null){
			teachingAageString = request.getParameter("teachingAge");
		}
		teacher.setTeachingAge(Utlity.stringToDate(teachingAageString));
		
		//??????????????????
		String isMultilanguage = "0";
		if (request.getParameterMap().containsKey("multiLanguage")) {
			isMultilanguage = request.getParameter("multiLanguage");
		}
		teacher.setMultiLanguage(isMultilanguage.equals("1") ? true : false);
		
		//??????????????????
		short subjectId = 0;
		if(request.getParameter("mainTeachingSubject")!=null){
			subjectId = Short.parseShort(request.getParameter("mainTeachingSubject"));
		}
		TeachingSubject mainSubject = new TeachingSubject();
		mainSubject.setSubject(iSubjectService.get(subjectId));
		mainSubject.setIsprime(true);
		teacherEx.setMainTeachingCourse(mainSubject);

		//??????????????????
		short gradeId = 0;
		if(request.getParameter("mainTeachingGrades")!=null){
			gradeId = Short.parseShort(request.getParameter("mainTeachingGrades"));
		}
		TeachingGrade mainGrade = new TeachingGrade();
		mainGrade.setGrade(iGradeService.get(gradeId));
		mainGrade.setIsprime(true);
		teacherEx.setMainTeachingClass(mainGrade);
		
		//??????????????????
		short languageId = 0;
		if(request.getParameter("mainTeachingLanguage")!=null){
			languageId = Short.parseShort(request.getParameter("mainTeachingLanguage"));
		}
		TeachingLanguage mainlLanguage = new TeachingLanguage();
		mainlLanguage.setLanguage(iLanguageService.get(languageId));
		mainlLanguage.setIsprime(true);
		teacherEx.setMainTeachingLanguage(mainlLanguage);
		
		//?????????????????????
		String password = "";
		if(request.getParameter("password")!=null){
			password = request.getParameter("password");
		}
		teacher.setPassword(password);
		teacher.setAuthorized((short)1);
		teacher.setStatus((short)-1);//???????????????????????????-1 ???????????????
		teacher.setCreattime(new Timestamp(System.currentTimeMillis()));
		
		String pageStatus = "OK";
		String message = "";
		
		Teacher tempTeacher = new Teacher();
		try {
			teacher.setIsFirstLogin((short)0);
			teacher.setIsTeachingSchool((short)0);
			tempTeacher = iTeacherService.add(teacher);

			// ????????????
			TeachingSubject tSubject = teacherEx
					.getMainTeachingCourse();
			tSubject.setTeacher(tempTeacher);
			iTeachingSubjectService.add(tSubject);
			String[] unMainTeachingSubject = request
					.getParameterValues("unMainTeachingSubject[]");
//			System.out.println(unMainTeachingSubject.toString());
			if (unMainTeachingSubject != null && unMainTeachingSubject.length > 0) {
				for (String tid : unMainTeachingSubject) {
					if(!"".equals(tid)){
						if (!tid.equals(tSubject.getSubject().getId()
								.toString())) {

							TeachingSubject tS = new TeachingSubject();
							tS.setIsprime(false);
							tS.setTeacher(tempTeacher);
							tS.setSubject(iSubjectService.get(Short
									.parseShort(tid)));
							iTeachingSubjectService.add(tS);
						}
					}
				}
			}
			// ????????????
			TeachingGrade tGrade = teacherEx.getMainTeachingClass();
			tGrade.setTeacher(tempTeacher);
			iTeachingGradeService.add(tGrade);
			String[] unMainTeachingGrades = request
					.getParameterValues("unMainTeachingGrades[]");
			if (unMainTeachingGrades != null && unMainTeachingGrades.length > 0) {
				for (String tid : unMainTeachingGrades) {
					if(!"".equals(tid)){
						if (!tid.equals(tGrade.getGrade().getId()
								.toString())) {
							TeachingGrade tg = new TeachingGrade();
							tg.setIsprime(false);
							tg.setTeacher(tempTeacher);
							tg.setGrade(iGradeService.get(Short
									.parseShort(tid)));
							iTeachingGradeService.add(tg);
						}
					}
				}
			}
			// ??????????????????

			TeachingLanguage tLanguage = teacherEx
					.getMainTeachingLanguage();
			tLanguage.setTeacher(tempTeacher);
			iTeachingLanguageService.add(tLanguage);
			String[] unMainTeachingLanguage = request
					.getParameterValues("unMainTeachingLanguage[]");
			if (unMainTeachingLanguage != null && unMainTeachingLanguage.length > 0) {
				for (String tid : unMainTeachingLanguage) {
					if(!"".equals(tid)){
						if (!tid.equals(tLanguage.getLanguage().getId()
								.toString())) {
							TeachingLanguage tL = new TeachingLanguage();
							tL.setIsprime(false);
							tL.setTeacher(tempTeacher);
							tL.setLanguage(iLanguageService.get(Short
									.parseShort(tid)));
							iTeachingLanguageService.add(tL);

						}
					}
				}
			}
			//?????????????????????session???.
			UserSession us = new UserSession();
			us.setId(tempTeacher.getId());
			us.setName(tempTeacher.getName());
			us.setOrganization(tempTeacher.getOrganization().getId());
			us.setStatus(tempTeacher.getStatus());
			session.setAttribute("teacherreviewsession", us);
			pageStatus = "OK";
			message = "??????";
			
		} catch (Exception e) {
			e.printStackTrace();
			pageStatus = "ERROR";
			if (tempTeacher.getId() != null) {

				// iTeacherService.delete(tempTeacher);
			}
			message = "??????????????????????????????";
//				result = "addInit";
		}
		String rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\""
				+ message + "\"}";
		Utlity.ResponseWrite(rString, "json", response);
		
	}

	/**
	 * ???????????????????????????
	 * 
	 * ?????????????????????
	 * 1??????????????????????????????????????????session??????????????????6??????????????????????????????????????????????????????
	 * 	??????????????????????????????????????????????????????session????????????????????????????????????
	 * 2????????????????????????????????????????????????????????????????????????????????????
	 */
	public void sendSms(){
		initServlert();
		
		String pageStatus = "";
		String message = "";
		String phone = "";
		StringBuilder sb = new StringBuilder();
		if(request.getParameter("phone")!=null){
			phone = request.getParameter("phone");
		}
		
		if (!Utlity.isMobileNO(phone.trim())) {
			pageStatus = "ERROR";
			message = "?????????????????????";
			sb.append("{");
			sb.append("\"Result\":\""+pageStatus+"\"");
			sb.append(",");
			sb.append("\"Message\":\""+message+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		//??????6??????????????????
		String captcha = Utlity.getCaptcha();
		
		
		TeacherMobileCode tmobileCode = new TeacherMobileCode();
		String uuid = Utlity.getUUID();
		tmobileCode.setCode(captcha+"");
		tmobileCode.setUuid(uuid);
		tmobileCode.setCreattime(new Timestamp(System.currentTimeMillis()));
		
		
		sb.append("{");
		
		try {

			//????????????????????????
//			String sms = "?????????????????????????????????"+captcha+",???????????????20????????????????????????????????????????????????";
//			String code = SendSms.sendSms(sms, phone);
			String code = SendSms.sendSms(session, captcha, phone);
			String[] strs = code.split("_");
			String status = strs[0];
			String msg = code;
			
			if("0".equals(status)){
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				tmobileCode.setStatus(Short.parseShort(status));
				tmobileCode.setMsg(msg);
				this.iTeacherMobileCodeService.add(tmobileCode);
				session.removeAttribute("code");
				session.setAttribute("code", uuid);
			}else{
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				tmobileCode.setStatus(Short.parseShort(status));
				tmobileCode.setMsg(msg);
				this.iTeacherMobileCodeService.add(tmobileCode);
			}
//			System.out.println(code);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e);
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
	}
	
	public void review(){
		initServlert();
		/*
		 * ??????????????????????????????????????????????????? ????????????
		 * 1?????????session????????????????????????OrganizationID
		 * 2????????????ID???????????????????????????????????????????????? ??????List??????
		 * 3???????????????????????????????????????????????????????????????????????????
		 * 4??????????????????JSON???????????????review.jsp??????
		 * 		???????????????????????????????????????????????????????????????????????????
		 */
		/*
		 * ????????????
		 * ????????????service???dao???????????????????????????
		 * findByOrganization(Integer OrganizationId)
		 */
		/*
		 * ??????
		 */
		try {
			// ?????????
			String ist = (String) request.getParameter("jtStartIndex");
			if (ist == null || ist.equals("") || ist.equals("NaN")) {
				ist = "0";
			}
			// ???????????????
			String ien = (String) request.getParameter("jtPageSize");
			if (ien == null || ien.equals("")) {
				ien = DictionyMap.maxPageSize + "";
			}

			int start = Integer.parseInt(ist);
			int length = DictionyMap.maxPageSize;
			length = Integer.parseInt(ien);
			
			UserSession us = (UserSession)session.getAttribute("teacherreviewsession");
			List<ProjectAdmin> li = new ArrayList<ProjectAdmin>();//??????????????????????????????????????????
			int records = 0;
			if(us != null){
				Integer organizationId = us.getOrganization();
				Organization organization = this.iOrganizationService.get(organizationId);
				
				if(organization != null){
					li =  this.iProjectAdminService.getAdminByOrganization(organizationId,start,length);
				}
				if(li != null && li.isEmpty()){//?????????????????????????????????????????????????????????ID????????????
					li = this.iProjectAdminService.getAdminByOrganization(organization.getOrganization().getId(), start, length);
				}
				records = li.size();
			}else{
				StringBuilder sb = new StringBuilder();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");

			sb.append("\"Records\":[");
			for (int i = 0; i < li.size(); i++) {
				ProjectAdmin pa = li.get(i);
				String name = pa.getName();//??????
				String mobile = pa.getMobile(); //?????????
				// ????????????
				String organizationName = pa.getOrganization().getName();
				String department = pa.getDepartment(); // ????????????


				String phone = pa.getPhone(); //????????????
				String fax = pa.getFax(); //??????
				String jobDuty = pa.getJobDuty(); // ??????

				StringBuilder sbstr = new StringBuilder();
				sbstr.append("{");
				sbstr.append("\"name\":\"" + name + "\"");
				sbstr.append(",");
				sbstr.append("\"mobile\":\"" + mobile + "\"");
				sbstr.append(",");
				sbstr.append("\"organization\":\"" + organizationName + "\"");
				sbstr.append(",");
				sbstr.append("\"department\":\"" + department + "\"");
				sbstr.append(",");
				sbstr.append("\"phone\":\"" + phone + "\"");
				sbstr.append(",");
				sbstr.append("\"fax\":\"" + fax + "\"");
				sbstr.append(",");
				sbstr.append("\"jobDuty\":\"" + jobDuty + "\"");
				sbstr.append("},");
				sb.append(sbstr.toString());
			}

			sb.delete(sb.length() - 1, sb.length());
			sb.append("]");
			sb.append(",");
			sb.append("\"TotalRecordCount\":" + records);
			sb.append("}");

			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
	}
	
	/**
	 * ??????????????????
	 */
	public void searchSchool() {
		initServlert();
		List<Organization> lstOrganizations = new ArrayList<Organization>();
		
		String string;
		if (request.getParameterMap().containsKey("search")) {
			string = request.getParameter("search");
			StringBuffer sb = new StringBuffer();

			String hqlString = "";
			
			string = string.replaceAll("'", "");
			string = string.replaceAll("\"", "\\\"");
			string = string.replaceAll(";", "");

			hqlString = "from Organization where isschool=1 and (name like '%" + string + "%' or pinying like '%" + string + "%' )";
				
			try {
				lstOrganizations = iOrganizationService.getListForPage(hqlString, 0, 10, null);
			} catch (Exception e) {
				lstOrganizations.clear();
				// TODO: handle exception
				e.printStackTrace();
			}
			
			sb.append("{");
			sb.append("\"Result\":\"OK\",");
			sb.append("\"Options\":");
			String ret = "[";
			String st = "";
			if (lstOrganizations.size() > 0) {

				for (Organization org : lstOrganizations) {
					st += "{\"id\":" + org.getId() + ",\"DisplayText\":\"" + org.getName() + "\"},";
				}

			}
			if (st.length() > 0) {
				st = st.substring(0, st.length() - 1);
			}
			ret += st + "]";

			sb.append(ret);
			sb.append("}");

			Utlity.ResponseWrite(sb.toString(), "application/json", response);

		}

	}
	
//	public void getStatus(){
//		initServlert();
//		String result = SendSms.getStatus();
//		System.out.println(result);
//	}
	
//	public Teacher getTeacher() {
//		return teacher;
//	}
//
//	public void setTeacher(Teacher teacher) {
//		this.teacher = teacher;
//	}
//
//	public teacherEx getTeacherEx() {
//		return teacherEx;
//	}
//
//	public void setTeacherEx(teacherEx teacherEx) {
//		this.teacherEx = teacherEx;
//	}

	public Area getProvince() {
		return province;
	}

	public void setProvince(Area province) {
		this.province = province;
	}

	public Area getCity() {
		return city;
	}

	public void setCity(Area city) {
		this.city = city;
	}

	public Area getCounty() {
		return county;
	}

	public void setCounty(Area county) {
		this.county = county;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Organization getOrganizationp() {
		return organizationp;
	}

	public void setOrganizationp(Organization organizationp) {
		this.organizationp = organizationp;
	}

//	public String getPageStatus() {
//		return pageStatus;
//	}
//
//	public void setPageStatus(String pageStatus) {
//		this.pageStatus = pageStatus;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}

	public ITeacherService getiTeacherService() {
		return iTeacherService;
	}

	public void setiTeacherService(ITeacherService iTeacherService) {
		this.iTeacherService = iTeacherService;
	}

	public IAreaService getiAreaService() {
		return iAreaService;
	}

	public void setiAreaService(IAreaService iAreaService) {
		this.iAreaService = iAreaService;
	}

	public IOrganizationService getiOrganizationService() {
		return iOrganizationService;
	}

	public void setiOrganizationService(IOrganizationService iOrganizationService) {
		this.iOrganizationService = iOrganizationService;
	}

	public IJobDutyService getiJobDutyService() {
		return iJobDutyService;
	}

	public void setiJobDutyService(IJobDutyService iJobDutyService) {
		this.iJobDutyService = iJobDutyService;
	}

	public IJobTitleService getiJobTitleService() {
		return iJobTitleService;
	}

	public void setiJobTitleService(IJobTitleService iJobTitleService) {
		this.iJobTitleService = iJobTitleService;
	}

	public IEductionBackgroundService getiEductionBackgroundService() {
		return iEductionBackgroundService;
	}

	public void setiEductionBackgroundService(
			IEductionBackgroundService iEductionBackgroundService) {
		this.iEductionBackgroundService = iEductionBackgroundService;
	}

	public IPoliticsService getiPoliticsService() {
		return iPoliticsService;
	}

	public void setiPoliticsService(IPoliticsService iPoliticsService) {
		this.iPoliticsService = iPoliticsService;
	}

	public IEthnicService getiEthnicService() {
		return iEthnicService;
	}

	public void setiEthnicService(IEthnicService iEthnicService) {
		this.iEthnicService = iEthnicService;
	}

	public ISubjectService getiSubjectService() {
		return iSubjectService;
	}

	public void setiSubjectService(ISubjectService iSubjectService) {
		this.iSubjectService = iSubjectService;
	}

	public IGradeService getiGradeService() {
		return iGradeService;
	}

	public void setiGradeService(IGradeService iGradeService) {
		this.iGradeService = iGradeService;
	}

	public IChineseLanguageLevelService getiChineseLanguageLevelService() {
		return iChineseLanguageLevelService;
	}

	public void setiChineseLanguageLevelService(
			IChineseLanguageLevelService iChineseLanguageLevelService) {
		this.iChineseLanguageLevelService = iChineseLanguageLevelService;
	}

	public ILanguageService getiLanguageService() {
		return iLanguageService;
	}

	public void setiLanguageService(ILanguageService iLanguageService) {
		this.iLanguageService = iLanguageService;
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

	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	public void setiProjectAdminService(IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}

	public ITeacherMobileCodeService getiTeacherMobileCodeService() {
		return iTeacherMobileCodeService;
	}

	public void setiTeacherMobileCodeService(
			ITeacherMobileCodeService iTeacherMobileCodeService) {
		this.iTeacherMobileCodeService = iTeacherMobileCodeService;
	}

//	public String[] getUnMainTeachingSubject() {
//		return unMainTeachingSubject;
//	}
//
//	public void setUnMainTeachingSubject(String[] unMainTeachingSubject) {
//		this.unMainTeachingSubject = unMainTeachingSubject;
//	}
//
//	public String[] getUnMainTeachingGrades() {
//		return unMainTeachingGrades;
//	}
//
//	public void setUnMainTeachingGrades(String[] unMainTeachingGrades) {
//		this.unMainTeachingGrades = unMainTeachingGrades;
//	}
//
//	public String[] getUnMainTeachingLanguage() {
//		return unMainTeachingLanguage;
//	}
//
//	public void setUnMainTeachingLanguage(String[] unMainTeachingLanguage) {
//		this.unMainTeachingLanguage = unMainTeachingLanguage;
//	}

	
	
}


