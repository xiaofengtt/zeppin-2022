package com.whaty.platform.entity.web.action.recruit.baoming;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.bean.PrStuMultiMajor;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.recruit.registration.PeRecStudentAddService;
import com.whaty.platform.entity.web.action.first.PeRecStudentBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * 管理员工作室学生报名信息的添加修改操作
 * 
 * @author 李冰
 * 
 */
public class PeRecStudentAddAction extends PeRecStudentBaseAction {
	private PeRecStudent student;
	private String site; // 招生学生注册页面中学习中心选项
	private String edutype; // 招生学生注册页面中报考类别选项
	private String major; // 招生学生注册页面中报考志愿选项
	private String graduateYear;// 毕业年份
	private String graduateMonth;// 毕业月份
	private String workYear;// 工作年份
	private String workMonth;// 工作月份
	private String stuCardId;// 原来的证件号，用于检查修改信息时候是否有改动
	private int canEdit;// 标准位，判断是否可以修改报考志愿等信息,1表示不能修改
	private String noexam;// 是否为考试生
	private String teacher;// 是否有教师资格
	// 上传照片所用字段
	private File upload; // 文件
	private String uploadFileName; // 文件名属性
	private String uploadContentType; // 文件类型属性
	private String savePath; // 文件存储位置
	
