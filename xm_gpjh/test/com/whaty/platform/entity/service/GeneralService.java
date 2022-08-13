package com.whaty.platform.entity.service;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.PeArea;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzSuggestion;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeInfoNews;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.WhatyuserLog4j;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;

public interface GeneralService<T extends AbstractBean> {

	public T save(T transientInstance) throws EntityException;
	
	/**
	 * 批量保存
	 * @param list
	 * @return
	 * @throws EntityException
	 */
	public List saveList(List list) throws EntityException;
	/**
	 * 存储日志
	 * @param whatyuserLog4j
	 * @return
	 * @throws EntityException
	 */
	public WhatyuserLog4j saveLog(WhatyuserLog4j whatyuserLog4j) throws EntityException;

	public void delete(T persistentInstance) throws EntityException;

	public int deleteByIds(List ids) throws EntityException;

	public int updateColumnByIds(List ids, String column, String value)
			throws EntityException;

	public T getById(String id) throws EntityException;
	
	public T getById(Class clazz,String id) throws EntityException;
	
	public PeInfoNews getPeInfoNews(String id) throws EntityException;
	
	public PeBulletin getPeBulletin(String id) throws EntityException;

	public List getByExample(final T instance) throws EntityException;

	public List getList(DetachedCriteria detachedCriteria) throws EntityException;

	public Page getByPage(DetachedCriteria detachedCriteria, int pageSize,
			int startIndex) throws EntityException;

	public List getBySQL(final String sql) throws EntityException;
	
	public List getBySQL(final String sql,final Map map) throws EntityException;

	public Page getByPageSQL(String sql, int pageSize, int startIndex) throws EntityException;
	
	public Page getByPageSQL(String sql, int pageSize, int startIndex,Map map) throws EntityException;

	public List getByHQL(final String hql) throws EntityException;

	public int executeByHQL(final String hql) throws EntityException;

	public int executeBySQL(final String sql) throws EntityException;
	
	public int executeBySQL(final String sql,final Map map) throws EntityException;
	
	public void saveError() throws EntityException;

	public GeneralDao<T> getGeneralDao() ;

	public List getNewBulletins(String id) throws EntityException;
	/**
	 * @getDetachList(DetachedCriteria detachedCriteria)
	 * @该方法返回是托管状态的List!
	 */
	public List getDetachList(DetachedCriteria detachedCriteria) throws EntityException;

	public PeEnterprise getSubEnterprise(String id) throws EntityException;;
	
	public List getStuBulletins() throws EntityException;;
	
	public PeTrainee getStudentInfo(DetachedCriteria studc) throws EntityException;;

	public void update(T persistentInstance) throws EntityException;;

	public void updateSsoUser(SsoUser ssoUser) throws EntityException;;

	public void updatePeEnterpriseManager(PeEnterpriseManager enterpriseManager) throws EntityException;;

	public void updatePeManager(PeManager peManager) throws EntityException;;

	public void updateEnterpriseManager(PeEnterpriseManager enterpriseManager) throws EntityException;;
	
	public List<String> canApplyHigherTrainingType(String loginId) throws EntityException;;

	public PeArea savePeArea(PeArea pa) throws EntityException;
	
	/**
	 * 用于发送系统短信，根据系统短信ID
	 * @param smsId
	 * @return boolean succ
	 * @throws EntityException
	 */
	public boolean sendSystemSms(String smsId,String mobile) throws EntityException;
}
