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
import cn.product.treasuremall.entity.NoticePublic;


/**
 * 公告信息管理
 */

@Controller
@RequestMapping(value = "/back/noticePublic")
public class NoticePublicController extends BaseController {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1202770811021716203L;

	/**
	 * 根据条件查询公告信息 
	 * @param title
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "title", message="名称", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String title, String status, Integer pageNum, Integer pageSize, String sorts) {
		
		InputParams params = new InputParams("noticePublicService", "list");
		params.addParams("title", null, title);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sorts);
		return this.execute(params);
	}
	
	/**
	 * 获取一条公告信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {	
		InputParams params = new InputParams("noticePublicService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 添加一条公告信息
	 * @param noticePublic
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "type", message="公告类型", type = DataType.STRING, required = true)
	@ActionParam(key = "title", message="标题", type = DataType.STRING, required = true)
	@ActionParam(key = "details", message="内容", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ActionParam(key = "onlinetime", message="上线时间", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(NoticePublic noticePublic) {
		
		InputParams params = new InputParams("noticePublicService", "add");
		params.addParams("noticePublic", null, noticePublic);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	/**
	 * 编辑一条公告信息
	 * @param noticePublic
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "type", message="公告类型", type = DataType.STRING, required = true)
	@ActionParam(key = "title", message="标题", type = DataType.STRING, required = true)
	@ActionParam(key = "details", message="内容", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ActionParam(key = "onlinetime", message="上线时间", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(NoticePublic noticePublic) {
		InputParams params = new InputParams("noticePublicService", "edit");
		params.addParams("noticePublic", null, noticePublic);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	/**
	 * 删除一条公告信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		InputParams params = new InputParams("noticePublicService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 变更公告状态
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status) {
		InputParams params = new InputParams("noticePublicService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		return this.execute(params);
	}
}
