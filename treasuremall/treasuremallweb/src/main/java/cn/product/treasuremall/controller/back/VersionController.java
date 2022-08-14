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


/**
 * 版本管理
 */

@Controller
@RequestMapping(value = "/back/version")
public class VersionController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3328319248126514718L;

	/**
	 * 列表
	 * @param type
	 * @param bundleid
	 * @param channel
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "type", message="系统类型", type = DataType.STRING)
	@ActionParam(key = "bundleid", message="包名", type = DataType.STRING)
	@ActionParam(key = "channel", message="应用商店", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String type, String bundleid, String channel, String status, Integer pageNum, Integer pageSize, String sorts) {
		
		InputParams params = new InputParams("versionService", "list");
		params.addParams("type", null, type);
		params.addParams("bundleid", null, bundleid);
		params.addParams("channel", null, channel);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sorts);
		return this.execute(params);
	}
	
	/**
	 * 获取
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		
		InputParams params = new InputParams("versionService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 添加
	 * @param type
	 * @param bundleid
	 * @param displayname
	 * @param channel
	 * @param name
	 * @param code
	 * @param mainurl
	 * @param tempurl
	 * @param downloadurl
	 * @param flag
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "type", message="系统类型", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "bundleid", message="包名", type = DataType.STRING, required = true, maxLength = 100)
	@ActionParam(key = "displayname", message="应用名称", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "channel", message="应用商店", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "name", message="版本号", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "code", message="版本编号", type = DataType.INTEGER, required = true)
	@ActionParam(key = "mainurl", message="实际应用地址", type = DataType.STRING)
	@ActionParam(key = "tempurl", message="马甲地址", type = DataType.STRING)
	@ActionParam(key = "downloadurl", message="马甲地址", type = DataType.STRING)
	@ActionParam(key = "flag", message="开关", type = DataType.BOOLEAN)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(String type, String bundleid, String displayname, String channel, String name, Integer code, String mainurl,
			String tempurl, String downloadurl, Boolean flag, String status) {
		
		InputParams params = new InputParams("versionService", "add");
		params.addParams("type", null, type);
		params.addParams("bundleid", null, bundleid);
		params.addParams("displayname", null, displayname);
		params.addParams("channel", null, channel);
		params.addParams("name", null, name);
		params.addParams("code", null, code);
		params.addParams("mainurl", null, mainurl);
		params.addParams("tempurl", null, tempurl);
		params.addParams("downloadurl", null, downloadurl);
		params.addParams("flag", null, flag);
		params.addParams("status", null, status);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
		
	}
	
	/**
	 * 编辑
	 * @param uuid
	 * @param mainurl
	 * @param tempurl
	 * @param downloadurl
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "mainurl", message="实际应用地址", type = DataType.STRING)
	@ActionParam(key = "tempurl", message="马甲地址", type = DataType.STRING)
	@ActionParam(key = "downloadurl", message="马甲地址", type = DataType.STRING)
	@ActionParam(key = "flag", message="开关", type = DataType.BOOLEAN)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(String uuid, String mainurl, String tempurl, String downloadurl, Boolean flag, String status) {
		
		InputParams params = new InputParams("versionService", "edit");
		params.addParams("uuid", null, uuid);
		params.addParams("mainurl", null, mainurl);
		params.addParams("tempurl", null, tempurl);
		params.addParams("downloadurl", null, downloadurl);
		params.addParams("flag", null, flag);
		params.addParams("status", null, status);
		return this.execute(params);
	}
	
	/**
	 * 删除
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		
		InputParams params = new InputParams("versionService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
}
