package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.News;
import cn.product.score.util.MyMapper;
import cn.product.score.vo.back.StatusCountVO;

public interface NewsMapper extends MyMapper<News> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<News> getListByParams(Map<String, Object> params);
	
	public void updateStatus(Map<String, Object> params);
	
	public List<StatusCountVO> getStatusList();
}