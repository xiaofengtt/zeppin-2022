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
import cn.zeppin.product.itic.backadmin.dao.api.ITKhXtkhgrDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITSsLogDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITKhXtkhgrService;
import cn.zeppin.product.itic.backadmin.vo.TSsOperatorVO;
import cn.zeppin.product.itic.core.entity.TCheckInfo;
import cn.zeppin.product.itic.core.entity.TCheckInfo.TCheckInfoType;
import cn.zeppin.product.itic.core.entity.TKhXtkhgr;
import cn.zeppin.product.itic.core.entity.TSsLog;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogStatus;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogType;
import cn.zeppin.product.itic.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.ReflectUtlity;
import cn.zeppin.product.utility.Utlity;

import com.alibaba.fastjson.serializer.SerializerFeature;

@Service
public class TKhXtkhgrService extends BaseService implements ITKhXtkhgrService {

	@Autowired
	private ITKhXtkhgrDAO  TKhXtkhgrDAO;

	@Autowired
	private ITCheckInfoDAO  TCheckInfoDAO;
	
	@Autowired
	private ITSsLogDAO  TSsLogDAO;
	
	@Override
	public TKhXtkhgr get(String id) {
		return TKhXtkhgrDAO.get(id);
	}
	
	@Override
	public TKhXtkhgr insert(TKhXtkhgr t) {
		return TKhXtkhgrDAO.insert(t);
	}

	@Override
	public TKhXtkhgr delete(TKhXtkhgr t) {
		return TKhXtkhgrDAO.delete(t);
	}

	@Override
	public TKhXtkhgr update(TKhXtkhgr t) {
		return TKhXtkhgrDAO.update(t);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return TKhXtkhgrDAO.getCount(inputParams);
	}

	@Override
	public List<TKhXtkhgr> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts) {
		return TKhXtkhgrDAO.getListForPage(inputParams, pageNum, pageSize, sorts);
	}

	@Override
	public void insertAll(List<Map<String, Object>> datasList, TSsOperatorVO operator) {
		for(Map<String, Object> datas : datasList){
			TKhXtkhgr td = new TKhXtkhgr();
			td.setId(UUID.randomUUID().toString());
			td.setCreatetime(new Timestamp(System.currentTimeMillis()));
			td.setUpdatetime(new Timestamp(System.currentTimeMillis()));
			td.setStatus("normal");
			for(String key : datas.keySet()){
				ReflectUtlity.invokeSet(td, key, datas.get(key));
			}
			TSsLog log = new TSsLog();
			
			log.setId(UUID.randomUUID().toString());
			log.setDatatype("TKhXtkhgr");
			log.setDataid(td.getId());
			log.setNewdata(Utlity.StringToClob(JSONUtils.obj2json(td, SerializerFeature.WriteMapNullValue)));
			log.setType(TSsLogType.ADD);
			log.setStatus(TSsLogStatus.NORMAL);
			log.setCreatetime(new Timestamp(System.currentTimeMillis()));
			log.setCreator(operator.getId());
			this.TSsLogDAO.insert(log);
			
			TCheckInfo check = new TCheckInfo();
			check.setId(UUID.randomUUID().toString());
			check.setDatatype("TKhXtkhgr");
			check.setDataid(td.getId());check.setDataproduct(log.getDataproduct());
			check.setType(TCheckInfoType.ADD);
			check.setNewdata(log.getNewdata());
			check.setUpdatetime(new Timestamp(System.currentTimeMillis()));check.setCreator(operator.getId());
			this.TCheckInfoDAO.insert(check);
		}
	}

	@Override
	public void deleteWholeData(TKhXtkhgr t, TSsOperatorVO operator) {
		TSsLog log = new TSsLog();
		log.setId(UUID.randomUUID().toString());
		log.setDatatype("TKhXtkhgr");
		log.setDataid(t.getId());
		log.setOlddata(Utlity.StringToClob(JSONUtils.obj2json(t)));
		log.setType(TSsLogType.DELETE);
		log.setCreatetime(new Timestamp(System.currentTimeMillis()));
		log.setCreator(operator.getId());
		log.setStatus(TSsLogStatus.NORMAL);
		this.TSsLogDAO.insert(log);
		
		this.TCheckInfoDAO.deleteByData("TKhXtkhgr", t.getId());
		this.delete(t);
	}
}
