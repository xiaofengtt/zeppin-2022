 
package enfo.crm.marketing;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.QuestionnaireVO;

@Component(value="questionnaire")
public class QuestionnaireBean extends enfo.crm.dao.CrmBusiExBean implements QuestionnaireLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#appendQuestInfo(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public Integer appendQuestInfo(QuestionnaireVO vo) throws BusiException {
		String sqlStr = "{?=call SP_ADD_TQuestInfo(?,?,?,?,?,?)}";
		Integer ret = new Integer(0);
		Object[] params = new Object[5];
		int outParamPos = 7;
		int outParamType = 4;

		params[0] = vo.getQues_no();
		params[1] = vo.getQues_title();
		params[2] = vo.getQues_type();
		params[3] = vo.getRemark();
		params[4] = vo.getInputMan();

		try {
			ret = (Integer) super.cudProc(sqlStr, params, outParamPos,
					outParamType);
		} catch (BusiException e) {
			throw new BusiException("新建问卷信息失败:" + e.getMessage());
		}

		return ret;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#modiQuestInfo(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public void modiQuestInfo(QuestionnaireVO vo) throws BusiException {
		String sqlStr = "{?=call SP_MODI_TQuestInfo(?,?,?,?,?,?)}";
		Object[] params = new Object[6];

		params[0] = vo.getQues_id();
		params[1] = vo.getQues_no();
		params[2] = vo.getQues_title();
		params[3] = vo.getQues_type();
		params[4] = vo.getRemark();
		params[5] = vo.getInputMan();

		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("修改问卷信息失败:" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#deleteQuestInfo(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public void deleteQuestInfo(QuestionnaireVO vo) throws BusiException {
		String sqlStr = "{?=call SP_DEL_TQuestInfo(?,?)}";
		Object[] params = new Object[2];

		params[0] = vo.getQues_id();
		params[1] = vo.getInputMan();

		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("删除问卷信息失败:" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#queryQuestInfo(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public List queryQuestInfo(QuestionnaireVO vo) throws BusiException {
		String strSql = "{ call SP_QUERY_TQuestInfo(?,?,?,?,?,?,?)}";
		List rsList = null;
		Object[] params = new Object[7];

		params[0] = vo.getQues_id();
		params[1] = vo.getQues_no();
		params[2] = vo.getQues_title();
		params[3] = vo.getQues_type();
		params[4] = vo.getCreator();
		params[5] = vo.getStatus();
		params[6] = vo.getInputMan();

		try {
			rsList = super.listProcAll(strSql, params);
		} catch (BusiException e) {
			throw new BusiException("问卷信息查询失败:" + e.getMessage());
		}

		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#pagelist_queryQuestInfo(enfo.crm.vo.QuestionnaireVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pagelist_queryQuestInfo(QuestionnaireVO vo,
			String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException {
		String strSql = "{ call SP_QUERY_TQuestInfo(?,?,?,?,?,?,?)}";
		IPageList rsList = null;
		Object[] params = new Object[7];

		params[0] = vo.getQues_id();
		params[1] = vo.getQues_no();
		params[2] = vo.getQues_title();
		params[3] = vo.getQues_type();
		params[4] = vo.getCreator();
		params[5] = vo.getStatus();
		params[6] = vo.getInputMan();

		try {
			rsList = super.listProcPage(strSql, params, totalColumn, pageIndex,
					pageSize);
		} catch (BusiException e) {
			throw new BusiException("问卷信息查询失败:" + e.getMessage());
		}
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#appendQuesTopic(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public Integer appendQuesTopic(QuestionnaireVO vo) throws BusiException {
		String sqlStr = "{?=call SP_ADD_TQuesTopic(?,?,?,?,?,?)}";
		Object[] params = new Object[5];
		Integer ret = new Integer(0);
		int outParamPos = 7;
		int outParamType = 4;

		params[0] = vo.getQues_id();
		params[1] = vo.getTopic_content();
		params[2] = vo.getTopic_type();
		params[3] = vo.getRemark();
		params[4] = vo.getInputMan();

		try {
			ret = (Integer) super.cudProc(sqlStr, params, outParamPos,
					outParamType);
		} catch (BusiException e) {
			throw new BusiException("新建问卷题目信息失败:" + e.getMessage());
		}

		return ret;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#modiQuesTopic(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public void modiQuesTopic(QuestionnaireVO vo) throws BusiException {
		String sqlStr = "{?=call SP_MODI_TQuesTopic(?,?,?,?,?,?)}";
		Object[] params = new Object[6];

		params[0] = vo.getTopic_id();
		params[1] = vo.getQues_id();
		params[2] = vo.getTopic_content();
		params[3] = vo.getTopic_type();
		params[4] = vo.getRemark();
		params[5] = vo.getInputMan();

		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("修改问卷题目信息失败:" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#deleteQuesTopic(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public void deleteQuesTopic(QuestionnaireVO vo) throws BusiException {
		String sqlStr = "{?=call SP_DEL_TQuesTopic(?,?)}";
		Object[] params = new Object[2];

		params[0] = vo.getTopic_id();
		params[1] = vo.getInputMan();

		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("删除问卷题目信息失败:" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#queryQuesTopic(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public List queryQuesTopic(QuestionnaireVO vo) throws BusiException {
		String sqlStr = "{ call SP_QUERY_TQuesTopic(?,?,?,?,?,?,?,?,?,?)}";
		List rsList = null;
		Object[] params = new Object[10];

		params[0] = vo.getTopic_id();
		params[1] = vo.getQues_id();
		params[2] = vo.getQues_no();
		params[3] = vo.getQues_title();
		params[4] = vo.getTopic_serial_no();
		params[5] = vo.getTopic_content();
		params[6] = vo.getTopic_type();
		params[7] = vo.getCreator();
		params[8] = vo.getRemark();
		params[9] = vo.getInputMan();

		try {
			rsList = super.listProcAll(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("问卷题目信息查询失败:" + e.getMessage());
		}

		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#pagelist_queryQuesTopic(enfo.crm.vo.QuestionnaireVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pagelist_queryQuesTopic(QuestionnaireVO vo,
			String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException {
		String sqlStr = "{ call SP_QUERY_TQuesTopic(?,?,?,?,?,?,?,?,?)}";
		IPageList rsList = null;
		Object[] params = new Object[9];

		params[0] = vo.getTopic_id();
		params[1] = vo.getQues_id();
		params[2] = vo.getQues_no();
		params[3] = vo.getQues_title();
		params[4] = vo.getTopic_serial_no();
		params[5] = vo.getTopic_content();
		params[6] = vo.getCreator();
		params[7] = vo.getRemark();
		params[8] = vo.getInputMan();

		try {
			rsList = super.listProcPage(sqlStr, params, totalColumn, pageIndex,
					pageSize);
		} catch (BusiException e) {
			throw new BusiException("问卷题目信息查询失败:" + e.getMessage());
		}
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#appendTTopicScore(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public void appendTTopicScore(QuestionnaireVO vo) throws BusiException {
		String sqlStr = "{?=call SP_ADD_TTopicScore(?,?,?,?,?,?,?)}";
		Object[] params = new Object[7];

		params[0] = vo.getQues_id();
		params[1] = vo.getTopic_id();
		params[2] = vo.getQues_title();
		params[3] = vo.getTopic_value();
		params[4] = vo.getScore();
		params[5] = vo.getRemark();
		params[6] = vo.getInputMan();

		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("新增问卷分值表信息失败:" + e.getMessage());
		}

	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#modiTTopicScore(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public void modiTTopicScore(QuestionnaireVO vo) throws BusiException {
		String sqlStr = "{?=call  SP_MODI_TTopicScore(?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[8];

		params[0] = vo.getSerial_no();
		params[1] = vo.getQues_id();
		params[2] = vo.getTopic_id();
		params[3] = vo.getQues_title();
		params[4] = vo.getTopic_value();
		params[5] = vo.getScore();
		params[6] = vo.getRemark();
		params[7] = vo.getInputMan();

		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("修改问卷分值表信息失败:" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#deleteTTopicScore(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public void deleteTTopicScore(QuestionnaireVO vo) throws BusiException {
		String sqlStr = "{?=call  SP_DEL_TTopicScore(?,?)}";
		Object[] params = new Object[2];

		params[0] = vo.getSerial_no();
		params[1] = vo.getInputMan();

		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("删除问卷分值表信息失败:" + e.getMessage());
		}

	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#queryTTopicScroe(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public List queryTTopicScroe(QuestionnaireVO vo) throws BusiException {
		String sqlStr = "{call  SP_QUERY_TTopicScore(?,?,?,?,?,?,?,?,?)}";
		List rsList = null;
		Object[] params = new Object[9];

		params[0] = vo.getSerial_no();
		params[1] = vo.getQues_id();
		params[2] = vo.getTopic_id();
		params[3] = vo.getQues_title();
		params[4] = vo.getTopic_value_no();
		params[5] = vo.getTopic_value();
		params[6] = vo.getScore();
		params[7] = vo.getRemark();
		params[8] = vo.getInputMan();

		try {
			rsList = super.listProcAll(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("问卷分值表信息查询失败:" + e.getMessage());
		}

		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#pagelist_queryTTopicScroe(enfo.crm.vo.QuestionnaireVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pagelist_queryTTopicScroe(QuestionnaireVO vo,
			String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException {
		String sqlStr = "{call  SP_QUERY_TTopicScore(?,?,?,?,?,?,?,?,?)}";
		IPageList rsList = null;
		Object[] params = new Object[9];

		params[0] = vo.getSerial_no();
		params[1] = vo.getQues_id();
		params[2] = vo.getTopic_id();
		params[3] = vo.getQues_title();
		params[4] = vo.getTopic_value_no();
		params[5] = vo.getTopic_value();
		params[6] = vo.getScore();
		params[7] = vo.getRemark();
		params[8] = vo.getInputMan();

		try {
			rsList = super.listProcPage(sqlStr, params, totalColumn, pageIndex,
					pageSize);
		} catch (BusiException e) {
			throw new BusiException("问卷分值表信息查询失败:" + e.getMessage());
		}

		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#appendQuestRecord(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public void appendQuestRecord(QuestionnaireVO vo) throws BusiException {
		String sqlStr = "{?=call SP_ADD_TQuestRecord(?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[8];
		
		params[0] = vo.getQues_taskId();
		params[1] = vo.getQues_taskDetailId();
		params[2] = vo.getTarget_custId();
		params[3] = vo.getQues_id();
		params[4] = vo.getTopic_id();
		params[5] = vo.getTopic_value();
		params[6] = vo.getScore();
		params[7] = vo.getInputMan();
		
		try {
			super.cudProc(sqlStr,params);
		} catch (BusiException e) {
			throw new BusiException("调查问卷任务记录信息插入失败:" + e.getMessage());
		}	
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#modiQuestRecord(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public void modiQuestRecord(QuestionnaireVO vo) throws BusiException {
		String sqlStr = "{?=call SP_MODI_TQuestRecord(?,?,?,?)}";
		Object[] params = new Object[4];
		
		params[0] = vo.getSerial_no();
		params[1] = vo.getTopic_value();
		params[2] = vo.getScore();
		params[3] = vo.getInputMan();
		
		try {
			super.cudProc(sqlStr,params);
		} catch (BusiException e) {
			throw new BusiException("调查问卷任务记录信息修改失败:" + e.getMessage());
		}	
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#delQuestRecord(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public void delQuestRecord(QuestionnaireVO vo) throws BusiException {
		String sqlStr = "{?=call  SP_DEL_TQuestRecord(?,?)}";
		Object[] params = new Object[2];

		params[0] = vo.getSerial_no();
		params[1] = vo.getInputMan();

		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("删除问卷任务记录信息失败:" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#queryQuestRecord(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public List queryQuestRecord(QuestionnaireVO vo) throws BusiException {
		String sqlStr = "{call  SP_QUERY_TQuestRecord(?,?,?,?,?,?,?)}";
		List rsList = null;
		Object[] params = new Object[7];
		
		params[0] = vo.getSerial_no();
		params[1] = vo.getQues_taskId();
		params[2] = vo.getQues_taskDetailId();
		params[3] = vo.getTarget_custId();
		params[4] = vo.getQues_id();
		params[5] = vo.getTopic_id();
		params[6] = vo.getTopic_value();
		
		try {
			rsList = super.listProcAll(sqlStr,params);
		} catch (BusiException e) {
			throw new BusiException("问卷任务记录信息查询失败:" + e.getMessage());
		}
	
		return rsList;			
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#locationTQuesTopic(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public void locationTQuesTopic(QuestionnaireVO vo) throws BusiException {
		String sqlStr = "{?=call SP_LOCATION_QuesTopic(?,?,?)}";
		Object[] params = new Object[3];

		params[0] = vo.getTopic_id();
		params[1] = vo.getFlag();
		params[2] = vo.getInputMan();

		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("调整问卷题目序号失败:" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#locationTTopicScore(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public void locationTTopicScore(QuestionnaireVO vo) throws BusiException {
		String sqlStr = "{?=call SP_LOCATION_TTopicScore(?,?,?)}";
		Object[] params = new Object[3];

		params[0] = vo.getSerial_no();
		params[1] = vo.getFlag();
		params[2] = vo.getInputMan();

		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("调整选择题选项序号失败:" + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuestionnaireLocal#statQuestRecord(enfo.crm.vo.QuestionnaireVO)
	 */
	@Override
	public List statQuestRecord(QuestionnaireVO vo) throws BusiException{
		String sqlStr = "{call  SP_STAT_TQuestRecord(?,?)}";
		List rsList = null;
		Object[] params = new Object[2];
		
		params[0] = vo.getQues_taskId();
		params[1] = vo.getTopic_id();
		
		try {
					rsList = super.listProcAll(sqlStr,params);
		} catch (BusiException e) {
			throw new BusiException("问卷任务记录信息统计失败:" + e.getMessage());
		}
		
		return rsList;		
	}
}