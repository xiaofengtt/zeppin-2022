/**
 * 
 */
package cn.product.score.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.score.api.base.ActionParam;
import cn.product.score.api.base.ActionParam.DataType;
import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.Result;
import cn.product.score.controller.BaseController;
import cn.product.score.entity.News;

/**
 * 新闻管理
 */

@Controller
@RequestMapping(value = "/back/news")
public class NewsController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5260719756090167629L;

	/**
	 * 根据条件查询列表
	 * @param category
	 * @param team
	 * @param title
	 * @param source
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING)
	@ActionParam(key = "title", message = "标题", type = DataType.STRING)
	@ActionParam(key = "source", message = "来源", type = DataType.STRING)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序", type = DataType.STRING)
	@ResponseBody
	public Result list(News news, Integer pageNum, Integer pageSize, String sort) {
		
		InputParams params = new InputParams("newsService", "list");
		params.addParams("news", null, news);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
		
	}
	
	/**
	 * 获取信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid) {
		
		InputParams params = new InputParams("newsService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
		
	}
	
	/**
	 * 添加
	 * @param category
	 * @param team
	 * @param title
	 * @param content
	 * @param author
	 * @param newstime
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING)
	@ActionParam(key = "title", message = "标题", type = DataType.STRING, required = true)
	@ActionParam(key = "content", message = "内容", type = DataType.STRING, required = true)
	@ActionParam(key = "author", message = "作者", type = DataType.STRING)
	@ActionParam(key = "newstime", message = "发布时间", type = DataType.STRING)
	@ResponseBody
	public Result add(News news) {
		
		InputParams params = new InputParams("newsService", "add");
		params.addParams("news", null, news);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
		
	}
	
	/**
	 * 编辑
	 * @param uuid
	 * @param name
	 * @param shortname
	 * @param parent
	 * @param scode
	 * @param level
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING)
	@ActionParam(key = "title", message = "标题", type = DataType.STRING, required = true)
	@ActionParam(key = "content", message = "内容", type = DataType.STRING, required = true)
	@ActionParam(key = "author", message = "作者", type = DataType.STRING)
	@ActionParam(key = "newstime", message = "发布时间", type = DataType.STRING)
	@ResponseBody
	public Result edit(News news) {
		
		InputParams params = new InputParams("newsService", "edit");
		params.addParams("news", null, news);
		return this.execute(params);
		
	}
	
	/**
	 * 变更状态
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status) {
		
		InputParams params = new InputParams("newsService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		return this.execute(params);
		
	}
	
	/**
	 * 删除
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		
		InputParams params = new InputParams("newsService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
		
	}
	
	/**
	 * 获取分状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {

		InputParams params = new InputParams("newsService", "statusList");
		return this.execute(params);
	}
}
