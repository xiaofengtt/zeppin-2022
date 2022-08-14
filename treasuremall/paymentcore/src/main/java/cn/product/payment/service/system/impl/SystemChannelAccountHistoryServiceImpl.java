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
import cn.product.payment.dao.ChannelAccountDao;
import cn.product.payment.dao.ChannelAccountHistoryDao;
import cn.product.payment.dao.ChannelDao;
import cn.product.payment.dao.CompanyDao;
import cn.product.payment.entity.Channel;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.ChannelAccountHistory;
import cn.product.payment.entity.Company;
import cn.product.payment.service.system.SystemChannelAccountHistoryService;
import cn.product.payment.vo.system.ChannelAccountHistoryVO;

/**
 * 渠道账户流水
 */
@Service("systemChannelAccountHistoryService")
public class SystemChannelAccountHistoryServiceImpl implements SystemChannelAccountHistoryService {

	@Autowired
	private ChannelAccountHistoryDao channelAccountHistoryDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private ChannelAccountDao channelAccountDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String channel = paramsMap.get("channel") == null ? null : paramsMap.get("channel").toString();
		String channelAccount = paramsMap.get("channelAccount") == null ? null : paramsMap.get("channelAccount").toString();
		String company = paramsMap.get("company") == null ? null : paramsMap.get("company").toString();
		String orderNum = paramsMap.get("orderNum") == null ? null : paramsMap.get("orderNum").toString();
		String type = paramsMap.get("type") == null ? null : paramsMap.get("type").toString();
		String starttime = paramsMap.get("starttime") == null ? null : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? null : paramsMap.get("endtime").toString();
		
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
		
		Integer totalCount = channelAccountHistoryDao.getCountByParams(searchMap);
		List<ChannelAccountHistory> list = channelAccountHistoryDao.getListByParams(searchMap);
		
		List<ChannelAccountHistoryVO> voList = new ArrayList<ChannelAccountHistoryVO>();
		for(ChannelAccountHistory cah : list){
			ChannelAccountHistoryVO vo = new ChannelAccountHistoryVO(cah);
			
			//渠道
			Channel ch = this.channelDao.get(cah.getChannel());
			if(ch != null){
				vo.setChannelName(ch.getName());
			}
			
			//渠道账户
			ChannelAccount ca = this.channelAccountDao.get(cah.getChannelAccount());
			if(ca != null){
				vo.setChannelName(ca.getName());
			}
			
			//商户
			Company c = this.companyDao.get(cah.getCompany());
			if(c != null){
				vo.setCompany(c.getName());
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
    	
		ChannelAccountHistory cah = channelAccountHistoryDao.get(uuid);
		if (cah == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		ChannelAccountHistoryVO vo = new ChannelAccountHistoryVO(cah);
		
		//渠道
		Channel ch = this.channelDao.get(cah.getChannel());
		if(ch != null){
			vo.setChannelName(ch.getName());
		}
		
		//渠道账户
		ChannelAccount ca = this.channelAccountDao.get(cah.getChannelAccount());
		if(ca != null){
			vo.setChannelName(ca.getName());
		}
		
		//商户
		Company c = this.companyDao.get(cah.getCompany());
		if(c != null){
			vo.setCompany(c.getName());
		}
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
}
