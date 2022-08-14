/**
 * 
 */
package cn.zeppin.product.ntb.qcb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayroll;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollFeedback;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollRecords;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyPayrollFeedbackService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyPayrollRecordsService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyPayrollService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.ntb.qcb.vo.AdminVO;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyPayrollRecordsVO;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * 企财宝企业发放薪资
 */

@Controller
@RequestMapping(value = "/qcb/companyPayrollRecords")
public class QcbCompanyPayrollRecordsController extends BaseController {
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	@Autowired
	private IQcbCompanyPayrollService qcbCompanyPayrollService;
	
	@Autowired
	private IQcbCompanyPayrollRecordsService qcbCompanyPayrollRecordsService;
	
	@Autowired
	private IQcbCompanyPayrollFeedbackService qcbCompanyPayrollFeedbackService;
	
	@Autowired
	private IQcbAdminService qcbAdminService;
	
	/**
	 * 查询企财宝企业薪资发放记录列表
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "qcbCompanyPayroll", message="薪资发放记录", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String qcbCompanyPayroll, String status, Integer pageNum, Integer pageSize, String sorts) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//获取企业福利金发放记录
		QcbCompanyPayroll qcp = this.qcbCompanyPayrollService.get(qcbCompanyPayroll);
		if (qcp == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		if(!qcp.getQcbCompany().equals(admin.getQcbCompany())){
			return ResultManager.createFailResult("您无权查看此记录！");
		}
				
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("qcbCompanyPayroll", qcbCompanyPayroll);
		searchMap.put("status", status);
		
		//查询符合条件的信息的总数
		Integer totalResultCount = qcbCompanyPayrollRecordsService.getCount(searchMap);
		//查询符合条件的信息列表
		List<Entity> list = qcbCompanyPayrollRecordsService.getListForPage(searchMap, pageNum, pageSize, sorts, QcbCompanyPayrollRecords.class);
		
		List<QcbCompanyPayrollRecordsVO> listVO = new ArrayList<QcbCompanyPayrollRecordsVO>();
		
		for(Entity e : list){
			QcbCompanyPayrollRecords qcpr = (QcbCompanyPayrollRecords) e;
			QcbCompanyPayrollRecordsVO qcprVO = new QcbCompanyPayrollRecordsVO(qcpr);
			
			if(!Utlity.checkStringNull(qcprVO.getQcbCompany())){
				QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcprVO.getQcbCompany());
				if(qca != null){
					qcprVO.setQcbCompanyName(qca.getName());
				}
			}
			
			if(!Utlity.checkStringNull(qcprVO.getQcbEmployee())){
				QcbEmployee qe = this.qcbEmployeeService.get(qcprVO.getQcbEmployee());
				if(qe != null){
					qcprVO.setQcbEmployeeName(qe.getRealname());
					qcprVO.setQcbEmployeeIdcard(qe.getIdcard());
					qcprVO.setQcbEmployeeMobile(qe.getMobile());
				}
			}
			
			if(!Utlity.checkStringNull(qcprVO.getCreator())){
				QcbAdmin qa = this.qcbAdminService.get(qcprVO.getCreator());
				if(qa != null){
					qcprVO.setCreatorName(qa.getName());
				}
			}
			
			Map<String, String> searchCountMap = new HashMap<String, String>();
			searchCountMap.put("qcbCompanyPayrollRecords", qcprVO.getUuid());
			
			Integer feedbackCount = this.qcbCompanyPayrollFeedbackService.getCount(searchCountMap);
			qcprVO.setFeedbackCount(feedbackCount);
			
			listVO.add(qcprVO);
		}
		
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取企财宝企业薪资发放记录
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//获取企业福利金发放记录
		QcbCompanyPayrollRecords qcpr = this.qcbCompanyPayrollRecordsService.get(uuid);
		if (qcpr == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		if(!qcpr.getQcbCompany().equals(admin.getQcbCompany())){
			return ResultManager.createFailResult("您无权查看此记录！");
		}
		
		//界面返回封装对象
		QcbCompanyPayrollRecordsVO qcprVO = new QcbCompanyPayrollRecordsVO(qcpr);
		
		if(!Utlity.checkStringNull(qcprVO.getQcbCompany())){
			QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcprVO.getQcbCompany());
			if(qca != null){
				qcprVO.setQcbCompanyName(qca.getName());
			}
		}
		
		if(!Utlity.checkStringNull(qcprVO.getQcbEmployee())){
			QcbEmployee qe = this.qcbEmployeeService.get(qcprVO.getQcbEmployee());
			if(qe != null){
				qcprVO.setQcbEmployeeName(qe.getRealname());
				qcprVO.setQcbEmployeeIdcard(qe.getIdcard());
				qcprVO.setQcbEmployeeMobile(qe.getMobile());
			}
		}
		
		if(!Utlity.checkStringNull(qcprVO.getCreator())){
			QcbAdmin qa = this.qcbAdminService.get(qcprVO.getCreator());
			if(qa != null){
				qcprVO.setCreatorName(qa.getName());
			}
		}
		
		Map<String, String> searchCountMap = new HashMap<String, String>();
		searchCountMap.put("qcbCompanyPayrollRecords", qcprVO.getUuid());
		
		Integer feedbackCount = this.qcbCompanyPayrollFeedbackService.getCount(searchCountMap);
		qcprVO.setFeedbackCount(feedbackCount);
		
		return ResultManager.createDataResult(qcprVO);
	}
	
	/**
	 * 查询企财宝企业薪资发放记录反馈列表
	 * @param qcbCompanyPayrollRecords
	 * @return
	 */
	@RequestMapping(value = "/feedbackList", method = RequestMethod.GET)
	@ActionParam(key = "qcbCompanyPayrollRecords", message="薪资发放详情记录", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result feedbackList(String qcbCompanyPayrollRecords) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//获取企业福利金发放记录
		QcbCompanyPayrollRecords qcpr = this.qcbCompanyPayrollRecordsService.get(qcbCompanyPayrollRecords);
		if (qcpr == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		if(!qcpr.getQcbCompany().equals(admin.getQcbCompany())){
			return ResultManager.createFailResult("您无权查看此记录！");
		}
			
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("qcbCompanyPayrollRecords", qcpr.getUuid());
		
		List<Entity> list = this.qcbCompanyPayrollFeedbackService.getListForPage(searchMap, -1, -1, null, QcbCompanyPayrollFeedback.class);
		
		return ResultManager.createDataResult(list, list.size());
	}
}
