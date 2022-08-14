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
import cn.product.worldmall.dao.ActivityInfoBuyfreeDao;
import cn.product.worldmall.dao.AreaDao;
import cn.product.worldmall.dao.FrontUserAddressDao;
import cn.product.worldmall.dao.FrontUserBuyfreeOrderDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserMessageDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.entity.ActivityInfoBuyfree;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserBuyfreeOrder;
import cn.product.worldmall.entity.FrontUserMessage;
import cn.product.worldmall.entity.Goods;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.FrontUser.FrontUserType;
import cn.product.worldmall.entity.FrontUserBuyfreeOrder.FrontUserBuyfreeOrderReceiveType;
import cn.product.worldmall.entity.FrontUserBuyfreeOrder.FrontUserBuyfreeOrderStatus;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageType;
import cn.product.worldmall.service.back.UserBuyfreeService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.SendSmsNew;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.FrontUserBuyfreeOrderVO;
import cn.product.worldmall.vo.back.ProvideInfoVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("userBuyfreeService")
public class UserBuyfreeServiceImpl implements UserBuyfreeService{
	
	@Autowired
    private FrontUserBuyfreeOrderDao frontUserBuyfreeOrderDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
    private ActivityInfoBuyfreeDao activityInfoBuyfreeDao;
	
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
		String goodsId = paramsMap.get("goodsId") == null ? "" : paramsMap.get("goodsId").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		String bettime = paramsMap.get("bettime") == null ? "" : paramsMap.get("bettime").toString();
		
		String userType = paramsMap.get("userType") == null ? "" : paramsMap.get("userType").toString();
		Boolean isLuck = paramsMap.get("isLuck") == null ? null : Boolean.valueOf(paramsMap.get("isLuck").toString());
		
		String receiveType = paramsMap.get("receiveType") == null ? "" : paramsMap.get("receiveType").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserShowId", showId);
		searchMap.put("goodsId", goodsId);
		if("winning".equals(status)) {
			String[] statuses = {"win","finished","receive","confirm","return"};
			searchMap.put("statuses", statuses);
			
		} else {
			searchMap.put("status", status);
		}
		
		if(isLuck != null) {
			searchMap.put("isLuck", isLuck);
		}
		
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

		searchMap.put("receiveType", receiveType);
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		List<FrontUserBuyfreeOrder> list = this.frontUserBuyfreeOrderDao.getLeftListByParams(searchMap);
		Integer totalCount = this.frontUserBuyfreeOrderDao.getLeftCountByParams(searchMap);
		List<FrontUserBuyfreeOrderVO> listvo = new ArrayList<FrontUserBuyfreeOrderVO>();
		
