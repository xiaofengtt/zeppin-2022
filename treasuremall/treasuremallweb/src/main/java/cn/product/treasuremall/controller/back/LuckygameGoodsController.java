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
import cn.product.treasuremall.entity.LuckygameGoods;

@Controller
@RequestMapping(value = "/back/luckygameGoods")
public class LuckygameGoodsController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3452292654385254615L;

	/**
	 * 查看详情
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("luckygameGoodsService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 查询列表
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ActionParam(key = "gameType", message="玩法类型", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sort, String gameType){
		InputParams params = new InputParams("luckygameGoodsService", "list");
		params.addParams("name", null, name);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("gameType", null, gameType);
		return this.execute(params);
	}
	
	/**
	 * 查询奖品期列表
	 * @param luckygameGoods
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value="/issuelist",method=RequestMethod.GET)
	@ActionParam(key = "luckygameGoods", message="抽奖奖品ID", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result issuelist(String luckygameGoods, String status, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("luckygameGoodsService", "issuelist");
		params.addParams("luckygameGoods", null, luckygameGoods);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 查看每期详情
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value="/issueget",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result issueget(String uuid){
		InputParams params = new InputParams("luckygameGoodsService", "issueget");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "goodsId", message="奖品ID", type = DataType.STRING, required = true)
	@ActionParam(key = "dPrice", message="奖品价值金币", type = DataType.NUMBER, required = true)
	@ActionParam(key = "profitRate", message="盈利比例", type = DataType.NUMBER, required = true)
	@ActionParam(key = "betPerShare", message="单人次金币", type = DataType.NUMBER, required = true)
	@ActionParam(key = "shares", message="总需人次", type = DataType.INTEGER, required = true)
	@ActionParam(key = "totalIssueNum", message="发布总期数", type = DataType.STRING, required = true)
	@ActionParam(key = "gameType", message="玩法类型", type = DataType.STRING, required = true)
	@ActionParam(key = "tabs", message="标签", type = DataType.STRING)
	@ResponseBody
    public Result add(LuckygameGoods luckygameGoods){
		InputParams params = new InputParams("luckygameGoodsService", "add");
		params.addParams("luckygameGoods", null, luckygameGoods);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
    }
    
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "goodsId", message="奖品ID", type = DataType.STRING, required = true)
	@ActionParam(key = "dPrice", message="奖品价值金币", type = DataType.NUMBER, required = true)
	@ActionParam(key = "profitRate", message="盈利比例", type = DataType.NUMBER, required = true)
	@ActionParam(key = "betPerShare", message="单人次金币", type = DataType.NUMBER, required = true)
	@ActionParam(key = "shares", message="总需人次", type = DataType.INTEGER, required = true)
	@ActionParam(key = "totalIssueNum", message="发布总期数", type = DataType.STRING, required = true)
	@ActionParam(key = "tabs", message="标签", type = DataType.STRING)
	@ResponseBody
	public Result edit(LuckygameGoods luckygameGoods){
		InputParams params = new InputParams("luckygameGoodsService", "edit");
		params.addParams("luckygameGoods", null, luckygameGoods);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	/**
	 * 上架下架
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/changeStatus",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status){
		InputParams params = new InputParams("luckygameGoodsService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		return this.execute(params);
	}
	
	/**
	 * 排序
	 * @param uuid
	 * @param type top-置顶 up-向上 down-向下
	 * @return
	 */
	@RequestMapping(value="/sorts",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "type", message="排序类型", type = DataType.STRING, required = true)
	@ResponseBody
	public Result sorts(String uuid, String type){
		InputParams params = new InputParams("luckygameGoodsService", "sorts");
		params.addParams("uuid", null, uuid);
		params.addParams("type", null, type);
		return this.execute(params);
	}
	
	/**
	 * 删除
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid){
		InputParams params = new InputParams("luckygameGoodsService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
}
