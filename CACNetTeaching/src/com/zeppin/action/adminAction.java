/**
 * 
 */
package com.zeppin.action;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.zeppin.entiey.DicAcademy;
import com.zeppin.entiey.DicAddress;
import com.zeppin.entiey.DicDuty;
import com.zeppin.entiey.DicMajor;
import com.zeppin.entiey.DicTechnicalTiltle;
import com.zeppin.entiey.Edusystem;
import com.zeppin.entiey.Message;
import com.zeppin.entiey.Sso;
import com.zeppin.entiey.SsoMessageMap;
import com.zeppin.entiey.Student;
import com.zeppin.entiey.Subject;
import com.zeppin.entiey.Teacher;
import com.zeppin.service.IDicAcademyService;
import com.zeppin.service.IDicDutyService;
import com.zeppin.service.IDicMajorService;
import com.zeppin.service.IDicTechnicalTitleService;
import com.zeppin.service.IEduSystemService;
import com.zeppin.service.IMessageService;
import com.zeppin.service.IMessageSsoService;
import com.zeppin.service.ISsoService;
import com.zeppin.service.IStudentService;
import com.zeppin.service.ISubjectService;
import com.zeppin.service.ITeachereService;
import com.zeppin.util.cryptogram.ECrypMethod;
import com.zeppin.util.cryptogram.ECrypType;
import com.zeppin.util.cryptogram.GetCriptString;

/**
 * @author suijign
 * 
 */

public class adminAction extends ActionSupport
{
    static Logger logger = LogManager.getLogger(adminAction.class);
    String title;// 页面标题
    IStudentService iss;// 学生操作
    ITeachereService its;// 教师操作
    IDicMajorService ims;// z专业操作
    IDicAcademyService ias;// 院系部门操作
    ISubjectService isubs;// 科目操作
    IEduSystemService ies;// 学制操作
    IDicTechnicalTitleService itts;// 职称操作
    IDicDutyService ids; // 职务操作
    ISsoService issos;// sso操作
    IMessageSsoService imss;// 消息用户操作
    IMessageService imsService;
    ISsoService iSsoService;

    String jsonString = "";
    int row;
    int total;// 总页数
    int page;// 第几页
    BigInteger records;// 总条数
    String hqlString = "";// hql语句
    List objects = new ArrayList();// 参数
    List<SsoMessageMap> lstSmm = new ArrayList<SsoMessageMap>();
    Teacher teacher = new Teacher();
    Message message = new Message();
    List<Message> lstMessages = new ArrayList<Message>();
    List<Teacher> lstTeachers = new ArrayList<Teacher>();
    List<Student> lStudents = new ArrayList<Student>();
    List<Sso> lSsos = new ArrayList<Sso>();

    /**
     * @return the issos
     */
    public ISsoService getIssos()
    {
	return issos;
    }

    /**
     * @param issos
     *            the issos to set
     */
    public void setIssos(ISsoService issos)
    {
	this.issos = issos;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title)
    {
	this.title = title;
    }

    /**
     * @return jsonString
     */
    public String getJsonString()
    {
	return jsonString;
    }

    /**
     * @param jsonString
     *            要设置的 jsonString
     */
    public void setJsonString(String jsonString)
    {
	this.jsonString = jsonString;
    }

    /**
     * @return ids
     */
    public IDicDutyService getIds()
    {
	return ids;
    }

    /**
     * @param ids
     *            要设置的 ids
     */
    public void setIds(IDicDutyService ids)
    {
	this.ids = ids;
    }

    /**
     * @return itts
     */
    public IDicTechnicalTitleService getItts()
    {
	return itts;
    }

    /**
     * @param itts
     *            要设置的 itts
     */
    public void setItts(IDicTechnicalTitleService itts)
    {
	this.itts = itts;
    }

    /**
     * @return ies
     */
    public IEduSystemService getIes()
    {
	return ies;
    }

    /**
     * @param ies
     *            要设置的 ies
     */
    public void setIes(IEduSystemService ies)
    {
	this.ies = ies;
    }

    /**
     * @return isubs
     */
    public ISubjectService getIsubs()
    {
	return isubs;
    }

    /**
     * @param isubs
     *            要设置的 isubs
     */
    public void setIsubs(ISubjectService isubs)
    {
	this.isubs = isubs;
    }

    /**
     * @return ias
     */
    public IDicAcademyService getIas()
    {
	return ias;
    }

    /**
     * @param ias
     *            要设置的 ias
     */
    public void setIas(IDicAcademyService ias)
    {
	this.ias = ias;
    }

    /**
     * @return ims
     */
    public IDicMajorService getIms()
    {
	return ims;
    }

    /**
     * @param ims
     *            要设置的 ims
     */
    public void setIms(IDicMajorService ims)
    {
	this.ims = ims;
    }

    /**
     * @return its
     */
    public ITeachereService getIts()
    {
	return its;
    }

    /**
     * @param its
     *            要设置的 its
     */
    public void setIts(ITeachereService its)
    {
	this.its = its;
    }

    /**
     * @return iss
     */
    public IStudentService getIss()
    {
	return iss;
    }

    /**
     * @param iss
     *            要设置的 iss
     */
    public void setIss(IStudentService iss)
    {
	this.iss = iss;
    }

