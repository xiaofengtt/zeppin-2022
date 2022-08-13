package com.whaty.platform.entity.service.imp;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeArea;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeInfoNews;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.bean.PrStuMultiMajor;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.WhatyuserLog4j;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.sms.SmsSendThread;
import com.whaty.platform.util.Const;
import com.whaty.util.string.AttributeManage;
import com.whaty.util.string.WhatyAttributeManage;
import com.whaty.platform.entity.bean.SmsInfo;
import com.whaty.platform.entity.bean.SmsSystempoint;

public class GeneralServiceImp<T extends AbstractBean> implements
		GeneralService<T> {

	private GeneralDao<T> generalDao;

	public GeneralDao<T> getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao<T> generalDao) {
		this.generalDao = generalDao;
	}

	public void delete(T persistentInstance) throws EntityException {
		try {
			this.generalDao.delete(persistentInstance);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}

	}

	public int deleteByIds(List ids) throws EntityException {
		int i = 0;
		try {
			i = this.generalDao.deleteByIds(ids);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return i;
	}

	public int executeByHQL(String hql) throws EntityException {
		int i = 0;
		try {
			i = this.generalDao.executeByHQL(hql);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return i;
	}

	public int executeBySQL(String sql) throws EntityException {
		int i = 0;
		try {
			i = this.generalDao.executeBySQL(sql);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return i;
	}

	public List getByExample(T instance) throws EntityException {
		List list = null;
		try {
			list = this.generalDao.getByExample(instance);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return list;
	}

	public List getByHQL(String hql) throws EntityException {
		List list = null;
		try {
			list = this.generalDao.getByHQL(hql);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return list;
	}

	public T getById(String id) throws EntityException {
		T instance = null;
		try {
			instance = this.generalDao.getById(id);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return instance;
	}
	
	public T getById(Class clazz,String id) throws EntityException {
		T instance = null;
		try {
			instance = this.generalDao.getById(clazz,id);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return instance;
	}

	public Page getByPage(DetachedCriteria detachedCriteria, int pageSize,
			int startIndex) throws EntityException {
		Page page = null;
		try {
			page = this.generalDao.getByPage(detachedCriteria, pageSize,
					startIndex);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return page;
	}

	public Page getByPageSQL(String sql, int pageSize, int startIndex)
			throws EntityException {
		Page page = null;
		try {
			page = this.generalDao.getByPageSQL(sql, pageSize, startIndex);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return page;
	}

	public List getBySQL(String sql) throws EntityException {
		List list = null;
		try {
			list = this.generalDao.getBySQL(sql);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return list;
	}

	public List getList(DetachedCriteria detachedCriteria)
			throws EntityException {
		List list = null;
		try {
			list = this.generalDao.getList(detachedCriteria);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return list;
	}

	public T save(T transientInstance) throws EntityException {
		T instance = null;
		try {
			instance = this.generalDao.save(transientInstance);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return instance;
	}

	public List saveList(List list) throws EntityException {
		for (int i = 0; i < list.size(); i++) {
			list.set(i, save((T) list.get(i)));
		}
		return list;
	}

	public int updateColumnByIds(List ids, String column, String value)
			throws EntityException {
		int i = 0;
		try {
			i = this.generalDao.updateColumnByIds(ids, column, value);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return i;
	}

	public void saveError() throws EntityException {
		throw new EntityException();
	}

	public PeBulletin getPeBulletin(String id) throws EntityException {

		return this.getGeneralDao().getPeBulletin(id);
	}

	public PeInfoNews getPeInfoNews(String id) throws EntityException {
		// TODO Auto-generated method stub
		return this.getGeneralDao().getPeInfoNews(id);
	}

	public List getNewBulletins(String id) {

		return this.getGeneralDao().getNewBulletins(id);
	}

	public PeEnterprise getSubEnterprise(String id) {
		return this.getGeneralDao().getSubEnterprise(id);
	}

	public List getStuBulletins() {

		return this.getGeneralDao().getStuBulletins();
	}
	public PeTrainee getStudentInfo(DetachedCriteria studc) {
		return this.getGeneralDao().getStudentInfo(studc);
	}
	
	public void update(T persistentInstance) {
		 this.generalDao.update(persistentInstance);
	}
	public void updateSsoUser(SsoUser ssoUser) {
		this.generalDao.updateSsoUser(ssoUser);
	}
	public void updatePeEnterpriseManager(PeEnterpriseManager enterpriseManager) {
		this.generalDao.updatePeEnterpriseManager(enterpriseManager);
	}
	
	public void updatePeManager(PeManager peManager) {
		this.generalDao.updatePeManager(peManager);
	}
	
	public void updateEnterpriseManager(PeEnterpriseManager enterpriseManager) {
		this.generalDao.updateEnterpriseManager(enterpriseManager);
	}

	public List getDetachList(DetachedCriteria detachedCriteria)
		throws EntityException {
	    return this.getGeneralDao().getDetachList(detachedCriteria);
	}
	public List getBySQL(String sql, Map map) throws EntityException {
		// TODO Auto-generated method stub
		return this.getGeneralDao().getBySQL(sql, map);
	}

	public int executeBySQL(String sql, Map map) throws EntityException {
		int i = 0;
		try{
			i = this.generalDao.executeBySQL(sql,map);
		}catch(RuntimeException e){
			throw new EntityException(e);
		}
		return i;
	}

	public Page getByPageSQL(String sql, int pageSize, int startIndex, Map map)
			throws EntityException {
		Page page = null;
		try{
			page = this.generalDao.getByPageSQL(sql, pageSize, startIndex,map);
		}catch(RuntimeException e){
			throw new EntityException(e);
		}
		return page;
	}

	
	public WhatyuserLog4j saveLog(WhatyuserLog4j whatyuserLog4j)
			throws EntityException {
		WhatyuserLog4j instance = null;
		try {
			instance = this.generalDao.saveLog(whatyuserLog4j);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return instance;
	}
	
	public PeArea savePeArea(PeArea pa) throws EntityException {
		PeArea instance = null;
		try {
			instance = this.generalDao.savePeArea(pa);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return instance;
	}
	public List<String> canApplyHigherTrainingType(String loginId)
		throws EntityException {
		List<String> resultList=new LinkedList<String>();
		try{
			;
		}
		catch(Exception e){
			throw new EntityException(e);
		} 
		if(false)
			return resultList;
		return resultList;
	}

	
	public boolean sendSystemSms(String smsId,String mobile) throws EntityException {
		SmsSystempoint sms=new SmsSystempoint();
		this.getGeneralDao().setEntityClass(SmsSystempoint.class);
		sms=(SmsSystempoint) this.getById(smsId);
		String content = sms.getContent();
		SmsSendThread sendThread = new SmsSendThread(mobile,
				content);
		sendThread.start();
		return false;
	} 
	public static void main (String[] args){
		System.out.println("ddddddddddddd");
	}
}
