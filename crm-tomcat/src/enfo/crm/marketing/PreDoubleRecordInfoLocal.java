package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.PreDoubleRecordInfoVO;

public interface PreDoubleRecordInfoLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ����˫¼¼����Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer addPreDoubleRecordInfo(PreDoubleRecordInfoVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * �޸�˫¼¼����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modiPreDoubleRecordInfo(PreDoubleRecordInfoVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ���˫¼¼����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void checkPreDoubleRecordInfo(PreDoubleRecordInfoVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * �������˫¼¼����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void uncheckPreDoubleRecordInfo(PreDoubleRecordInfoVO vo) throws BusiException;


	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ˫¼¼����Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	@SuppressWarnings("rawtypes")
	List queryPreDoubleRecordInfo(PreDoubleRecordInfoVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ˫¼¼����Ϣ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryPreDoubleRecordInfo(PreDoubleRecordInfoVO vo, int pageIndex, int pageSize) throws BusiException;

}