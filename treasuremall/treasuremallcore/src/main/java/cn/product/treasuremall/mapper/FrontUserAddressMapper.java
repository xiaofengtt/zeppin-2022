package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.product.treasuremall.entity.FrontUserAddress;
import cn.product.treasuremall.util.MyMapper;

public interface FrontUserAddressMapper extends MyMapper<FrontUserAddress> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<FrontUserAddress> getListByParams(Map<String,Object> params);
    
    public void updateDefault(@Param("frontUser") String frontUser);
}