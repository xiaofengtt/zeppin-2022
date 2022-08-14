package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.BenChangeVO;

public interface BenChangeLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ����Ȩת�á�����ҳ��ʾ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listAll(BenChangeVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ����Ȩת�á�����ҳ��ʾ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listAll(BenChangeVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ����Ȩת�á�����ҳ��ʾ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List list(BenChangeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ����Ȩת�á���������¼
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List listByChange(BenChangeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �������Ȩת��
	 * @param vo
	 * @throws BusiException
	 */
	Integer append(BenChangeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸�����Ȩת��
	 * @param vo
	 * @throws BusiException
	 */
	void modi(BenChangeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸�����Ȩת�ñ�ע
	 * @param vo
	 * @throws BusiException
	 */
	void modiSummary(BenChangeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ������Ȩת��
	 * @param vo
	 * @throws BusiException
	 */
	void delete(BenChangeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_CHECK_TBENCHANGES_PZ @IN_SERIAL_NO INT,
	                           @IN_REG_DATE  INT,
	                           @IN_INPUT_MAN INT
	 */
	void checkPZ(BenChangeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_CHECK_TBENCHANGES @IN_SERIAL_NO INT,
	                                  @IN_INPUT_MAN INT
	 * @param vo
	 * @throws BusiException
	 */
	void check(BenChangeVO vo) throws BusiException;

}