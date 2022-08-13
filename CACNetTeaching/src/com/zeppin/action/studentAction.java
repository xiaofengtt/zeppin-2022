/**
 * 
 */
package com.zeppin.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zeppin.entiey.Accessory;
import com.zeppin.entiey.AccessoryExt;
import com.zeppin.entiey.Classmap;
import com.zeppin.entiey.CourseCoursewareMap;
import com.zeppin.entiey.Coursedesign;
import com.zeppin.entiey.CoursedesignTeacherMap;
import com.zeppin.entiey.Courseschedule;
import com.zeppin.entiey.Homework;
import com.zeppin.entiey.HomeworkAccessoryMap;
import com.zeppin.entiey.Sso;
import com.zeppin.entiey.SsoMessageMap;
import com.zeppin.entiey.Student;
import com.zeppin.entiey.Teacher;
import com.zeppin.entiey.homeworkExt;
import com.zeppin.entiey.myCourse;
import com.zeppin.service.IAccessoryCourseMapService;
import com.zeppin.service.IAccessoryService;
import com.zeppin.service.IClassTeacherMapService;
import com.zeppin.service.IClassmapService;
import com.zeppin.service.ICoursePlanService;
import com.zeppin.service.ICourseScheduleService;
import com.zeppin.service.IHomeworkAccessoryMapService;
import com.zeppin.service.IHomeworkService;
import com.zeppin.service.IMessageService;
import com.zeppin.service.IMessageSsoService;
import com.zeppin.service.ISsoService;
import com.zeppin.service.IStudentService;
import com.zeppin.service.ITeachereService;
import com.zeppin.util.cryptogram.ECrypMethod;
import com.zeppin.util.cryptogram.ECrypType;
import com.zeppin.util.cryptogram.GetCriptString;

/**
 * @author suijing
 * 
 */
