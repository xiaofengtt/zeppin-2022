/**
 * 
 */
package com.zeppin.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.util.Streams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.opensymphony.xwork2.ActionSupport;
import com.zeppin.entiey.Classmap;
import com.zeppin.entiey.Coursedesign;
import com.zeppin.entiey.CoursedesignTeacherMap;
import com.zeppin.entiey.Courseschedule;
import com.zeppin.entiey.DicAcademy;
import com.zeppin.entiey.HomeworkAccessoryExt;
import com.zeppin.entiey.HomeworkAccessoryMap;
import com.zeppin.entiey.Student;
import com.zeppin.entiey.Subject;
import com.zeppin.entiey.Teacher;
import com.zeppin.entiey.studentExt;
import com.zeppin.service.IClassTeacherMapService;
import com.zeppin.service.IClassmapService;
import com.zeppin.service.ICoursePlanService;
import com.zeppin.service.ICourseScheduleService;
import com.zeppin.service.IHomeworkAccessoryMapService;
import com.zeppin.service.IStudentService;
import com.zeppin.util.cryptogram.DownloadZipFiles;

/**
 * @author suijing
 * 
 */
public class courseAction extends ActionSupport
{
    static Logger logger = LogManager.getLogger(baseDataAction.class);
    String title;// 页面标题
    String jsonString = "";
    int row;
    int total;// 总页数
    int page;// 第几页
    BigInteger records;// 总条数
    int id;
    String hqlString = "";// hql语句
    List objects = new ArrayList();// 参数
    Coursedesign coursedesign = new Coursedesign();// 课程
    Student student = new Student();
    String leaderId;// 组长id
    int studentCount;
    Courseschedule cs = new Courseschedule();// 课程表

    ICoursePlanService icps;// 课程计划操作
    IClassmapService ics;// 学生课程计划操作
    IClassTeacherMapService icms;// 教师课程计划操作
    ICourseScheduleService icss;// 课表操作
    IHomeworkAccessoryMapService ihams;// 作业操作
    IStudentService iss; // 学生操作

    List<CoursedesignTeacherMap> lstCoursedesignTeacherMaps = new ArrayList<CoursedesignTeacherMap>();
    List<Teacher> lstTeachers = new ArrayList<Teacher>();
    List<Classmap> lClassmaps = new ArrayList<Classmap>();
    List<Courseschedule> lstCourseschedules = new ArrayList<Courseschedule>();// 课表list
    List<HomeworkAccessoryMap> lstHam = new ArrayList<HomeworkAccessoryMap>();
    private List<Student> lstStudents = new ArrayList<Student>();// 学生列表
    List<HomeworkAccessoryExt> lsthaex = new ArrayList<HomeworkAccessoryExt>();
    List<studentExt> lstStudentExt = new ArrayList<studentExt>();

    /**
     * 初始化开课计划管理页面
     * 
     * @return
     */
    public String planManageInit()
    {
	return "planManageInit";
    }

