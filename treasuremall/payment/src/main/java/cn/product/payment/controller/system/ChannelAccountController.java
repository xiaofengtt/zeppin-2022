/**
 * 
 */
package cn.product.payment.controller.system;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import cn.product.payment.entity.Channel.ChannelStatus;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.ChannelAccount.ChannelAccountStatus;
import cn.product.payment.service.AdminService;
import cn.product.payment.service.ChannelAccountService;
import cn.product.payment.service.ChannelService;
import cn.product.payment.util.JSONUtils;
import cn.product.payment.vo.system.ChannelAccountVO;


/**
 * 渠道账户
 */

@Controller
@RequestMapping(value = "/system/channelAccount")
public class ChannelAccountController extends BaseController {

	@Autowired
	private ChannelAccountService channelAccountService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private AdminService adminService;
	
	/**
	 * 根据条件查询
	 * @param channel
	 * @param name
	 * @param type
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "channel", message="渠道", type = DataType.STRING)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String channel, String name, String status, String type, Integer pageNum, Integer pageSize, String sort) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("channel", channel);
		searchMap.put("name", name);
		searchMap.put("type", type);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalResultCount = channelAccountService.getCountByParams(searchMap);
		List<ChannelAccount> list = channelAccountService.getListByParams(searchMap);
		
		List<ChannelAccountVO> voList = new ArrayList<ChannelAccountVO>();
		for(ChannelAccount ca : list){
			ChannelAccountVO vo = new ChannelAccountVO(ca);
			
			Channel ch = this.channelService.get(ca.getChannel());
			if(ch != null){
				vo.setChannelName(ch.getName());
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
		ChannelAccount ca = channelAccountService.get(uuid);
		if (ca == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		ChannelAccountVO vo = new ChannelAccountVO(ca);
		
		Channel channel = this.channelService.get(ca.getChannel());
		if(channel != null){
			vo.setChannelName(channel.getName());
		}
		return ResultManager.createDataResult(vo);
	}
	
	/**
	 * 创建
	 * @param channel
	 * @param name
	 * @param accountNum
	 * @param data
	 * @param poundage
	 * @param poundageRate
	 * @param max
	 * @param min
	 * @param dailyMax
	 * @param totalMax
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "channel", message="渠道", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="账户名称", type = DataType.STRING, required = true)
	@ActionParam(key = "accountNum", message="账户号码", type = DataType.STRING, required = true)
	@ActionParam(key = "data", message="账户数据", type = DataType.STRING)
	@ActionParam(key = "poundage", message="手续费（固定）", type = DataType.NUMBER)
	@ActionParam(key = "poundageRate", message="手续费（比率）", type = DataType.NUMBER)
	@ActionParam(key = "max", message="单次最高额度", type = DataType.NUMBER, required = true)
	@ActionParam(key = "min", message="单次最低额度", type = DataType.NUMBER, required = true)
	@ActionParam(key = "dailyMax", message="每日限额", type = DataType.NUMBER, required = true)
	@ActionParam(key = "totalMax", message="总限额", type = DataType.NUMBER, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(ChannelAccount channelAccount) {
		if(!ChannelAccountStatus.NORMAL.equals(channelAccount.getStatus()) && !ChannelAccountStatus.DISABLE.equals(channelAccount.getStatus())){
			return ResultManager.createFailResult("状态错误！");
		}
		
		if(channelAccount.getPoundage() == null && channelAccount.getPoundageRate() == null){
			return ResultManager.createFailResult("手续费不能为空！");
		}
		
		if(channelAccount.getPoundage() != null && channelAccount.getPoundageRate() != null){
			return ResultManager.createFailResult("手续费暂时只能选择一种方式！");
		}
		
		if(channelAccount.getMax().compareTo(channelAccount.getMin()) < 0){
			return ResultManager.createFailResult("单次额度设置错误！");
		}
		
		Channel channel = this.channelService.get(channelAccount.getChannel());
		if(channel == null){
			return ResultManager.createFailResult("渠道不存在！");
		}
		
		Map<String, Object> baseMap = JSONUtils.json2map(channel.getData());
		Map<String, Object> dataMap = JSONUtils.json2map(channelAccount.getData());
		
		for(String key : baseMap.keySet()){
			if(dataMap.get(key) == null){
				return ResultManager.createFailResult("未填写"+baseMap.get(key)+"！");
			}
		}
		
		channelAccount.setUuid(UUID.randomUUID().toString());
		channelAccount.setType(channel.getType());
		channelAccount.setBalance(BigDecimal.ZERO);
		channelAccount.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.channelAccountService.insert(channelAccount);
		return ResultManager.createSuccessResult("创建成功！");
	}
	
	/**
	 * 修改
	 * @param uuid
	 * @param name
	 * @param accountNum
	 * @param data
	 * @param poundage
	 * @param poundageRate
	 * @param max
	 * @param min
	 * @param dailyMax
	 * @param totalMax
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="账户名称", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="账户名称", type = DataType.STRING, required = true)
	@ActionParam(key = "accountNum", message="账户号码", type = DataType.STRING, required = true)
	@ActionParam(key = "data", message="账户数据", type = DataType.STRING)
	@ActionParam(key = "poundage", message="手续费（固定）", type = DataType.NUMBER)
	@ActionParam(key = "poundageRate", message="手续费（比率）", type = DataType.NUMBER)
	@ActionParam(key = "max", message="单次最高额度", type = DataType.NUMBER, required = true)
	@ActionParam(key = "min", message="单次最低额度", type = DataType.NUMBER, required = true)
	@ActionParam(key = "dailyMax", message="每日限额", type = DataType.NUMBER, required = true)
	@ActionParam(key = "totalMax", message="总限额", type = DataType.NUMBER, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(ChannelAccount channelAccount) {
		if(!ChannelAccountStatus.NORMAL.equals(channelAccount.getStatus()) && !ChannelAccountStatus.DISABLE.equals(channelAccount.getStatus())){
			return ResultManager.createFailResult("状态错误！");
		}
		
		if(channelAccount.getPoundage() == null && channelAccount.getPoundageRate() == null){
			return ResultManager.createFailResult("手续费不能为空！");
		}
		
		if(channelAccount.getPoundage() != null && channelAccount.getPoundageRate() != null){
			return ResultManager.createFailResult("手续费暂时只能选择一种方式！");
		}
		
		ChannelAccount ca = channelAccountService.get(channelAccount.getUuid());
		if (ca == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		
		Channel channel = this.channelService.get(channelAccount.getChannel());
		Map<String, Object> baseMap = JSONUtils.json2map(channel.getData());
		Map<String, Object> dataMap = JSONUtils.json2map(channelAccount.getData());
		
		for(String key : baseMap.keySet()){
			if(dataMap.get(key) == null){
				return ResultManager.createFailResult("未填写"+baseMap.get(key)+"！");
			}
		}
		
		ca.setName(channelAccount.getName());
		ca.setAccountNum(channelAccount.getAccountNum());
		ca.setData(channelAccount.getData());
		ca.setPoundage(channelAccount.getPoundage());
		ca.setPoundageRate(channelAccount.getPoundageRate());
		ca.setMin(channelAccount.getMin());
		ca.setMax(channelAccount.getMax());
		ca.setDailyMax(channelAccount.getDailyMax());
		ca.setTotalMax(channelAccount.getTotalMax());
		ca.setStatus(channelAccount.getStatus());
		this.channelAccountService.update(channelAccount);
		return ResultManager.createSuccessResult("修改成功！");
	}
	
	/**
	 * 变更状态
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status) {
		if(!ChannelStatus.NORMAL.equals(status) && !ChannelStatus.DISABLE.equals(status)){
			return ResultManager.createFailResult("状态错误！");
		}
		
		ChannelAccount ca = channelAccountService.get(uuid);
		if (ca == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		
		ca.setStatus(status);
		this.channelAccountService.update(ca);
		return ResultManager.createSuccessResult("操作成功！");
	}
}
