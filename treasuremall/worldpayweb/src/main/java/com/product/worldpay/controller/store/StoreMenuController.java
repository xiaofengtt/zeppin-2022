/**
 * 
 */
package com.product.worldpay.controller.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.worldpay.controller.BaseController;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.controller.base.Result;

/**
 * 获取菜单
 */

@Controller
@RequestMapping(value = "/store/menu")
public class StoreMenuController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7070510893830023749L;

	/**
	 * 查询左侧菜单信息 
	 * @return
	 */
	@RequestMapping(value = "/left", method = RequestMethod.GET)
	@ResponseBody
	public Result left() {
		InputParams params = new InputParams("storeMenuService", "left");
		params.addParams("companyAdmin", null, getCompanyAdmin());
		return this.execute(params);
	}
}
