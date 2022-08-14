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
	 * 分页查询股东信息
	 * 
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * 
	 * IN(输入参数) IN_SERIAL_NO INT IN_CUST_ID INT IN_INPUT_MAN INT 操作员
	 *  
	 */
	IPageList listAll(AmlVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 读取股东信息
	 * 
	 * @param vo
	 * @return
	 * @throws BusiException
	 * 
	 * IN(输入参数) IN_SERIAL_NO INT IN_CUST_ID INT IN_INPUT_MAN INT 操作员
	 *  
	 */
	List list(AmlVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 新增
	 * 
	 * @param vo
	 * @throws BusiException
	 */
	void append(AmlVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 修改
	 * 
	 * @param vo
	 * @throws BusiException
	 */
	void modi(AmlVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 删除
	 * 
	 * @param vo
	 * @throws BusiException
	 */
	void delete(AmlVO vo) throws BusiException;

}