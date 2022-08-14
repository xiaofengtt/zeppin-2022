package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.RedeemVO;

public interface RedeemLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local" �����Ϣ----��ѯȫ��
	 * @SP_QUERY_TSQREDEEM
	 * @IN_SERIAL_NO INT
	 * @IN_PRODUCT_ID INT
	 * @IN_CONTRACT_BH VARCHAR(16)
	 * @IN_LIST_ID INT ������ID
	 * @IN_SQ_DATE INT
	 * @IN_CHECK_FLAG INT 1��ѯδ��˼�¼ 2��ѯ����ͨ��˼�¼ 3��ѯ�Ѳ�����˼�¼
	 * @IN_FALG INT, --1��ѯ�Ѿ������ݶ����ļ�¼0��ѯȫ��
	 * @IN_INPUT_MAN INT
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listAll(RedeemVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �����Ϣ----��ѯ��һ��Ϣ
	 * @SP_QUERY_TSQREDEEM
	 * @IN_SERIAL_NO INT
	 * @IN_PRODUCT_ID INT
	 * @IN_CONTRACT_BH VARCHAR(16)
	 * @IN_LIST_ID INT ������ID
	 * @IN_SQ_DATE INT
	 * @IN_CHECK_FLAG INT 1��ѯδ��˼�¼ 2��ѯ����ͨ��˼�¼ 3��ѯ�Ѳ�����˼�¼
	 * @IN_FALG INT, --1��ѯ�Ѿ������ݶ����ļ�¼0��ѯȫ��
	 * @IN_INPUT_MAN INT
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listBySql(RedeemVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �����Ϣ----���� SP_ADD_TSQREDEEM_CRM @ IN_BEN_SERIAL_NO
	 *                       INT
	 * @IN_REDEEM_AMOUNT DECIMAL(16,3)
	 * @IN_SQ_DATE INT
	 * @IN_INPUT_MAN INT
	 * @throws BusiException
	 */
	void append(RedeemVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �����Ϣ----�޸� SP_MODI_TSQREDEEM_CRM @ IN_SERIAL_NO
	 *                       INT
	 * @IN_REDEEM_AMOUNT DECIMAL(16,3)
	 * @IN_SQ_DATE INT
	 * @IN_INPUT_MAN INT
	 * @throws BusiException
	 */
	void modi(RedeemVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �����Ϣ----ɾ�� SP_DEL_TSQREDEEM
	 * @IN_SERIAL_NO INT ���
	 * @IN_INPUT_MAN INT ¼�����Ա
	 * @throws BusiException
	 */
	void delete(RedeemVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �����Ϣ----����/���
	 *                       SP_CHECK_TSQREDEEM_CRM
	 * @IN_SERIAL_NO INT ���
	 * @IN_CHECK_FLAG INT 2���ݸ���ͨ�� 3������ˣ�4�Ѷ���
	 * @IN_INPUT_MAN INT ¼�����Ա
	 * @throws BusiException
	 */
	void check(RedeemVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �����˻ָ� SP_CHECK_TSQREDEEM_BACK
	 * @IN_SERIAL_NO INT, --SERIAL_NO
	 * @IN_INPUT_MAN INT --����Ա
	 * @throws BusiException
	 */
	void recheck(RedeemVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * �޶���ز�ѯ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listSqredeemLarge(RedeemVO vo, int pageIndex, int pageSize) throws BusiException;

}