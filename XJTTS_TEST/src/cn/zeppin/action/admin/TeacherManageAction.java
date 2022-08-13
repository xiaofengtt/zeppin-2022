/**
 * 
 */
package cn.zeppin.action.admin;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import Organization.OrganizationEx;
import cn.zeppin.action.baseAction;
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
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.Teacher;
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
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeachingGradeService;
import cn.zeppin.service.ITeachingLanguageService;
import cn.zeppin.service.ITeachingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.IdCardUtil;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.pinyingUtil;

/**
 * @category 教师学员信息管理
 * @author sj
 * 
 */
public class TeacherManageAction extends baseAction {

	// 通用属性

	private static final long serialVersionUID = 1L;
	static Logger logger = LogManager.getLogger(OrganizationAction.class);
	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	// 属性
	private List<Politics> lstPolitics = new ArrayList<Politics>();// 政治面貌
	private List<EductionBackground> lstBackgrounds = new ArrayList<EductionBackground>();// 学历
	private List<Ethnic> lstEthnics = new ArrayList<Ethnic>();// 民族
	private List<JobTitle> lstJobTitles = new ArrayList<JobTitle>();// 职称
	private List<Organization> lstOrganization = new ArrayList<>();// 组织机构

	private List<OrganizationEx> lstOrganizationExs = new ArrayList<>();
	private List<Language> lstLanguages = new ArrayList<Language>();// 语言
	private List<Grade> lstGrades = new ArrayList<>();// 学段
	private List<Subject> lstSubjects = new ArrayList<>();// 学科
	private List<TeachingLanguage> lsTeachingLanguages = new ArrayList<TeachingLanguage>();// 教学语言
	private List<TeachingGrade> lstTeachingGrades = new ArrayList<TeachingGrade>();// 教学学段
	private List<TeachingSubject> lsttTeachingSubjects = new ArrayList<TeachingSubject>();// 教学科
	private List<teachingSubjectEx> lstteacTeachingSubjectExs = new ArrayList<>();// 教学学科ex
	private List<JobDuty> lstJobDuties = new ArrayList<JobDuty>();// 职务
	private List<ChineseLanguageLevel> lstChineseLanguageLevels = new ArrayList<ChineseLanguageLevel>();// 汉语言水平
	private List<Area> lstAreas = new ArrayList<Area>();// 地区
	private List<Area> lstProvince = new ArrayList<Area>();// 省级列表
	private List<Area> lstCity = new ArrayList<Area>();// 市级列表
	private List<Area> lstCountry = new ArrayList<>();// 县级列表
	private List<teacherEx> lstTeacherExs = new ArrayList<teacherEx>();// 教师信息list
	private List<Organization> tlstOrganizations = new ArrayList<>();// 获取所有学校信息

	private Teacher teacher;// 教师
	private teacherEx teacherEx;// 教师ex
	private Area province;// 省
	private Area city;// 市
	private Area county;// 县
	private String pageStatus;// 页面状态
	private String message;// 错误信息
	private int id;
	private Organization organizationp;
	private double totalPage = 1;
	int pageLength = 20;
	private boolean isUpdate = false;
	private boolean isReturn = false;
	private int organizationLevel = 0;
	// 服务定义
	ITeacherService iTeacherService;// 教师操作
	IAreaService iAreaService;// 地区操作
	ILanguageService iLanguageService;// 语言操作
	ISubjectService iSubjectService;// 学科操作
	IGradeService iGradeService;// 学段操作
	ITeachingLanguageService iTeachingLanguageService;// 教学语言操作
	ITeachingGradeService iTeachingGradeService;// 教学学段操作
	ITeachingSubjectService iTeachingSubjectService;// 教学科目操作
	IProjectAdminService iProjectAdminService;// 管理员
	IOrganizationService iOrganizationService;// 组织架构
	IEthnicService iEthnicService;// 民族
	IJobDutyService iJobDutyService;// 职务
	IJobTitleService iJobTitleService;// 职称
	IEductionBackgroundService iEductionBackgroundService;// 学历
	IPoliticsService iPoliticsService;// 政治面貌
	IChineseLanguageLevelService iChineseLanguageLevelService;// 汉语言水平

