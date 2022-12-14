package cn.zeppin.action.teacher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;














//import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.baseAction;
import cn.zeppin.action.admin.OrganizationAction;
import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.ChineseLanguageLevel;
import cn.zeppin.entity.Document;
import cn.zeppin.entity.EductionBackground;
import cn.zeppin.entity.Ethnic;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.ImgResult;
import cn.zeppin.entity.JobDuty;
import cn.zeppin.entity.JobTitle;
import cn.zeppin.entity.Language;
import cn.zeppin.entity.MailAttachment;
import cn.zeppin.entity.MailConnection;
import cn.zeppin.entity.MailInformation;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Politics;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectCycle;
import cn.zeppin.entity.ServiceApplyReply;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherEduAdvance;
import cn.zeppin.entity.TeacherEduEvidence;
import cn.zeppin.entity.TeacherMobileCode;
import cn.zeppin.entity.TeachingGrade;
import cn.zeppin.entity.TeachingLanguage;
import cn.zeppin.entity.TeachingSubject;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.entity.fileInfo;
import cn.zeppin.entity.teacherEx;
import cn.zeppin.entity.teachingSubjectEx;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IChineseLanguageLevelService;
import cn.zeppin.service.IDocumentService;
import cn.zeppin.service.IEductionBackgroundService;
import cn.zeppin.service.IEthnicService;
import cn.zeppin.service.IGradeService;
import cn.zeppin.service.IJobDutyService;
import cn.zeppin.service.IJobTitleService;
import cn.zeppin.service.ILanguageService;
import cn.zeppin.service.IMailConnectionService;
import cn.zeppin.service.IMailInformationService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IPoliticsService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectCycleService;
import cn.zeppin.service.IServiceApplyReplyService;
import cn.zeppin.service.ISubjectService;
import cn.zeppin.service.ITeacherEduAdvanceAduService;
import cn.zeppin.service.ITeacherEduAdvanceService;
import cn.zeppin.service.ITeacherEduEvidenceService;
import cn.zeppin.service.ITeacherMobileCodeService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeachingGradeService;
import cn.zeppin.service.ITeachingLanguageService;
import cn.zeppin.service.ITeachingSubjectService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.SendSms;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.dataTimeConvertUtility;
import cn.zeppin.utility.pinyingUtil;
 