	private PeRecStudentAddService peRecStudentAddService;
	/**
	 * 跳转到单个添加页面 首先判断当前时间是否在报名时间范围内
	 * 
	 * @return
	 */
	public String turntostudentsingleadd() {
		
		/**
		 * 取得管理员类型.总站管理员不限制报名时间
		 */
		UserSession us = (UserSession)ServletActionContext
			.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = "";//保存管理员类型
		if (us!=null) {
			userLoginType = us.getUserLoginType();
		}
		if(userLoginType.equals("3")){
			return "singleAdd";
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecruitplan.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		List<PeRecruitplan> list;
		try {
			list = this.getGeneralService().getList(dc);
			if (list.size() > 0) {
				Date start = list.get(0).getRegisterStartDate();
				Date end = list.get(0).getRegisterEndDate();
				Date date = new Date();
				if (date.after(start) && Const.compareDate(date, end)){
					return "singleAdd";
				}else{
					start = list.get(0).getSiteImportStartDate();
					end = list.get(0).getSiteImportEndDate();
					if (date.after(start) && Const.compareDate(date, end))
						return "singleAdd";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setMsg("当前时间不在报名日期范围内,无法报名！");
		return "msg";
	}

	/**
	 * 跳转到批量添加页面 首先判断当前时间是否在报名时间范围内
	 * 
	 * @return
	 */
	public String batch() {
		
		/**
		 * 取得管理员类型.总站管理员不限制报名时间
		 */
		UserSession us = (UserSession)ServletActionContext
			.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = "";//保存管理员类型
		if (us!=null) {
			userLoginType = us.getUserLoginType();
		}
		if(userLoginType.equals("3")){
			return "batch";
		}
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecruitplan.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		List<PeRecruitplan> list;
		try {
			list = this.getGeneralService().getList(dc);
			if (list.size() > 0) {
				Date start = list.get(0).getRegisterStartDate();
				Date end = list.get(0).getRegisterEndDate();
				Date date = new Date();
				if (date.after(start) && Const.compareDate(date, end)){
					return "batch";
				}else{
					start = list.get(0).getSiteImportStartDate();
					end = list.get(0).getSiteImportEndDate();
					if (date.after(start) && Const.compareDate(date, end))
						return "batch";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setMsg("当前时间不在报名日期范围内,无法报名！");
		return "msg";

	}
	
	public PeRecStudentAddService getPeRecStudentAddService() {
		return peRecStudentAddService;
	}

	public void setPeRecStudentAddService(
			PeRecStudentAddService peRecStudentAddService) {
		this.peRecStudentAddService = peRecStudentAddService;
	}

	public String uploadStudent(){
		int count = 0;
		try {
			count = this.getPeRecStudentAddService().saveUploadStudent(this.getUpload());
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			this.setTogo("back");
			return "msg";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		this.setTogo("back");
		return "msg";
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
		this.servletPath = "/entity/recruit/addPeRecStudent";
	}

	/**
	 * 单个学生报名信息录入
	 * 
	 * @return
	 */
	public String addStudent() {
		this.setTogo("back");
		String name = this.getStudent().getName();
		String minzu = this.getStudent().getFolk();
		// 验证证件号码是否已经使用
		DetachedCriteria dcPeRecStudent = DetachedCriteria
				.forClass(PeRecStudent.class);
		DetachedCriteria dcPeRecruitplan1 = dcPeRecStudent.createCriteria(
				"prRecPlanMajorSite", "prRecPlanMajorSite").createCriteria(
				"prRecPlanMajorEdutype", "prRecPlanMajorEdutype")
				.createCriteria("peRecruitplan", "peRecruitplan");
		dcPeRecruitplan1.add(Restrictions.eq("flagActive", "1"));
		dcPeRecStudent.add(Restrictions.eq("cardNo", this.getStudent()
				.getCardNo()));
		List<PeRecStudent> peRecStudentSiteList = new ArrayList();
		try {
			peRecStudentSiteList = this.getGeneralService().getList(
					dcPeRecStudent);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}

		if (peRecStudentSiteList.size() > 0) {
			this.setMsg("该证件号在当前招生批次下已经报名，请不要重复报名！");
			return "msg";
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
			this.setMsg("学习中心、层次和专业选择错误！"); // 学习中心、层次和专业选择错误
			return "msg";
		}

		this.getStudent().setPrRecPlanMajorSite(prRecPlanMajorSiteList.get(0));
		
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
			+ "     and info.card_no = '"+this.getStudent().getCardNo()+"' 	"	;
		List list = null;
		try {
			 list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if(list!=null&&list.size()>0){
			this.setMsg("添加失败！"+this.getEdutype()+this.getMajor()+"，已经有相同的证件号码存在。"); // 同一层次专业下的证件号码不能重复
			return "msg";
		}
		
		//设置专业备注（为原专业）
		this.getStudent().setEnumConstByFlagMajorType(this.getMyListService().getEnumConstByNamespaceCode("FlagMajorType", "0"));
		
		//判断是否为跨专业
		if(this.getEdutype().indexOf("本")>0){
			String major = this.getMajor();
			String oldMajor = this.getStudent().getGraduateMajor();
			if(major!=null && oldMajor !=null && major.length()>0 && oldMajor.length()>0){
				if(!this.checkMajorType(major, oldMajor)){
					this.getStudent().setEnumConstByFlagMajorType(
							this.getMyListService().getEnumConstByNamespaceCode("FlagMajorType", "1"));
				}
			}
		}
		/*
		 * 设置学生的报名号（原准考证号，改用报名号代替。）
		 */
		String archieveId = this.getStudent().getPrRecPlanMajorSite().getPeSite().getCode() + this.getRecSequence();
		this.getStudent().setExamCardNum(archieveId);
		
		
		// 设置学生的招生渠道，
		this.getStudent().setEnumConstByFlagRecChannel(
				this.getMyListService().getEnumConstByNamespaceCode("FlagRecChannel", "0"));
		//设置学生的密码  默认设置成学生的证件号码
		this.getStudent().setPassword(this.getStudent().getCardNo());
		
		// 设置学生的一些状态
		// 设置学生状态为确认交费
		EnumConst flagRecStatus = this.getMyListService()
				.getEnumConstByNamespaceCode("FlagRecStatus", "1");
		this.getStudent().setEnumConstByFlagRecStatus(flagRecStatus);
		// 设置学生学历验证状态 为未审核状态
		EnumConst flagXueliCheck = this.getMyListService()
				.getEnumConstByNamespaceCode("FlagXueliCheck", "0");
		this.getStudent().setEnumConstByFlagXueliCheck(flagXueliCheck);
		// 设置免试考试状态
		EnumConst enumConstByFlagNoexam = this.getMyListService()
				.getEnumConstByNamespaceCode("FlagNoexam", this.getNoexam());
		this.getStudent().setEnumConstByFlagNoexam(enumConstByFlagNoexam);
		// 设置学生的免试审核状态
		if (this.getNoexam().equals("0")) {
			EnumConst flagNoexamStatus = this.getMyListService()
					.getEnumConstByNamespaceCode("FlagNoexamStatus", "3");
			this.getStudent().setEnumConstByFlagNoexamStatus(flagNoexamStatus);
		} else if (this.getNoexam().equals("1")) {
			EnumConst flagNoexamStatus = this.getMyListService()
					.getEnumConstByNamespaceCode("FlagNoexamStatus", "2");
			this.getStudent().setEnumConstByFlagNoexamStatus(flagNoexamStatus);
		}
		// 设置录取状态为等待录取
		EnumConst enumConstByFlagMatriculate = this.getMyListService()
				.getEnumConstByNamespaceCode("FlagMatriculate", "0");
		this.getStudent().setEnumConstByFlagMatriculate(
				enumConstByFlagMatriculate);
		// 设置学生的教师资格
		EnumConst enumConstByFlagTeacher = this.getMyListService()
				.getEnumConstByNamespaceCode("FlagTeacher", this.getTeacher());
		this.getStudent().setEnumConstByFlagTeacher(enumConstByFlagTeacher);
		// 设置教师资格审核状态
		if (this.getTeacher().equals("1")) {
			EnumConst enumConstByFlagTeacherStatus = this.getMyListService()
					.getEnumConstByNamespaceCode("FlagTeacherStatus", "2");
			this.getStudent().setEnumConstByFlagTeacherStatus(
					enumConstByFlagTeacherStatus);
		} else {
			EnumConst enumConstByFlagTeacherStatus = this.getMyListService()
					.getEnumConstByNamespaceCode("FlagTeacherStatus", "3");
			this.getStudent().setEnumConstByFlagTeacherStatus(
					enumConstByFlagTeacherStatus);
		}
		// 生成毕业时间
		if (this.getGraduateYear() != null
				&& this.getGraduateYear().length() > 0
				&& this.getGraduateMonth() != null
				&& this.getGraduateMonth().length() > 0) {
			String graduateDate = this.getGraduateYear() + "年"
					+ this.getGraduateMonth() + "月";
			this.getStudent().setGraduateDate(graduateDate);
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
			this.getStudent().setWorkBegindate(workBegindate);
		// 照片上传部分
		if (this.getUpload() != null) {
			this.setMsg("照片上传失败！");
			if (this.getUploadFileName().toLowerCase().endsWith(".bmp")
					|| this.getUploadFileName().toLowerCase().endsWith(".jpg")
					|| this.getUploadFileName().toLowerCase().endsWith(".gif")) {
				String str = this.getUploadFileName().substring(
						this.getUploadFileName().length() - 4);
				String photoname = this.getStudent().getCardNo() + str;
				String photoLink = this.getSavePath() + "/" + photoname;
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
					this.setMsg("照片上传成功！");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (this.getMsg().equals("照片上传成功！")) {
					this.getStudent().setPhotoLink(savePath + "/" + photoname);
				}
			}
		}
		/**
		 * 学生信息添加
		 */
		PeRecStudent peRecStudent = new PeRecStudent();
		try {
			peRecStudent = (PeRecStudent) this.getGeneralService().save(
					this.getStudent());
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("学生报名失败！");
			return "msg";
		}
		if (this.getMsg() != null && this.getMsg().equals("照片上传失败！")) {
			this.setMsg("学生报名成功！" + this.getMsg());
		} else {
			this.setMsg("学生报名成功！");
		}
		return "msg";
	}

	/**
	 * 取得学生信息，转向修改信息页面
	 * 
	 * @return
	 */
	public String toEditStudent() {
		
		/**
		 * 取得管理员类型
		 */
		UserSession us = (UserSession)ServletActionContext
		.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = "";//保存管理员类型
		if (us!=null) {
			userLoginType = us.getUserLoginType();
		}
		/**
		 * 学生信息（包括学习中心、层次和专业信息）
		 */
		DetachedCriteria dcPeRecStudent = DetachedCriteria
				.forClass(PeRecStudent.class);

		DetachedCriteria dcPrRecPlanMajorSite = dcPeRecStudent.createCriteria(
				"prRecPlanMajorSite", "prRecPlanMajorSite");
		DetachedCriteria dcPeSite = dcPrRecPlanMajorSite.createCriteria(
				"peSite", "peSite");
		DetachedCriteria dcPrRecPlanMajorEdutype = dcPrRecPlanMajorSite
				.createCriteria("prRecPlanMajorEdutype",
						"prRecPlanMajorEdutype");
		DetachedCriteria dcPeRecruitplan = dcPrRecPlanMajorEdutype
				.createCriteria("peRecruitplan", "peRecruitplan");
		dcPrRecPlanMajorEdutype.createCriteria("peEdutype", "peEdutype");
		dcPrRecPlanMajorEdutype.createCriteria("peMajor", "peMajor");
//		dcPeRecruitplan.add(Restrictions.eq("flagActive", "1"));
		dcPeRecStudent.add(Restrictions.eq("id", this.getBean().getId()));
		List<PeRecStudent> studentList;
		Date registerStartDate;// 报名开始时间
		Date registerEndDate;// 报名结束时间
		Date siteEditStartDate;// 分站修改开始时间
		Date siteEditEndDate;// 分站修改结束时间
		try {
			studentList = this.getGeneralService().getList(dcPeRecStudent);
			if (studentList.get(0).getPrRecPlanMajorSite().getPrRecPlanMajorEdutype().getPeRecruitplan().getFlagActive().equals("1")) {
				this.setStudent(studentList.get(0));
				/**
				 * 取得各个时间段，并判断当前时间是否可以进行修改操作
				 */
				registerStartDate = studentList.get(0).getPrRecPlanMajorSite()
						.getPrRecPlanMajorEdutype().getPeRecruitplan()
						.getRegisterStartDate();
				registerEndDate = studentList.get(0).getPrRecPlanMajorSite()
						.getPrRecPlanMajorEdutype().getPeRecruitplan()
						.getRegisterEndDate();
				siteEditStartDate = studentList.get(0).getPrRecPlanMajorSite()
						.getPrRecPlanMajorEdutype().getPeRecruitplan()
						.getSiteEditStartDate();
				siteEditEndDate = studentList.get(0).getPrRecPlanMajorSite()
						.getPrRecPlanMajorEdutype().getPeRecruitplan()
						.getSiteEditEndDate();

				Date nowDate = new Date();
				boolean registerDate = false;
				boolean siteEditDate = false;
				if (nowDate.after(registerStartDate)
						&& Const.compareDate(nowDate, registerEndDate))
					registerDate = true;
				if (nowDate.after(siteEditStartDate)
						&& Const.compareDate(nowDate, siteEditEndDate))
					siteEditDate = true;
				
					if (!registerDate && !siteEditDate) {
						if(!userLoginType.equals("3")){
						this.setMsg("不在信息修改时间之内！无法修改学生信息！");
						return "msg";
						}else {
							this.setCanEdit(1);
						}
					}
			
				/**
				 * 判断学生状态为已审核的学生不能再修改基本信息
				 */
				if(!userLoginType.equals("3")){
					if (studentList.get(0).getEnumConstByFlagRecStatus().getCode().equals("3")) {
						this.setMsg("不能修改已审核状态的学生信息！");
						return "msg";					
					}
				}
				if (!registerDate && siteEditDate)
					this.setCanEdit(1);
				if (studentList.get(0).getGraduateDate() != null
						&& studentList.get(0).getGraduateDate().length() > 4) {
					String date = studentList.get(0).getGraduateDate();
					this.setGraduateYear(date.substring(0, 4));
					if (date.length() > 5)
						this.setGraduateMonth(date.substring(5, date.length()-1));
				}
				if (studentList.get(0).getWorkBegindate() != null
						&& studentList.get(0).getWorkBegindate().length() > 4) {
					String date = studentList.get(0).getWorkBegindate();
					this.setWorkYear(date.substring(0, 4));
					if (date.length() > 5) {
						this.setWorkMonth(date.substring(5, date.length()-1));
					}

				}
				this.setNoexam(studentList.get(0).getEnumConstByFlagNoexam().getCode());
				this.setTeacher(studentList.get(0).getEnumConstByFlagTeacher().getCode());


			} else {
				//总站管理员可以修改基本信息
				if(userLoginType.equals("3")){
					this.setCanEdit(1);
					this.setStudent(studentList.get(0));
					if (studentList.get(0).getGraduateDate() != null
							&& studentList.get(0).getGraduateDate().length() > 4) {
						String date = studentList.get(0).getGraduateDate();
						this.setGraduateYear(date.substring(0, 4));
						if (date.length() > 5)
							this.setGraduateMonth(date.substring(5, date.length()-1));
					}
					if (studentList.get(0).getWorkBegindate() != null
							&& studentList.get(0).getWorkBegindate().length() > 4) {
						String date = studentList.get(0).getWorkBegindate();
						this.setWorkYear(date.substring(0, 4));
						if (date.length() > 5) {
							this.setWorkMonth(date.substring(5, date.length()-1));
						}

					}
					this.setNoexam(studentList.get(0).getEnumConstByFlagNoexam().getCode());
					this.setTeacher(studentList.get(0).getEnumConstByFlagTeacher().getCode());

				}else{
				this.setMsg("学生信息不在活动招生批次内，无法修改！");
				return "msg";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "edit";
	}

	public String editStudent() {
		String name = this.getStudent().getName();
		String minzu = this.getStudent().getFolk();
		/**
		 * 判断证件号码是否有改动，如果有则进行数据库验证该号码是否已经存在
		 */
		if (!this.getStuCardId().equals(this.getStudent().getCardNo())) {
			// 验证证件号码是否已经使用
			DetachedCriteria dcPeRecStudent = DetachedCriteria
					.forClass(PeRecStudent.class);
			DetachedCriteria dcPeRecruitplan1 = dcPeRecStudent.createCriteria(
					"prRecPlanMajorSite", "prRecPlanMajorSite").createCriteria(
					"prRecPlanMajorEdutype", "prRecPlanMajorEdutype")
					.createCriteria("peRecruitplan", "peRecruitplan");
			dcPeRecruitplan1.add(Restrictions.eq("flagActive", "1"));
			dcPeRecStudent.add(Restrictions.eq("cardNo", this.getStudent()
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
				return "msg";
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
				return "msg";
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
			return "msg";
		}

		this.getStudent().setPrRecPlanMajorSite(prRecPlanMajorSiteList.get(0));

		// 生成毕业时间
		if (this.getGraduateYear() != null
				&& this.getGraduateYear().length() > 0
				&& this.getGraduateMonth() != null
				&& this.getGraduateMonth().length() > 0) {
			String graduateDate = this.getGraduateYear() + "年"
					+ this.getGraduateMonth() + "月";
			this.getStudent().setGraduateDate(graduateDate);
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
			this.getStudent().setWorkBegindate(workBegindate);
		/**
		 * 学生信息修改
		 */
		try {
			// 取出原学生报名信息，并将相应属性根据页面提交进行设置
			PeRecStudent peRecStudent = this.getGeneralService().getById(
					this.getStudent().getId());

			//设置专业备注（为原专业）
			this.getStudent().setEnumConstByFlagMajorType(this.getMyListService().getEnumConstByNamespaceCode("FlagMajorType", "0"));
			
			//判断是否为跨专业
			if(this.getEdutype().indexOf("本")>0){
				String major = this.getMajor();
				String oldMajor = this.getStudent().getGraduateMajor();
				if(major!=null && oldMajor !=null && major.length()>0 && oldMajor.length()>0){
					if(!this.checkMajorType(major, oldMajor)){
						this.getStudent().setEnumConstByFlagMajorType(
								this.getMyListService().getEnumConstByNamespaceCode("FlagMajorType", "1"));
					}
				}
			}
			if (!this.getNoexam().equals(peRecStudent.getEnumConstByFlagNoexam().getCode())) {
				// 设置免试考试状态
				EnumConst enumConstByFlagNoexam = this.getMyListService()
						.getEnumConstByNamespaceCode("FlagNoexam", this.getNoexam());
				this.getStudent().setEnumConstByFlagNoexam(enumConstByFlagNoexam);
				// 设置学生的免试审核状态
				if (this.getNoexam().equals("0")) {
					EnumConst flagNoexamStatus = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagNoexamStatus", "3");
					this.getStudent().setEnumConstByFlagNoexamStatus(flagNoexamStatus);
				} else if (this.getNoexam().equals("1")) {
					EnumConst flagNoexamStatus = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagNoexamStatus", "2");
					this.getStudent().setEnumConstByFlagNoexamStatus(flagNoexamStatus);
				}				
			}
			if (!this.getTeacher().equals(peRecStudent.getEnumConstByFlagTeacher().getCode())) {
				// 设置学生的教师资格
				EnumConst enumConstByFlagTeacher = this.getMyListService()
						.getEnumConstByNamespaceCode("FlagTeacher", this.getTeacher());
				this.getStudent().setEnumConstByFlagTeacher(enumConstByFlagTeacher);
				// 设置教师资格审核状态
				if (this.getTeacher().equals("1")) {
					EnumConst enumConstByFlagTeacherStatus = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagTeacherStatus", "2");
					this.getStudent().setEnumConstByFlagTeacherStatus(
							enumConstByFlagTeacherStatus);
				} else {
					EnumConst enumConstByFlagTeacherStatus = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagTeacherStatus", "3");
					this.getStudent().setEnumConstByFlagTeacherStatus(
							enumConstByFlagTeacherStatus);
				}				
			}
			peRecStudent = (PeRecStudent) setSubIds(peRecStudent, this
					.getStudent());
			this.getGeneralService().save(peRecStudent);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMsg("学生信息修改失败！");
			return "msg";
		}
		this.setMsg("学生信息修改成功！");
		return "msg";
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
	public void setBean(PeRecStudent instance) {
		super.superSetBean(instance);

	}

	public PeRecStudent getBean() {
		return super.superGetBean();
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

	public String getStuCardId() {
		return stuCardId;
	}

	public void setStuCardId(String stuCardId) {
		this.stuCardId = stuCardId;
	}

	public int getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(int canEdit) {
		this.canEdit = canEdit;
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
}
