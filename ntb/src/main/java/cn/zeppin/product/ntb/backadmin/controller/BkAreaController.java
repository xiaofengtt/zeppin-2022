/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBkAreaService;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 * 功能管理
 */

@Controller
@RequestMapping(value = "/backadmin/area")
public class BkAreaController extends BaseController {

	@Autowired
	private IBkAreaService areaService;
	/**
	 * 查询所有地区信息 
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Result all() {
		List<Entity> arealist = areaService.getAll(BkArea.class);
		
		return ResultManager.createDataResult(arealist, arealist.size());
	}
}
