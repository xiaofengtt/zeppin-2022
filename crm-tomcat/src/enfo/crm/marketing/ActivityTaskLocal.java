package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ActivityTaskVO;

public interface ActivityTaskLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local" ���ӻ������ϸ
	 * @param vo
	 * @throws BusiException
	 */
	void append(ActivityTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �޸Ŀͻ�������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modi(ActivityTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ɾ���ͻ�������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void delete(ActivityTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ��ҳ��ѯ�������Ϣ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pageList_query(ActivityTaskVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ��ѯ�������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query(ActivityTaskVO vo) throws BusiException;

}