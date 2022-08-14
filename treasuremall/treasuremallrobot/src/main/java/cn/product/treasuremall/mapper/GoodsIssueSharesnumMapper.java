package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.GoodsIssueSharesnum;
import cn.product.treasuremall.util.MyMapper;

public interface GoodsIssueSharesnumMapper extends MyMapper<GoodsIssueSharesnum> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<GoodsIssueSharesnum> getListByParams(Map<String,Object> params);
}