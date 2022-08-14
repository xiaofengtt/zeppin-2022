package cn.product.worldmall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;
import cn.product.worldmall.entity.Voucher;
import cn.product.worldmall.controller.BaseController;

@Controller
@RequestMapping(value = "/back/voucher")
public class VoucherController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -302522100813056740L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("voucherService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "title", message="标题", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public Result list(String title, String status, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("voucherService", "list");
		params.addParams("title", null, title);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "title", message="标题", type = DataType.STRING, required = true)
	@ActionParam(key = "dAmount", message="金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "payMin", message="最低限制", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "goodsType", message="限制类型", type = DataType.STRING, maxLength = 150)
	@ActionParam(key = "goods", message="限制商品", type = DataType.STRING, maxLength = 400)
	@ActionParam(key = "discription", message="限制描述", type = DataType.STRING, maxLength = 200)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING, maxLength = 30)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING, maxLength = 30)
	@ResponseBody
    public Result add(Voucher voucher){
		InputParams params = new InputParams("voucherService", "add");
		params.addParams("voucher", null, voucher);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
    }
    
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "title", message="标题", type = DataType.STRING, required = true)
	@ActionParam(key = "dAmount", message="金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "payMin", message="最低限制", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "goodsType", message="限制类型", type = DataType.STRING, maxLength = 150)
	@ActionParam(key = "goods", message="限制商品", type = DataType.STRING, maxLength = 400)
	@ActionParam(key = "discription", message="限制描述", type = DataType.STRING, maxLength = 200)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING, maxLength = 30)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING, maxLength = 30)
	@ResponseBody
	public Result edit(Voucher voucher){
		InputParams params = new InputParams("voucherService", "edit");
		params.addParams("voucher", null, voucher);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/changeStatus",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status){
		InputParams params = new InputParams("voucherService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		return this.execute(params);
	}
}
