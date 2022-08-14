package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.SqstopContractVO;

public interface SqstopContractLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 发行期退款申请的查询
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return pageList
	 */
	IPageList listAll(SqstopContractVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 发行期退款申请的查询
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return pageList
	 */
	IPageList listAll(SqstopContractVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 发行期退款申请的查询——单一退款信息 
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List listBySql(SqstopContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 添加发行期退款申请
	 * @param vo
	 * @throws BusiException
	 */
	void append(SqstopContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改发行期退款申请
	 * @param vo
	 * @throws BusiException
	 */
	void modi(SqstopContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除发行期退款申请
	 * @param vo
	 * @throws BusiException
	 */
	void delete(SqstopContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 发行期退款申请____审核 2008-08-04
	 * 
	 * SP_CHECK_TSQSTOPCONTRACT
	 * @IN_SERIAL_NO INT
	 * @IN_CHECK_FLAG INT 2普通审核 3财务审核通过 4财务审核不通过
	 * @IN_TRANS_DATE INT, --记账日期
	 * @IN_INPUT_MAN INT
	 * @throws BusiException
	 */
	void check(SqstopContractVO vo) throws BusiException;

}