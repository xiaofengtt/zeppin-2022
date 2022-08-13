package com.whaty.platform.info.web.action;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeRecExam;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PrRecExamStu;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.bean.PrStudentInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.recruit.PeRecStudentService;
import com.whaty.platform.entity.service.recruit.PeStudentService;
import com.whaty.platform.entity.service.recruit.PrRecExamStuService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.interfa.SMSFactory;

/**
 * 实现首页学生报名、信息修改操作、学生账号查询
 * 
 * @author zqf 审核人：
 */
public class FirstPageStudentRegisterAction extends MyBaseAction {

	private PeStudentService peStudentService;
	private PeRecStudentService peRecStudentService;
	private PrRecExamStuService prRecExamStuService;
	private PeRecStudent student; // 学生信息
	private PrRecExamStu prRecExamStu; // 学生考试批次信息
	private PeRecruitplan peRecruitplan; // 存储年级
	private String curPiCi; // 当前招生批次
	private String curGrade; // 所属的年级
	private List<PeRecExam> examPiCis; // 当前招生批次下的考试批次，只存储当前日期之后的考试批次

	private String site; // 招生学生注册页面中学习中心选项
	private String edutype; // 招生学生注册页面中报考类别选项
	private String major; // 招生学生注册页面中报考志愿选项
	private String pici; // 招生学生注册页面中报名参加哪次入学考试批次选项

	private String registerName; // 验证报名注册页面中所输入的name是否已经存在
	private String cardno; // 验证报名注册页面中所输入的证件号码是否已存在
	private String ajxResult; // 存储ajx验证后所返回的结果

	private String flagValid; // 用于首页网上报名，如果当前招生批次下已没有考试批次，flagValid设为0，并在页面显示当前招生批次下已无考试批次，暂无法报名

	private PrRecExamStu examstu; // 录取查询时用于存储学生相关信息
	private String isexist; // 所查询的学生是否存在
	private PrStudentInfo lqcx_stu;	//对于知金奥鹏的学生需要查询学籍库，学籍库中存在的学生已表明是已录取

	// 上传照片所用字段
	private File upload; // 文件
	private String uploadFileName; // 文件名属性
	private String uploadContentType; // 文件类型属性
	private String savePath; // 文件存储位置

	private String operateresult; // 学生注册结果

	private String reg_no; // 首页登陆页面用户名
	private String pass_word; // 首页登陆页面用户密码

	private String pass_old; // 旧密码（用于学生修改密码所用）

	/**
	 * 以下字段用于首页账号查询
	 */
	private String cardNo; // 用于首页账号查询
	private String name; // 用于首页账号查询
	private String studentstatus; // 所查学生的状态 0:在籍 1:退学 2：毕业
	private PrStudentInfo prStudentInfo; // 所查询到的学生

	public PeRecStudentService getPeRecStudentService() {
		return peRecStudentService;
	}

	public void setPeRecStudentService(PeRecStudentService peRecStudentService) {
		this.peRecStudentService = peRecStudentService;
	}

	public PrRecExamStuService getPrRecExamStuService() {
		return prRecExamStuService;
	}

	public void setPrRecExamStuService(PrRecExamStuService prRecExamStuService) {
		this.prRecExamStuService = prRecExamStuService;
	}

	public PeRecruitplan getPeRecruitplan() {
		return peRecruitplan;
	}

	public void setPeRecruitplan(PeRecruitplan peRecruitplan) {
		this.peRecruitplan = peRecruitplan;
	}

	public String getPici() {
		return pici;
	}

	public void setPici(String pici) {
		this.pici = pici;
	}

	public String getStudentstatus() {
		return studentstatus;
	}

	public void setStudentstatus(String studentstatus) {
		this.studentstatus = studentstatus;
	}

	public PrStudentInfo getPrStudentInfo() {
		return prStudentInfo;
	}

	public void setPrStudentInfo(PrStudentInfo prStudentInfo) {
		this.prStudentInfo = prStudentInfo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperateresult() {
		return operateresult;
	}

	public void setOperateresult(String operateresult) {
		this.operateresult = operateresult;
	}

	public String getIsexist() {
		return isexist;
	}

	public void setIsexist(String isexist) {
		this.isexist = isexist;
	}

	public String getReg_no() {
		return reg_no;
	}

	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}

	public String getPass_word() {
		return pass_word;
	}

	public void setPass_word(String pass_word) {
		this.pass_word = pass_word;
	}

	public String getPass_old() {
		return pass_old;
	}

	public void setPass_old(String pass_old) {
		this.pass_old = pass_old;
	}

	public PeRecStudent getStudent() {
		return student;
	}

