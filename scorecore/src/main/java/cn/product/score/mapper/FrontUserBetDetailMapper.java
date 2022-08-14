package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.FrontUserBetDetail;
import cn.product.score.util.MyMapper;
import cn.product.score.vo.back.BetSumVO;

public interface FrontUserBetDetailMapper extends MyMapper<FrontUserBetDetail> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserBetDetail> getListByParams(Map<String, Object> params);
	
	public List<BetSumVO> getBetSumList(Map<String, Object> params);
}
