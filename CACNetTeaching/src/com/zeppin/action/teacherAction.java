/**
 * 
 */
package com.zeppin.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zeppin.entiey.Accessory;
import com.zeppin.entiey.Classmap;
import com.zeppin.entiey.CourseCoursewareMap;
import com.zeppin.entiey.Coursedesign;
import com.zeppin.entiey.CoursedesignTeacherMap;
import com.zeppin.entiey.GroupStudenMap;
import com.zeppin.entiey.Homework;
import com.zeppin.entiey.HomeworkAccessoryExt;
import com.zeppin.entiey.HomeworkAccessoryMap;
import com.zeppin.entiey.Message;
import com.zeppin.entiey.Sso;
import com.zeppin.entiey.SsoMessageMap;
import com.zeppin.entiey.Student;
import com.zeppin.entiey.Studentgrou;
import com.zeppin.entiey.Teacher;
import com.zeppin.entiey.myCourse;
import com.zeppin.entiey.studentExt;
import com.zeppin.service.IAccessoryCourseMapService;
import com.zeppin.service.IAccessoryService;
import com.zeppin.service.IClassTeacherMapService;
import com.zeppin.service.IClassmapService;
import com.zeppin.service.ICoursePlanService;
import com.zeppin.service.IHomeworkAccessoryMapService;
import com.zeppin.service.IHomeworkService;
import com.zeppin.service.IMessageService;
import com.zeppin.service.IMessageSsoService;
import com.zeppin.service.ISgroupMapService;
import com.zeppin.service.ISsoService;
import com.zeppin.service.IStudentGroupService;
import com.zeppin.service.IStudentService;
import com.zeppin.service.ITeachereService;
import com.zeppin.util.cryptogram.ECrypMethod;
import com.zeppin.util.cryptogram.ECrypType;
import com.zeppin.util.cryptogram.GetCriptString;

/**
 * @author suijing
 * 
 */
public class teacherAction extends ActionSupport
{
    static Logger logger = LogManager.getLogger(teacherAction.class);
    IClassTeacherMapService ictmService;// 课程计划操作
    IStudentGroupService isgs;// 学生分组定义
    ICoursePlanService icps;// 课程计划操作
    IClassmapService ics;// 课程计划学生map操作
    ISgroupMapService isms; // 学生入组操作
    IStudentService iss; // 学生操作
    IAccessoryService ias;// 附件操作
    IAccessoryCourseMapService iacms;// 附件课程map操作
    IHomeworkService ihs;// 布置作业操作
    IHomeworkAccessoryMapService ihams;// 作业附件表
    ITeachereService its;// 教师操作
    ISsoService isss;
    IMessageSsoService imss;// 消息用户操作
    IMessageService imsService;

    Teacher teacher = new Teacher();
    CoursedesignTeacherMap ctm = new CoursedesignTeacherMap();// 课程计划老师map
    Homework homework = new Homework();

    String hqlString;
    String jsonString;
    String isHidden = "hidden";// 是否显示链接
    Message message = new Message();

    private int page;
    private int row;
    private BigInteger records;
    private int total;
    private List objects = new ArrayList();
    private int id;
    private int studentCount;
    private Coursedesign coursedesign;
    private List<Classmap> lClassmaps = new ArrayList<Classmap>();
    private List<Student> lstStudents = new ArrayList<Student>();
    List<studentExt> lstStudentExt = new ArrayList<studentExt>();
    List<CoursedesignTeacherMap> lstCdtMaps = new ArrayList<CoursedesignTeacherMap>();// 课程计划老师list
    List<myCourse> lstMc = new ArrayList<myCourse>();// 我的课程表
    List<Studentgrou> lstsg = new ArrayList<Studentgrou>();// 学生分组表
    List<GroupStudenMap> lstgsMaps = new ArrayList<GroupStudenMap>();// 分组-学生表
    List<CourseCoursewareMap> lstccwm = new ArrayList<CourseCoursewareMap>();
    List<Homework> lstHw = new ArrayList<Homework>();
    List<HomeworkAccessoryMap> lstHam = new ArrayList<HomeworkAccessoryMap>();
    List<HomeworkAccessoryExt> lsthaex = new ArrayList<HomeworkAccessoryExt>();
    List<SsoMessageMap> lstSmm = new ArrayList<SsoMessageMap>();

    /*
     * fileupload,fileuploadFileName,fileuploadContentType命名规则，否则无法取值
     * fileupload为客户端input上传组件的name
     * ,fileuploadFileName文件名称是fileupload+FileName组成，必须这样 fileuploadContentType
     * 文件类型是fileupload+ContentType组成，必须这样写
     */
    private File fileupload;// 上传的文件
    private String fileuploadFileName;// 文件的名称 如上传的文件是a.png
				      // 则fileuploadFileName值为"a.png"
    private String fileuploadContentType;// 文件的类型
					 // 如上传的是png格式的图片，则fileuploadContentType值为"image/png"
    private float fileuploadFileSize;
    /*
     * 指定上传文件在服务器上的保存目录，需要在Action中为定义savePath变量并为其添加相应的setter和getter方法
     * 便于Struts2将struts.xml中的<param
     * name="savePath">uploads/</param>值赋给savePath属性
     */
    private String savePath;// 文件的保存位置

