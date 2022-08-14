/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductInvestRecordsDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductInvestRecordsService;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestRecords;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 *
 */
@Service
public class BankFinancialProductInvestRecordsService extends BaseService implements IBankFinancialProductInvestRecordsService {

	@Autowired
	private IBankFinancialProductInvestRecordsDAO bankFinancialProductInvestRecordsDAO;
	
	@Override
	public BankFinancialProductInvestRecords insert(BankFinancialProductInvestRecords bankFinancialProductInvestRecords) {
		return bankFinancialProductInvestRecordsDAO.insert(bankFinancialProductInvestRecords);
	}

	@Override
	public BankFinancialProductInvestRecords delete(BankFinancialProductInvestRecords bankFinancialProductInvestRecords) {
		return bankFinancialProductInvestRecordsDAO.delete(bankFinancialProductInvestRecords);
	}

	@Override
	public BankFinancialProductInvestRecords update(BankFinancialProductInvestRecords bankFinancialProductInvestRecords) {
		return bankFinancialProductInvestRecordsDAO.update(bankFinancialProductInvestRecords);
	}

	@Override
	public BankFinancialProductInvestRecords get(String uuid) {
		return bankFinancialProductInvestRecordsDAO.get(uuid);
	}
}
