package cn.zeppin.product.score.controller.back;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import cn.zeppin.product.score.entity.CapitalAccount;
import cn.zeppin.product.score.entity.CapitalPlatform;
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.FrontUserAccount;
import cn.zeppin.product.score.entity.FrontUserHistory;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.zeppin.product.score.entity.FrontUserHistoryCheck;
import cn.zeppin.product.score.entity.FrontUserHistoryCheck.FrontUserHistoryCheckStatus;
import cn.zeppin.product.score.entity.FrontUserHistoryCheck.FrontUserHistoryCheckTransType;
import cn.zeppin.product.score.entity.FrontUserHistoryCheck.FrontUserHistoryCheckType;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.service.AdminService;
import cn.zeppin.product.score.service.CapitalAccountService;
import cn.zeppin.product.score.service.CapitalPlatformService;
import cn.zeppin.product.score.service.FrontUserAccountService;
import cn.zeppin.product.score.service.FrontUserHistoryCheckService;
import cn.zeppin.product.score.service.FrontUserHistoryService;
import cn.zeppin.product.score.service.FrontUserService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.util.JSONUtils;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.back.FrontUserHistoryCheckDetailVO;
import cn.zeppin.product.score.vo.back.FrontUserHistoryCheckVO;
import cn.zeppin.product.score.vo.back.StatusCountVO;

@Controller
@RequestMapping(value = "/back/userRecharge")
public class UserRechargeController extends BaseController{
	
	@Autowired
    private FrontUserService frontUserService;
	
	@Autowired
    private FrontUserAccountService frontUserAccountService;
	
	@Autowired
    private FrontUserHistoryService frontUserHistoryService;
	
	@Autowired
    private FrontUserHistoryCheckService frontUserHistoryCheckService;
	
	@Autowired
    private CapitalAccountService capitalAccountService;
	
	@Autowired
    private CapitalPlatformService capitalPlatformService;
	
	@Autowired
    private AdminService adminService;
	
