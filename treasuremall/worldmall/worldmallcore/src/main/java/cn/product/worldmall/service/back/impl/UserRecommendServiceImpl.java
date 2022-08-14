package cn.product.worldmall.service.back.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.AdminOffsetOrderDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserRechargeOrderDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.entity.AdminOffsetOrder;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserRechargeOrder;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.service.back.UserRecommendService;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.FrontUserRecommendHistoryVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("userRecommendService")
public class UserRecommendServiceImpl implements UserRecommendService{
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private FrontUserDao frontUserDao;

	@Autowired
	private SystemParamDao systemParamDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
	private AdminOffsetOrderDao adminOffsetOrderDao;
	
	@Autowired
	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;
	
	/**
	 * 根据条件查询用户0元购参与列表
	 */
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String createtime = paramsMap.get("createtime") == null ? "" : paramsMap.get("createtime").toString();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String showId = paramsMap.get("showId") == null ? "" : paramsMap.get("showId").toString();
		String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("orderType", Constants.ORDER_TYPE_USER_RECOMMEND);
		searchMap.put("showid", showId);
		searchMap.put("mobile", mobile);
		if(!Utlity.checkStringNull(createtime)) {
			String[] times = createtime.split("_");
			if(times != null && times.length == 2) {
				searchMap.put("timestart", times[0]);
				searchMap.put("timeend", times[1]);
			}
		}
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", "createtime desc");
		
		List<FrontUserHistory> list = this.frontUserHistoryDao.getLeftListByParams(searchMap);
		Integer totalCount = this.frontUserHistoryDao.getLeftCountByParams(searchMap);
		List<FrontUserRecommendHistoryVO> listvo = new ArrayList<FrontUserRecommendHistoryVO>();
		if(list != null && list.size() > 0) {
			//获取金币汇率
			SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
			BigDecimal rate = BigDecimal.ONE;
			if(sprate != null) {
				rate = BigDecimal.valueOf(Double.valueOf(sprate.getParamValue()));
			}
			
			//地址链接
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			
			for(FrontUserHistory fuh : list) {
				FrontUserRecommendHistoryVO furhvo = new FrontUserRecommendHistoryVO(fuh);
				furhvo.setAwardAmount(fuh.getdAmount().divide(rate));//换算单位（元）
				FrontUser frontUser = this.frontUserDao.get(fuh.getFrontUser());
				if(frontUser != null) {
					furhvo.setShowId(frontUser.getShowId());
					furhvo.setNickname(frontUser.getNickname());
					if(!Utlity.checkStringNull(frontUser.getImage())) {
			    		Resource re = this.resourceDao.get(frontUser.getImage());
			    		if(re != null) {
			    			furhvo.setImageURL(pathUrl + re.getUrl());
			    		}
			    	}
				}
				AdminOffsetOrder aoo = this.adminOffsetOrderDao.get(fuh.getOrderId());
				if(aoo != null) {
					if(!Utlity.checkStringNull(aoo.getRechargeOrder())) {
						FrontUserRechargeOrder furo = this.frontUserRechargeOrderDao.get(aoo.getRechargeOrder());
						if(furo != null) {
							FrontUser fu = this.frontUserDao.get(furo.getFrontUser());
							furhvo.setRecommendNickname(fu.getNickname());
							furhvo.setRecommendFrontUser(fu.getUuid());
							furhvo.setRecommendShowId(fu.getShowId());
							if(!Utlity.checkStringNull(fu.getImage())) {
					    		Resource re = this.resourceDao.get(fu.getImage());
					    		if(re != null) {
					    			furhvo.setRecommendImageURL(pathUrl + re.getUrl());
					    		}
					    	}
							furhvo.setRechargeAmount(furo.getAmount());
						}
					}
				}
				listvo.add(furhvo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		return;
	}
}
