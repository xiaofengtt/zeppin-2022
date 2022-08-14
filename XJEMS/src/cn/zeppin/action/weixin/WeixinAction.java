/**
 * This class is used for 微信公众服务号操作
 * 
 */
package cn.zeppin.action.weixin;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.dom4j.DocumentException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Ethnic;
import cn.zeppin.entity.ExamInformation;
import cn.zeppin.entity.ExamRoom;
import cn.zeppin.entity.ExamRoomInfo;
import cn.zeppin.entity.ExamTeacherRoom;
import cn.zeppin.entity.InvigilationTeacher;
import cn.zeppin.entity.InvigilationTeacherOld;
import cn.zeppin.entity.MobileCode;
import cn.zeppin.entity.Resource;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IEthnicService;
import cn.zeppin.service.api.IExamInformationService;
import cn.zeppin.service.api.IExamRoomService;
import cn.zeppin.service.api.IExamTeacherRoomService;
import cn.zeppin.service.api.IInvigilationTeacherService;
import cn.zeppin.service.api.IMobileCodeService;
import cn.zeppin.service.api.IResourceService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.HtmlHelper;
import cn.zeppin.utility.IDCardUtil;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.pinyingUtil;
import cn.zeppin.utility.wx.CheckUtil;
import cn.zeppin.utility.wx.CommonUtil;
import cn.zeppin.utility.wx.ConfigUtil;
import cn.zeppin.utility.wx.MessageUtil;