    /**
     * 我的课程
     * 
     * @return
     */
    public String myClassInit()
    {
	HttpServletRequest req = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = req.getSession();
	session.removeAttribute("isHiddent");
	isHidden = "hidden";
	if (session.getAttribute("teacher") != null)
	{
	    teacher = (Teacher) session.getAttribute("teacher");

	    hqlString = "from CoursedesignTeacherMap where coursedesign.status = 1 and teacher="
		    + teacher.getId() + " order by coursedesign desc";
	    lstCdtMaps = ictmService.getIClassTeacherMapDao().getListByHSQL(
		    hqlString);
	    lstMc.clear();
	    for (CoursedesignTeacherMap cstMap : lstCdtMaps)
	    {
		myCourse tMyCourse = new myCourse();
		tMyCourse.setCoursedesign(cstMap.getCoursedesign());
		tMyCourse
			.setMode(cstMap.getCoursedesign().getStudyMode() == 1 ? "必修"
				: "选修");
		hqlString = "from CoursedesignTeacherMap where coursedesign="
			+ cstMap.getCoursedesign().getId();
		List<CoursedesignTeacherMap> tLs = new ArrayList<CoursedesignTeacherMap>();
		tLs = ictmService.getIClassTeacherMapDao().getListByHSQL(
			hqlString);
		String tTeaheres = "";
		for (CoursedesignTeacherMap coursedesignTeacherMap : tLs)
		{

		    tTeaheres += coursedesignTeacherMap.getTeacher().getName()
			    + "; ";
		    if (coursedesignTeacherMap.getIsLeader())
		    {
			tMyCourse.setLeader(coursedesignTeacherMap.getTeacher()
				.getName());
			if (coursedesignTeacherMap.getTeacher().getId()
				.toString().equals(teacher.getId().toString()))
			{

			    isHidden = "visible";
			    session.setAttribute("isHiddent", "block");

			}

		    }
		}

		tMyCourse.setTeacheres(tTeaheres);

		lstMc.add(tMyCourse);
	    }
	}

	return "myClassInit";
    }

    public String groupManageInit() throws IOException
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = res.getSession();
	if (session.getAttribute("isHiddent") == null)
	{
	    return "login";

	}
	int coursid = Integer.parseInt(res.getParameter("coursedesignId"));
	if (!checkRight(res, coursid))
	{

	    return "login";
	}

