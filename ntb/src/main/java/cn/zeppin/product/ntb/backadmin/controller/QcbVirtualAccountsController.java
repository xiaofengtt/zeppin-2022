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
 * 企财宝企业账户信息
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
	 * 获取银行虚拟账户分状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		
		List<Entity> list = qcbVirtualAccountsService.getStatusList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 根据条件查询银行虚拟账户列表 
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "companyAccount", message="企业账户", type = DataType.STRING, maxLength = 36)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String status, String companyAccount, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(status)){
			searchMap.put("status", status);
			searchMap.put("companyAccount", companyAccount);
		}
		
		//查询符合条件的企业账户信息的总数
		Integer totalResultCount = qcbVirtualAccountsService.getCount(searchMap);
		//查询符合条件的企业账户信息列表
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
	 * 获取银行虚拟账户信息
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		//获取企业账户信息
		QcbCompanyAccount qca = qcbCompanyAccountService.get(uuid);
		if (qca == null) {
			return ResultManager.createFailResult("企业不存在！");
		}
		
		if(Utlity.checkStringNull(qca.getQcbVirtualAccounts())){
			return ResultManager.createFailResult("该企业未绑定银行虚拟账户！");
		}
		
		if(QcbCompanyAccountVirtualAccountType.VIRTUAL.equals(qca.getVirtualAccountType())){
			QcbVirtualAccounts qva = this.qcbVirtualAccountsService.get(qca.getQcbVirtualAccounts());
			if(qva == null){
				return ResultManager.createFailResult("银行虚拟户不存在！");
			}
			
			QcbVirtualAccountsDetailVO qvadVO = new QcbVirtualAccountsDetailVO(qva);
			qvadVO.setQcbCompany(uuid);
			qvadVO.setQcbCompanyId(qca.getMerchantId());
			qvadVO.setQcbCompanyName(qca.getName());
			
			CompanyAccount ca = this.companyAccountService.get(qvadVO.getCompanyAccount());
			if(ca == null){
				return ResultManager.createFailResult("银行虚拟户信息有误！");
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
				return ResultManager.createFailResult("银行账户信息有误！");
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
			return ResultManager.createFailResult("银行虚拟户信息有误！");
		}
	}
	
	/**
	 * 批量添加银行虚拟账户
	 * @param companyAccount
	 * @param start
	 * @param end
	 * @return
	 */
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	@ActionParam(key = "companyAccount", message="企业账户", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "start", message="起始值", type = DataType.STRING, required = true, maxLength = 6)
	@ActionParam(key = "end", message="结束值", type = DataType.STRING, required = true, maxLength = 6)
	@ResponseBody
	public Result batchAdd(String companyAccount, String start, String end) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		if(!Utlity.isInteger(start)){
			return ResultManager.createFailResult("起始值输入有误！");
		}
		
		if(!Utlity.isInteger(end)){
			return ResultManager.createFailResult("结束值输入有误！");
		}
		
		if(start.length() != end.length()){
			return ResultManager.createFailResult("起始值与结束值长度必须相等！");
		}
		
		if(Integer.valueOf(start) > Integer.valueOf(end)){
			return ResultManager.createFailResult("起始值不能大于结束值！");
		}
		
		CompanyAccount ca = this.companyAccountService.get(companyAccount);
		if(ca == null){
			return ResultManager.createFailResult("企业账户不存在！");
		}
		
		this.qcbVirtualAccountsService.batchAdd(companyAccount,start,end,currentOperator.getUuid());
		
		return ResultManager.createSuccessResult("批量添加成功！");
	}
	
	/**
	 * 批量删除银行虚拟账户
	 * @param companyAccount
	 * @param start
	 * @param end
	 * @return
	 */
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	@ActionParam(key = "companyAccount", message="企业账户", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "start", message="起始值", type = DataType.STRING, required = true, maxLength = 6)
	@ActionParam(key = "end", message="结束值", type = DataType.STRING, required = true, maxLength = 6)
	@ResponseBody
	public Result batchDelete(String companyAccount, String start, String end) {
		if(!Utlity.isInteger(start)){
			return ResultManager.createFailResult("起始值输入有误！");
		}
		
		if(!Utlity.isInteger(end)){
			return ResultManager.createFailResult("结束值输入有误！");
		}
		
		if(start.length() != end.length()){
			return ResultManager.createFailResult("起始值与结束值长度必须相等！");
		}
		
		if(Integer.valueOf(start) > Integer.valueOf(end)){
			return ResultManager.createFailResult("起始值不能大于结束值！");
		}
		
		CompanyAccount ca = this.companyAccountService.get(companyAccount);
		if(ca == null){
			return ResultManager.createFailResult("企业账户不存在！");
		}
		
		this.qcbVirtualAccountsService.batchDelete(companyAccount,start,end);
		return ResultManager.createSuccessResult("批量删除成功！");
	}
	
	/**
	 * 删除银行虚拟账户
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result delete(String uuid) {
		//获取银行虚拟账户
		QcbVirtualAccounts qva = qcbVirtualAccountsService.get(uuid);
		if(qva == null){
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		if(!QcbVirtualAccountsStatus.NORMAL.equals(qva.getStatus())){
			return ResultManager.createFailResult("该虚拟账户无法删除！");
		}
		
		qcbVirtualAccountsService.delete(qva);
		
		return ResultManager.createSuccessResult("删除成功！");
	}
}
