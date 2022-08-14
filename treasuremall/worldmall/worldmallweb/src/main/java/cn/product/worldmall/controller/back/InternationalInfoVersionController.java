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
 * 国家版本控制管理
 */

@Controller
@RequestMapping(value = "/back/control")
public class InternationalInfoVersionController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5100593551597189172L;

	/**
	 * 列表
	 * @param internationalInfo
	 * @param version
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "internationalInfo", message="国家UUID", type = DataType.STRING)
	@ActionParam(key = "version", message="渠道版本UUID", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String internationalInfo, String version, String status, Integer pageNum, Integer pageSize, String sorts) {
		
		InputParams params = new InputParams("internationalInfoVersionService", "list");
		params.addParams("internationalInfo", null, internationalInfo);
		params.addParams("version", null, version);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sorts);
		return this.execute(params);
	}
	
	/**
	 * 获取
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		
		InputParams params = new InputParams("internationalInfoVersionService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 添加
	 * @param internationalInfo
	 * @param version
	 * @param flagWithdraw
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "internationalInfo", message="国家UUID", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "version", message="渠道版本UUID", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "flagWithdraw", message="提现开关状态", type = DataType.BOOLEAN, required = true)
	@ResponseBody
	public Result add(String internationalInfo, String version, Boolean flagWithdraw) {
		
		InputParams params = new InputParams("internationalInfoVersionService", "add");
		params.addParams("internationalInfo", null, internationalInfo);
		params.addParams("version", null, version);
		params.addParams("flagWithdraw", null, flagWithdraw);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
		
	}
	
	/**
	 * 编辑
	 * @param uuid
	 * @param internationalInfo
	 * @param version
	 * @param flagWithdraw
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "internationalInfo", message="国家UUID", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "version", message="渠道版本UUID", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "flagWithdraw", message="提现开关状态", type = DataType.BOOLEAN, required = true)
	@ResponseBody
	public Result edit(String uuid, String internationalInfo, String version, Boolean flagWithdraw) {
		
		InputParams params = new InputParams("internationalInfoVersionService", "edit");
		params.addParams("uuid", null, uuid);
		params.addParams("internationalInfo", null, internationalInfo);
		params.addParams("version", null, version);
		params.addParams("flagWithdraw", null, flagWithdraw);
		return this.execute(params);
	}
	
	/**
	 * 批量更新状态
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/changeStatus",method=RequestMethod.POST)
	@ActionParam(key = "uuids", message="uuid", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String[] uuids, String status){
		
		InputParams params = new InputParams("internationalInfoVersionService", "changeStatus");
		params.addParams("uuids", null, uuids);
		params.addParams("status", null, status);
		return this.execute(params);
	}
	
}
