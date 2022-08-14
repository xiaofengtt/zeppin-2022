/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITnumberRelationDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITnumberRelationLogDAO;
import cn.zeppin.product.itic.backadmin.dao.api.IToperatorMobileDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITnumberRelationService;
import cn.zeppin.product.itic.core.entity.TnumberRelation;
import cn.zeppin.product.itic.core.entity.TnumberRelation.TnumberRelationProcessStatus;
import cn.zeppin.product.itic.core.entity.TnumberRelation.TnumberRelationStatus;
import cn.zeppin.product.itic.core.entity.TnumberRelationLog.TnumberRelationLogStatus;
import cn.zeppin.product.itic.core.entity.TnumberRelationLog.TnumberRelationLogType;
import cn.zeppin.product.itic.core.entity.TnumberRelationLog;
import cn.zeppin.product.itic.core.entity.ToperatorMobile;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.itic.core.exception.ProcessException;
import cn.zeppin.product.itic.core.service.base.BaseService;
import cn.zeppin.product.utility.CdrUtlity;
import cn.zeppin.product.utility.JSONUtils;

/**
 * @author L
 *
 */
@Service
public class TnumberRelationService extends BaseService implements ITnumberRelationService {

	@Autowired
	private ITnumberRelationDAO  tnumberRelationDAO;
	
	@Autowired
	private IToperatorMobileDAO  toperatorMobileDAO;
	
	@Autowired
	private ITnumberRelationLogDAO  tnumberRelationLogDAO;

	
	@Override
	public TnumberRelation get(Integer uuid) {
		return tnumberRelationDAO.get(uuid);
	}
	
	/**
	 * 向表中插入一条Controller数据
	 */
	@Override
	public TnumberRelation insert(TnumberRelation area) {
		return tnumberRelationDAO.insert(area);
	}

	@Override
	public TnumberRelation delete(TnumberRelation area) {
		return tnumberRelationDAO.delete(area);
	}

	@Override
	public TnumberRelation update(TnumberRelation area) {
		return tnumberRelationDAO.update(area);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return tnumberRelationDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return tnumberRelationDAO.getCount(inputParams);
	}

