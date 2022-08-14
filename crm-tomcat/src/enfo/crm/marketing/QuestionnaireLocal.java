package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.QuestionnaireVO;

public interface QuestionnaireLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local" 新增问卷信息
	 * @param vo
	 * @throws BusiException
	 */
	Integer appendQuestInfo(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 修改问卷信息
	 * @param vo
	 * @throws BusiException
	 */
	void modiQuestInfo(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 删除问卷信息
	 * @param vo
	 * @throws BusiException
	 */
	void deleteQuestInfo(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 查询问卷信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryQuestInfo(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 分页 查询问卷信息
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
	 * @ejb.interface-method view-type = "local" 新增问卷题目信息
	 * @param vo
	 * @throws BusiException
	 */
	Integer appendQuesTopic(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 修改问卷题目信息
	 * @param vo
	 * @throws BusiException
	 */
	void modiQuesTopic(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 删除问卷题目信息
	 * @param vo
	 * @throws BusiException
	 */
	void deleteQuesTopic(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 查询问卷题目信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryQuesTopic(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 查询问卷题目信息 分页
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
	 * @ejb.interface-method view-type = "local" 新增问卷分值表信息
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
	 * @ejb.interface-method view-type = "local" 删除问卷分值表信息
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
	 * @ejb.interface-method view-type = "local" 调查问卷任务记录 新增
	 * @param vo
	 * @throws BusiException
	 */
	void appendQuestRecord(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 调查问卷任务记录 修改
	 * @param vo
	 * @throws BusiException
	 */
	void modiQuestRecord(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 调查问卷任务记录 删除
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
	 * @ejb.interface-method view-type = "local" 调整问卷题目序号
	 * @param vo
	 * @throws BusiException
	 */
	void locationTQuesTopic(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 调整选择题选项序号
	 * @param vo
	 * @throws BusiException
	 */
	void locationTTopicScore(QuestionnaireVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 问卷任务记录信息统计
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List statQuestRecord(QuestionnaireVO vo) throws BusiException;

}