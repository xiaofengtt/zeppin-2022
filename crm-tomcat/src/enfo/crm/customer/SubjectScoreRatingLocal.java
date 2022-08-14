package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.RatingVO;

public interface SubjectScoreRatingLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ��Ŀ��ֵ������Ϣ
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
	 * ��ѯ��Ŀ��ֵ������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_subjectScoreRating(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���ӿ�Ŀ��ֵ������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void append_subjectScoreRating(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ŀ�Ŀ��ֵ������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modi_subjectScoreRating(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ����Ŀ��ֵ������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void delete_subjectScoreRating(RatingVO vo) throws BusiException;

}