	@Override
	public List<TnumberRelation> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts) {
		// TODO Auto-generated method stub
		return tnumberRelationDAO.getListForPage(inputParams, pageNum, pageSize, sorts);
	}

	@Override
	public void insert(TnumberRelation tr, ToperatorMobile tm) {
		// TODO Auto-generated method stub
		if(tm != null) {
			tm = this.toperatorMobileDAO.insert(tm);
			tr.setFkOpMobile(tm.getId());
		}
		this.tnumberRelationDAO.insert(tr);
	}

	@Override
	public void updateProcess(List<TnumberRelation> list, ToperatorMobile tm) throws ProcessException {
		// TODO Auto-generated method stub
		for(TnumberRelation tr : list) {
			//20180608 增加log记录
			TnumberRelationLog trl = new TnumberRelationLog();
			trl.setCreatetime(new Timestamp(System.currentTimeMillis()));
			trl.setStatus(TnumberRelationLogStatus.DISABLED);
			trl.setFkTcustomers(tr.getFkTcustomers());
			trl.setFkToperator(tr.getFkToperator());
			trl.setTcPhone(tr.getTcPhone());
			trl.setTcTel(tr.getTcTel());
			trl.setType(TnumberRelationLogType.DELETE);
			trl.setFkTnumberRelation(tr.getId());
			trl.setToMobile(tr.getToMobile());
			trl.setToTel(tr.getToTel());
			this.tnumberRelationLogDAO.insert(trl);
			
			if(TnumberRelationStatus.NORMAL.equals(tr.getStatus())) {
				Map<String, Object> result = CdrUtlity.setBindNum(CdrUtlity.process_operate_delete,tr);
				Map<String, Object> map = JSONUtils.json2map(result.get("header").toString());
				String status = map.get("STATE_CODE").toString();
				String message = map.get("STATE_NAME").toString();
				String remark = map.get("REMARK") == null ? "" : map.get("REMARK").toString();

				if (!CdrUtlity.request_status_0000.equals(status)) {// 请求成功
					throw new ProcessException("处理失败！" + message + "," + remark);
				}
				tr.setToMobile(tm.getToMobile());
				tr.setToTel(tm.getToTel());
				Map<String, Object> resultc = CdrUtlity.setBindNum(CdrUtlity.process_operate_create,tr);
				Map<String, Object> mapc = JSONUtils.json2map(resultc.get("header").toString());
				String statusc = mapc.get("STATE_CODE").toString();
				String messagec = mapc.get("STATE_NAME").toString();
				String remarkc = mapc.get("REMARK") == null ? "" : map.get("REMARK").toString();

				if (CdrUtlity.request_status_0000.equals(statusc)) {// 请求成功
					this.tnumberRelationDAO.update(tr);
				} else {
					throw new ProcessException("处理失败！" + messagec + "," + remarkc);
				}
			} else {
				tr.setToMobile(tm.getToMobile());
				tr.setToTel(tm.getToTel());
				this.tnumberRelationDAO.update(tr);
			}
			//20180608 增加log记录
			TnumberRelationLog trl2 = new TnumberRelationLog();
			trl2.setCreatetime(new Timestamp(System.currentTimeMillis()));
			trl2.setStatus(TnumberRelationLogStatus.DISABLED);
			trl2.setFkTcustomers(tr.getFkTcustomers());
			trl2.setFkToperator(tr.getFkToperator());
			trl2.setTcPhone(tr.getTcPhone());
			trl2.setTcTel(tr.getTcTel());
			trl2.setType(TnumberRelationLogType.INSERT);
			trl2.setFkTnumberRelation(tr.getId());
			trl2.setToMobile(tr.getToMobile());
			trl2.setToTel(tr.getToTel());
			this.tnumberRelationLogDAO.insert(trl2);
			
		}
		this.toperatorMobileDAO.update(tm);
	}

	@Override
	public void deleteProcess(List<TnumberRelation> list, ToperatorMobile tm) throws ProcessException {
		// TODO Auto-generated method stub
		for(TnumberRelation tr : list) {
			
			//20180608 增加log记录
			TnumberRelationLog trl = new TnumberRelationLog();
			trl.setCreatetime(new Timestamp(System.currentTimeMillis()));
			trl.setStatus(TnumberRelationLogStatus.DISABLED);
			trl.setFkTcustomers(tr.getFkTcustomers());
			trl.setFkToperator(tr.getFkToperator());
			trl.setTcPhone(tr.getTcPhone());
			trl.setTcTel(tr.getTcTel());
			trl.setType(TnumberRelationLogType.DELETE);
			trl.setFkTnumberRelation(tr.getId());
			trl.setToMobile(tr.getToMobile());
			trl.setToTel(tr.getToTel());
			this.tnumberRelationLogDAO.insert(trl);
			
			if(TnumberRelationStatus.NORMAL.equals(tr.getStatus())) {
				Map<String, Object> result = CdrUtlity.setBindNum(CdrUtlity.process_operate_delete,tr);
				Map<String, Object> map = JSONUtils.json2map(result.get("header").toString());
				String status = map.get("STATE_CODE").toString();
				String message = map.get("STATE_NAME").toString();
				String remark = map.get("REMARK") == null ? "" : map.get("REMARK").toString();
				if (CdrUtlity.request_status_0000.equals(status)) {// 请求成功
					tr.setToMobile(null);
					tr.setToTel(null);
					tr.setStatus(TnumberRelationStatus.ERROR);
					tr.setProcessStatus(TnumberRelationProcessStatus.NORMAL);
					this.tnumberRelationDAO.delete(tr);//直接删除 待重新分配后 重新填入
				} else {
					throw new ProcessException("处理失败！" + message + "," + remark);
				}
			} else {
				this.tnumberRelationDAO.delete(tr);//直接删除 待重新分配后 重新填入
			}
		}
		this.toperatorMobileDAO.update(tm);
	}

	@Override
	public void insertProcess(ToperatorMobile tm){
		// TODO Auto-generated method stub
		this.toperatorMobileDAO.updateTm(tm);
		this.tnumberRelationDAO.dailyInsert(tm);
//		this.tnumberRelationDAO.dailyUpdate(tm.getFkToperator());
	}

	@Override
	public void insertProcess(List<TnumberRelation> list) throws ProcessException {
		// TODO Auto-generated method stub
		for(TnumberRelation tr : list) {
			Map<String, Object> result = CdrUtlity.setBindNum(CdrUtlity.process_operate_create,tr);
			Map<String, Object> map = JSONUtils.json2map(result.get("header").toString());
			String status = map.get("STATE_CODE").toString();
			String message = map.get("STATE_NAME").toString();
			String remark = map.get("REMARK") == null ? "" : map.get("REMARK").toString();

			if (CdrUtlity.request_status_0000.equals(status)) {// 请求成功
				tr.setProcessStatus(TnumberRelationProcessStatus.NORMAL);
				this.tnumberRelationDAO.update(tr);
			} else {
				throw new ProcessException("处理失败！" + message + "," + remark);
			}
		}
	}

	@Override
	public void insertProcess(List<TnumberRelation> list, ToperatorMobile tm) throws ProcessException {
		// TODO Auto-generated method stub
		this.toperatorMobileDAO.update(tm);
		for(TnumberRelation tr : list) {
			Map<String, Object> result = CdrUtlity.setBindNum(CdrUtlity.process_operate_create,tr);
			Map<String, Object> map = JSONUtils.json2map(result.get("header").toString());
			String status = map.get("STATE_CODE").toString();
			String message = map.get("STATE_NAME").toString();
			String remark = map.get("REMARK") == null ? "" : map.get("REMARK").toString();

			if (CdrUtlity.request_status_0000.equals(status)) {// 请求成功
				tr.setProcessStatus(TnumberRelationProcessStatus.NORMAL);
				this.tnumberRelationDAO.update(tr);
			} else {
				throw new ProcessException("处理失败！" + message + "," + remark);
			}
		}
	}

	@Override
	public void update(TnumberRelation tr, TnumberRelationLog trl) {
		// TODO Auto-generated method stub
		if(trl != null) {
			this.tnumberRelationLogDAO.insert(trl);
		}
		this.tnumberRelationDAO.update(tr);
	}

	@Override
	public void update(TnumberRelation tr, TnumberRelationLog trl, TnumberRelationLog trl2) {
		// TODO Auto-generated method stub
		if(trl != null) {
			this.tnumberRelationLogDAO.insert(trl);
		}
		
		if(trl2 != null) {
			this.tnumberRelationLogDAO.insert(trl2);
		}
		
		this.tnumberRelationDAO.update(tr);
	}
}
