package cn.product.treasuremall.service.back.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.ActivityInfoDao;
import cn.product.treasuremall.dao.ActivityInfoRechargePrizeDao;
import cn.product.treasuremall.dao.GoodsDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.dao.VoucherDao;
import cn.product.treasuremall.entity.ActivityInfoRechargePrize;
import cn.product.treasuremall.entity.ActivityInfoRechargePrize.ActivityInfoRechargePrizeType;
import cn.product.treasuremall.entity.Voucher;
import cn.product.treasuremall.service.back.ActivityInfoRechargeService;
import cn.product.treasuremall.vo.back.ActivityInfoRechargePrizeVO;

@Service("activityInfoRechargeService")
public class ActivityInfoRechargeServiceImpl implements ActivityInfoRechargeService{
	
	@Autowired
	private ActivityInfoDao activityInfoDao;
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private ActivityInfoRechargePrizeDao activityInfoRechargePrizeDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private VoucherDao voucherDao;

	@Override
	public void configlist(InputParams params, DataResult<Object> result) {
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
		Integer totalResultCount = activityInfoRechargePrizeDao.getCountByParams(searchMap);
		//???????????????????????????????????????
		List<ActivityInfoRechargePrize> list = activityInfoRechargePrizeDao.getListByParams(searchMap);
		List<ActivityInfoRechargePrizeVO> volist = new ArrayList<ActivityInfoRechargePrizeVO>();
		
		if(list != null && list.size() > 0) {
			for(ActivityInfoRechargePrize aifp : list) {
				ActivityInfoRechargePrizeVO vo = new ActivityInfoRechargePrizeVO(aifp);
				if(ActivityInfoRechargePrizeType.VOUCHER.equals(aifp.getPrizeType())) {
					String[] vs = aifp.getPrize().split(",");
					List<Object> listPrize = new ArrayList<Object>();
					for(String voucher : vs) {
						Voucher v = this.voucherDao.get(voucher);
						if(v != null) {
							listPrize.add(v);
						}
					}
					vo.setListPrize(listPrize);
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
		ActivityInfoRechargePrize aifp = activityInfoRechargePrizeDao.get(name);
		if (aifp == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????");
			return;
		}
		ActivityInfoRechargePrizeVO vo = new ActivityInfoRechargePrizeVO(aifp);
		 if(ActivityInfoRechargePrizeType.VOUCHER.equals(aifp.getPrizeType())) {
			String[] vs = aifp.getPrize().split(",");
			List<Object> listPrize = new ArrayList<Object>();
			for(String voucher : vs) {
				Voucher v = this.voucherDao.get(voucher);
				if(v != null) {
					listPrize.add(v);
				}
			}
			vo.setListPrize(listPrize);
		}
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}
}
