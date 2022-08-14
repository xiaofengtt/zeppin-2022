/**
 * 
 */
package cn.product.payment.service.store.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.ChannelDao;
import cn.product.payment.dao.CompanyChannelDao;
import cn.product.payment.dao.CompanyDao;
import cn.product.payment.entity.Channel;
import cn.product.payment.entity.CompanyAdmin;
import cn.product.payment.entity.CompanyChannel;
import cn.product.payment.entity.CompanyChannel.CompanyChannelStatus;
import cn.product.payment.service.store.StoreCompanyChannelService;
import cn.product.payment.vo.store.CompanyChannelVO;

/**
 * 商户渠道
 * @author Administrator
 *
 */
@Service("storeCompanyChannelService")
public class StoreCompanyChannelServiceImpl implements StoreCompanyChannelService {

	@Autowired
	private CompanyChannelDao companyChannelDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		String type = paramsMap.get("type") == null ? null : paramsMap.get("type").toString();
		String channel = paramsMap.get("channel") == null ? null : paramsMap.get("channel").toString();
		CompanyAdmin admin = (CompanyAdmin)paramsMap.get("companyAdmin");
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", admin.getCompany());
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
    	CompanyAdmin admin = (CompanyAdmin)paramsMap.get("companyAdmin");
    	
		CompanyChannel cc = companyChannelDao.get(uuid);
		if (cc == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		if(!cc.getCompany().equals(admin.getCompany())){
			//非本商户信息
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("无法查询！");
			return;
		}
		
		CompanyChannelVO vo = new CompanyChannelVO(cc);
		Channel channel = this.channelDao.get(cc.getChannel());
		if(channel != null){
			vo.setChannelName(channel.getName());
		}

		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String status = paramsMap.get("status").toString();
    	CompanyAdmin admin = (CompanyAdmin)paramsMap.get("companyAdmin");
    	
		if(!CompanyChannelStatus.NORMAL.equals(status) && !CompanyChannelStatus.DISABLE.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("状态错误！");
			return;
		}
		
		CompanyChannel cc = companyChannelDao.get(uuid);
		if (cc == null || CompanyChannelStatus.DELETE.equals(cc.getStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		if(!cc.getCompany().equals(admin.getCompany())){
			//非本商户信息
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("无法修改！");
			return;
		}
		
		cc.setStatus(status);
		this.companyChannelDao.update(cc);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
}
