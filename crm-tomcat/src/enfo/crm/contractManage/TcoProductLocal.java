package enfo.crm.contractManage;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoProductVO;

public interface TcoProductLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��Ʒ�б�
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List listProcAllExt(TcoProductVO vo) throws BusiException;

	/**
		 * @ejb.interface-method view-type = "local"
		 * ��Ӳ�Ʒ��Ϣ
		 * @param vo
		 * @throws BusiException
		 * 
	 */
	void append(TcoProductVO vo) throws BusiException;

	/**
	 * ��ѯ--��ҳ����Ʒ�б�
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	IPageList queryByPageList(TcoProductVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * ��ѯ---�޷�ҳ����Ʒ�б�
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	List queryByList(TcoProductVO vo) throws BusiException;

	/**
	 * �޸�--��Ʒ
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void modi(TcoProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * ɾ��--��Ʒ
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TcoProductVO vo) throws BusiException;

	/**
	 * ���--��Ʒ
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */
	void check(TcoProductVO vo) throws BusiException;

}