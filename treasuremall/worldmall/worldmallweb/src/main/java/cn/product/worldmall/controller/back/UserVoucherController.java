/**
 * 
 */
package cn.product.worldmall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;


/**
 * 用户级别信息管理
 */

@Controller
@RequestMapping(value = "/back/userVoucher")
public class UserVoucherController extends BaseController {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8636831197897736844L;

	/**
	 * 根据条件查询用户级别信息 
	 * @param title
	 * @param status
	 * @param type
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "frontUser", message="用户", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "userType", message="用户类型", type = DataType.STRING)
	@ActionParam(key = "showid", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "mobile", message="手机号", type = DataType.STRING)
	@ActionParam(key = "createtime", message="时间", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public Result list(String frontUser, String status, Integer pageNum, Integer pageSize, String sort
			, String userType, String showid, String mobile, String createtime) {
		
		InputParams params = new InputParams("userVoucherService", "list");
		params.addParams("frontUser", null, frontUser);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("userType", null, userType);
		params.addParams("showid", null, showid);
		params.addParams("mobile", null, mobile);
		params.addParams("createtime", null, createtime);
		return this.execute(params);
	}
	
	/**
	 * 获取一条用户级别信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid) {	
		InputParams params = new InputParams("userVoucherService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 添加一条用户级别信息
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "frontUser", message="用户UUID", type = DataType.STRING, required = true)
	@ActionParam(key = "vouchers", message="金券ID数组", type = DataType.STRING_ARRAY, required = true)
	@ResponseBody
	public Result add(String frontUser, String[] vouchers) {
		
		InputParams params = new InputParams("userVoucherService", "add");
		params.addParams("frontUser", null, frontUser);
		params.addParams("vouchers", null, vouchers);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
}
