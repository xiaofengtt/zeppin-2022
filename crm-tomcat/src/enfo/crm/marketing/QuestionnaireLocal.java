package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.QuestionnaireVO;

public interface QuestionnaireLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local" �����ʾ���Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	Integer appendQuestInfo(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �޸��ʾ���Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modiQuestInfo(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ɾ���ʾ���Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void deleteQuestInfo(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ��ѯ�ʾ���Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryQuestInfo(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ��ҳ ��ѯ�ʾ���Ϣ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_queryQuestInfo(QuestionnaireVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �����ʾ���Ŀ��Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	Integer appendQuesTopic(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �޸��ʾ���Ŀ��Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modiQuesTopic(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ɾ���ʾ���Ŀ��Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void deleteQuesTopic(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ��ѯ�ʾ���Ŀ��Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryQuesTopic(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ��ѯ�ʾ���Ŀ��Ϣ ��ҳ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_queryQuesTopic(QuestionnaireVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �����ʾ��ֵ����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void appendTTopicScore(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws BusiException
	 */
	void modiTTopicScore(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ɾ���ʾ��ֵ����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void deleteTTopicScore(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryTTopicScroe(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_queryTTopicScroe(QuestionnaireVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �����ʾ������¼ ����
	 * @param vo
	 * @throws BusiException
	 */
	void appendQuestRecord(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �����ʾ������¼ �޸�
	 * @param vo
	 * @throws BusiException
	 */
	void modiQuestRecord(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �����ʾ������¼ ɾ��
	 * @param vo
	 * @throws BusiException
	 */
	void delQuestRecord(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryQuestRecord(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �����ʾ���Ŀ���
	 * @param vo
	 * @throws BusiException
	 */
	void locationTQuesTopic(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * ����ѡ����ѡ�����
	 * @param vo
	 * @throws BusiException
	 */
	void locationTTopicScore(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * �ʾ������¼��Ϣͳ��
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List statQuestRecord(QuestionnaireVO vo) throws BusiException;

}