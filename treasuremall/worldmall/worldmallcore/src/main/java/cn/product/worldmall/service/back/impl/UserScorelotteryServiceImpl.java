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
import cn.product.worldmall.dao.ActivityInfoScorelotteryPrizeDao;
import cn.product.worldmall.dao.AreaDao;
import cn.product.worldmall.dao.FrontUserAddressDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserMessageDao;
import cn.product.worldmall.dao.FrontUserScorelotteryHistoryDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserMessage;
import cn.product.worldmall.entity.FrontUserScorelotteryHistory;
import cn.product.worldmall.entity.Goods;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.ActivityInfoScorelotteryPrize.ActivityInfoScorelotteryPrizeType;
import cn.product.worldmall.entity.FrontUser.FrontUserType;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageType;
import cn.product.worldmall.entity.FrontUserScorelotteryHistory.FrontUserScorelotteryHistoryReceiveType;
import cn.product.worldmall.entity.FrontUserScorelotteryHistory.FrontUserScorelotteryHistoryStatus;
import cn.product.worldmall.service.back.UserScorelotteryService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.SendSmsNew;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.FrontUserScorelotteryHistoryVO;
import cn.product.worldmall.vo.back.ProvideInfoVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("userScorelotteryService")
public class UserScorelotteryServiceImpl implements UserScorelotteryService{
	
	@Autowired
    private FrontUserScorelotteryHistoryDao frontUserScorelotteryHistoryDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
    private ActivityInfoScorelotteryPrizeDao activityInfoScorelotteryPrizeDao;
	
	@Autowired
    private GoodsDao goodsDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private FrontUserAddressDao frontUserAddressDao;
	
	@Autowired
	private AreaDao areaDao;

	@Autowired
	private FrontUserMessageDao frontUserMessageDao;
	
	/**
	 * ????????????????????????0??????????????????
	 */
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String showId = paramsMap.get("showId") == null ? "" : paramsMap.get("showId").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		String bettime = paramsMap.get("bettime") == null ? "" : paramsMap.get("bettime").toString();
		
		String userType = paramsMap.get("userType") == null ? "" : paramsMap.get("userType").toString();
		
		String prizeType = paramsMap.get("prizeType") == null ? "" : paramsMap.get("prizeType").toString();
		
		String receiveType = paramsMap.get("receiveType") == null ? "" : paramsMap.get("receiveType").toString();
		
		String activityInfoPrize = paramsMap.get("activityInfoPrize") == null ? "" : paramsMap.get("activityInfoPrize").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserShowId", showId);
		searchMap.put("prizeType", prizeType);
		searchMap.put("receiveType", receiveType);
		searchMap.put("status", status);
		searchMap.put("activityInfoScorelotteryPrize", activityInfoPrize);
		
		
		if(!Utlity.checkStringNull(bettime)) {
			String[] times = bettime.split("_");
			if(times != null && times.length == 2) {
				searchMap.put("timestart", times[0]);
				searchMap.put("timeend", times[1]);
			}
		}
		
