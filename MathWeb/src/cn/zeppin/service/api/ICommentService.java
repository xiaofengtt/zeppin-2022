package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Comment;

public interface ICommentService {
	
	public void AddComment(Comment comment);
	
	public List<Comment> getCommentByMap(Map<String,String> map,int offset,int length);
	
}
