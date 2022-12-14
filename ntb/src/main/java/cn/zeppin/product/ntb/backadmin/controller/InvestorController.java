package cn.zeppin.product.ntb.backadmin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorAccountHistoryService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorBankcardService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorIdcardImgService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorInformationService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorProductBuyService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.backadmin.service.api.IOrderinfoByThirdpartyService;
import cn.zeppin.product.ntb.backadmin.service.api.IPlatformAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.InvestorAccountHistoryVO;
import cn.zeppin.product.ntb.backadmin.vo.InvestorAccountHistoryWithdrawVO;
import cn.zeppin.product.ntb.backadmin.vo.InvestorIdcardImgVO;
import cn.zeppin.product.ntb.backadmin.vo.InvestorVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.Investor.InvestorStatus;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryProcessStatus;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.InvestorBankcard;
import cn.zeppin.product.ntb.core.entity.InvestorBankcard.InvestorBankcardStatus;
import cn.zeppin.product.ntb.core.entity.InvestorIdcardImg;
import cn.zeppin.product.ntb.core.entity.InvestorIdcardImg.InvestorIdcardImgStatus;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy;
import cn.zeppin.product.ntb.core.entity.OrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeProductBuyService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.utility.Utlity;

/**
 * ???????????????????????????
 * 
 **/

@Controller
@RequestMapping(value = "/backadmin/investor")
public class InvestorController extends BaseController{
	
	@Autowired
	private IInvestorService investorService;
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	@Autowired
	private IInvestorIdcardImgService investorIdcardImgService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IInvestorAccountHistoryService investorAccountHistoryService;
	
	@Autowired
	private IQcbEmployeeHistoryService qcbEmployeeHistoryService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IInvestorBankcardService investorBankcardService;
	
	@Autowired
	private IInvestorProductBuyService investorProductBuyService;
	
	@Autowired
	private IQcbEmployeeProductBuyService qcbEmployeeProductBuyService;
	
	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private IOrderinfoByThirdpartyService orderinfoByThirdpartyService;
	
	@Autowired
	private IPlatformAccountService platformAccountService;
	
