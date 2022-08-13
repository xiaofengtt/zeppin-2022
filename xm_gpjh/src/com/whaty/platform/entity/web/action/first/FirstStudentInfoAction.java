package com.whaty.platform.entity.web.action.first;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrRecExamStuCourse;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.bean.PrStuMultiMajor;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class FirstStudentInfoAction extends MyBaseAction { 
	private PeRecStudent student;
	private String noexam;// 是否为考试生
	private String teacher;// 是否有教师资格
	private String graduateYear;// 毕业年份
	private String graduateMonth;// 毕业月份	
	private String workYear;// 工作年份 
	private String workMonth;// 工作月份
	private String site; // 招生学生注册页面中学习中心选项
	private String edutype; // 招生学生注册页面中报考类别选项
	private String major; // 招生学生注册页面中报考志愿选项
	private String stuCardId;// 原来的证件号，用于检查修改信息时候是否有改动
	private String title;//结果页面标题


	/**
	 * 转向功能列表页面
	 * @return
	 */
	public String turnToList(){
		this.setBean(this.getStudent());
		String cardNo = this.getBean().getCardNo();
		String pwd = this.getBean().getPassword();
		if (cardNo!=null&&cardNo.trim().length()>0){
			DetachedCriteria dc = DetachedCriteria.forClass(PeRecStudent.class);
			
			dc.add(Restrictions.eq("cardNo", cardNo));
			if(pwd!=null&&pwd.length()>0){
				dc.add(Restrictions.eq("password", pwd));
			}
			
			List<PeRecStudent> studentList = null;
			try {
				studentList = this.getGeneralService().getList(dc);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			if(studentList==null||studentList.isEmpty()){
				this.setMsg("没有查到相关的信息！可能是证件号码或者密码错误！");
				return "result";
			} else if(studentList.size()>1){
				this.setBean(studentList.get(0));
				//如果该号码有多条记录，则取出当前招生批次数据
				for (PeRecStudent peRecStudent : studentList) {
					if(peRecStudent.getPrRecPlanMajorSite().getPrRecPlanMajorEdutype().getPeRecruitplan().getFlagActive().equals("1")){
						this.setBean(peRecStudent);
					}
				}
			} else {
				this.setBean(studentList.get(0));
			}
		}
		ActionContext.getContext().getSession().put("PeRecStudentId",
				this.getBean().getId());
		return "infoList";
	}
	
	/**
	 * 查看报名信息
	 * @return
	 */
	public String infoDetail(){
		if(ActionContext.getContext().getSession().get("PeRecStudentId")==null){
			this.setMsg("登陆超时，请重新登陆。");
			return "result";
		}
		this.studentInfo();
		return "information";
	}
	
	/**
	 * 打印准考证
	 * @return
	 */
	public String print(){
		this.setBean(this.getStudent());
		if(ActionContext.getContext().getSession().get("PeRecStudentId")==null){
			this.setMsg("登陆超时，请重新登陆。");
			return "result";
		}
		this.studentInfo();
		PeRecStudent recStudent =  this.getBean();
		if(recStudent==null){
			this.setMsg("没有查到相关信息！");
			return "result";
		}
		if(!recStudent.getPrRecPlanMajorSite().getPrRecPlanMajorEdutype().getPeRecruitplan().getId()
				.equals(this.activePlan().getId())){
			this.setMsg("不在当前活动招生批次！");
			return "result";
		}		
		if(recStudent.getExamCardNum()==null||recStudent.getPeRecRoom()==null){
			this.setMsg("没有准考证信息！");
			return "result";
		}
		
		return "print";
	}

	/**
	 * 转向修改信息页面
	 * @return
	 */
	public String turnToEdit(){
		this.setBean(this.getStudent());
		if(ActionContext.getContext().getSession().get("PeRecStudentId")==null){
			this.setMsg("登陆超时，请重新登陆。");
			return "result";
		}
		this.studentInfo();
		PeRecStudent recStudent = this.getBean();
		if(recStudent==null){
			this.setMsg("没有查到相关信息！");
			return "result";
		}
		if(!recStudent.getPrRecPlanMajorSite().getPrRecPlanMajorEdutype().getPeRecruitplan().getId()
				.equals(this.activePlan().getId())){
			this.setMsg("不在当前活动招生批次！");
			return "result";
		}
		
		PeRecruitplan plan = this.activePlan();
		Date start = plan.getRegisterStartDate();
		Date end = plan.getRegisterEndDate();
		Date now = new Date();
		if(!(now.after(start)&&Const.compareDate(now, end))){
			this.setMsg("只能在报名时间段内修改信息！");
			return "result";
		}
		if(recStudent.getEnumConstByFlagRecStatus().getCode().equals("3")){
			this.setMsg("您的信息已经审核通过，无法修改！");
			return "result";
		}
		return "edit";
	}
	
	/**
	 * 查看录取情况
	 * @return
	 */
	public String recruitInfo(){
		this.setBean(this.getStudent());
		if(ActionContext.getContext().getSession().get("PeRecStudentId")==null){
			this.setMsg("登陆超时，请重新登陆。");
			return "result";
		}
		this.studentInfo();
		PeRecStudent recStudent = this.getBean();
		if(recStudent==null){
			this.setMsg("没有查到相关信息！");
			return "result";
		}
		this.setTitle("录取情况查询");
		String str = recStudent.getEnumConstByFlagMatriculate().getCode();
		if(str.equals("0")){
			this.setMsg("您的录取状况是：<br/>等待录取");
		} else if(str.equals("1")){
			this.setMsg("您的录取状况是：<br/>已通过录取");
		} else if(str.equals("2")){
			this.setMsg("您的录取状况是：<br/>未通过录取");
		}
		
		return "result";
	}
	
	/**
	 * 查询学号
	 * @return
	 */
	public String seeRegNo(){
		this.setBean(this.getStudent());
		if(ActionContext.getContext().getSession().get("PeRecStudentId")==null){
			this.setMsg("登陆超时，请重新登陆。");
			return "result";
		}
		this.studentInfo();
		PeRecStudent recStudent = this.getBean();
		if(recStudent==null){
			this.setMsg("没有查到相关信息！");
			return "result";
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.add(Restrictions.eq("peRecStudent", recStudent));
		List<PeStudent> list = null;
		try {
			list=this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(list==null||list.isEmpty()){
			this.setMsg("您未注册在籍，没有学号！");
			return "result";
		}
		this.setTitle("学号查询");
		if(!list.get(0).getEnumConstByFlagStudentStatus().getCode().equals("4")){
			this.setMsg("您未注册在籍，没有学号！");
			return "result";
		}
		String id = list.get(0).getRegNo();
		this.setMsg("您的学号是："+id);
		return "result";
	}
	/**
	 * 取得学生信息
	 * 
	 * @return
	 */
	public void studentInfo() {
		this.setBean(this.getStudent());
			PeRecStudent recStudent = null;
			try {
				recStudent = (PeRecStudent)this.getGeneralService()
					.getById(ActionContext.getContext().getSession().get("PeRecStudentId").toString());
			} catch (EntityException e) {
				e.printStackTrace();
				return ;
			}
			if(recStudent==null){
				return ;
			}
				this.setBean(recStudent);
				this.setStudent(recStudent);
				if (recStudent.getGraduateDate() != null
						&& recStudent.getGraduateDate().length() > 4) {
					String date = recStudent.getGraduateDate();
					this.setGraduateYear(date.substring(0, 4));
					if (date.length() > 5)
						this.setGraduateMonth(date.substring(5, date.length()-1));
				}
				if (recStudent.getWorkBegindate() != null
						&& recStudent.getWorkBegindate().length() > 4) {
					String date = recStudent.getWorkBegindate();
					this.setWorkYear(date.substring(0, 4));
					if (date.length() > 5) {
						this.setWorkMonth(date.substring(5, date.length()-1));
					}

				}
				this.setNoexam(recStudent.getEnumConstByFlagNoexam().getCode());
				this.setTeacher(recStudent.getEnumConstByFlagTeacher().getCode());
	}	
	
	/**
	 * 取得活动的招生批次
	 * @return
	 */
	private PeRecruitplan activePlan(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecruitplan.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		List<PeRecruitplan> list=null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(list!=null&&list.size()>0){
			return list.get(0);
		} else {
			return null;
		}
		
	}
	/**
	 * 修改报名信息
	 * @return
	 */
	public String editInfo() {
		this.setBean(this.getStudent());
		if(ActionContext.getContext().getSession().get("PeRecStudentId")==null){
			this.setMsg("登陆超时，请重新登陆。");
			return "result";
		}
		String name = this.getBean().getName();
		String minzu = this.getBean().getFolk();
		/**
		 * 判断证件号码是否有改动，如果有则进行数据库验证该号码是否已经存在
		 */
		if (!this.getStuCardId().equals(this.getBean().getCardNo())) {
			// 验证证件号码是否已经使用
			DetachedCriteria dcPeRecStudent = DetachedCriteria
					.forClass(PeRecStudent.class);
			DetachedCriteria dcPeRecruitplan1 = dcPeRecStudent.createCriteria(
					"prRecPlanMajorSite", "prRecPlanMajorSite").createCriteria(
					"prRecPlanMajorEdutype", "prRecPlanMajorEdutype")
					.createCriteria("peRecruitplan", "peRecruitplan");
			dcPeRecruitplan1.add(Restrictions.eq("flagActive", "1"));
			dcPeRecStudent.add(Restrictions.eq("cardNo", this.getBean()
					.getCardNo()));
			List<PeRecStudent> peRecStudentSiteList = new ArrayList();
			try {
				peRecStudentSiteList = this.getGeneralService().getList(
						dcPeRecStudent);
			} catch (EntityException e1) {
				e1.printStackTrace();
			}

			if (peRecStudentSiteList.size() > 0) {
				this.setMsg("修改失败！该证件号已经被其他学生使用！");
				return "result";
			}

			 // 同一层次专业下的证件号码不能重复
			String sql="  select student.id  					"	
				+ "    from pe_student      student,                  "
				+ "         pr_student_info info,                     "
				+ "         pe_major        major,                    "
				+ "         pe_edutype      edutype                   "
				+ "   where student.fk_student_info_id = info.id      "
				+ "     and student.fk_major_id = major.id            "
				+ "     and student.fk_edutype_id = edutype.id        "
				+ "     and major.name = '"+this.getMajor()+"'                  "
				+ "     and edutype.name = '"+this.getEdutype()+"'              "
				+ "     and info.card_no = '"+this.getBean().getCardNo()+"' 	"	;
			List list = null;
			try {
				 list = this.getGeneralService().getBySQL(sql);
			} catch (EntityException e1) {
				e1.printStackTrace();
			}
			if(list!=null&&list.size()>0){
				this.setMsg("修改失败！"+this.getEdutype()+this.getMajor()+"，已经有相同的证件号码存在。"); // 同一层次专业下的证件号码不能重复
				return "result";
			}
		}

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
			prRecPlanMajorSiteList = this.getGeneralService().getList(
					dcPrRecPlanMajorSite);
		} catch (EntityException e) {
			e.printStackTrace();
		}

		if (prRecPlanMajorSiteList.size() < 1) {
			this.setMsg("学习中心、层次和专业选择错误！不在当前活动招生批次下！"); // 学习中心、层次和专业选择错误
			return "result";
		}

		this.getBean().setPrRecPlanMajorSite(prRecPlanMajorSiteList.get(0));

		// 生成毕业时间
		if (this.getGraduateYear() != null
				&& this.getGraduateYear().length() > 0
				&& this.getGraduateMonth() != null
				&& this.getGraduateMonth().length() > 0) {
			String graduateDate = this.getGraduateYear() + "年"
					+ this.getGraduateMonth() + "月";
			this.getBean().setGraduateDate(graduateDate);
		}
		// 设置工作时间
		String workBegindate = "";
		if (this.getWorkYear() != null && this.getWorkYear().length() > 0) {
			workBegindate += this.getWorkYear() + "年";
		}
		if (this.getWorkMonth() != null && this.getWorkMonth().length() > 0) {
			workBegindate += this.getWorkMonth() + "月";
		}
		if (workBegindate.length() > 0)
			this.getBean().setWorkBegindate(workBegindate);
		/**
		 * 学生信息修改
		 */
		try {
			// 取出原学生报名信息，并将相应属性根据页面提交进行设置
			PeRecStudent peRecStudent = (PeRecStudent)this.getGeneralService().getById(
					this.getBean().getId());
			//设置专业备注（为原专业）
			this.getBean().setEnumConstByFlagMajorType(this.getMyListService().getEnumConstByNamespaceCode("FlagMajorType", "0"));
			
			//判断是否为跨专业
			if(this.getEdutype().indexOf("本")>0){
				String major = this.getMajor();
				String oldMajor = this.getBean().getGraduateMajor();
				if(major!=null && oldMajor !=null && major.length()>0 && oldMajor.length()>0){
					if(!this.checkMajorType(major, oldMajor)){
						this.getBean().setEnumConstByFlagMajorType(
								this.getMyListService().getEnumConstByNamespaceCode("FlagMajorType", "1"));
					}
				}
			}
			
			if (!this.getNoexam().equals(peRecStudent.getEnumConstByFlagNoexam().getCode())) {
				// 设置免试考试状态
				EnumConst enumConstByFlagNoexam = this.getMyListService()
						.getEnumConstByNamespaceCode("FlagNoexam", this.getNoexam());
				this.getBean().setEnumConstByFlagNoexam(enumConstByFlagNoexam);
				// 设置学生的免试审核状态
				if (this.getNoexam().equals("0")) {
					EnumConst flagNoexamStatus = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagNoexamStatus", "3");
					this.getBean().setEnumConstByFlagNoexamStatus(flagNoexamStatus);
				} else if (this.getNoexam().equals("1")) {
					EnumConst flagNoexamStatus = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagNoexamStatus", "2");
					this.getBean().setEnumConstByFlagNoexamStatus(flagNoexamStatus);
				}				
			}
			if (!this.getTeacher().equals(peRecStudent.getEnumConstByFlagTeacher().getCode())) {
				// 设置学生的教师资格
				EnumConst enumConstByFlagTeacher = this.getMyListService()
						.getEnumConstByNamespaceCode("FlagTeacher", this.getTeacher());
				this.getBean().setEnumConstByFlagTeacher(enumConstByFlagTeacher);
				// 设置教师资格审核状态
				if (this.getTeacher().equals("1")) {
					EnumConst enumConstByFlagTeacherStatus = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagTeacherStatus", "2");
					this.getBean().setEnumConstByFlagTeacherStatus(
							enumConstByFlagTeacherStatus);
				} else {
					EnumConst enumConstByFlagTeacherStatus = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagTeacherStatus", "3");
					this.getBean().setEnumConstByFlagTeacherStatus(
							enumConstByFlagTeacherStatus);
				}				
			}
			peRecStudent = (PeRecStudent) setSubIds(peRecStudent, this
					.getBean());
			this.getGeneralService().save(peRecStudent);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMsg("学生信息修改失败！");
			return "result";
		}
		this.setMsg("学生信息修改成功！");
		return "result";
	}	
	public List getRecStuPrint(){
		this.setBean(this.getStudent());
		try{
			DetachedCriteria dc = DetachedCriteria.forClass(PeRecStudent.class);
			dc.add(Restrictions.eq("id", this.getBean().getId()));
			return this.getGeneralService().getList(dc);
		}catch(Throwable e){
			e.printStackTrace();
			return null;
		}
	}
	public List getStuCourse(String id){
		try{
			DetachedCriteria dc = DetachedCriteria.forClass(PrRecExamStuCourse.class);
			dc.add(Restrictions.eq("peRecStudent.id", id));
			return this.getGeneralService().getList(dc);
		}catch(Throwable e){
			e.printStackTrace();
			return null;
		}
		
	}	
	
	/**
	 * 检查是否属于跨专业
	 * @param major 所报专业
	 * @param oldMajor 原专业
	 * @return true 原专业  false 跨专业
	 */
	private boolean checkMajorType(String major, String oldMajor){
		DetachedCriteria dcPrStuMultiMajor = DetachedCriteria.forClass(PrStuMultiMajor.class) ;
		dcPrStuMultiMajor.createCriteria("peMajor", "peMajor").add(Restrictions.eq("name", major));
		List list = null;
		try {
			list = this.getGeneralService().getList(dcPrStuMultiMajor);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(list==null||list.isEmpty()){
			return true;
		}
		dcPrStuMultiMajor.add(Restrictions.eq("oldMajor", oldMajor));
		try {
			list = this.getGeneralService().getList(dcPrStuMultiMajor);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(list==null||list.isEmpty()){
			return false;
		}
		return true;
	}
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}
	@Override
	public void setEntityClass() {
		this.entityClass = PeRecStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/first/firstStudentInfo";
	}
	public void setBean(PeRecStudent instance) {
		super.superSetBean(instance);

	}

	public PeRecStudent getBean() {
		return (PeRecStudent) super.superGetBean();
	}
	public String getNoexam() {
		return noexam;
	}

	public void setNoexam(String noexam) {
		this.noexam = noexam;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getGraduateYear() {
		return graduateYear;
	}

	public void setGraduateYear(String graduateYear) {
		this.graduateYear = graduateYear;
	}

	public String getGraduateMonth() {
		return graduateMonth;
	}

	public void setGraduateMonth(String graduateMonth) {
		this.graduateMonth = graduateMonth;
	}

	public String getWorkYear() {
		return workYear;
	}

	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}

	public String getWorkMonth() {
		return workMonth;
	}

	public void setWorkMonth(String workMonth) {
		this.workMonth = workMonth;
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


	public String getStuCardId() {
		return stuCardId;
	}

	public void setStuCardId(String stuCardId) {
		this.stuCardId = stuCardId;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PeRecStudent getStudent() {
		return student;
	}

	public void setStudent(PeRecStudent student) {
		this.student = student;
	}

}
