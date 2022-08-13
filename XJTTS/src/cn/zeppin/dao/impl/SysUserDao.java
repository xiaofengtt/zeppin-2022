package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import cn.zeppin.dao.ISysUserDao;
import cn.zeppin.entity.SysUser;

@SuppressWarnings("rawtypes")
public class SysUserDao extends BaseDaoImpl<SysUser, Integer> implements ISysUserDao {
	public List findByAll(Object[] pars, Integer role) {
		// TODO Auto-generated method stub

		// String hql =
		// "from SysUser t where (t.id.idcard=? or t.id.mobile=? or t.id.email=?) and t.id.password=?";

//		String sql = "select * from sys_user t where (t.idcard=? or t.mobile=? or t.email=?) and t.password=? and t.role=?";
//
//		Query query = this.getCurrentSession().createSQLQuery(sql);
//		query.setParameter(0, pars[0]);
//		query.setParameter(1, pars[0]);
//		query.setParameter(2, pars[0]);
//		query.setParameter(3, pars[1]);
//		query.setParameter(4, pars[2]);
//
//		List li = query.list();
//		return li;
		
		//20160929-优化登陆接口
		String sql = "";
		Query query = null;
		if(role == 5){
			sql = "select * from sys_user t where t.role=5 and (t.idcard=? or t.mobile=? or t.email=?) and t.password=?  ";
//			query = this.getCurrentSession().createSQLQuery(sql);
			
		}else {
			sql = "select t.id,t.idcard,t.mobile,t.email,t.password, ";
			if(role == 1){
				sql += "1 as 'role',t.name,t.organization,t.CREATEUSER,t.level,t.status,t.creator,t.creattime from project_admin";
			}else if(role == 2){
				sql += "2 as 'role',t.name,t.organization,t.CREATEUSER,1 as 'level',t.status,t.creator,t.creattime from training_admin";
			}else if(role == 4){
				sql += "4 as 'role',t.name,0 as 'organization',0 as 'CREATEUSER',1 as 'level',t.status,t.creator,t.creattime from project_expert";
			}else{
				sql += "3 as 'role',t.name,t.organization,0 as 'CREATEUSER',1 as 'level',t.status,t.creator,t.creattime from teacher";
			}
			sql += " t where (t.idcard=? or t.mobile=? or t.email=?) and t.password=? ";
			
		}
		query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter(0, pars[0]);
		query.setParameter(1, pars[0]);
		query.setParameter(2, pars[0]);
		query.setParameter(3, pars[1]);

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


	@Override
	public List findByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		
		String sql = "select * from connections t where (t.idcard=? or t.mobile=? or t.email=? or t.name=?)";
		Query query = this.getCurrentSession().createSQLQuery(sql);
		if(params != null && params.containsKey("search")){
			String search = params.get("search").toString();
			query.setParameter(0,search);
			query.setParameter(1, search);
			query.setParameter(2, search);
			query.setParameter(3, search);
		}

		
		List li = query.list();
		return li;
	}
}
