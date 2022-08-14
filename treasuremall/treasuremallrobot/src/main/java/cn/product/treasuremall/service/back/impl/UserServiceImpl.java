package cn.product.treasuremall.service.back.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.AdminDao;
import cn.product.treasuremall.dao.ChannelDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserAddressDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.entity.Channel;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUser.FrontUserStatus;
import cn.product.treasuremall.entity.FrontUser.FrontUserType;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserAddress;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.service.back.UserService;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.FrontUserVO;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
    private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
    private AdminDao adminDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private FrontUserAddressDao frontUserAddressDao;
	/**
	 * 获取用户详情
	 */
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		FrontUser fu = frontUserDao.get(uuid);
		if(fu == null || FrontUserStatus.DELETE.equals(fu.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户不存在");
			return;
		}
		
		FrontUserAccount fua = this.frontUserAccountDao.get(fu.getUuid());
		FrontUserVO fuvo = new FrontUserVO(fu, fua);
		
		if(!Utlity.checkStringNull(fu.getRegisterChannel())) {
			//获取渠道信息
			Channel lc = this.channelDao.get(fu.getRegisterChannel());
			if(lc != null) {
				fuvo.setRegisterChannelCN(lc.getTitle());
			}
		}
		//获取头像信息
		if(!Utlity.checkStringNull(fu.getImage())) {
			Resource res = resourceDao.get(fu.getImage());
			if(res != null) {
				fuvo.setImageURL(res.getUrl());
			}
		}
		
		result.setData(fuvo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	/**
	 * 根据条件查询用户列表
	 */
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String realname = paramsMap.get("realname") == null ? "" : paramsMap.get("realname").toString();
		String showid = paramsMap.get("showid") == null ? "" : paramsMap.get("showid").toString();
		String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		String channel = paramsMap.get("channel") == null ? "" : paramsMap.get("channel").toString();
		
		String level = paramsMap.get("level") == null ? "" : paramsMap.get("level").toString();
		
		String createtime = paramsMap.get("createtime") == null ? "" : paramsMap.get("createtime").toString();
		
		String totalRecharge = paramsMap.get("totalRecharge") == null ? "" : paramsMap.get("totalRecharge").toString();
		
		String totalWinning = paramsMap.get("totalWinning") == null ? "" : paramsMap.get("totalWinning").toString();
		
		String totalWithdraw = paramsMap.get("totalWithdraw") == null ? "" : paramsMap.get("totalWithdraw").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("realname", realname);
		searchMap.put("showid", showid);
		searchMap.put("mobile", mobile);
		searchMap.put("status", status);
		searchMap.put("type", FrontUserType.NORMAL);
		searchMap.put("channel", channel);
		searchMap.put("level", level);
		
		if(!Utlity.checkStringNull(createtime)) {
			String[] times = createtime.split("_");
			if(times != null && times.length == 2) {
				searchMap.put("timestart", times[0].trim());
				searchMap.put("timeend", times[1].trim());
			}
		}
		
		if(!Utlity.checkStringNull(totalRecharge)) {
			String[] recharge = totalRecharge.split("_");
			if(recharge != null && recharge.length == 2) {
				searchMap.put("rechargemin", recharge[0]);
				searchMap.put("rechargemax", recharge[1]);
			}
		}
		
		if(!Utlity.checkStringNull(totalWinning)) {
			String[] winning = totalWinning.split("_");
			if(winning != null && winning.length == 2) {
				searchMap.put("winningmin", winning[0]);
				searchMap.put("winningmax", winning[1]);
			}
		}
		
		if(!Utlity.checkStringNull(totalWithdraw)) {
			String[] withdraw = totalWithdraw.split("_");
			if(withdraw != null && withdraw.length == 2) {
				searchMap.put("withdrawmin", withdraw[0]);
				searchMap.put("withdrawmax", withdraw[1]);
			}
		}
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = frontUserDao.getCountByParams(searchMap);
		List<FrontUser> fuList = frontUserDao.getListByParams(searchMap);
		
		List<FrontUserVO> voList = new ArrayList<FrontUserVO>();
		for(FrontUser fu : fuList){
			FrontUserAccount fua = this.frontUserAccountDao.get(fu.getUuid());
			FrontUserVO fuvo = new FrontUserVO(fu, fua);
			
			if(!Utlity.checkStringNull(fu.getRegisterChannel())) {
				//获取渠道信息
				Channel lc = this.channelDao.get(fu.getRegisterChannel());
				if(lc != null) {
					fuvo.setRegisterChannelCN(lc.getTitle());
				}
			}
			//获取头像信息
			if(!Utlity.checkStringNull(fu.getImage())) {
				Resource res = resourceDao.get(fu.getImage());
				if(res != null) {
					fuvo.setImageURL(res.getUrl());
				}
			}
			voList.add(fuvo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		FrontUser fu = frontUserDao.get(uuid);
		if(fu == null || FrontUserStatus.DELETE.equals(fu.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户不存在");
			return;
		}
		
		fu.setStatus(status);
		
		frontUserDao.update(fu);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}

	/**
	 * 获取用户收货地址列表
	 */
	@Override
	public void addresslist(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", uuid);
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		List<FrontUserAddress> list = this.frontUserAddressDao.getListByParams(searchMap);
		Integer totalCount = this.frontUserAddressDao.getCountByParams(searchMap);
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("查询成功！");
		return;
	}
}
