/**
 * 
 */
package cn.product.score.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.score.api.base.ActionParam;
import cn.product.score.api.base.ActionParam.DataType;
import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.Result;
import cn.product.score.controller.BaseController;
import cn.product.score.entity.Category;

/**
 * 分类管理
 */

@Controller
@RequestMapping(value = "/front/category")
public class FrontCategoryController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4813498900946524108L;

	/**
	 * 赛事分类列表
	 * @param name
	 * @param shortname
	 * @param parent
	 * @param scode
	 * @param level
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "名称", type = DataType.STRING)
	@ActionParam(key = "shortname", message = "英文名称", type = DataType.STRING)
	@ActionParam(key = "parent", message = "父级", type = DataType.STRING)
	@ActionParam(key = "scode", message = "编码", type = DataType.STRING)
	@ActionParam(key = "level", message = "级别", type = DataType.NUMBER)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ResponseBody
	public Result list(Category category, Integer pageNum, Integer pageSize) {
		InputParams params = new InputParams("frontCategoryService", "list");
		params.addParams("category", null, category);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		return this.execute(params);
	}
	
	/**
	 * 赛事分类详细信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid) {
		InputParams params = new InputParams("frontCategoryService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
		
	}
	
	/**
	 * 积分榜
	 * @param category
	 * @return
	 */
	@RequestMapping(value = "/standingList", method = RequestMethod.GET)
	@ActionParam(key = "category", message = "赛事", type = DataType.STRING, required = true)
	@ResponseBody
	public Result standingList(String category) {
		InputParams params = new InputParams("frontCategoryService", "standingList");
		params.addParams("category", null, category);
		return this.execute(params);
	}
	
	/**
	 * 射手榜
	 * @param category
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/topscoreList", method = RequestMethod.GET)
	@ActionParam(key = "category", message = "赛事", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message = "排序", type = DataType.STRING)
	@ResponseBody
	public Result topscoreList(String category, String sort) {
		InputParams params = new InputParams("frontCategoryService", "topscoreList");
		params.addParams("category", null, category);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}

	
	/**
	 * 球队列表
	 * @param category
	 * @return
	 */
	@RequestMapping(value = "/teamList", method = RequestMethod.GET)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ResponseBody
	public Result teamList(String category) {
		InputParams params = new InputParams("frontCategoryService", "teamList");
		params.addParams("category", null, category);
		return this.execute(params);
	}
}
