package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Comment;

public interface ICommentDAO extends IBaseDAO<Comment, Integer> {
	
	public List<Comment> getCommentByMap(Map<String,String> map,int offset,int length);
	
}
