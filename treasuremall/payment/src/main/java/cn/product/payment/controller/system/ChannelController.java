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
import cn.product.payment.entity.Channel.ChannelStatus;
import cn.product.payment.service.AdminService;
import cn.product.payment.service.ChannelService;
import cn.product.payment.vo.system.ChannelVO;


/**
 * 用户充值
 */

@Controller
@RequestMapping(value = "/system/channel")
public class ChannelController extends BaseController {

	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private AdminService adminService;
	
	/**
	 * 根据条件查询
	 * @param type
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String status, String type, Integer pageNum, Integer pageSize, String sort) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("type", type);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalResultCount = channelService.getCountByParams(searchMap);
		List<Channel> list = channelService.getListByParams(searchMap);
		
		List<ChannelVO> voList = new ArrayList<ChannelVO>();
		for(Channel channel : list){
			ChannelVO vo = new ChannelVO(channel);
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
		Channel channel = channelService.get(uuid);
		if (channel == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		
		ChannelVO vo = new ChannelVO(channel);
		return ResultManager.createDataResult(vo);
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
		
		Channel channel = channelService.get(uuid);
		if (channel == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		
		channel.setStatus(status);
		this.channelService.update(channel);
		return ResultManager.createSuccessResult("操作成功！");
	}
}