	public void setStudent(PeRecStudent student) {
		this.student = student;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getEdutype() {
		return edutype;
	}

	public void setEdutype(String edutype) {
		this.edutype = edutype;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getCurPiCi() {
		return curPiCi;
	}

	public void setCurPiCi(String curPiCi) {
		this.curPiCi = curPiCi;
	}

	public String getCurGrade() {
		return curGrade;
	}

	public void setCurGrade(String curGrade) {
		this.curGrade = curGrade;
	}

	public String getAjxResult() {
		return ajxResult;
	}

	public String getFlagValid() {
		return flagValid;
	}

	public void setFlagValid(String flagValid) {
		this.flagValid = flagValid;
	}

	public void setAjxResult(String ajxResult) {
		this.ajxResult = ajxResult;
	}

	public PrRecExamStu getExamstu() {
		return examstu;
	}

	public void setExamstu(PrRecExamStu examstu) {
		this.examstu = examstu;
	}

	public List<PeRecExam> getExamPiCis() {
		return examPiCis;
	}

	public void setExamPiCis(List<PeRecExam> examPiCis) {
		this.examPiCis = examPiCis;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getSavePath() {
		return ServletActionContext.getRequest().getRealPath(savePath);
	}

	public String getSavePath(String path) {
		return ServletActionContext.getRequest().getRealPath(path);
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public PrRecExamStu getPrRecExamStu() {
		return prRecExamStu;
	}

	public void setPrRecExamStu(PrRecExamStu prRecExamStu) {
		this.prRecExamStu = prRecExamStu;
	}

	@Override
	public Map add() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public Page list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map updateColumn() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 验证用户名是否存在
	 * 
	 * @return
	 */
	public String isexist() {

		String result = "0";

		DetachedCriteria dcPeRecStudent = DetachedCriteria
				.forClass(PeRecStudent.class);

		dcPeRecStudent.add(Restrictions.eq("regNo", this.getRegisterName()));

		List<PeRecStudent> studentList = new ArrayList();

		try {
			studentList = this.getPeRecStudentService().getList(dcPeRecStudent);
		} catch (Exception e) {
			
		}
		/**
		 * 如果存在返回1
		 */
		if (studentList.size() > 0) {
			result = "1";
		}
		this.setAjxResult(result);
		return "ajxcheck";
	}

	/**
	 * 验证证件号码是否已存在
	 * 
	 * @return
	 */
	public String isexistCardNo() {

		String result = "0";
		
		String sql_check = " select student.id                                                    " + 
		"   from pe_rec_student            student,                            " +
		"        pr_rec_plan_major_site    majorsite,                          " +
		"        pr_rec_plan_major_edutype majoredutype,                       " +
		"        pe_recruitplan            recplan                             " +
		"  where student.fk_rec_major_site_id = majorsite.id                   " +
		"    and majorsite.fk_rec_plan_major_edutype_id = majoredutype.id      " +
		"    and majoredutype.fk_recruitplan_id = recplan.id                   " +
		"    and recplan.flag_active = '1'                                     " +
		"    and student.card_no = '"+this.getCardno()+"'                      " ;

		List list = new ArrayList();
		try{
			list = this.getPeStudentService().getBySQL(sql_check);
		}catch(Exception e){
			
		}
		
		DetachedCriteria dcPeStudent = DetachedCriteria
				.forClass(PrStudentInfo.class);

		dcPeStudent.add(Restrictions.eq("idCard", this.getCardno()));

		List studentList2 = new ArrayList();

		try {
			studentList2 = this.getPeRecStudentService().getList(dcPeStudent);
		} catch (Exception e) {
			
		}

		/**
		 * 如果存在返回1
		 */
		if (list.size() > 0) {
			result = "1";
		}
		if (studentList2.size() > 0) {
			result = "2";
		}
		this.setAjxResult(result);
		return "ajxcheck";
	}

	public String turntoRegister() {

		/**
		 * 查询出当前招生批次，用于页面显示
		 */
		DetachedCriteria dcPlan = DetachedCriteria
				.forClass(PeRecruitplan.class);

		DetachedCriteria dcGrade = dcPlan.createCriteria("peGrade", "peGrade");

		dcPlan.add(Restrictions.eq("flagActive", "1"));

		List<PeRecruitplan> planList = this.getPeRecStudentService().getList(
				dcPlan);

		if (planList.size() == 0) {
			this.setCurPiCi("当前没有活动招生批次！");
			this.setCurGrade("当前没有设置年级");
			this.setFlagValid("0");			//当前没有活动的招生批次
		} else {
			Calendar c = Calendar.getInstance();
			this.setCurPiCi(planList.get(0).getName());
			this.setCurGrade(planList.get(0).getPeGrade().getName());
			if((planList.get(0).getStartDate() != null && planList.get(0).getStartDate().after(c.getTime()))
					|| (planList.get(0).getEndDate() != null && planList.get(0).getEndDate().before(c.getTime()))){
				this.setFlagValid("1");		//不在报名时间范围内
			}else{
				this.setFlagValid("2");
			}
		}

		return "studentregister";
	}

	/**
	 * 报名学生信息添加
	 * 
	 * @return
	 */
	public String useradd() {

		String sitemanager = "";
		PeSitemanager peSitemanager = new PeSitemanager();
		
		//从session中获取到sitemanager的loginId的值
		try {
			sitemanager = ActionContext.getContext().getSession().get("sitemanager").toString();
		} catch (Exception e1) {
		}
		String sql_sitemanager = "";
		
		if(sitemanager != null && !"".equals(sitemanager)){
			sql_sitemanager = " select t.id from pe_sitemanager t,pe_site site,sso_user s where t.id = s.id and t.fk_site_id = site.id and site.name = '"+this.getSite()+"' and s.login_id = '"+sitemanager.trim()+"' ";
		}else{
			sql_sitemanager = " select t.id from pe_sitemanager t,pe_site s where t.fk_site_id = s.id and s.name = '"+this.getSite()+"' ";
		}
		
		List list = new ArrayList();
		try {
			list = this.getPeStudentService().getBySQL(sql_sitemanager);
		} catch (Exception e) {
		}
		if(list.size() == 0 && sitemanager != null && !"".equals(sitemanager)){
			sql_sitemanager = " select t.id from pe_sitemanager t,pe_site s where t.fk_site_id = s.id and s.name = '"+this.getSite()+"' ";
			try {
				list = this.getPeStudentService().getBySQL(sql_sitemanager);
			} catch (Exception e) {
			}
		}
		if(list.size() == 1){
			peSitemanager.setId(list.get(0).toString());
		}
		
		DetachedCriteria dcPeRecStudent = DetachedCriteria
				.forClass(PeRecStudent.class);

		dcPeRecStudent.add(Restrictions.eq("regNo", this.getStudent()
				.getRegNo()));

		List<PeRecStudent> studentList = new ArrayList();

		try {
			studentList = this.getPeRecStudentService().getList(dcPeRecStudent);
		} catch (Exception e) {
			
		}

		if (studentList.size() > 0) {
			this.setOperateresult("所输入的用户名已存在！");
			return "fail"; // 用于检测所输入的用户名是否已存在，如果存在，返回注册失败信息页面
		}

		/**
		 * 以下是学生信息录入部分
		 */
		// 学生表中外键
		PrRecPlanMajorSite FkprRecPlanMajorSite = new PrRecPlanMajorSite();

		/**
		 * 首先根据页面所选择的学习中心，层次和专业查出所对应的PrRecPlanMajorSite表的id，用于学生表中外键的存储
		 */
		DetachedCriteria dcPrRecPlanMajorSite = DetachedCriteria
				.forClass(PrRecPlanMajorSite.class);

		DetachedCriteria dcPeSite = dcPrRecPlanMajorSite.createCriteria(
				"peSite", "peSite");

		DetachedCriteria dcPrRecPlanMajorEdutype = dcPrRecPlanMajorSite
				.createCriteria("prRecPlanMajorEdutype",
						"prRecPlanMajorEdutype");

		DetachedCriteria dcPeMajor = dcPrRecPlanMajorEdutype.createCriteria(
				"peMajor", "peMajor");

		DetachedCriteria dcPeEdutype = dcPrRecPlanMajorEdutype.createCriteria(
				"peEdutype", "peEdutype");

		DetachedCriteria dcPeRecruitplan = dcPrRecPlanMajorEdutype
				.createCriteria("peRecruitplan", "peRecruitplan");

		dcPeSite.add(Restrictions.eq("name", this.getSite()));

		dcPeMajor.add(Restrictions.eq("name", this.getMajor()));

		dcPeEdutype.add(Restrictions.eq("name", this.getEdutype()));

		dcPeRecruitplan.add(Restrictions.eq("flagActive", "1"));

		List<PrRecPlanMajorSite> prRecPlanMajorSiteList = new ArrayList();

		try {
			prRecPlanMajorSiteList = this.getPeRecStudentService().getList(
					dcPrRecPlanMajorSite);
		} catch (Exception e) {
			
		}

		if (prRecPlanMajorSiteList.size() < 1) {
			this.setOperateresult("学习中心、层次和专业选择错误！"); // 学习中心、层次和专业选择错误
			return "fail";
		}

		if(peSitemanager != null && peSitemanager.getId() != null && !"".equals(peSitemanager.getId())){
			this.getStudent().setPeSitemanager(peSitemanager);
		}
		this.getStudent().setPrRecPlanMajorSite(prRecPlanMajorSiteList.get(0));
		this.getStudent().setStatus("0"); // 学生状态：默认为未审核
		this.getStudent().setFlagPublish("0"); // 学生录取信息：默认为未发布
		this.getStudent().setCaiwuStatus("0"); // 财务：默认为0
		this.getStudent().setRecruitType("0"); // 招生类别：默认为0：自主
		this.getStudent().setChangeStatus("0"); // 默认值0
		if ("1".equals(this.getStudent().getNoexam())) {
			this.getStudent().setNoexamStatus("4"); // 免试确认状态：默认4：未审核
		} else {
			this.getStudent().setNoexamStatus("3"); // 对于考试生默认为3
		}

		// 照片上传部分
		
		String tmp = this.getUploadFileName();
		if(tmp != null && !"".equals(tmp) && tmp.indexOf(".") > 0){
			tmp = tmp.substring(tmp.indexOf("."));
		}
		
		String photoname = this.getStudent().getCardNo()
				+ tmp;
		String photoLink = this.getSavePath(this.savePath + "/" + photoname);

		if (this.getUpload() != null) {
			try {
				FileOutputStream fos = new FileOutputStream(photoLink);
				FileInputStream fis = new FileInputStream(this.getUpload());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fis.close();
				fos.close();
			} catch (FileNotFoundException e) {
				
			} catch (IOException e) {
				
			}
			this.getStudent().setPhotoLink(this.savePath + "/" + photoname);
		}

		Date currentDate = new Date(System.currentTimeMillis());
		this.getStudent().setRegisterDate(currentDate);
		/**
		 * 学生信息添加
		 */
		PeRecStudent peRecStudent = new PeRecStudent();

		try {
			peRecStudent = this.getPeRecStudentService()
					.save(this.getStudent());
			/**
			 * 注册成功后，进行短信提示
			 */
			List MsgList = new ArrayList();
			try {
				MsgList = this.getPeStudentService().getMsgById("01");// 01:你已经成功完成华南师范大学(北京)现代远程教育的报名!
			} catch (EntityException e) {
				
			}
			if (MsgList.size() > 0) {
				SMSFactory.getInstance().send(peRecStudent.getMobilephone(),
						peRecStudent.getName() + MsgList.get(0).toString());
			}
		} catch (Exception e) {
			
			this.setOperateresult("学生注册失败！");
			return "fail";
		}

		/**
		 * 当学生选择考试批次时，将学生的考试信息添加到入学考试批次-学生关系表PR_REC_EXAM_STU中
		 */

		PeRecExam examstuFK = new PeRecExam(); // 入学考试-学生关系表中的考试批次的外键

		// 李冰 增加判断是否免试生功能
		if (this.getStudent().getNoexam().equals("1")) {
			this.setPici("请选择...");
		}

		if (!("请选择...".equals(this.getPici())
				|| "暂无考试批次".equals(this.getPici()) || "当前招生批次下已无考试批次"
				.equals(this.getPici()))) {

			DetachedCriteria dcPeRecExam = DetachedCriteria
					.forClass(PeRecExam.class);

			DetachedCriteria dcRecruitplan = dcPeRecExam.createCriteria(
					"peRecruitplan", "peRecruitplan");

			dcPeRecExam.add(Restrictions.eq("name", this.getPici()));

			dcRecruitplan.add(Restrictions.eq("flagActive", "1"));

			List<PeRecExam> piciList = new ArrayList();

			try {
				piciList = this.getPeRecStudentService().getList(dcPeRecExam);
			} catch (Exception e) {
				
			}
			if (piciList.size() > 0) {
				examstuFK = piciList.get(0);

				PrRecExamStu prRecExamStu = new PrRecExamStu(); // 保存学生考试批次信息
				if (examstuFK != null && examstuFK.getId() != null) {
					prRecExamStu.setPeRecExam(examstuFK);
				}
				prRecExamStu.setPeRecStudent(peRecStudent);

				try {
					this.getPrRecExamStuService().save(prRecExamStu);
				} catch (Exception e) {
					
				}
			}
		}

		this.setStudent(peRecStudent);

		this.setOperateresult("学生注册成功！");

		return "success";// 注册成功，返回信息修改页面
	}

	/**
	 * 登陆成功后转向登陆页面
	 * 
	 * @return
	 */
	public String login() {

		DetachedCriteria dcPeRecStudent = DetachedCriteria
				.forClass(PeRecStudent.class);

		dcPeRecStudent.add(Restrictions.eq("regNo", this.getReg_no()));

		dcPeRecStudent.add(Restrictions.eq("password", this.getPass_word()));

		List<PeRecStudent> studentList = new ArrayList();

		try {
			studentList = this.getPeRecStudentService().getList(dcPeRecStudent);
		} catch (Exception e) {
			
		}

		if (studentList.size() < 1) {
			this.setOperateresult("用户名和密码错误！请重新登陆！");
			return "fail";
		}

		this.setStudent(studentList.get(0));

		return "success";
	}

	public String getstudentInfo() {

		/**
		 * 学生信息（包括学习中心、层次和专业信息）
		 */
		DetachedCriteria dcPeRecStudent = DetachedCriteria
				.forClass(PeRecStudent.class);

		DetachedCriteria dcPrRecPlanMajorSite = dcPeRecStudent.createCriteria(
				"prRecPlanMajorSite", "prRecPlanMajorSite");

		DetachedCriteria dcPrRecPlanMajorEdutype = dcPrRecPlanMajorSite
				.createCriteria("prRecPlanMajorEdutype",
						"prRecPlanMajorEdutype");

		dcPrRecPlanMajorEdutype.createCriteria("peEdutype", "peEdutype");

		dcPrRecPlanMajorEdutype.createCriteria("peMajor", "peMajor");

		dcPeRecStudent.add(Restrictions.eq("id", this.getStudent().getId()));

		List<PeRecStudent> studentList = this.getPeRecStudentService().getList(
				dcPeRecStudent);

		if (studentList.size() > 0) {
			this.setStudent(studentList.get(0));
		}
		/**
		 * 学生考试批次信息
		 */
		DetachedCriteria dcPrRecExamStu = DetachedCriteria
				.forClass(PrRecExamStu.class);

		DetachedCriteria dcStudent = dcPrRecExamStu.createCriteria(
				"peRecStudent", "peRecStudent");

		dcPrRecExamStu.createCriteria("peRecExam", "peRecExam");

		dcStudent.add(Restrictions.eq("id", this.getStudent().getId()));

		List<PrRecExamStu> examstuList = this.getPeRecStudentService().getList(
				dcPrRecExamStu);

		if (examstuList.size() > 0) {
			this.setPrRecExamStu(examstuList.get(0));
		}

		/**
		 * 学生年级信息
		 */

		DetachedCriteria dcPeRecruitplan = DetachedCriteria
				.forClass(PeRecruitplan.class);

		dcPeRecruitplan.createCriteria("peGrade", "peGrade");

		dcPeRecruitplan.add(Restrictions.eq("flagActive", "1"));

		List<PeRecruitplan> recruitPlanList = this.getPeRecStudentService()
				.getList(dcPeRecruitplan);

		if (recruitPlanList.size() > 0) {
			this.setPeRecruitplan(recruitPlanList.get(0));
		}

		return "studentinfo";
	}

	/**
	 * 转向学生信息修改页面
	 * 
	 * @return
	 */
	public String turntoeditstudentInfo() {

		/**
		 * 学生信息（包括学习中心、层次和专业信息）
		 */
		DetachedCriteria dcPeRecStudent = DetachedCriteria
				.forClass(PeRecStudent.class);

		DetachedCriteria dcPrRecPlanMajorSite = dcPeRecStudent.createCriteria(
				"prRecPlanMajorSite", "prRecPlanMajorSite");

		DetachedCriteria dcPrRecPlanMajorEdutype = dcPrRecPlanMajorSite
				.createCriteria("prRecPlanMajorEdutype",
						"prRecPlanMajorEdutype");

		dcPrRecPlanMajorEdutype.createCriteria("peEdutype", "peEdutype");

		dcPrRecPlanMajorEdutype.createCriteria("peMajor", "peMajor");

		dcPeRecStudent.add(Restrictions.eq("id", this.getStudent().getId()));

		List<PeRecStudent> studentList = this.getPeRecStudentService().getList(
				dcPeRecStudent);

		if (studentList.size() > 0) {
			this.setStudent(studentList.get(0));
		}

		/**
		 * 将学生生日放到session中，用于页面显示
		 */

		ActionContext axt = ActionContext.getContext();
		try {
			if (this.getStudent().getBirthday() != null
					&& !"".equals(this.getStudent().getBirthday())) {
				axt.getSession().put("birthday",
						this.getStudent().getBirthday());
			} else
				axt.getSession().put("birthday", "");
		} catch (Exception e) {
			
		}

		/**
		 * 学生考试批次信息
		 */
		DetachedCriteria dcPrRecExamStu = DetachedCriteria
				.forClass(PrRecExamStu.class);

		DetachedCriteria dcStudent = dcPrRecExamStu.createCriteria(
				"peRecStudent", "peRecStudent");

		dcPrRecExamStu.createCriteria("peRecExam", "peRecExam");

		dcStudent.add(Restrictions.eq("id", this.getStudent().getId()));

		List<PrRecExamStu> examstuList = this.getPeRecStudentService().getList(
				dcPrRecExamStu);

		if (examstuList.size() > 0) {
			this.setPrRecExamStu(examstuList.get(0));
		}

		/**
		 * 学生年级信息
		 */

		DetachedCriteria dcPeRecruitplan = DetachedCriteria
				.forClass(PeRecruitplan.class);

		dcPeRecruitplan.createCriteria("peGrade", "peGrade");

		dcPeRecruitplan.add(Restrictions.eq("flagActive", "1"));

		List<PeRecruitplan> recruitPlanList = this.getPeRecStudentService()
				.getList(dcPeRecruitplan);

		if (recruitPlanList.size() > 0) {
			this.setPeRecruitplan(recruitPlanList.get(0));
		}

		return "turntoeditstudentinfo";
	}

	public String editstudentInfo() {

		String sitemanager = "";
		PeSitemanager peSitemanager = new PeSitemanager();
		
		//从session中获取到sitemanager的loginId的值
		try {
			sitemanager = ActionContext.getContext().getSession().get("sitemanager").toString();
		} catch (Exception e1) {
		}
		String sql_sitemanager = "";
		
		if(sitemanager != null && !"".equals(sitemanager)){
			sql_sitemanager = " select t.id from pe_sitemanager t,pe_site site,sso_user s where t.id = s.id and t.fk_site_id = site.id and site.name = '"+this.getSite()+"' and s.login_id = '"+sitemanager.trim()+"' ";
		}else{
			sql_sitemanager = " select t.id from pe_sitemanager t,pe_site s where t.fk_site_id = s.id and s.name = '"+this.getSite()+"' ";
		}
		
		List list = new ArrayList();
		try {
			list = this.getPeStudentService().getBySQL(sql_sitemanager);
		} catch (Exception e) {
		}
		if(list.size() == 0 && sitemanager != null && !"".equals(sitemanager)){
			sql_sitemanager = " select t.id from pe_sitemanager t,pe_site s where t.fk_site_id = s.id and s.name = '"+this.getSite()+"' ";
			try {
				list = this.getPeStudentService().getBySQL(sql_sitemanager);
			} catch (Exception e) {
			}
		}
		if(list.size() == 1){
			peSitemanager.setId(list.get(0).toString());
		}
		
		/**
		 * 以下是学生信息录入部分
		 */
		PeRecStudent peRecStudent = new PeRecStudent();

		DetachedCriteria dcRecStudent = DetachedCriteria
				.forClass(PeRecStudent.class);
		dcRecStudent.add(Restrictions.eq("id", this.getStudent().getId()));

		List<PeRecStudent> stuList = new ArrayList();

		try {
			stuList = this.getPrRecExamStuService().getList(dcRecStudent);
		} catch (Exception e) {
			
		}
		if (stuList.size() > 0) {
			peRecStudent = stuList.get(0);
		}

		peRecStudent.setName(this.getStudent().getName());
		peRecStudent.setGender(this.getStudent().getGender());
		peRecStudent.setFolk(this.getStudent().getFolk());
		peRecStudent.setBirthday(this.getStudent().getBirthday());
		peRecStudent.setStudyAddress(this.getStudent().getStudyAddress());
		peRecStudent.setZipAddress(this.getStudent().getZipAddress());
		peRecStudent.setEmail(this.getStudent().getEmail());
		peRecStudent.setHometown(this.getStudent().getHometown());
		peRecStudent.setCity(this.getStudent().getCity());
		peRecStudent.setRootphone(this.getStudent().getRootphone());
		peRecStudent.setMobilephone(this.getStudent().getMobilephone());
		peRecStudent.setWorkplace(this.getStudent().getWorkplace());
		peRecStudent.setZhicheng(this.getStudent().getZhicheng());
		peRecStudent.setZhiwu(this.getStudent().getZhiwu());
		peRecStudent.setZhiye(this.getStudent().getZhiye());
		peRecStudent.setZzmm(this.getStudent().getZzmm());
		peRecStudent.setMarry(this.getStudent().getMarry());
		peRecStudent.setOldGraduatedEduType(this.getStudent()
				.getOldGraduatedEduType());
		peRecStudent.setXueliNo(this.getStudent().getXueliNo());
		peRecStudent.setXueliSch(this.getStudent().getXueliSch());
		peRecStudent.setXueliSchno(this.getStudent().getXueliSchno());
		peRecStudent.setXueliTime(this.getStudent().getXueliTime());
		peRecStudent.setNoexam(this.getStudent().getNoexam());
		peRecStudent.setResume(this.getStudent().getResume());
		peRecStudent.setArea(this.getStudent().getArea());

		// 学生表中外键
		PrRecPlanMajorSite FkprRecPlanMajorSite = new PrRecPlanMajorSite();

		/**
		 * 首先根据页面所选择的学习中心，层次和专业查出所对应的PrRecPlanMajorSite表的id，用于学生表中外键的存储
		 */
		DetachedCriteria dcPrRecPlanMajorSite = DetachedCriteria
				.forClass(PrRecPlanMajorSite.class);

		DetachedCriteria dcPeSite = dcPrRecPlanMajorSite.createCriteria(
				"peSite", "peSite");

		DetachedCriteria dcPrRecPlanMajorEdutype = dcPrRecPlanMajorSite
				.createCriteria("prRecPlanMajorEdutype",
						"prRecPlanMajorEdutype");

		DetachedCriteria dcPeMajor = dcPrRecPlanMajorEdutype.createCriteria(
				"peMajor", "peMajor");

		DetachedCriteria dcPeEdutype = dcPrRecPlanMajorEdutype.createCriteria(
				"peEdutype", "peEdutype");

		DetachedCriteria dcPeRecruitplan = dcPrRecPlanMajorEdutype
				.createCriteria("peRecruitplan", "peRecruitplan");

		dcPeSite.add(Restrictions.eq("name", this.getSite()));

		dcPeMajor.add(Restrictions.eq("name", this.getMajor()));

		dcPeEdutype.add(Restrictions.eq("name", this.getEdutype()));

		dcPeRecruitplan.add(Restrictions.eq("flagActive", "1"));

		List<PrRecPlanMajorSite> prRecPlanMajorSiteList = new ArrayList();

		try {
			prRecPlanMajorSiteList = this.getPeRecStudentService().getList(
					dcPrRecPlanMajorSite);
		} catch (Exception e) {
			
		}

		if (prRecPlanMajorSiteList.size() < 1) {
			this.setOperateresult("学生信息修改失败！学习中心、层次和专业选择错误！"); // 学习中心、层次和专业选择错误
			return "success";
		}

		peRecStudent.setPrRecPlanMajorSite(prRecPlanMajorSiteList.get(0)); // 设置外键
		if(peSitemanager != null && peSitemanager.getId() != null && !"".equals(peSitemanager.getId())){
			peRecStudent.setPeSitemanager(peSitemanager);
		}

		// 照片上传部分
		
		String tmp = this.getUploadFileName();
		if(tmp != null && !"".equals(tmp) && tmp.indexOf(".") > 0){
			tmp = tmp.substring(tmp.indexOf("."));
		}
		
		String photoname = this.getStudent().getCardNo()
				+ tmp;
		String photoLink = this.getSavePath(this.savePath + "/" + photoname);

		if (this.getUpload() != null) {
			try {
				FileOutputStream fos = new FileOutputStream(photoLink);
				FileInputStream fis = new FileInputStream(this.getUpload());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fis.close();
				fos.close();
			} catch (FileNotFoundException e) {
				
			} catch (IOException e) {
				
			}
			peRecStudent.setPhotoLink(this.savePath + "/" + photoname);
		}

		try {
			peRecStudent = this.getPeRecStudentService().save(peRecStudent);
		} catch (Exception e) {
			
			this.setOperateresult("学生信息修改失败！");
			return "success";
		}

		/**
		 * 当学生选择考试批次时，将学生的考试信息添加到入学考试批次-学生关系表PR_REC_EXAM_STU中
		 */

		PeRecExam examstuFK = new PeRecExam(); // 入学考试-学生关系表中的考试批次的外键

		if (!("请选择...".equals(this.getPici())
				|| "暂无考试批次".equals(this.getPici()) || "当前招生批次下已无考试批次"
				.equals(this.getPici())) && !"1".equals(peRecStudent.getNoexam())) {
			
			checkBeforEdit(peRecStudent.getId());

			DetachedCriteria dcPeRecExam = DetachedCriteria
					.forClass(PeRecExam.class);

			DetachedCriteria dcRecruitplan = dcPeRecExam.createCriteria(
					"peRecruitplan", "peRecruitplan");

			dcPeRecExam.add(Restrictions.eq("name", this.getPici()));

			dcRecruitplan.add(Restrictions.eq("flagActive", "1"));

			List<PeRecExam> piciList = new ArrayList();

			try {
				piciList = this.getPeRecStudentService().getList(dcPeRecExam);
			} catch (Exception e) {
				
			}
			if (piciList.size() > 0) {
				examstuFK = piciList.get(0);
			}
			PrRecExamStu prRecExamStu = new PrRecExamStu(); // 保存学生考试批次信息

			DetachedCriteria dcPrRecExamStu = DetachedCriteria
					.forClass(PrRecExamStu.class);

			dcPrRecExamStu.createCriteria("peRecStudent", "peRecStudent").add(
					Restrictions.eq("id", peRecStudent.getId()));

			List<PrRecExamStu> examstuList = new ArrayList();

			try {
				examstuList = this.getPrRecExamStuService().getList(dcPrRecExamStu);
			} catch (Exception e) {
				
			}

			if (examstuList.size() > 0) {
				prRecExamStu = examstuList.get(0);
			}
			if (examstuFK != null && examstuFK.getId() != null) {
				prRecExamStu.setPeRecExam(examstuFK);
			}
			prRecExamStu.setPeRecStudent(peRecStudent);

			try {
				this.getPrRecExamStuService().save(prRecExamStu);
			} catch (Exception e) {
				
			}
		}

		

		this.setStudent(peRecStudent);

		this.setOperateresult("学生信息修改成功！");

		return "success";// 信息修改，返回信息修改页面
	}
	
	/**
	 * 用于修改学生考试批次之前的处理
	 * 学生如果原来已选择考试批次，则先删除，
	 * 如果不能删除，则说明已经分配过考试课程
	 * 再次修改的时候将认为是为学生重新分配考试批次
	 * @param student_id
	 */
	private void checkBeforEdit(String student_id){
		List list = new ArrayList();
		try {
			String sql = " select es.id from pr_rec_exam_stu es where es.fk_rec_stu_id = '"+student_id+"' ";
			list = this.getPeStudentService().getBySQL(sql);
			
			if(list.size() > 0){
				String sql_delete = " delete from pr_rec_exam_stu es where es.id = '"+list.get(0).toString()+"' ";
				this.getPeStudentService().executeBySQL(sql_delete);
			}
			
		} catch (Exception e) {
			
		}
		
	}

	/**
	 * 打印报名表
	 * 
	 * @return
	 */
	public String pdfprinter() {

		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		/**
		 * 首先取出学生信息（包括学习中心、层次和专业信息）
		 */
		DetachedCriteria dcPeRecStudent = DetachedCriteria
				.forClass(PeRecStudent.class);

		DetachedCriteria dcPrRecPlanMajorSite = dcPeRecStudent.createCriteria(
				"prRecPlanMajorSite", "prRecPlanMajorSite");

		DetachedCriteria dcPrRecPlanMajorEdutype = dcPrRecPlanMajorSite
				.createCriteria("prRecPlanMajorEdutype",
						"prRecPlanMajorEdutype");

		dcPrRecPlanMajorEdutype.createCriteria("peEdutype", "peEdutype");

		dcPrRecPlanMajorEdutype.createCriteria("peMajor", "peMajor");

		dcPeRecStudent.add(Restrictions.eq("id", this.getStudent().getId()));

		List<PeRecStudent> studentList = this.getPeRecStudentService().getList(
				dcPeRecStudent);

		if (studentList.size() > 0) {
			this.setStudent(studentList.get(0));
		}
		/**
		 * 学生考试批次信息
		 */
		DetachedCriteria dcPrRecExamStu = DetachedCriteria
				.forClass(PrRecExamStu.class);

		DetachedCriteria dcStudent = dcPrRecExamStu.createCriteria(
				"peRecStudent", "peRecStudent");

		dcPrRecExamStu.createCriteria("peRecExam", "peRecExam");

		dcStudent.add(Restrictions.eq("id", this.getStudent().getId()));

		List<PrRecExamStu> examstuList = this.getPeRecStudentService().getList(
				dcPrRecExamStu);

		if (examstuList.size() > 0) {
			this.setPrRecExamStu(examstuList.get(0));
		}

		/**
		 * 学生年级信息
		 */

		DetachedCriteria dcPeRecruitplan = DetachedCriteria
				.forClass(PeRecruitplan.class);

		dcPeRecruitplan.createCriteria("peGrade", "peGrade");

		dcPeRecruitplan.add(Restrictions.eq("flagActive", "1"));

		List<PeRecruitplan> recruitPlanList = this.getPeRecStudentService()
				.getList(dcPeRecruitplan);

		if (recruitPlanList.size() > 0) {
			this.setPeRecruitplan(recruitPlanList.get(0));
		}

		/***********************************************************************
		 * PdfPTable的排版
		 **********************************************************************/
		final float DEFAULT_CELL_HIGHT = 23.4f;
		Document document = new Document(PageSize.A4, 0, 0, 18, 0);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			
		}
		document.open();

		BaseFont bfChinese = null;
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			
		}
		Font font16 = new Font(bfChinese, 20, Font.BOLD);
		Font font12 = new Font(bfChinese, 12, Font.NORMAL);
		Font font12Bold = new Font(bfChinese, 12, Font.BOLD);
		Font font10 = new Font(bfChinese, 12, Font.NORMAL);
		Font font10Bold = new Font(bfChinese, 10, Font.BOLD);
		Font font9 = new Font(bfChinese, 9, Font.NORMAL);

		String tableTitle = "华南师范大学远程教育报名登记表";
		Paragraph paragraph = new Paragraph(tableTitle, font16);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		try {
			document.add(paragraph);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			
		}

		try {
			document.add(new Paragraph("\n"));
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			
		}

		PdfPTable pTable = new PdfPTable(new float[] { 12.7f, 4f });
		pTable.getDefaultCell().setPadding(0);
		// pTable.setTotalWidth(1670f);
		PdfPTable pCellTable = new PdfPTable(new float[] { 2.5f, 3.3f, 2.7f,
				4.2f });
		PdfPCell pCell = new PdfPCell(new Paragraph("学习中心名称", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCell.setColspan(2);
		pCellTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph(this.getStudent()
				.getPrRecPlanMajorSite().getPeSite().getName(), font10));
		pCell.setColspan(2);
		pCellTable.addCell(pCell);

		pCell = new PdfPCell(new Paragraph("报考批次", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCell.setColspan(2);
		pCellTable.addCell(pCell);
		String exampici = "";
		if (this.getPrRecExamStu() != null
				&& this.getPrRecExamStu().getPeRecExam() != null
				&& this.getPrRecExamStu().getPeRecExam().getName() != null) {
			exampici = this.getPrRecExamStu().getPeRecExam().getName();
		}
		pCell = new PdfPCell(new Paragraph(exampici, font10));
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCell.setColspan(2);
		pCellTable.addCell(pCell);

		pCell = new PdfPCell(new Paragraph("姓          名", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCellTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph(this.getStudent().getName(), font10));
		pCellTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph("性          别", font10));
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCellTable.addCell(pCell);
		String gender = "";
		if (this.getStudent().getGender() != null
				&& this.getStudent().getGender().equals("男")) {
			gender = "■ 男  □ 女";
		} else if (this.getStudent().getGender() != null
				&& this.getStudent().getGender().equals("女")) {
			gender = "□ 男  ■ 女";
		} else {
			gender = "□ 男  □ 女";
		}
		pCell = new PdfPCell(new Paragraph(gender, font10));
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCellTable.addCell(pCell);

		pCell = new PdfPCell(new Paragraph("民          族", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCellTable.addCell(pCell);
		String folk = "";
		if (this.getStudent().getFolk() != null) {
			folk = this.getStudent().getFolk();
		}
		pCell = new PdfPCell(new Paragraph(folk, font10));
		pCellTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph("出生年月日", font10));
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCellTable.addCell(pCell);
		String birthday = "";
		if (this.getStudent().getBirthday() != null) {
			birthday = this.getStudent().getBirthday();
		}
		pCell = new PdfPCell(new Paragraph(birthday, font10));
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCellTable.addCell(pCell);

		pCell = new PdfPCell(new Paragraph("固定电话", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCellTable.addCell(pCell);
		String rootphone = "";
		if (this.getStudent().getRootphone() != null) {
			rootphone = this.getStudent().getRootphone();
		}
		pCell = new PdfPCell(new Paragraph(rootphone, font10));
		pCellTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph("移动电话", font10));
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCellTable.addCell(pCell);
		String mobile = "";
		if (this.getStudent().getMobilephone() != null) {
			mobile = this.getStudent().getMobilephone();
		}
		pCell = new PdfPCell(new Paragraph(mobile, font10));
		pCellTable.addCell(pCell);
		String card_type = "";
		if (this.getStudent().getCardType() != null
				&& this.getStudent().getCardType().equals("身份证"))
			card_type = "■  身份证      □  军（警）官证      □  护照      □其他    ";
		else if (this.getStudent().getCardType() != null
				&& this.getStudent().getCardType().equals("军(警)官证"))
			card_type = "□  身份证      ■  军（警）官证      □  护照      □其他    ";
		else if (this.getStudent().getCardType() != null
				&& this.getStudent().getCardType().equals("护照"))
			card_type = "□  身份证      □  军（警）官证      ■  护照      □其他    ";
		else
			card_type = "□  身份证      □  军（警）官证      □  护照      ■其他    ";
		pCell = new PdfPCell(new Paragraph("证件名称", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCellTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph(card_type, font10));
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCell.setColspan(3);
		pCellTable.addCell(pCell);

		pCell = new PdfPCell(new Paragraph("证件号码", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCellTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph(this.getStudent().getCardNo(),
				font10));
		pCell.setColspan(3);
		pCellTable.addCell(pCell);

		pTable.addCell(pCellTable);

		String url = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort();
		String temp = url;
		url = url + this.getStudent().getPhotoLink();
		Image image = null;
		try {
			image = Image.getInstance(url);
			image.scaleAbsolute(112, 162);

		} catch (Exception e) {
			try {
				image = Image.getInstance(temp + "/incoming/photo/noImage.jpg");
			} catch (BadElementException e1) {
				// TODO Auto-generated catch block
				
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				
			}
			image.scaleAbsolute(112, 162);
		}
		pCell = new PdfPCell(image);
		pCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pTable.addCell(pCell);

		try {
			document.add(pTable);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			
		}

		pTable = new PdfPTable(new float[] { 2.5f, 6.5f, 2.2f, 5.5f });
		pCell = new PdfPCell(new Paragraph("工作单位", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pTable.addCell(pCell);
		String workplace = "";
		if (this.getStudent().getWorkplace() != null) {
			workplace = this.getStudent().getWorkplace();
		}
		pCell = new PdfPCell(new Paragraph(workplace, font10));
		pCell.setColspan(3);
		pTable.addCell(pCell);

		pCell = new PdfPCell(new Paragraph("通信地址", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph(
				this.getStudent().getStudyAddress() == null ? "" : this
						.getStudent().getStudyAddress(), font10));
		pCell.setColspan(3);
		pTable.addCell(pCell);

		pCell = new PdfPCell(new Paragraph("电子邮箱", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph(
				this.getStudent().getEmail() == null ? "" : this.getStudent()
						.getEmail(), font10));
		pTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph("邮政编码", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph(
				this.getStudent().getZipAddress() == null ? "" : this
						.getStudent().getZipAddress(), font10));
		pTable.addCell(pCell);

		pCell = new PdfPCell(new Paragraph("居   住   地", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		String city = "";
		if (this.getStudent().getCity() != null) {
			city = this.getStudent().getCity();
		}
		if(this.getStudent().getArea() != null){
			city += "/" + this.getStudent().getArea();
		}
		pTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph("             "
				+ this.getStudent().getHometown() + "             " + city
				+ "   市/区", font10));
		pCell.setColspan(3);
		pTable.addCell(pCell);

		pCell = new PdfPCell(new Paragraph("职          业", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pTable.addCell(pCell);
		String zhiye = "";
		if (this.getStudent().getZhiye() != null
				&& this.getStudent().getZhiye().equals("学生"))
			zhiye = "■ 学生  □ 教师  □ 公务员  □ 公司职员  □ 自由职业  □ 其它        ";
		else if (this.getStudent().getZhiye() != null
				&& this.getStudent().getZhiye().equals("教师"))
			zhiye = "□ 学生  ■ 教师  □ 公务员  □ 公司职员  □ 自由职业  □ 其它        ";
		else if (this.getStudent().getZhiye() != null
				&& this.getStudent().getZhiye().equals("公务员"))
			zhiye = "□ 学生  □ 教师  ■ 公务员  □ 公司职员  □ 自由职业  □ 其它        ";
		else if (this.getStudent().getZhiye() != null
				&& this.getStudent().getZhiye().equals("公司职员"))
			zhiye = "□ 学生  □ 教师  □ 公务员  ■ 公司职员  □ 自由职业  □ 其它        ";
		else if (this.getStudent().getZhiye() != null
				&& this.getStudent().getZhiye().equals("自由职业"))
			zhiye = "□ 学生  □ 教师  □ 公务员  □ 公司职员  ■ 自由职业  □ 其它        ";
		else if (this.getStudent().getZhiye() != null
				&& this.getStudent().getZhiye().equals("其他"))
			zhiye = "□ 学生  □ 教师  □ 公务员  □ 公司职员  □ 自由职业  ■ 其它        ";
		else
			zhiye = "□ 学生  □ 教师  □ 公务员  □ 公司职员  □ 自由职业  □ 其它        ";
		pCell = new PdfPCell(new Paragraph(zhiye, font10));
		pCell.setColspan(3);
		pTable.addCell(pCell);

		pCell = new PdfPCell(new Paragraph("最后学历", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pTable.addCell(pCell);
		String old_graduated_edu_type = "";
		if (this.getStudent().getOldGraduatedEduType() != null
				&& this.getStudent().getOldGraduatedEduType().equals("中专"))
			old_graduated_edu_type = "■ 中专 □ 中师  □ 普高  □ 职高 □ 技校 □ 大专 □ 本科 □ 研究生";
		else if (this.getStudent().getOldGraduatedEduType() != null
				&& this.getStudent().getOldGraduatedEduType().equals("中师"))
			old_graduated_edu_type = "□ 中专 ■ 中师  □ 普高  □ 职高 □ 技校 □ 大专 □ 本科 □ 研究生";
		else if (this.getStudent().getOldGraduatedEduType() != null
				&& this.getStudent().getOldGraduatedEduType().equals("普高"))
			old_graduated_edu_type = "□ 中专 □ 中师  ■ 普高  □ 职高 □ 技校 □ 大专 □ 本科 □ 研究生";
		else if (this.getStudent().getOldGraduatedEduType() != null
				&& this.getStudent().getOldGraduatedEduType().equals("职高"))
			old_graduated_edu_type = "□ 中专 □ 中师  □ 普高  ■ 职高 □ 技校 □ 大专 □ 本科 □ 研究生";
		else if (this.getStudent().getOldGraduatedEduType() != null
				&& this.getStudent().getOldGraduatedEduType().equals("技校"))
			old_graduated_edu_type = "□ 中专 □ 中师  □ 普高  □ 职高 ■ 技校 □ 大专 □ 本科 □ 研究生";
		else if (this.getStudent().getOldGraduatedEduType() != null
				&& this.getStudent().getOldGraduatedEduType().equals("大专"))
			old_graduated_edu_type = "□ 中专 □ 中师  □ 普高  □ 职高 □ 技校 ■ 大专 □ 本科 □ 研究生";
		else if (this.getStudent().getOldGraduatedEduType() != null
				&& this.getStudent().getOldGraduatedEduType().equals("本科"))
			old_graduated_edu_type = "□ 中专 □ 中师  □ 普高  □ 职高 □ 技校 □ 大专 ■ 本科 □ 研究生";
		else if (this.getStudent().getOldGraduatedEduType() != null
				&& this.getStudent().getOldGraduatedEduType().equals("研究生"))
			old_graduated_edu_type = "□ 中专 □ 中师  □ 普高  □ 职高 □ 技校 □ 大专 □ 本科 ■ 研究生";
		else
			old_graduated_edu_type = "□ 中专 □ 中师  □ 普高  □ 职高 □ 技校 □ 大专 □ 本科 □ 研究生";
		pCell = new PdfPCell(new Paragraph(old_graduated_edu_type, font10));
		pCell.setColspan(3);
		pTable.addCell(pCell);

		pCell = new PdfPCell(new Paragraph("报考类别", font10));
		pCell.setMinimumHeight((float) 2.2 * DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pTable.addCell(pCell);
		String edu_type = "";
		if ("高起专".equals(this.getStudent().getPrRecPlanMajorSite()
				.getPrRecPlanMajorEdutype().getPeEdutype().getName()))
			edu_type = "■ 高起专  □ 专升本";
		else
			edu_type = "□ 高起专  ■ 专升本";
		paragraph = new Paragraph(edu_type + "\n", font10);
		paragraph.add(new Paragraph("\n（报考专升本层次必须提供教育部学历认证材料，详见主页“报考必读”）",
				font10Bold));
		pCell = new PdfPCell(paragraph);
		pCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pCell.setColspan(3);
		pTable.addCell(pCell);

		pCell = new PdfPCell(new Paragraph("报考专业", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pTable.addCell(pCell);
		String major = "";
		if (this.getStudent().getPrRecPlanMajorSite() != null
				&& this.getStudent().getPrRecPlanMajorSite()
						.getPrRecPlanMajorEdutype() != null
				&& this.getStudent().getPrRecPlanMajorSite()
						.getPrRecPlanMajorEdutype().getPeMajor() != null
				&& this.getStudent().getPrRecPlanMajorSite()
						.getPrRecPlanMajorEdutype().getPeMajor().getName() != null) {
			major = this.getStudent().getPrRecPlanMajorSite()
					.getPrRecPlanMajorEdutype().getPeMajor().getName();
		}
		pCell = new PdfPCell(new Paragraph(major, font10));
		pCell.setColspan(3);
		pTable.addCell(pCell);

		pCell = new PdfPCell(new Paragraph("入学方式", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pTable.addCell(pCell);
		String noexam = "";
		if ("0".equals(this.getStudent().getNoexam()))
			noexam = "        ■ 考试        □ 免试";
		if ("1".equals(this.getStudent().getNoexam()))
			noexam = "        □ 考试        ■ 免试";
		pCell = new PdfPCell(new Paragraph(noexam, font10));
		pCell.setColspan(3);
		pTable.addCell(pCell);

		try {
			document.add(pTable);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			
		}

		pTable = new PdfPTable(new float[] { 1.1f, 15.6f });
		pTable.getDefaultCell().setPadding(0);
		pCell = new PdfPCell(new Paragraph("毕\n业\n证\n书\n信\n息", font10));
		pCell.setMinimumHeight(4 * DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pTable.addCell(pCell);

		pCellTable = new PdfPTable(new float[] { 2.8f, 12.8f });
		pCell = new PdfPCell(new Paragraph("毕业学校", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCellTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph(
				this.getStudent().getXueliSch() == null ? "" : this
						.getStudent().getXueliSch(), font10));
		pCellTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph("学校代码", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCellTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph(
				this.getStudent().getXueliSchno() == null ? "" : this
						.getStudent().getXueliSchno(), font10));
		pCellTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph("毕业时间", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCellTable.addCell(pCell);
		String xueli_time = "";
		xueli_time = this.getStudent().getXueliTime();
		if (xueli_time != null && xueli_time.length() > 4) {
			xueli_time = xueli_time.substring(0, 4);
		}
		pCell = new PdfPCell(new Paragraph(xueli_time, font10));
		pCellTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph("证书编号", font10));
		pCell.setMinimumHeight(DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCellTable.addCell(pCell);
		pCell = new PdfPCell(new Paragraph(
				this.getStudent().getXueliNo() == null ? "" : this.getStudent()
						.getXueliNo(), font10));
		pCellTable.addCell(pCell);

		pTable.addCell(pCellTable);

		pCell = new PdfPCell(new Paragraph("考\n\n生\n\n承\n\n诺", font10));
		pCell.setMinimumHeight(9 * DEFAULT_CELL_HIGHT);
		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pTable.addCell(pCell);
		paragraph = new Paragraph(
				"                                                                  本  人  承  诺\n\n",
				font12Bold);
		String promiseContent = "          本人已经阅读招生简章以及相关说明，以上所有信息和所提供的证件、证明均真实有效。"
				+ "\n\n如果经核实有隐瞒个人真实学习经历、尤其是伪造国民教育系列毕业证或伪造个人身份、学历\n\n证明等材料的情况，"
				+ "无论何时查出，本人愿意承担由此造成的一切后果（取消学籍、追回各种录\n\n取或学习证件、所交各项费用一律不予退还等）"
				+ "和全部责任。";
		paragraph.add(new Paragraph(promiseContent + "\n", font9));
		paragraph
				.add(new Paragraph("        本人已核对上述信息无误" + "\n\n", font12Bold));
		paragraph.add(new Chunk("                            考生签名： ", font10));
		paragraph.add(new Chunk("                    .", FontFactory.getFont(
				FontFactory.HELVETICA, 12, Font.UNDERLINE)));
		Paragraph pa = new Paragraph(
				"\n\n                                                                                                                               年         月         日",
				font10);
		paragraph.add(pa);
		pCell = new PdfPCell(paragraph);
		pCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pTable.addCell(pCell);

		try {
			document.add(pTable);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			
		}

		Chunk chunk = new Chunk("                            教育部学历认证网址： ",
				font12Bold);
		try {
			document.add(chunk);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			
		}
		chunk = new Chunk(" http://www.chsi.com.cn/xlcx", FontFactory.getFont(
				FontFactory.HELVETICA, 12, Font.UNDERLINE));
		try {
			document.add(chunk);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			
		}

		document.close();

		DataOutput output;
		try {
			output = new DataOutputStream(response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		byte[] bytes = baos.toByteArray();
		response.setContentType("application/pdf");
		response.reset();
		response.setContentLength(bytes.length);

		session.setAttribute("bytes", bytes);

		return "pdfprinter";
	}

	/**
	 * 转向修改密码
	 * 
	 * @return
	 */
	public String turntopasswordEdit() {
		DetachedCriteria dcPeRecStudent = DetachedCriteria
				.forClass(PeRecStudent.class);
		dcPeRecStudent.add(Restrictions.eq("id", this.getStudent().getId()));

		List<PeRecStudent> studentList = new ArrayList();

		try {
			studentList = this.getPeRecStudentService().getList(dcPeRecStudent);
		} catch (Exception e) {
			
		}
		if (studentList.size() > 0) {
			this.setStudent(studentList.get(0));
		}

		return "turntopasswordEdit";
	}

	/**
	 * 修改密码
	 */
	public String passwordedit() {

		DetachedCriteria dcPeRecStudent = DetachedCriteria
				.forClass(PeRecStudent.class);

		dcPeRecStudent.add(Restrictions.eq("id", this.getStudent().getId()));

		List<PeRecStudent> stuList = new ArrayList();

		try {
			stuList = this.getPrRecExamStuService().getList(dcPeRecStudent);
		} catch (Exception e) {
			
		}

		if (stuList.size() > 0) {
			this.setStudent(stuList.get(0));
		}

		if (!this.getStudent().getPassword().equals(this.getPass_old())) {
			this.setOperateresult("输入原密码错误，修改密码失败！");
			return "success";
		}

		this.getStudent().setPassword(this.getPass_word());

		try {
			this.getPeRecStudentService().save(this.getStudent());
		} catch (Exception e) {
			
			this.setOperateresult("修改密码失败！");
		}

		this.setOperateresult("修改密码成功！");

		return "success";
	}

	/**
	 * 录取查询
	 */

	public String lunqu() {
		
		String card_no = this.getCardNo();
		
		if(card_no != null && !"".equals(card_no)){
			card_no = card_no.trim();
		}

		DetachedCriteria dcPeRecStudent = DetachedCriteria
				.forClass(PeRecStudent.class);

		DetachedCriteria dcPrRecPlanMajorSite = dcPeRecStudent.createCriteria(
				"prRecPlanMajorSite", "prRecPlanMajorSite");

		DetachedCriteria dcPrRecPlanMajorEdutype = dcPrRecPlanMajorSite
				.createCriteria("prRecPlanMajorEdutype",
						"prRecPlanMajorEdutype");

		DetachedCriteria dcPeRecruitplan = dcPrRecPlanMajorEdutype
				.createCriteria("peRecruitplan", "peRecruitplan");

		dcPeRecruitplan.createCriteria("peGrade", "peGrade");

		dcPrRecPlanMajorEdutype.createCriteria("peMajor", "peMajor");

		dcPrRecPlanMajorEdutype.createCriteria("peEdutype", "peEdutype");

		dcPeRecStudent.add(Restrictions.eq("cardNo", card_no));

		List<PeRecStudent> stuList = new ArrayList();

		try {
			stuList = this.getPeRecStudentService().getList(dcPeRecStudent);
		} catch (Exception e) {
			
		}

		if (stuList.size() > 0) {
			this.setStudent(stuList.get(0));
			this.setIsexist("1");
		} else {
			this.setIsexist("0"); // 通过证件号码没有查找到该学生时设为0
		}

		DetachedCriteria dcPrRecExamStu = DetachedCriteria
				.forClass(PrRecExamStu.class);

		DetachedCriteria dcStudent = dcPrRecExamStu.createCriteria(
				"peRecStudent", "peRecStudent");
		
		dcPrRecExamStu.add(Restrictions.eq("peRecStudent.cardNo", card_no));

		List<PrRecExamStu> examstuList = new ArrayList();

		try {
			examstuList = this.getPeRecStudentService().getList(dcPrRecExamStu);
		} catch (Exception e) {
			
		}

		if (examstuList.size() > 0) {
			this.setExamstu(examstuList.get(0));
		}
		//对于知金奥鹏的学生，从学籍库中查询学生信息，如果学籍库中存在，也表明其已被录取
		if(this.getIsexist() != null && "0".equals(this.getIsexist())){
			this.setStudent(null);
			DetachedCriteria dcPrStudentInfo = DetachedCriteria.forClass(PrStudentInfo.class);
			dcPrStudentInfo.add(Restrictions.eq("idCard", card_no));
			List studentInfoList = new ArrayList();
			try {
				studentInfoList = this.getPeStudentService().getList(dcPrStudentInfo);
			} catch (Exception e) {
			}
			if(studentInfoList.size() > 0){
				this.setLqcx_stu((PrStudentInfo)studentInfoList.get(0));
				this.setIsexist("1");
			}else{
				this.setIsexist("0");
			}
		}
		
		return "luqusearch";
	}

	/**
	 * 首页账号查询
	 * 
	 * @return
	 */
	public String retrieve() {

		DetachedCriteria dcPrStudentInfo = DetachedCriteria
				.forClass(PrStudentInfo.class);
		dcPrStudentInfo.createCriteria("peStudent", "peStudent")
				.createCriteria("ssoUser", "ssoUser");
		dcPrStudentInfo.add(Restrictions.eq("idCard", this.getCardNo()));
		dcPrStudentInfo.add(Restrictions.eq("peStudent.trueName", this
				.getName()));

		List<PrStudentInfo> studentList = new ArrayList();

		try {
			studentList = this.getPeRecStudentService()
					.getList(dcPrStudentInfo);
		} catch (Exception e) {
			
		}
		if (studentList.size() < 1) {
			this.setPrStudentInfo(null);
			return "retrieve";
		}

		this.setPrStudentInfo(studentList.get(0));
		try {
			if (studentList.get(0).getPeStudent().getGraduationStatus().equals(
					"1")) {
				this.setStudentstatus("2"); // 该生已毕业
			}
			if (studentList.get(0).getPeStudent().getFlagAbortSchool().equals(
					"1")) {
				this.setStudentstatus("1"); // 该生已退学
			}
		} catch (Exception e) {
			
		}

		return "retrieve";
	}

	public PeStudentService getPeStudentService() {
		return peStudentService;
	}

	public void setPeStudentService(PeStudentService peStudentService) {
		this.peStudentService = peStudentService;
	}

	public PrStudentInfo getLqcx_stu() {
		return lqcx_stu;
	}

	public void setLqcx_stu(PrStudentInfo lqcx_stu) {
		this.lqcx_stu = lqcx_stu;
	}

}
