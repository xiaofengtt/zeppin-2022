/**
 * 
 */
package com.zeppin.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zeppin.entiey.Sso;
import com.zeppin.entiey.Student;
import com.zeppin.entiey.Teacher;
import com.zeppin.service.IAccessoryService;
import com.zeppin.service.IClassTeacherMapService;
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
@SuppressWarnings("serial")
public class LoginAction extends ActionSupport
{
    /*
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    static Logger logger = LogManager.getLogger(LoginAction.class);
    private IAccessoryService ias;
    private String pwd;
    private String userName;
    private String hqlString;
    private Student student = new Student();
    private Teacher teacher = new Teacher();
    private Sso sso = new Sso();
    private ISsoService iSsoService;
    private IStudentService iStudentService;
    private ITeachereService iTeachereService;
    private IClassTeacherMapService ictms;

    @Override
    public String execute() throws Exception
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	@SuppressWarnings("unused")
	HttpSession session = res.getSession();
	session.setMaxInactiveInterval(30 * 60);
	if (res.getParameterMap().containsKey("pwd"))
	{
	    pwd = GetCriptString.getString(pwd, ECrypType.MD5,
		    ECrypMethod.ENCODE);

	    hqlString = "from Sso where userCode='" + userName + "' and pwd='"
		    + pwd + "'";
	    try
	    {
		sso = iSsoService.getISsoDao().getListByHSQL(hqlString).get(0);
	    }
	    catch (Exception e)
	    {
		return "login";
	    }

	    if (sso != null)
	    {
		session.setAttribute("user", sso);
		if (sso.getUserType() == 1)// 学生
		{

		    hqlString = "from Student where studentCode='"
			    + sso.getUserCode() + "'";
		    student = iStudentService.getIStudentDao()
			    .getListByHSQL(hqlString).get(0);
		    session.setAttribute("student", student);
		    logger.log(Level.TRACE, userName + "登录成功");
		    return "student";

		}
		else
		// 老师
		{

		    hqlString = "from Teacher where workCode='"
			    + sso.getUserCode() + "'";
		    teacher = iTeachereService.getITeacherDao()
			    .getListByHSQL(hqlString).get(0);
		    String attName = "teacher";
		    if (teacher.getManageType() == 2)
		    {
			attName = "academic";

		    }
		    else if (teacher.getManageType() == 3)
		    {

			attName = "admin";

		    }
		    session.setAttribute(attName, teacher);
		    logger.log(Level.TRACE, userName + "登录成功");
		    return attName;

		}

	    }

	}

	return "login";

	// return "admin_StudentManage.action";
    }

    public String logout()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	@SuppressWarnings("unused")
	HttpSession session = res.getSession();
	@SuppressWarnings("rawtypes")
	Enumeration e = session.getAttributeNames();
	while (e.hasMoreElements())
	{
	    String sessionName = (String) e.nextElement();
	    session.removeAttribute(sessionName);
	}
	return "login";
    }

    /**
     * @return the ictms
     */
    public IClassTeacherMapService getIctms()
    {
	return ictms;
    }

    /**
     * @param ictms
     *            the ictms to set
     */
    public void setIctms(IClassTeacherMapService ictms)
    {
	this.ictms = ictms;
    }

    /**
     * @return the iStudentService
     */
    public IStudentService getiStudentService()
    {
	return iStudentService;
    }

    /**
     * @param iStudentService
     *            the iStudentService to set
     */
    public void setiStudentService(IStudentService iStudentService)
    {
	this.iStudentService = iStudentService;
    }

    /**
     * @return the iTeachereService
     */
    public ITeachereService getiTeachereService()
    {
	return iTeachereService;
    }

    /**
     * @param iTeachereService
     *            the iTeachereService to set
     */
    public void setiTeachereService(ITeachereService iTeachereService)
    {
	this.iTeachereService = iTeachereService;
    }

    /**
     * @return the iSsoService
     */
    public ISsoService getiSsoService()
    {
	return iSsoService;
    }

    /**
     * @param iSsoService
     *            the iSsoService to set
     */
    public void setiSsoService(ISsoService iSsoService)
    {
	this.iSsoService = iSsoService;
    }

    /**
     * @return userName
     */
    public String getUserName()
    {
	return userName;
    }

    /**
     * @param userName
     *            要设置的 userName
     */
    public void setUserName(String userName)
    {
	this.userName = userName;
    }

    /**
     * @return pwd
     */
    public String getPwd()
    {
	return pwd;
    }

    /**
     * @param pwd
     *            要设置的 pwd
     */
    public void setPwd(String pwd)
    {
	this.pwd = pwd;
    }

}
