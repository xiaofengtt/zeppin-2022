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

import cn.zeppin.product.itic.backadmin.dao.api.ITCheckInfoDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITSsLogDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITSyncDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITCheckInfoService;
import cn.zeppin.product.itic.backadmin.service.api.ITGgGdxxbService;
import cn.zeppin.product.itic.backadmin.service.api.ITGgJgxxbService;
import cn.zeppin.product.itic.backadmin.service.api.ITGgYgxxbService;
import cn.zeppin.product.itic.backadmin.service.api.ITGyGydbgxbService;
import cn.zeppin.product.itic.backadmin.service.api.ITGyGydbhtbService;
import cn.zeppin.product.itic.backadmin.service.api.ITGyGydzywbService;
import cn.zeppin.product.itic.backadmin.service.api.ITGyGyzhxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITGyGyzjyyjylService;
import cn.zeppin.product.itic.backadmin.service.api.ITGyGyzzyyhtxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITJyQjglxxfzqService;
import cn.zeppin.product.itic.backadmin.service.api.ITJyQjglxxzqService;
import cn.zeppin.product.itic.backadmin.service.api.ITJyXtsypzService;
import cn.zeppin.product.itic.backadmin.service.api.ITJyXtsyqzrxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITJyXtzjmjjfplService;
import cn.zeppin.product.itic.backadmin.service.api.ITJyXtzjyyjylService;
import cn.zeppin.product.itic.backadmin.service.api.ITKhDsfhzjgxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITKhJydsgrService;
import cn.zeppin.product.itic.backadmin.service.api.ITKhJydsjgService;
import cn.zeppin.product.itic.backadmin.service.api.ITKhJydsjggdxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITKhTzgwhtbService;
import cn.zeppin.product.itic.backadmin.service.api.ITKhXtkhgrService;
import cn.zeppin.product.itic.backadmin.service.api.ITKhXtkhjgService;
import cn.zeppin.product.itic.backadmin.service.api.ITKjGynbkmdzbService;
import cn.zeppin.product.itic.backadmin.service.api.ITKjGyzcfzkmtjbService;
import cn.zeppin.product.itic.backadmin.service.api.ITKjGyzzkjqkmbService;
import cn.zeppin.product.itic.backadmin.service.api.ITKjXtnbkmdzbService;
import cn.zeppin.product.itic.backadmin.service.api.ITKjXtxmzcfztjbService;
import cn.zeppin.product.itic.backadmin.service.api.ITKjXtxmzzkjqkmbService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmFdcjsxmxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmFfdcjsxmxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtdbgxbService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtdbhtbService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtdzywbService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtxmqsxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtxmsyqbService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtxmxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtxmyjhklypgbService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtzhxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtzjmjhtxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtzjyyhtxxService;
import cn.zeppin.product.itic.backadmin.vo.TSsOperatorVO;
import cn.zeppin.product.itic.core.entity.TCheckInfo;
import cn.zeppin.product.itic.core.entity.TCheckInfo.TCheckInfoType;
import cn.zeppin.product.itic.core.entity.TSsLog;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogStatus;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogType;
import cn.zeppin.product.itic.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.ReflectUtlity;
import cn.zeppin.product.utility.Utlity;

@Service
public class TCheckInfoService extends BaseService implements ITCheckInfoService {
	
	@Autowired
	private ITCheckInfoDAO  TCheckInfoDAO;

	@Autowired
	private ITSsLogDAO  TSsLogDAO;
	
	@Autowired
	private ITSyncDAO  TSyncDAO;
	
	@Autowired
	private ITGgGdxxbService TGgGdxxbService;
	
	@Autowired
	private ITGgJgxxbService TGgJgxxbService;
	
	@Autowired
	private ITGgYgxxbService TGgYgxxbService;
	
	@Autowired
	private ITGyGydbhtbService TGyGydbhtbService;
	
	@Autowired
	private ITGyGydbgxbService TGyGydbgxbService;
	
	@Autowired
	private ITGyGydzywbService TGyGydzywbService;
	
	@Autowired
	private ITGyGyzhxxService TGyGyzhxxService;
	
