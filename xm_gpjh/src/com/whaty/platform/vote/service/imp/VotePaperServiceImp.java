package com.whaty.platform.vote.service.imp;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.vote.bean.VotePaper;
import com.whaty.platform.vote.dao.VotePaperDao;
import com.whaty.platform.vote.service.VotePaperService;
import com.whaty.platform.vote.util.exception.VoteException;

public class VotePaperServiceImp implements VotePaperService {
	
	private  static final Log logger = LogFactory.getLog(VotePaperServiceImp.class);
	
	private VotePaperDao votePaperDao;
	
	public VotePaperDao getVotePaperDao() {
		return votePaperDao;
	}

	public void setVotePaperDao(VotePaperDao votePaperDao) {
		this.votePaperDao = votePaperDao;
	}

	/**
	 * 保存
	 * @param transientInstance
	 * @return
	 */
    public VotePaper save(VotePaper transientInstance)throws VoteException{
    	VotePaper vote = null;
    	try{
    		vote = getVotePaperDao().save(transientInstance);
    	}catch(Exception e){
    		logger.error(e);
    		throw new VoteException();
    	}
    	return vote;
    }
    
    /**
     * 根据id列表 删除
     * @param ids
     */
    public int deleteByIds(final List ids)throws VoteException{
    	int c = 0; 
    	try{
    		c = getVotePaperDao().deleteByIds(ids);
    	}catch(Exception e){
    		logger.error(e);
    		throw new VoteException();
    	}
    	return c;
    }
    
    /**
     * 删除单个
     * @param persistentInstance
     */
	public void delete(VotePaper persistentInstance)throws VoteException{
		try{
			
			getVotePaperDao().delete(persistentInstance);
			
		}catch(Exception e){
    		logger.error(e);
    		throw new VoteException();
    	}
	}
	
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
    public VotePaper getById( String id){
    	return getVotePaperDao().getById(id);
    }
    
    /**
     * 根据样例
     * @param instance
     * @return
     */
    public List getByExample(final VotePaper instance){
    	return getVotePaperDao().getByExample(instance);
    }
    
    /**
     * 根据条件查找
     * @param detachedCriteria
     * @return
     */
     public List getList(final DetachedCriteria detachedCriteria){
    	 return getVotePaperDao().getList(detachedCriteria);
     }
     
     /**
 	 * 根据条件分页获得
 	 */
 	public Page getsByPage(final DetachedCriteria detachedCriteria,
 			final int pageSize, final int startIndex){
 		return getVotePaperDao().getByPage(detachedCriteria, pageSize, startIndex);
 	}
 	
 	/**
 	 * 批量更新一个字段为某??
 	 * @param ids
 	 * @param column
 	 * @param value
 	 * @return
 	 */
 	public int updateColumnByIds(final List ids,final String column,final String value)throws VoteException{
 		int c = 0 ;
 		try{
 			c = getVotePaperDao().updateColumnByIds(ids, column, value);
 		
 		}catch(Exception e){
    		logger.error(e);
    		throw new VoteException();
    	}
 		
 		return c;
 	}
 	
 	/**
 	 * 使用sql查询的接口
 	 * @param sql
 	 * @return
 	 */
 	public List getBySQL(String sql){
 		return getVotePaperDao().getBySQL(sql);
 	}
 	
 	public List getByHQL(String sql){
 		return getVotePaperDao().getByHQL(sql);
 	}
 	
 	public int executeByHQL(final String hql){
 		return executeByHQL(hql);
 	}
 	
 	public int executeBySQL(final String sql){
 		return executeBySQL(sql);
 	}

}
