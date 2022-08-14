package cn.zeppin.product.score.controller.back;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.score.controller.base.ActionParam;
import cn.zeppin.product.score.controller.base.ActionParam.DataType;
import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.FrontUser.FrontUserStatus;
import cn.zeppin.product.score.entity.FrontUserAccount;
import cn.zeppin.product.score.service.AdminService;
import cn.zeppin.product.score.service.FrontUserAccountService;
import cn.zeppin.product.score.service.FrontUserService;
import cn.zeppin.product.score.vo.back.FrontUserVO;

@Controller
@RequestMapping(value = "/back/user")
public class UserController extends BaseController{
	
	@Autowired
    private FrontUserService frontUserService;
	
	@Autowired
    private FrontUserAccountService frontUserAccountService;
	
	@Autowired
    private AdminService adminService;
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		FrontUser fu = frontUserService.get(uuid);
		if(fu == null || FrontUserStatus.DELETE.equals(fu.getStatus())){
			return ResultManager.createFailResult("用户不存在");
		}
		
		FrontUserAccount fua = this.frontUserAccountService.getByFrontUser(fu.getUuid());
		FrontUserVO fuvo = new FrontUserVO(fu, fua);
		
		return ResultManager.createDataResult(fuvo);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "realname", message="真实姓名", type = DataType.STRING)
	@ActionParam(key = "mobile", message="手机号码", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String realname, String mobile, String status, Integer pageNum, Integer pageSize, String sort){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("realname", realname);
		params.put("mobile", mobile);
		params.put("status", status);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("sort", sort);
		
		Integer totalCount = frontUserService.getCountByParams(params);
		List<FrontUser> fuList = frontUserService.getListByParams(params);
		
		List<FrontUserVO> voList = new ArrayList<FrontUserVO>();
		for(FrontUser fu : fuList){
			FrontUserAccount fua = this.frontUserAccountService.getByFrontUser(fu.getUuid());
			FrontUserVO fuvo = new FrontUserVO(fu, fua);
			voList.add(fuvo);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
	}
	
	@RequestMapping(value="/changeStatus",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status){
		FrontUser fu = frontUserService.get(uuid);
		if(fu == null || FrontUserStatus.DELETE.equals(fu.getStatus())){
			return ResultManager.createFailResult("用户不存在");
		}
		
		fu.setStatus(status);
		
		frontUserService.update(fu);
		return ResultManager.createSuccessResult("修改成功！");
	}
}
