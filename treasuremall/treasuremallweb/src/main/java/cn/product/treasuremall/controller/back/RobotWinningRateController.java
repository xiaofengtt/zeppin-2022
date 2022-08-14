/**
 * 
 */
package cn.product.treasuremall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.controller.BaseController;


/**
 * 信息管理
 */

@Controller
@RequestMapping(value = "/back/winningRate")
public class RobotWinningRateController extends BaseController {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6400355867800178925L;
	

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
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String title, String status, Integer pageNum, Integer pageSize, String sorts) {
		
		InputParams params = new InputParams("robotWinningRateService", "list");
		params.addParams("title", null, title);
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
		InputParams params = new InputParams("robotWinningRateService", "get");
		params.addParams("uuid", null, uuid);
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
	@ActionParam(key = "goodsPriceMin", message="最小奖品价值", type = DataType.STRING, required = true)
	@ActionParam(key = "goodsPriceMax", message="最大奖品价值", type = DataType.STRING)
	@ActionParam(key = "winningRate", message="中奖率", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(String goodsPriceMin, String goodsPriceMax, String winningRate) {
		
		InputParams params = new InputParams("robotWinningRateService", "add");
		params.addParams("goodsPriceMin", null, goodsPriceMin);
		params.addParams("goodsPriceMax", null, goodsPriceMax);
		params.addParams("winningRate", null, winningRate);
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
	@ActionParam(key = "goodsPriceMin", message="最小奖品价值", type = DataType.STRING, required = true)
	@ActionParam(key = "goodsPriceMax", message="最大奖品价值", type = DataType.STRING)
	@ActionParam(key = "winningRate", message="中奖率", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(String uuid, String goodsPriceMin, String goodsPriceMax, String winningRate) {
		InputParams params = new InputParams("robotWinningRateService", "edit");
		params.addParams("goodsPriceMin", null, goodsPriceMin);
		params.addParams("goodsPriceMax", null, goodsPriceMax);
		params.addParams("winningRate", null, winningRate);
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
		InputParams params = new InputParams("robotWinningRateService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
}
