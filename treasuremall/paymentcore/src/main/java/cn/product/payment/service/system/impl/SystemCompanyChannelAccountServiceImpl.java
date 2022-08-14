/**
 * 
 */
package cn.product.payment.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.AdminDao;
import cn.product.payment.dao.ChannelAccountDao;
import cn.product.payment.dao.ChannelDao;
import cn.product.payment.dao.CompanyChannelAccountDao;
import cn.product.payment.dao.CompanyDao;
import cn.product.payment.entity.Channel;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.CompanyChannelAccount;
import cn.product.payment.service.system.SystemCompanyChannelAccountService;
import cn.product.payment.vo.system.CompanyChannelAccountVO;

/**
 * 商户渠道账户绑定
 */
@Service("systemCompanyChannelAccountService")
public class SystemCompanyChannelAccountServiceImpl implements SystemCompanyChannelAccountService {

	@Autowired
	private CompanyChannelAccountDao companyChannelAccountDao;
	
	@Autowired
	private ChannelAccountDao channelAccountDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String companyChannel = paramsMap.get("companyChannel") == null ? null : paramsMap.get("companyChannel").toString();
		String channel = paramsMap.get("channel") == null ? null : paramsMap.get("channel").toString();
		String company = paramsMap.get("company") == null ? null : paramsMap.get("company").toString();
		String channelAccount = paramsMap.get("channelAccount") == null ? null : paramsMap.get("channelAccount").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", company);
		searchMap.put("channel", channel);
		searchMap.put("companyChannel", companyChannel);
		searchMap.put("channelAccount", channelAccount);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		Integer totalCount = companyChannelAccountDao.getCountByParams(searchMap);
		List<CompanyChannelAccount> list = companyChannelAccountDao.getListByParams(searchMap);
		
		List<CompanyChannelAccountVO> voList = new ArrayList<CompanyChannelAccountVO>();
		for(CompanyChannelAccount cc : list){
			CompanyChannelAccountVO vo = new CompanyChannelAccountVO(cc);
			
			//商户
			Company c = this.companyDao.get(cc.getCompany());
			if(c != null){
				vo.setCompanyName(c.getName());
			}
			
			//渠道账户
			ChannelAccount ca = this.channelAccountDao.get(cc.getChannelAccount());
			if(ca != null){
				vo.setChannelAccountName(ca.getName());
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
    	
		CompanyChannelAccount cc = companyChannelAccountDao.get(uuid);
		if (cc == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		CompanyChannelAccountVO vo = new CompanyChannelAccountVO(cc);
		
		//商户
		Company company = this.companyDao.get(cc.getCompany());
		if(company != null){
			vo.setCompanyName(company.getName());
		}
		
		//渠道账户
		ChannelAccount ca = this.channelAccountDao.get(cc.getChannelAccount());
		if(ca != null){
			vo.setChannelAccountName(ca.getName());
		}
		
		//渠道
		Channel channel = this.channelDao.get(cc.getChannel());
		if(channel != null){
			vo.setChannelName(channel.getName());
		}

		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
}
