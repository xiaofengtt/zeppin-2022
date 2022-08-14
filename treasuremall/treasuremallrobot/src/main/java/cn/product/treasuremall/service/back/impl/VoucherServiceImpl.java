package cn.product.treasuremall.service.back.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.VoucherDao;
import cn.product.treasuremall.entity.Voucher;
import cn.product.treasuremall.entity.Voucher.VoucherStatus;
import cn.product.treasuremall.service.back.VoucherService;
import cn.product.treasuremall.util.Utlity;

@Service("voucherService")
public class VoucherServiceImpl implements VoucherService{
	
	@Autowired
	private VoucherDao voucherDao;
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		Voucher voucher = voucherDao.get(uuid);
		if(voucher == null || VoucherStatus.DELETE.equals(voucher.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("代金券不存在");
			return;
		}
		
		result.setData(voucher);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
		String title = paramsMap.get("title") == null ? "" : paramsMap.get("title").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("title", title);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = voucherDao.getCountByParams(searchMap);
		List<Voucher> cpList = voucherDao.getListByParams(searchMap);
		
		result.setData(cpList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Voucher voucher = (Voucher) paramsMap.get("voucher");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		if(voucher.getStarttime() != null){
			if(!Utlity.isTime(voucher.getStarttime()) && !voucher.getStarttime().startsWith("+")){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("开始时间不正确");
				return;
			}
		}
		
		if(voucher.getEndtime() != null){
			if(!Utlity.isTime(voucher.getEndtime()) && !voucher.getEndtime().startsWith("+")){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("结束时间不正确");
				return;
			}
		}
		
		voucher.setUuid(UUID.randomUUID().toString());
		voucher.setStatus(VoucherStatus.NORMAL);
		voucher.setCreator(admin);
		voucher.setCreatetime(new Timestamp(System.currentTimeMillis()));
		voucherDao.insert(voucher);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("添加成功！");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Voucher voucher = (Voucher) paramsMap.get("voucher");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		if(voucher.getStarttime() != null){
			if(!Utlity.isTime(voucher.getStarttime()) && !voucher.getStarttime().startsWith("+")){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("开始时间不正确");
				return;
			}
		}
		
		if(voucher.getEndtime() != null){
			if(!Utlity.isTime(voucher.getEndtime()) && !voucher.getEndtime().startsWith("+")){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("结束时间不正确");
				return;
			}
		}
		
		Voucher v = voucherDao.get(voucher.getUuid());
		if(v == null || VoucherStatus.DELETE.equals(v.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("代金券不存在");
			return;
		}
		v.setTitle(voucher.getTitle());
		v.setDiscription(voucher.getDiscription());
		v.setdAmount(voucher.getdAmount());
		v.setPayMin(voucher.getPayMin());
		v.setGoods(voucher.getGoods());
		v.setGoodsType(voucher.getGoodsType());
		v.setStarttime(voucher.getStarttime());
		v.setEndtime(voucher.getEndtime());
		
		voucher.setCreator(admin);
		voucher.setCreatetime(new Timestamp(System.currentTimeMillis()));
		voucherDao.update(v);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}

	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Voucher cp = voucherDao.get(uuid);
		if(cp == null || VoucherStatus.DELETE.equals(cp.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("代金券不存在");
			return;
		}
		cp.setStatus(status);
		
		voucherDao.update(cp);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}
	
}
