package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.FrontUserBet;
import cn.product.score.util.MyMapper;
import cn.product.score.vo.back.StatusCountVO;

public interface FrontUserBetMapper extends MyMapper<FrontUserBet> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserBet> getListByParams(Map<String, Object> params);
	
	public List<StatusCountVO> getStatusList();
}
