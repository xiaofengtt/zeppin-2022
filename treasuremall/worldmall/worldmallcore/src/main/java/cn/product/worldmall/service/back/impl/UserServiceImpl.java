package cn.product.worldmall.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.dao.AdminDao;
import cn.product.worldmall.dao.AreaDao;
import cn.product.worldmall.dao.ChannelDao;
import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserAddressDao;
import cn.product.worldmall.dao.FrontUserBlacklistDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserGroupDao;
import cn.product.worldmall.dao.InternationalInfoDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.entity.Area;
import cn.product.worldmall.entity.Channel;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUser.FrontUserStatus;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserAddress;
import cn.product.worldmall.entity.FrontUserBlacklist;
import cn.product.worldmall.entity.FrontUserGroup;
import cn.product.worldmall.entity.InternationalInfo;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.service.back.UserService;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.FrontUserAddressVO;
import cn.product.worldmall.vo.back.FrontUserBlacklistVO;
import cn.product.worldmall.vo.back.FrontUserVO;

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
	
	@Autowired
	private AreaDao areaDao;
	
	@Autowired
	private FrontUserGroupDao frontUserGroupDao;
	
	@Autowired
	private FrontUserBlacklistDao frontUserBlacklistDao;
	
	@Autowired
	private InternationalInfoDao internationalInfoDao;
	/**
	 * ??????????????????
	 */
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		FrontUser fu = frontUserDao.get(uuid);
		if(fu == null || FrontUserStatus.DELETE.equals(fu.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????");
			return;
		}
		
		FrontUserAccount fua = this.frontUserAccountDao.get(fu.getUuid());
		FrontUserVO fuvo = new FrontUserVO(fu, fua);
		
		if(!Utlity.checkStringNull(fu.getRegisterChannel())) {
			//??????????????????
			Channel lc = this.channelDao.get(fu.getRegisterChannel());
			if(lc != null) {
				fuvo.setRegisterChannelCN(lc.getTitle());
			}
		}
		//??????????????????
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
	 * ??????????????????????????????
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

		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("realname", realname);
		searchMap.put("showid", showid);
		searchMap.put("mobile", mobile);
		searchMap.put("status", status);
		searchMap.put("type", type);
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
				//??????????????????
				Channel lc = this.channelDao.get(fu.getRegisterChannel());
				if(lc != null) {
					fuvo.setRegisterChannelCN(lc.getTitle());
				}
			}
			//??????????????????
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
			result.setMessage("???????????????");
			return;
		}
		
		if(FrontUserStatus.BLACKLIST.equals(status)) {//???????????????
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????");
			return;
		}
		
		if(FrontUserStatus.BLACKLIST.equals(fu.getStatus())) {//???????????????
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("?????????????????????????????????");
			return;
		}
		
		fu.setStatus(status);
		
		frontUserDao.update(fu);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("???????????????");
	}

	/**
	 * ??????????????????????????????
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
		List<FrontUserAddressVO> voList = new ArrayList<FrontUserAddressVO>();
    	for(FrontUserAddress fua : list){
    		FrontUserAddressVO vo = new FrontUserAddressVO(fua);
    		if(!Utlity.checkStringNull(fua.getArea())) {
    			Area area = this.areaDao.get(fua.getArea());
        		List<String> areaNameList = this.areaDao.getFullName(fua.getArea());
        		vo.setAreaScode(area.getScode());
        		vo.setAreaNameList(areaNameList);
    		}
    		if(!Utlity.checkStringNull(fua.getInternationalInfo())) {
    			InternationalInfo ii = this.internationalInfoDao.get(fua.getInternationalInfo());
    			if(ii != null) {
    				vo.setCountry(ii.getNameEn());
    			}
    		}
    		voList.add(vo);
    	}
		
    	result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("???????????????");
		return;
	}

	@Override
	public void group(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String level = paramsMap.get("level") == null ? "" : paramsMap.get("level").toString();
		
		FrontUser fu = frontUserDao.get(uuid);
		if(fu == null || FrontUserStatus.DELETE.equals(fu.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????");
			return;
		}
		
		FrontUserGroup fug = this.frontUserGroupDao.get(level);
		if(fug == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("?????????????????????");
			return;
		}
		try {
			fu.setLevel(level);
			
			frontUserDao.update(fu);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("???????????????");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????");
		}
	}

	@Override
	public void blackadd(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String reason = paramsMap.get("reason") == null ? "" : paramsMap.get("reason").toString();
		
		FrontUser fu = frontUserDao.get(uuid);
		if(fu == null || FrontUserStatus.DELETE.equals(fu.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????");
			return;
		}
		
		fu.setStatus(FrontUserStatus.BLACKLIST);
		
		FrontUserBlacklist fubl = new FrontUserBlacklist();
		fubl.setUuid(UUID.randomUUID().toString());
		fubl.setFrontUser(fu.getUuid());
		fubl.setShowId(fu.getShowId());
		fubl.setNickname(fu.getNickname());
		fubl.setReason(reason);
		fubl.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		this.frontUserDao.updateBlacklist(fu, fubl);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("???????????????");
	}

	@Override
	public void blacklist(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String nickname = paramsMap.get("nickname") == null ? "" : paramsMap.get("nickname").toString();
		String showid = paramsMap.get("showid") == null ? "" : paramsMap.get("showid").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("nickname", nickname);
		searchMap.put("showid", showid);
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = frontUserBlacklistDao.getCountByParams(searchMap);
		List<FrontUserBlacklist> fuList = frontUserBlacklistDao.getListByParams(searchMap);
		
		List<FrontUserBlacklistVO> voList = new ArrayList<FrontUserBlacklistVO>();
		for(FrontUserBlacklist fubl : fuList){
			FrontUserBlacklistVO fublvo = new FrontUserBlacklistVO(fubl);
			FrontUser fu = this.frontUserDao.get(fubl.getFrontUser());
			
			FrontUserAccount fua = this.frontUserAccountDao.get(fu.getUuid());
			FrontUserVO fuvo = new FrontUserVO(fu, fua);
			
			if(!Utlity.checkStringNull(fu.getRegisterChannel())) {
				//??????????????????
				Channel lc = this.channelDao.get(fu.getRegisterChannel());
				if(lc != null) {
					fuvo.setRegisterChannelCN(lc.getTitle());
				}
			}
			//??????????????????
			if(!Utlity.checkStringNull(fu.getImage())) {
				Resource res = resourceDao.get(fu.getImage());
				if(res != null) {
					fuvo.setImageURL(res.getUrl());
				}
			}
			fublvo.setFrontUserVO(fuvo);
			voList.add(fublvo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void blackdrop(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();	
    	FrontUserBlacklist fubl = this.frontUserBlacklistDao.get(uuid);
    	if(fubl == null) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????????????????");
			return;
    	}
    	FrontUser fu = this.frontUserDao.get(fubl.getFrontUser());
    	if(fu == null) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("?????????????????????");
			return;
    	}
    	
    	fu.setStatus(FrontUserStatus.NORMAL);
    	this.frontUserBlacklistDao.delete(fubl, fu);
    	result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("???????????????");
	}
}