	@Autowired
    private ResourceService resourceService;
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING)
	@ResponseBody
	public Result get(String uuid){
		FrontUserHistoryCheck fuhc = this.frontUserHistoryCheckService.get(uuid);
		if(fuhc == null){
			return ResultManager.createFailResult("???????????????????????????");
		}
		if(!FrontUserHistoryCheckTransType.RECHARGE.equals(fuhc.getTransType())){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		FrontUserHistoryCheckDetailVO fuhcvo = new FrontUserHistoryCheckDetailVO(fuhc);
		if(!Utlity.checkStringNull(fuhc.getValue())){
			FrontUser fu = this.frontUserService.get(fuhcvo.getValue().getFrontUser());
			if(fu != null){
				fuhcvo.getValue().setFrontUserMobile(fu.getMobile());
				fuhcvo.getValue().setFrontUserName(fu.getRealname());
			}
			
			CapitalAccount ca = this.capitalAccountService.get(fuhcvo.getValue().getCapitalAccount());
			if(ca != null){
				fuhcvo.getValue().setCapitalAccountName(ca.getName());
			}
			CapitalPlatform cp = this.capitalPlatformService.get(ca.getCapitalPlatform());
			if(cp != null){
				fuhcvo.getValue().setCapitalPlatform(cp.getUuid());
				fuhcvo.getValue().setCapitalPlatformName(cp.getName());
			}
		}
		
		if(!Utlity.checkStringNull(fuhc.getChecker())){
			Admin checker = this.adminService.get(fuhc.getChecker());
			if(checker != null){
				fuhcvo.setCheckerName(checker.getRealname());
			}
		}
		
		Admin creator = this.adminService.get(fuhc.getCreator());
		if(creator != null){
			fuhcvo.setCreatorName(creator.getRealname());
		}
		
		if(!Utlity.checkStringNull(fuhc.getProof())){
			Resource resource = this.resourceService.get(fuhc.getProof());
			if(resource != null){
				fuhcvo.setProofUrl(resource.getUrl());
			}
		}
		
		return ResultManager.createDataResult(fuhcvo);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "type", message="??????", type = DataType.STRING)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.STRING)
	@ActionParam(key = "sort", message="??????", type = DataType.STRING)
	@ResponseBody
	public Result list(String type, String status, Integer pageNum, Integer pageSize, String sort){
		Admin admin = this.getCurrentOperator();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		if(!Utlity.checkStringNull(status) && !"all".equals(status)){
			params.put("status", status);
		}
		params.put("transType", FrontUserHistoryCheckTransType.RECHARGE);
		params.put("creator", admin.getUuid());
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("sort", sort);
		
		Integer totalCount = frontUserHistoryCheckService.getCountByParams(params);
		List<FrontUserHistoryCheck> fuhcList = frontUserHistoryCheckService.getListByParams(params);
		
		List<FrontUserHistoryCheckVO> voList = new ArrayList<FrontUserHistoryCheckVO>();
		for(FrontUserHistoryCheck fuhc : fuhcList){
			FrontUserHistoryCheckVO fuhcvo = new FrontUserHistoryCheckVO(fuhc);
			FrontUser fu = this.frontUserService.get(fuhcvo.getFrontUser());
			if(fu != null){
				fuhcvo.setFrontUserMobile(fu.getMobile());
				fuhcvo.setFrontUserName(fu.getRealname());
			}
			
			if(!Utlity.checkStringNull(fuhc.getChecker())){
				Admin checker = this.adminService.get(fuhc.getChecker());
				if(checker != null){
					fuhcvo.setCheckerName(checker.getRealname());
				}
			}
			
			Admin creator = this.adminService.get(fuhc.getCreator());
			if(creator != null){
				fuhcvo.setCreatorName(creator.getRealname());
			}
			voList.add(fuhcvo);
		}
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
	}
	
	@RequestMapping(value="/checkList",method=RequestMethod.GET)
	@ActionParam(key = "type", message="??????", type = DataType.STRING)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.STRING)
	@ActionParam(key = "sort", message="??????", type = DataType.STRING)
	@ResponseBody
	public Result checkList(String type, String status, Integer pageNum, Integer pageSize, String sort){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		if(!Utlity.checkStringNull(status) && !"all".equals(status)){
			params.put("status", status);
		}
		params.put("transType", FrontUserHistoryCheckTransType.RECHARGE);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("sort", sort);
		
		Integer totalCount = frontUserHistoryCheckService.getCountByParams(params);
		List<FrontUserHistoryCheck> fuhcList = frontUserHistoryCheckService.getListByParams(params);
		
		List<FrontUserHistoryCheckVO> voList = new ArrayList<FrontUserHistoryCheckVO>();
		for(FrontUserHistoryCheck fuhc : fuhcList){
			FrontUserHistoryCheckVO fuhcvo = new FrontUserHistoryCheckVO(fuhc);
			FrontUser fu = this.frontUserService.get(fuhcvo.getFrontUser());
			if(fu != null){
				fuhcvo.setFrontUserMobile(fu.getMobile());
				fuhcvo.setFrontUserName(fu.getRealname());
			}
			
			if(!Utlity.checkStringNull(fuhc.getChecker())){
				Admin checker = this.adminService.get(fuhc.getChecker());
				if(checker != null){
					fuhcvo.setCheckerName(checker.getRealname());
				}
			}
			
			Admin creator = this.adminService.get(fuhc.getCreator());
			if(creator != null){
				fuhcvo.setCreatorName(creator.getRealname());
			}
			voList.add(fuhcvo);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
	}
	
	/**
	 * ????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/checkStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result checkStatusList() {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("transType", FrontUserHistoryCheckTransType.RECHARGE);
		
		List<StatusCountVO> list = frontUserHistoryCheckService.getStatusList(searchMap);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		Admin admin = this.getCurrentOperator();
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("transType", FrontUserHistoryCheckTransType.RECHARGE);
		searchMap.put("creator", admin.getUuid());
		
		List<StatusCountVO> list = frontUserHistoryCheckService.getStatusList(searchMap);
		return ResultManager.createDataResult(list,list.size());
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "frontUser", message="??????", type = DataType.STRING, required = true)
	@ActionParam(key = "fee", message="??????", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "poundage", message="?????????", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "capitalAccount", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "transData", message="????????????", type = DataType.STRING)
	@ActionParam(key = "proof", message="??????", type = DataType.STRING)
	@ResponseBody
	public Result add(String frontUser, BigDecimal fee, BigDecimal poundage, String capitalAccount, String transData, String proof){
		Admin admin = this.getCurrentOperator();
		
		FrontUser fu = this.frontUserService.get(frontUser);	
		if(fu == null){
			return ResultManager.createFailResult("??????????????????");
		}
		
		CapitalAccount ca = this.capitalAccountService.get(capitalAccount);
		if(ca == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		FrontUserAccount fua = this.frontUserAccountService.getByFrontUser(frontUser);
		if(fua == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		FrontUserHistory fuh = new FrontUserHistory();
		fuh.setFrontUser(frontUser);
		fuh.setFrontUserAccount(fua.getUuid());
		fuh.setCapitalAccount(capitalAccount);
		fuh.setPay(BigDecimal.ZERO);
		fuh.setIncome(fee);
		fuh.setPoundage(poundage);
		fuh.setTransData(transData);
		fuh.setType(FrontUserHistoryType.USER_RECHARGE);
		
		FrontUserHistoryCheck fuhc = new FrontUserHistoryCheck();
		fuhc.setUuid(UUID.randomUUID().toString());
		fuhc.setType(FrontUserHistoryCheckType.ADD);
		fuhc.setTransType(FrontUserHistoryCheckTransType.RECHARGE);
		fuhc.setValue(JSONUtils.obj2json(fuh));
		fuhc.setProof(proof);
		fuhc.setStatus(FrontUserHistoryCheckStatus.NORMAL);
		fuhc.setCreator(admin.getUuid());
		fuhc.setSubmittime(new Timestamp(System.currentTimeMillis()));
		fuhc.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.frontUserHistoryCheckService.insert(fuhc);
		
		return ResultManager.createSuccessResult("???????????????");
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "fee", message="??????", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "poundage", message="?????????", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "capitalAccount", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "transData", message="????????????", type = DataType.STRING)
	@ActionParam(key = "proof", message="??????", type = DataType.STRING)
	@ResponseBody
	public Result edit(String uuid, BigDecimal fee, BigDecimal poundage, String capitalAccount, String transData, String proof){
		Admin admin = this.getCurrentOperator();
		
		FrontUserHistoryCheck fuhc = this.frontUserHistoryCheckService.get(uuid);
		if(fuhc == null || FrontUserHistoryCheckStatus.DELETE.equals(fuhc.getStatus())){
			return ResultManager.createFailResult("???????????????????????????");
		}
		if(FrontUserHistoryCheckStatus.CHECKED.equals(fuhc.getStatus())){
			return ResultManager.createFailResult("??????????????????????????????????????????");
		}
		if(!FrontUserHistoryCheckType.ADD.equals(fuhc.getType())){
			return ResultManager.createFailResult("????????????????????????");
		}
		if(!FrontUserHistoryCheckTransType.RECHARGE.equals(fuhc.getTransType())){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		FrontUserHistory fuhOld = JSONUtils.json2obj(fuhc.getValue(), FrontUserHistory.class);
		FrontUser fu = this.frontUserService.get(fuhOld.getFrontUser());	
		if(fu == null){
			return ResultManager.createFailResult("??????????????????");
		}
		
		CapitalAccount ca = this.capitalAccountService.get(capitalAccount);
		if(ca == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		FrontUserHistory fuh = new FrontUserHistory();
		fuh.setFrontUser(fu.getUuid());
		fuh.setCapitalAccount(capitalAccount);
		fuh.setIncome(fee);
		fuh.setPay(BigDecimal.ZERO);
		fuh.setPoundage(poundage);
		fuh.setTransData(transData);
		fuh.setType(FrontUserHistoryType.USER_RECHARGE);
		FrontUserAccount fua = this.frontUserAccountService.getByFrontUser(fu.getUuid());
		fuh.setFrontUserAccount(fua.getUuid());
		
		fuhc.setValue(JSONUtils.obj2json(fuh));
		fuhc.setProof(proof);
		fuhc.setStatus(FrontUserHistoryCheckStatus.NORMAL);
		fuhc.setCreator(admin.getUuid());
		fuhc.setSubmittime(new Timestamp(System.currentTimeMillis()));
		this.frontUserHistoryCheckService.update(fuhc);
		
		return ResultManager.createSuccessResult("???????????????");
	}
	
	@RequestMapping(value="/confirm",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "proof", message="??????", type = DataType.STRING)
	@ResponseBody
	public Result confirm(String uuid, String proof){
		Admin admin = this.getCurrentOperator();
		
		FrontUserHistory fuh = this.frontUserHistoryService.get(uuid);
		if(fuh == null || FrontUserHistoryStatus.DELETE.equals(fuh.getStatus())){
			return ResultManager.createFailResult("????????????????????????");
		}
		if(!FrontUserHistoryType.USER_RECHARGE.equals(fuh.getType())){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		if(!FrontUserHistoryStatus.NORMAL.equals(fuh.getStatus()) && !FrontUserHistoryStatus.FAILED.equals(fuh.getStatus())){
			return ResultManager.createFailResult("????????????????????????????????????");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("frontUserHistory", uuid);
		Integer count = this.frontUserHistoryCheckService.getCountByParams(params);
		if(count > 0){
			return ResultManager.createFailResult("???????????????????????????????????????????????????");
		}
		
		FrontUserHistoryCheck fuhc = new FrontUserHistoryCheck();
		fuhc.setUuid(UUID.randomUUID().toString());
		fuhc.setFrontUserHistory(fuh.getUuid());
		fuhc.setType(FrontUserHistoryCheckType.CONFIRM);
		fuhc.setTransType(FrontUserHistoryCheckTransType.RECHARGE);
		fuhc.setValue(JSONUtils.obj2json(fuh));
		fuhc.setProof(proof);
		fuhc.setStatus(FrontUserHistoryCheckStatus.NORMAL);
		fuhc.setCreator(admin.getUuid());
		fuhc.setSubmittime(new Timestamp(System.currentTimeMillis()));
		fuhc.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.frontUserHistoryCheckService.insert(fuhc);
		
		return ResultManager.createSuccessResult("???????????????");
	}
	
	@RequestMapping(value="/resubmit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "proof", message="??????", type = DataType.STRING)
	@ResponseBody
	public Result resubmit(String uuid, String proof){
		Admin admin = this.getCurrentOperator();
		
		FrontUserHistoryCheck fuhc = this.frontUserHistoryCheckService.get(uuid);
		if(fuhc == null || FrontUserHistoryCheckStatus.DELETE.equals(fuhc.getStatus())){
			return ResultManager.createFailResult("???????????????????????????");
		}
		if(!FrontUserHistoryCheckType.CONFIRM.equals(fuhc.getType())){
			return ResultManager.createFailResult("????????????????????????????????????");
		}
		if(!FrontUserHistoryCheckStatus.NOPASS.equals(fuhc.getStatus())){
			return ResultManager.createFailResult("????????????????????????????????????");
		}
		if(!FrontUserHistoryCheckTransType.RECHARGE.equals(fuhc.getTransType())){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		fuhc.setProof(proof);
		fuhc.setStatus(FrontUserHistoryCheckStatus.NORMAL);
		fuhc.setCreator(admin.getUuid());
		fuhc.setSubmittime(new Timestamp(System.currentTimeMillis()));
		this.frontUserHistoryCheckService.update(fuhc);
		
		return ResultManager.createSuccessResult("???????????????");
	}
	
	@RequestMapping(value="/close",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result close(String uuid){
		Admin admin = this.getCurrentOperator();
		
		FrontUserHistory fuh = this.frontUserHistoryService.get(uuid);
		if(fuh == null || FrontUserHistoryCheckStatus.DELETE.equals(fuh.getStatus())){
			return ResultManager.createFailResult("????????????????????????");
		}
		if(!FrontUserHistoryType.USER_RECHARGE.equals(fuh.getType())){
			return ResultManager.createFailResult("???????????????????????????");
		}
		if(!FrontUserHistoryStatus.NORMAL.equals(fuh.getStatus()) && !FrontUserHistoryStatus.FAILED.equals(fuh.getStatus())){
			return ResultManager.createFailResult("???????????????????????????");
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserHistory", fuh.getUuid());
		searchMap.put("status", FrontUserHistoryCheckStatus.NORMAL);
		int count = this.frontUserHistoryCheckService.getCountByParams(searchMap);
		if(count > 0){
			return ResultManager.createFailResult("???????????????????????????????????????");
		}
		
		fuh.setStatus(FrontUserHistoryStatus.CLOSE);
		fuh.setOperator(admin.getUuid());
		fuh.setOperattime(new Timestamp(System.currentTimeMillis()));
		this.frontUserHistoryService.update(fuh);
		
		return ResultManager.createSuccessResult("???????????????");
	}
	
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="??????", type = DataType.STRING, required = true)
	@ActionParam(key = "reason", message="??????", type = DataType.STRING)
	@ResponseBody
	public Result check(String uuid, String status, String reason){
		Admin admin = this.getCurrentOperator();
		
		FrontUserHistoryCheck fuhc = frontUserHistoryCheckService.get(uuid);
		if(fuhc == null){
			return ResultManager.createFailResult("???????????????????????????");
		}
		if(!FrontUserHistoryCheckStatus.NORMAL.equals(fuhc.getStatus())){
			return ResultManager.createFailResult("???????????????????????????");
		}
		if(!FrontUserHistoryCheckTransType.RECHARGE.equals(fuhc.getTransType())){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		if(FrontUserHistoryCheckStatus.NOPASS.equals(status)){
			fuhc.setReason(Utlity.checkStringNull(reason) ? "??????????????????" : reason);
			fuhc.setStatus(status);
			fuhc.setChecker(admin.getUuid());
			fuhc.setChecktime(new Timestamp(System.currentTimeMillis()));
			this.frontUserHistoryCheckService.update(fuhc);
			return ResultManager.createSuccessResult("???????????????");
		}else if(FrontUserHistoryCheckStatus.CHECKED.equals(status)){
			if(FrontUserHistoryCheckType.CONFIRM.equals(fuhc.getType())|| FrontUserHistoryCheckType.ADD.equals(fuhc.getType())){
				fuhc.setReason(Utlity.checkStringNull(reason) ? "???????????????" : reason);
				fuhc.setStatus(status);
				fuhc.setChecker(admin.getUuid());
				fuhc.setChecktime(new Timestamp(System.currentTimeMillis()));
				this.frontUserHistoryCheckService.checkRecharge(fuhc);
				return ResultManager.createSuccessResult("???????????????");
			}else{
				return ResultManager.createFailResult("????????????????????????");
			}
		}else{
			return ResultManager.createFailResult("?????????????????????");
		}
	}
}
