package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IQuestionDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Question;

public class QuestionDAO extends HibernateTemplateDAO<Question,Integer> implements IQuestionDAO {

	@Override
	public List<Question> getQuestionByMap(Map<String, String> map, int offset, int length) {
		
		StringBuilder sb  = new StringBuilder();
		sb.append("from Question q where 1=1 ");
		
		
		sb.append(" order by q.count desc,q.createtime desc ");
		
		return this.getByHQL(sb.toString(), offset, length);
	}

}
