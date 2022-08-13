package com.whaty.platform.info.service.imp;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.info.bean.InfoNewsType;
import com.whaty.platform.info.dao.InfoNewsTypeDao;
import com.whaty.platform.info.exception.InfoException;
import com.whaty.platform.info.service.InfoNewsTypeService;

public class InfoNewsTypeServiceImp implements InfoNewsTypeService {
	
	private static final Log logger = LogFactory.getLog(InfoNewsTypeServiceImp.class);
	
	private InfoNewsTypeDao infoNewsTypeDao;
	
	public InfoNewsTypeDao getInfoNewsTypeDao() {
		return infoNewsTypeDao;
	}

	public void setInfoNewsTypeDao(InfoNewsTypeDao infoNewsTypeDao) {
		this.infoNewsTypeDao = infoNewsTypeDao;
	}

	/**
	 * 保存新闻类型
	 * @param transientInstance
	 * @return
	 */
    public InfoNewsType save(InfoNewsType transientInstance)throws InfoException{
    	InfoNewsType newsType = null;
    	try{
    		newsType = this.infoNewsTypeDao.save(transientInstance);
    	}catch(Exception e){
    		logger.error(e);
    		throw new InfoException();
    	}
    	return newsType;
    }
    
    /**
     * 根据id列表 删除新闻信息
     * @param ids
     */
    public void deleteInfoNewsTypeByIds(final List ids)throws InfoException{
    	try{
    		this.infoNewsTypeDao.deleteInfoNewsTypeByIds(ids);
    	}catch(Exception e){
    		logger.error(e);
    		throw new InfoException(e);
    	}
    	
    }
    
    /**
     * 删除单个新闻类型
     * @param persistentInstance
     */
	public void delete(InfoNewsType persistentInstance)throws InfoException{
		try{
			this.infoNewsTypeDao.delete(persistentInstance);
		}catch(Exception e){
			logger.error(e);
			throw new InfoException();
		}
		
	}
	
	/**
	 * 根据id查找新闻类型
	 * @param id
	 * @return
	 */
    public InfoNewsType getInfoNewsTypeById( java.lang.String id){
    	return this.infoNewsTypeDao.getInfoNewsTypeById(id);
    }
    
    /**
     * 根据样例查找新闻;
     * @param instance
     * @return
     */
    public List getInfoNewsTypeByExample(final InfoNewsType instance){
    	return this.infoNewsTypeDao.getInfoNewsTypeByExample(instance);
    }
    
    /**
     * 根据条件查找新闻类型
     * @param detachedCriteria
     * @return
     */
     public List getInfoNewsTypeList(final DetachedCriteria detachedCriteria){
    	 return this.infoNewsTypeDao.getInfoNewsTypeList(detachedCriteria);
     }
     
     /**
  	 * 根据条件分页获得新闻类型
  	 */
  	public Page getInfoNewsTypeByPage(final DetachedCriteria detachedCriteria,
  			final int pageSize, final int startIndex){
  		return this.infoNewsTypeDao.getInfoNewsTypeByPage(detachedCriteria,pageSize,startIndex);
  	}
  	
  	/**
 	 * 根据条件获得新闻总数
 	 * @param detachedCriteria
 	 * @return
 	 */
 	public Integer getInfoNewsTypeTotalCount(final DetachedCriteria detachedCriteria){
 		return this.infoNewsTypeDao.getInfoNewsTypeTotalCount(detachedCriteria);
 	}

}
