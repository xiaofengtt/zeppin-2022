/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductPublishDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductPublishOperateDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishOperateService;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish.BankFinancialProductPublishStage;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublishOperate;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish.BankFinancialProductPublishStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublishOperate.BankFinancialProductPublishOperateStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublishOperate.BankFinancialProductPublishOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;

/**
 * @author hehe
 *
 */
@Service
public class BankFinancialProductPublishOperateService extends BaseService implements IBankFinancialProductPublishOperateService {

	@Autowired
	private IBankFinancialProductPublishOperateDAO bankFinancialProductPublishOperateDAO;
	
	@Autowired
	private IBankFinancialProductPublishDAO bankFinancialProductPublishDAO;
	
	@Override
	public BankFinancialProductPublishOperate insert(BankFinancialProductPublishOperate bankFinancialProductPublishOperate) {
		return bankFinancialProductPublishOperateDAO.insert(bankFinancialProductPublishOperate);
	}

	@Override
	public BankFinancialProductPublishOperate delete(BankFinancialProductPublishOperate bankFinancialProductPublishOperate) {
		bankFinancialProductPublishOperate.setStatus(BankFinancialProductPublishOperateStatus.DELETED);
		return bankFinancialProductPublishOperateDAO.update(bankFinancialProductPublishOperate);
	}

	@Override
	public BankFinancialProductPublishOperate update(BankFinancialProductPublishOperate bankFinancialProductPublishOperate) {
		return bankFinancialProductPublishOperateDAO.update(bankFinancialProductPublishOperate);
	}

	@Override
	public BankFinancialProductPublishOperate get(String uuid) {
		return bankFinancialProductPublishOperateDAO.get(uuid);
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
		return bankFinancialProductPublishOperateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return bankFinancialProductPublishOperateDAO.getCount(inputParams);
	}
	
	/**
	 * 审核
	 * @param bfppo
	 * @return result
	 */
	public HashMap<String,Object> check(BankFinancialProductPublishOperate bfppo) {
		HashMap<String,Object> result = new HashMap<String,Object>();
		//审核通过更新操作数据
		if(BankFinancialProductPublishOperateStatus.CHECKED.equals(bfppo.getStatus())){
			if(BankFinancialProductPublishOperateType.ADD.equals(bfppo.getType())){
				//添加
				BankFinancialProductPublish bfpp = JSONUtils.json2obj(bfppo.getValue(), BankFinancialProductPublish.class);
				Map<String,String> search = new HashMap<String, String>();
				search.put("bankFinancialProduct", bfpp.getBankFinancialProduct());
				search.put("status", BankFinancialProductStatus.CHECKED);
				Integer count = bankFinancialProductPublishDAO.getCount(search);
				if(count != null && count > 0){
					result.put("result", false);
					result.put("message", "该银行理财产品已存在发布信息！");
					return result;
				}
				if(bankFinancialProductPublishDAO.isExistBankFinancialProductPublishByName(bfpp.getName(),null)){
					result.put("result", false);
					result.put("message", "银行理财产品发布名称已存在！");
					return result;
				}
				if(bankFinancialProductPublishDAO.isExistBankFinancialProductPublishByScode(bfpp.getScode(),null)){
					result.put("result", false);
					result.put("message", "银行理财产品发布编号已存在！");
					return result;
				}
				bankFinancialProductPublishDAO.insert(bfpp);
			}else if(BankFinancialProductPublishOperateType.EDIT.equals(bfppo.getType())){
				//修改
				BankFinancialProductPublish bfpp = bankFinancialProductPublishDAO.get(bfppo.getBankFinancialProductPublish());
				if(bfpp != null){
					if(!BankFinancialProductPublishStatus.CHECKED.equals(bfpp.getStatus())){
						result.put("result", false);
						result.put("message", "理财产品发布信息审核状态错误，无法完成操作！");
						return result;
					}
					BankFinancialProductPublish newBfpp = JSONUtils.json2obj(bfppo.getValue(), BankFinancialProductPublish.class);
					if(bankFinancialProductPublishDAO.isExistBankFinancialProductPublishByName(newBfpp.getName(),newBfpp.getUuid())){
						result.put("result", false);
						result.put("message", "银行理财产品发布名称已存在！");
						return result;
					}
					if(bankFinancialProductPublishDAO.isExistBankFinancialProductPublishByScode(newBfpp.getScode(),newBfpp.getUuid())){
						result.put("result", false);
						result.put("message", "银行理财产品发布编号已存在！");
						return result;
					}
					bankFinancialProductPublishDAO.update(newBfpp);
				}else{
					result.put("result", false);
					result.put("message", "理财产品发布信息不存在");
					return result;
				}
			}else if(BankFinancialProductPublishOperateType.DELETE.equals(bfppo.getType())){
				//删除
				BankFinancialProductPublish bfpp = bankFinancialProductPublishDAO.get(bfppo.getBankFinancialProductPublish());
				if(bfpp != null){
					if(!BankFinancialProductPublishStatus.CHECKED.equals(bfpp.getStatus())){
						result.put("result", false);
						result.put("message", "理财产品发布信息审核状态错误，无法完成操作！");
						return result;
					}
					bfpp.setStatus(BankFinancialProductPublishStatus.DELETED);
					bankFinancialProductPublishDAO.update(bfpp);
				}else{
					result.put("result", false);
					result.put("message", "理财产品发布信息不存在");
					return result;
				}
			}else if(BankFinancialProductPublishOperateType.EXCEPTION.equals(bfppo.getType())){
				//异常下线
				BankFinancialProductPublish bfpp = bankFinancialProductPublishDAO.get(bfppo.getBankFinancialProductPublish());
				if(bfpp != null){
					if(!BankFinancialProductPublishStatus.CHECKED.equals(bfpp.getStatus())){
						result.put("result", false);
						result.put("message", "理财产品发布信息审核状态错误，无法完成操作！");
						return result;
					}
					if(!BankFinancialProductPublishStage.UNSTART.equals(bfpp.getStage()) && !BankFinancialProductPublishStage.COLLECT.equals(bfpp.getStage())){
						result.put("result", false);
						result.put("message", "理财产品发布信息审核状态错误，无法完成操作！");
						return result;
					}
					bfpp.setStage(BankFinancialProductPublishStage.EXCEPTION);
					bankFinancialProductPublishDAO.update(bfpp);
				}else{
					result.put("result", false);
					result.put("message", "理财产品发布信息不存在");
					return result;
				}
			}
		}
		if(BankFinancialProductPublishOperateStatus.CHECKED.equals(bfppo.getStatus()) && (bfppo.getReason() == null || "".equals(bfppo.getReason()))){
			bfppo.setReason("审核通过！");
		}
		bankFinancialProductPublishOperateDAO.update(bfppo);
		result.put("result", true);
		return result;
	}
	
	/**
	 * 获取银行理财产品发布操作分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return bankFinancialProductPublishOperateDAO.getStatusList(inputParams, resultClass);
	}
	
	/**
	 * 获取银行理财产品发布操作分状态列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return bankFinancialProductPublishOperateDAO.getTypeList(inputParams, resultClass);
	}
}
