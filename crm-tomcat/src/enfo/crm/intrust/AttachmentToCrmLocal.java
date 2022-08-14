package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.AttachmentVO;

public interface AttachmentToCrmLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��Ӹ���
	 * @param vo
	 * @throws BusiException
	 */
	void append(AttachmentVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ɾ������
	 * @param vo
	 * @throws BusiException
	 */
	void delete(AttachmentVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ����IDɾ������
	 * @param vo
	 * @throws BusiException
	 */
	void deleteById(AttachmentVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ɾ���ļ�
	 * @param vo
	 * @throws BusiException
	 */
	void deleteFile(AttachmentVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ȡ������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List load(AttachmentVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ����ID��ȡ������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List loadById(AttachmentVO vo) throws BusiException;

}