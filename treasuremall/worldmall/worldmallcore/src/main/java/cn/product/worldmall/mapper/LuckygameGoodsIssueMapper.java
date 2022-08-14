package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.LuckygameGoodsIssue;
import cn.product.worldmall.util.MyMapper;

public interface LuckygameGoodsIssueMapper extends MyMapper<LuckygameGoodsIssue> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<LuckygameGoodsIssue> getListByParams(Map<String,Object> params);
    
    public void updateSorts(Map<String,Object> params);
    
    public void updateOtherSorts(Map<String,Object> params);
    
    public void batchUpdateStatus(Map<String,Object> params);
}