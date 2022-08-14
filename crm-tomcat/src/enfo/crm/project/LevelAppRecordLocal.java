package enfo.crm.project;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;

public interface LevelAppRecordLocal extends IBusiExLocal {

	/**
	 * ��ʼ��������Ϣ
	 * @param sql
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void initStartFlow(String[] sql) throws BusiException;

	/**
	 * ��ѯ�ͻ���Ϣ
	 * @param sql
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List listCustomerInfo(String sql) throws BusiException;

	/**
	 * ��ѯ����ժҪ��Ϣ
	 * @param interfaceCode
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	List getFieldShowList(String interfaceCode) throws BusiException;

	//@Override
	void remove();

}