	@Autowired
	private ITGyGyzjyyjylService TGyGyzjyyjylService;
	
	@Autowired
	private ITGyGyzzyyhtxxService TGyGyzzyyhtxxService;
	
	@Autowired
	private ITJyQjglxxfzqService TJyQjglxxfzqService;
	
	@Autowired
	private ITJyQjglxxzqService TJyQjglxxzqService;
	
	@Autowired
	private ITJyXtsypzService TJyXtsypzService;
	
	@Autowired
	private ITJyXtsyqzrxxService TJyXtsyqzrxxService;
	
	@Autowired
	private ITJyXtzjmjjfplService TJyXtzjmjjfplService;
	
	@Autowired
	private ITJyXtzjyyjylService TJyXtzjyyjylService;
	
	@Autowired
	private ITKhDsfhzjgxxService TKhDsfhzjgxxService;
	
	@Autowired
	private ITKhJydsgrService TKhJydsgrService;
	
	@Autowired
	private ITKhJydsjgService TKhJydsjgService;
	
	@Autowired
	private ITKhJydsjggdxxService TKhJydsjggdxxService;
	
	@Autowired
	private ITKhTzgwhtbService TKhTzgwhtbService;
	
	@Autowired
	private ITKhXtkhgrService TKhXtkhgrService;
	
	@Autowired
	private ITKhXtkhjgService TKhXtkhjgService;
	
	@Autowired
	private ITKjGynbkmdzbService TKjGynbkmdzbService;
	
	@Autowired
	private ITKjGyzcfzkmtjbService TKjGyzcfzkmtjbService;
	
	@Autowired
	private ITKjGyzzkjqkmbService TKjGyzzkjqkmbService;
	
	@Autowired
	private ITKjXtnbkmdzbService TKjXtnbkmdzbService;
	
	@Autowired
	private ITKjXtxmzcfztjbService TKjXtxmzcfztjbService;
	
	@Autowired
	private ITKjXtxmzzkjqkmbService TKjXtxmzzkjqkmbService;
	
	@Autowired
	private ITXmFdcjsxmxxService TXmFdcjsxmxxService;
	
	@Autowired
	private ITXmFfdcjsxmxxService TXmFfdcjsxmxxService;
	
	@Autowired
	private ITXmXtdbgxbService TXmXtdbgxbService;
	
	@Autowired
	private ITXmXtdbhtbService TXmXtdbhtbService;
	
	@Autowired
	private ITXmXtdzywbService TXmXtdzywbService;
	
	@Autowired
	private ITXmXtxmqsxxService TXmXtxmqsxxService;
	
	@Autowired
	private ITXmXtxmsyqbService TXmXtxmsyqbService;
	
	@Autowired
	private ITXmXtxmxxService TXmXtxmxxService;
	
	@Autowired
	private ITXmXtxmyjhklypgbService TXmXtxmyjhklypgbService;
	
	@Autowired
	private ITXmXtzhxxService TXmXtzhxxService;
	
	@Autowired
	private ITXmXtzjmjhtxxService TXmXtzjmjhtxxService;
	
	@Autowired
	private ITXmXtzjyyhtxxService TXmXtzjyyhtxxService;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public TCheckInfo get(String id) {
		return TCheckInfoDAO.get(id);
	}
	
	@Override
	public TCheckInfo insert(TCheckInfo t) {
		return TCheckInfoDAO.insert(t);
	}

	@Override
	public TCheckInfo delete(TCheckInfo t) {
		return TCheckInfoDAO.delete(t);
	}

	@Override
	public TCheckInfo update(TCheckInfo t) {
		return TCheckInfoDAO.update(t);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return TCheckInfoDAO.getCount(inputParams);
	}

