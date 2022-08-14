package cn.product.worldmall.service.back.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.ActivityInfoBuyfreeDao;
import cn.product.worldmall.dao.ActivityInfoBuyfreeGoodsDao;
import cn.product.worldmall.dao.ActivityInfoDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.entity.ActivityInfoBuyfreeGoods;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.service.back.ActivityInfoBuyfreeService;
import cn.product.worldmall.vo.back.ActivityInfoBuyfreeGoodsVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("activityInfoBuyfreeService")
public class ActivityInfoBuyfreeServiceImpl implements ActivityInfoBuyfreeService{
	
	@Autowired
	private ActivityInfoDao activityInfoDao;
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private ActivityInfoBuyfreeGoodsDao activityInfoBuyfreeGoodsDao;
	
	@Autowired
	private ActivityInfoBuyfreeDao activityInfoBuyfreeDao;

	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void goodslist(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		searchMap.put("sort", sort);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//查询符合条件的活动信息的总数
		Integer totalResultCount = activityInfoBuyfreeGoodsDao.getCountByParams(searchMap);
		//查询符合条件的活动信息列表
		List<ActivityInfoBuyfreeGoods> list = activityInfoBuyfreeGoodsDao.getListByParams(searchMap);
		List<ActivityInfoBuyfreeGoodsVO> volist = new ArrayList<ActivityInfoBuyfreeGoodsVO>();
		
		if(list != null && list.size() > 0) {
			for(ActivityInfoBuyfreeGoods aibg : list) {
				ActivityInfoBuyfreeGoodsVO vo = new ActivityInfoBuyfreeGoodsVO(aibg);
				Resource re = this.resourceDao.get(aibg.getGoodsCover());
				if(re != null) {
					vo.setGoodsCoverUrl(re.getUrl());
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
		//获取活动信息
		ActivityInfoBuyfreeGoods aibg = activityInfoBuyfreeGoodsDao.get(name);
		if (aibg == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
			return;
		}
		ActivityInfoBuyfreeGoodsVO vo = new ActivityInfoBuyfreeGoodsVO(aibg);
		Resource re = this.resourceDao.get(aibg.getGoodsCover());
		if(re != null) {
			vo.setGoodsCoverUrl(re.getUrl());
		}
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}
}
