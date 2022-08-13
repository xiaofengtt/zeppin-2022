package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.Exception.TypeErrorException;
import com.whaty.platform.entity.bean.InteractionTeachclassInfo;
import com.whaty.platform.entity.bean.OnlinetestCourseInfo;
import com.whaty.platform.entity.bean.PeAnswers;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PeQuestions;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PeTchCourseware;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrTchTraineeCourse;
import com.whaty.platform.entity.bean.PrTraineeCourseware;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.TestLoreDir;
import com.whaty.platform.entity.bean.TestLoreInfo;
import com.whaty.platform.entity.bean.TestPaperpolicyInfo;
import com.whaty.platform.entity.bean.TestPaperquestionInfo;
import com.whaty.platform.entity.bean.TestStorequestionInfo;
import com.whaty.platform.entity.bean.TestTestpaperHistory;
import com.whaty.platform.entity.bean.TestTestpaperInfo;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.test.question.QuestionType;
import com.whaty.platform.test.question.TestQuestionType;
import com.whaty.platform.util.XMLParserUtil;
import com.whaty.util.Cookie.WhatyCookieManage;
import com.whaty.util.string.WhatyStrManage;
import com.whaty.util.string.encode.HtmlEncoder;

public class CourseResourcesAction extends MyBaseAction {
	
	//ID
	private String course_id;//课程id
	private String test_id;	//在线测试的id
	private String lore_id;	//题库知识点id
	private String loreDir_id;	//题库当前目录id
	private String fatherDir_id; //父级目录
	private String question_id;//题目id
	private String[] idaaa;//批量删除题目id
	private String[] DANXUANChild;//批量删除单选题目
	private String[] DUOXUANChild;//批量删除多选题目
	private String[] PANDUANChild;//批量删除判断题目
	private String[] TIANKONGChild;//批量删除填空题目
	private String[] WENDAChild;//批量删除问答题目
	private String[] YUEDUChild;//批量删除阅读题目
	private String paper_id;//试卷id
	private String history_id;//已交试卷id
	private String paperUser_id;//答卷学员id
	private String anliNextId;//案例分析的下一题
	
	//对象
	private PeTchCourse peTchCourse;//课程对象
	private InteractionTeachclassInfo interTchInfo;//下载资料对象
	private TestLoreDir rootTestLoreDir; //题库根目录对象
	private TestLoreDir curTestLoreDir; //题库根目录对象
	private TestLoreInfo testLoreInfo;	//题库知识点对象
	private OnlinetestCourseInfo onlineTest;//在线测试对象
	private TestStorequestionInfo questionInfo;//题目对象
	private TestTestpaperInfo paperInfo;//试卷对象
	private TestTestpaperHistory testpaperHistory;//最近一次考试历史信息对象
	private PeAnswers peAnswers;
	
	//List
	private List<InteractionTeachclassInfo> resList;	//下载资料
	private List testInfoList;	//在线测试
	private List createUserList; //在线测试创建者姓名列表
	private List questionList;	//常见问题库列表
	private List subTestLoreDirList;//题库子目录列表
	private List testLoreInfoList;//题库列表信息
	private List questionInfoList;//题库题目列表
	private List urlList;//题库题目查看URL列表
	private List typeStrList;//题库题目类型显示列表
	private List answerList;//题库题目答案列表
	private List testpaperList;//试卷列表
	private List testpaperHistoryList;//考试历史信息列表
	private List testpaperPolicyList;//组卷策略列表
	private List paperquestionList;//试卷已添加试题列表
	private List questionItemList;//试卷未添加试题列表
	private List leftQuestionList;//预览试卷时frame左边显示的题目列表
	private List titleStrList;//预览试卷时frame左边显示的题目标题列表
	private List singleObjectList;//预览单选题信息列表
	private List multiObjectList;//预览多选题信息列表
	private List judgeObjectList;//预览判断题信息列表
	private List blankObjectList;//预览填空题信息列表
	private List answerObjectList;//预览问答题信息列表
	private List comprehensionObjectList;//预览阅读题信息列表
	private List indexList;//试题预览答案标号列表
	private List resultList;//学员答题信息列表
	private List loreNameList;//知识点名列表
	private List numList;//存放阅读理解题型中各种类型题目的数量
	private List courseWareList;
	//添加下载资料start
	private String type;
	private String title;
	private String body;
	private List bodyList;
	private String r_id;
	//添加下载资料end
	
	//添加在线测试start
	private String test_title;
	private String note;
	private String start_date;
	private String start_hour;
	private String start_minute;
	private String start_second;
	private String end_date;
	private String end_hour;
	private String end_minute;
	private String end_second;
	private String status;
	private String isAutoCheck;
	private String isHiddenAnswer;
	private String onlineType;
	//添加在线测试end
	
	//添加题库知识点
	private String lore_active;
	private String lore_name;
	private Date creatDate;
	private String lore_content;
	//添加题库知识点
	
	//添加题库目录
	private String lore_dir_name;
	private String lore_dir_note;
	//添加题库目录
	
	//添加题目
	private String types;
	private String diff;
	private String cognizetype;
	private String referencescore;
	private String referencetime;
	private String answer;
	private String question_score;
	private String studentnote;
	private String teachernote;
	private String anliback;
	private String[] options;
	private String[] answers;
	private String[] purposeVal;
	private int singleNum;
	private int multiNum;
	private int judgeNum;
	private int blankNum;
	private int answerNum;
	//添加题目
	
	//添加试卷
	private String time;
	//添加试卷
	
	//批量导入excel
	private File src;
	private String srcFileName;
	private String srcContentType;
	//批量导入excel
	
	private String msg;//页面显示消息
	private String roleCode;//角色roleCode
	private int charCode; //题目编辑页面答案个数
	private String typeStr;//题目标题中文显示
	private String url;
	private String qNum;//预览试卷的题目号码
	private String userAnswer;//学员的答案
	private List userAnswerList;//学员的阅读题答案
	private String direction;//预览题目导向（next，pre）
	private boolean hasPre;//前面是否还有题目
	private boolean hasNext;//后面是否还有题目
	private String index;//学员答案
	private String[] indexs;//学员多选答案
	private String totalScore;//学员答题总得分
	private String totalNum;//阅读理解题的总数
	private String totalNote;//试卷批注
	
	//search
	private String searchVal;//查询关键字
	private String searchTitle; //按标题查询
	private String searchCreater;//按创建者姓名查询
	
	private String seconds;	//学员学习总的秒数
	private String learnedSeconds; //学员在线学习的时长
	
	//分页
	private int totalPage;
	private int curPage = 1;
	private int totalSize;
	private static int SIZE_PER_PAGE = 10;//每页显示条数
	
	//赵玉晓 begin
	private boolean succ;//是否操作成功
	private boolean hasAns;//是否被回复
	private List ansList;//答案列表
	private PeQuestions question;//问题
	
	//赵玉晓 end
	
	//侯学龙begin
	private List<PeVotePaper> peVotePaperList;  //问卷调查
	
	@Override
	public void initGrid() {

	}

	@Override
	public void setEntityClass() {

	}

	@Override
	public void setServletPath() {

	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	
	public String manage() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourse.class);
		try {
			PeTchCourse ptc = (PeTchCourse)this.getGeneralService().getById(this.getCourse_id());
			this.setPeTchCourse(ptc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "show_index";
	}
	
	public String showIframe() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourse.class);
		try {
			PeTchCourse ptc = (PeTchCourse)this.getGeneralService().getById(this.getCourse_id());
			this.setPeTchCourse(ptc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "show_iframe";
	}
	
	public String ziliaoList() {
		
		this.initZL();
		return "ziliao_list";
	}
	public String showVoteList() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeVotePaper.class);
		DetachedCriteria dcEnumConstByFlagIsvalid = dc.createCriteria(
				"enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		DetachedCriteria enumConstByFlagType = dc.createCriteria(
				"enumConstByFlagType", "enumConstByFlagType");
		dcEnumConstByFlagIsvalid.add(Restrictions.eq("code", "1"));
		
		enumConstByFlagType.add(Restrictions.eq("code", "2"));
		dc.add(Restrictions.like("keywords", "%" + this.getCourse_id().trim()
				+ "%"));

		dc.addOrder(Order.desc("foundDate"));
		try {
			List itiList = this.getGeneralService().getList(dc);
			int size = itiList.size();
			this.setTotalSize(size);
			if(size == 0) {
				this.setTotalPage(1);
			} else if(size % this.SIZE_PER_PAGE == 0) {
				this.setTotalPage(size/this.SIZE_PER_PAGE);
			} else {
				this.setTotalPage(size/this.SIZE_PER_PAGE + 1);
			}
			
			Page p = this.getGeneralService().getByPage(dc, this.SIZE_PER_PAGE, (curPage - 1) * this.SIZE_PER_PAGE);
			itiList = p.getItems();
			this.setPeVotePaperList(itiList);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "showVote_list";
	}
	public String addZL() {
		
		InteractionTeachclassInfo info = new InteractionTeachclassInfo();
		info.setContent(body);
		info.setPublishDate(new Date());
		info.setStatus("1");
		info.setType(type);
		info.setTeachclassId(this.getCourse_id());
		info.setTitle(title);
		
		this.getGeneralService().getGeneralDao().setEntityClass(InteractionTeachclassInfo.class);
		try {
			this.getGeneralService().save(info);
		} catch (EntityException e) {
			this.setMsg("添加资料失败！");
			e.printStackTrace();
		}
		
		this.setMsg("添加资料成功！");
		this.initZL();
		return "ziliao_list";
	}
	
	public String deleteZL() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(InteractionTeachclassInfo.class);
		try {
			InteractionTeachclassInfo info = (InteractionTeachclassInfo) this.getGeneralService().getById(this.getR_id());
			this.getGeneralService().delete(info);
		} catch (EntityException e) {
			this.setMsg("删除资料失败！");
			e.printStackTrace();
		}
		
