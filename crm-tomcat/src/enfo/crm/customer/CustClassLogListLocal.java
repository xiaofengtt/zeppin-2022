package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CustClassLogListVO;

public interface CustClassLogListLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local" 查询客户资料的基本信息——分页显示
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @param sQuery
	 * @throws BusiException
	 * @return rsList
	 */
	IPageList list_fenye(CustClassLogListVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 查询客户资料的基本信息——列表显示
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List list_leibiao(CustClassLogListVO vo) throws BusiException;

}