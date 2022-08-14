/**
 * 
 */
package cn.product.worldmall.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;
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
	@ActionParam(key = "receiver", type = DataType.STRING, message="Contact Name", required = true)
	@ActionParam(key = "phone", type = DataType.STRING, message="Mobile Number", required = true)
	@ActionParam(key = "area", type = DataType.STRING, message="Area")
	@ActionParam(key = "address", type = DataType.STRING, message="Street Address or P.O.Box", required = true)
	@ActionParam(key = "country", type = DataType.STRING, message="Country/Region", required = true)
	@ActionParam(key = "city", type = DataType.STRING, message="City", required = true)
	@ActionParam(key = "state", type = DataType.STRING, message="State/Province/Region", required = true)
	@ActionParam(key = "zipcode", type = DataType.STRING, message="ZIP Code", required = true)
	@ActionParam(key = "asub", type = DataType.STRING, message="Apt,Suite,Unit,Building(optional)")
	@ResponseBody
	public Result add(HttpServletRequest request, String receiver, String phone, String area, String address, String country, String city, String state, String asub, String zipcode) {
		InputParams params = new InputParams("frontUserAddressService", "add");
		params.addParams("receiver", null, receiver);
		params.addParams("phone", null, phone);
		params.addParams("area", null, area);
		params.addParams("address", null, address);
		params.addParams("country", null, country);
		params.addParams("city", null, city);
		params.addParams("state", null, state);
		params.addParams("asub", null, asub);
		params.addParams("zipcode", null, zipcode);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 修改地址
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="uuid", required = true)
	@ActionParam(key = "receiver", type = DataType.STRING, message="Contact Name", required = true)
	@ActionParam(key = "phone", type = DataType.STRING, message="Mobile Number", required = true)
	@ActionParam(key = "area", type = DataType.STRING, message="Area")
	@ActionParam(key = "address", type = DataType.STRING, message="Street Address or P.O.Box", required = true)
	@ActionParam(key = "country", type = DataType.STRING, message="Country/Region", required = true)
	@ActionParam(key = "city", type = DataType.STRING, message="City", required = true)
	@ActionParam(key = "state", type = DataType.STRING, message="State/Province/Region", required = true)
	@ActionParam(key = "zipcode", type = DataType.STRING, message="ZIP Code", required = true)
	@ActionParam(key = "asub", type = DataType.STRING, message="Apt,Suite,Unit,Building(optional)")
	@ResponseBody
	public Result edit(HttpServletRequest request, String uuid, String receiver, String phone, String area, String address, String country
			, String city, String state, String asub, String zipcode) {
		InputParams params = new InputParams("frontUserAddressService", "edit");
		params.addParams("uuid", null, uuid);
		params.addParams("receiver", null, receiver);
		params.addParams("phone", null, phone);
		params.addParams("area", null, area);
		params.addParams("address", null, address);
		params.addParams("country", null, country);
		params.addParams("city", null, city);
		params.addParams("state", null, state);
		params.addParams("asub", null, asub);
		params.addParams("zipcode", null, zipcode);
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
