package cn.product.worldmall.service.front.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserRanklistDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserRanklist;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.service.front.FrontUserRanklistService;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.front.FrontUserRanklistVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("frontUserRanklistService")
public class FrontUserRanklistServiceImpl implements FrontUserRanklistService{
	
	
	@Autowired
	private FrontUserRanklistDao frontUserRanklistDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	

	/**
	 * 获取排行榜列表
	 */
	@Override
	public void list(InputParams params, DataResult<Object> result) {
//		Map<String, Object> paramsMap = params.getParams();
//		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		//获取汇率
		//获取金币汇率
		SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
		BigDecimal rate = BigDecimal.ONE;
		if(sprate != null) {
			rate = BigDecimal.valueOf(Double.valueOf(sprate.getParamValue()));
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		List<FrontUserRanklist> list = frontUserRanklistDao.getListByParams(searchMap);
		List<FrontUserRanklistVO> listvo = new ArrayList<FrontUserRanklistVO>();
		
		if(list != null && list.size() > 0) {
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			for(FrontUserRanklist fur : list) {
				FrontUserRanklistVO furvo = new FrontUserRanklistVO(fur);
				furvo.setTotalWinning(fur.getTotalWinning().multiply(rate));
				FrontUser fu = this.frontUserDao.get(fur.getFrontUser());
				if(fu != null) {
					furvo.setShowId(fu.getShowId());
					furvo.setNickname(fu.getNickname());
					if(!Utlity.checkStringNull(fu.getImage())) {
						Resource re = this.resourceDao.get(fu.getImage());
						if(re != null) {
							furvo.setImageUrl(pathUrl + re.getUrl());
						}
					}
				}
				listvo.add(furvo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}
	
}
