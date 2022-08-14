package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.SqstopContractVO;

public interface SqstopContractLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * �������˿�����Ĳ�ѯ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return pageList
	 */
	IPageList listAll(SqstopContractVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �������˿�����Ĳ�ѯ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return pageList
	 */
	IPageList listAll(SqstopContractVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �������˿�����Ĳ�ѯ������һ�˿���Ϣ 
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List listBySql(SqstopContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ӷ������˿�����
	 * @param vo
	 * @throws BusiException
	 */
	void append(SqstopContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸ķ������˿�����
	 * @param vo
	 * @throws BusiException
	 */
	void modi(SqstopContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ���������˿�����
	 * @param vo
	 * @throws BusiException
	 */
	void delete(SqstopContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �������˿�����____��� 2008-08-04
	 * 
	 * SP_CHECK_TSQSTOPCONTRACT
	 * @IN_SERIAL_NO INT
	 * @IN_CHECK_FLAG INT 2��ͨ��� 3�������ͨ�� 4������˲�ͨ��
	 * @IN_TRANS_DATE INT, --��������
	 * @IN_INPUT_MAN INT
	 * @throws BusiException
	 */
	void check(SqstopContractVO vo) throws BusiException;

}