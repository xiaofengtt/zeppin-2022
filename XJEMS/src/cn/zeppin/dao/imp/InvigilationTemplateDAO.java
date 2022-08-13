package cn.zeppin.dao.imp;

import java.util.List;

import cn.zeppin.dao.api.IInvigilationTemplateDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.InvigilationTemplate;

/**
 * 监考责任书 模板
 * 
 * @author zeppin
 *
 */
public class InvigilationTemplateDAO extends HibernateTemplateDAO<InvigilationTemplate, Integer>
		implements IInvigilationTemplateDAO {

	@Override
	public List<InvigilationTemplate> getList(int offset, int length) {
		StringBuilder hql = new StringBuilder();
		hql.append("from InvigilationTemplate order by createtime desc");
		return this.getByHQL(hql.toString(), offset, length);
	}

	@Override
	public int getCount() {
		StringBuilder hql = new StringBuilder();
		hql.append(" select count(*) from InvigilationTemplate it where 1=1 ");
		return ((Long) this.getResultByHQL(hql.toString())).intValue();
	}
}
