/**
 * 
 */
package cn.zeppin.product.ntb.qcb.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperate;
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperate.QcbCompanyOperateStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperateCheck;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyOperateCheckService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyOperateService;
import cn.zeppin.product.ntb.qcb.vo.AdminVO;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyOperateCheckVO;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyOperateVO;

/**
 * @author hehe
 *
 * 企财宝企业账户信息
 */

@Controller
@RequestMapping(value = "/qcb/companyOperate")
public class QcbCompanyOperateController extends BaseController {
	
	@Autowired
	private IQcbCompanyOperateService qcbCompanyOperateService;
	
	@Autowired
	private IQcbCompanyOperateCheckService qcbCompanyOperateCheckService;
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbAdminService qcbAdminService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IResourceService resourceService;
	
	/**
	 * 获取企业认证信息
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Result get() {		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//校验企业是否存在
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(admin.getQcbCompany());
		if(qca == null){
			return ResultManager.createFailResult("企业不正确！");
		}
		
		//获取企业认证信息
		QcbCompanyOperate qco = this.qcbCompanyOperateService.getByQcbCompany(qca.getUuid());
		if (qco == null) {
			return ResultManager.createFailResult("该企业不存在认证申请信息！");
		}
		
		//界面返回封装对象
		QcbCompanyOperateVO qcoVO = new QcbCompanyOperateVO(qco);
		
//		Resource bl = this.resourceService.get(qcoVO.getBusinessLicence());
//		if(bl != null){
//			qcoVO.setBusinessLicenceUrl(bl.getUrl());
//		}
//		
//		Resource ev = this.resourceService.get(qcoVO.getEvidence());
//		if(ev != null){
//			qcoVO.setEvidenceUrl(ev.getUrl());
//		}
//		
//		Resource idf = this.resourceService.get(qcoVO.getIdcardFace());
//		if(idf != null){
//			qcoVO.setIdcardFaceUrl(idf.getUrl());
//		}
//		
//		Resource idb = this.resourceService.get(qcoVO.getIdcardBack());
//		if(idb != null){
//			qcoVO.setIdcardBackUrl(idb.getUrl());
//		}
		
		QcbAdmin qa = this.qcbAdminService.get(qcoVO.getCreator());
		if(qa != null){
			qcoVO.setCreatorName(qa.getName());
		}
		
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("qcbCompanyOperate", qcoVO.getUuid());
		
		List<Entity> list = this.qcbCompanyOperateCheckService.getListForPage(searchMap, -1, -1, null, QcbCompanyOperateCheck.class);
				
		List<QcbCompanyOperateCheckVO> checkList = new ArrayList<QcbCompanyOperateCheckVO>();
		for(Entity e : list){
			QcbCompanyOperateCheck qcoc = (QcbCompanyOperateCheck) e;
			QcbCompanyOperateCheckVO qcocVO = new QcbCompanyOperateCheckVO(qcoc);
			
			BkOperator bo = this.bkOperatorService.get(qcocVO.getCreator());
			if(bo != null){
				qcocVO.setCreatorName(bo.getRealname());
			}
			checkList.add(qcocVO);
		}
//		qcoVO.setCheckList(checkList);
		
		return ResultManager.createDataResult(qcoVO);
	}
	
	/**
	 * 企业认证
	 * @param businessLicence
	 * @param evidence
	 * @param idcardFace
	 * @param idcardBack
	 * @return
	 */
	@RequestMapping(value = "/operate", method = RequestMethod.POST)
	@ActionParam(key = "businessLicence", message="营业执照", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "evidence", message="证明材料", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "idcardFace", message="身份证正面", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "idcardBack", message="身份证反面", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result operate(String businessLicence, String evidence, String idcardFace, String idcardBack){
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//校验企业是否存在
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(admin.getQcbCompany());
		if(qca == null){
			return ResultManager.createFailResult("企业不正确！");
		}
		
		if(QcbCompanyAccountStatus.DELETED.equals(qca.getStatus())){
			return ResultManager.createFailResult("企业已被注销！");
		}
		
		if(this.resourceService.get(businessLicence) == null){
			return ResultManager.createFailResult("营业执照有误！");
		}
		
		if(this.resourceService.get(evidence) == null){
			return ResultManager.createFailResult("证明材料有误！");
		}
		
		if(this.resourceService.get(idcardFace) == null){
			return ResultManager.createFailResult("身份证正面有误！");
		}
		
		if(this.resourceService.get(idcardBack) == null){
			return ResultManager.createFailResult("身份证反面有误！");
		}
		
		QcbCompanyOperate qco = this.qcbCompanyOperateService.getByQcbCompany(qca.getUuid());
		
		if(qco == null){
			qco = new QcbCompanyOperate();
			qco.setUuid(UUID.randomUUID().toString());
			qco.setQcbCompany(admin.getQcbCompany());
//			qco.setBusinessLicence(businessLicence);
//			qco.setEvidence(evidence);
//			qco.setIdcardFace(idcardFace);
//			qco.setIdcardBack(idcardBack);
			qco.setStatus(QcbCompanyOperateStatus.UNCHECKED);
			qco.setCreator(admin.getUuid());
			qco.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			this.qcbCompanyOperateService.qcbCompanyInsert(qco);
		}else{
//			qco.setBusinessLicence(businessLicence);
//			qco.setEvidence(evidence);
//			qco.setIdcardFace(idcardFace);
//			qco.setIdcardBack(idcardBack);
			qco.setStatus(QcbCompanyOperateStatus.UNCHECKED);
			qco.setCreator(admin.getUuid());
			qco.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			this.qcbCompanyOperateService.qcbCompanyUpdate(qco);
		}
		
		return ResultManager.createSuccessResult("提交成功！");
	}
}
