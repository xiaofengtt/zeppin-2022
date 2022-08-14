package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.AmlVO;

public interface AmShareHolderLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ҳ��ѯ�ɶ���Ϣ
	 * 
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * 
	 * IN(�������) IN_SERIAL_NO INT IN_CUST_ID INT IN_INPUT_MAN INT ����Ա
	 *  
	 */
	IPageList listAll(AmlVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ȡ�ɶ���Ϣ
	 * 
	 * @param vo
	 * @return
	 * @throws BusiException
	 * 
	 * IN(�������) IN_SERIAL_NO INT IN_CUST_ID INT IN_INPUT_MAN INT ����Ա
	 *  
	 */
	List list(AmlVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ����
	 * 
	 * @param vo
	 * @throws BusiException
	 */
	void append(AmlVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * �޸�
	 * 
	 * @param vo
	 * @throws BusiException
	 */
	void modi(AmlVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ɾ��
	 * 
	 * @param vo
	 * @throws BusiException
	 */
	void delete(AmlVO vo) throws BusiException;

}