/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBkPaymentDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBkPaymentOperateDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBkPaymentOperateService;
import cn.zeppin.product.ntb.core.entity.BkPayment;
import cn.zeppin.product.ntb.core.entity.BkPayment.BkPaymentStatus;
import cn.zeppin.product.ntb.core.entity.BkPaymentOperate;
import cn.zeppin.product.ntb.core.entity.BkPaymentOperate.BkPaymentOperateStatus;
import cn.zeppin.product.ntb.core.entity.BkPaymentOperate.BkPaymentOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;

/**
 * @author hehe
 *
 */
@Service
public class BkPaymentOperateService extends BaseService implements IBkPaymentOperateService {

	@Autowired
	private IBkPaymentDAO bkPaymentDAO;
	
	@Autowired
	private IBkPaymentOperateDAO bkPaymentOperateDAO;
	
	@Override
	public BkPaymentOperate insert(BkPaymentOperate bkPaymentOperate) {
		return bkPaymentOperateDAO.insert(bkPaymentOperate);
	}

	@Override
	public BkPaymentOperate delete(BkPaymentOperate bkPaymentOperate) {
		bkPaymentOperate.setStatus(BkPaymentOperateStatus.DELETED);
		return bkPaymentOperateDAO.update(bkPaymentOperate);
	}

	@Override
	public BkPaymentOperate update(BkPaymentOperate bkPaymentOperate) {
		return bkPaymentOperateDAO.update(bkPaymentOperate);
	}

	@Override
	public BkPaymentOperate get(String uuid) {
		return bkPaymentOperateDAO.get(uuid);
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
		return bkPaymentOperateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return bkPaymentOperateDAO.getCount(inputParams);
	}
	
	/**
	 * 审核
	 * @param bfpo
	 * @return result
	 * @throws TransactionException 
	 */
	public void check(BkPaymentOperate bfpo) throws TransactionException {
		
		//审核通过更新操作数据
		if(BkPaymentOperateStatus.CHECKED.equals(bfpo.getStatus())){
			if(BkPaymentOperateType.ADD.equals(bfpo.getType())){//添加
				BkPayment bfp = JSONUtils.json2obj(bfpo.getValue(), BkPayment.class);
				if(bkPaymentDAO.isExistBkPayment(bfp.getPayment(),null)){
					throw new TransactionException("支付方式名称已存在！");
				}
				bkPaymentDAO.insert(bfp);
			} else if (BkPaymentOperateType.EDIT.equals(bfpo.getType())){//编辑
				BkPayment bfp = bkPaymentDAO.get(bfpo.getBkPayment());
				if(bfp != null){
					if(!BkPaymentStatus.NORMAL.equals(bfp.getStatus())){
						throw new TransactionException("支付方式状态不正确！");
					}
					BkPayment newBfpp = JSONUtils.json2obj(bfpo.getValue(), BkPayment.class);
					if(bkPaymentDAO.isExistBkPayment(newBfpp.getPayment(),newBfpp.getUuid())){
						throw new TransactionException("支付方式名称已存在！");
					}
					
					bfpo.setOld(JSONUtils.obj2json(bfp));
					
					bfp.setPayment(newBfpp.getPayment());
					bfp.setPaymentDes(newBfpp.getPaymentDes());
					
					bkPaymentDAO.update(bfp);
				} else {
					throw new TransactionException("支付方式不存在！");
				}
			} else if (BkPaymentOperateType.OPEN.equals(bfpo.getType()) || BkPaymentOperateType.CLOSED.equals(bfpo.getType())) {
				BkPayment bfp = bkPaymentDAO.get(bfpo.getBkPayment());
				if(bfp != null){
					if(!BkPaymentStatus.NORMAL.equals(bfp.getStatus())){
						throw new TransactionException("支付方式状态不正确！");
					}
					BkPayment newBfpp = JSONUtils.json2obj(bfpo.getValue(), BkPayment.class);
					
					bfpo.setOld(JSONUtils.obj2json(bfp));
					
					bfp.setFlagSwitch(newBfpp.getFlagSwitch());
					
					bkPaymentDAO.update(bfp);
				} else {
					throw new TransactionException("支付方式不存在！");
				}
			} else if (BkPaymentOperateType.DELETE.equals(bfpo.getType())){
				//删除
				BkPayment bfp = bkPaymentDAO.get(bfpo.getBkPayment());
				if(bfp != null){
					if(!BkPaymentStatus.NORMAL.equals(bfp.getStatus())){
						throw new TransactionException("支付方式审核状态错误，无法完成操作！");
					}
					bfp.setStatus(BkPaymentStatus.DELETED);
					bkPaymentDAO.update(bfp);
				}else{
					throw new TransactionException("支付方式不存在，无法完成操作！");
				}
			}
		}
		
		if(BkPaymentOperateStatus.CHECKED.equals(bfpo.getStatus()) && (bfpo.getReason() == null || "".equals(bfpo.getReason()))){
			bfpo.setReason("审核通过！");
		}else if(BkPaymentOperateStatus.UNPASSED.equals(bfpo.getStatus()) && (bfpo.getReason() == null || "".equals(bfpo.getReason()))){
			bfpo.setReason("审核不通过！");
		}
		bkPaymentOperateDAO.update(bfpo);
	}
	
	/**
	 * 获取支付方式操作分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return bkPaymentOperateDAO.getStatusList(inputParams, resultClass);
	}
	
	/**
	 * 获取支付方式操作分状态列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return bkPaymentOperateDAO.getTypeList(inputParams, resultClass);
	}
}
