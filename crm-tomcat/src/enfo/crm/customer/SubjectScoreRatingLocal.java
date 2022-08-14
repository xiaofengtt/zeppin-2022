package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.RatingVO;

public interface SubjectScoreRatingLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分页查询科目分值评级信息
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pageList_subjectScoreRating(RatingVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询科目分值评级信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_subjectScoreRating(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 增加科目分值评级信息
	 * @param vo
	 * @throws BusiException
	 */
	void append_subjectScoreRating(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改科目分值评级信息
	 * @param vo
	 * @throws BusiException
	 */
	void modi_subjectScoreRating(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除科目分值评级信息
	 * @param vo
	 * @throws BusiException
	 */
	void delete_subjectScoreRating(RatingVO vo) throws BusiException;

}