    @SuppressWarnings("unchecked")
    public void planManage() throws IOException, ParseException
    {

	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	String tableNameString = "coursedesign";
	String ObjtableName = "Coursedesign";
	String opt;
	String id;
	// String name;
	Date startTime;
	Subject subject = new Subject();
	DicAcademy academy = new DicAcademy();
	Coursedesign coursedesign = new Coursedesign();
	int weeks;
	int classHours;
	float credit;
	short studyMode;
	String studentDescirpt;
	String remark;
	String courseName;
	if (res.getParameterMap().containsKey("opt"))
	{
	    opt = res.getParameter("opt");
	    // 初始化
	    if (opt.equals("init"))
	    {
		hqlString = "select count(*) from " + tableNameString + " where status=1";
		page = Integer.parseInt(res.getParameter("page"));
		row = Integer.parseInt(res.getParameter("rows"));
		records = (BigInteger) icps.getICoursePlanDao()
			.executeSQL(hqlString, null).listIterator().next();
		total = records.intValue() / row + 1;
		String sidxString = res.getParameter("sidx");
		String sord = res.getParameter("sord");

		// 查询操作
		if (res.getParameter("_search").equals("true"))
		{
		    hqlString = "from " + ObjtableName + " Where status=1 ";

		    if (!res.getParameter("dicAcademy").equals("all"))
		    {
			int dc = Integer.parseInt(res
				.getParameter("dicAcademy"));
			hqlString += " and dicAcademy =" + dc;

		    }
		    if (!res.getParameter("subject").equals("all"))
		    {
			int dc = Integer.parseInt(res.getParameter("subject"));
			hqlString += " and subject =" + dc;

		    }

		    if (res.getParameterMap().containsKey("startTime"))
		    {

			if (!res.getParameter("startTime").equals(null))
			{
			    // SimpleDateFormat sf = new SimpleDateFormat(
			    // "yyyy-MM-dd HH:mm:ss");
			    hqlString += " and startTime ='"
				    + res.getParameter("startTime") + "'";
			}

		    }
		    if (res.getParameterMap().containsKey("courseName"))
		    {

			if (!res.getParameter("courseName").equals(null))
			{
			    courseName = res.getParameter("courseName");
			    hqlString += " and courseName=" + courseName;
			}

		    }
		    if (res.getParameterMap().containsKey("weeks"))
		    {

			if (!res.getParameter("weeks").equals(null))
			{
			    weeks = Integer.parseInt(res.getParameter("weeks"));
			    hqlString += " and weeks=" + weeks;
			}

		    }
		    if (res.getParameterMap().containsKey("classHour"))
		    {
			classHours = Integer.parseInt(res
				.getParameter("classHour"));

			hqlString += " and classHour=" + classHours;

		    }
		    if (res.getParameterMap().containsKey("credit"))
		    {

			credit = Integer.parseInt(res.getParameter("credit"));
			hqlString += " and credit=" + credit;

		    }

		    if (!res.getParameter("studyMode").equals("all"))
		    {
			hqlString += " and studyMode="
				+ res.getParameter("studyMode");
		    }

		    if (res.getParameterMap().containsKey("studentDescirpt"))
		    {

			studentDescirpt = res.getParameter("studentDescirpt");
			if (studentDescirpt != null)
			{
			    hqlString += " and studentDescirpt like '%"
				    + studentDescirpt + "%'";
			}

		    }

		    if (res.getParameterMap().containsKey("remark"))
		    {
			remark = res.getParameter("remark");
			if (!remark.equals(null))
			{
			    hqlString += " and remark like '%" + remark + "%'";

			}

		    }
		}
		// 初始化操作
		else
		{
		    hqlString = "from " + ObjtableName + " where status=1 order by "
			    + sidxString + " " + sord;

		}

		List<Coursedesign> lstcds = icps.getICoursePlanDao()
			.getListForPage(hqlString, page, row);
		jsonString = "";
		jsonString += "{";
		jsonString += "\"page\":\"" + page + "\",";
		jsonString += "\"total\":\"" + total + "\",";
		jsonString += "\"records\":\"" + records + "\",";
		jsonString += "\"rows\":[";
		for (Coursedesign c : lstcds)
		{
		    String smode = (c.getStudyMode() == 1 ? "必修" : "选修");

		    jsonString += "{\"id\":\"" + c.getId() + "\",";
		    jsonString += "\"cell\":["
			    + "\""
			    + c.getCourseName()
			    + "\",\""
			    + c.getDicAcademy().getName()
			    + "\",\""
			    + c.getSubject().getName()
			    + "\",\""
			    + DateFormat.getDateInstance(DateFormat.DEFAULT)
				    .format(c.getStartTime()) + "\",\""
			    + c.getWeeks() + "\",\"" + c.getClassHour()
			    + "\",\"" + c.getCredit() + "\",\"" + smode
			    + "\",\"" + c.getStudentDescirpt() + "\",\""
			    + c.getRemark() + "\"]},";

		}
		if (lstcds.size() > 0)
		    jsonString = jsonString.substring(0,
			    jsonString.length() - 1);
		jsonString += "]}";

		rps.setContentType("application/json");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);

	    }

	    // 添加新项目
	    else if (opt.equals("add") || opt.equals("edit"))
	    {

		academy.setId(Integer.parseInt(res.getParameter("dicAcademy")));
		subject.setId(Integer.parseInt(res.getParameter("subject")));
		courseName = replaceBlank(res.getParameter("courseName"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		startTime = (Date) sdf.parse(res.getParameter("startTime"));
		weeks = Short.parseShort(res.getParameter("weeks"));
		classHours = Short.parseShort(res.getParameter("classHour"));
		credit = Float.parseFloat(res.getParameter("credit"));
		studyMode = Short.parseShort(res.getParameter("studyMode"));
		studentDescirpt = null;
		if (res.getParameterMap().containsKey("studentDescirpt"))
		{
		    studentDescirpt = replaceBlank(res.getParameter("studentDescirpt"));
		}
		remark = replaceBlank(res.getParameter("remark"));
		coursedesign.setDicAcademy(academy);
		coursedesign.setSubject(subject);
		coursedesign.setStartTime(startTime);
		coursedesign.setWeeks(weeks);
		coursedesign.setClassHour(classHours);
		coursedesign.setCredit(credit);
		coursedesign.setStudyMode(studyMode);
		coursedesign.setStudentDescirpt(studentDescirpt);
		coursedesign.setRemark(remark);
		coursedesign.setCourseName(courseName);
		short status = 1;
		coursedesign.setStatus(status);
		if (opt.equals("add"))
		{

		    icps.getICoursePlanDao().add(coursedesign);

		}
		else
		{
		    id = res.getParameter("id");
		    // coursedesign.setId(Integer.parseInt(id));
		    // icps.getICoursePlanDao().update(coursedesign);
		    hqlString = "update "
			    + tableNameString
			    + " set courseName=?,startTime=?,fk_academy=?,fk_course=?,weeks=?,classHour=?,credit=?,studyMode=?,studentDescirpt=?,remark=? where id=?";

		    objects.clear();
		    objects.add(courseName);
		    objects.add((new java.text.SimpleDateFormat(
			    "yyyy-MM-dd HH:mm:ss")).format(startTime));
		    objects.add(academy.getId());
		    objects.add(subject.getId());
		    objects.add(weeks);
		    objects.add(classHours);
		    objects.add(credit);
		    objects.add(studyMode);
		    objects.add(studentDescirpt);
		    objects.add(remark);
		    objects.add(Integer.parseInt(id));

		    try
		    {
			icps.getICoursePlanDao().executeSQLUpdate(hqlString,
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
		    hqlString = "update " + tableNameString
			    + " set status=0 where id=?";
		    objects.clear();
		    objects.add(Integer.parseInt(id));
		    icps.getICoursePlanDao().executeSQLUpdate(hqlString,
			    objects.toArray());

		}

	    }

	}
    }

    public String listManageInit()
    {
	return "planManageInit";
    }

    public String studentManageInit()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();

	if (res.getParameterMap().containsKey("id"))
	{
	    id = Integer.parseInt(res.getParameter("id"));
	    coursedesign = icps.getICoursePlanDao().get(id);// 获取课程计划信息
	    hqlString = "from Classmap where coursedesign=" + id;
	    // 获取学生列表
	    lClassmaps = ics.getiClassMapDao().getListByHSQL(hqlString);
	    lstStudents.clear();
	    studentCount = lClassmaps.size();
	    if (lClassmaps.size() > 0)
	    {

		for (Classmap cm : lClassmaps)
		{
		    lstStudents.add(cm.getStudent());
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
	    id = Integer.parseInt(res.getParameter("id"));
	    coursedesign.setId(id);

	    String ids[] = res.getParameter("ids[]").split(",");
	    for (String id : ids)
	    {
		Classmap cm = new Classmap();
		Student student = new Student();
		student.setId(Integer.parseInt(id));
		cm.setStudent(student);
		cm.setCoursedesign(coursedesign);
		try
		{
		    ics.getiClassMapDao().add(cm);
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
	    int courseId = Integer.parseInt(res.getParameter("courseId"));
	    hqlString = "delete from Classmap where student=" + id
		    + " and coursedesign=" + courseId;
	    ics.getiClassMapDao().executeHSQL(hqlString);

	}
    }

    public String teacherManageInit()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();

	if (res.getParameterMap().containsKey("id"))
	{
	    id = Integer.parseInt(res.getParameter("id"));
	    coursedesign = icps.getICoursePlanDao().get(id);// 获取课程计划信息
	    hqlString = "from CoursedesignTeacherMap where coursedesign=" + id;
	    // 获取学生列表
	    lstCoursedesignTeacherMaps = icms.getIClassTeacherMapDao()
		    .getListByHSQL(hqlString);
	    lstTeachers.clear();
	    leaderId = "";
	    if (lstCoursedesignTeacherMaps.size() > 0)
	    {

		for (CoursedesignTeacherMap ct : lstCoursedesignTeacherMaps)
		{
		    if (ct.getIsLeader())
		    {
			leaderId = ct.getTeacher().getId().toString();

		    }
		    lstTeachers.add(ct.getTeacher());

		}

	    }

	}

	return "teacherManageInit";
    }

    public void addTeacher()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	if (res.getParameterMap().containsKey("id"))
	{
	    id = Integer.parseInt(res.getParameter("id"));
	    coursedesign.setId(id);

	    String ids[] = res.getParameter("ids[]").split(",");
	    for (String id : ids)
	    {
		CoursedesignTeacherMap ct = new CoursedesignTeacherMap();
		Teacher teacher = new Teacher();
		teacher.setId(Integer.parseInt(id));
		ct.setTeacher(teacher);
		ct.setCoursedesign(coursedesign);
		try
		{
		    icms.getIClassTeacherMapDao().add(ct);
		}
		catch (Exception e)
		{
		    continue;
		}

	    }
	}

    }

    public void removeTeacher()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	if (res.getParameterMap().containsKey("id"))
	{
	    id = Integer.parseInt(res.getParameter("id"));
	    int courseId = Integer.parseInt(res.getParameter("courseId"));
	    hqlString = "delete from CoursedesignTeacherMap where teacher="
		    + id + " and coursedesign=" + courseId;
	    icms.getIClassTeacherMapDao().executeHSQL(hqlString);

	}
    }

    public void leaderTeacher()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	if (res.getParameterMap().containsKey("id"))
	{
	    int courseId = Integer.parseInt(res.getParameter("courseId"));
	    id = Integer.parseInt(res.getParameter("id"));
	    hqlString = "update CoursedesignTeacherMap set isLeader=0 where coursedesign="
		    + courseId;
	    icms.getIClassTeacherMapDao().executeHSQL(hqlString);
	    hqlString = "update CoursedesignTeacherMap set isLeader=1 where coursedesign="
		    + courseId + " and teacher=" + id;
	    icms.getIClassTeacherMapDao().executeHSQL(hqlString);

	}
    }

    public String scheduleManageInit()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = res.getSession();
	String id = null;
	if (res.getParameterMap().containsKey("id")
		|| session.getAttribute("courseId") != null)
	{
	    if (res.getParameterMap().containsKey("id"))
	    {
		id = res.getParameter("id");
		session.setAttribute("courseId", id);
	    }
	    if (session.getAttribute("courseId") != null)
	    {
		id = session.getAttribute("courseId").toString();
	    }
	    coursedesign = icps.getICoursePlanDao().get(Integer.parseInt(id));// 获取课程计划信息

	    hqlString = "from CoursedesignTeacherMap where coursedesign="
		    + Integer.parseInt(id);
	    lstCoursedesignTeacherMaps = icms.getIClassTeacherMapDao()
		    .getListByHSQL(hqlString);
	    hqlString = "from Courseschedule where coursedesign="
		    + Integer.parseInt(id) + " order by startTime";
	    lstCourseschedules = icss.getICourseScheduleDao().getListByHSQL(
		    hqlString);
	}

	return "scheduleManageInit";
    }

    @SuppressWarnings("unchecked")
    public void scheduleManage() throws IOException, ParseException
    {

	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	String tableNameString = "courseschedule";
	String ObjtableName = "Courseschedule";
	String opt;
	String id;

	if (res.getParameterMap().containsKey("opt"))
	{
	    opt = res.getParameter("opt");

	    // 添加新项目
	    if (opt.equals("add"))
	    {
		coursedesign.setId(Integer.parseInt(res.getParameter("id")));
		cs.setCoursedesign(coursedesign);
		icss.getICourseScheduleDao().add(cs);
		cs = null;
		rps.sendRedirect("course_scheduleManageInit.action?id="
			+ res.getParameter("id"));
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
		    icss.getICourseScheduleDao().executeSQLUpdate(hqlString,
			    objects.toArray());

		}

	    }
	}

    }

