/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.ICouponStrategyDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICouponStrategyOperateDAO;
import cn.zeppin.product.ntb.backadmin.service.api.ICouponStrategyOperateService;
import cn.zeppin.product.ntb.core.entity.CouponStrategy;
import cn.zeppin.product.ntb.core.entity.CouponStrategy.CouponStrategyStatus;
import cn.zeppin.product.ntb.core.entity.CouponStrategyOperate;
import cn.zeppin.product.ntb.core.entity.CouponStrategyOperate.CouponStrategyOperateStatus;
import cn.zeppin.product.ntb.core.entity.CouponStrategyOperate.CouponStrategyOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;

/**
 *
 */
@Service
public class CouponStrategyOperateService extends BaseService implements ICouponStrategyOperateService {

	@Autowired
	private ICouponStrategyOperateDAO couponStrategyOperateDAO;
	
	@Autowired
	private ICouponStrategyDAO couponStrategyDAO;
	
	@Override
	public CouponStrategyOperate insert(CouponStrategyOperate couponStrategyOperate) {
		return couponStrategyOperateDAO.insert(couponStrategyOperate);
	}

	@Override
	public CouponStrategyOperate delete(CouponStrategyOperate couponStrategyOperate) {
		return couponStrategyOperateDAO.delete(couponStrategyOperate);
	}

	@Override
	public CouponStrategyOperate update(CouponStrategyOperate couponStrategyOperate) {
		return couponStrategyOperateDAO.update(couponStrategyOperate);
	}

	@Override
	public CouponStrategyOperate get(String uuid) {
		return couponStrategyOperateDAO.get(uuid);
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
		return couponStrategyOperateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return couponStrategyOperateDAO.getCount(inputParams);
	}
	
	/**
	 * 审核
	 * @param CouponStrategyOperate
	 * @return result
	 * @throws TransactionException 
	 */
	public void check(CouponStrategyOperate cao) throws TransactionException {
		//审核通过更新操作数据
		if(CouponStrategyOperateStatus.CHECKED.equals(cao.getStatus())){
			if(CouponStrategyOperateType.ADD.equals(cao.getType())){//添加
				CouponStrategy bfp = JSONUtils.json2obj(cao.getValue(), CouponStrategy.class);
				if(couponStrategyDAO.isExistOperatorByStrategy(bfp.getStrategyIdentification(), null)){
					throw new TransactionException("优惠券策略投放ID已存在！");
				}
				couponStrategyDAO.insert(bfp);
			} else if (CouponStrategyOperateType.EDIT.equals(cao.getType())){//编辑
				CouponStrategy bfp = couponStrategyDAO.get(cao.getCouponStrategy());
				if(bfp != null){
					if(CouponStrategyStatus.DELETED.equals(bfp.getStatus())){
						throw new TransactionException("优惠券策略状态不正确！");
					}
					CouponStrategy newBfpp = JSONUtils.json2obj(cao.getValue(), CouponStrategy.class);
					if(couponStrategyDAO.isExistOperatorByStrategy(bfp.getStrategyIdentification(), newBfpp.getUuid())){
						throw new TransactionException("优惠券策略投放ID已存在！");
					}
					
					cao.setOld(JSONUtils.obj2json(bfp));
					bfp.setCoupon(newBfpp.getCoupon());
					bfp.setExpiryDate(newBfpp.getExpiryDate());
					bfp.setStrategyIdentification(newBfpp.getStrategyIdentification());
					
					couponStrategyDAO.update(bfp);
				} else {
					throw new TransactionException("优惠券策略不存在！");
				}
			} else if (CouponStrategyOperateType.OPEN.equals(cao.getType()) || CouponStrategyOperateType.CLOSE.equals(cao.getType())) {
				CouponStrategy bfp = couponStrategyDAO.get(cao.getCouponStrategy());
				if(bfp != null){
					if(CouponStrategyStatus.DELETED.equals(bfp.getStatus())){
						throw new TransactionException("优惠券策略状态不正确！");
					}
					CouponStrategy newBfpp = JSONUtils.json2obj(cao.getValue(), CouponStrategy.class);
					
					cao.setOld(JSONUtils.obj2json(bfp));
					bfp.setStatus(newBfpp.getStatus());
					
					couponStrategyDAO.update(bfp);
				} else {
					throw new TransactionException("优惠券策略不存在！");
				}
			} else if (CouponStrategyOperateType.DELETE.equals(cao.getType())){
				//删除
				CouponStrategy bfp = couponStrategyDAO.get(cao.getCouponStrategy());
				if(bfp != null){
					if(CouponStrategyStatus.DELETED.equals(bfp.getStatus())){
						throw new TransactionException("优惠券策略审核状态错误，无法完成操作！");
					}
					bfp.setStatus(CouponStrategyStatus.DELETED);
					couponStrategyDAO.update(bfp);
				}else{
					throw new TransactionException("优惠券策略不存在，无法完成操作！");
				}
			}
		}
		
		if(CouponStrategyOperateStatus.CHECKED.equals(cao.getStatus()) && (cao.getReason() == null || "".equals(cao.getReason()))){
			cao.setReason("审核通过！");
		}else if(CouponStrategyOperateStatus.UNPASSED.equals(cao.getStatus()) && (cao.getReason() == null || "".equals(cao.getReason()))){
			cao.setReason("审核不通过！");
		}
		couponStrategyOperateDAO.update(cao);
	}
	
	/**
	 * 获取分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return couponStrategyOperateDAO.getStatusList(inputParams, resultClass);
	}
	
	/**
	 * 获取分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return couponStrategyOperateDAO.getTypeList(inputParams, resultClass);
	}

}
