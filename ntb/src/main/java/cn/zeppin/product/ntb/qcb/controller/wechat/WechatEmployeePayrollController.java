/**
 * 
 */
package cn.zeppin.product.ntb.qcb.controller.wechat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayroll;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollFeedback;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollFeedback.QcbCompanyPayrollFeedbackStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollRecords;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollRecords.QcbCompanyPayrollRecordsStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyPayrollFeedbackService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyPayrollRecordsService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyPayrollService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.ntb.qcb.vo.QcbEmployeePayrollRecordsVO;

/**
 * @author hehe
 *
 * 微信企财宝员工福利金接口
 */

@Controller
@RequestMapping(value = "/qcbWechat/payroll")
public class WechatEmployeePayrollController extends BaseController {
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	@Autowired
	private IQcbCompanyPayrollRecordsService qcbCompanyPayrollRecordsService;
	
	@Autowired
	private IQcbCompanyPayrollFeedbackService qcbCompanyPayrollFeedbackService;
	
	@Autowired
	private IQcbCompanyPayrollService qcbCompanyPayrollService;

	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	/**
	 * 获取福利金列表（按年）
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Result list(HttpServletRequest request) {
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee employee = (QcbEmployee)request.getAttribute("employee");
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbEmployee", employee.getUuid());
		List<Entity> list = this.qcbCompanyPayrollRecordsService.getListForWebPage(inputParams, -1, -1, null, QcbCompanyPayrollRecords.class);
		List<Map<String, Object>> listYear = new ArrayList<Map<String,Object>>();
		List<QcbEmployeePayrollRecordsVO> listvo = new ArrayList<QcbEmployeePayrollRecordsVO>();
		if(list != null && !list.isEmpty()){
			for(Entity entity : list){
				QcbCompanyPayrollRecords qcpr = (QcbCompanyPayrollRecords)entity;
				QcbCompanyPayroll qcp = this.qcbCompanyPayrollService.get(qcpr.getQcbCompanyPayroll());
				QcbEmployeePayrollRecordsVO vo = new QcbEmployeePayrollRecordsVO(qcpr);
				if(qcp != null){
					vo.setTitle(qcp.getTitle());
					QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcp.getQcbCompany());
					if(qca != null){
						vo.setQcbCompanyName(qca.getName());
					}
				}
				listvo.add(vo);
			}
		}
		
		for(QcbEmployeePayrollRecordsVO vo : listvo){
			if(listYear.isEmpty()){
				Map<String, Object> data = new HashMap<String, Object>();
				List<QcbEmployeePayrollRecordsVO> childlistvo = new ArrayList<QcbEmployeePayrollRecordsVO>();
				childlistvo.add(vo);
				data.put("time", vo.getTime());
				data.put("dataList", childlistvo);
				listYear.add(data);
			} else {
				boolean flag = false;
				for(Map<String, Object> map : listYear){
					if(map.get("time") != null && vo.getTime().equals(map.get("time").toString())){
						flag = true;
						List<QcbEmployeePayrollRecordsVO> childlistvo = (List<QcbEmployeePayrollRecordsVO>) map.get("dataList");
						childlistvo.add(vo);
						map.put("dataList", childlistvo);
					}
				}
				if(!flag){
					Map<String, Object> data = new HashMap<String, Object>();
					List<QcbEmployeePayrollRecordsVO> childlistvo = new ArrayList<QcbEmployeePayrollRecordsVO>();
					childlistvo.add(vo);
					data.put("time", vo.getTime());
					data.put("dataList", childlistvo);
					listYear.add(data);
				}
			}
		}
		
		return ResultManager.createDataResult(listYear, listYear.size());
	}
	
	/**
	 * 获取用户福利金详情信息
	 * @param uuid
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="福利金编号", required = true)
	@ResponseBody
	public Result get(String uuid, HttpServletRequest request) {
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		
		QcbCompanyPayrollRecords qcpr = this.qcbCompanyPayrollRecordsService.get(uuid);
		if(qcpr == null){
			return ResultManager.createFailResult("福利金信息不存在！");
		}
		
		QcbEmployee employee = (QcbEmployee)request.getAttribute("employee");
		if(!qcpr.getQcbEmployee().equals(employee.getUuid())) {
			return ResultManager.createFailResult("福利金信息错误！");
		}
		QcbCompanyPayroll qcp = this.qcbCompanyPayrollService.get(qcpr.getQcbCompanyPayroll());
		QcbEmployeePayrollRecordsVO vo = new QcbEmployeePayrollRecordsVO(qcpr);
		if(qcp != null){
			vo.setTitle(qcp.getTitle());
			vo.setColumn(qcp.getColumnData());
			
			QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcp.getQcbCompany());
			if(qca != null){
				vo.setQcbCompanyName(qca.getName());
			}
		}
		return ResultManager.createDataResult(vo);
	}
	
	/**
	 * 福利金确认
	 * @param uuid
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="福利金编号", required = true)
	@ResponseBody
	public Result confirm(String uuid, HttpServletRequest request) {
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		
		QcbCompanyPayrollRecords qcpr = this.qcbCompanyPayrollRecordsService.get(uuid);
		if(qcpr == null){
			return ResultManager.createFailResult("福利金信息不存在！");
		}
		
		QcbEmployee employee = (QcbEmployee)request.getAttribute("employee");
		if(!qcpr.getQcbEmployee().equals(employee.getUuid())) {
			return ResultManager.createFailResult("福利金信息错误！");
		}
		qcpr.setStatus(QcbCompanyPayrollRecordsStatus.CONFIRM);
		this.qcbCompanyPayrollRecordsService.update(qcpr);
		
		return ResultManager.createSuccessResult("确认成功！");
	}
	
	/**
	 * 福利金反馈
	 * @param uuid
	 * @param content
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="福利金编号", required = true)
	@ActionParam(key = "content", type = DataType.STRING, message="反馈内容", required = true, maxLength = 500)
	@ResponseBody
	public Result feedback(String uuid, String content, HttpServletRequest request) {
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		
		QcbCompanyPayrollRecords qcpr = this.qcbCompanyPayrollRecordsService.get(uuid);
		if(qcpr == null){
			return ResultManager.createFailResult("福利金信息不存在！");
		}
		
		QcbEmployee employee = (QcbEmployee)request.getAttribute("employee");
		if(!qcpr.getQcbEmployee().equals(employee.getUuid())) {
			return ResultManager.createFailResult("福利金条信息错误！");
		}
		
		QcbCompanyPayrollFeedback qcpf = new QcbCompanyPayrollFeedback();
		qcpf.setUuid(UUID.randomUUID().toString());
		qcpf.setContent(content);
		qcpf.setQcbCompanyPayroll(qcpr.getQcbCompanyPayroll());
		qcpf.setQcbCompanyPayrollRecords(qcpr.getUuid());
		qcpf.setStatus(QcbCompanyPayrollFeedbackStatus.UNREAD);
		qcpf.setCreator(employee.getUuid());
		qcpf.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		this.qcbCompanyPayrollFeedbackService.insert(qcpf);
		
		return ResultManager.createSuccessResult("反馈成功！");
	}
	
	/**
	 * 福利金反馈列表
	 * @param uuid
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/feedbackList", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="福利金编号", required = true)
	@ResponseBody
	public Result feedbackList(String uuid, HttpServletRequest request) {
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		
		QcbCompanyPayrollRecords qcpr = this.qcbCompanyPayrollRecordsService.get(uuid);
		if(qcpr == null){
			return ResultManager.createFailResult("福利金信息不存在！");
		}
		
		QcbEmployee employee = (QcbEmployee)request.getAttribute("employee");
		if(!qcpr.getQcbEmployee().equals(employee.getUuid())) {
			return ResultManager.createFailResult("福利金条信息错误！");
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbCompanyPayrollRecords", qcpr.getUuid());
		List<Entity> list = this.qcbCompanyPayrollFeedbackService.getListForPage(inputParams, -1, -1, null, QcbCompanyPayrollFeedback.class);
		return ResultManager.createDataResult(list, list.size());
	}
}
