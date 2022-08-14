/**
 * 
 */
package cn.zeppin.product.itic.backadmin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.itic.backadmin.service.api.ITCheckInfoService;
import cn.zeppin.product.itic.backadmin.vo.TSsOperatorVO;
import cn.zeppin.product.itic.core.controller.base.ActionParam;
import cn.zeppin.product.itic.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.itic.core.controller.base.BaseController;
import cn.zeppin.product.itic.core.controller.base.Result;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.itic.core.entity.TCheckInfo;
import cn.zeppin.product.utility.Utlity;

/**
 * 批量审核
 */

@Controller
@RequestMapping(value = "/backadmin/check")
public class TCheckController extends BaseController {

	@Autowired
	private ITCheckInfoService tCheckInfoService;
	
	/**
	 * 批量审核
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ActionParam(key = "types", message = "数据类型",  required = true, type = DataType.STRING_ARRAY)
	@ActionParam(key = "status", message = "审核状态", required = true, type = DataType.STRING)
	@ResponseBody
	public Result check(String[] types, String status) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		if(!"checked".equals(status) && !"nopass".equals(status)){
			return ResultManager.createFailResult("审核状态错误");
		}
		
		List<String> typeList = new ArrayList<>();
		for(String type : types){
			typeList.add(type);
		}
		
		Map<String, String> inputParams = new HashMap<>();
		inputParams.put("datatypes", Utlity.getStringForSql(typeList));
		List<TCheckInfo> checkList = this.tCheckInfoService.getListForPage(inputParams, -1, -1, null);
		
		this.tCheckInfoService.check(checkList, status, operator);
		
		return ResultManager.createDataResult("审核成功！");
	}
}