	return "groupManageInit";
    }

    @SuppressWarnings("unchecked")
    public void groupManaged() throws IOException
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	String tableNameString = "studentgrou";
	String ObjtableName = "Studentgrou";
	String opt;
	String id;
	Coursedesign coursedesign = new Coursedesign();

	if (res.getParameterMap().containsKey("opt"))
	{
	    opt = res.getParameter("opt");
	    int coursid = Integer.parseInt(res.getParameter("coursedesignId"));
	    coursedesign.setId(coursid);
	    if (opt.equals("init"))
	    {
		page = Integer.parseInt(res.getParameter("page"));
		row = Integer.parseInt(res.getParameter("rows"));
		hqlString = "select count(*) from  Studentgrou";
		records = (BigInteger) isgs.getIStudentGroupDao()
			.executeSQL(hqlString, null).listIterator().next();
		total = records.intValue() / row + 1;
		String sidxString = res.getParameter("sidx");
		String sord = res.getParameter("sord");
		hqlString = "from Studentgrou where coursedesign=" + coursid
			+ " order by " + sidxString + " " + sord;
		lstsg = isgs.getIStudentGroupDao().getListByHSQL(hqlString);
		jsonString = "";
		jsonString += "{";
		jsonString += "\"page\":\"" + page + "\",";
		jsonString += "\"total\":\"" + total + "\",";
		jsonString += "\"records\":\"" + records + "\",";
		jsonString += "\"rows\":[";
		for (Studentgrou s : lstsg)
		{

		    jsonString += "{\"id\":\"" + s.getId() + "\",";
		    jsonString += "\"cell\":[" + s.getId() + ",\""
			    + s.getCoursedesign().getSubject().getName()
			    + "\",\"" + s.getGroupName() + "\",\""
			    + s.getTeacher().getName() + "\"]},";

		}
		if (lstsg.size() > 0)
		    jsonString = jsonString.substring(0,
			    jsonString.length() - 1);
		jsonString += "]}";

		rps.setContentType("application/json");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);

	    }

	    else if (opt.equals("getkv"))
	    {

		hqlString = "from CoursedesignTeacherMap where coursedesign="
			+ coursid;
		lstCdtMaps = ictmService.getIClassTeacherMapDao()
			.getListByHSQL(hqlString);

		jsonString = "<select>";

		for (CoursedesignTeacherMap ctm : lstCdtMaps)
		{

		    jsonString += "<option value=" + ctm.getTeacher().getId()
			    + ">" + ctm.getTeacher().getName() + "</option>";

		}
		// jsonString = jsonString.substring(0, jsonString.length() -
		// 1);
		jsonString += "</select>";
		rps.setContentType("text/html");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);
	    }
	    // 添加新项目
	    else if (opt.equals("add") || opt.equals("edit"))
	    {
		Studentgrou studentgrou = new Studentgrou();
		int teacherId = Integer.parseInt(res.getParameter("teacheres"));
		teacher.setId(teacherId);
		String groupNameString = replaceBlank(res.getParameter("groupName"));
		studentgrou.setGroupName(groupNameString);
		studentgrou.setTeacher(teacher);
		studentgrou.setCoursedesign(coursedesign);

		if (opt.equals("add"))
		{

		    isgs.getIStudentGroupDao().add(studentgrou);

		}
		else
		{
		    id = res.getParameter("id");
		    // coursedesign.setId(Integer.parseInt(id));
		    // icps.getICoursePlanDao().update(coursedesign);
		    hqlString = "update "
			    + tableNameString
			    + " set fk_crousePlan=?,fk_teacher=?,groupName=? where id=?";

		    objects.clear();

		    objects.add(coursid);
		    objects.add(teacherId);
		    objects.add(groupNameString);
		    objects.add(Integer.parseInt(id));

		    try
		    {
			isgs.getIStudentGroupDao().executeSQLUpdate(hqlString,
				objects.toArray());
		    }
		    catch (Exception e)
		    {
			logger.error(e.getMessage());
		    }

		}

	    }

	    else if (opt.equalsIgnoreCase("del"))
	    {
		id = res.getParameter("id");
		if (!id.equalsIgnoreCase(""))
		{
		    hqlString = "delete from " + tableNameString
			    + "  where id=?";
		    objects.clear();
		    objects.add(Integer.parseInt(id));
		    isgs.getIStudentGroupDao().executeSQLUpdate(hqlString,
			    objects.toArray());

		}

	    }
	}

    }

    /**
     * 初始化符合条件的全体学生
     * 
     * @throws IOException
     */
    public void studentlistManage() throws IOException
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();

	if (res.getParameterMap().containsKey("opt"))
	{
	    String opt = res.getParameter("opt");
	    int coursedesignId = Integer.parseInt(res.getParameter("courseId"));
	    // coursedesign = icps.getICoursePlanDao().get(coursedesignId);//
	    // 获取课程计划信息
	    // 初始化
	    if (opt.equals("init"))
	    {

		hqlString = "select count(*) from  classmap where fk_courseDesignId="
			+ coursedesignId;
		page = Integer.parseInt(res.getParameter("page"));
		row = Integer.parseInt(res.getParameter("rows"));
		try
		{
		    records = (BigInteger) ics.getiClassMapDao()
			    .executeSQL(hqlString, null).listIterator().next();
		}
		catch (Exception e)
		{
		    logger.info(e.getMessage());
		}

		total = records.intValue() / row + 1;
		String sidxString = res.getParameter("sidx");
		String sord = res.getParameter("sord");

		hqlString = "from Classmap where coursedesign="
			+ coursedesignId + " order by " + sidxString + " "
			+ sord;

		List<Classmap> lstcs = ics.getiClassMapDao().getListForPage(
			hqlString, page, row);
		jsonString = "";
		jsonString += "{";
		jsonString += "\"page\":\"" + page + "\",";
		jsonString += "\"total\":\"" + total + "\",";
		jsonString += "\"records\":\"" + records + "\",";
		jsonString += "\"rows\":[";
		for (Classmap cs : lstcs)
		{
		    String sexString = (cs.getStudent().getSex() == 1 ? "男"
			    : "女");
		    String stateString = (cs.getStudent().getState() == 1 ? "正常"
			    : "异动");

		    jsonString += "{\"id\":\"" + cs.getStudent().getId()
			    + "\",";
		    jsonString += "\"cell\":["
			    + cs.getStudent().getId()
			    + ",\""
			    + cs.getStudent().getName()
			    + "\",\""
			    + cs.getStudent().getStudentCode()
			    + "\",\""
			    + cs.getStudent().getDicMajor().getName()
			    + "\",\""
			    + cs.getStudent().getClasses()
			    + "\",\""
			    + cs.getStudent().getEdusystem().getName()
			    + "\",\""
			    + cs.getStudent().getDicAddress().getName()
			    + "\",\""
			    + cs.getStudent().getDicAddress().getName()
			    + "\",\""
			    + DateFormat.getDateInstance(DateFormat.DEFAULT)
				    .format(cs.getStudent().getEduStartTime())
			    + "\",\"" + stateString + "\",\"" + sexString
			    + "\",\"" + cs.getStudent().getPhone() + "\",\""
			    + cs.getStudent().getIdCode() + "\",\""
			    + cs.getStudent().getFamilyName() + "\",\""
			    + cs.getStudent().getFamilyphone() + "\",\""
			    + cs.getStudent().getRemark() +

			    "\"]},";

		}
		if (lstcs.size() > 0)
		    jsonString = jsonString.substring(0,
			    jsonString.length() - 1);
		jsonString += "]}";

		rps.setContentType("application/json");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);

	    }
	}
    }

    public String studentManageInit()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();

	if (res.getParameterMap().containsKey("id"))
	{
	    id = Integer.parseInt(res.getParameter("id"));
	    int coursedesignId = Integer.parseInt(res.getParameter("courseId"));
	    coursedesign = icps.getICoursePlanDao().get(coursedesignId);// 获取课程计划信息
	    hqlString = "from GroupStudenMap where studentgrou=" + id;
	    // 获取学生列表
	    lstgsMaps = isms.getISgroupMapDao().getListByHSQL(hqlString);
	    lstStudents.clear();
	    studentCount = lstgsMaps.size();
	    if (lstgsMaps.size() > 0)
	    {

		for (GroupStudenMap gsm : lstgsMaps)
		{
		    lstStudents.add(gsm.getStudent());
		    // student = cm.getStudent();

		}

	    }

	}

	return "studentManageInit";
    }

    public void addStudent()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	if (res.getParameterMap().containsKey("id"))
	{
	    int groupId = Integer.parseInt(res.getParameter("id"));
	    Studentgrou studentgrou = new Studentgrou();
	    studentgrou.setId(groupId);
	    String ids[] = res.getParameter("ids[]").split(",");
	    for (String id : ids)
	    {
		GroupStudenMap gsm = new GroupStudenMap();
		Student student = new Student();
		student.setId(Integer.parseInt(id));
		gsm.setStudent(student);
		gsm.setStudentgrou(studentgrou);
		try
		{
		    isms.getISgroupMapDao().add(gsm);
		}
		catch (Exception e)
		{
		    continue;
		}

	    }
	}

    }

    public void removeStudent()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	if (res.getParameterMap().containsKey("id"))
	{
	    id = Integer.parseInt(res.getParameter("id"));
	    hqlString = "delete from GroupStudenMap where id=" + id;
	    isms.getISgroupMapDao().executeHSQL(hqlString);

	}
    }

    /**
     * 课件管理页面初始化
     * 
     * @return
     */
    public String courseWareInit()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();

	if (res.getParameterMap().containsKey("coursedesignId"))
	{
	    int coursedesignId = Integer.parseInt(res
		    .getParameter("coursedesignId"));
	    if (!checkRight(res, coursedesignId))
	    {

		return "login";
	    }
	    coursedesign = icps.getICoursePlanDao().get(coursedesignId);// 获取课程计划信息
	    hqlString = "from CourseCoursewareMap where coursedesign="
		    + coursedesignId;
	    // 获取学生列表
	    lstccwm = iacms.getIAccocessoryCourseMapDao().getListByHSQL(
		    hqlString);

	}

	return "courseWareInit";
    }

    public void delCourseWare()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	if (res.getParameterMap().containsKey("id"))
	{
	    id = Integer.parseInt(res.getParameter("id"));
	    Accessory accessory = ias.getAccessoryDao().get(id);
	    String absolutePath = ServletActionContext.getServletContext()
		    .getRealPath(""); // 获取项目根路径
	    // 文件路径
	    String path = absolutePath + "/" + accessory.getFilePath() + "/"
		    + accessory.getName() + accessory.getFileType();
	    File file = new File(path);
	    if (file.exists())
	    {
		file.delete();

	    }
	    hqlString = "delete from CourseCoursewareMap where accessory=" + id;
	    isms.getISgroupMapDao().executeHSQL(hqlString);
	    hqlString = "delete from Accessory where id=" + id;
	    ias.getAccessoryDao().executeHSQL(hqlString);

	}
    }

    public String homeworkInit()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();

	if (res.getParameterMap().containsKey("coursedesignId"))
	{
	    int coursedesignId = Integer.parseInt(res
		    .getParameter("coursedesignId"));
	    if (!checkRight(res, coursedesignId))
	    {

		return "login";
	    }
	    coursedesign = icps.getICoursePlanDao().get(coursedesignId);// 获取课程计划信息
	    hqlString = "from Homework where coursedesign=" + coursedesignId;
	    // 获取作业列表
	    lstHw = ihs.getIHomeworkDao().getListByHSQL(hqlString);

	}

	return "homeworkInit";
    }

    public void addHomework() throws IOException
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();

	if (res.getParameterMap().containsKey("coursedesignId"))
	{
	    int coursedesignId = Integer.parseInt(res
		    .getParameter("coursedesignId"));

	    coursedesign.setId(coursedesignId);
	    HttpSession session = res.getSession();
	    teacher = (Teacher) session.getAttribute("teacher");
	    homework.setTeacher(teacher);
	    homework.setCoursedesign(coursedesign);
	    homework.setStartTime(new Date());
	    ihs.getIHomeworkDao().add(homework);
	    homework = new Homework();
	    rps.sendRedirect("teacher_homeworkInit.action?coursedesignId="
		    + coursedesignId);

	}
    }

    public void delHomework()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	if (res.getParameterMap().containsKey("id"))
	{
	    id = Integer.parseInt(res.getParameter("id"));
	    hqlString = "delete from Homework where id=" + id;
	    ihs.getIHomeworkDao().executeHSQL(hqlString);

	}
    }

    /**
     * 成绩管理初始化列表
     * 
     * @return
     */
    public String scoreManageInit()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = res.getSession();
	if (session.getAttribute("isHiddent") == null)
	{
	    return "login";

	}
	if (res.getParameterMap().containsKey("coursedesignId"))
	{
	    int coursedesignId = Integer.parseInt(res
		    .getParameter("coursedesignId"));
	    if (!checkRight(res, coursedesignId))
	    {

		return "login";
	    }
	    coursedesign = icps.getICoursePlanDao().get(coursedesignId);// 获取课程计划信息
	    hqlString = "from Classmap where coursedesign=" + coursedesignId;
	    // 获取学生列表
	    try
	    {
		lClassmaps = ics.getiClassMapDao().getListByHSQL(hqlString);
	    }
	    catch (Exception e)
	    {
		logger.error(e.getMessage());
	    }
	    lstStudentExt.clear();
	    if (lClassmaps.size() > 0)
	    {

		for (Classmap cm : lClassmaps)
		{
		    studentExt sExt = new studentExt();
		    sExt.setStudent(cm.getStudent());
		    sExt.setSex(cm.getStudent().getSex() == 1 ? "男" : "女");
		    hqlString = "select  sg.groupName from studentgrou sg join group_studen_map gsm on sg.id=gsm.fk_group and sg.fk_crousePlan="
			    + coursedesignId
			    + " and gsm.fk_student="
			    + cm.getStudent().getId();
		    @SuppressWarnings({ "rawtypes" })
		    List lstTemp = ics.getiClassMapDao().executeSQL(hqlString,
			    null);
		    if (lstTemp.size() > 0)
		    {
			sExt.setGroup((String) lstTemp.get(0));
		    }
		    sExt.setScore(cm.getScore());
		    lstStudentExt.add(sExt);

		}

	    }

	}

	return "scoreManageInit";
    }

    public void scoreSubmit()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();

	if (res.getParameterMap().containsKey("courseId"))
	{
	    int coursedesignId = Integer.parseInt(res.getParameter("courseId"));

	    hqlString = "update Coursedesign set scoreState=1 where id="
		    + coursedesignId;
	    // 获取学生列表
	    try
	    {
		icps.getICoursePlanDao().executeHSQL(hqlString);
	    }
	    catch (Exception e)
	    {
		logger.error(e.getMessage());
	    }
	}
    }

    public void scoreInput() throws IOException
    {
    	HttpServletRequest res = ServletActionContext.getRequest();
    	HttpServletResponse rps = ServletActionContext.getResponse();
    	String jsonString = "";
    	if (res.getParameterMap().containsKey("courseId"))
    	{
    	    int coursedesignId = Integer.parseInt(res.getParameter(
    		    "courseId").toString());
    	    int sid = Integer.parseInt(res.getParameter("sid"));
    	    float score = Float.parseFloat(res.getParameter("score"));

    	    if (score >= 90){
    	    	String sqlString = "select count(*) from classmap where fk_courseDesignId = " + coursedesignId +" and score >= 90 and fk_studenteId <> "+ sid ;
    	    	Integer count =Integer.valueOf(ics.getiClassMapDao().executeSQL(sqlString, null).get(0).toString());
    	    	String sqlStr = "select count(*) from classmap where fk_courseDesignId = " + coursedesignId ;
    	    	Integer all =  Integer.valueOf(ics.getiClassMapDao().executeSQL(sqlStr, null).get(0).toString());
    	    	if (((float)count / (float)all) > 0.2){
    	    		jsonString += "{\"Status\":\"fail\"}";
        		    rps.setContentType("application/json");
        			rps.setCharacterEncoding("UTF-8");
        			rps.setHeader("Cache-Control", "no-cache");
        			rps.getWriter().write(jsonString);
    	    	}else{
    	    		hqlString = "update Classmap set score=" + score
    	    			    + " where coursedesign=" + coursedesignId + " and student="
    	    			    + sid;
    	    	
    	    		    // 获取学生列表
    	    		    try
    	    		    {
    	    			ics.getiClassMapDao().executeHSQL(hqlString);
    	    		    }
    	    		    catch (Exception e)
    	    		    {
    	    			logger.error(e.getMessage());
    	    		    }
    	    		    jsonString += "{\"Status\":\"sucess\"}";
    	    		    rps.setContentType("application/json");
    	    			rps.setCharacterEncoding("UTF-8");
    	    			rps.setHeader("Cache-Control", "no-cache");
    	    			rps.getWriter().write(jsonString);
    	    	}
    	    }else{
    		    hqlString = "update Classmap set score=" + score
    			    + " where coursedesign=" + coursedesignId + " and student="
    			    + sid;
    	
    		    // 获取学生列表
    		    try
    		    {
    			ics.getiClassMapDao().executeHSQL(hqlString);
    		    }
    		    catch (Exception e)
    		    {
    			logger.error(e.getMessage());
    		    }
    		    jsonString += "{\"Status\":\"sucess\"}";
    		    rps.setContentType("application/json");
    			rps.setCharacterEncoding("UTF-8");
    			rps.setHeader("Cache-Control", "no-cache");
    			rps.getWriter().write(jsonString);
    	    }
    	}
    }

    public String studentHomeworkList()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	int homeworkId = Integer.parseInt(res.getParameter("id"));
	int coursedesignId = Integer.parseInt(res
		.getParameter("coursedesignId"));
	hqlString = "from HomeworkAccessoryMap where homework=" + homeworkId;
	lstHam = ihams.getiHomeworkAccessoryMapDao().getListByHSQL(hqlString);
	lsthaex.clear();
	if (lstHam.size() > 0)
	{
	    for (HomeworkAccessoryMap ham : lstHam)
	    {
		HomeworkAccessoryExt hae = new HomeworkAccessoryExt();
		if (ham.getHomeworkState() == 1)
		{
		    hae.setHomeworkState("已提交");
		}
		else if (ham.getHomeworkState() == 2)
		{
		    hae.setHomeworkState("发回修改");
		}
		else if (ham.getHomeworkState() == 3)
		{
		    hae.setHomeworkState("通过");
		}

		hae.setHam(ham);
		studentExt sExt = new studentExt();
		hqlString = "from Student where studentCode='"
			+ ham.getStudent().getStudentCode() + "'";
		Student student = iss.getIStudentDao().getListByHSQL(hqlString)
			.get(0);
		sExt.setStudent(student);
		sExt.setSex(student.getSex() == 1 ? "男" : "女");
		hqlString = "select  sg.groupName from studentgrou sg join group_studen_map gsm on sg.id=gsm.fk_group and sg.fk_crousePlan="
			+ coursedesignId
			+ " and gsm.fk_student="
			+ student.getId();
		@SuppressWarnings({ "rawtypes" })
		List lstTemp = ics.getiClassMapDao()
			.executeSQL(hqlString, null);
		if (lstTemp.size() > 0)
		{
		    sExt.setGroup((String) lstTemp.get(0));
		}
		hae.setStexExt(sExt);
		lsthaex.add(hae);
	    }
	}
	return "studentHomeworkList";
    }

    public void updateHomewrokState()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	int id = Integer.parseInt(res.getParameter("id"));
	int val = Integer.parseInt(res.getParameter("val"));
	hqlString = "update HomeworkAccessoryMap set homeworkState=" + val
		+ " where id=" + id;
	ihams.getiHomeworkAccessoryMapDao().executeHSQL(hqlString);

    }

    public String userInfoInit()
    {

	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = res.getSession();

	if (session.getAttribute("teacher") != null)
	{
	    teacher = (Teacher) session.getAttribute("teacher");
	}
	return "userInfoInit";
    }

    public void userInfo() throws NoSuchAlgorithmException, IOException
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = res.getSession();

	if (session.getAttribute("teacher") != null)
	{
	    Teacher tTeacher = (Teacher) session.getAttribute("teacher");
	    tTeacher.setIdCode(teacher.getIdCode());
	    tTeacher.setPhone(teacher.getPhone());
	    its.getITeacherDao().update(tTeacher);
	    if (res.getParameterMap().containsKey("pwd"))
	    {
		String pwdString = replaceBlank(res.getParameter("pwd"));
		hqlString = "update Sso set pwd='"
			+ GetCriptString.getString(pwdString, ECrypType.MD5,
				ECrypMethod.ENCODE) + "' where userCode='"
			+ tTeacher.getWorkCode() + "'";
		isss.getISsoDao().executeHSQL(hqlString);
	    }

	    rps.sendRedirect("login_logout.action");

	}
    }

    public String messageInit()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpSession session = res.getSession();
	Sso ts = (Sso) session.getAttribute("user");
	hqlString = "from SsoMessageMap where message in (select id from Message where type=1 or type=2  order by isTop ,sendTime desc) and sso="
		+ ts.getId();
	lstSmm = imss.getIMessageSsoDao().getListByHSQL(hqlString);
	hqlString = "update SsoMessageMap set isRead=1 where sso=" + ts.getId();
	imss.getIMessageSsoDao().executeHSQL(hqlString);
	return "messageInit";
    }

    public void addMessage() throws SecurityException, IllegalStateException,
	    RollbackException, HeuristicMixedException,
	    HeuristicRollbackException, SystemException, IOException
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = res.getSession();
	/*
	 * teacher = (Teacher) session.getAttribute("teacher"); // 获取教师所教课程
	 * hqlString = "from CoursedesignTeacherMap where teacher=" +
	 * teacher.getId() + " order by id desc limit 0,20"; lstCdtMaps =
	 * ictmService.getIClassTeacherMapDao().getListByHSQL( hqlString);
	 */
	Sso tSso = (Sso) session.getAttribute("user");
	Teacher teacher = its.getITeacherDao().getListByHSQL("from teacher where work_code='" + tSso.getUserCode()+"'").get(0);
	message.setSendTime(new Date());
	message.setIsTop(false);
	message.setTeacher(teacher);
	message.setType((short) 4);
	message = imsService.getIMessageDao().add(message);
	lstSmm.clear();
	hqlString = "from Classmap where coursedesign=" + coursedesign.getId();
	lClassmaps = ics.getiClassMapDao().getListByHSQL(hqlString);
	for (Classmap cm : lClassmaps)
	{
	    hqlString = "from Sso where userCode='"
		    + cm.getStudent().getStudentCode() + "'";
	    Sso tSso2 = new Sso();
	    tSso2 = isss.getISsoDao().getListByHSQL(hqlString).get(0);
	    SsoMessageMap sMap = new SsoMessageMap();
	    sMap.setIsRead(false);
	    sMap.setMessage(message);
	    sMap.setSso(tSso2);
	    lstSmm.add(sMap);

	}
	rps.sendRedirect("teacher_messageInit.action");
    }

    public void delMsg()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpSession session = res.getSession();
	int id = Integer.parseInt(res.getParameter("id"));
	hqlString = "delete from SsoMessageMap where id=" + id;
	imss.getIMessageSsoDao().executeHSQL(hqlString);
    }

    public String message()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = res.getSession();
	teacher = (Teacher) session.getAttribute("teacher");
	// 获取教师所教课程
	hqlString = "from CoursedesignTeacherMap where teacher="
		+ teacher.getId() + " order by id desc limit 0,20";
	lstCdtMaps = ictmService.getIClassTeacherMapDao().getListByHSQL(
		hqlString);
	return "message";
    }

    public void upload() throws IOException
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	int coursedesignId = Integer.parseInt(res.getParameter("id"));
	if (coursedesign == null){
		coursedesign = new Coursedesign();
	}
	coursedesign.setId(coursedesignId);
	String absolutePath = ServletActionContext.getServletContext()
		.getRealPath(""); // 获取项目根路径
	// 文件路径
	String path = absolutePath + "/" + this.savePath + "/";
	// 创建路径，如果目录不存在，则创建
	File file = new File(path);
	if (!file.exists())
	{
	    file.mkdirs();
	}
	// 文件路径+文件名
	String name = UUID.randomUUID().toString();
	String oraName = this.getFileuploadFileName().substring(0,
		getFileuploadFileName().lastIndexOf("."));
	String ext = this.getFileuploadFileName().substring(oraName.length());

	path += name + ext;
	// 1.构建输入流
	FileInputStream fis = new FileInputStream(fileupload);

	// 2.构建输出流
	FileOutputStream fos = new FileOutputStream(path);
	// 3.通过字节写入输出流
	try
	{
	    byte[] buf = new byte[1024];
	    int len = 0;
	    while ((len = fis.read(buf)) > 0)
	    {
		fos.write(buf, 0, len);
	    }

	    Accessory accessory = new Accessory();
	    File tempf = new File(path);
	    if (tempf.exists())
	    {

		accessory.setFileSize(tempf.length() / 1024);
	    }
	    accessory.setName(name);
	    accessory.setOraName(oraName);
	    accessory.setFilePath(this.savePath);
	    accessory.setFileType(ext);

	    accessory.setCreateTime(new Date());
	    accessory.setIsCourseWare(true);
	    accessory = ias.getAccessoryDao().add(accessory);
	    CourseCoursewareMap ccwMap = new CourseCoursewareMap();
	    ccwMap.setAccessory(accessory);
	    ccwMap.setCoursedesign(coursedesign);
	    HttpSession session = res.getSession();
	    teacher = (Teacher) session.getAttribute("teacher");
	    ccwMap.setTeacher(teacher);
	    iacms.getIAccocessoryCourseMapDao().add(ccwMap);

	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	finally
	{
	    fis.close();
	    fos.close();
	    rps.sendRedirect("teacher_courseWareInit.action?coursedesignId="
		    + coursedesignId);
	}

    }

    /**
     * 检测是否有权利访问
     * 
     * @param res
     * @param courseId
     */
    private boolean checkRight(HttpServletRequest res, int coursid)
    {
	boolean hasRight = true;

	HttpSession session = res.getSession();
	if (session.getAttribute("teacher") != null)
	{
	    teacher = (Teacher) session.getAttribute("teacher");
	    hqlString = "from CoursedesignTeacherMap where teacher="
		    + teacher.getId() + " and coursedesign=" + coursid;
	    lstCdtMaps = ictmService.getIClassTeacherMapDao().getListByHSQL(
		    hqlString);
	    if (lstCdtMaps.size() == 0)// 判断是否有权利访问
	    {
		hasRight = false;

	    }
	}
	return hasRight;
    }

    public String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    
    /**
     * @return the ctm
     */
    public CoursedesignTeacherMap getCtm()
    {
	return ctm;
    }

    /**
     * @param ctm
     *            the ctm to set
     */
    public void setCtm(CoursedesignTeacherMap ctm)
    {
	this.ctm = ctm;
    }

    /**
     * @return the lstCdtMaps
     */
    public List<CoursedesignTeacherMap> getLstCdtMaps()
    {
	return lstCdtMaps;
    }

    /**
     * @return the ias
     */
    public IAccessoryService getIas()
    {
	return ias;
    }

    /**
     * @param ias
     *            the ias to set
     */
    public void setIas(IAccessoryService ias)
    {
	this.ias = ias;
    }

    /**
     * @param lstCdtMaps
     *            the lstCdtMaps to set
     */
    public void setLstCdtMaps(List<CoursedesignTeacherMap> lstCdtMaps)
    {
	this.lstCdtMaps = lstCdtMaps;
    }

    /**
     * @return the ictmService
     */
    public IClassTeacherMapService getIctmService()
    {
	return ictmService;
    }

    /**
     * @return the isms
     */
    public ISgroupMapService getIsms()
    {
	return isms;
    }

    /**
     * @param isms
     *            the isms to set
     */
    public void setIsms(ISgroupMapService isms)
    {
	this.isms = isms;
    }

    /**
     * @param ictmService
     *            the ictmService to set
     */
    public void setIctmService(IClassTeacherMapService ictmService)
    {
	this.ictmService = ictmService;
    }

    /**
     * @return the ics
     */
    public IClassmapService getIcs()
    {
	return ics;
    }

    /**
     * @param ics
     *            the ics to set
     */
    public void setIcs(IClassmapService ics)
    {
	this.ics = ics;
    }

    /**
     * @return the lsthaex
     */
    public List<HomeworkAccessoryExt> getLsthaex()
    {
	return lsthaex;
    }

    /**
     * @param lsthaex
     *            the lsthaex to set
     */
    public void setLsthaex(List<HomeworkAccessoryExt> lsthaex)
    {
	this.lsthaex = lsthaex;
    }

    /**
     * @return the lstMc
     */
    public List<myCourse> getLstMc()
    {
	return lstMc;
    }

    /**
     * @param lstMc
     *            the lstMc to set
     */
    public void setLstMc(List<myCourse> lstMc)
    {
	this.lstMc = lstMc;
    }

    /**
     * @return the isgs
     */
    public IStudentGroupService getIsgs()
    {
	return isgs;
    }

    /**
     * @param isgs
     *            the isgs to set
     */
    public void setIsgs(IStudentGroupService isgs)
    {
	this.isgs = isgs;
    }

    /**
     * @return the fileupload
     */
    public File getFileupload()
    {
	return fileupload;
    }

    /**
     * @param fileupload
     *            the fileupload to set
     */
    public void setFileupload(File fileupload)
    {
	this.fileupload = fileupload;
    }

    /**
     * @return the fileuploadFileSize
     */
    public float getFileuploadFileSize()
    {
	return fileuploadFileSize;
    }

    /**
     * @param fileuploadFileSize
     *            the fileuploadFileSize to set
     */
    public void setFileuploadFileSize(float fileuploadFileSize)
    {
	this.fileuploadFileSize = fileuploadFileSize;
    }

    /**
     * @return the fileuploadFileName
     */
    public String getFileuploadFileName()
    {
	return fileuploadFileName;
    }

    /**
     * @param fileuploadFileName
     *            the fileuploadFileName to set
     */
    public void setFileuploadFileName(String fileuploadFileName)
    {
	this.fileuploadFileName = fileuploadFileName;
    }

    /**
     * @return the fileuploadContentType
     */
    public String getFileuploadContentType()
    {
	return fileuploadContentType;
    }

    /**
     * @param fileuploadContentType
     *            the fileuploadContentType to set
     */
    public void setFileuploadContentType(String fileuploadContentType)
    {
	this.fileuploadContentType = fileuploadContentType;
    }

    /**
     * @return the savePath
     */
    public String getSavePath()
    {
	return savePath;
    }

    /**
     * @param savePath
     *            the savePath to set
     */
    public void setSavePath(String savePath)
    {
	this.savePath = savePath;
    }

    /**
     * @return the icps
     */
    public ICoursePlanService getIcps()
    {
	return icps;
    }

    /**
     * @param icps
     *            the icps to set
     */
    public void setIcps(ICoursePlanService icps)
    {
	this.icps = icps;
    }

    /**
     * @return the studentCount
     */
    public int getStudentCount()
    {
	return studentCount;
    }

    /**
     * @return the lstccwm
     */
    public List<CourseCoursewareMap> getLstccwm()
    {
	return lstccwm;
    }

    /**
     * @param lstccwm
     *            the lstccwm to set
     */
    public void setLstccwm(List<CourseCoursewareMap> lstccwm)
    {
	this.lstccwm = lstccwm;
    }

    /**
     * @param studentCount
     *            the studentCount to set
     */
    public void setStudentCount(int studentCount)
    {
	this.studentCount = studentCount;
    }

    /**
     * @return the coursedesign
     */
    public Coursedesign getCoursedesign()
    {
	return coursedesign;
    }

    /**
     * @param coursedesign
     *            the coursedesign to set
     */
    public void setCoursedesign(Coursedesign coursedesign)
    {
	this.coursedesign = coursedesign;
    }

    /**
     * @return the iss
     */
    public IStudentService getIss()
    {
	return iss;
    }

    /**
     * @param iss
     *            the iss to set
     */
    public void setIss(IStudentService iss)
    {
	this.iss = iss;
    }

    /**
     * @return the lstStudents
     */
    public List<Student> getLstStudents()
    {
	return lstStudents;
    }

    /**
     * @param lstStudents
     *            the lstStudents to set
     */
    public void setLstStudents(List<Student> lstStudents)
    {
	this.lstStudents = lstStudents;
    }

    /**
     * @return the iacms
     */
    public IAccessoryCourseMapService getIacms()
    {
	return iacms;
    }

    /**
     * @param iacms
     *            the iacms to set
     */
    public void setIacms(IAccessoryCourseMapService iacms)
    {
	this.iacms = iacms;
    }

    /**
     * @return the ihs
     */
    public IHomeworkService getIhs()
    {
	return ihs;
    }

    /**
     * @param ihs
     *            the ihs to set
     */
    public void setIhs(IHomeworkService ihs)
    {
	this.ihs = ihs;
    }

    /**
     * @return the homework
     */
    public Homework getHomework()
    {
	return homework;
    }

    /**
     * @param homework
     *            the homework to set
     */
    public void setHomework(Homework homework)
    {
	this.homework = homework;
    }

    /**
     * @return the lstHw
     */
    public List<Homework> getLstHw()
    {
	return lstHw;
    }

    /**
     * @param lstHw
     *            the lstHw to set
     */
    public void setLstHw(List<Homework> lstHw)
    {
	this.lstHw = lstHw;
    }

    /**
     * @return the lClassmaps
     */
    public List<Classmap> getlClassmaps()
    {
	return lClassmaps;
    }

    /**
     * @param lClassmaps
     *            the lClassmaps to set
     */
    public void setlClassmaps(List<Classmap> lClassmaps)
    {
	this.lClassmaps = lClassmaps;
    }

    /**
     * @return the lstStudentExt
     */
    public List<studentExt> getLstStudentExt()
    {
	return lstStudentExt;
    }

    /**
     * @param lstStudentExt
     *            the lstStudentExt to set
     */
    public void setLstStudentExt(List<studentExt> lstStudentExt)
    {
	this.lstStudentExt = lstStudentExt;
    }

    /**
     * @return the isHidden
     */
    public String getIsHidden()
    {
	return isHidden;
    }

    /**
     * @param isHidden
     *            the isHidden to set
     */
    public void setIsHidden(String isHidden)
    {
	this.isHidden = isHidden;
    }

    /**
     * @return the lstHam
     */
    public List<HomeworkAccessoryMap> getLstHam()
    {
	return lstHam;
    }

    /**
     * @param lstHam
     *            the lstHam to set
     */
    public void setLstHam(List<HomeworkAccessoryMap> lstHam)
    {
	this.lstHam = lstHam;
    }

    /**
     * @return the ihams
     */
    public IHomeworkAccessoryMapService getIhams()
    {
	return ihams;
    }

    /**
     * @param ihams
     *            the ihams to set
     */
    public void setIhams(IHomeworkAccessoryMapService ihams)
    {
	this.ihams = ihams;
    }

    /**
     * @return the its
     */
    public ITeachereService getIts()
    {
	return its;
    }

    /**
     * @param its
     *            the its to set
     */
    public void setIts(ITeachereService its)
    {
	this.its = its;
    }

    /**
     * @return the isss
     */
    public ISsoService getIsss()
    {
	return isss;
    }

    /**
     * @param isss
     *            the isss to set
     */
    public void setIsss(ISsoService isss)
    {
	this.isss = isss;
    }

    /**
     * @return the teacher
     */
    public Teacher getTeacher()
    {
	return teacher;
    }

    /**
     * @param teacher
     *            the teacher to set
     */
    public void setTeacher(Teacher teacher)
    {
	this.teacher = teacher;
    }

    /**
     * @return the imss
     */
    public IMessageSsoService getImss()
    {
	return imss;
    }

    /**
     * @param imss
     *            the imss to set
     */
    public void setImss(IMessageSsoService imss)
    {
	this.imss = imss;
    }

    /**
     * @return the lstSmm
     */
    public List<SsoMessageMap> getLstSmm()
    {
	return lstSmm;
    }

    /**
     * @param lstSmm
     *            the lstSmm to set
     */
    public void setLstSmm(List<SsoMessageMap> lstSmm)
    {
	this.lstSmm = lstSmm;
    }

    /**
     * @return the message
     */
    public Message getMessage()
    {
	return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(Message message)
    {
	this.message = message;
    }

    /**
     * @return the imsService
     */
    public IMessageService getImsService()
    {
	return imsService;
    }

    /**
     * @param imsService
     *            the imsService to set
     */
    public void setImsService(IMessageService imsService)
    {
	this.imsService = imsService;
    }

}
