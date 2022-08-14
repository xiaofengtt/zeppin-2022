package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.NoticePublic;
import cn.product.worldmall.util.MyMapper;

public interface NoticePublicMapper extends MyMapper<NoticePublic> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<NoticePublic> getListByParams(Map<String, Object> params);
}