	// 方法
	/**
	 * @category 构造函数
	 */
	public TeacherManageAction() {

	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * @category 页面初始化
	 * @return
	 * @author sj
	 */
	@SuppressWarnings("unchecked")
	public String initPage() {
		// long date = new java.util.Date().getTime();

		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		Organization organization = iOrganizationService.get(user.getOrganization());
		pageStatus = "";
		message = "";
		lstTeacherExs.clear();
		String result = "init";
		String hqlString;
		// 起始页
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "0";
		}

		int start = Integer.parseInt(ist);
		if (request.getParameterMap().containsKey("search")) {
			hqlString = (String) session.getAttribute("teacherSearch");
		} else {

			if (user.getOrganizationLevel() == DictionyMap.ORGANIZATION_LEVEL_SCHOOL) {
				hqlString = "from Teacher where organization =" + organization.getId();
			} else {
				hqlString = "from Teacher where organization in (from Organization where scode like '" + user.getOrganizationScode() + "%' and isschool=1)";
			}

			// if (session.getAttribute("lstSubParentOrg") != null) {
			// tlstOrganizations = (List<Organization>)
			// session.getAttribute("lstSubParentOrg");
			// }
			// String orgIds = "";
			// if (tlstOrganizations.size() > 0) {
			//
			// for (Organization tog : tlstOrganizations) {
			// orgIds += tog.getId() + ",";
			// }
			// orgIds = orgIds.substring(0, orgIds.length() - 1);
			// hqlString =
			// "from Teacher where organization in (from Organization where organization in ("
			// + orgIds + ") and isschool=1)";
			// } else {
			// hqlString = "from Teacher where organization =" +
			// organization.getId();
			// }

			// hqlString = "from Teacher where organization in (" + orgIds +
			// ")";

		}

		// 判断其他搜索字段是否存在

		// 排序 参数
		// 排序
		String sort = request.getParameter("sort");
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			hqlString += " order by " + sortname + " " + sorttype;
		}
		List<Teacher> lstTeachers = new ArrayList<>();
		lstTeachers = iTeacherService.getListForPage(hqlString, start, pageLength, null);
		totalPage = iTeacherService.getCount("select count(*) " + hqlString) / pageLength;
		session.setAttribute("teahcerTotalPage", totalPage);
		if (lstTeachers.size() > 0) {

			for (Teacher tTeacher : lstTeachers) {
				teacherEx teacherEx = new teacherEx();
				teacherEx.setTeacher(tTeacher);
				String areasString = "";
				List<String> lstA = iAreaService.getParentNodes(tTeacher.getArea().getCode());
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
					teacherEx.setAreaString(areasString.substring(0, areasString.length() - 2));
				}
				teacherEx.setAgeString((String.valueOf(Utlity.getAge(tTeacher.getBirthday()))));
				teacherEx.setSexString(tTeacher.getSex() == 1 ? "男" : "女");
				if (tTeacher.getTeachingAge() != null) {
					teacherEx.setTeachingAge(String.valueOf(Utlity.getAge(tTeacher.getTeachingAge())));
				}
				teacherEx.setIsMultiLanguage(tTeacher.getMultiLanguage() ? "是" : "否");
				// 获取主要教学语言
				hqlString = "from TeachingLanguage where teacher=" + tTeacher.getId() + " and isprime=true";

				List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
				if (lstTeachingLanguages.size() > 0) {
					TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
					teacherEx.setMainTeachingLanguage(teachingLanguage);
				}

				// 获取主要教学学段
				hqlString = "from TeachingGrade where teacher=" + tTeacher.getId() + " and isprime=1";
				List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
				if (lstTeachingGrades.size() > 0) {
					TeachingGrade teachingGrade = lstTeachingGrades.get(0);
					teacherEx.setMainTeachingClass(teachingGrade);
				}

				// 获取主要教学学科
				hqlString = "from TeachingSubject where teacher=" + tTeacher.getId() + " and isprime=1";
				List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
				if (lsttTeachingSubjects.size() > 0) {
					TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
					teacherEx.setMainTeachingCourse(teachingSubject);
				}

				// 获取创建人
				if (tTeacher.getCreator() != null && iProjectAdminService.get(tTeacher.getCreator()) != null) {
					teacherEx.setCreator(iProjectAdminService.get(tTeacher.getCreator()).getName());
				} else {
					teacherEx.setCreator("");
				}
				lstTeacherExs.add(teacherEx);
			}

		}
		// long date1 = new java.util.Date().getTime();
		// logger.info("dfdfdsf" + (date1 - date));

		return result;
	}

	public String addInit() {
		initServlert();
		String result = "addInit";
		id = -1;
		// pageStatus = "";
		isReturn = false;
		// 民族
		lstEthnics = iEthnicService.getEthnicByWight();
		// 职务
		lstJobDuties = iJobDutyService.findAll();
		// 职称
		lstJobTitles = iJobTitleService.findAll();
		// 学历
		lstBackgrounds = iEductionBackgroundService.findAll();
		// 政治面貌
		lstPolitics = iPoliticsService.findAll();
		// 汉语言水平
		lstChineseLanguageLevels = iChineseLanguageLevelService.findAll();
		// 教学学段
		lstTeachingGrades = iTeachingGradeService.findAll();
		// 学段
//		lstGrades = iGradeService.findAll();
		String getStuGradesHQL = " from Grade g where g.isSchool=0 ";
		lstGrades = iGradeService.getListByHSQL(getStuGradesHQL);
		// 学科
		lstSubjects = iSubjectService.findAll();
		lstteacTeachingSubjectExs.clear();
		for (Subject ts : lstSubjects) {
			teachingSubjectEx tt = new teachingSubjectEx();
			tt.setId(ts.getId().toString());
			tt.setName(ts.getName());
			tt.setSearchString(ts.getName() + pinyingUtil.getFirstSpell(ts.getName()));
			lstteacTeachingSubjectExs.add(tt);
		}
		// 语言
		lstLanguages = iLanguageService.findAll();
		if (request.getParameterMap().containsKey("opt")) {

			String optString = request.getParameter("opt");
			if (optString.equals("search")) {
				return "search";

			}

			if (optString.equals("add")) {
				teacher = null;
				teacherEx = new teacherEx();
				message = "";
				pageStatus = "";
				organizationp = new Organization();
				isUpdate = false;
				isReturn = false;
				province = new Area();
				city = new Area();
				county = new Area();

				UserSession user = (UserSession) session.getAttribute("usersession");
				Organization organization = iOrganizationService.get(user.getOrganization());
				int areaLevel;
				areaLevel = organization.getArea().getLevel();
				Area tArea = organization.getArea();

				if (areaLevel == 1) {
					province = tArea;
					lstCity = iAreaService.getParentCodeArea(province.getCode());
					lstProvince = iAreaService.getLevelArea((short) 1);

				} else if (areaLevel == 2) {
					city = tArea;
					province = iAreaService.getAreaByCode(tArea.getParentcode());
					lstCountry = iAreaService.getParentCodeArea(city.getCode());
					lstCity = iAreaService.getParentCodeArea(province.getCode());
					lstProvince = iAreaService.getLevelArea((short) 1);

				} else {
					county = tArea;
					city = iAreaService.getAreaByCode(tArea.getParentcode());
					province = iAreaService.getAreaByCode(city.getParentcode());
					lstCountry = iAreaService.getParentCodeArea(city.getCode());
					lstCity = iAreaService.getParentCodeArea(province.getCode());
					lstProvince = iAreaService.getLevelArea((short) 1);
				}

			}

		}

		else if (request.getParameterMap().containsKey("editId")) {
			id = Integer.parseInt(request.getParameter("editId"));
			teacher = iTeacherService.get(id);
			organizationp = teacher.getOrganization();
			UserSession user = (UserSession) session.getAttribute("usersession");
			if(user.getOrganizationLevel() != 1){
				this.organizationLevel = 1;
			}
			int areaLevel;

			Area tArea = teacher.getArea();
			areaLevel = tArea.getLevel();
			if (areaLevel == 1) {
				province = tArea;
				lstCity = iAreaService.getParentCodeArea(province.getCode());
				lstProvince = iAreaService.getLevelArea((short) 1);
				city = new Area();
			} else if (areaLevel == 2) {
				city = tArea;
				province = iAreaService.getAreaByCode(tArea.getParentcode());
				lstCountry = iAreaService.getParentCodeArea(city.getCode());
				lstCity = iAreaService.getParentCodeArea(province.getCode());
				lstProvince = iAreaService.getLevelArea((short) 1);
				county = new Area();
			} else {
				county = tArea;
				city = iAreaService.getAreaByCode(tArea.getParentcode());
				province = iAreaService.getAreaByCode(city.getParentcode());
				lstCountry = iAreaService.getParentCodeArea(city.getCode());
				lstCity = iAreaService.getParentCodeArea(province.getCode());
				lstProvince = iAreaService.getLevelArea((short) 1);
			}
			teacherEx = new teacherEx();
			teacherEx.setTeacher(teacher);
			teacherEx.setOrganization(teacher.getOrganization());
			// 教学学段
			String hqlString = "from TeachingGrade where teacher=" + id;
			lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
			for (TeachingGrade ttg : lstTeachingGrades) {
				if (ttg.getIsprime()) {
					teacherEx.setMainTeachingClass(ttg);
					// logger.info(teacherEx.getMainTeachingClass().getGrade()
					// .getName());
				} else {

					teacherEx.getUnMainTeachingGrades().add(ttg);
				}
			}
			// 教学学科
			hqlString = "from TeachingSubject where teacher=" + id;
			lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
			for (TeachingSubject ttsSubject : lsttTeachingSubjects) {
				if (ttsSubject.getIsprime()) {
					teacherEx.setMainTeachingCourse(ttsSubject);
				} else {

					teacherEx.getUnMaintTeachingSubjects().add(ttsSubject);
				}
			}
			// 教学语言
			hqlString = "from TeachingLanguage where teacher=" + id;
			lsTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
			for (TeachingLanguage ttlLanguage : lsTeachingLanguages) {
				if (ttlLanguage.getIsprime()) {
					teacherEx.setMainTeachingLanguage(ttlLanguage);
				} else {
					teacherEx.getUnMainTeachingLanguages().add(ttlLanguage);
				}
			}
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	public void addItem() throws ParseException, UnsupportedEncodingException {
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		teacherEx = new teacherEx();
		/*
		 * if (request.getParameterMap().containsKey("teacher.id")) {
		 */
		if (!request.getParameter("teacher.id").equals("")) {
			id = Integer.parseInt(request.getParameter("teacher.id"));
			teacher = iTeacherService.get(id);
			isUpdate = true;
		}
		// }

		else {
			teacher = new Teacher();

		}
		pageStatus = "";
		message = "";
		String result = "addInit";
		
		
		String regex = "^[\\u4e00-\\u9fa5]+[\\.]{0,1}[\\·]{0,1}[\\u4e00-\\u9fa5]+$";
		teacher.setName(URLDecoder.decode(request.getParameter("name").trim(), "UTF-8"));
		if (teacher.getName().equals("")) {
			pageStatus = "ERROR";
			message += "用户名不能为空||";
		}else if(!teacher.getName().matches(regex)){
			pageStatus = "ERROR";
			message += "用户名不合法(必须使用汉字开头或结尾,连字符用'.'或者'·')||";
		}
		String idCardString = request.getParameter("idCard").trim().toLowerCase();
		if (idCardString != null) {
			if (IdCardUtil.IDCardValidate(idCardString).equals("")) {
				if (iTeacherService.existIdCard(idCardString) && !isUpdate) {
					pageStatus = "ERROR";
					message += "已经存在相同的身份证号码||";
				} else {
					teacher.setIdcard(idCardString);
					teacher.setBirthday(IdCardUtil.getBirthday(idCardString));
					teacher.setSex(IdCardUtil.getSex(idCardString));
					String pwdString = idCardString.substring(idCardString.length() - 6);
					teacher.setPassword(pwdString);
				}

			} else {
				pageStatus = "ERROR";
				message += "身份证信息不合法||";
			}
		} else {
			pageStatus = "ERROR";
			message = "必须填写身份证信息";
		}
		String provinceId = request.getParameter("provinceId");
		if (!provinceId.equals("-1")) {
			province = iAreaService.get(Integer.parseInt(provinceId));

		} else {
			pageStatus = "ERROR";
			message += "必须选择省||";
		}
		String cityId = request.getParameter("cityId");
		if (!cityId.equals("-1")) {
			city = iAreaService.get(Integer.parseInt(cityId));

		}
		String countyId = request.getParameter("countyId");
		if (!countyId.equals("-1")) {
			county = iAreaService.get(Integer.parseInt(countyId));

		}

		// 地区赋值
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

		// 教龄
		String teachingAageString = request.getParameter("teachingAge");
		if (teachingAageString.equals("")) {
			pageStatus = "ERROR";
			message += "请填写参加工作时间||";
		} else {
			teacher.setTeachingAge(Utlity.stringToDate(teachingAageString));
		}
		// 组织机构
		String organazationString = request.getParameter("organization");
		if (organazationString.equals("")) {
			pageStatus = "ERROR";
			message += "请填写所在学校||";
		} else {
			organizationp = iOrganizationService.get(Integer.parseInt(organazationString));
			teacher.setOrganization(organizationp);
		}

		// 职位
		String jobDuty = request.getParameter("jobDuty");
		if (jobDuty.equals("-1")) {
			pageStatus = "ERROR";
			message += "请选择职务||";
		} else {
			teacher.setJobDuty(iJobDutyService.getJobDutyById(jobDuty));
		}
		// 职称
		String jobTitle = request.getParameter("jobTitle");
		if (jobTitle.equals("-1")) {
			pageStatus = "ERROR";
			message += "请选择职称||";
		} else {
			teacher.setJobTitle(iJobTitleService.getJobTitleById(jobTitle));
		}
		// 学历
		String backgroundString = request.getParameter("eductionBackground");
		if (backgroundString.equals("-1")) {
			pageStatus = "ERROR";
			message += "请选择最高学历||";
		} else {
			teacher.setEductionBackground(iEductionBackgroundService.geteEductionBackgroundById(backgroundString));
		}
		// 政治面貌
		String politcsString = request.getParameter("politics");
		if (politcsString.equals("-1")) {
			pageStatus = "ERROR";
			message += "请选择政治面貌||";
		} else {
			teacher.setPolitics(iPoliticsService.getPoliticsById(politcsString));

		}
		// 民族
		String ethnicString = request.getParameter("ethnic");
		if (ethnicString.equals("-1")) {
			pageStatus = "ERROR";
			message += "请选择民族||";
		} else {
			teacher.setEthnic(iEthnicService.get(Short.parseShort(ethnicString)));
		}
		// 手机
		String mobile = request.getParameter("mobile");
		if (mobile.equals("")) {
			pageStatus = "ERROR";
			message += "请填写手机号码||";
		} else {
			if (!Utlity.isMobileNO(mobile.trim())) {
				pageStatus = "ERROR";
				message += "手机号码非法！||";
			} else {
				if (iTeacherService.existMobile(mobile.trim()) && !isUpdate) {
					pageStatus = "ERROR";
					message += "手机号码已经存在！||";
				} else {
					teacher.setMobile(mobile.trim());
				}

			}

		}
		// 汉语言水平
		String chineseLanguageLevelString = request.getParameter("chineseLanguageLevel");
		if (chineseLanguageLevelString.equals("-1")) {
			pageStatus = "ERROR";
			message += "请选择汉语言水平||";
		} else {
			teacher.setChineseLanguageLevel(iChineseLanguageLevelService.getChineseLanguageLevelById(chineseLanguageLevelString));
		}
		// 邮件
		String email = URLDecoder.decode(request.getParameter("email").trim(), "UTF-8");
		if (email.equals("")) {
			pageStatus = "ERROR";
			message += "请填写邮箱地址||";
		} else {
			if (!Utlity.isEmail(email)) {
				pageStatus = "ERROR";
				message += "邮箱地址不正确||";
			} else {
				if (iTeacherService.existEmail(email) && !isUpdate) {
					pageStatus = "ERROR";
					message += "邮箱已经存在||";
				} else {
					teacher.setEmail(email);
				}

			}

		}
		if (request.getParameter("mainTeachingSubject").equals("-1")) {
			pageStatus = "ERROR";
			message += "请选择主要学科||";
		} else {
			short id = Short.parseShort(request.getParameter("mainTeachingSubject"));
			TeachingSubject mainSubject = new TeachingSubject();
			mainSubject.setSubject(iSubjectService.get(id));
			mainSubject.setIsprime(true);
			teacherEx.setMainTeachingCourse(mainSubject);

		}
		if (request.getParameter("mainTeachingGrades").equals("-1")) {
			pageStatus = "ERROR";
			message += "请选择主要学科段||";
		} else {
			short id = Short.parseShort(request.getParameter("mainTeachingGrades"));
			TeachingGrade mainGrade = new TeachingGrade();
			mainGrade.setGrade(iGradeService.get(id));
			mainGrade.setIsprime(true);
			teacherEx.setMainTeachingClass(mainGrade);
		}
		if (request.getParameter("mainTeachingLanguage").equals("-1")) {
			pageStatus = "ERROR";
			message += "请选择主要教学语言||";
		} else {
			short id = Short.parseShort(request.getParameter("mainTeachingLanguage"));
			TeachingLanguage mainlLanguage = new TeachingLanguage();
			mainlLanguage.setLanguage(iLanguageService.get(id));
			mainlLanguage.setIsprime(true);
			teacherEx.setMainTeachingLanguage(mainlLanguage);
		}

		teacher.setStatus(Short.parseShort(request.getParameter("status")));
		String isMultilanguage = "0";
		if (request.getParameterMap().containsKey("multiLanguage")) {
			isMultilanguage = request.getParameter("multiLanguage");
		}

		teacher.setMultiLanguage(isMultilanguage.equals("1") ? true : false);
		teacher.setRemark1(request.getParameter("remark1"));
		teacher.setRemark2(request.getParameter("remark2"));
		teacher.setCreattime(new Timestamp(System.currentTimeMillis()));
		teacher.setCreator(user.getId());

		if (!pageStatus.equals("ERROR")) {
			isReturn = false;
			pageStatus = "OK";
			if (!isUpdate) {
				Teacher tempTeacher = new Teacher();
				try {
					tempTeacher = iTeacherService.add(teacher);

					// 学科入库
					TeachingSubject tSubject = teacherEx.getMainTeachingCourse();
					tSubject.setTeacher(tempTeacher);
					iTeachingSubjectService.add(tSubject);
					String[] unMainTeachingSubject = request.getParameterValues("unMainTeachingSubject");
					if (unMainTeachingSubject != null) {
						if (unMainTeachingSubject.length > 0) {
							for (String tid : unMainTeachingSubject) {
								if (!tid.equals(tSubject.getSubject().getId().toString())) {

									TeachingSubject tS = new TeachingSubject();
									tS.setIsprime(false);
									tS.setTeacher(tempTeacher);
									tS.setSubject(iSubjectService.get(Short.parseShort(tid)));
									iTeachingSubjectService.add(tS);
								}

							}
						}

					}
					// 学段入库
					TeachingGrade tGrade = teacherEx.getMainTeachingClass();
					tGrade.setTeacher(tempTeacher);
					iTeachingGradeService.add(tGrade);
					String[] unMainTeachingGrades = request.getParameterValues("unMainTeachingGrades");
					if (unMainTeachingGrades != null) {
						if (unMainTeachingGrades.length > 0) {
							for (String tid : unMainTeachingGrades) {
								if (!tid.equals(tGrade.getGrade().getId().toString())) {
									TeachingGrade tg = new TeachingGrade();
									tg.setIsprime(false);
									tg.setTeacher(tempTeacher);
									tg.setGrade(iGradeService.get(Short.parseShort(tid)));
									iTeachingGradeService.add(tg);
								}

							}
						}
					}
					// 教学语言入库

					TeachingLanguage tLanguage = teacherEx.getMainTeachingLanguage();
					tLanguage.setTeacher(tempTeacher);
					iTeachingLanguageService.add(tLanguage);
					String[] unMainTeachingLanguage = request.getParameterValues("unMainTeachingLanguage");
					if (unMainTeachingLanguage != null) {
						if (unMainTeachingLanguage.length > 0) {
							for (String tid : unMainTeachingLanguage) {
								if (!tid.equals(tLanguage.getLanguage().getId().toString())) {
									TeachingLanguage tL = new TeachingLanguage();
									tL.setIsprime(false);
									tL.setTeacher(tempTeacher);
									tL.setLanguage(iLanguageService.get(Short.parseShort(tid)));
									iTeachingLanguageService.add(tL);

								}

							}
						}
					}
				} catch (Exception e) {
					pageStatus = "ERROR";
					if (tempTeacher.getId() != null) {

						// iTeacherService.delete(tempTeacher);
					}
					message = "添加失败，请重新添加";
					result = "addInit";
				}

			} else {

				teacher.setId(id);
				teacher = iTeacherService.update(teacher);

				// 学科入库

				String hqlString = "delete from TeachingSubject where  teacher=" + teacher.getId();
				iTeachingSubjectService.executeHSQL(hqlString);
				hqlString = "from TeachingSubject where isprime=1 and  teacher=" + teacher.getId();
				TeachingSubject tSubject = new TeachingSubject();
				tSubject.setIsprime(true);
				tSubject.setTeacher(teacher);
				tSubject.setSubject(iSubjectService.get(Short.parseShort(request.getParameter("mainTeachingSubject"))));
				iTeachingSubjectService.add(tSubject);

				List<String> tlst = new ArrayList<>();
				if (request.getParameterValues("unMainTeachingSubject") != null) {
					String[] unMainTeachingSubject = request.getParameterValues("unMainTeachingSubject");

					if (unMainTeachingSubject != null) {
						if (unMainTeachingSubject.length > 0) {
							tlst.clear();
							for (String tid : unMainTeachingSubject) {
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

				// 学段入库

				hqlString = "delete from TeachingGrade where teacher=" + teacher.getId();
				iTeachingGradeService.executeHSQL(hqlString);

				TeachingGrade tGrade = new TeachingGrade();

				tGrade.setIsprime(true);
				tGrade.setTeacher(teacher);
				tGrade.setGrade(iGradeService.get(Short.parseShort(request.getParameter("mainTeachingGrades"))));
				iTeachingGradeService.add(tGrade);
				if (request.getParameterValues("unMainTeachingGrades") != null) {
					String[] unMainTeachingGrades = request.getParameterValues("unMainTeachingGrades");

					if (unMainTeachingGrades.length > 0) {
						tlst.clear();
						for (String tid : unMainTeachingGrades) {
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

				// 教学语言入库
				hqlString = "delete from TeachingLanguage where teacher=" + teacher.getId();
				iTeachingLanguageService.executeHSQL(hqlString);
				TeachingLanguage tLanguage = new TeachingLanguage();
				tLanguage.setIsprime(true);
				tLanguage.setTeacher(teacher);
				tLanguage.setLanguage(iLanguageService.get(Short.parseShort(request.getParameter("mainTeachingLanguage"))));
				iTeachingLanguageService.add(tLanguage);
				if (request.getParameterValues("unMainTeachingLanguage") != null) {
					String[] unMainTeachingLanguage = request.getParameterValues("unMainTeachingLanguage");

					if (unMainTeachingLanguage.length > 0) {
						tlst.clear();
						for (String tid : unMainTeachingLanguage) {
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

		}

		else {
			// message = URLDecoder.decode(message, "Utf-8");
			// teacher = null;
			isReturn = true;
			pageStatus = "ERROR";
			// result = "addInit";
		}

		String rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
		Utlity.ResponseWrite(rString, "json", response);
		// return result;
	}

	@SuppressWarnings("unchecked")
	public void search() throws UnsupportedEncodingException {
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		Organization userOrganization = iOrganizationService.get(user.getOrganization());
		lstTeacherExs.clear();
		// 起始页
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "0";
		}

		int start = Integer.parseInt(ist);
		int page = 1;
		// pageStatus = "";
		String hqlString = "";
		List<Teacher> lstTeachers = new ArrayList<>();

		hqlString = "  from Teacher t  where 1=1 ";
		String mainTeachingSubject = request.getParameter("mainTeachingSubject");
		if (!mainTeachingSubject.equals("-1")) {
			hqlString += " and t in (select teacher from TeachingSubject where subject=" + mainTeachingSubject + " and isprime=1)";
		}

		String mainTeachingGrades = request.getParameter("mainTeachingGrades");
		if (!mainTeachingGrades.equals("-1")) {
			hqlString += " and t in (select teahcer from TeachingGrade where grade=" + mainTeachingGrades + " and isprime=1)";
		}
		String mainTeachingLanguage = request.getParameter("mainTeachingLanguage");
		if (!mainTeachingLanguage.equals("-1")) {

			hqlString += " and t in ( select teacher from TeachingLanguage where language=" + mainTeachingLanguage + " and isprime=1)";
		}

		String name = URLDecoder.decode(request.getParameter("tname"), "UTF-8");
		if (!name.equals("")) {

			hqlString += " and t.name like '%" + name + "%'";
		}
		String idcard = request.getParameter("idCard");
		if (!idcard.equals("")) {
			hqlString += " and t.idcard like '%" + idcard + "%'";
		}
		String jobTitle = request.getParameter("jobTitle");
		if (!jobTitle.equals("-1")) {
			hqlString += " and t.jobTitle=" + jobTitle;
		}
		String eductionBackground = request.getParameter("eductionBackground");
		if (!eductionBackground.equals("-1")) {
			hqlString += " and t.eductionBackground=" + eductionBackground;
		}
		String politics = request.getParameter("politics");
		if (!politics.equals("-1")) {
			hqlString += " and t.politics=" + politics;
		}
		String sex = request.getParameter("sex");
		if (!sex.equals("-1")) {
			hqlString += " and t.sex=" + sex;
		}
		String ethnic = request.getParameter("ethnic");
		if (!ethnic.equals("-1")) {
			hqlString += " and t.ethnic=" + ethnic;
		}
		String mobile = request.getParameter("mobile").trim();
		if (!mobile.equals("")) {
			hqlString += " and t.mobile=" + mobile;
		}
		String organization = request.getParameter("organization").trim();
		if (!organization.equals("")) {

			hqlString += " and t.organization=" + organization;
		}
		String chineseLanguageLevel = request.getParameter("chineseLanguageLevel");
		if (!chineseLanguageLevel.equals("-1")) {
			hqlString += " and t.chineseLanguageLevel=" + chineseLanguageLevel;
		}
		String teachingAgeStart = request.getParameter("teachingAgeStart");
		if (!teachingAgeStart.equals("")) {
			int year = Integer.parseInt(request.getParameter("teachingAgeStart"));
			Calendar c = Calendar.getInstance();
			int y = c.get(Calendar.YEAR);
			int m = c.get(Calendar.MONTH);
			int d = c.get(Calendar.DATE);
			String date = y - year + "-" + m + "-" + d;
			Date d1 = java.sql.Date.valueOf(date);
			hqlString += " and t.teachingAge<='" + d1.toLocaleString() + "'";
		}
		String teachingAgeEnd = request.getParameter("teachingAgeEnd");
		if (!teachingAgeEnd.equals("")) {
			int year = Integer.parseInt(request.getParameter("teachingAgeEnd"));
			Calendar c = Calendar.getInstance();
			int y = c.get(Calendar.YEAR);
			int m = c.get(Calendar.MONTH);
			int d = c.get(Calendar.DATE);
			String date = y - year + "-" + m + "-" + d;
			Date d1 = java.sql.Date.valueOf(date);
			hqlString += " and t.teachingAge>='" + d1.toLocaleString() + "'";
		}

		String email = request.getParameter("email").trim();
		if (!email.equals("")) {
			hqlString += " and t.email like '%" + email + "%'";
		}
		String status = request.getParameter("status");
		if (!status.equals("-1")) {
			hqlString += " and t.status=" + status;
		}

		String multiLanguage = request.getParameter("multiLanguage");
		if (!multiLanguage.equals("-1")) {
			hqlString += " and t.multiLanguage=" + multiLanguage;
		}

		// 地区权限判断
		
		if (user.getOrganizationLevel() == DictionyMap.ORGANIZATION_LEVEL_SCHOOL) {
			hqlString += " and t.organization =" + userOrganization.getId();
		} else {
			hqlString += " and t.organization in (from Organization where scode like '" + user.getOrganizationScode() + "%' and isschool=1) ";
		}
		
		// String orgIds = "";
		// if (user.getLstSubSchool().size() > 0) {
		//
		// for (Organization tog : user.getLstSubSchool()) {
		// orgIds += tog.getId() + ",";
		// }
		// orgIds = orgIds.substring(0, orgIds.length() - 1);
		// hqlString += " and t.organization in  (" + orgIds + ")";
		// } else {
		//
		// hqlString = "from Teacher where t.organization =" +
		// userOrganization.getId();
		//
		// }

		// hqlString += " and organization in (" + orgIds + ")";
		session.setAttribute("teacherSearch", hqlString);

		// 排序判断

		String sort = request.getParameter("sort");
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			hqlString += " order by " + sortname + " " + sorttype;
		}
		try {
			lstTeachers = iTeacherService.getListForPage(hqlString, start, pageLength, null);
			totalPage = iTeacherService.getCount("select count(*) " + hqlString) / pageLength;
			session.setAttribute("teahcerTotalPage", totalPage);
		} catch (Exception e) {
			lstTeachers.clear();
		}

		if (lstTeachers.size() > 0) {
			pageStatus = "OK";

			for (Teacher tTeacher : lstTeachers) {
				teacherEx teacherEx = new teacherEx();
				teacherEx.setTeacher(tTeacher);
				String areasString = "";
				List<String> lstA = iAreaService.getParentNodes(tTeacher.getArea().getCode());
				if (lstA.size() > 0) {
					for (String string : lstA) {
						areasString += string + ">>";
					}
				}
				teacherEx.setAreaString(areasString.substring(0, areasString.length() - 2));
				teacherEx.setAgeString((String.valueOf(Utlity.getAge(tTeacher.getBirthday()))));
				teacherEx.setSexString(tTeacher.getSex() == 1 ? "男" : "女");
				if (tTeacher.getTeachingAge() != null) {
					teacherEx.setTeachingAge(String.valueOf(Utlity.getAge(tTeacher.getTeachingAge())));
				}
				teacherEx.setIsMultiLanguage(tTeacher.getMultiLanguage() ? "是" : "否");
				// 获取主要教学语言
				hqlString = "from TeachingLanguage where teacher=" + tTeacher.getId() + " and isprime=true";

				List<TeachingLanguage> lstTeachingLanguages = new ArrayList<>();

				lstTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
				if (lstTeachingLanguages.size() > 0) {
					TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
					teacherEx.setMainTeachingLanguage(teachingLanguage);
				}

				// 获取主要教学学段
				hqlString = "from TeachingGrade where teacher=" + tTeacher.getId() + " and isprime=1";
				List<TeachingGrade> lstTeachingGrades = new ArrayList<>();
				lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
				if (lstTeachingGrades.size() > 0) {
					TeachingGrade teachingGrade = lstTeachingGrades.get(0);
					teacherEx.setMainTeachingClass(teachingGrade);
				}

				// 获取主要教学学科
				hqlString = "from TeachingSubject where teacher=" + tTeacher.getId() + " and isprime=1";
				List<TeachingSubject> lsttTeachingSubjects = new ArrayList<>();
				this.lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
				if (lsttTeachingSubjects.size() > 0) {
					TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
					teacherEx.setMainTeachingCourse(teachingSubject);
				}

				// 获取创建人
				// 获取创建人
				if(tTeacher.getCreator()!=null){
					teacherEx.setCreator(iProjectAdminService.get(tTeacher.getCreator()).getName());
				}else{
					teacherEx.setCreator("");
				}
				lstTeacherExs.add(teacherEx);
			}

		} else {
			message = "没有找到符合条查询条件的结果！";
			pageStatus = "ERROR";
		}
		String rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
		Utlity.ResponseWrite(rString, "json", response);
		// session.setAttribute("TeacherSearch", lstTeacherExs);
		// return "search";
	}

	public void getPageJson() {
		initServlert();
		int page = 1;

		if (request.getParameterMap().containsKey("page")) {
			if (!request.getParameter("page").equals("")) {
				page = Integer.parseInt(request.getParameter("page"));
			}
		}
		totalPage = (double) session.getAttribute("teahcerTotalPage");
		String msg = "{\"currentPage\":" + page + ", \"totalPage\":" + totalPage + "}";
		Utlity.ResponseWrite(msg, "json", response);
	}

//	/**
//	 * 修改15位身份证号教师的生日（一次性方法）
//	 */
//	public void changeBirthdaty(){
//		initServlert();
//		String hqlString = "from Teacher where LENGTH(idcard)=15";
//		List<Teacher> lstTeacher = this.iTeacherService.getListByHSQL(hqlString);
//		for(Teacher teacher: lstTeacher){
//			String idcard = teacher.getIdcard();
//			teacher.setBirthday(IdCardUtil.getBirthday(idcard));
//			this.iTeacherService.update(teacher);
//		}
//			
//	}
	/**
	 * @return the iAreaService
	 */
	public IAreaService getiAreaService() {
		return iAreaService;
	}

	/**
	 * @param iAreaService
	 *            the iAreaService to set
	 */
	public void setiAreaService(IAreaService iAreaService) {
		this.iAreaService = iAreaService;
	}

	/**
	 * @return the lstTeacherExs
	 */
	public List<teacherEx> getLstTeacherExs() {
		return lstTeacherExs;
	}

	/**
	 * @param lstTeacherExs
	 *            the lstTeacherExs to set
	 */
	public void setLstTeacherExs(List<teacherEx> lstTeacherExs) {
		this.lstTeacherExs = lstTeacherExs;
	}

	/**
	 * @return the iTeacherService
	 */
	public ITeacherService getiTeacherService() {
		return iTeacherService;
	}

	/**
	 * @param iTeacherService
	 *            the iTeacherService to set
	 */
	public void setiTeacherService(ITeacherService iTeacherService) {
		this.iTeacherService = iTeacherService;
	}

	/**
	 * @return the iTeachingLanguageService
	 */
	public ITeachingLanguageService getiTeachingLanguageService() {
		return iTeachingLanguageService;
	}

	/**
	 * @param iTeachingLanguageService
	 *            the iTeachingLanguageService to set
	 */
	public void setiTeachingLanguageService(ITeachingLanguageService iTeachingLanguageService) {
		this.iTeachingLanguageService = iTeachingLanguageService;
	}

	/**
	 * @return the iTeachingGradeService
	 */
	public ITeachingGradeService getiTeachingGradeService() {
		return iTeachingGradeService;
	}

	/**
	 * @param iTeachingGradeService
	 *            the iTeachingGradeService to set
	 */
	public void setiTeachingGradeService(ITeachingGradeService iTeachingGradeService) {
		this.iTeachingGradeService = iTeachingGradeService;
	}

	/**
	 * @return the iTeachingSubjectService
	 */
	public ITeachingSubjectService getiTeachingSubjectService() {
		return iTeachingSubjectService;
	}

	/**
	 * @param iTeachingSubjectService
	 *            the iTeachingSubjectService to set
	 */
	public void setiTeachingSubjectService(ITeachingSubjectService iTeachingSubjectService) {
		this.iTeachingSubjectService = iTeachingSubjectService;
	}

	/**
	 * @return the iProjectAdminService
	 */
	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	/**
	 * @param iProjectAdminService
	 *            the iProjectAdminService to set
	 */
	public void setiProjectAdminService(IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}

	/**
	 * @return the iOrganizationService
	 */
	public IOrganizationService getiOrganizationService() {
		return iOrganizationService;
	}

	/**
	 * @param iOrganizationService
	 *            the iOrganizationService to set
	 */
	public void setiOrganizationService(IOrganizationService iOrganizationService) {
		this.iOrganizationService = iOrganizationService;
	}

	/**
	 * @return the province
	 */
	public Area getProvince() {
		return province;
	}

	/**
	 * @param province
	 *            the province to set
	 */
	public void setProvince(Area province) {
		this.province = province;
	}

	/**
	 * @return the city
	 */
	public Area getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(Area city) {
		this.city = city;
	}

	/**
	 * @return the county
	 */
	public Area getCounty() {
		return county;
	}

	/**
	 * @param county
	 *            the county to set
	 */
	public void setCounty(Area county) {
		this.county = county;
	}

	/**
	 * @return the lstAreas
	 */
	public List<Area> getLstAreas() {
		return lstAreas;
	}

	/**
	 * @param lstAreas
	 *            the lstAreas to set
	 */
	public void setLstAreas(List<Area> lstAreas) {
		this.lstAreas = lstAreas;
	}

	/**
	 * @return the lstProvince
	 */
	public List<Area> getLstProvince() {
		return lstProvince;
	}

	/**
	 * @param lstProvince
	 *            the lstProvince to set
	 */
	public void setLstProvince(List<Area> lstProvince) {
		this.lstProvince = lstProvince;
	}

	/**
	 * @return the lstCity
	 */
	public List<Area> getLstCity() {
		return lstCity;
	}

	/**
	 * @param lstCity
	 *            the lstCity to set
	 */
	public void setLstCity(List<Area> lstCity) {
		this.lstCity = lstCity;
	}

	/**
	 * @return the lstCountry
	 */
	public List<Area> getLstCountry() {
		return lstCountry;
	}

	/**
	 * @param lstCountry
	 *            the lstCountry to set
	 */
	public void setLstCountry(List<Area> lstCountry) {
		this.lstCountry = lstCountry;
	}

	/**
	 * @return the iEthnicService
	 */
	public IEthnicService getiEthnicService() {
		return iEthnicService;
	}

	/**
	 * @param iEthnicService
	 *            the iEthnicService to set
	 */
	public void setiEthnicService(IEthnicService iEthnicService) {
		this.iEthnicService = iEthnicService;
	}

	/**
	 * @return the lstPolitics
	 */
	public List<Politics> getLstPolitics() {
		return lstPolitics;
	}

	/**
	 * @param lstPolitics
	 *            the lstPolitics to set
	 */
	public void setLstPolitics(List<Politics> lstPolitics) {
		this.lstPolitics = lstPolitics;
	}

	/**
	 * @return the lstBackgrounds
	 */
	public List<EductionBackground> getLstBackgrounds() {
		return lstBackgrounds;
	}

	/**
	 * @param lstBackgrounds
	 *            the lstBackgrounds to set
	 */
	public void setLstBackgrounds(List<EductionBackground> lstBackgrounds) {
		this.lstBackgrounds = lstBackgrounds;
	}

	/**
	 * @return the lstEthnics
	 */
	public List<Ethnic> getLstEthnics() {
		return lstEthnics;
	}

	/**
	 * @param lstEthnics
	 *            the lstEthnics to set
	 */
	public void setLstEthnics(List<Ethnic> lstEthnics) {
		this.lstEthnics = lstEthnics;
	}

	/**
	 * @return the lstJobTitles
	 */
	public List<JobTitle> getLstJobTitles() {
		return lstJobTitles;
	}

	/**
	 * @param lstJobTitles
	 *            the lstJobTitles to set
	 */
	public void setLstJobTitles(List<JobTitle> lstJobTitles) {
		this.lstJobTitles = lstJobTitles;
	}

	/**
	 * @return the lstLanguages
	 */
	public List<Language> getLstLanguages() {
		return lstLanguages;
	}

	/**
	 * @param lstLanguages
	 *            the lstLanguages to set
	 */
	public void setLstLanguages(List<Language> lstLanguages) {
		this.lstLanguages = lstLanguages;
	}

	/**
	 * @return the lsTeachingLanguages
	 */
	public List<TeachingLanguage> getLsTeachingLanguages() {
		return lsTeachingLanguages;
	}

	/**
	 * @param lsTeachingLanguages
	 *            the lsTeachingLanguages to set
	 */
	public void setLsTeachingLanguages(List<TeachingLanguage> lsTeachingLanguages) {
		this.lsTeachingLanguages = lsTeachingLanguages;
	}

	/**
	 * @return the lstTeachingGrades
	 */
	public List<TeachingGrade> getLstTeachingGrades() {
		return lstTeachingGrades;
	}

	/**
	 * @param lstTeachingGrades
	 *            the lstTeachingGrades to set
	 */
	public void setLstTeachingGrades(List<TeachingGrade> lstTeachingGrades) {
		this.lstTeachingGrades = lstTeachingGrades;
	}

	/**
	 * @return the lsttTeachingSubjects
	 */
	public List<TeachingSubject> getLsttTeachingSubjects() {
		return lsttTeachingSubjects;
	}

	/**
	 * @param lsttTeachingSubjects
	 *            the lsttTeachingSubjects to set
	 */
	public void setLsttTeachingSubjects(List<TeachingSubject> lsttTeachingSubjects) {
		this.lsttTeachingSubjects = lsttTeachingSubjects;
	}

	/**
	 * @return the lstJobDuties
	 */
	public List<JobDuty> getLstJobDuties() {
		return lstJobDuties;
	}

	/**
	 * @param lstJobDuties
	 *            the lstJobDuties to set
	 */
	public void setLstJobDuties(List<JobDuty> lstJobDuties) {
		this.lstJobDuties = lstJobDuties;
	}

	/**
	 * @return the lstChineseLanguageLevels
	 */
	public List<ChineseLanguageLevel> getLstChineseLanguageLevels() {
		return lstChineseLanguageLevels;
	}

	/**
	 * @param lstChineseLanguageLevels
	 *            the lstChineseLanguageLevels to set
	 */
	public void setLstChineseLanguageLevels(List<ChineseLanguageLevel> lstChineseLanguageLevels) {
		this.lstChineseLanguageLevels = lstChineseLanguageLevels;
	}

	/**
	 * @return the iJobDutyService
	 */
	public IJobDutyService getiJobDutyService() {
		return iJobDutyService;
	}

	/**
	 * @param iJobDutyService
	 *            the iJobDutyService to set
	 */
	public void setiJobDutyService(IJobDutyService iJobDutyService) {
		this.iJobDutyService = iJobDutyService;
	}

	/**
	 * @return the iJobTitleService
	 */
	public IJobTitleService getiJobTitleService() {
		return iJobTitleService;
	}

	/**
	 * @param iJobTitleService
	 *            the iJobTitleService to set
	 */
	public void setiJobTitleService(IJobTitleService iJobTitleService) {
		this.iJobTitleService = iJobTitleService;
	}

	/**
	 * @return the iEductionBackgroundService
	 */
	public IEductionBackgroundService getiEductionBackgroundService() {
		return iEductionBackgroundService;
	}

	/**
	 * @param iEductionBackgroundService
	 *            the iEductionBackgroundService to set
	 */
	public void setiEductionBackgroundService(IEductionBackgroundService iEductionBackgroundService) {
		this.iEductionBackgroundService = iEductionBackgroundService;
	}

	/**
	 * @return the iPoliticsService
	 */
	public IPoliticsService getiPoliticsService() {
		return iPoliticsService;
	}

	/**
	 * @param iPoliticsService
	 *            the iPoliticsService to set
	 */
	public void setiPoliticsService(IPoliticsService iPoliticsService) {
		this.iPoliticsService = iPoliticsService;
	}

	/**
	 * @return the iChineseLanguageLevelService
	 */
	public IChineseLanguageLevelService getiChineseLanguageLevelService() {
		return iChineseLanguageLevelService;
	}

	/**
	 * @param iChineseLanguageLevelService
	 *            the iChineseLanguageLevelService to set
	 */
	public void setiChineseLanguageLevelService(IChineseLanguageLevelService iChineseLanguageLevelService) {
		this.iChineseLanguageLevelService = iChineseLanguageLevelService;
	}

	/**
	 * @return the lstGrades
	 */
	public List<Grade> getLstGrades() {
		return lstGrades;
	}

	/**
	 * @param lstGrades
	 *            the lstGrades to set
	 */
	public void setLstGrades(List<Grade> lstGrades) {
		this.lstGrades = lstGrades;
	}

	/**
	 * @return the iLanguageService
	 */
	public ILanguageService getiLanguageService() {
		return iLanguageService;
	}

	/**
	 * @param iLanguageService
	 *            the iLanguageService to set
	 */
	public void setiLanguageService(ILanguageService iLanguageService) {
		this.iLanguageService = iLanguageService;
	}

	/**
	 * @return the iSubjectService
	 */
	public ISubjectService getiSubjectService() {
		return iSubjectService;
	}

	/**
	 * @param iSubjectService
	 *            the iSubjectService to set
	 */
	public void setiSubjectService(ISubjectService iSubjectService) {
		this.iSubjectService = iSubjectService;
	}

	/**
	 * @return the iGradeService
	 */
	public IGradeService getiGradeService() {
		return iGradeService;
	}

	/**
	 * @param iGradeService
	 *            the iGradeService to set
	 */
	public void setiGradeService(IGradeService iGradeService) {
		this.iGradeService = iGradeService;
	}

	/**
	 * @return the lstSubjects
	 */
	public List<Subject> getLstSubjects() {
		return lstSubjects;
	}

	/**
	 * @param lstSubjects
	 *            the lstSubjects to set
	 */
	public void setLstSubjects(List<Subject> lstSubjects) {
		this.lstSubjects = lstSubjects;
	}

	/**
	 * @return the teacher
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher
	 *            the teacher to set
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	/**
	 * @return the pageStatus
	 */
	public String getPageStatus() {
		return pageStatus;
	}

	/**
	 * @param pageStatus
	 *            the pageStatus to set
	 */
	public void setPageStatus(String pageStatus) {
		this.pageStatus = pageStatus;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the lstteacTeachingSubjectExs
	 */
	public List<teachingSubjectEx> getLstteacTeachingSubjectExs() {
		return lstteacTeachingSubjectExs;
	}

	/**
	 * @param lstteacTeachingSubjectExs
	 *            the lstteacTeachingSubjectExs to set
	 */
	public void setLstteacTeachingSubjectExs(List<teachingSubjectEx> lstteacTeachingSubjectExs) {
		this.lstteacTeachingSubjectExs = lstteacTeachingSubjectExs;
	}

	/**
	 * @return the lstOrganizationExs
	 */
	public List<OrganizationEx> getLstOrganizationExs() {
		return lstOrganizationExs;
	}

	/**
	 * @param lstOrganizationExs
	 *            the lstOrganizationExs to set
	 */
	public void setLstOrganizationExs(List<OrganizationEx> lstOrganizationExs) {
		this.lstOrganizationExs = lstOrganizationExs;
	}

	/**
	 * @return the lstOrganization
	 */
	public List<Organization> getLstOrganization() {
		return lstOrganization;
	}

	/**
	 * @param lstOrganization
	 *            the lstOrganization to set
	 */
	public void setLstOrganization(List<Organization> lstOrganization) {
		this.lstOrganization = lstOrganization;
	}

	/**
	 * @return the teacherEx
	 */
	public teacherEx getTeacherEx() {
		return teacherEx;
	}

	/**
	 * @param teacherEx
	 *            the teacherEx to set
	 */
	public void setTeacherEx(teacherEx teacherEx) {
		this.teacherEx = teacherEx;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the organizationp
	 */
	public Organization getOrganizationp() {
		return organizationp;
	}

	/**
	 * @param organizationp
	 *            the organizationp to set
	 */
	public void setOrganizationp(Organization organizationp) {
		this.organizationp = organizationp;
	}

	public int getOrganizationLevel() {
		return organizationLevel;
	}

	public void setOrganizationLevel(int organizationLevel) {
		this.organizationLevel = organizationLevel;
	}

}