		if(list != null && list.size() > 0) {
			for(FrontUserBuyfreeOrder fubo : list) {
				FrontUserBuyfreeOrderVO fubovo = new FrontUserBuyfreeOrderVO(fubo);
				//封装用户信息
				FrontUser fu = this.frontUserDao.get(fubo.getFrontUser());
				if(fu != null) {
					fubovo.setNickname(fu.getNickname());
					//获取头像信息
					if(!Utlity.checkStringNull(fu.getImage())) {
						Resource res = resourceDao.get(fu.getImage());
						if(res != null) {
							fubovo.setImageUrl(res.getUrl());
						}
					}
				}
				
				//封装商品信息
				ActivityInfoBuyfree aib = this.activityInfoBuyfreeDao.get(fubo.getActivityInfoBuyfree());
				if(aib != null) {
					fubovo.setIssueNum(aib.getIssueNum());
					fubovo.setTitle(aib.getGoodsTitle());
					fubovo.setShortTitle(aib.getGoodsShortTitle());
					fubovo.setShares(aib.getShares());
					fubovo.setPrice(aib.getGoodsPrice());
					
					fubovo.setCover("");
					Resource re = this.resourceDao.get(aib.getGoodsCover());
					if(re != null) {
						fubovo.setCover(re.getUrl());
					}
					
					Goods good = this.goodsDao.get(aib.getGoodsId());
					fubovo.setCode(good.getCode());
				}
				listvo.add(fubovo);
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
			FrontUserBuyfreeOrder fubo = this.frontUserBuyfreeOrderDao.get(uuid);
			if(fubo == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("中奖信息不存在！");
				return;
			}
			if(!FrontUserBuyfreeOrderStatus.RECEIVE.equals(fubo.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("中奖信息状态错误！");
				return;
			}
			
			if(!Utlity.checkStringNull(fubo.getProvideInfo())) {
				ProvideInfoVO pivo = JSONUtils.json2obj(fubo.getProvideInfo(), ProvideInfoVO.class);
				pivo.setCompany(company);
				pivo.setExpressNumber(expressNumber);
				
				fubo.setProvideInfo(JSONUtils.obj2json(pivo));
				fubo.setStatus(FrontUserBuyfreeOrderStatus.FINISHED);
				fubo.setOperator(admin);
				fubo.setOperattime(new Timestamp(System.currentTimeMillis()));
				
				this.frontUserBuyfreeOrderDao.update(fubo);
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("操作失败！");
				return;
			}
			
			/*
			 * 异步消息--暂不启动
			 */
			//派奖通知消息			
			Goods g = this.goodsDao.get(fubo.getGoodsId());
			FrontUserMessage fum = new FrontUserMessage();
			fum.setUuid(UUID.randomUUID().toString());
			fum.setFrontUser(fubo.getFrontUser());
			fum.setFrontUserShowId(fubo.getFrontUserShowId());
			fum.setTitle("提示，活动奖品发货");
			StringBuilder content = new StringBuilder();
			if(g != null) {
				content.append("您在"+Utlity.timeSpanToChinaDateString(fubo.getCreatetime())+"参与的0元购活动中，中得的"+g.getShortname()+"奖品已经通过"+company+"物流寄出，物流单号"+expressNumber+"。收货后请及时开箱验货，如有问题，请与客服联系。");
			}
			fum.setContent(content.toString());
			fum.setSourceId(fubo.getUuid());
			fum.setStatus(FrontUserMessageStatus.NORMAL);
			fum.setType(FrontUserMessageType.USER_WIN);
			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_BUYFREE);
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
			FrontUserBuyfreeOrder fubo = this.frontUserBuyfreeOrderDao.get(uuid);
			if(fubo == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("中奖信息不存在！");
				return;
			}
			if(!FrontUserBuyfreeOrderStatus.RETURN.equals(status) && !FrontUserBuyfreeOrderStatus.CONFIRM.equals(status)) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("状态错误！");
				return;
			}
			if(!FrontUserBuyfreeOrderStatus.FINISHED.equals(fubo.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("领奖信息状态错误！");
				return;
			}
			if(!Utlity.checkStringNull(fubo.getProvideInfo())) {
				fubo.setStatus(status);
				fubo.setOperator(admin);
				fubo.setOperattime(new Timestamp(System.currentTimeMillis()));
				
				this.frontUserBuyfreeOrderDao.update(fubo);
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
			FrontUserBuyfreeOrder fubo = this.frontUserBuyfreeOrderDao.get(uuid);
			if(fubo == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("中奖信息不存在！");
				return;
			}
			if(!FrontUserBuyfreeOrderStatus.RECEIVE.equals(fubo.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("领奖信息状态错误！");
				return;
			}
			if(!FrontUserBuyfreeOrderReceiveType.ENTITY.equals(fubo.getReceiveType())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("领奖信息类型错误！");
				return;
			}
			
			fubo.setReceiveType(FrontUserBuyfreeOrderReceiveType.NORMAL);
			fubo.setStatus(FrontUserBuyfreeOrderStatus.WIN);
			
			
			this.frontUserBuyfreeOrderDao.update(fubo);
			
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
