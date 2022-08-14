/**
 * 
 */
package cn.product.treasuremall.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;

/**
 * 前端商品列表
 */

@Controller
@RequestMapping(value = "/front/goods")
@Api(tags= {"goods"})
public class FrontGoodsIssueController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4682761247632061535L;

	/**
	 * 查询奖品期列表
	 * @param luckygameGoods
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@ApiOperation(value = "商品列表", notes = "首页的商品列表和搜索页的搜索列表")
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "luckygameGoods", message="抽奖奖品ID", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "goodsType", message="商品类型", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "betLineMax", message="最大平均投注额", type = DataType.STRING)
	@ActionParam(key = "betLineMin", message="最小平均投注额", type = DataType.STRING)
	@ActionParam(key = "priceLineMax", message="最大商品金额", type = DataType.STRING)
	@ActionParam(key = "priceLineMin", message="最小商品金额", type = DataType.STRING)
	@ActionParam(key = "gameType", message="玩法类型", type = DataType.STRING)
	@ResponseBody
	public Result list(String luckygameGoods, String goodsType, String status, Integer pageNum, Integer pageSize, String sort, String name
			, String betLineMax, String betLineMin, String priceLineMax, String priceLineMin, String gameType){
		InputParams params = new InputParams("frontGoodsIssueService", "list");
		params.addParams("luckygameGoods", null, luckygameGoods);
		params.addParams("status", null, status);
		params.addParams("goodsType", null, goodsType);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("name", null, name);
		params.addParams("betLineMax", null, betLineMax);
		params.addParams("betLineMin", null, betLineMin);
		params.addParams("priceLineMax", null, priceLineMax);
		params.addParams("priceLineMin", null, priceLineMin);
		params.addParams("gameType", null, gameType);
		return this.execute(params);
	}
	
	/**
	 * 查看每期详情
	 * @param uuid
	 * @return
	 */
	@ApiOperation(value = "商品详情", notes = "首页的商品列表和搜索页的搜索列表")
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "frontUser", message="uuid", type = DataType.STRING)
	@ResponseBody
	public Result get(HttpServletRequest request, String uuid, String frontUser){
		InputParams params = new InputParams("frontGoodsIssueService", "get");
		params.addParams("uuid", null, uuid);
		params.addParams("frontUser", null, frontUser);
		return this.execute(params);
	}
	
	/**
	 * 获取用户参与记录列表
	 * @param frontUser
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "本期参与记录", notes = "参与记录统计列表")
	@RequestMapping(value="/paymentList",method=RequestMethod.GET)
	@ActionParam(key = "showId", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "goodsIssue", message="奖品", type = DataType.STRING)
	@ActionParam(key = "isLuck", message="中奖状态", type = DataType.BOOLEAN)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ActionParam(key = "gameType", message="玩法类型", type = DataType.STRING)
	@ResponseBody
	public Result paymentList(String showId, String goodsIssue, String isLuck, Integer pageNum, Integer pageSize, String sort, String gameType){
		InputParams params = new InputParams("frontGoodsIssueService", "paymentList");
		params.addParams("showId", null, showId);
		params.addParams("goodsIssue", null, goodsIssue);
		params.addParams("isLuck", null, isLuck);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("gameType", null, gameType);
		return this.execute(params);
	}
	
	/**
	 * 获取用户中奖纪录列表
	 * @param frontUser
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "往期中奖纪录", notes = "往期中奖纪录")
	@RequestMapping(value="/winningInfoList",method=RequestMethod.GET)
	@ActionParam(key = "showId", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "type", message="领奖类型", type = DataType.STRING)
	@ActionParam(key = "goodsId", message="商品ID", type = DataType.STRING)
	@ActionParam(key = "winningTime", message="开奖时间", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING) 
	@ActionParam(key = "gameType", message="玩法类型", type = DataType.STRING)
	@ResponseBody
	public Result winningInfoList(Integer showId, String type, String goodsId, Integer pageNum, Integer pageSize, String sort, String gameType){
		InputParams params = new InputParams("frontGoodsIssueService", "winningInfoList");
		params.addParams("showId", null, showId);
		params.addParams("type", null, type);
		params.addParams("goodsId", null, goodsId);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("gameType", null, gameType);
		return this.execute(params);
	}
	
	
	/**
	 * 获取本期沙发、土豪包尾统计信息
	 * @param frontUser
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "获取本期top统计信息", notes = "获取本期沙发、土豪包尾统计信息")
	@RequestMapping(value="/paymentTop",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result paymentTop(String uuid){
		InputParams params = new InputParams("frontGoodsIssueService", "paymentTop");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 商品类型列表
	 * @param name
	 * @param parent
	 * @param level
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@ApiOperation(value = "获取商品类型列表", notes = "获取商品类型列表")
	@RequestMapping(value="/goodsType",method=RequestMethod.GET)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "parent", message="父级", type = DataType.STRING)
	@ActionParam(key = "level", message="级别", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result goodsType(String name, String parent, String level, String status, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("frontGoodsIssueService", "goodsType");
		params.addParams("name", null, name);
		params.addParams("parent", null, parent);
		params.addParams("level", null, level);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
}
