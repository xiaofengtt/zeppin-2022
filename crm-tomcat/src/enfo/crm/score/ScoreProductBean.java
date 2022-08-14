/*
 * 创建日期 2016-2-29
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.score;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;

@Component(value="scoreProduct")
public class ScoreProductBean extends CrmBusiExBean implements ScoreProductLocal {
	
	/* (non-Javadoc)
	 * @see enfo.crm.score.ScoreProductLocal#queryScoreProduct(enfo.crm.score.ScoreProductVo, int, int)
	 */
	@Override
	public IPageList queryScoreProduct(ScoreProductVo vo,int pageIndex,int pageSize) throws BusiException {

		String sql = "{call SP_QUERY_TSCORECARD_RULE_RATE (?,?)}";

		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getSubproduct_id(),new Integer(0));

		try {
			return  super.listProcPage(sql, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("查询产品积分倍率失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.score.ScoreProductLocal#addScoreProduct(enfo.crm.score.ScoreProductVo)
	 */
	@Override
	public void addScoreProduct(ScoreProductVo vo) throws Exception {
		String sql = "{? = call SP_ADD_TSCORECARD_RULE_RATE (?,?,?,?)}";

		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getSubproduct_id(),new Integer(0));
		params[2] = Utility.parseDecimal(Utility.trimNull(vo.getScore_rate()),new BigDecimal(0), 2,"1");
		params[3] = Utility.trimNull(vo.getSummary());
		
		super.cudProc(sql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.score.ScoreProductLocal#modiScoreProduct(enfo.crm.score.ScoreProductVo)
	 */
	@Override
	public void modiScoreProduct(ScoreProductVo vo) throws Exception {
		String sql = "{? = call SP_MODI_TSCORECARD_RULE_RATE (?,?,?,?,?)}";

		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getRule_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[2] = Utility.parseInt(vo.getSubproduct_id(),new Integer(0));
		params[3] = Utility.parseDecimal(Utility.trimNull(vo.getScore_rate()),new BigDecimal(0), 2,"1");
		params[4] = Utility.trimNull(vo.getSummary());
		
		super.cudProc(sql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.score.ScoreProductLocal#delScoreProduct(enfo.crm.score.ScoreProductVo)
	 */
	@Override
	public void delScoreProduct(ScoreProductVo vo) throws Exception {
		String sql = "{? = call SP_DEL_TSCORECARD_RULE_RATE (?)}";

		Object[] params = new Object[1];
		params[0] = Utility.parseInt(vo.getRule_id(),new Integer(0));
		
		super.cudProc(sql,params);
	}
}
