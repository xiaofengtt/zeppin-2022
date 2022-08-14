/**
 * 
 */
package cn.zeppin.product.score.controller.front;

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
import cn.zeppin.product.score.entity.CapitalAccount;
import cn.zeppin.product.score.entity.CapitalAccount.CapitalAccountStatus;
import cn.zeppin.product.score.entity.CapitalPlatform;
import cn.zeppin.product.score.entity.CapitalPlatform.CapitalPlatformStatus;
import cn.zeppin.product.score.entity.CapitalPlatform.CapitalPlatformTransType;
import cn.zeppin.product.score.service.CapitalAccountService;
import cn.zeppin.product.score.service.CapitalPlatformService;
import cn.zeppin.product.score.vo.front.CapitalAccountVO;

/**
 * 交易渠道
 */

@Controller
@RequestMapping(value = "/front/capital")
public class FrontCapitalAccountController extends BaseController{

	@Autowired
	private CapitalPlatformService capitalPlatformService;
	
	@Autowired
	private CapitalAccountService capitalAccountService;
	
	/**
	 * 查询充值渠道
	 * @return
	 */
	@RequestMapping(value = "/platformList", method = RequestMethod.GET)
	@ResponseBody
	public Result platformList() {
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("status", CapitalPlatformStatus.NORMAL);
		searchMap.put("transType", CapitalPlatformTransType.RECHARGE);
		List<CapitalPlatform> list = capitalPlatformService.getListByParams(searchMap);
		return ResultManager.createDataResult(list);
	}
	
	/**
	 * 查询可选充值账号
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/accountList", method = RequestMethod.GET)
	@ActionParam(key = "capitalPlatform", message = "充值渠道", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String capitalPlatform) {
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("capitalPlatform", capitalPlatform);
		searchMap.put("status", CapitalAccountStatus.NORMAL);
		
		List<CapitalAccount> list = capitalAccountService.getListByParams(searchMap);
		List<CapitalAccountVO> voList = new ArrayList<CapitalAccountVO>();
		for(CapitalAccount ca : list){
			voList.add(new CapitalAccountVO(ca));
		}
		
		return ResultManager.createDataResult(list);
	}
}
