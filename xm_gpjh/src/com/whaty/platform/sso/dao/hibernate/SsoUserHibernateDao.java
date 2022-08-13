package com.whaty.platform.sso.dao.hibernate;

import java.util.List;

import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.sso.bean.SsoUser;
import com.whaty.platform.sso.dao.SsoUserDao;

/**
 * @author lwx 2008-7-27
 * @email  <p>liweixin@whaty.com</p>
 *
 */
public class SsoUserHibernateDao extends AbstractEntityHibernateDao<SsoUser,String> implements
		SsoUserDao {
	
	private static final String LOAD_BY_LOGINID = "from SsoUser u where u.loginId = ?";

	public SsoUserHibernateDao() {
		this.entityClass=SsoUser.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
	
	/**
	 * 根据登陆id查找用户
	 * @param loginId
	 * @return
	 */
	public SsoUser getByLoginId(String loginId){
		List<SsoUser> list = this.getHibernateTemplate().find(LOAD_BY_LOGINID, loginId);
		if(list !=null && !list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
}
