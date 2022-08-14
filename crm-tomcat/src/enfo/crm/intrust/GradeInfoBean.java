 
package enfo.crm.intrust;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.GradeInfoVO;

@Component(value="gradeInfo")
public class GradeInfoBean extends enfo.crm.dao.IntrustBusiExBean implements GradeInfoLocal {

	/**
	 * 查询评级体系
	 */
	private static final String listSql = "{call SP_QUERY_TGRADEINFO(?)}";
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeInfoLocal#queryGradAll(enfo.crm.vo.GradeInfoVO, int, int)
	 */
	@Override
	public IPageList queryGradAll(GradeInfoVO vo, int pageIndex, int pageSize) throws BusiException {
		IPageList pageList = null;
		Object[] params = new Object[1];
		params[0] = Utility.parseInt(vo.getGrade_id(), new Integer(0));
		pageList = super.listProcPage(listSql, params, pageIndex, pageSize);
		return pageList;
	}
}