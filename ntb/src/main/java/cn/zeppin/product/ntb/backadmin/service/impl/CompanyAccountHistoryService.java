/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountHistoryDAO;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountHistoryService;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 *
 */
@Service
public class CompanyAccountHistoryService extends BaseService implements ICompanyAccountHistoryService {

	@Autowired
	private ICompanyAccountHistoryDAO companyAccountHistoryDAO;
	
	@Override
	public CompanyAccountHistory insert(CompanyAccountHistory companyAccountHistory) {
		return companyAccountHistoryDAO.insert(companyAccountHistory);
	}

	@Override
	public CompanyAccountHistory delete(CompanyAccountHistory companyAccountHistory) {
		return companyAccountHistoryDAO.delete(companyAccountHistory);
	}

	@Override
	public CompanyAccountHistory update(CompanyAccountHistory companyAccountHistory) {
		return companyAccountHistoryDAO.update(companyAccountHistory);
	}

	@Override
	public CompanyAccountHistory get(String uuid) {
		return companyAccountHistoryDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询CompanyAccountTransfer结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return companyAccountHistoryDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return companyAccountHistoryDAO.getCount(inputParams);
	}
	
	@Override
	public void insertBatch(List<CompanyAccountHistory> listInsert) {
		for(CompanyAccountHistory cah : listInsert){
			this.companyAccountHistoryDAO.insert(cah);
		}
	}

	/**
	 * 获取历史记录分类型列表
	 * @param inputParams
	 * @return
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return this.companyAccountHistoryDAO.getTypeList(inputParams, resultClass);
	}
	
	
}
