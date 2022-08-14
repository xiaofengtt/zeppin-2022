/**
 * 
 */
package cn.zeppin.product.ntb.shbx.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBkAreaService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 * 企财宝地区信息获取
 */

@Controller
@RequestMapping(value = "/shbxWeb/area")
public class ShbxWebAreaController extends BaseController {

	@Autowired
	private IBkAreaService areaService;
	
	/**
	 * 查询所有地区信息 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "level", message="级别", type = DataType.NUMBER)
	@ActionParam(key = "pid", message="父级地区", type = DataType.STRING)
	@ActionParam(key = "scode", message="编码", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, Integer level, String pid, String scode, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("level", level == null ? "" : level.toString());
		searchMap.put("pid", pid);
		
		if(scode != null){
			while (scode.endsWith("00")){
				scode = scode.substring(0, scode.length() - 2);
			}
		}
		searchMap.put("scode", scode);
		
		//查询符合条件的地区的总数
		Integer totalResultCount = areaService.getCount(searchMap);
		
		//查询符合条件的银行信息列表
		List<Entity> list = areaService.getListForPage(searchMap, pageNum, pageSize, sorts, BkArea.class);
		
		return ResultManager.createDataResult(list, pageNum, pageSize, totalResultCount);
	}
}
