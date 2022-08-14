package enfo.crm.intrust;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ToMoneyAccountVO;

public interface ToMoneyAccountLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ʽ�����Ϣ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList query_page(ToMoneyAccountVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �ʽ�����Ϣ���
	 * @param vo
	 * @throws BusiException
	 */
	void check(ToMoneyAccountVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �ʽ�����˻ָ���Ϣ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listRestoreCheck(ToMoneyAccountVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �ʽ�����Ϣ�ָ�
	 * @param vo
	 * @throws BusiException
	 */
	void restoreCheck(ToMoneyAccountVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ҵ�������
	 */
	void checkByManage(ToMoneyAccountVO vo) throws Exception;

}