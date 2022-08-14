package cn.product.treasuremall.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.ActivityInfoCheckinPrizeDao;
import cn.product.treasuremall.dao.AreaDao;
import cn.product.treasuremall.dao.FrontUserAddressDao;
import cn.product.treasuremall.dao.FrontUserCheckinHistoryDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserMessageDao;
import cn.product.treasuremall.dao.GoodsDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.entity.ActivityInfoCheckinPrize.ActivityInfoCheckinPrizeType;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUser.FrontUserType;
import cn.product.treasuremall.entity.FrontUserCheckinHistory;
import cn.product.treasuremall.entity.FrontUserCheckinHistory.FrontUserCheckinHistoryReceiveType;
import cn.product.treasuremall.entity.FrontUserCheckinHistory.FrontUserCheckinHistoryStatus;
import cn.product.treasuremall.entity.FrontUserMessage;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageType;
import cn.product.treasuremall.entity.Goods;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.service.back.UserCheckinService;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.SendSmsNew;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.FrontUserCheckinHistoryVO;
import cn.product.treasuremall.vo.back.ProvideInfoVO;

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
	 * 根据条件查询用户0元购参与列表
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
				result.setMessage("用户类型错误！");
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
				//封装用户信息
				FrontUser fu = this.frontUserDao.get(fuch.getFrontUser());
				if(fu != null) {
					fuchvo.setMobile(fu.getMobile());
					fuchvo.setNickname(fu.getNickname());
					//获取头像信息
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
		result.setMessage("查询成功！");
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
				result.setMessage("中奖信息不存在！");
				return;
			}
			if(!ActivityInfoCheckinPrizeType.ENTITY.equals(fuch.getPrizeType())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("奖品类型错误错误！");
				return;
			}
			if(!FrontUserCheckinHistoryStatus.RECEIVE.equals(fuch.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("中奖信息状态错误！");
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
				result.setMessage("操作失败！");
				return;
			}
			
			
			/*
			 * 异步消息--暂不启动
			 */
			//派奖通知消息			
			Goods g = this.goodsDao.get(fuch.getPrize());
			FrontUserMessage fum = new FrontUserMessage();
			fum.setUuid(UUID.randomUUID().toString());
			fum.setFrontUser(fuch.getFrontUser());
			fum.setFrontUserShowId(fuch.getFrontUserShowId());
			fum.setTitle("提示，活动奖品发货");
			StringBuilder content = new StringBuilder();
			if(g != null) {
				content.append("您在"+Utlity.timeSpanToChinaDateString(fuch.getCreatetime())+"参与的签到活动中，中得的"+g.getShortname()+"奖品已经通过"+company+"物流寄出，物流单号"+expressNumber+"。收货后请及时开箱验货，如有问题，请与客服联系。");
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
			result.setMessage("派奖登记成功！");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常！");
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
				result.setMessage("中奖信息不存在！");
				return;
			}
			if(!FrontUserCheckinHistoryStatus.RETURN.equals(status) && !FrontUserCheckinHistoryStatus.CONFIRM.equals(status)) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("状态错误！");
				return;
			}
			if(!FrontUserCheckinHistoryStatus.FINISHED.equals(fuch.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("领奖信息状态错误！");
				return;
			}
			if(!Utlity.checkStringNull(fuch.getProvideInfo())) {
				fuch.setStatus(status);
				fuch.setOperator(admin);
				fuch.setOperattime(new Timestamp(System.currentTimeMillis()));
				
				this.frontUserCheckinHistoryDao.update(fuch);
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("操作失败！");
				return;
			}
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("操作成功！");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常！");
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
				result.setMessage("中奖信息不存在！");
				return;
			}
			if(!FrontUserCheckinHistoryStatus.RECEIVE.equals(fuch.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("领奖信息状态错误！");
				return;
			}
			if(!FrontUserCheckinHistoryReceiveType.ENTITY.equals(fuch.getReceiveType())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("领奖信息类型错误！");
				return;
			}
			
			fuch.setReceiveType(FrontUserCheckinHistoryReceiveType.NORMAL);
			fuch.setStatus(FrontUserCheckinHistoryStatus.NORMAL);
			
			
			this.frontUserCheckinHistoryDao.update(fuch);
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("操作成功！");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常！");
			return;
		}
	}
}
