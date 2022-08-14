package enfo.crm.project;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;

public interface LevelAppRecordLocal extends IBusiExLocal {

	/**
	 * 初始化流程信息
	 * @param sql
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void initStartFlow(String[] sql) throws BusiException;

	/**
	 * 查询客户信息
	 * @param sql
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listCustomerInfo(String sql) throws BusiException;

	/**
	 * 查询流程摘要信息
	 * @param interfaceCode
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List getFieldShowList(String interfaceCode) throws BusiException;

	//@Override
	void remove();

}