public class studentAction extends ActionSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = -5394646595934176949L;
    IClassmapService ics;
    IClassTeacherMapService ictmService;// 课程计划操作
    ICoursePlanService icps;// 课程计划操作
    IAccessoryCourseMapService iacms;// 附件课程map操作
    ICourseScheduleService icss;// 课表操作
    IClassTeacherMapService icms;// 教师课程计划操作
    IHomeworkService ihs;// 布置作业操作
    IHomeworkAccessoryMapService iams;// 学生作业附件操作
    IAccessoryService ias;
    ISsoService iss;
    IStudentService ists;
    ITeachereService its;
    IMessageSsoService imss;// 消息用户操作
    IMessageService imsService;

    Student student = new Student();
    Teacher teacher = new Teacher();
    String hqlString;
    Coursedesign coursedesign = new Coursedesign();
    List<Classmap> lstcm = new ArrayList<Classmap>();
    List<myCourse> lstMc = new ArrayList<myCourse>();// 我的课程表
    List<CourseCoursewareMap> lstccwm = new ArrayList<CourseCoursewareMap>();
    List<CoursedesignTeacherMap> lstCoursedesignTeacherMaps = new ArrayList<CoursedesignTeacherMap>();
    List<Courseschedule> lstCourseschedules = new ArrayList<Courseschedule>();// 课表list
    List<Homework> lstHw = new ArrayList<Homework>(); // 作业list
    List<homeworkExt> lsthwe = new ArrayList<homeworkExt>();
    List<HomeworkAccessoryMap> lstham = new ArrayList<HomeworkAccessoryMap>();
    List<AccessoryExt> lstAe = new ArrayList<AccessoryExt>();
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
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = res.getSession();

	if (session.getAttribute("student") != null)
	{
	    student = (Student) session.getAttribute("student");
	    hqlString = "from Classmap where coursedesign.status=1 and student=" + student.getId()
		    + " order by coursedesign desc";
	    lstcm = ics.getiClassMapDao().getListByHSQL(hqlString);
	    lstMc.clear();
	    for (Classmap cm : lstcm)
	    {
		myCourse tMyCourse = new myCourse();
		tMyCourse.setCoursedesign(cm.getCoursedesign());
		tMyCourse
			.setMode(cm.getCoursedesign().getStudyMode() == 1 ? "必修"
				: "选修");
		hqlString = "from CoursedesignTeacherMap where coursedesign="
			+ cm.getCoursedesign().getId();
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
		    }
		}

		tMyCourse.setTeacheres(tTeaheres);
		// 所在组提取
		hqlString = "select  sg.groupName from studentgrou sg join group_studen_map gsm on sg.id=gsm.fk_group and sg.fk_crousePlan="
			+ cm.getCoursedesign().getId()
			+ " and gsm.fk_student="
			+ cm.getStudent().getId();
		@SuppressWarnings({ "unused", "rawtypes" })
		List lstTemp = ics.getiClassMapDao()
			.executeSQL(hqlString, null);
		if (lstTemp.size() > 0)
		{
		    tMyCourse.setGroupName((String) lstTemp.get(0));
		}

		// 成绩提取
		if (cm.getCoursedesign().getScoreState() == 2)// 判断成绩是否已经发布
		{
		    tMyCourse.setScore(cm.getScore()+"");
		}
		lstMc.add(tMyCourse);
	    }
	}

	return "myClassInit";
    }

    public String coursewareInit()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = res.getSession();
	int courseid = Integer.parseInt(res.getParameter("coursedesignId"));
	if (session.getAttribute("student") != null)
	{
	    if (!checkRight(res, courseid))
	    {

		return "login";
	    }
	    coursedesign = icps.getICoursePlanDao().get(courseid);// 获取课程计划信息
	    hqlString = "from CourseCoursewareMap where coursedesign="
		    + courseid;
	    // 获取学生列表
	    lstccwm = iacms.getIAccocessoryCourseMapDao().getListByHSQL(
		    hqlString);

	}

	return "coursewareInit";
    }

    public String homeworkInit()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = res.getSession();
	if (res.getParameterMap().containsKey("coursedesignId"))
	{
	    student = (Student) session.getAttribute("student");
	    int courseId = Integer.parseInt(res.getParameter("coursedesignId"));
	    coursedesign = icps.getICoursePlanDao().get(courseId);// 获取课程计划信息

	    hqlString = "from CoursedesignTeacherMap where coursedesign="
		    + courseId;
	    lstCoursedesignTeacherMaps = icms.getIClassTeacherMapDao()
		    .getListByHSQL(hqlString);
	    hqlString = "from Courseschedule where coursedesign=" + courseId
		    + " order by startTime";
	    lstCourseschedules = icss.getICourseScheduleDao().getListByHSQL(
		    hqlString);

	    hqlString = "from Homework where coursedesign=" + courseId;
	    // 获取作业列表
	    lstHw = ihs.getIHomeworkDao().getListByHSQL(hqlString);
	    lsthwe.clear();
	    if (lstHw.size() > 0)
	    {
		for (Homework hw : lstHw)
		{
		    homeworkExt homeworkExt = new homeworkExt();
		    homeworkExt.setHomework(hw);
		    homeworkExt.setIsEnd(hw.getIsEndWork() ? "是" : "否");
		    // 以下作业操作
		    Sso sso = (Sso) session.getAttribute("user");

		    // 大作业只能看到自己提交的

		    if (hw.getIsEndWork())
		    {

			hqlString = "from HomeworkAccessoryMap where homework="
				+ hw.getId() + " and student=" + student.getId();

			lstham = iams.getiHomeworkAccessoryMapDao()
				.getListByHSQL(hqlString);
			homeworkExt.getLstAccessoriex().clear();
			if (lstham.size() > 0)
			{
			    for (HomeworkAccessoryMap ham : lstham)
			    {
				AccessoryExt aExt = new AccessoryExt();
				aExt.setUserName(student.getName());
				aExt.setAccessory(ham.getAccessory());
				if (ham.getHomeworkState() == 1)
				    aExt.setHomworkState("已提交");
				else if (ham.getHomeworkState() == 2)
				{
				    aExt.setHomworkState("发回修改");
				}
				else
				{
				    aExt.setHomworkState("已通过");
				}
				homeworkExt.getLstAccessoriex().add(aExt);
			    }

			}
		    }
		    // 其他作业同组可见
		    else
		    {
			hqlString = "select homework_accessory_map.id from homework_accessory_map where homework_accessory_map.homeworkId="
				+ hw.getId()
				+ " and homework_accessory_map.userId in (select student.id from student where student.id in(select distinct group_studen_map.fk_student from group_studen_map where group_studen_map.fk_group in (select group_studen_map.fk_group from group_studen_map where group_studen_map.fk_group in(select studentGrou.id  from studentgrou where studentgrou.fk_crousePlan="
				+ courseId
				+ ") and  group_studen_map.fk_student="
				+ student.getId() + ")))";
			@SuppressWarnings("rawtypes")
			List lsttemp = new ArrayList();
			lsttemp = iacms.getIAccocessoryCourseMapDao()
				.executeSQL(hqlString, null);
			if (lsttemp.size() > 0)
			{
			    for (Object object : lsttemp)
			    {
				HomeworkAccessoryMap ham = (HomeworkAccessoryMap) iams
					.getiHomeworkAccessoryMapDao().get(
						(Integer) object);
				AccessoryExt aExt = new AccessoryExt();
				{
				    hqlString = "from Student where studentCode='"
					    + ham.getStudent().getStudentCode() + "'";
				    student = ists.getIStudentDao()
					    .getListByHSQL(hqlString).get(0);
				    aExt.setUserName(student.getName());
				}

				aExt.setAccessory(ham.getAccessory());
				if (ham.getHomeworkState() == 1)
				    aExt.setHomworkState("已提交");
				else if (ham.getHomeworkState() == 2)
				{
				    aExt.setHomworkState("发回修改");
				}
				else
				{
				    aExt.setHomworkState("已通过");
				}
				homeworkExt.getLstAccessoriex().add(aExt);

			    }

			}
		    }

		    lsthwe.add(homeworkExt);

		}

	    }
	}

	return "homeworkInit";
    }

    public void uploadHomework() throws IOException
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	int homeworkId = Integer.parseInt(res.getParameter("id"));
	int courseId = Integer.parseInt(res.getParameter("coursedesignId"));
	Homework hw = ihs.getIHomeworkDao().get(homeworkId);
	if (!hw.getIsEndWork())
	{
	    this.setSavePath("tempUpload");

	}
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

	    // 附件入库
	    @SuppressWarnings("unused")
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
	    accessory.setIsCourseWare(false);
	    accessory = ias.getAccessoryDao().add(accessory);
	    // 作业附件入库
	    HttpSession session = res.getSession();
	    Sso sso = (Sso) session.getAttribute("user");
	    Student s = ists.getIStudentDao().getListByHSQL("from Student where studentCode='"+sso.getUserCode() +"'").get(0);
	    HomeworkAccessoryMap ham = new HomeworkAccessoryMap();
	    ham.setAccessory(accessory);
	    ham.setHomework(hw);
	    ham.setStudent(s);
	    ham.setHomeworkState((short) 1);
	    iams.getiHomeworkAccessoryMapDao().add(ham);

	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	finally
	{
	    fis.close();
	    fos.close();
	    rps.sendRedirect("student_homeworkInit.action?coursedesignId="
		    + courseId);
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
	if (session.getAttribute("student") != null)
	{
	    student = (Student) session.getAttribute("student");
	    hqlString = "from Classmap where student=" + student.getId()
		    + " and coursedesign=" + coursid;
	    lstcm = ics.getiClassMapDao().getListByHSQL(hqlString);
	    if (lstcm.size() == 0)// 判断是否有权利访问
	    {
		hasRight = false;

	    }
	}
	return hasRight;
    }

    public String userInfoInit()
    {

	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = res.getSession();

	if (session.getAttribute("student") != null)
	{
	    student = (Student) session.getAttribute("student");
	}
	return "userInfoInit";
    }

    public void userInfo() throws NoSuchAlgorithmException, IOException
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = res.getSession();

	if (session.getAttribute("student") != null)
	{
	    Student tStudent = (Student) session.getAttribute("student");
	    tStudent.setIdCode(student.getIdCode());
	    tStudent.setFamilyName(student.getFamilyName());
	    tStudent.setFamilyphone(student.getFamilyphone());
	    tStudent.setPhone(student.getPhone());
	    ists.getIStudentDao().update(tStudent);
	    if (res.getParameterMap().containsKey("pwd"))
	    {
		String pwdString = replaceBlank(res.getParameter("pwd"));
		hqlString = "update Sso set pwd='"
			+ GetCriptString.getString(pwdString, ECrypType.MD5,
				ECrypMethod.ENCODE) + "' where userCode='"
			+ student.getStudentCode() + "'";
		iss.getISsoDao().executeHSQL(hqlString);
	    }

	    rps.sendRedirect("login_logout.action");

	}
    }

    public String messageInit()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpSession session = res.getSession();
	Sso ts = (Sso) session.getAttribute("user");
	hqlString = "from SsoMessageMap where sso=" + ts.getId();
	lstSmm = imss.getIMessageSsoDao().getListByHSQL(hqlString);
	hqlString = "update SsoMessageMap set isRead=1 where sso=" + ts.getId();
	imss.getIMessageSsoDao().executeHSQL(hqlString);
	return "messageInit";
    }

    public void delMsg()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpSession session = res.getSession();
	int id = Integer.parseInt(res.getParameter("id"));
	hqlString = "delete from SsoMessageMap where id=" + id;
	imss.getIMessageSsoDao().executeHSQL(hqlString);
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
     * @return the lstcm
     */
    public List<Classmap> getLstcm()
    {
	return lstcm;
    }

    /**
     * @param lstcm
     *            the lstcm to set
     */
    public void setLstcm(List<Classmap> lstcm)
    {
	this.lstcm = lstcm;
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
     * @return the ictmService
     */
    public IClassTeacherMapService getIctmService()
    {
	return ictmService;
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
     * @return the iams
     */
    public IHomeworkAccessoryMapService getIams()
    {
	return iams;
    }

    /**
     * @param iams
     *            the iams to set
     */
    public void setIams(IHomeworkAccessoryMapService iams)
    {
	this.iams = iams;
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
     * @return the lstCoursedesignTeacherMaps
     */
    public List<CoursedesignTeacherMap> getLstCoursedesignTeacherMaps()
    {
	return lstCoursedesignTeacherMaps;
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
     * @param lstCoursedesignTeacherMaps
     *            the lstCoursedesignTeacherMaps to set
     */
    public void setLstCoursedesignTeacherMaps(
	    List<CoursedesignTeacherMap> lstCoursedesignTeacherMaps)
    {
	this.lstCoursedesignTeacherMaps = lstCoursedesignTeacherMaps;
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
     * @return the lstHw
     */
    public List<Homework> getLstHw()
    {
	return lstHw;
    }

    /**
     * @return the lsthwe
     */
    public List<homeworkExt> getLsthwe()
    {
	return lsthwe;
    }

    /**
     * @param lsthwe
     *            the lsthwe to set
     */
    public void setLsthwe(List<homeworkExt> lsthwe)
    {
	this.lsthwe = lsthwe;
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
     * @return the lstham
     */
    public List<HomeworkAccessoryMap> getLstham()
    {
	return lstham;
    }

    /**
     * @param lstham
     *            the lstham to set
     */
    public void setLstham(List<HomeworkAccessoryMap> lstham)
    {
	this.lstham = lstham;
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
     * @return the lstAe
     */
    public List<AccessoryExt> getLstAe()
    {
	return lstAe;
    }

    /**
     * @param lstAe
     *            the lstAe to set
     */
    public void setLstAe(List<AccessoryExt> lstAe)
    {
	this.lstAe = lstAe;
    }

    /**
     * @return the iss
     */
    public ISsoService getIss()
    {
	return iss;
    }

    /**
     * @param iss
     *            the iss to set
     */
    public void setIss(ISsoService iss)
    {
	this.iss = iss;
    }

    /**
     * @return the ists
     */
    public IStudentService getIsts()
    {
	return ists;
    }

    /**
     * @param ists
     *            the ists to set
     */
    public void setIsts(IStudentService ists)
    {
	this.ists = ists;
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

}
