package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.PartnerVO;

public interface PartnerLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增合作伙伴信息 返回partn_id
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer append(PartnerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws BusiException
	 */
	void modi(PartnerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除合作伙伴信息
	 * @param vo
	 * @throws BusiException
	 */
	void delete(PartnerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 合作伙伴查询
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query(PartnerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 合作伙伴查询 分页
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query(PartnerVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

}