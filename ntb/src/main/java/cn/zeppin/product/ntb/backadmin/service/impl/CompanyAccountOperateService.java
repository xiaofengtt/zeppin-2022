/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountOperateDAO;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountOperateService;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.CompanyAccount.CompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountOperate;
import cn.zeppin.product.ntb.core.entity.CompanyAccountOperate.CompanyAccountOperateStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountOperate.CompanyAccountOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;

/**
 *
 */
@Service
public class CompanyAccountOperateService extends BaseService implements ICompanyAccountOperateService {

	@Autowired
	private ICompanyAccountOperateDAO companyAccountOperateDAO;
	
	@Autowired
	private ICompanyAccountDAO companyAccountDAO;
	
	@Override
	public CompanyAccountOperate insert(CompanyAccountOperate companyAccountOperate) {
		return companyAccountOperateDAO.insert(companyAccountOperate);
	}

	@Override
	public CompanyAccountOperate delete(CompanyAccountOperate companyAccountOperate) {
		return companyAccountOperateDAO.delete(companyAccountOperate);
	}

	@Override
	public CompanyAccountOperate update(CompanyAccountOperate companyAccountOperate) {
		return companyAccountOperateDAO.update(companyAccountOperate);
	}

	@Override
	public CompanyAccountOperate get(String uuid) {
		return companyAccountOperateDAO.get(uuid);
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
		return companyAccountOperateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return companyAccountOperateDAO.getCount(inputParams);
	}
	
	/**
	 * 审核
	 * @param CompanyAccountOperate
	 * @return result
	 * @throws TransactionException 
	 */
	public void check(CompanyAccountOperate cao) throws TransactionException {
		//审核通过更新操作数据
		if(CompanyAccountOperateStatus.CHECKED.equals(cao.getStatus())){
			if(CompanyAccountOperateType.ADD.equals(cao.getType())){
				//添加
				CompanyAccount ca = JSONUtils.json2obj(cao.getValue(), CompanyAccount.class);
				companyAccountDAO.insert(ca);
			}else if(CompanyAccountOperateType.EDIT.equals(cao.getType())){
				//修改
				CompanyAccount ca = companyAccountDAO.get(cao.getCompanyAccount());
				if(ca != null){
					cao.setOld(JSONUtils.obj2json(ca));
					
					CompanyAccount newCa = JSONUtils.json2obj(cao.getValue(), CompanyAccount.class);
					
					ca.setBranchBank(newCa.getBranchBank());
					ca.setAccountName(newCa.getAccountName());
					ca.setAccountNum(newCa.getAccountNum());
					ca.setCompanyName(newCa.getCompanyName());
					ca.setStatus(newCa.getStatus());
					
					companyAccountDAO.update(ca);
				}else{
					throw new TransactionException("企业账户信息不存在");
				}
			}else if(CompanyAccountOperateType.DELETE.equals(cao.getType())){
				//删除
				CompanyAccount ca = companyAccountDAO.get(cao.getCompanyAccount());
				if(ca != null){
					ca.setStatus(CompanyAccountStatus.DELETED);
					companyAccountDAO.update(ca);
				}else{
					throw new TransactionException("企业账户信息不存在");
				}
			}
		}
		if(CompanyAccountOperateStatus.CHECKED.equals(cao.getStatus()) && (cao.getReason() == null || "".equals(cao.getReason()))){
			cao.setReason("审核通过！");
		}else if(CompanyAccountOperateStatus.UNPASSED.equals(cao.getStatus()) && (cao.getReason() == null || "".equals(cao.getReason()))){
			cao.setReason("审核不通过！");
		}
		companyAccountOperateDAO.update(cao);
	}
	
	/**
	 * 获取分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return companyAccountOperateDAO.getStatusList(inputParams, resultClass);
	}
	
	/**
	 * 获取分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return companyAccountOperateDAO.getTypeList(inputParams, resultClass);
	}
}
