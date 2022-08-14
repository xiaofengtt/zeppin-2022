/*
 * 创建日期 2016-2-24
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.score;


import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;

@Component(value="scoreActivity")
public class ScoreActivityBean extends CrmBusiExBean implements ScoreActivityLocal {
	
	/* (non-Javadoc)
	 * @see enfo.crm.score.ScoreActivityLocal#queryScoreActivity(enfo.crm.score.ScoreActivityVo, int, int)
	 */
	@Override
	public IPageList queryScoreActivity(ScoreActivityVo vo,int pageIndex,int pageSize) throws BusiException {

		String sql = "{call SP_QUERY_TSCORECARD_ACTIVITY (?,?,?)}";

		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getDate_begin(),new Integer(0));
		params[1] = Utility.parseInt(vo.getDate_end(),new Integer(0));
		params[2] = Utility.parseInt(vo.getActivity_status(),new Integer(-1));
		
		try {
			return  super.listProcPage(sql, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("查询积分活动失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.score.ScoreActivityLocal#addScoreActivity(enfo.crm.score.ScoreActivityVo)
	 */
	@Override
	public void addScoreActivity(ScoreActivityVo vo)throws Exception{
		String sql = "{? = call SP_ADD_TSCORECARD_ACTIVITY (?,?,?,?,?,?)}";

		Object[] params = new Object[6];
		params[0] = Utility.parseInt(vo.getScore(),new Integer(0));
		params[1] = Utility.parseInt(vo.getDate_begin(),new Integer(0));
		params[2] = Utility.parseInt(vo.getDate_end(),new Integer(0));
		params[3] = Utility.parseInt(vo.getActivity_status(),new Integer(0));
		params[4] = Utility.parseInt(vo.getDf_activity_id(),new Integer(0));
		params[5] = Utility.trimNull(vo.getSummary());
		
		super.cudProc(sql,params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.score.ScoreActivityLocal#modiScoreActivity(enfo.crm.score.ScoreActivityVo)
	 */
	@Override
	public void modiScoreActivity(ScoreActivityVo vo) throws Exception {
		String sql = "{? = call SP_MODI_TSCORECARD_ACTIVITY (?,?,?,?,?,?,?)}";

		Object[] params = new Object[7];
		params[0] = Utility.parseInt(vo.getActivity_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getScore(),new Integer(0));
		params[2] = Utility.parseInt(vo.getDate_begin(),new Integer(0));
		params[3] = Utility.parseInt(vo.getDate_end(),new Integer(0));
		params[4] = Utility.parseInt(vo.getActivity_status(),new Integer(0));
		params[5] = Utility.parseInt(vo.getDf_activity_id(),new Integer(0));
		params[6] = Utility.trimNull(vo.getSummary());
		
		super.cudProc(sql,params);
	}
}