	@Autowired
	private IInvestorInformationService investorInformationService;
	
	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "uuid", required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		
		Investor investor = investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		InvestorVO investorvo = new InvestorVO(investor);
		if(investor.getReferrer() != null){
			Investor refferrer = investorService.get(investor.getReferrer());
			if(refferrer != null){
				investorvo.setReferrerName(refferrer.getRealname()+"_"+refferrer.getNickname());
			}else{
				investorvo.setReferrerName("???");
			}
		}
		
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("investor", investor.getUuid());
		searchMap.put("status", InvestorBankcardStatus.NORMAL);
		Integer countBankcard = this.investorBankcardService.getCount(searchMap);
		if(countBankcard > 0){
			investorvo.setBindingBankcardFlag(true);
		} else {
			investorvo.setBindingBankcardFlag(false);
		}
		return ResultManager.createDataResult(investorvo);
	}  
	
	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public Result test() {
		this.investorService.updateYesterdayAccount();
		return ResultManager.createSuccessResult("");
	}
	
	/**
	 * ???????????????????????????????????? 
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
	@ActionParam(key = "status", message = "??????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message = "????????????", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		if(name != null && !"".equals(name)){
			searchMap.put("name", name);
		}
		
		if(status != null && !"".equals(status) && !"all".equals(status)){
			searchMap.put("checkstatus", status);
		}
		
		Integer totalResultCount = investorService.getCount(searchMap);
		List<Entity> list = investorService.getListForPage(searchMap, pageNum, pageSize, sorts, Investor.class);
		List<InvestorVO> listvo = new ArrayList<InvestorVO>();
		if(list != null && list.size() > 0){
			for(Entity entity : list){
				Investor in = (Investor)entity;
				InvestorVO invo = new InvestorVO(in);
				if(in.getReferrer() != null){
					Investor refferrer = investorService.get(in.getReferrer());
					if(refferrer != null){
						invo.setReferrerName(refferrer.getRealname()+"_"+refferrer.getNickname());
					}else{
						invo.setReferrerName("???");
					}
				}
				if(in.getIdcardImg() != null){
					InvestorIdcardImg img = this.investorIdcardImgService.get(in.getIdcardImg());
					if(img != null){
						invo.setCheckstatus(img.getStatus());
					} else {
						invo.setCheckstatus(InvestorIdcardImgStatus.NOTUPLOAD);
					}
				} else {
					invo.setCheckstatus(InvestorIdcardImgStatus.NOTUPLOAD);
				}
				if(InvestorStatus.NORMAL.equals(in.getStatus())){
					String checkStatusCN = "???";
					if(InvestorIdcardImgStatus.NOTUPLOAD.equals(invo.getCheckstatus())){
						checkStatusCN = "???????????????";
					} else if (InvestorIdcardImgStatus.CHECKED.equals(invo.getCheckstatus())) {
						checkStatusCN = "??????";
					} else if (InvestorIdcardImgStatus.UNCHECKED.equals(invo.getCheckstatus())) {
						checkStatusCN = "???????????????";
					} else if (InvestorIdcardImgStatus.UNPASSED.equals(invo.getCheckstatus())) {
						checkStatusCN = "?????????????????????";
					}
					invo.setStatusCN(checkStatusCN);
				}
				searchMap.clear();
				searchMap.put("investor", in.getUuid());
				searchMap.put("status", InvestorBankcardStatus.NORMAL);
				Integer countBankcard = this.investorBankcardService.getCount(searchMap);
				if(countBankcard > 0){
					invo.setBindingBankcardFlag(true);
				} else {
					invo.setBindingBankcardFlag(false);
				}
				listvo.add(invo);
			}
		}
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		Map<String, String> searchMap = new HashMap<String, String>();
		//????????????????????????????????????
		List<Entity> list = investorIdcardImgService.getStatusList(searchMap, StstusCountVO.class);
		List<Entity> listNotupload = investorService.getNotuploadCount(StstusCountVO.class);
		list.addAll(listNotupload);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ????????????????????????????????????
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/idcardimgList", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "status", message = "??????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message = "????????????", type = DataType.STRING)
	@ResponseBody
	public Result idcardimgList(String name, String status, Integer pageNum, Integer pageSize, String sorts){
		
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!Utlity.checkStringNull(name)){
			searchMap.put("name", name);
		}
		
		if(!Utlity.checkStringNull(status) && !"all".equals(status)){
			searchMap.put("status", status);
		}
		
		Integer totalResultCount = investorIdcardImgService.getCountForBackPage(searchMap);
		List<Entity> list = investorIdcardImgService.getListForBackPage(searchMap, pageNum, pageSize, sorts, InvestorIdcardImgVO.class);
		if(list != null && list.size() > 0){
			for(Entity entity : list){
				InvestorIdcardImgVO id = (InvestorIdcardImgVO)entity;
				
				Resource imgface = this.resourceService.get(id.getImgface());
				if(imgface == null){
					return ResultManager.createFailResult("?????????????????????");
				}
				Resource imgback = this.resourceService.get(id.getImgback());
				if(imgback == null){
					return ResultManager.createFailResult("?????????????????????");
				}
				id.setImgfaceurl(imgface.getUrl());
				id.setImgbackurl(imgback.getUrl());
				id.setCreatetimeCN(Utlity.timeSpanToString(id.getCreatetime()));
				if(id.getChecktime() != null){
					id.setChecktimeCN(Utlity.timeSpanToString(id.getChecktime()));
				}
				
				if(id.getChecker() != null && !"".equals(id.getChecker())){
					BkOperator checker = this.bkOperatorService.get(id.getChecker());
					if(checker != null){
						id.setCheckerName(checker.getRealname());
					}
				}
			}
		}
		return ResultManager.createDataResult(list, pageNum, pageSize, totalResultCount);
		
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
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		Map<String, String> inputParams = new HashMap<String, String>();
		if(name != null && !"".equals(name)){
			inputParams.put("name", name);
		}
		inputParams.put("investor", investor.getUuid());
		Integer totalResultCount = investorAccountHistoryService.getCount(inputParams);
		List<Entity> list = this.investorAccountHistoryService.getListForPage(inputParams, pageNum, pageSize, null, InvestorAccountHistory.class);
		List<InvestorAccountHistoryVO> listVO = new ArrayList<InvestorAccountHistoryVO>();
		for(Entity entity : list){
			InvestorAccountHistory iah = (InvestorAccountHistory)entity;
			InvestorAccountHistoryVO iahVO = new InvestorAccountHistoryVO(iah);
			
			if (InvestorAccountHistoryType.BUY.equals(iahVO.getType()) || InvestorAccountHistoryType.DIVIDEND.equals(iahVO.getType()) 
					|| InvestorAccountHistoryType.RETURN.equals(iahVO.getType())) {
				inputParams.clear();
				inputParams.put("product", iahVO.getProduct());
				inputParams.put("investor", iahVO.getInvestor());
				List<Entity> listProductRecords = this.investorProductBuyService.getListForPage(inputParams, -1, -1, null, InvestorProductBuy.class);
				String remark = "";
				if(listProductRecords != null && listProductRecords.size() > 0){
					InvestorProductBuy ipbr = (InvestorProductBuy) listProductRecords.get(0);
					BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(ipbr.getProduct());
					Bank bank = this.bankService.get(bfpp.getCustodian());
					remark = "???"+bank.getShortName()+"???"+bfpp.getShortname();
				}
				iahVO.setRemark(iahVO.getRemark()+remark);
//				iahVO.setRemark(iahVO.getRemark()+"???????????????????????????????????????1???1724????????????????????????");
			}
			listVO.add(iahVO);
		}
		
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ????????????
	 * @param uuid
	 * @param userType
	 * @param billid
	 * @return
	 */
	@RequestMapping(value = "/getBillInfo", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="????????????", required = true)
	@ActionParam(key = "userType", type = DataType.STRING, message="????????????")
	@ActionParam(key = "billid", type = DataType.STRING, message="????????????", required = true)
	@ResponseBody
	public Result getBillInfo(String uuid, String userType, String billid){
		if("qcbEmployee".equals(userType)){
			QcbEmployee qe = this.qcbEmployeeService.get(uuid);
			if(qe == null){
				return ResultManager.createFailResult("????????????????????????");
			}
			
			QcbEmployeeHistory qeh = this.qcbEmployeeHistoryService.get(billid);
			if(qeh == null){
				return ResultManager.createFailResult("????????????????????????");
			}
			if(!uuid.equals(qeh.getQcbEmployee())){
				return ResultManager.createFailResult("?????????????????????");
			}
			InvestorAccountHistoryVO vo = new InvestorAccountHistoryVO(qeh);
			if (QcbEmployeeHistoryType.BUY.equals(vo.getType()) || QcbEmployeeHistoryType.DIVIDEND.equals(vo.getType()) 
					|| QcbEmployeeHistoryType.RETURN.equals(vo.getType())) {
				Map<String, String> inputParams = new HashMap<String, String>();
				inputParams.put("product", vo.getProduct());
				inputParams.put("qcbEmployee", vo.getInvestor());
				List<Entity> listProductRecords = this.qcbEmployeeProductBuyService.getListForPage(inputParams, -1, -1, null, QcbEmployeeProductBuy.class);
				String remark = "";
				if(listProductRecords != null && listProductRecords.size() > 0){
					QcbEmployeeProductBuy qepbr = (QcbEmployeeProductBuy) listProductRecords.get(0);
					BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(qepbr.getProduct());
					Bank bank = this.bankService.get(bfpp.getCustodian());
					remark = "???"+bank.getShortName()+"???"+bfpp.getShortname();
				}
				vo.setRemark(vo.getRemark()+remark);
			}
			return ResultManager.createDataResult(vo, "????????????");
		} else {
			Investor investor = this.investorService.get(uuid);
			if(investor == null){
				return ResultManager.createFailResult("????????????????????????");
			}
			
			InvestorAccountHistory iah = this.investorAccountHistoryService.get(billid);
			if(iah == null){
				return ResultManager.createFailResult("????????????????????????");
			}
			if(!uuid.equals(iah.getInvestor())){
				return ResultManager.createFailResult("?????????????????????");
			}
			InvestorAccountHistoryVO vo = new InvestorAccountHistoryVO(iah);
			if (InvestorAccountHistoryType.BUY.equals(vo.getType()) || InvestorAccountHistoryType.DIVIDEND.equals(vo.getType()) 
					|| InvestorAccountHistoryType.RETURN.equals(vo.getType())) {
				Map<String, String> inputParams = new HashMap<String, String>();
				inputParams.put("product", vo.getProduct());
				inputParams.put("investor", vo.getInvestor());
				List<Entity> listProductRecords = this.investorProductBuyService.getListForPage(inputParams, -1, -1, null, InvestorProductBuy.class);
				String remark = "";
				if(listProductRecords != null && listProductRecords.size() > 0){
					InvestorProductBuy ipbr = (InvestorProductBuy) listProductRecords.get(0);
					BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(ipbr.getProduct());
					Bank bank = this.bankService.get(bfpp.getCustodian());
					remark = "???"+bank.getShortName()+"???"+bfpp.getShortname();
				}
				vo.setRemark(vo.getRemark()+remark);
			}
			return ResultManager.createDataResult(vo, "????????????");
		}
	}
	
	/**
	 * ??????????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/idcardimgStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result idcardimgStatusList() {
		Map<String, String> searchMap = new HashMap<String, String>();
		//????????????????????????????????????
		List<Entity> list = investorIdcardImgService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ?????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/getidcardimgInfo", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result getidcardimgInfo(String uuid){
		InvestorIdcardImg id = this.investorIdcardImgService.get(uuid);
		if(id != null){
			InvestorIdcardImgVO idvo = new InvestorIdcardImgVO(id);
			Resource imgface = this.resourceService.get(id.getImgface());
			if(imgface == null){
				return ResultManager.createFailResult("?????????????????????");
			}
			Resource imgback = this.resourceService.get(id.getImgback());
			if(imgback == null){
				return ResultManager.createFailResult("?????????????????????");
			}
			Investor investor = this.investorService.get(id.getCreator());
			if(investor == null){
				return ResultManager.createFailResult("??????????????????");
			}
			idvo.setImgfaceurl(imgface.getUrl());
			idvo.setImgbackurl(imgback.getUrl());
			idvo.setName(investor.getRealname());
			idvo.setIdcard(investor.getIdcard());
			if(id.getChecker() != null && !"".equals(id.getChecker())){
				BkOperator checker = this.bkOperatorService.get(id.getChecker());
				if(checker != null){
					idvo.setCheckerName(checker.getRealname());
				}
			}
			return ResultManager.createDataResult(idvo);
		}
		return ResultManager.createFailResult("?????????????????????");
	}
	
	/**
	 * ???????????????
	 * @param uuid
	 * @param status
	 * @param reason
	 * @return
	 */
	@RequestMapping(value = "/checked", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message = "??????", type = DataType.STRING, required = true)
	@ActionParam(key = "reason", message = "????????????", type = DataType.STRING)
	@ResponseBody
	public Result checked(String uuid, String status, String reason){
		
		InvestorIdcardImg id = this.investorIdcardImgService.get(uuid);
		if(id == null){
			return ResultManager.createFailResult("?????????????????????");
		}
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		/*
		 * ????????????
		 */
		if(InvestorIdcardImgStatus.UNCHECKED.equals(id.getStatus())){
			if(InvestorIdcardImgStatus.CHECKED.equals(status)){//????????????
				if(Utlity.checkStringNull(reason)){
					reason = "???????????????";
				}
				id.setStatus(InvestorIdcardImgStatus.CHECKED);
			} else if (InvestorIdcardImgStatus.UNPASSED.equals(status)) {//???????????????
				if(Utlity.checkStringNull(reason)){
					reason = "??????????????????";
				}
				id.setStatus(InvestorIdcardImgStatus.UNPASSED);
			} else {
				return ResultManager.createFailResult("??????????????????");
			}
			id.setReason(reason);
			id.setChecker(currentOperator.getUuid());
			id.setChecktime(new Timestamp(System.currentTimeMillis()));
			this.investorIdcardImgService.update(id);
		} else {
			return ResultManager.createFailResult("??????????????????");
		}
		
		return ResultManager.createSuccessResult("??????");
	}
	
	/**
	 * ????????????????????????????????????
	 * @param name
	 * @param status ????????????   processing--????????? unprocess--????????? success--???????????? fail--????????????
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/withdrawList", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "status", message = "??????", type = DataType.STRING, required = true)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message = "????????????", type = DataType.STRING)
	@ResponseBody
	public Result withdrawList(String name, String status, Integer pageNum, Integer pageSize, String sorts){
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		if(name != null && !"".equals(name)){
			searchMap.put("name", name);
		}
		
		if(status != null && !"".equals(status) && !"all".equals(status)){
			searchMap.put("processingStatus", status);
		}
		searchMap.put("type", InvestorAccountHistoryType.WITHDRAW);
		Integer totalResultCount = this.investorAccountHistoryService.getCount(searchMap);
		List<Entity> list = this.investorAccountHistoryService.getListForWithdrawPage(searchMap, pageNum, pageSize, sorts, InvestorAccountHistory.class);
		List<InvestorAccountHistoryWithdrawVO> listVO = new ArrayList<InvestorAccountHistoryWithdrawVO>();
		for(Entity e : list){
			InvestorAccountHistory iah = (InvestorAccountHistory)e;
			InvestorAccountHistoryWithdrawVO iahvo = new InvestorAccountHistoryWithdrawVO(iah);
			
			Investor i = this.investorService.get(iah.getInvestor());
			if(i != null){
				iahvo.setInvestorName(i.getRealname());
			}
//			if(!Utlity.checkStringNull(iahvo.getBankcard())){
//				InvestorBankcard ib = this.investorBankcardService.get(iahvo.getBankcard());
//				iahvo.setb
//			}
			
			if(!Utlity.checkStringNull(iahvo.getProcessCompanyAccount())){
				CompanyAccount ca = this.companyAccountService.get(iahvo.getProcessCompanyAccount());
				if(ca != null){
					iahvo.setProcessCompanyAccountName(ca.getAccountName());
				} else {
					iahvo.setProcessCompanyAccountName("-");
				}
			}
			
			if(!Utlity.checkStringNull(iahvo.getProcessCreator())){
				BkOperator op = this.bkOperatorService.get(iahvo.getProcessCreator());
				if(op != null){
					iahvo.setProcessCreatorName(op.getRealname());
				} else {
					iahvo.setProcessCreatorName("-");
				}
			}
			
			if(!Utlity.checkStringNull(iahvo.getOrder())){
				OrderinfoByThirdparty obt = this.orderinfoByThirdpartyService.get(iahvo.getOrder());
				if(obt != null){
					iahvo.setOrderMessage(obt.getMessage());
				} else {
					iahvo.setOrderMessage("");
				}
			}
			
			listVO.add(iahvo);
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ??????????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/withdrawStatusList", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "????????????", type = DataType.STRING)
	@ResponseBody
	public Result withdrawStatusList(String name){
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		if(name != null && !"".equals(name)){
			searchMap.put("name", name);
		}
		
		List<Entity> list = this.investorAccountHistoryService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ?????????????????????????????????(??????)
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/withdrawEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result withdrawEdit(String uuid){
		String[] uuidArr = uuid.split(",");
		List<InvestorAccountHistory> iahList = new ArrayList<InvestorAccountHistory>();
		for(String uid : uuidArr){
			InvestorAccountHistory iah = this.investorAccountHistoryService.get(uid);
			if(iah == null){
				return ResultManager.createFailResult("????????????????????????????????????????????????");
			}
			iahList.add(iah);
		}
		//??????????????????
		try {
			HashMap<String, Object> result = this.investorAccountHistoryService.updateWithdrawBatch(iahList);
			if((Boolean)result.get("result")){
				Integer count = Integer.parseInt(result.get("count").toString());
				return ResultManager.createSuccessResult("????????????????????????"+count+"???????????????");
			} else {
				return ResultManager.createFailResult("??????");
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("?????????????????????");
		}
	}
	
	/**
	 * ???????????????(??????)
	 * @param uuid
	 * @param status ????????????   processing--?????????
	 * @return
	 */
	@RequestMapping(value = "/withdrawProcess", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message = "??????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result withdrawProcess(String uuid, String status){
		String[] uuidArr = uuid.split(",");
		List<InvestorAccountHistory> iahList = new ArrayList<InvestorAccountHistory>();
		for(String uid : uuidArr){
			InvestorAccountHistory iah = this.investorAccountHistoryService.get(uid);
			if(iah == null){
				return ResultManager.createFailResult("????????????????????????????????????????????????");
			}
			if(InvestorAccountHistoryProcessStatus.PROCESSING.equals(status)){
				iah.setProcessingStatus(status);
			} else {
				iah.setProcessingStatus(InvestorAccountHistoryProcessStatus.PROCESSING);
			}
			iahList.add(iah);
		}
		try {
			this.investorAccountHistoryService.updateBatch(iahList);
			return ResultManager.createSuccessResult("????????????");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("?????????????????????");
		}
		
	}
	
	/**
	 * ????????????????????????
	 * status ????????????   processing--????????? unprocess--????????? success--???????????? fail--????????????
	 * @return
	 */
	@RequestMapping(value = "/withdrawExport", method = RequestMethod.GET)
	@ActionParam(key = "status", message = "??????", type = DataType.STRING)
	@ResponseBody
	public ModelAndView withdrawExport(String status, HttpServletRequest request, HttpServletResponse response){
		Map<String, String> searchMap = new HashMap<String, String>();
		if(status != null && !"".equals(status) && !"all".equals(status)){
			searchMap.put("processingStatus", status);
		}
		searchMap.put("type", InvestorAccountHistoryType.WITHDRAW);
		List<Entity> list = this.investorAccountHistoryService.getListForWithdrawPage(searchMap, -1, -1, null, InvestorAccountHistory.class);
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "????????????????????????");
			XSSFRow row = s.createRow(0);
