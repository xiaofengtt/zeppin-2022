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

/**
 * 评论管理
 */

@Controller
@RequestMapping(value = "/back/newsComment")
public class NewsCommentController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5321980448042992947L;

	/**
	 * 根据条件查询列表
	 * @param content
	 * @param newspublish
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "content", message = "名称", type = DataType.STRING)
	@ActionParam(key = "newspublish", message = "新闻", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ResponseBody
	public Result list(String content, String newspublish, Integer pageNum, Integer pageSize) {
		
		InputParams params = new InputParams("newsCommentService", "list");
		params.addParams("content", null, content);
		params.addParams("newspublish", null, newspublish);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
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
		InputParams params = new InputParams("newsCommentService", "get");
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
		InputParams params = new InputParams("newsCommentService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
}
