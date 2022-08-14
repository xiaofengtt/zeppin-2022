/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.math.BigDecimal;
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
import cn.zeppin.product.ntb.backadmin.service.api.IBranchBankService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IQcbCompanyHistoryOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.QcbCompanyAccountHistoryVO;
import cn.zeppin.product.ntb.backadmin.vo.QcbCompanyHistoryOperateDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.QcbCompanyHistoryOperateVO;
import cn.zeppin.product.ntb.backadmin.vo.QcbVirtualAccountsDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.BranchBank;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountVirtualAccountType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyHistoryOperate;
import cn.zeppin.product.ntb.core.entity.QcbCompanyHistoryOperate.QcbCompanyHistoryOperateStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyHistoryOperate.QcbCompanyHistoryOperateType;
import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbVirtualAccountsService;
import cn.zeppin.product.utility.DataTimeConvert;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author elegantclack
 * 后台企业账户充值管理
 */

@Controller
@RequestMapping(value = "/backadmin/qcbCompanyTransfer")
public class QcbCompanyHistoryController extends BaseController {

	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IQcbCompanyHistoryOperateService qcbCompanyHistoryOperateService;
	
	@Autowired
	private IQcbVirtualAccountsService qcbVirtualAccountsService;
	
	@Autowired
	private IBranchBankService branchBankService;
	
	@Autowired
	private IQcbCompanyAccountHistoryService qcbCompanyAccountHistoryService;
	
	
	/**
	 * 企业账户充值
	 * @param qcbCompany
	 * @param totalAmount
	 * @param remark
	 * @param receipt
	 * @param paytime
	 * @return
	 */
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	@ActionParam(key = "qcbCompany", message="企业账户", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "totalAmount", message="充值金额", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "receipt", message="上传凭证", type = DataType.STRING, required = true)
	@ActionParam(key = "paytime", message="充值时间", type = DataType.STRING, required = true)
	@ResponseBody
	public Result recharge(String qcbCompany, BigDecimal totalAmount, String remark, String receipt, String paytime){
		if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("充值金额应大于0！");
		}
		
