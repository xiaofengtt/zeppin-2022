package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.ActivityInfoBuyfree;
import cn.product.treasuremall.util.MyMapper;

public interface ActivityInfoBuyfreeMapper extends MyMapper<ActivityInfoBuyfree> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<ActivityInfoBuyfree> getListByParams(Map<String,Object> params);
    
    public void updateSorts(Map<String,Object> params);
    
    public void updateOtherSorts(Map<String,Object> params);
    
    public void batchUpdateStatus(Map<String,Object> params);
}