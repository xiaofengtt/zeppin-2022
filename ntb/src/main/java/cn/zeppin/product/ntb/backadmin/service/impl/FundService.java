/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IFundDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IFundRateDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IFundService;
import cn.zeppin.product.ntb.core.entity.Fund;
import cn.zeppin.product.ntb.core.entity.FundRate;
import cn.zeppin.product.ntb.core.entity.Fund.FundStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class FundService extends BaseService implements IFundService {

	@Autowired
	private IFundDAO fundDAO;
	
	@Autowired
	private IFundRateDAO fundRateDAO;
	
	@Override
	public Fund insert(Fund fund) {
		return fundDAO.insert(fund);
	}

	@Override
	public Fund delete(Fund fund) {
		fund.setStatus(FundStatus.DELETED);
		return fundDAO.update(fund);
	}

	@Override
	public Fund update(Fund fund) {
		return fundDAO.update(fund);
	}

	@Override
	public Fund get(String uuid) {
		return fundDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询Fund结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return fundDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return fundDAO.getCount(inputParams);
	}
	
	/**
	 * 获取基金分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		return fundDAO.getStatusList(resultClass);
	}
	
	/**
	 * 新增基金
	 * @param fund
	 * @param fundRateList
	 */
	public void add(Fund fund, List<FundRate> fundRateList){
		if(fundRateList != null && fundRateList.size()>0){
			for(FundRate fundRate : fundRateList){
				fundRateDAO.insert(fundRate);
			}
		}
	}
	
	/**
	 * 编辑基金费率
	 * @param fund
	 * @param fundType
	 * @param fundRateList
	 */
	public void updateFundRate(Fund fund, String fundType, List<FundRate> fundRateList){
		fundRateDAO.deleteByType(fund, fundType);
		if(fundRateList != null && fundRateList.size()>0){
			for(FundRate fundRate : fundRateList){
				fundRateDAO.insert(fundRate);
			}
		}
	}
}
