package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoContractVO;

public interface TcoContractLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ͬ����-���Ӻ�ͬ��Ϣ
	 * @param vo
	 * @throws BusiException
	 * 
	 */
	Integer append(TcoContractVO vo) throws BusiException;

	/**
	 * ��ѯ--��ҳ����ͬ�Ǽ��б�
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryByPageList(TcoContractVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * ��ѯ---�޷�ҳ����ͬ�Ǽ��б�
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoContractVO vo) throws BusiException;

	/**
	 * �޸�--��ͬ�Ǽ�
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void modi(TcoContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * ɾ��--��ͬ�Ǽ�
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoContractVO vo) throws BusiException;

	/**
	 * ���--��ͬ�Ǽ�
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void check(TcoContractVO vo) throws BusiException;

}