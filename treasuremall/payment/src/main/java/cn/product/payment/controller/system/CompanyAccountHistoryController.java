/**
 * 
 */
package cn.product.payment.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.BaseController;
import cn.product.payment.controller.base.Result;
import cn.product.payment.controller.base.ResultManager;
import cn.product.payment.entity.Channel;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.CompanyAccountHistory;
import cn.product.payment.service.ChannelAccountService;
import cn.product.payment.service.ChannelService;
import cn.product.payment.service.CompanyAccountHistoryService;
import cn.product.payment.service.CompanyService;
import cn.product.payment.vo.system.CompanyAccountHistoryVO;


/**
 * 渠道账户
 */

@Controller
@RequestMapping(value = "/system/companyAccountHistory")
public class CompanyAccountHistoryController extends BaseController {

	@Autowired
	private CompanyAccountHistoryService companyAccountHistoryService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private ChannelAccountService channelAccountService;
	
	@Autowired
	private CompanyService companyService;
	
	/**
	 * 根据条件查询
	 * @param channel
	 * @param channelAccount
	 * @param company
	 * @param companyChannel
	 * @param orderNum
	 * @param companyOrderNum
	 * @param type
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "channel", message="渠道", type = DataType.STRING)
	@ActionParam(key = "channelAccount", message="渠道账户", type = DataType.STRING)
	@ActionParam(key = "company", message="商户", type = DataType.STRING)
	@ActionParam(key = "companyChannel", message="商户渠道", type = DataType.STRING)
	@ActionParam(key = "orderNum", message="订单号", type = DataType.STRING)
	@ActionParam(key = "companyOrderNum", message="商户订单号", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String channel, String channelAccount, String company, String companyChannel, String orderNum, String companyOrderNum, 
			String type, String starttime, String endtime, Integer pageNum, Integer pageSize, String sort) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("channel", channel);
		searchMap.put("channelAccount", channelAccount);
		searchMap.put("company", company);
		searchMap.put("companyChannel", companyChannel);
		searchMap.put("orderNum", orderNum);
		searchMap.put("companyOrderNum", companyOrderNum);
		searchMap.put("type", type);
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalResultCount = companyAccountHistoryService.getCountByParams(searchMap);
		List<CompanyAccountHistory> list = companyAccountHistoryService.getListByParams(searchMap);
		
		List<CompanyAccountHistoryVO> voList = new ArrayList<CompanyAccountHistoryVO>();
		for(CompanyAccountHistory cah : list){
			CompanyAccountHistoryVO vo = new CompanyAccountHistoryVO(cah);
			
			Channel ch = this.channelService.get(cah.getChannel());
			if(ch != null){
				vo.setChannelName(ch.getName());
			}
			ChannelAccount cha = this.channelAccountService.get(cah.getChannelAccount());
			if(cha != null){
				vo.setChannelAccountName(cha.getName());
			}
			Company c = this.companyService.get(cah.getCompany());
			if(c != null){
				vo.setCompanyName(c.getName());
			}
			
			voList.add(vo);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		CompanyAccountHistory cah = companyAccountHistoryService.get(uuid);
		if (cah == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		CompanyAccountHistoryVO vo = new CompanyAccountHistoryVO(cah);
		
		Channel ch = this.channelService.get(cah.getChannel());
		if(ch != null){
			vo.setChannelName(ch.getName());
		}
		ChannelAccount cha = this.channelAccountService.get(cah.getChannelAccount());
		if(cha != null){
			vo.setChannelAccountName(cha.getName());
		}
		Company c = this.companyService.get(cah.getCompany());
		if(c != null){
			vo.setCompanyName(c.getName());
		}
		return ResultManager.createDataResult(vo);
	}
}
