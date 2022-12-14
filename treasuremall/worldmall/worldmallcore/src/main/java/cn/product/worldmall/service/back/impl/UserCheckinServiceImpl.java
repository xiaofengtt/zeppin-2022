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
import cn.product.worldmall.dao.ActivityInfoCheckinPrizeDao;
import cn.product.worldmall.dao.AreaDao;
import cn.product.worldmall.dao.FrontUserAddressDao;
import cn.product.worldmall.dao.FrontUserCheckinHistoryDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserMessageDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserCheckinHistory;
import cn.product.worldmall.entity.FrontUserMessage;
import cn.product.worldmall.entity.Goods;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.ActivityInfoCheckinPrize.ActivityInfoCheckinPrizeType;
import cn.product.worldmall.entity.FrontUser.FrontUserType;
import cn.product.worldmall.entity.FrontUserCheckinHistory.FrontUserCheckinHistoryReceiveType;
import cn.product.worldmall.entity.FrontUserCheckinHistory.FrontUserCheckinHistoryStatus;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageType;
import cn.product.worldmall.service.back.UserCheckinService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.SendSmsNew;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.FrontUserCheckinHistoryVO;
import cn.product.worldmall.vo.back.ProvideInfoVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("userCheckinService")
public class UserCheckinServiceImpl implements UserCheckinService{
	
	@Autowired
    private FrontUserCheckinHistoryDao frontUserCheckinHistoryDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
    private ActivityInfoCheckinPrizeDao activityInfoCheckinPrizeDao;
	
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
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserShowId", showId);
		searchMap.put("prizeType", prizeType);
		searchMap.put("receiveType", receiveType);
		searchMap.put("status", status);
		
		
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
		
		List<FrontUserCheckinHistory> list = this.frontUserCheckinHistoryDao.getLeftListByParams(searchMap);
		Integer totalCount = this.frontUserCheckinHistoryDao.getLeftCountByParams(searchMap);
		List<FrontUserCheckinHistoryVO> listvo = new ArrayList<FrontUserCheckinHistoryVO>();
		
		if(list != null && list.size() > 0) {
			for(FrontUserCheckinHistory fuch : list) {
				FrontUserCheckinHistoryVO fuchvo = new FrontUserCheckinHistoryVO(fuch);
				//??????????????????
				FrontUser fu = this.frontUserDao.get(fuch.getFrontUser());
				if(fu != null) {
					fuchvo.setMobile(fu.getMobile());
					fuchvo.setNickname(fu.getNickname());
					//??????????????????
					if(!Utlity.checkStringNull(fu.getImage())) {
						Resource res = resourceDao.get(fu.getImage());
						if(res != null) {
							fuchvo.setImageUrl(res.getUrl());
						}
					}
				}
				
				if(!Utlity.checkStringNull(fuch.getPrizeCover())) {
					Resource re = this.resourceDao.get(fuch.getPrizeCover());
					if(re != null) {
						fuchvo.setPrizeCoverUrl(re.getUrl());
					}
				}
				listvo.add(fuchvo);
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
			FrontUserCheckinHistory fuch = this.frontUserCheckinHistoryDao.get(uuid);
			if(fuch == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("????????????????????????");
				return;
			}
			if(!ActivityInfoCheckinPrizeType.ENTITY.equals(fuch.getPrizeType())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????????????????");
				return;
			}
			if(!FrontUserCheckinHistoryStatus.RECEIVE.equals(fuch.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????????????????");
				return;
			}
			
			if(!Utlity.checkStringNull(fuch.getProvideInfo())) {
				ProvideInfoVO pivo = JSONUtils.json2obj(fuch.getProvideInfo(), ProvideInfoVO.class);
				pivo.setCompany(company);
				pivo.setExpressNumber(expressNumber);
				
				fuch.setProvideInfo(JSONUtils.obj2json(pivo));
				fuch.setStatus(FrontUserCheckinHistoryStatus.FINISHED);
				fuch.setOperator(admin);
				fuch.setOperattime(new Timestamp(System.currentTimeMillis()));
				
				this.frontUserCheckinHistoryDao.update(fuch);
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????");
				return;
			}
			
			
			/*
			 * ????????????--????????????
			 */
			//??????????????????			
			Goods g = this.goodsDao.get(fuch.getPrize());
			FrontUserMessage fum = new FrontUserMessage();
			fum.setUuid(UUID.randomUUID().toString());
			fum.setFrontUser(fuch.getFrontUser());
			fum.setFrontUserShowId(fuch.getFrontUserShowId());
			fum.setTitle("???????????????????????????");
			StringBuilder content = new StringBuilder();
			if(g != null) {
				content.append("??????"+Utlity.timeSpanToChinaDateString(fuch.getCreatetime())+"????????????????????????????????????"+g.getShortname()+"??????????????????"+company+"???????????????????????????"+expressNumber+"????????????????????????????????????????????????????????????????????????");
			}
			fum.setContent(content.toString());
			fum.setSourceId(fuch.getUuid());
			fum.setStatus(FrontUserMessageStatus.NORMAL);
			fum.setType(FrontUserMessageType.USER_WIN);
			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_CHECKIN);
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
			FrontUserCheckinHistory fuch = this.frontUserCheckinHistoryDao.get(uuid);
			if(fuch == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("????????????????????????");
				return;
			}
			if(!FrontUserCheckinHistoryStatus.RETURN.equals(status) && !FrontUserCheckinHistoryStatus.CONFIRM.equals(status)) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????");
				return;
			}
			if(!FrontUserCheckinHistoryStatus.FINISHED.equals(fuch.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????????????????");
				return;
			}
			if(!Utlity.checkStringNull(fuch.getProvideInfo())) {
				fuch.setStatus(status);
				fuch.setOperator(admin);
				fuch.setOperattime(new Timestamp(System.currentTimeMillis()));
				
				this.frontUserCheckinHistoryDao.update(fuch);
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
			FrontUserCheckinHistory fuch = this.frontUserCheckinHistoryDao.get(uuid);
			if(fuch == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("????????????????????????");
				return;
			}
			if(!FrontUserCheckinHistoryStatus.RECEIVE.equals(fuch.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????????????????");
				return;
			}
			if(!FrontUserCheckinHistoryReceiveType.ENTITY.equals(fuch.getReceiveType())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????????????????");
				return;
			}
			
			fuch.setReceiveType(FrontUserCheckinHistoryReceiveType.NORMAL);
			fuch.setStatus(FrontUserCheckinHistoryStatus.NORMAL);
			
			
			this.frontUserCheckinHistoryDao.update(fuch);
			
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
