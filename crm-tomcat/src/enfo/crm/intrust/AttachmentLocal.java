package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.AttachmentVO;

public interface AttachmentLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 添加附件
	 * @param vo
	 * @throws BusiException
	 */
	void append(AttachmentVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 删除附件
	 * @param vo
	 * @throws BusiException
	 */
	void delete(AttachmentVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 读取附件信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List load(AttachmentVO vo) throws BusiException;

}