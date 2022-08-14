package cn.zeppin.product.ntb.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IInvestorInformationService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.InvestorInformation;
import cn.zeppin.product.ntb.core.entity.InvestorInformation.InvestorInformationStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.web.vo.InvestorInformationVO;

@Controller
@RequestMapping(value = "/web/info")
public class UserInformationController extends BaseController{
	
	@Autowired
	private IInvestorService investorService;
	
	@Autowired
	private IInvestorInformationService investorInformationService;
	
	@Autowired
	private IResourceService resourceService;
	
	/**
	 * 获取用户收到的消息列表
	 * @param uuid
	 * @param stage
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result getList(String uuid, Integer pageNum, Integer pageSize, String name, String sorts){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? Integer.MAX_VALUE : pageSize;
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("investor", uuid);
		inputParams.put("name", name);
		Integer totalCount = this.investorInformationService.getCount(inputParams);
		List<Entity> list = this.investorInformationService.getListForPage(inputParams, pageNum, pageSize, sorts, InvestorInformation.class);
		List<InvestorInformationVO> listVO = new ArrayList<InvestorInformationVO>();
		if( list != null && !list.isEmpty()){
			for(Entity entity : list){
				InvestorInformation ii = (InvestorInformation)entity;
				InvestorInformationVO iivo = new InvestorInformationVO(ii);
				listVO.add(iivo);
			}
		}
		
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalCount);
	}
	
	/**
	 * 获取用户收到的消息内容
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ActionParam(key = "infoUuid", type = DataType.STRING, message="消息编号", required = true)
	@ResponseBody
	public Result get(String uuid, String infoUuid){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		InvestorInformation ii = this.investorInformationService.get(infoUuid);
		if( ii != null){
			if(!ii.getInvestor().equals(uuid)){
				return ResultManager.createFailResult("信息不存在");
			}
			ii.setStatus(InvestorInformationStatus.NORMAL);
			InvestorInformationVO iivo = new InvestorInformationVO(ii);
			this.investorInformationService.update(ii);
			return ResultManager.createDataResult(iivo);
		} else {
			return ResultManager.createFailResult("信息不存在");
		}
	}
	
	/**
	 * 全部表为已读
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/read", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ResponseBody
	public Result read(String uuid){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		try {
			this.investorInformationService.updateBatchRead(uuid);
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("处理异常，请重新操作！");
		}
		return ResultManager.createSuccessResult("成功！");
	}
}
