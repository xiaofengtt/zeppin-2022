package enfo.crm.intrust;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.GradeInfoVO;

public interface GradeInfoLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询评级体系设置
	 * <pre>
	 * SP_QUERY_TGRADEINFO 	@IN_GRADE_ID
	 * </pre>
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return pageList
	 */
	IPageList queryGradAll(GradeInfoVO vo, int pageIndex, int pageSize) throws BusiException;

}