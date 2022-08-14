package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.BenChangeVO;

public interface BenChangeLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询受益权转让——分页显示
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listAll(BenChangeVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询受益权转让——分页显示
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listAll(BenChangeVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询受益权转让——分页显示
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List list(BenChangeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询受益权转让——单条记录
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List listByChange(BenChangeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 添加受益权转让
	 * @param vo
	 * @throws BusiException
	 */
	Integer append(BenChangeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改受益权转让
	 * @param vo
	 * @throws BusiException
	 */
	void modi(BenChangeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改受益权转让备注
	 * @param vo
	 * @throws BusiException
	 */
	void modiSummary(BenChangeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除受益权转让
	 * @param vo
	 * @throws BusiException
	 */
	void delete(BenChangeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_CHECK_TBENCHANGES_PZ @IN_SERIAL_NO INT,
	                           @IN_REG_DATE  INT,
	                           @IN_INPUT_MAN INT
	 */
	void checkPZ(BenChangeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_CHECK_TBENCHANGES @IN_SERIAL_NO INT,
	                                  @IN_INPUT_MAN INT
	 * @param vo
	 * @throws BusiException
	 */
	void check(BenChangeVO vo) throws BusiException;

}