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
import cn.zeppin.product.score.entity.CapitalAccount.CapitalAccountStatus;
import cn.zeppin.product.score.entity.CapitalPlatform;
import cn.zeppin.product.score.entity.CapitalPlatform.CapitalPlatformType;
import cn.zeppin.product.score.service.AdminService;
import cn.zeppin.product.score.service.CapitalAccountService;
import cn.zeppin.product.score.service.CapitalPlatformService;
import cn.zeppin.product.score.util.JSONUtils;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.back.CapitalAccountVO;

@Controller
@RequestMapping(value = "/back/capitalAccount")
public class CapitalAccountController extends BaseController{
	
	@Autowired
    private CapitalAccountService capitalAccountService;
	
	@Autowired
    private CapitalPlatformService capitalPlatformService;
	
	@Autowired
    private AdminService adminService;
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		CapitalAccount ca = capitalAccountService.get(uuid);
		if(ca == null || CapitalAccountStatus.DELETE.equals(ca.getStatus())){
			return ResultManager.createFailResult("???????????????");
		}
		
		CapitalAccountVO cavo = new CapitalAccountVO(ca);
		CapitalPlatform cp = this.capitalPlatformService.get(ca.getCapitalPlatform());
		if(cp != null){
			cavo.setCapitalPlatformName(cp.getName());
			cavo.setCapitalPlatformType(cp.getType());
		}
		Admin creator = this.adminService.get(ca.getCreator());
		if(creator != null){
			cavo.setCreatorName(creator.getRealname());
		}
		return ResultManager.createDataResult(cavo);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "capitalPlatform", message="??????", type = DataType.STRING)
	@ActionParam(key = "name", message="??????", type = DataType.STRING)
	@ActionParam(key = "accountNum", message="??????", type = DataType.STRING)
	@ActionParam(key = "transType", message="????????????", type = DataType.STRING)
	@ActionParam(key = "auto", message="????????????", type = DataType.BOOLEAN)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.STRING)
	@ActionParam(key = "sort", message="??????", type = DataType.STRING)
	@ResponseBody
	public Result list(String capitalPlatform, String name, String accountNum, String transType, Boolean auto, String status, Integer pageNum, Integer pageSize, String sort){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("capitalPlatform", capitalPlatform);
		params.put("name", name);
		params.put("accountNum", accountNum);
		params.put("transType", transType);
		params.put("auto", auto);
		params.put("status", status);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("sort", sort);
		
		Integer totalCount = capitalAccountService.getCountByParams(params);
		List<CapitalAccount> caList = capitalAccountService.getListByParams(params);
		
		List<CapitalAccountVO> voList = new ArrayList<CapitalAccountVO>();
		for(CapitalAccount ca : caList){
			CapitalAccountVO cavo = new CapitalAccountVO(ca);
			CapitalPlatform cp = this.capitalPlatformService.get(ca.getCapitalPlatform());
			if(cp != null){
				cavo.setCapitalPlatformName(cp.getName());
				cavo.setCapitalPlatformType(cp.getType());
			}
			Admin creator = this.adminService.get(ca.getCreator());
			if(creator != null){
				cavo.setCreatorName(creator.getRealname());
			}
			voList.add(cavo);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "capitalPlatform", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="??????", type = DataType.STRING, required = true)
	@ActionParam(key = "accountNum", message="?????????", type = DataType.STRING, required = true)
	@ActionParam(key = "min", message="????????????", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "max", message="????????????", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "dailyMax", message="????????????", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "remark", message="??????", type = DataType.STRING)
	@ActionParam(key = "status", message="??????", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "privateKey", message="??????", type = DataType.STRING)
	@ActionParam(key = "accountName", message="?????????", type = DataType.STRING)
	@ActionParam(key = "bankName", message="????????????", type = DataType.STRING)
	@ActionParam(key = "branchBank", message="?????????", type = DataType.STRING)
	@ResponseBody
    public Result add(CapitalAccount capitalAccount, String privateKey, String accountName, String bankName, String branchBank){
		Admin admin = this.getCurrentOperator();
		
		CapitalPlatform cp = this.capitalPlatformService.get(capitalAccount.getCapitalPlatform());
		if(cp == null){
			return ResultManager.createFailResult("?????????????????????");
		}
		
		//??????:appid(accountnum),mch_id  ???????????????:appid(accountnum),privateKey ????????????:accountnum,accountname,bankname ????????????:accountnum,accountname,bankname,brankbank
		Map<String, Object> data = new HashMap<String, Object>();
		if(CapitalPlatformType.COMPANY_BANKCARD.equals(cp.getType())){
			if(Utlity.checkStringNull(accountName)){
				return ResultManager.createFailResult("?????????????????????");
			}
			if(Utlity.checkStringNull(bankName)){
				return ResultManager.createFailResult("????????????????????????");
			}
			if(Utlity.checkStringNull(branchBank)){
				return ResultManager.createFailResult("?????????????????????");
			}
			data.put("accountName", accountName);
			data.put("bankName", bankName);
			data.put("branchBank", branchBank);
		}else if(CapitalPlatformType.PERSONAL_BANKCARD.equals(cp.getType())){
			if(Utlity.checkStringNull(accountName)){
				return ResultManager.createFailResult("?????????????????????");
			}
			if(Utlity.checkStringNull(bankName)){
				return ResultManager.createFailResult("????????????????????????");
			}
			data.put("accountName", accountName);
			data.put("bankName", bankName);
		}else if(CapitalPlatformType.COMPANY_ALIPAY.equals(cp.getType())){
			if(Utlity.checkStringNull(privateKey)){
				return ResultManager.createFailResult("??????????????????");
			}
			data.put("privateKey", privateKey);
		}
		capitalAccount.setData(JSONUtils.obj2json(data));
		
		capitalAccount.setUuid(UUID.randomUUID().toString());
		capitalAccount.setPoundageRate(BigDecimal.ZERO);
		capitalAccount.setType(cp.getType());
		capitalAccount.setTransType(cp.getTransType());
		capitalAccount.setDailySum(BigDecimal.ZERO);
		capitalAccount.setBalance(BigDecimal.ZERO);
		capitalAccount.setCreator(admin.getUuid());
		capitalAccount.setCreatetime(new Timestamp(System.currentTimeMillis()));
		capitalAccountService.insert(capitalAccount);
		return ResultManager.createSuccessResult("???????????????");
    }
    
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="??????", type = DataType.STRING, required = true)
	@ActionParam(key = "accountNum", message="??????", type = DataType.STRING, required = true)
	@ActionParam(key = "min", message="????????????", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "max", message="????????????", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "dailyMax", message="????????????", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "remark", message="??????", type = DataType.STRING)
	@ActionParam(key = "status", message="??????", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "privateKey", message="??????", type = DataType.STRING)
	@ActionParam(key = "accountName", message="?????????", type = DataType.STRING)
	@ActionParam(key = "bankName", message="????????????", type = DataType.STRING)
	@ActionParam(key = "branchBank", message="?????????", type = DataType.STRING)
	@ResponseBody
	public Result edit(CapitalAccount capitalAccount, String privateKey, String accountName, String bankName, String branchBank){
		Admin admin = this.getCurrentOperator();
		
		CapitalAccount ca = capitalAccountService.get(capitalAccount.getUuid());
		if(ca == null || CapitalAccountStatus.DELETE.equals(ca.getStatus())){
			return ResultManager.createFailResult("???????????????");
		}
		
		CapitalPlatform cp = this.capitalPlatformService.get(ca.getCapitalPlatform());
		Map<String, Object> data = new HashMap<String, Object>();
		if(CapitalPlatformType.COMPANY_BANKCARD.equals(cp.getType())){
			if(Utlity.checkStringNull(accountName)){
				return ResultManager.createFailResult("?????????????????????");
			}
			if(Utlity.checkStringNull(bankName)){
				return ResultManager.createFailResult("????????????????????????");
			}
			if(Utlity.checkStringNull(branchBank)){
				return ResultManager.createFailResult("?????????????????????");
			}
			data.put("accountName", accountName);
			data.put("bankName", bankName);
			data.put("branchBank", branchBank);
		}else if(CapitalPlatformType.PERSONAL_BANKCARD.equals(cp.getType())){
			if(Utlity.checkStringNull(accountName)){
				return ResultManager.createFailResult("?????????????????????");
			}
			if(Utlity.checkStringNull(bankName)){
				return ResultManager.createFailResult("????????????????????????");
			}
			data.put("accountName", accountName);
			data.put("bankName", bankName);
		}else if(CapitalPlatformType.COMPANY_ALIPAY.equals(cp.getType())){
			if(Utlity.checkStringNull(privateKey)){
				return ResultManager.createFailResult("??????????????????");
			}
			data.put("privateKey", privateKey);
		}
		ca.setData(JSONUtils.obj2json(data));
		
		ca.setName(capitalAccount.getName());
		ca.setAccountNum(capitalAccount.getAccountNum());
		ca.setMin(capitalAccount.getMin());
		ca.setMax(capitalAccount.getMax());
		ca.setDailyMax(capitalAccount.getDailyMax());
		ca.setRemark(capitalAccount.getRemark());
		ca.setStatus(capitalAccount.getStatus());
		ca.setSort(capitalAccount.getSort());
		ca.setCreator(admin.getUuid());
		ca.setCreatetime(new Timestamp(System.currentTimeMillis()));
		capitalAccountService.update(ca);
		return ResultManager.createSuccessResult("???????????????");
	}
	
	@RequestMapping(value="/changeStatus",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="??????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status){
		CapitalAccount ca = capitalAccountService.get(uuid);
		if(ca == null || CapitalAccountStatus.DELETE.equals(ca.getStatus())){
			return ResultManager.createFailResult("???????????????");
		}
		
		ca.setStatus(status);
		
		capitalAccountService.update(ca);
		return ResultManager.createSuccessResult("???????????????");
	}
}
