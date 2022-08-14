package cn.zeppin.dao.imp;

import java.util.List;

import cn.zeppin.dao.api.IEthnicDao;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Ethnic;

public class EthnicDaoImpl extends HibernateTemplateDAO<Ethnic, Short> implements IEthnicDao {
	public List<Ethnic> getList() {
		String hql = "from Ethnic t  order by t.weight desc";
		return this.getByHQL(hql.toString(), -1, -1);
	}
}
