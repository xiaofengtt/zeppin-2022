package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.GuessingMatch;
import cn.zeppin.product.score.util.MyMapper;
import cn.zeppin.product.score.vo.back.StatusCountVO;

public interface GuessingMatchMapper extends MyMapper<GuessingMatch> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<GuessingMatch> getListByParams(Map<String, Object> params);
	
	public List<StatusCountVO> getStatusList();
	
	public List<GuessingMatch> getWaitingMatchList();
}