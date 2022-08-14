package cn.product.worldmall.service.back.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.ActivityInfoDao;
import cn.product.worldmall.dao.ActivityInfoFirstchargePrizeDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.dao.VoucherDao;
import cn.product.worldmall.entity.ActivityInfoFirstchargePrize;
import cn.product.worldmall.entity.Voucher;
import cn.product.worldmall.entity.ActivityInfoFirstchargePrize.ActivityInfoFirstchargePrizeType;
import cn.product.worldmall.service.back.ActivityInfoFirstchargeService;
import cn.product.worldmall.vo.back.ActivityInfoFirstchargePrizeVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("activityInfoFirstchargeService")
public class ActivityInfoFirstchargeServiceImpl implements ActivityInfoFirstchargeService{
	
	@Autowired
	private ActivityInfoDao activityInfoDao;
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private ActivityInfoFirstchargePrizeDao activityInfoFirstchargePrizeDao;
	
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
		Integer totalResultCount = activityInfoFirstchargePrizeDao.getCountByParams(searchMap);
		//查询符合条件的活动信息列表
		List<ActivityInfoFirstchargePrize> list = activityInfoFirstchargePrizeDao.getListByParams(searchMap);
		List<ActivityInfoFirstchargePrizeVO> volist = new ArrayList<ActivityInfoFirstchargePrizeVO>();
		
		if(list != null && list.size() > 0) {
			for(ActivityInfoFirstchargePrize aifp : list) {
				ActivityInfoFirstchargePrizeVO vo = new ActivityInfoFirstchargePrizeVO(aifp);
				if(ActivityInfoFirstchargePrizeType.ENTITY.equals(aifp.getPrizeType())) {
				} else if(ActivityInfoFirstchargePrizeType.VOUCHER.equals(aifp.getPrizeType())) {
					String[] vs = aifp.getPrize().split(",");
					List<Object> listPrize = new ArrayList<Object>();
					for(String voucher : vs) {
						Voucher v = this.voucherDao.get(voucher);
						if(v != null) {
							listPrize.add(v);
						}
					}
					vo.setListPrize(listPrize);
				} else if(ActivityInfoFirstchargePrizeType.GOLD.equals(aifp.getPrizeType())) {
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
		ActivityInfoFirstchargePrize aifp = activityInfoFirstchargePrizeDao.get(name);
		if (aifp == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
			return;
		}
		ActivityInfoFirstchargePrizeVO vo = new ActivityInfoFirstchargePrizeVO(aifp);
		if(ActivityInfoFirstchargePrizeType.ENTITY.equals(aifp.getPrizeType())) {
		} else if(ActivityInfoFirstchargePrizeType.VOUCHER.equals(aifp.getPrizeType())) {
			String[] vs = aifp.getPrize().split(",");
			List<Object> listPrize = new ArrayList<Object>();
			for(String voucher : vs) {
				Voucher v = this.voucherDao.get(voucher);
				if(v != null) {
					listPrize.add(v);
				}
			}
			vo.setListPrize(listPrize);
		} else if(ActivityInfoFirstchargePrizeType.GOLD.equals(aifp.getPrizeType())) {
		}
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}
}
