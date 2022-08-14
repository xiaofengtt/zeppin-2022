/*
 * �������� 2016-2-16
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.score;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;

@Component(value="scoreRule")
public class ScoreRuleBean extends CrmBusiExBean implements ScoreRuleLocal {
	
	/* (non-Javadoc)
	 * @see enfo.crm.score.ScoreRuleLocal#queryScoreRule(enfo.crm.score.ScoreRuleVo, int, int)
	 */
	@Override
	public IPageList queryScoreRule(ScoreRuleVo vo,int pageIndex,int pageSize) throws BusiException {

		String sql = "{call SP_QUERY_TSCORECARD_RULE }";

		Object[] params = new Object[0];
		
		try {
			return  super.listProcPage(sql, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("��ѯ���ֹ���ʧ��: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.score.ScoreRuleLocal#addScoreRule(enfo.crm.score.ScoreRuleVo)
	 */
	@Override
	public void addScoreRule(ScoreRuleVo vo)throws Exception{
		String sql = "{? = call SP_ADD_TSCORECARD_RULE (?,?,?,?)}";

		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getMore_amount(),new Integer(0));
		params[1] = Utility.parseDecimal(Utility.trimNull(vo.getUnitscore()),new BigDecimal(0), 2,"1");
		params[2] = Utility.parseDecimal(Utility.trimNull(vo.getDayscore()),new BigDecimal(0), 2,"1");
		params[3] = Utility.trimNull(vo.getSummary());
		
		super.cudProc(sql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.score.ScoreRuleLocal#modiScoreRule(enfo.crm.score.ScoreRuleVo)
	 */
	@Override
	public void modiScoreRule(ScoreRuleVo vo) throws Exception {
		String sql = "{? = call SP_MODI_TSCORECARD_RULE (?,?,?,?,?)}";

		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getRule_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getMore_amount(),new Integer(0));
		params[2] = Utility.parseDecimal(Utility.trimNull(vo.getUnitscore()),new BigDecimal(0), 2,"1");
		params[3] = Utility.parseDecimal(Utility.trimNull(vo.getDayscore()),new BigDecimal(0), 2,"1");
		params[4] = Utility.trimNull(vo.getSummary());
		
		super.cudProc(sql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.score.ScoreRuleLocal#delScoreRule(enfo.crm.score.ScoreRuleVo)
	 */
	@Override
	public void delScoreRule(ScoreRuleVo vo) throws Exception {
		String sql = "{? = call SP_DEL_TSCORECARD_RULE (?)}";

		Object[] params = new Object[1];
		params[0] = Utility.parseInt(vo.getRule_id(),new Integer(0));
		
		super.cudProc(sql,params);
	}
}
