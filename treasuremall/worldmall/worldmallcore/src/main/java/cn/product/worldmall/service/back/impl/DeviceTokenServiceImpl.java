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
import cn.product.worldmall.dao.FrontDeviceTokenDao;
import cn.product.worldmall.dao.FrontDeviceTokenMessageDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.InternationalInfoDao;
import cn.product.worldmall.dao.NoticeTopicDao;
import cn.product.worldmall.entity.FrontDeviceToken;
import cn.product.worldmall.entity.FrontDeviceTokenMessage;
import cn.product.worldmall.entity.FrontDeviceTokenMessage.FrontDeviceTokenMessageType;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.worldmall.entity.InternationalInfo;
import cn.product.worldmall.entity.NoticeTopic;
import cn.product.worldmall.entity.NoticeTopic.NoticeTopicStatus;
import cn.product.worldmall.service.back.DeviceTokenService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.FrontDeviceTokenVO;

@Service("deviceTokenService")
public class DeviceTokenServiceImpl implements DeviceTokenService{
	
	@Autowired
	private FrontDeviceTokenDao frontDeviceTokenDao;
	
	@Autowired
	private InternationalInfoDao internationalInfoDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private FrontDeviceTokenMessageDao frontDeviceTokenMessageDao;
	
	@Autowired
	private NoticeTopicDao noticeTopicDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String deviceType = paramsMap.get("deviceType") == null ? "" : paramsMap.get("deviceType").toString();
		String frontUserGroup = paramsMap.get("frontUserGroup") == null ? "" : paramsMap.get("frontUserGroup").toString();
		String country = paramsMap.get("country") == null ? "" : paramsMap.get("country").toString();
		String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
		String topic = paramsMap.get("topic") == null ? "" : paramsMap.get("topic").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		String createtime = paramsMap.get("createtime") == null ? "" : paramsMap.get("createtime").toString();
		
		String totalRecharge = paramsMap.get("totalRecharge") == null ? "" : paramsMap.get("totalRecharge").toString();
		
		String totalWinning = paramsMap.get("totalWinning") == null ? "" : paramsMap.get("totalWinning").toString();
		
