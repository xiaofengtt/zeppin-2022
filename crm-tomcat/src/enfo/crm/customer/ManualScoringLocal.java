package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.RatingVO;

public interface ManualScoringLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ�˹������Ϣ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pageList_tmanualscoring(RatingVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�˹������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_tmanualscoring(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����˹������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void append_tmanualscoring(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸��˹������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modi_tmanualscoring(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ���˹������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void delete_tmanualscoring(RatingVO vo) throws BusiException;

}