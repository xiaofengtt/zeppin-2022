/**
 * 
 */
package cn.zeppin.product.itic.backadmin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.itic.backadmin.service.api.ITAreaService;
import cn.zeppin.product.itic.core.controller.base.ActionParam;
import cn.zeppin.product.itic.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.itic.core.controller.base.BaseController;
import cn.zeppin.product.itic.core.controller.base.Result;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.itic.core.entity.TArea;

/**
 * 地区查询
 */

@Controller
@RequestMapping(value = "/backadmin/area")
public class TAreaController extends BaseController {

	@Autowired
	private ITAreaService tAreaService;
	
	/**
	 * 查询地区列表 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "level", message = "级别", type = DataType.NUMBER)
	@ActionParam(key = "pid", message = "父级ID", type = DataType.STRING)
	@ActionParam(key = "scode", message = "编码", type = DataType.STRING)
	@ResponseBody
	public Result list(Integer level, String pid, String scode) {
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("level", level==null ? "" : level+"");
		inputParams.put("pid", pid);
		inputParams.put("scode", scode);
		
		List<TArea> list = tAreaService.getList(inputParams);
		
		return ResultManager.createDataResult(list, list.size());
	}
	
	/**
	 * 查询地区全称
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "scode", message = "编码", type = DataType.STRING)
	@ResponseBody
	public Result get(String scode) {
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("scode", scode);
		
		List<TArea> list = tAreaService.getList(inputParams);
		String name = "";
		if(list != null && list.size() > 0){
			TArea area = list.get(0);
			while(area != null){
				name = area.getName() + " " + name;
				if(area.getPid() != null){
					area = this.tAreaService.get(area.getPid());
				}else{
					area = null;
				}
			}
		}
		
		return ResultManager.createDataResult(name);
	}
}
