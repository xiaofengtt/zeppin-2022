package com.whaty.platform.entity.web.action.first;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

/**
 * 首页网上报名处理
 * 
 * @author 李冰
 *  
 */
public class FirstPageStudentRegisterAction extends PeRecStudentBaseAction { 

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
	this.servletPath = "/entity/first/firstRegister";

	}

	private String operateresult; // 学生注册结果
	private PeRecStudent student;
	private String site; // 招生学生注册页面中学习中心选项
	private String edutype; // 招生学生注册页面中报考类别选项
	private String major; // 招生学生注册页面中报考志愿选项
	private String oldMajor;//原专业
	private String graduateYear;// 毕业年份
	private String graduateMonth;// 毕业月份
	private String workYear;// 工作年份
	private String workMonth;// 工作月份
	private String ajaxResult;// 验证报名页面中所输入的证件号码是否已存在
	private String noexam;// 是否为考试生
	private String teacher;// 是否有教师资格
	// 上传照片所用字段
	private File upload; // 文件
	private String uploadFileName; // 文件名属性
	private String uploadContentType; // 文件类型属性
	private String savePath; // 文件存储位置

	public PeRecStudent getStudent() {
		return student;
	}

	public void setStudent(PeRecStudent student) {
		this.student = student;
	}

	/**
	 * 验证证件号码是否已经存在
	 * 
	 * @return
	 */
	public String cardNoExist() {
		String result = "0";
		// 验证证件号码是否已经使用
		DetachedCriteria dcPeRecStudent = DetachedCriteria
				.forClass(PeRecStudent.class);
		DetachedCriteria dcPeRecruitplan1 = dcPeRecStudent.createCriteria(
				"prRecPlanMajorSite", "prRecPlanMajorSite").createCriteria(
				"prRecPlanMajorEdutype", "prRecPlanMajorEdutype")
				.createCriteria("peRecruitplan", "peRecruitplan");
		dcPeRecruitplan1.add(Restrictions.eq("flagActive", "1"));
		if(this.getStudent().getCardNo()!=null&&this.getStudent().getCardNo().length()>0){
			dcPeRecStudent.add(Restrictions.eq("cardNo", this.getStudent().getCardNo()));			
		}else {
			dcPeRecStudent.add(Restrictions.eq("cardNo", this.getStudent().getCardNo()));			
		}

		List<PeRecStudent> peRecStudentSiteList = new ArrayList();
		try {
			peRecStudentSiteList = this.getGeneralService().getList(
					dcPeRecStudent);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}

		if (peRecStudentSiteList.size() > 0) {
//			this.setOperateresult("该证件号在当前招生批次下已经报名，请不要重复报名！");
			result = "1";
		}
		this.setAjaxResult(result);
		return "ajaxcheck";
	}
	
	/**
	 * ajax请求，判断原专业与跨专业
	 * @return
	 */
	public String majorType(){
		String result = "0"; //0 原专业， 1 跨专业
		String major = "";
		String oldMajor = "";
		try {
			major = java.net.URLDecoder.decode(this.getMajor(), "UTF-8");
			oldMajor = java.net.URLDecoder.decode(this.getOldMajor(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if(major.length()>0 && oldMajor.length()>0){
			if(this.checkMajorType(major, oldMajor)){
				result = "0";
			}else {
				result = "1";
			}
		}
		this.setAjaxResult(result);
		return "ajaxcheck";
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

	/**
	 * 转向报名页面
	 * @return
	 */
	public String turnToAdd(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecruitplan.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		List<PeRecruitplan> list;
		try {
			list = this.getGeneralService().getList(dc);
			if (list.size() > 0) {
				Date start = list.get(0).getRegisterStartDate();
				Date end = list.get(0).getRegisterEndDate();
				Date date = new Date();
				if (date.after(start) && Const.compareDate(date, end))
					return "add";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setMsg("当前时间不在报名日期范围内,无法报名！");
		return "result";
	}
	
	/**
	 * 报名学生信息录入
	 */
	public String addStudent() {
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
			this.setOperateresult("该证件号在当前招生批次下已经报名，请不要重复报名！");
			return "fail";
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
			this.setOperateresult("学习中心、层次和专业选择错误！"); // 学习中心、层次和专业选择错误
			return "fail";
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
			this.setOperateresult(this.getEdutype()+this.getMajor()+"，已经有相同的证件号码存在。"); // 同一层次专业下的证件号码不能重复
			return "fail";
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
		/**
		 * 设置学生的报名号（原准考证号，改用报名号代替。）
		 */
		String archieveId = this.getStudent().getPrRecPlanMajorSite().getPeSite().getCode() + this.getRecSequence();
		this.getStudent().setExamCardNum(archieveId);
		
		// 设置学生的招生渠道，
		this.getStudent().setEnumConstByFlagRecChannel(
				this.getMyListService().getEnumConstByNamespaceCode("FlagRecChannel", "0"));

		// 设置学生的一些状态
		// 设置学生状态为未审核
		EnumConst flagRecStatus = this.getMyListService()
				.getEnumConstByNamespaceCode("FlagRecStatus", "0");
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
			this.setOperateresult("学生报名失败！");
			return "fail";
		}
		if (this.getMsg() != null && this.getMsg().equals("照片上传失败！")) {
			this.setOperateresult("学生报名成功！" + this.getMsg());
		} else {
			this.setOperateresult("学生报名成功！");
		}
		return "success";
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

	public String getOperateresult() {
		return operateresult;
	}

	public void setOperateresult(String operateresult) {
		this.operateresult = operateresult;
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

	public String getAjaxResult() {
		return ajaxResult;
	}

	public void setAjaxResult(String ajaxResult) {
		this.ajaxResult = ajaxResult;
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
	
	public void setBean(PeRecStudent instance) {
		super.superSetBean(instance);

	}

	public PeRecStudent getBean() {
		return (PeRecStudent) super.superGetBean();
	}

	public String getOldMajor() {
		return oldMajor;
	}

	public void setOldMajor(String oldMajor) {
		this.oldMajor = oldMajor;
	}
}
