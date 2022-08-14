/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.ICouponDAO;
import cn.zeppin.product.ntb.backadmin.service.api.ICouponService;
import cn.zeppin.product.ntb.core.entity.Coupon;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 *
 */
@Service
public class CouponService extends BaseService implements ICouponService {

	@Autowired
	private ICouponDAO couponDAO;
	
	@Override
	public Coupon insert(Coupon coupon) {
		return couponDAO.insert(coupon);
	}

	@Override
	public Coupon delete(Coupon coupon) {
		return couponDAO.delete(coupon);
	}

	@Override
	public Coupon update(Coupon coupon) {
		return couponDAO.update(coupon);
	}

	@Override
	public Coupon get(String uuid) {
		return couponDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询Coupon结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return couponDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return couponDAO.getCount(inputParams);
	}
	
	/**
	 * 获取分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return couponDAO.getTypeList(inputParams, resultClass);
	}

	@Override
	public void updateBatch(List<Coupon> listUpdate) {
		for(Coupon ca : listUpdate){
			this.couponDAO.update(ca);
		}
	}

	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return couponDAO.getStatusList(inputParams, resultClass);
	}

	@Override
	public void updateExpiryDate() {
		// TODO Auto-generated method stub
		this.couponDAO.updateExpiryDate();
	}

}
