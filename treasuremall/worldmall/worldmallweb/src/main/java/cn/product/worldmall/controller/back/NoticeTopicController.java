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
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;
import cn.product.worldmall.controller.BaseController;


/**
 * 主题管理
 */

@Controller
@RequestMapping(value = "/back/topic")
public class NoticeTopicController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8189377897456621682L;

	/**
	 * 根据条件查询主题 
	 * @param title
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "displayName", message="名称", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String displayName, String status, Integer pageNum, Integer pageSize, String sorts) {
		
		InputParams params = new InputParams("noticeTopicService", "list");
		params.addParams("displayName", null, displayName);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sorts);
		return this.execute(params);
	}
	
	/**
	 * 获取一条主题
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {	
		InputParams params = new InputParams("noticeTopicService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
}
