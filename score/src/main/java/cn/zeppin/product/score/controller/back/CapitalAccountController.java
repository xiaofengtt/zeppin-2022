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
			return ResultManager.createFailResult("账号不存在");
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
	@ActionParam(key = "capitalPlatform", message="渠道", type = DataType.STRING)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "accountNum", message="账户", type = DataType.STRING)
	@ActionParam(key = "transType", message="交易类型", type = DataType.STRING)
	@ActionParam(key = "auto", message="自动交易", type = DataType.BOOLEAN)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
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
	@ActionParam(key = "capitalPlatform", message="所属渠道", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "accountNum", message="账户号", type = DataType.STRING, required = true)
	@ActionParam(key = "min", message="最低金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "max", message="最高金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "dailyMax", message="每日限额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message="排序", type = DataType.NUMBER, required = true)
	@ActionParam(key = "privateKey", message="私钥", type = DataType.STRING)
	@ActionParam(key = "accountName", message="账户名", type = DataType.STRING)
	@ActionParam(key = "bankName", message="所属银行", type = DataType.STRING)
	@ActionParam(key = "branchBank", message="开户行", type = DataType.STRING)
	@ResponseBody
    public Result add(CapitalAccount capitalAccount, String privateKey, String accountName, String bankName, String branchBank){
		Admin admin = this.getCurrentOperator();
		
		CapitalPlatform cp = this.capitalPlatformService.get(capitalAccount.getCapitalPlatform());
		if(cp == null){
			return ResultManager.createFailResult("所属渠道不存在");
		}
		
		//微信:appid(accountnum),mch_id  支付宝对公:appid(accountnum),privateKey 银行对私:accountnum,accountname,bankname 银行对公:accountnum,accountname,bankname,brankbank
		Map<String, Object> data = new HashMap<String, Object>();
		if(CapitalPlatformType.COMPANY_BANKCARD.equals(cp.getType())){
			if(Utlity.checkStringNull(accountName)){
				return ResultManager.createFailResult("账户名不能为空");
			}
			if(Utlity.checkStringNull(bankName)){
				return ResultManager.createFailResult("所属银行不能为空");
			}
			if(Utlity.checkStringNull(branchBank)){
				return ResultManager.createFailResult("开户行不能为空");
			}
			data.put("accountName", accountName);
			data.put("bankName", bankName);
			data.put("branchBank", branchBank);
		}else if(CapitalPlatformType.PERSONAL_BANKCARD.equals(cp.getType())){
			if(Utlity.checkStringNull(accountName)){
				return ResultManager.createFailResult("账户名不能为空");
			}
			if(Utlity.checkStringNull(bankName)){
				return ResultManager.createFailResult("所属银行不能为空");
			}
			data.put("accountName", accountName);
			data.put("bankName", bankName);
		}else if(CapitalPlatformType.COMPANY_ALIPAY.equals(cp.getType())){
			if(Utlity.checkStringNull(privateKey)){
				return ResultManager.createFailResult("私钥不能为空");
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
		return ResultManager.createSuccessResult("添加成功！");
    }
    
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "accountNum", message="账号", type = DataType.STRING, required = true)
	@ActionParam(key = "min", message="最低金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "max", message="最高金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "dailyMax", message="每日限额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message="排序", type = DataType.NUMBER, required = true)
	@ActionParam(key = "privateKey", message="私钥", type = DataType.STRING)
	@ActionParam(key = "accountName", message="账户名", type = DataType.STRING)
	@ActionParam(key = "bankName", message="所属银行", type = DataType.STRING)
	@ActionParam(key = "branchBank", message="开户行", type = DataType.STRING)
	@ResponseBody
	public Result edit(CapitalAccount capitalAccount, String privateKey, String accountName, String bankName, String branchBank){
		Admin admin = this.getCurrentOperator();
		
		CapitalAccount ca = capitalAccountService.get(capitalAccount.getUuid());
		if(ca == null || CapitalAccountStatus.DELETE.equals(ca.getStatus())){
			return ResultManager.createFailResult("账号不存在");
		}
		
		CapitalPlatform cp = this.capitalPlatformService.get(ca.getCapitalPlatform());
		Map<String, Object> data = new HashMap<String, Object>();
		if(CapitalPlatformType.COMPANY_BANKCARD.equals(cp.getType())){
			if(Utlity.checkStringNull(accountName)){
				return ResultManager.createFailResult("账户名不能为空");
			}
			if(Utlity.checkStringNull(bankName)){
				return ResultManager.createFailResult("所属银行不能为空");
			}
			if(Utlity.checkStringNull(branchBank)){
				return ResultManager.createFailResult("开户行不能为空");
			}
			data.put("accountName", accountName);
			data.put("bankName", bankName);
			data.put("branchBank", branchBank);
		}else if(CapitalPlatformType.PERSONAL_BANKCARD.equals(cp.getType())){
			if(Utlity.checkStringNull(accountName)){
				return ResultManager.createFailResult("账户名不能为空");
			}
			if(Utlity.checkStringNull(bankName)){
				return ResultManager.createFailResult("所属银行不能为空");
			}
			data.put("accountName", accountName);
			data.put("bankName", bankName);
		}else if(CapitalPlatformType.COMPANY_ALIPAY.equals(cp.getType())){
			if(Utlity.checkStringNull(privateKey)){
				return ResultManager.createFailResult("私钥不能为空");
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
		return ResultManager.createSuccessResult("修改成功！");
	}
	
	@RequestMapping(value="/changeStatus",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status){
		CapitalAccount ca = capitalAccountService.get(uuid);
		if(ca == null || CapitalAccountStatus.DELETE.equals(ca.getStatus())){
			return ResultManager.createFailResult("账号不存在");
		}
		
		ca.setStatus(status);
		
		capitalAccountService.update(ca);
		return ResultManager.createSuccessResult("修改成功！");
	}
}