    /*
     * （非 Javadoc）
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Override
    public String execute() throws Exception
    {
	// TODO 自动生成的方法存根
	return super.execute();
    }

    /**
     * 初始化学生管理页面
     * 
     * @return
     */
    public String studentManageInit()
    {
	return "studentManageInit";
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    public void studentManage() throws Exception
    {

	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	String tableNameString = "student";
	String ObjtableName = "Student";
	String opt;
	String id;
	String name;
	String studentCode;
	DicMajor dMajor = new DicMajor();
	String classes;
	Edusystem es = new Edusystem();
	DicAddress dAddress = new DicAddress();
	Date eduStartTime;
	short state;
	short sex;
	String phone;
	String idCode;
	String familyName;
	String familyphone;
	String remark;
	Student student = new Student();

	if (res.getParameterMap().containsKey("opt"))
	{
	    opt = res.getParameter("opt");
	    // 初始化
	    if (opt.equals("init"))
	    {
		hqlString = "select count(*) from " + tableNameString
			+ " where isValue=1";
		page = Integer.parseInt(res.getParameter("page"));
		row = Integer.parseInt(res.getParameter("rows"));
		records = (BigInteger) its.getITeacherDao()
			.executeSQL(hqlString, null).listIterator().next();
		;
		total = records.intValue() / row + 1;
		String sidxString = res.getParameter("sidx");
		String sord = res.getParameter("sord");
		// 查询操作
		if (res.getParameter("_search").equals("true"))
		{
		    hqlString = "from " + ObjtableName + " Where isValue=1 ";
		    if (res.getParameterMap().containsKey("name"))
		    {
			name = res.getParameter("name");
			if (!name.equals(null))
			    hqlString += " and name like '%" + name + "%'";
		    }
		    if (res.getParameterMap().containsKey("studentCode"))
		    {
			studentCode = res.getParameter("studentCode");
			if (!studentCode.equals(null))
			    hqlString += " and studentCode like '%"
				    + studentCode + "%'";

		    }
		    if (!res.getParameter("dicMajor").equals("all"))
		    {
			hqlString += " and dicMajor="
				+ res.getParameter("dicMajor");
		    }

		    if (res.getParameterMap().containsKey("classes"))
		    {
		    	classes = res.getParameter("classes");
			if (!classes.equals(null))
			    hqlString += " and classes like '%" + classes + "%'";
		    }
		
		    if (!res.getParameter("edusystem").equals("all"))
		    {
			hqlString += " and edusystem="
				+ res.getParameter("edusystem");

		    }

		    if (res.getParameterMap().containsKey("dicAddress"))
		    {

			if (!res.getParameter("dicAddress").equals(null))
			{
			    hqlString += " and dicAddress.name like '%"
				    + res.getParameter("dicAddress")
			    	+ "%'";
			}

		    }
		    if (res.getParameterMap().containsKey("eduStartTime"))
		    {

			if (!res.getParameter("eduStartTime").equals(null))
			{
			    hqlString += " and eduStartTime= '"
				    + res.getParameter("eduStartTime") + "'";
			}

		    }

		    if (!res.getParameter("state").equals("all"))
		    {
			state = Short.parseShort(res.getParameter("state"));
			hqlString += " and state=" + state;

		    }
		    if (!res.getParameter("sex").equals("all"))
		    {
			sex = Short.parseShort(res.getParameter("sex"));
			hqlString += " and sex=" + sex;

		    }
		    if (res.getParameterMap().containsKey("phone"))
		    {
			phone = res.getParameter("phone");
			if (!phone.equals(null))
			{
			    hqlString += " and phone like '%" + phone + "%'";
			}

		    }
		    if (res.getParameterMap().containsKey("idCode"))
		    {

			idCode = res.getParameter("idCode");
			if (idCode != null)
			{
			    hqlString += " and idCode=" + idCode;
			}

		    }
		    if (res.getParameterMap().containsKey("familyName"))
		    {
			familyName = res.getParameter("familyName");
			if (familyName != null)
			{
			    hqlString += " and familyName=" + familyName;
			}

		    }
		    if (res.getParameterMap().containsKey("familyphone"))
		    {

			familyphone = res.getParameter("familyphone");
			if (familyphone != null)
			{
			    hqlString += " and familyphone=" + familyphone;
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
		    hqlString += " order by " + sidxString + " "
				    + sord;
		}
		// 初始化操作
		else
		{
		    hqlString = "from " + ObjtableName
			    + " where isValue=1 order by " + sidxString + " "
			    + sord;

		}

		List<Student> lstss = iss.getIStudentDao().getListForPage(
			hqlString, page, row);
		jsonString = "";
		jsonString += "{";
		jsonString += "\"page\":\"" + page + "\",";
		jsonString += "\"total\":\"" + total + "\",";
		jsonString += "\"records\":\"" + records + "\",";
		jsonString += "\"rows\":[";
		for (Student s : lstss)
		{
		    String sexString = (s.getSex() == 1 ? "男" : "女");
		    String stateString = (s.getState() == 1 ? "正常" : "异动");

		    jsonString += "{\"id\":\"" + s.getId() + "\",";
		    jsonString += "\"cell\":["
			    + s.getId()
			    + ",\""
			    + s.getName()
			    + "\",\""
			    + s.getStudentCode()
			    + "\",\""
			    + s.getDicMajor().getName()
			    + "\",\""
			    + s.getClasses()
			    + "\",\""
			    + s.getEdusystem().getName()
			    + "\",\""
			    + s.getDicAddress().getName()
			    + "\",\""
			    + s.getDicAddress().getName()
			    + "\",\""
			    + DateFormat.getDateInstance(DateFormat.DEFAULT)
				    .format(s.getEduStartTime()) + "\",\""
			    + stateString + "\",\"" + sexString + "\",\""
			    + s.getPhone() + "\",\"" + s.getIdCode() + "\",\""
			    + s.getFamilyName() + "\",\"" + s.getFamilyphone()
			    + "\",\"" + s.getRemark() +

			    "\"]},";

		}
		if (lstss.size() > 0)
		    jsonString = jsonString.substring(0,
			    jsonString.length() - 1);
		jsonString += "]}";

		rps.setContentType("application/json");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);
		
	    }

	    // 添加新项目
	    if (opt.equals("add") || opt.equals("edit"))
	    {

		name = replaceBlank(res.getParameter("name"));
		studentCode = replaceBlank(res.getParameter("studentCode"));
		dMajor.setId(Integer.valueOf(res.getParameter("dicMajor")));
		classes = res.getParameter("classes");
		es.setId(Integer.parseInt(res.getParameter("edusystem")));
		dAddress.setId(Integer.parseInt(res.getParameter("citycode")));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		eduStartTime = (Date) sdf.parse(res
			.getParameter("eduStartTime"));
		state = Short.parseShort(res.getParameter("state"));
		sex = Short.parseShort(res.getParameter("sex"));
		phone = null;
		if (res.getParameterMap().containsKey("phone"))
		{
		    phone = replaceBlank(res.getParameter("phone"));
		}
		idCode = null;
		if (res.getParameterMap().containsKey("idCode"))
		{

		    idCode = replaceBlank(res.getParameter("idCode"));
		}

		familyName = replaceBlank(res.getParameter("familyName"));
		familyphone = replaceBlank(res.getParameter("familyphone"));
		remark = replaceBlank(res.getParameter("remark"));
		if (!name.equals("") && !studentCode.equals(""))
		{
		    if (opt.equals("add"))
		    {
			student.setName(name);
			student.setStudentCode(studentCode);
			student.setDicMajor(dMajor);
			student.setEdusystem(es);
			student.setClasses(classes);
			student.setDicAddress(dAddress);
			student.setEduStartTime(eduStartTime);
			student.setState(state);
			student.setSex(sex);
			student.setPhone(phone);
			student.setIdCode(idCode);
			student.setFamilyName(familyName);
			student.setFamilyphone(familyphone);
			student.setRemark(remark);
			student.setIsValue(true);
			student.setCreateDate(new Date());
			iss.getIStudentDao().add(student);

			// sso操作
			String pwdString;
			if (studentCode.length() >= 6)
			{
			    pwdString = studentCode.substring(studentCode
				    .length() - 6);
			}
			else
			{
			    pwdString = studentCode;
			    for (int i = 0; i < 6 - studentCode.length(); i++)
			    {
				pwdString += 0;
			    }

			}
			Sso sso = new Sso();
			sso.setPwd(GetCriptString.getString(pwdString,
				ECrypType.MD5, ECrypMethod.ENCODE));
			sso.setUserCode(studentCode);
			sso.setCreateDate(new Date());
			sso.setUserType((short) 1);
			issos.getISsoDao().add(sso);

		    }
		    else
		    {
			id = res.getParameter("id");
			hqlString = "update "
				+ tableNameString
				+ " set name=?,studentCode=?,classes=?,fk_major=?,fk_eduSystem=?,fk_from=?,eduStartTime=?,state=?,sex=?,phone=?,idCode=?,familyName=?,familyphone=?,remark=?,modifiDate=? where id=?";

			objects.clear();
			objects.add(name);
			objects.add(studentCode);
			objects.add(classes);
			objects.add(dMajor.getId());
			objects.add(es.getId());
			objects.add(dAddress.getId());
			objects.add((new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss")).format(eduStartTime));
			objects.add(state);
			objects.add(sex);
			objects.add(phone);
			objects.add(idCode);
			objects.add(familyName);
			objects.add(familyphone);
			objects.add(remark);
			objects.add((new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss")).format(new Date()));
			objects.add(Integer.parseInt(id));
			try
			{
				iss.getIStudentDao().executeSQLUpdate("update sso inner join student on sso.userCode = student.studentCode set sso.userCode = '" + studentCode + "' where student.id = " + id,null);
			    iss.getIStudentDao().executeSQLUpdate(hqlString,
				    objects.toArray());
			    
			}
			catch (Exception e)
			{
			    logger.error(e.getMessage());
			}

		    }

		}

	    }
	    else if (opt.equalsIgnoreCase("del"))
	    {
		id = res.getParameter("id");
		if (!id.equalsIgnoreCase(""))
		{
		    if(id.contains(",")){
		    	hqlString = "update " + tableNameString + " set isValue=0 where id in (" + id + ")";
		    	its.getITeacherDao().executeSQLUpdate(hqlString,null);
		    	its.getITeacherDao().executeSQLUpdate("delete smm.* from sso_message_map smm , sso s , student st where smm.userId = s.id and st.studentCode = s.userCode and st.id in (" + id + ")",null);
		    	its.getITeacherDao().executeSQLUpdate("delete s.* from sso s , student st where st.studentCode = s.userCode and st.id in (" + id +")",null);
		    }else{
				hqlString = "update " + tableNameString
				    + " set isValue=0  where id=?";
			    objects.clear();
			    objects.add(Integer.parseInt(id));
			    its.getITeacherDao().executeSQLUpdate(hqlString,
				    objects.toArray());
			    its.getITeacherDao().executeSQLUpdate("delete smm.* from sso_message_map smm , sso s , student st where smm.userId = s.id and st.studentCode = s.userCode and st.id = " + Integer.parseInt(id),null);
			    its.getITeacherDao().executeSQLUpdate("delete s.* from sso s , student st where st.studentCode = s.userCode and st.id = " + Integer.parseInt(id),null);
		    }
		}

	    }

	}

    }

    /**
     * 教师管理
     * 
     * @return
     */
    public String teacherManageInit()
    {
	setTitle("教师管理");
	return "teacherManageInit";
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    public void teacherManage() throws ParseException, IOException,
	    NoSuchAlgorithmException
    {

	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	String tableNameString = "teacher";
	String ObjtableName = "Teacher";
	String opt;
	String id;
	String name;
	String workCode;
	short sex;
	Teacher teacher = new Teacher();
	DicDuty dduty = new DicDuty();
	DicTechnicalTiltle dTiltle = new DicTechnicalTiltle();
	DicAcademy dAcademy = new DicAcademy();
	String phone;
	String idCode;
	String remark;
	short manageType;

	if (res.getParameterMap().containsKey("opt"))
	{
	    opt = res.getParameter("opt");
	    // 初始化
	    if (opt.equals("init"))
	    {
		hqlString = "select count(*) from " + tableNameString
			+ " where isValue=1";
		page = Integer.parseInt(res.getParameter("page"));
		row = Integer.parseInt(res.getParameter("rows"));
		records = (BigInteger) its.getITeacherDao()
			.executeSQL(hqlString, null).listIterator().next();
		;
		total = records.intValue() / row + 1;
		String sidxString = res.getParameter("sidx");
		String sord = res.getParameter("sord");
		// 查询操作
		if (res.getParameter("_search").equals("true"))
		{
		    hqlString = "from " + ObjtableName + " Where isValue=1 ";
		    if (res.getParameterMap().containsKey("name"))
		    {
			name = res.getParameter("name");
			if (!name.equals(null))
			    hqlString += " and name like '%" + name + "%'";
		    }
		    if (res.getParameterMap().containsKey("workCode"))
		    {
			workCode = res.getParameter("workCode");
			if (!workCode.equals(null))
			    hqlString += " and workCode like '%" + workCode
				    + "%'";

		    }
		    if (!res.getParameter("dicDuty").equals("all"))
		    {
			hqlString += " and dicDuty="
				+ res.getParameter("dicDuty");
		    }

		    if (!res.getParameter("dicTechnicalTiltle").equals("all"))
		    {
			hqlString += " and dicTechnicalTiltle="
				+ res.getParameter("dicTechnicalTiltle");

		    }

		    if (!res.getParameter("dicAcademy").equals("all"))
		    {
			hqlString += " and dicAcademy="
				+ res.getParameter("dicAcademy");
		    }

		    if (res.getParameterMap().containsKey("phone"))
		    {
			phone = res.getParameter("phone");
			if (!phone.equals(null))
			{
			    hqlString += " and phone like '%" + phone + "%'";
			}

		    }
		    if (res.getParameterMap().containsKey("idCode"))
		    {

			idCode = res.getParameter("idCode");
			if (idCode != null)
			{
			    hqlString += " and idCode like '%" + idCode + "%'";
			}

		    }

		    if (!res.getParameter("sex").equals("all"))
		    {
			sex = Short.parseShort(res.getParameter("sex"));
			hqlString += " and sex=" + sex;

		    }

		    if (res.getParameterMap().containsKey("remark"))
		    {
			remark = res.getParameter("remark");
			if (!remark.equals(null))
			{
			    hqlString += " and remark like '%" + remark + "%'";

			}

		    }
		    hqlString += " order by " + sidxString + " "
				    + sord; 
		}
		// 初始化操作
		else
		{
		    hqlString = "from " + ObjtableName
			    + " where isValue=1 order by " + sidxString + " "
			    + sord;

		}

		List<Teacher> lstts = its.getITeacherDao().getListForPage(
			hqlString, page, row);
		jsonString = "";
		jsonString += "{";
		jsonString += "\"page\":\"" + page + "\",";
		jsonString += "\"total\":\"" + total + "\",";
		jsonString += "\"records\":\"" + records + "\",";
		jsonString += "\"rows\":[";
		for (Teacher t : lstts)
		{
		    String sexString = (t.getSex() == 1 ? "男" : "女");
		    String manageTypeString;
		    if (t.getManageType() == 1)
		    {
			manageTypeString = "教师";
		    }
		    else if (t.getManageType() == 2)
		    {
			manageTypeString = "教务处教师";
		    }
		    else
		    {
			manageTypeString = "管理员";
		    }
		    jsonString += "{\"id\":\"" + t.getId() + "\",";
		    jsonString += "\"cell\":[" + t.getId() + ",\""
			    + t.getName() + "\",\"" + t.getWorkCode() + "\",\""
			    + t.getDicDuty().getName() + "\",\""
			    + t.getDicTechnicalTiltle().getName() + "\",\""
			    + t.getDicAcademy().getName() + "\",\""
			    + t.getPhone() + "\",\"" + t.getIdCode() + "\",\""
			    + sexString + "\",\"" + manageTypeString + "\",\""
			    + t.getRemark() +

			    "\"]},";

		}
		if (lstts.size() > 0)
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
		jsonString = "<select>";
		if (res.getParameterMap().containsKey("search"))
		{
		    jsonString += "<option value=all>所有<//option>";
		}
		for (int i = 1; i <= 3; i++)
		{
		    String manageTypeString;
		    if (i == 1)
		    {
			manageTypeString = "教师";
		    }
		    else if (i == 2)
		    {
			manageTypeString = "教务处教师";
		    }
		    else
		    {
			manageTypeString = "管理员";
		    }
		    jsonString += "<option value=" + i + ">" + manageTypeString
			    + "<//option>";

		}
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		jsonString += "<//select>";
		rps.setContentType("text/html");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);
	    }

	    // 添加新项目
	    if (opt.equals("add") || opt.equals("edit"))
	    {

		name = replaceBlank(res.getParameter("name"));
		workCode = replaceBlank(res.getParameter("workCode"));
		dduty.setId(Integer.parseInt(res.getParameter("dicDuty")));
		dTiltle.setId(Integer.parseInt(res
			.getParameter("dicTechnicalTiltle")));
		dAcademy.setId(Integer.parseInt(res.getParameter("dicAcademy")));
		phone = null;
		if (res.getParameterMap().containsKey("phone"))
		{
		    phone = replaceBlank(res.getParameter("phone"));
		}
		idCode = null;
		if (res.getParameterMap().containsKey("idCode"))
		{
		    idCode = replaceBlank(res.getParameter("idCode"));
		}
		sex = Short.parseShort(res.getParameter("sex"));
		manageType = Short.parseShort(res.getParameter("manageType"));
		remark = replaceBlank(res.getParameter("remark"));
		if (!name.equals("") && !workCode.equals(""))
		{
		    if (opt.equals("add"))
		    {
			teacher.setName(name);
			teacher.setWorkCode(workCode);
			teacher.setDicDuty(dduty);
			teacher.setDicTechnicalTiltle(dTiltle);
			teacher.setDicAcademy(dAcademy);
			teacher.setPhone(phone);
			teacher.setIdCode(idCode);
			teacher.setSex((short) sex);
			teacher.setManageType(manageType);
			teacher.setRemark(remark);
			teacher.setIsValue(true);
			teacher.setCreateTime(new Date());
			its.getITeacherDao().add(teacher);
			// sso操作
			String pwdString;
			if (workCode.length() >= 6)
			{
			    pwdString = workCode
				    .substring(workCode.length() - 6);

			}
			else
			{
			    pwdString = workCode;
			    for (int i = 0; i < 6 - workCode.length(); i++)
			    {
				pwdString += 0;
			    }

			}
			Sso sso = new Sso();
			sso.setPwd(GetCriptString.getString(pwdString,
				ECrypType.MD5, ECrypMethod.ENCODE));
			sso.setUserCode(workCode);
			sso.setCreateDate(new Date());
			sso.setUserType((short) 2);
			issos.getISsoDao().add(sso);
		    }
		    else
		    {
			id = res.getParameter("id");
			hqlString = "update "
				+ tableNameString
				+ " set name=?,work_code=?,fk_duty=?,fk_technical_tiltle=?,fk_department=?,phone=?,idCode=?,sex=?,manageType=?,remark=?,modifyDate=? where id=?";

			objects.clear();
			objects.add(name);
			objects.add(workCode);
			objects.add(dduty.getId());
			objects.add(dTiltle.getId());
			objects.add(dAcademy.getId());
			objects.add(phone);
			objects.add(idCode);
			objects.add(sex);
			objects.add(manageType);
			objects.add(remark);
			objects.add((new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss")).format(new Date()));
			objects.add(Integer.parseInt(id));
			try
			{
			    its.getITeacherDao().executeSQLUpdate("update sso inner join teacher on sso.userCode = teacher.work_code set sso.userCode = '" + workCode + "' where teacher.id = " + id,null);
			    its.getITeacherDao().executeSQLUpdate(hqlString, objects.toArray());
			}
			catch (Exception e)
			{
			    logger.error(e.getMessage());
			}

		    }

		}

	    }
	    else if (opt.equalsIgnoreCase("del"))
	    {
		id = res.getParameter("id");
		if (!id.equalsIgnoreCase(""))
		{
			 if(id.contains(",")){
				hqlString = "update " + tableNameString + " set isValue=0 where id in (" + id + ")";
				its.getITeacherDao().executeSQLUpdate(hqlString,null);
				its.getITeacherDao().executeSQLUpdate("delete smm.* from sso_message_map smm , sso s , teacher t where smm.userId = s.id and t.work_code = s.userCode and t.id in (" + id + ")",null);
				its.getITeacherDao().executeSQLUpdate("delete s.* from sso s , teacher t where t.work_code = s.userCode and t.id in (" + id + ")",null);
			 }else{
			    hqlString = "update " + tableNameString + " set isValue=0  where id=?";
			    objects.clear();
			    objects.add(Integer.parseInt(id));
			    its.getITeacherDao().executeSQLUpdate(hqlString,
				    objects.toArray());
			    its.getITeacherDao().executeSQLUpdate("delete smm.* from sso_message_map smm , sso s , teacher t where smm.userId = s.id and t.work_code = s.userCode and t.id = " + Integer.parseInt(id),null);
			    its.getITeacherDao().executeSQLUpdate("delete s.* from sso s , teacher t where t.work_code = s.userCode and t.id = " + Integer.parseInt(id),null);
			 }
		}

	    }

	}

    }

    /**
     * 学科管理
     * 
     * @return
     */
    public String majorManageInit()
    {
	setTitle("专业管理");
	return "majorManageInit";
    }

    @SuppressWarnings("unchecked")
    public void majorManage() throws IOException
    {

	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	String tableNameString = "dic_major";
	String ObjtableName = "DicMajor";
	String opt;
	String id;
	String name;
	String enName;
	DicMajor dMajor = new DicMajor();
	int daId;
	DicAcademy da = new DicAcademy();

	if (res.getParameterMap().containsKey("opt"))
	{
	    opt = res.getParameter("opt");
	    // 初始化
	    if (opt.equals("init"))
	    {
		hqlString = "select count(*) from " + tableNameString
			+ " where isValue=1";
		page = Integer.parseInt(res.getParameter("page"));
		row = Integer.parseInt(res.getParameter("rows"));
		records = (BigInteger) ims.getIDicMajorDao()
			.executeSQL(hqlString, null).listIterator().next();
		;
		total = records.intValue() / row + 1;
		String sidxString = res.getParameter("sidx");
		String sord = res.getParameter("sord");

		hqlString = "from " + ObjtableName
			+ " where isValue=1 order by " + sidxString + " "
			+ sord;
		List<DicMajor> lstdm = ims.getIDicMajorDao().getListForPage(
			hqlString, page, row);
		jsonString = "";
		jsonString += "{";
		jsonString += "\"page\":\"" + page + "\",";
		jsonString += "\"total\":\"" + total + "\",";
		jsonString += "\"records\":\"" + records + "\",";
		jsonString += "\"rows\":[";
		for (DicMajor dms : lstdm)
		{

		    jsonString += "{\"id\":\"" + dms.getId() + "\",";
		    jsonString += "\"cell\":[" + dms.getId() + ",\""
			    + dms.getName() + "\",\"" + dms.getEnName()
			    + "\",\"" + dms.getDicAcademy().getName() + "\"]},";

		}
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		jsonString += "]}";

		rps.setContentType("application/json");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);

	    }
	    else if (opt.equals("getkv"))
	    {
		hqlString = "from " + ObjtableName + " where isvalue=1 ";
		List<DicMajor> lstdm = ims.getIDicMajorDao().getListForPage(
			hqlString, page, row);

		jsonString = "<select>";
		if (res.getParameterMap().containsKey("search"))
		{
		    jsonString += "<option value=all>所有<//option>";
		}
		for (DicMajor dm : lstdm)
		{
		    jsonString += "<option value=" + dm.getId() + ">"
			    + dm.getName() + "<//option>";

		}
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		jsonString += "<//select>";
		rps.setContentType("text/html");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);
	    }

	    // 添加新项目
	    else if (opt.equals("add") || opt.equalsIgnoreCase("edit"))
	    {
		name = replaceBlank(res.getParameter("name"));
		enName = replaceBlank(res.getParameter("enName"));
		daId = Integer.parseInt(res.getParameter("daId"));
		da.setId(daId);
		if (opt.equals("add"))
		{

		    if (!name.equals("") && !enName.equals(""))
		    {
			dMajor.setIsValue(true);
			dMajor.setName(name);
			dMajor.setEnName(enName);
			dMajor.setCreateDate(new Date());
			dMajor.setDicAcademy(da);
			ims.getIDicMajorDao().add(dMajor);
		    }
		}
		else
		{
		    id = res.getParameter("id");
		    if (!id.equalsIgnoreCase("") && !name.equalsIgnoreCase("")
			    && !enName.equals(""))
		    {
			hqlString = "update "
				+ tableNameString
				+ " set name=?,en_name=?,fk_academy=?,modifiDate=? where id=?";
			objects.clear();
			objects.add(name);
			objects.add(enName);
			objects.add(daId);
			objects.add((new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss")).format(new Date()));
			objects.add(Integer.parseInt(id));
			ims.getIDicMajorDao().executeSQLUpdate(hqlString,
				objects.toArray());

		    }
		}

	    }
	    else if (opt.equalsIgnoreCase("del"))
	    {
		id = res.getParameter("id");
		if (!id.equalsIgnoreCase(""))
		{
		    hqlString = "update " + tableNameString
			    + " set isValue=0  where id=?";
		    objects.clear();
		    objects.add(Integer.parseInt(id));
		    ims.getIDicMajorDao().executeSQLUpdate(hqlString,
			    objects.toArray());

		}

	    }

	}

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
     * 院系部门管理
     * 
     * @return
     */
    public String academyManageInit()
    {
	setTitle("院系部门管理");
	return "academyManageInit";
    }

    @SuppressWarnings("unchecked")
    public void academyManage() throws IOException
    {

	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	String tableNameString = "dic_academy";
	String ObjtableName = "DicAcademy";
	String opt;
	String id;
	String name;
	String enName;
	DicAcademy da = new DicAcademy();

	if (res.getParameterMap().containsKey("opt"))
	{
	    opt = res.getParameter("opt");
	    // 初始化
	    if (opt.equals("init"))
	    {
		hqlString = "select count(*) from " + tableNameString
			+ " where isValue=1";
		page = Integer.parseInt(res.getParameter("page"));
		row = Integer.parseInt(res.getParameter("rows"));
		records = (BigInteger) ias.getIDicAcademyDao()
			.executeSQL(hqlString, null).listIterator().next();
		;
		total = records.intValue() / row + 1;
		String sidxString = res.getParameter("sidx");
		String sord = res.getParameter("sord");

		hqlString = "from " + ObjtableName
			+ " where isValue=1 order by " + sidxString + " "
			+ sord;
		List<DicAcademy> lstda = ias.getIDicAcademyDao()
			.getListForPage(hqlString, page, row);

		jsonString = "";
		jsonString += "{";
		jsonString += "\"page\":\"" + page + "\",";
		jsonString += "\"total\":\"" + total + "\",";
		jsonString += "\"records\":\"" + records + "\",";
		jsonString += "\"rows\":[";
		for (DicAcademy das : lstda)
		{
		    jsonString += "{\"id\":\"" + das.getId() + "\",";
		    jsonString += "\"cell\":[" + das.getId() + ",\""
			    + das.getName() + "\",\"" + das.getEnName()
			    + "\"]},";

		}
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		jsonString += "]}";

		rps.setContentType("application/json");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);

	    }

	    else if (opt.equals("getkv"))
	    {
		hqlString = "from " + ObjtableName + " where isValue=1  ";
		List<DicAcademy> lstda = ias.getIDicAcademyDao()
			.getListForPage(hqlString, page, row);

		jsonString = "<select>";
		if (res.getParameterMap().containsKey("search"))
		{
		    jsonString += "<option value=all>所有</option>";
		}
		for (DicAcademy das : lstda)
		{
		    jsonString += "<option value=" + das.getId() + ">"
			    + das.getName() + "<//option>";

		}
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		jsonString += "<//select>";
		rps.setContentType("text/html");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);
	    }

	    // 添加新项目
	    else if (opt.equals("add"))
	    {
		name = replaceBlank(res.getParameter("name"));
		enName = replaceBlank(res.getParameter("enName"));
		if (!name.equals("") && !enName.equals(""))
		{
		    da.setIsValue(true);
		    da.setName(name);
		    da.setEnName(enName);
		    da.setCreateDate(new Date());
		    da.setParentId(0);
		    ias.getIDicAcademyDao().add(da);
		}

	    }
	    else if (opt.equalsIgnoreCase("del"))
	    {
		id = res.getParameter("id");
		if (!id.equalsIgnoreCase(""))
		{
		    hqlString = "update " + tableNameString
			    + " set isValue=0  where id=?";
		    objects.clear();
		    objects.add(Integer.parseInt(id));

		    ias.getIDicAcademyDao().executeSQLUpdate(hqlString,
			    objects.toArray());

		}

	    }
	    else if (opt.equalsIgnoreCase("edit"))
	    {
		id = res.getParameter("id");
		name = res.getParameter("name");
		enName = res.getParameter("enName");
		if (!id.equalsIgnoreCase("") && !name.equalsIgnoreCase("")
			&& !enName.equals(""))
		{
		    hqlString = "update " + tableNameString
			    + " set name=?,en_name=?,modifiDate=? where id=?";
		    objects.clear();
		    objects.add(name);
		    objects.add(enName);
		    objects.add((new java.text.SimpleDateFormat(
			    "yyyy-MM-dd HH:mm:ss")).format(new Date()));
		    objects.add(Integer.parseInt(id));
		    ias.getIDicAcademyDao().executeSQLUpdate(hqlString,
			    objects.toArray());

		}

	    }
	    // return "json";
	}

    }

    /**
     * 学科管理
     * 
     * @return
     */
    public String subjectManageInit()
    {
	setTitle("学科管理");
	return "subjectManageInit";
    }

    @SuppressWarnings("unchecked")
    public void subjectManage() throws IOException
    {

	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	String tableNameString = "subject";
	String ObjtableName = "Subject";
	String opt;
	String id;
	String code;
	String name;
	String enName;
	Subject sj = new Subject();

	if (res.getParameterMap().containsKey("opt"))
	{
	    opt = res.getParameter("opt");
	    // 初始化
	    if (opt.equals("init"))
	    {
		hqlString = "select count(*) from " + tableNameString
			+ " where isValue=1";
		page = Integer.parseInt(res.getParameter("page"));
		row = Integer.parseInt(res.getParameter("rows"));
		records = (BigInteger) isubs.getISubjectDao()
			.executeSQL(hqlString, null).listIterator().next();
		;
		total = records.intValue() / row + 1;
		String sidxString = res.getParameter("sidx");
		String sord = res.getParameter("sord");

		hqlString = "from " + ObjtableName
			+ " where isValue=1 order by " + sidxString + " "
			+ sord;
		List<Subject> lstsj = isubs.getISubjectDao().getListForPage(
			hqlString, page, row);
		jsonString = "";
		jsonString += "{";
		jsonString += "\"page\":\"" + page + "\",";
		jsonString += "\"total\":\"" + total + "\",";
		jsonString += "\"records\":\"" + records + "\",";
		jsonString += "\"rows\":[";
		for (Subject sjs : lstsj)
		{
		    jsonString += "{\"id\":\"" + sjs.getId() + "\",";
		    jsonString += "\"cell\":[" + sjs.getId() + ",\""
			    + sjs.getName() + "\",\"" + sjs.getEnName()
			    + "\",\"" + sjs.getCode() + "\"]},";

		}
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		jsonString += "]}";

		rps.setContentType("application/json");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);

	    }
	    else if (opt.equals("getkv"))
	    {
		hqlString = "from " + ObjtableName + " where isvalue=1 ";
		List<Subject> lstsj = isubs.getISubjectDao().getListForPage(
			hqlString, page, row);

		jsonString = "<select>";
		if (res.getParameterMap().containsKey("search"))
		{
		    jsonString += "<option value=all>所有<//option>";
		}
		for (Subject sj1 : lstsj)
		{
		    jsonString += "<option value=" + sj1.getId() + ">"
			    + sj1.getName() + "<//option>";

		}
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		jsonString += "<//select>";
		rps.setContentType("text/html");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);
	    }
	    // 添加新项目
	    if (opt.equals("add"))
	    {
		name = replaceBlank(res.getParameter("name"));
		enName = replaceBlank(res.getParameter("enName"));
		code = replaceBlank(res.getParameter("code"));
		if (!name.equals("") && !enName.equals("") && !code.equals(""))
		{
		    sj.setIsValue(true);
		    sj.setName(name);
		    sj.setEnName(enName);
		    sj.setCode(code);
		    sj.setCreateDate(new Date());
		    isubs.getISubjectDao().add(sj);
		}

	    }
	    else if (opt.equalsIgnoreCase("del"))
	    {
		id = res.getParameter("id");
		if (!id.equalsIgnoreCase(""))
		{
		    hqlString = "update " + tableNameString
			    + " set isValue=0  where id=?";
		    objects.clear();
		    objects.add(Integer.parseInt(id));
		    isubs.getISubjectDao().executeSQLUpdate(hqlString,
			    objects.toArray());

		}

	    }
	    else if (opt.equalsIgnoreCase("edit"))
	    {
		id = res.getParameter("id");
		name = res.getParameter("name");
		enName = res.getParameter("enName");
		code = res.getParameter("code");
		if (!id.equalsIgnoreCase("") && !name.equalsIgnoreCase("")
			&& !enName.equals("") && !code.equals(""))
		{
		    hqlString = "update "
			    + tableNameString
			    + " set name=?,enName=?,code=?,modifiDate=? where id=?";
		    objects.clear();
		    objects.add(name);
		    objects.add(enName);
		    objects.add(code);
		    objects.add((new java.text.SimpleDateFormat(
			    "yyyy-MM-dd HH:mm:ss")).format(new Date()));
		    objects.add(Integer.parseInt(id));
		    isubs.getISubjectDao().executeSQLUpdate(hqlString,
			    objects.toArray());

		}

	    }
	    // return "json";
	}

    }

    /**
     * 学制管理
     * 
     * @return
     */
    public String eduSysManageInit()
    {
	setTitle("学制管理");
	return "eduSysManageInit";
    }

    @SuppressWarnings("unchecked")
    public void eduSysManage() throws IOException
    {

	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	String tableNameString = "edusystem";
	String ObjtableName = "Edusystem";
	String opt;
	String name;
	String id;
	String year;
	Edusystem es = new Edusystem();

	if (res.getParameterMap().containsKey("opt"))
	{
	    opt = res.getParameter("opt");
	    // 初始化
	    if (opt.equals("init"))
	    {
		hqlString = "select count(*) from " + tableNameString
			+ " where isValue=1";
		page = Integer.parseInt(res.getParameter("page"));
		row = Integer.parseInt(res.getParameter("rows"));
		records = (BigInteger) ies.getiEduSystemDao()
			.executeSQL(hqlString, null).listIterator().next();
		;
		total = records.intValue() / row + 1;
		String sidxString = res.getParameter("sidx");
		String sord = res.getParameter("sord");

		hqlString = "from " + ObjtableName
			+ " where isValue=1 order by " + sidxString + " "
			+ sord;
		List<Edusystem> lstes = ies.getiEduSystemDao().getListForPage(
			hqlString, page, row);
		jsonString = "";
		jsonString += "{";
		jsonString += "\"page\":\"" + page + "\",";
		jsonString += "\"total\":\"" + total + "\",";
		jsonString += "\"records\":\"" + records + "\",";
		jsonString += "\"rows\":[";
		for (Edusystem eds : lstes)
		{
		    jsonString += "{\"id\":\"" + eds.getId() + "\",";
		    jsonString += "\"cell\":[" + eds.getId() + ",\""
			    + eds.getName() + "\",\"" + eds.getYear() + "\"]},";

		}
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		jsonString += "]}";

		rps.setContentType("application/json");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);

	    }
	    else if (opt.equals("getkv"))
	    {
		hqlString = "from " + ObjtableName + " where isvalue=1 ";
		List<Edusystem> lstes = ies.getiEduSystemDao().getListForPage(
			hqlString, page, row);

		jsonString = "<select>";
		if (res.getParameterMap().containsKey("search"))
		{
		    jsonString += "<option value=all>所有<//option>";
		}
		for (Edusystem ess : lstes)
		{
		    jsonString += "<option value=" + ess.getId() + ">"
			    + ess.getName() + "<//option>";

		}
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		jsonString += "<//select>";
		rps.setContentType("text/html");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);
	    }

	    // 添加新项目
	    else if (opt.equals("add"))
	    {
		name = replaceBlank(res.getParameter("name"));
		year = replaceBlank(res.getParameter("year"));
		if (!name.equals("") && !year.equals(""))
		{
		    es.setIsValue(true);
		    es.setName(name);
		    es.setYear(Float.parseFloat(year));
		    ies.getiEduSystemDao().add(es);
		}

	    }
	    else if (opt.equalsIgnoreCase("del"))
	    {
		id = res.getParameter("id");
		if (!id.equalsIgnoreCase(""))
		{
		    hqlString = "update " + tableNameString
			    + " set isValue=0  where id=?";
		    objects.clear();
		    objects.add(Integer.parseInt(id));
		    ies.getiEduSystemDao().executeSQLUpdate(hqlString,
			    objects.toArray());

		}

	    }
	    else if (opt.equalsIgnoreCase("edit"))
	    {
		id = res.getParameter("id");
		name = res.getParameter("name");
		year = res.getParameter("year");
		if (!id.equalsIgnoreCase("") && !name.equalsIgnoreCase("")
			&& !year.equals(""))
		{
		    hqlString = "update " + tableNameString
			    + " set name=?,year=? where id=?";
		    objects.clear();
		    objects.add(name);
		    objects.add(year);
		    objects.add(Integer.parseInt(id));

		    ies.getiEduSystemDao().executeSQLUpdate(hqlString,
			    objects.toArray());

		}

	    }
	    // return "json";
	}

    }

    /**
     * 职称管理
     * 
     * @return
     */
    public String technicalManageInit()
    {
	setTitle("职称管理");
	return "technicalManageInit";
    }

    @SuppressWarnings("unchecked")
    public void technicalManage() throws IOException
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	String tableNameString = "dic_technical_tiltle";
	String ObjtableName = "DicTechnicalTiltle";
	String opt;
	String name;
	String id;
	DicTechnicalTiltle dtt = new DicTechnicalTiltle();

	if (res.getParameterMap().containsKey("opt"))
	{
	    opt = res.getParameter("opt");
	    // 初始化
	    if (opt.equals("init"))
	    {
		hqlString = "select count(*) from " + tableNameString
			+ " where isValue=1";
		page = Integer.parseInt(res.getParameter("page"));
		row = Integer.parseInt(res.getParameter("rows"));
		records = (BigInteger) itts.getIDicTechnicalTitleDao()
			.executeSQL(hqlString, null).listIterator().next();
		;
		total = records.intValue() / row + 1;
		String sidxString = res.getParameter("sidx");
		String sord = res.getParameter("sord");

		hqlString = "from " + ObjtableName
			+ " where isValue=1 order by " + sidxString + " "
			+ sord;
		List<DicTechnicalTiltle> lstDtt = itts
			.getIDicTechnicalTitleDao().getListForPage(hqlString,
				page, row);
		jsonString = "";
		jsonString += "{";
		jsonString += "\"page\":\"" + page + "\",";
		jsonString += "\"total\":\"" + total + "\",";
		jsonString += "\"records\":\"" + records + "\",";
		jsonString += "\"rows\":[";
		for (DicTechnicalTiltle dttd : lstDtt)
		{
		    jsonString += "{\"id\":\"" + dttd.getId() + "\",";
		    jsonString += "\"cell\":[" + dttd.getId() + ",\""
			    + dttd.getName() + "\"]},";
		}
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		jsonString += "]}";

		rps.setContentType("application/json");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);

	    }

	    else if (opt.equals("getkv"))
	    {
		hqlString = "from " + ObjtableName + " where isvalue=1 ";
		List<DicTechnicalTiltle> lstdtt = itts
			.getIDicTechnicalTitleDao().getListForPage(hqlString,
				page, row);

		jsonString = "<select>";
		if (res.getParameterMap().containsKey("search"))
		{
		    jsonString += "<option value=all>所有<//option>";
		}
		for (DicTechnicalTiltle dtts : lstdtt)
		{
		    jsonString += "<option value=" + dtts.getId() + ">"
			    + dtts.getName() + "<//option>";

		}
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		jsonString += "<//select>";
		rps.setContentType("text/html");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);
	    }

	    // 添加新项目
	    else if (opt.equals("add"))
	    {
		name = replaceBlank(res.getParameter("name"));
		if (!name.equals(""))
		{
		    dtt.setIsValue(true);
		    dtt.setName(name);
		    dtt.setCreateDate(new Date());
		    itts.getIDicTechnicalTitleDao().add(dtt);
		}

	    }
	    else if (opt.equalsIgnoreCase("del"))
	    {
		id = res.getParameter("id");
		if (!id.equalsIgnoreCase(""))
		{
		    hqlString = "update " + tableNameString
			    + " set isValue=0 , modifiDate=? where id=?";
		    objects.clear();
		    objects.add((new java.text.SimpleDateFormat(
			    "yyyy-MM-dd HH:mm:ss")).format(new Date()));
		    objects.add(Integer.parseInt(id));
		    itts.getIDicTechnicalTitleDao().executeSQLUpdate(hqlString,
			    objects.toArray());
		    

		}

	    }
	    else if (opt.equalsIgnoreCase("edit"))
	    {
		id = res.getParameter("id");
		name = res.getParameter("name");
		if (!id.equalsIgnoreCase("") && !name.equalsIgnoreCase(""))
		{

		    hqlString = "update " + tableNameString
			    + " set name=?, modifiDate=? where id=?";
		    objects.clear();
		    objects.add(name);
		    objects.add((new java.text.SimpleDateFormat(
			    "yyyy-MM-dd HH:mm:ss")).format(new Date()));
		    objects.add(Integer.parseInt(id));
		    ids.getiDicDutyDao().executeSQLUpdate(hqlString,
			    objects.toArray());

		}

	    }
	    // return "json";
	}
    }

