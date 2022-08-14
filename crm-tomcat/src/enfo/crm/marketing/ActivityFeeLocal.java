package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ActivityFeeVO;

public interface ActivityFeeLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���ӻ������ϸ
	 * @param vo
	 * @throws BusiException
	 */
	void append(ActivityFeeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ļ������ϸ
	 * @param vo
	 * @throws BusiException
	 */

	void modi(ActivityFeeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ���������ϸ
	 * @param vo
	 * @throws BusiException
	 */
	void delete(ActivityFeeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ�������Ϣ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pageList_query(ActivityFeeVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query(ActivityFeeVO vo) throws BusiException;

}