		this.setMsg("删除资料成功！");
		this.initZL();
		return "ziliao_list";
		
	}
	
	public String viewZL() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(InteractionTeachclassInfo.class);
		try {
			InteractionTeachclassInfo info = (InteractionTeachclassInfo) this.getGeneralService().getById(this.getR_id());
			this.setInterTchInfo(info);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "view";
		
	}
	
	public String editZL() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(InteractionTeachclassInfo.class);
		try {
			InteractionTeachclassInfo info = (InteractionTeachclassInfo) this.getGeneralService().getById(this.getR_id());
			this.setInterTchInfo(info);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "edit";
		
	}
	
	public String doEdit() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(InteractionTeachclassInfo.class);
		try {
			InteractionTeachclassInfo info = (InteractionTeachclassInfo) this.getGeneralService().getById(this.getR_id());
			info.setTitle(title);
			info.setContent(body);
			this.getGeneralService().save(info);
			
		} catch (EntityException e) {
			this.setMsg("修改资料失败！");
			e.printStackTrace();
		}
		
		this.setMsg("修改资料成功！");
		this.initZL();
		return "ziliao_list";
	}
	
	private void initZL() {
		
		this.setUserRoleCode();
		
		this.getGeneralService().getGeneralDao().setEntityClass(InteractionTeachclassInfo.class);
		DetachedCriteria dc = DetachedCriteria.forClass(InteractionTeachclassInfo.class);
		dc.add(Restrictions.eq("teachclassId", this.course_id));
		if(searchVal != null && !"".equals(searchVal)) {
			dc.add(Restrictions.like("title", searchVal, MatchMode.ANYWHERE));
		}
		try {
			List<InteractionTeachclassInfo> list = this.getGeneralService().getList(dc);
			int s = list.size();
			this.setTotalSize(s);
			if(s == 0) {
				this.setTotalPage(1);
			} else if(s % this.SIZE_PER_PAGE == 0) {
				this.setTotalPage(s/this.SIZE_PER_PAGE);
			} else {
				this.setTotalPage(s/this.SIZE_PER_PAGE + 1);
			}
			
			Page p = this.getGeneralService().getByPage(dc, SIZE_PER_PAGE, (curPage - 1) * SIZE_PER_PAGE);
			
			if(p != null) {
				this.setResList(p.getItems());
			}
			
		} catch (EntityException e) {
			this.setMsg("初始化资料列表失败！");
			e.printStackTrace();
		}
		
	}
	
	public String stuManage() throws PlatformException {

		this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourse.class);
		try {
			PeTchCourse ptc = (PeTchCourse)this.getGeneralService().getById(this.getCourse_id());
			this.setPeTchCourse(ptc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		String sec = "0";
		if(!this.insertTraineeCourse(this.getCurTrainee(), this.getPeTchCourse())){
			sec=this.getOutTime(this.getPeTchCourse(), this.getCurTrainee());
		}
		this.setSeconds(sec);
		
		return "show_stuindex";
		
//		//临时的
//		this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourse.class);
//		PeTchCourse ptc = null;
//		try {
//			ptc = (PeTchCourse)this.getGeneralService().getById(this.getCourse_id());
//			this.setPeTchCourse(ptc);
//		} catch (EntityException e) {
//			e.printStackTrace();
//		}
//		
//		String sec = this.getOutTime(this.getPeTchCourse(), this.getCurTrainee());
//		this.setSeconds(sec);
//		
//		HttpServletRequest request = ServletActionContext.getRequest();
//		
//		UserSession userSession = (UserSession) ActionContext.getContext()
//		.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		if(userSession==null)
//		{
//			return "input";
//		}
//
//		try {
//			this.setM4Session(userSession.getSsoUser(), "0","student");
//		} catch (PlatformException e) {
//			e.printStackTrace();
//		}
//
//		Object o1 = request.getSession().getAttribute(
//				"student_eduplatform_user");
//		Object o2 = request.getSession().getAttribute(
//				"student_eduplatform_priv");
//		Student session_student;
//		StudentPriv studentPriv;
//		if (o1 != null)
//			session_student = (Student) o1;
//		else
//			session_student = (Student) (request.getSession()
//					.getAttribute("eduplatform_user"));
//
//		if (o2 != null)
//			studentPriv = (StudentPriv) o2;
//		else
//			studentPriv = (StudentPriv) (request.getSession()
//					.getAttribute("eduplatform_priv"));
//		
//		PlatformFactory platformFactory = PlatformFactory.getInstance();
//		StudentOperationManage studentOperationManage = platformFactory
//				.creatStudentOperationManage(studentPriv);
//
//		ManagerPriv includePriv = studentOperationManage.getNormalEntityManagePriv();
//
//		InteractionUserPriv interactionUserPriv = studentOperationManage.getInteractionUserPriv(studentPriv.getStudentId(), course_id);
//		request.getSession().setAttribute("interactionUserPriv",
//				interactionUserPriv);
//
//		OpenCourse openCourse = new OracleOpenCourse();
//
//		openCourse.setId(course_id);
//		
//		try {
//			openCourse.setBzzCourse(ptc);
//		} catch (Exception e) {
//			e.printStackTrace();
//			this.setMsg(e.getMessage());
//			return "msg";
//		}
//
//		request.getSession().removeAttribute("courseId");
//		request.getSession().setAttribute("courseId", course_id);
//
//		request.getSession().removeAttribute("openCourse");
//		request.getSession().setAttribute("openCourse", openCourse);
//		request.getSession().setAttribute("userType", "student");
//		request.getSession().setAttribute("First", "1");
//		
//		request.getSession().removeAttribute("opencourseId");
//		request.getSession().setAttribute("opencourseId", course_id);  //进入学习使用
//		
//		return "show_stuindex";
		
	}
	
//	/**
//	 * 权限设置（临时的）
//	 * 
//	 * @param user
//	 * @param loginType
//	 * @param type
//	 * @throws PlatformException
//	 */
//	private void setM4Session(SsoUser user, String loginType, String type) throws PlatformException {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		if (SsoConstant.SSO_TEACHER.equals(loginType)) {
//			PlatformFactory factory = PlatformFactory.getInstance();
//			PlatformManage platformManage = factory.createPlatformManage();
//			EntityUser euser = platformManage.getEntityUser(user.getId(), type);
//			request.getSession().setAttribute("eduplatform_user", euser);
//			TeacherPriv teacherPriv = factory.getTeacherPriv(user.getId());
//			request.getSession().setAttribute("eduplatform_priv", teacherPriv);
//		}
//		else if(SsoConstant.SSO_STUDENT.equals(loginType)){
//			PlatformFactory factory=PlatformFactory.getInstance();
//			PlatformManage platformManage=factory.createPlatformManage();
//			EntityUser euser=platformManage.getEntityUser(user.getId(),type);
//			request.getSession().setAttribute("eduplatform_user",euser);
//			StudentPriv studentPriv=factory.getStudentPriv(user.getId());
//			request.getSession().setAttribute("eduplatform_priv",studentPriv);
//		}
//	}
	
	/**
	 * 判断学员是否第一次学习，如果是，将该课程加入学员的课程中（PrTraineeCourse）
	 * @param trainee
	 * @param course
	 * @return 如果第一次学习返回true,否则返回false
	 */
	private boolean insertTraineeCourse(PeTrainee trainee,PeTchCourse course){
		DetachedCriteria dc=DetachedCriteria.forClass(PrTchTraineeCourse.class);
		dc.createCriteria("peTchCourse","peTchCourse");
		dc.createCriteria("peTrainee","peTrainee");
		dc.add(Restrictions.eq("peTchCourse", course));
		dc.add(Restrictions.eq("peTrainee", trainee));
		this.getGeneralService().getGeneralDao().setEntityClass(PrTchTraineeCourse.class);
		List list_t=new LinkedList();
		boolean flag=false;//默认是不存在的
		try {
			list_t=this.getGeneralService().getList(dc);
			if(list_t!=null&&list_t.size()>0){
				flag=true;//存在该课程
				PrTchTraineeCourse ptc=(PrTchTraineeCourse) list_t.get(0);
				ptc.setLastDate(new Date());
				this.getGeneralService().save(ptc);
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==false){//如果该学生课程列表中没有该课程
			PrTchTraineeCourse ptc=new PrTchTraineeCourse();
			ptc.setPeTchCourse(course);
			ptc.setPeTrainee(trainee);
			ptc.setOnlineTime("0");
			ptc.setLastDate(new Date());
			try {
				ptc=(PrTchTraineeCourse) this.getGeneralService().save(ptc);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.addCourseWare(trainee, course);
			return true;
		}
		return false;
		
	}
	
	public boolean addCourseWare(PeTrainee trainee,PeTchCourse course){
		SsoUser sso=trainee.getSsoUser();
		List list=new LinkedList();//课件列表
		
		String sql="select ptcw.id from pe_tch_courseware ptcw join enum_const enu on ptcw.version=enu.id where ptcw.fk_course_id=:ids1 and enu.code=("+
					"select max(enu.code) from pe_tch_courseware ptcw join enum_const enu on ptcw.version=enu.id  where ptcw.fk_course_id=:ids)";
		Map map=new HashMap();
		map.put("ids1", course.getId());
		map.put("ids", course.getId());
		List list_id=new LinkedList();
		try {
			list_id=this.getGeneralService().getBySQL(sql, map);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(list_id!=null&&list_id.size()>0){
			for(int i=0;i<list_id.size();i++){
				PeTchCourseware ware=null;
				try {
					this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourseware.class);
					ware=(PeTchCourseware) this.getGeneralService().getById((String)list_id.get(i));
				} catch (EntityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				PrTraineeCourseware ptcw=new PrTraineeCourseware();
				ptcw.setPeTchCourseware(ware);
				ptcw.setPeTrainee(trainee);
				this.getGeneralService().getGeneralDao().setEntityClass(PrTraineeCourseware.class);
				try {
					ptcw=(PrTraineeCourseware) this.getGeneralService().save(ptcw);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				TrainingCourseStudent tcs=new TrainingCourseStudent();
				tcs.setGetDate(new Date());
				tcs.setPercent(0d);
				tcs.setScore(0d);
				tcs.setPeTchCourseware(ware);
				tcs.setSsoUser(sso);
				this.getGeneralService().getGeneralDao().setEntityClass(TrainingCourseStudent.class);
				try {
					tcs=(TrainingCourseStudent) this.getGeneralService().save(tcs);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 获取当前学员学习课程的学习时间
	 * 
	 * @param ptc
	 * @param pt
	 * @return
	 */
	private String getOutTime(PeTchCourse ptc, PeTrainee pt) {
		
		this.getGeneralService().getGeneralDao().setEntityClass(PrTchTraineeCourse.class);
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchTraineeCourse.class);
		dc.add(Restrictions.eq("peTchCourse", ptc));
		dc.add(Restrictions.eq("peTrainee", pt));
		
		List list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		if(list != null && list.size() > 0) {
			PrTchTraineeCourse pttc = (PrTchTraineeCourse)list.get(0);
			return pttc.getOnlineTime();
		}
		return "0";
		
	}
	
	/**
	 * 根据课程id获取该课程的课件列表
	 * 
	 * @return
	 */
	public String showCourseWareList() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourseware.class);
		DetachedCriteria dc=DetachedCriteria.forClass(PeTchCourseware.class);
		dc.createCriteria("peTchCourse", "peTchCourse");
		dc.createCriteria("enumConstByVersion", "enumConstByVersion");
		dc.add(Restrictions.eq("peTchCourse.id", this.course_id));
		dc.addOrder(Order.asc("enumConstByVersion.code"));
		try {
			List list = this.getGeneralService().getList(dc);
			if(list != null && list.size() > 0) {
				this.setCourseWareList(list);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		try {
			if(this.courseWareList != null && this.courseWareList.size() > 0) {
				UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
				SsoUser ssoUser= us.getSsoUser();
				for(Object o : courseWareList) {
					PeTchCourseware ware = (PeTchCourseware)o;
					
					this.getGeneralService().getGeneralDao().setEntityClass(TrainingCourseStudent.class);
					DetachedCriteria dct=DetachedCriteria.forClass(TrainingCourseStudent.class);
					dct.add(Restrictions.eq("ssoUser", ssoUser));
					dct.add(Restrictions.eq("peTchCourseware", ware));
					List tList = this.getGeneralService().getList(dct);
					if(tList == null || tList.size() <= 0) {
						TrainingCourseStudent tcs = new TrainingCourseStudent();
						tcs.setPeTchCourseware(ware);
						tcs.setSsoUser(ssoUser);
						this.getGeneralService().save(tcs);
					}
				}
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "course_ware_list";
		
	}
	
	//zhaoyuxiao begin
	public String queryQuests(){
		String sql_count="select id from pe_questions where fk_course_id=:ids";
		Map map_count=new HashMap();
		map_count.put("ids", this.getCourse_id());
		List list_count=new LinkedList();
		try {
			list_count=this.getGeneralService().getBySQL(sql_count, map_count);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int size=0;
		if(list_count!=null){
			size=list_count.size();
		}
		this.setTotalSize(size);
		if(size==0){
			this.setTotalPage(1);
		}else{
			if(size%this.SIZE_PER_PAGE==0){
				this.setTotalPage(size/this.SIZE_PER_PAGE);
			}else{
				this.setTotalPage(size/this.SIZE_PER_PAGE+1);
			}
		}
		DetachedCriteria dc=DetachedCriteria.forClass(PeQuestions.class);
		dc.createCriteria("peTchCourse","peTchCourse");
		dc.createCriteria("peTrainee","peTrainee");
		dc.add(Restrictions.ne("peTchCourse.name", "留言板"));
		dc.add(Restrictions.eq("peTchCourse.id", this.getCourse_id()));
		dc.addOrder(Order.desc("createDate"));
		List list_ques=new LinkedList();
		try {
			Page page=this.getGeneralService().getByPage(dc, this.SIZE_PER_PAGE, (this.getCurPage()-1)*this.SIZE_PER_PAGE);
			list_ques=page.getItems();
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list_ques!=null&&list_ques.size()>0){
			this.setQuestionList(list_ques);
		}
		return "questList";
	}
	public String addQuestion(){
		return "addQues";
	}
	public String addQuestExe(){
		boolean flag=true;
		
		PeQuestions ques=new PeQuestions();
		ques.setCreateDate(new Date());
		ques.setDetail(this.getBody());
		ques.setEnumConstByFlagSolve(this.getMyListService().getEnumConstByNamespaceCode("FlagSolve", "0"));//未解决
		
		PeTchCourse ptc=new PeTchCourse();
		this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourse.class);
		try {
			ptc=(PeTchCourse) this.getGeneralService().getById(this.getCourse_id());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		}
		
		ques.setPeTchCourse(ptc);
		
		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		PeTrainee ee=new PeTrainee();
		DetachedCriteria dc=DetachedCriteria.forClass(PeTrainee.class);
		dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
		List list_ee=new LinkedList();
		try {
			list_ee=this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		}
		if(list_ee!=null&&list_ee.size()>0){
			ee=(PeTrainee) list_ee.get(0);
			ques.setPeTrainee(ee);
		}
		
		ques.setTitle(this.getTitle());
		ques.setReplyNum("0");
		ques.setOppose((long)0);
		ques.setScore((long)0);
		ques.setSupport((long)0);
		this.getGeneralService().getGeneralDao().setEntityClass(PeQuestions.class);
		try {
			ques=(PeQuestions) this.getGeneralService().save(ques);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		}
		this.setSucc(flag);
		if(flag){
			this.setMsg("提问成功");
		}else{
			this.setMsg("提问失败");
		}
		
		this.initQuestion();
		return "show_question";
	}
	
	public String quesDetail(){
		String id=this.getIds();
		PeQuestions ques=new PeQuestions();
		try {
			this.getGeneralService().getGeneralDao().setEntityClass(PeQuestions.class);
			DetachedCriteria dc=DetachedCriteria.forClass(PeQuestions.class);
			dc.createCriteria("enumConstByFlagSolve", "enumConstByFlagSolve", DetachedCriteria.LEFT_JOIN);
			dc.add(Restrictions.eq("id", id));
			List list = this.getGeneralService().getList(dc);
			if(list != null && list.size() > 0) {
				ques = (PeQuestions)list.get(0);
			}
//			ques=(PeQuestions) this.getGeneralService().getById(id);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(ques!=null){
			if(ques.getEnumConstByFlagSolve().getCode().equals("1")){//已解决
				this.setHasAns(true);
				
				PeAnswers ans=new PeAnswers();
				StringBuilder strb=new StringBuilder();
				strb.append(" select ans.id a_id,ans.detail a_detail,nvl(mgr.true_name,tcr.true_name) a_name,ans.create_date a_date,ans.fk_ques_id  from pe_answers ans   ");
				strb.append(" join sso_user sso on sso.id=ans.fk_sso_user                                                                                                    ");
				strb.append(" left join pe_manager mgr on mgr.fk_sso_user_id=sso.id                                                                                          ");
				strb.append(" left join pe_teacher tcr on tcr.fk_sso_user_id=sso.id                                                                                          ");
				strb.append(" where ans.fk_ques_id=:ids                                                                                                                          ");
				
				Map map=new HashMap();
				map.put("ids", ques.getId());
				List list_a=new LinkedList();
				try {
					list_a=this.getGeneralService().getBySQL(strb.toString(), map);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.setAnsList(list_a);
				
				this.getGeneralService().getGeneralDao().setEntityClass(PeAnswers.class);
				DetachedCriteria dcp=DetachedCriteria.forClass(PeAnswers.class);
				dcp.add(Restrictions.eq("peQuestions", ques));
				try {
					List list = this.getGeneralService().getList(dcp);
					if(list != null && list.size() > 0) {
						PeAnswers peAnswers = (PeAnswers)list.get(0);
						this.setPeAnswers(peAnswers);
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}
			}
			else{
				this.setHasAns(false);//未被回复
			}
		}
		if(!hasAns && ques.getPeTrainee() == this.getCurTrainee()) {
			this.setSucc(true);
		} else {
			this.setSucc(false);
		}
		
		this.setQuestion(ques);
		return "ques_detail";
	}
	/**
	 * 编辑问题
	 */
	public String edit(){
		String id=this.getIds();
		PeQuestions ques=new PeQuestions();
		this.getGeneralService().getGeneralDao().setEntityClass(PeQuestions.class);
		try {
			ques=(PeQuestions) this.getGeneralService().getById(id);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setQuestion(ques);
		return "editQuestion";
	}
	
	/**
	 * 保存修改
	 * @return
	 */
	public String editExe(){
		boolean flag=true;
		
		String id=this.getIds();
		String title=this.getTitle();
		String detail=this.getBody();

		PeQuestions ques=new PeQuestions();
		this.getGeneralService().getGeneralDao().setEntityClass(PeQuestions.class);
		try {
			ques=(PeQuestions) this.getGeneralService().getById(id);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			flag=false;
			System.out.println("查找问题失败");
			e.printStackTrace();
		}
		ques.setTitle(title);
		ques.setDetail(detail);
		try {
			ques=(PeQuestions) this.getGeneralService().save(ques);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			flag=false;
			System.out.println("保存问题失败");
			e.printStackTrace();
		}
		this.setSucc(flag);
		if(flag){
			this.setMsg("保存成功");
		}else{
			this.setMsg("保存失败");
		}
		this.setCourse_id(ques.getPeTchCourse().getId());
		return "questReslt";
	}
	
	/**
	 * 未回复删除问题
	 * @return
	 */
	public String delexe(){
		boolean flag=true;
		String id=this.getIds();
		
		PeQuestions ques=new PeQuestions();
		this.getGeneralService().getGeneralDao().setEntityClass(PeQuestions.class);
		try {
			ques=(PeQuestions) this.getGeneralService().getById(id);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			flag=false;
			System.out.println("查找问题 失败");
			e.printStackTrace();
		}
		this.setCourse_id(ques.getPeTchCourse().getId());
		
		if(ques.getEnumConstByFlagSolve().getCode().equals("1")){//如果问题已回答
			this.setSucc(false);
			this.setMsg("问题已回复，不能删除");
			return "questReslt";
		}
		String sql="delete from pe_questions where id=:ids";
		Map map=new HashMap();
		map.put("ids", id);
		int i=0;
		try {
			i=this.getGeneralService().executeBySQL(sql, map);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			flag=false;
			System.out.println("删除问题失败");
			e.printStackTrace();
		}
		if(i<1||!flag){
			this.setSucc(false);
			this.setMsg("删除失败");
		}else{
			this.setSucc(true);
			this.setMsg("删除成功");
		}
		return "questReslt";
	}
	
	public String quesList(){
		String id=this.getIds();
		PeQuestions ques=new PeQuestions();
		this.getGeneralService().getGeneralDao().setEntityClass(PeQuestions.class);
		try {
			ques=(PeQuestions) this.getGeneralService().getById(id);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String course_id=ques.getPeTchCourse().getId();
		this.setCourse_id(course_id);
		return this.queryQuests();
	}
	//zhaoyuxiao end
	
	/**
	 * 把学员学习的总时长存入数据库
	 * 
	 * @param ptc
	 * @param pt
	 */
	public String computTime() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourse.class);
		try {
			PeTchCourse ptc = (PeTchCourse)this.getGeneralService().getById(this.getCourse_id());
			this.setPeTchCourse(ptc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		PeTchCourse ptc = this.getPeTchCourse();
		PeTrainee pt = this.getCurTrainee();
		this.getGeneralService().getGeneralDao().setEntityClass(PrTchTraineeCourse.class);
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchTraineeCourse.class);
		dc.add(Restrictions.eq("peTchCourse", ptc));
		dc.add(Restrictions.eq("peTrainee", pt));
		
		List list = null;
		try {
			list = this.getGeneralService().getList(dc);
			if(list != null && list.size() > 0) {
				PrTchTraineeCourse pttc = (PrTchTraineeCourse)list.get(0);
				long oldTime = Long.parseLong(pttc.getOnlineTime());
				long newTime = Long.parseLong(this.getLearnedSeconds());
				long total = oldTime + newTime;
				pttc.setOnlineTime("" + total);
				this.getGeneralService().save(pttc);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "compute";
		
	}
	
	/**
	 * 在线测试
	 * 
	 * @return
	 */
	public String onlineTest() {
		
		this.getOnlineTestInfo();
		return "online_test";
	}
	
	/**
	 * 添加在线测试
	 * 
	 * @return
	 */
	public String addTest() {
		
		UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		
		OnlinetestCourseInfo oci = new OnlinetestCourseInfo();
		oci.setCreatdate(new Date());
		oci.setCreatuser(ssoUser.getId());
		oci.setGroupId(this.getCourse_id());
		oci.setIsautocheck(isAutoCheck);
		oci.setIshiddenanswer(isHiddenAnswer);
		oci.setStatus(status);
		oci.setNote(note);
		oci.setStartDate(start_date + " " + start_hour + ":" + start_minute + ":" + start_second);
		oci.setEndDate(end_date + " " + end_hour + ":" + end_minute + ":" + end_second);
		oci.setTitle(test_title);
		oci.setBatchId(onlineType);
		
		DetachedCriteria dco = DetachedCriteria.forClass(OnlinetestCourseInfo.class);
		this.getGeneralService().getGeneralDao().setEntityClass(OnlinetestCourseInfo.class);
		try {
			this.getGeneralService().save(oci);
		} catch (EntityException e) {
			this.setMsg("添加失败！");
			e.printStackTrace();
		}
		this.setMsg("添加成功！");
		
		this.getOnlineTestInfo();
		return "online_test";
		
	}
	
	/**
	 * 修改在线测试状态
	 * 
	 * @return
	 */
	public String changestatus() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(OnlinetestCourseInfo.class);
		try {
			OnlinetestCourseInfo oci = (OnlinetestCourseInfo)this.getGeneralService().getById(test_id);
			if(oci != null) {
				oci.setStatus(status);
				this.getGeneralService().save(oci);
				this.setMsg("修改状态成功！");
			}
		} catch (EntityException e) {
			this.setMsg("修改状态失败！");
			e.printStackTrace();
		}
		
		this.getOnlineTestInfo();
		return "online_test";
	}
	
	/**
	 * 修改试卷状态
	 * 
	 * @return
	 */
	public String changePaperStatus() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperInfo.class);
		try {
			TestTestpaperInfo paper = (TestTestpaperInfo)this.getGeneralService().getById(paper_id);
			if(paper != null) {
				paper.setStatus(status);
				this.getGeneralService().save(paper);
				this.setMsg("修改试卷状态成功！");
			}
		} catch (EntityException e) {
			this.setMsg("修改试卷状态失败！");
			e.printStackTrace();
		}
		
		this.initTestpaper();
		return "testpaper_list";
		
	}
	
	/**
	 * 删除在线测试
	 * 
	 * @return
	 */
	public String deleteTest() {
		
		DetachedCriteria dco = DetachedCriteria.forClass(OnlinetestCourseInfo.class);
		this.getGeneralService().getGeneralDao().setEntityClass(OnlinetestCourseInfo.class);
		try {
			OnlinetestCourseInfo oci = (OnlinetestCourseInfo)this.getGeneralService().getById(test_id);
			if(oci != null) {
				this.getGeneralService().delete(oci);
				this.setMsg("删除成功！");
			}
		} catch (EntityException e) {
			this.setMsg("删除失败！");
			e.printStackTrace();
		}
		
		this.getOnlineTestInfo();
		return "online_test";
		
	}
	
	/**
	 * 转向修改在线测试页面
	 * 
	 * @return
	 */
	public String editTest() {
		
		DetachedCriteria dco = DetachedCriteria.forClass(OnlinetestCourseInfo.class);
		this.getGeneralService().getGeneralDao().setEntityClass(OnlinetestCourseInfo.class);
		try {
			OnlinetestCourseInfo oci = (OnlinetestCourseInfo)this.getGeneralService().getById(test_id);
			if(oci != null) {
				this.setOnlineTest(oci);
				
				String startDate = oci.getStartDate();
				String endDate = oci.getEndDate();
				this.start_date = startDate.substring(0,10);
				this.start_hour = startDate.substring(11,13);
				this.start_minute = startDate.substring(14,16);
				this.start_second = startDate.substring(17,19);
				
				this.end_date = endDate.substring(0,10);
				this.end_hour = endDate.substring(11,13);
				this.end_minute = endDate.substring(14,16);
				this.end_second = endDate.substring(17,19);
				
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		this.setUserRoleCode();
		return "test_edit";
		
	}
	
	/**
	 * 执行修改在线测试
	 * 
	 * @return
	 */
	public String doEditTest() {
		
		UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();

		DetachedCriteria dco = DetachedCriteria.forClass(OnlinetestCourseInfo.class);
		this.getGeneralService().getGeneralDao().setEntityClass(OnlinetestCourseInfo.class);
		try {
			OnlinetestCourseInfo oci = (OnlinetestCourseInfo)this.getGeneralService().getById(test_id);
			if(oci != null) {
				oci.setCreatdate(new Date());
				oci.setCreatuser(ssoUser.getId());
				oci.setGroupId(this.getCourse_id());
				oci.setIsautocheck(isAutoCheck);
				oci.setIshiddenanswer(isHiddenAnswer);
				oci.setStatus(status);
				oci.setNote(note);
				oci.setStartDate(start_date + " " + start_hour + ":" + start_minute + ":" + start_second);
				oci.setEndDate(end_date + " " + end_hour + ":" + end_minute + ":" + end_second);
				oci.setTitle(test_title);
				this.getGeneralService().save(oci);
				this.setMsg("修改成功！");
			}
		} catch (EntityException e) {
			this.setMsg("修改失败！");
			e.printStackTrace();
		}
		
		this.getOnlineTestInfo();
		return "online_test";
		
	}
	
	/**
	 * 查看在线测试
	 * 
	 * @return
	 */
	public String testView() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(OnlinetestCourseInfo.class);
		try {
			OnlinetestCourseInfo oci = (OnlinetestCourseInfo)this.getGeneralService().getById(test_id);
			if(oci != null) {
				this.setOnlineTest(oci);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "test_view";
		
	}
	
	/**
	 * 进入测试试卷列表
	 * 
	 * @return
	 */
	public String enterTestpaper() {
		
		this.initTestpaper();
		return "testpaper_list";
		
	}
	
	/**
	 * 转向常见问题库
	 * 
	 * @return
	 */
	public String questionList() {
		
		this.initQuestion();
		return "show_question";
		
	}
	
	/**
	 * 转向题库管理
	 * 
	 * @return
	 */
	public String loreList() {
		
		this.initLore();
		return "lore_list";
		
	}
	
	/**
	 * 添加知识点
	 * 
	 * @return
	 */
	public String loreAdd() {
		
		UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestLoreDir.class);
		try {
			TestLoreDir dir = (TestLoreDir)this.getGeneralService().getById(this.getLoreDir_id());
			
			this.getGeneralService().getGeneralDao().setEntityClass(TestLoreInfo.class);
			if(this.checkLore(this.lore_name, dir)) {
				TestLoreInfo lore = new TestLoreInfo();
				lore.setActive(lore_active);
				lore.setContent(lore_content);
				lore.setCreatdate(creatDate);
				lore.setName(lore_name);
				lore.setSsoUser(ssoUser);
				lore.setTestLoreDir(dir);
				this.getGeneralService().save(lore);
				
				this.setMsg("新知识点添加成功！");
			} else {
				this.setMsg("此目录下有同名的知识点！");
			}
			
		} catch (EntityException e) {
			this.setMsg("新知识点添加失败！");
			e.printStackTrace();
		}
		
		this.initLore();
		return "lore_list";
		
	}
	
	/**
	 * 检查知识点是否同名
	 * 
	 * @param loreName
	 * @param dir
	 * @return
	 */
	private boolean checkLore(String loreName, TestLoreDir dir) {
		
		boolean flag = false;
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestLoreInfo.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestLoreInfo.class);
		dct.add(Restrictions.eq("name", loreName));
		dct.createCriteria("testLoreDir", "testLoreDir");
		dct.add(Restrictions.eq("testLoreDir.id", dir.getId()));
		if(this.lore_id != null && !"".equals(this.lore_id)) {
			dct.add(Restrictions.not(Restrictions.eq("id", this.lore_id)));
		}
		
		try {
			List list = this.getGeneralService().getList(dct);
			if(list != null && list.size() > 0) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return flag;
		
	}
	
	/**
	 * 添加题库目录
	 * 
	 * @return
	 */
	public String loreDirAdd() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestLoreDir.class);
		
		if(this.checkLoreDir(this.getLore_dir_name(), this.getFatherDir_id())) {
			TestLoreDir dir = new TestLoreDir();
			dir.setCreatdate(creatDate);
			dir.setFatherdir(this.getFatherDir_id());
			dir.setGroupId(this.getCourse_id());
			dir.setName(this.getLore_dir_name());
			dir.setNote(this.getLore_dir_note());
			try {
				this.getGeneralService().save(dir);
				this.setMsg("添加新目录成功！");
			} catch (EntityException e) {
				this.setMsg("添加新目录失败！");
				e.printStackTrace();
			}
		} else {
			this.setMsg("父目录下存在同名的目录！");
		}
		
		this.initLore();
		return "lore_list";
		
	}
	
	/**
	 * 检查此目录下是否有同名的目录
	 * 
	 * @param dirName
	 * @param fatherDir
	 * @return
	 */
	private boolean checkLoreDir(String dirName, String fatherDir) {
		
		boolean flag = false;
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestLoreDir.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestLoreDir.class);
		dct.add(Restrictions.eq("name", dirName));
		dct.add(Restrictions.eq("fatherdir", fatherDir));
		if(this.loreDir_id != null && !"".equals(this.loreDir_id)) {
			dct.add(Restrictions.not(Restrictions.eq("id", this.loreDir_id)));
		}
		
		try {
			List list = this.getGeneralService().getList(dct);
			if(list != null && list.size() > 0) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return flag;
		
	}
	
	/**
	 * 修改题库目录
	 * 
	 * @return
	 */
	public String loreDirEdit() {
		
		this.setUserRoleCode();
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestLoreDir.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestLoreDir.class);
		try {
			TestLoreDir dir = (TestLoreDir)this.getGeneralService().getById(this.getLoreDir_id());
			if(dir != null) {
				this.setCurTestLoreDir(dir);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "lore_dir_edit";
		
	}
	
	/**
	 * 修改题库目录
	 * 
	 * @return
	 */
	public String doLoreDirEdit() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestLoreDir.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestLoreDir.class);
		try {
			TestLoreDir dir = (TestLoreDir)this.getGeneralService().getById(this.getLoreDir_id());
			if(dir != null) {
				dir.setName(lore_dir_name);
				dir.setNote(lore_dir_note);
				dir.setCreatdate(this.creatDate);
			}
			
			this.getGeneralService().save(dir);
			this.setMsg("修改目录成功！");
		} catch (EntityException e) {
			this.setMsg("修改目录失败！");
			e.printStackTrace();
		}
		
		this.setLoreDir_id(this.fatherDir_id);
		this.initLore();
		return "lore_list";
		
	}
	
	/**
	 * 删除题库目录
	 * 
	 * @return
	 */
	public String loreDirDelete() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestLoreDir.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestLoreDir.class);
		try {
			TestLoreDir dir = (TestLoreDir)this.getGeneralService().getById(this.getLoreDir_id());
			if(dir != null) {
				this.getGeneralService().delete(dir);
			}
			
			this.setMsg("删除目录成功！");
		} catch (EntityException e) {
			this.setMsg("删除目录失败！");
			e.printStackTrace();
		}
		
		this.setLoreDir_id(this.fatherDir_id);
		this.initLore();
		return "lore_list";
		
	}
	
	/**
	 * 查看题库知识点信息
	 * 
	 * @return
	 */
	public String viewLoreInfo() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestLoreInfo.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestLoreInfo.class);
		try {
			TestLoreInfo info = (TestLoreInfo)this.getGeneralService().getById(this.lore_id);
			if(info != null) {
				this.setTestLoreInfo(info);
			}
			
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "view_lore_info";
		
	}
	
	/**
	 * 删除题库知识点
	 * 
	 * @return
	 */
	public String loreDelete() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestLoreInfo.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestLoreInfo.class);
		try {
			TestLoreInfo info = (TestLoreInfo)this.getGeneralService().getById(this.lore_id);
			if(info != null) {
				this.getGeneralService().delete(info);
			}
			
			this.setMsg("删除题库知识点成功！");
		} catch (EntityException e) {
			this.setMsg("删除题库知识点失败！");
			e.printStackTrace();
		}
		
		this.setLoreDir_id(this.fatherDir_id);
		this.initLore();
		return "lore_list";
		
	}
	
	/**
	 * 修改题库知识点
	 * 
	 * @return
	 */
	public String loreEdit() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestLoreInfo.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestLoreInfo.class);
		try {
			TestLoreInfo info = (TestLoreInfo)this.getGeneralService().getById(this.lore_id);
			if(info != null) {
				this.setTestLoreInfo(info);
			}
			
		} catch (EntityException e) {
			e.printStackTrace();
		}
	
		return "lore_edit";
		
	}
	
	/**
	 * 执行修改题库知识点操作
	 * 
	 * @return
	 */
	public String doLoreEdit() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestLoreInfo.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestLoreInfo.class);
		try {
			TestLoreInfo info = (TestLoreInfo)this.getGeneralService().getById(this.lore_id);
			if(info != null) {
				info.setName(lore_name);
				info.setCreatdate(creatDate);
				info.setContent(lore_content);
				this.getGeneralService().save(info);
			}
			
			this.setMsg("修改题库知识点成功！");
		} catch (EntityException e) {
			this.setMsg("修改题库知识点失败！");
			e.printStackTrace();
		}
		
		this.setLoreDir_id(this.fatherDir_id);
		this.initLore();
		return "lore_list";
		
	}
	
	/**
	 * 进入题库
	 * 
	 * @return
	 */
	public String enterLore() {
		
		this.initLoreQuestion();
		return "to_lore";
		
	}
	
	/**
	 * 转向添加题目页面
	 * 
	 * @return
	 */
	public String toQuestionAdd() {
		
		return "to_question_add";
	}
	
	/**
	 * 添加题目
	 * 
	 * @return
	 */
	public String questionAdd() {
		
		if(this.checkQuestion()) {
			
			TestStorequestionInfo info = new TestStorequestionInfo();
			info.setCognizetype(cognizetype);
			info.setCreatdate(new Date());
			info.setDiff(diff);
			info.setLore(lore_id);
			info.setKeyword("keyword");
			info.setReferencescore(referencescore);
			info.setReferencetime(referencetime);
			info.setTitle(title);
			info.setPurpose("ZUOYE|KAOSHI");
			info.setType(type);
			
			UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
			SsoUser ssoUser = userSession.getSsoUser();
			info.setCreatuser(ssoUser.getLoginId());
			
			String xml = "";
			if(type.equalsIgnoreCase(TestQuestionType.DANXUAN)) {
				
				xml = "<question><body>" + HtmlEncoder.encode(this.body) + "</body><select>";
				for(int i=0; i<options.length; i++) {
					int charCode = i + 65;		//选项字母的ASCII码
					String index = String.valueOf((char)charCode);	//将ASCII码转换成字母
					xml = xml + "<item><index>" + HtmlEncoder.encode(index) + "</index><content>" + HtmlEncoder.encode(options[i]) + "</content></item>";
				}
				
				xml = xml + "</select><answer>" + HtmlEncoder.encode(answer) + "</answer></question>";
				
			} else if(type.equalsIgnoreCase(TestQuestionType.DUOXUAN)) {
				
				xml = "<question><body>" + HtmlEncoder.encode(this.body) + "</body><select>";
				for(int i=0; i<options.length; i++) {
					int charCode = i + 65;		//选项字母的ASCII码
					String index = String.valueOf((char)charCode);	//将ASCII码转换成字母
					xml = xml + "<item><index>" + HtmlEncoder.encode(index) + "</index><content>" + HtmlEncoder.encode(options[i]) + "</content></item>";
				}
				
				String ans = "";
				for(int i=0; i<answers.length; i++) {
					ans = ans + answers[i] + "|";
				}
				
				xml = xml + "</select><answer>" + HtmlEncoder.encode(ans) + "</answer></question>";
				
			} else if(type.equalsIgnoreCase(TestQuestionType.PANDUAN) || type.equalsIgnoreCase(TestQuestionType.WENDA)) {
				
				xml = "<question><body>" + HtmlEncoder.encode(body) + "</body><answer>" + HtmlEncoder.encode(answer) + "</answer></question>";
				
			}
			
			info.setQuestioncore(xml);
			
			this.getGeneralService().getGeneralDao().setEntityClass(TestStorequestionInfo.class);
			try {
				this.getGeneralService().save(info);
				this.setMsg("添加题目成功！");
				
				if(this.types != null && "1".equals(this.types)) {
					String nextJsp = "store_question_single.jsp";
					if(type.equalsIgnoreCase(TestQuestionType.DANXUAN)) {
						nextJsp = "store_question_single.jsp";
					} else if(type.equalsIgnoreCase(TestQuestionType.DUOXUAN)) {
						nextJsp = "store_question_multi.jsp";
					} else if(type.equalsIgnoreCase(TestQuestionType.PANDUAN)) {
						nextJsp = "store_question_judge.jsp";
					} else if(type.equalsIgnoreCase(TestQuestionType.TIANKONG)) {
						nextJsp = "store_question_blank.jsp";
					} else if(type.equalsIgnoreCase(TestQuestionType.WENDA)) {
						nextJsp = "store_question_answer.jsp";
					} else if(type.equalsIgnoreCase(TestQuestionType.YUEDU)) {
						nextJsp = "store_question_comprehension.jsp";
					}
					String url = nextJsp + "?lore_id="+this.lore_id+"&fatherDir_id="+this.fatherDir_id+"&type=" + this.type;
					this.setUrl(url);
					return "to_question_add_next";
				}
				
			} catch (EntityException e) {
				this.setMsg("添加题目失败！");
				e.printStackTrace();
			}
			
		} else {
			this.setMsg("同一课程下题目名称不能重复，请重新录入题目名称。");
		}
		
		this.initLoreQuestion();
		return "to_lore_save";
		
	}
	
	/**
	 * 转向添加案例分析（阅读）题目页面
	 * 
	 * @return
	 */
	public String toCompQuestionAdd() {
		
		UserSession userSession = (UserSession) ActionContext.getContext()
			.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
	
		HttpServletRequest request = ServletActionContext.getRequest ();
		HttpSession session = request.getSession();
		
		if(session.getAttribute("storeQuestion") == null) {		
			
//			String purpose = "";
//			for(int i=0; i < purposeVal.length; i++) {
//				purpose = purpose + purposeVal[i] + "|";
//			}
//			
//			if(purpose.length() > 0) {
//				purpose = purpose.substring(0, purpose.length()-1);
//			}
				
			String questioncore = HtmlEncoder.encode(body);
			String xml = "<question><body>" + questioncore  + "</body>";
		
			TestStorequestionInfo storeQuestion = new TestStorequestionInfo();
			storeQuestion.setTitle(title);
			storeQuestion.setCreatuser(ssoUser.getLoginId());
			storeQuestion.setCreatdate(new Date());
			storeQuestion.setDiff(diff);
			storeQuestion.setKeyword("keyword");
			storeQuestion.setQuestioncore(xml);
			storeQuestion.setLore(lore_id);
			storeQuestion.setCognizetype(cognizetype);
//			storeQuestion.setPurpose(purpose);
			storeQuestion.setPurpose("KAOSHI");
			storeQuestion.setReferencescore(referencescore);
			storeQuestion.setReferencetime(referencetime);
			storeQuestion.setStudentnote(studentnote);
			storeQuestion.setTeachernote(teachernote);
			storeQuestion.setType(type);
			
			this.getGeneralService().getGeneralDao().setEntityClass(TestStorequestionInfo.class);
			try {
				storeQuestion=(TestStorequestionInfo) this.getGeneralService().save(storeQuestion);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			session.setAttribute("storeQuestion", storeQuestion);
		}
		
		return "to_comprehension_add";
		
	}

	/**
	 * 转向添加案例串添加新背景页面
	 * 
	 * @return
	 */
	public String toCompQuestionAnliBackAdd(){

		UserSession userSession = (UserSession) ActionContext.getContext()
			.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
	
		HttpServletRequest request = ServletActionContext.getRequest ();
		HttpSession session = request.getSession();
		
		if(session.getAttribute("storeQuestion") != null) {		

			TestStorequestionInfo storeQuestion_pre=(TestStorequestionInfo) session.getAttribute("storeQuestion");
			String xml = storeQuestion_pre.getQuestioncore();
			xml = xml + "</question>";
			storeQuestion_pre.setQuestioncore(xml);
			storeQuestion_pre.setCreatdate(new Date());
			this.getGeneralService().getGeneralDao().setEntityClass(TestStorequestionInfo.class);
			try {
				this.getGeneralService().save(storeQuestion_pre);
 				session.removeAttribute("storeQuestion");
				this.setMsg("添加题目成功！");
			} catch (EntityException e) {
				this.setMsg("添加题目失败！");
				e.printStackTrace();
			}
				
			String questioncore = HtmlEncoder.encode(body);
			xml = "<question><body>" + questioncore  + "</body>";
		
			TestStorequestionInfo storeQuestion = new TestStorequestionInfo();
			storeQuestion.setTestStorequestionInfo(storeQuestion_pre);
			storeQuestion.setTitle(title);
			storeQuestion.setCreatuser(ssoUser.getLoginId());
			storeQuestion.setCreatdate(new Date());
			storeQuestion.setDiff(diff);
			storeQuestion.setKeyword("keyword");
			storeQuestion.setQuestioncore(xml);
			storeQuestion.setLore(lore_id);
			storeQuestion.setCognizetype(cognizetype);
			storeQuestion.setPurpose("KAOSHI");
			storeQuestion.setReferencescore(referencescore);
			storeQuestion.setReferencetime(referencetime);
			storeQuestion.setStudentnote(studentnote);
			storeQuestion.setTeachernote(teachernote);
			storeQuestion.setType("YUEDU");
			
			this.getGeneralService().getGeneralDao().setEntityClass(TestStorequestionInfo.class);
			try {
				storeQuestion=(TestStorequestionInfo) this.getGeneralService().save(storeQuestion);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			

			session.setAttribute("storeQuestion", storeQuestion);
		}
		
		return "to_comprehension_add";
	}
	
	/**
	 * 添加案例分析（阅读）题目
	 * 
	 * @return
	 */
	public String compQuestionAdd() {
		
		HttpServletRequest request = ServletActionContext.getRequest ();
		HttpSession session = request.getSession();
		
		TestStorequestionInfo sq = (TestStorequestionInfo)session.getAttribute("storeQuestion");
		String branch_type = request.getParameter("branch_type");
		String xml = sq.getQuestioncore();
		
		if(types.equalsIgnoreCase(TestQuestionType.DANXUAN)) {
			String[] options = request.getParameterValues("single_options");
			String answer = request.getParameter("single_answer");
			
			xml = xml + "<subquestion><type>" + HtmlEncoder.encode(branch_type) + "</type><title>" + HtmlEncoder.encode(title) + "</title><referencetime>" + HtmlEncoder.encode(referencetime) 
			+ "</referencetime><referencescore>" + HtmlEncoder.encode(referencescore) + "</referencescore><body>" + HtmlEncoder.encode(body) 
			+ "</body><select>";
			
			for(int i=0; options!=null && i<options.length; i++) {
				int charCode = i + 65;		//选项字母的ASCII码
				String index = String.valueOf((char)charCode);	//将ASCII码转换成字母
				xml = xml + "<item><index>" + HtmlEncoder.encode(index) + "</index><content>" + HtmlEncoder.encode(options[i]) + "</content></item>";
			}
		
			xml = xml + "</select><answer>" + HtmlEncoder.encode(answer) + "</answer></subquestion>";
			
		} else if(types.equalsIgnoreCase(TestQuestionType.DUOXUAN)) {
			
			String[] options = request.getParameterValues("multi_options");
			String[] answers = request.getParameterValues("multi_answer");
			xml = xml + "<subquestion><type>" + HtmlEncoder.encode(branch_type) + "</type><title>" + HtmlEncoder.encode(title) + "</title><referencetime>" + HtmlEncoder.encode(referencetime) 
			+ "</referencetime><referencescore>" + HtmlEncoder.encode(referencescore) + "</referencescore><body>" + HtmlEncoder.encode(body) 
			+ "</body><select>";
			
			for(int i=0; options!=null && i<options.length; i++) {
				int charCode = i + 65;		//选项字母的ASCII码
				String index = String.valueOf((char)charCode);	//将ASCII码转换成字母
				xml = xml + "<item><index>" + HtmlEncoder.encode(index) + "</index><content>" + HtmlEncoder.encode(options[i]) + "</content></item>";
			}
		
			String answer = "";
			for(int i=0; i<answers.length; i++) {
				answer = answer + answers[i] + "|";
			}
			if(answer.length() > 0) {
				answer = answer.substring(0, answer.length()-1);
			}
			
			xml = xml + "</select><answer>" + HtmlEncoder.encode(answer) + "</answer></subquestion>";
			
		} else if(types.equalsIgnoreCase(TestQuestionType.PANDUAN)) {
			
			String answer = request.getParameter("judge_answer");
			xml = xml + "<subquestion><type>" + HtmlEncoder.encode(branch_type) + "</type><title>" + HtmlEncoder.encode(title) + "</title><referencetime>" + HtmlEncoder.encode(referencetime) 
			+ "</referencetime><referencescore>" + HtmlEncoder.encode(referencescore) + "</referencescore><body>" + HtmlEncoder.encode(body) 
			+ "</body><answer>" + HtmlEncoder.encode(answer) + "</answer></subquestion>";
			
		} else if(types.equalsIgnoreCase(TestQuestionType.TIANKONG)) {
			
			String answer = request.getParameter("blank_answer");
			xml = xml + "<subquestion><type>" + HtmlEncoder.encode(branch_type) + "</type><title>" + HtmlEncoder.encode(title) + "</title><referencetime>" + HtmlEncoder.encode(referencetime) 
			+ "</referencetime><referencescore>" + HtmlEncoder.encode(referencescore) + "</referencescore><body>" + HtmlEncoder.encode(body) 
			+ "</body><answer>" + HtmlEncoder.encode(answer) + "</answer></subquestion>";
			
		} else if(types.equalsIgnoreCase(TestQuestionType.WENDA)) {
			
			String answer = request.getParameter("answer_answer");
			xml = xml + "<subquestion><type>" + HtmlEncoder.encode(branch_type) + "</type><title>" + HtmlEncoder.encode(title) + "</title><referencetime>" + HtmlEncoder.encode(referencetime) 
			+ "</referencetime><referencescore>" + HtmlEncoder.encode(referencescore) + "</referencescore><body>" + HtmlEncoder.encode(body) 
			+ "</body><answer>" + HtmlEncoder.encode(answer) + "</answer></subquestion>";
			
		}		
		sq.setQuestioncore(xml);
		session.setAttribute("storeQuestion", sq);
		 
		String returnVal = "";
		if(types.equalsIgnoreCase(TestQuestionType.DANXUAN)){
			returnVal = "comprehension_add_single";
		} else if(types.equalsIgnoreCase(TestQuestionType.DUOXUAN)) {
			returnVal = "comprehension_add_multi";
		} else if(types.equalsIgnoreCase(TestQuestionType.PANDUAN)) {
			returnVal = "comprehension_add_judge";
		} else if(types.equalsIgnoreCase(TestQuestionType.TIANKONG)) {
			returnVal = "comprehension_add_blank";
		} else if(types.equalsIgnoreCase(TestQuestionType.WENDA)) {
			returnVal = "comprehension_add_answer";
		}		
		return returnVal;
			
	}
	
	/**
	 * 添加整个案例分析（阅读）题目保存到数据库
	 * 
	 * @return
	 */
	public String compQuestionAllAdd() {
		
		HttpServletRequest request = ServletActionContext.getRequest ();
		HttpSession session = request.getSession();
		
		TestStorequestionInfo sq = (TestStorequestionInfo)session.getAttribute("storeQuestion");
		String xml = sq.getQuestioncore();
		xml = xml + "</question>";
		sq.setQuestioncore(xml);
		sq.setCreatdate(new Date());
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestStorequestionInfo.class);
		try {
			this.getGeneralService().save(sq);
			session.removeAttribute("storeQuestion");
			this.setMsg("添加题目成功！");
		} catch (EntityException e) {
			this.setMsg("添加题目失败！");
			e.printStackTrace();
		}
		
		this.initLoreQuestion();
		return "to_lore_save";
		
	}
	
	/**
	 * 查看题目详细信息
	 * 
	 * @return
	 */
	public String viewQuestion() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestStorequestionInfo.class);
		try {
			TestStorequestionInfo question = (TestStorequestionInfo)this.getGeneralService().getById(this.question_id);
			String type = question.getType();		
			String questionCore = "";
			com.whaty.util.string.WhatyStrManage strManage = new com.whaty.util.string.WhatyStrManage();
			if(type.equalsIgnoreCase(TestQuestionType.DANXUAN) || type.equalsIgnoreCase(TestQuestionType.DUOXUAN))
				questionCore = XMLParserUtil.getSingleMultiContent(question.getQuestioncore());
			if(type.equalsIgnoreCase(TestQuestionType.TIANKONG) || type.equalsIgnoreCase(TestQuestionType.WENDA))
				questionCore = XMLParserUtil.getBlankAnswerContent(question.getQuestioncore());
			if(type.equalsIgnoreCase(TestQuestionType.PANDUAN))
				questionCore = XMLParserUtil.getJudgContent(question.getQuestioncore());
			
			strManage.setString(questionCore);
			questionCore = strManage.htmlDecode();
			
			this.setBody(questionCore);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "view_question";
		
	}
	
	/**
	 * 查看已经添加到试卷的题目信息
	 * 
	 * @return
	 */
	public String viewPaperQuestion() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestPaperquestionInfo.class);
		try {
			TestPaperquestionInfo question = (TestPaperquestionInfo)this.getGeneralService().getById(this.question_id);
			String type = question.getType();		
			String questionCore = "";
			com.whaty.util.string.WhatyStrManage strManage = new com.whaty.util.string.WhatyStrManage();
			if(type.equalsIgnoreCase(TestQuestionType.DANXUAN) || type.equalsIgnoreCase(TestQuestionType.DUOXUAN))
				questionCore = XMLParserUtil.getSingleMultiContent(question.getQuestioncore());
			if(type.equalsIgnoreCase(TestQuestionType.TIANKONG) || type.equalsIgnoreCase(TestQuestionType.WENDA))
				questionCore = XMLParserUtil.getBlankAnswerContent(question.getQuestioncore());
			if(type.equalsIgnoreCase(TestQuestionType.PANDUAN))
				questionCore = XMLParserUtil.getJudgContent(question.getQuestioncore());
			
			strManage.setString(questionCore);
			questionCore = strManage.htmlDecode();
			
			this.setBody(questionCore);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "view_question";
		
	}
	
	/**
	 * 查看已经添加到试卷的案例分析题信息
	 * 
	 * @return
	 */
	public String viewYueDuPaperQuestion() {
		this.getGeneralService().getGeneralDao().setEntityClass(TestPaperquestionInfo.class);
		try {
			TestPaperquestionInfo question = (TestPaperquestionInfo)this.getGeneralService().getById(this.question_id);
			if(question.getType().equals(TestQuestionType.YUEDU)) {
				
				List questionList = new ArrayList();
				String questionCoreXml = question.getQuestioncore();
				List coreList = XMLParserUtil.parserComprehension(questionCoreXml);
				String body = (String)coreList.get(0);
				this.setBody(body);
				
				for(int i=1; i<coreList.size(); i++) {
					List list = new ArrayList();
              		List subList = (List)coreList.get(i);
              		String type = (String)subList.get(0);
              		String subBody = (String)subList.get(4);
              		String answer = (String)subList.get(subList.size()-1);
              		
              		if(type.equals(TestQuestionType.PANDUAN)) {
                		if(answer.equals("1")) {
                			answer = "正确";
                		} else if(answer.equals("0")) {
                			answer = "错误";
                		}
                	}
              		list.add(0, subBody);
              		list.add(1,answer);
              		if(type.equals(TestQuestionType.DANXUAN) || type.equals(TestQuestionType.DUOXUAN)) {
              			List ll = new ArrayList();
                		for(int j=5; j<subList.size()-2; j=j+2) {
                			String index = (String)subList.get(j);
                			String content = (String)subList.get(j+1);
                			ll.add(index + "、" + content);
                		}
                		list.add(2,"XUAN");
                		list.add(3,ll);
              		} else {
              			list.add(2,"NOT");
              		}
              		
              		questionList.add(list);
				}
				
				this.setQuestionList(questionList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "view_yuedu_question";
		
	}
	
	/**
	 * 查看阅读题题目详细信息
	 * 
	 * @return
	 */
	public String viewYueDuQuestion() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestStorequestionInfo.class);
		try {
			TestStorequestionInfo question = (TestStorequestionInfo)this.getGeneralService().getById(this.question_id);
			
			if(question.getType().equals(TestQuestionType.YUEDU)) {
				String idsql="select a.id from test_storequestion_info a start with a.id =:ids connect by prior  a.id=a.anliback  ";
				Map map=new HashMap();
				map.put("ids", this.question_id);
				List idlist= new ArrayList();
				idlist=this.getGeneralService().getBySQL(idsql, map);
				DetachedCriteria dc = DetachedCriteria.forClass(TestStorequestionInfo.class);
				dc.add(Restrictions.in("id",idlist));

				List questionList = new ArrayList();
				questionList =this.getGeneralService().getList(dc);
				List questionListShow=new ArrayList();
				List bodyList=new ArrayList();
				for(int j=0;j<questionList.size();j++){

					question=(TestStorequestionInfo) questionList.get(j);
					String questionCoreXml = question.getQuestioncore();
					List coreList = XMLParserUtil.parserComprehension(questionCoreXml);
					String body = (String)coreList.get(0);
					bodyList.add(body);
					List questionList1=new ArrayList();
					for(int i=1; i<coreList.size(); i++) {
						List list = new ArrayList();
	              		List subList = (List)coreList.get(i);
	              		String type = (String)subList.get(0);
	              		String subBody = (String)subList.get(4);
	              		String answer = (String)subList.get(subList.size()-1);
	              		
	              		if(type.equals(TestQuestionType.PANDUAN)) {
	                		if(answer.equals("1")) {
	                			answer = "正确";
	                		} else if(answer.equals("0")) {
	                			answer = "错误";
	                		}
	                	}
	              		list.add(0, subBody);
	              		list.add(1,answer);
	              		if(type.equals(TestQuestionType.DANXUAN) || type.equals(TestQuestionType.DUOXUAN)) {
	              			List ll = new ArrayList();
	                		for(int k=5; k<subList.size()-2; k=k+2) {
	                			String index = (String)subList.get(k);
	                			String content = (String)subList.get(k+1);
	                			ll.add(index + "、" + content);
	                		}
	                		list.add(2,"XUAN");
	                		list.add(3,ll);
	              		} else {
	              			list.add(2,"NOT");
	              		}
	              		
	              		questionList1.add(list);
					}
					questionListShow.add(questionList1);
				}
				this.setQuestionList(questionListShow);
				this.setBodyList(bodyList);
			}
			
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "view_yuedu_question";
		
	}
	
	/**
	 * 删除题目
	 * 
	 * @return
	 */
	public String deleteQuestion() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestStorequestionInfo.class);
		TestStorequestionInfo question;
		try {
			question = (TestStorequestionInfo)this.getGeneralService().getById(this.question_id);
			if(question != null&&!question.getType().equals(TestQuestionType.YUEDU)) {
				this.getGeneralService().delete(question);
				this.setMsg("删除题目成功！");
			}else if(question != null&&question.getType().equals(TestQuestionType.YUEDU)) {
				this.deleteAnli(this.question_id);
			}
			
		} catch (EntityException e) {
			this.setMsg("删除题目失败！");
			e.printStackTrace();
		}
		
		this.initLoreQuestion();
		return "to_lore";
		
	}
	
	/**
	 * 批量删除题目
	 * 
	 * @return
	 */
	public String batchDeleteQuestion() {
		
		if(idaaa != null && idaaa.length > 0) {
			this.getGeneralService().getGeneralDao().setEntityClass(TestStorequestionInfo.class);
			List ids = new ArrayList();
			for(int i=0; i < idaaa.length; i++) {
				ids.add(idaaa[i]);
			}
			int count=0;
				for(int i=0;i<ids.size();i++){
					Map map=new HashMap();
					map.put("ids",(String)ids.get(i));
					int j=0;
					try {
					j=this.getGeneralService().executeBySQL("delete from test_storequestion_info where id=:ids",map);
					} catch (EntityException e) {
						this.setMsg("删除题目失败！");
						e.printStackTrace();
					}
					if(j<1){
						boolean flag=this.deleteAnli((String)ids.get(i));
						if(flag){
							count++;
						}
					}else{
						count++;
					}
				} 
				this.setMsg("成功删除 " + count + " 道题目！");
		}
		
		this.initLoreQuestion();
		return "to_lore";
		
	}
	public boolean deleteAnli(String id){
		String sql="select id from test_storequestion_info where anliback=:ids";
		Map map=new HashMap();
		map.put("ids", id);
		List list=new LinkedList();
		try {
			 list= this.getGeneralService().getBySQL(sql,map);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(list.size()>0){//说明后面还有子案例串
			String ids=(String) list.get(0);
			this.deleteAnli(ids);//递归删除子案例串
		}
		//是案例串的最后一个小题
		int i=0;
		sql="delete from test_storequestion_info where id=:ids";
		try {
			i=this.getGeneralService().executeBySQL(sql, map);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(i>0){//删除成功
			return true;
		}
		return false;
	}
	/**
	 * 编辑题目
	 * 
	 * @return
	 */
	public String editQuestion() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestStorequestionInfo.class);
		try {
			TestStorequestionInfo question = (TestStorequestionInfo)this.getGeneralService().getById(this.question_id);
			if(question != null) {
				this.setQuestionInfo(question);
				String questionCoreXml = question.getQuestioncore();
				
				List coreList = null;
				if(type.equals(TestQuestionType.DANXUAN) || type.equals(TestQuestionType.DUOXUAN)) {
					coreList = XMLParserUtil.parserSingleMulti(questionCoreXml);
				} else if(type.equals(TestQuestionType.PANDUAN)) {
					coreList = XMLParserUtil.parserJudge(questionCoreXml);
				} else if(type.equals(TestQuestionType.TIANKONG) || type.equals(TestQuestionType.WENDA)) {
					coreList = XMLParserUtil.parserBlankAnswer(questionCoreXml);
				} else if(type.equals(TestQuestionType.YUEDU)) {
					coreList = XMLParserUtil.parserComprehension(questionCoreXml);
				}
				
				if(coreList != null && coreList.size() > 0) {
					String bodyString = (String)coreList.get(0);
					this.setBody(bodyString);
					if(!type.equals(TestQuestionType.YUEDU)) {
						String answer = (String)coreList.get(coreList.size()-1);
						this.setAnswer(answer);
					}
					String type_str = TestQuestionType.typeShow(type);
					this.setTypeStr(type_str);
					
					if(type.equals(TestQuestionType.DANXUAN) || type.equals(TestQuestionType.DUOXUAN)) {
						
						int charCode = (coreList.size()-2)/2;
						this.setCharCode(charCode);
						
						List answerList = new ArrayList();
						for(int i=1; i<coreList.size()-2; i=i+2) {
							List list = new ArrayList();
							String index = (String)coreList.get(i);
							list.add(index);
							if(answer.indexOf(index)>=0) {
								list.add("1");
							}else {
								list.add("0");
							}
							String content = (String)coreList.get(i+1);
							WhatyStrManage strManage = new WhatyStrManage();
							strManage.setString(content);
							String html = strManage.htmlEncode();
							list.add(html);
							answerList.add(list);
						}
						this.setAnswerList(answerList);
						
					} else if(type.equals(TestQuestionType.YUEDU)) {
						List compList = new ArrayList();
						List numList = new ArrayList();
						int singleNum = 0;
						int multiNum = 0;
						int judgeNum = 0;
						int blankNum = 0;
						int answerNum = 0;
						for(int i=1; i<coreList.size(); i++) {
							List ll = new ArrayList();
					  		List subList = (List)coreList.get(i);
					  		String subType = (String)subList.get(0);
					  		String subTitle = (String)subList.get(1);
					  		String subTime = (String)subList.get(2);
					  		String subScore = (String)subList.get(3);
					  		String subBody = (String)subList.get(4);
					  		String subAnswer = (String)subList.get(subList.size()-1);
					  		
					  		ll.add(0,subType);
					  		ll.add(1,subTitle);
					  		ll.add(2,subTime);
					  		ll.add(3,subScore);
					  		ll.add(4,subBody);
					  		ll.add(5,subAnswer);
					  		
					  		if(subType.equals(TestQuestionType.DANXUAN)) {
					  			singleNum++;
					  			List single = new ArrayList();
					  			for(int j=5; j<subList.size()-2; j=j+2) {
					  				List indexList = new ArrayList();
									String index = (String)subList.get(j);
									String content = (String)subList.get(j+1);
									indexList.add(0, index);
									indexList.add(1, content);
									
									single.add(indexList);
					  			}
					  			ll.add(6, singleNum);
					  			ll.add(7, single);
					  			
					  		} else if(subType.equals(TestQuestionType.DUOXUAN)) {
					  			multiNum++;
					  			List multi = new ArrayList();
								for(int j=5; j<subList.size()-2; j=j+2) {
									List indexList = new ArrayList();
									String index = (String)subList.get(j);
									String content = (String)subList.get(j+1);
									indexList.add(0, index);
									indexList.add(1, content);
									
									multi.add(indexList);
								}
								ll.add(6, multiNum);
								ll.add(7, multi);
								
					  		} else if(subType.equals(TestQuestionType.PANDUAN)) {
					  			judgeNum++;
					  			ll.add(6, judgeNum);
					  		} else if(subType.equals(TestQuestionType.TIANKONG)) {
					  			blankNum++;
					  			ll.add(6, blankNum);
					  		} else if(subType.equals(TestQuestionType.WENDA)) {
					  			answerNum++;
					  			ll.add(6, answerNum);
					  		}
					  		
					  		compList.add(ll);
						}
						
						numList.add(0,singleNum);
						numList.add(1,multiNum);
						numList.add(2,judgeNum);
						numList.add(3,blankNum);
						numList.add(4,answerNum);
						this.setNumList(numList);
						this.setComprehensionObjectList(compList);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(type.equals(TestQuestionType.DANXUAN)) {
			return "edit_single_question";
		} else if(type.equals(TestQuestionType.DUOXUAN)) {
			return "edit_multi_question";
		} else if(type.equals(TestQuestionType.PANDUAN)) {
			return "edit_judge_question";
		} else if(type.equals(TestQuestionType.TIANKONG)) {
			return "edit_blank_question";
		} else if(type.equals(TestQuestionType.WENDA)) {
			return "edit_answer_question";
		} else if(type.equals(TestQuestionType.YUEDU)) {
			this.getAnliNextId(this.getQuestion_id());
			return "edit_comprehension_question";
		}
		
		this.setMsg("数据初始化异常！");
		this.initLoreQuestion();
		return "to_lore";
		
	}
	public String getAnliNextId(String id){
		String sql="select id from TEST_STOREQUESTION_INFO where anliback=:ids";
		Map map=new HashMap();
		map.put("ids", id);
		List list=new ArrayList();
		try {
			list=this.getGeneralService().getBySQL(sql, map);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String nextId="";
		if(list!=null&&list.size()>0){
			nextId=(String) list.get(0);
		}
		this.setAnliNextId(nextId);
		return nextId;
	}
	
	/**
	 * 执行题目的修改操作
	 * 
	 * @return
	 */
	public String doEditQuestion() {
		
		if(this.checkQuestion()) {
			this.getGeneralService().getGeneralDao().setEntityClass(TestStorequestionInfo.class);
			try {
				TestStorequestionInfo question = (TestStorequestionInfo)this.getGeneralService().getById(this.question_id);
				if(question != null) {
					question.setDiff(diff);
					question.setReferencescore(referencescore);
					question.setCognizetype(cognizetype);
					question.setTitle(title);
					if(this.referencetime != null && "".equals(this.referencetime)) {
						question.setReferencetime(referencetime);
					}
					if(this.teachernote != null && "".equals(this.teachernote)) {
						question.setTeachernote(teachernote);
					}
					if(this.studentnote != null && "".equals(this.studentnote)) {
						question.setStudentnote(studentnote);
					}
					String xml = "";
					if(type.equals(TestQuestionType.DANXUAN)) {
						
						xml = "<question><body>" + HtmlEncoder.encode(this.body) + "</body><select>";
						for(int i=0; i<options.length; i++) {
							int charCode = i + 65;		//选项字母的ASCII码
							String index = String.valueOf((char)charCode);	//将ASCII码转换成字母
							xml = xml + "<item><index>" + HtmlEncoder.encode(index) + "</index><content>" + HtmlEncoder.encode(options[i]) + "</content></item>";
						}
						
						xml = xml + "</select><answer>" + HtmlEncoder.encode(answer) + "</answer></question>";
					
					} else if(type.equals(TestQuestionType.DUOXUAN)) {
						
						xml = "<question><body>" + HtmlEncoder.encode(this.body) + "</body><select>";
						for(int i=0; i<options.length; i++) {
							int charCode = i + 65;		//选项字母的ASCII码
							String index = String.valueOf((char)charCode);	//将ASCII码转换成字母
							xml = xml + "<item><index>" + HtmlEncoder.encode(index) + "</index><content>" + HtmlEncoder.encode(options[i]) + "</content></item>";
						}
						
						String ans = "";
						for(int i=0; i<answers.length; i++) {
							ans = ans + answers[i] + "|";
						}
						
						xml = xml + "</select><answer>" + HtmlEncoder.encode(ans) + "</answer></question>";
						
					} else if(type.equalsIgnoreCase(TestQuestionType.PANDUAN) || type.equalsIgnoreCase(TestQuestionType.TIANKONG) || type.equalsIgnoreCase(TestQuestionType.WENDA)) {
						
						xml = "<question><body>" + HtmlEncoder.encode(body) + "</body><answer>" + HtmlEncoder.encode(answer) + "</answer></question>";
						
					} else if(type.equalsIgnoreCase(TestQuestionType.YUEDU)) {
						
						HttpServletRequest request = ServletActionContext.getRequest();
//						String purpose = "";
//						for(int i=0; i<purposeVal.length; i++) {
//							purpose = purpose + purposeVal[i] + "|";
//						}
//						if(purpose.length() > 0) {
//							purpose = purpose.substring(0, purpose.length()-1);
//						}
//						question.setPurpose(purpose);
						question.setPurpose("KAOSHI");
						
						HashMap ht = new HashMap();
						
						xml = "<question><body>" + HtmlEncoder.encode(body) + "</body>";
						for(int i=1; i<=singleNum; i++) {
							String subXml = "<subquestion>";
							String serial = request.getParameter("singleSerial"+i);
							String subBody = request.getParameter("singleBody"+i);
							String subType = request.getParameter("singleType"+i);
							String subTitle = request.getParameter("singleTitle"+i);
							String subTime = request.getParameter("singleTime"+i);
							String subScore = request.getParameter("singleScore"+i);
							String subAnswer = request.getParameter("singleAnswer"+i);
							String[] subOptions = request.getParameterValues("singleOptions"+i);
							subXml = subXml + "<type>" + HtmlEncoder.encode(subType) + "</type><title>" + HtmlEncoder.encode(subTitle) + "</title><referencetime>"
								+ subTime + "</referencetime><referencescore>" + HtmlEncoder.encode(subScore) + "</referencescore><body>" 
								+ HtmlEncoder.encode(subBody) + "</body><select>";
							for(int j=0; j<subOptions.length; j++) {
								int charCode = j + 65;		//选项字母的ASCII码
								String index = String.valueOf((char)charCode);	//将ASCII码转换成字母
								subXml = subXml + "<item><index>" + HtmlEncoder.encode(index) + "</index><content>" + HtmlEncoder.encode(subOptions[j]) + "</content></item>";
							}
							
							subXml = subXml + "</select><answer>" + HtmlEncoder.encode(subAnswer) + "</answer></subquestion>";
							ht.put(serial, subXml);
						}
						
						for(int i=1; i<=multiNum; i++) {
							String subXml = "<subquestion>";
							String serial = request.getParameter("multiSerial"+i);
							String subBody = request.getParameter("multiBody"+i);
							String subType = request.getParameter("multiType"+i);
							String subTitle = request.getParameter("multiTitle"+i);
							String subTime = request.getParameter("multiTime"+i);
							String subScore = request.getParameter("multiScore"+i);
							String[] subAnswers = request.getParameterValues("multiAnswer"+i);
							String[] subOptions = request.getParameterValues("multiOptions"+i);
							subXml = subXml + "<type>" + HtmlEncoder.encode(subType) + "</type><title>" + HtmlEncoder.encode(subTitle) + "</title><referencetime>"
								+ subTime + "</referencetime><referencescore>" + subScore + "</referencescore><body>" 
								+ HtmlEncoder.encode(subBody) + "</body><select>";
							for(int j=0; j<subOptions.length; j++) {
								int charCode = j + 65;		//选项字母的ASCII码
								String index = String.valueOf((char)charCode);	//将ASCII码转换成字母
								subXml = subXml + "<item><index>" + HtmlEncoder.encode(index) + "</index><content>" + HtmlEncoder.encode(subOptions[j]) + "</content></item>";
							}
							
							String subAnswer = "";
							for(int j=0; j<subAnswers.length; j++) {
								subAnswer = subAnswer + subAnswers[j] + "|";
							}
							if(subAnswer.length() > 0)
								subAnswer = subAnswer.substring(0, subAnswer.length()-1);
							
							subXml = subXml + "</select><answer>" + subAnswer + "</answer></subquestion>";
							ht.put(serial, subXml);
						}
						
						for(int i=1; i<=judgeNum; i++) {
							String subXml = "<subquestion>";
							String serial = request.getParameter("judgeSerial"+i);
							String subBody = request.getParameter("judgeBody"+i);
							String subType = request.getParameter("judgeType"+i);
							String subTitle = request.getParameter("judgeTitle"+i);
							String subTime = request.getParameter("judgeTime"+i);
							String subScore = request.getParameter("judgeScore"+i);
							String subAnswer = request.getParameter("judgeAnswer"+i);
							subXml = subXml + "<type>" + HtmlEncoder.encode(subType) + "</type><title>" + HtmlEncoder.encode(subTitle) + "</title><referencetime>"
								+ subTime + "</referencetime><referencescore>" + HtmlEncoder.encode(subScore) + "</referencescore><body>" 
								+ HtmlEncoder.encode(subBody) + "</body><answer>" + HtmlEncoder.encode(subAnswer) + "</answer></subquestion>";
							ht.put(serial, subXml);
						}
						
						for(int i=1; i<=blankNum; i++) {
							String subXml = "<subquestion>";
							String serial = request.getParameter("blankSerial"+i);
							String subBody = request.getParameter("blankBody"+i);
							String subType = request.getParameter("blankType"+i);
							String subTitle = request.getParameter("blankTitle"+i);
							String subTime = request.getParameter("blankTime"+i);
							String subScore = request.getParameter("blankScore"+i);
							String subAnswer = request.getParameter("blankAnswer"+i);
							subXml = subXml + "<type>" + HtmlEncoder.encode(subType) + "</type><title>" + HtmlEncoder.encode(subTitle) + "</title><referencetime>"
								+ subTime + "</referencetime><referencescore>" + subScore + "</referencescore><body>" 
								+ HtmlEncoder.encode(subBody) + "</body><answer>" + HtmlEncoder.encode(subAnswer) + "</answer></subquestion>";
							ht.put(serial, subXml);
						}
						
						for(int i=1; i<=answerNum; i++) {
							String subXml = "<subquestion>";
							String serial = request.getParameter("answerSerial"+i);
							String subBody = request.getParameter("answerBody"+i);
							String subType = request.getParameter("answerType"+i);
							String subTitle = request.getParameter("answerTitle"+i);
							String subTime = request.getParameter("answerTime"+i);
							String subScore = request.getParameter("answerScore"+i);
							String subAnswer = request.getParameter("answerAnswer"+i);
							subXml = subXml + "<type>" + HtmlEncoder.encode(subType) + "</type><title>" + HtmlEncoder.encode(subTitle) + "</title><referencetime>"
								+ subTime + "</referencetime><referencescore>" + HtmlEncoder.encode(subScore) + "</referencescore><body>" 
								+ HtmlEncoder.encode(subBody) + "</body><answer>" + HtmlEncoder.encode(subAnswer) + "</answer></subquestion>";
							ht.put(serial, subXml);
						}

						for(int i=1; i<=ht.size(); i++) {
							String subXML = (String)ht.get(String.valueOf(i));
							xml = xml + subXML;
						}
						xml = xml + "</question>";
							
					}
					
					question.setQuestioncore(xml);
					
					this.getGeneralService().save(question);
					this.setMsg("修改题目成功！");
				}
			} catch (EntityException e) {
				this.setMsg("修改题目失败！");
				e.printStackTrace();
			}
		} else {
			this.setMsg("同一课程下题目名称不能重复，请重新录入题目名称。");
		}
		
		this.initLoreQuestion();
		return "to_lore";
		
	}
	
	/**
	 * 导入excel文件
	 * 
	 * @return
	 */
	public String excelUpload() {
		
		String path = "/incoming/Excel/";
		if (this.src != null) {
			if(!checkFile(this.getSrcFileName())) {
				this.setMsg("请上传标准的excel文件！");
			} else {
				path += this.getSrcFileName();
				path = this.reName(path);
				java.io.InputStream is;
				try {
					is = new FileInputStream(src);
					java.io.OutputStream os = new FileOutputStream(ServletActionContext.getServletContext().getRealPath(path));
					
					byte buffer[] = new byte[8192];
					int count = 0;
					while ((count = is.read(buffer)) > 0)
					{
					    os.write(buffer, 0, count);
					}
					os.close();
					is.close();
					
					int row = this.importQuestion(ServletActionContext.getServletContext().getRealPath(path));
					if(row > 0) {
						this.setMsg(this.getMsg() + "成功上传 " + row + " 道题目！");
					}
				} catch (IOException e) {
					this.setMsg("上传文件失败！请验证是否标准的excel文件！");
					e.printStackTrace();
				}
			}
		}
		
		this.initLoreQuestion();
		return "to_lore";
		
	}
	
	/**
	 * 添加试卷
	 * 
	 * @return
	 */
	public String testpaperAdd() {
		
		if (this.checkPaper(this.title, this.test_id)) {
			
			TestTestpaperInfo paper = new TestTestpaperInfo();
			paper.setCreatdate(new Date());
			paper.setGroupId(this.test_id);
			paper.setNote(this.note);
			paper.setStatus(this.status);
			paper.setTitle(this.title);
			paper.setType(this.types);
			paper.setTime(this.time);
			
			UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
			SsoUser ssoUser = userSession.getSsoUser();
			paper.setCreatuser(ssoUser.getLoginId());
			
			this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperInfo.class);
			try {
				this.getGeneralService().save(paper);
				this.setMsg("添加试卷成功！");
			} catch (EntityException e) {
				this.setMsg("添加试卷失败！");
				e.printStackTrace();
			}
			
		} else {
			this.setMsg("已经存在此标题的试卷！");
		}
		
		this.initTestpaper();
		return "testpaper_list";
		
	}
	
	/**
	 * 删除试卷
	 * 
	 * @return
	 */
	public String deletePaper() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperInfo.class);
		try {
			TestTestpaperInfo paper = (TestTestpaperInfo)this.getGeneralService().getById(this.paper_id);
			if(paper != null) {
				this.getGeneralService().delete(paper);
				this.setMsg("删除试卷成功！");
			}
		} catch (EntityException e) {
			this.setMsg("删除试卷失败！");
			e.printStackTrace();
		}
		
		this.initTestpaper();
		return "testpaper_list";
		
	}
	
	/**
	 * 查看试卷信息
	 * 
	 * @return
	 */
	public String viewTestPaperInfo() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperInfo.class);
		try {
			TestTestpaperInfo paper = (TestTestpaperInfo)this.getGeneralService().getById(this.paper_id);
			if(paper != null) {
				this.setPaperInfo(paper);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "testpaper_view";
	}
	
	/**
	 * 修改试卷信息
	 * 
	 * @return
	 */
	public String editTestPaperInfo() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperInfo.class);
		try {
			TestTestpaperInfo paper = (TestTestpaperInfo)this.getGeneralService().getById(this.paper_id);
			if(paper != null) {
				this.setPaperInfo(paper);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "testpaper_edit";
	}
	
	/**
	 * 修改试卷信息
	 * 
	 * @return
	 */
	public String doEditTestPaperInfo() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperInfo.class);
		try {
			TestTestpaperInfo paper = (TestTestpaperInfo)this.getGeneralService().getById(this.paper_id);
			if(paper != null) {
				paper.setTitle(title);
				paper.setTime(time);
				paper.setNote(note);
				paper.setStatus(status);
				this.getGeneralService().save(paper);
				this.setMsg("修改试卷成功！");
			}
		} catch (EntityException e) {
			this.setMsg("修改试卷失败！");
			e.printStackTrace();
		}

		this.initTestpaper();
		return "testpaper_list";
	}
	/**
	 * 进入组卷策略
	 * 
	 * @return
	 */
	public String enterTestpaperpolicy() {
		
		this.initPaperpolicy();
		return "testpaperpolicy_list";
		
	}
	
	/**
	 * 试题编辑，添加
	 * 
	 * @return
	 */
	public String paperQuestionEdit() {
		
		this.initPaperQuestion();
		return "paper_question_edit";
	}
	
	/**
	 * 试题编辑，批量删除
	 * 
	 * @return
	 */
	public String batchDeletePaperQuestion() {
		
		String message = "";
		this.getGeneralService().getGeneralDao().setEntityClass(TestPaperquestionInfo.class);
		if(this.DANXUANChild != null && this.DANXUANChild.length > 0) {
			try {
				List idList = new ArrayList();
				for(int i = 0; i < this.DANXUANChild.length; i++) {
					idList.add(DANXUANChild[i]);
				}
				this.getGeneralService().deleteByIds(idList);
				message += "删除单选题成功！\\n";
			} catch (EntityException e) {
				message += "删除单选题失败！\\n";
				e.printStackTrace();
			}
		}
		
		if(this.DUOXUANChild != null && this.DUOXUANChild.length > 0) {
			try {
				List idList = new ArrayList();
				for(int i = 0; i < this.DUOXUANChild.length; i++) {
					idList.add(DUOXUANChild[i]);
				}
				this.getGeneralService().deleteByIds(idList);
				message += "删除多选题成功！\\n";
			} catch (EntityException e) {
				message += "删除多选题失败！\\n";
				e.printStackTrace();
			}
		}
		
		if(this.PANDUANChild != null && this.PANDUANChild.length > 0) {
			try {
				List idList = new ArrayList();
				for(int i = 0; i < this.PANDUANChild.length; i++) {
					idList.add(PANDUANChild[i]);
				}
				this.getGeneralService().deleteByIds(idList);
				message += "删除判断题成功！\\n";
			} catch (EntityException e) {
				message += "删除判断题失败！\\n";
				e.printStackTrace();
			}
		}
		
		if(this.TIANKONGChild != null && this.TIANKONGChild.length > 0) {
			try {
				List idList = new ArrayList();
				for(int i = 0; i < this.TIANKONGChild.length; i++) {
					idList.add(TIANKONGChild[i]);
				}
				this.getGeneralService().deleteByIds(idList);
				message += "删除填空题成功！\\n";
			} catch (EntityException e) {
				message += "删除填空题失败！\\n";
				e.printStackTrace();
			}
		}
		
		if(this.WENDAChild != null && this.WENDAChild.length > 0) {
			try {
				List idList = new ArrayList();
				for(int i = 0; i < this.WENDAChild.length; i++) {
					idList.add(WENDAChild[i]);
				}
				this.getGeneralService().deleteByIds(idList);
				message += "删除问答题成功！\\n";
			} catch (EntityException e) {
				message += "删除问答题失败！\\n";
				e.printStackTrace();
			}
		}
		
		if(this.YUEDUChild != null && this.YUEDUChild.length > 0) {
			try {
				List idList = new ArrayList();
				for(int i = 0; i < this.YUEDUChild.length; i++) {
					idList.add(YUEDUChild[i]);
				}
				this.getGeneralService().deleteByIds(idList);
				message += "删除案例分析题成功！\\n";
			} catch (EntityException e) {
				message += "删除案例分析题失败！\\n";
				e.printStackTrace();
			}
		}
		
		this.setMsg(message);
		this.initPaperQuestion();
		return "paper_question_edit";
		
	}
	
	/**
	 * 批量更新题目分数
	 * 
	 * @return
	 */
	public String batchUpdatePaperQuestion() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestPaperquestionInfo.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestPaperquestionInfo.class);
		dct.add(Restrictions.eq("testpaperId", this.paper_id));
		try {
			List list = this.getGeneralService().getList(dct);
			if(list != null && list.size() > 0) {
				for(Object o : list) {
					TestPaperquestionInfo q = (TestPaperquestionInfo)o;
					String str = q.getId() + "score";
					String q_score = request.getParameter(str);//页面传过来的参数，name属性：题目id + "score"
					q.setScore(q_score);
					this.getGeneralService().update(q);
				}
			}
			this.setMsg("修改题目分数成功！");
		} catch (EntityException e) {
			this.setMsg("修改题目分数失败！");
			e.printStackTrace();
		}
		
		this.initPaperQuestion();
		return "paper_question_edit";
		
	}
	
	/**
	 * 组卷中添加试题到试卷
	 * 
	 * @return
	 */
	public String paperQuetionItemToAdd() {
		
		this.initQuestionItem();
		return "paper_question_item_add";
		
	}
	
	/**
	 * 添加选中的题目到试卷
	 * 
	 * @return
	 */
	public String addQuestionToPaper() {
		
		if(this.idaaa != null && this.idaaa.length > 0) {
			this.getGeneralService().getGeneralDao().setEntityClass(TestStorequestionInfo.class);
			DetachedCriteria dct = DetachedCriteria.forClass(TestStorequestionInfo.class);
			dct.add(Restrictions.in("id", idaaa));
			try {
				List list = this.getGeneralService().getList(dct);
				
				if(list != null && list.size() > 0) {
					this.getGeneralService().getGeneralDao().setEntityClass(TestPaperquestionInfo.class);
					DetachedCriteria dc = DetachedCriteria.forClass(TestPaperquestionInfo.class);
					dc.add(Restrictions.eq("type", this.type));
					dc.add(Restrictions.eq("testpaperId", this.paper_id));
					List items = this.getGeneralService().getList(dc);
					
					int sq = 0;//题目序号
					if(items != null && items.size() > 0) {
						sq = items.size();
					}
					
					List<TestPaperquestionInfo> infoList = new ArrayList<TestPaperquestionInfo>();
					for(int i = 0; i < list.size(); i++) {
						TestStorequestionInfo store = (TestStorequestionInfo)list.get(i);
						TestPaperquestionInfo paper = new TestPaperquestionInfo();
						paper.setCreatuser(store.getCreatuser());
						paper.setCreatdate(store.getCreatdate());
						paper.setDiff(store.getDiff());
						paper.setQuestioncore(store.getQuestioncore());
						paper.setTitle(store.getTitle());
						int sq0 = sq + i + 1;
						if(sq0 < 10) {
							paper.setSerial("0" + sq0);
						} else {
							paper.setSerial("" + sq0);
						}
						paper.setScore(store.getReferencescore());
						paper.setLore(store.getLore());
						paper.setCognizetype(store.getCognizetype());
						paper.setPurpose(store.getPurpose());
						paper.setReferencescore(store.getReferencescore());
						paper.setReferencetime(store.getReferencetime());
						paper.setStudentnote(store.getStudentnote());
						paper.setTeachernote(store.getTeachernote());
						paper.setTestpaperId(this.paper_id);
						paper.setType(this.type);
						infoList.add(paper);
					}
					this.getGeneralService().getGeneralDao().setEntityClass(TestPaperquestionInfo.class);
					DetachedCriteria dcp = DetachedCriteria.forClass(TestPaperquestionInfo.class);
					this.getGeneralService().saveList(infoList);
				}
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		
		this.initPaperQuestion();
		return "paper_question_edit";
		
	}
	
	/**
	 * 预览试卷的框架top
	 * 
	 * @return
	 */
	public String previewTestpaperTop() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperInfo.class);
		try {
			TestTestpaperInfo paper = (TestTestpaperInfo)this.getGeneralService().getById(this.paper_id);
			if(paper != null) {
				HttpServletRequest request = ServletActionContext.getRequest ();
				WhatyCookieManage cookieManage = new WhatyCookieManage();
				String time = paper.getTime();
				String secondstr = cookieManage.getCookieValue(request,"TestTime",null); 
				long second = 0;
				if(secondstr == null || secondstr.equals("null")|| secondstr.equals("")) {
					second = Integer.parseInt(time)*60;
				} else {
					second = Integer.parseInt(secondstr);
				}
				this.setSeconds("" + second);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "preview_testpaper_top";
		
	}
	
	/**
	 * 预览试卷的框架left
	 * 
	 * @return
	 */
	public String previewTestpaperLeft() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestPaperquestionInfo.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestPaperquestionInfo.class);
		dct.add(Restrictions.eq("testpaperId", this.paper_id));
		
		try {
			List list = this.getGeneralService().getList(dct);
			if(list != null && list.size() > 0) {
				
				List singleList = new ArrayList();
				List multiList = new ArrayList();
				List judgeList = new ArrayList();
				List blankList = new ArrayList();
				List answerList = new ArrayList();
				List comprehensionList = new ArrayList();
				
				for(int i=0; i<list.size(); i++) {
					
					TestPaperquestionInfo pq = (TestPaperquestionInfo)list.get(i);
					String pq_type = pq.getType();
					
					if(pq_type.equalsIgnoreCase(TestQuestionType.DANXUAN)) {singleList.add(pq);}
						
					if(pq_type.equalsIgnoreCase(TestQuestionType.DUOXUAN)) {multiList.add(pq);}
						
					if(pq_type.equalsIgnoreCase(TestQuestionType.PANDUAN)) {judgeList.add(pq);}
						
					if(pq_type.equalsIgnoreCase(TestQuestionType.TIANKONG)) {blankList.add(pq);}
						
					if(pq_type.equalsIgnoreCase(TestQuestionType.WENDA)) {answerList.add(pq);}
						
					if(pq_type.equalsIgnoreCase(TestQuestionType.YUEDU)) {comprehensionList.add(pq);}
				}
				
				int qNum = 0;//题目编号
				Map numToQuestion = new HashMap();//题目编号和具体题目的对应
				//单选
				List singleObjectList = new ArrayList();
				List singleSerialList = new ArrayList();
				List singleTitleList = new ArrayList();
				List singleNumList = new ArrayList();
				for(int i = 0; i < singleList.size(); i++) {
					int serial = 1000 + i + 1;
					singleSerialList.add(serial);
					qNum = qNum + 1;
					singleNumList.add(qNum);
					TestPaperquestionInfo q = (TestPaperquestionInfo)singleList.get(i);
					numToQuestion.put(qNum, q);
					singleTitleList.add("第" + qNum + "题");
				}
				singleObjectList.add(0, singleList);
				singleObjectList.add(1, singleSerialList);
				singleObjectList.add(2, singleTitleList);
				singleObjectList.add(3, singleNumList);
				//多选
				List multiObjectList = new ArrayList();
				List multiSerialList = new ArrayList();
				List multiTitleList = new ArrayList();
				List multiNumList = new ArrayList();
				for(int i = 0; i < multiList.size(); i++) {
					int serial = 2000 + i + 1;
					multiSerialList.add(serial);
					qNum = qNum + 1;
					multiNumList.add(qNum);
					TestPaperquestionInfo q = (TestPaperquestionInfo)multiList.get(i);
					numToQuestion.put(qNum, q);
					multiTitleList.add("第" + qNum + "题");
				}
				multiObjectList.add(0, multiList);
				multiObjectList.add(1, multiSerialList);
				multiObjectList.add(2, multiTitleList);
				multiObjectList.add(3, multiNumList);
				//判断
				List judgeObjectList = new ArrayList();
				List judgeSerialList = new ArrayList();
				List judgeTitleList = new ArrayList();
				List judgeNumList = new ArrayList();
				for(int i = 0; i < judgeList.size(); i++) {
					int serial = 3000 + i + 1;
					judgeSerialList.add(serial);
					qNum = qNum + 1;
					judgeNumList.add(qNum);
					TestPaperquestionInfo q = (TestPaperquestionInfo)judgeList.get(i);
					numToQuestion.put(qNum, q);
					judgeTitleList.add("第" + qNum + "题");
				}
				judgeObjectList.add(0, judgeList);
				judgeObjectList.add(1, judgeSerialList);
				judgeObjectList.add(2, judgeTitleList);
				judgeObjectList.add(3, judgeNumList);
				//填空
				List blankObjectList = new ArrayList();
				List blankSerialList = new ArrayList();
				List blankTitleList = new ArrayList();
				List blankNumList = new ArrayList();
				for(int i = 0; i < blankList.size(); i++) {
					int serial = 4000 + i + 1;
					blankSerialList.add(serial);
					qNum = qNum + 1;
					blankNumList.add(qNum);
					TestPaperquestionInfo q = (TestPaperquestionInfo)blankList.get(i);
					numToQuestion.put(qNum, q);
					blankTitleList.add("第" + qNum + "题");
				}
				blankObjectList.add(0, blankList);
				blankObjectList.add(1, blankSerialList);
				blankObjectList.add(2, blankTitleList);
				blankObjectList.add(3, blankNumList);
				//问答
				List answerObjectList = new ArrayList();
				List answerSerialList = new ArrayList();
				List answerTitleList = new ArrayList();
				List answerNumList = new ArrayList();
				for(int i = 0; i < answerList.size(); i++) {
					int serial = 5000 + i + 1;
					answerSerialList.add(serial);
					qNum = qNum + 1;
					answerNumList.add(qNum);
					TestPaperquestionInfo q = (TestPaperquestionInfo)answerList.get(i);
					numToQuestion.put(qNum, q);
					answerTitleList.add("第" + qNum + "题");
				}
				answerObjectList.add(0, answerList);
				answerObjectList.add(1, answerSerialList);
				answerObjectList.add(2, answerTitleList);
				answerObjectList.add(3, answerNumList);
				//阅读
				List comprehensionObjectList = new ArrayList();
				List comprehensionSerialList = new ArrayList();
				List comprehensionTitleList = new ArrayList();
				List comprehensionNumList = new ArrayList();
				for(int i = 0; i < comprehensionList.size(); i++) {
					int serial = 6000 + i + 1;
					comprehensionSerialList.add(serial);
					qNum = qNum + 1;
					comprehensionNumList.add(qNum);
					TestPaperquestionInfo q = (TestPaperquestionInfo)comprehensionList.get(i);
					numToQuestion.put(qNum, q);
					comprehensionTitleList.add("第" + qNum + "题");
				}
				comprehensionObjectList.add(0, comprehensionList);
				comprehensionObjectList.add(1, comprehensionSerialList);
				comprehensionObjectList.add(2, comprehensionTitleList);
				comprehensionObjectList.add(3, comprehensionNumList);
				
				List leftQuestionList = new ArrayList();
				leftQuestionList.add(0, singleObjectList);
				leftQuestionList.add(1, multiObjectList);
				leftQuestionList.add(2, judgeObjectList);
				leftQuestionList.add(3, blankObjectList);
				leftQuestionList.add(4, answerObjectList);
				leftQuestionList.add(5, comprehensionObjectList);
				
				List titleStrList = new ArrayList();
				titleStrList.add(0, "单选题");
				titleStrList.add(1, "多选题");
				titleStrList.add(2, "判断题");
				titleStrList.add(3, "填空题");
				titleStrList.add(4, "问答题");
				titleStrList.add(5, "案例分析题");
				
				this.setLeftQuestionList(leftQuestionList);
				
				HttpServletRequest request = ServletActionContext.getRequest ();
				HttpSession session = request.getSession();
				session.setAttribute("leftQuestionList", leftQuestionList);
				session.setAttribute("questionCount", qNum);//试卷总的多少题目
				session.setAttribute("numToQuestion", numToQuestion);//题目编号和具体题目的对应
				session.setAttribute("paperId", this.paper_id);//试卷id
				session.setAttribute("isAutoCheck", this.getPaperIsAutoCheck(this.paper_id));
				
				this.setTitleStrList(titleStrList);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "preview_testpaper_left";
		
	}
	
	/**
	 * frame显示题目信息
	 * 
	 * @return
	 */
	public String previewQuestion() {
		
		HttpServletRequest request = ServletActionContext.getRequest ();
		HttpSession session = request.getSession();
		List leftQuestionList = (List)session.getAttribute("leftQuestionList");
		
		List singleList = null;
		List multiList = null;
		List judgeList = null;
		List blankList = null;
		List answerList = null;
		List comprehensionList = null;
		if(leftQuestionList != null && leftQuestionList.size() > 0) {
			if(leftQuestionList.get(0) != null && ((List)leftQuestionList.get(0)).size() > 0) {
				singleList = (List)((List)leftQuestionList.get(0)).get(0);
			}
			if(leftQuestionList.get(1) != null && ((List)leftQuestionList.get(1)).size() > 0) {
				multiList = (List)((List)leftQuestionList.get(1)).get(0);
			}
			if(leftQuestionList.get(2) != null && ((List)leftQuestionList.get(2)).size() > 0) {
				judgeList = (List)((List)leftQuestionList.get(2)).get(0);
			}
			if(leftQuestionList.get(3) != null && ((List)leftQuestionList.get(3)).size() > 0) {
				blankList = (List)((List)leftQuestionList.get(3)).get(0);
			}
			if(leftQuestionList.get(4) != null && ((List)leftQuestionList.get(4)).size() > 0) {
				answerList = (List)((List)leftQuestionList.get(4)).get(0);
			}
			if(leftQuestionList.get(5) != null && ((List)leftQuestionList.get(5)).size() > 0) {
				comprehensionList = (List)((List)leftQuestionList.get(5)).get(0);
			}
		} else {
			this.setMsg("该试卷暂时还没添加试题！");
		}
		
		int qNum = 0;
		Map standardAnswer = new HashMap();
		Map standardScore = new HashMap();
		Map titleMap = new HashMap();
		Map typeMap = new HashMap();
		List idList = new ArrayList();
		if(singleList != null) {
			List singleObjectList = new ArrayList();
			for(Object o : singleList) {
				TestPaperquestionInfo pq = (TestPaperquestionInfo)o;
				String coreXML = pq.getQuestioncore();
				String id = pq.getId();
				List list = XMLParserUtil.parserSingleMulti(coreXML);
				String body = HtmlEncoder.decode((String)list.get(0));
				String standard_answer = HtmlEncoder.decode((String)list.get(list.size()-1));
				
				List singleIndexContentList = new ArrayList();
				for(int i=1; i<list.size()-2; i=i+2) {
	    			String index = (String)list.get(i);
	    			String content =  HtmlEncoder.decode((String)list.get(i+1));
	    			String s = index+"、"+content;
	    			
	    			List l = new ArrayList();
	    			l.add(0, index);
	    			l.add(1, s);
	    			singleIndexContentList.add(l);
				}
				qNum = qNum + 1;
				
				List ctList = new ArrayList();
				String s = "第"+qNum+"题"+"   "+body;
				ctList.add(0, s);
				ctList.add(1, id);
				ctList.add(2, singleIndexContentList);
				singleObjectList.add(ctList);
				
				standardAnswer.put(id,standard_answer);
				idList.add(id);
				standardScore.put(id, pq.getScore());
				typeMap.put(id, pq.getType());
				titleMap.put(id, s);
				
			}
			this.setSingleObjectList(singleObjectList);
		}
		
		if(multiList != null) {
			List multiObjectList = new ArrayList();
			for(int t=0; t < multiList.size(); t++) {
				TestPaperquestionInfo pq = (TestPaperquestionInfo)multiList.get(t);
				String coreXML = pq.getQuestioncore();
				String id = pq.getId();
				List list = XMLParserUtil.parserSingleMulti(coreXML);
				String body = HtmlEncoder.decode((String)list.get(0));
				String standard_answer = HtmlEncoder.decode((String)list.get(list.size()-1));
				
				List multiIndexContentList = new ArrayList();
				for(int i=1; i<list.size()-2; i=i+2) {
	    			String index = (String)list.get(i);
	    			String content = HtmlEncoder.decode((String)list.get(i+1));
	    			String s = index+"、"+content;
	    			List l = new ArrayList();
	    			l.add(0, index);
	    			l.add(1, s);
	    			multiIndexContentList.add(l);
				}
				List ctList = new ArrayList();
				qNum = qNum + 1;
				String s = "第"+qNum+"题"+"   "+body;
				ctList.add(0, s);
				ctList.add(1, id);
				ctList.add(2, multiIndexContentList);
				multiObjectList.add(ctList);
				
				standardAnswer.put(id,standard_answer);
				idList.add(id);
				standardScore.put(id, pq.getScore());
				typeMap.put(id, pq.getType());
				titleMap.put(id, s);
				
			}
			this.setMultiObjectList(multiObjectList);
		}
		
		if(judgeList != null) {
			List judgeObjectList = new ArrayList();
			for(int t=0;t<judgeList.size();t++) {
				TestPaperquestionInfo pq = (TestPaperquestionInfo)judgeList.get(t);
				String coreXML = pq.getQuestioncore();
				String id = pq.getId();
				List list = XMLParserUtil.parserJudge(coreXML);
				String body = HtmlEncoder.decode((String)list.get(0));
				String standard_answer = HtmlEncoder.decode((String)list.get(list.size()-1));
				
				List ctList = new ArrayList();
				qNum = qNum + 1;
				String s = "第"+qNum+"题"+"   "+body;
				ctList.add(0, s);
				ctList.add(1, id);
				judgeObjectList.add(ctList);
				
				standardAnswer.put(id,standard_answer);
				idList.add(id);
				standardScore.put(id, pq.getScore());
				typeMap.put(id, pq.getType());
				titleMap.put(id, s);
				
			}
			this.setJudgeObjectList(judgeObjectList);
		}
		
		if(blankList != null) {
			List blankObjectList = new ArrayList();
			for(int t=0;t<blankList.size();t++) {
				TestPaperquestionInfo pq = (TestPaperquestionInfo)blankList.get(t);
				String coreXML = pq.getQuestioncore();
				String id = pq.getId();
				List list = XMLParserUtil.parserBlankAnswer(coreXML);
				String body = HtmlEncoder.decode((String)list.get(0));
				String standard_answer = HtmlEncoder.decode((String)list.get(list.size()-1));
				
				List ctList = new ArrayList();
				qNum = qNum + 1;
				String s = "第"+qNum+"题"+"   "+body;
				ctList.add(0, s);
				ctList.add(1, id);
				blankObjectList.add(ctList);
				
				standardAnswer.put(id,standard_answer);
				idList.add(id);
				standardScore.put(id, pq.getScore());
				typeMap.put(id, pq.getType());
				titleMap.put(id, s);
				
			}
			this.setBlankObjectList(blankObjectList);
		}

		if(answerList != null) {
			List answerObjectList = new ArrayList();
			for(int t=0;t<answerList.size();t++) {
				TestPaperquestionInfo pq = (TestPaperquestionInfo)answerList.get(t);
				String coreXML = pq.getQuestioncore();
				String id = pq.getId();
				List list = XMLParserUtil.parserBlankAnswer(coreXML);
				String body = HtmlEncoder.decode((String)list.get(0));
				String standard_answer = HtmlEncoder.decode((String)list.get(list.size()-1));
				
				List ctList = new ArrayList();
				qNum = qNum + 1;
				String s = "第"+qNum+"题"+"   "+body;
				ctList.add(0, s);
				ctList.add(1, id);
				answerObjectList.add(ctList);
				
				standardAnswer.put(id,standard_answer);
				idList.add(id);
				standardScore.put(id, pq.getScore());
				typeMap.put(id, pq.getType());
				titleMap.put(id, s);
				
			}
			this.setAnswerObjectList(answerObjectList);
		}
		
		if(comprehensionList != null) {
			List comprehensionObjectList = new ArrayList();
			for(int t=0;t<comprehensionList.size();t++) {
				List ll = new ArrayList();
				TestPaperquestionInfo pq = (TestPaperquestionInfo)comprehensionList.get(t);
				String coreXML = pq.getQuestioncore();
				String id = pq.getId();
				List coreList = XMLParserUtil.parserComprehension(coreXML);
				String bodyString = HtmlEncoder.decode((String)coreList.get(0));
				List scoreList = new ArrayList();
				List titleList = new ArrayList();
				List typeList = new ArrayList();
				List standard_answer = new ArrayList();
				standard_answer.add("");
				titleList.add(pq.getTitle());
				scoreList.add(pq.getScore());
				typeList.add(pq.getType());
				qNum = qNum + 1;
				
				List li = new ArrayList();
				for(int i = 1; i<coreList.size(); i++) {
					List cList = new ArrayList();
			  		List subList = (List)coreList.get(i);
			  		String subType = HtmlEncoder.decode((String)subList.get(0));
			  		String subTitle = HtmlEncoder.decode((String)subList.get(1));
			  		String subTime = HtmlEncoder.decode((String)subList.get(2));
			  		String subScore = HtmlEncoder.decode((String)subList.get(3));
			  		String subBody = HtmlEncoder.decode((String)subList.get(4));
			  		String subAnswer = HtmlEncoder.decode((String)subList.get(subList.size()-1));
			  		if(subType.equals(TestQuestionType.PANDUAN)) {
			  			if(subAnswer.equals("1")) {
			  				subAnswer = "正确";
			  			} else {
			  				subAnswer = "错误";
			  			}
			  		}	
			  		standard_answer.add(subAnswer);
			  		titleList.add(subTitle);
			  		scoreList.add(subScore);
			  		typeList.add(subType);
			  		
			  		cList.add(0, subTitle);
			  		cList.add(1, subBody);
			  		
			  		if(subType.equals(TestQuestionType.DANXUAN)) {
			  			List l = new ArrayList();
			  			for(int j=5; j<subList.size()-2; j=j+2) {
			  				List ic = new ArrayList();
							String index = (String)subList.get(j);
							String content = HtmlEncoder.decode((String)subList.get(j+1));
							ic.add(0, index);
							ic.add(1, index+"、"+content);
							
							l.add(ic);
			  			}
			  			cList.add(2, "DANXUAN");
			  			cList.add(3, l);
			  		} else if(subType.equals(TestQuestionType.DUOXUAN)) {
			  			List l = new ArrayList();
			  			for(int j=5; j<subList.size()-2; j=j+2) {
			  				List ic = new ArrayList();
							String index = (String)subList.get(j);
							String content = HtmlEncoder.decode((String)subList.get(j+1));
							ic.add(0, index);
							ic.add(1, index+"、"+content);
							
							l.add(ic);
			  			}
			  			cList.add(2, "DUOXUAN");
			  			cList.add(3, l);
			  		} else if(subType.equals(TestQuestionType.PANDUAN)) {
			  			cList.add(2, "PANDUAN");
			  		} else if(subType.equals(TestQuestionType.TIANKONG)) {
			  			cList.add(2, "TIANKONG");
			  		} else if(subType.equals(TestQuestionType.WENDA)) {
			  			cList.add(2, "WENDA");
			  		}
			  		
			  		li.add(cList);
				}
				
				String s = "第" + qNum + "题" + "   " + bodyString;
				ll.add(0, pq.getId());
				ll.add(1, coreList.size() - 1);
				ll.add(2, s);
				ll.add(3, li);
				
				comprehensionObjectList.add(ll);
				
				idList.add(id);
				standardAnswer.put(id,standard_answer);
				standardScore.put(id, scoreList);
				typeMap.put(id, pq.getType());
				typeMap.put(id + "subType", typeList);//存放阅读题的小题的类型
				titleMap.put(id, titleList);
				
			}
			
			this.setComprehensionObjectList(comprehensionObjectList);
		}
		
		session.setAttribute("idList", idList);
		session.setAttribute("standardAnswer", standardAnswer);
		session.setAttribute("standardScore", standardScore);
		session.setAttribute("typeMap", typeMap);
		session.setAttribute("titleMap", titleMap);
		
		//考试预览页面显示单个题目
		String returnVal = "preview_question";
		if(this.question_id != null && this.qNum != null && !"".equals(this.question_id) && !"".equals(this.qNum)) {
			returnVal = this.previewInfo();
		}
		
//		return "preview_question";//考试预览显示所有题目
		return returnVal;
		
	}
	
	/**
	 * 试卷预览中点击左边框架的题目信息
	 * 
	 * @return
	 */
	public String previewInfo() {
		
		HttpServletRequest request = ServletActionContext.getRequest ();
		HttpSession session = request.getSession();
		HashMap userAnswer = (HashMap)session.getAttribute("userAnswer");
		int questionCount = (Integer)session.getAttribute("questionCount");
		int c = Integer.parseInt(qNum);
		if(c > 1 && c <= questionCount) {
			this.setHasPre(true);
		} else {
			this.setHasPre(false);
		}
		if(c >= 1 && c < questionCount) {//后面是否还有题目
			this.setHasNext(true);
		} else {
			this.setHasNext(false);
		}
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestPaperquestionInfo.class);
		try {
			TestPaperquestionInfo pq = (TestPaperquestionInfo)this.getGeneralService().getById(this.question_id);
			if(pq != null) {
				String coreXML = pq.getQuestioncore();
				this.setType(pq.getType());
				if(TestQuestionType.DANXUAN.equalsIgnoreCase(this.type) || TestQuestionType.DUOXUAN.equalsIgnoreCase(this.type)) {
					List list = XMLParserUtil.parserSingleMulti(coreXML);
					
					String body = (String)list.get(0);
					String str = "第" + (Integer.parseInt(qNum)) + "题" + "   " + body;
					this.setTitle(str);
					
					List indexList = new ArrayList();
					for(int i=1; i<list.size()-2; i=i+2) {
						List l = new ArrayList();
	        			String index = (String)list.get(i);
	        			String content = (String)list.get(i+1);
	        			l.add(0, index);
	        			l.add(1, index+"、"+content);
	        			
	        			indexList.add(l);
					}
					this.setIndexList(indexList);
					
				} else if(type.equals(TestQuestionType.PANDUAN)) {
					List list = XMLParserUtil.parserJudge(coreXML);
					String body = (String)list.get(0);
					String str = "第" + (Integer.parseInt(qNum)) + "题" + "   " + body;
					this.setTitle(str);
				} else if(type.equals(TestQuestionType.TIANKONG) || type.equals(TestQuestionType.WENDA)) {
					List list = XMLParserUtil.parserBlankAnswer(coreXML);
					String body = (String)list.get(0);
					String str = "第" + (Integer.parseInt(qNum)) + "题" + "   " + body;
					this.setTitle(str);
				} else if(type.equals(TestQuestionType.YUEDU)) {
					List list = XMLParserUtil.parserComprehension(coreXML);
					String body = (String)list.get(0);
					String str = "第" + (Integer.parseInt(qNum)) + "题" + "   " + body;
					this.setTitle(str);
					
					List compList = new ArrayList();
					List user_answer = null;
					if(userAnswer != null) {
						user_answer = (List)userAnswer.get(pq.getId());
					}
					
					for(int i = 1; i < list.size(); i++) {
						
						List cList = new ArrayList();
						
						List subList = (List)list.get(i);
				  		String subType = (String)subList.get(0);
				  		String subTitle = (String)subList.get(1);
				  		String subTime = (String)subList.get(2);
				  		String subScore = (String)subList.get(3);
				  		String subBody = (String)subList.get(4);
				  		String subAnswer = (String)subList.get(subList.size()-1);
				  		String subUserAnswer;
				  		
				  		try{
				  			subUserAnswer = (String)user_answer.get(i);
				  		}catch(Exception e)
				  		{
				  			subUserAnswer = "";
				  		}
				  		
				  		cList.add(0, i);
				  		cList.add(1, subTitle);
				  		cList.add(2, subBody);
				  		cList.add(3, subUserAnswer);
				  		
				  		if(subType.equals(TestQuestionType.DANXUAN)) {
				  			List l = new ArrayList();
				  			for(int j=5; j<subList.size()-2; j=j+2) {
				  				List inList = new ArrayList();
								String index = (String)subList.get(j);
								String content = (String)subList.get(j+1);
								
								inList.add(0, index);
								inList.add(1, content);
								
								l.add(inList);
				  			}
				  			
				  			cList.add(4, "DANXUAN");
				  			cList.add(5, l);
				  		} else if(subType.equals(TestQuestionType.DUOXUAN)) {
				  			List l = new ArrayList();
				  			for(int j=5; j<subList.size()-2; j=j+2) {
				  				List inList = new ArrayList();
				  				
								String index = (String)subList.get(j);
								String content = (String)subList.get(j+1);
								
								inList.add(0, index);
								inList.add(1, content);
								
								l.add(inList);
				  			}
				  			
				  			cList.add(4, "DUOXUAN");
				  			cList.add(5, l);
				  		} else if(subType.equals(TestQuestionType.PANDUAN)) {
				  			cList.add(4, "PANDUAN");
				  		} else if(subType.equals(TestQuestionType.TIANKONG)) {
				  			cList.add(4, "TIANKONG");
				  		} else if(subType.equals(TestQuestionType.WENDA)) {
				  			cList.add(4, "WENDA");
				  		}
				  		
				  		compList.add(cList);
				  		
					}
					this.setComprehensionObjectList(compList);
					
				}
				
				if(userAnswer!=null && !this.type.equals(TestQuestionType.YUEDU)) {
					this.setUserAnswer((String)userAnswer.get(this.question_id));
				}
				
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		String returnVal = "single_info";
		if(TestQuestionType.DUOXUAN.equalsIgnoreCase(this.type)) {
			returnVal = "multi_info";
		} else if(TestQuestionType.PANDUAN.equalsIgnoreCase(this.type)) {
			returnVal = "judge_info";
		} else if(TestQuestionType.TIANKONG.equalsIgnoreCase(this.type)) {
			returnVal = "blank_info";
		} else if(TestQuestionType.WENDA.equalsIgnoreCase(this.type)) {
			returnVal = "answer_info";
		} else if(TestQuestionType.YUEDU.equalsIgnoreCase(this.type)) {
			returnVal = "comprehension_info";
		}
		return returnVal;
		
	}
	
	/**
	 * 点击上一题，下一题，完成自测时保存答案到session（userAnswer）并导向
	 * 
	 * @return
	 */
	public String saveResultAndDirect() {
		
		HttpServletRequest request = ServletActionContext.getRequest ();
		HttpSession session = request.getSession();
		int questionCount = (Integer)session.getAttribute("questionCount");
		HashMap numToQuestion = (HashMap)session.getAttribute("numToQuestion");
		int num = Integer.parseInt(this.qNum);
		TestPaperquestionInfo q = (TestPaperquestionInfo)numToQuestion.get(num);
		HashMap userAnswer = (HashMap)session.getAttribute("userAnswer");
		if(userAnswer == null) {
			userAnswer = new HashMap();
		}
		
		if(TestQuestionType.DANXUAN.equalsIgnoreCase(this.type)) {
			
			userAnswer.put(q.getId(), this.index);
			
		} else if(TestQuestionType.DUOXUAN.equalsIgnoreCase(this.type)) {
			String s = "";
			for(int i = 0; i < this.indexs.length; i++) {
				s += indexs[i] + "|";
			}
			userAnswer.put(q.getId(), s);
			
		} else if(TestQuestionType.PANDUAN.equalsIgnoreCase(this.type)) {
			
			userAnswer.put(q.getId(), this.answer);
			
		} else if(TestQuestionType.TIANKONG.equalsIgnoreCase(this.type)) {
			
			userAnswer.put(q.getId(), this.answer);
			
		} else if(TestQuestionType.WENDA.equalsIgnoreCase(this.type)) {
			
			userAnswer.put(q.getId(), this.answer);
			
		} else if(TestQuestionType.YUEDU.equalsIgnoreCase(this.type)) {
			
			List user_answer = new ArrayList();
			user_answer.add("");
			
			for(int i=1;i<=Integer.parseInt(totalNum);i++) {
				String answer = "";
				String answers[] = request.getParameterValues("answer_"+i);
				if(answers!=null) {
					for(int t=0;t<answers.length;t++) {
						answer+="|"+answers[t];
					}
				}
				
				if(answer.length()>0) {
					answer = answer.substring(1);
				}
					
				user_answer.add(answer);
			}
			
			userAnswer.put(q.getId(),user_answer);
		}
		
		session.setAttribute("userAnswer", userAnswer);
		TestPaperquestionInfo qq = null;
		if(this.direction != null && direction.equals("next")) {
			num = num + 1;
		} else if(this.direction != null && direction.equals("last")) {
			num = num - 1;
		} else if(this.direction != null && direction.equals("final")) {
			
			this.savePaperResult();
			session.removeAttribute("userAnswer");//保存答案以后remove掉用户答案
			
			if(((String)session.getAttribute("isAutoCheck")).equals("1")) {
				return "paperReslut";
			} else {
				return "paperOver";
			}
			
		}
		
		if(num > 1 && num <= questionCount) {
			this.setHasPre(true);
		} else {
			this.setHasPre(false);
		}
		if(num >= 1 && num < questionCount) {//后面是否还有题目
			this.setHasNext(true);
		} else {
			this.setHasNext(false);
		}
		qq = (TestPaperquestionInfo)numToQuestion.get(num);
		if(qq != null) {
			String coreXML = qq.getQuestioncore();
			this.setType(qq.getType());
			
			if(type.equals(TestQuestionType.DANXUAN) || type.equals(TestQuestionType.DUOXUAN)) {
				List list = XMLParserUtil.parserSingleMulti(coreXML);
				String body = (String)list.get(0);
				String str = "第" + num + "题" + "   " + body;
				this.setTitle(str);
					
				List indexList = new ArrayList();
				for(int i=1; i<list.size()-2; i=i+2) {
					List l = new ArrayList();
	        		String index = (String)list.get(i);
	        		String content = (String)list.get(i+1);
	        		l.add(0, index);
	        		l.add(1, index+"、"+content);
	        		
	        		indexList.add(l);
				}
				
				this.setIndexList(indexList);
				
			} else if(type.equals(TestQuestionType.PANDUAN)) {
				List list = XMLParserUtil.parserJudge(coreXML);
				String body = (String)list.get(0);
				String str = "第" + num + "题" + "   " + body;
				this.setTitle(str);
			} else if(type.equals(TestQuestionType.TIANKONG) || type.equals(TestQuestionType.WENDA)) {
				List list = XMLParserUtil.parserBlankAnswer(coreXML);
				String body = (String)list.get(0);
				String str = "第" + num + "题" + "   " + body;
				this.setTitle(str);
			} else if(type.equals(TestQuestionType.YUEDU)) {
				List list = XMLParserUtil.parserComprehension(coreXML);
				String body = (String)list.get(0);
				String str = "第" + num + "题" + "   " + body;
				this.setTitle(str);
				
				List compList = new ArrayList();
				List user_answer = null;
				if(userAnswer != null) {
					user_answer = (List)userAnswer.get(qq.getId());
				}
				
				for(int i = 1; i < list.size(); i++) {
					
					List cList = new ArrayList();
					
					List subList = (List)list.get(i);
			  		String subType = (String)subList.get(0);
			  		String subTitle = (String)subList.get(1);
			  		String subTime = (String)subList.get(2);
			  		String subScore = (String)subList.get(3);
			  		String subBody = (String)subList.get(4);
			  		String subAnswer = (String)subList.get(subList.size()-1);
			  		String subUserAnswer;
			  		
			  		try{
			  			subUserAnswer = (String)user_answer.get(i);
			  		}catch(Exception e)
			  		{
			  			subUserAnswer = "";
			  		}
			  		
			  		cList.add(0, i);
			  		cList.add(1, subTitle);
			  		cList.add(2, subBody);
			  		cList.add(3, subUserAnswer);
			  		
			  		if(subType.equals(TestQuestionType.DANXUAN)) {
			  			List l = new ArrayList();
			  			for(int j=5; j<subList.size()-2; j=j+2) {
			  				List inList = new ArrayList();
							String index = (String)subList.get(j);
							String content = (String)subList.get(j+1);
							
							inList.add(0, index);
							inList.add(1, content);
							
							l.add(inList);
			  			}
			  			
			  			cList.add(4, "DANXUAN");
			  			cList.add(5, l);
			  		} else if(subType.equals(TestQuestionType.DUOXUAN)) {
			  			List l = new ArrayList();
			  			for(int j=5; j<subList.size()-2; j=j+2) {
			  				List inList = new ArrayList();
			  				
							String index = (String)subList.get(j);
							String content = (String)subList.get(j+1);
							
							inList.add(0, index);
							inList.add(1, content);
							
							l.add(inList);
			  			}
			  			
			  			cList.add(4, "DUOXUAN");
			  			cList.add(5, l);
			  		} else if(subType.equals(TestQuestionType.PANDUAN)) {
			  			cList.add(4, "PANDUAN");
			  		} else if(subType.equals(TestQuestionType.TIANKONG)) {
			  			cList.add(4, "TIANKONG");
			  		} else if(subType.equals(TestQuestionType.WENDA)) {
			  			cList.add(4, "WENDA");
			  		}
			  		
			  		compList.add(cList);
			  		
				}
				
				this.setComprehensionObjectList(compList);
				
			}
			
			if(userAnswer!=null && !type.equals(TestQuestionType.YUEDU)) {
				this.setUserAnswer((String)userAnswer.get(qq.getId()));
			}
			
		}
		
		this.setQNum("" + num);
		
		String returnVal = "single_info";
		if(TestQuestionType.DUOXUAN.equalsIgnoreCase(this.type)) {
			returnVal = "multi_info";
		} else if(TestQuestionType.PANDUAN.equalsIgnoreCase(this.type)) {
			returnVal = "judge_info";
		} else if(TestQuestionType.TIANKONG.equalsIgnoreCase(this.type)) {
			returnVal = "blank_info";
		} else if(TestQuestionType.WENDA.equalsIgnoreCase(this.type)) {
			returnVal = "answer_info";
		} else if(TestQuestionType.YUEDU.equalsIgnoreCase(this.type)) {
			returnVal = "comprehension_info";
		}
		
		return returnVal;
		
	}
	
	/**
	 * 查看已交试卷
	 * 
	 * @return
	 */
	public String viewTestHistoryPaper() {
		
		this.initTestHistoryPaper();
		return "view_history_teacher";
		
	}
	
	/**
	 * 批改试卷
	 * 
	 * @return
	 */
	public String testhistoryCheck() {
		
		HttpServletRequest request = ServletActionContext.getRequest ();
		HttpSession session = request.getSession();
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperHistory.class);
		try {
			TestTestpaperHistory history = (TestTestpaperHistory)this.getGeneralService().getById(this.history_id);
			String paperId = history.getTestpaperId();
			this.setPaper_id(paperId);
			String paperUserId =history.getUserId();
			this.setPaperUser_id(paperUserId);
			HashMap map = this.getTestResultMap(history.getTestResult());
			List idList = (List)map.get("idList");
			session.setAttribute("idList",idList);
			HashMap userAnswer = (HashMap)map.get("userAnswer");
			session.setAttribute("userAnswer",userAnswer);
			
			HashMap standardAnswer = (HashMap)map.get("standardAnswer");
			session.setAttribute("standardAnswer",standardAnswer);
			
			HashMap titleMap = (HashMap)map.get("title");
			session.setAttribute("titleMap",titleMap);
			HashMap typeMap = (HashMap)map.get("type");
			session.setAttribute("typeMap",typeMap);
			HashMap standardScore = (HashMap)map.get("standardScore");
			session.setAttribute("standardScore",standardScore);
			
			HashMap userScore = (HashMap)map.get("userScore");
			session.setAttribute("userScore", userScore);
			
			HashMap Note = (HashMap)map.get("note");
			String total_score = (String)map.get("totalScore");
			String total_note = (String)map.get("totalNote");
			
			this.getGeneralService().getGeneralDao().setEntityClass(TestPaperquestionInfo.class);
			DetachedCriteria dct = DetachedCriteria.forClass(TestPaperquestionInfo.class);
			dct.add(Restrictions.eq("testpaperId", paperId));
			List questionList = this.getGeneralService().getList(dct);
			
			if(questionList != null && questionList.size() > 0) {
				HashMap questionMap = new HashMap();
				
				for(Object o : questionList) {
					TestPaperquestionInfo t = (TestPaperquestionInfo)o;
					questionMap.put(t.getId(), t);
				}
				
				String id = "";
				String title = "";
				String type = "";
				String user_answer = "";
				String standard_answer = "";
				String standard_score = "";
				String user_score = "";
				String note = "";
				List testPaperquestionList = new ArrayList();
				
				for(int i = 0; i < idList.size(); i++) {
					
					List myList = new ArrayList();
					id = (String)idList.get(i);
					type = (String)typeMap.get(id);
					TestPaperquestionInfo pq = (TestPaperquestionInfo)questionMap.get(id);
					String coreXML = pq.getQuestioncore();
					
					if(!type.equalsIgnoreCase(TestQuestionType.YUEDU)) {
            			List list = XMLParserUtil.parserSingleMulti(coreXML);
            			String body = HtmlEncoder.decode((String)list.get(0));
            		
						title = (String)titleMap.get(id);
						standard_answer = (String)standardAnswer.get(id);
						user_answer = (String)userAnswer.get(id);
						if(standard_answer!=null && !standard_answer.equals("") && !standard_answer.equals("null") || user_answer!=null) {
							standard_answer = standard_answer.replace('|',',');
						}
						
						if(type.equalsIgnoreCase(TestQuestionType.PANDUAN)) {
							if(standard_answer.equals("0"))
								standard_answer = "错误";
							if(standard_answer.equals("1"))
								standard_answer = "正确";
						}
						
						standard_score = (String)standardScore.get(id);
						user_answer =(String)userAnswer.get(id);
						user_score = (String)userScore.get(id);
						note = (String)Note.get(id);
						
						if(user_answer != null) {
							user_answer = user_answer.replace('|',',');
						} else {
							user_answer = "未答此题";
						}
							
						if(type.equalsIgnoreCase(TestQuestionType.PANDUAN)) {
							if(user_answer.equals("0")) {
								user_answer = "错误";
							}
							if(user_answer.equals("1")) {
								user_answer = "正确";
							}
						}
						
						if(note==null || note.equals("") || note.equals("null")) {
							note = "";
						}
						
						myList.add(0, "NOTYUEDU");
						myList.add(1, title);
						myList.add(2, TestQuestionType.typeShow(type));
						myList.add(3, body);
						myList.add(4, standard_answer);
						myList.add(5, user_answer);
						myList.add(6, standard_score);
						myList.add(7, user_score);
						myList.add(8, note);
						myList.add(9, id);
						
					} else {
						List coreList = XMLParserUtil.parserComprehension(coreXML);
						String bodyString = HtmlEncoder.decode((String)coreList.get(0));
									
						List titleList = (List)titleMap.get(id);
						List standard_answerList = (List)standardAnswer.get(id);
						List standard_scoreList = (List)standardScore.get(id);
						List user_answerList = (List)userAnswer.get(id);
						List user_scoreList = (List)userScore.get(id);
						List noteList = (List)Note.get(id);
						title = (String)titleList.get(0);
						String notetmp = (String)noteList.get(0);
						String scoretmp = (String)user_scoreList.get(0);
						String scoretmp1 = (String)standard_scoreList.get(0);
						
						myList.add(0, "YUEDU");
						myList.add(1, title);
						myList.add(2, TestQuestionType.typeShow(type));
						myList.add(3, bodyString);
						myList.add(4, scoretmp1);
						myList.add(5, scoretmp);
						myList.add(6, notetmp);
						myList.add(7, id);
						
						if(scoretmp==null || scoretmp.equals("") ||scoretmp.equals("null")) {
							standard_answer = "未答此题！";
							myList.add(8, false);
							myList.add(9, titleList.size());
						} else {
							myList.add(8, true);
							myList.add(9, titleList.size());
							
							List l = new ArrayList();
							for(int k=1;k<titleList.size();k++) {
								
								List ll = new ArrayList();
                        		List subList = (List)coreList.get(k);	
                        		String subBody = HtmlEncoder.decode((String)subList.get(4));	
                        			
                        		String subTitle = (String)titleList.get(k);
								standard_answer = (String)standard_answerList.get(k);
								if(standard_answer!=null) {
									standard_answer = standard_answer.replace('|',',');
								} else {
									standard_answer = "";
								}
								standard_score = (String)standard_scoreList.get(k);
								user_answer = (String)user_answerList.get(k);
								if(user_answer!=null) {
									user_answer = user_answer.replace('|',',');
								}
								user_score = (String)user_scoreList.get(k);
								note = (String)noteList.get(k);
								
								ll.add(0, subTitle);
								ll.add(1, subBody);
								ll.add(2, standard_answer);
								ll.add(3, user_answer);
								ll.add(4, standard_score);
								ll.add(5, user_score);
								ll.add(6, note);
								
								l.add(ll);
								
                        	}
							
							myList.add(10, l);
						}
						
						
						
						
					}
					testPaperquestionList.add(myList);
				}
				this.setTotalScore(total_score);
				this.setTotalNote(total_note);
				this.setResultList(testPaperquestionList);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "testhistory_check";
		
	}
	
	/**
	 * 查看已交试卷的详细信息
	 * 
	 * @return
	 */
	public String viewHistoryInfo() {
		
		this.initHistoryInfo();
		return "view_history_info";
		
	}
	
	/**
	 * 删除已交试卷
	 * 
	 * @return
	 */
	public String deleteHistory() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperHistory.class);
		try {
			TestTestpaperHistory history = (TestTestpaperHistory)this.getGeneralService().getById(this.history_id);
			this.getGeneralService().delete(history);
			this.setMsg("删除试卷成功！");
		} catch (EntityException e) {
			this.setMsg("删除试卷失败！");
			e.printStackTrace();
		}
		
		this.initTestHistoryPaper();
		return "view_history_teacher";
	}
	
	/**
	 * 教师批改试卷
	 * 
	 * @return
	 */
	public String checkPaper() {
		
		HttpServletRequest request = ServletActionContext.getRequest ();
		HttpSession session = request.getSession();
		
		HashMap userAnswer = (HashMap)session.getAttribute("userAnswer");
		HashMap standardAnswer = (HashMap)session.getAttribute("standardAnswer");
		HashMap titleMap = (HashMap)session.getAttribute("titleMap");
		HashMap typeMap = (HashMap)session.getAttribute("typeMap");
		HashMap standardScore = (HashMap)session.getAttribute("standardScore");
		HashMap userScore = new HashMap();
		HashMap noteMap = new HashMap();
		Set set = titleMap.keySet();
		String id = "";
		String type = "";
		String uScore = "";
		String note = "";
		String totalnum = "";
		for(Iterator it=set.iterator();it.hasNext();) {
			id = (String) it.next();
			type = (String)typeMap.get(id);
			if(!type.equalsIgnoreCase(TestQuestionType.YUEDU)) {
				uScore = request.getParameter(id+"_score").trim();
				
				note = request.getParameter(id+"_note").trim();
				userScore.put(id,uScore);
				noteMap.put(id,note);
				
			} else {
				
				List scoreList = new ArrayList();
				List noteList = new ArrayList();
				totalnum = request.getParameter(id+"_totalnum").trim();
				uScore = request.getParameter(id+"_score").trim();
				scoreList.add(uScore);
				note = request.getParameter(id+"_note").trim();
			
				noteList.add(note);
				for(int i=1;i<Integer.parseInt(totalnum);i++)
				{
//					uScore = request.getParameter(id+"_"+i+"_score").trim();
					scoreList.add(uScore);
//					note = request.getParameter(id+"_"+i+"_note").trim();
					noteList.add(note);
				}
				userScore.put(id,scoreList);
				noteMap.put(id,noteList);
			}
		}
		
		HashMap map = new HashMap();
		map.put("idList",session.getAttribute("idList"));
		map.put("userAnswer",userAnswer);
		map.put("standardAnswer",standardAnswer);
		map.put("title",titleMap);
		map.put("type",typeMap);
		map.put("standardScore",standardScore);
		map.put("userScore",userScore);
		map.put("note",noteMap);
		map.put("totalScore",totalScore);
		map.put("totalNote",totalNote);
		map.put("status","1");
		
		String result = this.getTestResult(map);
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperHistory.class);
		try {
			TestTestpaperHistory p_history = (TestTestpaperHistory)this.getGeneralService().getById(this.history_id);
			p_history.setIscheck("1");
			p_history.setTestResult(result);
			p_history.setScore(totalScore);
			
			this.getGeneralService().update(p_history);
			this.setMsg("试卷批改保存成功");
		} catch (EntityException e) {
			this.setMsg("试卷批改保存失败");
			e.printStackTrace();
		}
		
		this.initTestHistoryPaper();
		return "view_history_teacher";
		
	}
	
	/**
	 * 初始化已交试卷信息
	 * 
	 */
	private void initHistoryInfo() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperHistory.class);
		try {
			TestTestpaperHistory history = (TestTestpaperHistory)this.getGeneralService().getById(this.history_id);
			
			if(history != null) {
				HashMap map = this.getTestResultMap(history.getTestResult());
				List idList = (List)map.get("idList");
				HashMap userAnswer = (HashMap)map.get("userAnswer");
				HashMap standardAnswer = (HashMap)map.get("standardAnswer");
				HashMap titleMap = (HashMap)map.get("title");
				HashMap typeMap = (HashMap)map.get("type");
				HashMap standardScore = (HashMap)map.get("standardScore");
				HashMap userScore = (HashMap)map.get("userScore");
				HashMap noteMap = (HashMap)map.get("note");
				String total_score = "0";
				if(history.getScore() != null && !"".equals(history.getScore()) && !history.getScore().equals("null")) {
					total_score = String.valueOf(Math.floor(Double.parseDouble(history.getScore())));
				} 
				String total_note = (String)map.get("totalNote");
				
				String id = "";
				String title = "";
				String type = "";
				String user_answer = "";
				String standard_answer = "";
				String standard_score = "";
				String user_score = "";
				String note = "";
				List testQuestionList = new ArrayList();
				for(int i = 0; i < idList.size(); i++) {
					id = (String)idList.get(i);
					type = (String)typeMap.get(id);
					List myList = new ArrayList();
					
					this.getGeneralService().getGeneralDao().setEntityClass(TestPaperquestionInfo.class);
					TestPaperquestionInfo q = (TestPaperquestionInfo)this.getGeneralService().getById(id);
					
					if(!type.equalsIgnoreCase(TestQuestionType.YUEDU)) {
						title = (String)titleMap.get(id);
						standard_answer = (String)standardAnswer.get(id);
						user_answer = (String)userAnswer.get(id);
						
						if(type.equalsIgnoreCase(TestQuestionType.PANDUAN)) {
							if(standard_answer.equals("0"))
								standard_answer = "错误";
							if(standard_answer.equals("1"))
								standard_answer = "正确";
						}
						
						standard_score = (String)standardScore.get(id);
						user_answer = (String)userAnswer.get(id);
						user_score = (String)userScore.get(id);
						note = (String)noteMap.get(id);
						if(user_answer!=null) {
							user_answer = user_answer.replace('|',',');
						} else {
							user_answer = "未答此题";
						}
						
						if(standard_answer!=null) {
							standard_answer = standard_answer.replace('|',',');
						} else {
							standard_answer = "";
						}
							
						if(type.equalsIgnoreCase(TestQuestionType.PANDUAN)) {
							if(user_answer.equals("0"))
								user_answer = "错误";
							if(user_answer.equals("1"))
								user_answer = "正确";
						}
						
						if(note==null || note.equals("") || note.equals("null")) {
							note = "";
						}
							
						String questionCore = "";
						if(q != null) {
							if(type.equalsIgnoreCase(TestQuestionType.DANXUAN) || type.equalsIgnoreCase(TestQuestionType.DUOXUAN)) {
								questionCore = XMLParserUtil.getSingleMultiContent(q.getQuestioncore());
							}
								
							if(type.equalsIgnoreCase(TestQuestionType.TIANKONG) || type.equalsIgnoreCase(TestQuestionType.WENDA)) {
								questionCore = XMLParserUtil.getBlankAnswerContent(q.getQuestioncore());
							}
								
							if(type.equalsIgnoreCase(TestQuestionType.PANDUAN)) {
								questionCore = XMLParserUtil.getJudgContent(q.getQuestioncore());
							}
						}
						
						myList.add(0, "NOTYUEDU");
						myList.add(1, id);
						myList.add(2, title);
						myList.add(3, questionCore);
						myList.add(4, standard_answer);
						myList.add(5, user_answer);
						myList.add(6, standard_score);
						myList.add(7, user_score);
						myList.add(8, note);
						
					} else {
						
						List coreList = XMLParserUtil.parserComprehension(q.getQuestioncore());
						String bodyString = HtmlEncoder.decode((String)coreList.get(0));
						
						List titleList = (List)titleMap.get(id);
						List standard_answerList = (List)standardAnswer.get(id);
						List standard_scoreList = (List)standardScore.get(id);
						List user_answerList = (List)userAnswer.get(id);
						List user_scoreList = (List)userScore.get(id);
						List noteList = (List)noteMap.get(id);
						title = (String)titleList.get(0);
						String notetmp = (String)noteList.get(0);
						String scoretmp = (String)user_scoreList.get(0);
						String scoretmp1 = (String)standard_scoreList.get(0);
						
						myList.add(0, "YUEDU");
						myList.add(1, title);
						myList.add(2, bodyString);
						myList.add(3, scoretmp1);
						myList.add(4, scoretmp);
						myList.add(5, notetmp);
						
						List l = new ArrayList();
						if(scoretmp==null || scoretmp.equals("") ||scoretmp.equals("null")) {
							myList.add(6, false);
						} else {
							myList.add(6, true);
							
							for(int k=1;k<titleList.size();k++) {
								List ll = new ArrayList();
								
								List subList = (List)coreList.get(k);	
								String subType = (String)subList.get(0);
                        		String subBody = HtmlEncoder.decode((String)subList.get(4));	
                        			
                        		String subTitle = (String)titleList.get(k);
								standard_answer = (String)standard_answerList.get(k);
								if(standard_answer!=null) {
									standard_answer = standard_answer.replace('|',',');
								} else {
									standard_answer = "";
								}
								standard_score = (String)standard_scoreList.get(k);
								user_answer = (String)user_answerList.get(k);
								if(user_answer!=null) {
									user_answer = user_answer.replace('|',',');
								}
								user_score = (String)user_scoreList.get(k);
								note = (String)noteList.get(k);
								
								ll.add(0, subTitle);
								ll.add(1, subBody);
								ll.add(2, standard_answer);
								ll.add(3, user_answer);
								ll.add(4, standard_score);
								ll.add(5, user_score);
								ll.add(6, note);
								if(subType.equals(TestQuestionType.DANXUAN)) {
						  			String s = "<br/>";
						  			for(int j=5; j<subList.size()-2; j=j+2) {
						  				List inList = new ArrayList();
										String index = (String)subList.get(j);
										String content = (String)subList.get(j+1);
										
										s = s + index + "、" + content + "<br/>";
						  			}
						  			
						  			ll.add(7, s);
						  			
						  		} else if(subType.equals(TestQuestionType.DUOXUAN)) {
						  			String s = "<br/>";
						  			for(int j=5; j<subList.size()-2; j=j+2) {
						  				List inList = new ArrayList();
						  				
										String index = (String)subList.get(j);
										String content = (String)subList.get(j+1);
										
										s = s + index + "、" + content + "<br/>";
						  			}
						  			
						  			ll.add(7, s);
						  		} else if(subType.equals(TestQuestionType.PANDUAN)) {
						  			ll.add(7, "");
						  		} else if(subType.equals(TestQuestionType.TIANKONG)) {
						  			ll.add(7, "");
						  		} else if(subType.equals(TestQuestionType.WENDA)) {
						  			ll.add(7, "");
						  		}
								
								l.add(ll);
                        	}
							
							myList.add(7, l);
						}
						
					}
					
					testQuestionList.add(myList);
					this.setQuestionList(testQuestionList);
					this.setTotalScore(total_score);
					this.setTotalNote(total_note);
				}
			}
			
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 保存测试结果到test_testpaper_history表
	 * 
	 * @return
	 */
	private void savePaperResult() {
		
		HttpServletRequest request = ServletActionContext.getRequest ();
		HttpSession session = request.getSession();
		
		String p_id = (String)session.getAttribute("paperId");
		HashMap userAnswer = (HashMap)session.getAttribute("userAnswer");
		HashMap standardAnswer = (HashMap)session.getAttribute("standardAnswer");
		HashMap titleMap = (HashMap)session.getAttribute("titleMap");
		HashMap typeMap = (HashMap)session.getAttribute("typeMap");
		HashMap standardScore = (HashMap)session.getAttribute("standardScore");
		
		HashMap map = new HashMap();
		map.put("idList",session.getAttribute("idList"));
		map.put("userAnswer",userAnswer);
		map.put("standardAnswer",standardAnswer);
		map.put("title",titleMap);
		map.put("type",typeMap);
		map.put("standardScore",standardScore);
		String totalScore = "0.0";
		
		if(((String)session.getAttribute("isAutoCheck")).equals("1")) {
			HashMap userScore = this.getUserQuestionScore();
			totalScore = this.getTotalScore();
			map.put("userScore",userScore);
			map.put("totalScore",totalScore);
		}
		
		UserSession userSession = (UserSession) ActionContext.getContext()
			.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		String result = this.getTestResult(map);

		
		TestTestpaperHistory p_history = new TestTestpaperHistory();
		p_history.setIscheck((String)session.getAttribute("isAutoCheck"));
		p_history.setTestDate(new Date());
		p_history.setTestpaperId((String)session.getAttribute("paperId"));
		p_history.setTestResult(result);
		p_history.setUserId(ssoUser.getId());
		p_history.setScore(totalScore);
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperHistory.class);
		try {
			this.getGeneralService().save(p_history);
			this.setMsg("测试结束，试卷信息保存成功！");
		} catch (EntityException e) {
			this.setMsg("测试结束，试卷信息保存失败！");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取当前试卷的学员答题得分
	 * 
	 * @return
	 */
	private HashMap getUserQuestionScore() {
		
		HashMap userScore = new HashMap();
		float user_score = 0f;
		float total_score = 0f;
		String id = "";
		String title = "";
		String type = "";
		String user_answer = "";
		String standard_answer = "";
		String standard_score = "";
		
		HttpServletRequest request = ServletActionContext.getRequest ();
		HttpSession session = request.getSession();
		HashMap titleMap = (HashMap)session.getAttribute("titleMap");
		HashMap typeMap = (HashMap)session.getAttribute("typeMap");
		HashMap standardAnswer = (HashMap)session.getAttribute("standardAnswer");
		HashMap standardScore = (HashMap)session.getAttribute("standardScore");
		HashMap userAnswer = (HashMap)session.getAttribute("userAnswer");
		List idList = (List)session.getAttribute("idList");
		
		List resultList = new ArrayList();
		
		for(int i = 0; i < idList.size(); i++) {
			id = (String)idList.get(i);
			type = (String)typeMap.get(id);
			List infoList = new ArrayList();
			
			if(!type.equalsIgnoreCase(TestQuestionType.YUEDU)) {
				title = (String)titleMap.get(id);
				standard_answer = (String)standardAnswer.get(id);
				
				if(standard_answer!=null) {
					standard_answer = standard_answer.replace('|',',');
				} else {
					standard_answer = "";
				}
					
				standard_score = (String)standardScore.get(id);
				user_answer = (String)userAnswer.get(id);
				
				if(user_answer!=null) {
					user_answer = user_answer.replace('|',',');
				}
				else {
					user_answer = "未答此题";
				}
				
				if(user_answer!=null && standard_answer.trim().equals(user_answer.trim())) {
					user_score = Float.parseFloat(standard_score);
				} else {
					user_score = 0f;
				}
				
				total_score += user_score;
				userScore.put(id,Float.toString(user_score));
				
				infoList.add(0, "NOTYUEDU");
				infoList.add(1, title);
				infoList.add(2, standard_answer);
				infoList.add(3, user_answer);
				infoList.add(4, Float.toString(user_score));
				
			} else {
				List titleList = (List)titleMap.get(id);
				List standard_answerList = (List)standardAnswer.get(id);
				List standard_scoreList = (List)standardScore.get(id);
				List user_answerList = (List)userAnswer.get(id);
				List user_scoreList = new ArrayList();
				float scoretmp = 0f;
				user_scoreList.add("");
				title = (String)titleList.get(0);
				infoList.add(0, "YUEDU");
				infoList.add(1, title);
				
				if(user_answer==null) {
					user_answer = "未答此题";
					infoList.add(2, false);
				} else {
					infoList.add(2, true);
					List l = new ArrayList();
					List uScoreList=new LinkedList();
					for(int k=1;k<titleList.size();k++) {
						List cl = new ArrayList();
                		title = (String)titleList.get(k);
						standard_answer = (String)standard_answerList.get(k);
						
						if(standard_answer!=null) {
							standard_answer = standard_answer.replace('|',',');
						} else {
							standard_answer = "";
						}
						
						standard_score = (String)standard_scoreList.get(k);
						
						if(user_answerList!=null) {
							user_answer = (String)user_answerList.get(k);
							user_answer = user_answer.replace('|',',');
						} else {
							user_answer = "未答此题";
						}
							
						if(user_answer!=null && standard_answer.equals(user_answer)) {
							user_score = Float.parseFloat(standard_score);
							
						} else {
							user_score = 0f;
						}
						
						total_score += user_score;
						scoretmp += user_score;
						
						user_scoreList.add(Float.toString(user_score));
						uScoreList.add(user_score+"");
						
						cl.add(0, title);
						cl.add(1, standard_answer);
						cl.add(2, user_answer);
						cl.add(3, Float.toString(user_score));
						cl.add(4, Float.toString(scoretmp));
						
						l.add(cl);
                	}
					userScore.put(id,uScoreList);
					uScoreList.add(0, scoretmp+"");
					infoList.add(3, l);
				}
				
			}
			
			resultList.add(infoList);
			
		}
		
		this.setResultList(resultList);
		this.setTotalScore("" + total_score);
		return userScore;
		
	}
	
	/**
	 * 获取test_result
	 * 
	 * @param map
	 * @return
	 */
	private String getTestResult(HashMap map) {
		String xml = "<answers>";
		List idList = (List) map.get("idList");
		HashMap userAnswer = (HashMap) map.get("userAnswer");
		HashMap standardAnswer = (HashMap) map.get("standardAnswer");
		HashMap Title = (HashMap) map.get("title");
		HashMap Type = (HashMap) map.get("type");
		HashMap standardScore = (HashMap) map.get("standardScore");
		HashMap userScore = (HashMap) map.get("userScore");
		HashMap Note = (HashMap) map.get("note");
		if (userAnswer == null)
			userAnswer = new HashMap();
		if (standardAnswer == null)
			standardAnswer = new HashMap();
		if (Title == null)
			Title = new HashMap();
		if (Type == null)
			Type = new HashMap();
		if (standardScore == null)
			standardScore = new HashMap();
		if (userScore == null)
			userScore = new HashMap();
		if (Note == null)
			Note = new HashMap();
		String totalScore = (String) map.get("totalScore");
		xml += "<totalscore>" + totalScore + "</totalscore>";
		String totalNote = (String) map.get("totalNote");
		if (totalNote == null)
			totalNote = "";
		xml += "<totalnote>" + HtmlEncoder.encode(totalNote) + "</totalnote>";
		String id = "";
		String type = "";
		String uAnswer = "";
		String sAnswer = "";
		String title = "";
		String sScore = "";
		String uScore = "";
		String note = "";
		for (Iterator it = idList.iterator(); it.hasNext();) {
			id = (String) it.next();
			type = (String) Type.get(id);
			if (!type.equalsIgnoreCase(TestQuestionType.YUEDU)) {
				uAnswer = (String) userAnswer.get(id);
				sAnswer = (String) standardAnswer.get(id);
				title = (String) Title.get(id);
				sScore = (String) standardScore.get(id);
				uScore = (String) userScore.get(id);
				note = (String) Note.get(id);
				if (uAnswer == null || uAnswer.equals("")
						|| uAnswer.equals("null"))
					uAnswer = "";
				if (sAnswer == null || sAnswer.equals("")
						|| sAnswer.equals("null"))
					sAnswer = "";
				if (title == null || title.equals("") || title.equals("null"))
					title = "";
				if (type == null || type.equals("") || type.equals("null"))
					type = "";
				if (sScore == null || sScore.equals("")
						|| sScore.equals("null"))
					sScore = "0";
				if (uScore == null || uScore.equals("")
						|| uScore.equals("null"))
					uScore = "0";
				if (note == null || note.equals("") || note.equals("null"))
					note = "";
				xml += "<item><id>" + id + "</id><type>" + type
						+ "</type><title>" + HtmlEncoder.encode(title)
						+ "</title><sanswer>" + HtmlEncoder.encode(sAnswer)
						+ "</sanswer><uanswer>" + HtmlEncoder.encode(uAnswer)
						+ "</uanswer><sscore>" + HtmlEncoder.encode(sScore)
						+ "</sscore><uscore>" + HtmlEncoder.encode(uScore)
						+ "</uscore><note>" + HtmlEncoder.encode(note)
						+ "</note></item>";
			} else {
				xml += "<item><id>" + id + "</id><type>" + type + "</type>";
				List uAnswerList = (List) userAnswer.get(id);
				if (uAnswerList == null)
					uAnswerList = new ArrayList();
				List sAnswerList = (List) standardAnswer.get(id);
				List titleList = (List) Title.get(id);
				List sScoreList = (List) standardScore.get(id);
				List uScoreList = (List) userScore.get(id);
				if (uScoreList == null)
					uScoreList = new ArrayList();
				List noteList = (List) Note.get(id);
				if (noteList == null)
					noteList = new ArrayList();
				title = (String) titleList.get(0);
				if (title == null || title.equals("") || title.equals("null"))
					title = "";
				xml += "<title>" + HtmlEncoder.encode(title) + "</title>";
				sScore = (String) sScoreList.get(0);
				if (sScore == null || sScore.equals("")
						|| sScore.equals("null"))
					sScore = "0";
				xml += "<sscore>" + HtmlEncoder.encode(sScore) + "</sscore>";
				try{
				uScore = (String) uScoreList.get(0);
				} catch(Exception e1) {
					
				}
				if (uScore == null || uScore.equals("")
						|| uScore.equals("null"))
					uScore = "0";
				xml += "<uscore>" + HtmlEncoder.encode(uScore) + "</uscore>";
				if (noteList != null && noteList.size() > 0)
					note = (String) noteList.get(0);
				else
					note = "";
				if (note == null || note.equals("") || note.equals("null"))
					note = "";
				xml += "<note>" + HtmlEncoder.encode(note) + "</note><subitem>";

				for (int k = 1; k < titleList.size(); k++) {
					try {
						uAnswer = (String) uAnswerList.get(k);
					} catch (Exception e) {
						uAnswer = "";
					}
					sAnswer = (String) sAnswerList.get(k);
					title = (String) titleList.get(k);
					sScore = (String) sScoreList.get(k);
					try {
						uScore = (String) uScoreList.get(k);
					} catch (Exception e) {
						uScore = "";
					}
					if (noteList != null && noteList.size() > 0)
						note = (String) noteList.get(k);
					else
						note = "";
					if (uAnswer == null || uAnswer.equals("")
							|| uAnswer.equals("null"))
						uAnswer = "";
					if (sAnswer == null || sAnswer.equals("")
							|| sAnswer.equals("null"))
						sAnswer = "";
					if (title == null || title.equals("")
							|| title.equals("null"))
						title = "";
					if (sScore == null || sScore.equals("")
							|| sScore.equals("null"))
						sScore = "0";
					if (uScore == null || uScore.equals("")
							|| uScore.equals("null"))
						uScore = "0";
					if (note == null || note.equals("") || note.equals("null"))
						note = "";
					xml += "<item><id>" + k + "</id><title>"
							+ HtmlEncoder.encode(title) + "</title><sanswer>"
							+ HtmlEncoder.encode(sAnswer)
							+ "</sanswer><uanswer>"
							+ HtmlEncoder.encode(uAnswer)
							+ "</uanswer><sscore>" + HtmlEncoder.encode(sScore)
							+ "</sscore><uscore>" + HtmlEncoder.encode(uScore)
							+ "</uscore><note>" + HtmlEncoder.encode(note)
							+ "</note></item>";
				}
				xml += "</subitem></item>";
			}
		}
		xml += "</answers>";
		
		return xml;
		
	}
	
	/**
	 * 初始化已交试卷
	 * 
	 */
	private void initTestHistoryPaper() {
		
		this.setUserRoleCode();
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperHistory.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestTestpaperHistory.class);
		dct.add(Restrictions.eq("testpaperId", this.paper_id));
		
		if(searchVal != null && !"".equals(searchVal)) {
			dct.add(Restrictions.eq("ischeck", searchVal));
		}
		try {
			List list = this.getGeneralService().getList(dct);
			int s = list.size();
			this.setTotalSize(s);
			if(s == 0) {
				this.setTotalPage(1);
			} else if(s % this.SIZE_PER_PAGE == 0) {
				this.setTotalPage(s/this.SIZE_PER_PAGE);
			} else {
				this.setTotalPage(s/this.SIZE_PER_PAGE + 1);
			}
			
			Page p = this.getGeneralService().getByPage(dct, SIZE_PER_PAGE, (curPage - 1) * SIZE_PER_PAGE);
			
			if(p != null) {
				List pList = new ArrayList();
				for(Object o : p.getItems()) {
					List l = new ArrayList();
					TestTestpaperHistory h = (TestTestpaperHistory)o;
					String pId = h.getTestpaperId();
					this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperInfo.class);
					TestTestpaperInfo tt = (TestTestpaperInfo)this.getGeneralService().getById(pId);
					
					String uId = h.getUserId();
					this.getGeneralService().getGeneralDao().setEntityClass(SsoUser.class);
					SsoUser user = (SsoUser)this.getGeneralService().getById(uId);
					
					l.add(0, h);
					if(tt != null) {
						l.add(1, tt.getTitle());
					}
					if(user != null) {
						l.add(2,user.getLoginId());
					}
					
					pList.add(l);
				}
				
				this.setTestpaperHistoryList(pList);
			}
		} catch(EntityException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 根据已交试卷的test_result获取相关的map
	 * 
	 * @return
	 */
	private HashMap getTestResultMap(String result) {
		HashMap map = new HashMap();
		try {
			Document doc = DocumentHelper.parseText(result);
			String totalScore = doc.selectSingleNode("/answers/totalscore")
					.getText();
			map.put("totalScore", totalScore);
			String totalNote = doc.selectSingleNode("/answers/totalnote")
					.getText();
			map.put("totalNote", totalNote);
			List answerList = doc.selectNodes("/answers/item");
			HashMap userAnswer = new HashMap();
			HashMap standardAnswer = new HashMap();
			HashMap Title = new HashMap();
			HashMap Type = new HashMap();
			HashMap standardScore = new HashMap();
			HashMap userScore = new HashMap();
			HashMap Note = new HashMap();
			List idList = new ArrayList();
			String id = "";
			String uAnswer = "";
			String sAnswer = "";
			String title = "";
			String type = "";
			String sScore = "";
			String uScore = "";
			String note = "";
			for (Iterator it = answerList.iterator(); it.hasNext();) {
				Element answer = (Element) it.next();
				Element idEle = answer.element("id");
				id = idEle.getTextTrim();
				idList.add(id);
				Element typeEle = answer.element("type");
				type = typeEle.getTextTrim();
				if (!type.equalsIgnoreCase(TestQuestionType.YUEDU)) {
					Element uAnswerEle = answer.element("uanswer");
					uAnswer = uAnswerEle.getTextTrim();
					Element sAnswerEle = answer.element("sanswer");
					sAnswer = sAnswerEle.getTextTrim();
					Element titleEle = answer.element("title");
					title = titleEle.getTextTrim();
					Element sScoreEle = answer.element("sscore");
					sScore = sScoreEle.getTextTrim();
					Element uScoreEle = answer.element("uscore");
					uScore = uScoreEle.getTextTrim();
					Element noteEle = answer.element("note");
					note = noteEle.getTextTrim();

					userAnswer.put(id, HtmlEncoder.decode(uAnswer));
					standardAnswer.put(id, HtmlEncoder.decode(sAnswer));
					Title.put(id, HtmlEncoder.decode(title));
					Type.put(id, type);
					standardScore.put(id, HtmlEncoder.decode(sScore));
					userScore.put(id, HtmlEncoder.decode(uScore));
					Note.put(id, HtmlEncoder.decode(note));
				} else {
					List uAnswerList = new ArrayList();
					List sAnswerList = new ArrayList();
					List titleList = new ArrayList();
					List sScoreList = new ArrayList();
					List uScoreList = new ArrayList();
					List noteList = new ArrayList();

					uAnswer = "";
					uAnswerList.add(HtmlEncoder.decode(uAnswer));
					sAnswer = "";
					sAnswerList.add(HtmlEncoder.decode(sAnswer));
					Element titleEle = answer.element("title");
					title = titleEle.getTextTrim();
					titleList.add(HtmlEncoder.decode(title));
					Element sScoreEle = answer.element("sscore");
					sScore = sScoreEle.getTextTrim();
					sScoreList.add(HtmlEncoder.decode(sScore));
					Element uScoreEle = answer.element("uscore");
					uScore = uScoreEle.getTextTrim();
					uScoreList.add(HtmlEncoder.decode(uScore));
					Element noteEle = answer.element("note");
					note = noteEle.getTextTrim();
					noteList.add(HtmlEncoder.decode(note));

					Element subEle = answer.element("subitem");//
					Iterator itSub = subEle.elementIterator("item");//
					while (itSub.hasNext()) {
						Element subItem = (Element) itSub.next();//
						Element idEleSub = answer.element("id");
						id = idEleSub.getTextTrim();

						Element uAnswerEleSub = subItem.element("uanswer");
						uAnswer = uAnswerEleSub.getTextTrim();
						uAnswerList.add(HtmlEncoder.decode(uAnswer));
						Element sAnswerEleSub = subItem.element("sanswer");
						sAnswer = sAnswerEleSub.getTextTrim();
						sAnswerList.add(HtmlEncoder.decode(sAnswer));
						Element titleEleSub = subItem.element("title");
						title = titleEleSub.getTextTrim();
						titleList.add(HtmlEncoder.decode(title));
						Element sScoreEleSub = subItem.element("sscore");//
						sScore = sScoreEleSub.getTextTrim();
						sScoreList.add(HtmlEncoder.decode(sScore));
						Element uScoreEleSub = subItem.element("uscore");
						uScore = uScoreEleSub.getTextTrim();
						uScoreList.add(HtmlEncoder.decode(uScore));
						Element noteEleSub = subItem.element("note");
						note = noteEleSub.getTextTrim();
						noteList.add(HtmlEncoder.decode(note));
					}
					userAnswer.put(id, uAnswerList);
					standardAnswer.put(id, sAnswerList);
					Title.put(id, titleList);
					Type.put(id, type);
					standardScore.put(id, sScoreList);
					userScore.put(id, uScoreList);
					Note.put(id, noteList);
				}
			}
			map.put("idList", idList);
			map.put("userAnswer", userAnswer);
			map.put("standardAnswer", standardAnswer);
			map.put("title", Title);
			map.put("type", Type);
			map.put("standardScore", standardScore);
			map.put("userScore", userScore);
			map.put("note", Note);
		} catch (DocumentException de) {
		}
		return map;
	}
	
	/**
	 * 初始化未添加的题目
	 * 
	 */
	private void initQuestionItem() {
		
		String url = "/entity/studyZone/courseResources_viewQuestion.action";
		try {
			this.setTypeStr(TestQuestionType.typeShow(this.type));
			
			if(type.equals(TestQuestionType.YUEDU)) {
				url = "/entity/studyZone/courseResources_viewYueDuQuestion.action";
			}
		} catch (TypeErrorException e1) {
			e1.printStackTrace();
		}
		
		this.setUrl(url);
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestLoreInfo.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestLoreInfo.class);
		dct.createCriteria("testLoreDir", "testLoreDir");
		dct.add(Restrictions.eq("testLoreDir.groupId", this.course_id));
		if(this.searchVal != null && "".equals(this.searchVal)) {
			dct.add(Restrictions.like("name", this.searchVal, MatchMode.ANYWHERE));
		}
		
		try {
			List list = this.getGeneralService().getList(dct);
			if(list != null && list.size() > 0) {
				List idList = new ArrayList();
				for(Object o : list) {
					String id = ((TestLoreInfo)o).getId();
					idList.add(id);
				}
				
				//找出已经添加到试卷的题目并从itemList中去掉
				this.getGeneralService().getGeneralDao().setEntityClass(TestPaperquestionInfo.class);
				DetachedCriteria dcp = DetachedCriteria.forClass(TestPaperquestionInfo.class);
				dcp.add(Restrictions.eq("type", this.type));
				dcp.add(Restrictions.eq("testpaperId", this.paper_id));
				List items = this.getGeneralService().getList(dcp);
				
				List il = new ArrayList();
				this.getGeneralService().getGeneralDao().setEntityClass(TestStorequestionInfo.class);
				
				if(items != null && items.size() > 0) {
					for(Object o : items) {
						DetachedCriteria dcs = DetachedCriteria.forClass(TestStorequestionInfo.class);
						TestPaperquestionInfo q = (TestPaperquestionInfo)o;
						dcs.add(Restrictions.eq("title", q.getTitle()));
						dcs.add(Restrictions.eq("lore", q.getLore()));
						dcs.add(Restrictions.eq("type", this.type));
						List l = this.getGeneralService().getList(dcs);
						if(l != null && l.size() > 0) {
							for(Object obj : l) {
								TestStorequestionInfo store = (TestStorequestionInfo)obj;
								il.add(store.getId());
							}
						}
					}
				}
				
				DetachedCriteria dc = DetachedCriteria.forClass(TestStorequestionInfo.class);
				dc.add(Restrictions.like("purpose", "KAOSHI", MatchMode.ANYWHERE));
				dc.add(Restrictions.eq("type", this.type));
				if(idList != null && idList.size() > 0) {
					dc.add(Restrictions.in("lore", idList));
				}
				if(il != null && il.size() > 0) {
					dc.add(Restrictions.not(Restrictions.in("id", il)));
				}
				dc.addOrder(Order.desc("lore"));
				
				List itemList = this.getGeneralService().getList(dc);
				
				int s = itemList.size();
				this.setTotalSize(s);
				if(s == 0) {
					this.setTotalPage(1);
				} else if(s % this.SIZE_PER_PAGE == 0) {
					this.setTotalPage(s/this.SIZE_PER_PAGE);
				} else {
					this.setTotalPage(s/this.SIZE_PER_PAGE + 1);
				}
				
				Page p = this.getGeneralService().getByPage(dc, SIZE_PER_PAGE, (curPage - 1) * SIZE_PER_PAGE);
				
				if(p != null) {
					this.setQuestionItemList(p.getItems());
					List l = this.getLoreName(p.getItems());
					this.setLoreNameList(l);
				}
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 根据题目获取知识点名字
	 * 
	 * @param list
	 * @return
	 */
	private List getLoreName(List list) {
		
		List l = new ArrayList();
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestLoreInfo.class);
		try {
			for(Object o : list) {
				TestStorequestionInfo t = (TestStorequestionInfo)o;
				String s = t.getLore();
				TestLoreInfo lore = (TestLoreInfo)this.getGeneralService().getById(s);
				
				l.add(lore.getName());
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return l;
	}
	
	/**
	 * 初始化试卷的试题
	 * 
	 */
	private void initPaperQuestion() {
		
//		this.setUserRoleCode();
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestPaperquestionInfo.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestPaperquestionInfo.class);
		dct.add(Restrictions.eq("testpaperId", this.paper_id));
		dct.addOrder(Order.asc("serial"));
		if(searchVal != null && !"".equals(searchVal)) {
			dct.add(Restrictions.like("title", searchVal, MatchMode.ANYWHERE));
		}
		try {
			List list = this.getGeneralService().getList(dct);
			
			if(list != null) {
				List questionList = list;
				List paperQuestions = new ArrayList();
				
				List singleList = new ArrayList();
				List multiList = new ArrayList();
				List judgeList = new ArrayList();
				List blankList = new ArrayList();
				List answerList = new ArrayList();
				List comprehensionList = new ArrayList();
				for(int i=0; i<questionList.size(); i++) {
					TestPaperquestionInfo pq = (TestPaperquestionInfo)questionList.get(i);
					String pq_type = pq.getType();
					if(pq_type.equalsIgnoreCase(TestQuestionType.DANXUAN)) {
						singleList.add(pq);
					}
						
					if(pq_type.equalsIgnoreCase(TestQuestionType.DUOXUAN)) {
						multiList.add(pq);
					}
						
					if(pq_type.equalsIgnoreCase(TestQuestionType.PANDUAN)) {
						judgeList.add(pq);
					}
						
					if(pq_type.equalsIgnoreCase(TestQuestionType.TIANKONG)) {
						blankList.add(pq);
					}
						
					if(pq_type.equalsIgnoreCase(TestQuestionType.WENDA)) {
						answerList.add(pq);
					}
						
					if(pq_type.equalsIgnoreCase(TestQuestionType.YUEDU)) {
						comprehensionList.add(pq);
					}
						
				}
				
				String url = "/entity/studyZone/courseResources_viewPaperQuestion.action";
				
				List single = new ArrayList();
				single.add(0, TestQuestionType.DANXUAN);
				single.add(1, TestQuestionType.typeShow(TestQuestionType.DANXUAN));
				single.add(2, singleList);
				single.add(3, url);
				
				List multi = new ArrayList();
				multi.add(0, TestQuestionType.DUOXUAN);
				multi.add(1, TestQuestionType.typeShow(TestQuestionType.DUOXUAN));
				multi.add(2, multiList);
				multi.add(3, url);
				
				List judge = new ArrayList();
				judge.add(0, TestQuestionType.PANDUAN);
				judge.add(1, TestQuestionType.typeShow(TestQuestionType.PANDUAN));
				judge.add(2, judgeList);
				judge.add(3, url);
				
				List answer = new ArrayList();
				answer.add(0, TestQuestionType.WENDA);
				answer.add(1, TestQuestionType.typeShow(TestQuestionType.WENDA));
				answer.add(2, answerList);
				answer.add(3, url);
				
				List blank = new ArrayList();
				blank.add(0, TestQuestionType.TIANKONG);
				blank.add(1, TestQuestionType.typeShow(TestQuestionType.TIANKONG));
				blank.add(2, blankList);
				blank.add(3, url);
				
				List comprehension = new ArrayList();
				comprehension.add(0, TestQuestionType.YUEDU);
				comprehension.add(1, TestQuestionType.typeShow(TestQuestionType.YUEDU));
				comprehension.add(2, comprehensionList);
				comprehension.add(3, "/entity/studyZone/courseResources_viewYueDuPaperQuestion.action");
				
				paperQuestions.add(0,single);
				paperQuestions.add(1,multi);
				paperQuestions.add(2,judge);
				paperQuestions.add(3,answer);
				paperQuestions.add(4,blank);
				paperQuestions.add(5,comprehension);
				
				this.setPaperquestionList(paperQuestions);

			}
		} catch(Exception e) {
			this.setMsg("初始化试卷题目失败！");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取该试卷是否自动判卷
	 * 
	 * @param paperId
	 * @return
	 */
	private String getPaperIsAutoCheck(String paperId) {
		
		String check = "0";
		this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperInfo.class);
		try {
			TestTestpaperInfo paper = (TestTestpaperInfo)this.getGeneralService().getById(paperId);
			if(paper != null) {
				String testId = paper.getGroupId();
				this.getGeneralService().getGeneralDao().setEntityClass(OnlinetestCourseInfo.class);
				OnlinetestCourseInfo test = (OnlinetestCourseInfo)this.getGeneralService().getById(testId);
				if(test != null) {
					check = test.getIsautocheck();
				}
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return check;
		
	}
	
	/**
	 * 检查试卷标题是否已经存在,true:不存在，可以添加；false:存在，不能添加
	 * 
	 * @param paperTitle
	 * @param groupId
	 * @return
	 */
	private boolean checkPaper(String paperTitle, String groupId) {
		
		boolean flag = false;
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperInfo.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestTestpaperInfo.class);
		dct.add(Restrictions.eq("title", paperTitle));
		dct.add(Restrictions.eq("groupId", groupId));
		if(this.paper_id != null && !"".equals(this.paper_id)) {
			dct.add(Restrictions.not(Restrictions.eq("id", this.paper_id)));
		}
		
		try {
			List list = this.getGeneralService().getList(dct);
			if(list != null && list.size() > 0) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return flag;
		
	}
	
	/**
	 * 初始化组卷策略
	 * 
	 */
	private void initPaperpolicy() {
		
		this.setUserRoleCode();
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestPaperpolicyInfo.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestPaperpolicyInfo.class);
		if(searchVal != null && !"".equals(searchVal)) {
			dct.add(Restrictions.like("title", searchVal, MatchMode.ANYWHERE));
		}
		try {
			List list = this.getGeneralService().getList(dct);
			int s = list.size();
			this.setTotalSize(s);
			if(s == 0) {
				this.setTotalPage(1);
			} else if(s % this.SIZE_PER_PAGE == 0) {
				this.setTotalPage(s/this.SIZE_PER_PAGE);
			} else {
				this.setTotalPage(s/this.SIZE_PER_PAGE + 1);
			}
			
			Page p = this.getGeneralService().getByPage(dct, SIZE_PER_PAGE, (curPage - 1) * SIZE_PER_PAGE);
			
			if(p != null) {
				this.setTestpaperPolicyList(p.getItems());
			}
		} catch(EntityException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 将上传的excel模板中的题目导入到数据库
	 * 
	 * @return
	 */
	private int importQuestion(String path) {
		
		int totalSuc = 0;

		Workbook w ;
		try{
			w = Workbook.getWorkbook(new File(path));
		}catch(Exception e){
			this.setMsg("对不起，您的模板有误！");
			return 0;
		}
		
		Sheet sheet = w.getSheet(0);
		String modeltype1 = "";
		
		try{
			modeltype1 = sheet.getCell(0,0).getContents();	
		}catch(Exception e){
			this.setMsg("对不起，您的模板有误！");
			return 0;
		}
		
		if(modeltype1.indexOf("单选题")==-1&&modeltype1.indexOf("多选题")==-1&&modeltype1.indexOf("判断题")==-1&&modeltype1.indexOf("填空题")==-1&&modeltype1.indexOf("问答题")==-1) {
			this.setMsg("对不起，您的模板有误！");
			return 0;
		}
		
		int rowsi = sheet.getRows();
		if(rowsi==2){
			this.setMsg("对不起，您模板中输入的内容为空！");
			return 0;
		}
		
		int cols = sheet.getColumns();  
		if(type.equals(QuestionType.DANXUAN)){
			if(cols == 15 && "单选题".equals(modeltype1)){
			}else{
				this.setMsg("对不起，您的模板有误，请重新下载单选题模板！");
				return 0;
			}
		}
		
		if(type.equals(QuestionType.DUOXUAN)) {
			if(cols ==  15&& "多选题".equals(modeltype1)) {
			}else{
				this.setMsg("对不起，您的模板有误，请重新下载多选题模板！");
				return 0;
			}
		}
		
		if(type.equals(QuestionType.PANDUAN)) {
			if(cols == 10&& "判断题".equals(modeltype1)) {
			}else{
				this.setMsg("对不起，您的模板有误，请重新下载判断题模板！");
				return 0;
			}
		}
		
		if(type.equals(QuestionType.TIANKONG)) {
			if(cols == 10&& "填空题".equals(modeltype1)) {
			}else{
				this.setMsg("对不起，您的模板有误，请重新下载填空题模板！");
				return 0;
			}
		}
		
		if(type.equals(QuestionType.WENDA)) {
			if(cols == 10&& "问答题".equals(modeltype1)) {
			}else{
				this.setMsg("对不起，您的模板有误，请重新下载问答题模板！");
				return 0;
			}
		}
		
		String message = "";
		try{
			for(int i=2; i<sheet.getRows(); i++) {
			 //   难度	建议题目分值	建议作答时间	认知分类	题目用途	题目名称	题目内容	题目提示	题目要求	选项A	选项B	选项C	选项D	选项E	答案

				String diff = sheet.getCell(0, i).getContents().trim();   
				  
				if(diff.equals("")|| diff== null){
					message = message + "第" + (i+1) +"行的难度值为空，导入失败！\\n";
						continue;
				}
				
				if(!diff.equals("")){
					try{
						Double.parseDouble(diff);
					}catch(Exception e){
						message = message + "难度值应该在0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0中的一个，"+ "第" + (i+1) + "行导入失败！\\n";
						continue;
					}
						
					if(Double.parseDouble(diff)>1||Double.parseDouble(diff)<0||diff.length()!=3){
						message = message + "难度值应该在0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0中的一个，"+ "第" + (i+1) + "行导入失败！\\n";
						continue;
					}
				}
				
					
				String referencescore = sheet.getCell(1, i).getContents().trim();
				
				if(referencescore.equals("")|| referencescore== null){
					message = message + "第" + (i+1) +"行的建议题目分值为空，导入失败！\\n";
					continue;
				}
				
				if(!referencescore.equals("")){
					try{
						Integer.parseInt(referencescore);
					}catch(Exception e){
						message = message + "建议题目分值应该为0到100之间整数，"+ "第" + (i+1) + "行导入失败！\\n";
						continue;
					}
					if(Integer.parseInt(referencescore)>100||Integer.parseInt(referencescore)<1){
						message = message + "建议题目分值应该为0到100之间整数，"+ "第" + (i+1) + "行导入失败！\\n";
						continue;
					}
							
				}
				
				String referencetime = sheet.getCell(2, i).getContents().trim();
					
				if(referencetime.equals("")|| referencetime== null){
					message = message + "第" + (i+1) +"行的建议作答时间为空，导入失败！\\n";
					continue;
				}
				
				if(!referencetime.equals("")){
					try{
						Integer.parseInt(referencetime);
					}catch(Exception e){
						message = message + "建议作答时间应该为大于0的整数，"+ "第" + (i+1) + "行导入失败！\\n";
						continue;
					}
					if(Integer.parseInt(referencetime)<1){
						message = message + "建议作答时间应该为大于0的整数，"+ "第" + (i+1) + "行导入失败！\\n";
						continue;
					}
				}
				
				
			 	String cognizetype = sheet.getCell(3, i).getContents().trim();
			 	
			 	
				if(cognizetype.equals("")|| cognizetype== null){
					message = message + "第" + (i+1) +"行的认知分类为空，导入失败！\\n";
					continue;
				}
				
				if(cognizetype.length()!=2)	{
					message = message + "第" + (i+1) +"行的认知分类应为 了解|理解|应用|分析|综合|评鉴的一个，且只能是一个，行导入失败！\\n";
					continue;
				}
				
				if(cognizetype!=null&&cognizetype.trim().length()==2){
					int countcon =0;
					if(cognizetype.indexOf("了解")==-1&&cognizetype.indexOf("理解")==-1&&cognizetype.indexOf("应用")==-1&&cognizetype.indexOf("分析")==-1&&cognizetype.indexOf("综合")==-1&&cognizetype.indexOf("评鉴")==-1){
						message = message + "第" + (i+1) +"行的认知分类应为 了解|理解|应用|分析|综合|评鉴的一个，且只能是一个，导入失败！\\n";
						continue;
					}
					if(cognizetype.indexOf("了解")!=-1){
						countcon ++;
					}
					if(cognizetype.indexOf("理解")!=-1){
						countcon ++;
					}
					if(cognizetype.indexOf("应用")!=-1){
						countcon ++;
					}
					if(cognizetype.indexOf("分析")!=-1){
						countcon ++;
					}
					if(cognizetype.indexOf("综合")!=-1){
						countcon ++;
					}
					if(cognizetype.indexOf("评鉴")!=-1){
						countcon ++;
					}
					if(countcon>1){
						message = message + "第" + (i+1) +"行的认知分类应为 了解|理解|应用|分析|综合|评鉴的一个，且只能是一个，导入失败！\\n";
						continue;
					}
				   if(cognizetype.equalsIgnoreCase("了解")){
				 	    cognizetype= cognizetype.replace("了解","LIAOJIE");
				   }else if(cognizetype.equalsIgnoreCase("理解")){
				   		cognizetype= cognizetype.replace("理解","LIJIE");
				   }else if(cognizetype.equalsIgnoreCase("应用")){
				   		cognizetype= cognizetype.replace("应用","YINGYONG");
				   }else if(cognizetype.equalsIgnoreCase("分析")){
				   		cognizetype= cognizetype.replace("分析","FENXI");
				   }else if(cognizetype.equalsIgnoreCase("综合")){
				   		cognizetype= cognizetype.replace("综合","ZONGHE");
				   }else if(cognizetype.equalsIgnoreCase("评鉴")){
				   		cognizetype= cognizetype.replace("评鉴","PINGJIAN");
				   }
				}
				 		
			    String purpose = sheet.getCell(4, i).getContents().trim();
			    if(purpose.equals("")|| purpose== null){
			    	message = message + "第" + (i+1) +"行的题目用途为空，导入失败！\\n";
					continue;
				}
			    
			    if(purpose!=null&&purpose.trim().length()>0){
			    	if(purpose.indexOf("在线自测")==-1&&purpose.indexOf("在线作业")==-1&&purpose.indexOf("实验")==-1&&purpose.indexOf("在线考试")==-1){
			    		message = message + "第" + (i+1) +"行的题目用途应该是 在线自测|在线作业|实验|在线考试|中的一个或多个，多个用“|”隔开，导入失败！\\n";
						continue;
			    	}
			    	if(purpose.length()>4&&purpose.indexOf("|")==-1){
			    		message = message + "第" + (i+1) +"行的题目用途应该是 在线自测|在线作业|实验|在线考试|中的一个或多个，多个用“|”隔开，导入失败！\\n";
						continue;
			    	}	
				    if(purpose.indexOf("在线自测")!=-1){
				   		purpose = purpose.replace("在线自测","KAOSHI");
				    }
				    if(purpose.indexOf("在线作业")!=-1){
				   		purpose = purpose.replace("在线作业","ZUOYE");
				    }
				    if(purpose.indexOf("实验")!=-1){
				   		purpose = purpose.replace("实验","EXPERIMENT");
				    }
				    if(purpose.indexOf("在线考试")!=-1){
				   		purpose = purpose.replace("在线考试","EXAM");
				    } 
				}
			    
				String title = sheet.getCell(5, i).getContents().trim();
				if(title.equals("")|| title== null){
					message = message + "第" + (i+1) +"行的题目名称为空，导入失败！\\n";
					continue;
				}
				
				//保证同一门课程下试题题目名称不能重复
				this.setTitle(title);
				if(!this.checkQuestion())
				{
					message = message + "第" + (i+1) +"行的题目内容有重复，同一课程下题目名称不能重复，导入失败！\\n";
					continue;
				}
				
				String questioncore = sheet.getCell(6, i).getContents().trim();
				if(questioncore.equals("")|| questioncore== null){
					message = message + "第" + (i+1) +"行的题目内容为空，导入失败！\\n";
					continue;
				}
				
				String studentNote =  sheet.getCell(7, i).getContents().trim();
				String teacherNote = sheet.getCell(8, i).getContents().trim();
				
				String modeltype = sheet.getCell(0,0).getContents();
				String xml = "";
				if(type.equals(QuestionType.DANXUAN)) {
					if(cols == 15 && "单选题".equals(modeltype)) {
						xml = "<question><body>" + questioncore + "</body><select>";
						List optionList = new ArrayList();
						String option_strA = sheet.getCell(9, i).getContents().trim();
						String option_strB = sheet.getCell(10, i).getContents().trim();
						if((option_strA==null||"".equals(option_strA))||(option_strB==null||"".equals(option_strB))){
							message = message + "第" + (i+1) +"行的选项A和选项B不能为空，导入失败！\\n";
							continue;
						}
						for(int j=9; j<=13; j++) {
							if(!sheet.getCell(j, i).getContents().trim().equals("")) {
								optionList.add(sheet.getCell(j, i).getContents().trim());
							} else {
								break;
							}
						}
						for(int k=0; k<optionList.size(); k++) {
							int charCode = k + 65;		//选项字母的ASCII码
							String index = String.valueOf((char)charCode);	//将ASCII码转换成字母
							xml = xml + "<item><index>" + index + "</index><content>" + (String)optionList.get(k) + "</content></item>";
						}
						
						String answer = sheet.getCell(14, i).getContents().trim();
						
						if(answer.equals("")|| answer== null){
							message = message + "第" + (i+1) +"行的答案内容为空,导入失败！\\n";
							continue;
						}
						if(answer.length()!=1){
							message = message + "第" + (i+1) +"行的答案应为ABCDE中的一个,导入失败！\\n";
							continue;
						}
						if(answer.length()!=1||(answer.indexOf("A")==-1&&answer.indexOf("B")==-1&&answer.indexOf("C")==-1&&answer.indexOf("D")==-1&&answer.indexOf("E")==-1)){
							message = message + "第" + (i+1) +"行的答案应为ABCDE中的一个,导入失败！\\n";
							continue;
						}
						
						int countans = 0;
						if(answer.indexOf("A")!=-1){
							countans++;
						}
						if(answer.indexOf("B")!=-1){
							countans++;
						}
						if(answer.indexOf("C")!=-1){
							countans++;
						}
						if(answer.indexOf("D")!=-1){
							countans++;
						}
						if(answer.indexOf("E")!=-1){
							countans++;
						}
						if(countans>1){
							message = message + "第" + (i+1) +"行的答案应为ABCDE中的一个，导入失败！\\n";
							continue;
						}
						
						xml = xml + "</select><answer>" + answer + "</answer></question>";
						
					} else {
						message = message + "请选择正确的题目模板！\\n";
					}
				}
				
				if(type.equals(QuestionType.DUOXUAN)) {
					if(cols ==  15&& "多选题".equals(modeltype)) {
						xml = "<question><body>" +  questioncore + "</body><select>";
						List optionList = new ArrayList();
						
						String option_strA = sheet.getCell(9, i).getContents().trim();
						String option_strB = sheet.getCell(10, i).getContents().trim();
						if((option_strA==null||"".equals(option_strA))||(option_strB==null||"".equals(option_strB))){
							message = message + "第" + (i+1) +"行的选项A和选项B不能为空，导入失败！\\n";
							continue;
						}
						
						for(int j=9; j<=13; j++) {
							if(!sheet.getCell(j, i).getContents().trim().equals("")) {
								optionList.add(sheet.getCell(j, i).getContents().trim());
							} else {
								break;
							}
						}
						for(int k=0; k<optionList.size(); k++) {
							int charCode = k + 65;		//选项字母的ASCII码
							String index = String.valueOf((char)charCode);	//将ASCII码转换成字母
							xml = xml + "<item><index>" + index + "</index><content>" + (String)optionList.get(k) + "</content></item>";
						}
						String answer = sheet.getCell(14, i).getContents().trim();
						
						if(answer.equals("")|| answer== null){
							message = message + "第" + (i+1) +"行的答案内容为空，导入失败！\\n";
							continue;
						}
						
						if(answer.indexOf("A")==-1&&answer.indexOf("B")==-1&&answer.indexOf("C")==-1&&answer.indexOf("D")==-1&&answer.indexOf("E")==-1){
							message = message + "第" + (i+1) +"行的答案应为ABCDE中的一个或多个,且用‘|’隔开,导入失败！\\n";
							continue;
						}
						if(answer.length()>1&&answer.indexOf("|")==-1){
							message = message + "第" + (i+1) +"行的答案应为ABCDE中的一个或多个,且用‘|’隔开，导入失败！\\n";
							continue;
						}
						
						xml = xml + "</select><answer>" +  answer  + "</answer></question>";
						
					} else {
						message = message + "请选择正确的题目模板！\\n";
					}
				}
			
				if(type.equals(QuestionType.PANDUAN)) {
					if(cols == 10&& "判断题".equals(modeltype)) {
						String answer = sheet.getCell(9, i).getContents().trim();
						
						if(answer.equals("")|| answer== null){
							message = message + "第" + (i+1) +"行的答案内容为空，导入失败！\\n";
							continue;
						}
						if(answer.indexOf("正确")==-1&&answer.indexOf("错误")==-1){
							message = message + "第" + (i+1) +"行的答案内容只能为“正确”或“错误”，导入失败！\\n";
							continue;
						}
						int countanser=0;
						if(answer.indexOf("正确")!=-1){
							countanser++;
						}
						if(answer.indexOf("错误")!=-1){
							countanser++;
						}
						if(countanser>1){
							message = message + "第" + (i+1) +"行的答案内容只能为“正确”或“错误”,只能是一个，导入失败！\\n";
							continue;
						}
						if(answer.trim().equals("正确")){
							answer="1";
						}
						if(answer.trim().equals("错误")){
							answer="0";
						}
						
						xml = "<question><body>" +  questioncore  + "</body><answer>" +  answer  + "</answer></question>";
						
					} else {
						message = message + "请选择正确的题目模板！\\n";
					}
				}
				
				if(type.equals(QuestionType.TIANKONG)) {
					if(cols == 10&& "填空题".equals(modeltype)) {
						String answer = sheet.getCell(9, i).getContents().trim();
						
						if(answer.equals("")|| answer== null){
							message = message + "第" + (i+1) +"行的答案内容为空，导入失败！\\n";
							continue;
						}
						
						xml = "<question><body>" +  questioncore  + "</body><answer>" +  answer  + "</answer></question>";
						
					} else {
						message = message + "请选择正确的题目模板！\\n";
					}
				}
				
				if(type.equals(QuestionType.WENDA)) {
					if(cols == 10&& "问答题".equals(modeltype)) {
						String answer = sheet.getCell(9, i).getContents().trim();
						
						if(answer.equals("")|| answer== null){
							message = message + "第" + (i+1) +"行的答案内容为空，导入失败！\\n";
							continue;
						}
						
						xml = "<question><body>" +  questioncore  + "</body><answer>" +  answer  + "</answer></question>";

					} else {
						message = message + "请选择正确的题目模板！\\n";
					}
				}
				
				TestStorequestionInfo info = new TestStorequestionInfo();
				info.setCognizetype(cognizetype);
				info.setCreatdate(new Date());
				info.setDiff(diff);
				info.setLore(lore_id);
				info.setKeyword("keyword");
				info.setReferencescore(referencescore);
				info.setReferencetime(referencetime);
				info.setTitle(title);
				info.setPurpose(purpose);
				info.setType(type);
				info.setStudentnote(studentNote);
				info.setTeachernote(teacherNote);
				info.setQuestioncore(xml);
				
				UserSession userSession = (UserSession) ActionContext.getContext()
					.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
				SsoUser ssoUser = userSession.getSsoUser();
				info.setCreatuser(ssoUser.getLoginId());
				
				this.getGeneralService().getGeneralDao().setEntityClass(TestStorequestionInfo.class);
				try {
					this.getGeneralService().save(info);
					totalSuc++;
				} catch (EntityException e) {
					message = message + "第" + (i+1) + "行导入失败！\\n";
					e.printStackTrace();
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		this.setMsg(message);
		return totalSuc;
		
	}
	
	//检查上传文件格式
	private boolean checkFile(String fileName) {
		List fileList=Arrays.asList("xls");;
		int inde=fileName.lastIndexOf(".");
		if(inde>=0){
			String filtyp=fileName.substring(inde+1, fileName.length());
			if(!fileList.contains(filtyp)){
				return false;
			}
		}
		return true;
	}
	
	//对上传的excel文件进行重命名
	private String reName(String path){
		
		String repath = "";
		
		if(new File(ServletActionContext.getServletContext().getRealPath(path)).isFile()){
			int point = (path.lastIndexOf(".")>0 ? path.lastIndexOf("."):path.length());
			repath = path.substring(0, point)+"["+System.currentTimeMillis()+"]"+path.substring(point);
		}else{
			repath = path;
		}
		
		return repath;
	}
	
	/**
	 * 检查同一门课程下试题题目名称是否相同(false:有相同题目,true:没有相同题目，可以添加;)
	 * 
	 * @return
	 */
	private boolean checkQuestion() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestLoreInfo.class);
		DetachedCriteria dc = DetachedCriteria.forClass(TestLoreInfo.class);
		dc.createCriteria("testLoreDir", "testLoreDir");
		dc.add(Restrictions.eq("id", this.lore_id));
		try {
			List list = this.getGeneralService().getList(dc);
			if(list != null && list.size() > 0) {
				TestLoreInfo info = (TestLoreInfo)list.get(0);
				TestLoreDir dir = info.getTestLoreDir();
				DetachedCriteria dct = DetachedCriteria.forClass(TestLoreInfo.class);
				dct.add(Restrictions.eq("testLoreDir", dir));
				List infoList = this.getGeneralService().getList(dct);
				if(infoList != null && infoList.size() > 0) {
					List idList = new ArrayList();
					for(Object o : infoList) {
						TestLoreInfo t = (TestLoreInfo)o;
						idList.add(t.getId());
					}
					
					this.getGeneralService().getGeneralDao().setEntityClass(TestStorequestionInfo.class);
					DetachedCriteria dcq = DetachedCriteria.forClass(TestStorequestionInfo.class);
					dcq.add(Restrictions.in("lore", idList));
					dcq.add(Restrictions.eq("title", this.title));
					if(this.question_id != null && !"".equals(this.question_id)) {
						dcq.add(Restrictions.not(Restrictions.eq("id", this.question_id)));
					}
					
					List l = this.getGeneralService().getList(dcq);
					if(l != null && l.size() > 0) {
						return false;
					} else {
						return true;
					}
				}
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	/**
	 * 初始化试卷列表
	 * 
	 */
	private void initTestpaper() {
		
		this.setUserRoleCode();
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperInfo.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestTestpaperInfo.class);
		dct.add(Restrictions.eq("groupId", this.test_id));
		try {
			List list = this.getGeneralService().getList(dct);
			int s = list.size();
			this.setTotalSize(s);
			if(s == 0) {
				this.setTotalPage(1);
			} else if(s % this.SIZE_PER_PAGE == 0) {
				this.setTotalPage(s/this.SIZE_PER_PAGE);
			} else {
				this.setTotalPage(s/this.SIZE_PER_PAGE + 1);
			}
			
			Page p = this.getGeneralService().getByPage(dct, SIZE_PER_PAGE, (curPage - 1) * SIZE_PER_PAGE);
			
			if(p != null) {
				this.setTestpaperList(p.getItems());
				if(this.roleCode != null && "0".equals(this.roleCode)) {
					List historyList = new ArrayList();
					for(Object o : testpaperList) {
						TestTestpaperInfo paper = (TestTestpaperInfo)o;
						String[] history = this.getLastTestHistory(paper.getId());
						historyList.add(history);
					}
					this.setTestpaperHistoryList(historyList);
				}
				
			}
			
		} catch (Exception e) {
			this.setMsg("初始化试卷失败！");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取当前学员试卷的最近一次的考试历史信息
	 * 
	 * ==================================
	 * 成绩修改为获取历次最高成绩
	 * 修改人：zqf   2010-06-29
	 * ==================================
	 * @return
	 */
	private String[] getLastTestHistory(String paperId) {
		
		String[] infoList = new String[4];
		UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestTestpaperHistory.class);
		DetachedCriteria dch = DetachedCriteria.forClass(TestTestpaperHistory.class);
		dch.add(Restrictions.eq("userId", ssoUser.getId()));
		dch.add(Restrictions.eq("testpaperId", paperId));
		dch.addOrder(Order.desc("testDate"));
		
		//获取最高成绩
		String sql = " select max(nvl(decode(t.score,'null',0,t.score),0)) from test_testpaper_history t where t.user_id = '"+ssoUser.getId()+"' and t.testpaper_id = '"+paperId+"' ";
		String max_score = "";
		try {
			max_score = this.getGeneralService().getBySQL(sql).get(0).toString();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			List list = this.getGeneralService().getList(dch);
			if(list != null && list.size() > 0) {
				TestTestpaperHistory history = (TestTestpaperHistory)list.get(0);
				infoList[0] = "1";//已经考试
				String s = history.getScore();
				try {
					double d = Double.parseDouble(s);
					if(d < 0) {
						infoList[1] = "2";//分数未判
					}else if(d < 60) {
						infoList[1] = "0";//不及格
					} else {
						infoList[1] = "1";//及格
					}
					infoList[3] = max_score;//String.valueOf(d);
				} catch (NumberFormatException e) {
					infoList[1] = "2";//分数未判
					infoList[3] = "0";
				}
				infoList[2] = history.getId();
			} else {
				infoList[0] = "0";
				infoList[1] = "2";
				infoList[2] = "0";
				infoList[3] = "0";
			}
		} catch (EntityException e) {
			this.setMsg("历史考试数据初始化失败！");
			e.printStackTrace();
		}
		
		return infoList;
		
	}
	
	/**
	 * 初始化知识点题目
	 * 
	 */
	private void initLoreQuestion() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestStorequestionInfo.class);
		DetachedCriteria dc = DetachedCriteria.forClass(TestStorequestionInfo.class);
		dc.add(Restrictions.eq("lore", this.lore_id));
		dc.add(Restrictions.isNull("testStorequestionInfo"));
		if(searchVal != null && !"".equals(searchVal)) {
			dc.add(Restrictions.like("title", searchVal, MatchMode.ANYWHERE));
		}
		dc.addOrder(Order.asc("type"));
		try {
			List list = this.getGeneralService().getList(dc);
			int s = list.size();
			this.setTotalSize(s);
			if(s == 0) {
				this.setTotalPage(1);
			} else if(s % this.SIZE_PER_PAGE == 0) {
				this.setTotalPage(s/this.SIZE_PER_PAGE);
			} else {
				this.setTotalPage(s/this.SIZE_PER_PAGE + 1);
			}
			
			Page p = this.getGeneralService().getByPage(dc, SIZE_PER_PAGE, (curPage - 1) * SIZE_PER_PAGE);
			
			if(p != null) {
				this.setQuestionInfoList(p.getItems());
			}
			
			if(questionInfoList != null && questionInfoList.size() > 0) {
				List strList = new ArrayList();
				List urlList = new ArrayList();
				
				for (int i = 0; i < questionInfoList.size(); i++) {
					TestStorequestionInfo info = (TestStorequestionInfo)questionInfoList.get(i);
					String type = info.getType();
					String url = "/entity/studyZone/courseResources_viewQuestion.action";
					if(type.equals(TestQuestionType.YUEDU)) {
						url = "/entity/studyZone/courseResources_viewYueDuQuestion.action";
					}
					
					urlList.add(i, url);
					strList.add(i, TestQuestionType.typeShow(type));
					
				}
				
				this.setUrlList(urlList);
				this.setTypeStrList(strList);
				
			}
			
		} catch (Exception e) {
			this.setMsg("初始化该知识点题目失败！");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 初始化题库管理
	 * 
	 */
	private void initLore() {
		
		this.setUserRoleCode();
		
		this.getGeneralService().getGeneralDao().setEntityClass(TestLoreDir.class);
		DetachedCriteria dct = DetachedCriteria.forClass(TestLoreDir.class);
		dct.add(Restrictions.eq("groupId", this.course_id));
		dct.add(Restrictions.eq("fatherdir", "0"));
		
		try {
			List list = this.getGeneralService().getList(dct);
			TestLoreDir tld = null;
			if(list != null && list.size() > 0) {
				tld = (TestLoreDir)list.get(0);
				if(tld != null) {
					this.setRootTestLoreDir(tld);
				}
			} else {
				tld = new TestLoreDir();
				tld.setCreatdate(new Date());
				tld.setFatherdir("0");
				tld.setGroupId(this.course_id);
				tld.setName("根目录");
				tld.setNote("知识点根目录");
				this.getGeneralService().save(tld);
				
				list = this.getGeneralService().getList(dct);
				if(list != null && list.size() > 0) {
					tld = (TestLoreDir)list.get(0);
					if(tld != null) {
						this.setRootTestLoreDir(tld);
					}
				} else {
					this.setMsg("初始化题库管理失败！");
				}
			}
			
			if(this.getLoreDir_id() != null && !"".equals(this.getLoreDir_id())) {
				TestLoreDir t = (TestLoreDir)this.getGeneralService().getById(this.getLoreDir_id());
				if(t != null) {
					this.setCurTestLoreDir(t);
				}
			} else {
				this.setCurTestLoreDir(this.getRootTestLoreDir());
			}
			
			DetachedCriteria subdct = DetachedCriteria.forClass(TestLoreDir.class);
			subdct.add(Restrictions.eq("groupId", this.course_id));
			subdct.add(Restrictions.eq("fatherdir", this.getCurTestLoreDir().getId()));
			List subList = this.getGeneralService().getList(subdct);
			if(subList != null && subList.size() > 0) {
				this.setSubTestLoreDirList(subList);
			}
			
			this.getGeneralService().getGeneralDao().setEntityClass(TestLoreInfo.class);
			DetachedCriteria dcInfo = DetachedCriteria.forClass(TestLoreInfo.class);
			dcInfo.add(Restrictions.eq("testLoreDir", this.getCurTestLoreDir()));
			List infoList = this.getGeneralService().getList(dcInfo);
			if(infoList != null && infoList.size() > 0) {
				this.setTestLoreInfoList(infoList);
			}
			
		} catch (EntityException e) {
			this.setMsg("初始化题库管理失败！");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 初始化常见问题库
	 * 
	 */
	private void initQuestion() {
		
		this.setUserRoleCode();
		
		this.getGeneralService().getGeneralDao().setEntityClass(PeQuestions.class);
		DetachedCriteria dc = DetachedCriteria.forClass(PeQuestions.class);
		dc.createCriteria("peTchCourse", "peTchCourse", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peTrainee", "peTrainee", DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("peTchCourse.id", this.course_id));
		if(searchTitle != null && !"".equals(searchTitle)) {
			dc.add(Restrictions.like("title", searchTitle, MatchMode.ANYWHERE));
		}
		if(searchCreater != null && !"".equals(searchCreater)) {
			dc.add(Restrictions.like("peTrainee.name", searchCreater, MatchMode.ANYWHERE));
		}
		try {
			List list = this.getGeneralService().getList(dc);
			int s = list.size();
			this.setTotalSize(s);
			if(s == 0) {
				this.setTotalPage(1);
			} else if(s % this.SIZE_PER_PAGE == 0) {
				this.setTotalPage(s/this.SIZE_PER_PAGE);
			} else {
				this.setTotalPage(s/this.SIZE_PER_PAGE + 1);
			}
			
			Page p = this.getGeneralService().getByPage(dc, SIZE_PER_PAGE, (curPage - 1) * SIZE_PER_PAGE);
			
			if(p != null) {
				this.setQuestionList(p.getItems());
			}
			
		} catch (EntityException e) {
			this.setMsg("初始化常见问题库失败！");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取在线自测信息列表
	 * 
	 */
	private void getOnlineTestInfo() {
		
		this.setUserRoleCode();
		
		this.getGeneralService().getGeneralDao().setEntityClass(OnlinetestCourseInfo.class);
		DetachedCriteria dco = DetachedCriteria.forClass(OnlinetestCourseInfo.class);
		dco.add(Restrictions.eq("groupId", this.course_id));
		dco.add(Restrictions.eq("batchId", this.onlineType));
		if(searchVal != null && !"".equals(searchVal)) {
			dco.add(Restrictions.like("title", searchVal, MatchMode.ANYWHERE));
		}
		if("0".equals(this.roleCode)) { //普通学员
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String time = sdf.format(new Date());
			dco.add(Restrictions.eq("status", "1"));
			dco.add(Restrictions.le("startDate", time));
			dco.add(Restrictions.ge("endDate", time));
		}
		try {
			List list = this.getGeneralService().getList(dco);
			int s = list.size();
			this.setTotalSize(s);
			if(s == 0) {
				this.setTotalPage(1);
			} else if(s % this.SIZE_PER_PAGE == 0) {
				this.setTotalPage(s/this.SIZE_PER_PAGE);
			} else {
				this.setTotalPage(s/this.SIZE_PER_PAGE + 1);
			}
			
			Page p = this.getGeneralService().getByPage(dco, SIZE_PER_PAGE, (curPage - 1) * SIZE_PER_PAGE);
			
			if(p != null) {
				this.setTestInfoList(p.getItems());
				this.getCreateUserName(p.getItems());
			}
			
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 根据当前用户session设置角色的roleCode
	 * 
	 */
	private void setUserRoleCode() {
		
		UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		
		this.getGeneralService().getGeneralDao().setEntityClass(PePriRole.class);
		DetachedCriteria dcr = DetachedCriteria.forClass(PePriRole.class);
		dcr.createCriteria("enumConstByFlagRoleType", "enumConstByFlagRoleType");
		dcr.add(Restrictions.eq("id", ssoUser.getPePriRole().getId()));
		try {
			List l = this.getGeneralService().getList(dcr);
			if(l != null && l.size() > 0) {
				PePriRole ppr = (PePriRole)l.get(0);
				this.setRoleCode(ppr.getEnumConstByFlagRoleType().getCode());
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		
	}
	
	/**
	 * 根据在线测试列表查找出相关的创建者姓名
	 * 
	 * @param list
	 */
	private void getCreateUserName(List list) {
		this.getGeneralService().getGeneralDao().setEntityClass(SsoUser.class);
		List nameList = new ArrayList();
		try {
			for(Object o : list) {
				OnlinetestCourseInfo oci = (OnlinetestCourseInfo)o;
				String userId = oci.getCreatuser();
				SsoUser user = (SsoUser) this.getGeneralService().getById(userId);
				if(user != null) {
					nameList.add(user.getLoginId());
				}
				
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		this.setCreateUserList(nameList);
	}
	
	/**
	 * 获取当前session中的学员
	 * 
	 * @return
	 */
	private PeTrainee getCurTrainee() {
		
		UserSession userSession = (UserSession) ActionContext.getContext()
		.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		this.getGeneralService().getGeneralDao().setEntityClass(PeTrainee.class);
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
		dc.add(Restrictions.eq("ssoUser", ssoUser));
		List peTraineeList = new ArrayList();
		try {
			peTraineeList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(peTraineeList != null && peTraineeList.size() > 0) {
			return (PeTrainee) peTraineeList.get(0);
		}
		 return null;
		 
	}
	
	public PeTchCourse getPeTchCourse() {
		return peTchCourse;
	}

	public void setPeTchCourse(PeTchCourse peTchCourse) {
		this.peTchCourse = peTchCourse;
	}

	public List<InteractionTeachclassInfo> getResList() {
		return resList;
	}

	public void setResList(List<InteractionTeachclassInfo> resList) {
		this.resList = resList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getR_id() {
		return r_id;
	}

	public void setR_id(String r_id) {
		this.r_id = r_id;
	}

	public InteractionTeachclassInfo getInterTchInfo() {
		return interTchInfo;
	}

	public void setInterTchInfo(InteractionTeachclassInfo interTchInfo) {
		this.interTchInfo = interTchInfo;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSearchVal() {
		return searchVal;
	}

	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}

	public String getSeconds() {
		return seconds;
	}

	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}

	public String getLearnedSeconds() {
		return learnedSeconds;
	}

	public void setLearnedSeconds(String learnedSeconds) {
		this.learnedSeconds = learnedSeconds;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public List getTestInfoList() {
		return testInfoList;
	}

	public void setTestInfoList(List testInfoList) {
		this.testInfoList = testInfoList;
	}

	public String getTest_title() {
		return test_title;
	}

	public void setTest_title(String test_title) {
		this.test_title = test_title;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getStart_hour() {
		return start_hour;
	}

	public void setStart_hour(String start_hour) {
		this.start_hour = start_hour;
	}

	public String getStart_minute() {
		return start_minute;
	}

	public void setStart_minute(String start_minute) {
		this.start_minute = start_minute;
	}

	public String getStart_second() {
		return start_second;
	}

	public void setStart_second(String start_second) {
		this.start_second = start_second;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getEnd_hour() {
		return end_hour;
	}

	public void setEnd_hour(String end_hour) {
		this.end_hour = end_hour;
	}

	public String getEnd_minute() {
		return end_minute;
	}

	public void setEnd_minute(String end_minute) {
		this.end_minute = end_minute;
	}

	public String getEnd_second() {
		return end_second;
	}

	public void setEnd_second(String end_second) {
		this.end_second = end_second;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsAutoCheck() {
		return isAutoCheck;
	}

	public void setIsAutoCheck(String isAutoCheck) {
		this.isAutoCheck = isAutoCheck;
	}

	public String getIsHiddenAnswer() {
		return isHiddenAnswer;
	}

	public void setIsHiddenAnswer(String isHiddenAnswer) {
		this.isHiddenAnswer = isHiddenAnswer;
	}

	public String getTest_id() {
		return test_id;
	}

	public void setTest_id(String test_id) {
		this.test_id = test_id;
	}

	public OnlinetestCourseInfo getOnlineTest() {
		return onlineTest;
	}

	public void setOnlineTest(OnlinetestCourseInfo onlineTest) {
		this.onlineTest = onlineTest;
	}

	public List getCreateUserList() {
		return createUserList;
	}

	public void setCreateUserList(List createUserList) {
		this.createUserList = createUserList;
	}

	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

	public String getSearchCreater() {
		return searchCreater;
	}

	public void setSearchCreater(String searchCreater) {
		this.searchCreater = searchCreater;
	}

	public List getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List questionList) {
		this.questionList = questionList;
	}

	public TestLoreDir getRootTestLoreDir() {
		return rootTestLoreDir;
	}

	public void setRootTestLoreDir(TestLoreDir rootTestLoreDir) {
		this.rootTestLoreDir = rootTestLoreDir;
	}

	public List getTestLoreInfoList() {
		return testLoreInfoList;
	}

	public void setTestLoreInfoList(List testLoreInfoList) {
		this.testLoreInfoList = testLoreInfoList;
	}

	public TestLoreDir getCurTestLoreDir() {
		return curTestLoreDir;
	}

	public void setCurTestLoreDir(TestLoreDir curTestLoreDir) {
		this.curTestLoreDir = curTestLoreDir;
	}

	public String getLoreDir_id() {
		return loreDir_id;
	}

	public void setLoreDir_id(String loreDir_id) {
		this.loreDir_id = loreDir_id;
	}

	public List getSubTestLoreDirList() {
		return subTestLoreDirList;
	}

	public void setSubTestLoreDirList(List subTestLoreDirList) {
		this.subTestLoreDirList = subTestLoreDirList;
	}

	public String getFatherDir_id() {
		return fatherDir_id;
	}

	public void setFatherDir_id(String fatherDir_id) {
		this.fatherDir_id = fatherDir_id;
	}

	public String getLore_active() {
		return lore_active;
	}

	public void setLore_active(String lore_active) {
		this.lore_active = lore_active;
	}

	public String getLore_name() {
		return lore_name;
	}

	public void setLore_name(String lore_name) {
		this.lore_name = lore_name;
	}

	public String getLore_content() {
		return lore_content;
	}

	public void setLore_content(String lore_content) {
		this.lore_content = lore_content;
	}

	public String getLore_dir_name() {
		return lore_dir_name;
	}

	public void setLore_dir_name(String lore_dir_name) {
		this.lore_dir_name = lore_dir_name;
	}

	public String getLore_dir_note() {
		return lore_dir_note;
	}

	public void setLore_dir_note(String lore_dir_note) {
		this.lore_dir_note = lore_dir_note;
	}

	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	public String getLore_id() {
		return lore_id;
	}

	public void setLore_id(String lore_id) {
		this.lore_id = lore_id;
	}

	public TestLoreInfo getTestLoreInfo() {
		return testLoreInfo;
	}

	public void setTestLoreInfo(TestLoreInfo testLoreInfo) {
		this.testLoreInfo = testLoreInfo;
	}

	public List getQuestionInfoList() {
		return questionInfoList;
	}

	public void setQuestionInfoList(List questionInfoList) {
		this.questionInfoList = questionInfoList;
	}

	public List getUrlList() {
		return urlList;
	}

	public void setUrlList(List urlList) {
		this.urlList = urlList;
	}

	public List getTypeStrList() {
		return typeStrList;
	}

	public void setTypeStrList(List typeStrList) {
		this.typeStrList = typeStrList;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getDiff() {
		return diff;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}

	public String getCognizetype() {
		return cognizetype;
	}

	public void setCognizetype(String cognizetype) {
		this.cognizetype = cognizetype;
	}

	public String getReferencescore() {
		return referencescore;
	}

	public void setReferencescore(String referencescore) {
		this.referencescore = referencescore;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}

	public String getReferencetime() {
		return referencetime;
	}

	public void setReferencetime(String referencetime) {
		this.referencetime = referencetime;
	}

	public String getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}

	public String[] getIdaaa() {
		return idaaa;
	}

	public void setIdaaa(String[] idaaa) {
		this.idaaa = idaaa;
	}

	public TestStorequestionInfo getQuestionInfo() {
		return questionInfo;
	}

	public void setQuestionInfo(TestStorequestionInfo questionInfo) {
		this.questionInfo = questionInfo;
	}

	public int getCharCode() {
		return charCode;
	}

	public void setCharCode(int charCode) {
		this.charCode = charCode;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public List getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List answerList) {
		this.answerList = answerList;
	}

	public String[] getAnswers() {
		return answers;
	}

	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	public File getSrc() {
		return src;
	}

	public void setSrc(File src) {
		this.src = src;
	}

	public String getSrcFileName() {
		return srcFileName;
	}

	public void setSrcFileName(String srcFileName) {
		this.srcFileName = srcFileName;
	}

	public String getSrcContentType() {
		return srcContentType;
	}

	public void setSrcContentType(String srcContentType) {
		this.srcContentType = srcContentType;
	}

	public List getTestpaperList() {
		return testpaperList;
	}

	public void setTestpaperList(List testpaperList) {
		this.testpaperList = testpaperList;
	}

	public String getPaper_id() {
		return paper_id;
	}

	public void setPaper_id(String paper_id) {
		this.paper_id = paper_id;
	}

	public TestTestpaperInfo getPaperInfo() {
		return paperInfo;
	}

	public void setPaperInfo(TestTestpaperInfo paperInfo) {
		this.paperInfo = paperInfo;
	}

	public TestTestpaperHistory getTestpaperHistory() {
		return testpaperHistory;
	}

	public void setTestpaperHistory(TestTestpaperHistory testpaperHistory) {
		this.testpaperHistory = testpaperHistory;
	}

	public List getTestpaperHistoryList() {
		return testpaperHistoryList;
	}

	public void setTestpaperHistoryList(List testpaperHistoryList) {
		this.testpaperHistoryList = testpaperHistoryList;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List getTestpaperPolicyList() {
		return testpaperPolicyList;
	}

	public void setTestpaperPolicyList(List testpaperPolicyList) {
		this.testpaperPolicyList = testpaperPolicyList;
	}

	public List getPaperquestionList() {
		return paperquestionList;
	}

	public void setPaperquestionList(List paperquestionList) {
		this.paperquestionList = paperquestionList;
	}

	public List getQuestionItemList() {
		return questionItemList;
	}

	public void setQuestionItemList(List questionItemList) {
		this.questionItemList = questionItemList;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String[] getDANXUANChild() {
		return DANXUANChild;
	}

	public void setDANXUANChild(String[] child) {
		DANXUANChild = child;
	}

	public String[] getDUOXUANChild() {
		return DUOXUANChild;
	}

	public void setDUOXUANChild(String[] child) {
		DUOXUANChild = child;
	}

	public String[] getPANDUANChild() {
		return PANDUANChild;
	}

	public void setPANDUANChild(String[] child) {
		PANDUANChild = child;
	}

	public String[] getTIANKONGChild() {
		return TIANKONGChild;
	}

	public void setTIANKONGChild(String[] child) {
		TIANKONGChild = child;
	}

	public String[] getWENDAChild() {
		return WENDAChild;
	}

	public void setWENDAChild(String[] child) {
		WENDAChild = child;
	}

	public String[] getYUEDUChild() {
		return YUEDUChild;
	}

	public void setYUEDUChild(String[] child) {
		YUEDUChild = child;
	}

	public List getLeftQuestionList() {
		return leftQuestionList;
	}

	public void setLeftQuestionList(List leftQuestionList) {
		this.leftQuestionList = leftQuestionList;
	}

	public List getTitleStrList() {
		return titleStrList;
	}

	public void setTitleStrList(List titleStrList) {
		this.titleStrList = titleStrList;
	}

	public List getSingleObjectList() {
		return singleObjectList;
	}

	public void setSingleObjectList(List singleObjectList) {
		this.singleObjectList = singleObjectList;
	}

	public List getMultiObjectList() {
		return multiObjectList;
	}

	public void setMultiObjectList(List multiObjectList) {
		this.multiObjectList = multiObjectList;
	}

	public List getJudgeObjectList() {
		return judgeObjectList;
	}

	public void setJudgeObjectList(List judgeObjectList) {
		this.judgeObjectList = judgeObjectList;
	}

	public List getBlankObjectList() {
		return blankObjectList;
	}

	public void setBlankObjectList(List blankObjectList) {
		this.blankObjectList = blankObjectList;
	}

	public List getAnswerObjectList() {
		return answerObjectList;
	}

	public void setAnswerObjectList(List answerObjectList) {
		this.answerObjectList = answerObjectList;
	}

	public List getComprehensionObjectList() {
		return comprehensionObjectList;
	}

	public void setComprehensionObjectList(List comprehensionObjectList) {
		this.comprehensionObjectList = comprehensionObjectList;
	}

	public String getQNum() {
		return qNum;
	}

	public void setQNum(String num) {
		qNum = num;
	}

	public List getIndexList() {
		return indexList;
	}

	public void setIndexList(List indexList) {
		this.indexList = indexList;
	}

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public boolean isHasPre() {
		return hasPre;
	}

	public void setHasPre(boolean hasPre) {
		this.hasPre = hasPre;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getQuestion_score() {
		return question_score;
	}

	public void setQuestion_score(String question_score) {
		this.question_score = question_score;
	}

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public String[] getIndexs() {
		return indexs;
	}

	public void setIndexs(String[] indexs) {
		this.indexs = indexs;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getHistory_id() {
		return history_id;
	}

	public void setHistory_id(String history_id) {
		this.history_id = history_id;
	}

	public String getPaperUser_id() {
		return paperUser_id;
	}

	public void setPaperUser_id(String paperUser_id) {
		this.paperUser_id = paperUser_id;
	}

	public String getTotalNote() {
		return totalNote;
	}

	public void setTotalNote(String totalNote) {
		this.totalNote = totalNote;
	}

	public String getStudentnote() {
		return studentnote;
	}

	public void setStudentnote(String studentnote) {
		this.studentnote = studentnote;
	}

	public String getTeachernote() {
		return teachernote;
	}

	public void setTeachernote(String teachernote) {
		this.teachernote = teachernote;
	}

	public String[] getPurposeVal() {
		return purposeVal;
	}

	public void setPurposeVal(String[] purposeVal) {
		this.purposeVal = purposeVal;
	}

	public List getUserAnswerList() {
		return userAnswerList;
	}

	public void setUserAnswerList(List userAnswerList) {
		this.userAnswerList = userAnswerList;
	}

	public List getLoreNameList() {
		return loreNameList;
	}

	public void setLoreNameList(List loreNameList) {
		this.loreNameList = loreNameList;
	}

	public List getNumList() {
		return numList;
	}

	public void setNumList(List numList) {
		this.numList = numList;
	}

	public int getSingleNum() {
		return singleNum;
	}

	public void setSingleNum(int singleNum) {
		this.singleNum = singleNum;
	}

	public int getMultiNum() {
		return multiNum;
	}

	public void setMultiNum(int multiNum) {
		this.multiNum = multiNum;
	}

	public int getJudgeNum() {
		return judgeNum;
	}

	public void setJudgeNum(int judgeNum) {
		this.judgeNum = judgeNum;
	}

	public int getBlankNum() {
		return blankNum;
	}

	public void setBlankNum(int blankNum) {
		this.blankNum = blankNum;
	}

	public int getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(int answerNum) {
		this.answerNum = answerNum;
	}

	public List getCourseWareList() {
		return courseWareList;
	}

	public void setCourseWareList(List courseWareList) {
		this.courseWareList = courseWareList;
	}

	public boolean isSucc() {
		return succ;
	}

	public void setSucc(boolean succ) {
		this.succ = succ;
	}

	public boolean isHasAns() {
		return hasAns;
	}

	public void setHasAns(boolean hasAns) {
		this.hasAns = hasAns;
	}

	public List getAnsList() {
		return ansList;
	}

	public void setAnsList(List ansList) {
		this.ansList = ansList;
	}

	public PeQuestions getQuestion() {
		return question;
	}

	public void setQuestion(PeQuestions question) {
		this.question = question;
	}

	public PeAnswers getPeAnswers() {
		return peAnswers;
	}

	public void setPeAnswers(PeAnswers peAnswers) {
		this.peAnswers = peAnswers;
	}

	public String getOnlineType() {
		return onlineType;
	}

	public void setOnlineType(String onlineType) {
		this.onlineType = onlineType;
	}

	public List<PeVotePaper> getPeVotePaperList() {
		return peVotePaperList;
	}

	public void setPeVotePaperList(List<PeVotePaper> peVotePaperList) {
		this.peVotePaperList = peVotePaperList;
	}

	public List getBodyList() {
		return bodyList;
	}

	public void setBodyList(List bodyList) {
		this.bodyList = bodyList;
	}

	public String getAnliNextId() {
		return anliNextId;
	}

	public void setAnliNextId(String anliNextId) {
		this.anliNextId = anliNextId;
	}


}