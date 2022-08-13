package com.whaty.platform.entity.service.imp.recruit.registration;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.bean.PrStuMultiMajor;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.recruit.registration.PeRecStudentAddService;
import com.whaty.platform.util.Const;
import com.whaty.util.string.AttributeManage;
import com.whaty.util.string.WhatyAttributeManage;

public class PeRecStudentAddServiceImp implements PeRecStudentAddService {
	private GeneralDao<PeRecStudent> generalDao;

    
    public int saveUploadStudent(File file) throws EntityException {
		StringBuffer msg = new StringBuffer();
		int count = 0;

		Workbook work = null;

		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量添加失败！<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("表格为空！<br/>");
		}
		String temp = "";
		Set<PeRecStudent> studentSet = new HashSet();
		Set<String> cards = new HashSet();
		for (int i = 1; i < rows; i++) {
			PeRecStudent student = new PeRecStudent();
			// *姓名
			temp = sheet.getCell(0, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，姓名为空！<br/>");
				continue;
			}
			student.setName(temp);

			// *性别(男,女)
			temp = sheet.getCell(1, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，性别为空！<br/>");
				continue;
			}
			if (temp.equals("男")||temp.equals("女")){
				student.setGender(temp);
				} else {
					msg.append("第" + (i + 1) + "行数据，性别只能为男/女！<br/>");
					continue;
				}

			// *出生日期（严格按照yyyy-MM-dd填写）
			temp = sheet.getCell(2, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，出生日期为空！<br/>");
				continue;
			}
			try {
				student.setBirthday(this.stringToDate(temp));
			} catch (Exception e) {
				msg.append("第" + (i + 1) + "行数据，出生日期格式有误！<br/>");
				continue;
			}

			// *出生地所在省
			temp = sheet.getCell(3, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，出生地所在省为空！<br/>");
				continue;
			}
			student.setProvince(temp);

			// 出生地所在市
			temp = sheet.getCell(4, i).getContents().trim();
			student.setCity(temp);

			// *婚姻状况(已婚，未婚)
			temp = sheet.getCell(5, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，婚姻状况为空！<br/>");
				continue;
			}
			if (temp.equals("已婚")||temp.equals("未婚")){
				student.setMarriage(temp);
			} else {
					msg.append("第" + (i + 1) + "行数据，婚姻状况只能为已婚/未婚！<br/>");
					continue;
			}


			// *证件类型 (身份证，军官证，护照，港澳台有效证件）
			temp = sheet.getCell(6, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，证件类型为空！<br/>");
				continue;
			}
			if (temp.equals("身份证")||temp.equals("军官证")
					||temp.equals("护照")||temp.equals("港澳台有效证件")){
				student.setCardType(temp);
			} else {
					msg.append("第" + (i + 1) + "行数据，证件类型只能为(身份证，军官证，护照，港澳台有效证件）！<br/>");
					continue;
			}
			

			// *证件号码
			temp = sheet.getCell(7, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，证件号码为空！<br/>");
				continue;
			}
			if (student.getCardType().equals("身份证")) {
				if (!this.checkCardNo(temp)) {
					msg.append("第" + (i + 1) + "行数据，证件号码格式不正确！<br/>");
					continue;
				}
				if(!this.checkCardNoBirthday(temp, sheet.getCell(2, i).getContents().trim())){
					msg.append("第" + (i + 1) + "行数据，身份证号与所填出生日期不一致！<br/>");
					continue;
				}
			}
			if (!this.cardNoIsExist(temp)) {
				msg.append("第" + (i + 1) + "行数据，证件号码在当前招生批次已经存在！<br/>");
				continue;
			}
			if (!cards.add(temp)) {
				msg.append("第" + (i + 1) + "行数据，证件号码与文件中前面的证件号码重复！<br/>");
				continue;
			}
			student.setCardNo(temp);

			// *政治面貌 (参考学生信息单个录入页面)
			temp = sheet.getCell(8, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，政治面貌为空！<br/>");
				continue;
			}
			student.setZzmm(temp);

			// *民族 (参考学生信息单个录入页面)
			temp = sheet.getCell(9, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，民族状况为空！<br/>");
				continue;
			}
			student.setFolk(temp);

			// *文化程度 (小学，初中，高中，中专，职高，大专，高职，本科，硕士，博士)
			temp = sheet.getCell(10, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，文化程度为空！<br/>");
				continue;
			}
			student.setXueli(temp);

			// *户口
			temp = sheet.getCell(11, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，户口为空！<br/>");
				continue;
			}
			student.setRegister(temp);

			// *联系地址
			temp = sheet.getCell(12, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，联系地址为空！<br/>");
				continue;
			}
			student.setAddress(temp);

			// *邮政编码
			temp = sheet.getCell(13, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，邮政编码为空！<br/>");
				continue;
			}
			if (!isZIP(temp)) {
				msg.append("第" + (i + 1) + "行数据，邮政编码格式错误！<br/>");
				continue;
			}
			student.setZip(temp);

			// *E-mail
			temp = sheet.getCell(14, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，E-mail为空！<br/>");
				continue;
			}
			if (!isEmail(temp)) {
				msg.append("第" + (i + 1) + "行数据，E-mail格式错误！<br/>");
				continue;
			}
			student.setEmail(temp);

			// *联系电话
			temp = sheet.getCell(15, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，联系电话为空！<br/>");
				continue;
			}
			if (!isTelephone(temp)) {
				msg.append("第" + (i + 1) + "行数据，联系电话格式错误！<br/>");
				continue;
			}
			student.setPhone(temp);

			// *手机（必须11位）
			temp = sheet.getCell(16, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，手机为空！<br/>");
				continue;
			}
			if(!this.isMobile(temp)){
				msg.append("第" + (i + 1) + "行数据，手机格式错误！<br/>");
				continue;
			}
			student.setMobilephone(temp);

			// *毕业院校
			temp = sheet.getCell(17, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，毕业院校为空！<br/>");
				continue;
			}
			student.setGraduateSchool(temp);

			// *毕业时间（yyyy年MM月）
			temp = sheet.getCell(18, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，毕业时间为空！<br/>");
				continue;
			}
			if (!isYear(temp)) {
				msg.append("第" + (i + 1) + "行数据，毕业时间格式错误！请按照（yyyy年MM月）格式填写。<br/>");
				continue;
			}
			student.setGraduateDate(temp);

			// *毕业证书编号
			temp = sheet.getCell(19, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，毕业证书编号为空！<br/>");
				continue;
			}
			student.setGraduateCode(temp);

			// *就读专业
			temp = sheet.getCell(20, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，就读专业为空！<br/>");
				continue;
			}
			student.setGraduateMajor(temp);

			// *职业
			temp = sheet.getCell(21, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，职业为空！<br/>");
				continue;
			}
			student.setOccupation(temp);

			// 工作单位
			temp = sheet.getCell(22, i).getContents().trim();
			student.setWorkplace(temp);

			// 单位电话
			temp = sheet.getCell(23, i).getContents().trim();
			if (!(temp == null || "".equals(temp))) {
				if (!isTelephone(temp)) {
					msg.append("第" + (i + 1) + "行数据，单位电话格式错误！<br/>");
					continue;
				}
			}
			student.setWorkplacePhone(temp);

			// 单位邮编
			temp = sheet.getCell(24, i).getContents().trim();
			if (!(temp == null || "".equals(temp))) {
				if (!isZIP(temp)) {
					msg.append("第" + (i + 1) + "行数据，单位邮编格式错误！<br/>");
					continue;
				}
			}
			student.setWorkplaceZip(temp);

			// 何时参加工作（ yyyy年MM月）
			temp = sheet.getCell(25, i).getContents().trim();
			if (!(temp == null || "".equals(temp))) {
				if (!isYear(temp)) {
					msg.append("第" + (i + 1) + "行数据，何时参加工作格式错误！请按照（ yyyy年MM月）格式填写。<br/>");
					continue;
				}
			}
			student.setWorkBegindate(temp);

			// *学习中心
			temp = sheet.getCell(26, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，学习中心为空！<br/>");
				continue;
			}
			String site = temp;

			// *报考层次
			temp = sheet.getCell(27, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，报考层次为空！<br/>");
				continue;
			}
			String edutype = temp;

			// *报考专业
			temp = sheet.getCell(28, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，报考专业为空！<br/>");
				continue;
			}
			String major = temp;

//			// *录取省份(参考学生信息单个录入页面）
//			temp = sheet.getCell(29, i).getContents().trim();
//			if (temp == null || "".equals(temp)) {
//				msg.append("第" + (i + 1) + "行数据，录取省份为空！<br/>");
//				continue;
//			}
//			student.setRecProvince(temp);

			// *报考类型 0:考试生 1:免试生
			temp = sheet.getCell(29, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，报考类型为空！<br/>");
				continue;
			}
			String noExam = temp;

			// *是否具有教师资格(0无 1有 2其他专业)
			temp = sheet.getCell(30, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，是否具有教师资格为空！<br/>");
				continue;
			}
			String teacher = temp;

			DetachedCriteria dc = DetachedCriteria
					.forClass(PrRecPlanMajorSite.class);
			DetachedCriteria dcPeSite = dc.createCriteria("peSite", "peSite");
			DetachedCriteria dcPrRecPlanMajorEdutype = dc
					.createCriteria("prRecPlanMajorEdutype", "prRecPlanMajorEdutype");
			DetachedCriteria dcPeMajor = dcPrRecPlanMajorEdutype
					.createCriteria("peMajor", "peMajor");
			DetachedCriteria dcPeEdutype = dcPrRecPlanMajorEdutype
					.createCriteria("peEdutype", "peEdutype");
			DetachedCriteria dcPeRecruitplan = dcPrRecPlanMajorEdutype
					.createCriteria("peRecruitplan", "peRecruitplan");
			dcPeSite.add(Restrictions.eq("name", site));
			dcPeMajor.add(Restrictions.eq("name", major));
			dcPeEdutype.add(Restrictions.eq("name", edutype));
			dcPeRecruitplan.add(Restrictions.eq("flagActive", "1"));
			List<PrRecPlanMajorSite> prRecPlanMajorSiteList = this
					.getGeneralDao().getList(dc);
			if (prRecPlanMajorSiteList.size() == 0) {
				msg.append("第" + (i + 1) + "行数据，当前招生批次下无所填学习中心层次专业关系！<br/>");
				continue;
			}
			student.setPrRecPlanMajorSite(prRecPlanMajorSiteList.get(0));

			 // 同一层次专业下的证件号码不能重复
			String sql="  select student.id  					"	
			+ "    from pe_student      student,                  "
			+ "         pr_student_info info,                     "
			+ "         pe_major        major,                    "
			+ "         pe_edutype      edutype                   "
			+ "   where student.fk_student_info_id = info.id      "
			+ "     and student.fk_major_id = major.id            "
			+ "     and student.fk_edutype_id = edutype.id        "
			+ "     and major.name = '"+major+"'                  "
			+ "     and edutype.name = '"+edutype+"'              "
			+ "     and info.card_no = '"+student.getCardNo()+"' 	"	;
			List list = null;
				 list = this.getGeneralDao().getBySQL(sql);
			if(list!=null&&list.size()>0){
				msg.append("第" + (i + 1) + "行数据，该层次和专业下已经有相同的证件号码！<br/>");
				continue;
			}
			
			// 是否免试生及免试审核状态
			if (noExam.equals("0")) {
				EnumConst enumConstByFlagNoexamStatus = this.getGeneralDao()
						.getEnumConstByNamespaceCode("FlagNoexamStatus", "3");
				student
						.setEnumConstByFlagNoexamStatus(enumConstByFlagNoexamStatus);
			} else if (noExam.equals("1")) {
				EnumConst enumConstByFlagNoexamStatus = this.getGeneralDao()
						.getEnumConstByNamespaceCode("FlagNoexamStatus", "2");
				student.setEnumConstByFlagNoexamStatus(enumConstByFlagNoexamStatus);
			} else {
				msg.append("第" + (i + 1) + "行数据，报考类型错误 ！<br/>");
				continue;
			}
			student.setEnumConstByFlagNoexam(
					this.getGeneralDao().getEnumConstByNamespaceCode("FlagNoexam", noExam));
			
			// 是否具有教师资格及教师资格审核状态
			if (teacher.equals("1")) {
				student.setEnumConstByFlagTeacherStatus(
						this.getGeneralDao().getEnumConstByNamespaceCode("FlagTeacherStatus", "2"));
			} else if (teacher.equals("0")||teacher.equals("2")) {
				student.setEnumConstByFlagTeacherStatus(
						this.getGeneralDao().getEnumConstByNamespaceCode("FlagTeacherStatus", "3"));
			} else {
				msg.append("第" + (i + 1) + "行数据，是否具有教师资格填写错误 ！<br/>");
				continue;
			}
			student.setEnumConstByFlagTeacher(
					this.getGeneralDao().getEnumConstByNamespaceCode("FlagTeacher", teacher));
			
			//设置专业备注（为原专业）
			student.setEnumConstByFlagMajorType(this.getGeneralDao().getEnumConstByNamespaceCode("FlagMajorType", "0"));
			
			//判断是否为跨专业
			if(edutype.indexOf("本")>0){
				String oldMajor = student.getGraduateMajor();
				if(major!=null && oldMajor !=null && major.length()>0 && oldMajor.length()>0){
					if(!this.checkMajorType(major, oldMajor)){
						student.setEnumConstByFlagMajorType(
								this.getGeneralDao().getEnumConstByNamespaceCode("FlagMajorType", "1"));
					}
				}
			}
			String archieveId = student.getPrRecPlanMajorSite().getPeSite().getCode() + this.getRecSequence();
			student.setExamCardNum(archieveId);
			
			// 设置招生渠道
			student.setEnumConstByFlagRecChannel(
					this.getGeneralDao().getEnumConstByNamespaceCode("FlagRecChannel", "0"));
			
			//设置学生的密码  默认设置成学生的证件号码
			student.setPassword(student.getCardNo());
			
			// 设置学生学历验证状态 为未审核状态
			student.setEnumConstByFlagXueliCheck(this.getGeneralDao()
					.getEnumConstByNamespaceCode("FlagXueliCheck", "0"));			
			// 设置学生状态
			student.setEnumConstByFlagRecStatus(
					this.getGeneralDao().getEnumConstByNamespaceCode("FlagRecStatus", "1"));
			
			// 设置学生录取状态
			student.setEnumConstByFlagMatriculate(
					this.getGeneralDao().getEnumConstByNamespaceCode("FlagMatriculate", "0"));
			if (!studentSet.add(student)) {
				msg.append("第" + (i + 1) + "行数据与文件中前面的数据重复！<br/>");
				continue;
			}
		
		}