    public String homeworkInit()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = res.getSession();
	int id;
	if (res.getParameterMap().containsKey("id")
		|| session.getAttribute("courseId") != null)
	{
	    id = Integer.parseInt(res.getParameter("id"));
	    hqlString = "from HomeworkAccessoryMap where  homework in (select id from Homework where isEndWork=1 and coursedesign="
		    + id + ")";
	    lstHam = ihams.getiHomeworkAccessoryMapDao().getListByHSQL(
		    hqlString);
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
		    Student student = iss.getIStudentDao()
			    .getListByHSQL(hqlString).get(0);
		    sExt.setStudent(student);
		    sExt.setSex(student.getSex() == 1 ? "男" : "女");
		    hqlString = "select  sg.groupName from studentgrou sg join group_studen_map gsm on sg.id=gsm.fk_group and sg.fk_crousePlan="
			    + id + " and gsm.fk_student=" + student.getId();
		    @SuppressWarnings({ "unused", "rawtypes" })
		    List lstTemp = ics.getiClassMapDao().executeSQL(hqlString,
			    null);
		    if (lstTemp.size() > 0)
		    {
			sExt.setGroup((String) lstTemp.get(0));
		    }
		    hae.setStexExt(sExt);
		    lsthaex.add(hae);
		}
	    }

	}
	return "homeworkInit";
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

    public String scoreInit()
    {

	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = res.getSession();
	int id;
	if (res.getParameterMap().containsKey("id")
		|| session.getAttribute("courseId") != null)
	{
	    id = Integer.parseInt(res.getParameter("id"));
	    hqlString = "from Classmap where coursedesign=" + id;
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
			    + id
			    + " and gsm.fk_student="
			    + cm.getStudent().getId();
		    @SuppressWarnings({ "unused", "rawtypes" })
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
	return "scoreInit";
    }
    
    public void homeworkDownload() throws Exception{
    	HttpServletRequest res = ServletActionContext.getRequest();
    	HttpServletResponse rps = ServletActionContext.getResponse();
    	if (res.getParameterMap().containsKey("courseId"))
    	{
    		int coursedesignId = Integer.parseInt(res.getParameter("courseId"));
    		String absolutePath = ServletActionContext.getServletContext().getRealPath(""); 
    		hqlString = "select s.name as sname, s.studentCode , a.oraName , a.fileType , a.filePath , a.name "
    				+ " from homework_accessory_map ham , homework h , student s , accessory a "
    				+ " where ham.homeworkId=h.id and ham.userId = s.id and ham.accessoryId = a.id "
    				+ " and h.isEndWork=1 and h.fk_courese = " + coursedesignId;
    		List resultList = ics.getiClassMapDao().executeSQL(hqlString, null);
    		BufferedInputStream inputStream;
			BufferedOutputStream outputStream;
			String fileName = "homework" + (int)(Math.random() * 1000);
    		for (int i = 0; i < resultList.size(); i++) {
    			Object[] obj = (Object[]) resultList.get(i);//[ s.name , s.studentCode , a.oraName , a.fileType , a.filePath , a.name ]
    			String oldPath = absolutePath + "/" + obj[4].toString() + "/" + obj[5].toString() + obj[3].toString();
    			String newPath = absolutePath + "/" + fileName + "/" + obj[1].toString() + obj[0].toString() + "/" + obj[2].toString() + obj[3].toString();
    			File newFile = new File(absolutePath + "/" + fileName + "/" + obj[1].toString() + obj[0].toString() + "/");
    			if (!newFile.exists()) {
    				newFile.mkdirs();
				}
    			inputStream = new BufferedInputStream(new FileInputStream(oldPath));
				outputStream = new BufferedOutputStream(new FileOutputStream(newPath));
				Streams.copy(inputStream, outputStream, true);
				inputStream.close();
				outputStream.flush();
				outputStream.close();
    		}
    		
    		File zipFile = new File(absolutePath + "/" +fileName + ".zip");
    		FileOutputStream fous = new FileOutputStream(zipFile); 
    		ZipOutputStream zipOut  = new ZipOutputStream(fous);
    		DownloadZipFiles.zip(zipOut , new File(absolutePath + "/" +fileName), "");
            zipOut.close();
            fous.close();
    		 
    		rps = DownloadZipFiles.downloadZip(zipFile,rps);
    		DownloadZipFiles.deleteDir(zipFile);
    		DownloadZipFiles.deleteDir(new File(absolutePath + "/"+fileName));
    	}
    }
    
    public void scoreDownload() throws IOException{
    	HttpServletRequest res = ServletActionContext.getRequest();
    	HttpServletResponse rps = ServletActionContext.getResponse();
    	if (res.getParameterMap().containsKey("courseId"))
    	{
    		int coursedesignId = Integer.parseInt(res.getParameter("courseId"));
    		hqlString = "select distinct s.studentCode , s.name , s.sex , dm.name as major, s.classes , g.groupName , s.phone , c.score " 
    					+ " from classmap c, coursedesign cd, dic_major dm , student s left join group_studen_map gsm  on gsm.fk_student = s.id left join studentgrou g on  g.id = gsm.fk_group "
    					+ " where s.id = c.fk_studenteId and cd.id = c.fk_courseDesignId and dm.id = s.fk_major and cd.id =" 
    					+ coursedesignId; 
    		List resultList = ics.getiClassMapDao().executeSQL(hqlString, null);
    		 
    		HSSFWorkbook wb = new HSSFWorkbook();
    		HSSFSheet s = wb.createSheet();
    		wb.setSheetName(0, "成绩单");
    		HSSFRow row = s.createRow(0);
    		String[] title = { "学号" , "姓名" , "性别" , "专业"  , "班级" , "分组" , "联系方式" , "分数"};
    		for (int j = 0; j < title.length; j++) {
    			row.createCell(j).setCellValue(title[j]);
    		}
    		int t = 0;
    		for (int i = 0; i < resultList.size(); i++) {
    			Object[] obj = (Object[]) resultList.get(i);
    			t++;
    			row = s.createRow(t);
    			if (obj[2].toString().equals("1")){
    				obj[2] = "男";
    			}else{
    				obj[2] = "女";
    			}
    			
    			for (int j = 0; j < obj.length; j++) {
    				if (obj[j] != null){
    					row.createCell(j).setCellValue(obj[j].toString());
    				}else{
    					row.createCell(j).setCellValue("");
    				}
    			}
    		}
    		rps.setContentType("application/vnd.ms-excel");
    		rps.setHeader("Content-disposition",
    				"attachment;filename=scoreList.xls");
    		OutputStream ouputStream = rps.getOutputStream();
    		wb.write(ouputStream);
    	}
    }
    
    
    public void scoreSubmit()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = res.getSession();

	if (res.getParameterMap().containsKey("courseId"))
	{
	    int coursedesignId = Integer.parseInt(res.getParameter("courseId"));

	    hqlString = "update Coursedesign set scoreState=2 where id="
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
     * @return the student
     */
    public Student getStudent()
    {
	return student;
    }

    /**
     * @param student
     *            the student to set
     */
    public void setStudent(Student student)
    {
	this.student = student;
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
     * @return the icms
     */
    public IClassTeacherMapService getIcms()
    {
	return icms;
    }

    /**
     * @param icms
     *            the icms to set
     */
    public void setIcms(IClassTeacherMapService icms)
    {
	this.icms = icms;
    }

    /**
     * @return the lstCoursedesignTeacherMaps
     */
    public List<CoursedesignTeacherMap> getLstCoursedesignTeacherMaps()
    {
	return lstCoursedesignTeacherMaps;
    }

    /**
     * @param lstCoursedesignTeacherMaps
     *            the lstCoursedesignTeacherMaps to set
     */
    public void setLstCoursedesignTeacherMaps(
	    List<CoursedesignTeacherMap> lstCoursedesignTeacherMaps)
    {
	this.lstCoursedesignTeacherMaps = lstCoursedesignTeacherMaps;
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
     * @return the lstTeachers
     */
    public List<Teacher> getLstTeachers()
    {
	return lstTeachers;
    }

    /**
     * @param lstTeachers
     *            the lstTeachers to set
     */
    public void setLstTeachers(List<Teacher> lstTeachers)
    {
	this.lstTeachers = lstTeachers;
    }

    /**
     * @return the leaderId
     */
    public String getLeaderId()
    {
	return leaderId;
    }

    /**
     * @param leaderId
     *            the leaderId to set
     */
    public void setLeaderId(String leaderId)
    {
	this.leaderId = leaderId;
    }

    /**
     * @return the studentCount
     */
    public int getStudentCount()
    {
	return studentCount;
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
     * @return the cs
     */
    public Courseschedule getCs()
    {
	return cs;
    }

    /**
     * @param cs
     *            the cs to set
     */
    public void setCs(Courseschedule cs)
    {
	this.cs = cs;
    }

    /**
     * @return the lstCourseschedules
     */
    public List<Courseschedule> getLstCourseschedules()
    {
	return lstCourseschedules;
    }

    /**
     * @param lstCourseschedules
     *            the lstCourseschedules to set
     */
    public void setLstCourseschedules(List<Courseschedule> lstCourseschedules)
    {
	this.lstCourseschedules = lstCourseschedules;
    }

    /**
     * @return the icss
     */
    public ICourseScheduleService getIcss()
    {
	return icss;
    }

    /**
     * @param icss
     *            the icss to set
     */
    public void setIcss(ICourseScheduleService icss)
    {
	this.icss = icss;
    }

    /**
     * @return the lstHam
     */
    public List<HomeworkAccessoryMap> getLstHam()
    {
	return lstHam;
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
     * @param lstHam
     *            the lstHam to set
     */
    public void setLstHam(List<HomeworkAccessoryMap> lstHam)
    {
	this.lstHam = lstHam;
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

}
