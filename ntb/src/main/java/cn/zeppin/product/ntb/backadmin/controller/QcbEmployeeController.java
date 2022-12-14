package cn.zeppin.product.ntb.backadmin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IOrderinfoByThirdpartyService;
import cn.zeppin.product.ntb.backadmin.service.api.IPlatformAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.QcbEmployeeHistoryVO;
import cn.zeppin.product.ntb.backadmin.vo.QcbEmployeeVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard.QcbEmployeeBankcardStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeBankcardService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeProductBuyService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.utility.Utlity;

/**
 * ????????????????????????
 * 
 **/

@Controller
@RequestMapping(value = "/backadmin/employee")
public class QcbEmployeeController extends BaseController{
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private IOrderinfoByThirdpartyService orderinfoByThirdpartyService;
	
	@Autowired
	private IPlatformAccountService platformAccountService;
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	@Autowired
	private IQcbEmployeeHistoryService qcbEmployeeHistoryService;
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbEmployeeBankcardService qcbEmployeeBankcardService;
	
	@Autowired
	private IQcbEmployeeProductBuyService qcbEmployeeProductBuyService; 
	
	/**
	 * ????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "uuid", required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		
		QcbEmployee employee = qcbEmployeeService.get(uuid);
		if(employee == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		QcbEmployeeVO vo = new QcbEmployeeVO(employee);
		//??????????????????
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbEmployee", employee.getUuid());
		inputParams.put("status", QcbEmployeeBankcardStatus.NORMAL);
		Integer count = this.qcbEmployeeBankcardService.getCount(inputParams);
		if(count > 0){
			vo.setBindingBankcardFlag(true);
		} else {
			vo.setBindingBankcardFlag(false);
		}
		return ResultManager.createDataResult(vo);
	}  
	
	/**
	 * ?????????????????????????????????????????? 
	 * @param name
	 * @param role
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "qcbCompany", message = "????????????????????????", type = DataType.STRING)
	@ActionParam(key = "accountBalance", message = "???????????????", type = DataType.STRING)
	@ActionParam(key = "status", message = "??????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message = "????????????", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String qcbCompany, String accountBalance, String status, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		if(name != null && !"".equals(name)){
			searchMap.put("name", name);
		}
		
		if(!Utlity.checkStringNull(qcbCompany)){
			searchMap.put("qcbCompany", qcbCompany);
		}
		
		if(status != null && !"".equals(status) && !"all".equals(status)){
			searchMap.put("status", status);
		}
		
		if(accountBalance != null && "1".equals(accountBalance)){
			searchMap.put("accountBalance", accountBalance);
		}
		
		List<QcbEmployeeVO> listvo = new ArrayList<QcbEmployeeVO>();
		Integer totalResultCount = this.qcbEmployeeService.getCountForNTB(searchMap);
		List<Entity> list = qcbEmployeeService.getListForNTBPage(searchMap, pageNum, pageSize, sorts, QcbEmployee.class);
		if(list != null && !list.isEmpty()){
			for(Entity entity : list){
				QcbEmployee employee = (QcbEmployee)entity;
				QcbEmployeeVO vo = new QcbEmployeeVO(employee);
				//??????????????????
				Map<String, String> inputParams = new HashMap<String, String>();
				inputParams.put("qcbEmployee", employee.getUuid());
				inputParams.put("status", QcbEmployeeBankcardStatus.NORMAL);
				Integer count = this.qcbEmployeeBankcardService.getCount(inputParams);
				if(count > 0){
					vo.setBindingBankcardFlag(true);
				} else {
					vo.setBindingBankcardFlag(false);
				}
				listvo.add(vo);
			}
		}
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ??????????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ActionParam(key = "qcbCompany", message = "????????????????????????", type = DataType.STRING)
	@ResponseBody
	public Result statusList(String qcbCompany) {
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!Utlity.checkStringNull(qcbCompany)){
			searchMap.put("qcbCompany", qcbCompany);
		}
		List<Entity> list = this.qcbEmployeeService.getStatusListForNTB(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	
	/**
	 * ??????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/getBill", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="????????????", required = true)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "name", message = "????????????", type = DataType.STRING)
	@ResponseBody
	public Result getBill(String uuid, Integer pageNum, Integer pageSize, String name){
		QcbEmployee employee = qcbEmployeeService.get(uuid);
		if(employee == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		if(name != null && !"".equals(name)){
			inputParams.put("name", name);
		}
		inputParams.put("qcbEmployee", employee.getUuid());
		Integer totalResultCount = this.qcbEmployeeHistoryService.getCount(inputParams);
		List<Entity> list = this.qcbEmployeeHistoryService.getListForPage(inputParams, pageNum, pageSize, null, QcbEmployeeHistory.class);
		
		List<QcbEmployeeHistoryVO> listvo = new ArrayList<QcbEmployeeHistoryVO>();
		if(list != null && !list.isEmpty()){
			for(Entity entity : list){
				QcbEmployeeHistory qcpr = (QcbEmployeeHistory)entity;
				QcbEmployeeHistoryVO vo = new QcbEmployeeHistoryVO(qcpr);
				
				if (QcbEmployeeHistoryType.BUY.equals(qcpr.getType()) || QcbEmployeeHistoryType.DIVIDEND.equals(qcpr.getType()) 
						|| QcbEmployeeHistoryType.RETURN.equals(qcpr.getType())) {
					if(qcpr.getProductType() != null){//&& Utlity.PAY_TYPE_BANKCARD.equals(qcpr.getProductType())
						inputParams.clear();
						inputParams.put("product", qcpr.getProduct());
						inputParams.put("qcbEmployee", qcpr.getQcbEmployee());
						List<Entity> listProductRecords = this.qcbEmployeeProductBuyService.getListForPage(inputParams, -1, -1, null, QcbEmployeeProductBuy.class);
						String remark = "";
						if(listProductRecords != null && listProductRecords.size() > 0){
							QcbEmployeeProductBuy qepb = (QcbEmployeeProductBuy) listProductRecords.get(0);
							if(Utlity.PAY_TYPE_BANKCARD.equals(qcpr.getProductType())){
								BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(qepb.getProduct());
								Bank bank = this.bankService.get(bfpp.getCustodian());
								remark = "???"+bank.getShortName()+"???"+bfpp.getShortname();
							}
							vo.setRemark(vo.getRemark()+remark);
						}
					}
				}
				listvo.add(vo);
			}
		}
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ????????????
	 * @param uuid
	 * @param billid
	 * @return
	 */
	@RequestMapping(value = "/getBillInfo", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="????????????", required = true)
	@ActionParam(key = "billid", type = DataType.STRING, message="????????????", required = true)
	@ResponseBody
	public Result getBillInfo(String uuid, String billid){
		
		QcbEmployee employee = qcbEmployeeService.get(uuid);
		if(employee == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		QcbEmployeeHistory qcpr = this.qcbEmployeeHistoryService.get(billid);
		if(qcpr == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		if(!uuid.equals(qcpr.getQcbEmployee())){
			return ResultManager.createFailResult("?????????????????????");
		}
		
		QcbEmployeeHistoryVO vo = new QcbEmployeeHistoryVO(qcpr);
		
		if (QcbEmployeeHistoryType.BUY.equals(qcpr.getType()) || QcbEmployeeHistoryType.DIVIDEND.equals(qcpr.getType()) 
				|| QcbEmployeeHistoryType.RETURN.equals(qcpr.getType())) {
			if(qcpr.getProductType() != null){//&& Utlity.PAY_TYPE_BANKCARD.equals(qcpr.getProductType())
				Map<String, String> inputParams = new HashMap<String, String>();
				inputParams.clear();
				inputParams.put("product", qcpr.getProduct());
				inputParams.put("qcbEmployee", qcpr.getQcbEmployee());
				List<Entity> listProductRecords = this.qcbEmployeeProductBuyService.getListForPage(inputParams, -1, -1, null, QcbEmployeeProductBuy.class);
				String remark = "";
				if(listProductRecords != null && listProductRecords.size() > 0){
					QcbEmployeeProductBuy qepb = (QcbEmployeeProductBuy) listProductRecords.get(0);
					if(Utlity.PAY_TYPE_BANKCARD.equals(qcpr.getProductType())){
						BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(qepb.getProduct());
						Bank bank = this.bankService.get(bfpp.getCustodian());
						remark = "???"+bank.getShortName()+"???"+bfpp.getShortname();
					}
					vo.setRemark(vo.getRemark()+remark);
				}
			}
		}
		return ResultManager.createDataResult(vo, "????????????");
	}
	
}
