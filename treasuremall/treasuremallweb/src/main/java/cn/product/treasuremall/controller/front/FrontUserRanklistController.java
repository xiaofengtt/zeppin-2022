/**
 * 
 */
package cn.product.treasuremall.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 排行榜
 */

@Controller
@RequestMapping(value = "/front/ranklist")
@Api(tags= {"ranklist"})
public class FrontUserRanklistController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5288171073618956512L;

	@ApiOperation(value = "排行榜列表", notes = "rankNum-排名/buyCount-参与人次/totalWinning-中间金额（单位元）")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Result list() {
		InputParams params = new InputParams("frontUserRanklistService", "list");
		return this.execute(params);
	}
}
