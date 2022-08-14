/**
 * 
 */
package cn.zeppin.product.score.controller.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.FrontUserAccount;
import cn.zeppin.product.score.entity.FrontUserHistory;
import cn.zeppin.product.score.service.FrontUserAccountService;
import cn.zeppin.product.score.service.FrontUserHistoryService;
import cn.zeppin.product.score.vo.front.FrontUserHistoryVO;

/**
 * 用户信息
 */

@Controller
@RequestMapping(value = "/front/userAccount")
public class FrontUserAccountController extends BaseController{
	
	@Autowired
	private FrontUserAccountService frontUserAccountService;
	
	@Autowired
	private FrontUserHistoryService frontUserHistoryService;
	
	/**
	 * 获取用户账户信息
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Result get(HttpServletRequest request) {
		FrontUser fu = getFrontUser(request);
		FrontUserAccount fua = this.frontUserAccountService.getByFrontUser(fu.getUuid());
		return ResultManager.createDataResult(fua);
	}
	
	/**
	 * 用户流水儿信息
	 * @return
	 */
	@RequestMapping(value = "/historyList", method = RequestMethod.GET)
	@ActionParam(key = "type", type = DataType.STRING, message="类型")
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ResponseBody
	public Result historyList(HttpServletRequest request, Integer pageNum, Integer pageSize, String type) {
		FrontUser fu = getFrontUser(request);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("frontUser", fu.getUuid());
		params.put("type", type);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			params.put("offSet", (pageNum-1)*pageSize);
			params.put("pageSize", pageSize);
		}
		params.put("sort", "createtime desc");
		
		List<FrontUserHistory> list = this.frontUserHistoryService.getListByParams(params);
		List<FrontUserHistoryVO> voList = new ArrayList<FrontUserHistoryVO>();
		for(FrontUserHistory fuh : list){
			voList.add(new FrontUserHistoryVO(fuh));
		}
		
		return ResultManager.createDataResult(voList);
	}
}
