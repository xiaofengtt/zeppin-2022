package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.NoticeTopic;
import cn.product.worldmall.util.MyMapper;

public interface NoticeTopicMapper extends MyMapper<NoticeTopic> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<NoticeTopic> getListByParams(Map<String, Object> params);
}

