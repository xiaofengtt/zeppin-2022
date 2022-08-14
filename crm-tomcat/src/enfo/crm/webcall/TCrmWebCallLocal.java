package enfo.crm.webcall;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.TCrmWebCallVO;

public interface TCrmWebCallLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 添加操作员与UUID对应
	 * @param vo
	 * @throws BusiException
	 */
	void appendOperatorUUID(TCrmWebCallVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查找操作员与UUID对应
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listOperatorUUID(TCrmWebCallVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 添加客户ID和webcall对应
	 * @param vo
	 * @throws BusiException
	 */
	void appendCrmWebcall(TCrmWebCallVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查找操作员与UUID对应
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listCrmWebcall(TCrmWebCallVO vo) throws BusiException;

}