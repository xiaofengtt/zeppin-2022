/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jgroups.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsLogDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITSsLogService;
import cn.zeppin.product.itic.backadmin.vo.TSsOperatorVO;
import cn.zeppin.product.itic.core.entity.TSsLog;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogStatus;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogType;
import cn.zeppin.product.itic.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.ReflectUtlity;
import cn.zeppin.product.utility.Utlity;

@Service
public class TSsLogService extends BaseService implements ITSsLogService {
	
	@Autowired
	private ITSsLogDAO  TSsLogDAO;

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public TSsLog get(String id) {
		return TSsLogDAO.get(id);
	}
	
	@Override
	public TSsLog insert(TSsLog t) {
		return TSsLogDAO.insert(t);
	}

	@Override
	public TSsLog delete(TSsLog t) {
		return TSsLogDAO.delete(t);
	}

	@Override
	public TSsLog update(TSsLog t) {
		return TSsLogDAO.update(t);
	}
	
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return TSsLogDAO.getCount(inputParams);
	}

	@Override
	public List<TSsLog> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts) {
		return TSsLogDAO.getListForPage(inputParams, pageNum, pageSize, sorts);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void rollback(TSsLog t, TSsOperatorVO operator) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Class cla1 = Class.forName("cn.zeppin.product.itic.core.entity."+t.getDatatype());
			if(t.getOlddata() == null){
				Object obj = session.get(cla1, t.getDataid());
				
				TSsLog log = new TSsLog();
				log.setId(UUID.randomUUID().toString());
				log.setDatatype(t.getDatatype());
				log.setDataid(t.getDataid());
				log.setDataproduct(t.getDataproduct());
				log.setOlddata(Utlity.StringToClob(JSONUtils.obj2json(obj)));
				log.setType(TSsLogType.ROLLBACK);
				log.setCreatetime(new Timestamp(System.currentTimeMillis()));
				log.setCreator(operator.getId());
				log.setStatus(TSsLogStatus.NORMAL);
				this.TSsLogDAO.insert(log);
				
				ReflectUtlity.invokeSet(obj, "status", "delete");
				session.update(obj);
				
			}else {
				Object obj = session.get(cla1, t.getDataid());
				
				if(obj != null){
					TSsLog log = new TSsLog();
					log.setId(UUID.randomUUID().toString());
					log.setDatatype(t.getDatatype());
					log.setDataid(t.getDataid());
					log.setDataproduct(t.getDataproduct());
					log.setOlddata(Utlity.StringToClob(JSONUtils.obj2json(obj)));
					log.setNewdata(t.getOlddata());
					log.setType(TSsLogType.ROLLBACK);
					log.setCreatetime(new Timestamp(System.currentTimeMillis()));
					log.setCreator(operator.getId());
					log.setStatus(TSsLogStatus.NORMAL);
					this.TSsLogDAO.insert(log);
					
					Object data = JSONUtils.json2obj(Utlity.ClobToString(t.getOlddata()), cla1);
					ReflectUtlity.invokeSet(data, "updatetime", new Timestamp(System.currentTimeMillis()));
					Map<String,Object> dataMap = JSONUtils.json2map(Utlity.ClobToString(t.getOlddata()));
					for(String key : dataMap.keySet()){
						ReflectUtlity.invokeSet(obj, key, ReflectUtlity.invokeGet(data, key));
					}
					session.update(obj);
				}
			}
			
			t.setStatus(TSsLogStatus.ROLLBACK);
			this.TSsLogDAO.update(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
