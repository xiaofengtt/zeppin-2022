/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IFundPublishDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IMobileCodeDAO;
import cn.zeppin.product.ntb.core.entity.FundPublish;
import cn.zeppin.product.ntb.core.entity.FundPublish.FundPublishUuid;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployee.QcbEmployeeStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeHistoryDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 */
@Service
public class QcbEmployeeService extends BaseService implements IQcbEmployeeService {

	@Autowired
	private IQcbEmployeeDAO qcbEmployeeDAO;
	
	@Autowired
	private IQcbEmployeeHistoryDAO qcbEmployeeHistoryDAO;
	
	@Autowired
	private IMobileCodeDAO mobileCodeDAO;
	
	@Autowired
	private IFundPublishDAO fundPublishDAO;
	
	@Override
	public QcbEmployee insert(QcbEmployee qcbEmployee) {
		return qcbEmployeeDAO.insert(qcbEmployee);
	}

	@Override
	public QcbEmployee delete(QcbEmployee qcbEmployee) {
		qcbEmployee.setStatus(QcbEmployeeStatus.DELETED);
		return qcbEmployeeDAO.update(qcbEmployee);
	}

	@Override
	public QcbEmployee update(QcbEmployee qcbEmployee) {
		return qcbEmployeeDAO.update(qcbEmployee);
	}

	@Override
	public QcbEmployee get(String uuid) {
		return qcbEmployeeDAO.get(uuid);
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
		return qcbEmployeeDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbEmployeeDAO.getCount(inputParams);
	}

	@Override
	public QcbEmployee getByMobile(String mobile) {
		return qcbEmployeeDAO.getByMobile(mobile);
	}

	@Override
	public QcbEmployee getByOpenId(String openId) {
		return qcbEmployeeDAO.getByOpenId(openId);
	}
	
	@Override
	public boolean isExistQcbEmployeeByMobile(String mobile, String uuid) {
		return qcbEmployeeDAO.isExistQcbEmployeeByMobile(mobile, uuid);
	}

	@Override
	public boolean isExistQcbEmployeeByIdcard(String idcard, String uuid) {
		return qcbEmployeeDAO.isExistQcbEmployeeByIdcard(idcard, uuid);
	}

	@Override
	public void updateLogin(MobileCode mc, QcbEmployee qe, QcbEmployee employee) {
		this.mobileCodeDAO.update(mc);
		if(qe != null){
			this.qcbEmployeeDAO.update(qe);
		}
		this.qcbEmployeeDAO.update(employee);
	}

	@Override
	public List<Entity> getListForNTBPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return this.qcbEmployeeDAO.getListForNTBPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCountForNTB(Map<String, String> inputParams) {
		return this.qcbEmployeeDAO.getCountForNTB(inputParams);
	}

	@Override
	public List<Entity> getStatusListForNTB(Map<String, String> inputParams,Class<? extends Entity> resultClass) {
		return this.qcbEmployeeDAO.getStatusListForNTB(inputParams, resultClass);
	}
	
	@Override
	public void updateBatch(List<QcbEmployee> listUpdate) {
		try {
			for(QcbEmployee qe : listUpdate){
				this.qcbEmployeeDAO.update(qe);	
			}
		} catch (Exception e) {
			super.flushAll();
		}
	}
	
	/**
	 * 余额自动转入活期理财
	 */
	public void updateCurrentAccount(){
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("flagCurrent", "1");
		inputParams.put("accountBalance", "1");
		
		List<Entity> list = this.qcbEmployeeDAO.getListForPage(inputParams, -1, -1, null, QcbEmployee.class);
		if(list != null && list.size() > 0){
			//获取当前净值
			FundPublish fp = this.fundPublishDAO.get(FundPublishUuid.CURRENT);
			BigDecimal netValue = fp.getNetWorth();
			
			//更新
			for(Entity e : list){
				QcbEmployee qe = (QcbEmployee) e;
				BigDecimal currentAccountAdd = qe.getAccountBalance().divide(netValue, 8, BigDecimal.ROUND_HALF_UP);
				
				QcbEmployeeHistory qeh = new QcbEmployeeHistory();
				qeh.setUuid(UUID.randomUUID().toString());
				qeh.setQcbEmployee(qe.getUuid());
				qeh.setOrderId(qeh.getUuid());
				qeh.setOrderNum(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_OHER,Utlity.BILL_PAYTYPE_BALANCE,Utlity.BILL_PURPOSE_BUY));
				qeh.setPay(qe.getAccountBalance());
				qeh.setIncome(BigDecimal.ZERO);
				qeh.setPoundage(BigDecimal.ZERO);
				qeh.setStatus(QcbEmployeeHistoryStatus.SUCCESS);
				qeh.setType(QcbEmployeeHistoryType.CURRENT_BUY);
				qeh.setCreatetime(new Timestamp(System.currentTimeMillis()));
				qeh.setAccountBalance(BigDecimal.ZERO);
				this.qcbEmployeeHistoryDAO.insert(qeh);
				
				qe.setCurrentAccount(qe.getCurrentAccount().add(currentAccountAdd));
				qe.setAccountBalance(BigDecimal.ZERO);
				this.qcbEmployeeDAO.update(qe);
			}
		}
	}
	
	/**
	 * 更新活期理财
	 */
	@Override
	public void updateCurrentPay(QcbEmployee qe, QcbEmployeeHistory qeh) {
		this.qcbEmployeeHistoryDAO.insert(qeh);
		this.qcbEmployeeDAO.update(qe);
	}
	
	/**
	 * 更新昨日余额
	 */
	public void updateYesterdayAccount(){
		this.qcbEmployeeDAO.updateYesterdayAccount();
	}

	@Override
	public BigDecimal getTotalBalacne() {
		// TODO Auto-generated method stub
		return this.qcbEmployeeDAO.getTotalBalacne();
	}
}
