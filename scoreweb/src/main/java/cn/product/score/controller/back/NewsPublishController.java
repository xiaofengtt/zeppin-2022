/**
 * 
 */
package cn.product.score.controller.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.score.api.base.ActionParam;
import cn.product.score.api.base.ActionParam.DataType;
import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.Result;
import cn.product.score.controller.BaseController;
import cn.product.score.entity.NewsPublish;

/**
 * 新闻发布管理
 */

@Controller
@RequestMapping(value = "/back/newsPublish")
public class NewsPublishController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2068556597561161417L;

	@Autowired
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
	@ActionParam(key = "news", message = "新闻", type = DataType.STRING)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING)
	@ActionParam(key = "title", message = "标题", type = DataType.STRING)
	@ActionParam(key = "source", message = "来源", type = DataType.STRING)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序", type = DataType.STRING)
	@ResponseBody
	public Result list(NewsPublish newsPublish, Integer pageNum, Integer pageSize, String sort) {
		
		InputParams params = new InputParams("", "list");
		params.addParams("newsPublish", null, newsPublish);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
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
	@RequestMapping(value = "/checkList", method = RequestMethod.GET)
	@ActionParam(key = "news", message = "新闻", type = DataType.STRING)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING)
	@ActionParam(key = "title", message = "标题", type = DataType.STRING)
	@ActionParam(key = "source", message = "来源", type = DataType.STRING)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序", type = DataType.STRING)
	@ResponseBody
	public Result checkList(NewsPublish newsPublish, Integer pageNum, Integer pageSize, String sort) {
		
		InputParams params = new InputParams("newsPublishService", "checkList");
		params.addParams("newsPublish", null, newsPublish);
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
		
		InputParams params = new InputParams("newsPublishService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 添加
	 * @param news
	 * @param category
	 * @param team
	 * @param title
	 * @param content
	 * @param cover
	 * @param author
	 * @param newstime
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "news", message = "新闻", type = DataType.STRING)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING)
	@ActionParam(key = "title", message = "标题", type = DataType.STRING, required = true)
	@ActionParam(key = "content", message = "内容", type = DataType.STRING, required = true)
	@ActionParam(key = "cover", message = "封面图", type = DataType.STRING)
	@ActionParam(key = "author", message = "作者", type = DataType.STRING)
	@ActionParam(key = "newstime", message = "发布时间", type = DataType.STRING)
	@ResponseBody
	public Result add(NewsPublish newsPublish) {
		
		InputParams params = new InputParams("newsPublishService", "add");
		params.addParams("newsPublish", null, newsPublish);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	/**
	 * 编辑
	 * @param uuid
	 * @param category
	 * @param team
	 * @param title
	 * @param content
	 * @param cover
	 * @param author
	 * @param newstime
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING)
	@ActionParam(key = "title", message = "标题", type = DataType.STRING, required = true)
	@ActionParam(key = "content", message = "内容", type = DataType.STRING, required = true)
	@ActionParam(key = "cover", message = "封面图", type = DataType.STRING)
	@ActionParam(key = "author", message = "作者", type = DataType.STRING)
	@ActionParam(key = "newstime", message = "发布时间", type = DataType.STRING)
	@ResponseBody
	public Result edit(NewsPublish newsPublish) {
		InputParams params = new InputParams("newsPublishService", "edit");
		params.addParams("newsPublish", null, newsPublish);
		return this.execute(params);
	}
	
	/**
	 * 重新提交审核
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/resubmit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result resubmit(String uuid) {
		
		InputParams params = new InputParams("newsPublishService", "resubmit");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 审核发布
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message = "审核状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result check(String uuid, String status) {
		
		InputParams params = new InputParams("newsPublishService", "check");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	/**
	 * 下架
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result remove(String uuid) {
		
		InputParams params = new InputParams("newsPublishService", "remove");
		params.addParams("uuid", null, uuid);
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
		
		InputParams params = new InputParams("newsPublishService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
		
	}
	
	/**
	 * 审核者分状态列表
	 * @return
	 */
	@RequestMapping(value = "/checkStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result checkStatusList() {
		
		InputParams params = new InputParams("newsPublishService", "checkStatusList");
		return this.execute(params);
		
	}
	
	/**
	 * 分状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		
		InputParams params = new InputParams("newsPublishService", "checkStatusList");
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
}
