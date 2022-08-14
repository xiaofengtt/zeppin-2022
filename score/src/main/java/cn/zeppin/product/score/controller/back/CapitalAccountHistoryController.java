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
import cn.zeppin.product.score.entity.CapitalAccount;
import cn.zeppin.product.score.entity.CapitalAccountHistory;
import cn.zeppin.product.score.entity.CapitalAccountHistory.CapitalAccountHistoryStatus;
import cn.zeppin.product.score.entity.CapitalPlatform;
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.service.AdminService;
import cn.zeppin.product.score.service.CapitalAccountHistoryService;
import cn.zeppin.product.score.service.CapitalAccountService;
import cn.zeppin.product.score.service.CapitalPlatformService;
import cn.zeppin.product.score.service.FrontUserService;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.back.CapitalAccountHistoryVO;
import cn.zeppin.product.score.vo.back.StatusCountVO;

@Controller
@RequestMapping(value = "/back/capitalAccountHistory")
public class CapitalAccountHistoryController extends BaseController{
	
	@Autowired
    private CapitalAccountHistoryService capitalAccountHistoryService;
	
	@Autowired
    private CapitalAccountService capitalAccountService;
	
	@Autowired
    private CapitalPlatformService capitalPlatformService;
	
	@Autowired
    private FrontUserService frontUserService;
	
	@Autowired
    private AdminService adminService;
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		CapitalAccountHistory cah = capitalAccountHistoryService.get(uuid);
		if(cah == null || CapitalAccountHistoryStatus.DELETE.equals(cah.getStatus())){
			return ResultManager.createFailResult("流水信息不存在");
		}
		
		CapitalAccountHistoryVO cahvo = new CapitalAccountHistoryVO(cah);
		CapitalAccount ca = this.capitalAccountService.get(cah.getCapitalAccount());
		if(ca != null){
			cahvo.setCapitalAccountName(ca.getName());
		}
		CapitalPlatform cp = this.capitalPlatformService.get(cah.getCapitalPlatform());
		if(cp != null){
			cahvo.setCapitalPlatformName(cp.getName());
		}
		if(!Utlity.checkStringNull(cah.getFrontUser())){
			FrontUser fu = this.frontUserService.get(cah.getFrontUser());
			if(fu != null){
				cahvo.setFrontUserName(fu.getRealname());
				cahvo.setFrontUserMobile(fu.getMobile());
			}
		}
		if(!Utlity.checkStringNull(cah.getOperator())){
			Admin operator = this.adminService.get(cah.getOperator());
			if(operator != null){
				cahvo.setOperatorName(operator.getRealname());
			}
		}
		
		return ResultManager.createDataResult(cahvo);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "capitalPlatform", message="渠道", type = DataType.STRING)
	@ActionParam(key = "capitalAccount", message="渠道账户", type = DataType.STRING)
	@ActionParam(key = "frontUser", message="用户", type = DataType.STRING)
	@ActionParam(key = "operator", message="经办人", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String capitalPlatform, String capitalAccount, String frontUser, String operator, String type, String status, Integer pageNum, Integer pageSize, String sort){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("capitalPlatform", capitalPlatform);
		params.put("capitalAccount", capitalAccount);
		params.put("frontUser", frontUser);
		params.put("operator", operator);
		if(!Utlity.checkStringNull(type) && !"all".equals(type)){
			params.put("type", type);
		}
		params.put("status", status);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("sort", sort);
		
		Integer totalCount = capitalAccountHistoryService.getCountByParams(params);
		List<CapitalAccountHistory> cahList = capitalAccountHistoryService.getListByParams(params);
		
		List<CapitalAccountHistoryVO> voList = new ArrayList<CapitalAccountHistoryVO>();
		for(CapitalAccountHistory cah : cahList){
			CapitalAccountHistoryVO cahvo = new CapitalAccountHistoryVO(cah);
			CapitalAccount ca = this.capitalAccountService.get(cah.getCapitalAccount());
			if(ca != null){
				cahvo.setCapitalAccountName(ca.getName());
			}
			CapitalPlatform cp = this.capitalPlatformService.get(cah.getCapitalPlatform());
			if(cp != null){
				cahvo.setCapitalPlatformName(cp.getName());
			}
			if(!Utlity.checkStringNull(cah.getFrontUser())){
				FrontUser fu = this.frontUserService.get(cah.getFrontUser());
				if(fu != null){
					cahvo.setFrontUserName(fu.getRealname());
					cahvo.setFrontUserMobile(fu.getMobile());
				}
			}
			if(!Utlity.checkStringNull(cah.getOperator())){
				Admin op = this.adminService.get(cah.getOperator());
				if(op != null){
					cahvo.setOperatorName(op.getRealname());
				}
			}
			voList.add(cahvo);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
	}
	
	/**
	 * 分类型列表
	 * @return
	 */
	@RequestMapping(value = "/typeList", method = RequestMethod.GET)
	@ResponseBody
	public Result typeList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", CapitalAccountHistoryStatus.SUCCESS);
		List<StatusCountVO> list = capitalAccountHistoryService.getTypeList(params);
		return ResultManager.createDataResult(list,list.size());
	}
}
