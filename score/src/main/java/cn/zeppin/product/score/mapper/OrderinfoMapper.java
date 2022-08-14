package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.Orderinfo;
import cn.zeppin.product.score.util.MyMapper;

public interface OrderinfoMapper extends MyMapper<Orderinfo> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Orderinfo> getListByParams(Map<String, Object> params);
}