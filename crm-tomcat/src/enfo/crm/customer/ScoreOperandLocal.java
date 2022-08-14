package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.RatingVO;

public interface ScoreOperandLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ�Ʒֲ�������Ϣ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pageList_tscoreoperand(RatingVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ�Ʒֲ�������Ϣ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List listBySqltscoreoperand(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�Ʒֲ�������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_tscoreoperand(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���ӼƷֲ�������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void append_tscoreoperand(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸ļƷֲ�������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modi_tscoreoperand(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ���Ʒֲ�������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void delete_tscoreoperand(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �������ֿ�Ŀ��ѯ�Ʒֲ�������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_subjectoperand(RatingVO vo) throws BusiException;

}