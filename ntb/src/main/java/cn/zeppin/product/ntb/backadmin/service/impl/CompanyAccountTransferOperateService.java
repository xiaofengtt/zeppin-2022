/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountHistoryDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountTransferOperateDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IPlatformAccountDAO;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountTransferOperateService;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.CompanyAccountTransferOperate;
import cn.zeppin.product.ntb.core.entity.CompanyAccountTransferOperate.CompanyAccountTransferOperateStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountTransferOperate.CompanyAccountTransferOperateType;
import cn.zeppin.product.ntb.core.entity.PlatformAccount;
import cn.zeppin.product.ntb.core.entity.PlatformAccount.PlatformAccountUuid;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;

/**
 *
 */
@Service
public class CompanyAccountTransferOperateService extends BaseService implements ICompanyAccountTransferOperateService {

	@Autowired
	private ICompanyAccountDAO companyAccountDAO;
	
	@Autowired
	private IPlatformAccountDAO platformAccountDAO;
	
	@Autowired
	private ICompanyAccountHistoryDAO companyAccountHistoryDAO;
	
	@Autowired
	private ICompanyAccountTransferOperateDAO companyAccountTransferOperateDAO;
	
	@Override
	public CompanyAccountTransferOperate insert(CompanyAccountTransferOperate companyAccountTransferOperate) {
		return companyAccountTransferOperateDAO.insert(companyAccountTransferOperate);
	}

	@Override
	public CompanyAccountTransferOperate delete(CompanyAccountTransferOperate companyAccountTransferOperate) {
		return companyAccountTransferOperateDAO.delete(companyAccountTransferOperate);
	}

	@Override
	public CompanyAccountTransferOperate update(CompanyAccountTransferOperate companyAccountTransferOperate) {
		return companyAccountTransferOperateDAO.update(companyAccountTransferOperate);
	}

	@Override
	public CompanyAccountTransferOperate get(String uuid) {
		return companyAccountTransferOperateDAO.get(uuid);
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
		return companyAccountTransferOperateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return companyAccountTransferOperateDAO.getCount(inputParams);
	}
	
	/**
	 * 审核
	 * @param CompanyAccountOperate
	 * @return result
	 * @throws TransactionException 
	 */
	public void check(CompanyAccountTransferOperate caho) throws TransactionException {
		//审核通过更新操作数据
		if(CompanyAccountTransferOperateStatus.CHECKED.equals(caho.getStatus())){
			CompanyAccountHistory cah = JSONUtils.json2obj(caho.getValue(), CompanyAccountHistory.class);
			if(cah.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0){
				throw new TransactionException("交易金额应大于0！");
			}
			if(CompanyAccountTransferOperateType.RECHARGE.equals(caho.getType())){
				//充值
				CompanyAccount ca = companyAccountDAO.get(cah.getCompanyAccountIn());
				ca.setAccountBalance(ca.getAccountBalance().add(cah.getTotalAmount()));
				companyAccountDAO.update(ca);
				
				//20180622增加记录本次余额信息
				cah.setAccountBalance(ca.getAccountBalance());
				
				PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
				pa.setTotalAmount(pa.getTotalAmount().add(cah.getTotalAmount()));
				platformAccountDAO.update(pa);
				PlatformAccount pab = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
				pab.setTotalAmount(pab.getTotalAmount().add(cah.getTotalAmount()));
				platformAccountDAO.update(pab);
			}else if(CompanyAccountTransferOperateType.EXPEND.equals(caho.getType())){
				//费用录入
				CompanyAccount ca = companyAccountDAO.get(cah.getCompanyAccountOut());
				if(cah.getTotalAmount().compareTo(ca.getAccountBalance()) > 0){
					throw new TransactionException("企业账户余额不足！");
				}
				
				ca.setAccountBalance(ca.getAccountBalance().subtract(cah.getTotalAmount()));
				companyAccountDAO.update(ca);
				
				//20180622增加记录本次余额信息
				cah.setAccountBalance(ca.getAccountBalance());
				
				PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
				pa.setTotalAmount(pa.getTotalAmount().subtract(cah.getTotalAmount()));
				platformAccountDAO.update(pa);
				PlatformAccount pab = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
				pab.setTotalAmount(pab.getTotalAmount().subtract(cah.getTotalAmount()));
				platformAccountDAO.update(pab);
			}else if(CompanyAccountTransferOperateType.TRANSFER.equals(caho.getType())){
				//转账
				CompanyAccount cao = companyAccountDAO.get(cah.getCompanyAccountOut());
				if((cah.getTotalAmount().add(cah.getPoundage())).compareTo(cao.getAccountBalance()) > 0){
					throw new TransactionException("企业账户余额不足！");
				}
				
				cao.setAccountBalance(cao.getAccountBalance().subtract(cah.getTotalAmount()).subtract(cah.getPoundage()));
				companyAccountDAO.update(cao);
				
				//20180622增加记录本次余额信息
				cah.setAccountBalance(cao.getAccountBalance());
				
				CompanyAccount cai = companyAccountDAO.get(cah.getCompanyAccountIn());
				cai.setAccountBalance(cai.getAccountBalance().add(cah.getTotalAmount()));
				companyAccountDAO.update(cai);
				
				cah.setAccountBalanceIn(cai.getAccountBalance());//入账账户余额记录
				
				PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
				pa.setTotalAmount(pa.getTotalAmount().subtract(cah.getPoundage()));
				platformAccountDAO.update(pa);
				PlatformAccount pab = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
				pab.setTotalAmount(pab.getTotalAmount().subtract(cah.getPoundage()));
				platformAccountDAO.update(pab);
				
				if(cah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
					CompanyAccountHistory cahe = new CompanyAccountHistory();
					cahe.setUuid(UUID.randomUUID().toString());
					cahe.setType(CompanyAccountHistoryType.EXPEND);
					cahe.setRelated(cah.getUuid());
					cahe.setTotalAmount(cah.getPoundage());
					cahe.setCompanyAccountOut(cao.getUuid());
					cahe.setPoundage(BigDecimal.ZERO);
					cahe.setStatus(CompanyAccountHistoryStatus.NORMAL);
					cahe.setCreatetime(new Timestamp(System.currentTimeMillis()));
					cahe.setCreator(cah.getCreator());
					
					//20180622增加记录本次余额信息
					cah.setAccountBalance(cao.getAccountBalance());
					companyAccountHistoryDAO.insert(cahe);
					cah.setRelated(cahe.getUuid());
				}
			}
			companyAccountHistoryDAO.insert(cah);
		}	
		if(CompanyAccountTransferOperateStatus.CHECKED.equals(caho.getStatus()) && (caho.getReason() == null || "".equals(caho.getReason()))){
			caho.setReason("审核通过！");
		}else if(CompanyAccountTransferOperateStatus.UNPASSED.equals(caho.getStatus()) && (caho.getReason() == null || "".equals(caho.getReason()))){
			caho.setReason("审核不通过！");
		}
		companyAccountTransferOperateDAO.update(caho);
	}
	
	/**
	 * 获取分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return companyAccountTransferOperateDAO.getStatusList(inputParams, resultClass);
	}
	
	/**
	 * 获取分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return companyAccountTransferOperateDAO.getTypeList(inputParams, resultClass);
	}
}
