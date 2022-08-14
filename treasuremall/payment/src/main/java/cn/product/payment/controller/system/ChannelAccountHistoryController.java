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
import cn.product.payment.entity.ChannelAccountHistory;
import cn.product.payment.entity.Company;
import cn.product.payment.service.ChannelAccountHistoryService;
import cn.product.payment.service.ChannelAccountService;
import cn.product.payment.service.ChannelService;
import cn.product.payment.service.CompanyService;
import cn.product.payment.vo.system.ChannelAccountHistoryVO;


/**
 * 渠道账户
 */

@Controller
@RequestMapping(value = "/system/channelAccountHistory")
public class ChannelAccountHistoryController extends BaseController {

	@Autowired
	private ChannelAccountHistoryService channelAccountHistoryService;
	
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
	 * @param orderNum
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
	@ActionParam(key = "orderNum", message="订单号", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String channel, String channelAccount, String company, String orderNum, String type, 
			String starttime, String endtime, Integer pageNum, Integer pageSize, String sort) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("channel", channel);
		searchMap.put("channelAccount", channelAccount);
		searchMap.put("company", company);
		searchMap.put("orderNum", orderNum);
		searchMap.put("type", type);
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalResultCount = channelAccountHistoryService.getCountByParams(searchMap);
		List<ChannelAccountHistory> list = channelAccountHistoryService.getListByParams(searchMap);
		
		List<ChannelAccountHistoryVO> voList = new ArrayList<ChannelAccountHistoryVO>();
		for(ChannelAccountHistory cah : list){
			ChannelAccountHistoryVO vo = new ChannelAccountHistoryVO(cah);
			
			Channel ch = this.channelService.get(cah.getChannel());
			if(ch != null){
				vo.setChannelName(ch.getName());
			}
			ChannelAccount ca = this.channelAccountService.get(cah.getChannelAccount());
			if(ca != null){
				vo.setChannelName(ca.getName());
			}
			Company c = this.companyService.get(cah.getCompany());
			if(c != null){
				vo.setCompany(c.getName());
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
		ChannelAccountHistory cah = channelAccountHistoryService.get(uuid);
		if (cah == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		ChannelAccountHistoryVO vo = new ChannelAccountHistoryVO(cah);
		
		Channel ch = this.channelService.get(cah.getChannel());
		if(ch != null){
			vo.setChannelName(ch.getName());
		}
		ChannelAccount ca = this.channelAccountService.get(cah.getChannelAccount());
		if(ca != null){
			vo.setChannelName(ca.getName());
		}
		Company c = this.companyService.get(cah.getCompany());
		if(c != null){
			vo.setCompany(c.getName());
		}
		return ResultManager.createDataResult(vo);
	}
}
