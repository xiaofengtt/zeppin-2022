/**
 * 
 */
package cn.product.payment.service.system.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.AdminDao;
import cn.product.payment.dao.ChannelAccountDao;
import cn.product.payment.dao.ChannelDao;
import cn.product.payment.dao.CompanyChannelAccountDao;
import cn.product.payment.dao.CompanyChannelDao;
import cn.product.payment.dao.CompanyDao;
import cn.product.payment.entity.Channel;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.CompanyChannel;
import cn.product.payment.entity.CompanyChannel.CompanyChannelStatus;
import cn.product.payment.entity.CompanyChannelAccount;
import cn.product.payment.service.system.SystemCompanyChannelService;
import cn.product.payment.vo.system.CompanyChannelVO;

/**
 * 商户渠道开通
 */
@Service("systemCompanyChannelService")
public class SystemCompanyChannelServiceImpl implements SystemCompanyChannelService {

	@Autowired
	private CompanyChannelDao companyChannelDao;
	
	@Autowired
	private CompanyChannelAccountDao companyChannelAccountDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private ChannelAccountDao channelAccountDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String company = paramsMap.get("company") == null ? null : paramsMap.get("company").toString();
		String channel = paramsMap.get("channel") == null ? null : paramsMap.get("channel").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		String type = paramsMap.get("type") == null ? null : paramsMap.get("type").toString();
		
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
		
		Integer totalCount = companyChannelDao.getCountByParams(searchMap);
		List<CompanyChannel> list = companyChannelDao.getListByParams(searchMap);
		