		//获取企业账户信息
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcbCompany);
		if(qca == null){
			return ResultManager.createFailResult("账户信息不存在！");
		}
		
		//获取绑定虚拟户信息
		CompanyAccount ca = null;
		if(QcbCompanyAccountVirtualAccountType.VIRTUAL.equals(qca.getVirtualAccountType())){
			QcbVirtualAccounts qva = this.qcbVirtualAccountsService.get(qca.getQcbVirtualAccounts());
			if(qva == null){
				return ResultManager.createFailResult("未分配专属充值账号！");
			}
			
			ca = this.companyAccountService.get(qva.getCompanyAccount());
			if(ca == null){
				return ResultManager.createFailResult("银行虚拟户信息有误！");
			}
		}else if(QcbCompanyAccountVirtualAccountType.REALLY.equals(qca.getVirtualAccountType())){
			ca = this.companyAccountService.get(qca.getQcbVirtualAccounts());
			if(ca == null){
				return ResultManager.createFailResult("未分配专属充值账号！");
			}
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//封装充值交易记录
		QcbCompanyAccountHistory qcah = new QcbCompanyAccountHistory();
		qcah.setUuid(UUID.randomUUID().toString());
		qcah.setQcbCompany(qca.getUuid());
		
		//生成订单号
		//校验订单号是否存在
		String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_QCB,Utlity.BILL_PAYTYPE_NTLC,Utlity.BILL_PURPOSE_INCOME);
		if(this.qcbCompanyAccountHistoryService.checkOrderNum(orderNumStr)){
			return ResultManager.createFailResult("服务器繁忙,请稍后再试！");
		}
		qcah.setOrderNum(orderNumStr);
		qcah.setOrderType(QcbCompanyAccountHistoryOrderType.PAY_TYPE_BANK_TRANSFER);
		qcah.setAccountBalance(qca.getAccountBalance().add(totalAmount));
		qcah.setIncome(totalAmount);
		qcah.setPay(BigDecimal.ZERO);
		qcah.setType(QcbCompanyAccountHistoryType.INCOME);
		qcah.setCompanyAccount(ca.getUuid());
		qcah.setPoundage(BigDecimal.ZERO);
		qcah.setStatus(QcbCompanyAccountHistoryStatus.SUCCESS);
		qcah.setCreatetime(DataTimeConvert.stringToTimeStamp(paytime));
		qcah.setCreator(currentOperator.getUuid());
		qcah.setRemark(remark);

		//生成充值交易待审核记录
		QcbCompanyHistoryOperate qcho = new QcbCompanyHistoryOperate();
		qcho.setUuid(UUID.randomUUID().toString());
		qcho.setType(QcbCompanyHistoryOperateType.RECHARGE);
		qcho.setValue(JSONUtils.obj2json(qcah));
		qcho.setStatus(QcbCompanyHistoryOperateStatus.UNCHECKED);
		qcho.setCreator(currentOperator.getUuid());
		qcho.setSubmittime(new Timestamp(System.currentTimeMillis()));
		qcho.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		qcho.setReceipt(receipt);
		
		this.qcbCompanyHistoryOperateService.insert(qcho);
		
		return ResultManager.createDataResult(qcho, "添加待审核记录成功");
	}
	
	/**
	 * 企业账户支出
	 * @param qcbCompany
	 * @param totalAmount
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/expend", method = RequestMethod.POST)
	@ActionParam(key = "qcbCompany", message="企业账户", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "totalAmount", message="开票金额", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "flag", message="是否为开票金额", type = DataType.BOOLEAN, required = true)
	@ResponseBody
	public Result expend(String qcbCompany, BigDecimal totalAmount, String remark, Boolean flag){
		if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("充值金额应大于0！");
		}
		
		//获取企业账户信息
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcbCompany);
		if(qca == null){
			return ResultManager.createFailResult("账户信息不存在！");
		}
		
//		//获取绑定虚拟户信息
//		QcbVirtualAccounts qva = this.qcbVirtualAccountsService.get(qca.getQcbVirtualAccounts());
//		if(qva == null){
//			return ResultManager.createFailResult("未分配专属充值账号！");
//		}
//		
//		CompanyAccount ca = this.companyAccountService.get(qva.getCompanyAccount());
//		if(ca == null){
//			return ResultManager.createFailResult("银行虚拟户信息有误！");
//		}
		
		//判断企业可用余额是否充足
		BigDecimal price = BigDecimal.ZERO;
		if(flag){
			price = totalAmount.subtract(totalAmount.divide(qca.getFeeTicket().add(BigDecimal.ONE), 8, BigDecimal.ROUND_HALF_UP)).setScale(2, BigDecimal.ROUND_HALF_UP);
		} else {
			price = totalAmount;
		}
//		BigDecimal price = totalAmount.subtract(totalAmount.divide(qca.getFeeTicket().add(BigDecimal.ONE), 8, BigDecimal.ROUND_HALF_UP)).setScale(2, BigDecimal.ROUND_HALF_UP);
		if(qca.getAccountPay().compareTo(price) == -1){
			return ResultManager.createFailResult("企业账户余额不足，请提醒企业充值！");
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//封装支出交易记录
		QcbCompanyAccountHistory qcah = new QcbCompanyAccountHistory();
		qcah.setUuid(UUID.randomUUID().toString());
		qcah.setQcbCompany(qca.getUuid());
		
		//生成订单号
		//校验订单号是否存在
		String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_QCB,Utlity.BILL_PAYTYPE_NTLC,Utlity.BILL_PURPOSE_NTLC);
		if(this.qcbCompanyAccountHistoryService.checkOrderNum(orderNumStr)){
			return ResultManager.createFailResult("服务器繁忙,请稍后再试！");
		}
		qcah.setOrderNum(orderNumStr);
		qcah.setOrderType(QcbCompanyAccountHistoryOrderType.PAY_TYPE_BALANCE);
		qcah.setAccountBalance(qca.getAccountBalance().subtract(price));
		qcah.setIncome(BigDecimal.ZERO);
		qcah.setPay(price);
		qcah.setType(QcbCompanyAccountHistoryType.EXPEND);
//		qcah.setCompanyAccount(ca.getUuid());
		qcah.setPoundage(BigDecimal.ZERO);
		qcah.setStatus(QcbCompanyAccountHistoryStatus.SUCCESS);
		qcah.setCreatetime(new Timestamp(System.currentTimeMillis()));
		qcah.setCreator(currentOperator.getUuid());
		qcah.setRemark(remark);
		qcah.setOtherPrice(totalAmount);

		//生成充值交易待审核记录
		QcbCompanyHistoryOperate qcho = new QcbCompanyHistoryOperate();
		qcho.setUuid(UUID.randomUUID().toString());
		qcho.setType(QcbCompanyHistoryOperateType.EXPEND);
		qcho.setValue(JSONUtils.obj2json(qcah));
		qcho.setStatus(QcbCompanyHistoryOperateStatus.UNCHECKED);
		qcho.setCreator(currentOperator.getUuid());
		qcho.setSubmittime(new Timestamp(System.currentTimeMillis()));
		qcho.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		this.qcbCompanyHistoryOperateService.insert(qcho);
		
		return ResultManager.createDataResult(qcho, "添加待审核记录成功");
	}
	
	/** 获取企业账户交易操作审核信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {
		//获取信息记录
		QcbCompanyHistoryOperate qcho = this.qcbCompanyHistoryOperateService.get(uuid);
		if (qcho == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		QcbCompanyHistoryOperateDetailVO qchovo = new QcbCompanyHistoryOperateDetailVO(qcho);
		BkOperator creator = this.bkOperatorService.get(qcho.getCreator());
		if(creator != null){
			qchovo.setCreatorName(creator.getRealname());
		}
		
		//上传的凭证图片信息
		if(qchovo.getReceipt() != null && !"".equals(qchovo.getReceipt())){
			List<Resource> reList = new ArrayList<Resource>();
			String[] receiptArr = qchovo.getReceipt().split(",");
			String str = "";
			for(String receipt : receiptArr){
				Resource re = this.resourceService.get(receipt);
				if(re != null){
					str += receipt+",";
					reList.add(re);
				}
			}
			if(str.length() > 0){
				str = str.substring(0, str.length() - 1);
			}
			qchovo.setReceipt(str);
			qchovo.setListReceipt(reList);
		}
		
		QcbCompanyAccountHistory qcah = JSONUtils.json2obj(qcho.getValue(), QcbCompanyAccountHistory.class);
		QcbCompanyAccountHistoryVO qcahvo = new QcbCompanyAccountHistoryVO(qcah);
		
		
		BkOperator c = this.bkOperatorService.get(qcahvo.getCreator());
		if(c != null){
			qcahvo.setCreatorName(c.getRealname());
		}
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcah.getQcbCompany());
		if(qca == null){
			return ResultManager.createFailResult("企业信息错误！");
		}
		if(qcahvo.getPriceflag()){
			qcahvo.setAccountBalance(qca.getAccountBalance().add(qcah.getIncome()));//取最新余额
		} else {
			qcahvo.setAccountBalance(qca.getAccountBalance().subtract(qcah.getPay()));//取最新余额
		}
		
		qcahvo.setFeeTicketCN(Utlity.numFormat4UnitDetail(qca.getFeeTicket().multiply(BigDecimal.valueOf(100))));
		
		qchovo.setNewData(qcahvo);
		
		
		
		//获取绑定虚拟户信息
		if(QcbCompanyAccountVirtualAccountType.VIRTUAL.equals(qca.getVirtualAccountType())){
			QcbVirtualAccounts qva = this.qcbVirtualAccountsService.get(qca.getQcbVirtualAccounts());
			if(qva != null){
				QcbVirtualAccountsDetailVO qvadVO = new QcbVirtualAccountsDetailVO(qva);
				
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
		
				qvadVO.setQcbCompanyId(qca.getMerchantId());
				qvadVO.setQcbCompanyName(qca.getName());
				
				qchovo.setQcbVirtualAccountInfo(qvadVO);
			}
		}else if(QcbCompanyAccountVirtualAccountType.REALLY.equals(qca.getVirtualAccountType())){
			CompanyAccount ca = this.companyAccountService.get(qca.getQcbVirtualAccounts());
			if(ca != null){
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
				qchovo.setQcbVirtualAccountInfo(qvadVO);
			}
		}
		
		return ResultManager.createDataResult(qchovo);
	}
	
	/**
	 * 企业账户交易操作列表(编辑)
	 * @param status
	 * @param type = income
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/operateList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result operateList(String status, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		//查询符合条件的企业账户信息的总数
		Integer totalResultCount = qcbCompanyHistoryOperateService.getCount(searchMap);
		//查询符合条件的企业账户信息列表
		List<Entity> list = qcbCompanyHistoryOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, QcbCompanyHistoryOperate.class);
		
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				QcbCompanyHistoryOperate qcho = (QcbCompanyHistoryOperate)e;
				QcbCompanyHistoryOperateVO qchovo = new QcbCompanyHistoryOperateVO(qcho);
				
				QcbCompanyAccountHistory qcah = JSONUtils.json2obj(qcho.getValue(), QcbCompanyAccountHistory.class);
				
				QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcah.getQcbCompany());
				if(qca == null){
					return ResultManager.createFailResult("企业信息不存在！");
				}
				qchovo.setQcbCompany(qca.getName());
				if(qcah.getIncome().compareTo(BigDecimal.ZERO) == 1) {
					qchovo.setPrice(Utlity.numFormat4UnitDetail(qcah.getIncome()));
				}
				if (qcah.getPay().compareTo(BigDecimal.ZERO) == 1) {
					qchovo.setPrice(Utlity.numFormat4UnitDetail(qcah.getPay()));
				}
				
				BkOperator creator = this.bkOperatorService.get(qchovo.getCreator());
				if(creator != null){
					qchovo.setCreatorName(creator.getRealname());
				}
				if(qchovo.getChecker() != null && !"".equals(qchovo.getChecker())){
					BkOperator checker = this.bkOperatorService.get(qchovo.getChecker());
					if(checker != null){
						qchovo.setCheckerName(checker.getRealname());
					}
				}
				listVO.add(qchovo);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 企业账户交易操作列表(管理员)
	 * @param status
	 * @param type
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/operateCheckList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result operateCheckList(String status, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		if(Utlity.checkStringNull(sorts)){
			sorts = "submittime-desc";
		}
		//查询符合条件的企业账户信息的总数
		Integer totalResultCount = qcbCompanyHistoryOperateService.getCount(searchMap);
		//查询符合条件的企业账户信息列表
		List<Entity> list = qcbCompanyHistoryOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, QcbCompanyHistoryOperate.class);
		
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				QcbCompanyHistoryOperate qcho = (QcbCompanyHistoryOperate)e;
				QcbCompanyHistoryOperateVO qchovo = new QcbCompanyHistoryOperateVO(qcho);
				
				QcbCompanyAccountHistory qcah = JSONUtils.json2obj(qcho.getValue(), QcbCompanyAccountHistory.class);
				
				QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcah.getQcbCompany());
				if(qca == null){
					return ResultManager.createFailResult("企业信息不存在！");
				}
				qchovo.setQcbCompany(qca.getName());
				if(qcah.getIncome().compareTo(BigDecimal.ZERO) == 1) {
					qchovo.setPrice(Utlity.numFormat4UnitDetail(qcah.getIncome()));
				}
				if (qcah.getPay().compareTo(BigDecimal.ZERO) == 1) {
					qchovo.setPrice(Utlity.numFormat4UnitDetail(qcah.getPay()));
				}
				
				BkOperator creator = this.bkOperatorService.get(qchovo.getCreator());
				if(creator != null){
					qchovo.setCreatorName(creator.getRealname());
				}
				if(qchovo.getChecker() != null && !"".equals(qchovo.getChecker())){
					BkOperator checker = this.bkOperatorService.get(qchovo.getChecker());
					if(checker != null){
						qchovo.setCheckerName(checker.getRealname());
					}
				}
				listVO.add(qchovo);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 编辑企业账户交易操作信息
	 * @param uuid
	 * @param totalAmount
	 * @param poundage
	 * @param purpose
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/operateEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "totalAmount", message="充值金额", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "receipt", message="上传凭证", type = DataType.STRING, required = true)
	@ActionParam(key = "paytime", message="充值时间", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateEdit(String uuid, BigDecimal totalAmount, String remark, String receipt, String paytime){
		if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("交易金额应大于0！");
		}
		
		QcbCompanyHistoryOperate cato = this.qcbCompanyHistoryOperateService.get(uuid);
		if (cato != null) {
			QcbCompanyAccountHistory qcah = JSONUtils.json2obj(cato.getValue(), QcbCompanyAccountHistory.class);
			
			//获取企业账户信息
			QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcah.getQcbCompany());
			if(qca == null){
				return ResultManager.createFailResult("企业信息不存在！");
			}
			
			if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
				return ResultManager.createFailResult("充值金额应大于0！");
			}
			
			qcah.setAccountBalance(qca.getAccountBalance().add(totalAmount));
			qcah.setIncome(totalAmount);
			qcah.setCreatetime(DataTimeConvert.stringToTimeStamp(paytime));
			qcah.setRemark(remark);
			
			//修改待审核记录
			cato.setValue(JSONUtils.obj2json(qcah));
			cato.setCreatetime(new Timestamp(System.currentTimeMillis()));

			cato.setReceipt(receipt);
			
			qcbCompanyHistoryOperateService.update(cato);
			return ResultManager.createDataResult("修改待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 编辑企业账户交易操作信息
	 * @param uuid
	 * @param totalAmount
	 * @param poundage
	 * @param purpose
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/operateExpendEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "totalAmount", message="开票金额", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ResponseBody
	public Result operateExpendEdit(String uuid, BigDecimal totalAmount, String remark){
		if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("交易金额应大于0！");
		}
		
		QcbCompanyHistoryOperate cato = this.qcbCompanyHistoryOperateService.get(uuid);
		if (cato != null) {
			QcbCompanyAccountHistory qcah = JSONUtils.json2obj(cato.getValue(), QcbCompanyAccountHistory.class);
			
			//获取企业账户信息
			QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcah.getQcbCompany());
			if(qca == null){
				return ResultManager.createFailResult("企业信息不存在！");
			}
			
			//判断企业可用余额是否充足
			BigDecimal price = totalAmount.multiply(qca.getFeeTicket()).setScale(2, BigDecimal.ROUND_HALF_UP);
			if(qca.getAccountPay().compareTo(price) == -1){
				return ResultManager.createFailResult("企业账户余额不足，请提醒企业充值！");
			}
			
			qcah.setAccountBalance(qca.getAccountBalance().subtract(price));
			qcah.setPay(price);
			qcah.setRemark(remark);
			qcah.setCreatetime(new Timestamp(System.currentTimeMillis()));
			qcah.setOtherPrice(totalAmount);
			
			//修改待审核记录
			cato.setValue(JSONUtils.obj2json(qcah));
			cato.setCreatetime(new Timestamp(System.currentTimeMillis()));

			qcbCompanyHistoryOperateService.update(cato);
			return ResultManager.createDataResult("修改待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除企业账户交易操作信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateDelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateDelete(String uuid) {
		//获取企业账户交易操作信息
		QcbCompanyHistoryOperate cato = this.qcbCompanyHistoryOperateService.get(uuid);
		if(cato != null){
			if(!QcbCompanyHistoryOperateStatus.DRAFT.equals(cato.getStatus()) && !QcbCompanyHistoryOperateStatus.UNPASSED.equals(cato.getStatus())){
				return ResultManager.createFailResult("审核状态错误");
			}
			cato.setStatus(QcbCompanyHistoryOperateStatus.DELETED);
			qcbCompanyHistoryOperateService.update(cato);
			return ResultManager.createSuccessResult("操作成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 企业账户交易提交审核
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateSubmitCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateSubmitCheck(String uuid) {
		//获取企业账户交易操作信息
		QcbCompanyHistoryOperate cato = this.qcbCompanyHistoryOperateService.get(uuid);
		if(cato != null){
			if(QcbCompanyHistoryOperateStatus.CHECKED.equals(cato.getStatus())){
				return ResultManager.createFailResult("该记录已审核完毕");
			}
			cato.setSubmittime(new Timestamp(System.currentTimeMillis()));
			cato.setStatus(QcbCompanyHistoryOperateStatus.UNCHECKED);
			qcbCompanyHistoryOperateService.update(cato);
			return ResultManager.createSuccessResult("提交审核成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 审核企业账户交易操作
	 * @param uuid
	 * @param status
	 * @param reason
	 * @return
	 */
	@RequestMapping(value = "/operateCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="审核状态", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "reason", message="审核原因", type = DataType.STRING, maxLength = 500)
	@ResponseBody
	public Result operateCheck(String uuid, String status, String reason) {
		if(!QcbCompanyHistoryOperateStatus.CHECKED.equals(status) && !QcbCompanyHistoryOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("审核状态错误");
		}
		
		//获取企业账户交易信息
		QcbCompanyHistoryOperate cato = this.qcbCompanyHistoryOperateService.get(uuid);
		if(cato != null){
			if(!QcbCompanyHistoryOperateStatus.UNCHECKED.equals(cato.getStatus())){
				return ResultManager.createFailResult("该记录审核状态错误");
			}
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(cato.getCreator().equals(currentOperator.getUuid())){
				return ResultManager.createFailResult("无法审核自己提交的操作记录");
			}
			
			cato.setChecker(currentOperator.getUuid());
			cato.setChecktime(new Timestamp(System.currentTimeMillis()));
			cato.setStatus(status);
			cato.setReason(reason);
			try {
				qcbCompanyHistoryOperateService.check(cato);
			} catch (TransactionException e) {
				super.flushAll();
				return ResultManager.createFailResult(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult("数据操作出错！");
			}
			return ResultManager.createSuccessResult("审核记录成功！");
			
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 获取企业账户交易分状态列表(编辑)
	 * @return
	 */
	@RequestMapping(value = "/operateStatusList", method = RequestMethod.GET)
	@ActionParam(key = "transferType", message="业务类型", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateStatusList(String transferType) {	
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		//获取企业账户交易信息
		List<Entity> list = qcbCompanyHistoryOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取企业账户交易分状态列表(管理员)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ActionParam(key = "transferType", message="业务类型", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckStatusList(String transferType) {	
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		//获取企业账户交易信息
		List<Entity> list = qcbCompanyHistoryOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取企业账户交易分类型列表
	 * @return
	 */
	@RequestMapping(value = "/operateTypeList", method = RequestMethod.GET)
	@ActionParam(key = "transferType", message="业务类型", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "status", message="审核状态", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateTypeList(String transferType, String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		List<Entity> list = qcbCompanyHistoryOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取企业账户交易分类型列表(管理员)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckTypeList", method = RequestMethod.GET)
	@ActionParam(key = "transferType", message="业务类型", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "status", message="审核状态", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckTypeList(String transferType, String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", status);
		
		List<Entity> list = qcbCompanyHistoryOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
