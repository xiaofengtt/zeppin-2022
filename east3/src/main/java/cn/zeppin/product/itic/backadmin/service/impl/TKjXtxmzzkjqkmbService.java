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
import cn.zeppin.product.itic.backadmin.dao.api.ITKjXtxmzzkjqkmbDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITSsLogDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITKjXtxmzzkjqkmbService;
import cn.zeppin.product.itic.backadmin.vo.TSsOperatorVO;
import cn.zeppin.product.itic.core.entity.TCheckInfo;
import cn.zeppin.product.itic.core.entity.TCheckInfo.TCheckInfoType;
import cn.zeppin.product.itic.core.entity.TKjXtxmzzkjqkmb;
import cn.zeppin.product.itic.core.entity.TSsLog;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogStatus;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogType;
import cn.zeppin.product.itic.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.ReflectUtlity;
import cn.zeppin.product.utility.Utlity;

import com.alibaba.fastjson.serializer.SerializerFeature;

@Service
public class TKjXtxmzzkjqkmbService extends BaseService implements ITKjXtxmzzkjqkmbService {

	@Autowired
	private ITKjXtxmzzkjqkmbDAO  TKjXtxmzzkjqkmbDAO;

	@Autowired
	private ITCheckInfoDAO  TCheckInfoDAO;
	
	@Autowired
	private ITSsLogDAO  TSsLogDAO;
	
	@Override
	public TKjXtxmzzkjqkmb get(String id) {
		return TKjXtxmzzkjqkmbDAO.get(id);
	}
	
	@Override
	public TKjXtxmzzkjqkmb insert(TKjXtxmzzkjqkmb t) {
		return TKjXtxmzzkjqkmbDAO.insert(t);
	}

	@Override
	public TKjXtxmzzkjqkmb delete(TKjXtxmzzkjqkmb t) {
		return TKjXtxmzzkjqkmbDAO.delete(t);
	}

	@Override
	public TKjXtxmzzkjqkmb update(TKjXtxmzzkjqkmb t) {
		return TKjXtxmzzkjqkmbDAO.update(t);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return TKjXtxmzzkjqkmbDAO.getCount(inputParams);
	}

	@Override
	public List<TKjXtxmzzkjqkmb> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts) {
		return TKjXtxmzzkjqkmbDAO.getListForPage(inputParams, pageNum, pageSize, sorts);
	}

	@Override
	public void insertAll(List<Map<String, Object>> datasList, TSsOperatorVO operator) {
		for(Map<String, Object> datas : datasList){
			TKjXtxmzzkjqkmb td = new TKjXtxmzzkjqkmb();
			td.setId(UUID.randomUUID().toString());
			td.setCreatetime(new Timestamp(System.currentTimeMillis()));
			td.setUpdatetime(new Timestamp(System.currentTimeMillis()));
			td.setStatus("normal");
			for(String key : datas.keySet()){
				ReflectUtlity.invokeSet(td, key, datas.get(key));
			}
			TSsLog log = new TSsLog();
			
			log.setId(UUID.randomUUID().toString());
			log.setDatatype("TKjXtxmzzkjqkmb");
			log.setDataid(td.getId());log.setDataproduct(td.getXtxmbh());
			log.setNewdata(Utlity.StringToClob(JSONUtils.obj2json(td, SerializerFeature.WriteMapNullValue)));
			log.setType(TSsLogType.ADD);
			log.setStatus(TSsLogStatus.NORMAL);
			log.setCreatetime(new Timestamp(System.currentTimeMillis()));
			log.setCreator(operator.getId());
			this.TSsLogDAO.insert(log);
			
			TCheckInfo check = new TCheckInfo();
			check.setId(UUID.randomUUID().toString());
			check.setDatatype("TKjXtxmzzkjqkmb");
			check.setDataid(td.getId());check.setDataproduct(log.getDataproduct());
			check.setType(TCheckInfoType.ADD);
			check.setNewdata(log.getNewdata());
			check.setUpdatetime(new Timestamp(System.currentTimeMillis()));check.setCreator(operator.getId());
			this.TCheckInfoDAO.insert(check);
		}
	}

	@Override
	public void deleteWholeData(TKjXtxmzzkjqkmb t, TSsOperatorVO operator) {
		TSsLog log = new TSsLog();
		log.setId(UUID.randomUUID().toString());
		log.setDatatype("TKjXtxmzzkjqkmb");
		log.setDataid(t.getId());
		log.setOlddata(Utlity.StringToClob(JSONUtils.obj2json(t)));
		log.setType(TSsLogType.DELETE);
		log.setCreatetime(new Timestamp(System.currentTimeMillis()));
		log.setCreator(operator.getId());
		log.setStatus(TSsLogStatus.NORMAL);
		this.TSsLogDAO.insert(log);
		
		this.TCheckInfoDAO.deleteByData("TKjXtxmzzkjqkmb", t.getId());
		this.delete(t);
	}
}
