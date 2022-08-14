package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.RatingVO;

public interface ScoreOperandLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询计分操作数信息
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pageList_tscoreoperand(RatingVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询计分操作数信息
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List listBySqltscoreoperand(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询计分操作数信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_tscoreoperand(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 增加计分操作数信息
	 * @param vo
	 * @throws BusiException
	 */
	void append_tscoreoperand(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改计分操作数信息
	 * @param vo
	 * @throws BusiException
	 */
	void modi_tscoreoperand(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除计分操作数信息
	 * @param vo
	 * @throws BusiException
	 */
	void delete_tscoreoperand(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 跟据评分科目查询计分操作数信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_subjectoperand(RatingVO vo) throws BusiException;

}