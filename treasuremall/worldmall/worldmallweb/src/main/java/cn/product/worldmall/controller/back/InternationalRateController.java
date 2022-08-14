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
 * 信息管理
 */

@Controller
@RequestMapping(value = "/back/exchangeRate")
public class InternationalRateController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1694320500973261973L;

	/**
	 * 根据条件查询信息 
	 * @param title
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "currencyCode", message="货币代码", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String currencyCode, String status, Integer pageNum, Integer pageSize, String sorts) {
		
		InputParams params = new InputParams("internationalRateService", "list");
		params.addParams("currencyCode", null, currencyCode);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sorts);
		return this.execute(params);
	}
	
	/**
	 * 获取一条信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {	
		InputParams params = new InputParams("internationalRateService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 根据条件查询信息 
	 * @param title
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/checkList", method = RequestMethod.GET)
	@ActionParam(key = "dailyDate", message="汇率日期", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result checkList(String dailyDate, String status, Integer pageNum, Integer pageSize, String sorts) {
		
		InputParams params = new InputParams("internationalRateService", "checkList");
		params.addParams("dailyDate", null, dailyDate);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sorts);
		return this.execute(params);
	}
	
	/**
	 * 获取一条信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "dataInfo", message="汇率信息", type = DataType.STRING, required = true)
	@ResponseBody
	public Result check(String uuid, String dataInfo) {	
		InputParams params = new InputParams("internationalRateService", "check");
		params.addParams("uuid", null, uuid);
		params.addParams("dataInfo", null, dataInfo);
		return this.execute(params);
	}
	
	/**
	 * 添加一条信息
	 * @param channelId
	 * @param title
	 * @param appkey
	 * @param channel
	 * @param isDefault
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "internationalInfo", message="国家信息", type = DataType.STRING)
	@ActionParam(key = "currencyCode", message="货币代码", type = DataType.STRING, required = true)
	@ActionParam(key = "currencyName", message="货币名称", type = DataType.STRING, required = true)
	@ActionParam(key = "baseCurrency", message="基准货币", type = DataType.STRING, required = true)
	@ActionParam(key = "realRate", message="当前汇率", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(String internationalInfo, String currencyCode, String currencyName, String baseCurrency, String realRate) {
		
		InputParams params = new InputParams("internationalRateService", "add");
		params.addParams("internationalInfo", null, internationalInfo);
		params.addParams("currencyCode", null, currencyCode);
		params.addParams("currencyName", null, currencyName);
		params.addParams("baseCurrency", null, baseCurrency);
		params.addParams("realRate", null, realRate);
		return this.execute(params);
	}
	
	/**
	 *  编辑一条信息
	 * @param uuid
	 * @param channelId
	 * @param title
	 * @param appkey
	 * @param channel
	 * @param isDefault
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "internationalInfo", message="国家信息", type = DataType.STRING)
	@ActionParam(key = "baseCurrency", message="基准货币", type = DataType.STRING, required = true)
	@ActionParam(key = "realRate", message="当前汇率", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(String uuid, String internationalInfo, String baseCurrency, String realRate) {
		InputParams params = new InputParams("internationalRateService", "edit");
		params.addParams("internationalInfo", null, internationalInfo);
		params.addParams("baseCurrency", null, baseCurrency);
		params.addParams("realRate", null, realRate);
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 删除一条信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		InputParams params = new InputParams("internationalRateService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 设置状态（取消发布）
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status) {
		InputParams params = new InputParams("internationalRateService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		return this.execute(params);
	}
}
