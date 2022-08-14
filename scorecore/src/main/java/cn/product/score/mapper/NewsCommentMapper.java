package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.NewsComment;
import cn.product.score.util.MyMapper;

public interface NewsCommentMapper extends MyMapper<NewsComment> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<NewsComment> getListByParams(Map<String, Object> params);
}