public class TeacherInfoAction extends baseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -524196355411671118L;
	
	static Logger logger = LogManager.getLogger(OrganizationAction.class);
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	
	IOrganizationService iOrganizationService;// ????????????
	ITeacherService iTeacherService;//????????????
	IAreaService iAreaService;//????????????
	ITeachingLanguageService iTeachingLanguageService;// ??????????????????
	ITeachingGradeService iTeachingGradeService;// ??????????????????
	ITeachingSubjectService iTeachingSubjectService;// ??????????????????

	
	IEthnicService iEthnicService;// ??????
	IJobDutyService iJobDutyService;// ??????
	IJobTitleService iJobTitleService;// ??????
	IEductionBackgroundService iEductionBackgroundService;// ??????
	IPoliticsService iPoliticsService;// ????????????
	IGradeService iGradeService;// ????????????
	IChineseLanguageLevelService iChineseLanguageLevelService;// ???????????????
	ISubjectService iSubjectService;// ????????????
	ILanguageService iLanguageService;// ????????????
	
	ITeacherMobileCodeService iTeacherMobileCodeService;//???????????????
	
	private IMailInformationService mailInformationService;
	
	private IMailConnectionService mailConnectionService;
	private IProjectAdminService iProjectAdminService;
	private ITrainingAdminService iTrainingAdmin;
	
	private ITeacherEduAdvanceService iTeacherEduAdvanceService;
	private ITeacherEduAdvanceAduService iTeacherEduAdvanceAduService;
	private ITeacherEduEvidenceService ITeacherEduEvidenceService;
	private IDocumentService iDocumentService;//??????
	
	private IProjectCycleService projectCycleService;
	private IServiceApplyReplyService serviceApplyReplyService;
	
	private int offset;// ?????????
	
	/**
	 * @category ????????????
	 */
	public TeacherInfoAction() {

	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}
	
	public void initPage(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession user = (UserSession) session.getAttribute("teachersession");
		if(user != null){
			try {
				
				Teacher teacher = iTeacherService.getTeacherByIdCard(user.getIdcard());
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????\"");
				sb.append(",");
				sb.append("\"Records\":");
				sb.append("{");
				sb.append("\"name\":\""+teacher.getName()+"\"");
				sb.append(",");
				sb.append("\"idCard\":\""+teacher.getIdcard()+"\"");
				sb.append(",");
				String email = teacher.getEmail() == null ? "???":teacher.getEmail();
				sb.append("\"email\":\""+email+"\"");
				sb.append(",");
				String path = ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath();
				String headImgPath = teacher.getHeadPicPath() == null ? "0":path+teacher.getHeadPicPath();
				sb.append("\"headImgPath\":\""+headImgPath+"\"");
				sb.append(",");
				sb.append("\"organization\":\""+teacher.getOrganization().getName()+"\"");
				sb.append(",");
				String areasString = "";
				List<String> lstA = iAreaService.getParentNodes(teacher.getArea().getCode());
				if (lstA.size() > 0) {
					List<String> t = new ArrayList<>();
					for (int i = lstA.size() - 1; i >= 0; i--) {
						t.add(lstA.get(i));
					}
	
					for (String string : t) {
						areasString += string + ">>";
					}
				}
				if (areasString.length() > 2) {
					sb.append("\"teacherArea\":\""+areasString.substring(0, areasString.length() - 2)+"\"");
				}
				sb.append(",");
				sb.append("\"jobDuty\":\""+teacher.getJobDuty().getName()+"\"");
				String graduation = teacher.getGraduation() == null?"???":teacher.getGraduation();
				if("".equals(teacher.getGraduation())){
					graduation = "???";
				}
				String major = teacher.getMajor() == null?"???":teacher.getMajor();
				if("".equals(teacher.getMajor())){
					major = "???";
				}
				sb.append(",");
				sb.append("\"graduation\":\""+graduation+"\"");
				sb.append(",");
				sb.append("\"major\":\""+major+"\"");
				sb.append(",");
				sb.append("\"jobTitle\":\""+teacher.getJobTitle().getName()+"\"");
				sb.append(",");
				sb.append("\"ethnic\":\""+teacher.getEthnic().getName()+"\"");
				sb.append(",");
				sb.append("\"eductionBackground\":\""+teacher.getEductionBackground().getName()+"\"");
				sb.append(",");
				sb.append("\"politics\":\""+teacher.getPolitics().getName()+"\"");
				sb.append(",");
				sb.append("\"chineseLanguageLevel\":\""+teacher.getChineseLanguageLevel().getName()+"\"");
				sb.append(",");
				sb.append("\"teachingAge\":\""+teacher.getTeachingAge()+"\"");
				sb.append(",");
				//??????
				String sex = teacher.getSex() == 1?"???":"???";
				sb.append("\"sex\":\""+sex+"\"");
				sb.append(",");
				//??????????????????
				String multiLanguage = teacher.getMultiLanguage() == true?"???":"???";
				sb.append("\"multiLanguage\":\""+multiLanguage+"\"");
				sb.append(",");
				// ????????????????????????
				String hqlString = "from TeachingLanguage where teacher=" + teacher.getId() + " and isprime=1";
	
				List<TeachingLanguage> lsTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
									   
				if (lsTeachingLanguages.size() > 0) {
					TeachingLanguage teachingLanguage = lsTeachingLanguages.get(0);
					sb.append("\"mainTeachingLanguage\":\""+teachingLanguage.getLanguage().getName()+"\"");
					sb.append(",");
				}
	
				// ????????????????????????
				hqlString = "from TeachingGrade where teacher=" + teacher.getId() + " and isprime=1";
				List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
				if (lstTeachingGrades.size() > 0) {
					TeachingGrade teachingGrade = lstTeachingGrades.get(0);
					sb.append("\"mainTeachingGrades\":\""+teachingGrade.getGrade().getName()+"\"");
					sb.append(",");
				}
	
				// ????????????????????????
				hqlString = "from TeachingSubject where teacher=" + teacher.getId() + " and isprime=1";
				List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
				if (lsttTeachingSubjects.size() > 0) {
					TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
					sb.append("\"mainTeachingSubject\":\""+teachingSubject.getSubject().getName()+"\"");
					sb.append(",");
				}
				
				// ????????????????????????
				hqlString = "from TeachingLanguage where teacher=" + teacher.getId() + " and isprime=0";
	
				List<TeachingLanguage> unlsTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
									   
				if (unlsTeachingLanguages.size() > 0) {
					StringBuilder unteachingLanguage = new StringBuilder();
					for(TeachingLanguage teachingLanguage:unlsTeachingLanguages){
						unteachingLanguage.append(teachingLanguage.getLanguage().getName()+"???");
					}
					unteachingLanguage.delete(unteachingLanguage.length()-1, unteachingLanguage.length());
					sb.append("\"unMainTeachingLanguage\":\""+unteachingLanguage.toString()+"\"");
					sb.append(",");
				}else{
					sb.append("\"unMainTeachingLanguage\":\"???\"");
					sb.append(",");
				}
	
				// ????????????????????????
				hqlString = "from TeachingGrade where teacher=" + teacher.getId() + " and isprime=0";
				List<TeachingGrade> unlstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
				if (unlstTeachingGrades.size() > 0) {
					StringBuilder unTeachingGrades = new StringBuilder();
					for(TeachingGrade teachingGrade:unlstTeachingGrades){
						unTeachingGrades.append(teachingGrade.getGrade().getName()+"???");
					}
					unTeachingGrades.delete(unTeachingGrades.length()-1, unTeachingGrades.length());
					sb.append("\"unMainTeachingGrades\":\""+unTeachingGrades.toString()+"\"");
					sb.append(",");
				}else{
					sb.append("\"unMainTeachingGrades\":\"???\"");
					sb.append(",");
				}
	
				// ????????????????????????
				hqlString = "from TeachingSubject where teacher=" + teacher.getId() + " and isprime=0";
				List<TeachingSubject> unMainTeachingSubject = iTeachingSubjectService.getListByHSQL(hqlString);
				if (unMainTeachingSubject.size() > 0) {
					StringBuilder unTeachingSubject = new StringBuilder();
					for(TeachingSubject teachingSubject:unMainTeachingSubject){
						unTeachingSubject.append(teachingSubject.getSubject().getName()+"???");
					}
					unTeachingSubject.delete(unTeachingSubject.length()-1, unTeachingSubject.length());
					sb.append("\"unMainTeachingSubject\":\""+unTeachingSubject.toString()+"\"");
					sb.append(",");
				}else{
					sb.append("\"unMainTeachingSubject\":\"???\"");
					sb.append(",");
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("}}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			} catch (Exception e) {
				// TODO: handle exception
				StringBuilder sbs = new StringBuilder();
				sbs.append("{");
				sbs.append("\"Result\":\"ERROR\"");
				sbs.append(",");
				sbs.append("\"Message\":\"????????????\"");
				sbs.append("}");
				Utlity.ResponseWrite(sbs.toString(), "application/json", response);
			}
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}
	
	/**
	 * ???????????????????????????
	 * options???????????????????????????
	 * teacher?????????????????????
	 */
	public void baseInfoEdit(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		if(us != null){
			Teacher teacher = this.iTeacherService.get(us.getId());
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"????????????\"");
			sb.append(",");
			sb.append("\"Records\":[");
			sb.append("{\"teacher\":[{");
			String graduation = teacher.getGraduation() == null?"???":teacher.getGraduation();
			if("".equals(teacher.getGraduation())){
				graduation = "???";
			}
			String major = teacher.getMajor() == null?"???":teacher.getMajor();
			if("".equals(teacher.getMajor())){
				major = "???";
			}
			sb.append("\"graduation\":\""+graduation+"\"");
			sb.append(",");
			sb.append("\"major\":\""+major+"\"");
			sb.append(",");
			sb.append("\"organization\":{");
			sb.append("\"name\":\""+teacher.getOrganization().getName()+"\"");
			sb.append(",");
			sb.append("\"id\":\""+teacher.getOrganization().getId()+"\"");
			sb.append("},");
			sb.append("\"eductionBackground\":{");
			sb.append("\"name\":\""+teacher.getEductionBackground().getName()+"\"");
			sb.append(",");
			sb.append("\"id\":\""+teacher.getEductionBackground().getId()+"\"");
			sb.append("},");
			sb.append("\"jobDuty\":{");
			sb.append("\"name\":\""+teacher.getJobDuty().getName()+"\"");
			sb.append(",");
			sb.append("\"id\":\""+teacher.getJobDuty().getId()+"\"");
			sb.append("},");
			sb.append("\"jobTitle\":{");
			sb.append("\"name\":\""+teacher.getJobTitle().getName()+"\"");
			sb.append(",");
			sb.append("\"id\":\""+teacher.getJobTitle().getId()+"\"");
			sb.append("},");
			sb.append("\"ethnic\":{");
			sb.append("\"name\":\""+teacher.getEthnic().getName()+"\"");
			sb.append(",");
			sb.append("\"id\":\""+teacher.getEthnic().getId()+"\"");
			sb.append("},");
			sb.append("\"politics\":{");
			sb.append("\"name\":\""+teacher.getPolitics().getName()+"\"");
			sb.append(",");
			sb.append("\"id\":\""+teacher.getPolitics().getId()+"\"");
			sb.append("},");
			sb.append("\"chineseLanguageLevel\":{");
			sb.append("\"name\":\""+teacher.getChineseLanguageLevel().getName()+"\"");
			sb.append(",");
			sb.append("\"id\":\""+teacher.getChineseLanguageLevel().getId()+"\"");
			sb.append("},");
			
			Area area = teacher.getArea();
			sb.append("\"area\":[{");
			if(area.getLevel() == 1){
				sb.append("\"province\":{");
				sb.append("\"name\":\""+area.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+area.getId()+"\"");
				sb.append("},");
				sb.append("\"city\":{");
				sb.append("\"name\":\"?????????\"");
				sb.append(",");
				sb.append("\"id\":\"0\"");
				sb.append("},");
				sb.append("\"country\":{");
				sb.append("\"name\":\"?????????\"");
				sb.append(",");
				sb.append("\"id\":\"0\"");
				sb.append("}}]");
			}
			if(area.getLevel() == 2){
				Area province = iAreaService.getAreaByCode(area.getParentcode());
				sb.append("\"province\":{");
				sb.append("\"name\":\""+province.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+province.getId()+"\"");
				sb.append("},");
				sb.append("\"city\":{");
				sb.append("\"name\":\""+area.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+area.getId()+"\"");
				sb.append("},");
				sb.append("\"country\":{");
				sb.append("\"name\":\"?????????\"");
				sb.append(",");
				sb.append("\"id\":\"0\"");
				sb.append("}}]");
			}
			if(area.getLevel() == 3){
				Area city = this.iAreaService.getAreaByCode(area.getParentcode());
				Area province = this.iAreaService.getAreaByCode(city.getParentcode());
				sb.append("\"province\":{");
				sb.append("\"name\":\""+province.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+province.getId()+"\"");
				sb.append("},");
				sb.append("\"city\":{");
				sb.append("\"name\":\""+city.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+city.getId()+"\"");
				sb.append("},");
				sb.append("\"country\":{");
				sb.append("\"name\":\""+area.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+area.getId()+"\"");
				sb.append("}}]");
			}
			if(area.getLevel() == 4){
				Area county = this.iAreaService.getAreaByCode(area.getParentcode());
				Area city = this.iAreaService.getAreaByCode(county.getParentcode());
				Area province = this.iAreaService.getAreaByCode(city.getParentcode());
				sb.append("\"province\":{");
				sb.append("\"name\":\""+province.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+province.getId()+"\"");
				sb.append("},");
				sb.append("\"city\":{");
				sb.append("\"name\":\""+city.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+city.getId()+"\"");
				sb.append("},");
				sb.append("\"country\":{");
				sb.append("\"name\":\""+county.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+county.getId()+"\"");
				sb.append("}}]");
			}
			if(area.getLevel() == 5){
				Area county = this.iAreaService.getAreaByCode(area.getParentcode());//???
				Area county2 = this.iAreaService.getAreaByCode(county.getParentcode());//???
				Area city = this.iAreaService.getAreaByCode(county2.getParentcode());
				Area province = this.iAreaService.getAreaByCode(city.getParentcode());
				sb.append("\"province\":{");
				sb.append("\"name\":\""+province.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+province.getId()+"\"");
				sb.append("},");
				sb.append("\"city\":{");
				sb.append("\"name\":\""+city.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+city.getId()+"\"");
				sb.append("},");
				sb.append("\"country\":{");
				sb.append("\"name\":\""+county2.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+county2.getId()+"\"");
				sb.append("}}]");
			}
			sb.append("}]");
			sb.append("},");
			sb.append("{\"options\":[{");
			
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
			
			//????????????
			
			Organization organization = iOrganizationService.get(26124);//??????26124
			Area province = organization.getArea();
			List<Area> lstCity = iAreaService.getParentCodeArea(province.getCode());
			
			//??????????????????????????????????????????
			sb.append("\"area\":[");
			sb.append("{\"province\":");//??????????????????
			sb.append("{");
			sb.append("\"id\":\""+province.getId()+"\"");
			sb.append(",");
			sb.append("\"name\":\""+province.getName()+"\"");
			sb.append("}},");
			
			sb.append("{\"lstCity\":[");//???????????????????????????
			if(!lstCity.isEmpty()){
				for(Area areas : lstCity){
					sb.append("{");
					sb.append("\"cityId\":\""+areas.getId()+"\"");
					sb.append(",");
					sb.append("\"cityName\":\""+areas.getName()+"\"");
					sb.append("},");
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]}");
			}
			sb.append("]},");
			
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
				sb.append(",");
			}
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
				sb.append(",");
			}
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
				sb.append(",");
			}
			
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
				sb.append(",");
			}
			
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
				sb.append(",");
			}
			
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
				sb.append(",");
			}
			sb.delete(sb.length()-1, sb.length());
			sb.append("]}");
			sb.append("]}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
	}
	
	/**
	 * ????????????????????????
	 */
	public void saveBaseInfo(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		
		UserSession us = (UserSession)session.getAttribute("teachersession");
		if(us != null){
			Teacher teacher = this.iTeacherService.get(us.getId());

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
			
//			// ??????
//			String backgroundString = "";
//			if(request.getParameter("eductionBackground")!=null){
//				backgroundString = request.getParameter("eductionBackground");
//			}
//			teacher.setEductionBackground(iEductionBackgroundService.geteEductionBackgroundById(backgroundString));
			
			//????????????
			String graduationString = "";
			if(request.getParameter("graduation")!=null && !"".equals(request.getParameter("graduation"))){
				graduationString = request.getParameter("graduation").trim();
			}else{
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"?????????????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			teacher.setGraduation(graduationString);
			
			//????????????
			String majorString = "";
			if(request.getParameter("major")!=null && !"".equals(request.getParameter("major"))){
				majorString = request.getParameter("major").trim();
			}else{
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"???????????????????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			teacher.setMajor(majorString);
			
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
			
			String updater = us.getRole()+"_"+us.getId();
			teacher.setUpdater(updater);
			try {
				this.iTeacherService.update(teacher);
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}
	
	/**
	 * ?????????????????????
	 */
	public void initTeachInfo(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		
		UserSession us = (UserSession)session.getAttribute("teachersession");
		if(us != null){
			Teacher teacher = this.iTeacherService.get(us.getId());
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"????????????\"");
			sb.append(",");
			sb.append("\"Records\":[");
			sb.append("{\"teacher\":[{");
			sb.append("\"teachingAge\":\""+teacher.getTeachingAge()+"\"");
			sb.append("},");
			int multiLanguage = teacher.getMultiLanguage() == true?1:0;
			sb.append("{\"multiLanguage\":\""+multiLanguage+"\"");
			sb.append("},");
			
			// ????????????
			String hqlString = "from TeachingGrade where teacher=" + teacher.getId();
			List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
			List<TeachingGrade> mainTeachingGrades = new ArrayList<TeachingGrade>();
			List<TeachingGrade> unMainTeachingGrades = new ArrayList<TeachingGrade>();
			for(TeachingGrade ttg: lstTeachingGrades){
				if(ttg.getIsprime()){
					mainTeachingGrades.add(ttg);
				}else{
					unMainTeachingGrades.add(ttg);
				}
			}
			if(mainTeachingGrades.size()>0){
				sb.append("{\"mainTeachingGrades\":");
				sb.append("{\"name\":\""+mainTeachingGrades.get(0).getGrade().getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+mainTeachingGrades.get(0).getGrade().getId()+"\"");
				sb.append("}},");
			} else {
				sb.append("{\"mainTeachingGrades\":");
				sb.append("{\"name\":\"?????????\"");
				sb.append(",");
				sb.append("\"id\":\"0\"");
				sb.append("}},");
			}
			
			sb.append("{\"unMainTeachingGrades\":[");
			if(unMainTeachingGrades.size()>0){
				
				for(TeachingGrade grade : unMainTeachingGrades){
					sb.append("{\"name\":\""+grade.getGrade().getName()+"\"");
					sb.append(",");
					sb.append("\"id\":\""+grade.getGrade().getId()+"\"");
					sb.append("},");
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]},");
			}else{
				sb.append("{\"name\":\"???\"");
				sb.append(",");
				sb.append("\"id\":\"-2\"");
				sb.append("}");
				sb.append("]},");
			}
			
			// ????????????
			hqlString = "from TeachingSubject where teacher=" + teacher.getId();
			List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
			List<TeachingSubject> mainTeachingSubject = new ArrayList<TeachingSubject>();
			List<TeachingSubject> unMainTeachingSubject = new ArrayList<TeachingSubject>();
			for (TeachingSubject ttsSubject : lsttTeachingSubjects) {
				if (ttsSubject.getIsprime()) {
					mainTeachingSubject.add(ttsSubject);
				} else {

					unMainTeachingSubject.add(ttsSubject);
				}
			}
			if(mainTeachingSubject.size()>0){
				sb.append("{\"mainTeachingSubject\":");
				sb.append("{\"name\":\""+mainTeachingSubject.get(0).getSubject().getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+mainTeachingSubject.get(0).getSubject().getId()+"\"");
				sb.append("}},");
			} else {
				sb.append("{\"mainTeachingSubject\":");
				sb.append("{\"name\":\"?????????\"");
				sb.append(",");
				sb.append("\"id\":\"0\"");
				sb.append("}},");
			}
			sb.append("{\"unMainTeachingSubject\":[");
			if(unMainTeachingSubject.size()>0){
				
				for(TeachingSubject subject : unMainTeachingSubject){
					sb.append("{\"name\":\""+subject.getSubject().getName()+"\"");
					sb.append(",");
					sb.append("\"id\":\""+subject.getSubject().getId()+"\"");
					sb.append("},");
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]},");
			}else{
				sb.append("{\"name\":\"???\"");
				sb.append(",");
				sb.append("\"id\":\"-2\"");
				sb.append("}");
				sb.append("]},");
			}
			
			// ????????????
			hqlString = "from TeachingLanguage where teacher=" + teacher.getId();
			List<TeachingLanguage> lsTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
			List<TeachingLanguage> mainTeachingLanguage = new ArrayList<TeachingLanguage>();
			List<TeachingLanguage> unMainTeachingLanguage = new ArrayList<TeachingLanguage>();
			for (TeachingLanguage ttlLanguage : lsTeachingLanguages) {
				if (ttlLanguage.getIsprime()) {
					mainTeachingLanguage.add(ttlLanguage);
				} else {
					unMainTeachingLanguage.add(ttlLanguage);
				}
			}
			if(mainTeachingLanguage.size()>0){
				sb.append("{\"mainTeachingLanguage\":");
				sb.append("{\"name\":\""+mainTeachingLanguage.get(0).getLanguage().getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+mainTeachingLanguage.get(0).getLanguage().getId()+"\"");
				sb.append("}},");
			} else {
				sb.append("{\"mainTeachingLanguage\":");
				sb.append("{\"name\":\"?????????\"");
				sb.append(",");
				sb.append("\"id\":\"0\"");
				sb.append("}},");
			}
			
			sb.append("{\"unMainTeachingLanguage\":[");
			if(unMainTeachingLanguage.size()>0){
				
				for(TeachingLanguage language : unMainTeachingLanguage){
					sb.append("{\"name\":\""+language.getLanguage().getName()+"\"");
					sb.append(",");
					sb.append("\"id\":\""+language.getLanguage().getId()+"\"");
					sb.append("},");
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]},");
			}else{
				sb.append("{\"name\":\"???\"");
				sb.append(",");
				sb.append("\"id\":\"-2\"");
				sb.append("}");
				sb.append("]},");
			}
			
			sb.delete(sb.length()-1, sb.length());
			sb.append("]},");
			sb.append("{\"options\":[");
			
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
			List<Language> lstLanguages = iLanguageService.findAll();
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
			sb.append("]}");
			sb.append("]}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
	}
	
	public void saveTeachInfo(){
		initServlert();
		
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		
		if(us != null){
			Teacher teacher = this.iTeacherService.get(us.getId());
			try {
				
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
				
				teacherEx teacherEx = new teacherEx();
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
				
				// ????????????
				//????????????????????????
				this.iTeachingSubjectService.deleteTeachingSubjectByTeacher(teacher.getId());
				TeachingSubject tSubject = new TeachingSubject();
				tSubject.setIsprime(true);
				tSubject.setTeacher(teacher);
				tSubject.setSubject(iSubjectService.get(Short.parseShort(request.getParameter("mainTeachingSubject"))));
				iTeachingSubjectService.add(tSubject);

				List<String> tlst = new ArrayList<>();
				if (request.getParameterValues("unMainTeachingSubject[]") != null) {
					String[] unMainTeachingSubject = request.getParameterValues("unMainTeachingSubject[]");
					if (unMainTeachingSubject != null) {
						if (unMainTeachingSubject.length > 0) {
							tlst.clear();
							for (String tid : unMainTeachingSubject) {
								if(!"".equals(tid)){
									if (!tlst.contains(tid) && !tid.equals(tSubject.getSubject().getId().toString())) {
										tlst.add(tid);
										TeachingSubject tS = new TeachingSubject();
										tS.setIsprime(false);
										tS.setTeacher(teacher);
										tS.setSubject(iSubjectService.get(Short.parseShort(tid)));
										iTeachingSubjectService.add(tS);
									}
								}
								
							}

						}
					}
				}
				// ????????????
				//?????????????????????
				this.iTeachingGradeService.deleteTeachingGradeByTeacher(teacher.getId());
				TeachingGrade tGrade = new TeachingGrade();

				tGrade.setIsprime(true);
				tGrade.setTeacher(teacher);
				tGrade.setGrade(iGradeService.get(Short.parseShort(request.getParameter("mainTeachingGrades"))));
				iTeachingGradeService.add(tGrade);
				if (request.getParameterValues("unMainTeachingGrades[]") != null) {
					String[] unMainTeachingGrades = request.getParameterValues("unMainTeachingGrades[]");

					if (unMainTeachingGrades.length > 0) {
						tlst.clear();
						for (String tid : unMainTeachingGrades) {
							if(!"".equals(tid)){
								if (!tlst.contains(tid) && !tid.equals(tGrade.getGrade().getId().toString())) {
									tlst.add(tid);
									TeachingGrade tg = new TeachingGrade();
									tg.setIsprime(false);
									tg.setTeacher(teacher);
									tg.setGrade(iGradeService.get(Short.parseShort(tid)));
									iTeachingGradeService.add(tg);
								}
							}
							
						}
					}
				}
				// ??????????????????
				//?????????????????????
				this.iTeachingLanguageService.deleteTeachingLanguage(teacher.getId());
				TeachingLanguage tLanguage = new TeachingLanguage();
				tLanguage.setIsprime(true);
				tLanguage.setTeacher(teacher);
				tLanguage.setLanguage(iLanguageService.get(Short.parseShort(request.getParameter("mainTeachingLanguage"))));
				iTeachingLanguageService.add(tLanguage);
				if (request.getParameterValues("unMainTeachingLanguage[]") != null) {
					String[] unMainTeachingLanguage = request.getParameterValues("unMainTeachingLanguage[]");

					if (unMainTeachingLanguage.length > 0) {
						tlst.clear();
						for (String tid : unMainTeachingLanguage) {
							if(!"".equals(tid)){
								if (!tlst.contains(tid) && !tid.equals(tLanguage.getLanguage().getId().toString())) {
									tlst.add(tid);
									TeachingLanguage tL = new TeachingLanguage();
									tL.setIsprime(false);
									tL.setTeacher(teacher);
									tL.setLanguage(iLanguageService.get(Short.parseShort(tid)));
									iTeachingLanguageService.add(tL);
								}
							}
						}
					}
				}
				String updater = us.getRole()+"_"+us.getId();
				teacher.setUpdater(updater);
				this.iTeacherService.update(teacher);
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				
			} catch (Exception e) {
				e.printStackTrace();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}
	
	/**
	 * ???????????????????????????
	 */
	public void initMobilePage(){
		initServlert();
		
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		if(us != null){
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"????????????\"");
			sb.append(",");
			sb.append("\"Records\":");
			sb.append("{");
			String mobile = us.getMobile() == null ? "??????":us.getMobile();
			sb.append("\"mobile\":\""+mobile+"\"");
			sb.append("}}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}
	
	/**
	 * ???????????????????????????
	 * 
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
		UserSession us = (UserSession)session.getAttribute("teachersession");		
		TeacherMobileCode tmobileCode = new TeacherMobileCode();
		String uuid = Utlity.getUUID();
		tmobileCode.setCode(captcha+"");
		tmobileCode.setUuid(uuid);
		tmobileCode.setCreattime(new Timestamp(System.currentTimeMillis()));
		if(us != null){
			tmobileCode.setTeacher(us.getId());
		}else{
			pageStatus = "FAILED";
			message = "??????????????????";
			sb.append("{");
			sb.append("\"Result\":\""+pageStatus+"\"");
			sb.append(",");
			sb.append("\"Message\":\""+message+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		
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
				sb.append("\"Message\":\"??????\"");
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
				sb.append("\"Message\":\"??????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				tmobileCode.setStatus(Short.parseShort(status));
				tmobileCode.setMsg(msg);
				this.iTeacherMobileCodeService.add(tmobileCode);
			}
			
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
	/**
	 * ??????????????????
	 */
	public void changeMobile(){
		initServlert();
		
		String pageStatus = "";
		String message = "";
		
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		
		if( us == null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
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
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		} else {
			if (iTeacherService.existMobile(mobile.trim())) {
				pageStatus = "ERRORMOBILE";
				message = "???????????????????????????";
				sb.append("{");
				sb.append("\"Result\":\""+pageStatus+"\"");
				sb.append(",");
				sb.append("\"Message\":\""+message+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			} else if(mobile.equals(us.getMobile())){
				pageStatus = "ERRORMOBILE";
				message = "????????????????????????";
				sb.append("{");
				sb.append("\"Result\":\""+pageStatus+"\"");
				sb.append(",");
				sb.append("\"Message\":\""+message+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
		}
		//???????????????
		String code = "";
		if(request.getParameter("code")!=null && !"".equals(request.getParameter("code"))){
			code = request.getParameter("code");
		}
		String uuid = (String)session.getAttribute("code");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		if(uuid != null){
			TeacherMobileCode tmc = this.iTeacherMobileCodeService.getRecordByUuid(uuid);
			if(tmc != null){
				int seconds = (int)Math.ceil((time.getTime()-tmc.getCreattime().getTime())/(60*1000));
				if(!tmc.getCode().equals(code)){
					pageStatus = "ERRORCODE";
					message = "???????????????????????????????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}else if(seconds>20){
					pageStatus = "ERRORCODE";
					message = "?????????????????????????????????????????????";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
			}
		}else{
			pageStatus = "ERRORCODE";
			message = "??????????????????????????????";
			sb.append("{");
			sb.append("\"Result\":\""+pageStatus+"\"");
			sb.append(",");
			sb.append("\"Message\":\""+message+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		Teacher teacher = this.iTeacherService.get(us.getId());
		if(teacher != null){
			teacher.setMobile(mobile);
			String updater = us.getRole()+"_"+us.getId();
			teacher.setUpdater(updater);
			this.iTeacherService.update(teacher);
			pageStatus = "OK";
			message = "???????????????";
			sb.append("{");
			sb.append("\"Result\":\""+pageStatus+"\"");
			sb.append(",");
			sb.append("\"Message\":\""+message+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}else{
			pageStatus = "ERROR";
			message = "???????????????";
			sb.append("{");
			sb.append("\"Result\":\""+pageStatus+"\"");
			sb.append(",");
			sb.append("\"Message\":\""+message+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
	}
	
	/**
	 * ????????????
	 */
	public void changePassword(){
		initServlert();
		
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		
		String password = "";
		if(request.getParameter("password") != null && !"".equals(request.getParameter("password"))){
			password = request.getParameter("password");
		}
		String oldpwd = "";
		if(request.getParameter("oldpwd") != null && !"".equals(request.getParameter("oldpwd"))){
			oldpwd = request.getParameter("oldpwd");
		}
		
		if(us != null){
			try {
					
				Teacher teacher = this.iTeacherService.get(us.getId());
				if(oldpwd.equals(teacher.getPassword())){
					teacher.setPassword(password);
					String updater = us.getRole()+"_"+us.getId();
					teacher.setUpdater(updater);
					this.iTeacherService.update(teacher);
					sb.append("{");
					sb.append("\"Result\":\"OK\"");
					sb.append(",");
					sb.append("\"Message\":\"????????????\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
				}else{
					sb.append("{");
					sb.append("\"Result\":\"ERROR\"");
					sb.append(",");
					sb.append("\"Message\":\"????????????\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
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
			string = string.replaceAll("'", "");
			string = string.replaceAll("\"", "\\\"");
			string = string.replaceAll(";", "");
			String hqlString = "";

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
	
	/**
	 * ??????/??????????????????
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void inputImage() {

		initServlert();
		
		UserSession us = (UserSession)session.getAttribute("teachersession");
		
		StringBuilder sb = new StringBuilder();
		

		ImgResult result = new ImgResult();
		result.avatarUrls = new ArrayList();
		result.success = false;
		result.msg = "Failure!";


		if(us == null){
			result.msg = "???????????????";
			sb.append(JSONObject.fromObject(result).toString());
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (isMultipart) {
			Teacher teacher = this.iTeacherService.get(us.getId());
			String serverPath = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/");
			serverPath = Utlity.getRealPath(ServletActionContext.getServletContext());

//			String userid;
//			String username;

			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				FileItemIterator fileItems = upload.getItemIterator(request);
				// ???????????????????????????????????????????????????
				int avatarNumber = 1;
				// ??????????????????+8???????????????????????????????????????????????????????????????
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyyMMddHHmmssS");
				String fileName = simpleDateFormat.format(new Date());
				Random random = new Random();
				String randomCode = "";
				for (int i = 0; i < 8; i++) {
					randomCode += Integer.toString(random.nextInt(36), 36);
				}
				fileName = fileName + randomCode;
				// ??????????????????????????????
				String initParams = "";
				BufferedInputStream inputStream;
				BufferedOutputStream outputStream;
				// ???????????????
				while (fileItems.hasNext()) {
					FileItemStream fileItem = fileItems.next();
					String fieldName = fileItem.getFieldName();
					// ????????????????????? file ???????????????????????? file
					// ???????????????__source??????????????????????????????????????????????????????src_field_name???
					Boolean isSourcePic = fieldName.equals("__source");
					// ???????????????????????????????????????????????????????????????????????????????????????????????????????????????POST???????????????????????????????????????????????????????????????????????????????????????????????????
					// ??????????????????????????????????????????url???????????????url+??????????????????????????????????????????url????????????????????????????????????
					if (fieldName.equals("__initParams")) {
						inputStream = new BufferedInputStream(
								fileItem.openStream());
						byte[] bytes = new byte[inputStream.available()];
						inputStream.read(bytes);
						initParams = new String(bytes, "UTF-8");
						inputStream.close();
					}
					// ????????????????????? file
					// ?????????????????????????????????????????????????????????__avatar?????????(???????????????????????????__avatar1,2,3...??????????????????????????????????????????????????????avatar_field_names)
					else if (isSourcePic || fieldName.startsWith("__avatar")) {
						
						String[] dir = UUID.randomUUID().toString().split("-");
						String basePath = "upload/headImage/";

						for (String sPath : dir) {
							basePath += sPath + "/";

							File tfFile = new File(serverPath + "/" + basePath);
							if (!tfFile.exists()) {
								tfFile.mkdirs();
							}
						}
						
						String virtualPath = basePath
								+ avatarNumber + "_" + fileName + ".jpg";
						
						// ???????????????????????? file
						// ???????????????__source??????????????????????????????????????????????????????src_field_name??????
						if (isSourcePic) {
							// ????????????????????????????????????????????????????????????????????????????????????????????? *FromWebcam.jpg
							String sourceFileName = fileItem.getName();
							// ????????????????????????(????????????.???)
							String sourceExtendName = sourceFileName
									.substring(sourceFileName.lastIndexOf('.') + 1);
//							result.sourceUrl = virtualPath = String.format(
//									"/upload/headImage/jsp_source_%s.%s", fileName,
//									sourceExtendName);
							result.sourceUrl = basePath + fileName +"."+ sourceExtendName;
						}
						// ???????????????????????? file
						// ???????????????__avatar1,2,3...??????????????????????????????????????????????????????avatar_field_names??????
						else {
							result.avatarUrls.add("/"+virtualPath);
							avatarNumber++;
						}
						inputStream = new BufferedInputStream(
								fileItem.openStream());
						outputStream = new BufferedOutputStream(
								new FileOutputStream(serverPath + "/"
										+ virtualPath));
						Streams.copy(inputStream, outputStream, true);
						inputStream.close();
						outputStream.flush();
						outputStream.close();
					} else {
						// ?????????
						// upload_url??????????????????????????????????????????method???post????????????????????????????????????????????????????????????????????????????????????????????????
						inputStream = new BufferedInputStream(
								fileItem.openStream());
						byte[] bytes = new byte[inputStream.available()];
						inputStream.read(bytes);
						inputStream.close();
						if (fieldName.equals("userid")) {
							result.userid = new String(bytes, "UTF-8");
						} else if (fieldName.equals("username")) {
							result.username = new String(bytes, "UTF-8");
						}
					}
				}
				// ????????? upload_url??????????????????????????????????????????method???get??????????????????????????????
				/*
				 * result.userid = request.getParameter("userid");
				 * result.username = request.getParameter("username");
				 */

				if (result.sourceUrl != null) {
					result.sourceUrl += initParams;
					teacher.setHeadPicPath(result.getSourceUrl());
				}else{
					teacher.setHeadPicPath(result.avatarUrls.get(0).toString());
				}
				try {
					String updater = us.getRole()+"_"+us.getId();
					teacher.setUpdater(updater);
					this.iTeacherService.update(teacher);
					result.success = true;
					result.msg = "Success!";
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					result.msg = "??????????????????!";
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				result.setSuccess(false);
				result.msg = "????????????????????????!";
			}
			/*
			 * To Do...??????????????????????????????
			 */
			// ?????????????????????????????????????????????json??????????????????????????????????????????fastjson?????????
			JSONObject obj = JSONObject.fromObject(result);
			sb.append(obj.toString());
			
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}
	
	/**
	 * ??????????????????
	 */
	public void getHeadImg(){
		initServlert();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		StringBuilder sb = new StringBuilder();
		
		if(us == null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		Teacher teacher = this.iTeacherService.get(us.getId());
		
		if(teacher != null){
				String path = ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath();
				String headImgPath = teacher.getHeadPicPath() == null ? "0":path+teacher.getHeadPicPath();
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????\"");
				sb.append(",");
				sb.append("\"Records\":");
				sb.append("{");
				sb.append("\"name\":\""+teacher.getName()+"\"");
				sb.append(",");
				sb.append("\"headImgPath\":\""+headImgPath+"\"");
				sb.append("}");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				
		}
	}
	
	/**
	 * ?????????????????????
	 */
	@SuppressWarnings("rawtypes")
	public void getInboxList() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("teachersession");
		Map<String, Object> params = new HashMap<String, Object>();
//		inboxMap = new HashMap<Integer, String[]>();
//		String[] str = null;

		// ??????(???????????????)
		String sort = "sendtime desc";
		// ?????? ??? ????????????????????????
		String status = request.getParameter("status") == null ? "0" : request.getParameter("status");;
		if("".equals(status)){
			status = "0";
		}
		if (!"0".equals(status)) {
			params.put("status", status);
		}
		params.put("paId", us.getId());// ????????????id
		params.put("role", us.getRole());// ??????????????????
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
					//?????????????????????????????????????????????
					int id = mc.getId();//??????ID
					int mid = mi.getId();
					short type = mi.getType();//????????????
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
			csb.append("\"Message\":\"??????????????????\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
		}
//		return "inboxList";
	}
	
	/**
	 * ??????????????????
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
			csb.append("\"Message\":\"????????????\"");
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
			csb.append("\"Message\":\"?????????????????????\"");
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
	 * ??????????????????
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
			csb.append("\"Message\":\"????????????\"");
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
			csb.append("\"Message\":\"?????????????????????\"");
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
	 * ????????????
	 */
	public void deletemail(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();
		String type = request.getParameter("type") == null ?"":request.getParameter("type");
		if("".equals(type)){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"????????????\"");
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
			csb.append("\"Message\":\"????????????\"");
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
			sb.append("\"Message\":\"??????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
	}
	
	/**
	 * ??????????????????
	 * status 1?????? 0??????
	 */
	public void getReplaList(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("teachersession");
		StringBuilder sb = new StringBuilder();
		String id = request.getParameter("id") == null ?"":request.getParameter("id");
		if("".equals(id)){
			StringBuilder csb = new StringBuilder();
			csb.append("{");
			csb.append("\"Result\":\"ERROR\"");
			csb.append(",");
			csb.append("\"Message\":\"????????????\"");
			csb.append("}");
			Utlity.ResponseWrite(csb.toString(), "application/json", response);
			return;
		}
		String status = request.getParameter("status") == null ? "0" : request.getParameter("status");;
		if("".equals(status)){
			status = "0";
		}
		// ??????
		// ?????????
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
	private void getQueryParameter(UserSession us, Map<String, Object> params) {
		// ????????????
		String content = request.getParameter("content");

		if (content != null && content != "") {
			params.put("content", content);
		}

		// ??????
		// ?????????
		String ist = (String) request.getParameter("page");

		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);
		offset = (start - 1) * DictionyMap.maxPageSize;
	}
	
	/**
	 * ???????????????????????????
	 * ???????????????????????????????????????????????????????????????????????????
	 */
	public void initCheckTeacherInfo(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		Object us = session.getAttribute("teacherId");
		if(us != null){
			String teacherId = us.toString();

			Teacher teacher = this.iTeacherService.get(Integer.parseInt(teacherId));
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"????????????\"");
			sb.append(",");
			sb.append("\"Records\":[");
			sb.append("{\"teacher\":[{");
			sb.append("\"organization\":{");
			sb.append("\"name\":\""+teacher.getOrganization().getName()+"\"");
			sb.append(",");
			sb.append("\"id\":\""+teacher.getOrganization().getId()+"\"");
			sb.append("},");
			sb.append("\"eductionBackground\":{");
			sb.append("\"name\":\""+teacher.getEductionBackground().getName()+"\"");
			sb.append(",");
			sb.append("\"id\":\""+teacher.getEductionBackground().getId()+"\"");
			sb.append("},");
			sb.append("\"jobDuty\":{");
			sb.append("\"name\":\""+teacher.getJobDuty().getName()+"\"");
			sb.append(",");
			sb.append("\"id\":\""+teacher.getJobDuty().getId()+"\"");
			sb.append("},");
			sb.append("\"jobTitle\":{");
			sb.append("\"name\":\""+teacher.getJobTitle().getName()+"\"");
			sb.append(",");
			sb.append("\"id\":\""+teacher.getJobTitle().getId()+"\"");
			sb.append("},");
			sb.append("\"ethnic\":{");
			sb.append("\"name\":\""+teacher.getEthnic().getName()+"\"");
			sb.append(",");
			sb.append("\"id\":\""+teacher.getEthnic().getId()+"\"");
			sb.append("},");
			String politicss = "";
			if(teacher.getPolitics() != null){
				politicss = teacher.getPolitics().getName();
			}
			sb.append("\"politics\":{");
			sb.append("\"name\":\""+politicss+"\"");
			sb.append(",");
			sb.append("\"id\":\""+teacher.getPolitics().getId()+"\"");
			sb.append("},");
			sb.append("\"chineseLanguageLevel\":{");
			sb.append("\"name\":\""+teacher.getChineseLanguageLevel().getName()+"\"");
			sb.append(",");
			sb.append("\"id\":\""+teacher.getChineseLanguageLevel().getId()+"\"");
			sb.append("},");
			
			Area area = teacher.getArea();
			sb.append("\"area\":[{");
			if(area.getLevel() == 1){
				sb.append("\"province\":{");
				sb.append("\"name\":\""+area.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+area.getId()+"\"");
				sb.append("},");
				sb.append("\"city\":{");
				sb.append("\"name\":\"?????????\"");
				sb.append(",");
				sb.append("\"id\":\"0\"");
				sb.append("},");
				sb.append("\"country\":{");
				sb.append("\"name\":\"?????????\"");
				sb.append(",");
				sb.append("\"id\":\"0\"");
				sb.append("}}]");
			}
			if(area.getLevel() == 2){
				Area province = iAreaService.getAreaByCode(area.getParentcode());
				sb.append("\"province\":{");
				sb.append("\"name\":\""+province.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+province.getId()+"\"");
				sb.append("},");
				sb.append("\"city\":{");
				sb.append("\"name\":\""+area.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+area.getId()+"\"");
				sb.append("},");
				sb.append("\"country\":{");
				sb.append("\"name\":\"?????????\"");
				sb.append(",");
				sb.append("\"id\":\"0\"");
				sb.append("}}]");
			}
			if(area.getLevel() == 3){
				Area city = this.iAreaService.getAreaByCode(area.getParentcode());
				Area province = this.iAreaService.getAreaByCode(city.getParentcode());
				sb.append("\"province\":{");
				sb.append("\"name\":\""+province.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+province.getId()+"\"");
				sb.append("},");
				sb.append("\"city\":{");
				sb.append("\"name\":\""+city.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+city.getId()+"\"");
				sb.append("},");
				sb.append("\"country\":{");
				sb.append("\"name\":\""+area.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+area.getId()+"\"");
				sb.append("}}]");
			}
			if(area.getLevel() == 4){
				Area xiang = this.iAreaService.getAreaByCode(area.getParentcode());
				Area city = this.iAreaService.getAreaByCode(xiang.getParentcode());
				Area province = this.iAreaService.getAreaByCode(city.getParentcode());
				
				sb.append("\"province\":{");
				sb.append("\"name\":\""+province.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+province.getId()+"\"");
				sb.append("},");
				sb.append("\"city\":{");
				sb.append("\"name\":\""+city.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+city.getId()+"\"");
				sb.append("},");
				sb.append("\"country\":{");
				sb.append("\"name\":\""+area.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+area.getId()+"\"");
				sb.append("}}]");
			}
			
			if(area.getLevel() == 5){
				Area cun = this.iAreaService.getAreaByCode(area.getParentcode());
				Area xiang = this.iAreaService.getAreaByCode(cun.getParentcode());
				Area city = this.iAreaService.getAreaByCode(xiang.getParentcode());
				Area province = this.iAreaService.getAreaByCode(city.getParentcode());
				
				sb.append("\"province\":{");
				sb.append("\"name\":\""+province.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+province.getId()+"\"");
				sb.append("},");
				sb.append("\"city\":{");
				sb.append("\"name\":\""+city.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+city.getId()+"\"");
				sb.append("},");
				sb.append("\"country\":{");
				sb.append("\"name\":\""+area.getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+area.getId()+"\"");
				sb.append("}}]");
			}
			
			sb.append(",");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String teacherAge = "";
			if(teacher.getTeachingAge() != null){
				teacherAge = df.format(teacher.getTeachingAge());
			}
			sb.append("\"teachingAge\":\""+teacherAge+"\"");
			sb.append(",");
			int multiLanguage = teacher.getMultiLanguage() == true?1:0;
			sb.append("\"multiLanguage\":\""+multiLanguage+"\"");
			sb.append(",");
			
			sb.append("\"name\":\""+teacher.getName()+"\"");
			sb.append(",");
			sb.append("\"idCard\":\""+teacher.getIdcard()+"\"");
			sb.append(",");
			String email = "";
			if(teacher.getEmail() != null){
				email = teacher.getEmail();
			}
			sb.append("\"email\":\""+email+"\"");
			sb.append(",");
			String path = ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath();
			String headImgPath = teacher.getHeadPicPath() == null ? "0":path+teacher.getHeadPicPath();
			sb.append("\"headImgPath\":\""+headImgPath+"\"");
			sb.append(",");
			String mobile = "";
			if(teacher.getMobile() != null){
				mobile = teacher.getMobile();
			}
			sb.append("\"mobile\":\""+mobile+"\"");
			sb.append(",");
			String graduation = teacher.getGraduation() == null ? "" : teacher.getGraduation();
			sb.append("\"graduation\":\""+graduation+"\"");
			sb.append(",");
			String major = teacher.getMajor() == null ? "" : teacher.getMajor();
			sb.append("\"major\":\""+major+"\"");
			sb.append(",");
			
			// ????????????
			String hqlString = "from TeachingGrade where teacher=" + teacher.getId();
			List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
			List<TeachingGrade> mainTeachingGrades = new ArrayList<TeachingGrade>();
			List<TeachingGrade> unMainTeachingGrades = new ArrayList<TeachingGrade>();
			for(TeachingGrade ttg: lstTeachingGrades){
				if(ttg.getIsprime()){
					mainTeachingGrades.add(ttg);
				}else{
					unMainTeachingGrades.add(ttg);
				}
			}
			if(mainTeachingGrades.size()>0){
				sb.append("\"mainTeachingGrades\":");
				sb.append("{\"name\":\""+mainTeachingGrades.get(0).getGrade().getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+mainTeachingGrades.get(0).getGrade().getId()+"\"");
				sb.append("},");
			}else{
				sb.append("\"mainTeachingGrades\":");
				sb.append("{\"name\":\"???\"");
				sb.append(",");
				sb.append("\"id\":\"-2\"");
				sb.append("}");
				sb.append(",");
			}
			
			sb.append("\"unMainTeachingGrades\":[");
			if(unMainTeachingGrades.size()>0){
				
				for(TeachingGrade grade : unMainTeachingGrades){
					sb.append("{\"name\":\""+grade.getGrade().getName()+"\"");
					sb.append(",");
					sb.append("\"id\":\""+grade.getGrade().getId()+"\"");
					sb.append("},");
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("],");
			}else{
				sb.append("{\"name\":\"???\"");
				sb.append(",");
				sb.append("\"id\":\"-2\"");
				sb.append("}");
				sb.append("],");
			}
			
			// ????????????
			hqlString = "from TeachingSubject where teacher=" + teacher.getId();
			List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
			List<TeachingSubject> mainTeachingSubject = new ArrayList<TeachingSubject>();
			List<TeachingSubject> unMainTeachingSubject = new ArrayList<TeachingSubject>();
			for (TeachingSubject ttsSubject : lsttTeachingSubjects) {
				if (ttsSubject.getIsprime()) {
					mainTeachingSubject.add(ttsSubject);
				} else {

					unMainTeachingSubject.add(ttsSubject);
				}
			}
			if(mainTeachingSubject.size()>0){
				sb.append("\"mainTeachingSubject\":");
				sb.append("{\"name\":\""+mainTeachingSubject.get(0).getSubject().getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+mainTeachingSubject.get(0).getSubject().getId()+"\"");
				sb.append("},");
			}else{
				sb.append("\"mainTeachingSubject\":");
				sb.append("{\"name\":\"???\"");
				sb.append(",");
				sb.append("\"id\":\"-2\"");
				sb.append("}");
				sb.append(",");
			}
			sb.append("\"unMainTeachingSubject\":[");
			if(unMainTeachingSubject.size()>0){
				
				for(TeachingSubject subject : unMainTeachingSubject){
					sb.append("{\"name\":\""+subject.getSubject().getName()+"\"");
					sb.append(",");
					sb.append("\"id\":\""+subject.getSubject().getId()+"\"");
					sb.append("},");
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("],");
			}else{
				sb.append("{\"name\":\"???\"");
				sb.append(",");
				sb.append("\"id\":\"-2\"");
				sb.append("}");
				sb.append("],");
			}
			
			// ????????????
			hqlString = "from TeachingLanguage where teacher=" + teacher.getId();
			List<TeachingLanguage> lsTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
			List<TeachingLanguage> mainTeachingLanguage = new ArrayList<TeachingLanguage>();
			List<TeachingLanguage> unMainTeachingLanguage = new ArrayList<TeachingLanguage>();
			for (TeachingLanguage ttlLanguage : lsTeachingLanguages) {
				if (ttlLanguage.getIsprime()) {
					mainTeachingLanguage.add(ttlLanguage);
				} else {
					unMainTeachingLanguage.add(ttlLanguage);
				}
			}
			if(mainTeachingLanguage.size()>0){
				sb.append("\"mainTeachingLanguage\":");
				sb.append("{\"name\":\""+mainTeachingLanguage.get(0).getLanguage().getName()+"\"");
				sb.append(",");
				sb.append("\"id\":\""+mainTeachingLanguage.get(0).getLanguage().getId()+"\"");
				sb.append("},");
			}else{
				sb.append("\"mainTeachingLanguage\":");
				sb.append("{\"name\":\"???\"");
				sb.append(",");
				sb.append("\"id\":\"-2\"");
				sb.append("}");
				sb.append(",");
			}
			
			sb.append("\"unMainTeachingLanguage\":[");
			if(unMainTeachingLanguage.size()>0){
				
				for(TeachingLanguage language : unMainTeachingLanguage){
					sb.append("{\"name\":\""+language.getLanguage().getName()+"\"");
					sb.append(",");
					sb.append("\"id\":\""+language.getLanguage().getId()+"\"");
					sb.append("},");
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]");
			}else{
				sb.append("{\"name\":\"???\"");
				sb.append(",");
				sb.append("\"id\":\"-2\"");
				sb.append("}");
				sb.append("]");
			}
			
			sb.append("}]");
			sb.append("},");
			sb.append("{\"options\":[{");
			
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
			
			//????????????
			
			Organization organization = iOrganizationService.get(26124);//??????26124
			Area province = organization.getArea();
			List<Area> lstCity = iAreaService.getParentCodeArea(province.getCode());
			
			//??????????????????????????????????????????
			sb.append("\"area\":[");
			sb.append("{\"province\":");//??????????????????
			sb.append("{");
			sb.append("\"id\":\""+province.getId()+"\"");
			sb.append(",");
			sb.append("\"name\":\""+province.getName()+"\"");
			sb.append("}},");
			
			sb.append("{\"lstCity\":[");//???????????????????????????
			if(!lstCity.isEmpty()){
				for(Area areas : lstCity){
					sb.append("{");
					sb.append("\"cityId\":\""+areas.getId()+"\"");
					sb.append(",");
					sb.append("\"cityName\":\""+areas.getName()+"\"");
					sb.append("},");
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]}");
			}
			sb.append("]},");
			
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
				sb.append(",");
			}
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
				sb.append(",");
			}
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
				sb.append(",");
			}
			
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
				sb.append(",");
			}
			
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
				sb.append(",");
			}
			
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
				sb.append(",");
			}
//			sb.delete(sb.length()-1, sb.length());
			
			
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
			List<Language> lstLanguages = iLanguageService.findAll();
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
			
			sb.append("]}");
			sb.append("]}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
	}
	
	/**
	 * ??????????????????????????????????????????
	 */
	public void saveCheckTeacherInfo(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		
		Object us = session.getAttribute("teacherId");
		if(us != null){
			try {
				String teacherId = us.toString();
				Teacher teacher = this.iTeacherService.get(Integer.parseInt(teacherId));

				/*
				 * ????????? ????????????
				 */
				String email = "";
				if(request.getParameter("email")!=null){
//					email = request.getParameter("email").trim();
					email = URLDecoder.decode(request.getParameter("email").trim(),"UTF-8");
				}else{
					sb.append("{");
					sb.append("\"Result\":\"ERROREMAIL\"");
					sb.append(",");
					sb.append("\"Message\":\"???????????????????????????\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				if (!Utlity.isEmail(email)) {
					sb.append("{");
					sb.append("\"Result\":\"ERROREMAIL\"");
					sb.append(",");
					sb.append("\"Message\":\"????????????????????????\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				} else {
					boolean flag = iTeacherService.existEmail(email);
					if (teacher.getEmail() == null && flag) {
						sb.append("{");
						sb.append("\"Result\":\"ERROREMAIL\"");
						sb.append(",");
						sb.append("\"Message\":\"???????????????\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;
					} else if (!email.equals(teacher.getEmail()) && flag) {
						sb.append("{");
						sb.append("\"Result\":\"ERROREMAIL\"");
						sb.append(",");
						sb.append("\"Message\":\"???????????????\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;
					}

				}
				teacher.setEmail(email);
				// ??????
				String mobile = "";
				if(request.getParameter("mobile")!=null){
					mobile = request.getParameter("mobile");
				}else{
					sb.append("{");
					sb.append("\"Result\":\"ERRORMOBILE\"");
					sb.append(",");
					sb.append("\"Message\":\"?????????????????????\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				if (!Utlity.isMobileNO(mobile.trim())) {
					sb.append("{");
					sb.append("\"Result\":\"ERRORMOBILE\"");
					sb.append(",");
					sb.append("\"Message\":\"??????????????????\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				} else {
					boolean flag = iTeacherService.existMobile(mobile.trim());
					if (teacher.getMobile() == null && flag) {
						sb.append("{");
						sb.append("\"Result\":\"ERRORMOBILE\"");
						sb.append(",");
						sb.append("\"Message\":\"?????????????????????\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;
					}  else if (!mobile.trim().equals(teacher.getMobile()) && flag) {
						sb.append("{");
						sb.append("\"Result\":\"ERRORMOBILE\"");
						sb.append(",");
						sb.append("\"Message\":\"?????????????????????\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;
					}
				}
				teacher.setMobile(mobile);
				//?????????????????????????????????
				String graduation = request.getParameter("graduation") == null ? "" : request.getParameter("graduation");
				String major = request.getParameter("major") == null ? "" : request.getParameter("major");
				if("".equals(major)){
					sb.append("{");
					sb.append("\"Result\":\"ERRORMAJOR\"");
					sb.append(",");
					sb.append("\"Message\":\"?????????????????????\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				if("".equals(graduation)){
					sb.append("{");
					sb.append("\"Result\":\"ERRORGRADUATION\"");
					sb.append(",");
					sb.append("\"Message\":\"?????????????????????\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				//??????????????????
				short languageId = 0;
				if(request.getParameter("mainTeachingLanguage")!=null){
					languageId = Short.parseShort(request.getParameter("mainTeachingLanguage"));
				}
//				if(languageId == -2){
//					sb.append("{");
//					sb.append("\"Result\":\"ERRORGRADUATION\"");
//					sb.append(",");
//					sb.append("\"Message\":\"?????????????????????\"");
//					sb.append("}");
//					Utlity.ResponseWrite(sb.toString(), "application/json", response);
//					return;
//				}
				
				
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
				
				
				/*
				 * ????????????
				 */
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
				
				teacherEx teacherEx = new teacherEx();
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
				

				TeachingLanguage mainlLanguage = new TeachingLanguage();
				mainlLanguage.setLanguage(iLanguageService.get(languageId));
				mainlLanguage.setIsprime(true);
				teacherEx.setMainTeachingLanguage(mainlLanguage);
				
				// ????????????
				//????????????????????????
				this.iTeachingSubjectService.deleteTeachingSubjectByTeacher(teacher.getId());
				TeachingSubject tSubject = new TeachingSubject();
				tSubject.setIsprime(true);
				tSubject.setTeacher(teacher);
				tSubject.setSubject(iSubjectService.get(Short.parseShort(request.getParameter("mainTeachingSubject"))));
				iTeachingSubjectService.add(tSubject);

				List<String> tlst = new ArrayList<>();
				if (request.getParameterValues("unMainTeachingSubject[]") != null) {
					String[] unMainTeachingSubject = request.getParameterValues("unMainTeachingSubject[]");
					if (unMainTeachingSubject != null) {
						if (unMainTeachingSubject.length > 0) {
							tlst.clear();
							for (String tid : unMainTeachingSubject) {
								if(!"".equals(tid)){
									if (!tlst.contains(tid) && !tid.equals(tSubject.getSubject().getId().toString())) {
										tlst.add(tid);
										TeachingSubject tS = new TeachingSubject();
										tS.setIsprime(false);
										tS.setTeacher(teacher);
										tS.setSubject(iSubjectService.get(Short.parseShort(tid)));
										iTeachingSubjectService.add(tS);
									}
								}
								
							}

						}
					}
				}
				// ????????????
				//?????????????????????
				this.iTeachingGradeService.deleteTeachingGradeByTeacher(teacher.getId());
				TeachingGrade tGrade = new TeachingGrade();

				tGrade.setIsprime(true);
				tGrade.setTeacher(teacher);
				tGrade.setGrade(iGradeService.get(Short.parseShort(request.getParameter("mainTeachingGrades"))));
				iTeachingGradeService.add(tGrade);
				if (request.getParameterValues("unMainTeachingGrades[]") != null) {
					String[] unMainTeachingGrades = request.getParameterValues("unMainTeachingGrades[]");

					if (unMainTeachingGrades.length > 0) {
						tlst.clear();
						for (String tid : unMainTeachingGrades) {
							if(!"".equals(tid)){
								if (!tlst.contains(tid) && !tid.equals(tGrade.getGrade().getId().toString())) {
									tlst.add(tid);
									TeachingGrade tg = new TeachingGrade();
									tg.setIsprime(false);
									tg.setTeacher(teacher);
									tg.setGrade(iGradeService.get(Short.parseShort(tid)));
									iTeachingGradeService.add(tg);
								}
							}
							
						}
					}
				}
				// ??????????????????
				//?????????????????????
				this.iTeachingLanguageService.deleteTeachingLanguage(teacher.getId());
				TeachingLanguage tLanguage = new TeachingLanguage();
				tLanguage.setIsprime(true);
				tLanguage.setTeacher(teacher);
				tLanguage.setLanguage(iLanguageService.get(Short.parseShort(request.getParameter("mainTeachingLanguage"))));
				iTeachingLanguageService.add(tLanguage);
				if (request.getParameterValues("unMainTeachingLanguage[]") != null) {
					String[] unMainTeachingLanguage = request.getParameterValues("unMainTeachingLanguage[]");

					if (unMainTeachingLanguage.length > 0) {
						tlst.clear();
						for (String tid : unMainTeachingLanguage) {
							if(!"".equals(tid)){
								if (!tlst.contains(tid) && !tid.equals(tLanguage.getLanguage().getId().toString())) {
									tlst.add(tid);
									TeachingLanguage tL = new TeachingLanguage();
									tL.setIsprime(false);
									tL.setTeacher(teacher);
									tL.setLanguage(iLanguageService.get(Short.parseShort(tid)));
									iTeachingLanguageService.add(tL);
								}
							}
						}
					}
				}
				
				String updater = 3+"_"+teacherId;
				teacher.setUpdater(updater);
				
				teacher.setGraduation(graduation);
				teacher.setMajor(major);
				
				teacher.setIsFirstLogin((short)1);
				
				this.iTeacherService.update(teacher);
				UserSession user = new UserSession();
				// ????????? session???
				
				user.setId(teacher.getId() != null ? teacher.getId() : 0);
				user.setIdcard(teacher.getIdcard() != null ? teacher.getIdcard() : null);
				user.setMobile(teacher.getMobile() != null ? teacher.getMobile() : null);
				user.setEmail(teacher.getEmail() != null ? teacher.getEmail() : null);
				user.setRole((short)3);
				user.setName(teacher.getName() != null ? teacher.getName() : null);
				user.setOrganization(teacher.getOrganization().getId() != null ? teacher.getOrganization().getId() : 0);
				user.setPassword(teacher.getPassword() != null ? teacher.getPassword() : null);
				user.setCreateuser(teacher.getCreator() != null ? teacher.getCreator() : 0);
				user.setLevel(1);
				user.setStatus(teacher.getStatus() != null ? teacher.getStatus() : 0);
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????\"");
				sb.append("}");
				session.setAttribute("teachersession",user);
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			} catch (Exception e) {
				// TODO: handle exception
				// TODO: handle exception
				e.printStackTrace();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}
			
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}
	
	
	/**
	 * ??????????????????????????????
	 */
	public void getEduAdvanceList(){
		initServlert();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		StringBuilder sb = new StringBuilder();
		
		if(us == null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		initServlert();
		//????????????
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
		int limit = DictionyMap.maxPageSize;
		limit = Integer.parseInt(ien);

		// ??????
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
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("teacher", us.getId());
		params.put("teacherShowList", us.getId());
		
		int count = this.iTeacherEduAdvanceService.getListCountByParams(params);
		List<TeacherEduAdvance> list = this.iTeacherEduAdvanceService.getListByParams(params, start, limit, sorts);
		if(list != null && list.size() > 0){
			params.remove("teacherShowList");
			params.put("finalStatus", 1);
			int checkpass = this.iTeacherEduAdvanceService.getListCountByParams(params);
			params.remove("finalStatus");
			params.put("otherstatus", 0);
			int checknopass = this.iTeacherEduAdvanceService.getListCountByParams(params);
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Totalcount\":"+count);
			sb.append(",");
			sb.append("\"CheckPass\":"+checkpass);
			sb.append(",");
			sb.append("\"CheckNoPass\":"+checknopass);
			sb.append(",");
			sb.append("\"Records\":[");
			int index = 1;
			for(TeacherEduAdvance tea : list){
				Teacher teacher = tea.getTeacher();
				EductionBackground eb = tea.getEducationBackground();//???????????????
				Set<TeacherEduEvidence> teacherEduEvidences = tea.getTeacherEduEvidences();//????????????
				String oldeb = tea.getOldEducationBack() == null ? teacher.getEductionBackground().getName() : tea.getOldEducationBack();//?????????
				if("".equals(oldeb)){
					oldeb = teacher.getEductionBackground().getName();
				}
//				Organization ora = teacher.getOrganization();//??????????????????????????????
				String status = "";
				if(tea.getFinalStatus() == -1){//?????????
					if(tea.getStatus() == 1){
						status = "????????????";
					}else if(tea.getStatus() == 0){
						status = "?????????";
					}else if(tea.getStatus() == -1){
						status = "?????????";
					}else{
						status = "?????????";
					}
				}else{//??????
					if(tea.getFinalStatus() == 1){
						status = "????????????";
					} else {
						status = "?????????";
					}
				}
				
				sb.append("{");
				sb.append("\"id\":"+tea.getId());
//				sb.append(",");
//				sb.append("\"name\":\""+teacher.getName()+"\"");
//				sb.append(",");
//				sb.append("\"idcard\":\""+teacher.getIdcard()+"\"");
				sb.append(",");
				sb.append("\"status\":\""+status+"\"");
				sb.append(",");
				sb.append("\"index\":\""+index+"\"");
				sb.append(",");
				sb.append("\"graduation\":\""+tea.getGraduation()+"\"");
				sb.append(",");
				sb.append("\"major\":\""+tea.getMajor()+"\"");
				sb.append(",");
				sb.append("\"createtime\":\""+Utlity.timeSpanToString(tea.getCreatetime())+"\"");
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
				sb.append("},");
				index++;
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
	 * ???????????????????????????????????????
	 * ??????????????????
	 */
	public void initAddEduAdvance(){
		initServlert();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		StringBuilder sb = new StringBuilder();
		
		if(us == null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		Teacher teacher = this.iTeacherService.get(us.getId());
		String hqlString = "from EductionBackground where level<" + teacher.getEductionBackground().getLevel();
		List<EductionBackground> lstBackgrounds = iEductionBackgroundService.getListByHSQL(hqlString);
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Records\":[");
		if(lstBackgrounds != null && lstBackgrounds.size() > 0){
			sb.append("{\"lstBackgrounds\":[");
			for(EductionBackground background: lstBackgrounds){
				
				sb.append("{");
				sb.append("\"eBackgroudId\":\""+background.getId()+"\"");
				sb.append(",");
				sb.append("\"eBackgroudName\":\""+background.getName()+"\"");
				sb.append("},");
				
			}
			sb.delete(sb.length()-1, sb.length());
			sb.append("]}");
		}
		sb.append("]}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * ????????????????????????????????????????????????
	 */
	public void loadRecordInfo(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		
		UserSession us = (UserSession)session.getAttribute("teachersession");
		
		if(us == null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		String id = request.getParameter("id") == null ? "0" : request.getParameter("id");
		if("".equals(id)){
			id = "0";
		}
		TeacherEduAdvance tea = this.iTeacherEduAdvanceService.get(Integer.parseInt(id));
		if(tea == null){
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"?????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		
		
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Records\":{");
		
		Teacher teacher = tea.getTeacher();
		EductionBackground eb = tea.getEducationBackground();//???????????????
		Set<TeacherEduEvidence> teacherEduEvidences = tea.getTeacherEduEvidences();//????????????
		String oldeb = tea.getOldEducationBack() == null ? teacher.getEductionBackground().getName() : tea.getOldEducationBack();//?????????
		if("".equals(oldeb)){
			oldeb = teacher.getEductionBackground().getName();
		}
//		Organization ora = teacher.getOrganization();//??????????????????????????????
//		int isAdmin = 0;//?????????????????????????????? 2??????????????????
//		if(oz.getId() == ora.getId()){//???????????????
//			isAdmin = 0;
//		}else if(oz.getId() == ora.getOrganization().getId()){
//			isAdmin = 1;
//		}else{
//			isAdmin = 2;
//		}
		
//		sb.append("{");
		sb.append("\"id\":"+tea.getId());
//		sb.append(",");
//		sb.append("\"name\":\""+teacher.getName()+"\"");
//		sb.append(",");
//		sb.append("\"idcard\":\""+teacher.getIdcard()+"\"");
//		sb.append(",");
//		sb.append("\"isAdmin\":"+isAdmin);
//		sb.append(",");
//		sb.append("\"organization\":\""+ora.getName()+"\"");
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
		sb.append("\"educationBackgroundid\":\""+eb.getId()+"\"");
		sb.append(",");
		sb.append("\"educationBackground\":\""+eb.getName()+"\"");
		sb.append(",");
		sb.append("\"odlEducationBackground\":\""+oldeb+"\"");
		sb.append(",");
		List<EductionBackground> lstBackgrounds = iEductionBackgroundService.findAll();
		sb.append("\"lstBackgrounds\":[");
		if(!lstBackgrounds.isEmpty()){
			for(EductionBackground background: lstBackgrounds){
				
				sb.append("{");
				sb.append("\"eBackgroudId\":\""+background.getId()+"\"");
				sb.append(",");
				sb.append("\"eBackgroudName\":\""+background.getName()+"\"");
				sb.append("},");
				
			}
			sb.delete(sb.length()-1, sb.length());
			sb.append("]");
		}else{
			sb.append("]");
		}
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
	 * ????????????????????????
	 */
	public void addEduAdvance(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		
		UserSession us = (UserSession)session.getAttribute("teachersession");
		
		if(us == null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		String method = request.getParameter("method") == null ? "" : request.getParameter("method");
		if("".equals(method)){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		/*
		 * ????????????
		 */
		String graduation = request.getParameter("graduation") == null ? "" : request.getParameter("graduation");
		if("".equals(graduation)){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"?????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		String major = request.getParameter("major") == null ? "" : request.getParameter("major");
		if("".equals(major)){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"?????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		String certificate = request.getParameter("certificate") == null ? "" : request.getParameter("certificate");
		if("".equals(certificate)){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		String starttime = request.getParameter("starttime") == null ? "" : request.getParameter("starttime");
		
		if (starttime == null || starttime.equals("")
				|| starttime.equals("0")) {
			Utlity.sendResponse("FAILED", "??????????????????????????????", response);
			return;
		}
		
		String endtime = request.getParameter("endtime") == null ? "" : request.getParameter("endtime");
		if (endtime == null || endtime.equals("")
				|| endtime.equals("0")) {
			Utlity.sendResponse("FAILED", "??????????????????????????????", response);
			return;
		}
		
		String educationbackground = request.getParameter("educationbackground") == null ? "0" : request.getParameter("educationbackground");
		if("0".equals(educationbackground)){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"????????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		
		Teacher teacher = this.iTeacherService.get(us.getId());
		EductionBackground eb = this.iEductionBackgroundService.get(Integer.parseInt(educationbackground));
		
		if("edit".equals(method)){//?????? ????????????
			try {
				String id = request.getParameter("id") == null ? "0" : request.getParameter("id");
				if("".equals(id)){
					id = "0";
				}
				if("0".equals(id)){
					sb.append("{");
					sb.append("\"Result\":\"FAILED\"");
					sb.append(",");
					sb.append("\"Message\":\"????????????\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				TeacherEduAdvance tea = this.iTeacherEduAdvanceService.get(Integer.parseInt(id));
				if(tea == null){
					sb.append("{");
					sb.append("\"Result\":\"FAILED\"");
					sb.append(",");
					sb.append("\"Message\":\"????????????????????????\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				tea.setEducationBackground(eb);
				tea.setTeacher(teacher);
				
				tea.setGraduation(graduation);
				tea.setMajor(major);
				tea.setCertificate(certificate);
				tea.setStarttime(dataTimeConvertUtility.stringToTimeStamp(starttime+" 00:00:00"));
				tea.setEndtime(dataTimeConvertUtility.stringToTimeStamp(endtime+" 00:00:00"));
				
				if(teacher.getEductionBackground() != null){
					tea.setOldEducationBack(teacher.getEductionBackground().getName());
				}else{
					tea.setOldEducationBack(null);
				}
				@SuppressWarnings("unchecked")
				List<fileInfo> lsFiles = (List<fileInfo>) session.getAttribute("teachereduevidence");
				if(lsFiles != null && lsFiles.size() > 0){//????????????????????????????????????????????????????????????
					if(tea.getTeacherEduEvidences().size() > 0){
						Set<TeacherEduEvidence> tees = tea.getTeacherEduEvidences();
						tea.setTeacherEduEvidences(new HashSet<TeacherEduEvidence>());
						for(TeacherEduEvidence tee: tees){
							Document document = tee.getDocument();
							this.ITeacherEduEvidenceService.delete(tee);
							this.iDocumentService.delete(document);
						}
					}
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
						document.setType((short) 5);
						document.setResourceType((short) 1);
						document = iDocumentService.add(document);
						TeacherEduEvidence tee = new TeacherEduEvidence();
						tee.setDocument(document);
						tee.setTeacherEduAdvance(tea);
						tea.getTeacherEduEvidences().add(tee);
					}
					
				}
				tea.setStatus((short)-1);
				tea.setFinalStatus((short)-1);
				this.iTeacherEduAdvanceService.update(tea);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				StringBuilder sbstr = new StringBuilder();
				sbstr.append("{");
				sbstr.append("\"Result\":\"FAILED\"");
				sbstr.append(",");
				sbstr.append("\"Message\":\"????????????\"");
				sbstr.append("}");
				Utlity.ResponseWrite(sbstr.toString(), "application/json", response);
				return;
			}
			
		}else if ("add".equals(method)){//??????
			try {
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("teacher", us.getId());
//				params.put("otherstatus", -1);
				
				List<TeacherEduAdvance> lsts= this.iTeacherEduAdvanceService.getListByParams(params, -1, -1, "createtime desc");
				if(lsts != null && lsts.size() > 0){
					boolean flag = false;
					for(TeacherEduAdvance tea : lsts){
						if (tea.getStatus() == -1 || (tea.getStatus() == 1 && tea.getFinalStatus() == -1)) {
							flag = true;
						}
					}
					if(flag){
						sb.append("{");
						sb.append("\"Result\":\"FAILED\"");
						sb.append(",");
						sb.append("\"Message\":\"??????????????????????????????????????????????????????????????????\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;
					}
				}
				
				params.put("educationBackground",educationbackground);
				
				
				List<TeacherEduAdvance> lst= this.iTeacherEduAdvanceService.getListByParams(params, -1, -1, "createtime desc");
				if(lst != null && lst.size() > 0){
					boolean flag = false;
					for(TeacherEduAdvance teas : lst){
						if(teas.getStatus() !=0 || teas.getFinalStatus() != 0){
							flag = true;
						}
					}
					if(flag){
						sb.append("{");
						sb.append("\"Result\":\"FAILED\"");
						sb.append(",");
						sb.append("\"Message\":\"????????????????????????????????????????????????????????????\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;
					}
				}
				params.remove("educationBackground");
				//?????????????????????????????????????????????
				//??????????????????????????????????????????
				List<ProjectCycle> listpc = this.projectCycleService.findAll();
				int startYear=0;
				int endYear=0;
				Date nowdate=new Date();
				if(listpc != null && listpc.size() > 0){
					for(ProjectCycle pc : listpc){
						String start = pc.getStartyear();
						String end = pc.getEndyear();
						Date startday = Utlity.getYearFirst(Integer.parseInt(start));
						Date endday = Utlity.getYearLast(Integer.parseInt(end));
						if(startday.before(nowdate) && endday.after(nowdate)){//????????????
							startYear=Integer.parseInt(start);
							endYear=Integer.parseInt(end);
						}
					}
				}
				if(startYear == 0 && endYear == 0){
					startYear = 2014;
					endYear = 2020;
				}
//				Map<String, Object> params = new HashMap<String, Object>();
//				params.put("teacher", teacherId);
				String startTime = Utlity.timeSpanToDateString(Utlity.getYearFirst(startYear));//??????????????????????????????
				String endTime = Utlity.timeSpanToDateString(Utlity.getYearLast(endYear));//???????????????????????????????????????
				params.put("startYear", startTime);
				params.put("endYear", endTime);
				params.put("finalStatus", 1);
				int count = this.iTeacherEduAdvanceService.getListCountByParams(params);
				if(count > 0){
					sb.append("{");
					sb.append("\"Result\":\"FAILED\"");
					sb.append(",");
					sb.append("\"Message\":\"????????????????????????????????????????????????????????????\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				@SuppressWarnings("unchecked")
				List<fileInfo> lsFiles = (List<fileInfo>) session.getAttribute("teachereduevidence");
				TeacherEduAdvance tea  = new TeacherEduAdvance();
				
				tea.setEducationBackground(eb);
				tea.setTeacher(teacher);
				
				tea.setGraduation(graduation);
				tea.setMajor(major);
				tea.setCertificate(certificate);
				tea.setStarttime(dataTimeConvertUtility.stringToTimeStamp(starttime+" 00:00:00"));
				tea.setEndtime(dataTimeConvertUtility.stringToTimeStamp(endtime+" 00:00:00"));
				
				if(teacher.getEductionBackground() != null){
					tea.setOldEducationBack(teacher.getEductionBackground().getName());
				}else{
					tea.setOldEducationBack(null);
				}
				
				//??????????????????
				tea.setFinalStatus((short)-1);
				tea.setStatus((short)-1);
				
				tea.setCreatetime(new Timestamp(System.currentTimeMillis()));
				
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
						document.setType((short) 5);
						document.setResourceType((short) 1);
						document = iDocumentService.add(document);
						TeacherEduEvidence tee = new TeacherEduEvidence();
						tee.setDocument(document);
						tee.setTeacherEduAdvance(tea);
						tea.getTeacherEduEvidences().add(tee);
					}
				}else{
					sb.append("{");
					sb.append("\"Result\":\"FAILED\"");
					sb.append(",");
					sb.append("\"Message\":\"???????????????????????????\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				this.iTeacherEduAdvanceService.add(tea);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				StringBuilder sbstr = new StringBuilder();
				sbstr.append("{");
				sbstr.append("\"Result\":\"FAILED\"");
				sbstr.append(",");
				sbstr.append("\"Message\":\"????????????\"");
				sbstr.append("}");
				Utlity.ResponseWrite(sbstr.toString(), "application/json", response);
				return;
			}
			
		}else{
			StringBuilder sbstr = new StringBuilder();
			sbstr.append("{");
			sbstr.append("\"Result\":\"FAILED\"");
			sbstr.append(",");
			sbstr.append("\"Message\":\"????????????\"");
			sbstr.append("}");
			Utlity.ResponseWrite(sbstr.toString(), "application/json", response);
			return;
		}
		
		session.removeAttribute("teachereduevidence");
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Message\":\"????????????\"");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
		return;
		
	}
	
	/**
	 * ????????????
	 */
	public void delete(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		
		UserSession us = (UserSession)session.getAttribute("teachersession");
		
		if(us == null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		String id = request.getParameter("id") == null ? "0" : request.getParameter("id");
		if("".equals(id)){
			id = "0";
		}
		TeacherEduAdvance tea = this.iTeacherEduAdvanceService.get(Integer.parseInt(id));
		if(tea != null){
			if(tea.getStatus() == -1){
				tea.setStatus((short)-2);
				this.iTeacherEduAdvanceService.update(tea);
			}else{
				sb.append("{");
				sb.append("\"Result\":\"FAILED\"");
				sb.append(",");
				sb.append("\"Message\":\"??????????????????,????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
		}
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Message\":\"????????????\"");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	
	public IOrganizationService getiOrganizationService() {
		return iOrganizationService;
	}

	public void setiOrganizationService(IOrganizationService iOrganizationService) {
		this.iOrganizationService = iOrganizationService;
	}

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

	public IEthnicService getiEthnicService() {
		return iEthnicService;
	}

	public void setiEthnicService(IEthnicService iEthnicService) {
		this.iEthnicService = iEthnicService;
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

	public ISubjectService getiSubjectService() {
		return iSubjectService;
	}

	public void setiSubjectService(ISubjectService iSubjectService) {
		this.iSubjectService = iSubjectService;
	}

	public ILanguageService getiLanguageService() {
		return iLanguageService;
	}

	public void setiLanguageService(ILanguageService iLanguageService) {
		this.iLanguageService = iLanguageService;
	}

	public ITeacherMobileCodeService getiTeacherMobileCodeService() {
		return iTeacherMobileCodeService;
	}

	public void setiTeacherMobileCodeService(
			ITeacherMobileCodeService iTeacherMobileCodeService) {
		this.iTeacherMobileCodeService = iTeacherMobileCodeService;
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

	public void setiProjectAdminService(IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}

	public ITrainingAdminService getiTrainingAdmin() {
		return iTrainingAdmin;
	}

	public void setiTrainingAdmin(ITrainingAdminService iTrainingAdmin) {
		this.iTrainingAdmin = iTrainingAdmin;
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

	public IDocumentService getiDocumentService() {
		return iDocumentService;
	}

	public void setiDocumentService(IDocumentService iDocumentService) {
		this.iDocumentService = iDocumentService;
	}

	public IProjectCycleService getProjectCycleService() {
		return projectCycleService;
	}

	public void setProjectCycleService(IProjectCycleService projectCycleService) {
		this.projectCycleService = projectCycleService;
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
	
}