/**
 * ClassName: WeixinAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public class WeixinAction extends BaseAction {
	// private final String PATH = "http://ks.xsdkszx.cn/";
	private final String PATH = "http://" + request.getServerName() + "/";
	/**
	 * 
	 */
	private static final long serialVersionUID = 369393031897616109L;

	private IInvigilationTeacherService invigilationTeacherService;
	private IExamTeacherRoomService examTeacherRoomService;
	private IExamRoomService examRoomService;
	private IExamInformationService examInformationService;
	private IEthnicService ethnicService;
	private IResourceService resourceService;
	private IMobileCodeService mobileCodeService;

	public IMobileCodeService getMobileCodeService() {
		return mobileCodeService;
	}

	public void setMobileCodeService(IMobileCodeService mobileCodeService) {
		this.mobileCodeService = mobileCodeService;
	}

	public IInvigilationTeacherService getInvigilationTeacherService() {
		return invigilationTeacherService;
	}

	public void setInvigilationTeacherService(IInvigilationTeacherService invigilationTeacherService) {
		this.invigilationTeacherService = invigilationTeacherService;
	}

	public IExamTeacherRoomService getExamTeacherRoomService() {
		return examTeacherRoomService;
	}

	public void setExamTeacherRoomService(IExamTeacherRoomService examTeacherRoomService) {
		this.examTeacherRoomService = examTeacherRoomService;
	}

	public IExamRoomService getExamRoomService() {
		return examRoomService;
	}

	public void setExamRoomService(IExamRoomService examRoomService) {
		this.examRoomService = examRoomService;
	}

	public IExamInformationService getExamInformationService() {
		return examInformationService;
	}

	public void setExamInformationService(IExamInformationService examInformationService) {
		this.examInformationService = examInformationService;
	}

	public IEthnicService getEthnicService() {
		return ethnicService;
	}

	public void setEthnicService(IEthnicService ethnicService) {
		this.ethnicService = ethnicService;
	}

	public IResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}

	/**
	 * 老师注册，根据身份证号判断教师信息是否已经存在， 如果存在则更新，不存在则新添加
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "photo", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "idcard", type = ValueType.STRING, nullable = false, emptyable = false)
//	@ActionParam(key = "sex", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "ethnic", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "major", type = ValueType.STRING)
	@ActionParam(key = "mobile", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "code", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "type", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "organization", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "inschooltime", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "specialty", type = ValueType.STRING)
	@ActionParam(key = "sid", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "invigilateCampus", type = ValueType.STRING, nullable = false, emptyable = false)
	// @ActionParam(key = "invigilateType", type = ValueType.STRING, nullable =
	// false, emptyable = false)
	@ActionParam(key = "jobDuty", type = ValueType.STRING)
	@ActionParam(key = "studyMajor", type = ValueType.STRING)
	@ActionParam(key = "studyGrade", type = ValueType.STRING)
	@ActionParam(key = "studyLength", type = ValueType.NUMBER)
	@ActionParam(key = "bankCard", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "openid", type = ValueType.STRING)
	@ActionParam(key = "idcardPhoto", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "formation", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "occupation", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "bankOrg", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "bankName", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "email", type = ValueType.STRING, nullable = false, emptyable = false)
	public void Add() {
		ActionResult actionResult = new ActionResult();

		String name = request.getParameter("name");
		String idcard = request.getParameter("idcard");
//		Short sex = Short.parseShort(request.getParameter("sex"));
		Short ethnic = Short.parseShort(request.getParameter("ethnic"));
		String major = request.getParameter("major");
		String mobile = request.getParameter("mobile");
		Short type = Short.parseShort(request.getParameter("type"));
		String organization = request.getParameter("organization");
		String inschooltime = request.getParameter("inschooltime");
		// 可空
		String sid = request.getParameter("sid");
		
		
		//20190418新增5个字段
		String formation = request.getParameter("formation");
//		if(type == 3){//职工非空
		if(Utlity.checkStringNull(formation)){
			actionResult.init(FAIL_STATUS, "请选择编制属性信息", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
//		} else {
//			formation = Utlity.checkStringNull(formation) ? "未选择" : formation;
//		}
		String occupation = request.getParameter("occupation");
		if(Utlity.checkStringNull(occupation)){
			actionResult.init(FAIL_STATUS, "请填写职业信息", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if(occupation.length() > 50){
			actionResult.init(FAIL_STATUS, "职业内容超出50字长度限制", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		String bankOrg = request.getParameter("bankOrg");
		if(Utlity.checkStringNull(bankOrg)){
			actionResult.init(FAIL_STATUS, "请填写开户行所属地区", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if(bankOrg.length() > 50){
			actionResult.init(FAIL_STATUS, "开户行所属地区超出50字长度限制", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		String bankName = request.getParameter("bankName");
		if(Utlity.checkStringNull(bankName)){
			actionResult.init(FAIL_STATUS, "请填写开户行", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if(bankName.length() > 50){
			actionResult.init(FAIL_STATUS, "开户行超出50字长度限制", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		String email = request.getParameter("email");
		if(Utlity.checkStringNull(email)){
			actionResult.init(FAIL_STATUS, "请填写电子信箱", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		
		Integer photo = this.getIntValue(request.getParameter("photo"));
		if(photo == 1){
			actionResult.init(FAIL_STATUS, "请上传头像", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		String studyLength = request.getParameter("studyLength") == null ? "0" : request.getParameter("studyLength");
		String specialty = request.getParameter("specialty") == null ? "" : request.getParameter("specialty");
		String invigilateCampus = request.getParameter("invigilateCampus");//
		// 监考校区
		// String invigilateType = request.getParameter("invigilateType");//
		// 监考类型
		String jobDuty = request.getParameter("jobDuty");// 职务
		String studyMajor = request.getParameter("studyMajor");// 研究生所学专业
		String studyGrade = request.getParameter("studyGrade");// 研究生所在年级
		String bankCard = request.getParameter("bankCard");// 交通银行卡卡号
		String openid = request.getParameter("openid");// openid
		String idcardPhoto = request.getParameter("idcardPhoto");// 身份证正面照

		if (Utlity.checkStringNull(idcardPhoto)) {
			actionResult.init(FAIL_STATUS, "请先上传身份证正面照", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
	
		Integer idPhoto = this.getIntValue(idcardPhoto);

		InvigilationTeacher teacher = new InvigilationTeacher();

		try {
			if (idcard != null) {
				if (IDCardUtil.IDCardValidate(idcard).equals("")) {
					idcard = idcard.toUpperCase();
					Map<String, Object> searchCard = new HashMap<String, Object>();
					searchCard.put("idcard", idcard);
					List<InvigilationTeacher> list = this.invigilationTeacherService
							.searchInvigilationTeacher(searchCard, null, -1, -1);
					if (list != null && list.size() > 0) {
						// teacher = list.get(0);
						actionResult.init(FAIL_STATUS, "该身份证号已被注册", null);
						Utlity.ResponseWrite(actionResult, dataType, response);
						return;
						// isAdd = false;
					}

				} else {
					actionResult.init(FAIL_STATUS, "请正确填写身份证号", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
			} else {
				actionResult.init(FAIL_STATUS, "必须填写身份证号", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}

			if (!Utlity.isNumeric(bankCard)) {
				actionResult.init(FAIL_STATUS, "交通银行卡号输入有误", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			if (!Utlity.isMobileNO(mobile)) {
				actionResult.init(FAIL_STATUS, "手机号码输入有误", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			Map<String, Object> searchMobile = new HashMap<String, Object>();
			searchMobile.put("mobile", mobile);
			Integer count = this.invigilationTeacherService.searchInvigilationTeacherCount(searchMobile);
			if (count > 0) {
				actionResult.init(FAIL_STATUS, "该手机号已注册", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return; 
			}
			// 必填
			String code = request.getParameter("code");
			if (code == null || code.equals("")) {
				actionResult.init(FAIL_STATUS, "请输入验证码！", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			String uuid = (String) session.getAttribute("code");
			String sessionCode = null;
			Map<String, Object> params = new HashMap<>();
			params.put("uuid", uuid);
			List<MobileCode> mobileCode = mobileCodeService.getMobileCodeByParams(params);
			if (mobileCode != null && mobileCode.size() > 0) {
				sessionCode = mobileCode.get(0).getCode();
			}
			if (uuid == null || uuid.equals("")) {
				actionResult.init(FAIL_STATUS, "请先获取短信验证码！", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			if (sessionCode == null || !code.equals(sessionCode)) {
				actionResult.init(FAIL_STATUS, "验证码输入错误", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			if (Utlity.checkStringNull(inschooltime)) {
				actionResult.init(FAIL_STATUS, "请选择入校时间", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			
			//20180919新增强制校验 研究生和本科 学制和年级必填
			if("2".equals(type) || "4".equals(type)){
				if(Utlity.checkStringNull(studyGrade) || "0".equals(studyGrade)){
					actionResult.init(FAIL_STATUS, "请选择所在年级", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
				if(Utlity.checkStringNull(studyLength) || "0".equals(studyLength)){
					actionResult.init(FAIL_STATUS, "请选择学制信息", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
			}

			InvigilationTeacherOld teacherOld = invigilationTeacherService.getTeacherOldByIdcard(idcard);
			if (teacherOld != null) {
				teacher.setName(teacherOld.getName());
				teacher.setIdcard(teacherOld.getIdcard());
				teacher.setIntgral(getIntValue(teacherOld.getIntegral()));
				teacher.setCheckStatus((short) 2);// 审核状态：审核通过
				teacher.setChecktime(new Timestamp(System.currentTimeMillis()));
			} else {
				teacher.setName(name);
				teacher.setIdcard(idcard);
				teacher.setIntgral(0);
				teacher.setCheckStatus((short) 1);// 审核状态：未审核
			}
			// 存储数据
			teacher.setPinyin(pinyingUtil.getFirstSpell(name));
			teacher.setMobile(mobile);
			teacher.setSex(IDCardUtil.getSex(idcard));
			Ethnic eth = this.ethnicService.getById(ethnic);
			teacher.setEthnic(eth);
			Resource pho = this.resourceService.getById(photo);
			teacher.setPhoto(pho);
			teacher.setType(type);
			// 研究生 无职务
			teacher.setStudyMajor(studyMajor);
			teacher.setStudyGrade(studyGrade);
			teacher.setJobDuty(jobDuty);
			teacher.setMajor(major);
			teacher.setOrganization(organization);
			
			//20190418新增5个字段信息
			teacher.setFormation(formation);
			teacher.setOccupation(occupation);
			teacher.setBankOrg(bankOrg);
			teacher.setBankName(bankName);
			teacher.setEmail(email);
			
			// teacher.setInschooltime(Timestamp.valueOf(inschooltime + "
			// 00:00:00"));
			teacher.setInschooltime(inschooltime);

			teacher.setSpecialty(specialty);
			teacher.setInvigilateCampus(invigilateCampus);
			// teacher.setInvigilateType(invigilateType);
			teacher.setInvigilateCount(0);
			// teacher.setOpenID(openid);
			teacher.setSid(sid);
			teacher.setBankCard(bankCard);
			teacher.setCreator(0);

			teacher.setStatus((short) 1);//
			teacher.setCreatetime(new Timestamp(System.currentTimeMillis()));
			teacher.setRemark("");
			teacher.setIsChiefExaminer((short) 0);
			teacher.setIsMixedExaminer((short) 0);

			teacher.setInvigilateCount(0);
			teacher.setOpenID(openid);
			teacher.setDisableType((short) 1);
			teacher.setIsDisable((short) 0);
			teacher.setStudyLength(Integer.parseInt(studyLength));

			// 身份证正面照
			Resource idCardPhotrR = this.resourceService.getById(idPhoto);
			teacher.setIdCardPhoto(idCardPhotrR);

			this.invigilationTeacherService.add(teacher);

			// 清空验证码
			session.removeAttribute("code");
			String myMessageUrl = PATH + "XJ_wechat/myMessage.html";
			actionResult.init(SUCCESS, "添加成功", ConfigUtil.getCodeUrl(myMessageUrl));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "添加失败", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 修改(修改教师信息需要审核)
	 * 2017.12.14学制及所在年级，身份类别不可修改，
	 */
//	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "photo", type = ValueType.NUMBER, nullable = false, emptyable = false)
	// @ActionParam(key = "name", type = ValueType.STRING, nullable = false,
	// emptyable = false)
	// @ActionParam(key = "idcard", type = ValueType.STRING, nullable = false,
	// emptyable = false)
