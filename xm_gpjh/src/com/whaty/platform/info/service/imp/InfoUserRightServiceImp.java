package com.whaty.platform.info.service.imp;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.info.bean.InfoNewsType;
import com.whaty.platform.info.bean.InfoUserRight;
import com.whaty.platform.info.dao.InfoNewsTypeDao;
import com.whaty.platform.info.dao.InfoUserRigthDao;
import com.whaty.platform.info.exception.InfoException;
import com.whaty.platform.info.service.InfoUserRightService;
import com.whaty.platform.sso.bean.SsoUser;
import com.whaty.platform.sso.dao.SsoUserDao;

public class InfoUserRightServiceImp implements InfoUserRightService {

	private static final Log logger = LogFactory.getLog(InfoUserRightServiceImp.class);
	
	private InfoUserRigthDao infoUserRightDao;
	private SsoUserDao ssoUserDao;
	private InfoNewsTypeDao infoNewsTypeDao;

	public InfoNewsTypeDao getInfoNewsTypeDao() {
		return infoNewsTypeDao;
	}

	public void setInfoNewsTypeDao(InfoNewsTypeDao infoNewsTypeDao) {
		this.infoNewsTypeDao = infoNewsTypeDao;
	}

	public SsoUserDao getSsoUserDao() {
		return ssoUserDao;
	}

	public void setSsoUserDao(SsoUserDao ssoUserDao) {
		this.ssoUserDao = ssoUserDao;
	}

	public InfoUserRigthDao getInfoUserRightDao() {
		return infoUserRightDao;
	}

	public void setInfoUserRightDao(InfoUserRigthDao infoUserRightDao) {
		this.infoUserRightDao = infoUserRightDao;
	}

	/**
	 * 保存
	 * @param transientInstance
	 * @return
	 */
    public InfoUserRight save(InfoUserRight transientInstance)throws InfoException{
    	InfoUserRight userRight = null;
    	try{
    		userRight = getInfoUserRightDao().save(transientInstance);
    	}catch(Exception e){
    		logger.error(e);
    		throw new InfoException();
    	}
    	return userRight;
    }
    
    /**
     * 根据id列表 删除
     * @param ids
     */
    public int deleteByIds(final List ids)throws InfoException{
    	
    	int count = 0;
    	try{
    		count = getInfoUserRightDao().deleteByIds(ids);
    	}catch(Exception e){
    		logger.error(e);
    		throw new InfoException();
    	}
    	return count;
    }
    
    /**
     * 删除单个
     * @param persistentInstance
     */
	public void delete(InfoUserRight persistentInstance)throws InfoException{
		try{
			getInfoUserRightDao().delete(persistentInstance);
		}catch(Exception e){
			logger.error(e);
			throw new InfoException();
		}
	}
	
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
    public InfoUserRight getById( String id){
    	return getInfoUserRightDao().getById(id);
    }
    
    /**
     * 根据样例
     * @param instance
     * @return
     */
    public List getByExample(final InfoUserRight instance){
    	return getInfoUserRightDao().getByExample(instance);
    }
    
    /**
     * 根据条件查找
     * @param detachedCriteria
     * @return
     */
     public List getList(final DetachedCriteria detachedCriteria){
    	 return getInfoUserRightDao().getList(detachedCriteria);
     }
     
     /**
 	 * 根据条件分页获得
 	 */
 	public Page getByPage(final DetachedCriteria detachedCriteria,
 			final int pageSize, final int startIndex){
 		return getInfoUserRightDao().getByPage(detachedCriteria, pageSize, startIndex);
 	}
 	
 	/**
 	 * 批量更新一个字段为某??
 	 * @param ids
 	 * @param column
 	 * @param value
 	 * @return
 	 */
 	public int updateColumnByIds(final List ids,final String column,final String value)throws InfoException{
 		int count = 0 ;
 		try{
 			count = getInfoUserRightDao().updateColumnByIds(ids, column, value);
 		}catch(Exception e){
 			logger.error(e);
 			throw new InfoException();
 		}
 		return count;
 	}
 	
 	/**
 	 * 使用sql查询的接口
 	 * @param sql
 	 * @return
 	 */
 	public List getBySQL(String sql){
 		return getInfoUserRightDao().getBySQL(sql);
 	}
 	
 	public List getByHQL(String sql){
 		return getInfoUserRightDao().getByHQL(sql);
 	}
 	
 	/**
 	 * 设置用户新闻类型权限
 	 */
 	public int updateInfoUserRight(String userId,List pageNewsTypeIds,List selectedNewsTypeIds)throws InfoException{
 		int count = 0 ;
 		
 		try{
// 			删除含有页面的用户权限新闻类型
 			getInfoUserRightDao().deleteByNewsTypeIds(pageNewsTypeIds);
 			
 			//插入新选择的新闻类型。
 			SsoUser user = getSsoUserDao().getById(userId);
 			InfoNewsType newsType = null;
 			String id = null;
 			Iterator it = selectedNewsTypeIds.iterator();
 			while(it.hasNext()){
 				id = (String)it.next();
 				newsType = getInfoNewsTypeDao().getInfoNewsTypeById(id);
 				InfoUserRight userRight = new InfoUserRight();
 				userRight.setSsoUser(user);
 				userRight.setInfoNewsType(newsType);
 				getInfoUserRightDao().save(userRight);
 				count++;
 			}
 			
 			if(count != selectedNewsTypeIds.size()){
 				throw new InfoException();
 			}
 		}catch(Exception e){
 			logger.error(e);
 			throw new InfoException();
 		}
 		return count;
 	}
 	
 	/**
	 * 根据newtype 列表删除记录
	 */
	public int deleteByNewsTypeIds(List newsTypeIds)throws InfoException{
		int c = 0;
		try{
			c = getInfoUserRightDao().deleteByNewsTypeIds(newsTypeIds);
		}catch(Exception e){
			logger.error(e);
			throw new InfoException();
		}
		return c;
	}

}