	@Override
	public List<TCheckInfo> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts) {
		return TCheckInfoDAO.getListForPage(inputParams, pageNum, pageSize, sorts);
	}

	@Override
	public List<Object[]> getCountListForUser(Map<String, String> inputParams) {
		return TCheckInfoDAO.getCountListForUser(inputParams);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void check(List<TCheckInfo> checkList, String status, TSsOperatorVO operator) {
		Session session = sessionFactory.getCurrentSession();
		for(TCheckInfo check : checkList){
			try {
				Class cla1 = Class.forName("cn.zeppin.product.itic.core.entity."+check.getDatatype());
				if("checked".equals(status)){
					if(TCheckInfoType.ADD.equals(check.getType())){
						
						Object t = JSONUtils.json2obj(Utlity.ClobToString(check.getNewdata()), cla1);
						session.save(t);
						
						TSsLog log = new TSsLog();
						log.setId(UUID.randomUUID().toString());
						log.setDatatype(check.getDatatype());
						log.setDataid(check.getDataid());
						log.setDataproduct(check.getDataproduct());
						log.setNewdata(Utlity.StringToClob(JSONUtils.obj2json(t)));
						log.setType(TSsLogType.CHECK);
						log.setCreatetime(new Timestamp(System.currentTimeMillis()));
						log.setCreator(operator.getId());
						log.setStatus(TSsLogStatus.NORMAL);
						this.TSsLogDAO.insert(log);
						
					}else if(TCheckInfoType.EDIT.equals(check.getType())){
						Object t = session.get(cla1, check.getDataid());
						
						if(t != null){
							TSsLog log = new TSsLog();
							log.setId(UUID.randomUUID().toString());
							log.setDatatype(check.getDatatype());
							log.setDataid(check.getDataid());
							log.setDataproduct(check.getDataproduct());
							log.setOlddata(Utlity.StringToClob(JSONUtils.obj2json(t)));
							log.setNewdata(check.getNewdata());
							log.setType(TSsLogType.CHECK);
							log.setCreatetime(new Timestamp(System.currentTimeMillis()));
							log.setCreator(operator.getId());
							log.setStatus(TSsLogStatus.NORMAL);
							this.TSsLogDAO.insert(log);
							
							Object data = JSONUtils.json2obj(Utlity.ClobToString(check.getNewdata()), cla1);
							ReflectUtlity.invokeSet(data, "updatetime", new Timestamp(System.currentTimeMillis()));
							Map<String,Object> dataMap = JSONUtils.json2map(Utlity.ClobToString(check.getNewdata()));
							for(String key : dataMap.keySet()){
								ReflectUtlity.invokeSet(t, key, ReflectUtlity.invokeGet(data, key));
							}
							session.update(t);
						}
					}
				}else{
					if(TCheckInfoType.ADD.equals(check.getType())){
						Object t = JSONUtils.json2obj(Utlity.ClobToString(check.getNewdata()), cla1);
						
						TSsLog log = new TSsLog();
						log.setId(UUID.randomUUID().toString());
						log.setDatatype(check.getDatatype());
						log.setDataid(check.getDataid());
						log.setDataproduct(check.getDataproduct());
						log.setNewdata(Utlity.StringToClob(JSONUtils.obj2json(t)));
						log.setType(TSsLogType.NOPASS);
						log.setCreatetime(new Timestamp(System.currentTimeMillis()));
						log.setCreator(operator.getId());
						log.setStatus(TSsLogStatus.NORMAL);
						this.TSsLogDAO.insert(log);
					}else if(TCheckInfoType.EDIT.equals(check.getType())){
						Object t = session.get(cla1, check.getDataid());
						if(t != null){
							TSsLog log = new TSsLog();
							log.setId(UUID.randomUUID().toString());
							log.setDatatype(check.getDatatype());
							log.setDataid(check.getDataid());
							log.setDataproduct(check.getDataproduct());
							log.setOlddata(Utlity.StringToClob(JSONUtils.obj2json(t)));
							log.setNewdata(check.getNewdata());
							log.setType(TSsLogType.NOPASS);
							log.setCreatetime(new Timestamp(System.currentTimeMillis()));
							log.setCreator(operator.getId());
							log.setStatus(TSsLogStatus.NORMAL);
							this.TSsLogDAO.insert(log);
						}
					}
				}
				this.TCheckInfoDAO.delete(check);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void insertAll(Map<String, List<Map<String, Object>>> datasMap, TSsOperatorVO operator) {
		if(datasMap.get("TGgGdxxb") != null){
			this.TGgGdxxbService.insertAll(datasMap.get("TGgGdxxb"), operator);
		}else if(datasMap.get("TGgJgxxb") != null){
			this.TGgJgxxbService.insertAll(datasMap.get("TGgJgxxb"), operator);
		}else if(datasMap.get("TGgYgxxb") != null){
			this.TGgYgxxbService.insertAll(datasMap.get("TGgYgxxb"), operator);
		}else if(datasMap.get("TGyGydbgxb") != null){
			this.TGyGydbgxbService.insertAll(datasMap.get("TGyGydbgxb"), operator);
		}else if(datasMap.get("TGyGydbhtb") != null){
			this.TGyGydbhtbService.insertAll(datasMap.get("TGyGydbhtb"), operator);
		}else if(datasMap.get("TGyGydzywb") != null){
			this.TGyGydzywbService.insertAll(datasMap.get("TGyGydzywb"), operator);
		}else if(datasMap.get("TGyGyzhxx") != null){
			this.TGyGyzhxxService.insertAll(datasMap.get("TGyGyzhxx"), operator);
		}else if(datasMap.get("TGyGyzjyyjyl") != null){
			this.TGyGyzjyyjylService.insertAll(datasMap.get("TGyGyzjyyjyl"), operator);
		}else if(datasMap.get("TGyGyzzyyhtxx") != null){
			this.TGyGyzzyyhtxxService.insertAll(datasMap.get("TGyGyzzyyhtxx"), operator);
		}else if(datasMap.get("TJyQjglxxfzq") != null){
			this.TJyQjglxxfzqService.insertAll(datasMap.get("TJyQjglxxfzq"), operator);
		}else if(datasMap.get("TJyQjglxxzq") != null){
			this.TJyQjglxxzqService.insertAll(datasMap.get("TJyQjglxxzq"), operator);
		}else if(datasMap.get("TJyXtsypz") != null){
			this.TJyXtsypzService.insertAll(datasMap.get("TJyXtsypz"), operator);
		}else if(datasMap.get("TJyXtsyqzrxx") != null){
			this.TJyXtsyqzrxxService.insertAll(datasMap.get("TJyXtsyqzrxx"), operator);
		}else if(datasMap.get("TJyXtzjmjjfpl") != null){
			this.TJyXtzjmjjfplService.insertAll(datasMap.get("TJyXtzjmjjfpl"), operator);
		}else if(datasMap.get("TJyXtzjyyjyl") != null){
			this.TJyXtzjyyjylService.insertAll(datasMap.get("TJyXtzjyyjyl"), operator);
		}else if(datasMap.get("TKhDsfhzjgxx") != null){
			this.TKhDsfhzjgxxService.insertAll(datasMap.get("TKhDsfhzjgxx"), operator);
		}else if(datasMap.get("TKhJydsgr") != null){
			this.TKhJydsgrService.insertAll(datasMap.get("TKhJydsgr"), operator);
		}else if(datasMap.get("TKhJydsjg") != null){
			this.TKhJydsjgService.insertAll(datasMap.get("TKhJydsjg"), operator);
		}else if(datasMap.get("TKhJydsjggdxx") != null){
			this.TKhJydsjggdxxService.insertAll(datasMap.get("TKhJydsjggdxx"), operator);
		}else if(datasMap.get("TKhTzgwhtb") != null){
			this.TKhTzgwhtbService.insertAll(datasMap.get("TKhTzgwhtb"), operator);
		}else if(datasMap.get("TKhXtkhgr") != null){
			this.TKhXtkhgrService.insertAll(datasMap.get("TKhXtkhgr"), operator);
		}else if(datasMap.get("TKhXtkhjg") != null){
			this.TKhXtkhjgService.insertAll(datasMap.get("TKhXtkhjg"), operator);
		}else if(datasMap.get("TKjGynbkmdzb") != null){
			this.TKjGynbkmdzbService.insertAll(datasMap.get("TKjGynbkmdzb"), operator);
		}else if(datasMap.get("TKjGyzcfzkmtjb") != null){
			this.TKjGyzcfzkmtjbService.insertAll(datasMap.get("TKjGyzcfzkmtjb"), operator);
		}else if(datasMap.get("TKjGyzzkjqkmb") != null){
			this.TKjGyzzkjqkmbService.insertAll(datasMap.get("TKjGyzzkjqkmb"), operator);
		}else if(datasMap.get("TKjXtnbkmdzb") != null){
			this.TKjXtnbkmdzbService.insertAll(datasMap.get("TKjXtnbkmdzb"), operator);
		}else if(datasMap.get("TKjXtxmzcfztjb") != null){
			this.TKjXtxmzcfztjbService.insertAll(datasMap.get("TKjXtxmzcfztjb"), operator);
		}else if(datasMap.get("TKjXtxmzzkjqkmb") != null){
			this.TKjXtxmzzkjqkmbService.insertAll(datasMap.get("TKjXtxmzzkjqkmb"), operator);
		}else if(datasMap.get("TXmFdcjsxmxx") != null){
			this.TXmFdcjsxmxxService.insertAll(datasMap.get("TXmFdcjsxmxx"), operator);
		}else if(datasMap.get("TXmFfdcjsxmxx") != null){
			this.TXmFfdcjsxmxxService.insertAll(datasMap.get("TXmFfdcjsxmxx"), operator);
		}else if(datasMap.get("TXmXtdbgxb") != null){
			this.TXmXtdbgxbService.insertAll(datasMap.get("TXmXtdbgxb"), operator);
		}else if(datasMap.get("TXmXtdbhtb") != null){
			this.TXmXtdbhtbService.insertAll(datasMap.get("TXmXtdbhtb"), operator);
		}else if(datasMap.get("TXmXtdzywb") != null){
			this.TXmXtdzywbService.insertAll(datasMap.get("TXmXtdzywb"), operator);
		}else if(datasMap.get("TXmXtxmqsxx") != null){
			this.TXmXtxmqsxxService.insertAll(datasMap.get("TXmXtxmqsxx"), operator);
		}else if(datasMap.get("TXmXtxmsyqb") != null){
			this.TXmXtxmsyqbService.insertAll(datasMap.get("TXmXtxmsyqb"), operator);
		}else if(datasMap.get("TXmXtxmxx") != null){
			this.TXmXtxmxxService.insertAll(datasMap.get("TXmXtxmxx"), operator);
		}else if(datasMap.get("TXmXtxmyjhklypgb") != null){
			this.TXmXtxmyjhklypgbService.insertAll(datasMap.get("TXmXtxmyjhklypgb"), operator);
		}else if(datasMap.get("TXmXtzhxx") != null){
			this.TXmXtzhxxService.insertAll(datasMap.get("TXmXtzhxx"), operator);
		}else if(datasMap.get("TXmXtzjmjhtxx") != null){
			this.TXmXtzjmjhtxxService.insertAll(datasMap.get("TXmXtzjmjhtxx"), operator);
		}else if(datasMap.get("TXmXtzjyyhtxx") != null){
			this.TXmXtzjyyhtxxService.insertAll(datasMap.get("TXmXtzjyyhtxx"), operator);
		}
	}

	@Override
	public Map<String,Object> sync(TSsOperatorVO operator, Map<String,Boolean> datatypeMap) {
		Map<String,Object> dataMap = this.TSyncDAO.sync(datatypeMap);
		
		TSsLog log = new TSsLog();
		log.setId(UUID.randomUUID().toString());
		log.setType(TSsLogType.SYNC);
		log.setStatus(TSsLogStatus.NORMAL);
		log.setCreator(operator.getId());
		log.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.TSsLogDAO.insert(log);
		return dataMap;
	}
	
	@Override
	public Map<String,Object> middleSync(TSsOperatorVO operator, Map<String,Boolean> datatypeMap) {
		Map<String,Object> dataMap = this.TSyncDAO.middleSync(datatypeMap);
		
		TSsLog log = new TSsLog();
		log.setId(UUID.randomUUID().toString());
		log.setType(TSsLogType.MIDSYNC);
		log.setStatus(TSsLogStatus.NORMAL);
		log.setCreator(operator.getId());
		log.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.TSsLogDAO.insert(log);
		return dataMap;
	}
}
