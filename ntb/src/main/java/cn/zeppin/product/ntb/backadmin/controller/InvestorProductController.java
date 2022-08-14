package cn.zeppin.product.ntb.backadmin.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountHistoryService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorAccountHistoryService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorProductBuyService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.backadmin.service.api.IPlatformAccountService;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductPublishVO;
import cn.zeppin.product.ntb.backadmin.vo.InvestorTransferCountVO;
import cn.zeppin.product.ntb.backadmin.vo.ProductTransferCountVO;
import cn.zeppin.product.ntb.backadmin.vo.UserAccountHistory;
import cn.zeppin.product.ntb.backadmin.vo.UserAccountHistory.UserAccountHistoryUserType;
import cn.zeppin.product.ntb.backadmin.vo.UserAccountHistoryVO;
import cn.zeppin.product.ntb.backadmin.vo.UserProductBuy;
import cn.zeppin.product.ntb.backadmin.vo.UserProductBuy.UserProductBuyUserType;
import cn.zeppin.product.ntb.backadmin.vo.UserProductBuyVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy.InvestorProductBuyProductType;
import cn.zeppin.product.ntb.core.entity.PlatformAccount;
import cn.zeppin.product.ntb.core.entity.PlatformAccount.PlatformAccountUuid;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy.QcbEmployeeProductBuyProductType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeProductBuyService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.utility.Utlity;

/**
 * 投资者投资接口
 * 
 **/

@Controller
@RequestMapping(value = "/backadmin/investorProduct")
public class InvestorProductController extends BaseController{
	
	@Autowired
	private IInvestorService investorService;
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	@Autowired
	private IInvestorProductBuyService investorProductBuyService;
	
	@Autowired
	private IQcbEmployeeProductBuyService qcbEmployeeProductBuyService;
	
	@Autowired
	private IInvestorAccountHistoryService investorAccountHistoryService;
	
	@Autowired
	private IQcbEmployeeHistoryService qcbEmployeeHistoryService;
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IPlatformAccountService platformAccountService;
	
	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private ICompanyAccountHistoryService companyAccountHistoryService;
	
