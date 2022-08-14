/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountHistoryDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IPlatformAccountDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IQcbCompanyFeeOperateDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IQcbCompanyFeeOperateService;
import cn.zeppin.product.ntb.backadmin.vo.QcbCompanyAccountFeeVO;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyFeeOperate;
import cn.zeppin.product.ntb.core.entity.QcbCompanyFeeOperate.QcbCompanyFeeOperateStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyFeeOperate.QcbCompanyFeeOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountHistoryDAO;
import cn.zeppin.product.utility.JSONUtils;

/**
 *
 */
@Service
public class QcbCompanyFeeOperateService extends BaseService implements IQcbCompanyFeeOperateService {

	@Autowired
	private ICompanyAccountDAO companyAccountDAO;
	
	@Autowired
	private IPlatformAccountDAO platformAccountDAO;
	
	@Autowired
	private IQcbCompanyAccountDAO qcbCompanyAccountDAO;
	
	@Autowired
	private ICompanyAccountHistoryDAO companyAccountHistoryDAO;
	
	@Autowired
	private IQcbCompanyAccountHistoryDAO qcbCompanyAccountHistoryDAO;
	
	@Autowired
	private IQcbCompanyFeeOperateDAO qcbCompanyFeeOperateDAO;
	
	@Override
	public QcbCompanyFeeOperate insert(QcbCompanyFeeOperate qcbCompanyFeeOperate) {
		return qcbCompanyFeeOperateDAO.insert(qcbCompanyFeeOperate);
	}

	@Override
	public QcbCompanyFeeOperate delete(QcbCompanyFeeOperate qcbCompanyFeeOperate) {
		return qcbCompanyFeeOperateDAO.delete(qcbCompanyFeeOperate);
	}

	@Override
	public QcbCompanyFeeOperate update(QcbCompanyFeeOperate qcbCompanyFeeOperate) {
		return qcbCompanyFeeOperateDAO.update(qcbCompanyFeeOperate);
	}

	@Override
	public QcbCompanyFeeOperate get(String uuid) {
		return qcbCompanyFeeOperateDAO.get(uuid);
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
		return qcbCompanyFeeOperateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbCompanyFeeOperateDAO.getCount(inputParams);
	}
	
	/**
	 * 审核
	 * @param CompanyAccountOperate
	 * @return result
	 * @throws TransactionException 
	 */
	public void check(QcbCompanyFeeOperate caho) throws TransactionException {
		//审核通过更新操作数据
		if(QcbCompanyFeeOperateStatus.CHECKED.equals(caho.getStatus())){
			QcbCompanyAccountFeeVO qcafvo = JSONUtils.json2obj(caho.getValue(), QcbCompanyAccountFeeVO.class);
			
			if(QcbCompanyFeeOperateType.FEE_TICKET.equals(caho.getType())){
				//开票费率
				if(qcafvo.getFeeTicket().compareTo(BigDecimal.ZERO) <= 0){
					throw new TransactionException("费率应大于0！");
				}
				
				QcbCompanyAccount qca = this.qcbCompanyAccountDAO.get(qcafvo.getUuid());
				qca.setFeeTicket(qcafvo.getFeeTicket());
				this.qcbCompanyAccountDAO.update(qca);
				
			}
		}	
		if(QcbCompanyFeeOperateStatus.CHECKED.equals(caho.getStatus()) && (caho.getReason() == null || "".equals(caho.getReason()))){
			caho.setReason("审核通过！");
		}else if(QcbCompanyFeeOperateStatus.UNPASSED.equals(caho.getStatus()) && (caho.getReason() == null || "".equals(caho.getReason()))){
			caho.setReason("审核不通过！");
		}
		qcbCompanyFeeOperateDAO.update(caho);
	}
	
	/**
	 * 获取分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return qcbCompanyFeeOperateDAO.getStatusList(inputParams, resultClass);
	}
	
	/**
	 * 获取分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return qcbCompanyFeeOperateDAO.getTypeList(inputParams, resultClass);
	}
}
