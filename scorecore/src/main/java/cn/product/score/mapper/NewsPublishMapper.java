package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.NewsPublish;
import cn.product.score.util.MyMapper;
import cn.product.score.vo.back.StatusCountVO;

public interface NewsPublishMapper extends MyMapper<NewsPublish> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<NewsPublish> getListByParams(Map<String, Object> params);
	
	public void updateStatus(Map<String, Object> params);
	
	public List<StatusCountVO> getStatusList(Map<String, Object> params);
}