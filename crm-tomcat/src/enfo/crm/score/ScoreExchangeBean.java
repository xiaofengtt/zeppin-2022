/*
 * �������� 2016-2-29
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.score;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;

@Component(value="scoreExchange")
public class ScoreExchangeBean extends CrmBusiExBean implements ScoreExchangeLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.score.ScoreExchangeLocal#addScoreExchange(enfo.crm.score.ScoreExchangeVo)
	 */
	@Override
	public void addScoreExchange(ScoreExchangeVo vo) throws Exception {
		String sql = "{? = call SP_ADD_TSCORECARD_CUST_EXCHANGE (?,?,?,?)}";

		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getScore(),new Integer(0));
		params[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[3] = Utility.trimNull(vo.getSummary());
		
		super.cudProc(sql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.score.ScoreExchangeLocal#queryScoreExchange(enfo.crm.score.ScoreExchangeVo, int, int)
	 */
	@Override
	public IPageList queryScoreExchange(ScoreExchangeVo vo,int pageIndex,int pageSize) throws BusiException {

		String sql = "{call SP_QUERY_TSCORECARD_CUST_EXCHANGE (?,?)}";

		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getExchange_date(),new Integer(0));

		try {
			return  super.listProcPage(sql, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("��ѯ�ͻ����ֶһ�����ʧ��: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.score.ScoreExchangeLocal#modiscoreExchange(enfo.crm.score.ScoreExchangeVo)
	 */
	@Override
	public void modiscoreExchange(ScoreExchangeVo vo) throws Exception {
		String sql = "{? = call SP_ADD_TSCORECARD_CUST_EXCHANGE (?,?,?,?)}";

		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getScore(),new Integer(0));
		params[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[3] = Utility.trimNull(vo.getSummary());
		
		super.cudProc(sql,params);
	}
}
