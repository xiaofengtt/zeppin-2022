/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.jgroups.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITCheckInfoDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITJyXtzjyyjylDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITSsLogDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITJyXtzjyyjylService;
import cn.zeppin.product.itic.backadmin.vo.TSsOperatorVO;
import cn.zeppin.product.itic.core.entity.TCheckInfo;
import cn.zeppin.product.itic.core.entity.TCheckInfo.TCheckInfoType;
import cn.zeppin.product.itic.core.entity.TJyXtzjyyjyl;
import cn.zeppin.product.itic.core.entity.TSsLog;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogStatus;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogType;
import cn.zeppin.product.itic.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.ReflectUtlity;
import cn.zeppin.product.utility.Utlity;

import com.alibaba.fastjson.serializer.SerializerFeature;

@Service
public class TJyXtzjyyjylService extends BaseService implements ITJyXtzjyyjylService {

	@Autowired
	private ITJyXtzjyyjylDAO  TJyXtzjyyjylDAO;

	@Autowired
	private ITCheckInfoDAO  TCheckInfoDAO;
	
	@Autowired
	private ITSsLogDAO  TSsLogDAO;
	
	@Override
	public TJyXtzjyyjyl get(String id) {
		return TJyXtzjyyjylDAO.get(id);
	}
	
	@Override
	public TJyXtzjyyjyl insert(TJyXtzjyyjyl t) {
		return TJyXtzjyyjylDAO.insert(t);
	}

	@Override
	public TJyXtzjyyjyl delete(TJyXtzjyyjyl t) {
		return TJyXtzjyyjylDAO.delete(t);
	}

	@Override
	public TJyXtzjyyjyl update(TJyXtzjyyjyl t) {
		return TJyXtzjyyjylDAO.update(t);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return TJyXtzjyyjylDAO.getCount(inputParams);
	}

	@Override
	public List<TJyXtzjyyjyl> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts) {
		return TJyXtzjyyjylDAO.getListForPage(inputParams, pageNum, pageSize, sorts);
	}
	
	@Override
	public void insertAll(List<Map<String, Object>> datasList, TSsOperatorVO operator) {
		for(Map<String, Object> datas : datasList){
			TJyXtzjyyjyl td = new TJyXtzjyyjyl();
			td.setId(UUID.randomUUID().toString());
			td.setCreatetime(new Timestamp(System.currentTimeMillis()));
			td.setUpdatetime(new Timestamp(System.currentTimeMillis()));
			td.setStatus("normal");
			for(String key : datas.keySet()){
				ReflectUtlity.invokeSet(td, key, datas.get(key));
			}
			TSsLog log = new TSsLog();
			
			log.setId(UUID.randomUUID().toString());
			log.setDatatype("TJyXtzjyyjyl");
			log.setDataid(td.getId());log.setDataproduct(td.getXtxmbm());
			log.setNewdata(Utlity.StringToClob(JSONUtils.obj2json(td, SerializerFeature.WriteMapNullValue)));
			log.setType(TSsLogType.ADD);
			log.setStatus(TSsLogStatus.NORMAL);
			log.setCreatetime(new Timestamp(System.currentTimeMillis()));
			log.setCreator(operator.getId());
			this.TSsLogDAO.insert(log);
			
			TCheckInfo check = new TCheckInfo();
			check.setId(UUID.randomUUID().toString());
			check.setDatatype("TJyXtzjyyjyl");
			check.setDataid(td.getId());check.setDataproduct(log.getDataproduct());
			check.setType(TCheckInfoType.ADD);
			check.setNewdata(log.getNewdata());
			check.setUpdatetime(new Timestamp(System.currentTimeMillis()));check.setCreator(operator.getId());
			this.TCheckInfoDAO.insert(check);
		}
	}

	@Override
	public void deleteWholeData(TJyXtzjyyjyl t, TSsOperatorVO operator) {
		TSsLog log = new TSsLog();
		log.setId(UUID.randomUUID().toString());
		log.setDatatype("TJyXtzjyyjyl");
		log.setDataid(t.getId());
		log.setOlddata(Utlity.StringToClob(JSONUtils.obj2json(t)));
		log.setType(TSsLogType.DELETE);
		log.setCreatetime(new Timestamp(System.currentTimeMillis()));
		log.setCreator(operator.getId());
		log.setStatus(TSsLogStatus.NORMAL);
		this.TSsLogDAO.insert(log);
		
		this.TCheckInfoDAO.deleteByData("TJyXtzjyyjyl", t.getId());
		this.delete(t);
	}
}
