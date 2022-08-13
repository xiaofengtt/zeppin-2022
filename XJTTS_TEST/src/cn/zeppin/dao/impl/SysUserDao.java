package cn.zeppin.dao.impl;

import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.ISysUserDao;
import cn.zeppin.entity.SysUser;

@SuppressWarnings("rawtypes")
public class SysUserDao extends BaseDaoImpl<SysUser, Integer> implements ISysUserDao {
	public List findByAll(Object[] pars) {
		// TODO Auto-generated method stub

		// String hql =
		// "from SysUser t where (t.id.idcard=? or t.id.mobile=? or t.id.email=?) and t.id.password=?";

		String sql = "select * from sys_user t where (t.idcard=? or t.mobile=? or t.email=?) and t.password=? and t.role=?";

		Query query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter(0, pars[0]);
		query.setParameter(1, pars[0]);
		query.setParameter(2, pars[0]);
		query.setParameter(3, pars[1]);
		query.setParameter(4, pars[2]);

		List li = query.list();
		return li;
	}


	@Override
	public List findByIdCard(Object[] pars) {
		// TODO Auto-generated method stub
		String sql = "select * from sys_user t where (t.idcard=? or t.mobile=? or t.email=?) and t.role=?";
		Query query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter(0, pars[0]);
		query.setParameter(1, pars[0]);
		query.setParameter(2, pars[0]);
		query.setParameter(3, pars[1]);
		
		List li = query.list();
		return li;
	}
}
