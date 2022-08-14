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
import cn.product.score.entity.GuessingMatch;

@Controller
@RequestMapping(value = "/back/guessingMatch")
public class GuessingMatchController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4663164718297034999L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("guessingMatchService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String status, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("guessingMatchService", "list");
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "infoMatch", message="赛事", type = DataType.STRING, required = true)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.NUMBER)
	@ResponseBody
    public Result add(GuessingMatch guessingMatch){
		
		InputParams params = new InputParams("guessingMatchService", "add");
		params.addParams("guessingMatch", null, guessingMatch);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
    }
    
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid){
		InputParams params = new InputParams("guessingMatchService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/finish",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "rightArray", message="正确选项", type = DataType.STRING_ARRAY, required = true)
	@ResponseBody
	public Result finish(String uuid, String[] rightArray){
		InputParams params = new InputParams("guessingMatchService", "finish");
		params.addParams("uuid", null, uuid);
		params.addParams("rightArray", null, rightArray);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	/**
	 * 获取分状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		InputParams params = new InputParams("guessingMatchService", "statusList");
		return this.execute(params);
	}
}