	/**
	 * 获取用户投资列表
	 * @param investor
	 * @param product
	 * @param productStage
	 * @param productName
	 * @param productType
	 * @param productCustodian
	 * @param productRedeem
	 * @param startTime
	 * @param endTime
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/listProductBuy", method = RequestMethod.GET)
	@ActionParam(key = "investor", message="投资者uuid", type = DataType.STRING)
	@ActionParam(key = "qcbEmployee", message="企财宝员工uuid", type = DataType.STRING)
	@ActionParam(key = "product", message="募集产品uuid", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result listProductBuy(String investor, String qcbEmployee, String product, Integer pageNum, Integer pageSize, String sorts){
		
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("investor", investor);
		searchMap.put("qcbEmployee", qcbEmployee);
		searchMap.put("product", product);
		
		Integer totalResultCount = investorProductBuyService.getAllUserCount(searchMap);
		List<Entity> list = this.investorProductBuyService.getAllUserListForPage(searchMap, pageNum, pageSize, sorts, UserProductBuy.class);
		List<UserProductBuyVO> listVO = new ArrayList<UserProductBuyVO>();
		
		for(Entity e : list){
			UserProductBuy upb = (UserProductBuy)e;
			UserProductBuyVO upbvo = new UserProductBuyVO(upb);
			
			if(UserProductBuyUserType.INVESTOR.equals(upb.getUserType())){
				Investor i = this.investorService.get(upb.getUser());
				if(i != null){
					upbvo.setUserName(i.getRealname());
				}
			}else if(UserProductBuyUserType.QCBEMPLOYEE.equals(upb.getUserType())){
				QcbEmployee qe = this.qcbEmployeeService.get(upb.getUser());
				if(qe != null){
					upbvo.setUserName(qe.getRealname());
				}
			}
			
			BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(upb.getProduct());
			if(bfpp != null){
				upbvo.setProductName(bfpp.getName());
			}
			
			listVO.add(upbvo);
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	 
	/**
	 * 获取用户投资列表
	 * @param investor
	 * @param product
	 * @param productStage
	 * @param productName
	 * @param productType
	 * @param productCustodian
	 * @param productRedeem
	 * @param deadline 1-昨天 2-前7天  3-前30天 4-前60天 5-前365天
	 * @param starttime
	 * @param endtime
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/listProductBuyRecords", method = RequestMethod.GET)
	@ActionParam(key = "investor", message="投资者uuid", type = DataType.STRING)
	@ActionParam(key = "qcbEmployee", message="企财宝员工uuid", type = DataType.STRING)
	@ActionParam(key = "product", message="募集产品uuid", type = DataType.STRING)
	@ActionParam(key = "productStage", message="募集产品阶段", type = DataType.STRING)//阶段
	@ActionParam(key = "productName", message="募集产品期限", type = DataType.STRING)//名称
	@ActionParam(key = "productType", message="募集产品类型", type = DataType.STRING)//类型
	@ActionParam(key = "productCustodian", message="募集产品管理银行", type = DataType.STRING)//管理银行
	@ActionParam(key = "productRedeem", message="募集产品赎回状态", type = DataType.BOOLEAN)//赎回状态
	@ActionParam(key = "deadline", message="期限", type = DataType.STRING)
	@ActionParam(key = "starttime", message="购买起始时间", type = DataType.DATE)//起始时间
	@ActionParam(key = "endtime", message="购买结束时间", type = DataType.DATE)//结束时间
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ResponseBody
	public Result listProductBuyRecords(String investor, String qcbEmployee, String product, String productStage, String productName, String productType,
			String productCustodian, String deadline, String starttime, String endtime,String productRedeem, Integer pageNum, 
			Integer pageSize, String sorts, String name){
		
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("type", InvestorAccountHistoryType.BUY);
		searchMap.put("status", InvestorAccountHistoryStatus.SUCCESS);
		searchMap.put("investor", investor);
		searchMap.put("qcbEmployee", qcbEmployee);
		searchMap.put("product", product);
		searchMap.put("productName", productName);
		if(!Utlity.checkStringNull(name)){
			searchMap.put("name", name);
		}
		
		Calendar c = Calendar.getInstance();
		if(deadline!= null && !"all".equals(deadline)){
			switch (deadline) {
			case "1":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -1);
		        Date m = c.getTime();
				starttime = Utlity.timeSpanToDateString(m);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "2":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -7);
		        Date m2 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m2);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "3":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -30);
		        Date m3 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m3);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "4":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -90);
		        Date m4 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m4);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "5":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -365);
		        Date m5 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m5);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			default:
				break;
			}
		}
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		
		if(!"all".equals(productStage)){
			searchMap.put("productStage", productStage);
		}
		if(!"all".equals(productType)){
			searchMap.put("productType", productType);
		}
		if(!"all".equals(productCustodian)){
			searchMap.put("productCustodian", productCustodian);
		}
		if(!"all".equals(productRedeem)){
			searchMap.put("productRedeem", productRedeem);
		}
		
		Integer totalResultCount = investorAccountHistoryService.getCountByUser(searchMap);
		List<Entity> list = this.investorAccountHistoryService.getListForPageByUser(searchMap, pageNum, pageSize, sorts, UserAccountHistory.class);
		List<UserAccountHistoryVO> listVO = new ArrayList<UserAccountHistoryVO>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		
		for(Entity e : list){
			UserAccountHistory uah = (UserAccountHistory)e;
			UserAccountHistoryVO iahvo = new UserAccountHistoryVO(uah);
			
			if(UserAccountHistoryUserType.INVESTOR.equals(uah.getUserType())){
				Investor i = this.investorService.get(uah.getUser());
				if(i != null){
					iahvo.setUserName(i.getRealname());
				}
			}else if(UserAccountHistoryUserType.QCBEMPLOYEE.equals(uah.getUserType())){
				QcbEmployee qe = this.qcbEmployeeService.get(uah.getUser());
				if(qe != null){
					iahvo.setUserName(qe.getRealname());
				}
			}
			
			BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(uah.getProduct());
			if(bfpp != null){
				BankFinancialProductPublishVO bfppvo = new BankFinancialProductPublishVO(bfpp);
				iahvo.setProductInfo(bfppvo);
			}
			
			listVO.add(iahvo);
		}
		
		searchMap.remove("investor");
		searchMap.remove("qcbEmployee");
		List<Entity> listAll = this.investorAccountHistoryService.getListForPageByUser(searchMap, 1, Integer.MAX_VALUE, sorts, UserAccountHistory.class);
		BigDecimal totalAmount = BigDecimal.ZERO;
		for(Entity e : listAll){
			UserAccountHistory uah = (UserAccountHistory)e;
			totalAmount = totalAmount.add(uah.getPay());
		}
		
		dataMap.put("totalAmount", totalAmount);
		dataMap.put("totalAmountCN", Utlity.numFormat4UnitDetail(totalAmount));
		dataMap.put("dataList", listVO);
		return ResultManager.createDataResult(dataMap, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取用户充值提现交易列表
	 * @param deadline 1-昨天 2-前7天  3-前30天 4-前60天 5-前365天
	 * @param starttime
	 * @param endtime
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/listInvestorRecharge", method = RequestMethod.GET)
	@ActionParam(key = "deadline", message="期限", type = DataType.STRING)
	@ActionParam(key = "starttime", message="起始时间", type = DataType.DATE)//起始时间
	@ActionParam(key = "endtime", message="结束时间", type = DataType.DATE)//结束时间
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ActionParam(key = "name", message="筛选参数", type = DataType.STRING)
	@ResponseBody
	public Result listInvestorRecharge(String deadline, String starttime, String endtime, Integer pageNum, Integer pageSize, String sorts, String name){
		PlatformAccount pa = this.platformAccountService.get(PlatformAccountUuid.BALANCE);
		PlatformAccount pai = this.platformAccountService.get(PlatformAccountUuid.INVESTOR);
		
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("serviceType", "'"+InvestorAccountHistoryType.INCOME+"','"+InvestorAccountHistoryType.WITHDRAW+"'");
		if(!Utlity.checkStringNull(name)){
			searchMap.put("name", name);
		}
		Calendar c = Calendar.getInstance();
		if(deadline!= null && !"all".equals(deadline)){
			switch (deadline) {
			case "1":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -1);
		        Date m = c.getTime();
				starttime = Utlity.timeSpanToDateString(m);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "2":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -7);
		        Date m2 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m2);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "3":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -30);
		        Date m3 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m3);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "4":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -90);
		        Date m4 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m4);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "5":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -365);
		        Date m5 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m5);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			default:
				break;
			}
		}
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		searchMap.put("status", InvestorAccountHistoryStatus.SUCCESS);
		Integer totalResultCount = investorAccountHistoryService.getCountByUser(searchMap);
		List<Entity> list = this.investorAccountHistoryService.getListForPageByUser(searchMap, pageNum, pageSize, sorts, UserAccountHistory.class);
		
		List<UserAccountHistoryVO> listVO = new ArrayList<UserAccountHistoryVO>();
		for(Entity e : list){
			UserAccountHistory uah = (UserAccountHistory)e;
			UserAccountHistoryVO uahvo = new UserAccountHistoryVO(uah);
			
			if(UserAccountHistoryUserType.INVESTOR.equals(uah.getUserType())){
				Investor i = this.investorService.get(uah.getUser());
				if(i != null){
					uahvo.setUserName(i.getRealname());
				}
			}else if(UserAccountHistoryUserType.QCBEMPLOYEE.equals(uah.getUserType())){
				QcbEmployee qe = this.qcbEmployeeService.get(uah.getUser());
				if(qe != null){
					uahvo.setUserName(qe.getRealname());
				}
			}
			
			listVO.add(uahvo);
		}
		
		List<Entity> listAll = this.investorAccountHistoryService.getListForPageByUser(searchMap, 1, Integer.MAX_VALUE, sorts, UserAccountHistory.class);
		BigDecimal totalRecharge = BigDecimal.ZERO;
		BigDecimal totalWithdraw = BigDecimal.ZERO;
		for(Entity e : listAll){
			UserAccountHistory uah = (UserAccountHistory)e;
			if(uah.getIncome().compareTo(BigDecimal.ZERO) == 1){
				totalRecharge = totalRecharge.add(uah.getIncome());
			} else if (uah.getPay().compareTo(BigDecimal.ZERO) == 1) {
				totalWithdraw = totalWithdraw.add(uah.getPay());
			}
		}
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("totalRecharge", totalRecharge);
		dataMap.put("totalRechargeCN", Utlity.numFormat4UnitDetail(totalRecharge));
		dataMap.put("totalWithdraw", totalWithdraw);
		dataMap.put("totalWithdrawCN", Utlity.numFormat4UnitDetail(totalWithdraw));
		dataMap.put("accountBalance", pa.getTotalAmount().add(pai.getTotalAmount()));
		dataMap.put("accountBalanceCN", Utlity.numFormat4UnitDetail(pa.getTotalAmount().add(pai.getTotalAmount())));
		dataMap.put("dataList", listVO);
		return ResultManager.createDataResult(dataMap, pageNum, pageSize, totalResultCount);
	}

	/**
	 * 获取每日用户充值、每日用户提现、每日募集产品到期预计还款额图表数据
	 * @param deadline
	 * @param starttime
	 * @param endtime
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/listDailyInvestorRecharge", method = RequestMethod.GET)
	@ActionParam(key = "deadline", message="期限", type = DataType.STRING)
	@ActionParam(key = "starttime", message="起始时间", type = DataType.DATE)//起始时间
	@ActionParam(key = "endtime", message="结束时间", type = DataType.DATE)//结束时间
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result listDailyInvestorRecharge(String deadline, String starttime, String endtime, Integer pageNum, Integer pageSize, String sorts){
		PlatformAccount pa = this.platformAccountService.get(PlatformAccountUuid.BALANCE);
		PlatformAccount pai = this.platformAccountService.get(PlatformAccountUuid.INVESTOR);
		
		//查询每日用户充值提现统计
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("type", "'"+InvestorAccountHistoryType.INCOME+"','"+InvestorAccountHistoryType.WITHDRAW+"'");
		Calendar c = Calendar.getInstance();
		if(deadline!= null && !"all".equals(deadline)){
			switch (deadline) {
			case "1":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -1);
		        Date m = c.getTime();
				starttime = Utlity.timeSpanToDateString(m);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "2":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -7);
		        Date m2 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m2);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "3":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -30);
		        Date m3 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m3);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "4":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -90);
		        Date m4 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m4);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "5":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -365);
		        Date m5 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m5);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			default:
				break;
			}
		}
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		searchMap.put("status", InvestorAccountHistoryStatus.SUCCESS);
		try {
			List<Date> listDate = Utlity.findDates(Utlity.stringToDate(starttime), Utlity.stringToDate(endtime));
			List<String> listDateStr = new ArrayList<String>();
			Map<String, String> mapRecharge = new HashMap<String, String>();
			Map<String, String> mapWithdraw = new HashMap<String, String>();
			Map<String, String> mapTotalReturn = new HashMap<String, String>();
			for(Date date : listDate){//初始化纵坐标数据
				listDateStr.add(Utlity.timeSpanToDateString(date));
				mapRecharge.put(Utlity.timeSpanToDateString(date), "0.00");
				mapWithdraw.put(Utlity.timeSpanToDateString(date), "0.00");
				mapTotalReturn.put(Utlity.timeSpanToDateString(date), "0.00");
			}
			List<Entity> list = this.investorAccountHistoryService.getListForCountPage(searchMap, pageNum, pageSize, sorts, InvestorTransferCountVO.class);
			BigDecimal totalRecharge = BigDecimal.ZERO;
			BigDecimal totalWithdraw = BigDecimal.ZERO;
			for(Entity entity : list){//纵坐标数据赋值
				InvestorTransferCountVO itfvo = (InvestorTransferCountVO)entity;
				if(InvestorAccountHistoryType.INCOME.equals(itfvo.getType())){
					totalRecharge = totalRecharge.add(itfvo.getRecharge());
					mapRecharge.put(itfvo.getCreatetime(), itfvo.getRecharge().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				} else {
					totalWithdraw = totalWithdraw.add(itfvo.getWithdraw());
					mapWithdraw.put(itfvo.getCreatetime(), itfvo.getWithdraw().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				}
			}
			
			searchMap.put("type", "'"+QcbEmployeeHistoryType.INCOME+"','"+QcbEmployeeHistoryType.WITHDRAW+"'");
			list = this.qcbEmployeeHistoryService.getListForCountPage(searchMap, pageNum, pageSize, sorts, InvestorTransferCountVO.class);
			for(Entity entity : list){//纵坐标数据赋值
				InvestorTransferCountVO itfvo = (InvestorTransferCountVO)entity;
				if(QcbEmployeeHistoryType.INCOME.equals(itfvo.getType())){
					totalRecharge = totalRecharge.add(itfvo.getRecharge());
					mapRecharge.put(itfvo.getCreatetime(), itfvo.getRecharge().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				} else {
					totalWithdraw = totalWithdraw.add(itfvo.getWithdraw());
					mapWithdraw.put(itfvo.getCreatetime(), itfvo.getWithdraw().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				}
			}
			
			//牛投理财用户理财购买
			searchMap.clear();
			searchMap.put("type", "'"+InvestorProductBuyProductType.BANK_PRODUCT+"'");
			searchMap.put("starttime", starttime);
			searchMap.put("endtime", endtime);
			list = this.investorProductBuyService.getListForCountPage(searchMap, pageNum, pageSize, sorts, ProductTransferCountVO.class);
			for(Entity entity : list){//纵坐标数据赋值
				ProductTransferCountVO itfvo = (ProductTransferCountVO)entity;
				BigDecimal termRate = BigDecimal.valueOf(Double.valueOf(itfvo.getTerm())).divide(BigDecimal.valueOf(Double.valueOf(365)),100,BigDecimal.ROUND_FLOOR);
				BigDecimal totalReturn = itfvo.getPrice().multiply(itfvo.getReturnRate().divide(BigDecimal.valueOf(100))).multiply(termRate);
				totalReturn = totalReturn.setScale(2, BigDecimal.ROUND_FLOOR);
				BigDecimal totalAmount = itfvo.getPrice().add(totalReturn);
				BigDecimal lastAmount = BigDecimal.valueOf(Double.valueOf(mapTotalReturn.get(itfvo.getCreatetime())));
				mapTotalReturn.put(itfvo.getCreatetime(), totalAmount.add(lastAmount).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			}
			
			//企财宝员工理财购买
			searchMap.clear();
			searchMap.put("type", "'"+QcbEmployeeProductBuyProductType.BANK_PRODUCT+"'");
			searchMap.put("starttime", starttime);
			searchMap.put("endtime", endtime);
			list = this.qcbEmployeeProductBuyService.getListForCountPage(searchMap, pageNum, pageSize, sorts, ProductTransferCountVO.class);
			for(Entity entity : list){//纵坐标数据赋值
				ProductTransferCountVO itfvo = (ProductTransferCountVO)entity;
				BigDecimal termRate = BigDecimal.valueOf(Double.valueOf(itfvo.getTerm())).divide(BigDecimal.valueOf(Double.valueOf(365)),100,BigDecimal.ROUND_FLOOR);
				BigDecimal totalReturn = itfvo.getPrice().multiply(itfvo.getReturnRate().divide(BigDecimal.valueOf(100))).multiply(termRate);
				totalReturn = totalReturn.setScale(2, BigDecimal.ROUND_FLOOR);
				BigDecimal totalAmount = itfvo.getPrice().add(totalReturn);
				BigDecimal lastAmount = BigDecimal.valueOf(Double.valueOf(mapTotalReturn.get(itfvo.getCreatetime())));
				mapTotalReturn.put(itfvo.getCreatetime(), totalAmount.add(lastAmount).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			}
			
			List<Double> listRecharge = new ArrayList<Double>();
			List<Double> listWithdraw = new ArrayList<Double>();
			List<Double> listTotalReturn = new ArrayList<Double>();
			for(String dateStr : listDateStr){//生成纵坐标数据集合
				listRecharge.add(Double.parseDouble(mapRecharge.get(dateStr)));
				listWithdraw.add(Double.parseDouble(mapWithdraw.get(dateStr)));
				listTotalReturn.add(Double.parseDouble(mapTotalReturn.get(dateStr)));
			}
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("totalRecharge", totalRecharge);
			dataMap.put("totalRechargeCN", Utlity.numFormat4UnitDetail(totalRecharge));
			dataMap.put("totalWithdraw", totalWithdraw);
			dataMap.put("totalWithdrawCN", Utlity.numFormat4UnitDetail(totalWithdraw));
			dataMap.put("accountBalance", pa.getTotalAmount().add(pai.getTotalAmount()));
			dataMap.put("accountBalanceCN", Utlity.numFormat4UnitDetail(pa.getTotalAmount().add(pai.getTotalAmount())));
			dataMap.put("listDate", listDateStr);
			dataMap.put("listRecharge", listRecharge);
			dataMap.put("listWithdraw", listWithdraw);
			dataMap.put("listTotalReturn", listTotalReturn);
			return ResultManager.createDataResult(dataMap,"成功");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("获取信息失败，请重试！");
		}
	}
	
	/**
	 * 获取每日用户投资统计列表
	 * @param investor
	 * @param product
	 * @param productStage
	 * @param productName
	 * @param productType
	 * @param productCustodian
	 * @param productRedeem
	 * @param deadline 1-昨天 2-前7天  3-前30天 4-前60天 5-前365天
	 * @param starttime
	 * @param endtime
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/listDailyProductBuyRecords", method = RequestMethod.GET)
	@ActionParam(key = "investor", message="投资者uuid", type = DataType.STRING)
	@ActionParam(key = "qcbEmployee", message="企财宝员工uuid", type = DataType.STRING)
	@ActionParam(key = "product", message="募集产品uuid", type = DataType.STRING)
	@ActionParam(key = "productStage", message="募集产品阶段", type = DataType.STRING)//阶段
	@ActionParam(key = "productName", message="募集产品期限", type = DataType.STRING)//名称
	@ActionParam(key = "productType", message="募集产品类型", type = DataType.STRING)//类型
	@ActionParam(key = "productCustodian", message="募集产品管理银行", type = DataType.STRING)//管理银行
	@ActionParam(key = "productRedeem", message="募集产品赎回状态", type = DataType.BOOLEAN)//赎回状态
	@ActionParam(key = "deadline", message="期限", type = DataType.STRING)
	@ActionParam(key = "starttime", message="购买起始时间", type = DataType.DATE)//起始时间
	@ActionParam(key = "endtime", message="购买结束时间", type = DataType.DATE)//结束时间
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result listDailyProductBuyRecords(String investor, String qcbEmployee, String product, String productStage, String productName, String productType,
			String productCustodian, String deadline, String starttime, String endtime,String productRedeem, Integer pageNum, 
			Integer pageSize, String sorts){
		
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("type", InvestorAccountHistoryType.BUY);
		searchMap.put("status", InvestorAccountHistoryStatus.SUCCESS);
		searchMap.put("investor", investor);
		searchMap.put("qcbEmployee", qcbEmployee);
		searchMap.put("product", product);
		searchMap.put("productName", productName);
		
		Calendar c = Calendar.getInstance();
		if(deadline!= null && !"all".equals(deadline)){
			switch (deadline) {
			case "1":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -1);
		        Date m = c.getTime();
				starttime = Utlity.timeSpanToDateString(m);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "2":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -7);
		        Date m2 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m2);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "3":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -30);
		        Date m3 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m3);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "4":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -90);
		        Date m4 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m4);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			case "5":
				c.setTime(new Date());
		        c.add(Calendar.DATE, -365);
		        Date m5 = c.getTime();
				starttime = Utlity.timeSpanToDateString(m5);
				endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
				break;
			default:
				break;
			}
		}
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		
		if(!"all".equals(productStage)){
			searchMap.put("productStage", productStage);
		}
		if(!"all".equals(productType)){
			searchMap.put("productType", productType);
		}
		if(!"all".equals(productCustodian)){
			searchMap.put("productCustodian", productCustodian);
		}
		if(!"all".equals(productRedeem)){
			searchMap.put("productRedeem", productRedeem);
		}
		
		try {
			List<Date> listDate = Utlity.findDates(Utlity.stringToDate(starttime), Utlity.stringToDate(endtime));
			List<String> listDateStr = new ArrayList<String>();
			Map<String, String> mapBuyRecords = new HashMap<String, String>();
			searchMap.put("type", "'"+InvestorAccountHistoryType.BUY+"'");
			searchMap.put("starttime", starttime);
			searchMap.put("endtime", endtime);
			for(Date date : listDate){//初始化纵坐标数据
				listDateStr.add(Utlity.timeSpanToDateString(date));
				mapBuyRecords.put(Utlity.timeSpanToDateString(date), "0.00");
			}
			
			//牛投理财用户购买
			List<Entity> list = this.investorAccountHistoryService.getListForCountPage(searchMap, pageNum, pageSize, sorts, InvestorTransferCountVO.class);
			BigDecimal totalAmount = BigDecimal.ZERO;
			for(Entity entity : list){//纵坐标数据赋值
				InvestorTransferCountVO itfvo = (InvestorTransferCountVO)entity;
				BigDecimal price = itfvo.getWithdraw();
				totalAmount = totalAmount.add(price);
				mapBuyRecords.put(itfvo.getCreatetime(), price.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			}
			
			//企财宝员工购买
			searchMap.put("type", "'"+QcbEmployeeHistoryType.BUY+"'");
			list = this.qcbEmployeeHistoryService.getListForCountPage(searchMap, pageNum, pageSize, sorts, InvestorTransferCountVO.class);
			for(Entity entity : list){//纵坐标数据赋值
				InvestorTransferCountVO itfvo = (InvestorTransferCountVO)entity;
				BigDecimal price = itfvo.getWithdraw();
				totalAmount = totalAmount.add(price);
				mapBuyRecords.put(itfvo.getCreatetime(), price.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			}
			
			List<Double> listBuyRecords = new ArrayList<Double>();
			for(String dateStr : listDateStr){//生成纵坐标数据集合
				listBuyRecords.add(Double.parseDouble(mapBuyRecords.get(dateStr)));
			}
			
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("totalAmount", totalAmount);
			dataMap.put("totalAmountCN", Utlity.numFormat4UnitDetail(totalAmount));
			dataMap.put("listDate", listDateStr);
			dataMap.put("listBuyRecords", listBuyRecords);
			return ResultManager.createDataResult(dataMap,"成功");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("获取信息失败，请重试！");
		}

	}
//	/**
//	 * 模拟用户充值
//	 * @return
//	 */
//	@RequestMapping(value = "/recharge", method = RequestMethod.GET)
//	@ResponseBody
//	public Result recharge(){
//		String uuid = "";
//		//取管理员信息
//		Subject subject = SecurityUtils.getSubject();
//		Session session = subject.getSession();
//		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
//		if("1d2f3518-83f7-42cd-84df-edef10e615f2".equals(currentOperator.getUuid())){
//			uuid = "68c354d3-4b75-4e26-8929-8b16af2623ec";
//		} else {
//			uuid = "bb4ac373-33e2-42d0-8ce8-b004bb5f0e1f";
//		}
//		
//		Investor investor = this.investorService.get(uuid);
//		if(investor == null){
//			return ResultManager.createFailResult("用户不存在");
//		}
////		price = Base64Util.getFromBase64(price);
//		String price = String.valueOf((int)(Math.random()*50000 + 1000));
//		System.out.println(price);
//		if (!Utlity.isPositiveCurrency4Web(price)) {
//			return ResultManager.createFailResult("充值金额输入错误!");
//		}
//		BigDecimal totalFees = BigDecimal.valueOf(Double.parseDouble(price));
//		
//		String body = "";
//		if(Utlity.checkStringNull(body)){
//			body = "牛投理财-账户充值";
//		} else {
//			body = Base64Util.getFromBase64(body);
//		}
//		
//		String openid = investor.getOpenid();
//		String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_ANDROID,Utlity.BILL_PAYTYPE_WECHART,Utlity.BILL_PURPOSE_INCOME);
//		if(this.investorAccountHistoryService.getCheckOrderNum(orderNumStr)){
//			return ResultManager.createFailResult("手速太快，服务器未响应！");
//		}
//		OrderinfoByThirdparty obt = new OrderinfoByThirdparty();
//		obt.setUuid(UUID.randomUUID().toString());
//		obt.setType(OrderinfoByThirdpartyType.WEXIN);
//		obt.setInvestor(investor.getUuid());
//		obt.setOrderNum(orderNumStr);
//		obt.setBody(body);
//		obt.setTotalFee(totalFees);
//		obt.setPaySource(openid);
//		obt.setStatus(OrderinfoByThirdpartyReturnStatus.SUCCESS);
//		obt.setCreatetime(new Timestamp(System.currentTimeMillis()));
//		obt.setErrCode("");
//		obt.setErrCodeDes("");
//		obt.setMessage("充值成功");
////		this.orderinfoByThirdpartyService.insert(obt);
//		
//		InvestorAccountHistory iah = new InvestorAccountHistory();
//		iah.setUuid(UUID.randomUUID().toString());
//		iah.setInvestor(investor.getUuid());
//		iah.setIncome(totalFees);
//		iah.setPay(BigDecimal.ZERO);
//		iah.setAccountBalance(investor.getAccountBalance());
//		iah.setOrderId(obt.getUuid());
//		iah.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_WECHART);
//		iah.setOrderNum(orderNumStr);
//		iah.setType(InvestorAccountHistoryType.INCOME);
//		iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
//		iah.setCreatetime(new Timestamp(System.currentTimeMillis()));
//		iah.setCompanyAccount(CompanyAccountUuid.WECHART);
//		iah.setPoundage(BigDecimal.ZERO);
//		
//		//充值、利息、返还本金进个人账号
//		BigDecimal total = investor.getAccountBalance();
//		total = total.add(totalFees);
//		investor.setAccountBalance(total);//更新余额
//		iah.setAccountBalance(total);
//		
//		//更新企业账户余额
//		CompanyAccount ca = this.companyAccountService.get(iah.getCompanyAccount());
//		if(ca == null){
//			return ResultManager.createFailResult("充值失败，企业账户不存在");
//		}
//		BigDecimal accountTotal = ca.getAccountBalance();
//		accountTotal = accountTotal.add(totalFees);
//		ca.setAccountBalance(accountTotal);
////		this.companyAccountService.update(ca);
//		
//		//更新系统总帐户余额
//		PlatformAccount pa = platformAccountService.get(PlatformAccountUuid.TOTAL);
//		pa.setTotalAmount(pa.getTotalAmount().add(totalFees));
////		this.platformAccountService.update(pa);
//		
//		//更新系统总帐户余额
//		PlatformAccount paf = platformAccountService.get(PlatformAccountUuid.BALANCE);
////		paf.setTotalAmount(paf.getTotalAmount().add(totalFees));
//		
//		//更新系统用户总帐户余额
//		PlatformAccount pai = platformAccountService.get(PlatformAccountUuid.INVESTOR);
//		pai.setTotalAmount(pai.getTotalAmount().add(totalFees));
//		
//		//增加公司账户交易记录--用户充值
//		CompanyAccountHistory cat = new CompanyAccountHistory();
//		cat.setUuid(UUID.randomUUID().toString());
//		cat.setCompanyAccountIn(iah.getCompanyAccount());//用户充值
//		cat.setType(CompanyAccountHistoryType.FILLIN);
//
//		cat.setTotalAmount(totalFees);
//		cat.setPoundage(iah.getPoundage());
//		cat.setPurpose("用户充值");
//		cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
//		
//		cat.setCreator(investor.getUuid());
//		cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
//		cat.setInvestor(investor.getUuid());
//		cat.setInvestorAccountHistory(iah.getUuid());
////		this.companyAccountHistoryService.insert(cat);
//		try {
//			this.investorAccountHistoryService.insertTestRecharge(obt, iah, investor, ca, cat, pa, paf, pai);
//			return ResultManager.createSuccessResult("充值成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			super.flushAll();
//			return ResultManager.createFailResult("充值失败");
//		}
//	}
//	
//	/**
//	 * 模拟用户提现
//	 * @return
//	 */
//	@RequestMapping(value = "/withdraw", method = RequestMethod.GET)
//	@ResponseBody
//	public Result withdraw(){
//		String uuid = "";
//		//取管理员信息
//		Subject subject = SecurityUtils.getSubject();
//		Session session = subject.getSession();
//		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
//		if("1d2f3518-83f7-42cd-84df-edef10e615f2".equals(currentOperator.getUuid())){//龙哥
//			uuid = "68c354d3-4b75-4e26-8929-8b16af2623ec";
//		} else {
//			uuid = "bb4ac373-33e2-42d0-8ce8-b004bb5f0e1f";//老大
//		}
//		
//		Investor investor = this.investorService.get(uuid);
//		if(investor == null){
//			return ResultManager.createFailResult("用户信息不存在!");
//		}
//		
//		String price = String.valueOf((int)(Math.random()*5000 + 100));
//		System.out.println(price);
//		if (!Utlity.isPositiveCurrency4Web(price)) {
//			return ResultManager.createFailResult("提现金额输入错误!");
//		}
//		BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));
////		pay = pay.divide(BigDecimal.valueOf(100));
//		String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_ANDROID,Utlity.BILL_PAYTYPE_CHANPAY,Utlity.BILL_PURPOSE_INCOME);
//		if(this.investorAccountHistoryService.getCheckOrderNum(orderNumStr)){
//			return ResultManager.createFailResult("手速太快，服务器未响应！");
//		}
//		
//		//验证账户余额是否充足
//		BigDecimal total = investor.getAccountBalance();
//		if(total.compareTo(pay) == -1){
//			return ResultManager.createFailResult("超出账户余额!");
//		}
//		
//		OrderinfoByThirdparty obt = new OrderinfoByThirdparty();
//		obt.setUuid(UUID.randomUUID().toString());
//		obt.setType(OrderinfoByThirdpartyType.CHANPAY);
//		obt.setInvestor(investor.getUuid());
//		obt.setOrderNum(orderNumStr);
//		obt.setBody("畅捷支付--用户提现");
//		obt.setTotalFee(pay);
//		obt.setPaySource(MD5.getMD5(investor.getUuid()));
//
//		
//		//增加提现记录
//		InvestorAccountHistory iah = new InvestorAccountHistory();
//		iah.setUuid(UUID.randomUUID().toString());
//		iah.setInvestor(investor.getUuid());//银行卡支付订单
//		iah.setPay(pay);
//		iah.setIncome(BigDecimal.ZERO);
//		iah.setOrderId(obt.getUuid());
//		iah.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_CHANPAY);
//		iah.setOrderNum(orderNumStr);
//		iah.setType(InvestorAccountHistoryType.WITHDRAW);
//		iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
//		iah.setCreatetime(new Timestamp(System.currentTimeMillis()));
//		iah.setAccountBalance(total);
//		iah.setCompanyAccount(CompanyAccountUuid.WECHART);
//		iah.setPoundage(ChanPayUtil.POUNDAGE);
//		
//		//减少公司账户余额
//		CompanyAccount ca = this.companyAccountService.get(iah.getCompanyAccount());
//		if(ca == null){
//			return ResultManager.createFailResult("提现账户错误!");
//		}
//		BigDecimal accountTotal = ca.getAccountBalance();
//		if(accountTotal.compareTo(pay) == -1){
//			return ResultManager.createFailResult("提现账户余额不足!");
//		}
//		//银行付款接口调用 返回成功后
//		obt.setStatus(OrderinfoByThirdpartyReturnStatus.WITHDRAWAL_SUCCESS);
//		obt.setCreatetime(new Timestamp(System.currentTimeMillis()));
//		obt.setErrCode("");
//		obt.setErrCodeDes("");
//		obt.setMessage("提现成功");
//		
//		obt.setPaytime(new Timestamp(System.currentTimeMillis()));
//		total = total.subtract(pay);
//		investor.setAccountBalance(total);
//		iah.setAccountBalance(total);
//		
//		accountTotal = accountTotal.subtract(pay);
//		//更新企业账户余额
//		PlatformAccount pa = platformAccountService.get(PlatformAccountUuid.TOTAL);
//		BigDecimal totalAmount = pa.getTotalAmount();
//		totalAmount = totalAmount.subtract(pay);
//		
//		//更新系统总帐户余额
//		PlatformAccount paf = platformAccountService.get(PlatformAccountUuid.BALANCE);
//		BigDecimal totalAmountf = paf.getTotalAmount();
////		totalAmountf = totalAmountf.subtract(pay);
//		
//		PlatformAccount pai = platformAccountService.get(PlatformAccountUuid.INVESTOR);
//		pai.setTotalAmount(pai.getTotalAmount().subtract(pay));
//		
//		if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
//			accountTotal = accountTotal.subtract(iah.getPoundage());
//			totalAmount = totalAmount.subtract(iah.getPoundage());
//			totalAmountf = totalAmountf.subtract(iah.getPoundage());
//		}
//		
//		ca.setAccountBalance(accountTotal);
//		//更新系统总帐户余额
//		pa.setTotalAmount(totalAmount);
//		paf.setTotalAmount(totalAmountf);
//		
//		
//		//增加公司账户交易记录--用户提现
//		CompanyAccountHistory cat = new CompanyAccountHistory();
//		cat.setUuid(UUID.randomUUID().toString());
//		cat.setCompanyAccountOut(iah.getCompanyAccount());//用户提现
//		cat.setType(CompanyAccountHistoryType.TAKEOUT);
//	
//		cat.setTotalAmount(pay);
//		cat.setPoundage(iah.getPoundage());
//		cat.setPurpose("用户提现");
//		cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
//		cat.setInvestor(investor.getUuid());
//		
//		cat.setCreator(investor.getUuid());
//		cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
//		
//		cat.setInvestorAccountHistory(iah.getUuid());
////		this.companyAccountDAO.update(ca);
////		this.platformAccountDAO.update(pa);
////		this.companyAccountHistoryDAO.insert(cat);
////		if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
//		//增加公司账户交易记录--费用录入（扣除交易手续费）
//		CompanyAccountHistory catp = new CompanyAccountHistory();
//		catp.setUuid(UUID.randomUUID().toString());
//		catp.setCompanyAccountOut(iah.getCompanyAccount());//用户充值
//		catp.setType(CompanyAccountHistoryType.EXPEND);
//
//		catp.setTotalAmount(iah.getPoundage());
//		catp.setPoundage(BigDecimal.ZERO);
//		catp.setPurpose("用户提现--手续费扣除");
//		catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
//		catp.setInvestor(investor.getUuid());
//		catp.setCreator(investor.getUuid());
//		catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
//		catp.setInvestorAccountHistory(iah.getUuid());
//		catp.setRelated(cat.getUuid());
//		cat.setRelated(catp.getUuid());
//		try {
//			this.investorAccountHistoryService.insertTestWithdraw(obt, iah, investor, ca, cat, catp, pa, paf, pai);
//			return ResultManager.createDataResult("提现成功！");
//		} catch (Exception e) {
//			e.printStackTrace();
//			super.flushAll();
//			return ResultManager.createFailResult("提现失败！");
//		}
//	}
	
	public static void main(String[] args) {
		int r = (int) (Math.random()*50000 + 1000);
		System.out.println(r);
	}
}
