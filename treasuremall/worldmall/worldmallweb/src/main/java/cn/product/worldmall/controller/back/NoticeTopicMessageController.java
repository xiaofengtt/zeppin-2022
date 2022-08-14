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
 * 推送消息信息管理
 */

@Controller
@RequestMapping(value = "/back/topicMessage")
public class NoticeTopicMessageController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7740011227070311199L;

	/**
	 * 根据条件查询推送消息信息
	 * @param type
	 * @param topic
	 * @param title
	 * @param status
	 * @param sendtime
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "type", message="消息类型", type = DataType.STRING)
	@ActionParam(key = "topic", message="发送主题", type = DataType.STRING)
	@ActionParam(key = "title", message="名称", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "sendtime", message="发送时间", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String type, String topic, String title, String status, String sendtime, Integer pageNum, Integer pageSize, String sorts) {
		
		InputParams params = new InputParams("noticeTopicMessageService", "list");
		params.addParams("type", null, type);
		params.addParams("topic", null, topic);
		params.addParams("title", null, title);
		params.addParams("status", null, status);
		params.addParams("sendtime", null, sendtime);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sorts);
		return this.execute(params);
	}
	
	/**
	 * 获取一条推送消息信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {	
		InputParams params = new InputParams("noticeTopicMessageService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 添加一条推送消息信息
	 * @param uuid
	 * @param type
	 * @param topic
	 * @param title
	 * @param content
	 * @param sendtime
	 * @return
	 */
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="UUID", type = DataType.STRING)
	@ActionParam(key = "type", message="推送消息类型", type = DataType.STRING)
	@ActionParam(key = "title", message="标题", type = DataType.STRING, required = true)
	@ActionParam(key = "content", message="内容", type = DataType.STRING, required = true)
	@ActionParam(key = "topic", message="主题ID", type = DataType.STRING, required = true)
	@ActionParam(key = "sendtime", message="上线时间", type = DataType.STRING)
	@ResponseBody
	public Result send(String uuid, String type, String topic, String title, String content, String sendtime) {
		
		InputParams params = new InputParams("noticeTopicMessageService", "send");
		params.addParams("uuid", null, uuid);
		params.addParams("type", null, type);
		params.addParams("topic", null, topic);
		params.addParams("title", null, title);
		params.addParams("content", null, content);
		params.addParams("sendtime", null, sendtime);
		return this.execute(params);
	}
}
