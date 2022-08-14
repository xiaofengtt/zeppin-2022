/**
 * 
 */
package cn.zeppin.product.score.controller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.score.controller.base.ActionParam;
import cn.zeppin.product.score.controller.base.ActionParam.DataType;
import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.entity.Version;
import cn.zeppin.product.score.service.FrontUserService;
import cn.zeppin.product.score.service.VersionService;
import cn.zeppin.product.score.vo.front.VersionVO;

/**
 * 用户信息
 */

@Controller
@RequestMapping(value = "/front/version")
public class FrontVersionController extends BaseController{
	
	@Autowired
	private FrontUserService frontUserService;
	
	@Autowired
	private VersionService versionService;
	
	/**
	 * 获取版本信息
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "channel", message = "渠道ID", type = DataType.STRING, required = true)
	@ActionParam(key = "version", message = "版本号", type = DataType.STRING, required = true)
	@ActionParam(key = "bundleid", message = "包名", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String channel, String version, String bundleid) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("channel", channel);
		searchMap.put("name", version);
		searchMap.put("bundleid", bundleid);
		List<Version> list = this.versionService.getListByParams(searchMap);
		if(list != null && !list.isEmpty()) {
			VersionVO vvo = new VersionVO(list.get(0));
			return ResultManager.createDataResult(vvo);
		} else {
			return ResultManager.createDataResult(new VersionVO());
		}
	}
}
