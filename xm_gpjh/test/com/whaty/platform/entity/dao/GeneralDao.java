package com.whaty.platform.entity.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeArea;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeBzzAssess;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzCourseFeedback;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzSuggestion;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeInfoNews;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.bean.WhatyuserLog4j;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.exception.EntityException;

public interface GeneralDao<T extends AbstractBean> {
	/**
	 * 保存
	 * @param transientInstance
	 * @return
	 */
    public T save(T transientInstance);
    
    /**
     * 存储日志
     * @param whatyuserLog4j
     * @return
     */
    public WhatyuserLog4j saveLog(WhatyuserLog4j whatyuserLog4j);
    /**
     * 存储省市县地区
     * 
     * @param pa
     * @return
     */
    public PeArea savePeArea(PeArea pa);
    
    /**
     * 根据id列表 删除
     * @param ids
     */
    public int deleteByIds(final List ids);
    
    /**
     * 删除单个
     * @param persistentInstance
     */
	public void delete(T persistentInstance);
	
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
    public T getById( String id);
    
    //处理高并发出错问题
    public T getById(Class clazz,String id);
    
    /**
     * 根据样例
     * @param instance
     * @return
     */
    public List getByExample(final T instance);
    
    /**
     * 根据条件查找
     * @param detachedCriteria
     * @return
     */
     public List getList(final DetachedCriteria detachedCriteria);
     
     /**
 	 * 根据条件分页获得
 	 */
 	public Page getByPage(final DetachedCriteria detachedCriteria,
 			final int pageSize, final int startIndex);
 	
 	/**
 	 * 批量更新一个字段为某??
 	 * @param ids
 	 * @param column
 	 * @param value
 	 * @return
 	 */
 	public int updateColumnByIds(final List ids,final String column,final String value);
 	
 	/**
 	 * 使用sql查询的接口
 	 * @param sql
 	 * @return
 	 */
 	public List getBySQL(final String sql);
 	
 	public List getBySQL(final String sql,final Map map);
 	/**
 	 * 根据sql分页获得
 	 * @param sql
 	 * @param pageSize
 	 * @param startIndex
 	 * @return
 	 */
 	public Page getByPageSQL(String sql, int pageSize, int startIndex);
 	
 	public Page getByPageSQL(String sql, int pageSize, int startIndex,final Map map);
 	
 	public List getByHQL(final String hql);
 	
 	public int executeByHQL(final String hql);
 	
 	public int executeBySQL(final String sql);
 	
 	public int executeBySQL(final String sql,final Map map);
 	
 	public EnumConst getEnumConstByNamespaceCode(String namespace, String code);
 	
 	public void setEntityClass(Class entityClass);
 	
 	public HibernateTemplate getMyHibernateTemplate();

	public PeBulletin getPeBulletin(String id);

	public PeInfoNews getPeInfoNews(String id);

	public List getNewBulletins(String id);

	public PeSitemanager getPeSitemanager(String loginId);

	public PeEnterprise getSubEnterprise(String id);

	public List getStuBulletins();
	
	public PeEnterpriseManager getEnterprisemanager(String loginId);

	public void saveSsoUser(SsoUser sso);

	public void delete(SsoUser ssoUser);

	public PeTrainee getStudentInfo(DetachedCriteria studc);

	public PrBzzTchOpencourse getPrBzzTchOpencourse(DetachedCriteria pdc);

	public List<PeBzzCourseFeedback> getFeeDbackList(DetachedCriteria feeDback);
	
	public PeBzzSuggestion getPeBzzSuggestion(String sugid);

	public void updatepeBzzSuggestion(PeBzzSuggestion peBzzSuggestion);

	public void updatePeBzzAssess(PeBzzAssess assess);

	public void updateSsoUser(SsoUser ssoUser);

	public void updatePeEnterpriseManager(PeEnterpriseManager enterpriseManager);

	public void updatePeManager(PeManager peManager);

	public void updateEnterpriseManager(PeEnterpriseManager enterpriseManager);

	public void updateelective(TrainingCourseStudent trainingCourseStudent);

	public PeBzzBatch getPeBzzBatch(DetachedCriteria criteria);
	
	public void update(T persistentInstance);

	public List getDetachList(DetachedCriteria detachedCriteria);
	
	public void saveTest(T entity,String id);

}