		List<CompanyChannelVO> voList = new ArrayList<CompanyChannelVO>();
		for(CompanyChannel cc : list){
			CompanyChannelVO vo = new CompanyChannelVO(cc);
			
			//商户
			Company c = this.companyDao.get(cc.getCompany());
			if(c != null){
				vo.setCompanyName(c.getName());
			}
			
			//渠道
			Channel ch = this.channelDao.get(cc.getChannel());
			if(ch != null){
				vo.setChannelName(ch.getName());
			}
			voList.add(vo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	
		CompanyChannel cc = companyChannelDao.get(uuid);
		if (cc == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		CompanyChannelVO vo = new CompanyChannelVO(cc);
		
		//商户
		Company company = this.companyDao.get(cc.getCompany());
		if(company != null){
			vo.setCompanyName(company.getName());
		}
		
		//渠道
		Channel channel = this.channelDao.get(cc.getChannel());
		if(channel != null){
			vo.setChannelName(channel.getName());
		}
		
		//取商户绑定的渠道
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("companyChannel", uuid);
		List<CompanyChannelAccount> ccaList = this.companyChannelAccountDao.getListByParams(searchMap);
		List<String> caList = new ArrayList<String>();
		for(CompanyChannelAccount cca : ccaList){
			caList.add(cca.getChannelAccount());
		}
		vo.setCompanyChannelAccountList(caList);
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		CompanyChannel companyChannel = (CompanyChannel) paramsMap.get("companyChannel");
		String[] channelAccounts = {};
		
		//取填写的商户渠道数据
		if(paramsMap.get("channelAccount") != null){
			channelAccounts = (String[])paramsMap.get("channelAccount");
		}
		
		if(!CompanyChannelStatus.NORMAL.equals(companyChannel.getStatus()) && !CompanyChannelStatus.DISABLE.equals(companyChannel.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("状态错误！");
			return;
		}
		
		if(companyChannel.getMax().compareTo(companyChannel.getMin()) < 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("单次额度设置错误！");
			return;
		}
		
		if(companyChannel.getPoundage() == null && companyChannel.getPoundageRate() == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("手续费不能为空！");
			return;
		}
		
		if(companyChannel.getPoundage() != null && companyChannel.getPoundageRate() != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("手续费暂时只能选择一种方式！");
			return;
		}
		
		Company company = this.companyDao.get(companyChannel.getCompany());
		if(company == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商户不存在！");
			return;
		}
		
		Channel channel = this.channelDao.get(companyChannel.getChannel());
		if(channel == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道不存在！");
			return;
		}
		
		companyChannel.setUuid(UUID.randomUUID().toString());
		companyChannel.setType(channel.getType());
		companyChannel.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		//处理绑定的渠道账户
		List<CompanyChannelAccount> ccaList = new ArrayList<CompanyChannelAccount>();
		//循环绑定的渠道账户
		for(String channelAccount : channelAccounts){
			ChannelAccount ca = this.channelAccountDao.get(channelAccount);
			if(ca == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("渠道账户不存在！");
				return;
			}
			
			//创建新绑定关系
			CompanyChannelAccount cca = new CompanyChannelAccount();
			cca.setUuid(UUID.randomUUID().toString());
			cca.setCompany(company.getUuid());
			cca.setChannel(channel.getUuid());
			cca.setChannelAccount(ca.getUuid());
			cca.setCompanyChannel(companyChannel.getUuid());
			ccaList.add(cca);
		}
		
		//入库商户渠道 和 商户渠道绑定的渠道账户关系
		this.companyChannelDao.insertCompanyChannel(companyChannel, ccaList);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("创建成功！");
	}
	
	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		CompanyChannel companyChannel = (CompanyChannel) paramsMap.get("companyChannel");
		String[] channelAccounts = {};
		
		//取填写的商户渠道数据
		if(paramsMap.get("channelAccount") != null){
			channelAccounts = (String[])paramsMap.get("channelAccount");
		}
		
		if(!CompanyChannelStatus.NORMAL.equals(companyChannel.getStatus()) && !CompanyChannelStatus.DISABLE.equals(companyChannel.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("状态错误！");
			return;
		}
		
		if(companyChannel.getMax().compareTo(companyChannel.getMin()) < 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("单次额度设置错误！");
			return;
		}
		
		if(companyChannel.getPoundage() == null && companyChannel.getPoundageRate() == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("手续费不能为空！");
			return;
		}
		
		if(companyChannel.getPoundage() != null && companyChannel.getPoundageRate() != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("手续费暂时只能选择一种方式！");
			return;
		}
		
		CompanyChannel cc = companyChannelDao.get(companyChannel.getUuid());
		if (cc == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		//处理绑定的渠道账户
		List<CompanyChannelAccount> ccaList = new ArrayList<CompanyChannelAccount>();
		//循环绑定的渠道账户
		for(String channelAccount : channelAccounts){
			ChannelAccount ca = this.channelAccountDao.get(channelAccount);
			if(ca == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("渠道账户不存在！");
				return;
			}
			
			//创建新绑定关系
			CompanyChannelAccount cca = new CompanyChannelAccount();
			cca.setUuid(UUID.randomUUID().toString());
			cca.setCompany(companyChannel.getCompany());
			cca.setChannel(companyChannel.getChannel());
			cca.setChannelAccount(ca.getUuid());
			cca.setCompanyChannel(companyChannel.getUuid());
			ccaList.add(cca);
		}
		
		cc.setMax(companyChannel.getMax());
		cc.setMin(companyChannel.getMin());
		cc.setPoundage(companyChannel.getPoundage());
		cc.setPoundageRate(companyChannel.getPoundageRate());
		cc.setStatus(companyChannel.getStatus());
		
		//更新商户渠道 和 商户渠道绑定的渠道账户关系
		this.companyChannelDao.updateCompanyChannel(cc, ccaList);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}
	
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String status = paramsMap.get("status").toString();
    	
		if(!CompanyChannelStatus.NORMAL.equals(status) && !CompanyChannelStatus.DISABLE.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("状态错误！");
			return;
		}
		
		CompanyChannel cc = companyChannelDao.get(uuid);
		if (cc == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		cc.setStatus(status);
		this.companyChannelDao.update(cc);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
}
