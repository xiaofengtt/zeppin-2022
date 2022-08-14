/**
 * 
 */
package cn.product.score.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.score.api.base.ActionParam;
import cn.product.score.api.base.ActionParam.DataType;
import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.Result;
import cn.product.score.controller.BaseController;
import cn.product.score.entity.NewsComment;
import cn.product.score.entity.NewsPublish;

/**
 * 前端新闻接口
 */

@Controller
@RequestMapping(value = "/front/news")
public class FrontNewsController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -686556009013331061L;

	/**
	 * 新闻列表
	 * @param category
	 * @param team
	 * @param title
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING)
	@ActionParam(key = "title", message = "标题", type = DataType.STRING)
	@ActionParam(key = "except", message = "排除", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序", type = DataType.STRING)
	@ResponseBody
	public Result list(NewsPublish newsPublish, String except, Integer pageNum, Integer pageSize, String sort) {
		
		InputParams params = new InputParams("frontNewsService", "list");
		params.addParams("newsPublish", null, newsPublish);
		params.addParams("except", null, except);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 新闻详细信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid) {
		
		InputParams params = new InputParams("frontNewsService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 评论列表
	 * @param newsPublish
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/commentList", method = RequestMethod.GET)
	@ActionParam(key = "newsPublish", message = "新闻", type = DataType.STRING, required = true)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ResponseBody
	public Result commentList(String newsPublish, Integer pageNum, Integer pageSize) {
		
		InputParams params = new InputParams("frontNewsService", "commentList");
		params.addParams("newsPublish", null, newsPublish);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		return this.execute(params);
	}
	
	/**
	 * 添加评论
	 * @param newspublish
	 * @param parent
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@ActionParam(key = "newspublish", message = "新闻", type = DataType.STRING, required = true)
	@ActionParam(key = "parent", message = "回复主体", type = DataType.STRING)
	@ActionParam(key = "content", message = "评论内容", type = DataType.STRING, required = true)
	@ResponseBody
	public Result comment(NewsComment nc, HttpServletRequest request) {
		
		InputParams params = new InputParams("frontNewsService", "comment");
		params.addParams("nc", null, nc);
		params.addParams("user", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
}
