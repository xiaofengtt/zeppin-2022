/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductDailyDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductOperateDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductOperateService;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductOperateDailyVO;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductDaily;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductOperate;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductOperate.BankFinancialProductOperateStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductOperate.BankFinancialProductOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;

/**
 * @author hehe
 *
 */
@Service
public class BankFinancialProductOperateService extends BaseService implements IBankFinancialProductOperateService {

	@Autowired
	private IBankFinancialProductDAO bankFinancialProductDAO;
	
	@Autowired
	private IBankFinancialProductDailyDAO bankFinancialProductDailyDAO;
	
	@Autowired
	private IBankFinancialProductOperateDAO bankFinancialProductOperateDAO;
	
	@Override
	public BankFinancialProductOperate insert(BankFinancialProductOperate bankFinancialProductOperate) {
		return bankFinancialProductOperateDAO.insert(bankFinancialProductOperate);
	}

	@Override
	public BankFinancialProductOperate delete(BankFinancialProductOperate bankFinancialProductOperate) {
		bankFinancialProductOperate.setStatus(BankFinancialProductOperateStatus.DELETED);
		return bankFinancialProductOperateDAO.update(bankFinancialProductOperate);
	}

	@Override
	public BankFinancialProductOperate update(BankFinancialProductOperate bankFinancialProductOperate) {
		return bankFinancialProductOperateDAO.update(bankFinancialProductOperate);
	}

	@Override
	public BankFinancialProductOperate get(String uuid) {
		return bankFinancialProductOperateDAO.get(uuid);
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
		return bankFinancialProductOperateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return bankFinancialProductOperateDAO.getCount(inputParams);
	}
	
	/**
	 * 审核
	 * @param bfpo
	 * @return result
	 */
	public HashMap<String,Object> check(BankFinancialProductOperate bfpo) {
		HashMap<String,Object> result = new HashMap<String,Object>();
		//审核通过更新操作数据
		if(BankFinancialProductOperateStatus.CHECKED.equals(bfpo.getStatus())){
			if(BankFinancialProductOperateType.ADD.equals(bfpo.getType())){
				//添加
				BankFinancialProduct bfp = JSONUtils.json2obj(bfpo.getValue(), BankFinancialProduct.class);
				if(bankFinancialProductDAO.isExistBankFinancialProductByName(bfp.getName(),null)){
					result.put("result", false);
					result.put("message", "银行理财产品名称已存在！");
					return result;
				}
				if(bankFinancialProductDAO.isExistBankFinancialProductByScode(bfp.getScode(),null)){
					result.put("result", false);
					result.put("message", "银行理财产品编号已存在！");
					return result;
				}
				bankFinancialProductDAO.insert(bfp);
			}else if(BankFinancialProductOperateType.EDIT.equals(bfpo.getType())){
				//修改
				BankFinancialProduct bfp = bankFinancialProductDAO.get(bfpo.getBankFinancialProduct());
				if(bfp != null){
					if(!BankFinancialProductStatus.CHECKED.equals(bfp.getStatus())){
						result.put("result", false);
						result.put("message", "理财产品审核状态错误，无法完成操作！");
						return result;
					}
					BankFinancialProduct newBfpp = JSONUtils.json2obj(bfpo.getValue(), BankFinancialProduct.class);
					if(bankFinancialProductDAO.isExistBankFinancialProductByName(newBfpp.getName(),newBfpp.getUuid())){
						result.put("result", false);
						result.put("message", "银行理财产品名称已存在！");
						return result;
					}
					if(bankFinancialProductDAO.isExistBankFinancialProductByScode(newBfpp.getScode(),newBfpp.getUuid())){
						result.put("result", false);
						result.put("message", "银行理财产品编号已存在！");
						return result;
					}
					bankFinancialProductDAO.update(newBfpp);
				}else{
					result.put("result", false);
					result.put("message", "理财产品不存在，无法完成操作！");
					return result;
				}
			}else if(BankFinancialProductOperateType.DELETE.equals(bfpo.getType())){
				//删除
				BankFinancialProduct bfp = bankFinancialProductDAO.get(bfpo.getBankFinancialProduct());
				if(bfp != null){
					if(!BankFinancialProductStatus.CHECKED.equals(bfp.getStatus())){
						result.put("result", false);
						result.put("message", "理财产品审核状态错误，无法完成操作！");
						return result;
					}
					bfp.setStatus(BankFinancialProductStatus.DELETED);
					bankFinancialProductDAO.update(bfp);
				}else{
					result.put("result", false);
					result.put("message", "理财产品不存在，无法完成操作！");
					return result;
				}
			}else if(BankFinancialProductOperateType.NETVALUE.equals(bfpo.getType())){
				//净值录入
				BankFinancialProduct bfp = bankFinancialProductDAO.get(bfpo.getBankFinancialProduct());
				if(bfp != null){
					if(!BankFinancialProductStatus.CHECKED.equals(bfp.getStatus())){
						result.put("result", false);
						result.put("message", "理财产品审核状态错误，无法完成操作！");
						return result;
					}
					List<BankFinancialProductOperateDailyVO> list = JSONUtils.json2list(bfpo.getValue(), BankFinancialProductOperateDailyVO.class);
					for(BankFinancialProductOperateDailyVO bfpodVO : list){
						if(BankFinancialProductOperateType.ADD.equals(bfpodVO.getType())){//添加净值
							if(bfpodVO.getBankFinancialProductDaily() != null){
								bankFinancialProductDailyDAO.insert(bfpodVO.getBankFinancialProductDaily());
							}else{
								result.put("result", false);
								result.put("message", "净值信息有误，无法完成操作！");
								return result;
							}
						} else if (BankFinancialProductOperateType.EDIT.equals(bfpodVO.getType())) {
							if(bfpodVO.getBankFinancialProductDaily() != null){
								bankFinancialProductDailyDAO.update(bfpodVO.getBankFinancialProductDaily());
							}else{
								result.put("result", false);
								result.put("message", "净值信息有误，无法完成操作！");
								return result;
							}
						} else if (BankFinancialProductOperateType.DELETE.equals(bfpodVO.getType())) {
							if(bfpodVO.getBankFinancialProductDaily() != null){
								BankFinancialProductDaily bfpd = this.bankFinancialProductDailyDAO.get(bfpodVO.getBankFinancialProductDaily().getUuid());
								if(bfpd != null){
									bankFinancialProductDailyDAO.delete(bfpd);
								}else{
									result.put("result", false);
									result.put("message", "净值信息不存在，无法完成操作！");
									return result;
								}
							}else{
								result.put("result", false);
								result.put("message", "净值信息有误，无法完成操作！");
								return result;
							}
						} else {
							result.put("result", false);
							result.put("message", "净值信息更新类型有误，无法完成操作！");
							return result;
						}
					}
				}else{
					result.put("result", false);
					result.put("message", "理财产品不存在，无法完成操作！");
					return result;
				}
			}
		}
		if(BankFinancialProductOperateStatus.CHECKED.equals(bfpo.getStatus()) && (bfpo.getReason() == null || "".equals(bfpo.getReason()))){
			bfpo.setReason("审核通过！");
		}
		bankFinancialProductOperateDAO.update(bfpo);
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
		return bankFinancialProductOperateDAO.getStatusList(inputParams, resultClass);
	}
	
	/**
	 * 获取银行理财产品操作分状态列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return bankFinancialProductOperateDAO.getTypeList(inputParams, resultClass);
	}
}
