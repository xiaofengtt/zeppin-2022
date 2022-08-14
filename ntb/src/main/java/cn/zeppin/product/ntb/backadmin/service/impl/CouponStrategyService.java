/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.ICouponStrategyDAO;
import cn.zeppin.product.ntb.backadmin.service.api.ICouponStrategyService;
import cn.zeppin.product.ntb.core.entity.CouponStrategy;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 *
 */
@Service
public class CouponStrategyService extends BaseService implements ICouponStrategyService {

	@Autowired
	private ICouponStrategyDAO couponStrategyDAO;
	
	@Override
	public CouponStrategy insert(CouponStrategy couponStrategy) {
		return couponStrategyDAO.insert(couponStrategy);
	}

	@Override
	public CouponStrategy delete(CouponStrategy couponStrategy) {
		return couponStrategyDAO.delete(couponStrategy);
	}

	@Override
	public CouponStrategy update(CouponStrategy couponStrategy) {
		return couponStrategyDAO.update(couponStrategy);
	}

	@Override
	public CouponStrategy get(String uuid) {
		return couponStrategyDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询CouponStrategy结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return couponStrategyDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return couponStrategyDAO.getCount(inputParams);
	}
	
	@Override
	public void updateBatch(List<CouponStrategy> listUpdate) {
		for(CouponStrategy ca : listUpdate){
			this.couponStrategyDAO.update(ca);
		}
	}

	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return couponStrategyDAO.getStatusList(inputParams, resultClass);
	}


	@Override
	public boolean isExistOperatorByStrategy(String strategy, String uuid) {
		// TODO Auto-generated method stub
		return false;
	}
}