		String totalWithdraw = paramsMap.get("totalWithdraw") == null ? "" : paramsMap.get("totalWithdraw").toString();
		
//		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("deviceType", deviceType);
		searchMap.put("frontUserGroup", frontUserGroup);
		searchMap.put("country", country);
		searchMap.put("ip", ip);
		searchMap.put("topic", topic);
		
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
		if(Utlity.checkStringNull(status)) {
			status = "normal";
		}
		searchMap.put("status", status);
		searchMap.put("sort", sort);
		
		
		Integer totalResultCount = frontDeviceTokenDao.getCountByParams(searchMap);
		List<FrontDeviceToken> list = frontDeviceTokenDao.getListByParams(searchMap);
		List<FrontDeviceTokenVO> listvo = new ArrayList<FrontDeviceTokenVO>();
		if(list != null && list.size() > 0) {
			for(FrontDeviceToken fdt : list) {
				FrontDeviceTokenVO fdtvo = new FrontDeviceTokenVO(fdt);
				if(!Utlity.checkStringNull(fdt.getCountry())) {
					InternationalInfo ii = this.internationalInfoDao.get(fdt.getCountry());
					if(ii != null) {
						fdtvo.setCountryName(ii.getName());
						fdtvo.setCountryNameEn(ii.getNameEn());
					}
				}
				
				if(!Utlity.checkStringNull(fdt.getFrontUser())) {
					FrontUser fu = this.frontUserDao.get(fdt.getFrontUser());
					fdtvo.setNickname(fu.getNickname());
					fdtvo.setShowId(fu.getShowId());
				}
				
				if(!Utlity.checkStringNull(fdt.getTopic())) {
					Map<String, Object> topicMap = JSONUtils.json2map(fdt.getTopic());
					if(topicMap != null && topicMap.size() > 0) {
						List<Map<String, Object>> topicList = new ArrayList<Map<String,Object>>();
						for(String key : topicMap.keySet()) {
							Map<String, Object> topicInfo = new HashMap<String, Object>();
							NoticeTopic nt = this.noticeTopicDao.get(key);
							if(nt != null) {
								topicInfo.put("displayName", nt.getDisplayName());
								if(!Utlity.checkStringNull(topicMap.get(key) == null ? "" : topicMap.get(key).toString())) {
									topicInfo.put("isBind", true);
								} else {
									topicInfo.put("isBind", false);
								}
								topicList.add(topicInfo);
							}
						}
						fdtvo.setTopicList(topicList);
					}
				}
				
				listvo.add(fdtvo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
    	FrontDeviceToken fdt = frontDeviceTokenDao.get(uuid);
		if (fdt == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在");
			return;
		}
		FrontDeviceTokenVO fdtvo = new FrontDeviceTokenVO(fdt);
		if(!Utlity.checkStringNull(fdt.getCountry())) {
			InternationalInfo ii = this.internationalInfoDao.get(fdt.getCountry());
			if(ii != null) {
				fdtvo.setCountryName(ii.getName());
				fdtvo.setCountryNameEn(ii.getNameEn());
			}
		}
		if(!Utlity.checkStringNull(fdt.getFrontUser())) {
			FrontUser fu = this.frontUserDao.get(fdt.getFrontUser());
			fdtvo.setNickname(fu.getNickname());
			fdtvo.setShowId(fu.getShowId());
		}
		
		if(!Utlity.checkStringNull(fdt.getTopic())) {
			Map<String, Object> topicMap = JSONUtils.json2map(fdt.getTopic());
			if(topicMap != null && topicMap.size() > 0) {
				List<Map<String, Object>> topicList = new ArrayList<Map<String,Object>>();
				for(String key : topicMap.keySet()) {
					Map<String, Object> topicInfo = new HashMap<String, Object>();
					NoticeTopic nt = this.noticeTopicDao.get(key);
					if(nt != null) {
						topicInfo.put("displayName", nt.getDisplayName());
						if(!Utlity.checkStringNull(topicMap.get(key) == null ? "" : topicMap.get(key).toString())) {
							topicInfo.put("isBind", true);
						} else {
							topicInfo.put("isBind", false);
						}
						topicList.add(topicMap);
					}
				}
				fdtvo.setTopicList(topicList);
			}
		}
		result.setData(fdtvo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
	}

	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
//		Map<String, Object> paramsMap = params.getParams();
//		String[] uuids = paramsMap.get("uuids") == null ? null : (String[])paramsMap.get("uuids");
//		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
//		
//		if(!InternationalInfoVersionStatus.NORMAL.equals(status) && !InternationalInfoVersionStatus.DISABLE.equals(status) && !InternationalInfoVersionStatus.DELETE.equals(status)){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("变更状态有误");
//			return;
//		}
//		
//		try {
//			if(uuids != null) {
//				List<InternationalInfoVersion> iivList = new ArrayList<InternationalInfoVersion>();
//				for(String ids : uuids) {
//					InternationalInfoVersion iiv = frontDeviceTokenDao.get(ids);
//					if(iiv == null || FrontUserStatus.DELETE.equals(iiv.getStatus())){
//						result.setStatus(ResultStatusType.FAILED);
//						result.setMessage("信息不存在");
//						return;
//					}
//					if(InternationalInfoVersionStatus.NORMAL.equals(status)) {
//						iiv.setFlagWithdraw(true);
//					} else if(InternationalInfoVersionStatus.DISABLE.equals(status)) {
//						iiv.setFlagWithdraw(false);
//					} else {
//						iiv.setStatus(status);
//					}
//					iivList.add(iiv);
//				}
//				this.internationalInfoDao.batchUpdate(iivList);
//				result.setStatus(ResultStatusType.SUCCESS);
//				result.setMessage("修改成功！");
//				return;
//			} else {
//				result.setStatus(ResultStatusType.FAILED);
//				result.setMessage("用户参数错误");
//				return;
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("操作异常！");
//			return;
//		}
	}

	@Override
	public void send(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String[] uuids = paramsMap.get("uuids") == null ? null : (String[])paramsMap.get("uuids");

		String title = paramsMap.get("title") == null ? "" : paramsMap.get("title").toString();
		String content = paramsMap.get("content") == null ? "" : paramsMap.get("content").toString();
		
		try {
			List<FrontDeviceTokenMessage> list = new ArrayList<FrontDeviceTokenMessage>();
			for(String ids : uuids) {
				FrontDeviceToken fdt = this.frontDeviceTokenDao.get(ids);
				if(fdt != null && !Utlity.checkStringNull(fdt.getEndpointArn())) {
					FrontDeviceTokenMessage fdtm = new FrontDeviceTokenMessage();
					fdtm.setUuid(UUID.randomUUID().toString());
					fdtm.setCreatetime(new Timestamp(System.currentTimeMillis()));
					fdtm.setType(FrontDeviceTokenMessageType.SYSTEM_NOTICE);
					fdtm.setStatus(FrontUserMessageStatus.NORMAL);
					fdtm.setTitle(title);
					fdtm.setContent(content);
					fdtm.setFrontDeviceToken(fdt.getUuid());
					fdtm.setFlagUserSend(true);
					list.add(fdtm);
				}
			}
			
			this.frontDeviceTokenMessageDao.sendSNSMessage(list);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("操作成功！");
			return;
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常！");
			return;		
		}
	}

	@Override
	public void bind(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String[] uuids = paramsMap.get("uuids") == null ? null : (String[])paramsMap.get("uuids");
		String topic = paramsMap.get("topic") == null ? "" : paramsMap.get("topic").toString();
		
		try {
			NoticeTopic nt = this.noticeTopicDao.get(topic);
			if(nt != null && NoticeTopicStatus.NORMAL.equals(nt.getStatus())) {
				List<FrontDeviceToken> list = new ArrayList<FrontDeviceToken>();
				for(String ids : uuids) {
					FrontDeviceToken fdt = this.frontDeviceTokenDao.get(ids);
					if(fdt != null && !Utlity.checkStringNull(fdt.getEndpointArn())) {//已经绑定SNS成功的，才可以绑定主题
						Map<String, Object> topicMap = new HashMap<String, Object>();
						if(!Utlity.checkStringNull(fdt.getTopic())) {
							topicMap = JSONUtils.json2map(fdt.getTopic());
						}
						if(!topicMap.containsKey(topic)) {//未绑定过的主题，才可以绑定
							topicMap.put(topic, "");
						}
						fdt.setTopic(JSONUtils.obj2json(topicMap));
						list.add(fdt);
					}
				}
				this.frontDeviceTokenDao.bindTopic(list);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("提交绑定成功！");
				return;
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Topic信息异常！");
				return;		
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常！");
			return;		
		}
	}
	
}
