package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.product.worldmall.entity.FrontUserAddress;
import cn.product.worldmall.util.MyMapper;

public interface FrontUserAddressMapper extends MyMapper<FrontUserAddress> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<FrontUserAddress> getListByParams(Map<String,Object> params);
    
    public void updateDefault(@Param("frontUser") String frontUser);
}