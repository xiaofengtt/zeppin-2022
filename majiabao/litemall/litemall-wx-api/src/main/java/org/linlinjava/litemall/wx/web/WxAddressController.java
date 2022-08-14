package org.linlinjava.litemall.wx.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.RegexUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallAddress;
import org.linlinjava.litemall.db.service.LitemallAddressService;
import org.linlinjava.litemall.db.service.LitemallRegionService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.config.sign.AuthInterceptor;
import org.linlinjava.litemall.wx.service.GetRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户收货地址服务
 */
@RestController
@RequestMapping("/wx/address")
@Validated
@Api(value = "用户收货地址",description = "用户收货地址接口")
public class WxAddressController extends GetRegionService {
	private final Log logger = LogFactory.getLog(WxAddressController.class);

	@Autowired
	private LitemallAddressService addressService;

	@Autowired
	private LitemallRegionService regionService;


	/**
	 * 用户收货地址列表
	 *
	 * @param userId 用户ID
	 * @return 收货地址列表
	 */
	@GetMapping("list")
	@AuthInterceptor(needAuthTokenVerify = true)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "int")
	})
	@ApiOperation(value="用户收货地址列表", notes="用户收货地址列表(登入验证)")
	public Object list(@LoginUser Integer userId) {
		if (userId == null) {
			return ResponseUtil.unlogin();
		}
		List<LitemallAddress> addressList = addressService.queryByUid(userId);
		List<LitemallAddress> onelist=new ArrayList<> ();
		int index=0;


		if(addressList.size ()!=0&&addressList.size ()!=1){

			for (int i = 0; i < addressList.size (); i++) {
				if(addressList.get (i).getIsDefault ()){
					onelist.add (addressList.get (i));
					index=i;
				}
			}
			if(index!=101)
			addressList.remove(index);
		}

//		for (LitemallAddress litemallAddress: addressList) {
//			if(litemallAddress.getIsDefault ()){
//				onelist.add (litemallAddress);
//				addressList.remove (litemallAddress);
//			}
//
//		}

		onelist.addAll (addressList);
		return ResponseUtil.okList(onelist);
	}

	/**
	 * 收货地址详情
	 *
	 * @param userId 用户ID
	 * @param id     收货地址ID
	 * @return 收货地址详情
	 */
	@GetMapping("detail")
	@AuthInterceptor(needAuthTokenVerify = true)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "int"),
			@ApiImplicitParam(paramType="query", name="id" ,value = "收货地址id", required = true, dataType = "int")
	})
	@ApiOperation(value="收货地址详情", notes="收货地址详情(登入验证)")
	public Object detail(@LoginUser Integer userId, @NotNull Integer id) {
		if (userId == null) {
			return ResponseUtil.unlogin();
		}

		LitemallAddress address = addressService.query(userId, id);
		if (address == null) {
			return ResponseUtil.badArgumentValue();
		}
		return ResponseUtil.ok(address);
	}

	private Object validate(LitemallAddress address) {
		String name = address.getName();
		if (StringUtils.isEmpty(name)) {
			return ResponseUtil.badArgument();
		}

		// 测试收货手机号码是否正确
		String mobile = address.getTel();
		if (StringUtils.isEmpty(mobile)) {
			return ResponseUtil.badArgument();
		}
		if (!RegexUtil.isMobileExact(mobile)) {
			return ResponseUtil.badArgument();
		}

		String province = address.getProvince();
		if (StringUtils.isEmpty(province)) {
			return ResponseUtil.badArgument();
		}

		String city = address.getCity();
		if (StringUtils.isEmpty(city)) {
			return ResponseUtil.badArgument();
		}

		String county = address.getCounty();
		if (StringUtils.isEmpty(county)) {
			return ResponseUtil.badArgument();
		}


		String areaCode = address.getAreaCode();
		if (StringUtils.isEmpty(areaCode)) {
			return ResponseUtil.badArgument();
		}

		String detailedAddress = address.getAddressDetail();
		if (StringUtils.isEmpty(detailedAddress)) {
			return ResponseUtil.badArgument();
		}

		Boolean isDefault = address.getIsDefault();
		if (isDefault == null) {
			return ResponseUtil.badArgument();
		}
		return null;
	}

	/**
	 * 添加或更新收货地址
	 *
	 * @param userId  用户ID
	 * @param address 用户收货地址
	 * @return 添加或更新操作结果
	 */
	@PostMapping("save")
	@AuthInterceptor(needAuthTokenVerify = true)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "int"),
			@ApiImplicitParam(paramType="body", name="address" ,value = "{\n" +
					"\"id\":\"2\",\n" +
					"\"addressDetail\": \"123124\",\n" +
					"\"areaCode\": \"110101\",\n" +
					"\"city\": \"市辖区\",\n" +
					"\"country\": \"\",\n" +
					"\"county\": \"东城区\",\n" +
					"\"isDefault\": true,\n" +
					"\"name\": \"test1\",\n" +
					"\"postalCode\": \"\",\n" +
					"\"province\": \"北京市\",\n" +
					"\"tel\": \"17786530802\"\n" +
					"}(ps:添加不传id)", required = true, dataType = "json")
	})
	@ApiOperation(value="添加或更新收货地址", notes="添加或更新收货地址(登入验证)")
	public Object save(@LoginUser Integer userId, @RequestBody LitemallAddress address) {
		if (userId == null) {
			return ResponseUtil.unlogin();
		}
		Object error = validate(address);
		if (error != null) {
			return error;
		}

		if (address.getIsDefault()) {
			// 重置其他收货地址的默认选项
			addressService.resetDefault(userId);
		}

		if (address.getId() == null || address.getId().equals(0)) {
			address.setId(null);
			address.setUserId(userId);
			addressService.add(address);
		} else {
			address.setUserId(userId);
			if (addressService.update(address) == 0) {
				return ResponseUtil.updatedDataFailed();
			}
		}
		return ResponseUtil.ok(address.getId());
	}

	/**
	 * 删除收货地址
	 *
	 * @param userId  用户ID
	 * @return 删除操作结果
	 */
	@PostMapping("delete")
	@AuthInterceptor(needAuthTokenVerify = true)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "int"),
			@ApiImplicitParam(paramType="query", name="id" ,value = "收货地址id", required = true, dataType = "int")
	})
	@ApiOperation(value="删除收货地址", notes="删除收货地址(登入验证)")
	public Object delete(@LoginUser Integer userId,  Integer id) {
		if (userId == null) {
			return ResponseUtil.unlogin();
		}
		if (id == null) {
			return ResponseUtil.badArgument();
		}

		addressService.delete(id);
		return ResponseUtil.ok();
	}
}