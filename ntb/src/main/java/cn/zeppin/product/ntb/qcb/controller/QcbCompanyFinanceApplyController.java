/**
 * 
 */
package cn.zeppin.product.ntb.qcb.controller;

import java.sql.Timestamp;
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

import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyFinanceApply;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountFinanceStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyFinanceApply.QcbCompanyFinanceApplyStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyFinanceApplyService;
import cn.zeppin.product.ntb.qcb.vo.AdminVO;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyFinanceApplyVO;

/**
 * @author hehe
 *
 * 企财宝管理员信息管理
 */

@Controller
@RequestMapping(value = "/qcb/finance")
public class QcbCompanyFinanceApplyController extends BaseController {
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbAdminService qcbAdminService; 
	
	@Autowired
	private IQcbCompanyFinanceApplyService qcbCompanyFinanceApplyService;
	
	/**
	 * 获取一财税服务申请信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Result get() {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		
		String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(company)){
			return ResultManager.createFailResult("无权查看该企业管理员信息！");
		}
		
		QcbCompanyAccount ca = this.qcbCompanyAccountService.get(company);
		if(ca == null){
			return ResultManager.createFailResult("企业信息错误！");
		}
		
		//查询当前企业是否发起过财税服务申请
		QcbCompanyFinanceApplyVO  qcfavo = new QcbCompanyFinanceApplyVO();
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbCompany", ca.getUuid());
		List<Entity> list = this.qcbCompanyFinanceApplyService.getListForPage(inputParams, -1, -1, null, QcbCompanyFinanceApply.class);
		if(list != null && !list.isEmpty()){
			for(Entity entity : list){
				QcbCompanyFinanceApply qcfa = (QcbCompanyFinanceApply)entity;
				qcfavo = new QcbCompanyFinanceApplyVO(qcfa);
				QcbAdmin qa = this.qcbAdminService.get(qcfa.getCreator());
				if(qa != null){
					qcfavo.setCreatorName(qa.getName());
				}
			}
		}
		
		return ResultManager.createDataResult(qcfavo);
	}
	
	/**
	 * 添加一财税服务申请信息
	 * 
	 * @param name
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "contacts", message="联系人", type = DataType.STRING, required = true)
	@ActionParam(key = "contactsMobile", message="联系电话", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(String contacts, String contactsMobile) {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		
		String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(company)){
			return ResultManager.createFailResult("无权操作该企业管理员信息！");
		}
		
		QcbCompanyAccount companyAccount = this.qcbCompanyAccountService.get(company);
		if(companyAccount == null){
			return ResultManager.createFailResult("企业信息错误！");
		}
		
		//判断是否已经申请过
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbCompany", companyAccount.getUuid());
		Integer count = this.qcbCompanyFinanceApplyService.getCount(inputParams);
		if(count > 0){
			return ResultManager.createFailResult("企业已申请过财税服务，不能重复申请！");
		}
		
		QcbCompanyFinanceApply qcfa = new QcbCompanyFinanceApply();
		qcfa.setUuid(UUID.randomUUID().toString());
		qcfa.setCreatetime(new Timestamp(System.currentTimeMillis()));
		qcfa.setCreator(qcbAdmin.getUuid());
		qcfa.setStatus(QcbCompanyFinanceApplyStatus.UNCHECKED);
		qcfa.setContacts(contacts);
		qcfa.setContactsMobile(contactsMobile);
		qcfa.setQcbCompany(companyAccount.getUuid());
		
		companyAccount.setFinanceStatus(QcbCompanyAccountFinanceStatus.UNCHECK);
		try {
			this.qcbCompanyFinanceApplyService.insert(qcfa, companyAccount);
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createSuccessResult("操作异常！");
		}
		
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 修改编辑
	 * @param uuid
	 * @param role
	 * @param authority
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "contacts", message="联系人", type = DataType.STRING, required = true)
	@ActionParam(key = "contactsMobile", message="联系电话", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(String uuid, String contacts, String contactsMobile) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		
		String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(company)){
			return ResultManager.createFailResult("无权操作该企业管理员信息！");
		}
		
		QcbCompanyAccount companyAccount = this.qcbCompanyAccountService.get(company);
		if(companyAccount == null){
			return ResultManager.createFailResult("企业信息错误！");
		}
		
		QcbCompanyFinanceApply qcfa = this.qcbCompanyFinanceApplyService.get(uuid);
		if(qcfa == null){
			return ResultManager.createFailResult("信息不存在！");
		}
		
		if(QcbCompanyFinanceApplyStatus.DELETED.equals(qcfa.getStatus())){
			return ResultManager.createFailResult("信息不存在！");
		}
		
		if(QcbCompanyFinanceApplyStatus.UNCHECKED.equals(qcfa.getStatus())){
			qcfa.setContacts(contacts);
			qcfa.setContactsMobile(contactsMobile);
			this.qcbCompanyFinanceApplyService.update(qcfa);
			return ResultManager.createSuccessResult("保存成功！");
		} else {
			return ResultManager.createFailResult("信息在审核中或已审核完成，不能修改！");
		}
	}
	
	/**
	 * 删除
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result delete(String uuid) {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		
		String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(company)){
			return ResultManager.createFailResult("无权操作该企业管理员信息！");
		}
		
		QcbCompanyAccount companyAccount = this.qcbCompanyAccountService.get(company);
		if(companyAccount == null){
			return ResultManager.createFailResult("企业信息错误！");
		}
		
		QcbCompanyFinanceApply qcfa = this.qcbCompanyFinanceApplyService.get(uuid);
		if(qcfa == null){
			return ResultManager.createFailResult("信息不存在！");
		}
		
		if(QcbCompanyFinanceApplyStatus.DELETED.equals(qcfa.getStatus())){
			return ResultManager.createFailResult("信息不存在！");
		}
		
		if(QcbCompanyFinanceApplyStatus.UNCHECKED.equals(qcfa.getStatus())){
			this.qcbCompanyFinanceApplyService.delete(qcfa);
			return ResultManager.createSuccessResult("删除成功！");
		} else {
			return ResultManager.createFailResult("信息在审核中或已审核完成，不能删除！");
		}
	}
}
