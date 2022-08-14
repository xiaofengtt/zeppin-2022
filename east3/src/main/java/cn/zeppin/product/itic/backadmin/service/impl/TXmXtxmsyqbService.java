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
import cn.zeppin.product.itic.backadmin.dao.api.ITSsLogDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITXmXtxmsyqbDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtxmsyqbService;
import cn.zeppin.product.itic.backadmin.vo.TSsOperatorVO;
import cn.zeppin.product.itic.core.entity.TCheckInfo;
import cn.zeppin.product.itic.core.entity.TCheckInfo.TCheckInfoType;
import cn.zeppin.product.itic.core.entity.TSsLog;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogStatus;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogType;
import cn.zeppin.product.itic.core.entity.TXmXtxmsyqb;
import cn.zeppin.product.itic.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.ReflectUtlity;
import cn.zeppin.product.utility.Utlity;

import com.alibaba.fastjson.serializer.SerializerFeature;

@Service
public class TXmXtxmsyqbService extends BaseService implements ITXmXtxmsyqbService {

	@Autowired
	private ITXmXtxmsyqbDAO  TXmXtxmsyqbDAO;

	@Autowired
	private ITCheckInfoDAO  TCheckInfoDAO;
	
	@Autowired
	private ITSsLogDAO  TSsLogDAO;
	
	@Override
	public TXmXtxmsyqb get(String id) {
		return TXmXtxmsyqbDAO.get(id);
	}
	
	@Override
	public TXmXtxmsyqb insert(TXmXtxmsyqb t) {
		return TXmXtxmsyqbDAO.insert(t);
	}

	@Override
	public TXmXtxmsyqb delete(TXmXtxmsyqb t) {
		return TXmXtxmsyqbDAO.delete(t);
	}

	@Override
	public TXmXtxmsyqb update(TXmXtxmsyqb t) {
		return TXmXtxmsyqbDAO.update(t);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return TXmXtxmsyqbDAO.getCount(inputParams);
	}

	@Override
	public List<TXmXtxmsyqb> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts) {
		return TXmXtxmsyqbDAO.getListForPage(inputParams, pageNum, pageSize, sorts);
	}

	@Override
	public void insertAll(List<Map<String, Object>> datasList, TSsOperatorVO operator) {
		for(Map<String, Object> datas : datasList){
			TXmXtxmsyqb td = new TXmXtxmsyqb();
			td.setId(UUID.randomUUID().toString());
			td.setCreatetime(new Timestamp(System.currentTimeMillis()));
			td.setUpdatetime(new Timestamp(System.currentTimeMillis()));
			td.setStatus("normal");
			for(String key : datas.keySet()){
				ReflectUtlity.invokeSet(td, key, datas.get(key));
			}
			TSsLog log = new TSsLog();
			
			log.setId(UUID.randomUUID().toString());
			log.setDatatype("TXmXtxmsyqb");
			log.setDataid(td.getId());log.setDataproduct(td.getXtxmbm());
			log.setNewdata(Utlity.StringToClob(JSONUtils.obj2json(td, SerializerFeature.WriteMapNullValue)));
			log.setType(TSsLogType.ADD);
			log.setStatus(TSsLogStatus.NORMAL);
			log.setCreatetime(new Timestamp(System.currentTimeMillis()));
			log.setCreator(operator.getId());
			this.TSsLogDAO.insert(log);
			
			TCheckInfo check = new TCheckInfo();
			check.setId(UUID.randomUUID().toString());
			check.setDatatype("TXmXtxmsyqb");
			check.setDataid(td.getId());check.setDataproduct(log.getDataproduct());
			check.setType(TCheckInfoType.ADD);
			check.setNewdata(log.getNewdata());
			check.setUpdatetime(new Timestamp(System.currentTimeMillis()));check.setCreator(operator.getId());
			this.TCheckInfoDAO.insert(check);
		}
	}

	@Override
	public void deleteWholeData(TXmXtxmsyqb t, TSsOperatorVO operator) {
		TSsLog log = new TSsLog();
		log.setId(UUID.randomUUID().toString());
		log.setDatatype("TXmXtxmsyqb");
		log.setDataid(t.getId());
		log.setOlddata(Utlity.StringToClob(JSONUtils.obj2json(t)));
		log.setType(TSsLogType.DELETE);
		log.setCreatetime(new Timestamp(System.currentTimeMillis()));
		log.setCreator(operator.getId());
		log.setStatus(TSsLogStatus.NORMAL);
		this.TSsLogDAO.insert(log);
		
		this.TCheckInfoDAO.deleteByData("TXmXtxmsyqb", t.getId());
		this.delete(t);
	}
}