		if (msg.length() > 0) {
			msg.append("学生报名信息批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}

		for (PeRecStudent student : studentSet) {
			try {
				this.getGeneralDao().save(student);
				count++;
			} catch (Exception e) {
				e.printStackTrace();
				throw new EntityException("批量上传学生报名信息失败");
			}
		}
		return count;
	}

	/**
	 * 把"yyyy-MM-dd"型的String转换成Date
	 * 
	 * @param dateString
	 * @return
	 * @throws Exception
	 */
	private Date stringToDate(String dateString) throws Exception {
		if(!isDate(dateString)){
			throw new Exception();
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = bartDateFormat.parse(dateString);
		return date;
	}

	/**
	 * 检测证件号码是否已经存在
	 * 
	 * @param cardNo
	 * @return
	 */
	private boolean cardNoIsExist(String cardNo) {
		DetachedCriteria dcPeRecStudent = DetachedCriteria
				.forClass(PeRecStudent.class);
		DetachedCriteria dcPeRecruitplan1 = dcPeRecStudent.createCriteria(
				"prRecPlanMajorSite", "prRecPlanMajorSite").createCriteria(
				"prRecPlanMajorEdutype", "prRecPlanMajorEdutype")
				.createCriteria("peRecruitplan", "peRecruitplan");
		dcPeRecruitplan1.add(Restrictions.eq("flagActive", "1"));
		dcPeRecStudent.add(Restrictions.eq("cardNo", cardNo));
		List<PeRecStudent> peRecStudentSiteList = new ArrayList();
		try {
			peRecStudentSiteList = this.getGeneralDao().getList(dcPeRecStudent);
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
		if (peRecStudentSiteList.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 检查证件号码格式是否正确
	 * 
	 * @param cardNo
	 * @return
	 */
	private boolean checkCardNo(String cardNo) {
		AttributeManage manage=new WhatyAttributeManage();
		try {
			return manage.isValidIdcard(cardNo);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	/**
	 * 验证日期
	 * @param date
	 * @return
	 */
	private boolean isDate(String date) {
		AttributeManage manage=new WhatyAttributeManage();
		try {
			String[] strings = date.split("-");
			if(strings.length != 3)
				return false;
			return manage.isValidDate(strings[0], strings[1], strings[2]);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	/**
	 * 校验手机号码
	 * @param mobile
	 * @return
	 */
    public boolean isMobile(String mobile) { 
        if (mobile == null) 
            return false; 
        else 
        	return	mobile.matches(Const.mobile);
    } 


    /** 
     * 校验email格式 
     *  
     * @param email 
     *            email 
     * @return 
     */ 
    public static boolean isEmail(String email) { 
        if (email == null) 
            return false; 
        else 
        	return email.matches(Const.email);
    } 
    
    /** 
     * 校验邮编格式 
     *  
     * @param zip 
     *            zip 
     * @return 
     */ 
    public static boolean isZIP(String zip) { 
        if (zip == null) 
            return false; 
        else 
        	return zip.matches(Const.zip);
    }    
    
    public static boolean isTelephone(String telephone) { 
        if (telephone == null) 
            return false; 
        else 
        	return telephone.matches(Const.telephone);
    } 
    
	 /**
	  * 检验年月是否符合yyyy年MM月的格式
	  * @param year
	  * @return
	  */   
    public boolean isYear(String year){
    	if (year.length()!=8) {
    		return false;
    	}
    	if (!year.substring(0,4).matches(Const.number)) {
    		return false;
    	}
    	if (!year.substring(5,7).matches("^(0[1-9]|1[0-2])$")) {
    		return false;
    	}
    	return true;
    }
    /**
     * 检验身份证号的出生日期和所填出生日期是否一致
     * @param cardNo 身份证号
     * @param birthday 出生日期
     * @return
     */
	public boolean checkCardNoBirthday(String cardNo , String birthday){
		String verify18PatternStr = "(\\d{6})" + "(\\d{8})" + "(\\d{3})" + "([\\d[xX]]{1})";
		Pattern verify18Pattern = Pattern.compile(verify18PatternStr);
		String verify15PatternStr = "(\\d{6})" + "(\\d{6})" + "(\\d{3})";
		Pattern verify15Pattern = Pattern.compile(verify15PatternStr);
		Matcher m = null;
		String birthdayNum = "";
		if(cardNo.length()==15){
		    m = verify15Pattern.matcher(cardNo);
		 
		} else{
			  m = verify18Pattern.matcher(cardNo);

		}
	    if(m.matches()==false) {
		      return false;
		}
		birthdayNum = m.group(2);
		   String FullBirthdayNum = null;
		    if(cardNo.length()==15)//在生日号码前加“19”
		      FullBirthdayNum = "19" + birthdayNum;
		    else
		      FullBirthdayNum = birthdayNum;
		 
		    String year = FullBirthdayNum.substring(0, 4);
		    String month = FullBirthdayNum.substring(4, 6);
		    String date = FullBirthdayNum.substring(6, 8);
		    String str = year + "-" + month + "-" + date;
		    if(birthday.equals(str)){
		    	return true;
		    }else{
		    	return false;
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
		list = this.getGeneralDao().getList(dcPrStuMultiMajor);
		if(list==null||list.isEmpty()){
			return true;
		}
		dcPrStuMultiMajor.add(Restrictions.eq("oldMajor", oldMajor));
		list = this.getGeneralDao().getList(dcPrStuMultiMajor);
		if(list==null||list.isEmpty()){
			return false;
		}
		return true;
	}
	
	/**
	 * 取得学生报名号： 时间+4位顺序号
	 * @return
	 */
	public String getRecSequence(){
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); 
		String time = dateFormat.format(date);
		String sql = "select lpad(ARCHIEVE_ID.nextval,4,'0') from dual";
			List list = this.getGeneralDao().getBySQL(sql);
			if(list!=null&&list.size()>0){
				time += list.get(0);
			}
		return time;
	}
	
	public GeneralDao<PeRecStudent> getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao<PeRecStudent> generalDao) {
		this.generalDao = generalDao;
	}

}
