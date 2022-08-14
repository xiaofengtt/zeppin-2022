/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts;
import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts.QcbVirtualAccountsStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbVirtualAccountsDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbVirtualAccountsService;

/**
 * @author hehe
 *
 */
@Service
public class QcbVirtualAccountsService extends BaseService implements IQcbVirtualAccountsService {

	@Autowired
	private IQcbVirtualAccountsDAO qcbVirtualAccountsDAO;
	
	@Override
	public QcbVirtualAccounts insert(QcbVirtualAccounts qcbVirtualAccounts) {
		return qcbVirtualAccountsDAO.insert(qcbVirtualAccounts);
	}

	@Override
	public QcbVirtualAccounts delete(QcbVirtualAccounts qcbVirtualAccounts) {
		qcbVirtualAccounts.setStatus(QcbVirtualAccountsStatus.DELETED);
		qcbVirtualAccounts.setAccountNum(qcbVirtualAccounts.getAccountNum() + "_#" + qcbVirtualAccounts.getUuid());
		return qcbVirtualAccountsDAO.update(qcbVirtualAccounts);
	}

	@Override
	public QcbVirtualAccounts update(QcbVirtualAccounts qcbVirtualAccounts) {
		return qcbVirtualAccountsDAO.update(qcbVirtualAccounts);
	}

	@Override
	public QcbVirtualAccounts get(String uuid) {
		return qcbVirtualAccountsDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return qcbVirtualAccountsDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbVirtualAccountsDAO.getCount(inputParams);
	}
	
	/**
	 * 获取分状态列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return qcbVirtualAccountsDAO.getStatusList(inputParams, resultClass);
	}
	
	/**
	 * 批量添加
	 * @param companyAccount
	 * @param start
	 * @param end
	 * @param creator
	 */
	public void batchAdd(String companyAccount, String start, String end, String creator){
		Integer startNum = Integer.valueOf(start);
		Integer endNum = Integer.valueOf(end);
		Timestamp time = new Timestamp(System.currentTimeMillis());
		
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("companyAccount", companyAccount);
		searchMap.put("start", start);
		searchMap.put("end", end);
		
		List<String> list= this.qcbVirtualAccountsDAO.getAccountNumByParams(searchMap);
		
		List<Object[]> objsList = new ArrayList<Object[]>();
		for(int i=startNum; i<=endNum; i++){
			Boolean flag = true;
			for(int j=0; j<list.size(); j++){
				if(list.get(j).equals(String.format("%0" + start.length() + "d", i))){
					flag = false;
					list.remove(j);
					break;
				}
			}
			
			if(flag){
				Object[] objs =  new Object[6];
				objs[0] = UUID.randomUUID().toString();
				objs[1] = String.format("%0" + start.length() + "d", i);
				objs[2] = companyAccount;
				objs[3] = QcbVirtualAccountsStatus.NORMAL;
				objs[4] = creator;
				objs[5] = time;
				objsList.add(objs);
			}
		}
		this.qcbVirtualAccountsDAO.batchAdd(objsList);
	}
	
	/**
	 * 批量删除
	 * @param companyAccount
	 * @param start
	 * @param end
	 */
	public void batchDelete(String companyAccount, String start, String end){
		this.qcbVirtualAccountsDAO.batchDelete(companyAccount, start, end);
	}
}