//	@ActionParam(key = "sex", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "ethnic", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "major", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "mobile", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "code", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "type", type = ValueType.NUMBER)
	@ActionParam(key = "organization", type = ValueType.STRING)
	@ActionParam(key = "inschooltime", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "specialty", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "sid", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "invigilateCampus", type = ValueType.STRING, nullable = false, emptyable = false)
	// @ActionParam(key = "invigilateType", type = ValueType.STRING, nullable =
	// false, emptyable = false)
	@ActionParam(key = "jobDuty", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "studyMajor", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "studyGrade", type = ValueType.STRING)
	@ActionParam(key = "studyLength", type = ValueType.NUMBER)
	@ActionParam(key = "bankCard", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "idcardPhoto", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "formation", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "occupation", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "bankOrg", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "bankName", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "email", type = ValueType.STRING, nullable = false, emptyable = false)
	public void Update() {
		ActionResult actionResult = new ActionResult();
		InvigilationTeacher teacher = (InvigilationTeacher) session.getAttribute("teachersession");
		if(teacher == null){
			actionResult.init(UNLOGIN_STATUS, "用户未登录", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
//		Integer id = this.getIntValue(request.getParameter("id"));
		// String name = request.getParameter("name");
		// String idcard = request.getParameter("idcard");
//		Short sex = Short.parseShort(request.getParameter("sex"));
		Short ethnic = Short.parseShort(request.getParameter("ethnic"));
		String major = request.getParameter("major");
		String mobile = request.getParameter("mobile");
		String type = request.getParameter("type");
		String organization = request.getParameter("organization");
		
		//20190418新增5个字段
		String formation = request.getParameter("formation");
//		if(teacher.getType() == 3){//职工非空
		if(Utlity.checkStringNull(formation)){
			actionResult.init(FAIL_STATUS, "请选择编制属性信息", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
//		} else {
//			formation = Utlity.checkStringNull(formation) ? "未选择" : formation;
//		}
		String occupation = request.getParameter("occupation");
		if(Utlity.checkStringNull(occupation)){
			actionResult.init(FAIL_STATUS, "请填写职业信息", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if(occupation.length() > 50){
			actionResult.init(FAIL_STATUS, "职业内容超出50字长度限制", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		String bankOrg = request.getParameter("bankOrg");
		if(Utlity.checkStringNull(bankOrg)){
			actionResult.init(FAIL_STATUS, "请填写开户行所属地区", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if(bankOrg.length() > 50){
			actionResult.init(FAIL_STATUS, "开户行所属地区超出50字长度限制", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		String bankName = request.getParameter("bankName");
		if(Utlity.checkStringNull(bankName)){
			actionResult.init(FAIL_STATUS, "请填写开户行", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if(bankName.length() > 50){
			actionResult.init(FAIL_STATUS, "开户行超出50字长度限制", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		String email = request.getParameter("email");
		if(Utlity.checkStringNull(email)){
			actionResult.init(FAIL_STATUS, "请填写电子信箱", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		
		String inschooltime = request.getParameter("inschooltime");
		// 可空
		String sid = request.getParameter("sid");
		Integer photo = this.getIntValue(request.getParameter("photo"));
		if(photo == 1){
			actionResult.init(FAIL_STATUS, "请上传头像", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		Integer studyLength = this.getIntValue(request.getParameter("studyLength"),0);
		String specialty = request.getParameter("specialty") == null ? "" : request.getParameter("specialty");
		String invigilateCampus = request.getParameter("invigilateCampus");//
		// 监考校区
		// String invigilateType = request.getParameter("invigilateType");//
		// 监考类型
		String jobDuty = request.getParameter("jobDuty");// 职务
		String studyMajor = request.getParameter("studyMajor");// 研究生所学专业
		String studyGrade = request.getParameter("studyGrade");// 研究生所在年级
		String bankCard = request.getParameter("bankCard");// 交通银行卡卡号
		String idcardPhoto = request.getParameter("idcardPhoto");// 身份证正面照

		if (Utlity.checkStringNull(idcardPhoto)) {
			actionResult.init(FAIL_STATUS, "请先上传身份证正面照", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		Integer idPhoto = this.getIntValue(idcardPhoto);

		if (!Utlity.isMobileNO(mobile)) {
			actionResult.init(FAIL_STATUS, "手机号码输入有误", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		try {
//			InvigilationTeacher teacher = invigilationTeacherService.getById(id);
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("mobile", mobile);
			Integer count = this.invigilationTeacherService.searchInvigilationTeacherCount(searchParams);
			if (count > 0 && !(mobile).equals(teacher.getMobile())) {
				actionResult.init(FAIL_STATUS, "该手机号已注册", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			if (!Utlity.isNumeric(bankCard)) {
				actionResult.init(FAIL_STATUS, "交通银行卡号输入有误", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}

			// 必填
			String code = request.getParameter("code");
			if (code == null || code.equals("")) {
				actionResult.init(FAIL_STATUS, "请输入验证码！", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			String uuid = (String) session.getAttribute("code");
			String sessionCode = null;
			Map<String, Object> params = new HashMap<>();
			params.put("uuid", uuid);
			List<MobileCode> mobileCode = mobileCodeService.getMobileCodeByParams(params);
			if (mobileCode != null && mobileCode.size() > 0) {
				sessionCode = mobileCode.get(0).getCode();
			}
			if (uuid == null || uuid.equals("")) {
				actionResult.init(FAIL_STATUS, "请先获取短信验证码！", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			if (sessionCode == null || !code.equals(sessionCode)) {
				actionResult.init(FAIL_STATUS, "验证码输入错误", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			if(Utlity.checkStringNull(organization)){
				actionResult.init(FAIL_STATUS, "请正确填写所在学院或部门", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			//20180919新增强制校验 研究生和本科 学制和年级必填
			if(!Utlity.checkStringNull(type)){
				if("2".equals(type) || "4".equals(type)){
					if(Utlity.checkStringNull(studyGrade) || "0".equals(studyGrade)){
						actionResult.init(FAIL_STATUS, "请选择所在年级", null);
						Utlity.ResponseWrite(actionResult, dataType, response);
						return;
					}
					if(studyLength == 0){
						actionResult.init(FAIL_STATUS, "请选择学制信息", null);
						Utlity.ResponseWrite(actionResult, dataType, response);
						return;
					}
				}
			}
			
			if (Utlity.checkStringNull(inschooltime)) {
				actionResult.init(FAIL_STATUS, "请选择入校时间", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}

//			if (teacher != null) {
			// teacher.setName(name);
			// teacher.setIdcard(idcard);
			// teacher.setPinyin(pinyingUtil.getFirstSpell(name));
			teacher.setMobile(mobile);
//				teacher.setSex(sex);
			Ethnic eth = this.ethnicService.getById(ethnic);
			teacher.setEthnic(eth);
			Resource pho = this.resourceService.getById(photo);
			teacher.setPhoto(pho);
			if(!Utlity.checkStringNull(type)){
				teacher.setType(Short.parseShort(type));
			}
			// 研究生 无职务
			teacher.setStudyMajor(studyMajor);
			teacher.setStudyGrade(studyGrade);
			teacher.setJobDuty(jobDuty);
			teacher.setMajor(major);
			teacher.setOrganization(organization);
			
			//20190418新增5个字段信息
			teacher.setFormation(formation);
			teacher.setOccupation(occupation);
			teacher.setBankOrg(bankOrg);
			teacher.setBankName(bankName);
			teacher.setEmail(email);
			
			teacher.setInschooltime(inschooltime);
			teacher.setStudyLength(studyLength);

			teacher.setSpecialty(specialty);
			teacher.setInvigilateCampus(invigilateCampus);
			// teacher.setInvigilateType(invigilateType);
			teacher.setSid(sid);
			teacher.setBankCard(bankCard);
			// 身份证正面照
			Resource idCardPhotrR = this.resourceService.getById(idPhoto);
			teacher.setIdCardPhoto(idCardPhotrR);
			// teacher.setCheckStatus((short) 1);// 审核状态：未审核
			// teacher.setPassword(bankCard.substring(bankCard.length() -
			// 6));// 默认密码身份证后6位

//			} else {
//				actionResult.init(FAIL_STATUS, "教师信息不存在", null);
//				Utlity.ResponseWrite(actionResult, dataType, response);
//				return;
//			}

			this.invigilationTeacherService.update(teacher);
			// 清空验证码
			session.removeAttribute("code");
			String myMessageUrl = PATH + "XJ_wechat/myMessage.html";
			actionResult.init(SUCCESS, "教师信息更新成功", ConfigUtil.getCodeUrl(myMessageUrl));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "修改失败", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 变更状态
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Change() {
		// TODO Auto-generated method stub
		ActionResult actionResult = new ActionResult();
		int id = Integer.parseInt(request.getParameter("id"));
		String status = request.getParameter("status");
		try {
			InvigilationTeacher InvigilationTeacher = invigilationTeacherService.getById(id);
			if (InvigilationTeacher != null) {
				InvigilationTeacher.setStatus(Short.parseShort(status));
				this.invigilationTeacherService.update(InvigilationTeacher);
	
				actionResult.init(SUCCESS, "操作成功", null);
			} else {
				actionResult.init(FAIL_STATUS, "考试信息不存在", null);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "操作失败", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);

	}

	/**
	 * 取消申请
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void CancelApply() {
		// TODO Auto-generated method stub
		ActionResult actionResult = new ActionResult();
		String id = request.getParameter("id");
		try {
			ExamTeacherRoom etr = this.examTeacherRoomService.getById(Integer.parseInt(id));
			if (etr != null) {
				if (etr.getTeacher().getStatus() == 0) {
					actionResult.init(FAIL_STATUS, "您已被停用，无法取消申请", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
				if (etr.getStatus() == 1 && etr.getRoom() != null) {
					actionResult.init(FAIL_STATUS, "已分配监考，不可再取消", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
				if (etr.getStatus() == 2) {
					actionResult.init(FAIL_STATUS, "您已被停用本次考试，无法取消申请", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
				if (etr.getExam().getStatus() == 0) {
					actionResult.init(FAIL_STATUS, "考试已结束，无法取消申请", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
				Timestamp currentTime = new Timestamp(System.currentTimeMillis());
				if (currentTime.getTime() > etr.getExam().getApplyendtime().getTime()) {
					actionResult.init(FAIL_STATUS, "申报时间已截止，无法取消申请", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
				etr.setStatus((short) 0);
				etr.setRoom(null);
				etr.setIsChief((short) 0);
				etr.setIsMixed((short) 0);
				etr.setIsConfirm((short) 0);
				etr.setConfirtime(null);
				etr.setStartTime(null);
				etr.setEndTime(null);
				this.examTeacherRoomService.update(etr);
			}
			String url = PATH + "XJ_wechat/tip.html";
			actionResult.init(SUCCESS, "操作成功", ConfigUtil.getCodeUrl(url));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "操作失败", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);

	}

	/**
	 * 微信交互处理
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @throws DocumentException
	 */
	public void WeixinAccount() throws IOException, ParseException, DocumentException {
		Verify();
		HandleMessage();
	}

	/**
	 * 消息处理
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	private void HandleMessage() throws IOException, ParseException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Map<String, String> map = MessageUtil.xmlToMap(request);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			String message = null;
			if (MessageUtil.MESSAGE_EVNET.equals(msgType)) {// 事件
				String eventType = map.get("Event");
				if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {// 关注
					message = MessageUtil.initText(toUserName, fromUserName, "欢迎您关注新疆师范大学考试中心~");
				} else if (MessageUtil.MESSAGE_VIEW.equals(eventType)) {// 底部button按钮
					String url = map.get("EventKey");
					// ReGetCode(url);
					message = MessageUtil.initText(toUserName, fromUserName, url);
				}
			} else if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
				if ("1".equals(content)) {
					message = noticeCurrentExam(toUserName, fromUserName);
				} else {
					message = MessageUtil.initText(toUserName, fromUserName, "请通过菜单操作哦~\n也可输入“1”查看当前考试信息");
				}
			} else {
				message = MessageUtil.initText(toUserName, fromUserName, "请通过菜单操作哦~\n也可输入“1”查看当前考试信息");
			}
			System.out.println(message);
			out.print(message);

			System.out.println("----" + (String) session.getAttribute("openid"));
			// System.out.println("............ "+
			// session.getAttribute("openId"));
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {

			out.close();
		}
	}

	/**
	 * 公众号校验
	 * 
	 * @throws IOException
	 */
	private void Verify() throws IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		System.out.println(signature + "---" + timestamp + "---" + nonce + "----" + echostr);
		PrintWriter out = response.getWriter();
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
	}

	/**
	 * 获取教师历史监考记录
	 */
	@SuppressWarnings("rawtypes")
	public void HistoryInfo() {
		ActionResult actionResult = new ActionResult();
		// InvigilationTeacher invigilationTeacher = invigilationTeacherService
		// .getInvigilationTeacherInfoByOpenID(toUserName);
		//
		String code = request.getParameter("code");
		InvigilationTeacher invigilationTeacher = checkOpenId(actionResult, code, "history");
		if (invigilationTeacher == null) {
			return;
		}
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		int recordCount = 0;
		int pageCount = 0;
		try {
			// 查询历史监考记录
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("teacher", invigilationTeacher.getId());
			searchMap.put("status", 1);
			searchMap.put("distribute", 3);
			//2018-04-12新增   只有教师二次确认了以后才显示在“历史监考”记录中
			searchMap.put("isConfirm", 1);
			List listHistory = this.examTeacherRoomService.searchExamTeacherRoom(searchMap, null, offset, pagesize);
			if (listHistory != null && !listHistory.isEmpty()) {
				recordCount = listHistory.size();
				pageCount = (int) Math.ceil((double) recordCount / pagesize);
				for (int i = 0; i < listHistory.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					Object o = listHistory.get(i);
					Object[] obj = (Object[]) o;
					ExamTeacherRoom exam = (ExamTeacherRoom) obj[0];
					String examName = exam.getExam().getName();
					ExamRoom room = exam.getRoom();
					String roomIndex = room.getRoomIndex();
					String roomAdd = room.getRoomAddress();
					String time = Utlity.timeSpanToString2(exam.getCreatetime());
					String remark = exam.getRemark();
					String reason = exam.getReason();
					map.put("index", i);
					map.put("examName", examName);
					map.put("roomIndex", roomIndex);
					map.put("roomAdd", roomAdd);
					map.put("applytime", time);
					map.put("score", exam.getCredit() == null ? "0" : exam.getCredit());
					map.put("remark", remark);
					map.put("reason", reason);
					// map.put("examnationTime", room.getExamnationTime());
					// map.put("examnationTime",
					// room.getExam().getExamStarttime() +
					// "-"+room.getExam().getExamEndtime());
					if (room.getExamRoomInfo() != null) {
						List<ExamRoomInfo> userList = JSON.parseArray(room.getExamRoomInfo(), ExamRoomInfo.class);
						map.put("examnation", userList);
					} else {
						map.put("examnation", null);
					}

					listMap.add(map);
				}
			} else {
				actionResult.init(FAIL_STATUS, "暂无历史监考记录", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "信息异常", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		actionResult.init(SUCCESS_STATUS, "获取资源成功", listMap);
		actionResult.setPageCount(pageCount);
		actionResult.setPageNum(pagenum);
		actionResult.setPageSize(pagesize);
		actionResult.setTotalCount(recordCount);
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 通过教师openId和考场id 申请监考
	 */
	@SuppressWarnings("rawtypes")
	public void Apply() {
		ActionResult actionResult = new ActionResult();
		// 接受页面参数
		Integer exam = this.getIntValue(request.getParameter("exam"));
		Integer tid = this.getIntValue(request.getParameter("tid"));
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		// InvigilationTeacher teacher = checkOpenId(actionResult, toUserName);
		InvigilationTeacher teacher = invigilationTeacherService.getById(tid);
		if (teacher == null) {
			actionResult.init(FAIL_STATUS, "监考教师信息不存在", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if (teacher.getStatus() == 0) {
			actionResult.init(FAIL_STATUS, "您已被停用，无法申请监考", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		ExamInformation information = this.examInformationService.getById(exam);
		if (information == null) {
			actionResult.init(FAIL_STATUS, "考试信息不存在", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if (information.getStatus() == 0) {
			actionResult.init(FAIL_STATUS, "考试已结束，不能申报监考", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if (information.getStatus() == 2) {
			actionResult.init(FAIL_STATUS, "考试已停止申报，不能申报监考", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		if (currentTime.getTime() > information.getApplyendtime().getTime()) {
			actionResult.init(FAIL_STATUS, "超出申报截止时间，不能申报监考", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		// 判断是否已申报
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("teacher", teacher.getId());
		searchMap.put("exam", exam);
		// 禁用的教师不可申报 删除的可以
		// searchMap.put("status", 1);// 正常的
		try {
			List e_list = this.examTeacherRoomService.searchExamTeacherRoom(searchMap, null, -1, -1);
			if (e_list != null && e_list.size() > 0) {
				Object o = e_list.get(0);
				Object[] obj = (Object[]) o;
				ExamTeacherRoom etroom = (ExamTeacherRoom) obj[0];
				if (etroom.getStatus() == 0) {// 删除
					// case 0:// 删除
					Timestamp applyTime = new Timestamp(System.currentTimeMillis());
					etroom.setStatus((short) 1);
					etroom.setApplytime(applyTime);
					etroom.setCreator(teacher.getId());
					etroom.setIsAuto((short) 1);// 自主申报
					etroom.setIsFirstApply((short) 0);
					etroom.setStartTime(Timestamp.valueOf(startTime + " 00:00:00"));// 开始时间
					etroom.setEndTime(Timestamp.valueOf(endTime + " 00:00:00"));// 结束时间
					this.examTeacherRoomService.update(etroom);

					String url = PATH + "XJ_wechat/tip.html";
					actionResult.init(SUCCESS, "申请成功", ConfigUtil.getCodeUrl(url));
					Map<String, String> templateParams = new HashMap<>();
					templateParams.put("first", "申请成功");
					templateParams.put("keyword1", teacher.getName());
					templateParams.put("keyword2", "参加" + etroom.getExam().getName() + "考试监考的申请!");
					templateParams.put("keyword3", Utlity.timeSpanToString5(applyTime));
					templateParams.put("remark", "相关结果请等待系统通知。");
					templateParams.put("url", "");
					templateParams.put("touser", teacher.getOpenID());
					ConfigUtil.sendTemplate(CommonUtil.getAccessToken().getAccessToken(),
							MessageUtil.teacherApplyTemplate(templateParams));
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
					// case 1:// 已申报
					// actionResult.init(FAIL_STATUS, "已经申报过本场考试监考，不能重复申报",
					// null);
					// Utlity.ResponseWrite(actionResult, dataType, response);
					// return;
					// case 2:// 禁用
					// actionResult.init(FAIL_STATUS, "您已被停用，请联系管理员", null);
					// Utlity.ResponseWrite(actionResult, dataType, response);
					// return;
					// default:
					// break;
				} else if (etroom.getStatus() == 2) {
					actionResult.init(FAIL_STATUS, "您在本次考试中已被停用，请联系管理员", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				} else {
					actionResult.init(FAIL_STATUS, "您已申请过该考试", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
			} else {
				// 创建考试信息实例存储数据
				ExamTeacherRoom etr = new ExamTeacherRoom();
				etr.setTeacher(teacher);
				etr.setExam(information);
				etr.setIsConfirm((short) 0);
				etr.setIsConfirmCanceled((short) 0);
				etr.setIsAuto((short) 1);// 自主申报
				// etr.setCredit(0);
				etr.setStatus((short) 1);// 初始化状态为
				etr.setCreatetime(new Timestamp(System.currentTimeMillis()));
				etr.setCreator(teacher.getId());

				etr.setStartTime(Timestamp.valueOf(startTime + " 00:00:00"));// 开始时间
				etr.setEndTime(Timestamp.valueOf(endTime + " 00:00:00"));// 结束时间

				// 查询是否申报过
				searchMap.remove("exam");
				List list = this.examTeacherRoomService.searchExamTeacherRoom(searchMap, null, -1, -1);
				if (list != null && list.size() > 0) {
					boolean flag = true;
					for (int i = 0; i < list.size(); i++) {
						Object o = list.get(i);
						Object[] obj = (Object[]) o;
						ExamTeacherRoom etroom = (ExamTeacherRoom) obj[0];
						if (etroom.getExam().getId() == exam) {
							flag = false;
						}
					}
					if (!flag) {
						etr.setIsFirstApply((short) 0);
					} else {
						etr.setIsFirstApply((short) 1);
					}
				} else {
					etr.setIsFirstApply((short) 1);
				}
				this.examTeacherRoomService.add(etr);
				String url = PATH + "XJ_wechat/tip.html?";
				actionResult.init(SUCCESS, "申请成功", ConfigUtil.getCodeUrl(url));
				// 给用户发送系统消息通知
				Map<String, String> templateParams = new HashMap<>();
				templateParams.put("first", "申请成功");
				templateParams.put("keyword1", teacher.getName());
				templateParams.put("keyword2", "参加" + etr.getExam().getName() + "考试监考的申请!");
				templateParams.put("keyword3", Utlity.timeSpanToString5(etr.getCreatetime()));
				templateParams.put("remark", "相关结果请等待系统通知。");
				templateParams.put("url", "");
				templateParams.put("touser", teacher.getOpenID());
				ConfigUtil.sendTemplate(CommonUtil.getAccessToken().getAccessToken(),
						MessageUtil.teacherApplyTemplate(templateParams));

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "操作失败", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 教师分配后，查看教师监考信息
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	public void GetRoomInfo() {
		// 监考记录id
		int id = getIntValue(request.getParameter("id"));
		// String code = request.getParameter("code");
		ActionResult actionResult = new ActionResult();
		Map<String, Object> data = null;
		// InvigilationTeacher teacher = checkOpenId(actionResult,code);
		// if (teacher == null) {
		// return;
		// }
		try {
			ExamTeacherRoom etr = this.examTeacherRoomService.getById(id);
			if (etr == null) {
				actionResult.init(FAIL_STATUS, "教师申报信息不存在", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			if (etr.getRoom() == null) {
				actionResult.init(FAIL_STATUS, "考场信息不存在", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			if (etr.getExam().getStatus() == 0) {
				actionResult.init(FAIL_STATUS, "考试已结束", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}

			if (etr.getStatus() == 0) {
				actionResult.init(FAIL_STATUS, "教师申报信息已失效，请重新申报后再分配考场", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			if (etr.getStatus() == 2) {
				actionResult.init(FAIL_STATUS, "教师申报信息已被停用", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}

			data = SerializeEntity.ExamTeacherRoom2Map(etr);

		} catch (Exception e) {
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "信息异常", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		actionResult.init(SUCCESS_STATUS, "获取资源成功", data);

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 教师确认监考考场
	 */
	public void Confirm() {
		// 监考记录id
		int id = Integer.parseInt(request.getParameter("id"));
		ActionResult actionResult = new ActionResult();
		try {
			ExamTeacherRoom etr = examTeacherRoomService.getById(id);
			if (etr != null) {
				Timestamp currentTime = new Timestamp(System.currentTimeMillis());
				if (currentTime.getTime() > etr.getExam().getCheckendtime().getTime()) {
					actionResult.init(FAIL_STATUS, "已超出确认截止时间，不能进行二次确认", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
				
				if (etr.getStatus() == 2 || etr.getTeacher().getStatus() == 0) {
					actionResult.init(FAIL_STATUS, "您已被停用，无法进行二次确认", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
				if(etr.getIsConfirmCanceled() == 1){
					actionResult.init(FAIL_STATUS, "您已被管理员禁用二次确认", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
				etr.setConfirtime(new Timestamp(System.currentTimeMillis()));// 确认时间
				etr.setIsConfirm((short) 1);// 确认状态
				examTeacherRoomService.update(etr);
				actionResult.init(SUCCESS, "已确认参加", null);
				// 给用户发送系统消息通知
				etr.getRoom().getExamRoomInfo();
				List<ExamRoomInfo> userList = JSON.parseArray(etr.getRoom().getExamRoomInfo(), ExamRoomInfo.class);

				String examnationInfor = userList.get(0).getExamnationInformation() + "...";

				String examnationTime = userList.get(0).getExamnationTime();
				if (examnationTime.length() > 15) {
					examnationTime = examnationTime.substring(0, 15) + "...";
				}
				String arrivaltime = userList.get(0).getArrivaltime();
				if (arrivaltime.length() > 15) {
					arrivaltime = arrivaltime.substring(0, 15) + "...";
				}

				Map<String, String> templateParams = new HashMap<>();
				templateParams.put("first", "您已经再次确认参加" + etr.getExam().getName() + "考试监考工作。");
				templateParams.put("keyword1", etr.getTeacher().getName());
				templateParams.put("keyword2", examnationInfor);
				templateParams.put("keyword3", examnationTime);
				templateParams.put("keyword4", arrivaltime);
				templateParams.put("keyword5", etr.getRoom().getRoomIndex() + "-" + etr.getRoom().getRoomAddress());
				templateParams.put("remark", "请准时前往指定地点参加监考工作。\n考务组联系方式：0991-4332567");
				templateParams.put("url", PATH + "XJ_wechat/roomMessage.html?id=" + etr.getId());
				templateParams.put("touser", etr.getTeacher().getOpenID());
				ConfigUtil.sendTemplate(CommonUtil.getAccessToken().getAccessToken(),
						MessageUtil.teacherArrangeTemplate(templateParams));
			} else {
				actionResult.init(FAIL_STATUS, "考场信息不存在", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "操作失败", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 获取教师信息 1、获取已注册教师的openID，并校验 2、通过已注册教师的openID 获取教师的基本信息， 3、筛选微信端教师不需要看到的信息
	 * 4、返回信息到页面
	 * 
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void GetInfo() throws IOException, DocumentException {
		ActionResult actionResult = new ActionResult();
		InvigilationTeacher teacher = (InvigilationTeacher) session.getAttribute("teachersession");
		String tid = request.getParameter("id");
		String code = request.getParameter("code");
		if (!Utlity.checkStringNull(tid)) {
			if(teacher != null){
				if(!tid.equals(teacher.getId()+"")){
					actionResult.init(FAIL_STATUS, "信息查询异常", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
			} else {
				String url = PATH + "/XJ_wechat/myMessage.html";
				url = ConfigUtil.getCodeUrl(url);
				actionResult.init(UNLOGIN_STATUS, "请登录", url);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			teacher = this.invigilationTeacherService.getById(Integer.parseInt(tid));
			if (teacher == null) {
				actionResult.init(UNREGISTERED_STATUS, "教师未在本平台注册，将要跳转到注册页面。。。", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
			}
		} else {
			teacher = checkOpenId(actionResult, code, "myMessage");
		}
		if (teacher == null) {
			return;
		}
		// 返回教师信息
		Map<String, Object> data = null;
		try {
			data = SerializeEntity.InvigilationTeacher2Map(teacher);
			String[] arry = {};
			if (data.get("invigilateCampus") != null && data.get("invigilateCampus").toString().length() > 0) {
				String invigilateCampus = data.get("invigilateCampus").toString();
				String[] invigilateCampusArry = invigilateCampus.split(",");
				data.put("invigilateCampusArry", invigilateCampusArry);
			} else {
				data.put("invigilateCampusArry", arry);
			}
			actionResult.init(SUCCESS_STATUS, "获取信息成功", data);
		} catch (Exception e) {
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "信息转换异常", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		// Map<String, String> map = MessageUtil.xmlToMap(request);
		// String fromUserName = map.get("FromUserName");
		// String toUserName = map.get("ToUserName");
		// String message = MessageUtil.initNewsMessage(toUserName,
		// fromUserName);
		// System.out.println(message);
		// out.print(message);
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 获取考试信息 1、获取已注册教师的openID，并校验 2、查询考试信息 3、查询是否已申报监考 4、返回信息到页面
	 */

	@SuppressWarnings("rawtypes")
	public void GetExamInfo() {
		ActionResult actionResult = new ActionResult();
		String code = request.getParameter("code");
		InvigilationTeacher teacher = checkOpenId(actionResult, code, "tip");
		if (teacher == null) {
			return;
		}
		if (teacher.getStatus() == 0) {
			actionResult.init(FAIL_STATUS, "您已被停用，停用原因：\"" + teacher.getReason() + "\"。", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if (teacher.getCheckStatus() == 1) {
			actionResult.init(FAIL_STATUS, "您的信息还未审核，无法进行其他操作", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if (teacher.getCheckStatus() == 0) {
			actionResult.init(FAIL_STATUS, "您的信息未通过审核，未通过原因：\"" + teacher.getCheckReason() + "\"。请在“我的资料”修改信息。", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		String exam = request.getParameter("exam") == null ? "" : request.getParameter("exam");

		ExamInformation examinformation = new ExamInformation();
		if (!"".equals(exam)) {
			examinformation = this.examInformationService.getById(Integer.parseInt(exam));
			if (examinformation == null) {
				actionResult.init(FAIL_STATUS, "考试信息查询失败", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
		} else {
			Map<String, Object> searchMap = new HashMap<String, Object>();
			// searchMap.put("currenttime", Utlity.timeSpanToString(new
			// Timestamp(System.currentTimeMillis())));
			searchMap.put("statusNormal", 1);// 未结束的考试

			List<ExamInformation> list = this.examInformationService.searchExamInformation(searchMap, null, -1, -1);
			if (list != null && list.size() > 0) {
				examinformation = list.get(0);
				if (examinformation.getStatus() == 0) {
					actionResult.init(END_STATUS, "考试结束", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
				if (examinformation.getStatus() == -1) {
					actionResult.init(EMPTY_STATUS, "暂无正在进行的考试", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
			} else {
				actionResult.init(EMPTY_STATUS, "暂无正在进行的考试", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
		}

		Map<String, Object> data = null;
		try {
			data = SerializeEntity.ExamInformation2Map(examinformation);

			// 是否已报名
			// 判断是否已申报
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("teacher", teacher.getId());
			searchMap.put("exam", examinformation.getId());
			data.put("tid", teacher.getId());
			data.put("isApply", 0);// 可申请
			List etrList = examTeacherRoomService.searchExamTeacherRoom(searchMap, null, -1, -1);
			if (etrList != null && etrList.size() > 0) {
				Object o = etrList.get(0);
				Object[] obj = (Object[]) o;
				ExamTeacherRoom etr = (ExamTeacherRoom) obj[0];
				if (etr.getStatus() != 0) {// 禁用的和已分配的不可再申请
					data.put("isApply", 1);// 不可申请监考
					data.put("recordsId", etr.getId());
				}
			}
			actionResult.init(SUCCESS_STATUS, "获取信息成功", data);
		} catch (Exception e) {
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "信息转换异常", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 获取当前时间内考试信息
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	public void GetCurrent() {
		ActionResult actionResult = new ActionResult();

		Map<String, Object> searchMap = new HashMap<String, Object>();
		// searchMap.put("currenttime", Utlity.timeSpanToString(new
		// Timestamp(System.currentTimeMillis())));
		searchMap.put("statusNormal", 1);// 未结束的考试
		try {

			List<ExamInformation> list = this.examInformationService.searchExamInformation(searchMap, null, -1, -1);
			if (list != null && list.size() > 0) {
				ExamInformation information = list.get(0);

				Map<String, Object> data = null;
				try {
					data = SerializeEntity.ExamInformation2Map(information);
					if (information.getStatus() == 0) {
						actionResult.init(END_STATUS, "考试结束", null);
						Utlity.ResponseWrite(actionResult, dataType, response);
						return;
					}
					if (information.getStatus() == -1) {
						actionResult.init(EMPTY_STATUS, "暂无正在进行的考试", null);
						Utlity.ResponseWrite(actionResult, dataType, response);
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
					actionResult.init(FAIL_STATUS, "信息转换异常", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}

				actionResult.init(SUCCESS_STATUS, "获取资源成功", data);
			} else {
				actionResult.init(EMPTY_STATUS, "暂无正在进行的考试", null);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "信息获取异常", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 检测openID
	 * 
	 * @param actionResult
	 * @param openID
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private InvigilationTeacher checkOpenId(ActionResult actionResult, String code, String pagename) {
		String openID = GetOpenId(code);
		if (openID == null || "".equals(openID)) {
			actionResult.init(FAIL_STATUS, "微信授权失败", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return null;
		}
		try {
			InvigilationTeacher teacher = this.invigilationTeacherService.getInvigilationTeacherInfoByOpenID(openID);
			if (teacher == null) {
				String url = PATH + "XJ_wechat/login.html?pagename=" + pagename + "&openid=" + openID;
				actionResult.init(UNLOGIN_STATUS, "请登录", url);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return null;
			}
			// 保存会话状态
			session.setAttribute("teachersession", teacher);
			return teacher;
		} catch (Exception e) {
			actionResult.init(FAIL_STATUS, "信息获取异常", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
		}
		return null;
	}

	/**
	 * 微信登录
	 * 
	 * @author
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void Login() throws IOException, DocumentException {
		ActionResult result = new ActionResult();
		String openid = request.getParameter("openid");
		String pagename = request.getParameter("pagename");
		String loginName = request.getParameter("mobile");
		String smsCode = request.getParameter("smsCode");
		// 验证手机号为空
		if (loginName == null || loginName.equals("")) {
			result.init(FAIL_STATUS, "请输入手机号！", null);
			Utlity.ResponseWrite(result, dataType, response);
			return;
		}
		if (!Utlity.isMobileNO(loginName)) {
			result.init(FAIL_STATUS, "请输入正确的手机号码", null);
			Utlity.ResponseWrite(result, dataType, response);
			return;
		}
		if (smsCode == null || smsCode.equals("")) {
			result.init(FAIL_STATUS, "请输入验证码！", null);
			Utlity.ResponseWrite(result, dataType, response);
			return;
		}
		String uuid = (String) session.getAttribute("code");
		String sessionCode = null;
		Map<String, Object> params = new HashMap<>();
		params.put("uuid", uuid);
		List<MobileCode> mobileCode = mobileCodeService.getMobileCodeByParams(params);
		if (mobileCode != null && mobileCode.size() > 0) {
			sessionCode = mobileCode.get(0).getCode();
		}
		if (uuid == null || uuid.equals("")) {
			result.init(FAIL_STATUS, "请先获取短信验证码！", null);
			Utlity.ResponseWrite(result, dataType, response);
			return;
		}
		if((System.currentTimeMillis() - mobileCode.get(0).getCreatetime().getTime()) > 1200000) {//超时20分钟
			result.init(FAIL_STATUS, "验证码超时，请重新获取验证码！", null);
			Utlity.ResponseWrite(result, dataType, response);
			return;
		}
		if (sessionCode == null || !smsCode.equals(sessionCode)) {
			result.init(FAIL_STATUS, "验证码输入错误", null);
			Utlity.ResponseWrite(result, dataType, response);
			return;
		}
		InvigilationTeacher teacher = this.invigilationTeacherService.getTeacher(loginName);
		if (teacher == null) {// 用户不存在，请先注册
			result.init(FAIL_STATUS, "此用户不存在，请先注册！", null);
			Utlity.ResponseWrite(result, dataType, response);
			return;
		}
		if (teacher.getStatus() != Dictionary.USER_STATUS_NOMAL) {
			result.init(FAIL_STATUS, "该账户已停用！", null);
			Utlity.ResponseWrite(result, dataType, response);
			return;
		}
		// 保存会话状态
		session.setAttribute("teachersession", teacher);
		
		// 清除之前openid绑定的账号
		InvigilationTeacher tea = this.invigilationTeacherService.getInvigilationTeacherInfoByOpenID(openid);
		if (tea != null && !tea.getIdcard().equals(teacher.getIdcard())) {
			tea.setOpenID(null);
			this.invigilationTeacherService.update(tea);
		}
		//从新将openid绑定登录账号
		teacher.setOpenID(openid);
		this.invigilationTeacherService.update(teacher);
		// 清空验证码
		session.removeAttribute("code");
		String url = PATH + "XJ_wechat/" + pagename + ".html";
		System.out.println("登录成功后跳转：" + ConfigUtil.getCodeUrl(url));
		result.init(SUCCESS_STATUS, "登录成功，正在努力为您加载页面！", ConfigUtil.getCodeUrl(url));
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	public void ReGetCode(String url) {// + request.getServerName()
		try {
			System.out.println("当前url:" + url);
			// 先要判断是否是获取code后跳转过来的
			// Code为空时，先获取Code
			String getCodeUrls;
			getCodeUrls = ConfigUtil.getCodeUrl(url);

			response.sendRedirect(getCodeUrls);//// 先跳转到微信的服务器，取得code后会跳
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String GetOpenId(String code) {
		String openid = "";
		openid = ConfigUtil.getOauthAccessOpenId(code);// 重新取得用户的openid
		return openid;
	}

	/**
	 * pdf 打印 教师执考通知单
	 */
	public void ToPdf() {
		ActionResult result = new ActionResult();
		try {
			int id = getIntValue(request.getParameter("id"));
			ExamTeacherRoom etr = this.examTeacherRoomService.getById(id);
			String path = request.getSession().getServletContext().getRealPath("/").replace("\\", "/");
			String TemplatePDF = path + "model/modelPDF.pdf";

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);

			PdfReader reader = new PdfReader(TemplatePDF);
			PdfStamper stamp = new PdfStamper(reader, baos);

			AcroFields form = stamp.getAcroFields();
			form.addSubstitutionFont(bfChinese);

			form.setField("examname", etr.getExam().getName());
			form.setField("teachername", etr.getTeacher().getName());
			form.setField("sex", etr.getTeacher().getSex() == 1 ? "男" : "女");
			String intgral = etr.getTeacher().getIntgral() + "";
			form.setField("integral", Utlity.checkStringNull(intgral) ? "无" : intgral);

			// 头像位置
			int pageNo = form.getFieldPositions("photo").get(0).page;
			Rectangle signRect = form.getFieldPositions("photo").get(0).position;
			float x = signRect.getLeft();
			float y = signRect.getBottom();

			try {
				// 读图片
				Image image = Image.getInstance(path + etr.getTeacher().getPhoto().getSourcePath());
				// 获取操作的页面
				PdfContentByte under = stamp.getOverContent(pageNo);
				// 根据域的大小缩放图片
				image.scaleToFit(signRect.getWidth(), signRect.getHeight());
				// 添加图片
				image.setAbsolutePosition(x, y);
				under.addImage(image);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 证件照
			int pageNoIdCard = form.getFieldPositions("idcard").get(0).page;
			Rectangle signRectIdCard = form.getFieldPositions("idcard").get(0).position;
			float xIdCard = signRectIdCard.getLeft();
			float yIdCard = signRectIdCard.getBottom();
			try {
				Image imgIdCard = Image.getInstance(path + etr.getTeacher().getIdCardPhoto().getSourcePath());
				// 获取操作的页面
				PdfContentByte underIdCard = stamp.getOverContent(pageNoIdCard);
				// 根据域的大小缩放图片
				imgIdCard.scaleToFit(signRectIdCard.getWidth(), signRectIdCard.getHeight());
				// 添加图片
				imgIdCard.setAbsolutePosition(xIdCard, yIdCard);
				underIdCard.addImage(imgIdCard);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 查询历史监考记录
			ExamRoom room = etr.getRoom();
			String examnationInformation = "";
			String examnationTime = "";
			String arrivaltime = "";
			if (room.getExamRoomInfo() != null) {
				List<ExamRoomInfo> userList = JSON.parseArray(room.getExamRoomInfo(), ExamRoomInfo.class);
				for (ExamRoomInfo examRoomInfo : userList) {
					examnationInformation += examRoomInfo.getExamnationInformation() + "\n";
					examnationTime += examRoomInfo.getExamnationTime() + "\n";
					arrivaltime += examRoomInfo.getArrivaltime() + "\n";
				}
			}

			// String[] examnationInformationArr =
			// room.getExamnationInformation().split(";");
			// String[] examnationTimeArr = room.getExamnationTime().split(";");
			// String[] arrivaltimeArr = room.getArrivaltime().split(";");

			form.setField("index", room.getRoomIndex());
			form.setField("address", room.getRoomAddress());
			form.setField("time", examnationTime);
			form.setField("information", examnationInformation);// 监考科目
			form.setField("arrivaltime", arrivaltime);// 到岗时间

			// 监考注意事项
		    String invigilationNotice = room.getInvigilationNotice();
		    if(Utlity.checkStringNull(invigilationNotice)){
		    	invigilationNotice =etr.getExam().getInvigilationNotice();
		    }
		    String formatContract = HtmlHelper.parseString2Html(HtmlHelper.strDscape(invigilationNotice));
			form.setField("contract",formatContract);

			stamp.setFormFlattening(true); // 千万不漏了这句啊, */
			stamp.close();

			response.reset();
			response.setContentType("application/pdf");
			String filename = etr.getTeacher().getName() + "-执考通知单";
			response.setHeader("Content-disposition",
					"attachment;filename=" + new String(filename.getBytes(), "iso-8859-1") + ".pdf");
			OutputStream ouputStream = response.getOutputStream();

			Document doc = new Document();
			PdfCopy pdfCopy = new PdfCopy(doc, ouputStream);
			doc.open();

			PdfImportedPage impPage = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
			pdfCopy.addPage(impPage);

			doc.close();// 当文件拷贝 记得关闭doc
			result.init(SUCCESS_STATUS, "下载完成", null);
		} catch (Exception e) {
			e.printStackTrace();
			result.init(FAIL_STATUS, "异常", null);
		}

		Utlity.ResponseWrite(result, dataType, response);
	}

	private String noticeCurrentExam(String toUserName, String fromUserName) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("statusNormal", 1);// 未结束的考试
		try {

			List<ExamInformation> list = this.examInformationService.searchExamInformation(searchMap, null, -1, -1);
			if (list != null && list.size() > 0) {
				ExamInformation information = list.get(0);
				if (information.getStatus() == 1) {
					System.out.println("考试" + information.getName());
					return MessageUtil.initNewsMessage(toUserName, fromUserName, information);
				}
			}
			return MessageUtil.initText(toUserName, fromUserName, "当前暂无考试~");
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.initText(toUserName, fromUserName, "当前暂无考试~");
		}
	}

	public void CreateMenu() throws ParseException, IOException {
		// 菜单
		String menu = JSONObject.toJSONString(ConfigUtil.initMenu());
		ConfigUtil.createMenu(CommonUtil.getAccessToken().getAccessToken(), menu);
	}
}
