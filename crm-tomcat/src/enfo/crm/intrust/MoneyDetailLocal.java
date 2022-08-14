package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.MoneyDetailVO;

public interface MoneyDetailLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ��ɿ�������ϸ��Ϣ
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List listBySql(MoneyDetailVO vo) throws BusiException;

	/**
	 *  @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ��ɿ�������ϸ��Ϣ ��ҳ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList query_page(MoneyDetailVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ɿ��ͬ��Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Object[] queryContractJkBase(MoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �½��ɿ�
	 * @param vo
	 * @throws Exception
	 */
	Integer append(MoneyDetailVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ľɿ�
	 * @param vo
	 * @throws BusiException
	 */
	void modi(MoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ���ɿ�
	 * @param vo
	 * @throws Exception
	 */
	void delete(MoneyDetailVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����ɿ�
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List load(MoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ�������ϸ,��ӡ�ͻ����˵���
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List listTradeDetail(MoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * Ϊ�������˿��ѯ�ɿ��¼
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listForRebate(MoneyDetailVO vo) throws BusiException;

}