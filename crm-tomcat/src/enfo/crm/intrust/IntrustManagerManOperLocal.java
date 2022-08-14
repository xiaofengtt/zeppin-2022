package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.IntrustManagerManOpVO;

public interface IntrustManagerManOperLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 新增责任人成员信息
	 * @param vo
	 * @throws BusiException
	 */
	void addManagerOp(IntrustManagerManOpVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 删除责任人成员信息
	 * @param vo
	 * @throws BusiException
	 */
	void delManagerManOp(IntrustManagerManOpVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 修改责任人成员信息
	 * @param vo
	 * @throws BusiException
	 */
	void modiManagerOp(IntrustManagerManOpVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询责任人成员信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList queryManagerOp(IntrustManagerManOpVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询责任人成员信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryManagerOp(IntrustManagerManOpVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 获得该员工所属的责任人MANAGER_ID
	 * @param op_code
	 * @return
	 * @throws Exception
	 */
	List queryManagerOp(Integer op_code) throws Exception;

}