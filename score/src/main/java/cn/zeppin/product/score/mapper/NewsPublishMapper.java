package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.NewsPublish;
import cn.zeppin.product.score.util.MyMapper;
import cn.zeppin.product.score.vo.back.StatusCountVO;

public interface NewsPublishMapper extends MyMapper<NewsPublish> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<NewsPublish> getListByParams(Map<String, Object> params);
	
	public void updateStatus(Map<String, Object> params);
	
	public List<StatusCountVO> getStatusList(Map<String, Object> params);
}