		if(!Utlity.checkStringNull(userType)) {
			if(!FrontUserType.NORMAL.equals(userType) && !FrontUserType.ROBOT.equals(userType)) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("?????????????????????");
				return;
			}
			searchMap.put("userType", userType);
		}
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		List<FrontUserScorelotteryHistory> list = this.frontUserScorelotteryHistoryDao.getLeftListByParams(searchMap);
		Integer totalCount = this.frontUserScorelotteryHistoryDao.getLeftCountByParams(searchMap);
		List<FrontUserScorelotteryHistoryVO> listvo = new ArrayList<FrontUserScorelotteryHistoryVO>();
		
		if(list != null && list.size() > 0) {
			for(FrontUserScorelotteryHistory fush : list) {
				FrontUserScorelotteryHistoryVO fushvo = new FrontUserScorelotteryHistoryVO(fush);
				//??????????????????
				FrontUser fu = this.frontUserDao.get(fush.getFrontUser());
				if(fu != null) {
					fushvo.setMobile(fu.getMobile());
					fushvo.setNickname(fu.getNickname());
					//??????????????????
					if(!Utlity.checkStringNull(fu.getImage())) {
						Resource res = resourceDao.get(fu.getImage());
						if(res != null) {
							fushvo.setImageUrl(res.getUrl());
						}
					}
				}
				
				if(!Utlity.checkStringNull(fush.getPrizeCover())) {
					Resource re = this.resourceDao.get(fush.getPrizeCover());
					if(re != null) {
						fushvo.setPrizeCoverUrl(re.getUrl());
					}
				}
				listvo.add(fushvo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("???????????????");
		return;	
	}

	@Override
	public void receive(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String company = paramsMap.get("company") == null ? "" : paramsMap.get("company").toString();
		String expressNumber = paramsMap.get("expressNumber") == null ? "" : paramsMap.get("expressNumber").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		try {
			FrontUserScorelotteryHistory fush = this.frontUserScorelotteryHistoryDao.get(uuid);
			if(fush == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("????????????????????????");
				return;
			}
			if(!ActivityInfoScorelotteryPrizeType.ENTITY.equals(fush.getPrizeType())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????????????????");
				return;
			}
			if(!FrontUserScorelotteryHistoryStatus.RECEIVE.equals(fush.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????????????????");
				return;
			}
			
			if(!Utlity.checkStringNull(fush.getProvideInfo())) {
				ProvideInfoVO pivo = JSONUtils.json2obj(fush.getProvideInfo(), ProvideInfoVO.class);
				pivo.setCompany(company);
				pivo.setExpressNumber(expressNumber);
				
				fush.setProvideInfo(JSONUtils.obj2json(pivo));
				fush.setStatus(FrontUserScorelotteryHistoryStatus.FINISHED);
				fush.setOperator(admin);
				fush.setOperattime(new Timestamp(System.currentTimeMillis()));
				
				this.frontUserScorelotteryHistoryDao.update(fush);
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????");
				return;
			}
			
			/*
			 * ????????????--????????????
			 */
			//??????????????????			
			Goods g = this.goodsDao.get(fush.getPrize());
			FrontUserMessage fum = new FrontUserMessage();
			fum.setUuid(UUID.randomUUID().toString());
			fum.setFrontUser(fush.getFrontUser());
			fum.setFrontUserShowId(fush.getFrontUserShowId());
			fum.setTitle("???????????????????????????");
			StringBuilder content = new StringBuilder();
			if(g != null) {
				content.append("??????"+Utlity.timeSpanToChinaDateString(fush.getCreatetime())+"????????????????????????????????????????????????"+g.getShortname()+"??????????????????"+company+"???????????????????????????"+expressNumber+"????????????????????????????????????????????????????????????????????????");
			}
			fum.setContent(content.toString());
			fum.setSourceId(fush.getUuid());
			fum.setStatus(FrontUserMessageStatus.NORMAL);
			fum.setType(FrontUserMessageType.USER_WIN);
			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_SCORELOTTERY);
			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
			this.frontUserMessageDao.sendMessage(fum);
			this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_DELIVERY);
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("?????????????????????");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????");
			return;
		}
	}

	@Override
	public void confirm(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		try {
			FrontUserScorelotteryHistory fush = this.frontUserScorelotteryHistoryDao.get(uuid);
			if(fush == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("????????????????????????");
				return;
			}
			if(!FrontUserScorelotteryHistoryStatus.RETURN.equals(status) && !FrontUserScorelotteryHistoryStatus.CONFIRM.equals(status)) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????");
				return;
			}
			if(!FrontUserScorelotteryHistoryStatus.FINISHED.equals(fush.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????????????????");
				return;
			}
			if(!Utlity.checkStringNull(fush.getProvideInfo())) {
				fush.setStatus(status);
				fush.setOperator(admin);
				fush.setOperattime(new Timestamp(System.currentTimeMillis()));
				
				this.frontUserScorelotteryHistoryDao.update(fush);
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????");
				return;
			}
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("???????????????");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????");
			return;
		}		
	}

	@Override
	public void reset(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		try {
			FrontUserScorelotteryHistory fush = this.frontUserScorelotteryHistoryDao.get(uuid);
			if(fush == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("????????????????????????");
				return;
			}
			if(!FrontUserScorelotteryHistoryStatus.RECEIVE.equals(fush.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????????????????");
				return;
			}
			if(!FrontUserScorelotteryHistoryReceiveType.ENTITY.equals(fush.getReceiveType())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????????????????");
				return;
			}
			
			fush.setReceiveType(FrontUserScorelotteryHistoryReceiveType.NORMAL);
			fush.setStatus(FrontUserScorelotteryHistoryStatus.NORMAL);
			
			
			this.frontUserScorelotteryHistoryDao.update(fush);
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("???????????????");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????");
			return;
		}		
	}
}
