package cn.product.worldmall.service.back.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.ActivityInfoCheckinPrizeDao;
import cn.product.worldmall.dao.ActivityInfoDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.dao.VoucherDao;
import cn.product.worldmall.entity.ActivityInfoCheckinPrize;
import cn.product.worldmall.entity.Goods;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.Voucher;
import cn.product.worldmall.entity.ActivityInfoCheckinPrize.ActivityInfoCheckinPrizeType;
import cn.product.worldmall.service.back.ActivityInfoCheckinService;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.ActivityInfoCheckinPrizeVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("activityInfoCheckinService")
public class ActivityInfoCheckinServiceImpl implements ActivityInfoCheckinService{
	
	@Autowired
	private ActivityInfoDao activityInfoDao;
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private ActivityInfoCheckinPrizeDao activityInfoCheckinPrizeDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private VoucherDao voucherDao;

	@Override
	public void prizelist(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		//????????????
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		searchMap.put("sort", sort);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//??????????????????????????????????????????
		Integer totalResultCount = activityInfoCheckinPrizeDao.getCountByParams(searchMap);
		//???????????????????????????????????????
		List<ActivityInfoCheckinPrize> list = activityInfoCheckinPrizeDao.getListByParams(searchMap);
		List<ActivityInfoCheckinPrizeVO> volist = new ArrayList<ActivityInfoCheckinPrizeVO>();
		
		if(list != null && list.size() > 0) {
			for(ActivityInfoCheckinPrize aicp : list) {
				ActivityInfoCheckinPrizeVO vo = new ActivityInfoCheckinPrizeVO(aicp);
				if(!Utlity.checkStringNull(aicp.getPrizeCover())) {
					Resource re = this.resourceDao.get(aicp.getPrizeCover());
					if(re != null) {
						vo.setPrizeCoverUrl(re.getUrl());
					}
				}
				
				if(ActivityInfoCheckinPrizeType.ENTITY.equals(aicp.getPrizeType())) {
					//????????????
					Goods goods = this.goodsDao.get(aicp.getPrize());
					if(goods != null) {
						vo.setPrizeDetail(goods.getName());
					}
				} else if(ActivityInfoCheckinPrizeType.VOUCHER.equals(aicp.getPrizeType())) {
					//????????????
					Voucher v = this.voucherDao.get(aicp.getPrize());
					if(v != null) {
						vo.setPrizeDetail(v.getTitle());
					}
				} else if(ActivityInfoCheckinPrizeType.GOLD.equals(aicp.getPrizeType())) {
					//????????????
					vo.setPrizeDetail(aicp.getPrize());
				}
				volist.add(vo);
			}
		}
		result.setData(volist);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String name = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		//??????????????????
		ActivityInfoCheckinPrize aicp = activityInfoCheckinPrizeDao.get(name);
		if (aicp == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????");
			return;
		}
		ActivityInfoCheckinPrizeVO vo = new ActivityInfoCheckinPrizeVO(aicp);
		if(Utlity.checkStringNull(aicp.getPrizeCover())) {
			Resource re = this.resourceDao.get(aicp.getPrizeCover());
			if(re != null) {
				vo.setPrizeCoverUrl(re.getUrl());
			}
		}
		
		if(ActivityInfoCheckinPrizeType.ENTITY.equals(aicp.getPrizeType())) {
			//????????????
			Goods goods = this.goodsDao.get(aicp.getPrize());
			if(goods != null) {
				vo.setPrizeDetail(goods.getName());
			}
		} else if(ActivityInfoCheckinPrizeType.VOUCHER.equals(aicp.getPrizeType())) {
			//????????????
			Voucher v = this.voucherDao.get(aicp.getPrize());
			if(v != null) {
				vo.setPrizeDetail(v.getTitle());
			}
		} else if(ActivityInfoCheckinPrizeType.GOLD.equals(aicp.getPrizeType())) {
			//????????????
			vo.setPrizeDetail(aicp.getPrize());
		}
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}
}
