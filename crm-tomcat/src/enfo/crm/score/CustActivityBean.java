/*
 * �������� 2016-3-1
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

@Component(value="custActivity")
public class CustActivityBean extends CrmBusiExBean implements CustActivityLocal {
	
	/* (non-Javadoc)
	 * @see enfo.crm.score.CustActivityLocal#queryCustActivity(enfo.crm.score.CustActivityVo, int, int)
	 */
	@Override
	public IPageList queryCustActivity(CustActivityVo vo,int pageIndex,int pageSize) throws BusiException {

		String sql = "{call SP_QUERY_TSCORECARD_CUST_ACTIVITY (?,?)}";

		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));

		try {
			return  super.listProcPage(sql, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("��ѯ�ͻ��ʧ��: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.score.CustActivityLocal#addCustActivity(enfo.crm.score.CustActivityVo)
	 */
	@Override
	public void addCustActivity(CustActivityVo vo) throws Exception {
		String sql = "{? = call SP_ADD_TSCORECARD_CUST_ACTIVITY (?,?,?,?)}";

		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getActivity_id(),new Integer(0));
		params[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[3] = Utility.trimNull(vo.getSummary());
		
		super.cudProc(sql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.score.CustActivityLocal#delCustActivity(enfo.crm.score.CustActivityVo)
	 */
	@Override
	public void delCustActivity(CustActivityVo vo) throws Exception {
		String sql = "{? = call SP_DEL_TSCORECARD_CUST_ACTIVITY (?,?)}";
		
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getActivity_id(),new Integer(0));
		
		super.cudProc(sql,params);
	}
}
