package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ICommentDAO;
import cn.zeppin.entity.Comment;
import cn.zeppin.service.api.ICommentService;

public class CommentService implements ICommentService {

	private ICommentDAO commentDAO;

	public ICommentDAO getCommentDAO() {
		return commentDAO;
	}

	public void setCommentDAO(ICommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}

	@Override
	public void AddComment(Comment comment) {
		this.commentDAO.save(comment);
		
	}

	@Override
	public List<Comment> getCommentByMap(Map<String, String> map, int offset, int length) {
		return this.commentDAO.getCommentByMap(map, offset, length);
	}

}
