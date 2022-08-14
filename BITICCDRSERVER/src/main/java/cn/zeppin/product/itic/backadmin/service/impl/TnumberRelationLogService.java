/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITnumberRelationLogDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITnumberRelationLogService;
import cn.zeppin.product.itic.core.entity.TnumberRelation;
import cn.zeppin.product.itic.core.entity.TnumberRelationLog;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.itic.core.exception.ProcessException;
import cn.zeppin.product.itic.core.service.base.BaseService;

/**
 * @author L
 *
 */
@Service
public class TnumberRelationLogService extends BaseService implements ITnumberRelationLogService {

	@Autowired
	private ITnumberRelationLogDAO  tnumberRelationLogDAO;
	
	
	@Override
	public TnumberRelationLog get(Integer uuid) {
		return tnumberRelationLogDAO.get(uuid);
	}
	
	/**
	 * 向表中插入一条Controller数据
	 */
	@Override
	public TnumberRelationLog insert(TnumberRelationLog area) {
		return tnumberRelationLogDAO.insert(area);
	}

	@Override
	public TnumberRelationLog delete(TnumberRelationLog area) {
		return tnumberRelationLogDAO.delete(area);
	}

	@Override
	public TnumberRelationLog update(TnumberRelationLog area) {
		return tnumberRelationLogDAO.update(area);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return tnumberRelationLogDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return tnumberRelationLogDAO.getCount(inputParams);
	}

	@Override
	public void insertProcess(List<TnumberRelation> list) throws ProcessException {
		// TODO Auto-generated method stub
//		for(TnumberRelation tr : list) {
//			Map<String, Object> result = CdrUtlity.setBindNum(CdrUtlity.process_operate_create,tr);
//			Map<String, Object> map = JSONUtils.json2map(result.get("header").toString());
//			String status = map.get("STATE_CODE").toString();
//			String message = map.get("STATE_NAME").toString();
//			String remark = map.get("REMARK") == null ? "" : map.get("REMARK").toString();

//			if (CdrUtlity.request_status_0000.equals(status)) {// 请求成功
//				tr.setProcessStatus(TnumberRelationProcessStatus.NORMAL);
//				this.tnumberRelationDAO.update(tr);
//			} else {
//				throw new ProcessException("处理失败！" + message + "," + remark);
//			}
//		}
	}
}
