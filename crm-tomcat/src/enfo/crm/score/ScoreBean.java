/*
 * �������� 2016-1-28
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

@Component(value="score")
public class ScoreBean extends CrmBusiExBean implements ScoreLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.score.ScoreLocal#queryCustScore(enfo.crm.score.ScoreVo, int, int)
	 */
	@Override
	public IPageList queryCustScore(ScoreVo vo,int pageIndex,int pageSize) throws BusiException {

		String sql = "{call SP_QUERY_TSCORECARD_CUST (?)}";

		Object[] params = new Object[1];
		params[0] = Utility.trimNull(vo.getCustName());

		try {
			return  super.listProcPage(sql, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("��ѯ�ͻ�����ʧ��: " + e.getMessage());
		}
	}
	
}
