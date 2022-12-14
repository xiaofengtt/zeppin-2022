/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

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

import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBranchBankService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.backadmin.vo.QcbVirtualAccountsDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.QcbVirtualAccountsLessVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.BranchBank;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountVirtualAccountType;
import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts.QcbVirtualAccountsStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbVirtualAccountsService;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * ???????????????????????????
 */

@Controller
@RequestMapping(value = "/backadmin/qcbVirtualAccounts")
public class QcbVirtualAccountsController extends BaseController {
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbVirtualAccountsService qcbVirtualAccountsService;
	
	@Autowired
	private IQcbAdminService qcbAdminService;
	
	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IBranchBankService branchBankService;
	
	/**
	 * ???????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		
		List<Entity> list = qcbVirtualAccountsService.getStatusList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ?????????????????????????????????????????? 
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "status", message="??????", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "companyAccount", message="????????????", type = DataType.STRING, maxLength = 36)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result list(String status, String companyAccount, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(status)){
			searchMap.put("status", status);
			searchMap.put("companyAccount", companyAccount);
		}
		
		//????????????????????????????????????????????????
		Integer totalResultCount = qcbVirtualAccountsService.getCount(searchMap);
		//?????????????????????????????????????????????
		List<Entity> list = qcbVirtualAccountsService.getListForPage(searchMap, pageNum, pageSize, sorts, QcbVirtualAccounts.class);
		
		List<QcbVirtualAccountsLessVO> listvo = new ArrayList<QcbVirtualAccountsLessVO>();
		
		for(Entity e : list){
			QcbVirtualAccounts qva = (QcbVirtualAccounts) e;
			QcbVirtualAccountsLessVO qvaVO = new QcbVirtualAccountsLessVO(qva);
			
			CompanyAccount ca = this.companyAccountService.get(qvaVO.getCompanyAccount());
			if(ca != null){
				qvaVO.setCompanyAccountName(ca.getCompanyName());
				qvaVO.setCompanyAccountNum(ca.getAccountNum());
				
				if(!Utlity.checkStringNull(ca.getBranchBank())){
					BranchBank bb = this.branchBankService.get(ca.getBranchBank());
					if(bb != null){
						qvaVO.setBranchBankName(bb.getName());
					}
				}
			}
			
			if(!Utlity.checkStringNull(qvaVO.getQcbCompany())){
				QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qvaVO.getQcbCompany());
				if(qca != null){
					qvaVO.setQcbCompanyId(qca.getMerchantId());
					qvaVO.setQcbCompanyName(qca.getName());
				}
			}
			
			listvo.add(qvaVO);
		}
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ??????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		//????????????????????????
		QcbCompanyAccount qca = qcbCompanyAccountService.get(uuid);
		if (qca == null) {
			return ResultManager.createFailResult("??????????????????");
		}
		
		if(Utlity.checkStringNull(qca.getQcbVirtualAccounts())){
			return ResultManager.createFailResult("???????????????????????????????????????");
		}
		
		if(QcbCompanyAccountVirtualAccountType.VIRTUAL.equals(qca.getVirtualAccountType())){
			QcbVirtualAccounts qva = this.qcbVirtualAccountsService.get(qca.getQcbVirtualAccounts());
			if(qva == null){
				return ResultManager.createFailResult("???????????????????????????");
			}
			
			QcbVirtualAccountsDetailVO qvadVO = new QcbVirtualAccountsDetailVO(qva);
			qvadVO.setQcbCompany(uuid);
			qvadVO.setQcbCompanyId(qca.getMerchantId());
			qvadVO.setQcbCompanyName(qca.getName());
			
			CompanyAccount ca = this.companyAccountService.get(qvadVO.getCompanyAccount());
			if(ca == null){
				return ResultManager.createFailResult("??????????????????????????????");
			}
			qvadVO.setCompanyAccountName(ca.getCompanyName());
			qvadVO.setCompanyAccountNum(ca.getAccountNum());
			
			if(!Utlity.checkStringNull(ca.getBranchBank())){
				BranchBank bb = this.branchBankService.get(ca.getBranchBank());
				if(bb != null){
					qvadVO.setBranchBankName(bb.getName());
					qvadVO.setBranchBankAddress(bb.getAddress());
				}
			}
			
			QcbAdmin qa = this.qcbAdminService.get(qvadVO.getCreator());
			if(qa != null){
				qvadVO.setCreatorName(qa.getName());
			}
			return ResultManager.createDataResult(qvadVO);
		}else if(QcbCompanyAccountVirtualAccountType.REALLY.equals(qca.getVirtualAccountType())){
			CompanyAccount ca = this.companyAccountService.get(qca.getQcbVirtualAccounts());
			if(ca == null){
				return ResultManager.createFailResult("???????????????????????????");
			}
			QcbVirtualAccountsDetailVO qvadVO = new QcbVirtualAccountsDetailVO();
			qvadVO.setQcbCompany(qca.getUuid());
			qvadVO.setQcbCompanyId(qca.getMerchantId());
			qvadVO.setQcbCompanyName(qca.getName());
			
			qvadVO.setCompanyAccount(ca.getUuid());
			qvadVO.setCompanyAccountName(ca.getCompanyName());
			qvadVO.setCompanyAccountNum(ca.getAccountNum());
			qvadVO.setAccountNum("");
			if(!Utlity.checkStringNull(ca.getBranchBank())){
				BranchBank bb = this.branchBankService.get(ca.getBranchBank());
				if(bb != null){
					qvadVO.setBranchBankName(bb.getName());
					qvadVO.setBranchBankAddress(bb.getAddress());
				}
			}
			
			qvadVO.setCreatetime(ca.getCreatetime());
			qvadVO.setCreatetimeCN(Utlity.timeSpanToString(ca.getCreatetime()));
			qvadVO.setCreator(ca.getCreator());
			
			QcbAdmin qa = this.qcbAdminService.get(qvadVO.getCreator());
			if(qa != null){
				qvadVO.setCreatorName(qa.getName());
			}
			return ResultManager.createDataResult(qvadVO);
		}else{
			return ResultManager.createFailResult("??????????????????????????????");
		}
	}
	
	/**
	 * ??????????????????????????????
	 * @param companyAccount
	 * @param start
	 * @param end
	 * @return
	 */
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	@ActionParam(key = "companyAccount", message="????????????", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "start", message="?????????", type = DataType.STRING, required = true, maxLength = 6)
	@ActionParam(key = "end", message="?????????", type = DataType.STRING, required = true, maxLength = 6)
	@ResponseBody
	public Result batchAdd(String companyAccount, String start, String end) {
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		if(!Utlity.isInteger(start)){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		if(!Utlity.isInteger(end)){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		if(start.length() != end.length()){
			return ResultManager.createFailResult("??????????????????????????????????????????");
		}
		
		if(Integer.valueOf(start) > Integer.valueOf(end)){
			return ResultManager.createFailResult("?????????????????????????????????");
		}
		
		CompanyAccount ca = this.companyAccountService.get(companyAccount);
		if(ca == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		this.qcbVirtualAccountsService.batchAdd(companyAccount,start,end,currentOperator.getUuid());
		
		return ResultManager.createSuccessResult("?????????????????????");
	}
	
	/**
	 * ??????????????????????????????
	 * @param companyAccount
	 * @param start
	 * @param end
	 * @return
	 */
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	@ActionParam(key = "companyAccount", message="????????????", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "start", message="?????????", type = DataType.STRING, required = true, maxLength = 6)
	@ActionParam(key = "end", message="?????????", type = DataType.STRING, required = true, maxLength = 6)
	@ResponseBody
	public Result batchDelete(String companyAccount, String start, String end) {
		if(!Utlity.isInteger(start)){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		if(!Utlity.isInteger(end)){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		if(start.length() != end.length()){
			return ResultManager.createFailResult("??????????????????????????????????????????");
		}
		
		if(Integer.valueOf(start) > Integer.valueOf(end)){
			return ResultManager.createFailResult("?????????????????????????????????");
		}
		
		CompanyAccount ca = this.companyAccountService.get(companyAccount);
		if(ca == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		this.qcbVirtualAccountsService.batchDelete(companyAccount,start,end);
		return ResultManager.createSuccessResult("?????????????????????");
	}
	
	/**
	 * ????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result delete(String uuid) {
		//????????????????????????
		QcbVirtualAccounts qva = qcbVirtualAccountsService.get(uuid);
		if(qva == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		if(!QcbVirtualAccountsStatus.NORMAL.equals(qva.getStatus())){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		qcbVirtualAccountsService.delete(qva);
		
		return ResultManager.createSuccessResult("???????????????");
	}
}
