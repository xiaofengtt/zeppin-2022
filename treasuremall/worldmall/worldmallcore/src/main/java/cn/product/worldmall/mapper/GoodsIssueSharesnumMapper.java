package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.GoodsIssueSharesnum;
import cn.product.worldmall.util.MyMapper;

public interface GoodsIssueSharesnumMapper extends MyMapper<GoodsIssueSharesnum> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<GoodsIssueSharesnum> getListByParams(Map<String,Object> params);
}