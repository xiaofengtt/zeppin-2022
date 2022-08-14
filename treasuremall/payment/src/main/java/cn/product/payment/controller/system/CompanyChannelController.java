/**
 * 
 */
package cn.product.payment.controller.system;

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
import cn.product.payment.entity.Admin;
import cn.product.payment.entity.Channel;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.CompanyChannel;
import cn.product.payment.entity.CompanyChannel.CompanyChannelStatus;
import cn.product.payment.service.AdminService;
import cn.product.payment.service.ChannelService;
import cn.product.payment.service.CompanyChannelService;
import cn.product.payment.service.CompanyService;
import cn.product.payment.vo.system.CompanyChannelVO;


/**
 * 商户渠道
 */

@Controller
@RequestMapping(value = "/system/companyChannel")
public class CompanyChannelController extends BaseController {

	@Autowired
	private CompanyChannelService companyChannelService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private AdminService adminService;
	
	/**
	 * 根据条件查询
	 * @param company
	 * @param channel
	 * @param type
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "company", message="商户", type = DataType.STRING)
	@ActionParam(key = "channel", message="渠道", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String company, String channel, String status, String type, Integer pageNum, Integer pageSize, String sort) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", company);
		searchMap.put("channel", channel);
		searchMap.put("type", type);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalResultCount = companyChannelService.getCountByParams(searchMap);
		List<CompanyChannel> list = companyChannelService.getListByParams(searchMap);
		
		List<CompanyChannelVO> voList = new ArrayList<CompanyChannelVO>();
		for(CompanyChannel cc : list){
			CompanyChannelVO vo = new CompanyChannelVO(cc);
			
			Company c = this.companyService.get(cc.getCompany());
			if(c != null){
				vo.setCompanyName(c.getName());
			}
			
			Channel ch = this.channelService.get(cc.getChannel());
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
		CompanyChannel cc = companyChannelService.get(uuid);
		if (cc == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		
		CompanyChannelVO vo = new CompanyChannelVO(cc);
		
		Company company = this.companyService.get(cc.getCompany());
		if(company != null){
			vo.setCompanyName(company.getName());
		}
		
		Channel channel = this.channelService.get(cc.getChannel());
		if(channel != null){
			vo.setChannelName(channel.getName());
		}
		
		return ResultManager.createDataResult(vo);
	}
	
	/**
	 * 创建
	 * @param uuid
	 * @param company
	 * @param channel
	 * @param max
	 * @param min
	 * @param poundage
	 * @param poundageRate
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "company", message="商户", type = DataType.STRING, required = true)
	@ActionParam(key = "channel", message="渠道", type = DataType.STRING, required = true)
	@ActionParam(key = "max", message="单次最高额度", type = DataType.NUMBER, required = true)
	@ActionParam(key = "min", message="单次最低额度", type = DataType.NUMBER, required = true)
	@ActionParam(key = "poundage", message="手续费（固定）", type = DataType.NUMBER)
	@ActionParam(key = "poundageRate", message="手续费（比率）", type = DataType.NUMBER)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(CompanyChannel companyChannel) {
		Admin admin = getSystemAdmin();
		
		if(!CompanyChannelStatus.NORMAL.equals(companyChannel.getStatus()) && !CompanyChannelStatus.DISABLE.equals(companyChannel.getStatus())){
			return ResultManager.createFailResult("状态错误！");
		}
		
		if(companyChannel.getMax().compareTo(companyChannel.getMin()) < 0){
			return ResultManager.createFailResult("单次额度设置错误！");
		}
		
		if(companyChannel.getPoundage() == null && companyChannel.getPoundageRate() == null){
			return ResultManager.createFailResult("手续费不能为空！");
		}
		
		if(companyChannel.getPoundage() != null && companyChannel.getPoundageRate() != null){
			return ResultManager.createFailResult("手续费暂时只能选择一种方式！");
		}
		
		Company company = this.companyService.get(companyChannel.getCompany());
		if(company == null){
			return ResultManager.createFailResult("商户不存在！");
		}
		
		Channel channel = this.channelService.get(companyChannel.getChannel());
		if(channel == null){
			return ResultManager.createFailResult("渠道不存在！");
		}
		
		companyChannel.setUuid(UUID.randomUUID().toString());
		companyChannel.setType(channel.getType());
		companyChannel.setCreator(admin.getUuid());
		companyChannel.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		this.companyChannelService.insert(companyChannel);
		return ResultManager.createSuccessResult("创建成功！");
	}
	
	/**
	 * 修改
	 * @param uuid
	 * @param max
	 * @param min
	 * @param poundage
	 * @param poundageRate
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "max", message="单次最高额度", type = DataType.NUMBER, required = true)
	@ActionParam(key = "min", message="单次最低额度", type = DataType.NUMBER, required = true)
	@ActionParam(key = "poundage", message="手续费（固定）", type = DataType.NUMBER)
	@ActionParam(key = "poundageRate", message="手续费（比率）", type = DataType.NUMBER)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(CompanyChannel companyChannel) {
		if(!CompanyChannelStatus.NORMAL.equals(companyChannel.getStatus()) && !CompanyChannelStatus.DISABLE.equals(companyChannel.getStatus())){
			return ResultManager.createFailResult("状态错误！");
		}
		
		if(companyChannel.getMax().compareTo(companyChannel.getMin()) < 0){
			return ResultManager.createFailResult("单次额度设置错误！");
		}
		
		if(companyChannel.getPoundage() == null && companyChannel.getPoundageRate() == null){
			return ResultManager.createFailResult("手续费不能为空！");
		}
		
		if(companyChannel.getPoundage() != null && companyChannel.getPoundageRate() != null){
			return ResultManager.createFailResult("手续费暂时只能选择一种方式！");
		}
		CompanyChannel cc = companyChannelService.get(companyChannel.getUuid());
		if (cc == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		
		cc.setMax(companyChannel.getMax());
		cc.setMin(companyChannel.getMin());
		cc.setPoundage(companyChannel.getPoundage());
		cc.setPoundageRate(companyChannel.getPoundageRate());
		cc.setStatus(companyChannel.getStatus());
		
		this.companyChannelService.update(cc);
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
		if(!CompanyChannelStatus.NORMAL.equals(status) && !CompanyChannelStatus.DISABLE.equals(status)){
			return ResultManager.createFailResult("状态错误！");
		}
		
		CompanyChannel cc = companyChannelService.get(uuid);
		if (cc == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		
		cc.setStatus(status);
		this.companyChannelService.update(cc);
		return ResultManager.createSuccessResult("操作成功！");
	}
}
