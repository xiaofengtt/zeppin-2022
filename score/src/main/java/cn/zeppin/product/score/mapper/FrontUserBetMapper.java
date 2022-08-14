package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.FrontUserBet;
import cn.zeppin.product.score.util.MyMapper;
import cn.zeppin.product.score.vo.back.StatusCountVO;

public interface FrontUserBetMapper extends MyMapper<FrontUserBet> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserBet> getListByParams(Map<String, Object> params);
	
	public List<StatusCountVO> getStatusList();
}
