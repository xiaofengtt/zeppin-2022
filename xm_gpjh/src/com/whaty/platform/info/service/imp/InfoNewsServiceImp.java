package com.whaty.platform.info.service.imp;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.info.bean.InfoNews;
import com.whaty.platform.info.dao.InfoNewsDAO;
import com.whaty.platform.info.service.InfoNewsService;

public class InfoNewsServiceImp implements InfoNewsService {
	
	private InfoNewsDAO infoNewsDao;


	/**
	 * 保存
	 * @param transientInstance
	 */
    public InfoNews save(InfoNews transientInstance){
    	return this.infoNewsDao.save(transientInstance);
    }
    
    /**
     * 批量更新活动状态
     */
    public int updateActiveStatusByIds(final List ids){
    	return infoNewsDao.updateActiveStatusByIds(ids);
    }
    
    /**
     * 批量更新置顶状态
     */
    public int updateTopStatusByIds(final List ids){
    	return infoNewsDao.updateTopStatusByIds(ids);
    }
    
    /**
     * 批量更新弹出状态
     */
    public int updatePopStatusByIds(final List ids){
    	return infoNewsDao.updatePopStatusByIds(ids);
    }
    
    /**
     * 批量更新审核状态
     */
    public int updateConfirmStatusByIds(final List ids){
    	return infoNewsDao.updateConfirmStatusByIds(ids);
    }
    
    /**
     * 根据id列表 删除新闻信息
     * @param ids
     */
    public void deleteInfoNewsByIds(final List ids){
    	this.infoNewsDao.deleteInfoNewsByIds(ids);
    }
    
    /**
     * 删除
     * @param persistentInstance
     */
	public void delete(InfoNews persistentInstance){
		this.infoNewsDao.delete(persistentInstance);
	}
	
	/**
	 * 根据id获得新闻信息
	 * @param id
	 * @return
	 */
    public InfoNews getInfoNewsById( java.lang.String id){
    	return this.infoNewsDao.getInfoNewsById(id);
    }
    
    /**
     * 根据样例条件获得新闻信息
     * @param instance
     * @return
     */
    public List getInfoNewsByExample(final InfoNews instance){
    	return this.infoNewsDao.getInfoNewsByExample(instance);
    }
    
    /**
     * 根据条件查找新闻信息
     * @param detachedCriteria
     * @return
     */
   public List getInfoNewsList(final DetachedCriteria detachedCriteria){
	   return this.getInfoNewsDao().getInfoNewsList(detachedCriteria);
   }
   
   /**
	 * 根据条件分页获得新闻信息
	 */
	public Page getInfoNewsByPage(final DetachedCriteria detachedCriteria,
			final int pageSize, final int startIndex){
		return this.infoNewsDao.getInfoNewsByPage(detachedCriteria,pageSize,startIndex);
	}
	
	/**
	 * 根据条件获得新闻总数
	 * @param detachedCriteria
	 * @return
	 */
	public Integer getInfoNewsTotalCount(final DetachedCriteria detachedCriteria){
		return this.infoNewsDao.getInfoNewsTotalCount(detachedCriteria);
	}
   

	public InfoNewsDAO getInfoNewsDao() {
		return infoNewsDao;
	}

	public void setInfoNewsDao(InfoNewsDAO infoNewsDao) {
		this.infoNewsDao = infoNewsDao;
	}

	

}
