package cn.zeppin.product.score.controller.back;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.score.controller.base.ActionParam;
import cn.zeppin.product.score.controller.base.ActionParam.DataType;
import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.entity.Admin;
import cn.zeppin.product.score.entity.Bank;
import cn.zeppin.product.score.entity.CapitalAccount;
import cn.zeppin.product.score.entity.CapitalPlatform;
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.FrontUserBankcard;
import cn.zeppin.product.score.entity.FrontUserHistory;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.zeppin.product.score.entity.FrontUserHistoryCheck;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.service.AdminService;
import cn.zeppin.product.score.service.BankService;
import cn.zeppin.product.score.service.CapitalAccountService;
import cn.zeppin.product.score.service.CapitalPlatformService;
import cn.zeppin.product.score.service.FrontUserAccountService;
import cn.zeppin.product.score.service.FrontUserBankcardService;
import cn.zeppin.product.score.service.FrontUserHistoryCheckService;
import cn.zeppin.product.score.service.FrontUserHistoryService;
import cn.zeppin.product.score.service.FrontUserService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.back.FrontUserHistoryVO;
import cn.zeppin.product.score.vo.back.StatusCountVO;

@Controller
@RequestMapping(value = "/back/userHistory")
public class UserHistoryController extends BaseController{
	
	@Autowired
    private FrontUserService frontUserService;
	
	@Autowired
    private FrontUserAccountService frontUserAccountService;
	
	@Autowired
    private FrontUserBankcardService frontUserBankcardService;
	
	@Autowired
    private FrontUserHistoryService frontUserHistoryService;
	
	@Autowired
    private FrontUserHistoryCheckService frontUserHistoryCheckService;
	
	@Autowired
    private CapitalAccountService capitalAccountService;
	
	@Autowired
    private CapitalPlatformService capitalPlatformService;
	
	@Autowired
    private BankService bankService;
	
	@Autowired
    private AdminService adminService;
	
	@Autowired
    private ResourceService resourceService;
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		FrontUserHistory fuh = frontUserHistoryService.get(uuid);
		if(fuh == null || FrontUserHistoryStatus.DELETE.equals(fuh.getStatus())){
			return ResultManager.createFailResult("交易信息不存在");
		}
		
		FrontUserHistoryVO fuhvo = new FrontUserHistoryVO(fuh);
		
		FrontUser fu = this.frontUserService.get(fuh.getFrontUser());
		if(fu != null){
			fuhvo.setFrontUserName(fu.getRealname());
			fuhvo.setFrontUserMobile(fu.getMobile());
		}
		
		if(!Utlity.checkStringNull(fuh.getCapitalAccount())){
			CapitalAccount ca = this.capitalAccountService.get(fuh.getCapitalAccount());
			if(ca != null){
				fuhvo.setCapitalAccountName(ca.getName());
				
				CapitalPlatform cp = this.capitalPlatformService.get(ca.getCapitalPlatform());
				if(cp != null){
					fuhvo.setCapitalPlatform(cp.getUuid());
					fuhvo.setCapitalPlatformName(cp.getName());
				}
			}
		}
		
		if(!Utlity.checkStringNull(fuh.getFrontUserBankcard())){
			FrontUserBankcard fub = this.frontUserBankcardService.get(fuh.getFrontUserBankcard());
			if(fub != null){
				fuhvo.setFrontUserBankcardAccountHolder(fub.getAccountHolder());
				fuhvo.setFrontUserBankcardAccountNumber(fub.getAccountNumber());
				Bank bank = this.bankService.get(fub.getBank());
				if(bank != null){
					fuhvo.setFrontUserBankcardBankName(bank.getShortName());
				}
			}
		}
		
		if(!Utlity.checkStringNull(fuh.getOperator())){
			Admin operator = this.adminService.get(fuh.getOperator());
			if(operator != null){
				fuhvo.setOperatorName(operator.getRealname());
			}
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("frontUserHistory", uuid);
		
		List<FrontUserHistoryCheck> checkList = this.frontUserHistoryCheckService.getListByParams(params);
		if(checkList.size() > 0){
			FrontUserHistoryCheck check = checkList.get(0);
			fuhvo.setFrontUserAccountCheck(check.getUuid());
			
			if(!Utlity.checkStringNull(check.getProof())){
				Resource proof = this.resourceService.get(check.getProof());
				if(proof != null){
					fuhvo.setFrontUserAccountCheckProof(proof.getUrl());
				}
			}
		}
		
		return ResultManager.createDataResult(fuhvo);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "frontUser", message="用户", type = DataType.STRING)
	@ActionParam(key = "capitalAccount", message="账户", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String frontUser, String capitalAccount, String type, String status, Integer pageNum, Integer pageSize, String sort){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("frontUser", frontUser);
		params.put("capitalAccount", capitalAccount);
		params.put("type", type);
		if(!Utlity.checkStringNull(status) && !"all".equals(status)){
			params.put("status", status);
		}
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("sort", sort);
		
		Integer totalCount = frontUserHistoryService.getCountByParams(params);
		List<FrontUserHistory> fuhList = frontUserHistoryService.getListByParams(params);
		
		List<FrontUserHistoryVO> voList = new ArrayList<FrontUserHistoryVO>();
		for(FrontUserHistory fuh : fuhList){
			FrontUserHistoryVO fuhvo = new FrontUserHistoryVO(fuh);
			
			FrontUser fu = this.frontUserService.get(fuh.getFrontUser());
			if(fu != null){
				fuhvo.setFrontUserName(fu.getRealname());
				fuhvo.setFrontUserMobile(fu.getMobile());
			}
			
			if(!Utlity.checkStringNull(fuh.getCapitalAccount())){
				CapitalAccount ca = this.capitalAccountService.get(fuh.getCapitalAccount());
				if(ca != null){
					fuhvo.setCapitalAccountName(ca.getName());
					
					CapitalPlatform cp = this.capitalPlatformService.get(ca.getCapitalPlatform());
					if(cp != null){
						fuhvo.setCapitalPlatform(cp.getUuid());
						fuhvo.setCapitalPlatformName(cp.getName());
					}
				}
			}
			
			if(!Utlity.checkStringNull(fuh.getFrontUserBankcard())){
				FrontUserBankcard fub = this.frontUserBankcardService.get(fuh.getFrontUserBankcard());
				if(fub != null){
					fuhvo.setFrontUserBankcardAccountHolder(fub.getAccountHolder());
					Bank bank = this.bankService.get(fub.getBank());
					if(bank != null){
						fuhvo.setFrontUserBankcardBankName(bank.getShortName());
					}
				}
			}
			
			if(!Utlity.checkStringNull(fuh.getOperator())){
				Admin operator = this.adminService.get(fuh.getOperator());
				if(operator != null){
					fuhvo.setOperatorName(operator.getRealname());
				}
			}
			voList.add(fuhvo);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
	}
	
	/**
	 * 获取分状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ResponseBody
	public Result statusList(String type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		
		List<StatusCountVO> list = frontUserHistoryService.getStatusList(params);
		return ResultManager.createDataResult(list,list.size());
	}
}
