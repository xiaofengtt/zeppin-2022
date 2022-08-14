package cn.product.worldmall.service.back.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.GoodsTypeDao;
import cn.product.worldmall.entity.GoodsType;
import cn.product.worldmall.entity.GoodsType.GoodsTypeStatus;
import cn.product.worldmall.service.back.GoodsTypeService;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("goodsTypeService")
public class GoodsTypeServiceImpl implements GoodsTypeService{
	
	@Autowired
	private GoodsTypeDao goodsTypeDao;

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String code = paramsMap.get("code") == null ? "" : paramsMap.get("code").toString();
		GoodsType goodsType = goodsTypeDao.get(code);
		if(goodsType == null || GoodsTypeStatus.DELETE.equals(goodsType.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商品类型不存在");
			return;
		}
		result.setData(goodsType);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String level = paramsMap.get("level") == null ? "" : paramsMap.get("level").toString();
		String parent = paramsMap.get("parent") == null ? "" : paramsMap.get("parent").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("name", name);
		paramsls.put("level", level);
		paramsls.put("parent", parent);
		paramsls.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			paramsls.put("offSet", (pageNum-1)*pageSize);
			paramsls.put("pageSize", pageSize);
		}
		paramsls.put("sort", sort);
		
		Integer totalCount = goodsTypeDao.getCountByParams(paramsls);
		List<GoodsType> goodsTypeList = goodsTypeDao.getListByParams(paramsls);
		
		result.setData(goodsTypeList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		GoodsType goodsType = (GoodsType) paramsMap.get("goodsType");
		
		GoodsType gt = goodsTypeDao.get(goodsType.getCode());
		if(gt != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商品类型编码已存在！");
			return;
		}
		
		if(goodsType.getParent() != null){
			GoodsType parent = goodsTypeDao.get(goodsType.getParent());
			if(parent != null){
				goodsType.setLevel(parent.getLevel() + 1);
			}else{
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("商品类型父类型不存在！");
				return;
			}
		}else{
			goodsType.setLevel(1);
		}
		
		goodsTypeDao.insert(goodsType);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("添加成功！");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		GoodsType goodsType = (GoodsType) paramsMap.get("goodsType");
		
		GoodsType gt = goodsTypeDao.get(goodsType.getCode());
		if(gt == null || GoodsTypeStatus.DELETE.equals(gt.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商品类型不存在");
			return;
		}
		gt.setName(goodsType.getName());
		gt.setIcon(goodsType.getIcon());
		gt.setSort(goodsType.getSort());
		gt.setStatus(goodsType.getStatus());
		goodsTypeDao.update(gt);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}

	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String code = paramsMap.get("code") == null ? "" : paramsMap.get("code").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		GoodsType gt = goodsTypeDao.get(code);
		if(gt == null || GoodsTypeStatus.DELETE.equals(gt.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商品类型不存在");
			return;
		}
		
		gt.setStatus(status);
		
		goodsTypeDao.update(gt);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}
	
}
