/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IFundDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IFundPublishDailyDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishDailyService;
import cn.zeppin.product.ntb.core.entity.FundPublishDaily;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class FundPublishDailyService extends BaseService implements IFundPublishDailyService {

	@Autowired
	private IFundPublishDailyDAO fundPublishDailyDAO;
	@Autowired
	private IFundDAO fundDAO;
	
	@Override
	public FundPublishDaily insert(FundPublishDaily fundPublishDaily) {
		return fundPublishDailyDAO.insert(fundPublishDaily);
	}

	@Override
	public FundPublishDaily delete(FundPublishDaily fundPublishDaily) {
		return fundPublishDailyDAO.delete(fundPublishDaily);
	}

	@Override
	public FundPublishDaily update(FundPublishDaily fundPublishDaily) {
		return fundPublishDailyDAO.update(fundPublishDaily);
	}

	@Override
	public FundPublishDaily get(String uuid) {
		return fundPublishDailyDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询FundPublishDaily结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return fundPublishDailyDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return fundPublishDailyDAO.getCount(inputParams);
	}
}