//			String title[] = { "??????", "????????????", "??????", "????????????", "????????????", "??????????????????", "??????????????????", "???????????????", "??????", "??????", "????????????", "????????????", "????????????????????????", "????????????????????????" };
			String title[] = { "??????", "????????????", "??????", "????????????", "????????????", "??????????????????", "??????????????????", "???????????????", "??????", "??????", "????????????", "????????????", "????????????????????????", "????????????????????????" };
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			int t = 0;
			for(Entity entity : list){
				t++;
				row = s.createRow(t);
				InvestorAccountHistory iah = (InvestorAccountHistory)entity;
				InvestorAccountHistoryWithdrawVO iahvo = new InvestorAccountHistoryWithdrawVO(iah);
				Investor i = this.investorService.get(iah.getInvestor());
				
				String name = i.getRealname();
//				String orderNum = iahvo.getOrderNum();
				String price = iahvo.getPrice();
				InvestorBankcard ib = this.investorBankcardService.get(iah.getBankcard());
				String bankcardnum = ib.getBindingBankCard().split("_#")[0];
				Bank bank = this.bankService.get(ib.getBank());
				String bankName = bank.getName();
				String bankCode = bank.getCode();
				String orderTypeCN = iahvo.getOrderTypeCN();
				String application = iahvo.getApplication();
				String reason = "";
				OrderinfoByThirdparty obt = this.orderinfoByThirdpartyService.get(iah.getOrderId());
				if(obt != null){
					reason = obt.getMessage();
				}
				String time = iahvo.getCreatetimeCN();
				String processingStatus = iahvo.getProcessingStatusCN();
				String companyAccountNum = "";
				String companyAccountName = "";
				
//				String tInfo[] = { t+"", orderNum, name, price, bankName, bankCode, bankcardnum, orderTypeCN, application, reason, time, processingStatus, companyAccountName, companyAccountNum };
				String tInfo[] = { t+"", iah.getUuid(), name, price, bankName, bankCode, bankcardnum, orderTypeCN, application, reason, time, processingStatus, companyAccountName, companyAccountNum };
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=withdrawList.xlsx");
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {  
			if(ouputStream != null){
				try {
					ouputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
			if(wb != null){
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
        } 
		
		return null;
	}
	
	/**
	 * ???????????????????????????
	 * status ????????????   processing--????????? unprocess--????????? success--???????????? fail--????????????
	 * @return
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "/withdrawImport", method = RequestMethod.POST)
	@ActionParam(key = "file", message = "????????????UUID", type = DataType.STRING, required = true)
	@ResponseBody
	public Result withdrawImport(String file, HttpServletRequest request, HttpServletResponse response){
		String message = "???????????????";
		Resource r = this.resourceService.get(file);
		if(r == null){
			return ResultManager.createFailResult("?????????????????????????????????");
		}
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		try {
			String resourcePath = r.getUrl();
			String serverPath = request.getServletContext().getRealPath("/").replace("\\", "/");
			File realfile = new File(serverPath + resourcePath);
			InputStream is = null;
			boolean isE2007 = false; // ???????????????excel2007??????
			if (resourcePath.endsWith("xlsx")) {
				isE2007 = true;
			}
			if (realfile.exists()) {
				try {
					is = new FileInputStream(realfile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			Workbook wb = null;
			if (is == null) {
				return ResultManager.createFailResult("?????????????????????????????????");
			}
			if (isE2007) {
				wb = new XSSFWorkbook(is);
			} else {
				wb = new HSSFWorkbook(is);
			}
			Sheet s = wb.getSheetAt(0);
			Row row;
			int t = s.getLastRowNum(); 
			List<InvestorAccountHistory> iahList = new ArrayList<InvestorAccountHistory>();
			Map<String, InvestorAccountHistory> iahMap = new HashMap<String, InvestorAccountHistory>();
			for(int i = 1; i <= t; i++){
				row = s.getRow(i);
				String orderNum = "";
				String companyAccountName = "";
				String companyAccountNum = "";
				
				if (row.getCell(1) != null) {
					row.getCell(1).setCellType(CellType.STRING);
					orderNum = row.getCell(1).getStringCellValue();
				}
				
				if (row.getCell(12) != null) {
					row.getCell(12).setCellType(CellType.STRING);
					companyAccountName = row.getCell(12).getStringCellValue();
				}
				
				if (row.getCell(13) != null) {
					row.getCell(13).setCellType(CellType.STRING);
					companyAccountNum = row.getCell(13).getStringCellValue();
				}
				
				if(orderNum == null || "".equals(orderNum)){
					message = "???"+i+"??????????????????????????????";
					return ResultManager.createFailResult(message);
				}
				
				if(companyAccountName == null || "".equals(companyAccountName)){
					message = "???"+i+"??????????????????????????????????????????";
					return ResultManager.createFailResult(message);
				}
				
				if(companyAccountNum == null || "".equals(companyAccountNum)){
					message = "???"+i+"??????????????????????????????????????????";
					return ResultManager.createFailResult(message);
				}
				
				if(iahMap.containsKey(orderNum)){
					message = "???"+i+"????????????????????????????????????";
					return ResultManager.createFailResult(message);
				}
				InvestorAccountHistory iah = this.investorAccountHistoryService.get(orderNum);
				
				if(iah == null){
					return ResultManager.createFailResult("??????????????????");
				}
				if(!InvestorAccountHistoryType.WITHDRAW.equals(iah.getType())){
					return ResultManager.createFailResult("?????????????????????");
				}
				if(InvestorAccountHistoryStatus.SUCCESS.equals(iah.getStatus())){
					continue;
				}
				iah.setPoundage(BigDecimal.ZERO);
				iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
				iah.setProcessCreator(currentOperator.getUuid());
				iah.setProcessCreatetime(new Timestamp(System.currentTimeMillis()));
				iah.setProcessingStatus(InvestorAccountHistoryProcessStatus.SUCCESS);
				Map<String, String> inputParams = new HashMap<String, String>();
				inputParams.put("accountName", companyAccountName);
				inputParams.put("accountNum", companyAccountNum);
				List<Entity> listCa = this.companyAccountService.getListForPage(inputParams, -1, -1, null, CompanyAccount.class);
				if(listCa != null && !listCa.isEmpty()){
					CompanyAccount ca = (CompanyAccount) listCa.get(0);
					iah.setProcessCompanyAccount(ca.getUuid());
					iahMap.put(orderNum, iah);
				} else {
					return ResultManager.createFailResult("?????????????????????");
				}
			}
			if(!iahMap.isEmpty()){
				for(InvestorAccountHistory iah : iahMap.values()){
					iahList.add(iah);
				}
			}
			this.investorAccountHistoryService.updateWithdraw(iahList);
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("??????????????????");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("??????????????????");
		}
		
		return ResultManager.createSuccessResult("????????????");
	}
}
