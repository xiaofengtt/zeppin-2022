package cn.zeppin.action.teacher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
import cn.zeppin.entity.EductionBackground;
import cn.zeppin.entity.Ethnic;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.ImgResult;
import cn.zeppin.entity.JobDuty;
import cn.zeppin.entity.JobTitle;
import cn.zeppin.entity.Language;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Politics;
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
import cn.zeppin.service.ISubjectService;
import cn.zeppin.service.ITeacherMobileCodeService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeachingGradeService;
import cn.zeppin.service.ITeachingLanguageService;
import cn.zeppin.service.ITeachingSubjectService;
import cn.zeppin.utility.SendSms;
import cn.zeppin.utility.Utlity;
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
				sb.append("\"email\":\""+teacher.getEmail()+"\"");
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
			sb.append("\"mobile\":\""+us.getMobile()+"\"");
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
		
		
		//????????????????????????
		String sms = "?????????????????????????????????"+captcha+",???????????????20????????????????????????????????????????????????";
		sb.append("{");
		
		try {
			String code = SendSms.sendSms(sms, phone);
			String[] strs = code.split("_");
			String status = strs[0];
			String msg = strs[1];
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
	
	
	
}
