package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.util.MyMapper;

public interface FrontUserMapper extends MyMapper<FrontUser> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUser> getListByParams(Map<String, Object> params);
	
	/**
	 * 批量更新
	 */
	public void updateStatus(Map<String, Object> params);
	
	public Integer getCountAllByParams(Map<String, Object> params);
	
	public List<Map<String, Object>> getRegistListByParams(Map<String, Object> params);
	
	public Integer getRobotCountByParams(Map<String, Object> params);
	
	public List<FrontUser> getRobotListByParams(Map<String, Object> params);
}