package cn.product.score.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.score.api.base.ActionParam;
import cn.product.score.api.base.ActionParam.DataType;
import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.Result;
import cn.product.score.controller.BaseController;
import cn.product.score.entity.GuessingMatchType;

@Controller
@RequestMapping(value = "/back/guessingMatchOdds")
public class GuessingMatchOddsController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4835885341287493102L;

	@RequestMapping(value="/controlList",method=RequestMethod.GET)
	@ResponseBody
	public Result controlList(){
		InputParams params = new InputParams("guessingMatchOddsService", "controlList");
		return this.execute(params);
	}
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "guessingMatch", message="竞猜比赛", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String guessingMatch){
		InputParams params = new InputParams("capitalAccountService", "get");
		params.addParams("guessingMatch", null, guessingMatch);
		return this.execute(params);
	}
	
	@RequestMapping(value="/getBetSum",method=RequestMethod.GET)
	@ActionParam(key = "guessingMatch", message="竞猜比赛", type = DataType.STRING)
	@ResponseBody
	public Result getBetSum(String guessingMatch){
		InputParams params = new InputParams("capitalAccountService", "getBetSum");
		params.addParams("guessingMatch", null, guessingMatch);
		return this.execute(params);
	}
	
	@RequestMapping(value="/addType",method=RequestMethod.POST)
	@ActionParam(key = "guessingMatch", message="竞猜赛事", type = DataType.STRING, required = true)
	@ActionParam(key = "oddsType", message="赔率类型", type = DataType.STRING, required = true)
	@ActionParam(key = "type", message="竞猜类型", type = DataType.STRING, required = true)
	@ActionParam(key = "maxMoney", message="最大投注限额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "flagSingle", message="是否可以单投", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "betEndtime", message="截止时间", type = DataType.STRING, required = true)
	@ActionParam(key = "odds", message="赔率", type = DataType.STRING_ARRAY, required = true)
	@ResponseBody
    public Result addType(GuessingMatchType guessingMatchType, String betEndtime, String[] odds){
		
		InputParams params = new InputParams("capitalAccountService", "addType");
		params.addParams("guessingMatchType", null, guessingMatchType);
		params.addParams("betEndtime", null, betEndtime);
		params.addParams("odds", null, odds);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
    }
    
	@RequestMapping(value="/editType",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "oddsType", message="赔率类型", type = DataType.STRING, required = true)
	@ActionParam(key = "type", message="竞猜类型", type = DataType.STRING, required = true)
	@ActionParam(key = "maxMoney", message="最大投注限额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "flagSingle", message="是否可以单投", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "betEndtime", message="截止时间", type = DataType.STRING, required = true)
	@ActionParam(key = "odds", message="赔率", type = DataType.STRING_ARRAY, required = true)
	@ResponseBody
	public Result editType(GuessingMatchType guessingMatchType, String betEndtime, String[] odds){

		InputParams params = new InputParams("capitalAccountService", "editType");
		params.addParams("guessingMatchType", null, guessingMatchType);
		params.addParams("betEndtime", null, betEndtime);
		params.addParams("odds", null, odds);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	

	@RequestMapping(value="/publishType",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result publishType(String uuid){

		InputParams params = new InputParams("capitalAccountService", "publishType");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/deleteType",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result deleteType(String uuid){

		InputParams params = new InputParams("capitalAccountService", "deleteType");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/historyList",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result historyList(String uuid){

		InputParams params = new InputParams("capitalAccountService", "historyList");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ActionParam(key = "datas", message="数据", type = DataType.STRING_ARRAY, required = true)
	@ResponseBody
	public Result update(String[] datas){
		InputParams params = new InputParams("capitalAccountService", "update");
		params.addParams("datas", null, datas);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	
}
