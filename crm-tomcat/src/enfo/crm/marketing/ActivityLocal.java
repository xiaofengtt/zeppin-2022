package enfo.crm.marketing;

import java.sql.SQLException;
import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ActivityVO;

public interface ActivityLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���ӻ��Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	Integer append(ActivityVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ļ��Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modi(ActivityVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ�����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void delete(ActivityVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ���Ϣ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pageList_query(ActivityVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ���Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query(ActivityVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�������Ϣ
	 * @param serial_no
	 * @return
	 * @throws BusiException
	 */
	List load(Integer serial_no) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws BusiException
	 * @throws SQLException 
	 */
	void appendCustomerInfo(ActivityVO vo) throws BusiException, SQLException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param serial_no
	 * @return
	 * @throws BusiException
	 */
	List queryByListCustomerInfo(Integer serial_no) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param serial_no
	 * @throws BusiException
	 * @throws SQLException
	 */
	void deleteCustomerInfo(Integer serial_no) throws BusiException, SQLException;

}