package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.NoticeTopicMessage;
import cn.product.worldmall.util.MyMapper;

public interface NoticeTopicMessageMapper extends MyMapper<NoticeTopicMessage> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<NoticeTopicMessage> getListByParams(Map<String, Object> params);
}

