package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.RatingVO;

public interface ScoreSubjectLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询评分科目
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pageList_query(RatingVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询评分科目
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 增加评分科目
	 * @param vo
	 * @throws BusiException
	 */
	void append(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改评分科目
	 * @param vo
	 * @throws BusiException
	 */
	void modi(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除评分科目
	 * @param vo
	 * @throws BusiException
	 */
	void delete(RatingVO vo) throws BusiException;

}