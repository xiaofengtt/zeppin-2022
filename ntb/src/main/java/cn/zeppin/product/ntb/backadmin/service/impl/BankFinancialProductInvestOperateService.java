/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductInvestDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductInvestOperateDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductInvestOperateService;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvest;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvest.BankFinancialProductInvestStage;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate.BankFinancialProductInvestOperateStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvest.BankFinancialProductInvestStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate.BankFinancialProductInvestOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;

/**
 * @author hehe
 *
 */
@Service
public class BankFinancialProductInvestOperateService extends BaseService implements IBankFinancialProductInvestOperateService {

	@Autowired
	private IBankFinancialProductInvestDAO bankFinancialProductInvestDAO;
	
	@Autowired
	private IBankFinancialProductInvestOperateDAO bankFinancialProductInvestOperateDAO;
	
	@Override
	public BankFinancialProductInvestOperate insert(BankFinancialProductInvestOperate bankFinancialProductInvestOperate) {
		return bankFinancialProductInvestOperateDAO.insert(bankFinancialProductInvestOperate);
	}

	@Override
	public BankFinancialProductInvestOperate delete(BankFinancialProductInvestOperate bankFinancialProductInvestOperate) {
		bankFinancialProductInvestOperate.setStatus(BankFinancialProductInvestOperateStatus.DELETED);
		return bankFinancialProductInvestOperateDAO.update(bankFinancialProductInvestOperate);
	}

	@Override
	public BankFinancialProductInvestOperate update(BankFinancialProductInvestOperate bankFinancialProductInvestOperate) {
		return bankFinancialProductInvestOperateDAO.update(bankFinancialProductInvestOperate);
	}

	@Override
	public BankFinancialProductInvestOperate get(String uuid) {
		return bankFinancialProductInvestOperateDAO.get(uuid);
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
		return bankFinancialProductInvestOperateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return bankFinancialProductInvestOperateDAO.getCount(inputParams);
	}
	
	/**
	 * 审核
	 * @param bfpio
	 * @return result
	 */
	public HashMap<String,Object> check(BankFinancialProductInvestOperate bfpio) {
		HashMap<String,Object> result = new HashMap<String,Object>();
		//审核通过更新操作数据
		if(BankFinancialProductInvestOperateStatus.CHECKED.equals(bfpio.getStatus())){
			if(BankFinancialProductInvestOperateType.ADD.equals(bfpio.getType())){
				//添加
				BankFinancialProductInvest bfpi = JSONUtils.json2obj(bfpio.getValue(), BankFinancialProductInvest.class);
				bankFinancialProductInvestDAO.insert(bfpi);
			}else if(BankFinancialProductInvestOperateType.EDIT.equals(bfpio.getType())){
				//修改
				BankFinancialProductInvest bfpp = bankFinancialProductInvestDAO.get(bfpio.getBankFinancialProductInvest());
				if(bfpp != null){
					if(!BankFinancialProductInvestStatus.UNCHECKED.equals(bfpp.getStatus())){
						result.put("result", false);
						result.put("message", "理财产品投资审核状态错误，无法完成操作！");
						return result;
					}
					BankFinancialProductInvest newBfpp = JSONUtils.json2obj(bfpio.getValue(), BankFinancialProductInvest.class);
					bankFinancialProductInvestDAO.update(newBfpp);
				}else{
					result.put("result", false);
					result.put("message", "投资信息不存在，无法完成操作！");
					return result;
				}
			}else if(BankFinancialProductInvestOperateType.DELETE.equals(bfpio.getType())){
				//删除
				BankFinancialProductInvest bfpp = bankFinancialProductInvestDAO.get(bfpio.getBankFinancialProductInvest());
				if(bfpp != null){
					if(!BankFinancialProductInvestStatus.UNCHECKED.equals(bfpp.getStatus())){
						result.put("result", false);
						result.put("message", "理财产品投资审核状态错误，无法完成操作！");
						return result;
					}
					bfpp.setStatus(BankFinancialProductInvestStatus.DELETED);
					bankFinancialProductInvestDAO.update(bfpp);
				}else{
					result.put("result", false);
					result.put("message", "投资信息不存在，无法完成操作！");
					return result;
				}
			}else if(BankFinancialProductInvestOperateType.CHECK.equals(bfpio.getType())){
				//审核
				BankFinancialProductInvest bfpp = bankFinancialProductInvestDAO.get(bfpio.getBankFinancialProductInvest());
				if(bfpp != null){
					if(!BankFinancialProductInvestStatus.UNCHECKED.equals(bfpp.getStatus())){
						result.put("result", false);
						result.put("message", "理财产品投资审核状态错误，无法完成操作！");
						return result;
					}
					bfpp.setStatus(bfpio.getValue());
					bankFinancialProductInvestDAO.update(bfpp);
				}else{
					result.put("result", false);
					result.put("message", "投资信息不存在，无法完成操作！");
					return result;
				}
			}else if(BankFinancialProductInvestOperateType.REDEEM.equals(bfpio.getType())){
				//赎回
				BankFinancialProductInvest bfpp = bankFinancialProductInvestDAO.get(bfpio.getBankFinancialProductInvest());
				if(bfpp != null){
					if(!BankFinancialProductInvestStatus.CHECKED.equals(bfpp.getStatus()) || BankFinancialProductInvestStage.INCOME.equals(bfpp.getStage())){
						result.put("result", false);
						result.put("message", "理财产品投资审核状态错误，无法完成操作！");
						return result;
					}
					BankFinancialProductInvest newBfpp = JSONUtils.json2obj(bfpio.getValue(), BankFinancialProductInvest.class);
					bankFinancialProductInvestDAO.update(newBfpp);
				}else{
					result.put("result", false);
					result.put("message", "投资信息不存在，无法完成操作！");
					return result;
				}
			}
		}
		bankFinancialProductInvestOperateDAO.update(bfpio);
		result.put("result", true);
		return result;
	}
	
	/**
	 * 获取银行理财产品发布操作分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		return bankFinancialProductInvestOperateDAO.getStatusList(resultClass);
	}
	
	/**
	 * 获取银行理财产品操作分状态列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return bankFinancialProductInvestOperateDAO.getTypeList(inputParams, resultClass);
	}
}