    /**
     * 职务管理
     * 
     * @return
     */
    public String dutyManageInit()
    {
	setTitle("职务管理");
	return "dutyManageInit";

    }

    /**
     * 职称管理
     * 
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public void dutyManage() throws IOException
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	String opt;
	String objectTableName = "DicDuty";
	String name;
	String id;
	DicDuty ddDuty = new DicDuty();

	if (res.getParameterMap().containsKey("opt"))
	{
	    opt = res.getParameter("opt");
	    // 初始化
	    if (opt.equals("init"))
	    {
		hqlString = "select count(*) from dic_duty where isValue=1";
		page = Integer.parseInt(res.getParameter("page"));
		row = Integer.parseInt(res.getParameter("rows"));
		records = (BigInteger) ids.getiDicDutyDao()
			.executeSQL(hqlString, null).listIterator().next();
		;
		total = records.intValue() / row + 1;
		String sidxString = res.getParameter("sidx");
		String sord = res.getParameter("sord");

		hqlString = "from DicDuty where isvalue=1 order by "
			+ sidxString + " " + sord;
		List<DicDuty> lstDuty = ids.getiDicDutyDao().getListForPage(
			hqlString, page, row);
		jsonString = "";
		jsonString += "{";
		jsonString += "\"page\":\"" + page + "\",";
		jsonString += "\"total\":\"" + total + "\",";
		jsonString += "\"records\":\"" + records + "\",";
		jsonString += "\"rows\":[";
		for (DicDuty dd : lstDuty)
		{
		    jsonString += "{\"id\":\"" + dd.getId() + "\",";
		    jsonString += "\"cell\":[" + dd.getId() + ",\""
			    + dd.getName() + "\"]},";
		}
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		jsonString += "]}";

		rps.setContentType("application/json");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);

	    }

	    else if (opt.equals("getkv"))
	    {
		hqlString = "from " + objectTableName + " where isvalue=1 ";
		List<DicDuty> lstdt = ids.getiDicDutyDao().getListForPage(
			hqlString, page, row);

		jsonString = "<select>";
		if (res.getParameterMap().containsKey("search"))
		{
		    jsonString += "<option value=all>所有<//option>";
		}
		for (DicDuty dds : lstdt)
		{
		    jsonString += "<option value=" + dds.getId() + ">"
			    + dds.getName() + "<//option>";

		}
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		jsonString += "<//select>";
		rps.setContentType("text/html");
		rps.setCharacterEncoding("UTF-8");
		rps.setHeader("Cache-Control", "no-cache");
		rps.getWriter().write(jsonString);
	    }

	    // 添加新项目
	    else if (opt.equals("add"))
	    {
		name = replaceBlank(res.getParameter("name"));
		if (!name.equals(""))
		{
		    ddDuty.setIsValue(true);
		    ddDuty.setName(name);
		    ddDuty.setCreateDate(new Date());
		    ids.getiDicDutyDao().add(ddDuty);
		}

	    }
	    else if (opt.equalsIgnoreCase("del"))
	    {
		id = res.getParameter("id");
		if (!id.equalsIgnoreCase(""))
		{
		    hqlString = "update dic_duty set isValue=0 , modifiDate=? where id=?";
		    objects.clear();
		    objects.add((new java.text.SimpleDateFormat(
			    "yyyy-MM-dd HH:mm:ss")).format(new Date()));
		    objects.add(Integer.parseInt(id));
		    ids.getiDicDutyDao().executeSQLUpdate(hqlString,
			    objects.toArray());

		}

	    }
	    else if (opt.equalsIgnoreCase("edit"))
	    {
		id = res.getParameter("id");
		name = res.getParameter("name");
		if (!id.equalsIgnoreCase("") && !name.equalsIgnoreCase(""))
		{
		    /*
		     * ddDuty = ids.getiDicDutyDao().get(Integer.parseInt(id));
		     * ddDuty.setName(name); ddDuty.setCreateDate(new Date());
		     * ddDuty.setModifiDate(new Date());
		     * ids.getiDicDutyDao().update(ddDuty);
		     */
		    hqlString = "update dic_duty set name=?, modifiDate=? where id=?";
		    objects.clear();
		    objects.add(name);
		    objects.add((new java.text.SimpleDateFormat(
			    "yyyy-MM-dd HH:mm:ss")).format(new Date()));
		    objects.add(Integer.parseInt(id));
		    ids.getiDicDutyDao().executeSQLUpdate(hqlString,
			    objects.toArray());

		}

	    }
	    // return "json";

	}
    }

    /**
     * 
     * @param ids
     *            需要修改的用户id
     * @param userType
     *            用户类型
     * @throws NoSuchAlgorithmException
     */
    @SuppressWarnings("deprecation")
    public void initPwd() throws NoSuchAlgorithmException
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	String userType = res.getParameter("userType");
	String ids[] = res.getParameter("ids[]").split(",");

	if (userType.equals("1"))// 学生
	{
	    for (String id : ids)
	    {
		Student student = new Student();
		student = iss.getIStudentDao().get(Integer.parseInt(id));
		Sso sso = new Sso();
		sso = issos
			.getISsoDao()
			.getListByHSQL(
				"from Sso where userCode='"
					+ student.getStudentCode() + "'")
			.get(0);
		// sso操作
		String pwdString = student.getStudentCode();
		if (pwdString.length() >= 6)
		{
		    pwdString = pwdString.substring(pwdString.length() - 6);

		}
		else
		{

		    for (int i = 0; i < 6 - pwdString.length(); i++)
		    {
			pwdString += 0;
		    }

		}

		sso.setPwd(pwdString);
		sso.setUserCode(student.getStudentCode());
		sso.setModifiDate(new Date());
		sso.setUserType((short) 1);
		hqlString = "update Sso set pwd='"
			+ GetCriptString.getString(sso.getPwd(), ECrypType.MD5,
				ECrypMethod.ENCODE) + "',modifiDate='"
			+ sso.getModifiDate().toLocaleString()
			+ "' where userCode='" + sso.getUserCode() + "'";
		issos.getISsoDao().executeHSQL(hqlString);

	    }
	}
	else
	{
	    for (String id : ids)
	    {
		Teacher teacher = new Teacher();
		teacher = its.getITeacherDao().get(Integer.parseInt(id));

		// sso操作
		String pwdString = teacher.getWorkCode();
		if (pwdString.length() >= 6)
		{
		    pwdString = pwdString.substring(pwdString.length() - 6);

		}
		else
		{

		    for (int i = 0; i < 6 - pwdString.length(); i++)
		    {
			pwdString += 0;
		    }

		}
		Sso sso = new Sso();
		sso.setPwd(pwdString);
		sso.setUserCode(teacher.getWorkCode());
		sso.setModifiDate(new Date());
		sso.setUserType((short) 2);
		hqlString = "update Sso set pwd='"
			+ GetCriptString.getString(sso.getPwd(), ECrypType.MD5,
				ECrypMethod.ENCODE) + "',modifiDate='"
			+ sso.getModifiDate().toLocaleString()
			+ "' where userCode='" + sso.getUserCode() + "'";
		issos.getISsoDao().executeHSQL(hqlString);
	    }

	}

    }

    public String userInfoInit()
    {

	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = res.getSession();

	if (session.getAttribute("academic") != null)
	{
	    teacher = (Teacher) session.getAttribute("academic");
	}
	return "userInfoInit";
    }

    public void userInfo() throws NoSuchAlgorithmException, IOException
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	HttpSession session = res.getSession();

	if (session.getAttribute("academic") != null)
	{
	    Teacher tTeacher = (Teacher) session.getAttribute("academic");
	    tTeacher.setIdCode(teacher.getIdCode());
	    tTeacher.setPhone(teacher.getPhone());
	    its.getITeacherDao().update(tTeacher);
	    if (res.getParameterMap().containsKey("pwd"))
	    {
		String pwdString = res.getParameter("pwd");
		hqlString = "update Sso set pwd='"
			+ GetCriptString.getString(pwdString, ECrypType.MD5,
				ECrypMethod.ENCODE) + "' where userCode='"
			+ tTeacher.getWorkCode() + "'";
		issos.getISsoDao().executeHSQL(hqlString);
	    }

	    rps.sendRedirect("login_logout.action");

	}
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

    public String messageInit()
    {
	HttpServletRequest res = ServletActionContext.getRequest();
	HttpSession session = res.getSession();
	Sso ts = (Sso) session.getAttribute("user");
	hqlString = "from SsoMessageMap where message in (select id from Message where type=1 or type=2 or type=3 ) and sso="
		+ ts.getId() + "order by id desc";
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
	teacher = (Teacher) session.getAttribute("academic");
	message.setSendTime(new Date());
	message.setIsTop(false);
	message.setTeacher(teacher);
	message = imsService.getIMessageDao().add(message);
	lstSmm.clear();
	if (message.getType() == 1)// 全体人员
	{
	    hqlString = "from Sso where userCode in (select workCode from Teacher where isValue=1)";// 取老师
	    lSsos = issos.getISsoDao().getListByHSQL(hqlString);
	    for (Sso s : lSsos)
	    {
		SsoMessageMap sMap = new SsoMessageMap();
		sMap.setIsRead(false);
		sMap.setIsValue(true);
		sMap.setMessage(message);
		sMap.setSso(s);
		lstSmm.add(sMap);

	    }
	    hqlString = "from Sso where userCode in (select studentCode from Student where isValue=1)";// 取学生
	    lSsos = issos.getISsoDao().getListByHSQL(hqlString);
	    for (Sso s : lSsos)
	    {
		SsoMessageMap sMap = new SsoMessageMap();
		sMap.setIsRead(false);
		sMap.setIsValue(true);
		sMap.setMessage(message);
		sMap.setSso(s);
		lstSmm.add(sMap);

	    }
	    imss.getIMessageSsoDao().addList(lstSmm);

	}
	else if (message.getType() == 2)// 全体老师
	{
	    hqlString = "from Sso where userCode in (select workCode from Teacher where isValue=1)";// 取老师
	    lSsos = issos.getISsoDao().getListByHSQL(hqlString);
	    for (Sso s : lSsos)
	    {
		SsoMessageMap sMap = new SsoMessageMap();
		sMap.setIsRead(false);
		sMap.setIsValue(true);
		sMap.setMessage(message);
		sMap.setSso(s);
		lstSmm.add(sMap);

	    }
	    imss.getIMessageSsoDao().addList(lstSmm);
	}
	else
	// 全体学生
	{
	    hqlString = "from Sso where userCode in (select workCode from Teacher where isValue=1 and manageType=2)";// 取教务处
	    lSsos = issos.getISsoDao().getListByHSQL(hqlString);
	    for (Sso s : lSsos)
	    {
		SsoMessageMap sMap = new SsoMessageMap();
		sMap.setIsRead(false);
		sMap.setIsValue(true);
		sMap.setMessage(message);
		sMap.setSso(s);
		lstSmm.add(sMap);

	    }
	    hqlString = "from Sso where userCode in (select studentCode from Student where isValue=1)";// 取学生
	    lSsos = issos.getISsoDao().getListByHSQL(hqlString);
	    for (Sso s : lSsos)
	    {
		SsoMessageMap sMap = new SsoMessageMap();
		sMap.setIsRead(false);
		sMap.setIsValue(true);
		sMap.setMessage(message);
		sMap.setSso(s);
		lstSmm.add(sMap);

	    }
	    imss.getIMessageSsoDao().addList(lstSmm);
	}

	rps.sendRedirect("admin_messageInit.action");
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
    
    public String message()
    {
	return "message";
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
     * @return the lstMessages
     */
    public List<Message> getLstMessages()
    {
	return lstMessages;
    }

    /**
     * @param lstMessages
     *            the lstMessages to set
     */
    public void setLstMessages(List<Message> lstMessages)
    {
	this.lstMessages = lstMessages;
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
     * @return the lSsos
     */
    public List<Sso> getlSsos()
    {
	return lSsos;
    }

    /**
     * @param lSsos
     *            the lSsos to set
     */
    public void setlSsos(List<Sso> lSsos)
    {
	this.lSsos = lSsos;
    }
}
