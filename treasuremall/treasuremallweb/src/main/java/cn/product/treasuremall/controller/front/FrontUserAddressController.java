/**
 * 
 */
package cn.product.treasuremall.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.controller.BaseController;
import io.swagger.annotations.Api;

/**
 * 用户地址
 */

@Controller
@RequestMapping(value = "/front/userAddress")
@Api(tags= {"userAddress"})
public class FrontUserAddressController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8570328079571263136L;

	/**
	 * 获取用户地址信息列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "sort", type = DataType.STRING, message="sort")
	@ResponseBody
	public Result list(HttpServletRequest request, String sort) {
		InputParams params = new InputParams("frontUserAddressService", "list");
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		params.addParams("sort", null, sort);
		return this.execute(params);
	}

	/**
	 * 获取用户地址信息
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="uuid", required = true)
	@ResponseBody
	public Result get(HttpServletRequest request, String uuid) {
		InputParams params = new InputParams("frontUserAddressService", "get");
		params.addParams("uuid", null, uuid);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 添加地址
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "receiver", type = DataType.STRING, message="收货人", required = true)
	@ActionParam(key = "phone", type = DataType.STRING, message="手机号码", required = true)
	@ActionParam(key = "area", type = DataType.STRING, message="所在地区", required = true)
	@ActionParam(key = "address", type = DataType.STRING, message="收货地址", required = true)
	@ResponseBody
	public Result add(HttpServletRequest request, String receiver, String phone, String area, String address) {
		InputParams params = new InputParams("frontUserAddressService", "add");
		params.addParams("receiver", null, receiver);
		params.addParams("phone", null, phone);
		params.addParams("area", null, area);
		params.addParams("address", null, address);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 修改地址
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="uuid", required = true)
	@ActionParam(key = "receiver", type = DataType.STRING, message="收货人", required = true)
	@ActionParam(key = "phone", type = DataType.STRING, message="手机号码", required = true)
	@ActionParam(key = "area", type = DataType.STRING, message="所在地区", required = true)
	@ActionParam(key = "address", type = DataType.STRING, message="详细地址", required = true)
	@ResponseBody
	public Result edit(HttpServletRequest request, String uuid, String receiver, String phone, String area, String address) {
		InputParams params = new InputParams("frontUserAddressService", "edit");
		params.addParams("uuid", null, uuid);
		params.addParams("receiver", null, receiver);
		params.addParams("phone", null, phone);
		params.addParams("area", null, area);
		params.addParams("address", null, address);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 删除地址
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="uuid", required = true)
	@ResponseBody
	public Result delete(HttpServletRequest request, String uuid) {
		InputParams params = new InputParams("frontUserAddressService", "delete");
		params.addParams("uuid", null, uuid);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
}
