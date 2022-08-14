/**
 * 
 */
package cn.product.worldmall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;


/**
 * 广告图信息管理
 */

@Controller
@RequestMapping(value = "/back/banner")
public class BannerController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1631824883592845111L;

	/**
	 * 根据条件查询广告图信息 
	 * @param title
	 * @param status
	 * @param type
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "title", message="名称", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "type", message="一级分类", type = DataType.STRING, required = true)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String title, String status, String type, Integer pageNum, Integer pageSize, String sorts) {
		
		InputParams params = new InputParams("bannerService", "list");
		params.addParams("title", null, title);
		params.addParams("status", null, status);
		params.addParams("type", null, type);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sorts", null, sorts);
		return this.execute(params);
	}
	
	/**
	 * 获取一条广告图信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {	
		InputParams params = new InputParams("bannerService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 添加一条广告图信息
	 * @param type
	 * @param title
	 * @param code
	 * @param image
	 * @param url
	 * @param status
	 * @param endtime
	 * @param sort
	 * @param frontuserlevel
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "type", message="广告图类型", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "title", message="广告图名称", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "code", message="广告图英文名", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "image", message="广告图", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "url", message="广告图链接", type = DataType.STRING, required = true, maxLength = 100)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "endtime", message="广告图截止日期", type = DataType.STRING, required = true, maxLength = 100)
	@ActionParam(key = "frontuserlevel", message="用户级别", type = DataType.STRING, maxLength = 100)
	@ActionParam(key = "sort", message="权重", type = DataType.NUMBER, maxLength = 100)
	@ResponseBody
	public Result add(String type, String title, String code, String image, String url, String status, String endtime, String frontuserlevel, Integer sort) {
		
		InputParams params = new InputParams("bannerService", "add");
		params.addParams("type", null, type);
		params.addParams("title", null, title);
		params.addParams("code", null, code);
		params.addParams("image", null, image);
		params.addParams("url", null, url);
		params.addParams("status", null, status);
		params.addParams("endtime", null, endtime);
		params.addParams("frontuserlevel", null, frontuserlevel);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 编辑一条广告图信息
	 * @param uuid
	 * @param type
	 * @param title
	 * @param code
	 * @param image
	 * @param url
	 * @param status
	 * @param endtime
	 * @param sort
	 * @param frontuserlevel
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "title", message="广告图名称", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "code", message="广告图英文名", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "image", message="广告图", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "url", message="广告图链接", type = DataType.STRING, required = true, maxLength = 100)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "endtime", message="广告图截止日期", type = DataType.STRING, required = true, maxLength = 100)
	@ActionParam(key = "frontuserlevel", message="用户级别", type = DataType.STRING, maxLength = 100)
	@ActionParam(key = "sort", message="权重", type = DataType.NUMBER, maxLength = 100)
	@ResponseBody
	public Result edit(String uuid, String title, String code, String image, String url, String status, String endtime, String frontuserlevel, Integer sort) {
		InputParams params = new InputParams("bannerService", "edit");
		params.addParams("title", null, title);
		params.addParams("code", null, code);
		params.addParams("image", null, image);
		params.addParams("url", null, url);
		params.addParams("status", null, status);
		params.addParams("endtime", null, endtime);
		params.addParams("frontuserlevel", null, frontuserlevel);
		params.addParams("uuid", null, uuid);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 删除一条广告图信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		InputParams params = new InputParams("bannerService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 置顶广告图信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result top(String uuid) {
		InputParams params = new InputParams("bannerService", "top");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 刷新显示
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	@ActionParam(key = "type", message="广告图类型", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result refresh(String type) {
		
		InputParams params = new InputParams("bannerService", "refresh");
		params.addParams("type", null, type);
		return this.execute(params);
	}
}
