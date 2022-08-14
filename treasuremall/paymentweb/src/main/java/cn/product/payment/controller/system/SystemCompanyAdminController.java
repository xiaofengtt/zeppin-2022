/**
 * 
 */
package cn.product.payment.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.BaseController;
import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.controller.base.Result;
import cn.product.payment.entity.CompanyAdmin;


/**
 * 商户管理员
 */

@Controller
@RequestMapping(value = "/system/companyAdmin")
public class SystemCompanyAdminController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1924997681733421750L;

	/**
	 * 根据条件查询
	 * @param company
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "company", message="商户", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String company, String status, Integer pageNum, Integer pageSize, String sort) {
		InputParams params = new InputParams("systemCompanyAdminService", "list");
		params.addParams("company", null, company);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 获取
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		InputParams params = new InputParams("systemCompanyAdminService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 创建
	 * @param company
	 * @param username
	 * @param password
	 * @param mobile
	 * @param email
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "company", message="商户", type = DataType.STRING, required = true)
	@ActionParam(key = "username", message="用户名", type = DataType.STRING, required = true, maxLength = 100)
	@ActionParam(key = "password", message="密码", type = DataType.STRING, required = true)
	@ActionParam(key = "mobile", message="手机号", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "email", message="邮箱", type = DataType.NUMBER, maxLength = 100)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(CompanyAdmin companyAdmin) {
		InputParams params = new InputParams("systemCompanyAdminService", "add");
		params.addParams("companyAdmin", null, companyAdmin);
		return this.execute(params);
	}
	
	/**
	 * 修改
	 * @param uuid
	 * @param company
	 * @param username
	 * @param mobile
	 * @param email
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "company", message="商户", type = DataType.STRING, required = true)
	@ActionParam(key = "username", message="用户名", type = DataType.STRING, required = true, maxLength = 100)
	@ActionParam(key = "mobile", message="手机号", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "email", message="邮箱", type = DataType.NUMBER, maxLength = 100)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(CompanyAdmin companyAdmin) {
		InputParams params = new InputParams("systemCompanyAdminService", "edit");
		params.addParams("companyAdmin", null, companyAdmin);
		return this.execute(params);
	}
	
	/**
	 * 变更状态
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status) {
		InputParams params = new InputParams("systemCompanyAdminService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		return this.execute(params);
	}
}
