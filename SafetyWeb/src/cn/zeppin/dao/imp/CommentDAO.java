package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ICommentDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Comment;

public class CommentDAO extends HibernateTemplateDAO<Comment, Integer> implements ICommentDAO {

	@Override
	public List<Comment> getCommentByMap(Map<String, String> map, int offset, int length) {

		StringBuilder sb = new StringBuilder();
		sb.append("from Comment c where 1=1 ");
		if (map != null && !map.isEmpty()) {
			if (map.containsKey("qid")) {
				sb.append(" and c.question=").append(map.get("qid"));
			}
		}

		return this.getByHQL(sb.toString(), offset, length);
	}

}
